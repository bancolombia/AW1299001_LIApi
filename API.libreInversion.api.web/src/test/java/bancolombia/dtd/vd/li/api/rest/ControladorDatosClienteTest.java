/**
 * 
 */
package bancolombia.dtd.vd.li.api.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.jwt.MalformedClaimException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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

/**
 * @author cgallego
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ServicioDatosCliente.class })
public class ControladorDatosClienteTest {

	private static final Logger logger = LogManager.getLogger(ControladorDatosClienteTest.class);

	ControladorDatosCliente service;

	@Mock
	ServicioDatosCliente servicioDatosCliente;

	@Mock
	ServicioAutenticacion servicioAutenticacion;

	HttpServletRequest requestContext;

	@Before
	public void init() throws ExceptionLI {
		requestContext = Mockito.mock(HttpServletRequest.class);
		service = new ControladorDatosCliente();
		service.setControladorDatosCliente(servicioAutenticacion, servicioDatosCliente);
	}

	@Test
	public void pruebasLogs() {
		logger.trace("the built-in TRACE level");
		logger.debug("the built-in DEBUG level");
		logger.info("the built-in INFO level");
		logger.warn("the built-in WARN level");
		logger.error("the built-in ERROR level");
		logger.fatal("the built-in FATAL level");
	}

	@Test
	public void testconsultarAsesorRest() throws ExceptionLI {
		ConsultarDatosSucursalRS respu = new ConsultarDatosSucursalRS();
		respu.setCodigoCIFAsesor("12345");
		respu.setCodigoCIFSucursal("005");
		Mockito.when(servicioDatosCliente.consultarAsesor(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.consultarAsesor(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testconsultarAsesorRestExceptionLI401() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.consultarAsesor(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.consultarAsesor(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testconsultarAsesorRestExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.consultarAsesor(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.consultarAsesor(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testconsultarAsesorRestException() throws ExceptionLI {
		Mockito.when(servicioDatosCliente.consultarAsesor(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.consultarAsesor(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testconsultaCuentasRest() throws ExceptionLI, IOException {
		ConsultarProductoDepositosRSApi respu = new ConsultarProductoDepositosRSApi();
		respu.setCuentas(null);
		Mockito.when(servicioDatosCliente.consultaCuentas(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.consultaCuentas(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testconsultaCuentasRestExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.consultaCuentas(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.consultaCuentas(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testconsultaCuentasRestExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.consultaCuentas(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.consultaCuentas(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testconsultaCuentasRestExceptionLI600() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_600, "error");
		Mockito.when(servicioDatosCliente.consultaCuentas(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.consultaCuentas(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.TRESCEROTRES);
	}

	@Test
	public void testconsultaCuentasRestException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.consultaCuentas(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.consultaCuentas(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testconsultaDatosUbicacionRest() throws ExceptionLI, IOException {
		ConsultarDatosClienteRS respu = new ConsultarDatosClienteRS();
		respu.setCodigoSegmento("G9");
		Mockito.when(servicioDatosCliente.consultaDatosUbicacion(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.consultaDatosUbicacion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testconsultaDatosUbicacionRestExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.consultaDatosUbicacion(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.consultaDatosUbicacion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testconsultaDatosUbicacionRestExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.consultaDatosUbicacion(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.consultaDatosUbicacion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testconsultaDatosUbicacionRestException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.consultaDatosUbicacion(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.consultaDatosUbicacion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testconsultaDatosBasicosRest() throws ExceptionLI, IOException {
		ConsultarDatosClienteRS respu = new ConsultarDatosClienteRS();
		respu.setCodigoSegmento("G9");
		Mockito.when(servicioDatosCliente.consultaDatosBasicosCliente(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.consultaDatosBasicos(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testconsultaDatosBasicosRestExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.consultaDatosBasicosCliente(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.consultaDatosBasicos(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testconsultaDatosBasicosRestExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.consultaDatosBasicosCliente(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.consultaDatosBasicos(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testconsultaDatosBasicosRestException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.consultaDatosBasicosCliente(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.consultaDatosBasicos(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testconsultarPrecalculadoBasicoRest() throws ExceptionLI, IOException {
		ProductoPreaprobadoBasicoRS respu = new ProductoPreaprobadoBasicoRS();
		respu.setMoneda("COP");
		Mockito.when(servicioDatosCliente.consultaPrecalculadoCredito(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.consultaPrecalculadoCliente(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testconsultarPrecalculadoBasicoRestExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.consultaPrecalculadoCredito(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.consultaPrecalculadoCliente(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testconsultarPrecalculadoBasicoRestExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.consultaPrecalculadoCredito(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.consultaPrecalculadoCliente(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testconsultarPrecalculadoBasicoRestExceptionLI600() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_600, "error");
		Mockito.when(servicioDatosCliente.consultaPrecalculadoCredito(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.consultaPrecalculadoCliente(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.TRESCEROTRES);
	}

	@Test
	public void testconsultarPrecalculadoBasicoRestException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.consultaPrecalculadoCredito(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.consultaPrecalculadoCliente(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testCrearSolicitudLibreInversion() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.crearCreditoConsumoLibreInversion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testCrearSolicitudLibreInversionExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.crearCreditoConsumoLibreInversion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testCrearSolicitudLibreInversionExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.crearCreditoConsumoLibreInversion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testCrearSolicitudLibreInversionException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.crearCreditoConsumoLibreInversion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testCrearCrediagil() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.crearCreditoConsumoCrediagil(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testCrearCrediagilExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.crearCreditoConsumoCrediagil(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testCrearCrediagilExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.crearCreditoConsumoCrediagil(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testCrearCrediagilException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.crearCreditoConsumoCrediagil(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testCrearSolicitudLibranza() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.crearCreditoConsumoLibranza(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testCrearSolicitudLibranzaExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.crearCreditoConsumoLibranza(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testCrearSolicitudLibranzaExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.crearCreditoConsumoLibranza(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testCrearSolicitudLibranzaException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.crearCreditoConsumoLibranza(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testCrearSolicitudLibranzaColpensiones() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.crearCreditoConsumoLibranzaColpensiones(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testCrearSolicitudLibranzaColpensionesExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.crearCreditoConsumoLibranzaColpensiones(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testCrearSolicitudLibranzaColpensionesExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.crearCreditoConsumoLibranzaColpensiones(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testCrearSolicitudLibranzaColpensionesException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.crearCreditoConsumoLibranzaColpensiones(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testCrearSolicitudMicroCredito() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.crearCreditoConsumoMicroCredito(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testCrearSolicitudMicroCreditoExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.crearCreditoConsumoMicroCredito(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testCrearSolicitudMicroCreditoExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.crearCreditoConsumoMicroCredito(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testCrearSolicitudMicroCreditoException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.crearCreditoConsumoMicroCredito(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testConfirmarSolicitudLibreInversion() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.confirmarCreditoConsumoLibreInversion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testConfirmarSolicitudLibreInversionExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.confirmarCreditoConsumoLibreInversion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testConfirmarSolicitudLibreInversionExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.confirmarCreditoConsumoLibreInversion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testConfirmarSolicitudLibreInversionException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.confirmarCreditoConsumoLibreInversion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testConfirmarCrediagil() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.confirmarCreditoConsumoCrediagil(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testConfirmarCrediagilExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.confirmarCreditoConsumoCrediagil(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testConfirmarCrediagilExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.confirmarCreditoConsumoCrediagil(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testConfirmarCrediagilException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.confirmarCreditoConsumoCrediagil(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testConfirmarSolicitudLibranza() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.confirmarCreditoConsumoLibranza(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testConfirmarSolicitudLibranzaExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.confirmarCreditoConsumoLibranza(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testConfirmarSolicitudLibranzaExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.confirmarCreditoConsumoLibranza(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testConfirmarSolicitudLibranzaException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.confirmarCreditoConsumoLibranza(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testConfirmarSolicitudLibranzaColpensiones() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.confirmarCreditoConsumoLibranzaColpensiones(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testConfirmarSolicitudLibranzaColpensionesExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.confirmarCreditoConsumoLibranzaColpensiones(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testConfirmarSolicitudLibranzaColpensionesExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.confirmarCreditoConsumoLibranzaColpensiones(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testConfirmarSolicitudLibranzaColpensionesException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.confirmarCreditoConsumoLibranzaColpensiones(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testConfirmarSolicitudMicroCredito() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.confirmarCreditoConsumoMicroCredito(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testConfirmarSolicitudMicroCreditoExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.confirmarCreditoConsumoMicroCredito(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testConfirmarSolicitudMicroCreditoExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.confirmarCreditoConsumoMicroCredito(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testConfirmarSolicitudMicroCreditoException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.confirmarCreditoConsumoMicroCredito(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testfirmarDocumentosSolicitudLibreInversion() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.firmarDocumentosCreditoConsumoLibreInversion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testfirmarDocumentosSolicitudLibreInversionExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.firmarDocumentosCreditoConsumoLibreInversion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testfirmarDocumentosSolicitudLibreInversionExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.firmarDocumentosCreditoConsumoLibreInversion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testfirmarDocumentosSolicitudLibreInversionException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.firmarDocumentosCreditoConsumoLibreInversion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testfirmarDocumentosCrediagil() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.firmarDocumentosCreditoConsumoCrediagil(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testfirmarDocumentosCrediagilExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.firmarDocumentosCreditoConsumoCrediagil(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testfirmarDocumentosCrediagilExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.firmarDocumentosCreditoConsumoCrediagil(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testfirmarDocumentosCrediagilException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.firmarDocumentosCreditoConsumoCrediagil(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testfirmarDocumentosSolicitudLibranza() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.firmarDocumentosCreditoConsumoLibranza(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testfirmarDocumentosSolicitudLibranzaExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.firmarDocumentosCreditoConsumoLibranza(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testfirmarDocumentosSolicitudLibranzaExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.firmarDocumentosCreditoConsumoLibranza(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testfirmarDocumentosSolicitudLibranzaException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.firmarDocumentosCreditoConsumoLibranza(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testfirmarDocumentosSolicitudLibranzaColpensiones() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.firmarDocumentosCreditoConsumoLibranzaColpensiones(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testfirmarDocumentosSolicitudLibranzaColpensionesExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.firmarDocumentosCreditoConsumoLibranzaColpensiones(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testfirmarDocumentosSolicitudLibranzaColpensionesExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.firmarDocumentosCreditoConsumoLibranzaColpensiones(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testfirmarDocumentosSolicitudLibranzaColpensionesException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.firmarDocumentosCreditoConsumoLibranzaColpensiones(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testfirmarDocumentosSolicitudMicroCredito() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.firmarDocumentosCreditoConsumoMicroCredito(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testfirmarDocumentosSolicitudMicroCreditoExceptionLI401() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.firmarDocumentosCreditoConsumoMicroCredito(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testfirmarDocumentosSolicitudMicroCreditoExceptionLI500() throws ExceptionLI, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.firmarDocumentosCreditoConsumoMicroCredito(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testfirmarDocumentosSolicitudMicroCreditoException() throws ExceptionLI, IOException {
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.firmarDocumentosCreditoConsumoMicroCredito(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testrecalcularCuota() throws ExceptionLI {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.recalculoCuota(anyString(), anyString())).thenReturn(respu);
		Response resp = service.recalcularCuota(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testrecalcularCuotaExceptionLI401() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.recalculoCuota(anyString(), anyString())).thenThrow(ex);
		Response resp = service.recalcularCuota(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testrecalcularCuotaExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.recalculoCuota(anyString(), anyString())).thenThrow(ex);
		Response resp = service.recalcularCuota(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testrecalcularCuotaException() throws ExceptionLI {
		Mockito.when(servicioDatosCliente.recalculoCuota(anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.recalcularCuota(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testrecalcularSeguro() throws ExceptionLI {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.recalcularSeguro(anyString(), anyString())).thenReturn(respu);
		Response resp = service.recalcularSeguro(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testrecalcularSeguroExceptionLI401() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.recalcularSeguro(anyString(), anyString())).thenThrow(ex);
		Response resp = service.recalcularSeguro(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testrecalcularSeguroExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.recalcularSeguro(anyString(), anyString())).thenThrow(ex);
		Response resp = service.recalcularSeguro(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testrecalcularSeguroException() throws ExceptionLI {
		Mockito.when(servicioDatosCliente.recalcularSeguro(anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.recalcularSeguro(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testfinalizarExp() throws ExceptionLI {
		String respu = "ok";
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString())).thenReturn(respu);
		Response resp = service.finalizarExp(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testfinalizarExpExceptionLI401() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString())).thenThrow(ex);
		Response resp = service.finalizarExp(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testfinalizarExpExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString())).thenThrow(ex);
		Response resp = service.finalizarExp(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testfinalizarExpException() throws ExceptionLI {
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString())).thenThrow(new NullPointerException());
		Response resp = service.finalizarExp(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testenviarCorreo() throws ExceptionLI {
		String[] respu = { "ok" };
		Mockito.when(servicioDatosCliente.enviarCorreo(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.enviarCorreo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testenviarCorreoExceptionLI401() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.enviarCorreo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.enviarCorreo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testenviarCorreoExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.enviarCorreo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.enviarCorreo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testenviarCorreoException() throws ExceptionLI {
		Mockito.when(servicioDatosCliente.enviarCorreo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.enviarCorreo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testvalidacionPreguntas() throws ExceptionLI {
		ValidacionPreguntasRS respu = new ValidacionPreguntasRS();
		Mockito.when(servicioDatosCliente.validacionPreguntas(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.validacionPreguntas(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testvalidacionPreguntasExceptionLI401() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.validacionPreguntas(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.validacionPreguntas(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testvalidacionPreguntasExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.validacionPreguntas(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.validacionPreguntas(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testvalidacionPreguntasException() throws ExceptionLI {
		Mockito.when(servicioDatosCliente.validacionPreguntas(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.validacionPreguntas(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testhealth() throws ExceptionLI {
		Response resp = service.health();
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testdescargarDocumentosBienvenida() throws ExceptionLI {
		String[] respu = { "ok" };
		Mockito.when(servicioDatosCliente.descargarDocumentosBienvenida(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.descargarDocumentosBienvenida(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testconsultarAuthFuerte() throws ExceptionLI {
		AutenticacionFuerteRS respu = new AutenticacionFuerteRS();
		Mockito.when(servicioDatosCliente.consultarAuthFuerte(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.consultarAuthFuerte(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testconsultarAuthFuerteSessionInvalida() throws ExceptionLI {
		AutenticacionFuerteRS respu = new AutenticacionFuerteRS();
		respu.setCodFuncional("AUTH-409");
		Mockito.when(servicioDatosCliente.consultarAuthFuerte(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.consultarAuthFuerte(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testconsultarAuthFuerteExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.consultarAuthFuerte(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.consultarAuthFuerte(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testgeneracionClave() throws ExceptionLI {
		GeneracionClaveRS respu = new GeneracionClaveRS();
		Mockito.when(servicioDatosCliente.generacionClave(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.generacionClave(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testgeneracionClaveExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.generacionClave(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.generacionClave(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testvalidacionClave() throws ExceptionLI {
		ValidacionClaveRS respu = new ValidacionClaveRS();
		Mockito.when(servicioDatosCliente.validacionClave(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.validacionClave(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testvalidacionClaveExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.validacionClave(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.validacionClave(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}
	
	
	
	@Test
	public void testvalidarSesion() throws ExceptionLI, MalformedClaimException {
		AutenticacionRS  respu = new AutenticacionRS ();
		Mockito.when(servicioAutenticacion.validarSesion(anyString(), anyString())).thenReturn(respu);
		Response resp = service.validarSesion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testvalidarSesionExceptionLI500() throws ExceptionLI, MalformedClaimException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioAutenticacion.validarSesion(anyString(),  anyString())).thenThrow(ex);
		Response resp = service.validarSesion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}
	
	
	@Test
	public void testcerrarSesion() throws ExceptionLI {
		AutenticacionRS  respu = new AutenticacionRS ();
		Mockito.when(servicioAutenticacion.cerrarSesion(anyString(), anyString())).thenReturn(respu);
		Response resp = service.cerrarSesion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testcerrarSesionExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioAutenticacion.cerrarSesion(anyString(),  anyString())).thenThrow(ex);
		Response resp = service.cerrarSesion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}
	
	
	@Test
	public void testcerrarSesionExceptionLI303() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.RET303, "error");
		Mockito.when(servicioAutenticacion.cerrarSesion(anyString(),  anyString())).thenThrow(ex);
		Response resp = service.cerrarSesion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.TRESCEROTRES);
	}
	
	
	
	@Test
	public void testterminarSesion() throws ExceptionLI, MalformedClaimException, GeneralSecurityException, IOException {
		AutenticacionRS  respu = new AutenticacionRS ();
		Mockito.when(servicioAutenticacion.terminarSesion(anyString())).thenReturn(respu);
		Response resp = service.terminarSesion(requestContext, "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testterminarSesionExceptionLI500() throws ExceptionLI, MalformedClaimException, GeneralSecurityException, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioAutenticacion.terminarSesion(anyString())).thenThrow(ex);
		Response resp = service.terminarSesion(requestContext, "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testabrirSesion() throws ExceptionLI, MalformedClaimException {
		AutenticacionRS  respu = new AutenticacionRS ();
		Mockito.when(servicioAutenticacion.abrirSesion(anyString())).thenReturn(respu);
		Response resp = service.abrirSesion(requestContext, "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testabrirSesionExceptionLI500() throws ExceptionLI, MalformedClaimException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioAutenticacion.abrirSesion(anyString())).thenThrow(ex);
		Response resp = service.abrirSesion(requestContext, "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testguardarPaso() throws ExceptionLI {
		String respu = "";
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString())).thenReturn(respu);
		Response resp = service.guardarPaso(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testguardarPasoExceptionLI401() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString())).thenThrow(ex);
		Response resp = service.guardarPaso(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testguardarPasoExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString())).thenThrow(ex);
		Response resp = service.guardarPaso(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testguardarPasoException() throws ExceptionLI {
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.guardarPaso(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}
	
	
	
	@Test
	public void testrecibirTokenInicial() throws ExceptionLI, MalformedClaimException, ValidacionException, IOException, GeneralSecurityException {
		AutenticacionRS  respu = new AutenticacionRS();
		Mockito.when(servicioAutenticacion.recibirTokenInicial(anyString(), anyString())).thenReturn(respu);
		Response resp = service.recibirTokenInicial(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testrecibirTokenInicialExceptionLI401() throws ExceptionLI, MalformedClaimException, ValidacionException, IOException, GeneralSecurityException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioAutenticacion.recibirTokenInicial(anyString(), anyString())).thenThrow(ex);
		Response resp = service.recibirTokenInicial(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}
	
	
	@Test
	public void testrecibirTokenInicialExceptionLI303() throws ExceptionLI, MalformedClaimException, ValidacionException, IOException, GeneralSecurityException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.RET303, "error");
		Mockito.when(servicioAutenticacion.recibirTokenInicial(anyString(), anyString())).thenThrow(ex);
		Response resp = service.recibirTokenInicial(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.TRESCEROTRES);
	}

	@Test
	public void testrecibirTokenInicialExceptionLI500() throws ExceptionLI, MalformedClaimException, ValidacionException, IOException, GeneralSecurityException {
		ValidacionException  ex = new ValidacionException("500", null, "error");
		Mockito.when(servicioAutenticacion.recibirTokenInicial(anyString(), anyString())).thenThrow(ex);
		Response resp = service.recibirTokenInicial(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}


	@Test
	public void testgenTkn() throws ExceptionLI {
		String respu = "";
		AutenticacionCont param = new AutenticacionCont();
		Mockito.when(servicioDatosCliente.generarTokenContenedor(param)).thenReturn(respu);
		Response resp = service.generarTokenContenedor(requestContext, "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	
}
