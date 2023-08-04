package bancolombia.dtd.vd.sucursales.api.datosCliente.util;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.UnsupportedCharsetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import bancolombia.dtd.vd.sucursales.api.datosCliente.dto.JsonModelRequest;
import bancolombia.dtd.vd.sucursales.api.datosCliente.dto.SaveReponse;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bancolombia.vtd.api.persistence.business.OfertaDigitalBusiness;

import com.bcol.vtd.lib.comunes.exception.ConectorClientException;
import com.google.gson.Gson;
import com.grupobancolombia.intf.clientes.gestionclientes.recuperardatoscliente.enlace.v4.ClienteNoExisteBusinessExceptionMsg;
import com.grupobancolombia.intf.producto.comun.gestionsolicitudcreditoconsumo.enlace.v2.BusinessExceptionMsg;
import com.grupobancolombia.intf.producto.comun.gestionsolicitudcreditoconsumo.enlace.v2.SystemExceptionMsg;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;


import b1c.li.proxy.gestionCreditoConsumo.ServicioGestionCreditoConsumo;
import bancolombia.dtd.vd.li.commons.exception.ExceptionLI;
import bancolombia.dtd.vd.li.commons.util.CargadorPropiedades;
import bancolombia.dtd.vd.li.commons.util.ConstantesGeneracionPDF;
import bancolombia.dtd.vd.li.commons.util.Parametro;
import bancolombia.dtd.vd.li.dto.mensajesError.DataRequest;
import bancolombia.dtd.vd.li.dto.mensajesError.DataResponse;

import bancolombia.dtd.vd.li.dto.mensajesError.ErrorRequestDTO;
import bancolombia.dtd.vd.li.dto.mensajesError.ErrorResponseDTO;

import bancolombia.dtd.vd.li.dto.mensajesError.Header;
import bancolombia.dtd.vd.li.dto.mensajesError.MensajeFuncionalDTO;

import bancolombia.dtd.vd.li.dto.proxy.codAsesor.ConsultarDatosSucursalRQ;
import bancolombia.dtd.vd.li.dto.proxy.codAsesor.ConsultarDatosSucursalRS;
import bancolombia.dtd.vd.li.dto.proxy.comun.RespuestaServicio;
import bancolombia.dtd.vd.li.dto.proxy.consultarProductoDepositos.ConsultarProductoDepositosRQ;
import bancolombia.dtd.vd.li.dto.proxy.consultarProductoDepositos.ConsultarProductoDepositosRS;
import bancolombia.dtd.vd.li.dto.proxy.consultarProductoDepositos.Cuentas;
import bancolombia.dtd.vd.li.dto.proxy.datosCliente.ConsultarDatosClienteRQ;
import bancolombia.dtd.vd.li.dto.proxy.datosCliente.RecuperarDatosBasicosClienteResponse;
import bancolombia.dtd.vd.li.dto.proxy.datosCliente.RecuperarDatosUbicacionClienteResponse;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRQ;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRS;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.OfertaDigital;
import bancolombia.dtd.vd.li.dto.proxy.gestionPrecalculadoCliente.GestionPrecalculadoClienteRQ;
import bancolombia.dtd.vd.li.dto.proxy.gestionPrecalculadoCliente.GestionPrecalculadoClienteRS;
import suc.lib.consulta.datos.comerciales.sucursal.ServicioConsultaDatosComercialesSucursal;
import suc.lib.consulta.producto.deposito.ServicioConsultaProductoDeposito;
import suc.lib.gestion.precalculado.cliente.ServicioGestionPrecalculadoCliente;
import suc.lib.recuperar.datos.cliente.ServicioRecuperarDatosCliente;

@SuppressWarnings("deprecation")
public class CallRestServiceBack implements Serializable {

    private static final long serialVersionUID = 1304286930347425535L;
    private String mensaje = "";
    private static final String CONTENT_TYPE = "application/json;";
    private static final String CHARSET = "UTF-8";
    private Client client = null;
    private static Gson json = new Gson();
    protected static CargadorPropiedades propiedades = CargadorPropiedades.getInstance();
    private Utilities util = new Utilities();

