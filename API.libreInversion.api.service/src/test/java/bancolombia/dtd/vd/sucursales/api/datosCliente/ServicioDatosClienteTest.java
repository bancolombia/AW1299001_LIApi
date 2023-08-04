/**
 *
 */
package bancolombia.dtd.vd.sucursales.api.datosCliente;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.client.HttpResponseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.bcol.vtd.lib.comunes.exception.ValidacionException;
import com.google.gson.Gson;
import com.grupobancolombia.ents.common.genericexception.v2.GenericException;
import com.grupobancolombia.intf.canal.movil.generaciontoken.v1.Respuesta;
import com.grupobancolombia.intf.canal.movil.generaciontoken.v1.SolicitarTokenResponse;
import com.grupobancolombia.intf.canal.movil.generaciontoken.v1.ValidarTokenResponse;
import com.grupobancolombia.intf.seguridad.autenticacion.gestionautenticacionfuerte.enlace.v1.BusinessExceptionMsg;
import com.grupobancolombia.intf.seguridad.autenticacion.gestionautenticacionfuerte.enlace.v1.SystemExceptionMsg;
import com.grupobancolombia.intf.seguridad.autenticacion.gestionautenticacionfuerte.v1.BusinessException;
import com.grupobancolombia.intf.seguridad.autenticacion.gestionautenticacionfuerte.v1.SystemException;
import com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.v2.AutenticarClienteOTPODAResponse;
import com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.v2.AutenticarClienteOTPSoftokenResponse;
import com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.v2.GenerarEnviarOTPODAResponse;
import com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.v2.GenerarOTPODAResponse;

import bancolombia.dtd.vd.li.api.autenticacion.ServicioAutenticacion;
import bancolombia.dtd.vd.li.commons.exception.ExceptionLI;
import bancolombia.dtd.vd.li.commons.util.CargadorPropiedades;
import bancolombia.dtd.vd.li.dto.autenticacion.AutenticacionRS;
import bancolombia.dtd.vd.li.dto.mensajesError.ErrorRequestDTO;
import bancolombia.dtd.vd.li.dto.mensajesError.ErrorResponseDTO;
import bancolombia.dtd.vd.li.dto.mensajesError.MensajeFuncionalDTO;

import bancolombia.dtd.vd.li.dto.proxy.aprobacionesMotor.EstudioCreditoConsumoRS;
import bancolombia.dtd.vd.li.dto.proxy.authFuerte.AutenticacionFuerteRQ;
import bancolombia.dtd.vd.li.dto.proxy.authFuerte.AutenticacionFuerteRS;
import bancolombia.dtd.vd.li.dto.proxy.clave.GeneracionClaveRQ;
import bancolombia.dtd.vd.li.dto.proxy.clave.GeneracionClaveRS;
import bancolombia.dtd.vd.li.dto.proxy.clave.ValidacionClaveRQ;
import bancolombia.dtd.vd.li.dto.proxy.clave.ValidacionClaveRS;
import bancolombia.dtd.vd.li.dto.proxy.codAsesor.ConsultarDatosSucursalRQ;
import bancolombia.dtd.vd.li.dto.proxy.codAsesor.ConsultarDatosSucursalRS;
import bancolombia.dtd.vd.li.dto.proxy.consultarProductoDepositos.ConsultarProductoDepositosRQ;
import bancolombia.dtd.vd.li.dto.proxy.consultarProductoDepositos.ConsultarProductoDepositosRS;
import bancolombia.dtd.vd.li.dto.proxy.consultarProductoDepositos.ConsultarProductoDepositosRSApi;
import bancolombia.dtd.vd.li.dto.proxy.consultarProductoDepositos.Cuentas;
import bancolombia.dtd.vd.li.dto.proxy.datosCliente.ConsultarDatosClienteRQ;
import bancolombia.dtd.vd.li.dto.proxy.datosCliente.ConsultarDatosClienteRS;
import bancolombia.dtd.vd.li.dto.proxy.datosCliente.RecuperarDatosUbicacionClienteResponse;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRQ;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRS;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.OfertaDigital;
import bancolombia.dtd.vd.li.dto.proxy.gestionPrecalculadoCliente.GestionPrecalculadoClienteRQ;
import bancolombia.dtd.vd.li.dto.proxy.gestionPrecalculadoCliente.GestionPrecalculadoClienteRS;
import bancolombia.dtd.vd.li.dto.proxy.gestionPrecalculadoCliente.ProductoPreaprobadoBasicoRS;
import bancolombia.dtd.vd.li.dto.proxy.validacionpreguntas.ValidacionPreguntasRS;
import bancolombia.dtd.vd.sucursales.api.datosCliente.util.CallRestServiceBack;
import bancolombia.dtd.vd.sucursales.api.datosCliente.util.ConstantesLI;
import suc.lib.autenticarClienteOTP.ServicioAutenticarClienteOTP;
import suc.lib.autenticarClienteOTP.util.AutenticarClienteOTPRQ;
import suc.lib.generacionToken.ServicioGeneracionToken;
import suc.lib.generacionToken.util.GeneracionTokenRQ;
import suc.lib.generarOTP.ServicioGenerarOTP;
import suc.lib.generarOTP.util.AutenticacionOTPRQ;
import suc.lib.gestionauthfuerte.ServicioGestionAutenticacionFuerte;
import suc.lib.gestionauthfuerte.util.PeticionGestionAutenticacionFuerte;
import suc.lib.gestionauthfuerte.util.RespuestaGestionAutenticacionFuerte;

