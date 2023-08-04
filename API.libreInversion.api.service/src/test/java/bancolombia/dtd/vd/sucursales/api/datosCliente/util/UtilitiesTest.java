/**
 * 
 */
package bancolombia.dtd.vd.sucursales.api.datosCliente.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import bancolombia.dtd.vd.li.dto.proxy.consultarProductoDepositos.Cuentas;
import bancolombia.dtd.vd.li.dto.utils.BeneficiarioDto;
import bancolombia.dtd.vd.sucursales.api.datosCliente.ConstantesTest;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gson.Gson;

import bancolombia.dtd.vd.li.commons.exception.ExceptionLI;
import bancolombia.dtd.vd.li.commons.util.CargadorPropiedades;

import bancolombia.dtd.vd.li.dto.proxy.aprobacionesMotor.EstudioCreditoConsumoRS;

import bancolombia.dtd.vd.li.dto.proxy.clave.GeneracionClaveRQ;
import bancolombia.dtd.vd.li.dto.proxy.comun.Propiedad;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRQ;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.OfertaDigital;
import bancolombia.dtd.vd.li.dto.utils.InformacionDispositivoDto;
import bancolombia.dtd.vd.li.dto.utils.LibreInversion;
import bancolombia.dtd.vd.li.dto.utils.ProductoCreditoDto;
import bancolombia.dtd.vd.sucursales.api.datosCliente.Variables;

/**
 * @author cgallego
 *
 */
public class UtilitiesTest {

	Utilities utilities;
	Gson gsonTest;
	GestionCreditoConsumoRQ gestionCreditoConsumoRQ;
	BeneficiarioDto beneficiarioDto;
	private static Map<String, String> propiedades;
	
	@Before
	public void init() {
		utilities = new Utilities();
		gsonTest = new Gson();
		beneficiarioDto = new BeneficiarioDto();
		propiedades = Variables.getPropiedades();
		utilities.propiedades.setPropiedades(propiedades);
	}

	@Test
	public void cuandoSeEjecutaRetornaFechaActualConFormatoDefinido() {
		String fecha = utilities.getFechaActual();
		assertNotNull(fecha);
	}

	@Test
	public void crearListaConfiguracion() {
		CargadorPropiedades prop = new CargadorPropiedades();
		prop.setPropiedades(Variables.getPropiedades3());
		List<Propiedad> resp = utilities.crearListaConfiguracion(prop,
				"PROXY_ENDPOINT_GESTIONAR_PROSPECTO,PROXY_SYSTEM_ID_GESTIONAR_PROSPECTO,PROXY_CLASSIFICATION_GESTIONAR_PROSPECTO,PROXY_GESTIONAR_PROSPECTO_NAMESPACE,PROXY_REQUEST_TIMEOUT_GESTIONAR_PROSPECTO,PROXY_CONNECTION_TIMEOUT_GESTIONAR_PROSPECTO");
		assertNotNull(resp);
	}

	@Test
	public void validarMarcasCuentasTrue() throws ExceptionLI, IOException {
		List<String> marcas = new ArrayList<>();
		marcas.add("Tiene Cuentas x Cobrar");
		boolean resp = utilities.validarMarcasCuentas(marcas);
		assertNotNull(resp);
		assertFalse(resp);
	}

	@Test
	public void validarMarcasCuentasFalse() throws ExceptionLI, IOException {
		List<String> marcas = new ArrayList<>();
		marcas.add("Cuenta CATS");
		marcas.add("Tiene Embargo");
		boolean resp = utilities.validarMarcasCuentas(marcas);
		assertNotNull(resp);
		assertTrue(resp);
	}


	@Test
	public void permitoDebitoCredito() throws ExceptionLI, IOException {
		LibreInversion libreInv = new LibreInversion();
		libreInv.setEsLibreInversion(true);
		Cuentas cuenta = new Cuentas();
		cuenta.setCuentaPermiteCreditos("Y");
		cuenta.setCuentaPermiteDebitos("Y");
		boolean resp = utilities.permiteDebitoyCredito(cuenta);
		assertNotNull(resp);
		assertTrue(resp);
	}

