package bancolombia.dtd.vd.sucursales.api.datosCliente;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.grupobancolombia.intf.canal.movil.generaciontoken.v1.SolicitarTokenResponse;
import com.grupobancolombia.intf.canal.movil.generaciontoken.v1.ValidarTokenResponse;
import com.grupobancolombia.intf.seguridad.autenticacion.gestionautenticacionfuerte.enlace.v1.BusinessExceptionMsg;
import com.grupobancolombia.intf.seguridad.autenticacion.gestionautenticacionfuerte.enlace.v1.SystemExceptionMsg;
import com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.v2.AutenticarClienteOTPODAResponse;
import com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.v2.AutenticarClienteOTPSoftokenResponse;
import com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.v2.GenerarEnviarOTPODAResponse;

import bancolombia.dtd.vd.li.api.autenticacion.ServicioAutenticacion;

import bancolombia.dtd.vd.li.api.autenticacion.util.UtilsJwt;
import bancolombia.dtd.vd.li.commons.exception.ExceptionLI;
import bancolombia.dtd.vd.li.commons.util.CargadorPropiedades;
import bancolombia.dtd.vd.li.commons.util.ConstantesGeneracionPDF;
import bancolombia.dtd.vd.li.commons.util.Parametro;
import bancolombia.dtd.vd.li.dto.autenticacion.AutenticacionCont;
import bancolombia.dtd.vd.li.dto.autenticacion.AutenticacionRS;
import bancolombia.dtd.vd.li.dto.catalogos.Catalogo;
import bancolombia.dtd.vd.li.dto.catalogos.Catalogos;

import bancolombia.dtd.vd.li.dto.mensajesError.ErrorRequestDTO;
import bancolombia.dtd.vd.li.dto.mensajesError.ErrorResponseDTO;
import bancolombia.dtd.vd.li.dto.mensajesError.MensajeFuncionalDTO;
import bancolombia.dtd.vd.li.dto.proxy.authFuerte.AutenticacionFuerteRQ;
import bancolombia.dtd.vd.li.dto.proxy.authFuerte.AutenticacionFuerteRS;
import bancolombia.dtd.vd.li.dto.proxy.clave.GeneracionClaveRQ;
import bancolombia.dtd.vd.li.dto.proxy.clave.GeneracionClaveRS;
import bancolombia.dtd.vd.li.dto.proxy.clave.ValidacionClaveRQ;
import bancolombia.dtd.vd.li.dto.proxy.clave.ValidacionClaveRS;
import bancolombia.dtd.vd.li.dto.proxy.codAsesor.ConsultarDatosSucursalRQ;
import bancolombia.dtd.vd.li.dto.proxy.codAsesor.ConsultarDatosSucursalRS;
import bancolombia.dtd.vd.li.dto.proxy.comun.ErrorProxyComun;
import bancolombia.dtd.vd.li.dto.proxy.consultarProductoDepositos.ConsultarProductoDepositosRQ;
import bancolombia.dtd.vd.li.dto.proxy.consultarProductoDepositos.ConsultarProductoDepositosRS;
import bancolombia.dtd.vd.li.dto.proxy.consultarProductoDepositos.ConsultarProductoDepositosRSApi;
import bancolombia.dtd.vd.li.dto.proxy.datosCliente.ConsultarDatosClienteRQ;
import bancolombia.dtd.vd.li.dto.proxy.datosCliente.ConsultarDatosClienteRS;
import bancolombia.dtd.vd.li.dto.proxy.datosCliente.DireccionDto;
import bancolombia.dtd.vd.li.dto.proxy.datosCliente.InformacionUbicacionCliente;
import bancolombia.dtd.vd.li.dto.proxy.datosCliente.RecuperarDatosBasicosClienteResponse;
import bancolombia.dtd.vd.li.dto.proxy.datosCliente.RecuperarDatosUbicacionClienteResponse;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRQ;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRS;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.OfertaDigital;
import bancolombia.dtd.vd.li.dto.proxy.gestionPrecalculadoCliente.GestionPrecalculadoClienteRQ;
import bancolombia.dtd.vd.li.dto.proxy.gestionPrecalculadoCliente.GestionPrecalculadoClienteRS;
import bancolombia.dtd.vd.li.dto.proxy.gestionPrecalculadoCliente.ProductoPreaprobadoBasicoRS;
import bancolombia.dtd.vd.li.dto.proxy.validacionpreguntas.ValidacionPreguntasRQ;
import bancolombia.dtd.vd.li.dto.proxy.validacionpreguntas.ValidacionPreguntasRS;
import bancolombia.dtd.vd.li.dto.utils.EnvioCorreoDto;
import bancolombia.dtd.vd.li.dto.utils.InformacionTransaccionDto;
import bancolombia.dtd.vd.li.dto.utils.LibreInversion;
import bancolombia.dtd.vd.sucursales.api.datosCliente.util.CallRestServiceBack;
import bancolombia.dtd.vd.sucursales.api.datosCliente.util.ConstantesLI;
import bancolombia.dtd.vd.sucursales.api.datosCliente.util.PlantillasUtil;
import bancolombia.dtd.vd.sucursales.api.datosCliente.util.Utilities;
import org.jose4j.jwt.MalformedClaimException;
import suc.lib.autenticarClienteOTP.ServicioAutenticarClienteOTP;
import suc.lib.autenticarClienteOTP.util.AutenticarClienteOTPRQ;
import suc.lib.generacionToken.ServicioGeneracionToken;
import suc.lib.generacionToken.util.GeneracionTokenRQ;
import suc.lib.generarOTP.ServicioGenerarOTP;
import suc.lib.generarOTP.util.AutenticacionOTPRQ;
import suc.lib.generarOTP.util.Destinatario;
import suc.lib.generarOTP.util.InformacionMensajeType;
import suc.lib.gestionauthfuerte.ServicioGestionAutenticacionFuerte;
import suc.lib.gestionauthfuerte.util.PeticionGestionAutenticacionFuerte;
import suc.lib.gestionauthfuerte.util.RespuestaGestionAutenticacionFuerte;

/**
 * Clase para realizar los flujos de los diferentes servicios utilizados por la
 * experiencia, desde aqui se controla los flujos por los cuales debe pasar una
 * E2E y las validaciones respectivas de autenticacion y acceso a cada recurso
 *
 *
 */

public class ServicioDatosCliente {

	private static final Logger logger = LogManager.getLogger(ServicioDatosCliente.class);

	private String mensaje = "";

	protected static CargadorPropiedades propiedades = CargadorPropiedades.getInstance();
	private Map<Object, Object> param;
	private static final Gson gson = new Gson();

	@Inject
	CallRestServiceBack callRestServiceBack;

	private LibreInversion libreInversion = new LibreInversion();
	private ErrorProxyComun er = new ErrorProxyComun();

	@Inject
	private ServicioAutenticacion autenticacion;

	private Utilities util = new Utilities();
	UtilsJwt utilsJwt = new UtilsJwt();

	public CargadorPropiedades recargarConf() {
		propiedades = CargadorPropiedades.getInstanceAuto();
		return propiedades;
	}

	/**
	 * Metodo para realizar consumo y validacion del asesor que esta realizando el
	 * proceso
	 *
	 * @param authorization JWT con la autorizacion del proceso
	 * @param encrypter     Carga Util para realizar el flujo
	 * @param ipCliente     ip desde donde se realiza el consumo
	 * @return datos de respuesta del flujo realizado
	 * @throws ExceptionLI control de algun excepcion
	 */
	public ConsultarDatosSucursalRS consultarAsesor(String authorization, String encrypter, String ipCliente)
			throws ExceptionLI {
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		ConsultarDatosSucursalRS respuesta = new ConsultarDatosSucursalRS();
		ConsultarDatosSucursalRQ consultarDatosSucursalRQ = new ConsultarDatosSucursalRQ();
		AutenticacionRS sesion = new AutenticacionRS();
		param = new HashMap<>();
		String cargaUtil = null;
		try {
			mensaje = "Inicio consumo datos cliente sucursal : " + ipCliente;
			logger.info(mensaje);
			cargaUtil = encrypter;
			consultarDatosSucursalRQ = gson.fromJson(cargaUtil, ConsultarDatosSucursalRQ.class);

			param.put(ConstantesLI.DB_ID_SESION, consultarDatosSucursalRQ.getIdSesion());
			param.put(ConstantesLI.DB_PASO, consultarDatosSucursalRQ.getPasoFuncional());
			this.guardarDatosDB(param, cargaUtil);

			if (consultarDatosSucursalRQ.getTipoConsulta()
					.equalsIgnoreCase(ConstantesLI.TRES_TXT)) {// recibir_el_token_primer_consumo
				sesion = this.validarSesion(authorization, consultarDatosSucursalRQ.getPasoFuncional(),
						consultarDatosSucursalRQ.getIdSesion());
			}
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, consultarDatosSucursalRQ.getIdSesion());
			logger.info("Consulta contra servicio Cod Asesor");
			// consulta por numero de identificacion del asesor
			consultarDatosSucursalRQ.setDatoConsulta(
					StringUtils.leftPad(consultarDatosSucursalRQ.getDatoConsulta(), ConstantesLI.TRES, "0"));

			respuesta = callRestServiceBack.proxyDatosComercialesSucursal(consultarDatosSucursalRQ,
					propiedades.getPropiedades());

			// Guardar consumo del servicio sea cual sea el estado
			mensaje = "Fin consumo datos cliente sucursal" + consultarDatosSucursalRQ.getIdSesion();
			logger.info(mensaje);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, consultarDatosSucursalRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_R);

			if (StringUtils.isNotEmpty(respuesta.getCodError())) {
				if (ConstantesLI.VAL_CONSUMO_COD_ASESOR_DATOS_ASESOR
						.contains(consultarDatosSucursalRQ.getTipoConsulta())) {
					mapearMensajeError(respuesta, "CDCSA-" + respuesta.getCodError());
				} else if (ConstantesLI.VAL_CONSUMO_COD_ASESOR_DATOS_SUCURSAL
						.equalsIgnoreCase(consultarDatosSucursalRQ.getTipoConsulta())) {
					mapearMensajeError(respuesta, "CDCS-" + respuesta.getCodError());
				}
				param.put(ConstantesLI.DB_PASO,
						consultarDatosSucursalRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			}
			if (ConstantesLI.TRES_TXT
					.equalsIgnoreCase(consultarDatosSucursalRQ.getTipoConsulta())) {
				respuesta.setTokenActual(sesion.getTokenNuevo());
			}
			respuesta.setTipoConsulta(consultarDatosSucursalRQ.getTipoConsulta());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (ExceptionLI e) {
			mensaje = "Error desencriptando carga util" + consultarDatosSucursalRQ.getIdSesion() + e.getMensaje();
			logger.error(mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, consultarDatosSucursalRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError(e.getCodigo());
			respuesta.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuesta,  e.getCodigo());
			}else{
				mapearMensajeError(respuesta, "CDCS-" + e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(respuesta));
		}
		return respuesta;
	}