/**
 * @author cgallego
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ServicioDatosCliente.class })

public class ServicioDatosClienteTest {

	@Mock
	CallRestServiceBack callRestServiceBack;

	@Mock
	ServicioGestionAutenticacionFuerte servicioGestionAutenticacionFuerte;

	@Mock
	ServicioGeneracionToken servicioGeneracionToken;

	@Mock
	ServicioAutenticarClienteOTP servicioAutenticarClienteOTP;

	@Mock
	ServicioGenerarOTP servicioGenerarOTP;

	@Mock
	ServicioAutenticacion servicioAutenticacion;

	private ServicioDatosCliente service;

	@Mock
	private ServicioDatosCliente serviceMail;


	private static Map<String, String> propiedades;

	private AutenticacionRS auth;

	@Before
	public void init() throws ExceptionLI, IOException {
		service = new ServicioDatosCliente();
		service.callRestServiceBack = callRestServiceBack;
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);
		auth = new AutenticacionRS();
		auth.setTokenNuevo("RRRR");
		Mockito.when(callRestServiceBack.guardarDatosDB(Mockito.any(), Mockito.any())).thenReturn("OK");
		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString())).thenReturn(auth);
		service.setAutenticacion(servicioAutenticacion);
	}



	@Test
	public void testConsultarInfoAsesorSucursal() throws Exception {
		ConsultarDatosSucursalRS consultaDatosSucursal = new ConsultarDatosSucursalRS();

		consultaDatosSucursal.setCodigoCIFAsesor("31105");
		consultaDatosSucursal.setCodigoCIFGerente("55555");
		consultaDatosSucursal.setCodigoCIFSucursal("00005");
		consultaDatosSucursal.setCodigoCRMAsesor("44444");
		consultaDatosSucursal.setCodigoCRMGerente("55555");
		consultaDatosSucursal.setCodigoCRMSucursal("00000066");
		consultaDatosSucursal.setNumeroIdAsesor("1020452408");
		consultaDatosSucursal.setNumeroIdGerente("43583347");
		consultaDatosSucursal.setTipoDocAsesor("FS001");
		consultaDatosSucursal.setTipoDocGerente("FS001");
		when(callRestServiceBack.proxyDatosComercialesSucursal(Mockito.any(ConsultarDatosSucursalRQ.class),
				Mockito.any())).thenReturn(consultaDatosSucursal);

		ConsultarDatosSucursalRS respuesta = service.consultarAsesor("", ConstantesTest.CARGAUTILINFOASESORCEDULA,
				"127.0.0.1");

		ConsultarDatosSucursalRS respuestaEsperada = new ConsultarDatosSucursalRS();
		respuestaEsperada.setCodigoCIFAsesor("");
		respuestaEsperada.setCodigoCIFSucursal("00006");
		respuestaEsperada.setCodigoCRMSucursal("00000066");
		respuestaEsperada.setNumeroIdAsesor("");
		respuestaEsperada.setNumeroIdGerente("numeroIdgerente");
		respuestaEsperada.setTipoDocAsesor("FS001");
		respuestaEsperada.setTipoDocGerente("FS001");
		respuestaEsperada.setCodigoCIFGerente("");
		respuestaEsperada.setCodigoCRMAsesor("");
		respuestaEsperada.setCodigoCRMGerente("");

		assertNotNull(respuesta);
		assertThat(respuesta.getCodigoCRMSucursal(), is(respuestaEsperada.getCodigoCRMSucursal()));
	}


	@Test
	public void testConsultarInfoAsesorExc() throws Exception {
		ConsultarDatosSucursalRS respuestaer = new ConsultarDatosSucursalRS();
		respuestaer.setCodError("500");
		respuestaer.setDescError("Error en consumo de servicio Consulta Datos Comerciales Sucursal");

		when(callRestServiceBack.proxyDatosComercialesSucursal(Mockito.any(ConsultarDatosSucursalRQ.class),
				Mockito.any())).thenReturn(respuestaer);
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("DEFAULT-500");
		errorResponseDTO
				.setDescripcionTecnica("En este momento el sistema no esta disponible. Por favor intenta mas tarde.");
		mensajeFuncional.setCodigoFuncional("DEFAULT-500");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no esta disponible. Por favor intenta mas tarde");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		ConsultarDatosSucursalRS respuesta = service.consultarAsesor("", ConstantesTest.CARGAUTILINFOASESORCEDULA,
				"127.0.0.1");
		assertNotNull(respuesta);
		assertThat(respuesta.getCodFuncional(), is("DEFAULT-500"));
	}


	@Test
	public void testConsultarInfoAsesorSessionExpirada() throws Exception {
		ExceptionLI exc = new ExceptionLI("AUTH-401", "La session ha expirado.");
		when(servicioAutenticacion.validarSesion(anyString(), anyString())).thenThrow(exc);
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("AUTH-401");
		errorResponseDTO
				.setDescripcionTecnica("La session ha expirado.");
		mensajeFuncional.setCodigoFuncional("AUTH-401");
		mensajeFuncional
				.setDescripcionFuncional("La session ha expirado. Ingrese nuevamente");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		doReturn(errorResponseDTO).when(callRestServiceBack).errorService(any(), anyString(), anyString());

		ConsultarDatosSucursalRS respuesta = service.consultarAsesor("", ConstantesTest.CARGAUTILINFOASESORCEDULA,
				"127.0.0.1");
		assertNotNull(respuesta);
		assertThat(respuesta.getCodFuncional(), is("AUTH-401"));
	}


	@Test
	public void testConsultarCuentas() throws ExceptionLI, IOException {
		ConsultarProductoDepositosRS respuesta = new ConsultarProductoDepositosRS();
		ConsultarProductoDepositosRSApi respuestaApi = new ConsultarProductoDepositosRSApi();

		List<Cuentas> cuentas = new ArrayList<Cuentas>();
		Cuentas cuenta = new Cuentas();
		cuenta.setCodigoMoneda("COP");
		cuenta.setCodigoOficina("00001");
		cuenta.setCuentaPermiteCreditos("N");
		cuenta.setCuentaPermiteDebitos("N");
		cuenta.setFechaAperturaCuenta("2017-11-07-05:00");
		cuenta.setFechaCancelacionCuenta("0001-01-01-05:00");
		cuenta.setIndicadorCuentaCancelada("N");
		List<String> marcas = new ArrayList<String>();
		marcas.add("Permite ser Pagadora");
		marcas.add("Permite Débito Automático");

		cuenta.setMarcas(marcas);
		cuenta.setNumeroCuenta("133211001016932");
		cuenta.setNumeroDiasInactividadCuenta("1");
		cuenta.setRelacionClienteCuenta("T");
		cuenta.setSaldoBloqueado("0.00");
		cuenta.setSaldoCanjeCuenta("0.00");
		cuenta.setSaldoCanjeInicioDia("0.00");
		cuenta.setSaldoCuentaPorCobrar("0.00");
		cuenta.setSaldoDisponibleCuenta("0.00");
		cuenta.setSaldoEfectivoCuenta("0.00");
		cuenta.setSaldoEfectivoInicioDia("0.00");
		cuenta.setTipoCuenta("D");
		cuenta.setValorCupoSobregiro("0.00");
		cuentas.add(cuenta);

		cuenta = new Cuentas();

		cuenta.setCodigoMoneda("COP");
		cuenta.setCodigoOficina("00001");
		cuenta.setCuentaPermiteCreditos("Y");
		cuenta.setCuentaPermiteDebitos("Y");
		cuenta.setFechaAperturaCuenta("2017-11-07-05:00");
		cuenta.setFechaCancelacionCuenta("0001-01-01-05:00");
		cuenta.setIndicadorCuentaCancelada("N");
		marcas = new ArrayList<String>();
		marcas.add("Permite ser Pagadora 2");
		marcas.add("Permite Débito Automático 2");

		cuenta.setMarcas(marcas);

		cuenta.setNumeroCuenta("234324155501693");
		cuenta.setNumeroDiasInactividadCuenta("1");
		cuenta.setRelacionClienteCuenta("T");
		cuenta.setSaldoBloqueado("0.00");
		cuenta.setSaldoCanjeCuenta("0.00");
		cuenta.setSaldoCanjeInicioDia("0.00");
		cuenta.setSaldoCuentaPorCobrar("0.00");
		cuenta.setSaldoDisponibleCuenta("0.00");
		cuenta.setSaldoEfectivoCuenta("0.00");
		cuenta.setSaldoEfectivoInicioDia("0.00");
		cuenta.setTipoCuenta("D");
		cuenta.setValorCupoSobregiro("0.00");
		cuentas.add(cuenta);
		respuesta.setCuentas(cuentas);

		when(callRestServiceBack.proxyConsultaCuentas(Mockito.any(ConsultarProductoDepositosRQ.class), Mockito.any()))
				.thenReturn(respuesta);

		respuestaApi = service.consultaCuentas("", ConstantesTest.CARGAUTILCUENTAS, "127.0.0.1");
		assertNotNull(respuestaApi);
		assertThat(respuestaApi.getCuentas().get(0).getDescripcion(), is("234324155501693"));
	}

	@Test
	public void testConsultarCuentasNoExistenCuentasQuePermitenDebitoYCreditos() throws ExceptionLI, IOException {
		ConsultarProductoDepositosRS respuesta = new ConsultarProductoDepositosRS();
		ConsultarProductoDepositosRSApi respuestaApi = null;
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("CPD-600");
		errorResponseDTO.setDescripcionTecnica("600No existen cuentas asociadas");
		mensajeFuncional.setCodigoFuncional("CPD-600");
		mensajeFuncional.setDescripcionFuncional("El cliente no posee cuentas válidas para desembolsar");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		List<Cuentas> cuentas = new ArrayList<>();
		Cuentas cuenta = new Cuentas();
		cuenta.setCodigoMoneda("COP");
		cuenta.setCodigoOficina("00001");
		cuenta.setCuentaPermiteCreditos("N");
		cuenta.setCuentaPermiteDebitos("N");
		cuenta.setFechaAperturaCuenta("2017-11-07-05:00");
		cuenta.setFechaCancelacionCuenta("0001-01-01-05:00");
		cuenta.setIndicadorCuentaCancelada("N");
		List<String> marcas = new ArrayList<String>();
		marcas.add("Permite ser Pagadora");
		marcas.add("Permite Débito Automático");

		cuenta.setMarcas(marcas);

		cuenta.setNumeroCuenta("133211001016932");
		cuenta.setNumeroDiasInactividadCuenta("1");
		cuenta.setRelacionClienteCuenta("T");
		cuenta.setSaldoBloqueado("0.00");
		cuenta.setSaldoCanjeCuenta("0.00");
		cuenta.setSaldoCanjeInicioDia("0.00");
		cuenta.setSaldoCuentaPorCobrar("0.00");
		cuenta.setSaldoDisponibleCuenta("0.00");
		cuenta.setSaldoEfectivoCuenta("0.00");
		cuenta.setSaldoEfectivoInicioDia("0.00");
		cuenta.setTipoCuenta("D");
		cuenta.setValorCupoSobregiro("0.00");
		cuentas.add(cuenta);

		cuenta = new Cuentas();
		cuenta.setCodigoMoneda("COP");
		cuenta.setCodigoOficina("00001");
		cuenta.setCuentaPermiteCreditos("N");
		cuenta.setCuentaPermiteDebitos("N");
		cuenta.setFechaAperturaCuenta("2017-11-07-05:00");
		cuenta.setFechaCancelacionCuenta("0001-01-01-05:00");
		cuenta.setIndicadorCuentaCancelada("N");
		marcas = new ArrayList<String>();
		marcas.add("Permite ser Pagadora 2");
		marcas.add("Permite Débito Automático 2");

		cuenta.setMarcas(marcas);

		cuenta.setNumeroCuenta("234324155501693");
		cuenta.setNumeroDiasInactividadCuenta("1");
		cuenta.setRelacionClienteCuenta("T");
		cuenta.setSaldoBloqueado("0.00");
		cuenta.setSaldoCanjeCuenta("0.00");
		cuenta.setSaldoCanjeInicioDia("0.00");
		cuenta.setSaldoCuentaPorCobrar("0.00");
		cuenta.setSaldoDisponibleCuenta("0.00");
		cuenta.setSaldoEfectivoCuenta("0.00");
		cuenta.setSaldoEfectivoInicioDia("0.00");
		cuenta.setTipoCuenta("D");
		cuenta.setValorCupoSobregiro("0.00");
		cuentas.add(cuenta);
		respuesta.setCuentas(cuentas);

		when(callRestServiceBack.proxyConsultaCuentas(Mockito.any(ConsultarProductoDepositosRQ.class), Mockito.any()))
				.thenReturn(respuesta);
		respuestaApi = service.consultaCuentas("", ConstantesTest.CARGAUTILCUENTAS, "127.0.0.1");
		System.out.println("Respuesta de testConsultarCuentasNoExistenCuentasQuePermitenDebitoYCreditos:"
				+ new Gson().toJson(respuestaApi));
		assertNotNull(respuestaApi);
		assertThat(respuestaApi.getCodFuncional(), is("CPD-600"));
	}

	@Test
	public void testConsultarCuentasExc() throws ExceptionLI, IOException {
		ExceptionLI respuesta = new ExceptionLI("500", "Prueba Error");
		ConsultarProductoDepositosRSApi respuestaApi = new ConsultarProductoDepositosRSApi();

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("CPD-500");
		errorResponseDTO.setDescripcionTecnica("No se encontro codError en API Errores");
		mensajeFuncional.setCodigoFuncional("CPD-500");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no esta disponible. Por favor intenta mas tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);
		when(callRestServiceBack.proxyConsultaCuentas(Mockito.any(ConsultarProductoDepositosRQ.class), Mockito.any()))
				.thenThrow(respuesta);

		respuestaApi = service.consultaCuentas("", ConstantesTest.CARGAUTILCUENTAS, "127.0.0.1");
		assertNotNull(respuestaApi);
		assertThat(respuestaApi.getCodFuncional(), is("CPD-500"));
	}

	@Test
	public void testConsultarCuentasSession() throws Exception {
		ExceptionLI exc = new ExceptionLI("AUTH-401", "La session ha expirado.");
		when(servicioAutenticacion.validarSesion(anyString(), anyString())).thenThrow(exc);
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("AUTH-401");
		errorResponseDTO
				.setDescripcionTecnica("La session ha expirado.");
		mensajeFuncional.setCodigoFuncional("AUTH-401");
		mensajeFuncional
				.setDescripcionFuncional("La session ha expirado. Ingrese nuevamente");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		doReturn(errorResponseDTO).when(callRestServiceBack).errorService(any(), anyString(), anyString());
		ConsultarProductoDepositosRSApi respuestaApi = new ConsultarProductoDepositosRSApi();
		respuestaApi = service.consultaCuentas("", ConstantesTest.CARGAUTILCUENTAS, "127.0.0.1");
		assertNotNull(respuestaApi);
		assertThat(respuestaApi.getCodError(), is("AUTH-401"));
	}

	@Test
	public void testConsultaDatosUbicacion() throws ExceptionLI, IOException {
		ConsultarDatosClienteRS respuestaRS;

		when(callRestServiceBack.llamadoBackDatosCliente(Mockito.any(ConsultarDatosClienteRQ.class), Mockito.any(),
				Mockito.anyString())).thenReturn(ConstantesTest.RESPUESTADATOSCLIENTE);

		respuestaRS = service.consultaDatosUbicacion("", ConstantesTest.CARGAUTILINFOASESORCEDULA, "127.0.0.1");
		assertNotNull(respuestaRS);
		assertThat(respuestaRS.getDirecciones().get(0).getCorreoElectronico(), is("cgallego@co.ibm.com"));
	}

	@Test
	public void testConsultaDatosUbicacionExc() throws ExceptionLI, IOException {
		ExceptionLI respuestaErr = new ExceptionLI("500","");
		ConsultarDatosClienteRS respuestaRS;

		when(callRestServiceBack.llamadoBackDatosCliente(Mockito.any(ConsultarDatosClienteRQ.class), Mockito.any(),
				Mockito.anyString())).thenThrow(respuestaErr);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("DEFAULT-500");
		errorResponseDTO.setDescripcionTecnica("No se encontrá codError en API Errores");
		mensajeFuncional.setCodigoFuncional("DEFAULT-500");
		mensajeFuncional.setDescripcionFuncional(
				"En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		respuestaRS = service.consultaDatosUbicacion("", ConstantesTest.CARGAUTILINFOASESORCEDULA, "127.0.0.1");
		assertNotNull(respuestaRS);
		assertThat(respuestaRS.getCodFuncional(), is("DEFAULT-500"));
	}


	@Test
	public void testConsultarDatosUbicacionSession() throws Exception {
		ExceptionLI exc = new ExceptionLI("AUTH-401", "La session ha expirado.");
		when(servicioAutenticacion.validarSesion(anyString(), anyString())).thenThrow(exc);
		ConsultarDatosClienteRS respuestaRS;
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("AUTH-401");
		errorResponseDTO
				.setDescripcionTecnica("La session ha expirado.");
		mensajeFuncional.setCodigoFuncional("AUTH-401");
		mensajeFuncional
				.setDescripcionFuncional("La session ha expirado. Ingrese nuevamente");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		doReturn(errorResponseDTO).when(callRestServiceBack).errorService(any(), anyString(), anyString());
		respuestaRS = service.consultaDatosUbicacion("", ConstantesTest.CARGAUTILINFOASESORCEDULA, "127.0.0.1");
		assertNotNull(respuestaRS);
		assertThat(respuestaRS.getCodFuncional(), is("AUTH-401"));
	}


	@Test
	public void testNoSeEncuentranDatosUbicacion() throws ExceptionLI, IOException {
		ConsultarDatosClienteRS respuestaRS;
		when(callRestServiceBack.llamadoBackDatosCliente(Mockito.any(ConsultarDatosClienteRQ.class), Mockito.any(),
				Mockito.anyString())).thenReturn(null);

		ErrorResponseDTO res = new ErrorResponseDTO();
		res.setCodigoError("CDC-800");
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		mensajeFuncional.setCodigoFuncional("CLS-005");
		mensajeFuncional.setDescripcionFuncional("No se puede continuar con la experiencia.");
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		listaMensajes.add(mensajeFuncional);
		res.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(res);

		respuestaRS = service.consultaDatosUbicacion("", ConstantesTest.CARGAUTILINFOASESORCEDULA, "127.0.0.1");
		assertThat(respuestaRS.getCodError(), is("CDC-800"));
	}

	@Test
	public void testConsultaPrecalculadoCredito() throws ExceptionLI, IOException {
		GestionPrecalculadoClienteRS respuesta = new GestionPrecalculadoClienteRS();
		ProductoPreaprobadoBasicoRS respuestaApi = new ProductoPreaprobadoBasicoRS();

		respuesta.setIdentificadorBancoFilial("9");
		respuesta.setNumeroDocumento("111111111111");
		List<ProductoPreaprobadoBasicoRS> productosPreaprobadosBasico = new ArrayList<>();
		ProductoPreaprobadoBasicoRS producto = new ProductoPreaprobadoBasicoRS();
		producto.setCodigoProducto("14");
		producto.setCupo("18785196");
		producto.setEstadoProducto("D");
		producto.setFechaVigencia("2019-09-07-05:00");
		producto.setGrupoProducto("3");
		producto.setMoneda("COP");
		producto.setNombreProducto("LIBRE INVERSION");
		productosPreaprobadosBasico.add(producto);

		producto = new ProductoPreaprobadoBasicoRS();
		producto.setCodigoProducto("14");
		producto.setCupo("86867073");
		producto.setEstadoProducto("D");
		producto.setFechaVigencia("2019-04-20-05:00");
		producto.setGrupoProducto("3");
		producto.setMoneda("COP");
		producto.setNombreProducto("tlibre inversion");
		productosPreaprobadosBasico.add(producto);

		producto = new ProductoPreaprobadoBasicoRS();
		producto.setCodigoProducto("12");
		producto.setCupo("396817");
		producto.setEstadoProducto("D");
		producto.setFechaVigencia("2019-01-23-05:00");
		producto.setGrupoProducto("3");
		producto.setMoneda("COP");
		producto.setNombreProducto("TARJETA DE CREDITO");
		producto.setPrioridadProducto("1");
		productosPreaprobadosBasico.add(producto);

		respuesta.setProductosPreaprobadosBasico(productosPreaprobadosBasico);
		respuesta.setRespuestaPrecalculado("S");
		respuesta.setTipoDocumento("FS001");

		when(callRestServiceBack.proxyConsultaPrecalculadoCliente(Mockito.any(GestionPrecalculadoClienteRQ.class),
				Mockito.any())).thenReturn(respuesta);

		respuestaApi = service.consultaPrecalculadoCredito("", ConstantesTest.CARGAUTILINFOASESORCEDULA, "127.0.0.1");

		ProductoPreaprobadoBasicoRS productoEsperado = new ProductoPreaprobadoBasicoRS();
		productoEsperado.setCodigoProducto("14");
		productoEsperado.setCupo("18785196");
		productoEsperado.setEstadoProducto("D");
		productoEsperado.setFechaVigencia("2019-09-07-05:00");
		productoEsperado.setGrupoProducto("3");
		productoEsperado.setMoneda("COP");
		productoEsperado.setNombreProducto("LIBRE INVERSION");

		assertThat(respuestaApi.getNombreProducto(), is(productoEsperado.getNombreProducto()));
	}

	@Test
	public void testNoExistePrecalculadoCredito() throws Exception {
		GestionPrecalculadoClienteRS respuesta = new GestionPrecalculadoClienteRS();
		ProductoPreaprobadoBasicoRS respuestaApi = new ProductoPreaprobadoBasicoRS();
		AutenticacionRS respuestaAutenticacion = new AutenticacionRS();

		respuesta.setIdentificadorBancoFilial("9");
		respuesta.setNumeroDocumento("111111111111");
		List<ProductoPreaprobadoBasicoRS> productosPreaprobadosBasico = new ArrayList<>();
		ProductoPreaprobadoBasicoRS producto = new ProductoPreaprobadoBasicoRS();
		producto.setCodigoProducto("10");
		producto.setCupo("18785196");
		producto.setEstadoProducto("D");
		producto.setFechaVigencia("2019-09-07-05:00");
		producto.setGrupoProducto("3");
		producto.setMoneda("COP");
		producto.setNombreProducto("LIBRE INVERSION");
		productosPreaprobadosBasico.add(producto);

		producto = new ProductoPreaprobadoBasicoRS();
		producto.setCodigoProducto("11");
		producto.setCupo("86867073");
		producto.setEstadoProducto("D");
		producto.setFechaVigencia("2019-04-20-05:00");
		producto.setGrupoProducto("3");
		producto.setMoneda("COP");
		producto.setNombreProducto("tlibre inversion");
		productosPreaprobadosBasico.add(producto);

		producto = new ProductoPreaprobadoBasicoRS();
		producto.setCodigoProducto("12");
		producto.setCupo("396817");
		producto.setEstadoProducto("D");
		producto.setFechaVigencia("2019-01-23-05:00");
		producto.setGrupoProducto("3");
		producto.setMoneda("COP");
		producto.setNombreProducto("TARJETA DE CREDITO");
		producto.setPrioridadProducto("1");
		productosPreaprobadosBasico.add(producto);

		respuesta.setProductosPreaprobadosBasico(productosPreaprobadosBasico);
		respuesta.setRespuestaPrecalculado("S");
		respuesta.setTipoDocumento("FS001");

		respuestaAutenticacion.setIdSesion("2werwd32rewr233de324d3f");
		respuestaAutenticacion.setTokenNuevo("ertw12qddacbfr2");

		when(callRestServiceBack.proxyConsultaPrecalculadoCliente(Mockito.any(GestionPrecalculadoClienteRQ.class),
				Mockito.any())).thenReturn(respuesta);

		respuestaApi = service.consultaPrecalculadoCredito("", ConstantesTest.CARGAUTILINFOASESORCEDULA, "127.0.0.1");
		assertThat(respuestaApi.getNombreProducto(), is("TARJETA DE CREDITO"));
	}

	@Test
	public void testConsultarPrecalculadoSession() throws Exception {
		ExceptionLI exc = new ExceptionLI("AUTH-401", "La session ha expirado.");
		when(servicioAutenticacion.validarSesion(anyString(), anyString())).thenThrow(exc);
		ProductoPreaprobadoBasicoRS respuestaApi = new ProductoPreaprobadoBasicoRS();
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("AUTH-401");
		errorResponseDTO
				.setDescripcionTecnica("La session ha expirado.");
		mensajeFuncional.setCodigoFuncional("AUTH-401");
		mensajeFuncional
				.setDescripcionFuncional("La session ha expirado. Ingrese nuevamente");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		doReturn(errorResponseDTO).when(callRestServiceBack).errorService(any(), anyString(), anyString());
		respuestaApi = service.consultaPrecalculadoCredito("", ConstantesTest.CARGAUTILINFOASESORCEDULA, "127.0.0.1");
		assertNotNull(respuestaApi);
		assertThat(respuestaApi.getCodFuncional(), is("AUTH-401"));
	}

	@Test
	public void testSolicitudCreditoConsumoPaso1() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");

		when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class), Mockito.any(),
				Mockito.anyString())).thenReturn(respuestaService);

		String respuesta = ConstantesTest.RESPUESTAOFERTASOLICITUDCREDITOCONSUMOPASO1;
		OfertaDigital ofertaDigital = new Gson().fromJson(respuesta, OfertaDigital.class);
		when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		respuestaApi = service.crearCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO1,
				"127.0.0.1");
		Integer[] respuestaEsperada = { 36, 59 };
		assertArrayEquals(respuestaEsperada, respuestaApi.getPlazos().toArray());
	}

	@Test
	public void testSolicitudCreditoConsumoPaso1ConfNotNull() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-500");
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-500");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no esta disponible. Por favor intenta mas tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);

		String respuesta = ConstantesTest.RESPUESTAOFERTASOLICITUDCREDITOCONSUMOPASO3;
		OfertaDigital ofertaDigital = new Gson().fromJson(respuesta, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		respuestaApi = service.crearCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO1,
				"127.0.0.1");
		assertEquals("GCC-500", respuestaApi.getCodError());
	}

	@Test
	public void testCrearCreditoSession() throws Exception {
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		ExceptionLI exc = new ExceptionLI("AUTH-401", "La session ha expirado.");
		when(servicioAutenticacion.validarSesion(anyString(), anyString())).thenThrow(exc);
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("AUTH-401");
		errorResponseDTO
				.setDescripcionTecnica("La session ha expirado.");
		mensajeFuncional.setCodigoFuncional("AUTH-401");
		mensajeFuncional
				.setDescripcionFuncional("La session ha expirado. Ingrese nuevamente");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		doReturn(errorResponseDTO).when(callRestServiceBack).errorService(any(), anyString(), anyString());
		respuestaApi = service.crearCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO1,
				"127.0.0.1");
		assertNotNull(respuestaApi);
		assertThat(respuestaApi.getCodFuncional(), is("AUTH-401"));
	}

	@Test
	public void testSolicitudCreditoConsumoPaso2ConfNotNull() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-500");
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-500");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no esta disponible. Por favor intenta mas tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);

		String respuesta = ConstantesTest.RESPUESTAOFERTASOLICITUDCREDITOCONSUMOPASO3;
		OfertaDigital ofertaDigital = new Gson().fromJson(respuesta, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		respuestaApi = service.confirmarCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO2,
				"127.0.0.1");
		assertEquals("GCC-500", respuestaApi.getCodError());
	}

	@Test
	public void testSolicitudCreditoConsumoPaso1null() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-500");
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-500");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no esta disponible. Por favor intenta mas tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);

		OfertaDigital ofertaDigital = null;
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		respuestaApi = service.crearCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO1,
				"127.0.0.1");
		assertEquals("GCC-500", respuestaApi.getCodError());
	}

	@Test
	public void testSolicitudCreditoConsumoPasoLibranza() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4263-4");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);

		String respuesta = ConstantesTest.RESPUESTAOFERTASOLICITUDCREDITOCONSUMOPASO1LIBRANZA;
		OfertaDigital ofertaDigital = new Gson().fromJson(respuesta, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		respuestaApi = service.crearCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO1,
				"127.0.0.1");
		Integer[] respuestaEsperada = { 72, 84, 96 };
		assertArrayEquals(respuestaEsperada, respuestaApi.getPlazos().toArray());
	}

	@Test
	public void testSolicitudCreditoConsumoPaso2() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");
		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);
		String respuestaJson = ConstantesTest.RESPUESTAOFERTASOLICITUDCREDITOCONSUMOPASO2;
		OfertaDigital ofertaDigital = new Gson().fromJson(respuestaJson, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		respuesta = service.confirmarCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO2,
				"127.0.0.1");
		assertNotNull(respuesta);
	}

	@Test
	public void testConfirmarCreditoSession() throws Exception {
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		ExceptionLI exc = new ExceptionLI("AUTH-401", "La session ha expirado.");
		when(servicioAutenticacion.validarSesion(anyString(), anyString())).thenThrow(exc);
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("AUTH-401");
		errorResponseDTO
				.setDescripcionTecnica("La session ha expirado.");
		mensajeFuncional.setCodigoFuncional("AUTH-401");
		mensajeFuncional
				.setDescripcionFuncional("La session ha expirado. Ingrese nuevamente");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		doReturn(errorResponseDTO).when(callRestServiceBack).errorService(any(), anyString(), anyString());
		respuestaApi = service.confirmarCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO2,
				"127.0.0.1");
		assertNotNull(respuestaApi);
		assertThat(respuestaApi.getCodFuncional(), is("AUTH-401"));
	}

	@Test
	public void testSolicitudCreditoConsumoPaso3() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);
		String respuestaJson3 = ConstantesTest.RESPUESTAOFERTASOLICITUDCREDITOCONSUMOPASO3;
		OfertaDigital ofertaDigital = new Gson().fromJson(respuestaJson3, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		respuesta = service.firmarDocumentosCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO3,
				"127.0.0.1");
		assertNotNull(respuesta);
		assertThat(respuesta.getIdProceso(), is("4463"));
	}

	@Test
	public void testSolicitudCreditoConsumoPaso3Error() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-LI0001");
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-LI0001");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no esta disponible. Por favor intenta mas tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);
		String respuestaJson3 = ConstantesTest.RESPUESTAOFERTASOLICITUDCREDITOCONSUMOPASO3ERR;
		OfertaDigital ofertaDigital = new Gson().fromJson(respuestaJson3, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		respuesta = service.firmarDocumentosCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO3,
				"127.0.0.1");
		assertEquals("GCC-LI0001", respuesta.getCodError());

	}

	@Test
	public void testFirmarCreditoSession() throws Exception {
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		ExceptionLI exc = new ExceptionLI("AUTH-401", "La session ha expirado.");
		when(servicioAutenticacion.validarSesion(anyString(), anyString())).thenThrow(exc);
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("AUTH-401");
		errorResponseDTO
				.setDescripcionTecnica("La session ha expirado.");
		mensajeFuncional.setCodigoFuncional("AUTH-401");
		mensajeFuncional
				.setDescripcionFuncional("La session ha expirado. Ingrese nuevamente");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		doReturn(errorResponseDTO).when(callRestServiceBack).errorService(any(), anyString(), anyString());
		respuestaApi = service.firmarDocumentosCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO3,
				"127.0.0.1");
		assertNotNull(respuestaApi);
		assertThat(respuestaApi.getCodFuncional(), is("AUTH-401"));
	}

	@Test
	public void testRecalculoCuota() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");
		OfertaDigital ofertaDigital = new Gson().fromJson(ConstantesTest.RESPUESTAOFERTARECALCULO, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		respuesta = service.recalculoCuota(ConstantesTest.CARGAUTILRECALCULO, "127.0.0.1");
		assertNotNull(respuesta);
		assertThat(respuesta.getProductos().get(0).getCuota(), is(BigDecimal.valueOf(157127.27412135515)));
	}

	@Test
	public void testRecalculoCuotaNull() throws ExceptionLI, IOException {

		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-100");
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-100");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no esta disponible. Por favor intenta mas tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		OfertaDigital ofertaDigital = null;
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		respuesta = service.recalculoCuota(ConstantesTest.CARGAUTILRECALCULO, "127.0.0.1");
		assertEquals("GCC-100", respuesta.getCodError());

	}

	@Test
	public void testRecalcularSeguroNull() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respuesta;

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-100");
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-100");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no esta disponible. Por favor intenta mas tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		OfertaDigital ofertaDigital = null;
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		respuesta = service.recalcularSeguro(ConstantesTest.CARGAUTILRECALCULO, "127.0.0.1");
		assertEquals("GCC-100", respuesta.getCodError());

	}

	@Test
	public void testRecalcularSeguro() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");

		OfertaDigital ofertaDigital = new Gson().fromJson(ConstantesTest.RESPUESTAOFERTARECALCULO, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		respuesta = service.recalcularSeguro(ConstantesTest.CARGAUTILRECALCULO, "127.0.0.1");
		assertNotNull(respuesta);
		assertThat(respuesta.getProducto().getCostoTotalSeguro(), is(BigDecimal.valueOf(1450.0)));
	}

	@Test
	public void testFinalizarExperiencia() throws ExceptionLI, IOException {
		String cargaUtil = "{'cuentasDisponibles':[],'datosCliente':{'tipoIdentificacion':'FS001'},'informacionSucursal':{'nombreCompleto':'Usuario Prueba','codigoAsesor':'1037644654','usuarioRed':'IEUSER2'},'informacionDispositivo':{'deviceBrowser':'chrome','deviceOS':'windows','sistemaOperativo':'windows','userAgent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36','dispositivo':'unknown','versionSistemaOperativo':'windows-10','ip':'127.0.0.1'},'informacionCredito':{'isDebitoAutomatico':true,'isAceptaSeguro':true,'isBeneficiarios':false,'beneficiario':[]},'informacionDocumentos':{'aceptaTerminos':false},'informacionBiometria':{'reintentosValidacionNoexitosa':'0'},'informacionTransaccion':{'pasoFuncional':'paso_li_100','idSesion':'gjopl9D7oz4Si3AC3xQdEw','fechaHoraTransaccion':'','ipCliente':'','tokenActual':'toooooooooooooooken'},'esCrediagil':false,'esLibranza':false,'esLibreInversion':false,'hash':'fff22f8a1fc76a173329b8952707e772'}";

		String respuesta = service.finalizarExp(cargaUtil, "127.0.0.1");
		assertNotNull(respuesta);
		assertThat(respuesta, is("Finalizado OK"));
	}

	@Test(expected = Exception.class)
	public void testFinalizarExperienciaException() throws Exception {
		String cargaUtil = "'{'informacionTransaccion\':{'pasoFuncional\':\"paso4\",'idSesion\':\"3123123123213\"}}'";

		Mockito.when(service.finalizarExp(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("500", "Error"));

		service.finalizarExp(cargaUtil, "127.0.0.1");
	}

	@Test
	public void castUbicacionCliente() {
		String res = "{\"informacionUbicacionCliente\":[{\"identificacionCliente\":{\"tipoDocumento\":\"FS001\",\"numeroDocumento\":\"1909131013\"},\"codigoDireccionFuente\":\"0074004417\",\"codigoTipoDireccionFuente\":\"Z001\",\"direccionPrincipal\":\"CL 18 D 28 84\",\"codigoCiudad\":\"17001000\",\"codigoDepartamento\":\"17\",\"codigoPais\":{\"codigoISO3166Alfa2\":\"CO\"},\"telefonoFijo\":\"5770090\",\"codigoPostal\":\"0017001000\",\"codigoCorrespondencia\":\"X\"}]}";
		res = res.replace("", "");
		RecuperarDatosUbicacionClienteResponse resp = new Gson().fromJson(res,
				RecuperarDatosUbicacionClienteResponse.class);
		assertNotNull(resp);
	}

	@Test
	public void testValidacionPreguntas() throws ExceptionLI, IOException {
		propiedades = Variables.getPropiedadesProspecto();
		service.propiedades.setPropiedades(propiedades);
		ValidacionPreguntasRS respuesta;

		respuesta = service.validacionPreguntas("", ConstantesTest.CARGAUTIL_VAL_PREGUNTAS, "127.0.0.1");
		assertThat(respuesta.getTokenActual(), is("RRRR"));
	}

	@Test
	public void testValidacionPreguntasExcLI() throws ExceptionLI, IOException {
		propiedades = Variables.getPropiedadesProspecto();
		service.propiedades.setPropiedades(propiedades);
		ValidacionPreguntasRS respuesta;

		ErrorResponseDTO res = new ErrorResponseDTO();
		res.setCodigoError("CGP-401");
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		mensajeFuncional.setCodigoFuncional("CGP-401");
		mensajeFuncional.setDescripcionFuncional("No se puede continuar con la experiencia.");
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		listaMensajes.add(mensajeFuncional);
		res.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(res);

		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("CGP-401", "No autorizado"));
		respuesta = service.validacionPreguntas("", ConstantesTest.CARGAUTIL_VAL_PREGUNTAS, "127.0.0.1");
		assertEquals(respuesta.getCodError(), res.getCodigoError());
	}

	@Test
	public void testValidacionPreguntasErr() throws ExceptionLI, IOException {
		propiedades = Variables.getPropiedadesProspecto();
		service.propiedades.setPropiedades(propiedades);
		ValidacionPreguntasRS respuesta;

		ErrorResponseDTO res = new ErrorResponseDTO();
		res.setCodigoError("CLS-005");
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		mensajeFuncional.setCodigoFuncional("CLS-005");
		mensajeFuncional.setDescripcionFuncional("No se puede continuar con la experiencia.");
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		listaMensajes.add(mensajeFuncional);
		res.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(res);

		respuesta = service.validacionPreguntas("", ConstantesTest.CARGAUTIL_VAL_PREGUNTAS_ERROR, "127.0.0.1");
		assertThat(respuesta.getCodError(), is("CLS-005"));
	}

	@Test
	public void testValidacionPreguntasSession() throws Exception {
		ValidacionPreguntasRS respuesta;
		ExceptionLI exc = new ExceptionLI("AUTH-401", "La session ha expirado.");
		when(servicioAutenticacion.validarSesion(anyString(), anyString())).thenThrow(exc);
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("AUTH-401");
		errorResponseDTO
				.setDescripcionTecnica("La session ha expirado.");
		mensajeFuncional.setCodigoFuncional("AUTH-401");
		mensajeFuncional
				.setDescripcionFuncional("La session ha expirado. Ingrese nuevamente");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		doReturn(errorResponseDTO).when(callRestServiceBack).errorService(any(), anyString(), anyString());
		respuesta = service.validacionPreguntas("", ConstantesTest.CARGAUTIL_VAL_PREGUNTAS, "127.0.0.1");
		assertNotNull(respuesta);
		assertThat(respuesta.getCodFuncional(), is("AUTH-401"));
	}



	@Test
	public void testConsultarApiErrores() throws HttpResponseException, IOException, ExceptionLI {
		ErrorRequestDTO errorRequestDTO = new ErrorRequestDTO();
		errorRequestDTO.setIdAplicacion("99");
		errorRequestDTO.setCodigoInterno("4-ACOACOO902");

		ErrorResponseDTO res = new ErrorResponseDTO();
		res.setCodigoError("902");
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		mensajeFuncional.setCodigoFuncional("4-ACOACOO902");
		mensajeFuncional.setDescripcionFuncional("No se puede continuar con la experiencia.");
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		listaMensajes.add(mensajeFuncional);
		res.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(res);

		assertNotNull(res);
		assertEquals("4-ACOACOO902", res.getMensajeFuncional().get(0).getCodigoFuncional());
	}

	@Test
	public void testConsultaDatosBasicosClienteValidarNombre() throws ExceptionLI, IOException {
		ConsultarDatosClienteRS respuestaRS;
		
		Mockito.when(callRestServiceBack.llamadoBackDatosCliente(Mockito.any(ConsultarDatosClienteRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(ConstantesTest.RESPUESTADATOSBASICOSCLIENTE);

		respuestaRS = service.consultaDatosBasicosCliente("", ConstantesTest.CARGAUTILINFOASESORCEDULA, "127.0.0.1");

		assertNotNull(respuestaRS);
		assertThat(respuestaRS.getCliente().getPersonaNatural().getNombreCliente().getPrimerNombre(), is("FERNEY"));
	}

	@Test
	public void testConsultaDatosBasicosClienteNoValidarNombre() throws ExceptionLI, IOException {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);
		ConsultarDatosClienteRS respuestaRS;
		Mockito.when(callRestServiceBack.llamadoBackDatosCliente(Mockito.any(ConsultarDatosClienteRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(ConstantesTest.RESPUESTADATOSBASICOSCLIENTE);

		respuestaRS = service.consultaDatosBasicosCliente("", ConstantesTest.CARGAUTILINFOASESORCEDULA, "127.0.0.1");

		assertNotNull(respuestaRS);
		assertThat(respuestaRS.getCliente().getPersonaNatural().getNombreCliente().getPrimerNombre(), is("FERNEY"));
	}

	@Test
	public void testConsultaDatosBasicosClienteExc() throws ExceptionLI, IOException {
		ExceptionLI respuestaErr = new ExceptionLI("500", "");
		ConsultarDatosClienteRS respuestaRS ;
		Mockito.when(callRestServiceBack.llamadoBackDatosCliente(Mockito.any(ConsultarDatosClienteRQ.class),
				Mockito.any(), Mockito.anyString())).thenThrow(respuestaErr);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("DEFAULT-500");
		errorResponseDTO.setDescripcionTecnica("No se encontro codError en API Errores");
		mensajeFuncional.setCodigoFuncional("DEFAULT-500");
		mensajeFuncional.setDescripcionFuncional(
				"En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		respuestaRS = service.consultaDatosBasicosCliente("", ConstantesTest.CARGAUTILINFOASESORCEDULA, "127.0.0.1");
		assertNotNull(respuestaRS);
		assertThat(respuestaRS.getCodFuncional(), is("DEFAULT-500"));
	}

	@Test
	public void testNoSeEncuentranDatosBasicos() throws ExceptionLI, IOException {
		ConsultarDatosClienteRS respuestaRS;
		Mockito.when(callRestServiceBack.llamadoBackDatosCliente(Mockito.any(ConsultarDatosClienteRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(null);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("CLS-003");
		errorResponseDTO.setDescripcionTecnica("Cliente no existe");
		mensajeFuncional.setCodigoFuncional("CLS-003");
		mensajeFuncional.setDescripcionFuncional("Verifica la cédula ya que no es cliente Bancolombia.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		respuestaRS = service.consultaDatosBasicosCliente("", ConstantesTest.CARGAUTILINFOASESORCEDULA, "127.0.0.1");
		assertThat(respuestaRS.getCodFuncional(), is("CLS-003"));
	}

	@Test
	public void testEnviarCorreoExcLI() throws ExceptionLI, IOException {
		Response res = Response.status(Status.OK).entity("Lista es null").build();
		Mockito.when(callRestServiceBack.enviarCorreo(Mockito.anyMap(), Mockito.anyString())).thenReturn(res);

		String respuestaCorreo[] = service.enviarCorreo("", ConstantesTest.CARGAUTILENVIOCORREO, "127.0.0.1");
		assertNotNull(respuestaCorreo);
	}

	@Test
	public void testEnviarCorreoExc() throws ExceptionLI, Exception {
		Response res = Response.status(Status.OK).entity("Lista es null").build();
		Mockito.when(callRestServiceBack.enviarCorreo(Mockito.anyMap(), Mockito.anyString())).thenReturn(res);

		String respuestaCorreo[] = service.enviarCorreo("", ConstantesTest.CARGAUTILENVIOCORREOEXCEP, "127.0.0.1");
		assertNotNull(respuestaCorreo);
	}
	
	
	@Test
	public void testEnviarCorreoColpe() throws ExceptionLI, Exception {
		Response res = Response.status(Status.OK).entity("Lista es null").build();
		Mockito.when(callRestServiceBack.enviarCorreo(Mockito.anyMap(), Mockito.anyString())).thenReturn(res);

		String respuestaCorreo[] = service.enviarCorreo("", ConstantesTest.CARGAUTILENVIOCORREOCOLP, "127.0.0.1");
		assertNotNull(respuestaCorreo);
	}
	
	

	@Test
	public void testRecalcularSeguroExcLI() throws Exception {
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();
		Exception er = new Exception();
		ExceptionLI respuestaError = new ExceptionLI("500", er.getMessage(), er);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-" + respuestaError.getCodigo());
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-" + respuestaError.getCodigo());
		mensajeFuncional.setDescripcionFuncional(
				"En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(respuestaError);

		respuesta = service.recalcularSeguro(ConstantesTest.CARGAUTILRECALCULO, "127.0.0.1");
		assertThat(respuesta.getCodFuncional(), is("GCC-" + "500"));
	}

	@Test
	public void testRecalcularSeguroExc() throws ExceptionLI, Exception {
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();
		Exception er = new Exception();
		ExceptionLI respuestaError = new ExceptionLI("500", er.getMessage(), er);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-" + respuestaError.getCodigo());
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-" + respuestaError.getCodigo());
		mensajeFuncional.setDescripcionFuncional(
				"En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(respuestaError);

		respuesta = service.recalcularSeguro(ConstantesTest.CARGAUTILRECALCULOEXC, "127.0.0.1");
		assertThat(respuesta.getCodFuncional(), is("GCC-" + "500"));
	}

	@Test
	public void testRecalculoCuotaExcLI() throws ExceptionLI {
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();
		Exception er = new Exception();
		ExceptionLI respuestaError = new ExceptionLI("500", er.getMessage(), er);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-" + respuestaError.getCodigo());
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-" + respuestaError.getCodigo());
		mensajeFuncional.setDescripcionFuncional(
				"En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(respuestaError);
		respuesta = service.recalculoCuota(ConstantesTest.CARGAUTILRECALCULO, "127.0.0.1");
		assertThat(respuesta.getCodFuncional(), is("GCC-" + "500"));
	}

	@Test
	public void testRecalculoCuotaExc() throws ExceptionLI {
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();
		Exception er = new Exception();
		ExceptionLI respuestaError = new ExceptionLI("500", er.getMessage(), er);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-" + respuestaError.getCodigo());
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-" + respuestaError.getCodigo());
		mensajeFuncional.setDescripcionFuncional(
				"En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(respuestaError);
		respuesta = service.recalculoCuota(ConstantesTest.CARGAUTILRECALCULOEXC, "127.0.0.1");
		assertThat(respuesta.getCodFuncional(), is("GCC-" + "500"));
	}

	@Test
	public void testConsultaDatosUbicacionExcLI() throws ExceptionLI, IOException {
		ExceptionLI respuestaErr = new ExceptionLI("500", "Error Prueba");
		ConsultarDatosClienteRS respuestaRS = new ConsultarDatosClienteRS();

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("DEFAULT-500");
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("DEFAULT-500");
		mensajeFuncional.setDescripcionFuncional(
				"En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		respuestaRS = service.consultaDatosUbicacion("", ConstantesTest.CARGAUTILINFOASESORCEDULA, "127.0.0.1");

		assertNotNull(respuestaRS);
		assertThat(respuestaRS.getCodFuncional(), is("DEFAULT-500"));
	}

	@Test
	public void testConsultaDatosBasicosClienteExcLI() throws ExceptionLI, IOException {
		ExceptionLI respuestaErr = new ExceptionLI("500", "Error Prueba");
		ConsultarDatosClienteRS respuestaRS;
		Mockito.when(callRestServiceBack.llamadoBackDatosCliente(Mockito.any(ConsultarDatosClienteRQ.class),
				Mockito.any(), Mockito.anyString())).thenThrow(respuestaErr);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("CDC-500");
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("CDC-500");
		mensajeFuncional.setDescripcionFuncional(
				"En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		respuestaRS = service.consultaDatosBasicosCliente("", ConstantesTest.CARGAUTILINFOASESORCEDULA, "127.0.0.1");

		assertNotNull(respuestaRS);
		assertThat(respuestaRS.getCodFuncional(), is("CDC-500"));
	}

	@Test
	public void testConsultarInfoAsesorExcIO() throws Exception {
		ConsultarDatosSucursalRS respuestaer = new ConsultarDatosSucursalRS();
		respuestaer.setCodError("500");
		respuestaer.setDescError("Error en consumo de servicio Consulta Datos Comerciales Sucursal");

		Mockito.when(callRestServiceBack.proxyDatosComercialesSucursal(Mockito.any(ConsultarDatosSucursalRQ.class),
				Mockito.any())).thenReturn(respuestaer);
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("DEFAULT-500");
		errorResponseDTO
				.setDescripcionTecnica("En este momento el sistema no está disponible. Por favor intenta más tarde");
		mensajeFuncional.setCodigoFuncional("DEFAULT-500");
		mensajeFuncional.setDescripcionFuncional(
				"En este momento el sistema no está disponible. Por favor intenta más tarde");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		ConsultarDatosSucursalRS respuesta = service.consultarAsesor("", ConstantesTest.CARGAUTILINFOASESORCEDULA,
				"127.0.0.1");

		assertNotNull(respuesta);
		assertThat(respuesta.getCodFuncional(), is("DEFAULT-500"));
	}

	@Test
	public void testSolicitudCreditoConsumoExcLI() throws ExceptionLI, IOException {
		ExceptionLI respuestaService = new ExceptionLI("500", "error prueba");
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-500");
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-500");
		mensajeFuncional.setDescripcionFuncional(
				"En este momento el sistema no est� disponible. Por favor intenta m�s tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);
		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("GCC-500", "No se encontró codError en API Errores"));
		respuestaApi = service.crearCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO1,
				"127.0.0.1");
		assertThat(respuestaApi.getCodFuncional(), is("GCC-500"));
	}

	@Test
	public void propiedadesEjec() {
		CargadorPropiedades propiedades = service.recargarConf();
		assertNotNull(propiedades);
	}

	@Test
	public void testNoExisteInfoAsesor2() throws Exception {
		String cargaUtil = "{'datoConsulta':'1018418380','tipoConsulta': '3','idSesion': "
				+ "'2werwd32rewr233de324d3f','pasofuncional': 'paso_li_101'}";
		Mockito.when(callRestServiceBack.llamadoBackInternoCatalogo(Mockito.any(), Mockito.anyString()))
				.thenReturn(ConstantesTest.RESPUESTACATALOGO);
		ConsultarDatosSucursalRS consultaDatosSucursal = new ConsultarDatosSucursalRS();
		consultaDatosSucursal.setCodError("100");

		Mockito.when(callRestServiceBack.proxyDatosComercialesSucursal(Mockito.any(ConsultarDatosSucursalRQ.class),
				Mockito.any())).thenReturn(consultaDatosSucursal);
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("CDCSA-100");
		errorResponseDTO.setDescripcionTecnica("100 Error al consultar el asesor");
		mensajeFuncional.setCodigoFuncional("CDCSA-100");
		mensajeFuncional.setDescripcionFuncional("Asesor no autorizado");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		ConsultarDatosSucursalRS respuesta = service.consultarAsesor("", cargaUtil, "127.0.0.1");

		assertNotNull(respuesta);
		assertThat(respuesta.getCodFuncional(), is("CDCSA-100"));
	}

	@Test
	public void testNoExisteInfoSucursal2() throws Exception {
		String cargaUtil = "{'datoConsulta':'1018418380','tipoConsulta': '2','idSesion': '2werwd32rewr233de324d3f','pasofuncional': 'paso_li_101'}";
		Mockito.when(callRestServiceBack.llamadoBackInternoCatalogo(Mockito.any(), Mockito.anyString()))
				.thenReturn(ConstantesTest.RESPUESTACATALOGO);
		ConsultarDatosSucursalRS consultaDatosSucursal = new ConsultarDatosSucursalRS();
		consultaDatosSucursal.setCodError("100");

		Mockito.when(callRestServiceBack.proxyDatosComercialesSucursal(Mockito.any(ConsultarDatosSucursalRQ.class),
				Mockito.any())).thenReturn(consultaDatosSucursal);
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("CDCSA-100");
		errorResponseDTO.setDescripcionTecnica("100 Error al consultar el asesor");
		mensajeFuncional.setCodigoFuncional("CDCSA-100");
		mensajeFuncional.setDescripcionFuncional("Asesor no autorizado");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		ConsultarDatosSucursalRS respuesta = service.consultarAsesor("", cargaUtil, "127.0.0.1");

		assertNotNull(respuesta);
		assertThat(respuesta.getCodFuncional(), is("CDCSA-100"));
	}

	@Test
	public void testConvenio() {
		try {
			OfertaDigital oferta = new Gson().fromJson(ConstantesTest.DATOS_CON, OfertaDigital.class);
			if (oferta.getOferta().getConvenio() != null) {
				assertThat(oferta.getOferta().getConvenio().getNombreConvenio().trim(), is("CONTACTAMOS SAS"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testConsultarCuentasNoExisten() throws ExceptionLI, IOException {
		ConsultarProductoDepositosRS respuesta = new ConsultarProductoDepositosRS();
		respuesta.setCodError("600");
		ConsultarProductoDepositosRSApi respuestaApi = new ConsultarProductoDepositosRSApi();

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("CPD- " + respuesta.getCodError());
		errorResponseDTO.setDescripcionTecnica("600 No existen cuentas asociadas");
		mensajeFuncional.setCodigoFuncional("CPD- " + respuesta.getCodError());
		mensajeFuncional.setDescripcionFuncional("El cliente no posee cuentas válidas para desembolsar");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		Mockito.when(callRestServiceBack.proxyConsultaCuentas(Mockito.any(ConsultarProductoDepositosRQ.class),
				Mockito.any())).thenReturn(respuesta);
		respuestaApi = service.consultaCuentas("", ConstantesTest.CARGAUTILCUENTAS, "127.0.0.1");
		assertNotNull(respuestaApi);
		assertThat(respuestaApi.getCodError(), is("CPD- 600"));
	}

	@Test
	public void testSolicitudCreditoConsumoIdProcesEmpty() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();

		respuestaService.setIdProceso("");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");
		respuestaService.setCodError("06");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-06");
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-06");
		mensajeFuncional.setDescripcionFuncional(
				"En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		respuestaApi = service.crearCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO1,
				"127.0.0.1");
		assertThat(respuestaApi.getCodError(), is("GCC-06"));
	}

	@Test
	public void testDatosBasicosRespuestaVacia() throws ExceptionLI {
		ConsultarDatosClienteRS respuestaRS;
		Mockito.when(callRestServiceBack.llamadoBackDatosCliente(Mockito.any(ConsultarDatosClienteRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(null);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("CDC-500");
		errorResponseDTO.setDescripcionTecnica("Fallo datos basicos");
		mensajeFuncional.setCodigoFuncional("CDC-500");
		mensajeFuncional.setDescripcionFuncional(
				"En este momento el sistema no esta disponible. Por favor intenta mas tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		respuestaRS = service.consultaDatosBasicosCliente("", ConstantesTest.CARGAUTILINFOASESORCEDULA, "127.0.0.1");

		assertThat(respuestaRS.getCodFuncional(), is("CDC-500"));
	}


	@Test
	public void testConsultaPrecalculadoSucursalNoHabilitadaTDC() throws ExceptionLI, IOException {
		GestionPrecalculadoClienteRS respuesta = new GestionPrecalculadoClienteRS();
		ProductoPreaprobadoBasicoRS respuestaApi = new ProductoPreaprobadoBasicoRS();
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		respuesta.setIdentificadorBancoFilial("9");
		respuesta.setNumeroDocumento("111111111111");
		List<ProductoPreaprobadoBasicoRS> productosPreaprobadosBasico = new ArrayList<>();
		ProductoPreaprobadoBasicoRS producto = new ProductoPreaprobadoBasicoRS();
		producto.setCodigoProducto("12");
		producto.setCupo("396817");
		producto.setEstadoProducto("D");
		producto.setFechaVigencia("2019-01-23-05:00");
		producto.setGrupoProducto("3");
		producto.setMoneda("COP");
		producto.setNombreProducto("TARJETA DE CREDITO");
		producto.setPrioridadProducto("1");
		productosPreaprobadosBasico.add(producto);

		respuesta.setProductosPreaprobadosBasico(productosPreaprobadosBasico);
		respuesta.setRespuestaPrecalculado("S");
		respuesta.setTipoDocumento("FS001");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("CLS-004");
		errorResponseDTO
				.setDescripcionTecnica("La sucursal no está habilitada para preaprobados de tarjeta de crédito");
		mensajeFuncional.setCodigoFuncional("CLS-004");
		mensajeFuncional.setDescripcionFuncional(
				"El preaprobado de este cliente debe ser gestionado por el proceso tradicional.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);
		Mockito.when(callRestServiceBack
				.proxyConsultaPrecalculadoCliente(Mockito.any(GestionPrecalculadoClienteRQ.class), Mockito.any()))
				.thenReturn(respuesta);

		respuestaApi = service.consultaPrecalculadoCredito("", ConstantesTest.CARGAUTILINFOASESORCEDULA, "127.0.0.1");
		assertThat(respuestaApi.getCodError(), is(errorResponseDTO.getCodigoError()));
	}

//	@Test
//	public void testEnviarCorreoSeguro() throws ExceptionLI, IOException {
//		Response res = Response.status(Status.OK).entity("Lista es null").build();
//		//Mockito.when(service(Mockito.anyMap(), Mockito.anyString())).thenReturn(res);
//		Mockito.when(callRestServiceBack.enviarCorreo(Mockito.anyMap(), Mockito.anyString())).thenReturn(res);
//
//		int respuesta = service.enviarCorreo("", ConstantesTest.CARGAUTILENVIOCORREOSEGURO, "127.0.0.1");
//		assertNotNull(respuesta);
//		assertThat(respuesta, is(200));
//	}

	@SuppressWarnings("static-access")
	@Test
	public void testconsultarAuthFuerte() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		AutenticacionFuerteRQ rqAuth = new AutenticacionFuerteRQ();
		rqAuth.setIdSesion("sadasd21321edsas");
		rqAuth.setNumeroDocumento("12312321");
		rqAuth.setPasoFuncional("paso2");
		rqAuth.setTipoDocumento("FS001");

		RespuestaGestionAutenticacionFuerte resAuth = new RespuestaGestionAutenticacionFuerte();
		Date fecha = new Date();
		resAuth.setMecanismo("ODA");
		resAuth.setCorreoElectronico("correo@prueba.com");
		resAuth.setNumeroCelular("3123123123");
		resAuth.setFechaInscripcion(fecha);
		resAuth.setUltimaFechaModificacionDatos(fecha);
		resAuth.setUltimaFechaModificacionMecanismo(fecha);
		resAuth.setEstadoServicioOTP("OK");
		resAuth.setMetodoEnvioOTPODA("SMS");

		PowerMockito.whenNew(ServicioGestionAutenticacionFuerte.class).withAnyArguments()
				.thenReturn(servicioGestionAutenticacionFuerte);
		Mockito.when(servicioGestionAutenticacionFuerte
				.consultarEnrolamientoCliente(Mockito.any(PeticionGestionAutenticacionFuerte.class)))
				.thenReturn(resAuth);

		AutenticacionFuerteRS resp = service.consultarAuthFuerte("", new Gson().toJson(rqAuth), "127.0.0.1");

		assertEquals(resAuth.getMecanismo(), resp.getMecanismo());

	}

	@SuppressWarnings("static-access")
	@Test
	public void testconsultarAuthFuerteBusiness() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		AutenticacionFuerteRQ rqAuth = new AutenticacionFuerteRQ();
		rqAuth.setIdSesion("sadasd21321edsas");
		rqAuth.setNumeroDocumento("12312321");
		rqAuth.setPasoFuncional("paso2");
		rqAuth.setTipoDocumento("FS001");

		RespuestaGestionAutenticacionFuerte resAuth = new RespuestaGestionAutenticacionFuerte();
		Date fecha = new Date();
		resAuth.setMecanismo("ATALLA");
		resAuth.setCorreoElectronico("correo@prueba.com");
		resAuth.setNumeroCelular("3123123123");
		resAuth.setFechaInscripcion(fecha);
		resAuth.setUltimaFechaModificacionDatos(fecha);
		resAuth.setUltimaFechaModificacionMecanismo(fecha);
		resAuth.setEstadoServicioOTP("OK");
		resAuth.setMetodoEnvioOTPODA("SMS");

		PowerMockito.whenNew(ServicioGestionAutenticacionFuerte.class).withAnyArguments()
				.thenReturn(servicioGestionAutenticacionFuerte);
		BusinessException faultInfo = new BusinessException();
		faultInfo.setGenericException(new GenericException());
		faultInfo.getGenericException().setCode("678");
		BusinessExceptionMsg exc = new BusinessExceptionMsg("", faultInfo);

		Mockito.when(servicioGestionAutenticacionFuerte
				.consultarEnrolamientoCliente(Mockito.any(PeticionGestionAutenticacionFuerte.class))).thenThrow(exc);

		AutenticacionFuerteRS resp = service.consultarAuthFuerte("", new Gson().toJson(rqAuth), "127.0.0.1");

		assertEquals(resAuth.getMecanismo(), resp.getMecanismo());

	}

	@SuppressWarnings("static-access")
	@Test
	public void testconsultarAuthFuerteExceptionLI() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		AutenticacionFuerteRQ rqAuth = new AutenticacionFuerteRQ();
		rqAuth.setIdSesion("sadasd21321edsas");
		rqAuth.setNumeroDocumento("12312321");
		rqAuth.setPasoFuncional("paso2");
		rqAuth.setTipoDocumento("FS001");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("CDCSA-100");
		errorResponseDTO.setDescripcionTecnica("100 Error al consultar el asesor");
		mensajeFuncional.setCodigoFuncional("CDCSA-100");
		mensajeFuncional.setDescripcionFuncional("Asesor no autorizado");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioGestionAutenticacionFuerte.class).withAnyArguments()
				.thenReturn(servicioGestionAutenticacionFuerte);

		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("CDCSA-100", "No autorizado"));

		AutenticacionFuerteRS resp = service.consultarAuthFuerte("", new Gson().toJson(rqAuth), "127.0.0.1");

		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@SuppressWarnings("static-access")
	@Test
	public void testconsultarAuthFuerteException() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		AutenticacionFuerteRQ rqAuth = new AutenticacionFuerteRQ();
		rqAuth.setIdSesion("sadasd21321edsas");
		rqAuth.setNumeroDocumento("12312321");
		rqAuth.setPasoFuncional("paso2");
		rqAuth.setTipoDocumento("FS001");

		ErrorResponseDTO res = new ErrorResponseDTO();
		res.setCodigoError("CGP-401");
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		mensajeFuncional.setCodigoFuncional("CGP-401");
		mensajeFuncional.setDescripcionFuncional("No se puede continuar con la experiencia.");
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		listaMensajes.add(mensajeFuncional);
		res.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(res);

		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("CGP-401", "No autorizado"));
		AutenticacionFuerteRS resp = service.consultarAuthFuerte("", new Gson().toJson(rqAuth), "127.0.0.1");

		assertEquals(res.getCodigoError(), resp.getCodError());
	}

	@SuppressWarnings("static-access")
	@Test
	public void testconsultarAuthFuerteSystemExceptionMsg() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		AutenticacionFuerteRQ rqAuth = new AutenticacionFuerteRQ();
		rqAuth.setIdSesion("sadasd21321edsas");
		rqAuth.setNumeroDocumento("12312321");
		rqAuth.setPasoFuncional("paso2");
		rqAuth.setTipoDocumento("FS001");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("CDCSA-100");
		errorResponseDTO.setDescripcionTecnica("100 Error al consultar el asesor");
		mensajeFuncional.setCodigoFuncional("CDCSA-100");
		mensajeFuncional.setDescripcionFuncional("Asesor no autorizado");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioGestionAutenticacionFuerte.class).withAnyArguments()
				.thenReturn(servicioGestionAutenticacionFuerte);
		SystemException faultInfo = new SystemException();
		faultInfo.setGenericException(new GenericException());
		faultInfo.getGenericException().setCode("678");
		SystemExceptionMsg exc = new SystemExceptionMsg("", faultInfo);

		Mockito.when(servicioGestionAutenticacionFuerte
				.consultarEnrolamientoCliente(Mockito.any(PeticionGestionAutenticacionFuerte.class))).thenThrow(exc);

		AutenticacionFuerteRS resp = service.consultarAuthFuerte("", new Gson().toJson(rqAuth), "127.0.0.1");

		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@SuppressWarnings("static-access")
	@Test
	public void testgeneracionClaveODA() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		genClave.setCorreoElectronico("prueba@pru.com");
		genClave.setMetodoEnvioOTPODA(ConstantesLI.TIPO_MENSAJE_TEXTO);

		GenerarEnviarOTPODAResponse otp = new GenerarEnviarOTPODAResponse();
		otp.setOtp("1234");

		PowerMockito.whenNew(ServicioGenerarOTP.class).withAnyArguments().thenReturn(servicioGenerarOTP);
		Mockito.when(servicioGenerarOTP.enviarOTP(Mockito.any(AutenticacionOTPRQ.class))).thenReturn(otp);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(ConstantesLI.VAR_CLAVE_OTPODA, resp.getOtp());
	}

	@SuppressWarnings("static-access")
	@Test
	public void testgeneracionClaveExceptionLI() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("500");
		errorResponseDTO.setDescripcionTecnica("500");
		mensajeFuncional.setCodigoFuncional("500");
		mensajeFuncional.setDescripcionFuncional("500");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		genClave.setCorreoElectronico("prueba@pru.com");
		genClave.setMetodoEnvioOTPODA(ConstantesLI.TIPO_MENSAJE_CORREO);

		GenerarOTPODAResponse otp = new GenerarOTPODAResponse();
		otp.setOtp("1234");
		ExceptionLI exc = new ExceptionLI("500", "Error prueba");

		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("500", "Error prueba"));

		PowerMockito.whenNew(ServicioGenerarOTP.class).withAnyArguments().thenReturn(servicioGenerarOTP);
		Mockito.when(servicioGenerarOTP.generarOTP(Mockito.any(AutenticacionOTPRQ.class))).thenReturn(otp);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(exc.getCodigo(), resp.getCodError());
	}

	@SuppressWarnings("static-access")
	@Test
	public void testgeneracionClaveODASystemExceptionMsg() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		genClave.setCorreoElectronico("prueba@pru.com");
		genClave.setMetodoEnvioOTPODA(ConstantesLI.TIPO_MENSAJE_MIXTO);


		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("111");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("111");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.v2.SystemException faultInfo = new com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.v2.SystemException();
		faultInfo.setGenericException(new GenericException());
		faultInfo.getGenericException().setCode("111");
		com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.enlace.v2.SystemExceptionMsg exc = new com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.enlace.v2.SystemExceptionMsg(
				"", faultInfo);

		PowerMockito.whenNew(ServicioGenerarOTP.class).withAnyArguments().thenReturn(servicioGenerarOTP);

		Mockito.when(servicioGenerarOTP.generarOTP(Mockito.any(AutenticacionOTPRQ.class))).thenThrow(exc);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@SuppressWarnings("static-access")
	@Test
	public void testgeneracionClaveODABusinessExceptionMsg() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		genClave.setCorreoElectronico("prueba@pru.com");
		genClave.setMetodoEnvioOTPODA(ConstantesLI.TIPO_MENSAJE_TEXTO);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("111");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("111");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.v2.BusinessException faultInfo = new com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.v2.BusinessException();
		faultInfo.setGenericException(new GenericException());
		faultInfo.getGenericException().setCode("111");
		com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.enlace.v2.BusinessExceptionMsg exc = new com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.enlace.v2.BusinessExceptionMsg(
				"", faultInfo);

		PowerMockito.whenNew(ServicioGenerarOTP.class).withAnyArguments().thenReturn(servicioGenerarOTP);

		Mockito.when(servicioGenerarOTP.generarOTP(Mockito.any(AutenticacionOTPRQ.class))).thenThrow(exc);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@SuppressWarnings("static-access")
	@Test
	public void testgeneracionClaveATALLA() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.OTP_ATALLA);
		genClave.setCorreoElectronico("prueba@pru.com");
		genClave.setCelular("1312312312");
		genClave.setMetodoEnvioOTPODA(ConstantesLI.TIPO_MENSAJE_CORREO);
		
		SolicitarTokenResponse otp = new SolicitarTokenResponse();
		otp.setRespuesta(new Respuesta());
		otp.getRespuesta().setCodigoRespuesta("000");

		PowerMockito.whenNew(ServicioGeneracionToken.class).withAnyArguments().thenReturn(servicioGeneracionToken);
		Mockito.when(servicioGeneracionToken.solicitarToken(Mockito.any(GeneracionTokenRQ.class))).thenReturn(otp);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(ConstantesLI.OTP_ATALLA, resp.getToken());
	}

	@SuppressWarnings("static-access")
	@Test
	public void testgeneracionClaveATALLASystemExceptionMsg() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.OTP_ATALLA);
		genClave.setCorreoElectronico("prueba@pru.com");
		genClave.setMetodoEnvioOTPODA(ConstantesLI.TIPO_MENSAJE_CORREO);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("111");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("111");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		com.grupobancolombia.intf.canal.movil.generaciontoken.v1.SystemException faultInfo = new com.grupobancolombia.intf.canal.movil.generaciontoken.v1.SystemException();
		faultInfo.setGenericException(new GenericException());
		faultInfo.getGenericException().setCode("111");
		com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.SystemExceptionMsg exc = new com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.SystemExceptionMsg(
				"", faultInfo);

		PowerMockito.whenNew(ServicioGeneracionToken.class).withAnyArguments().thenReturn(servicioGeneracionToken);
		Mockito.when(servicioGeneracionToken.solicitarToken(Mockito.any(GeneracionTokenRQ.class))).thenThrow(exc);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@Test
	public void testgeneracionClaveATALLABusinessExceptionMsg() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.OTP_ATALLA);
		genClave.setCorreoElectronico("prueba@pru.com");
		genClave.setMetodoEnvioOTPODA(ConstantesLI.TIPO_MENSAJE_CORREO);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("111");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("111");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		com.grupobancolombia.intf.canal.movil.generaciontoken.v1.BusinessException faultInfo = new com.grupobancolombia.intf.canal.movil.generaciontoken.v1.BusinessException();
		faultInfo.setGenericException(new GenericException());
		faultInfo.getGenericException().setCode("111");
		com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.BusinessExceptionMsg exc = new com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.BusinessExceptionMsg(
				"", faultInfo);

		PowerMockito.whenNew(ServicioGeneracionToken.class).withAnyArguments().thenReturn(servicioGeneracionToken);
		Mockito.when(servicioGeneracionToken.solicitarToken(Mockito.any(GeneracionTokenRQ.class))).thenThrow(exc);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@Test
	public void testgeneracionClaveATALLAExceptionLI() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.OTP_ATALLA);
		genClave.setMetodoEnvioOTPODA(ConstantesLI.TIPO_MENSAJE_CORREO);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("901");
		errorResponseDTO.setDescripcionTecnica("Sin email");
		mensajeFuncional.setCodigoFuncional("901");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioGeneracionToken.class).withAnyArguments().thenReturn(servicioGeneracionToken);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}


	@Test
	public void testgeneracionClaveATALLASessionExceptionLI() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);
		ExceptionLI exc = new ExceptionLI("AUTH-401", "Session expirada");
		when(servicioAutenticacion.validarSesion(anyString(), anyString())).thenThrow(exc);
		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.OTP_ATALLA);
		genClave.setMetodoEnvioOTPODA(ConstantesLI.TIPO_MENSAJE_CORREO);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("AUTH-401");
		errorResponseDTO.setDescripcionTecnica("Session expirada");
		mensajeFuncional.setCodigoFuncional("AUTH-401");
		mensajeFuncional.setDescripcionFuncional("Session expirada");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		doReturn(errorResponseDTO).when(callRestServiceBack).errorService(any(), anyString(), anyString());

		PowerMockito.whenNew(ServicioGeneracionToken.class).withAnyArguments().thenReturn(servicioGeneracionToken);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@Test
	public void testValidacionClave() throws ExceptionLI {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("111");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("111");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		ExceptionLI exc = new ExceptionLI("AUTH-500", "Error prueba");

		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("AUTH-500", "Error prueba"));

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}


	@Test
	public void testValidacionClaveODA() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("1232456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("901");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resAuthODA.getResultCode(), resp.getResultCodeODA());
	}

	@Test
	public void testValidacionClaveODA902() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("902");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("2");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCO-902");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCO-902");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveODADefault() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("905");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("2");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("DEFAULT-500");
		errorResponseDTO.setDescripcionTecnica("905");
		mensajeFuncional.setCodigoFuncional("DEFAULT-500");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}
	
	@Test
	public void testValidacionClaveODA9021() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("902");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCO-903");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCO-903");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveODA1021() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("1021");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCO-1021");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCO-1021");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveODA1022() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("1022");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCO-1022");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCO-1022");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveODA803() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("803");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCO-803");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCO-803");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveODABlanco() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCO-900");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCO-900");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveODASystemExceptionMsg() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("000");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("111");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("111");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.v2.SystemException faultInfo = new com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.v2.SystemException();
		faultInfo.setGenericException(new GenericException());
		faultInfo.getGenericException().setCode("111");
		com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.enlace.v2.SystemExceptionMsg exc = new com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.enlace.v2.SystemExceptionMsg(
				"", faultInfo);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenThrow(exc);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@Test
	public void testValidacionClaveODAException() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("000");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("111");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("111");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenThrow(new Exception());

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@Test
	public void testValidacionClaveSFTKN() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPSoftokenResponse resAuthODA = new AutenticarClienteOTPSoftokenResponse();
		resAuthODA.setResultCode("801");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(
				servicioAutenticarClienteOTP.autenticarClienteOTPSoftoken(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resAuthODA.getResultCode(), resp.getResultCodeODA());
	}

	@Test
	public void testValidacionClaveSFTKN802() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPSoftokenResponse resAuthODA = new AutenticarClienteOTPSoftokenResponse();
		resAuthODA.setResultCode("802");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("2");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCS-802");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCS-802");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(
				servicioAutenticarClienteOTP.autenticarClienteOTPSoftoken(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveSFTKNDefault() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPSoftokenResponse resAuthODA = new AutenticarClienteOTPSoftokenResponse();
		resAuthODA.setResultCode("905");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("2");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("DEFAULT-500");
		errorResponseDTO.setDescripcionTecnica("500");
		mensajeFuncional.setCodigoFuncional("DEFAULT-500");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(
				servicioAutenticarClienteOTP.autenticarClienteOTPSoftoken(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}
	
	@Test
	public void testValidacionClaveSFTKN8021() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPSoftokenResponse resAuthODA = new AutenticarClienteOTPSoftokenResponse();
		resAuthODA.setResultCode("802");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCS-803");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCS-803");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(
				servicioAutenticarClienteOTP.autenticarClienteOTPSoftoken(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveSFTKN1021() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPSoftokenResponse resAuthODA = new AutenticarClienteOTPSoftokenResponse();
		resAuthODA.setResultCode("1021");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCS-1021");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCS-1021");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(
				servicioAutenticarClienteOTP.autenticarClienteOTPSoftoken(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveSFTKN1022() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPSoftokenResponse resAuthODA = new AutenticarClienteOTPSoftokenResponse();
		resAuthODA.setResultCode("1022");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCS-1022");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCS-1022");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(
				servicioAutenticarClienteOTP.autenticarClienteOTPSoftoken(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveSFTKN900() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPSoftokenResponse resAuthODA = new AutenticarClienteOTPSoftokenResponse();
		resAuthODA.setResultCode("");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCS-900");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCS-900");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(
				servicioAutenticarClienteOTP.autenticarClienteOTPSoftoken(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveSFTKNExc() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPSoftokenResponse resAuthODA = new AutenticarClienteOTPSoftokenResponse();
		resAuthODA.setResultCode("");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("CLASTKN-500");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("CLASTKN-500");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(
				servicioAutenticarClienteOTP.autenticarClienteOTPSoftoken(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenThrow(new Exception());

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveATALLA() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.OTP_ATALLA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setTokenEntrada("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("2312312312");
		validacionClaveRQ.setCelular("123123121");

		ValidarTokenResponse resAuthODA = new ValidarTokenResponse();
		resAuthODA.setRespuesta(new Respuesta());
		resAuthODA.getRespuesta().setCodigoRespuesta("000");
		resAuthODA.getRespuesta().setDescripcionRespuesta("Success");

		PowerMockito.whenNew(ServicioGeneracionToken.class).withAnyArguments().thenReturn(servicioGeneracionToken);
		Mockito.when(servicioGeneracionToken.validarToken(Mockito.any(GeneracionTokenRQ.class))).thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resAuthODA.getRespuesta().getCodigoRespuesta(), resp.getCodigoRespuestaTOKEN());
	}

	@Test
	public void testValidacionClaveATALLASystemExceptionMsg() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.OTP_ATALLA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setTokenEntrada("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("2312312312");
		validacionClaveRQ.setCelular("123123121");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("111");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("111");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		com.grupobancolombia.intf.canal.movil.generaciontoken.v1.SystemException faultInfo = new com.grupobancolombia.intf.canal.movil.generaciontoken.v1.SystemException();
		faultInfo.setGenericException(new GenericException());
		faultInfo.getGenericException().setCode("111");
		com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.SystemExceptionMsg exc = new com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.SystemExceptionMsg(
				"", faultInfo);

		PowerMockito.whenNew(ServicioGeneracionToken.class).withAnyArguments().thenReturn(servicioGeneracionToken);
		Mockito.when(servicioGeneracionToken.validarToken(Mockito.any(GeneracionTokenRQ.class))).thenThrow(exc);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@Test
	public void testValidacionClaveATALLABusinessExceptionMsg() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.OTP_ATALLA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setTokenEntrada("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("2312312312");
		validacionClaveRQ.setCelular("123123121");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCA-802");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCA-802");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		com.grupobancolombia.intf.canal.movil.generaciontoken.v1.BusinessException faultInfo = new com.grupobancolombia.intf.canal.movil.generaciontoken.v1.BusinessException();
		faultInfo.setGenericException(new GenericException());
		faultInfo.getGenericException().setCode("111");
		com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.BusinessExceptionMsg exc = new com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.BusinessExceptionMsg(
				"", faultInfo);

		PowerMockito.whenNew(ServicioGeneracionToken.class).withAnyArguments().thenReturn(servicioGeneracionToken);
		Mockito.when(servicioGeneracionToken.validarToken(Mockito.any(GeneracionTokenRQ.class))).thenThrow(exc);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

}