	@Test
	public void calculotasas() throws ExceptionLI, IOException {
		String oferta = "{\"cabecera\":{\"estadoTransaccion\":\"\",\"pasoFuncional\":\"paso1\",\"numeroSolicitud\":\"SUC_2020_126017\",\"extensionFuncional\":[{\"llave\":\"\",\"valor\":\"\"}]},\"oferta\":{\"informacionCliente\":{\"idSolicitante\":\"169507\",\"tipoDocumento\":\"FS001\",\"numeroDocumento\":\"190311162626\",\"nombreCompletoCliente\":\" Elena Madrid Melo\",\"direccionPrincipal\":\"AV 34 sur 21\",\"codigoCiudad\":\"5001000\",\"codigoDepartamento\":\"5\",\"codigoPais\":\"CO\",\"telefono\":\"3156397893\",\"celular\":\"3156397893\",\"correoElectronicoCliente\":\"mebusta@bancolombia.com.coumm\",\"codigoDireccionPrincipalFuente\":\"0074536896\"},\"producto\":[{\"idProducto\":\"14\",\"nombreProducto\":\"libre inversion\",\"montoOtorgado\":\"300000000.00\",\"categoria\":[{\"codigoCategoria\":\"Credito de Consumo\",\"subproducto\":[{\"idSubproducto\":\"486863\",\"nombreSubproducto\":\"PREAPROBADO DIGITAL LIBRE INV\",\"descripcionSubproducto\":\"PREAPROBADO DIGITAL LIBRE INV\",\"cupoMinimo\":1000000.0,\"cupoMaximo\":300000000.0,\"codigoImagen\":\"\",\"listaCondiciones\":[],\"comisionDisponibilidad\":0.0,\"isProductOwner\":false,\"planes\":[{\"idPlan\":\"P85\",\"factorSeguroDesempleo\":0.0,\"tasas\":[{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":12000000.0,\"montoSuperiorProducto\":29999999.0,\"plazoInferior\":61,\"plazoSuperior\":72,\"tasaMesVencida\":1.999917,\"tasaNominalAnualMesVencido\":0.23999,\"tasaEfectivaAnual\":0.2682,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":150000000.0,\"montoSuperiorProducto\":299999999.0,\"plazoInferior\":60,\"plazoSuperior\":60,\"tasaMesVencida\":1.999917,\"tasaNominalAnualMesVencido\":0.23999,\"tasaEfectivaAnual\":0.2682,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":60000000.0,\"montoSuperiorProducto\":149999999.0,\"plazoInferior\":24,\"plazoSuperior\":24,\"tasaMesVencida\":1.084321,\"tasaNominalAnualMesVencido\":0.13011,\"tasaEfectivaAnual\":0.1381,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":30000000.0,\"montoSuperiorProducto\":59999999.0,\"plazoInferior\":24,\"plazoSuperior\":24,\"tasaMesVencida\":1.164321,\"tasaNominalAnualMesVencido\":0.13971,\"tasaEfectivaAnual\":0.149,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":60000000.0,\"montoSuperiorProducto\":149999999.0,\"plazoInferior\":60,\"plazoSuperior\":60,\"tasaMesVencida\":1.999917,\"tasaNominalAnualMesVencido\":0.23999,\"tasaEfectivaAnual\":0.2682,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":12000000.0,\"montoSuperiorProducto\":29999999.0,\"plazoInferior\":24,\"plazoSuperior\":24,\"tasaMesVencida\":1.394321,\"tasaNominalAnualMesVencido\":0.16731,\"tasaEfectivaAnual\":0.1807,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":1000000.0,\"montoSuperiorProducto\":11999999.0,\"plazoInferior\":24,\"plazoSuperior\":24,\"tasaMesVencida\":1.504321,\"tasaNominalAnualMesVencido\":0.18051,\"tasaEfectivaAnual\":0.1962,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":30000000.0,\"montoSuperiorProducto\":59999999.0,\"plazoInferior\":60,\"plazoSuperior\":60,\"tasaMesVencida\":1.999917,\"tasaNominalAnualMesVencido\":0.23999,\"tasaEfectivaAnual\":0.2682,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":12000000.0,\"montoSuperiorProducto\":29999999.0,\"plazoInferior\":60,\"plazoSuperior\":60,\"tasaMesVencida\":1.999917,\"tasaNominalAnualMesVencido\":0.23999,\"tasaEfectivaAnual\":0.2682,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":1000000.0,\"montoSuperiorProducto\":11999999.0,\"plazoInferior\":60,\"plazoSuperior\":60,\"tasaMesVencida\":1.999917,\"tasaNominalAnualMesVencido\":0.23999,\"tasaEfectivaAnual\":0.2682,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":150000000.0,\"montoSuperiorProducto\":299999999.0,\"plazoInferior\":37,\"plazoSuperior\":48,\"tasaMesVencida\":1.999917,\"tasaNominalAnualMesVencido\":0.23999,\"tasaEfectivaAnual\":0.2682,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":60000000.0,\"montoSuperiorProducto\":149999999.0,\"plazoInferior\":37,\"plazoSuperior\":48,\"tasaMesVencida\":1.999917,\"tasaNominalAnualMesVencido\":0.23999,\"tasaEfectivaAnual\":0.2682,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":1000000.0,\"montoSuperiorProducto\":11999999.0,\"plazoInferior\":37,\"plazoSuperior\":48,\"tasaMesVencida\":1.999917,\"tasaNominalAnualMesVencido\":0.23999,\"tasaEfectivaAnual\":0.2682,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":30000000.0,\"montoSuperiorProducto\":59999999.0,\"plazoInferior\":37,\"plazoSuperior\":48,\"tasaMesVencida\":1.999917,\"tasaNominalAnualMesVencido\":0.23999,\"tasaEfectivaAnual\":0.2682,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":12000000.0,\"montoSuperiorProducto\":29999999.0,\"plazoInferior\":37,\"plazoSuperior\":48,\"tasaMesVencida\":1.999917,\"tasaNominalAnualMesVencido\":0.23999,\"tasaEfectivaAnual\":0.2682,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":150000000.0,\"montoSuperiorProducto\":299999999.0,\"plazoInferior\":36,\"plazoSuperior\":36,\"tasaMesVencida\":1.999917,\"tasaNominalAnualMesVencido\":0.23999,\"tasaEfectivaAnual\":0.2682,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":60000000.0,\"montoSuperiorProducto\":149999999.0,\"plazoInferior\":36,\"plazoSuperior\":36,\"tasaMesVencida\":1.999917,\"tasaNominalAnualMesVencido\":0.23999,\"tasaEfectivaAnual\":0.2682,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":30000000.0,\"montoSuperiorProducto\":59999999.0,\"plazoInferior\":36,\"plazoSuperior\":36,\"tasaMesVencida\":1.999917,\"tasaNominalAnualMesVencido\":0.23999,\"tasaEfectivaAnual\":0.2682,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":12000000.0,\"montoSuperiorProducto\":29999999.0,\"plazoInferior\":36,\"plazoSuperior\":36,\"tasaMesVencida\":1.999917,\"tasaNominalAnualMesVencido\":0.23999,\"tasaEfectivaAnual\":0.2682,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":1000000.0,\"montoSuperiorProducto\":11999999.0,\"plazoInferior\":36,\"plazoSuperior\":36,\"tasaMesVencida\":1.999917,\"tasaNominalAnualMesVencido\":0.23999,\"tasaEfectivaAnual\":0.2682,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":300000000.0,\"montoSuperiorProducto\":300000000.0,\"plazoInferior\":24,\"plazoSuperior\":24,\"tasaMesVencida\":0.994321,\"tasaNominalAnualMesVencido\":0.11931,\"tasaEfectivaAnual\":0.126,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"},{\"grupoRiesgo\":\"G1\",\"montoInferiorProducto\":150000000.0,\"montoSuperiorProducto\":299999999.0,\"plazoInferior\":24,\"plazoSuperior\":24,\"tasaMesVencida\":1.034321,\"tasaNominalAnualMesVencido\":0.12411,\"tasaEfectivaAnual\":0.1314,\"tasaMora\":24.04,\"dtf\":0,\"puntosDtf\":0,\"tipoTasa\":\"F\"}],\"seguroProducto\":[{\"montoMinimoSeguro\":1000000,\"montoMaximoSeguro\":20000000,\"factor\":0.00145},{\"montoMinimoSeguro\":20000001,\"montoMaximoSeguro\":50000000,\"factor\":0.00149},{\"montoMinimoSeguro\":50000001,\"montoMaximoSeguro\":100000000,\"factor\":0.00151},{\"montoMinimoSeguro\":100000001,\"montoMaximoSeguro\":200000000,\"factor\":0.00155},{\"montoMinimoSeguro\":200000001,\"montoMaximoSeguro\":500000000,\"factor\":0.00156}]}]}]}]}],\"tarjetasCredito\":{\"cicloFactura\":\"\",\"nombreRealce\":\"\"}}}";

		OfertaDigital ofertaDigital = new Gson().fromJson(oferta, OfertaDigital.class);

		ProductoCreditoDto res = utilities.obtenerCalculoSeguro(ofertaDigital, "1000000");
		assertEquals(new BigDecimal("1450.0"), res.getCostoTotalSeguro());
	}

