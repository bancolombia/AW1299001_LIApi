package bancolombia.dtd.vd.li.api.rest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import bancolombia.dtd.vd.li.api.autenticacion.ServicioAutenticacion;
import bancolombia.dtd.vd.li.api.autenticacion.exceptions.ValidacionException;
import bancolombia.dtd.vd.li.commons.exception.ExceptionLI;
import bancolombia.dtd.vd.li.dto.autenticacion.AutenticacionCont;
import bancolombia.dtd.vd.li.dto.autenticacion.AutenticacionRS;
import bancolombia.dtd.vd.li.dto.proxy.authFuerte.AutenticacionFuerteRS;
import bancolombia.dtd.vd.li.dto.proxy.clave.GeneracionClaveRS;
import bancolombia.dtd.vd.li.dto.proxy.clave.ValidacionClaveRS;
import bancolombia.dtd.vd.li.dto.proxy.codAsesor.ConsultarDatosSucursalRS;
import bancolombia.dtd.vd.li.dto.proxy.consultarProductoDepositos.ConsultarProductoDepositosRSApi;
import bancolombia.dtd.vd.li.dto.proxy.datosCliente.ConsultarDatosClienteRS;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRS;
import bancolombia.dtd.vd.li.dto.proxy.gestionPrecalculadoCliente.ProductoPreaprobadoBasicoRS;
import bancolombia.dtd.vd.li.dto.proxy.validacionpreguntas.ValidacionPreguntasRS;
import bancolombia.dtd.vd.sucursales.api.datosCliente.ServicioDatosCliente;
import bancolombia.dtd.vd.sucursales.api.datosCliente.util.ConstantesLI;
import bancolombia.dtd.vd.sucursales.api.datosCliente.util.Utilities;
import org.jose4j.jwt.MalformedClaimException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;


@Path("/servicio")
public class ControladorDatosCliente {
	private static final Logger logger = LogManager.getLogger(ControladorDatosCliente.class);

	private static final String ENCONDING = "UTF-8";
	private static Gson json = new Gson();
	private static Utilities util = new Utilities();
	private String mensaje = "";

	@Inject
	private ServicioAutenticacion servicioAutenticacion;