	public ConsultarProductoDepositosRSApi consultaCuentas(String authorization, String encrypter, String ipCliente)
			throws ExceptionLI, IOException {
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		ConsultarProductoDepositosRS respuesta = new ConsultarProductoDepositosRS();
		ConsultarProductoDepositosRSApi respuestaApi = new ConsultarProductoDepositosRSApi();
		ConsultarProductoDepositosRQ consultarProductoDepositosRQ = new ConsultarProductoDepositosRQ();
		param = new HashMap<>();
		try {

			this.libreInversion = gson.fromJson(encrypter, LibreInversion.class);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID,
					this.libreInversion.getInformacionTransaccion().getIdSesion());

			param.put(ConstantesLI.DB_ID_SESION, this.libreInversion.getInformacionTransaccion().getIdSesion());
			param.put(ConstantesLI.DB_PASO, this.libreInversion.getInformacionTransaccion().getPasoFuncional());
			this.guardarDatosDB(param, encrypter);
			mensaje = "Inicio consulta contra servicio consultaCuentas"
					+ this.libreInversion.getInformacionTransaccion().getIdSesion();
			logger.info(mensaje);

			AutenticacionRS sesion = this.validarSesion(authorization,
					this.libreInversion.getInformacionTransaccion().getPasoFuncional(),
					this.libreInversion.getInformacionTransaccion().getIdSesion());
			respuestaApi.setTokenActual(sesion.getTokenNuevo());

			consultarProductoDepositosRQ.setIdSesion(this.libreInversion.getInformacionTransaccion().getIdSesion());
			consultarProductoDepositosRQ
					.setNumeroDocumento(this.libreInversion.getDatosCliente().getNumeroIdentificacion());
			consultarProductoDepositosRQ
					.setTipoDocumento(this.libreInversion.getDatosCliente().getTipoIdentificacion());
			consultarProductoDepositosRQ.setNumeroPagina(ConstantesLI.DEP_NUMEROPAGINA);
			consultarProductoDepositosRQ.setCantidadRegistro(ConstantesLI.DEP_CANTIDADREGISTRO);

			respuesta = callRestServiceBack.proxyConsultaCuentas(consultarProductoDepositosRQ,
					propiedades.getPropiedades());

			param.remove(ConstantesLI.DB_PASO);
			this.libreInversion.setCuentasDisponibles(util.filtrarCuentas(respuesta.getCuentas(), false, this.libreInversion));
			if (StringUtils.isNotEmpty(respuesta.getCodError())) {
				mapearMensajeError(respuestaApi, "CPD-" + respuesta.getCodError());
				param.put(ConstantesLI.DB_PASO,
						this.libreInversion.getInformacionTransaccion().getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			} else {
				respuestaApi.setCuentas(util.filtrarCuentas(respuesta.getCuentas(), true, this.libreInversion));
				respuestaApi.setTokenActual(sesion.getTokenNuevo());
				param.put(ConstantesLI.DB_PASO,
						this.libreInversion.getInformacionTransaccion().getPasoFuncional() + ConstantesLI.RESPUESTA_R);
			}

			/**
			 * solo aplica para productos diferentes a crediagil y TDC
			 */
			if (respuestaApi.getCuentas().isEmpty()
					&& !(this.libreInversion.isEsCrediagil() || this.libreInversion.isEsTarjetaCredito())) {
				mapearMensajeError(respuestaApi, "CPD-600");
				param.put(ConstantesLI.DB_PASO,
						this.libreInversion.getInformacionTransaccion().getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			}

			if (this.libreInversion.isEsCrediagil() || this.libreInversion.isEsTarjetaCredito()) {
				param.remove(ConstantesLI.DB_PASO);
				param.put(ConstantesLI.DB_PASO,
						this.libreInversion.getInformacionTransaccion().getPasoFuncional() + ConstantesLI.RESPUESTA_R);
			}

			respuestaApi.setPreguntas(util.generacionPreguntas(this.libreInversion, propiedades, callRestServiceBack));
			mensaje = "Fin consumo Consulta cuentas" + consultarProductoDepositosRQ.getIdSesion();
			logger.info(mensaje);
			this.guardarDatosDB(param, gson.toJson(respuestaApi));
		} catch (ExceptionLI e) {
			mensaje = "Error consultaCuentas" + consultarProductoDepositosRQ.getIdSesion() + e.getMensaje();
			logger.error(mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO,
					this.libreInversion.getInformacionTransaccion().getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuestaApi.setCodError(e.getCodigo());
			respuestaApi.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuesta,  e.getCodigo());
			}else{
				mapearMensajeError(respuestaApi, "CPD-" + e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(respuestaApi));
		}
		return respuestaApi;
	}

	/**
	 * Metodo para consultar las direcciones de un cliente
	 *
	 * @param authorization JWT con la autorizacion del consumo
	 * @param encrypter     carga util con los datos para el consumo
	 * @param ipCliente     ip desde donde se realiza el consumo
	 * @return lista de direcciones en el siguiente orden si aplica, 0 para
	 *         direccion Z001 y 1 para la Z002
	 * @throws ExceptionLI excepcion sobre el proceso
	 * @throws IOException excepcion sobre el proceso
	 */
	@SuppressWarnings("rawtypes")
	public ConsultarDatosClienteRS consultaDatosUbicacion(String authorization, String encrypter, String ipCliente)
			throws ExceptionLI, IOException {
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		ConsultarDatosClienteRS respuestaRS = new ConsultarDatosClienteRS();
		ConsultarDatosClienteRQ consultarDatosClienteRQ = new ConsultarDatosClienteRQ();
		param = new HashMap<>();
		try {
			consultarDatosClienteRQ = obtenerDatosCliente(encrypter);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, consultarDatosClienteRQ.getIdSesion());

			AutenticacionRS sesion = this.validarSesion(authorization, consultarDatosClienteRQ.getPasoFuncional(),
					consultarDatosClienteRQ.getIdSesion());

			RecuperarDatosUbicacionClienteResponse respuesta = (RecuperarDatosUbicacionClienteResponse)
					callRestServiceBack.llamadoBackDatosCliente(consultarDatosClienteRQ,
					propiedades.getPropiedades(), ConstantesLI.SERVICIO_RECUPERAR_UBICACION_CLIENTE);
			respuestaRS.setTokenActual(sesion.getTokenNuevo());

			if (null != respuesta) {
				if (StringUtils.isBlank(respuesta.getCodError())) {
					for (Iterator iterator = respuesta.getInformacionUbicacionCliente().iterator(); iterator.hasNext();) {
						InformacionUbicacionCliente rep = (InformacionUbicacionCliente) iterator.next();
						DireccionDto dir = new DireccionDto();
						dir.setCorreoElectronico(rep.getCorreoElectronico());
						dir.setCelular(rep.getCelular());
						dir.setCiudad(rep.getCodigoCiudad());
						dir.setCorrespondencia(rep.getCodigoCorrespondencia());
						dir.setDepartamento(rep.getCodigoDepartamento());
						dir.setDireccion(rep.getDireccionPrincipal());
						dir.setLlaveDireccion(rep.getCodigoDireccionFuente());
						dir.setPais("CO");
						dir.setTipoDireccion(rep.getCodigoTipoDireccionFuente());
						dir.setLlaveDireccionCif(rep.getLlaveDireccionCif());
						dir.setTelefonoFijo(rep.getTelefonoFijo());
						respuestaRS.getDirecciones().add(dir);
					}
					if (validateResponse(respuestaRS)) {
						throw new ExceptionLI("800", "Cliente sin direcciones");
					}
					param.remove(ConstantesLI.DB_PASO);
					param.put(ConstantesLI.DB_PASO,
							consultarDatosClienteRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_R);
					this.guardarDatosDB(param, gson.toJson(respuestaRS));
				} else {
					if (respuesta.getCodError().equalsIgnoreCase(ConstantesLI.COD_ERROR_006)) {
						mapearMensajeError(respuestaRS, "CLS-" + respuesta.getCodError());
					} else {
						mapearMensajeError(respuestaRS, "CDC-" + respuesta.getCodError());
					}
					param.remove(ConstantesLI.DB_PASO);
					param.put(ConstantesLI.DB_PASO,
							consultarDatosClienteRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
					this.guardarDatosDB(param, gson.toJson(respuestaRS));
				}

			} else {
				throw new ExceptionLI("800", "Cliente sin direcciones");
			}
			mensaje = "Fin consultaDatosUbicacion" + consultarDatosClienteRQ.getIdSesion();
			logger.info(mensaje);

		} catch (ExceptionLI e) {
			mensaje = "Error consultaDatosUbicacion" + consultarDatosClienteRQ.getIdSesion() + e.getMensaje();
			logger.error(mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, consultarDatosClienteRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuestaRS.setCodError(e.getCodigo());
			respuestaRS.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuestaRS,  e.getCodigo());
			}else{
				mapearMensajeError(respuestaRS, "CDC-" + e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(respuestaRS));
		}
		return respuestaRS;
	}


	private boolean validateResponse(ConsultarDatosClienteRS response){
		int cont = 0;
		boolean responseNotFound = false;
		for(int i=0;i<response.getDirecciones().size();i++){
			if(StringUtils.isBlank(response.getDirecciones().get(i).getDireccion())  &&
					StringUtils.isBlank(response.getDirecciones().get(i).getCelular()) && StringUtils.isBlank(response.getDirecciones().get(i).getTelefonoFijo())
					&& StringUtils.isBlank(response.getDirecciones().get(i).getCorreoElectronico())){
					cont++;
			}
		}
		if(cont == response.getDirecciones().size()){
			responseNotFound = true;
		}
		return responseNotFound;
	}
	/**
	 * Se obtiene la informacion que llega del Front para consultar el servicio de
	 * RecuperarDatosCLiente
	 *
	 * @param encrypter String con la información del Front
	 * @return ConsultarDatosClienteRQ request de consultarDatosCliente
	 */
	private ConsultarDatosClienteRQ obtenerDatosCliente(String encrypter) {
		ConsultarDatosClienteRQ consultarDatosClienteRQ;
		String cargaUtil;
		cargaUtil = encrypter;
		consultarDatosClienteRQ = gson.fromJson(cargaUtil, ConsultarDatosClienteRQ.class);
		ThreadContext.put(ConstantesLI.TXT_SESION_ID, consultarDatosClienteRQ.getIdSesion());

		param.put(ConstantesLI.DB_ID_SESION, consultarDatosClienteRQ.getIdSesion());
		param.put(ConstantesLI.DB_PASO, consultarDatosClienteRQ.getPasoFuncional());
		this.guardarDatosDB(param, cargaUtil);

		return consultarDatosClienteRQ;
	}

	public ConsultarDatosClienteRS consultaDatosBasicosCliente(String authorization, String encrypter, String ipCliente)
			throws ExceptionLI {
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		ConsultarDatosClienteRS respuestaRS = new ConsultarDatosClienteRS();
		ConsultarDatosClienteRQ consultarDatosClienteRQ = new ConsultarDatosClienteRQ();
		param = new HashMap<>();
		try {
			consultarDatosClienteRQ = obtenerDatosCliente(encrypter);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, consultarDatosClienteRQ.getIdSesion());

			AutenticacionRS sesion = this.validarSesion(authorization, consultarDatosClienteRQ.getPasoFuncional(),
					consultarDatosClienteRQ.getIdSesion());
			RecuperarDatosBasicosClienteResponse respuesta = (RecuperarDatosBasicosClienteResponse) callRestServiceBack.llamadoBackDatosCliente(consultarDatosClienteRQ,
					propiedades.getPropiedades(), ConstantesLI.SERVICIO_RECUPERAR_DATOS_BASICOS_CLIENTE);
			respuestaRS.setTokenActual(sesion.getTokenNuevo());

			if (null != respuesta) {
				if (StringUtils.isBlank(respuesta.getCodError())) {
					String nombreCompletoCliente = respuesta.getCliente().getPersonaNatural().getNombreLargoCliente();
					respuestaRS.setCliente(respuesta.getCliente());
					respuestaRS.setNombreLargoCliente(nombreCompletoCliente);
					respuestaRS.setNombreCortoCliente(respuesta.getCliente().getPersonaNatural().getNombreCortoCliente());
					respuestaRS.setFechaNacimientoPersona(respuesta.getInformacionNacimientoCliente().getFecha());
					respuestaRS.setLugarExpedicionCedula(
							respuesta.getInformacionExpedicionIdentificacion().getCodigoCiudadExpedicion());
					respuestaRS.setLugarNacimientoPersona(respuesta.getInformacionNacimientoCliente().getCodigoCiudad());
					respuestaRS.setCodigoSegmento(respuesta.getCodigoSegmento());
					respuestaRS.setCodigoSubSegmento(respuesta.getCodigoSubSegmento());
					respuestaRS.setFechaEstadoVinculacion(respuesta.getFechaEstadoVinculacion());
					param.remove(ConstantesLI.DB_PASO);
					param.put(ConstantesLI.DB_PASO,
							consultarDatosClienteRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_R);

					if (propiedades.getValue(ConstantesLI.VALIDAR_CARACTERES_NOMBRE).equalsIgnoreCase(ConstantesLI.TRUE)
							&& !Pattern.matches(ConstantesLI.ALFANUMERICO, nombreCompletoCliente)) {
						mapearMensajeError(respuestaRS, "CLS-002");
						param.put(ConstantesLI.DB_PASO,
								consultarDatosClienteRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
					}
				} else {
					if (respuesta.getCodError().equalsIgnoreCase(ConstantesLI.COD_ERROR_003)) {
						mapearMensajeError(respuestaRS, "CLS-" + respuesta.getCodError());
					} else {
						mapearMensajeError(respuestaRS, "CDC-" + respuesta.getCodError());
					}
					param.remove(ConstantesLI.DB_PASO);
					param.put(ConstantesLI.DB_PASO,
							consultarDatosClienteRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
				}
			} else {
				mapearMensajeError(respuestaRS, "CDC-500");
				param.remove(ConstantesLI.DB_PASO);
				param.put(ConstantesLI.DB_PASO, consultarDatosClienteRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			}
			mensaje = "Fin consultaDatosBasicos" + consultarDatosClienteRQ.getIdSesion();
			logger.info(mensaje);
			this.guardarDatosDB(param, gson.toJson(respuestaRS));
		} catch (ExceptionLI e) {
			mensaje = "Error consultaDatosBasicosCliente: " + e.getMensaje();
			logger.error(mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, consultarDatosClienteRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuestaRS.setCodError(e.getCodigo());
			respuestaRS.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuestaRS,  e.getCodigo());
			}else{
				mapearMensajeError(respuestaRS, "CDC-" + e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(respuestaRS));
		}

		return respuestaRS;
	}

	public ProductoPreaprobadoBasicoRS consultaPrecalculadoCredito(String authorization, String encrypter,
			String ipCliente) throws ExceptionLI, IOException {
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		ProductoPreaprobadoBasicoRS respuestaService = new ProductoPreaprobadoBasicoRS();
		GestionPrecalculadoClienteRQ gestionPrecalculadoClienteRQ = new GestionPrecalculadoClienteRQ();
		param = new HashMap<>();
		String cargaUtil = null;
		try {
			cargaUtil = encrypter;
			gestionPrecalculadoClienteRQ = gson.fromJson(cargaUtil, GestionPrecalculadoClienteRQ.class);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, gestionPrecalculadoClienteRQ.getIdSesion());

			param.put(ConstantesLI.DB_ID_SESION, gestionPrecalculadoClienteRQ.getIdSesion());
			param.put(ConstantesLI.DB_PASO, gestionPrecalculadoClienteRQ.getPasoFuncional());
			this.guardarDatosDB(param, cargaUtil);

			mensaje = "Inicio consultaPrecalculadoCliente" + gestionPrecalculadoClienteRQ.getIdSesion();
			logger.info(mensaje);
			gestionPrecalculadoClienteRQ.setIdentificadorBancoFilial(
					propiedades.getValue("PROXY_BANCO_FILIAL_GESTION_PRECALCULADO_CLIENTE"));
			gestionPrecalculadoClienteRQ.setPropiedades(util.crearListaConfiguracion(propiedades,
					propiedades.getValue(ConstantesLI.API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE)));

			AutenticacionRS sesion = this.validarSesion(authorization, gestionPrecalculadoClienteRQ.getPasoFuncional(),
					gestionPrecalculadoClienteRQ.getIdSesion());

			GestionPrecalculadoClienteRS respuesta = callRestServiceBack.proxyConsultaPrecalculadoCliente(
					gestionPrecalculadoClienteRQ,
					propiedades.getPropiedades());

			param.remove(ConstantesLI.DB_PASO);

			if (StringUtils.isNotBlank(respuesta.getCodError()) || StringUtils.isNotBlank(respuesta.getDescError())) {
				mapearMensajeError(respuestaService, "CPB-" + respuesta.getCodError());
				param.put(ConstantesLI.DB_PASO,
						gestionPrecalculadoClienteRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
				this.guardarDatosDB(param, gson.toJson(respuestaService));
			} else if ("N".equals(respuesta.getRespuestaPrecalculado())) {
				mapearMensajeError(respuestaService, "CLS-001");
				param.put(ConstantesLI.DB_PASO,
						gestionPrecalculadoClienteRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
				this.guardarDatosDB(param, gson.toJson(respuestaService));
			} else {
				if (!respuesta.getProductosPreaprobadosBasico().isEmpty()) {
					String[] lista = propiedades.getValue("API_GESTION_PRECALCULADO_CLIENTE_TIPO_PRODUCTO").split(",");
					for (Iterator<ProductoPreaprobadoBasicoRS> iterator = respuesta.getProductosPreaprobadosBasico()
							.iterator(); iterator.hasNext();) {
						ProductoPreaprobadoBasicoRS prd = iterator.next();
						if (Arrays.asList(lista).contains(prd.getCodigoProducto().trim())) {
							respuestaService = prd;
							respuestaService.setCodigoProducto(respuestaService.getCodigoProducto().trim());
							respuestaService.setCupo(String.valueOf(new BigInteger(respuestaService.getCupo())));
							break;
						} else {
							respuestaService.setCodigoProducto("99");
							if (!"999".equals(prd.getCodigoProducto())) {
								respuestaService.setNombreProducto(prd.getNombreProducto());
							}
						}
					}
				}
				String[] productoTarjeta = propiedades.getValue("VAL_API_COD_PRODUCTO_TDC").split(",");
				if (Arrays.asList(productoTarjeta).contains(respuestaService.getCodigoProducto())
						&& "true".equals(propiedades.getValue("VALIDAR_SUCURSAL_PILOTO"))) {
					this.validarSucursalPiloto(gestionPrecalculadoClienteRQ, respuestaService, sesion.getTokenNuevo());
				} else {
					respuestaService.setTokenActual(sesion.getTokenNuevo());
					mensaje = "Fin respuesta consultaPrecalculadoCredito" + respuestaService
							+ gestionPrecalculadoClienteRQ.getIdSesion();
					logger.info(mensaje);

					param.put(ConstantesLI.DB_PASO, gestionPrecalculadoClienteRQ.getPasoFuncional() + "_R");
					this.guardarDatosDB(param, gson.toJson(respuestaService));
				}
			}
			respuestaService.setTokenActual(sesion.getTokenNuevo());
		} catch (ExceptionLI e) {
			mensaje = "Error consultaPrecalculadoCredito: " + e.getMensaje();
			logger.error(mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, gestionPrecalculadoClienteRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuestaService.setCodError(e.getCodigo());
			respuestaService.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuestaService,  e.getCodigo());
			}else{
				mapearMensajeError(respuestaService, "CPB-" + e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(respuestaService));
		}
		return respuestaService;
	}

	/**
	 * Método que valida si una sucursal está autorizada para realizar el
	 * preaprobado de tarjeta de crédito.
	 *
	 * @param gestionPrecalculadoClienteRQ, request de Precalculado.
	 * @param respuestaService,             response de gestión precalculado.
	 * @param tokenNuevo                    String con el tokenNuevo para enviar al
	 *                                      front.
	 */
	private void validarSucursalPiloto(GestionPrecalculadoClienteRQ gestionPrecalculadoClienteRQ,
			ProductoPreaprobadoBasicoRS respuestaService, String tokenNuevo) {
		String[] listaSucursales = propiedades.getValue("SUCURSALES_HABILITADAS").split(",");
		try {
			if (Arrays.asList(listaSucursales).contains(gestionPrecalculadoClienteRQ.getCodigoSucursal())) {
				respuestaService.setTokenActual(tokenNuevo);
				mensaje = "Sucursal habilitada - consultaPrecalculadoCredito" + gson.toJson(respuestaService)
						+ gestionPrecalculadoClienteRQ.getIdSesion();
				logger.info(mensaje);
				param.put(ConstantesLI.DB_PASO, gestionPrecalculadoClienteRQ.getPasoFuncional() + "_R");
			} else {
				mensaje = "Sucursal no autorizada - consultaPrecalculadoCredito"
						+ gestionPrecalculadoClienteRQ.getIdSesion();
				logger.info(mensaje);
				param.remove(ConstantesLI.DB_PASO);
				param.put(ConstantesLI.DB_PASO, gestionPrecalculadoClienteRQ.getPasoFuncional() + "_E");

				mapearMensajeError(respuestaService, "CLS-004");
			}
			this.guardarDatosDB(param, gson.toJson(respuestaService));
		} catch (ExceptionLI e) {
			mensaje = "Error validando sucursal autorizada - consultaPrecalculadoCredito: " + e.getMensaje();
			logger.error(mensaje, e);
		}
	}

	/**
	 * Método para crear el caso de la solicitud de crédito.
	 *
	 * @param authorization String
	 * @param encrypter     String
	 * @param ipCliente     String
	 * @return GestionCreditoConsumoRS
	 * @throws IOException IOException
	 * @throws ExceptionLI ExceptionLI
	 */
	public GestionCreditoConsumoRS crearCreditoConsumo(String authorization, String encrypter, String ipCliente)
			throws IOException, ExceptionLI {
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new GestionCreditoConsumoRQ();
		param = new HashMap<>();
		try {
			gestionCreditoConsumoRQ = obtenerGestionCreditoConsumoRQ(encrypter);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, gestionCreditoConsumoRQ.getIdSesion());

			respuestaService = callProxyGestionCreditoConsumo(authorization, respuestaService, gestionCreditoConsumoRQ);

			if (StringUtils.isNotEmpty(respuestaService.getIdProceso())) {
				OfertaDigital resp = consultaApiGestionCreditoConsumo(gestionCreditoConsumoRQ.getIdSesion(),
						respuestaService.getNumeroSolicitud(), gestionCreditoConsumoRQ.getPaso(), true);

				if (resp == null) {
					this.mapearMensajeError(respuestaService, ConstantesLI.ERROR_GCC_500);
					param.remove(ConstantesLI.DB_PASO);
					param.put(ConstantesLI.DB_PASO,
							gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
					this.guardarDatosDB(param, gson.toJson(respuestaService));
				} else {
					if (resp.getConfirmacionTransaccion() == null) {
						String[] lista = propiedades.getValue("COD_PRD_SIN_TASAS").split(",");
						// Validar_que_no_sea_Crediagil_TDC_ya_que_no_llegan_las_tasas
						if (!Arrays.asList(lista).contains(gestionCreditoConsumoRQ.getIdProducto())) {
							util.obtenerPlazosCredito(respuestaService, resp);
						}
						respuestaService.setCodSolicitante(resp.getOferta().getInformacionCliente().getIdSolicitante());
						respuestaService.setIdProducto(resp.getOferta().getProducto().get(0).getCategoria().get(0)
								.getSubproducto().get(0).getIdSubproducto());

						util.mapearDatosLibranza(propiedades, resp, respuestaService);

						this.mapearRespuestaOk(gestionCreditoConsumoRQ, respuestaService);
					} else {
						this.mapearMensajeError(respuestaService,
								"GCC-" + resp.getConfirmacionTransaccion().getCodigo());
						param.remove(ConstantesLI.DB_PASO);
						param.put(ConstantesLI.DB_PASO,
								gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
						this.guardarDatosDB(param, gson.toJson(respuestaService));
					}
				}
			} else {
				if (StringUtils.isNotBlank(respuestaService.getCodError())) {
					this.mapearMensajeError(respuestaService, "GCC-" + respuestaService.getCodError());
					param.remove(ConstantesLI.DB_PASO);
					param.put(ConstantesLI.DB_PASO,
							gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
					this.guardarDatosDB(param, gson.toJson(respuestaService));
				}
			}
		} catch (ExceptionLI e) {
			mensaje = "Error crear Credito. " + e.getMensaje();
			logger.error(mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuestaService.setCodError(e.getCodigo());
			respuestaService.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuestaService,  e.getCodigo());
			}else{
				this.mapearMensajeError(respuestaService, "GCC-" + e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(respuestaService));
		}
		return respuestaService;

	}

	/**
	 * Método para confirmar la solicitud del crédito (Avanzar momento1).
	 *
	 * @param authorization String
	 * @param encrypter     String
	 * @param ipCliente     String
	 * @return GestionCreditoConsumoRS
	 * @throws IOException IOException
	 * @throws ExceptionLI ExceptionLI
	 */
	public GestionCreditoConsumoRS confirmarCreditoConsumo(String authorization, String encrypter, String ipCliente)
			throws IOException, ExceptionLI {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new GestionCreditoConsumoRQ();
		param = new HashMap<>();
		try {
			gestionCreditoConsumoRQ = obtenerGestionCreditoConsumoRQ(encrypter);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, gestionCreditoConsumoRQ.getIdSesion());
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);

			respuestaService = callProxyGestionCreditoConsumo(authorization, respuestaService, gestionCreditoConsumoRQ);

			if (StringUtils.isNotEmpty(respuestaService.getIdProceso())) {
				OfertaDigital resp = consultaApiGestionCreditoConsumo(gestionCreditoConsumoRQ.getIdSesion(),
						respuestaService.getNumeroSolicitud(), gestionCreditoConsumoRQ.getPaso(), true);

				if (null == resp) {
					this.mapearMensajeError(respuestaService, ConstantesLI.ERROR_GCC_500);
					param.remove(ConstantesLI.DB_PASO);
					param.put(ConstantesLI.DB_PASO,
							gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
					this.guardarDatosDB(param, gson.toJson(respuestaService));
				} else if (resp.getConfirmacionTransaccion() == null
						|| "".contentEquals(resp.getConfirmacionTransaccion().getCodigo())) {
					if (resp.getDocumentos() != null && !resp.getDocumentos().get(0).getDocumento().isEmpty()) {
						respuestaService.setDocumento(resp.getDocumentos().get(0).getDocumento());
					}
					mapearRespuestaOk(gestionCreditoConsumoRQ, respuestaService);
				} else {
					this.mapearMensajeError(respuestaService, "GCC-" + resp.getConfirmacionTransaccion().getCodigo());
					param.remove(ConstantesLI.DB_PASO);
					param.put(ConstantesLI.DB_PASO,
							gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
					this.guardarDatosDB(param, gson.toJson(respuestaService));
				}
			} else {
				if (StringUtils.isNotBlank(respuestaService.getCodError())) {
					this.mapearMensajeError(respuestaService, "GCC-" + respuestaService.getCodError());
					param.remove(ConstantesLI.DB_PASO);
					param.put(ConstantesLI.DB_PASO,
							gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
					this.guardarDatosDB(param, gson.toJson(respuestaService));
				}
			}
		} catch (ExceptionLI e) {
			mensaje = "Error confirmar Credito. " + e.getMensaje();
			logger.error(mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuestaService.setCodError(e.getCodigo());
			respuestaService.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuestaService,  e.getCodigo());
			}else{
				this.mapearMensajeError(respuestaService, "GCC-" + e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(respuestaService));
		}
		return respuestaService;
	}

	/**
	 * Método para firmar los documentos de la solicitud del crédito (Avanzar
	 * momento2).
	 *
	 * @param authorization String
	 * @param encrypter     String
	 * @param ipCliente     String
	 * @return GestionCreditoConsumoRS
	 * @throws IOException IOException
	 * @throws ExceptionLI ExceptionLI
	 */
	public GestionCreditoConsumoRS firmarDocumentosCreditoConsumo(String authorization, String encrypter,
			String ipCliente) throws IOException, ExceptionLI {
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new GestionCreditoConsumoRQ();
		param = new HashMap<>();
		try {
			gestionCreditoConsumoRQ = obtenerGestionCreditoConsumoRQ(encrypter);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, gestionCreditoConsumoRQ.getIdSesion());

			respuestaService = callProxyGestionCreditoConsumo(authorization, respuestaService, gestionCreditoConsumoRQ);

			if (StringUtils.isNotEmpty(respuestaService.getIdProceso())) {
				OfertaDigital resp = consultaApiGestionCreditoConsumo(gestionCreditoConsumoRQ.getIdSesion(),
						respuestaService.getNumeroSolicitud(), gestionCreditoConsumoRQ.getPaso(), true);

				if (resp == null) {
					mapearMensajeError(respuestaService, ConstantesLI.ERROR_GCC_500);
					param.remove(ConstantesLI.DB_PASO);
					param.put(ConstantesLI.DB_PASO,
							gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
					this.guardarDatosDB(param, gson.toJson(respuestaService));
				} else {
					if ("Ok".equalsIgnoreCase(resp.getConfirmacionTransaccion().getCodigo())) {
						respuestaService.setNumCredito(resp.getConfirmacionTransaccion().getDescripcion());
						mapearRespuestaOk(gestionCreditoConsumoRQ, respuestaService);
					} else {
						mapearMensajeError(respuestaService, "GCC-" + resp.getConfirmacionTransaccion().getCodigo());
						param.remove(ConstantesLI.DB_PASO);
						param.put(ConstantesLI.DB_PASO,
								gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
						this.guardarDatosDB(param, gson.toJson(respuestaService));
					}
				}
			} else {
				if (StringUtils.isNotBlank(respuestaService.getCodError())) {
					mapearMensajeError(respuestaService, "GCC-" + respuestaService.getCodError());
					param.remove(ConstantesLI.DB_PASO);
					param.put(ConstantesLI.DB_PASO,
							gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
					this.guardarDatosDB(param, gson.toJson(respuestaService));
				}
			}
		} catch (ExceptionLI e) {
			mensaje = "Error firmar Documentos Credito. " + e.getMensaje();
			logger.error(mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuestaService.setCodError(e.getCodigo());
			respuestaService.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuestaService,  e.getCodigo());
			}else{
				mapearMensajeError(respuestaService, "GCC-" + e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(respuestaService));
		}
		return respuestaService;
	}

	private void mapearRespuestaOk(GestionCreditoConsumoRQ gestionCreditoConsumoRQ,
			GestionCreditoConsumoRS respuestaService) {
		param.remove(ConstantesLI.DB_PASO);
		param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_R);
		GestionCreditoConsumoRS respuestaServicetmp = new GestionCreditoConsumoRS();
		respuestaServicetmp.setNumCredito(respuestaService.getNumCredito());
		respuestaServicetmp.setIdProceso(respuestaService.getIdProceso());
		respuestaServicetmp.setIdProducto(respuestaService.getIdProducto());
		respuestaServicetmp.setNumeroSolicitud(respuestaService.getNumeroSolicitud());
		respuestaServicetmp.setPlazos(respuestaService.getPlazos());
		respuestaServicetmp.setProducto(respuestaService.getProducto());
		respuestaServicetmp.setTokenActual(respuestaService.getTokenActual());
		respuestaServicetmp.setCodSolicitante(respuestaService.getCodSolicitante());
		respuestaServicetmp.setDocumento(null);
		respuestaServicetmp.setNombreConvenio(respuestaService.getNombreConvenio());
		this.guardarDatosDB(param, gson.toJson(respuestaServicetmp));

	}

	private GestionCreditoConsumoRQ obtenerGestionCreditoConsumoRQ(String encrypter) {
		String cargaUtil = null;
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = null;
		cargaUtil = encrypter;
		gestionCreditoConsumoRQ = gson.fromJson(cargaUtil, GestionCreditoConsumoRQ.class);
		util.validarCaracteresGestionConsumoRQ(gestionCreditoConsumoRQ);

		param.put(ConstantesLI.DB_ID_SESION, gestionCreditoConsumoRQ.getIdSesion());
		param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional());
		this.guardarDatosDB(param, cargaUtil);
		return gestionCreditoConsumoRQ;
	}

	private GestionCreditoConsumoRS callProxyGestionCreditoConsumo(String authorization,
			GestionCreditoConsumoRS respuestaService, GestionCreditoConsumoRQ gestionCreditoConsumoRQ)
			throws ExceptionLI, IOException {
		AutenticacionRS sesion = this.validarSesion(authorization, gestionCreditoConsumoRQ.getPasoFuncional(),
				gestionCreditoConsumoRQ.getIdSesion());

		if (gestionCreditoConsumoRQ.getPaso().equalsIgnoreCase(ConstantesLI.PASO1)) {
			gestionCreditoConsumoRQ.setTipoVenta("P");
			if (gestionCreditoConsumoRQ.getTasaFija() == null) {
				gestionCreditoConsumoRQ.setTasaFija(false);
			}
			if (gestionCreditoConsumoRQ.getTasavariable() == null) {
				gestionCreditoConsumoRQ.setTasavariable(false);
			}
			if (!gestionCreditoConsumoRQ.getTasaFija() && !gestionCreditoConsumoRQ.getTasavariable()) {
				gestionCreditoConsumoRQ.setTasaFija(true);
			}

			respuestaService = callRestServiceBack.proxyGestionCreditoConsumo(gestionCreditoConsumoRQ,
					propiedades.getPropiedades(), ConstantesLI.SERVICIO_OFERTA_DIGITAL_CREAR_SOL);
		} else if (gestionCreditoConsumoRQ.getPaso().equalsIgnoreCase(ConstantesLI.PASO2)) {
			if (StringUtils
					.isNotBlank(gestionCreditoConsumoRQ.getProducto().get(ConstantesLI.CERO).getCuentaDesembolso())) {
				String tipoCuenta = gestionCreditoConsumoRQ.getProducto().get(ConstantesLI.CERO).getCuentaDesembolso()
						.split("-")[ConstantesLI.CERO].trim();
				String cuenta = gestionCreditoConsumoRQ.getProducto().get(ConstantesLI.CERO).getCuentaDesembolso()
						.split("-")[ConstantesLI.UNO].trim();
				gestionCreditoConsumoRQ.getProducto().get(ConstantesLI.CERO).setTipoCuentaDesembolso(tipoCuenta);
				gestionCreditoConsumoRQ.getProducto().get(ConstantesLI.CERO).setCuentaDesembolso(cuenta);
				gestionCreditoConsumoRQ.getProducto().get(ConstantesLI.CERO).setTipoCuentaDebitoAutomatico(tipoCuenta);
				gestionCreditoConsumoRQ.getProducto().get(ConstantesLI.CERO).setCuentaDebitoAutomatico(cuenta);

			}

			gestionCreditoConsumoRQ
					.setNombreTarea(propiedades.getValue("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_TAREA_M1"));
			respuestaService = callRestServiceBack.proxyGestionCreditoConsumo(gestionCreditoConsumoRQ,
					propiedades.getPropiedades(), ConstantesLI.SERVICIO_OFERTA_DIGITAL_AVANZAR_SOL);
		} else if (gestionCreditoConsumoRQ.getPaso().equalsIgnoreCase(ConstantesLI.PASO3)) {
			gestionCreditoConsumoRQ
					.setNombreTarea(propiedades.getValue("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_TAREA_M2"));
			gestionCreditoConsumoRQ.setNombreArchivoTraza(
					propiedades.getValue("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_ARCHIVO_TRAZA"));

			respuestaService = callRestServiceBack.proxyGestionCreditoConsumo(gestionCreditoConsumoRQ,
					propiedades.getPropiedades(), ConstantesLI.SERVICIO_OFERTA_DIGITAL_AVANZAR_SOL);
		}

		respuestaService.setTokenActual(sesion.getTokenNuevo());
		param.remove(ConstantesLI.DB_PASO);
		param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + "_Bizagi");
		this.guardarDatosDB(param, gson.toJson(respuestaService));

		return respuestaService;
	}

	/**
	 * Método para el servicio de recalculo de cuota en la pantalla 1 del credito
	 *
	 * @param encrypter
	 * @return
	 * @throws ExceptionLI
	 */
	public GestionCreditoConsumoRS recalculoCuota(String encrypter, String ipCliente) throws ExceptionLI {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new GestionCreditoConsumoRQ();
		param = new HashMap<>();
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		try {
			gestionCreditoConsumoRQ = obtenerGestionCreditoConsumoRQ(encrypter);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, gestionCreditoConsumoRQ.getIdSesion());

			OfertaDigital ofertaDigital = consultaApiGestionCreditoConsumo(gestionCreditoConsumoRQ.getIdSesion(),
					gestionCreditoConsumoRQ.getNumeroSolicitud(), gestionCreditoConsumoRQ.getPaso(), false);
			respuestaService.setNumeroSolicitud(gestionCreditoConsumoRQ.getNumeroSolicitud());
			param.remove(ConstantesLI.DB_PASO);
			if (ofertaDigital != null) {
				respuestaService.setProductos(util.obtenerCalculoCuota(ofertaDigital,
						gestionCreditoConsumoRQ.getProducto().get(ConstantesLI.CERO).getCupoElegido().toString(),
						gestionCreditoConsumoRQ.getProducto().get(ConstantesLI.CERO).getPlazo().toString(), propiedades));
				param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_R);

			} else {
				mapearMensajeError(respuestaService, "GCC-100");
				param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			}
			this.guardarDatosDB(param, gson.toJson(respuestaService));
		} catch (ExceptionLI e) {
			mensaje = "Error solicitudCreditoConsumo." + e.getMensaje();
			logger.error(mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuestaService.setCodError(e.getCodigo());
			respuestaService.setDescError(e.getMensaje());
			mapearMensajeError(respuestaService, "GCC-" + e.getCodigo());
			this.guardarDatosDB(param, gson.toJson(respuestaService));
		} catch (Exception e) {
			// se usa excepcion generica por si se lanza alguna excepcion no controlada
			// por el flujo
			mensaje = "Error SolicitudCreditoConsumo" + gestionCreditoConsumoRQ.getIdSesion();
			logger.error(mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuestaService.setDescError(e.getMessage());
			mapearMensajeError(respuestaService, ConstantesLI.ERROR_GCC_500);
			this.guardarDatosDB(param, gson.toJson(respuestaService));
		}
		return respuestaService;
	}

	/**
	 * Metodo para el servicio de recalcular Seguro en la pantalla 1 del credito
	 *
	 * @param encrypter
	 * @return
	 * @throws ExceptionLI
	 */
	public GestionCreditoConsumoRS recalcularSeguro(String encrypter, String ipCliente) throws ExceptionLI {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new GestionCreditoConsumoRQ();
		param = new HashMap<>();
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		try {
			gestionCreditoConsumoRQ = obtenerGestionCreditoConsumoRQ(encrypter);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, gestionCreditoConsumoRQ.getIdSesion());

			OfertaDigital ofertaDigital = consultaApiGestionCreditoConsumo(gestionCreditoConsumoRQ.getIdSesion(),
					gestionCreditoConsumoRQ.getNumeroSolicitud(), gestionCreditoConsumoRQ.getPaso(), false);
			respuestaService.setNumeroSolicitud(gestionCreditoConsumoRQ.getNumeroSolicitud());
			param.remove(ConstantesLI.DB_PASO);
			if (ofertaDigital != null) {
				respuestaService.setProducto(util.obtenerCalculoSeguro(ofertaDigital,
						gestionCreditoConsumoRQ.getProducto().get(ConstantesLI.CERO).getCupoElegido().toString()));
				param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + "_R");
			} else {
				mapearMensajeError(respuestaService, "GCC-100");
				param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			}
			this.guardarDatosDB(param, gson.toJson(respuestaService));
		} catch (ExceptionLI e) {
			mensaje = "Error solicitudCreditoConsumo." + e.getMensaje();
			logger.error(mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuestaService.setCodError(e.getCodigo());
			respuestaService.setDescError(e.getMensaje());
			mapearMensajeError(respuestaService, "GCC-" + e.getCodigo());
			this.guardarDatosDB(param, gson.toJson(respuestaService));
		} catch (Exception e) {
			// llamados entre api que pueden generar errores diferentes a los conocidos,
			// por eso se procesa de esta forma y se usa la excepcion generica
			mensaje = "Error SolicitudCreditoConsumo" + e.getMessage();
			logger.error(mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuestaService.setDescError(e.getMessage());
			mapearMensajeError(respuestaService, ConstantesLI.ERROR_GCC_500);
			this.guardarDatosDB(param, gson.toJson(respuestaService));
		}
		return respuestaService;
	}

	/**
	 * Metodo encargado de realizar los consumos al API de gestion Credito de
	 * Consumo partiendo de la configuracion que se tenga para el mismo
	 *
	 * @param numeroSolicitud numero de solicitud que retornado del consumo de crear
	 *                        o avanzar caso de bizagi
	 * @param paso            paso que queremos buscar, paso1: Creacion de caso,
	 *                        paso2: documentos, paso3: confirmacion
	 * @return Objeto OfertaDigital recibido de bizagi
	 * @throws Exception
	 */
	private OfertaDigital consultaApiGestionCreditoConsumo(String idSesion, String numeroSolicitud, String paso,
			boolean esperaInicial) {
		OfertaDigital resp = null;
		int multiplo = ConstantesLI.MIL;
		int rangoInicial = Integer.parseInt(propiedades.getValue("VAL_TIEMPO_ESPERA_INICIAL_GESTION_CRED")) * multiplo;// 5
		int rangoReintento = Integer.parseInt(propiedades.getValue("VAL_TIEMPO_REINTENTO_GESTION_CRED")) * multiplo;// 5
		// 60 5 segundos por default
		int tiempoEspera = ConstantesLI.CINCO * multiplo;

		if (ConstantesLI.PASO1.equalsIgnoreCase(paso)) {
			tiempoEspera = Integer.parseInt(propiedades.getValue("VAL_TIEMPO_TOTAL_GESTION_CRED")) * multiplo;
		} else if (ConstantesLI.PASO2.equalsIgnoreCase(paso)) {
			tiempoEspera = Integer.parseInt(propiedades.getValue("VAL_TIEMPO_TOTAL_GESTION_CRED_MOM1")) * multiplo;
		} else if (ConstantesLI.PASO3.equalsIgnoreCase(paso)) {
			tiempoEspera = Integer.parseInt(propiedades.getValue("VAL_TIEMPO_TOTAL_GESTION_CRED_MOM2")) * multiplo;
		}

		int temporal = rangoInicial;
		mensaje = paso + " Consulta consultaApiGestionCreditoConsumo: " + new Date().toString() + idSesion;
		logger.info(mensaje);

		try {
			if (esperaInicial) {
				Thread.sleep(rangoInicial);
			}

			while (temporal <= tiempoEspera) {
				mensaje = paso + " Consulta consultaApiGestionCreditoConsumo: " + new Date().toString() + idSesion;
				logger.info(mensaje);
				resp = callRestServiceBack.apiGestionCreditos(numeroSolicitud, paso);
				mensaje = paso + " Respuesta consultaApiGestionCreditoConsumo: " + idSesion;
				logger.info(mensaje);

				if (resp != null) {
					break;
				} else {
					temporal += rangoReintento;
					Thread.sleep(rangoReintento);
				}
			}
		} catch (InterruptedException e) {
			logger.error("Error InterruptedException en  consultaApiGestionCreditoConsumo: ", e);
			Thread.currentThread().interrupt();
		} catch (Exception e) {
			// llamados entre api que pueden generar errores diferentes a los conocidos,
			// por eso se procesa de esta forma y se usa la excepcion generica
			logger.error("Error consumo API Gestion Credito Consumo.", e);
		}
		return resp;
	}

	/**
	 * Metodo para guardar datos en DB segun lo requerido desde el front, aplica
	 * solo para algunos pasos funcionales
	 *
	 * @param encrypter Carga util que se va a guardar
	 * @param ipCliente Ip desde donde se realiza el consumo
	 * @return Confirmacion de ejecucion del servicio, siempre Finalizado OK
	 * @throws ExceptionLI control de alguna excepcion
	 */
	public String finalizarExp(String encrypter, String ipCliente) throws ExceptionLI {
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		String cargaUtil = null;
		libreInversion = new LibreInversion();
		param = new HashMap<>();
		try {
			cargaUtil = encrypter;
			libreInversion = gson.fromJson(cargaUtil, LibreInversion.class);
			util.validarCaracteresEspeciales(libreInversion.getInformacionDispositivo());
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, libreInversion.getInformacionTransaccion().getIdSesion());

			param.put(ConstantesLI.DB_ID_SESION, libreInversion.getInformacionTransaccion().getIdSesion());
			param.put(ConstantesLI.DB_PASO, libreInversion.getInformacionTransaccion().getPasoFuncional());
			if (libreInversion.getInformacionCredito() != null) {
				libreInversion.getInformacionCredito().setDocumento(null);
			}
				this.guardarDatosDB(param, gson.toJson(libreInversion));

		} catch (JsonSyntaxException e) {
			logger.error("Error finalizarExp JsonSyntaxException", e);
			throw new ExceptionLI("500", e.getMessage(), e);
		} catch (Exception e) {
			// llamados entre api que pueden generar errores diferentes a los conocidos,
			// por eso se procesa de esta forma y se usa la excepcion generica
			logger.error("Error finalizarExp Exception ", e);
			throw new ExceptionLI("500", e.getMessage(), e);
		}

		return "Finalizado OK";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void guardarDatosDB(Map conf, String cargaUtil) {
		try {
			conf.put(ConstantesLI.DB_ESQUEMA, ConstantesLI.ESQUEMA_DB_NAME);
			conf.put(ConstantesLI.DB_TABLA, "JSONLI");
			ThreadContext.put("sessionId", conf.get(ConstantesLI.DB_ID_SESION).toString());

			String resp = callRestServiceBack.guardarDatosDB(conf, cargaUtil);

			mensaje = "Respuesta Guardar " + resp + " : Paso: " + conf.get(ConstantesLI.DB_PASO).toString()
					+ " idSession: " + conf.get(ConstantesLI.DB_ID_SESION).toString();
			logger.info(mensaje);
		} catch (Exception e) {
			// se utiliza excepcion generica ya que se realiza consumo a una base de datos y
			// se pueden presentar multiples problemas que no estan en las excepciones
			// individuales
			logger.error("Error Llamado a base de datos Generico", e);
		}
	}

	/**
	 * Metodo encargado de preparar los datos necesarios para enviar al correo
	 * electronico del cliente la informacion de su credito.
	 *
	 * @param encrypter String con la informacion para enviar el correo.
	 * @return int con el codigo de la respuesta del servicio de envio de correos
	 * @throws ExceptionLI ExceptionLI
	 */
	public String[] enviarCorreo(String authorization, String encrypter, String ipCliente) throws ExceptionLI {
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		Map<String, Object> datosCorreo = new java.util.HashMap<>();
		Response res = null;
		String cargaUtil = null;
		EnvioCorreoDto datosEnvioCorreo = new EnvioCorreoDto();
		param = new HashMap<>();
		String[] respuesta = new String[ConstantesLI.DOS];
		AutenticacionRS sesion = new AutenticacionRS();
		try {
			cargaUtil = encrypter;
			datosEnvioCorreo = gson.fromJson(cargaUtil, EnvioCorreoDto.class);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, datosEnvioCorreo.getIdSesion());
			param.put(ConstantesLI.DB_ID_SESION, datosEnvioCorreo.getIdSesion());
			param.put(ConstantesLI.DB_PASO, datosEnvioCorreo.getPasoFuncional());

			this.guardarDatosCorreoBD(datosEnvioCorreo);
			if(datosEnvioCorreo.getNumeroIntentosEnvio() == ConstantesLI.UNO){
				sesion = this.validarSesion(authorization, datosEnvioCorreo.getPasoFuncional(),
						datosEnvioCorreo.getIdSesion());
				respuesta[ConstantesLI.UNO] = sesion.getTokenNuevo();
			}else{
				respuesta[ConstantesLI.UNO] = authorization.split(" ")[ConstantesLI.UNO];
			}


			this.prepararDatosEnvioCorreo(datosCorreo, datosEnvioCorreo);
			res = callRestServiceBack.enviarCorreo(datosCorreo,
					propiedades.getValue(ConstantesLI.API_ENDPOINT_ENVIO_CORREO));

			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, datosEnvioCorreo.getPasoFuncional() + ConstantesLI.RESPUESTA_R);
			respuesta[0] = String.valueOf(res.getStatus());
			this.guardarDatosDB(param, gson.toJson(res.getEntity()));
		} catch (ExceptionLI e) {
			mensaje = "Error enviando correo ExceptionLI" + datosEnvioCorreo.getIdSesion();
			logger.error(mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO,
					libreInversion.getInformacionTransaccion().getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			er.setCodError(e.getCodigo());
			er.setDescError(e.getMensaje());
			er.setCodFuncional(e.getCodigoFuncional());
			er.setDescFuncional(e.getMensajeFuncional());
			respuesta[0] = e.getCodigo();
			respuesta[1] = sesion.getTokenNuevo();
			this.guardarDatosDB(param, gson.toJson(er));
		} catch (Exception e) {
			// llamados entre api que pueden generar errores diferentes a los conocidos,
			// por eso se procesa de esta forma y se usa la excepcion generica
			mensaje = "Error enviando correo Exception" + datosEnvioCorreo.getIdSesion();
			logger.error(mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO,
					libreInversion.getInformacionTransaccion().getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			er.setCodError("500");
			er.setDescError(e.getMessage());
			respuesta[0] = er.getCodError();
			respuesta[1] = sesion.getTokenNuevo();
			this.guardarDatosDB(param, gson.toJson(er));
		}
		return respuesta;
	}

	/**
	 * Método que guarda en base datos la información para el envío y descarga del
	 * correo de bienvenida
	 *
	 * @param datosEnvioCorreo, EnvioCorreoDto con la información del crédito del
	 *                          cliente
	 */
	private void guardarDatosCorreoBD(EnvioCorreoDto datosEnvioCorreo) {
		EnvioCorreoDto envioCorreoDto = new EnvioCorreoDto();
		envioCorreoDto.setIdSesion(datosEnvioCorreo.getIdSesion());
		envioCorreoDto.setNombreCliente(datosEnvioCorreo.getNombreCliente());
		envioCorreoDto.setDocumentoCliente(datosEnvioCorreo.getDocumentoCliente());
		envioCorreoDto.setCorreo(datosEnvioCorreo.getCorreo());
		envioCorreoDto.setDocumentoPdf(null);
		envioCorreoDto.setCupoSolicitado(datosEnvioCorreo.getCupoSolicitado());
		envioCorreoDto.setNombreProducto(datosEnvioCorreo.getNombreProducto());
		envioCorreoDto.setPasoFuncional(datosEnvioCorreo.getPasoFuncional());
		envioCorreoDto.setTasaNominal(datosEnvioCorreo.getTasaNominal());
		envioCorreoDto.setPlazoCredito(datosEnvioCorreo.getPlazoCredito());
		envioCorreoDto.setValorCuota(datosEnvioCorreo.getValorCuota());
		envioCorreoDto.setCuentaDebito(datosEnvioCorreo.getCuentaDebito());
		envioCorreoDto.setNumeroCredito(datosEnvioCorreo.getNumeroCredito());
		envioCorreoDto.setCodigoProducto(datosEnvioCorreo.getCodigoProducto());
		envioCorreoDto.setPuntosDTF(datosEnvioCorreo.getPuntosDTF());
		envioCorreoDto.setCreaTarjeta(datosEnvioCorreo.getCreaTarjeta());
		envioCorreoDto.setSeguroEmpleado(datosEnvioCorreo.getSeguroEmpleado());
		envioCorreoDto.setCiudadCliente(datosEnvioCorreo.getCiudadCliente());
		envioCorreoDto.setProductoTarjeta(datosEnvioCorreo.getProductoTarjeta());
		envioCorreoDto.setNumeroDescarga(datosEnvioCorreo.getNumeroDescarga());

		this.guardarDatosDB(param, gson.toJson(envioCorreoDto));
	}

	/**
	 * Método que construye los datos necesarios para la plantilla de bienvenida.
	 *
	 * @param datosCorreo      Map<String, Object>
	 * @param datosEnvioCorreo EnvioCorreoDto
	 */
	private void prepararDatosEnvioCorreo(Map<String, Object> datosCorreo, EnvioCorreoDto datosEnvioCorreo) {
		DecimalFormat formatNum = new DecimalFormat("#,###");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(ConstantesLI.DOS);
		Date fechaHoraTransaccion = new Date();
		String siguienteDiaPago = util.siguienteDiaPago(fechaHoraTransaccion);
		int diaPagoSiguiente = util.diaDeCadaMes(fechaHoraTransaccion);
		String[] productoCrediAgil = propiedades.getValue("VAL_API_COD_PRODUCTO_CREDIAGIL").split(",");
		String[] productoLibranza = propiedades.getValue("VAL_API_COD_PRODUCTO_LIBRANZA").split(",");
		String[] productoLibranzaColpensiones = propiedades.getValue("VAL_API_COD_PRODUCTO_LIBRANZA_COLPENSIONES").split(",");
		String documentoPdf = "";

		datosCorreo.put(ConstantesGeneracionPDF.EMAIL, datosEnvioCorreo.getCorreo());
		datosCorreo.put(ConstantesGeneracionPDF.NOMBRE_CLIENTE, datosEnvioCorreo.getNombreCliente());
		datosCorreo.put(ConstantesGeneracionPDF.NO_DOCUMENTO, datosEnvioCorreo.getDocumentoCliente());


		if (Arrays.asList(productoCrediAgil).contains(datosEnvioCorreo.getCodigoProducto())) {
			datosCorreo.put(ConstantesGeneracionPDF.RUTA_PLANTILLA_CORREO,
					ConstantesGeneracionPDF.RUTA_PLANTILLA_CREDIAGIL_SUCURSALES);
			datosCorreo.put(ConstantesLI.RUTA_IMG,
					propiedades.getValue(ConstantesGeneracionPDF.RUTA_PLANTILLA_CREDIAGIL_SUCURSALES));
			datosCorreo.put(ConstantesGeneracionPDF.ASUNTO_CORREO, ConstantesGeneracionPDF.ASUNTO_CREDIAGIL);
			datosCorreo.put(ConstantesGeneracionPDF.VALOR_CUOTA,
					formatNum.format(Double.parseDouble(datosEnvioCorreo.getValorCuota().replaceAll(",", "."))));
			datosCorreo.put(ConstantesGeneracionPDF.VALOR_DESEMBOLSO,
					formatNum.format(Integer.parseInt(datosEnvioCorreo.getCupoSolicitado())));
			datosCorreo.put(ConstantesGeneracionPDF.IMAGENES, ConstantesLI.IMAGENES_CORREO_CREDIAGIL);
		} else {
			// si el producto es diferente a crediagil se mapean estos
			// campos
			double tasaNominal = Double.parseDouble(datosEnvioCorreo.getTasaNominal());
			double tasaEA = Double.parseDouble(datosEnvioCorreo.getTasaEA());
			double tasaMV = Double.parseDouble(datosEnvioCorreo.getTasaMV());

			datosCorreo.put(ConstantesGeneracionPDF.FECHA_DESEMBOLSO, sdf.format(fechaHoraTransaccion));
			datosCorreo.put(ConstantesGeneracionPDF.VALOR_DESEMBOLSO,
					formatNum.format(Integer.parseInt(datosEnvioCorreo.getCupoSolicitado())));
			datosCorreo.put(ConstantesGeneracionPDF.FECHA_PRIMER_PAGO, siguienteDiaPago);
			datosCorreo.put(ConstantesGeneracionPDF.DIA_PAGO_SIGUIENTE, diaPagoSiguiente);
			datosCorreo.put(ConstantesGeneracionPDF.PLAZO_CREDITO, datosEnvioCorreo.getPlazoCredito());
			datosCorreo.put(ConstantesGeneracionPDF.VALOR_CUOTA,
					formatNum.format(Double.parseDouble(datosEnvioCorreo.getValorCuota().replaceAll(",", "."))));
			datosCorreo.put(ConstantesGeneracionPDF.PERIODICIDAD_PAGO, ConstantesGeneracionPDF.MENSUAL);
			if (null != datosEnvioCorreo.getCuentaDebito() && !"".equals(datosEnvioCorreo.getCuentaDebito())) {
				datosCorreo.put(ConstantesGeneracionPDF.CUENTA_DEBITO,
						datosEnvioCorreo.getCuentaDebito().split("-")[1].trim());
			}
			datosCorreo.put(ConstantesGeneracionPDF.CREDITO_NUMERO, datosEnvioCorreo.getNumeroCredito());
			datosCorreo.put(ConstantesGeneracionPDF.TASA_EA, tasaEA);
			datosCorreo.put(ConstantesGeneracionPDF.TASA_MV, tasaMV);
			datosCorreo.put(ConstantesGeneracionPDF.CUENTA_DESEMBOLSO, datosEnvioCorreo.getCuentaDesembolso());
			if (Arrays.asList(productoLibranza).contains(datosEnvioCorreo.getCodigoProducto()) ||
					Arrays.asList(productoLibranzaColpensiones).contains(datosEnvioCorreo.getCodigoProducto())) {
				datosCorreo.put(ConstantesGeneracionPDF.TIPO_TASA, ConstantesGeneracionPDF.TIPO_TASA_LIBRANZA);
				datosCorreo.put(ConstantesGeneracionPDF.TASA_INTERES_LIBRANZA, numberFormat.format(tasaNominal));
				datosCorreo.put(ConstantesGeneracionPDF.PUNTOS_DTF,
						numberFormat.format(Double.parseDouble(datosEnvioCorreo.getPuntosDTF())));
				datosCorreo.put(ConstantesGeneracionPDF.NOMBRE_CLIENTE, datosEnvioCorreo.getNombreCliente());
				if (Arrays.asList(productoLibranza).contains(datosEnvioCorreo.getCodigoProducto())) {
					datosCorreo.put(ConstantesGeneracionPDF.RUTA_PLANTILLA_CORREO,
							ConstantesGeneracionPDF.RUTA_PLANTILLA_LIBRANZA_SUCURSALES);
					datosCorreo.put(ConstantesLI.RUTA_IMG,
							propiedades.getValue(ConstantesGeneracionPDF.RUTA_PLANTILLA_LIBRANZA_SUCURSALES));
					datosCorreo.put(ConstantesGeneracionPDF.ASUNTO_CORREO, ConstantesGeneracionPDF.ASUNTO_LIBRANZA);
					datosCorreo.put(ConstantesGeneracionPDF.IMAGENES, ConstantesLI.IMAGENES_CORREO_LIBRANZA);
					datosCorreo.put(ConstantesGeneracionPDF.NOMBRE_EMPRESA, datosEnvioCorreo.getNombreEmpresa());
				} else if (Arrays.asList(productoLibranzaColpensiones).contains(datosEnvioCorreo.getCodigoProducto())) {
					datosCorreo.remove(ConstantesGeneracionPDF.DIA_PAGO_SIGUIENTE);
					datosCorreo.put(ConstantesGeneracionPDF.DIA_PAGO_SIGUIENTE, ConstantesLI.CINCO);

					datosCorreo.put(ConstantesGeneracionPDF.FECHA_PAGO_SIGUIENTE,
							util.fechaSiguientePagoColpensiones(fechaHoraTransaccion));

					sdf = new SimpleDateFormat("dd/MM/yyyy");
					datosCorreo.remove(ConstantesGeneracionPDF.FECHA_DESEMBOLSO);
					datosCorreo.put(ConstantesGeneracionPDF.FECHA_DESEMBOLSO, sdf.format(fechaHoraTransaccion));

					datosCorreo.put(ConstantesGeneracionPDF.RUTA_PLANTILLA_CORREO,
							ConstantesGeneracionPDF.RUTA_PLANTILLA_LIBRANZA_COLPENSIONES_SUCURSALES);
					datosCorreo.put(ConstantesLI.RUTA_IMG,
							propiedades.getValue(ConstantesGeneracionPDF.RUTA_PLANTILLA_LIBRANZA_COLPENSIONES_SUCURSALES));
					datosCorreo.put(ConstantesGeneracionPDF.ASUNTO_CORREO, ConstantesGeneracionPDF.ASUNTO_LIBRANZA_COLPENSIONES);
					datosCorreo.put(ConstantesGeneracionPDF.IMAGENES, ConstantesGeneracionPDF.IMAGENES_CORREO_LIBRANZA_COLPENSIONES);
				}

			} else {
				datosCorreo.put(ConstantesGeneracionPDF.NOMBRE_PRODUCTO, ConstantesLI.LIBRE_INVERSION);
				datosCorreo.put(ConstantesGeneracionPDF.TASA_FIJA_NOMINAL, numberFormat.format(tasaNominal));
				datosCorreo.put(ConstantesGeneracionPDF.RUTA_PLANTILLA_CORREO,
						ConstantesGeneracionPDF.RUTA_PLANTILLA_LIBREINV_SUCURSALES);
				datosCorreo.put(ConstantesLI.RUTA_IMG,
						propiedades.getValue(ConstantesGeneracionPDF.RUTA_PLANTILLA_LIBREINV_SUCURSALES));
				datosCorreo.put(ConstantesGeneracionPDF.IMAGENES, ConstantesLI.IMAGENES_CORREO_LIBRE_INVERSION);
				String[] microCredito = propiedades.getValue("VAL_API_COD_PRODUCTO_MICROCREDITO").split(",");
				if (Arrays.asList(microCredito).contains(datosEnvioCorreo.getCodigoProducto())) {
					datosCorreo.put(ConstantesGeneracionPDF.NOMBRE_PRODUCTO, ConstantesLI.MICROCREDITO);
					datosCorreo.put(ConstantesGeneracionPDF.ASUNTO_CORREO, ConstantesGeneracionPDF.ASUNTO_MICROCREDITO);
				} else {
					datosCorreo.put(ConstantesGeneracionPDF.ASUNTO_CORREO,
							ConstantesGeneracionPDF.ASUNTO_LIBREINVERSION);
				}
			}
		}

		if (datosEnvioCorreo.getSeguroEmpleado()) {
			documentoPdf = this.obtenerArchivosSeguro(datosEnvioCorreo, datosCorreo);
		} else {
			documentoPdf = datosEnvioCorreo.getDocumentoPdf();
		}
		datosCorreo.put(ConstantesGeneracionPDF.APLICA_EP, datosEnvioCorreo.getSeguroEmpleado());
		datosCorreo.put(ConstantesGeneracionPDF.DOCUMENTO_PDF, documentoPdf);
	}

	/**
	 * Metodo encargado de realizar la validacion de las preguntas de seguridad
	 *
	 * @param authorization JWT con la autorizacion para ejecutar el flujo
	 * @param encrypter     carga util
	 * @param ipCliente     Ip desde donde se realiza la peticion
	 * @return Objeto con la respuesta del flujo
	 * @throws ExceptionLI captura de error personalizada
	 */
	public ValidacionPreguntasRS validacionPreguntas(String authorization, String encrypter, String ipCliente)
			throws ExceptionLI {
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		ValidacionPreguntasRS respuesta = new ValidacionPreguntasRS();
		ValidacionPreguntasRQ validacionPreguntasRQ = new ValidacionPreguntasRQ();
		AutenticacionRS sesion = new AutenticacionRS();
		param = new HashMap<>();

		try {
			validacionPreguntasRQ = gson.fromJson(encrypter, ValidacionPreguntasRQ.class);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, validacionPreguntasRQ.getIdSesion());

			param.put(ConstantesLI.DB_ID_SESION, validacionPreguntasRQ.getIdSesion());
			param.put(ConstantesLI.DB_PASO, validacionPreguntasRQ.getPasoFuncional());
			this.guardarDatosDB(param, encrypter);

			sesion = this.validarSesion(authorization, validacionPreguntasRQ.getPasoFuncional(),
					validacionPreguntasRQ.getIdSesion());

			respuesta.setTokenActual(sesion.getTokenNuevo());

			boolean validacion = util.validarPreguntas(validacionPreguntasRQ);

			if (!validacion) {
				mapearMensajeError(respuesta, "CLS-005");
				param.remove(ConstantesLI.DB_PASO);
				param.put(ConstantesLI.DB_PASO, validacionPreguntasRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			} else {
				param.remove(ConstantesLI.DB_PASO);
				param.put(ConstantesLI.DB_PASO, validacionPreguntasRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_R);
			}
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (ExceptionLI e) {
			logger.error("Error crearActualizarDireccionGestionProspecto", e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO,
					libreInversion.getInformacionTransaccion().getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError(e.getCodigo());
			respuesta.setDescError(e.getMensaje());
			respuesta.setTokenActual(sesion.getTokenNuevo());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuesta, e.getCodigo());
			}else{
				mapearMensajeError(respuesta, "CGP-" + e.getCodigo());
			}

			this.guardarDatosDB(param, gson.toJson(respuesta));
		}

		mensaje = "Fin valiacion preguntas" + validacionPreguntasRQ.getIdSesion();
		logger.info(mensaje);
		return respuesta;
	}

	/**
	 * Método que consulta el mensaje de error, ingresado por parámetro, en el api
	 * de errores.
	 *
	 * @param codError String
	 * @return ErrorResponseDTO
	 * @throws ExceptionLI ExceptionLI
	 */
	public ErrorResponseDTO consultarMensajeError(String codError) throws ExceptionLI {
		ErrorResponseDTO respuesta = new ErrorResponseDTO();

		if (propiedades.getValue("VAL_API_ERRORES_ACTIVO").equalsIgnoreCase(ConstantesLI.TRUE)) {
			ErrorRequestDTO errorRequestDTO = new ErrorRequestDTO();
			errorRequestDTO.setIdAplicacion(propiedades.getValue("VAL_API_ERRORES_COD_APLICACION"));
			errorRequestDTO.setCodigoInterno(codError);
			respuesta = callRestServiceBack.errorService(errorRequestDTO,
					propiedades.getValue(ConstantesLI.API_ENDPOINT_ERROR_BYCODE),
					propiedades.getValue(ConstantesLI.MSG_DEFAULT_API));
		} else {
			respuesta.setCodigoError(codError);
			respuesta.setDescripcionTecnica("No se tiene activo el consumo al API Errores");
			MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
			mensajeFuncional.setCodigoFuncional(codError);
			mensajeFuncional.setDescripcionFuncional(propiedades.getValue(ConstantesLI.MSG_DEFAULT_API));
			List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
			listaMensajes.add(mensajeFuncional);
			respuesta.setMensajeFuncional(listaMensajes);
		}
		return respuesta;
	}

	/**
	 * Método que mapea los mensajes de error para ser mostrados en pantalla y en el
	 * reporte funcional.
	 *
	 * @param respuesta   ErrorProxyComun
	 * @param codigoError String
	 * @throws ExceptionLI ExceptionLI
	 */
	private void mapearMensajeError(ErrorProxyComun respuesta, String codigoError) throws ExceptionLI {
		ErrorResponseDTO res = this.consultarMensajeError(codigoError);
		respuesta.setCodError(res.getCodigoError());
		String descErrorExc = respuesta.getDescError() == null ? "" : " (" + respuesta.getDescError() + ")";
		respuesta
				.setDescError(res.getDescripcionTecnica() + " (" + res.getMensajeFuncional().get(0).getCodigoFuncional()
						+ "-" + res.getMensajeFuncional().get(0).getDescripcionFuncional() + ")" + " (" + codigoError
						+ ")" + descErrorExc);
		respuesta.setCodFuncional(res.getMensajeFuncional().get(0).getCodigoFuncional());
		respuesta.setDescFuncional(res.getMensajeFuncional().get(0).getDescripcionFuncional());
		respuesta.setOperacion(res.getOperacion());
		respuesta.setServicio(res.getServicio());
	}

	/**
	 * Metodo para generar los documentos de bienvenida y retornarlos al front
	 *
	 * @param authorization JWT con la autorizacion para el consumo del servicio
	 * @param encrypter     Carga Util para consumir el servicio
	 * @param ipCliente     IP desde donde se realiza el consumo
	 * @return String codificado en base 64 con los documentos de bienvenida.
	 */
	public String[] descargarDocumentosBienvenida(String authorization, String encrypter, String ipCliente) {
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		Map<String, Object> datosCorreo = new java.util.HashMap<>();
		String cargaUtil = "";
		String[] respuesta = new String[2];
		EnvioCorreoDto datosEnvioCorreo = new EnvioCorreoDto();
		param = new HashMap<>();

		try {
			cargaUtil = encrypter;
			datosEnvioCorreo = gson.fromJson(cargaUtil, EnvioCorreoDto.class);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, datosEnvioCorreo.getIdSesion());

			param.put(ConstantesLI.DB_ID_SESION, datosEnvioCorreo.getIdSesion());
			param.put(ConstantesLI.DB_PASO, datosEnvioCorreo.getPasoFuncional());
			this.guardarDatosCorreoBD(datosEnvioCorreo);

			if (Integer.parseInt(datosEnvioCorreo.getNumeroDescarga()) == 0 ) {
				AutenticacionRS sesion = this.validarSesion(authorization, datosEnvioCorreo.getPasoFuncional(),
						datosEnvioCorreo.getIdSesion());
				mensaje = "Respuesta descarga documento " + sesion.getTokenNuevo() + datosEnvioCorreo.getIdSesion();
				logger.info(mensaje);
				respuesta[ConstantesLI.UNO] = sesion.getTokenNuevo();
			} else {
				respuesta[ConstantesLI.UNO] = authorization.split(" ")[ConstantesLI.UNO];
			}
			this.prepararDatosEnvioCorreo(datosCorreo, datosEnvioCorreo);
			respuesta[0] = this.crearDocumentos(datosCorreo, propiedades);

			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, datosEnvioCorreo.getPasoFuncional() + ConstantesLI.RESPUESTA_R);
			this.guardarDatosCorreoBD(datosEnvioCorreo);
		} catch (ExceptionLI e) {
			logger.error("ExceptionLI descargarDocumentosBienvenida", e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO,
					libreInversion.getInformacionTransaccion().getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			this.guardarDatosCorreoBD(datosEnvioCorreo);
		}
		return respuesta;
	}

	/**
	 * Método que crea el documento completo con la carta de bienvenida y el pagaré.
	 *
	 *
	 * @param datos       Map<String, Object>, con los datos necesarios para crear
	 *                    la plantilla de bienvenida y el pagaré.
	 * @param propiedades CargadorPropiedades.
	 * @return String codificado en base 64 con los documentos de bienvenida.
	 * @throws IOException
	 */
	public String crearDocumentos(Map<String, Object> datos, CargadorPropiedades propiedades) {
		File archivoFinal = null;
		String fileExtention = "pdf";
		String documento = "";

		List<Parametro> listaParametros = new ArrayList<>();
		listaParametros.add(new Parametro(ConstantesGeneracionPDF.PARA, datos.get(ConstantesGeneracionPDF.EMAIL)));
		listaParametros
				.add(new Parametro(ConstantesGeneracionPDF.NOMBRE, datos.get(ConstantesGeneracionPDF.NOMBRE_CLIENTE)));

		// Se ingresan los parametros que vienen del map
		for (Entry<String, Object> dato : datos.entrySet()) {
			if (ConstantesGeneracionPDF.RUTA_PLANTILLA_CORREO.equals(dato.getKey())) {
				listaParametros.add(new Parametro(dato.getKey(), dato.getValue().toString()));
			} else if (ConstantesGeneracionPDF.ASUNTO_CORREO.equals(dato.getKey())) {
				listaParametros.add(new Parametro(dato.getKey(), dato.getValue().toString()));
			} else if (ConstantesGeneracionPDF.IMAGENES.equals(dato.getKey())) {
				listaParametros.add(new Parametro(dato.getKey(), dato.getValue().toString()));
			} else if (ConstantesGeneracionPDF.DOCUMENTO_PDF.equals(dato.getKey())) {
				documento = dato.getValue().toString();
			} else {
				listaParametros.add(new Parametro(dato.getKey(), dato.getValue()));
			}
		}


		File cartaBienvenida = null;
		File transforDocs = null;
		List<File> listFiles = new ArrayList<>();

			if ((null != datos && !datos.isEmpty()) || null != propiedades) {
				PlantillasUtil plantillasUtil = new PlantillasUtil(listaParametros, propiedades,
						propiedades.getValue(ConstantesGeneracionPDF.NOMBRE_PLANTILLA_CORREO));
				plantillasUtil.createFile(true);
				cartaBienvenida = plantillasUtil.getFilePdf();
				listFiles.add(cartaBienvenida);//Carta_de_bienvenida_Correo
			}

			if (null != documento && !"".equals(documento)) {
				transforDocs = util.transformStringB64ToFile(documento, fileExtention);
				listFiles.add(transforDocs);// archivo_del_envio_correo
			}

		archivoFinal = util.mergePDFFiles(listFiles, fileExtention);


		byte[] fileContent = null;
		try {
			fileContent = FileUtils.readFileToByteArray(archivoFinal);
			Files.delete(transforDocs.toPath());
			if (null != cartaBienvenida) {
				Files.delete(cartaBienvenida.toPath());
			}
			if (null != archivoFinal) {
				Files.delete(archivoFinal.toPath());
			}
		} catch (IOException e) {
			logger.error("Error creando documentos de bienvenida", e);
		}
		return java.util.Base64.getEncoder().encodeToString(fileContent);
	}

	/**
	 * Metodo para consultar el estado de enrolamiento del cliente
	 *
	 * @param authorization JWT para la autorizacion
	 * @param encrypter     Carga util para el consumo del servicio
	 * @param ipCliente     IP del consumo
	 * @return Respuesta del proceso realizado
	 * @throws ExceptionLI Excepcion del proceso
	 */
	public AutenticacionFuerteRS consultarAuthFuerte(String authorization, String encrypter, String ipCliente)
			throws ExceptionLI {
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		AutenticacionFuerteRS respuesta = new AutenticacionFuerteRS();
		AutenticacionFuerteRQ rqAuth = new AutenticacionFuerteRQ();
		mensaje = "";
		param = new HashMap<>();

		try {
			rqAuth = gson.fromJson(encrypter, AutenticacionFuerteRQ.class);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, rqAuth.getIdSesion());

			param.put(ConstantesLI.DB_ID_SESION, rqAuth.getIdSesion());
			param.put(ConstantesLI.DB_PASO, rqAuth.getPasoFuncional());
			this.guardarDatosDB(param, encrypter);
			mensaje = "Inicio consultarAuthFuerte" + rqAuth.getIdSesion();
			logger.log(Level.INFO, mensaje);

			AutenticacionRS sesion = this.validarSesion(authorization, rqAuth.getPasoFuncional(), rqAuth.getIdSesion());
			respuesta.setTokenActual(sesion.getTokenNuevo());
			respuesta.setTiempoGeneracionToken(
					Integer.parseInt(propiedades.getValue(ConstantesLI.TIEMPO_GENERACION_TOKEN_NUEVO)));
			ServicioGestionAutenticacionFuerte serAuth = new ServicioGestionAutenticacionFuerte(rqAuth.getIdSesion(),
					propiedades.getPropiedades(), ipCliente, rqAuth.getTokenUsuario());
			PeticionGestionAutenticacionFuerte peticion = new PeticionGestionAutenticacionFuerte();
			peticion.setNumeroDocumento(rqAuth.getNumeroDocumento());
			peticion.setTipoDocumento(rqAuth.getTipoDocumento());
			RespuestaGestionAutenticacionFuerte resAuth = serAuth.consultarEnrolamientoCliente(peticion);

			respuesta.setMecanismo(resAuth.getMecanismo());
			respuesta.setCorreoElectronico(resAuth.getCorreoElectronico());
			respuesta.setNumeroCelular(resAuth.getNumeroCelular());
			respuesta.setFechaInscripcion(resAuth.getFechaInscripcion());
			respuesta.setUltimaFechaModificacionDatos(resAuth.getUltimaFechaModificacionDatos());
			respuesta.setUltimaFechaModificacionMecanismo(resAuth.getUltimaFechaModificacionMecanismo());
			respuesta.setEstadoServicioOTP(resAuth.getEstadoServicioOTP());
			respuesta.setMetodoEnvioOTPODA(resAuth.getMetodoEnvioOTPODA());

			param.put(ConstantesLI.DB_ID_SESION, rqAuth.getIdSesion());
			param.put(ConstantesLI.DB_PASO, rqAuth.getPasoFuncional() + ConstantesLI.RESPUESTA_R);
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (BusinessExceptionMsg e) {
			mensaje = "Error BusinessExceptionMsg consultarAuthFuerte" + rqAuth.getIdSesion() + " Error: "
					+ e.getFaultInfo().getGenericException().getCode() + " : "
					+ e.getFaultInfo().getGenericException().getDescription();
			logger.log(Level.INFO, mensaje, e);
			respuesta.setMecanismo(ConstantesLI.OTP_ATALLA);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, rqAuth.getPasoFuncional() + ConstantesLI.RESPUESTA_R);
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (ExceptionLI e) {
			mensaje = "Error ExceptionLI consultarAuthFuerte" + rqAuth.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, rqAuth.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError(e.getCodigo());
			respuesta.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuesta, e.getCodigo());
			}else{
				mapearMensajeError(respuesta, "CAF-" + e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (ParseException | DatatypeConfigurationException | SystemExceptionMsg e) {
			mensaje = "Error consultarAuthFuerte" + rqAuth.getIdSesion() + " - " + ipCliente;
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, rqAuth.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CAF-500");
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (Exception e) {
			// se usa excepcion generica ya que se realiza llamado a servicios banco y
			// pueden
			// generarse algunas otras excepciones no contempladas en el proceso
			mensaje = "Error Exception consultarAuthFuerte: " + e.getMessage();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, rqAuth.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CAF-500");
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		}

		return respuesta;
	}

	/**
	 * Metodo para la generacion de claves para la firma de documentos, aplica para
	 * generar ODA y Token
	 *
	 * @param authorization JWT con la autorizacion de la transaccion
	 * @param encrypter     carga util con los datos del proceso
	 * @param ipCliente     Ip desde donde se realiza el consumo
	 * @return Respuesta del flujo
	 * @throws ExceptionLI excepcion controlada del flujo
	 */
	public GeneracionClaveRS generacionClave(String authorization, String encrypter, String ipCliente)
			throws ExceptionLI {
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		mensaje = "";
		GeneracionClaveRS respuesta = new GeneracionClaveRS();
		GeneracionClaveRQ generacionClaveRQ = new GeneracionClaveRQ();
		param = new HashMap<>();

		try {
			generacionClaveRQ = gson.fromJson(encrypter, GeneracionClaveRQ.class);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, generacionClaveRQ.getIdSesion());

			param.put(ConstantesLI.DB_ID_SESION, generacionClaveRQ.getIdSesion());
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional());
			this.guardarDatosDB(param, encrypter);
			mensaje = "Inicio generacionClave" + generacionClaveRQ.getIdSesion();
			logger.log(Level.INFO, mensaje);

			AutenticacionRS sesion = this.validarSesion(authorization, generacionClaveRQ.getPasoFuncional(),
					generacionClaveRQ.getIdSesion());
			respuesta.setTokenActual(sesion.getTokenNuevo());

			if (ConstantesLI.VAR_CLAVE_OTPODA.equalsIgnoreCase(generacionClaveRQ.getMecanismo())) {
				llamadoGeneracionODA(generacionClaveRQ, respuesta, ipCliente);

			} else if (ConstantesLI.OTP_ATALLA.equalsIgnoreCase(generacionClaveRQ.getMecanismo())) {
				llamadoGeneracionToken(generacionClaveRQ, respuesta, ipCliente);
			}

			if (StringUtils.isBlank(respuesta.getCodError())) {
				param.remove(ConstantesLI.DB_PASO);
				param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_R);
				this.guardarDatosDB(param, gson.toJson(respuesta));
			}
			respuesta.setOtp(ConstantesLI.VAR_CLAVE_OTPODA);
			respuesta.setToken(ConstantesLI.OTP_ATALLA);
		} catch (ExceptionLI e) {
			mensaje = "Error ExceptionLI generacionClave ::" + generacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError(e.getCodigo());
			respuesta.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuesta, e.getCodigo());
			}else{
				mapearMensajeError(respuesta, "CGT-" + e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (Exception e) {
			// se usa excepcion generica ya que se realiza llamado a servicios banco y
			// pueden
			// generarse algunas otras excepciones no contempladas en el proceso
			mensaje = "Error Exception generacionClave: " + e.getMessage();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CGT-500");
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		}

		return respuesta;
	}

	private void llamadoGeneracionToken(GeneracionClaveRQ generacionClaveRQ, GeneracionClaveRS respuesta,
			String ipCliente) throws ExceptionLI {
		try {
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);

			GeneracionTokenRQ generacionTokenRQ = new GeneracionTokenRQ();
			generacionTokenRQ.setIdServidor(propiedades.getValue(ConstantesLI.TOKEN_ID_SERVIDOR));
			generacionTokenRQ.setIdSistemaFuente(propiedades.getValue(ConstantesLI.TOKEN_SISTEMA_FUENTE));
			generacionTokenRQ.setTipoDocumento(generacionClaveRQ.getTipoDocumento());
			generacionTokenRQ.setNumeroDocumento(generacionClaveRQ.getNumeroDocumento());

			if (StringUtils.isNotBlank(generacionClaveRQ.getCelular())) {
				generacionTokenRQ.setCelular(new BigInteger(generacionClaveRQ.getCelular()));
			} else {
				throw new ExceptionLI("901", "Los datos de cliente no son válidos para el firmado por Token(ATALLA)");
			}
			generacionTokenRQ.setCorreoElectronicoCliente(generacionClaveRQ.getCorreoElectronico());

			generacionTokenRQ.setGeneraTokenCifrado(propiedades.getValue(ConstantesLI.TOKEN_GENERACION_TOKEN_CIFRADO));
			generacionTokenRQ.setEnviarToken(propiedades.getValue(ConstantesLI.TOKEN_ENVIAR_TOKEN));
			generacionTokenRQ.setIdSesion(generacionClaveRQ.getIdSesion());

			// Cambiar el proxy
			ServicioGeneracionToken servicioGeneracionToken = new ServicioGeneracionToken(
					generacionClaveRQ.getIdSesion(), propiedades.getPropiedades(), ipCliente, null);
			SolicitarTokenResponse respGenToken = servicioGeneracionToken.solicitarToken(generacionTokenRQ);
			respuesta.setToken(respGenToken.getTokenGenerado());
			respuesta.setEstadoSolicitud(respGenToken.getRespuesta().getCodigoRespuesta() + " - "
					+ respGenToken.getRespuesta().getDescripcionRespuesta());
		} catch (com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.SystemExceptionMsg e) {
			mensaje = "Error SystemExceptionMsg: " + generacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CGT-" + e.getFaultInfo().getGenericException().getCode());
			respuesta.setDescError(e.getFaultInfo().getGenericException().getDescription());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.BusinessExceptionMsg e) {
			mensaje = "Error BusinessExceptionMsg: " + generacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CGT-" + e.getFaultInfo().getGenericException().getCode());
			respuesta.setDescError(e.getFaultInfo().getGenericException().getDescription());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (ExceptionLI e) {
			mensaje = "Error ExceptionLI llamadoGeneracionToken ::" + e.getMensaje();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError(e.getCodigo());
			respuesta.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuesta, e.getCodigo());
			}else{
				mapearMensajeError(respuesta, "CGT-" + e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (Exception e) {
			// se usa excepcion generica ya que se realiza llamado a servicios banco y
			// pueden
			// generarse algunas otras excepciones no contempladas en el proceso
			mensaje = "Error Exception consultarAuthFuerte" + e.getMessage();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CGT-500");
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		}

	}

	private void llamadoGeneracionODA(GeneracionClaveRQ generacionClaveRQ, GeneracionClaveRS respuesta,
			String ipCliente) throws ExceptionLI {
		try {
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
			ServicioGenerarOTP servicioGenerarOTP = new ServicioGenerarOTP(generacionClaveRQ.getIdSesion(),
					propiedades.getPropiedades(), ipCliente, null);
			AutenticacionOTPRQ autenticacionOTPRQ = new AutenticacionOTPRQ();
			autenticacionOTPRQ.setIdentificacionCanal(propiedades.getValue(ConstantesLI.CLAVE_IDENTIFICACION_CANAL));
			autenticacionOTPRQ.setIdSesion(generacionClaveRQ.getIdSesion());
			autenticacionOTPRQ.setSharedKey(util.generarSharedKey(generacionClaveRQ.getTipoDocumento(),
					generacionClaveRQ.getNumeroDocumento()));
			autenticacionOTPRQ.setInformacionMensaje(new InformacionMensajeType());
			autenticacionOTPRQ.getInformacionMensaje().setDestinatario(new Destinatario());
			autenticacionOTPRQ.getInformacionMensaje().setTipoMensaje(util.buscarTipoMensaje(generacionClaveRQ));
			autenticacionOTPRQ.getInformacionMensaje().getDestinatario()
					.setTipoDocumento(generacionClaveRQ.getTipoDocumento());
			autenticacionOTPRQ.getInformacionMensaje().getDestinatario()
					.setNumeroDocumento(generacionClaveRQ.getNumeroDocumento());
			autenticacionOTPRQ.getInformacionMensaje().getDestinatario()
					.setCelular(autenticacionOTPRQ.getInformacionMensaje().getTipoMensaje()
							.equalsIgnoreCase(ConstantesLI.TIPO_MENSAJE_M) ? generacionClaveRQ.getCelular() : null);
			autenticacionOTPRQ.getInformacionMensaje().getDestinatario()
					.setCorreoElectronico(autenticacionOTPRQ.getInformacionMensaje().getTipoMensaje().equalsIgnoreCase(
							ConstantesLI.TIPO_MENSAJE_C) ? generacionClaveRQ.getCorreoElectronico() : null);
			autenticacionOTPRQ.getInformacionMensaje().getDestinatario().setNombres(generacionClaveRQ.getNombres());

			autenticacionOTPRQ.getInformacionMensaje()
					.setCodigoOperacion(propiedades.getValue(ConstantesLI.CLAVE_COD_OPERACION));
			autenticacionOTPRQ.getInformacionMensaje().setAsunto(propiedades.getValue(ConstantesLI.CLAVE_ASUNTO));
			autenticacionOTPRQ.getInformacionMensaje()
					.setContenidoMensaje(propiedades.getValue(ConstantesLI.CLAVE_CONTENIDO_MENSAJE));
			autenticacionOTPRQ.getInformacionMensaje()
					.setCodigoAplicacion(propiedades.getValue(ConstantesLI.CLAVE_COD_APLICACION));
			mensaje = "Datos para enviarOTP: " + gson.toJson(autenticacionOTPRQ);
			logger.debug(mensaje);

			GenerarEnviarOTPODAResponse respuestaGenEnviar = servicioGenerarOTP.enviarOTP(autenticacionOTPRQ);
			respuesta.setOtp(respuestaGenEnviar.getOtp());
		} catch (com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.enlace.v2.BusinessExceptionMsg e) {
			mensaje = "Error BusinessExceptionMsg" + generacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CGOO-" + e.getFaultInfo().getGenericException().getCode());
			respuesta.setDescError(e.getFaultInfo().getGenericException().getDescription());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.enlace.v2.SystemExceptionMsg e) {
			mensaje = "Error SystemExceptionMsg::" + generacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CGOO-" + e.getFaultInfo().getGenericException().getCode());
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (Exception e) {
			// se usa excepcion generica ya que se realiza llamado a servicios banco y
			// pueden
			// generarse algunas otras excepciones no contempladas en el proceso
			mensaje = "Error Exception consultarAuthFuerte" + generacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CGOO-500");
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		}
	}

	public ValidacionClaveRS validacionClave(String authorization, String encrypter, String ipCliente)
			throws ExceptionLI {
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		mensaje = "";
		ValidacionClaveRS respuesta = new ValidacionClaveRS();
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		param = new HashMap<>();

		try {

			validacionClaveRQ = gson.fromJson(encrypter, ValidacionClaveRQ.class);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, validacionClaveRQ.getIdSesion());

			param.put(ConstantesLI.DB_ID_SESION, validacionClaveRQ.getIdSesion());
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional());
			this.guardarDatosDB(param, encrypter);
			mensaje = "Inicio validacionClave " + validacionClaveRQ.getIdSesion();
			logger.log(Level.INFO, mensaje);

			AutenticacionRS sesion = this.validarSesion(authorization, validacionClaveRQ.getPasoFuncional(),
					validacionClaveRQ.getIdSesion());
			respuesta.setTokenActual(sesion.getTokenNuevo());

			// validacion de token con autenticarClienteOTP o autenticarClienteOTPSoftoken
			if (ConstantesLI.VAR_CLAVE_OTPODA.equalsIgnoreCase(validacionClaveRQ.getMecanismo())) {
				llamadoValidacionODA(validacionClaveRQ, respuesta, ipCliente);
			} else if (ConstantesLI.OTP_ATALLA.equalsIgnoreCase(validacionClaveRQ.getMecanismo())) {
				llamadoValidacionToken(validacionClaveRQ, respuesta, ipCliente);
			} else { // ConstantesLI.VAR_CLAVE_SOFTOKEN
				llamadoValidacionSoftoken(validacionClaveRQ, respuesta, ipCliente);
			}

			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_R);
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (ExceptionLI e) {
			mensaje = "Error ExceptionLI validacionClave ::" + e.getMensaje();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_R);
			respuesta.setCodError(e.getCodigo());
			respuesta.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuesta, e.getCodigo());
			}else{
				mapearMensajeError(respuesta, "CVT-" + e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (Exception e) {
			// se usa excepcion generica ya que se realiza llamado a servicios banco y
			// pueden
			// generarse algunas otras excepciones no contempladas en el proceso
			mensaje = "Error Exception validacion clave" + validacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CLA-500");
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		}
		return respuesta;
	}

	private void llamadoValidacionODA(ValidacionClaveRQ validacionClaveRQ, ValidacionClaveRS respuesta,
			String ipCliente) throws ExceptionLI {
		try {
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
			ServicioAutenticarClienteOTP service = new ServicioAutenticarClienteOTP(validacionClaveRQ.getIdSesion(),
					propiedades.getPropiedades(), ipCliente, null);
			AutenticarClienteOTPRQ autRQ = new AutenticarClienteOTPRQ();
			autRQ.setSharedKey(util.generarSharedKey(validacionClaveRQ.getTipoDocumento(),
					validacionClaveRQ.getNumeroDocumento()));
			autRQ.setIdentificacionCanal(propiedades.getValue(ConstantesLI.CLAVE_IDENTIFICACION_CANAL));
			autRQ.setIdSesion(validacionClaveRQ.getIdSesion());
			autRQ.setOtp(validacionClaveRQ.getOtp());
			AutenticarClienteOTPODAResponse resAuthODA = service.autenticarClienteOTP(autRQ);

			respuesta.setResultCodeODA(resAuthODA.getResultCode());
			respuesta.setResultDescriptionODA(resAuthODA.getResultDescription());
			respuesta.setIntentosFallidosRestantesODA(resAuthODA.getIntentosFallidosRestantes());

			if (StringUtils.isNotBlank(resAuthODA.getResultCode())) {
				switch (resAuthODA.getResultCode()) {
				case "901": // ok
					break;
				case "902":
					if (!ConstantesLI.UNO_TXT.equalsIgnoreCase(resAuthODA.getIntentosFallidosRestantes())) {
						throw new ExceptionLI("902", "Clave Dinámica incorrecta.");
					} else {
						throw new ExceptionLI("903",
								"903 Clave Dinámica incorrecta. Si fallas el próximo intento será bloqueada");
					}
				case "1021":
					throw new ExceptionLI("1021", "1021 Por tu seguridad, la Clave Dinámica fue bloqueada. "
							+ "Debes cambiar la clave de tu tarjeta en Cajeros Automáticos, PAC o Sucursales Físicas.");
				case "1022":
					throw new ExceptionLI("1022",
							"1022 Superaste el número de intentos permitidos y la Clave dinámica está bloqueada. "
									+ "Debes cambiar la clave de tu tarjeta en Cajeros Automáticos, PAC o Sucursales Físicas.");
				case "803":
					throw new ExceptionLI("803", "Tu clave dinámica ha expirado.");
				default:
					throw new ExceptionLI(resAuthODA.getResultCode(), "Codigo de error no conocido llamadoValidacionODA");
				}
			} else {
				throw new ExceptionLI("900", "Sin respuesta de ServicioAutenticarClienteOTP ");
			}

		} catch (com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.enlace.v2.SystemExceptionMsg e) {
			mensaje = "Error SystemExceptionMsg" + validacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("VCO-" + e.getFaultInfo().getGenericException().getCode());
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (ExceptionLI e) {
			mensaje = "Error ExceptionLI validacionClave ::" + e.getMensaje();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError(e.getCodigo());
			respuesta.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuesta, e.getCodigo());
			}else{
				mapearMensajeError(respuesta, "VCO-" + e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (Exception e) {
			// se usa excepcion generica ya que se realiza llamado a servicios banco y
			// pueden
			// generarse algunas otras excepciones no contempladas en el proceso
			mensaje = "Error Exception validacionClave" + validacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("VCO-500");
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		}
	}

	private void llamadoValidacionSoftoken(ValidacionClaveRQ validacionClaveRQ, ValidacionClaveRS respuesta,
			String ipCliente) throws ExceptionLI {
		try {
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
			ServicioAutenticarClienteOTP service = new ServicioAutenticarClienteOTP(validacionClaveRQ.getIdSesion(),
					propiedades.getPropiedades(), ipCliente, null);
			AutenticarClienteOTPRQ autRQ = new AutenticarClienteOTPRQ();
			autRQ.setSharedKey(util.generarSharedKey(validacionClaveRQ.getTipoDocumento(),
					validacionClaveRQ.getNumeroDocumento()));
			autRQ.setIdentificacionCanal(propiedades.getValue(ConstantesLI.CLAVE_IDENTIFICACION_CANAL));
			autRQ.setIdSesion(validacionClaveRQ.getIdSesion());
			autRQ.setOtp(validacionClaveRQ.getOtp());
			AutenticarClienteOTPSoftokenResponse resAuthSFTNK = service.autenticarClienteOTPSoftoken(autRQ);

			respuesta.setResultCodeODA(resAuthSFTNK.getResultCode());
			respuesta.setResultDescriptionODA(resAuthSFTNK.getResultDescription());
			respuesta.setIntentosFallidosRestantesODA(resAuthSFTNK.getIntentosFallidosRestantes());

			if (StringUtils.isNotBlank(resAuthSFTNK.getResultCode())) {
				switch (resAuthSFTNK.getResultCode()) {
				case "801": // ok
					break;
				case "802":
					if (!ConstantesLI.UNO_TXT.equalsIgnoreCase(resAuthSFTNK.getIntentosFallidosRestantes())) {
						throw new ExceptionLI("802", "Clave Dinámica incorrecta.");
					} else {
						throw new ExceptionLI("803",
								"803 Clave Dinámica incorrecta. Si fallas el próximo intento será bloqueada");
					}
				case "1021":
					throw new ExceptionLI("1021", "1021 Por tu seguridad, la Clave Dinámica fue bloqueada. "
							+ "Debes cambiar la clave de tu tarjeta en Cajeros Automáticos, PAC o Sucursales Físicas.");
				case "1022":
					throw new ExceptionLI("1022",
							"1022 Superaste el número de intentos permitidos y la Clave dinámica está bloqueada. "
									+ "Debes cambiar la clave de tu tarjeta en Cajeros Automáticos, PAC o Sucursales Físicas.");
				default:
					throw new ExceptionLI(resAuthSFTNK.getResultCode(), "Codigo de error no conocido llamadoValidacionSoftoken");
				}
			} else {
				throw new ExceptionLI("900", "Sin respuesta de ServicioAutenticarClienteOTP ");
			}

		} catch (com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.enlace.v2.SystemExceptionMsg e) {
			mensaje = "Error SystemExceptionMsg llamadoValidacionSoftoken: " + e.getMessage();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("VCS-" + e.getFaultInfo().getGenericException().getCode());
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (ExceptionLI e) {
			mensaje = "Error ExceptionLI llamadoValidacionSoftoken ::" + e.getMensaje();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError(e.getCodigo());
			respuesta.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuesta, e.getCodigo());
			}else{
				mapearMensajeError(respuesta, "VCS-" + e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (Exception e) {
			// se usa excepcion generica ya que se realiza llamado a servicios banco y
			// pueden
			// generarse algunas otras excepciones no contempladas en el proceso
			mensaje = "Error Exception llamadoValidacionSoftoken" + validacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("VCS-500");
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		}

	}

	private void llamadoValidacionToken(ValidacionClaveRQ validacionClaveRQ, ValidacionClaveRS respuesta,
			String ipCliente) throws ExceptionLI {
		try {
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
			ServicioGeneracionToken servicioGeneracionToken = new ServicioGeneracionToken(
					validacionClaveRQ.getIdSesion(), propiedades.getPropiedades(), ipCliente, null);
			GeneracionTokenRQ generacionTokenRQ = new GeneracionTokenRQ();
			generacionTokenRQ.setIdServidor(propiedades.getValue(ConstantesLI.TOKEN_ID_SERVIDOR));
			generacionTokenRQ.setIdSistemaFuente(propiedades.getValue(ConstantesLI.TOKEN_SISTEMA_FUENTE));
			generacionTokenRQ.setTipoDocumento(validacionClaveRQ.getTipoDocumento());
			generacionTokenRQ.setNumeroDocumento(validacionClaveRQ.getNumeroDocumento());
			if (StringUtils.isNotBlank(validacionClaveRQ.getCelular())) {
				generacionTokenRQ.setCelular(new BigInteger(validacionClaveRQ.getCelular()));
			}

			generacionTokenRQ.setTokenCifrado(propiedades.getValue(ConstantesLI.TOKEN_CIFRADO));
			generacionTokenRQ.setTokenEntrada(validacionClaveRQ.getTokenEntrada());
			generacionTokenRQ.setIdSesion(validacionClaveRQ.getIdSesion());
			// Cambiar el proxy
			ValidarTokenResponse respValToken = servicioGeneracionToken.validarToken(generacionTokenRQ);

			respuesta.setCodigoRespuestaTOKEN(respValToken.getRespuesta().getCodigoRespuesta());
			respuesta.setDescripcionRespuestaTOKEN(respValToken.getRespuesta().getDescripcionRespuesta());
		} catch (com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.SystemExceptionMsg e) {
			mensaje = "Error SystemExceptionMsg" + validacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CVT-" + e.getFaultInfo().getGenericException().getCode());
			respuesta.setDescError(e.getFaultInfo().getGenericException().getDescription());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.BusinessExceptionMsg e) {
			mensaje = "Error BusinessExceptionMsg" + validacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CVT-" + e.getFaultInfo().getGenericException().getCode());
			respuesta.setDescError(e.getFaultInfo().getGenericException().getDescription());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (Exception e) {
			// se usa excepcion generica ya que se realiza llamado a servicios banco y
			// pueden
			// generarse algunas otras excepciones no contempladas en el proceso
			mensaje = "Error Exception llamadoValidacionToken" + validacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CVT-500");
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		}

	}

	/**
	 * Método que obtiene los documentos de la póliza y carta de bienvenida para
	 * seguro empleado protegido
	 *
	 * @param datosCliente   EnvioCorreoDto con los datos del cliente necesario para
	 *                       incluir en la carta
	 * @param datosPlantilla Map con los datos necesarios para crear la carta de
	 *                       bienvenida
	 * @return String con el pagaré, poliza y carta de bienvenida unificados
	 */
	private String obtenerArchivosSeguro(EnvioCorreoDto datosCliente, Map<String, Object> datosPlantilla) {
		String fileExtention = "pdf";
		String rutaPlantilla = "";
		List<Parametro> listaParametros = new ArrayList<>();
		byte[] fileContent = null;
		File transforBy64 = null;

		// Se ingresan los parametros que vienen del map
		for (Entry<String, Object> dato : datosPlantilla.entrySet()) {
			if (ConstantesGeneracionPDF.RUTA_PLANTILLA_CORREO.equals(dato.getKey())) {
				listaParametros.add(new Parametro(dato.getKey(), dato.getValue().toString()));
				rutaPlantilla = dato.getValue().toString();
			} else if (ConstantesGeneracionPDF.IMAGENES.equals(dato.getKey())) {
				listaParametros.add(new Parametro(dato.getKey(), dato.getValue().toString()));
			} else {
				listaParametros.add(new Parametro(dato.getKey(), dato.getValue()));
			}
		}
		listaParametros.add(new Parametro(ConstantesGeneracionPDF.NOMBRE,
				datosPlantilla.get(ConstantesGeneracionPDF.NOMBRE_CLIENTE)));
		listaParametros.add(new Parametro("CIUDAD", this.obtenerCiudadSucursal(datosCliente.getCiudadCliente())));
		listaParametros.add(new Parametro("FECHA", util.getFechaActualLetra()));

		File polizaSeguro = new File(propiedades.getValue(rutaPlantilla),
				propiedades.getValue(ConstantesGeneracionPDF.NOMBRE_POLIZA_SEGURO));
		List<File> listFiles = new ArrayList<>();
		transforBy64 =  util.transformStringB64ToFile(datosCliente.getDocumentoPdf(), fileExtention);



		PlantillasUtil plantillasUtil = new PlantillasUtil(listaParametros, propiedades,
				propiedades.getValue(ConstantesLI.NOMBRE_CARTA_SEGURO));
		plantillasUtil.createFile(false);
		File carta = plantillasUtil.getFilePdf();

		listFiles.add(transforBy64);// pagare
		listFiles.add(carta);// carta_Bienvenida__Carta_bienvenida_Bancolombia.vm
		listFiles.add(polizaSeguro);// poliza__Solicitud_de_Seguro.pdf

		File archivoFinal = util.mergePDFFiles(listFiles, fileExtention);
		try {
			fileContent = FileUtils.readFileToByteArray(archivoFinal);
		} catch (IOException e) {
			logger.error(String.format("Error creando documentos de bienvenida %s", e.getMessage()), e);
		} finally {
			if (null != archivoFinal) {
				try {
					Files.delete(archivoFinal.toPath());
					Files.delete(transforBy64.toPath());
					Files.delete(carta.toPath());
				} catch (IOException e) {
					logger.error(String.format("Error eliminando documento final %s", e.getMessage()), e);
				}
			}
		}
		return java.util.Base64.getEncoder().encodeToString(fileContent);
	}

	/**
	 * Método para obtener la ciudad en la que se encuentra la sucursal en la que se
	 * está realizando el desembolso.
	 *
	 * @param codigoSucursal String con el código de la sucursal a la que se
	 *                       encuentra relacionado el asesor.
	 * @return String con el nombre de la ciudad en la que se encuentra asociada la
	 *         sucursal.
	 */
	private String obtenerCiudadSucursal(String codigoSucursal) {
		String ciudad = "";
		String respuestaCat;
		try {
			respuestaCat = callRestServiceBack.llamadoBackInternoCatalogo("{\"idcatalogo\" : \"7\"}",
					propiedades.getValue("API_ENDPOINT_CALL_CATALOGOS") + ConstantesLI.SERVICIO_CATALOGOS);
			Catalogos cat = gson.fromJson(respuestaCat, Catalogos.class);
			for (Catalogo catalogo : cat.getLista()) {
				if (catalogo.getId().equals(codigoSucursal)) {
					ciudad = catalogo.getDescripcion();
				}
			}
		} catch (ExceptionLI | IOException e) {
			logger.error(String.format("Error consultando el catálogo sucursal_ciudad %s ", e.getMessage()), e);
		}
		return ciudad;
	}

	/**
	 * Método para validar la sesion de todos los pasos de la experiencia.
	 *
	 * @param authorization
	 * @param pasoFuncional
	 * @param idSesion
	 * @return
	 * @throws ExceptionLI
	 */
	private AutenticacionRS validarSesion(String authorization, String pasoFuncional, String idSesion)
			throws ExceptionLI {

		if (this.libreInversion == null) {
			this.libreInversion = new LibreInversion();
		}

		this.libreInversion.setInformacionTransaccion(new InformacionTransaccionDto());
		this.libreInversion.getInformacionTransaccion().setPasoFuncional(pasoFuncional);
		this.libreInversion.getInformacionTransaccion().setIdSesion(idSesion);

		String cargaUtil = gson.toJson(this.libreInversion);

		return autenticacion.validarSesion(authorization, cargaUtil);
	}

	public String generarTokenContenedor(AutenticacionCont param) throws ExceptionLI {
		try {
			logger.info("Generando Token contenedor");
			return utilsJwt.crearTokenContenedor(param.getIdSesion(), param.getMinutosExpiracion(),
					 param.getPasoFuncional(), param.getUsuarioRed(), param.getNombrCompleto(),
					param.getCedula(), param.getId());
		} catch (bancolombia.dtd.vd.li.api.autenticacion.exceptions.ValidacionException e) {
			logger.error(ConstantesLI.MSJ_ERROR_GEN + e.getMessage());
			throw new ExceptionLI("500", ConstantesLI.MSJ_SESION_INVAL, null);
		}
	}

	/**
	 * @param autenticacion the autenticacion to set
	 */
	public void setAutenticacion(ServicioAutenticacion autenticacion) {
		this.autenticacion = autenticacion;
	}

}