	@Test
	public void getIpCliente() {
		HttpServletRequest http = Mockito.mock(HttpServletRequest.class);
		Mockito.when(http.getHeader(Mockito.anyString())).thenReturn("127.0.0.2");

		http.setAttribute("X-FORWARDED-FOR", "127.0.0.2");
		String rep = utilities.getIpCliente(http);
		assertEquals(rep, "127.0.0.2");
	}

	@Test
	public void getIpClienteNull() {
		HttpServletRequest http = Mockito.mock(HttpServletRequest.class);
		Mockito.when(http.getHeader(Mockito.anyString())).thenReturn(null);
		Mockito.when(http.getRemoteAddr()).thenReturn("127.0.0.2");
		http.setAttribute("X-FORWARDED-FOR", "127.0.0.2");
		String rep = utilities.getIpCliente(http);
		assertEquals(rep, "127.0.0.2");
	}

	@Test
	public void jsonGestionCredito() {
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new GestionCreditoConsumoRQ();
		gestionCreditoConsumoRQ.setPasoFuncional("pasoFun");
		gestionCreditoConsumoRQ.setPaso("paso2");
		gestionCreditoConsumoRQ.setTipodocumento("FS001");
		gestionCreditoConsumoRQ.setNumeroDocumento("12312312");
		gestionCreditoConsumoRQ.setTipoVenta("P");
		gestionCreditoConsumoRQ.setTasaFija(false);
		gestionCreditoConsumoRQ.setTasavariable(true);
		gestionCreditoConsumoRQ.setSucursalRadicacion("21312");
		gestionCreditoConsumoRQ.setCodigoReferido("1111");
		gestionCreditoConsumoRQ.setNumeroSolicitud("SUC_000001");
		gestionCreditoConsumoRQ.setNombreTarea("Tarea");
		gestionCreditoConsumoRQ.setCodigoSolicitante("222222222");
		gestionCreditoConsumoRQ.setIdSesion("1232312312312");
		List<ProductoCreditoDto> prd = new ArrayList<>();
		ProductoCreditoDto pr = new ProductoCreditoDto();
		pr.setPlazo(36);
		pr.setAceptaDebitoAutomatico(true);
		pr.setClaveDinamica(true);
		pr.setCupoElegido(new BigDecimal("15000000"));
		pr.setIdProducto("14");
		pr.setCuota(new BigDecimal("250000"));
		pr.setTasaMV(new BigDecimal("0.6543"));
		pr.setTasaNAMV(new BigDecimal("0.12243"));
		pr.setTasaEA(new BigDecimal("0.00145"));
		pr.setTasaMora(new BigDecimal("0.13"));
		pr.setDtf(new BigDecimal("0.654"));
		pr.setTipoTasa("0.6543");
		pr.setPuntosDtf(new BigDecimal("0.11"));
		pr.setIndicadorSubirCategoriaTDC(false);
		pr.setFactorSeguro(new BigDecimal("0.113"));
		pr.setCostoTotalSeguro(new BigDecimal("0.7777"));
		pr.setClaveDinamica(false);
		pr.setTipoCuentaDesembolso("1");
		pr.setCuentaDesembolso("14141441414141");
		pr.setAceptaDebitoAutomatico(false);
		pr.setTipoCuentaDebitoAutomatico("1");
		pr.setCuentaDebitoAutomatico("3333333333333");
		prd.add(pr);
		gestionCreditoConsumoRQ.setProducto(prd);

		String r = new Gson().toJson(gestionCreditoConsumoRQ);
		System.out.println(r);

	}