    @Inject
    private OfertaDigitalBusiness ofertaDigiPersis;

    public CallRestServiceBack() {
        try {
            if (client == null) {
                DefaultClientConfig defaultClientConfig = new DefaultClientConfig();
                defaultClientConfig.getClasses().add(JacksonJsonProvider.class);
                client = Client.create(defaultClientConfig);
            }
        } catch (Exception ex) {
            logger.error(" Error iniciando constructor CallRestServiceBack: ", ex.getMessage());
        }

    }

    private static final Logger logger = LogManager.getLogger(CallRestServiceBack.class);

    /**
     * @param consultarDatosSucursalRQ
     * @param propiedades
     * @return
     * @throws IOException
     * @throws @throws     Exception
     */
    public ConsultarDatosSucursalRS proxyDatosComercialesSucursal(ConsultarDatosSucursalRQ consultarDatosSucursalRQ,
                                                                  Map<String, String> propiedades) {
        ConsultarDatosSucursalRS respuesta = null;
        try {
            ServicioConsultaDatosComercialesSucursal service = new ServicioConsultaDatosComercialesSucursal(propiedades,
                    consultarDatosSucursalRQ.getIdSesion());

            respuesta = service.consultarDatosSucursal(consultarDatosSucursalRQ.getDatoConsulta(),
                    consultarDatosSucursalRQ.getTipoConsulta());

        } catch (Exception e) {
            // se utiliza excepcion generica ya que las excecpiones custom estan capturadas
            // en la libreria del llamado, pero si genera otra diferente a ella aqui se debe
            // capturar
            logger.error(" Error llamando microServicio Proxy proxyDatosComercialesSucursal: ", e);
            respuesta = new ConsultarDatosSucursalRS();
            respuesta.setCodError("500");
            respuesta.setDescError("Error en consumo de servicio Consulta Datos Comerciales Sucursal");
        }
        return respuesta;
    }