	@Inject
	private ServicioDatosCliente servicioDatosCliente;

	
	
	
	/**
	 * @param servicioAutenticacion
	 * @param servicioDatosCliente
	 */
	public void setControladorDatosCliente(ServicioAutenticacion servicioAutenticacion,
			ServicioDatosCliente servicioDatosCliente) {
		this.servicioAutenticacion = servicioAutenticacion;
		this.servicioDatosCliente = servicioDatosCliente;
	}
	
	
	/**
	 * Metodo para el consumo del servicio consulta datos comerciales sucursal
	 * 
	 * @param requestContext contexto de la peticion
	 * @param authorization  JWT con datos de autorizacion
	 * @param encrypter      Carga util para el consumo
	 * @return Respuesta del proceso
	 * @throws ExceptionLI excepcion de aplicacion por si se genera algun problema
	 */
	@POST
	@Path("codAsesor")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response consultarAsesor(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {

		Response response = null;
		try {
			ConsultarDatosSucursalRS consultarDatosSucursalRS = servicioDatosCliente.consultarAsesor(authorization,
					encrypter, util.getIpCliente(requestContext));
			response = validResponseErrorSession(consultarDatosSucursalRS.getCodFuncional(), consultarDatosSucursalRS);
		} catch (ExceptionLI e) {
			logger.error("Error Cod Asesor INTERNAL_SERVER_ERROR", e);
			if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_401)) {
				response = Response.status(Status.UNAUTHORIZED).build();
			} else {
				response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error Cod Asesor.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return response;
	}

	@POST
	@Path("consultaCuentas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response consultaCuentas(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {
			ConsultarProductoDepositosRSApi consultarProductoDepositosRS = servicioDatosCliente
					.consultaCuentas(authorization, encrypter, util.getIpCliente(requestContext));
			response = validResponseErrorSession(consultarProductoDepositosRS.getCodFuncional(), consultarProductoDepositosRS);
		} catch (ExceptionLI e) {
			logger.error("Error Consulta cuentas INTERNAL_SERVER_ERROR", e);
			if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_401)) {
				response = Response.status(Status.UNAUTHORIZED).build();
			} else if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_600)) {
				response = Response.status(Status.SEE_OTHER).build();
			} else {
				response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error Consulta cuentas.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@POST
	@Path("consultaDatosUbicacion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response consultaDatosUbicacion(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {
			ConsultarDatosClienteRS consultaUbicacionesRS = servicioDatosCliente.consultaDatosUbicacion(authorization,
					encrypter, util.getIpCliente(requestContext));
			response = validResponseErrorSession(consultaUbicacionesRS.getCodFuncional(), consultaUbicacionesRS);
		} catch (ExceptionLI e) {

			logger.error("Error consultaDatosUbicacion INTERNAL_SERVER_ERROR", e);
			if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_401)) {
				response = Response.status(Status.UNAUTHORIZED).build();
			} else {
				response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error consultaDatosUbicacion.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	/*
	 * Se agrega nueva operacion para consultar los datos basicos del cliente Autor:
	 * Harold Pinto (Desarrollador)
	 * 
	 */

	@POST
	@Path("consultaDatosBasicos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response consultaDatosBasicos(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {
			ConsultarDatosClienteRS consultaDatosBasicoRS = servicioDatosCliente
					.consultaDatosBasicosCliente(authorization, encrypter, util.getIpCliente(requestContext));
			response = validResponseErrorSession(consultaDatosBasicoRS.getCodFuncional(), consultaDatosBasicoRS);
		} catch (ExceptionLI e) {

			logger.error("Error consultaDatosBasicos INTERNAL_SERVER_ERROR", e);
			if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_401)) {
				response = Response.status(Status.UNAUTHORIZED).build();
			} else {
				response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error consultaDatosBasicos.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@POST
	@Path("consultarPrecalculadoBasico")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response consultaPrecalculadoCliente(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {

			ProductoPreaprobadoBasicoRS gestionPrecalculadoClienteRS = servicioDatosCliente
					.consultaPrecalculadoCredito(authorization, encrypter, util.getIpCliente(requestContext));
			response = validResponseErrorSession(gestionPrecalculadoClienteRS.getCodFuncional(), gestionPrecalculadoClienteRS);
		} catch (ExceptionLI e) {
			logger.error("Error consultaPrecalculadoCliente INTERNAL_SERVER_ERROR", e);
			if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_401)) {
				response = Response.status(Status.UNAUTHORIZED).build();
			} else if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_600)) {
				response = Response.status(Status.SEE_OTHER).build();
			} else {
				ExceptionLI rep = new ExceptionLI(e.getCodigo(), "");
				response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(json.toJson(rep)).build();
			}
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error consultaPrecalculadoCliente.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@POST
	@Path("crearCreditoLibreInversion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response crearCreditoConsumoLibreInversion(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		return crearCreditoConsumo(requestContext, authorization, encrypter);
	}

	@POST
	@Path("crearCreditoCrediagil")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response crearCreditoConsumoCrediagil(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		return crearCreditoConsumo(requestContext, authorization, encrypter);
	}

	@POST
	@Path("crearCreditoLibranza")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response crearCreditoConsumoLibranza(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		return crearCreditoConsumo(requestContext, authorization, encrypter);
	}

	@POST
	@Path("crearCreditoLibranzaColpensiones")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response crearCreditoConsumoLibranzaColpensiones(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		return crearCreditoConsumo(requestContext, authorization, encrypter);
	}

	@POST
	@Path("crearCreditoMicroCredito")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response crearCreditoConsumoMicroCredito(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		return crearCreditoConsumo(requestContext, authorization, encrypter);
	}

	/**
	 * Método comun para hacer el llamado a la operación crearCreditoConsumo.
	 * 
	 * @param requestContext HttpServletRequest
	 * @param authorization  String
	 * @param encrypter      String
	 * @return Response
	 */
	private Response crearCreditoConsumo(HttpServletRequest requestContext, String authorization, String encrypter) {
		Response response = null;
		try {
			GestionCreditoConsumoRS gestionCreditoConsumoRS = servicioDatosCliente.crearCreditoConsumo(authorization,
					encrypter, util.getIpCliente(requestContext));
			response = validResponseErrorSession(gestionCreditoConsumoRS.getCodFuncional(), gestionCreditoConsumoRS);
		} catch (ExceptionLI e) {
			logger.error("Error crearCreditoConsumo INTERNAL_SERVER_ERROR", e);
			if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_401)) {
				response = Response.status(Status.UNAUTHORIZED).build();
			} else {
				response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error crearCreditoConsumo.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@POST
	@Path("confirmarCreditoLibreInversion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response confirmarCreditoConsumoLibreInversion(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		return confirmarCreditoConsumo(requestContext, authorization, encrypter);
	}

	@POST
	@Path("confirmarCreditoCrediagil")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response confirmarCreditoConsumoCrediagil(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		return confirmarCreditoConsumo(requestContext, authorization, encrypter);
	}

	@POST
	@Path("confirmarCreditoLibranza")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response confirmarCreditoConsumoLibranza(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		return confirmarCreditoConsumo(requestContext, authorization, encrypter);
	}

	@POST
	@Path("confirmarCreditoLibranzaColpensiones")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response confirmarCreditoConsumoLibranzaColpensiones(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		return confirmarCreditoConsumo(requestContext, authorization, encrypter);
	}

	@POST
	@Path("confirmarCreditoMicroCredito")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response confirmarCreditoConsumoMicroCredito(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		return confirmarCreditoConsumo(requestContext, authorization, encrypter);
	}

	/**
	 * Método comun para hacer el llamado a la operación confirmarCreditoConsumo.
	 * 
	 * @param requestContext HttpServletRequest
	 * @param authorization  String
	 * @param encrypter      String
	 * @return Response
	 */
	private Response confirmarCreditoConsumo(HttpServletRequest requestContext, String authorization,
			String encrypter) {
		Response response = null;
		try {
			GestionCreditoConsumoRS gestionCreditoConsumoRS = servicioDatosCliente
					.confirmarCreditoConsumo(authorization, encrypter, util.getIpCliente(requestContext));
			response = validResponseErrorSession(gestionCreditoConsumoRS.getCodFuncional(), gestionCreditoConsumoRS);
		} catch (ExceptionLI e) {
			logger.error("Error confirmarCreditoConsumo INTERNAL_SERVER_ERROR", e);
			if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_401)) {
				response = Response.status(Status.UNAUTHORIZED).build();
			} else {
				response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error confirmarCreditoConsumo.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@POST
	@Path("firmarDocumentosCreditoLibreInversion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response firmarDocumentosCreditoConsumoLibreInversion(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		return firmarDocumentosCreditoConsumo(requestContext, authorization, encrypter);
	}

	@POST
	@Path("firmarDocumentosCreditoCrediagil")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response firmarDocumentosCreditoConsumoCrediagil(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		return firmarDocumentosCreditoConsumo(requestContext, authorization, encrypter);
	}

	@POST
	@Path("firmarDocumentosCreditoLibranza")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response firmarDocumentosCreditoConsumoLibranza(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		return firmarDocumentosCreditoConsumo(requestContext, authorization, encrypter);
	}

	@POST
	@Path("firmarDocumentosCreditoLibranzaColpensiones")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response firmarDocumentosCreditoConsumoLibranzaColpensiones(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		return firmarDocumentosCreditoConsumo(requestContext, authorization, encrypter);
	}

	@POST
	@Path("firmarDocumentosCreditoMicroCredito")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response firmarDocumentosCreditoConsumoMicroCredito(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		return firmarDocumentosCreditoConsumo(requestContext, authorization, encrypter);
	}

	/**
	 * Método comun para hacer el llamado a la operación
	 * firmarDocumentosCreditoConsumo.
	 * 
	 * @param requestContext HttpServletRequest
	 * @param authorization  String
	 * @param encrypter      String
	 * @return Response
	 */
	private Response firmarDocumentosCreditoConsumo(HttpServletRequest requestContext, String authorization,
			String encrypter) {
		Response response = null;
		try {
			GestionCreditoConsumoRS gestionCreditoConsumoRS = servicioDatosCliente
					.firmarDocumentosCreditoConsumo(authorization, encrypter, util.getIpCliente(requestContext));
			response = validResponseErrorSession(gestionCreditoConsumoRS.getCodFuncional(), gestionCreditoConsumoRS);
		} catch (ExceptionLI e) {
			logger.error("Error firmarDocumentosCreditoConsumo INTERNAL_SERVER_ERROR", e);
			if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_401)) {
				response = Response.status(Status.UNAUTHORIZED).build();
			} else {
				response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error firmarDocumentosCreditoConsumo.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@POST
	@Path("recalcularCuota")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response recalcularCuota(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {
			GestionCreditoConsumoRS gestionCreditoConsumoRS = servicioDatosCliente.recalculoCuota(encrypter,
					util.getIpCliente(requestContext));
			response = Response.status(Status.OK).entity(json.toJson(gestionCreditoConsumoRS)).build();

		} catch (ExceptionLI e) {

			logger.error("Error recalcularCuota INTERNAL_SERVER_ERROR", e);
			if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_401)) {
				response = Response.status(Status.UNAUTHORIZED).build();
			} else {
				response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error recalcularCuota.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@POST
	@Path("recalcularSeguro")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response recalcularSeguro(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {
			GestionCreditoConsumoRS gestionCreditoConsumoRS = servicioDatosCliente.recalcularSeguro(encrypter,
					util.getIpCliente(requestContext));
			response = Response.status(Status.OK).entity(json.toJson(gestionCreditoConsumoRS)).build();
		} catch (ExceptionLI e) {
			logger.error("Error recalcularCuota INTERNAL_SERVER_ERROR", e);
			if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_401)) {
				response = Response.status(Status.UNAUTHORIZED).build();
			} else {
				response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error recalcularCuota.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@POST
	@Path("finalizarExp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response finalizarExp(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {

			String resp = servicioDatosCliente.finalizarExp(encrypter, util.getIpCliente(requestContext));
			response = Response.status(Status.OK).entity(json.toJson(resp)).build();

		} catch (ExceptionLI e) {

			logger.error("Error finalizarExp INTERNAL_SERVER_ERROR", e);
			if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_401)) {
				response = Response.status(Status.UNAUTHORIZED).build();
			} else {
				response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error finalizarExp.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@POST
	@Path("enviarCorreo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response enviarCorreo(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {
			String[] respuesta = servicioDatosCliente.enviarCorreo(authorization, encrypter,
					util.getIpCliente(requestContext));
			response = Response.status(Status.OK).entity(json.toJson(respuesta)).build();
		} catch (ExceptionLI e) {
			logger.error("Error enviarCorreo INTERNAL_SERVER_ERROR", e);
			if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_401)) {
				response = Response.status(Status.UNAUTHORIZED).build();
			} else {
				response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error enviarCorreo.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@POST
	@Path("validacionPreguntas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response validacionPreguntas(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {

			ValidacionPreguntasRS validacionPreguntasRS = servicioDatosCliente.validacionPreguntas(authorization,
					encrypter, util.getIpCliente(requestContext));
			response = validResponseErrorSession(validacionPreguntasRS.getCodFuncional(), validacionPreguntasRS);
		} catch (ExceptionLI e) {
			logger.error("Error solicitudCreditoConsumo Autenticacion", e);
			if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_401)) {
				response = Response.status(Status.UNAUTHORIZED).build();
			} else {
				response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {

			logger.error("Error solicitudCreditoConsumo. INTERNAL_SERVER_ERROR", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	/**
	 * Metodo para saber la salud del APi y si esta o no disponible
	 * 
	 * @return respuesta al consumo
	 */
	@GET
	@Path("health")
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response health() {
		return Response.status(Status.OK).entity("Health OK").build();
	}

	/**
	 * Metodo para descargar los documentos del cliente cuando no tiene correo o
	 * fallo el envio
	 * 
	 * @param requestContext contexto de la peticion
	 * @param authorization  JWT de autorizacion
	 * @param encrypter      Carga util para el envio
	 * @return Respuesta del frlujo
	 * @throws ExceptionLI control de algun problema presentado
	 */
	@POST
	@Path("descargarDocumentos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response descargarDocumentosBienvenida(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {
			String[] resp = servicioDatosCliente.descargarDocumentosBienvenida(authorization, encrypter,
					util.getIpCliente(requestContext));
			response = Response.status(Status.OK).entity(json.toJson(resp)).build();
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error descargando documentos.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	/**
	 * Metodo para cosultar el mecanismo de enrolamiento del cliente
	 * 
	 * @param requestContext contexto de la solicitud
	 * @param authorization  JWT de autorizacion
	 * @param encrypter      CargaUtil para el llamado de servicios y control del
	 *                       flujo
	 * @return Respuesta al proceso
	 * @throws ExceptionLI excepciones del proceso
	 */
	@POST
	@Path("validarAuthFuerte")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response consultarAuthFuerte(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {
			AutenticacionFuerteRS resp = servicioDatosCliente.consultarAuthFuerte(authorization, encrypter,
					util.getIpCliente(requestContext));
			response = validResponseErrorSession(resp.getCodFuncional(), resp);
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error validarAuthFuerte.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	/**
	 * Metodo para generar clave de clientes
	 * 
	 * @param requestContext contexto de la solicitud
	 * @param authorization  JWT de autorizacion
	 * @param encrypter      CargaUtil para el llamado de servicios y control del
	 *                       flujo
	 * @return Respuesta al proceso
	 * @throws ExceptionLI excepciones del proceso
	 */
	@POST
	@Path("generacionClave")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response generacionClave(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {
			GeneracionClaveRS resp = servicioDatosCliente.generacionClave(authorization, encrypter,
					util.getIpCliente(requestContext));
			response = validResponseErrorSession(resp.getCodFuncional(), resp);
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error generacionClave.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	/**
	 * Metodo para validar claves de cliente
	 * 
	 * @param requestContext contexto de la solicitud
	 * @param authorization  JWT de autorizacion
	 * @param encrypter      CargaUtil para el llamado de servicios y control del
	 *                       flujo
	 * @return Respuesta al proceso
	 * @throws ExceptionLI excepciones del proceso
	 */
	@POST
	@Path("validacionClave")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response validacionClave(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {
			ValidacionClaveRS resp = servicioDatosCliente.validacionClave(authorization, encrypter,
					util.getIpCliente(requestContext));
			response = validResponseErrorSession(resp.getCodFuncional(), resp);
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error validacionClave.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@POST
	@Path("guardarPaso")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response guardarPaso(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {

			String resp = servicioDatosCliente.finalizarExp(encrypter, util.getIpCliente(requestContext));
			response = Response.status(Status.OK).entity(json.toJson(resp)).build();

		} catch (ExceptionLI e) {

			logger.error("Error guardarPaso INTERNAL_SERVER_ERROR", e);
			if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_401)) {
				response = Response.status(Status.UNAUTHORIZED).build();
			} else {
				response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {
			// llamados entre api y a la DB que pueden generar excepciones no conocidas, se
			// procesan con la generica para controlar y procesar el error
			logger.error("Error guardarPaso.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	/**
	 * Metodo para generar el token del contenedor
	 * 
	 * @param requestContext contexto de la solicitud
	 * @param encrypter      CargaUtil para el llamado de servicios y control del
	 *                       flujo
	 * @return Respuesta al proceso
	 * @throws ExceptionLI excepciones del proceso
	 */
	@POST
	@Path("generarTokenContenedor")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response generarTokenContenedor(@Context HttpServletRequest requestContext, String encrypter)
			throws ExceptionLI {

		AutenticacionCont param = json.fromJson(encrypter, AutenticacionCont.class);
		String respuesta = servicioDatosCliente.generarTokenContenedor(param);
		return Response.status(Status.OK).entity(respuesta).build();
	}

	@POST
	@Path("validarSesion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response validarSesion(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter)  {
		AutenticacionRS respuesta = null;
		try {
			respuesta = servicioAutenticacion.validarSesion(authorization, encrypter);
			respuesta.setIpCliente(util.getIpCliente(requestContext));
			return Response.status(Status.OK).entity(json.toJson(respuesta)).build();
		} catch (ExceptionLI e) {
			mensaje = ConstantesLI.MSG_VAL_SESSION + e.getMensaje();
			logger.error(mensaje, e);
			return Response.status(Status.UNAUTHORIZED).entity(e.getMensaje()).build();
		}
	}

	@POST
	@Path("recibirTokenInicial")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response recibirTokenInicial(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		AutenticacionRS respuesta = null;
		try {
			respuesta = servicioAutenticacion.recibirTokenInicial(authorization, encrypter);
			respuesta.setIpCliente(util.getIpCliente(requestContext));
			return Response.status(Status.OK).entity(respuesta).build();
		} catch (ExceptionLI e) {
			mensaje = ConstantesLI.MSG_VAL_SESSION + e.getMensaje();
			logger.error(mensaje, e);
			if (e.getCodigo().equalsIgnoreCase(ConstantesLI.RET303)) {
				return Response.status(Status.SEE_OTHER).entity(e.getMensaje()).build();
			} else {
				return Response.status(Status.UNAUTHORIZED).entity(e.getMensaje()).build();
			}
		} catch (MalformedClaimException | ValidacionException | IOException | GeneralSecurityException e) {
			mensaje = ConstantesLI.MSG_VAL_SESSION + e.getMessage();
			logger.error(mensaje, e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(json.toJson(e.getMessage())).build();
		}
	}

	@POST
	@Path("cerrarSesion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response cerrarSesion(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		AutenticacionRS respuesta = null;
		try {
			respuesta = servicioAutenticacion.cerrarSesion(authorization, encrypter);
			respuesta.setIpCliente(util.getIpCliente(requestContext));
			return Response.status(Status.OK).entity(respuesta).build();
		} catch (ExceptionLI e) {
			mensaje = ConstantesLI.MSG_VAL_SESSION + e.getMensaje();
			logger.error(mensaje, e);
			if (e.getCodigo().equalsIgnoreCase(ConstantesLI.RET303)) {
				return Response.status(Status.SEE_OTHER).entity(e.getMensaje()).build();
			} else {
				return Response.status(Status.UNAUTHORIZED).entity(e.getMensaje()).build();
			}
		}
	}

	@POST
	@Path("terminarSesion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response terminarSesion(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization) throws ExceptionLI, MalformedClaimException, GeneralSecurityException, IOException {
		AutenticacionRS respuesta = null;
		try {
			respuesta = servicioAutenticacion.terminarSesion(authorization);
			respuesta.setIpCliente(util.getIpCliente(requestContext));
			return Response.status(Status.OK).entity(json.toJson(respuesta)).build();
		} catch (ExceptionLI e) {
			mensaje = ConstantesLI.MSG_VAL_SESSION + e.getMensaje();
			logger.error(mensaje, e);
			return Response.status(Status.UNAUTHORIZED).entity(e.getMensaje()).build();
		}
	}

	@POST
	@Path("abrirSesion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response abrirSesion(@Context HttpServletRequest requestContext,
								   @HeaderParam("Authorization") String authorization) throws ExceptionLI, MalformedClaimException {
		AutenticacionRS respuesta = null;
		try {
			respuesta = servicioAutenticacion.abrirSesion(authorization);
			respuesta.setIpCliente(util.getIpCliente(requestContext));
			return Response.status(Status.OK).entity(json.toJson(respuesta)).build();
		} catch (ExceptionLI e) {
			mensaje = ConstantesLI.MSG_VAL_SESSION + e.getMensaje();
			logger.error(mensaje, e);
			return Response.status(Status.UNAUTHORIZED).entity(e.getMensaje()).build();
		}
	}

	private Response validResponseErrorSession(String codError, Object object){
		if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(codError)) {
				return  Response.status(Status.UNAUTHORIZED).entity(json.toJson(object)).build();
		}else{
				return Response.status(Status.OK).entity(json.toJson(object)).build();
		}
	}


}