	@Test
	public void testFechaLetra() {
		String rep = utilities.getFechaActualLetra();
		assertNotNull(rep);
	}

	@Test
	public void decimal() {
		BigDecimal r = new BigDecimal("1.925").setScale(2, BigDecimal.ROUND_UP);
		System.out.println("respuesta: " + r);
	}

	@Test
	public void random() {
		for (int i = 0; i < 20; i++) {
			int idP = new Random().nextInt(3);
			System.out.println("respuesta " + i + ": " + idP);
		}

	}

	@Test
	public void aniosEntreFechas() {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime uno = format.parseDateTime("2019-05-06");
		DateTime dos = new DateTime();

		int anios = Years.yearsBetween(uno, dos).getYears();
		System.out.println(anios);

	}

	@Test
	public void doble() {
		String val = String.valueOf(new BigInteger("15000000000"));
		System.out.println(val);
	}

	@Test
	public void castli() {
		String liStr = "\r\n" + "{\r\n" + "    \"datosCliente\":\r\n" + "    {\r\n"
				+ "        \"tipoIdentificacion\": \"FS001\",\r\n"
				+ "        \"numeroIdentificacion\": \"23323232\",\r\n"
				+ "        \"fechaNacimiento\": \"1984-06-27\",\r\n"
				+ "        \"lugarNacimientoPersona\": \"5004000\",\r\n"
				+ "        \"lugarExpedicionCedula\": \"5001000\",\r\n"
				+ "        \"nombre\": \"Carlos Alberto Valderrama Palacios\",\r\n"
				+ "        \"codigoSegmento\": \"01\",\r\n" + "        \"codigoSubSegmento\": \"29\",\r\n"
				+ "        \"direcciones\": [\r\n" + "        {\r\n"
				+ "            \"llaveDireccion\": \"0184335383\",\r\n"
				+ "            \"llaveDireccionCif\": \"61022113134053\",\r\n"
				+ "            \"correspondencia\": \"X\",\r\n" + "            \"tipoDireccion\": \"Z001\",\r\n"
				+ "            \"direccion\": \"Carrera 34 # 34 - 34\",\r\n" + "            \"pais\": \"CO\",\r\n"
				+ "            \"departamento\": \"05\",\r\n" + "            \"ciudad\": \"5001000\",\r\n"
				+ "            \"celular\": \"3182392011\",\r\n" + "            \"telefonoFijo\": \"45860485\",\r\n"
				+ "            \"correoElectronico\": \"correodatosunicacioncliente@ibm.com\"\r\n" + "        },\r\n"
				+ "        {\r\n" + "            \"llaveDireccion\": \"0184335525\",\r\n"
				+ "            \"llaveDireccionCif\": \"170506145908037\",\r\n"
				+ "            \"tipoDireccion\": \"Z002\",\r\n"
				+ "            \"direccion\": \"KR 48C 16A 50 SUR BR EL POBLADO AP 1801\",\r\n"
				+ "            \"pais\": \"CO\",\r\n" + "            \"departamento\": \"05\",\r\n"
				+ "            \"ciudad\": \"5001000\",\r\n" + "            \"celular\": \"3183740996\",\r\n"
				+ "            \"telefonoFijo\": \"44178515\",\r\n"
				+ "            \"correoElectronico\": \"correodatosunicacioncliente@ibm.com\"\r\n" + "        }],\r\n"
				+ "        \"fechaEstadoVinculacion\": \"2000-03-12\"\r\n" + "    },\r\n"
				+ "    \"informacionSucursal\":\r\n" + "    {\r\n" + "        \"tipoConsulta\": \"3\",\r\n"
				+ "        \"codigoSucursal\": \"005\",\r\n" + "        \"numeroIdAsesor\": \"54654645\",\r\n"
				+ "        \"codigoAsesor\": \"10376446543\",\r\n" + "        \"codigoCRMSucursal\": \"00000066\",\r\n"
				+ "        \"numeroIdGerente\": \"69747747\",\r\n" + "        \"codigoCIFAsesor\": \"12333\",\r\n"
				+ "        \"codigoCIFGerente\": \"2222\",\r\n" + "        \"codigoCRMAsesor\": \"332221\",\r\n"
				+ "        \"codigoCRMGerente\": \"EM00001859\",\r\n" + "        \"tipoDocAsesor\": \"544545\",\r\n"
				+ "        \"tipoDocGerente\": \"FS001\",\r\n" + "        \"nombreCompleto\": \"prueba\",\r\n"
				+ "        \"usuarioRed\": \"prueba\"\r\n" + "    },\r\n" + "    \"informacionDocumentos\":\r\n"
				+ "    {\r\n" + "        \"aceptaTerminos\": false\r\n" + "    },\r\n"
				+ "    \"informacionDispositivo\":\r\n" + "    {\r\n" + "        \"deviceBrowser\": \"chrome\",\r\n"
				+ "        \"deviceOS\": \"windows\",\r\n"
				+ "        \"userAgent\": \"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36 Edg/83.0.478.45\",\r\n"
				+ "        \"sistemaOperativo\": \"windows\",\r\n"
				+ "        \"versionSistemaOperativo\": \"windows-10\",\r\n"
				+ "        \"dispositivo\": \"unknown\"\r\n" + "    },\r\n" + "    \"informacionBiometria\":\r\n"
				+ "    {\r\n" + "        \"reintentosValidacionNoexitosa\": \"0\"\r\n" + "    },\r\n"
				+ "    \"informacionCredito\":\r\n" + "    {\r\n"
				+ "        \"cupoAprobadoLibreInversion\": \"10000000\",\r\n" + "        \"idProducto\": \"21\",\r\n"
				+ "        \"montoSolicitado\": \"10000000\",\r\n" + "        \"isBeneficiarios\": false,\r\n"
				+ "        \"isDebitoAutomatico\": true,\r\n" + "        \"productoCredito\": [\r\n" + "        {\r\n"
				+ "            \"idProducto\": \"21\",\r\n" + "            \"idSeguro\": 0,\r\n"
				+ "            \"beneficiario\": [],\r\n" + "            \"seguros\": []\r\n" + "        }]\r\n"
				+ "    },\r\n" + "    \"informacionTransaccion\":\r\n" + "    {\r\n"
				+ "        \"pasoFuncional\": \"paso_li_107\",\r\n"
				+ "        \"idSesion\": \"JLT5M9sRA5bYHGPbUUk4YA\",\r\n"
				+ "        \"fechaHoraTransaccion\": \"\",\r\n" + "        \"ipCliente\": \"\",\r\n"
				+ "        \"tokenActual\": \"mitoken\"" + "    },\r\n" + "    \"cuentasDisponibles\": [\r\n"
				+ "    {\r\n" + "        \"id\": \"2-40671119007\",\r\n"
				+ "        \"descripcion\": \"40671119007\",\r\n" + "        \"diasInactividad\": 3\r\n" + "    },\r\n"
				+ "    {\r\n" + "        \"id\": \"2-40671118004\",\r\n"
				+ "        \"descripcion\": \"40671118004\",\r\n" + "        \"diasInactividad\": 34\r\n"
				+ "    }],\r\n" + "    \"preguntasAutenticacion\": [\r\n" + "    {\r\n"
				+ "        \"pregunta\": \" Fecha de Nacimiento\",\r\n" + "        \"respuesta\": \"1984-06-27\",\r\n"
				+ "        \"validacion\": \"true\"\r\n" + "    },\r\n" + "    {\r\n"
				+ "        \"pregunta\": \"Teléfono fijo\",\r\n" + "        \"respuesta\": \"44178515\",\r\n"
				+ "        \"validacion\": \"true\"\r\n" + "    },\r\n" + "    {\r\n"
				+ "        \"pregunta\": \"Tiempo de vinculación\",\r\n"
				+ "        \"respuesta\": \"Mayor a 5 años\",\r\n" + "        \"validacion\": \"true\"\r\n"
				+ "    }],\r\n" + "    \"esCrediagil\": false,\r\n" + "    \"esLibranza\": false,\r\n"
				+ "    \"esTarjetaCredito\": true,\r\n" + "    \"esLibreInversion\": false,\r\n"
				+ "    \"esMicroCredito\": false\r\n" + "}\r\n" + "";
		LibreInversion libre = new Gson().fromJson(liStr, LibreInversion.class);
		System.out.println(new Gson().toJson(libre));
	}