    public String llamadoBackInternoCatalogo(Object request, String endpoint) throws ExceptionLI, IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String salida = "";
        try {

            HttpPost postRequest = new HttpPost(endpoint);

            StringEntity input = new StringEntity(request.toString(), CHARSET);
            input.setContentType(CONTENT_TYPE);
            input.setContentEncoding(CHARSET);

            postRequest.setEntity(input);
            HttpResponse response = httpClient.execute(postRequest);

            response.getStatusLine();

            if (response.getStatusLine().getStatusCode() != ConstantesLI.DOCIENTOS) {
                mensaje = "Error microServicio : " + response.getStatusLine().getStatusCode();
                logger.log(Level.ERROR, mensaje);
                throw new ExceptionLI(response.getStatusLine().getStatusCode() + "",
                        ConstantesLI.MSG_ERROR_HTTP_CALL + response.getStatusLine().getStatusCode());
            }

            salida = new BasicResponseHandler().handleResponse(response);
            httpClient.getConnectionManager().shutdown();
        } catch (IOException e) {
            logger.error(" Error llamando microServicio llamadoBackInternoCatalogo: " + e.getMessage(), e);
        } finally {
            httpClient.close();
        }
        return salida;
    }

    public ConsultarProductoDepositosRS proxyConsultaCuentas(ConsultarProductoDepositosRQ consultarProductoDepositosRQ,
                                                             Map<String, String> propiedades) throws ExceptionLI {
        ConsultarProductoDepositosRS respuesta = null;

        try {
            ServicioConsultaProductoDeposito service = new ServicioConsultaProductoDeposito(propiedades,
                    consultarProductoDepositosRQ.getIdSesion());

            respuesta = service.consultarProductoDepositos(consultarProductoDepositosRQ);
        } catch (Exception e) {
            respuesta = new ConsultarProductoDepositosRS();
            respuesta.setCuentas(new ArrayList<Cuentas>());
        }
        return respuesta;
    }

    public GestionPrecalculadoClienteRS proxyConsultaPrecalculadoCliente(
            GestionPrecalculadoClienteRQ gestionPrecalculadoClienteRQ, Map<String, String> propiedades) {
        GestionPrecalculadoClienteRS respuesta = null;

        try {
            ServicioGestionPrecalculadoCliente service = new ServicioGestionPrecalculadoCliente(propiedades,
                    gestionPrecalculadoClienteRQ.getIdSesion());

            respuesta = service.consultarPrecalculadoBasico(gestionPrecalculadoClienteRQ);
        } catch (Exception e) {
            respuesta = new GestionPrecalculadoClienteRS();
            respuesta.setCodError("500");
            respuesta.setDescError("Error en consumo de servicio precalculado Cliente");
        }

        return respuesta;
    }

    public Object llamadoBackDatosCliente(ConsultarDatosClienteRQ consultarDatosClienteRQ,
                                          Map<String, String> propiedades, String operacion) throws ExceptionLI {

        Object salida = null;
        Utilities util = new Utilities();

        try {
            ServicioRecuperarDatosCliente service = new ServicioRecuperarDatosCliente(propiedades,
                    consultarDatosClienteRQ.getIdSesion());

            if (ConstantesLI.SERVICIO_RECUPERAR_DATOS_BASICOS_CLIENTE.equalsIgnoreCase(operacion)) {
                com.grupobancolombia.intf.clientes.gestionclientes.recuperardatoscliente.v4.
                        RecuperarDatosBasicosClienteResponse rep;
                rep = service.recuperarDatosBasicosCliente(consultarDatosClienteRQ);
                salida = util.transformarRespuestaDatosCliente(rep);

            } else if (ConstantesLI.SERVICIO_RECUPERAR_UBICACION_CLIENTE.equalsIgnoreCase(operacion)) {
                com.grupobancolombia.intf.clientes.gestionclientes.recuperardatoscliente.v4.
                        RecuperarDatosUbicacionClienteResponse rep;
                rep = service.recuperarDatosUbicacionCliente(consultarDatosClienteRQ);
                salida = json.fromJson(json.toJson(rep), RecuperarDatosUbicacionClienteResponse.class);
            }
        } catch (ClienteNoExisteBusinessExceptionMsg e) {
            logger.error(e);
            if (ConstantesLI.SERVICIO_RECUPERAR_DATOS_BASICOS_CLIENTE.equalsIgnoreCase(operacion)) {
                salida = new RecuperarDatosBasicosClienteResponse();
                ((RecuperarDatosBasicosClienteResponse) salida).setCodError(ConstantesLI.COD_ERROR_003);
                ((RecuperarDatosBasicosClienteResponse) salida).setDescError(e.getMessage());
                ((RecuperarDatosBasicosClienteResponse) salida).setTipoError(e.getClass().getName());
                ((RecuperarDatosBasicosClienteResponse) salida).setServicio("recuperarDatosBasicosCliente");

            } else if (ConstantesLI.SERVICIO_RECUPERAR_UBICACION_CLIENTE.equalsIgnoreCase(operacion)) {
                salida = new RecuperarDatosUbicacionClienteResponse();
                ((RecuperarDatosUbicacionClienteResponse) salida).setCodError("006");
                ((RecuperarDatosUbicacionClienteResponse) salida).setDescError(e.getMessage());
                ((RecuperarDatosUbicacionClienteResponse) salida).setTipoError(e.getClass().getName());
                ((RecuperarDatosUbicacionClienteResponse) salida).setServicio("recuperarDatosUbicacionCliente");
            }
        } catch (com.grupobancolombia.intf.clientes.gestionclientes.recuperardatoscliente.enlace.v4.SystemExceptionMsg e) {
            logger.error(e);

            if (ConstantesLI.SERVICIO_RECUPERAR_DATOS_BASICOS_CLIENTE.equalsIgnoreCase(operacion)) {
                salida = new RecuperarDatosBasicosClienteResponse();
                ((RecuperarDatosBasicosClienteResponse) salida).setCodError("500");
                ((RecuperarDatosBasicosClienteResponse) salida).setDescError(e.getMessage());
                ((RecuperarDatosBasicosClienteResponse) salida).setServicio("recuperarDatosBasicosCliente");
                ((RecuperarDatosBasicosClienteResponse) salida).setTipoError(e.getClass().getName());
            } else if (ConstantesLI.SERVICIO_RECUPERAR_UBICACION_CLIENTE.equalsIgnoreCase(operacion)) {
                salida = new RecuperarDatosUbicacionClienteResponse();
                ((RecuperarDatosUbicacionClienteResponse) salida).setCodError("500");
                ((RecuperarDatosUbicacionClienteResponse) salida).setDescError(e.getMessage());
                ((RecuperarDatosUbicacionClienteResponse) salida).setServicio("recuperarDatosUbicacionCliente");
                ((RecuperarDatosUbicacionClienteResponse) salida).setTipoError(e.getClass().getName());
            }
        } catch (Exception e) {
            logger.error(e);
            salida = new RecuperarDatosBasicosClienteResponse();
            ((RecuperarDatosBasicosClienteResponse) salida).setCodError("500");
            ((RecuperarDatosBasicosClienteResponse) salida).setDescError(e.getMessage());
            ((RecuperarDatosBasicosClienteResponse) salida).setServicio("recuperarDatosCliente");
            ((RecuperarDatosBasicosClienteResponse) salida).setTipoError(e.getClass().getName());
        }

        return salida;
    }

    /**
     * Metodo para el consumo del servicio Gestion Credito Consumo
     *
     * @param gestionCreditoConsumoRQ carga util para el consumo del servicio
     * @param propiedades             propiedades de configuracion usadas para el
     *                                servicio
     * @param paso                    paso que esta ejecutando
     * @return Respuesta con los datos del consumo al servicio
     * @throws ExceptionLI Excepcion personalizada que captura algun problema
     */
    public GestionCreditoConsumoRS proxyGestionCreditoConsumo(GestionCreditoConsumoRQ gestionCreditoConsumoRQ,
                                                              Map<String, String> propiedades, String paso) throws ExceptionLI {
        GestionCreditoConsumoRS respuesta = null;

        try {
            ServicioGestionCreditoConsumo service = new ServicioGestionCreditoConsumo(propiedades,
                    gestionCreditoConsumoRQ.getIdSesion());

            if (ConstantesLI.SERVICIO_OFERTA_DIGITAL_CREAR_SOL.equalsIgnoreCase(paso)) {
                respuesta = service.crearSolicitudCreditoConsumo(gestionCreditoConsumoRQ);
            } else if (ConstantesLI.SERVICIO_OFERTA_DIGITAL_AVANZAR_SOL.equalsIgnoreCase(paso)) {
                respuesta = service.avanzarSolicitudCreditoConsumo(gestionCreditoConsumoRQ);
            }

        } catch (BusinessExceptionMsg e) {
            logger.error(String.format(" Error BusinessExceptionMsg ServicioGestionCreditoConsumo %s ", e.getMessage()),
                    e);
            throw new ExceptionLI(e.getFaultInfo().getGenericException().getCode(), e.getMessage());
        } catch (SystemExceptionMsg e) {
            logger.error(String.format(" Error SystemExceptionMsg ServicioGestionCreditoConsumo %s ", e.getMessage()),
                    e);
            throw new ExceptionLI(e.getFaultInfo().getGenericException().getCode(), e.getMessage());
        } catch (Exception e) {
            // llamados a servicios banco que pueden generar algun otro tipo de excepcion y
            // debe ser capturada pero no es conocida
            throw new ExceptionLI("500", e.getMessage());
        }
        return respuesta;
    }

    /**
     * Metodo para buscar las ofertas retornadas por bizagi
     *
     * @param numeroSolicitud numero de la solicitud a buscar
     * @param paso            paso de la solicitud que se busca
     * @return Orferta digital que lleda desde bizagi
     * @throws ExceptionLI Control de excepcion del flujo
     */
    public OfertaDigital apiGestionCreditos(String numeroSolicitud, String paso) throws ExceptionLI {
        OfertaDigital ofertaDigital = null;
        try {

            String oferta = ofertaDigiPersis.getOfertaDigitaByNumeroSolicitud(numeroSolicitud, paso);
            ofertaDigital = json.fromJson(oferta, OfertaDigital.class);
        } catch (ConectorClientException e) {
            logger.error(" Error buscando caso en DB, apiGestionCreditos-ConectorClientException");
        } catch (Exception e) {
            // se utiliza excepcion generica ya que se realiza consumo a la Base de datos y
            // se pueden generar excepiones no mapeadas que pueden generar flujos diferentes
            // para la experiencia
            // se controlan los conocidos y se deja evidencia en el log para tratamientos
            // futuros.
            logger.error(String.format(" Error buscando caso en DB, apiGestionCreditos-Exception: %s ", e.getMessage()),
                    e);
        }

        return ofertaDigital;
    }

    /**
     * Metodo para guardar info en la bade de datos
     *
     * @param conf      Datos para saber donde guardar
     * @param cargaUtil datos a guardar
     * @return resultado de la operacion
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public String guardarDatosDB(Map<?, ?> conf, String cargaUtil) {
        SaveReponse saveReponse = new SaveReponse();
        try {
            Map envio = new HashMap<>();
            envio.put("Conf", conf);
            envio.put("Utils", cargaUtil);
            String sessionId = (String) conf.get("ID_SESION");
            String paso = (String) conf.get("PASO");
            String url = propiedades.getValue(ConstantesLI.ENDPOINT_PERSISTENCE);

            JsonModelRequest jsonModelRequest = new JsonModelRequest(LocalDateTime.now(), sessionId, paso, cargaUtil);
            WebResource webResource= client.resource(url);
            ClientResponse clientResponse=webResource.type(MediaType.APPLICATION_JSON)
                    .post(ClientResponse.class,jsonModelRequest);

            if (clientResponse != null && clientResponse.getStatus() == ConstantesLI.DOCIENTOS) {
                saveReponse=clientResponse.getEntity(SaveReponse.class);
                mensaje = " Guardar datosEnviadosDB: " + json.toJson(envio);
                logger.info(mensaje);
            }

            return "OK ".concat(json.toJson(saveReponse));
        } catch (Exception e) {
            mensaje = " Error guardarDatosDB: " + e;
            logger.error(mensaje);
            return e.getMessage();
        }
    }


    /**
     * Metodo que prepara los datos para consumir el API de envio de correo.
     *
     * @param datos    Map<String, Object>
     * @param endPoint String
     * @return Response
     * @throws ExceptionLI ExceptionLI
     */
    public Response enviarCorreo(Map<String, Object> datos, String endPoint) throws ExceptionLI {
        String url = endPoint;
        RespuestaServicio respuestaServicio = new RespuestaServicio();
        respuestaServicio.setCodigo("500");

        List<Parametro> listaParametros = new ArrayList<>();
        listaParametros
                .add(new Parametro(ConstantesGeneracionPDF.ARCHIVO1, datos.get(ConstantesGeneracionPDF.DOCUMENTO_PDF)));
        listaParametros.add(new Parametro(ConstantesGeneracionPDF.PARA, datos.get(ConstantesGeneracionPDF.EMAIL)));
        listaParametros
                .add(new Parametro(ConstantesGeneracionPDF.NOMBRE, datos.get(ConstantesGeneracionPDF.NOMBRE_CLIENTE)));
        listaParametros
                .add(new Parametro(ConstantesGeneracionPDF.DOCUMENTO, datos.get(ConstantesGeneracionPDF.NO_DOCUMENTO)));

        // Se ingresan los parametros que vienen del map
        for (Entry<String, Object> dato : datos.entrySet()) {
            if (ConstantesGeneracionPDF.RUTA_PLANTILLA_CORREO.equals(dato.getKey())) {
                listaParametros.add(new Parametro(dato.getKey(), dato.getValue().toString()));
            } else if (ConstantesGeneracionPDF.ASUNTO_CORREO.equals(dato.getKey())) {
                listaParametros.add(new Parametro(dato.getKey(), dato.getValue().toString()));
            } else if (ConstantesGeneracionPDF.IMAGENES.equals(dato.getKey())) {
                listaParametros.add(new Parametro(dato.getKey(), dato.getValue().toString()));
            } else {
                listaParametros.add(new Parametro(dato.getKey(), dato.getValue()));
            }
        }

        WebResource resource = client.resource(url);
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, listaParametros);

        if (response != null && response.getStatus() == ConstantesLI.DOCIENTOS) {
            respuestaServicio = response.getEntity(RespuestaServicio.class);

        } else {
            respuestaServicio.setDescripcion("Ocurrio un error intentando enviar el email");
        }

        if (respuestaServicio.getCodigo().equals("500")) {
            throw new ExceptionLI(respuestaServicio.getCodigo(), respuestaServicio.getDescripcion());
        }
        return Response.status(Status.OK).entity(respuestaServicio).build();
    }

    public ErrorResponseDTO errorService(ErrorRequestDTO errorRequestDTO, String endPoint, String mensajeDefault)
            throws ExceptionLI {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
        List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            String codErrorDefault = "DEFAULT-500";
            Header header = new Header();
            header.setId(ConstantesLI.ID_HEADER_MENSAJE_ERROR);
            header.setType(ConstantesLI.TYPE_HEADER_MENSAJE_ERROR);
            errorRequestDTO.setHeader(header);

            HttpPost postRequest = new HttpPost(endPoint);

            DataRequest rq = new DataRequest();
            ArrayList<ErrorRequestDTO> datos = new ArrayList<ErrorRequestDTO>();
            datos.add(errorRequestDTO);
            rq.setData(datos);

            StringEntity input = new StringEntity(json.toJson(rq), CHARSET);
            input.setContentType(MediaType.APPLICATION_JSON);
            input.setContentEncoding(CHARSET);

            postRequest.setEntity(input);
            HttpResponse response;
            try {
                response = httpClient.execute(postRequest);
                response.getStatusLine();

                if (response.getStatusLine().getStatusCode() != ConstantesLI.DOCIENTOS) {
                    mensaje = "Error microServicio Proxy errorService: " + response.getStatusLine().getStatusCode();
                    logger.error(mensaje);
                    errorResponseDTO.setCodigoError(codErrorDefault);
                    errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
                    mensajeFuncional.setCodigoFuncional(codErrorDefault);
                    mensajeFuncional.setDescripcionFuncional(mensajeDefault);
                    listaMensajes.add(mensajeFuncional);
                    errorResponseDTO.setMensajeFuncional(listaMensajes);
                } else {
                    String salida = new BasicResponseHandler().handleResponse(response);
                    DataResponse listErrorResponsetDTO = json.fromJson(salida, DataResponse.class);
                    errorResponseDTO = listErrorResponsetDTO.getData().get(0).getMensajeError().get(0);
                }
            } catch (ClientProtocolException e) {
                mensaje = "Error ClientProtocolException microServicio Proxy errorService: " + e.getMessage();
                logger.error(mensaje, e);
                errorResponseDTO.setCodigoError(codErrorDefault);
                errorResponseDTO.setDescripcionTecnica(mensajeDefault);
                mensajeFuncional.setCodigoFuncional(codErrorDefault);
                mensajeFuncional.setDescripcionFuncional(mensajeDefault);
                listaMensajes.add(mensajeFuncional);
                errorResponseDTO.setMensajeFuncional(listaMensajes);
            } catch (IOException e) {
                mensaje = "Error IOException microServicio Proxy errorService: " + e.getMessage();
                logger.error(mensaje, e);
                errorResponseDTO.setCodigoError(codErrorDefault);
                errorResponseDTO.setDescripcionTecnica(mensajeDefault);
                mensajeFuncional.setCodigoFuncional(codErrorDefault);
                mensajeFuncional.setDescripcionFuncional(mensajeDefault);
                listaMensajes.add(mensajeFuncional);
                errorResponseDTO.setMensajeFuncional(listaMensajes);
            }
            httpClient.getConnectionManager().shutdown();
        } catch (UnsupportedCharsetException e) {
            logger.error(" Error llamando microServicio Proxy errorService: " + e.getMessage(), e);
        } finally {
            httpClient.close();
        }
        return errorResponseDTO;
    }
}