	@Test
	public void generarSharedKey() {
		assertEquals(utilities.generarSharedKey("FS001", "1223423"), "011223423");
	}

	@Test
	public void buscarTipoMensaje() {
		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		genClave.setCorreoElectronico("prueba@pru.com");
		genClave.setMetodoEnvioOTPODA(ConstantesLI.TIPO_MENSAJE_CORREO);

		assertEquals(utilities.buscarTipoMensaje(genClave), ConstantesLI.TIPO_MENSAJE_C);
	}

	@Test
	public void buscarTipoMensajeSMS() {
		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		genClave.setCorreoElectronico("prueba@pru.com");
		genClave.setMetodoEnvioOTPODA(ConstantesLI.TIPO_MENSAJE_TEXTO);

		assertEquals(utilities.buscarTipoMensaje(genClave), ConstantesLI.TIPO_MENSAJE_M);
	}

	@Test
	public void buscarTipoMensajeMIXTO() {
		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		genClave.setCorreoElectronico("prueba@pru.com");
		genClave.setMetodoEnvioOTPODA(ConstantesLI.TIPO_MENSAJE_MIXTO);

		assertEquals(utilities.buscarTipoMensaje(genClave), ConstantesLI.TIPO_MENSAJE_C);
	}

	@Test
	public void buscarTipoMensajeSTK() {
		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		genClave.setCorreoElectronico("prueba@pru.com");
		genClave.setMetodoEnvioOTPODA("");

		assertEquals(utilities.buscarTipoMensaje(genClave), ConstantesLI.TIPO_MENSAJE_C);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testsiguienteDiaPago28() {
		Date fecha = new java.sql.Date(2020 - 1900, 0, 29);
		String resp = utilities.siguienteDiaPago(fecha);
		assertEquals(resp, "2020-02-28");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testsiguienteDiaPago31() {
		Date fecha = new java.sql.Date(2020 - 1900, 2, 31);
		String resp = utilities.siguienteDiaPago(fecha);
		assertEquals(resp, "2020-04-30");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testsiguienteDiaPago30() {
		Date fecha = new java.sql.Date(2020 - 1900, 11, 31);
		String resp = utilities.siguienteDiaPago(fecha);
		assertEquals(resp, "2021-01-31");
	}

	@Test
	public void testfun() {
		List<Integer> numeros = Arrays.asList(18, 6, 4, 15, 55, 78, 12, 9, 8);
		Long result = numeros.stream().filter(num -> num > 10).count();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testfechaSiguientePagoColpensiones1_5() {
		Date fecha = new Date(2020 - 1900, 0, 1);
		String fechaPago = utilities.fechaSiguientePagoColpensiones(fecha);
		assertEquals(fechaPago, "05/04/2020");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testfechaSiguientePagoColpensiones6_30() {
		Date fecha = new Date(2020 - 1900, 0, 6);
		String fechaPago = utilities.fechaSiguientePagoColpensiones(fecha);
		assertEquals(fechaPago, "05/04/2020");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testfechaSiguientePagoColpensionesFinAno() {
		Date fecha = new Date(2020 - 1900, 10, 6);
		String fechaPago = utilities.fechaSiguientePagoColpensiones(fecha);
		assertEquals(fechaPago, "05/02/2021");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testfechaSiguientePagoColpensionesFinAno1_5() {
		Date fecha = new Date(2020 - 1900, 10, 5);
		String fechaPago = utilities.fechaSiguientePagoColpensiones(fecha);
		assertEquals(fechaPago, "05/02/2021");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testfechaSiguientePagoColpensionesFinAnoFeb() {
		Date fecha = new Date(2020 - 1900, 1, 28);
		String fechaPago = utilities.fechaSiguientePagoColpensiones(fecha);
		assertEquals(fechaPago, "05/05/2020");
	}


	@Test
	public void validarCaracteresInfoDispositivo1() {
		InformacionDispositivoDto infoDispositivo = new InformacionDispositivoDto();
		infoDispositivo.setDeviceBrowser("chrome-10");
		infoDispositivo.setDeviceOS("windows");
		infoDispositivo.setDispositivo("unknown");
		infoDispositivo.setIp("127.0.0.1");
		infoDispositivo.setSistemaOperativo("windows");
		utilities.validarCaracteresEspeciales(infoDispositivo);
	}

	@Test
	public void validarCaracteresInfoDispositivo2() {
		InformacionDispositivoDto infoDispositivo = new InformacionDispositivoDto();
		infoDispositivo.setDeviceBrowser("chrome");
		infoDispositivo.setDeviceOS("windows-11");
		infoDispositivo.setDispositivo("unknown");
		infoDispositivo.setIp("127.0.0.1");
		infoDispositivo.setSistemaOperativo("windows");
		utilities.validarCaracteresEspeciales(infoDispositivo);

	}


	@Test
	public void validarCaracteresInfoDispositivo3() {
		InformacionDispositivoDto infoDispositivo = new InformacionDispositivoDto();
		infoDispositivo.setDeviceBrowser("chrome");
		infoDispositivo.setDeviceOS("windows");
		infoDispositivo.setDispositivo("unknown-12");
		infoDispositivo.setIp("127.0.0.1");
		infoDispositivo.setSistemaOperativo("windows");
		utilities.validarCaracteresEspeciales(infoDispositivo);
	}

	@Test
	public void validarCaracteresInfoDispositivo4() {
		InformacionDispositivoDto infoDispositivo = new InformacionDispositivoDto();
		infoDispositivo.setDeviceBrowser("chrome");
		infoDispositivo.setDeviceOS("windows");
		infoDispositivo.setDispositivo("unknown");
		infoDispositivo.setIp("127.0.0.283");
		infoDispositivo.setSistemaOperativo("windows");
		utilities.validarCaracteresEspeciales(infoDispositivo);
	}


	@Test
	public void validarCaracteresInfoDispositivo5() {
		InformacionDispositivoDto infoDispositivo = new InformacionDispositivoDto();
		infoDispositivo.setDeviceBrowser("chrome");
		infoDispositivo.setDeviceOS("windows");
		infoDispositivo.setDispositivo("unknown");
		infoDispositivo.setIp("127.0.0.1");
		infoDispositivo.setSistemaOperativo("windows-10.2");
		utilities.validarCaracteresEspeciales(infoDispositivo);
	}

	@Test
	public void validarCaracteresInfoDispositivo() {
		InformacionDispositivoDto infoDispositivo = null;
		utilities.validarCaracteresEspeciales(infoDispositivo);
		assertNull(infoDispositivo);
	}


	@Test
	public void validarCaracteresInfoDispositivoDevicenull() {
		InformacionDispositivoDto infoDispositivo = new InformacionDispositivoDto();
		infoDispositivo.setDeviceBrowser(null);
		utilities.validarCaracteresEspeciales(infoDispositivo);
		assertNull(infoDispositivo.getDeviceBrowser());
	}

	@Test
	public void asignacionyNombreCompletoMatchTest() {
		gestionCreditoConsumoRQ = gsonTest.fromJson(ConstantesTest.beneficiarioMatch, GestionCreditoConsumoRQ.class);
		utilities.validarCaracteresGestionConsumoRQ(gestionCreditoConsumoRQ);
	}

	@Test
	public void asignacionyNombreCompletoNoMatchTest() {
		gestionCreditoConsumoRQ =  gsonTest.fromJson(ConstantesTest.beneficiarioNoMatch, GestionCreditoConsumoRQ.class);
		utilities.validarCaracteresGestionConsumoRQ(gestionCreditoConsumoRQ);

	}

	@Test
	public void beneficiarioDtoNullTest() {
		gestionCreditoConsumoRQ = gsonTest.fromJson("{ \"producto\": null }", GestionCreditoConsumoRQ.class);
		utilities.validarCaracteresGestionConsumoRQ(gestionCreditoConsumoRQ);

		assertNull(gestionCreditoConsumoRQ.getProducto());
	}
}

