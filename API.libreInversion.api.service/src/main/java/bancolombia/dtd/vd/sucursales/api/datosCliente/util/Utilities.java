package bancolombia.dtd.vd.sucursales.api.datosCliente.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;


import bancolombia.dtd.vd.li.dto.proxy.datosCliente.DireccionDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import bancolombia.dtd.vd.li.dto.proxy.datosCliente.RecuperarDatosBasicosClienteResponse;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRQ;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRS;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.OfertaDigital;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.SeguroProducto;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.Tasas;
import bancolombia.dtd.vd.li.commons.exception.ExceptionLI;
import bancolombia.dtd.vd.li.commons.util.CargadorPropiedades;
import bancolombia.dtd.vd.li.dto.catalogos.Catalogo;
import bancolombia.dtd.vd.li.dto.catalogos.Catalogos;
import bancolombia.dtd.vd.li.dto.proxy.clave.GeneracionClaveRQ;
import bancolombia.dtd.vd.li.dto.proxy.comun.Propiedad;
import bancolombia.dtd.vd.li.dto.proxy.consultarProductoDepositos.Cuentas;
import bancolombia.dtd.vd.li.dto.proxy.consultarProductoDepositos.CuentasRSApi;
import bancolombia.dtd.vd.li.dto.proxy.validacionpreguntas.ValidacionPreguntasRQ;
import bancolombia.dtd.vd.li.dto.utils.BeneficiarioDto;
import bancolombia.dtd.vd.li.dto.utils.InformacionDispositivoDto;
import bancolombia.dtd.vd.li.dto.utils.LibreInversion;
import bancolombia.dtd.vd.li.dto.utils.PreguntaDto;
import bancolombia.dtd.vd.li.dto.utils.ProductoCreditoDto;

public final class Utilities {
	public static final java.lang.String STANDARD_DATE_FORMAT = "yyyy-MM-dd";
	private static final Logger logger = LogManager.getLogger(Utilities.class);
	private static final long CONSTANTE_MILLON = 1_000_000;
	private static final long CONSTANTE_CIEN = 100;
	private static final Gson gson = new Gson();
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(STANDARD_DATE_FORMAT);

	protected static CargadorPropiedades propiedades = CargadorPropiedades.getInstance();

	private String rutaFileTemp = null;

	public String deviceBrowser;
	public String deviceOs;
	public String sistemaOperativo;
	public String dispositivo;

	public String asignacion;
	public String nombreCompleto;

	/**
	 * Metodo para realizar el calculo de cuota y seguro para los planes que llegan
	 * en la oferta de libre inversion y libranza
	 * 
	 * @param oferta            Oferta recibida para ofrecerle al cliente
	 * @param montoSolicitado   monto que desea el cliente
	 * @param plazoSeleccionado plazo que desea el cliente
	 * @return lista de prodcutos con los datos para los diferentes planes
	 * @throws ExceptionLI captura de excepciones en el flujo
	 */
	public List<ProductoCreditoDto> obtenerCalculoCuota(OfertaDigital oferta, String montoSolicitado,
			String plazoSeleccionado, CargadorPropiedades propiedades) throws ExceptionLI {

		List<ProductoCreditoDto> productos = new ArrayList<>();
		String[] productoLibranza = propiedades.getValue(ConstantesLI.VAL_API_COD_PRODUCTO_LIBRANZA).split(",");
		String[] conveniosLibranzaBanco = propiedades.getValue(ConstantesLI.CODIGO_LIBRANZA_BANCOLOMBIA).split(",");

		for (int i = 0; i < oferta.getOferta().getProducto().get(0).getCategoria().get(0).getSubproducto().get(0)
				.getPlanes().size(); i++) {
			ProductoCreditoDto tmp = obtenerCalculoCuotaUnitario(montoSolicitado, plazoSeleccionado,
					oferta.getOferta().getProducto().get(0).getCategoria().get(0).getSubproducto().get(0).getPlanes()
							.get(i).getTasas(),
					oferta.getOferta().getProducto().get(0).getCategoria().get(0).getSubproducto().get(0).getPlanes()
							.get(i).getSeguroProducto());
			tmp.setIdPlan(oferta.getOferta().getProducto().get(0).getCategoria().get(0).getSubproducto().get(0)
					.getPlanes().get(i).getIdPlan());
			if (i > 0 && oferta.getOferta().getInformacionCliente().getCodigoSegmento() != "9" ) {
				tmp.setCostoTotalSeguroDesempleo(BigDecimal.valueOf(Double.parseDouble(montoSolicitado) * Double
						.parseDouble(oferta.getOferta().getProducto().get(0).getCategoria().get(0).getSubproducto()
								.get(0).getPlanes().get(i).getFactorSeguroDesempleo().toPlainString())));
				tmp.setCuota(tmp.getCuota().add(tmp.getCostoTotalSeguroDesempleo()));
			}
			if (!(Arrays.asList(productoLibranza).contains(oferta.getOferta().getProducto().get(0).getIdProducto())
					&& Arrays.asList(conveniosLibranzaBanco)
							.contains(oferta.getOferta().getConvenio().getCodigoConvenio())
					&& i > 0)) {
				tmp.setIdSeguro(i);
				tmp.setNombreSeguro(ConstantesLI.getNombresSeguros()[i]);
				productos.add(tmp);
			}
		}

		return productos;
	}

	private ProductoCreditoDto obtenerCalculoCuotaUnitario(String montoSolicitado, String plazoSeleccionado,
			List<Tasas> tasas, List<SeguroProducto> seguros) throws ExceptionLI {
		ProductoCreditoDto producto = new ProductoCreditoDto();

		try {
			boolean valido = false;

			Double monto = Double.parseDouble(montoSolicitado);
			int plazo = Integer.parseInt(plazoSeleccionado);
			double tasaMV = 0;
			double factor = 0;

			boolean existePlazo = false;

			for (int i = 0; i < seguros.size(); i++) {
				if (monto >= seguros.get(i).getMontoMinimoSeguro() && monto <= seguros.get(i).getMontoMaximoSeguro()) {
					factor = (seguros.get(i).getFactor().doubleValue());
					valido = true;
					break;
				}
			}

			if (valido) {
				valido = false;

				for (int i = 0; i < tasas.size(); i++) {
					if (plazo == tasas.get(i).getPlazoSuperior()) {
						existePlazo = true;
					}
					if (monto >= tasas.get(i).getMontoInferiorProducto().doubleValue()
							&& monto <= tasas.get(i).getMontoSuperiorProducto().doubleValue()
							&& plazo == tasas.get(i).getPlazoSuperior()) {
						tasaMV = (tasas.get(i).getTasaMesVencida().doubleValue() / CONSTANTE_CIEN);
						double parte1 = monto * tasaMV;
						double parte2 = Math.pow((1 + tasaMV), plazo);
						double parte3 = Math.pow((1 + tasaMV), (plazo)) - 1;
						double parte4 = (monto * factor);
						double cuota = ((parte1 * parte2) / (parte3)) + parte4;
						producto.setCuota(BigDecimal.valueOf(cuota));
						producto.setCostoTotalSeguro(BigDecimal.valueOf(parte4));
						producto.setFactorSeguro(BigDecimal.valueOf(factor));
						producto.setTasaMV(BigDecimal.valueOf(tasas.get(i).getTasaMesVencida().doubleValue()));
						producto.setTasaEA(BigDecimal.valueOf(tasas.get(i).getTasaEfectivaAnual().doubleValue()));
						producto.setTasaMora(BigDecimal.valueOf(tasas.get(i).getTasaMora().doubleValue()));
						producto.setTasaNAMV(
								BigDecimal.valueOf(tasas.get(i).getTasaNominalAnualMesVencido().doubleValue()));

						if (tasas.get(i).getDtf() == null) {
							producto.setDtf(BigDecimal.valueOf(0));
						} else {
							producto.setDtf(BigDecimal.valueOf(tasas.get(i).getDtf().doubleValue()));
						}

						if (tasas.get(i).getPuntosDtf() == null) {
							producto.setPuntosDtf(BigDecimal.valueOf(0));
						} else {
							producto.setPuntosDtf(BigDecimal.valueOf(tasas.get(i).getPuntosDtf().doubleValue()));
						}

						valido = true;
						break;
					}
				}
			}
			if (!valido && !existePlazo) {
				throw new ExceptionLI("Error calculando plazo", null);
			}
		} catch (ExceptionLI e) {
			logger.error("Error calculando cuota::::" + e.getMessage(), e);
		}

		return producto;
	}

	/**
	 * Metodo para obtener el calculo del segudo para productos de Crediagil
	 * 
	 * @param oferta          Oferta que se va a procesar
	 * @param montoSolicitado monto para el que cual se calcula el seguro
	 * @return Clase con los datos del calculo del seguro
	 * @throws ExceptionLI Control de excepcion sobre el flujo
	 */
	public ProductoCreditoDto obtenerCalculoSeguro(OfertaDigital oferta, String montoSolicitado) throws ExceptionLI {
		ProductoCreditoDto producto = new ProductoCreditoDto();

		try {
			boolean valido = false;

			Double monto = Double.parseDouble(montoSolicitado);
			List<SeguroProducto> seguros = oferta.getOferta().getProducto().get(0).getCategoria().get(0)
					.getSubproducto().get(0).getPlanes().get(0).getSeguroProducto();
			producto.setComisionDisponibilidad(oferta.getOferta().getProducto().get(0).getCategoria().get(0)
					.getSubproducto().get(0).getComisionDisponibilidad());

			producto.setIdPlan(oferta.getOferta().getProducto().get(0).getCategoria().get(0).getSubproducto().get(0)
					.getPlanes().get(0).getIdPlan());
			double factor = 0;

			for (int i = 0; i < seguros.size(); i++) {
				if (monto >= seguros.get(i).getMontoMinimoSeguro() && monto <= seguros.get(i).getMontoMaximoSeguro()) {
					factor = (seguros.get(i).getFactor().doubleValue());
					valido = true;
					double parte4 = (CONSTANTE_MILLON * factor);
					producto.setFactorSeguro(BigDecimal.valueOf(factor));
					producto.setCostoTotalSeguro(BigDecimal.valueOf(parte4));
					producto.setNombreSeguro(ConstantesLI.getNombresSeguros()[ConstantesLI.CERO]);
					break;
				}
			}
			if (!valido) {
				throw new ExceptionLI("Error obtenerCalculoSeguro", null);
			}
		} catch (ExceptionLI e) {
			logger.error("Error obtenerCalculoSeguro::::" + e.getMessage(), e);
		}

		return producto;
	}

	public String getFechaActual() {
		Date myDate = new Date();

		return new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(myDate);
	}

	/**
	 * Metodo para calcular la fecha de proximo pago de un credito
	 * 
	 * @param fecha fecha inicial del desembolso
	 * @return fecha de proximo pago
	 */
	public String siguienteDiaPago(java.util.Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);

		int dia = cal.get(Calendar.DAY_OF_MONTH);
		int mes = cal.get(Calendar.MONTH) + 1;
		int anno = cal.get(Calendar.YEAR);

		Integer siguienteMes = mes + 1;
		// Validamos que el siguiente mes sea febrero
		if (mes == 1 && dia > 28) {
			return anno + "-02-28";
		}

		// Validamos que el siguiente mes no sea uno de 30 días
		else if ((siguienteMes == 4 || siguienteMes == 6 || siguienteMes == 9 || siguienteMes == 11) && dia == 31) {
			String mesTemporal = siguienteMes.toString();
			if (siguienteMes < 10) {
				mesTemporal = "0" + siguienteMes;
			}
			return anno + "-" + mesTemporal + "-30";
		}
		// Validamos que el mes presente es diciembre
		else if (mes == 12) {
			return (anno + 1) + "-" + "01" + "-" + dia;
		} else {
			String mesTemporal = siguienteMes.toString();
			if (siguienteMes < 10) {
				mesTemporal = "0" + siguienteMes;
			}
			return anno + "-" + mesTemporal + "-" + dia;
		}

	}

	public int diaDeCadaMes(java.util.Date fechaTransaccion) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaTransaccion);

		int dia = cal.get(Calendar.DAY_OF_MONTH);
		int mes = cal.get(Calendar.MONTH);

		int siguienteMes = mes + 1;
		// Validamos que el siguiente mes sea febrero
		if (mes == 1 && dia > 28) {
			return 28;
		}
		// Validamos que el siguiente mes no sea uno de 30 días
		else if ((siguienteMes == 4 || siguienteMes == 6 || siguienteMes == 9 || siguienteMes == 11) && dia == 31) {

			return 30;
		}
		return dia;
	}

	/**
	 * Metodo que permite generar la lista de variables de configuracion para envio
	 * de parametros a consumo back internos
	 * 
	 * @param propiedades CargadorPropiedades usado por la aplicacion de donde se
	 *                    sacaran las variables para el mapeo a construir
	 * @param varParam    lista de nombres de parametros separados por comas y que
	 *                    seran los utilizados para la construccion de la respuesta
	 * 
	 * @return List de parametros
	 */
	public List<Propiedad> crearListaConfiguracion(CargadorPropiedades propiedades, String varParam) {
		List<Propiedad> resp = new ArrayList<>();
		Propiedad para;
		String[] param = varParam.split(",");
		for (int i = 0; i < param.length; i++) {
			para = new Propiedad();
			para.setLlave(param[i]);
			para.setValor(propiedades.getValue(param[i]));
			resp.add(para);
		}
		return resp;
	}

	/**
	 * Metodo para validar si una cuenta de cliete permite debitos y creditos
	 * 
	 * @param cuenta Cuenta a evaludar
	 * @return resultado de la validacion
	 */
	public boolean permiteDebitoyCredito(Cuentas cuenta) {
		 return cuenta.getCuentaPermiteCreditos().equalsIgnoreCase(ConstantesLI.LETRA_Y)
					&& cuenta.getCuentaPermiteDebitos().equalsIgnoreCase(ConstantesLI.LETRA_Y);
	}

	/**
	 * Metodo para validar si las marcas de una cuenta de una cliente tiene alguna
	 * marca no permitida
	 * 
	 * @param marcas marcas que posee la cuenta a evaluar
	 * @return bolleano con la respuesta de si la cuenta tiene o no alguna de las
	 *         marcas no permitidas
	 */
	@SuppressWarnings("rawtypes")
	public boolean validarMarcasCuentas(List<String> marcas) {
		for (Iterator iterator = marcas.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			if (Arrays.asList(ConstantesLI.getMarcasnopermitidas()).contains(string)) {
				return true;
			}
		}
		return false;
	}



	/**
	 * Metodo para transformar un documento base 64 a un archivo tipo File
	 * 
	 * @param encodeStringB64 datos del documetos en base 64
	 * @param fileExtention   tipo de archivo a generar
	 * @return archivo procesado
	 */

	public File transformStringB64ToFile(String encodeStringB64, String fileExtention) {
		byte[] byteArrayFile = Base64.getDecoder().decode(encodeStringB64);
		File tempFile = null;
		Path tempFileDir = null;
		try {
			rutaFileTemp = propiedades.getValue(ConstantesLI.RUTA_FILE_TEMP);
			Path direct = Paths.get(rutaFileTemp);
			String sistemaOperativo = System.getProperty("os.name").toUpperCase();
			FileAttribute<Set<PosixFilePermission>> attributes = PosixFilePermissions.asFileAttribute(
					new HashSet<>(Arrays.asList(PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_READ)));
			if (sistemaOperativo.contains(ConstantesLI.SOWIND)) {
				tempFileDir = Files.createTempFile(direct, "tmp", ".".concat(fileExtention));
			} else {
				tempFileDir = Files.createTempFile(direct, "tmp", ".".concat(fileExtention), attributes);
			}
			tempFile = tempFileDir.toFile();
		} catch (IOException e) {
			logger.error("Error haciendo merge del documento::::" + e.getMessage(), e);
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(tempFile);
		} catch (FileNotFoundException e) {
			logger.error("Error haciendo merge del documento::::" + e.getMessage(), e);
		}
		try {
			fos.write(byteArrayFile);
			fos.close();
		} catch (IOException e) {
			logger.error("Error transformando bytes a File::::" + e.getMessage(), e);
		}
		return tempFile;
	}

	/**
	 * Metodo para unir varios archivos
	 * 
	 * @param listFiles     lista de archivos a unir
	 * @param fileExtention extension de archivo final
	 * @return archivo mezclado
	 */
	public File mergePDFFiles(List<File> listFiles, String fileExtention) {
		try {
			// Instantiating PDFMergerUtility class
			PDFMergerUtility PDFmerger = new PDFMergerUtility();
			File mergedFile;
			Path mergedFileDir = null;
			// Setting the destination file
			rutaFileTemp = propiedades.getValue(ConstantesLI.RUTA_FILE_TEMP);
			Path direct = Paths.get(rutaFileTemp);
			String sistemaOperativo = System.getProperty("os.name").toUpperCase();
			FileAttribute<Set<PosixFilePermission>> attributes = PosixFilePermissions.asFileAttribute(
					new HashSet<>(Arrays.asList(PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_READ)));
			if (sistemaOperativo.contains(ConstantesLI.SOWIND)) {
				mergedFileDir = Files.createTempFile(direct, "merged", ".".concat(fileExtention));
			} else {
				mergedFileDir = Files.createTempFile(direct, "merged", ".".concat(fileExtention), attributes);
			}
			mergedFile = mergedFileDir.toFile();
			PDFmerger.setDestinationFileName(mergedFile.getAbsolutePath());

			for (File file : listFiles) {
				PDFmerger.addSource(file);
			}

			PDFmerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
			return mergedFile;
		} catch (IOException e) {
			logger.error("Error haciendo merge del documento::::" + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Metodo para recuperar la IP de quien genera la peticion a los diferentes
	 * consumos sobre la API
	 * 
	 * @param requestContext request recibido de quien genera la peticion
	 * @return IP desde donde se realiza la peticion
	 */
	public String getIpCliente(HttpServletRequest requestContext) {
		String ipCliente = requestContext.getHeader("X-FORWARDED-FOR");
		if (ipCliente == null) {
			ipCliente = requestContext.getRemoteAddr();
		} else {
			ipCliente = ipCliente.contains(",") ? ipCliente.split(",")[0] : ipCliente;
		}
		
		if (ipCliente != null && !ipCliente.matches(ConstantesLI.REGINFODISPOSITIVO3)) {
			ipCliente = "127.0.0.1";
		}
		return ipCliente;
	}

	/**
	 * Metodo que mapea los datos del convenio para los productos de libranza y
	 * controla los convenio excluidos
	 * 
	 * @param propiedades      mapa de propiedades de la aplicacion donde se tienen
	 *                         los convenios que estan excluidos
	 * @param resp             Oferta que llega desde bizagi
	 * @param respuestaService respuesta que se retorna al front, esta es una
	 *                         referencia al metodo ppal
	 * @throws ExceptionLI control de algun problema en el mapeo y proceso de los
	 *                     datos
	 */
	public void mapearDatosLibranza(CargadorPropiedades propiedades, OfertaDigital resp,
			GestionCreditoConsumoRS respuestaService) throws ExceptionLI {
		if (resp.getOferta().getConvenio() != null) {
			respuestaService.setNombreConvenio(resp.getOferta().getConvenio().getNombreConvenio());
		}
	}

	/**
	 * Metodo para filtrar las cuentas de un cliente segun los requisitos
	 * funcionales establecidos como son que sea del titular, que no este cancelada,
	 * que permita debitos, creditos y no tenga alguna marca no permitida
	 * 
	 * @param cuentas          lista de cuentas a validar
	 * @param validacionMarcas si se aplican o no los filtros, siempre las
	 *                         canceladas seran excluidas
	 * @return lista de cuentas despues del filtro
	 */
	public List<CuentasRSApi> filtrarCuentas(List<Cuentas> cuentas, Boolean validacionMarcas, LibreInversion libreInversion) {
		List<CuentasRSApi> cuentasDesembolso = new ArrayList<>();

		for (Iterator<Cuentas> iterator = cuentas.iterator(); iterator.hasNext();) {
			Cuentas cuenta = iterator.next();

			if ((ConstantesLI.TITULARCUENTA.equalsIgnoreCase(cuenta.getRelacionClienteCuenta())
					&& ConstantesLI.CUENTACANCELADA.equalsIgnoreCase(cuenta.getIndicadorCuentaCancelada())
					&& !validarMarcasCuentas(cuenta.getMarcas()) && permiteDebitoyCredito(cuenta))
					|| (!validacionMarcas
							&& ConstantesLI.CUENTACANCELADA.equalsIgnoreCase(cuenta.getIndicadorCuentaCancelada()))) {
				CuentasRSApi temp = new CuentasRSApi();
				String tipoTemp = cuenta.getTipoCuenta().trim().equalsIgnoreCase(ConstantesLI.CUENTA_AHORROS_LETRA)
						? ConstantesLI.CUENTA_AHORROS_NUM
						: ConstantesLI.CUENTA_CORRIENTE_NUM;
				temp.setId(tipoTemp + "-" + cuenta.getNumeroCuenta().trim());
				temp.setDescripcion(cuenta.getNumeroCuenta().trim());
				temp.setDiasInactividad(Integer.parseInt(cuenta.getNumeroDiasInactividadCuenta()));
				cuentasDesembolso.add(temp);
			}
		}

		Collections.sort((List<CuentasRSApi>) cuentasDesembolso);
		return cuentasDesembolso;
	}

	/**
	 * Método que crea la fecha actual en formato, ejm: '21 de Abril de 2020'
	 * 
	 * @return String con fecha actual
	 */
	public String getFechaActualLetra() {
		Calendar c1 = new GregorianCalendar(new Locale("es", "ES"));
		String mes = new SimpleDateFormat("MMMM", new Locale("es", "ES")).format(c1.getTime());
		String mesActual = mes.substring(0, 1).toUpperCase() + mes.substring(1);
		return c1.get(Calendar.DAY_OF_MONTH) + " de " + mesActual + " de " + c1.get(Calendar.YEAR);
	}

	/**
	 * Metodo encargado de generar las preguntas de seguridad que seran usadas en el
	 * front
	 * 
	 * @param libreInversion carga util con los datos base para la generacion de
	 *                       preguntas
	 * @param propiedades    propiedades de la aplicacion
	 * @return lista de preguntas de seguridad
	 */

	public List<PreguntaDto> generacionPreguntas(LibreInversion libreInversion, CargadorPropiedades propiedades,
			CallRestServiceBack callRestServiceBack) {
		List<PreguntaDto> respuestaPreguntas = new ArrayList<>();

		String[] preguntasCat1 = propiedades.getValue(ConstantesLI.PREGUNTAS_CAT_1).split(",");
		String[] preguntasCat2 = propiedades.getValue(ConstantesLI.PREGUNTAS_CAT_2).split(",");
		String[] preguntasCat3 = propiedades.getValue(ConstantesLI.PREGUNTAS_CAT_3).split(",");

		PreguntaDto pr = null;
		for (int i = 0; i < ConstantesLI.INTENTOS_PREGUNTAS; i++) {
			pr = generarPreguntaCategoria1(libreInversion, propiedades, preguntasCat1, callRestServiceBack);
			if (StringUtils.isNotBlank(pr.getRespuesta())) {
				respuestaPreguntas.add(pr);
				break;
			}
		}

		for (int i = 0; i < ConstantesLI.INTENTOS_PREGUNTAS; i++) {
			pr = generarPreguntaCategoria2(libreInversion, propiedades, preguntasCat2);
			if (StringUtils.isNotBlank(pr.getRespuesta())) {
				respuestaPreguntas.add(pr);
				break;
			}
		}

		for (int i = 0; i < ConstantesLI.INTENTOS_PREGUNTAS; i++) {
			pr = generarPreguntaCategoria3(libreInversion, propiedades, preguntasCat3);
			if (StringUtils.isNotBlank(pr.getRespuesta())) {
				respuestaPreguntas.add(pr);
				break;
			}
		}
		return respuestaPreguntas;
	}

	private PreguntaDto generarPreguntaCategoria1(LibreInversion libreInversion, CargadorPropiedades propiedades,
			String[] preguntasCat1, CallRestServiceBack callRestServiceBack) {
		PreguntaDto resp = new PreguntaDto();
		int idP = new SecureRandom().nextInt(preguntasCat1.length);
		String[] preguntaSelec = preguntasCat1[idP].split(":");

		int pregunta = Integer.parseInt(preguntaSelec[1]);
		resp.setPregunta(preguntaSelec[0]);

		switch (pregunta) {
		// Número de cédula - Recuperar datos basicos
		case ConstantesLI.UNO:
			resp.setRespuesta(libreInversion.getDatosCliente().getNumeroIdentificacion());
			break;
		// Fecha de Nacimiento - Recuperar datos basicos
		case ConstantesLI.DOS:
			resp.setRespuesta(libreInversion.getDatosCliente().getFechaNacimiento());
			break;
		// Lugar de Nacimiento - Recuperar datos basicos
		case ConstantesLI.TRES:
			resp.setRespuesta(obtenerNombreCiudad(libreInversion.getDatosCliente().getLugarNacimientoPersona(),
					propiedades, callRestServiceBack));
			break;
		// Ciudad de Expedición de la cedula - Recuperar datos basicos
		case ConstantesLI.CUATRO:
			resp.setRespuesta(obtenerNombreCiudad(libreInversion.getDatosCliente().getLugarExpedicionCedula(),
					propiedades, callRestServiceBack));
			break;
		default:
			resp.setRespuesta(null);
			break;
		}

		return resp;
	}

	private PreguntaDto generarPreguntaCategoria2(LibreInversion libreInversion, CargadorPropiedades propiedades,
			String[] preguntasCat2) {
		PreguntaDto resp = new PreguntaDto();
		int idP = new SecureRandom().nextInt(preguntasCat2.length);

		String[] preguntaSelec = preguntasCat2[idP].split(":");

		List<DireccionDto> direcciones =  libreInversion.getDatosCliente().getDirecciones();
		int posicionDireccionObjetivo = new SecureRandom().nextInt(direcciones.size());

		int pregunta = Integer.parseInt(preguntaSelec[1]);
		resp.setPregunta(preguntaSelec[0]);
		boolean responseFound = false;
		for(int i=0; i < libreInversion.getDatosCliente().getDirecciones().size(); i++){
			switch (pregunta) {
			// Direccion de residencia - Recuperar datos ubicación
			case ConstantesLI.ONCE:
					resp.setRespuesta(libreInversion.getDatosCliente().getDirecciones().get(i).getDireccion());
					responseFound = true;
					break;
			// Teléfono de residencia - Recuperar datos ubicación
			case ConstantesLI.DOCE:
					resp.setRespuesta(libreInversion.getDatosCliente().getDirecciones().get(i).getTelefonoFijo());
					responseFound = true;
					break;
			// Teléfono fijo - Recuperar datos ubicación
			case ConstantesLI.TRECE:
					resp.setRespuesta(
							libreInversion.getDatosCliente().getDirecciones().get(i).getCelular());
					responseFound = true;
					break;
			// Correo Electrónico - Recuperar datos ubicación
			case ConstantesLI.CATORCE:
					resp.setRespuesta(
							libreInversion.getDatosCliente().getDirecciones().get(i).getCorreoElectronico());
					responseFound = true;
					break;
			default:
				resp.setRespuesta(null);
				break;
			}
			if(responseFound && !StringUtils.isBlank(resp.getRespuesta())){
				break;
			}
		}
		return resp;
	}

	private PreguntaDto generarPreguntaCategoria3(LibreInversion libreInversion, CargadorPropiedades propiedades,
			String[] preguntasCat3) {
		PreguntaDto resp = new PreguntaDto();
		int idP = new SecureRandom().nextInt(preguntasCat3.length);

		String[] preguntaSelec = preguntasCat3[idP].split(":");

		int pregunta = Integer.parseInt(preguntaSelec[1]);
		resp.setPregunta(preguntaSelec[0]);

		switch (pregunta) {
		// Tiempo de vinculación - Se debe calcular del campo: FechaEstadoVinculacion
		case ConstantesLI.VEINTIUNO:
			resp.setRespuesta(calcularFechaVinculacion(libreInversion.getDatosCliente().getFechaEstadoVinculacion()));
			break;
		// Número de cuenta - servicio cosulta producto depositos
		case ConstantesLI.VEINTIDOS:
			String cuentasT = "";
			for (Iterator<CuentasRSApi> iterator = libreInversion.getCuentasDisponibles().iterator(); iterator
					.hasNext();) {
				CuentasRSApi cuenta = iterator.next();
				if (StringUtils.isBlank(cuentasT)) {
					cuentasT += cuenta.getDescripcion();
				} else {
					cuentasT += " - " + cuenta.getDescripcion();
				}
			}
			if (StringUtils.isBlank(cuentasT)) {
				cuentasT = ConstantesLI.MSG_SIN_CUENTAS;
				resp.setRespuesta(cuentasT);
			} else {
				resp.setRespuesta(cuentasT);
			}
			break;
		// Año apertura de la cuenta
		case ConstantesLI.VEINTITRES:
			resp.setRespuesta(null);
			break;
		// 6 últimos dígitos de la tarjeta debito
		case ConstantesLI.VEINTICUATRO:
			resp.setRespuesta(null);
			break;
		default:
			resp.setRespuesta(null);
			break;
		}

		return resp;

	}

	private String calcularFechaVinculacion(String fechaEstadoVinculacion) {
		try {
			if (StringUtils.isNotBlank(fechaEstadoVinculacion)) {
				DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
				DateTime finicial = format.parseDateTime(fechaEstadoVinculacion);
				DateTime ffinal = new DateTime();
				int aniosVinc = Years.yearsBetween(finicial, ffinal).getYears();
				if (aniosVinc <= ConstantesLI.CERO) {
					return "Menor a un año";
				} else if (aniosVinc > ConstantesLI.CERO && aniosVinc <= ConstantesLI.TRES) {
					return "Entre 1 y 3 años";
				} else if (aniosVinc > ConstantesLI.TRES && aniosVinc <= ConstantesLI.CINCO) {
					return "Entre 3 y 5 años";
				} else if (aniosVinc > ConstantesLI.CINCO) {
					return "Mayor a 5 años";
				}
			}
		} catch (IllegalArgumentException | UnsupportedOperationException e) {
			String mensaje = "Error en calcularFechaVinculacion: " + e.getMessage();
			logger.error(mensaje, e);
		}
		return null;
	}

	private String obtenerNombreCiudad(String codigoCuidad, CargadorPropiedades propiedades,
			CallRestServiceBack callRestServiceBack) {
		String ciudad = "";
		String respuestaCat;
		try {
			if (StringUtils.isNotBlank(codigoCuidad)) {
				respuestaCat = callRestServiceBack.llamadoBackInternoCatalogo("{\"idcatalogo\" : \"4\"}",
						propiedades.getValue("API_ENDPOINT_CALL_CATALOGOS") + ConstantesLI.SERVICIO_CATALOGOS);
				Catalogos cat = gson.fromJson(respuestaCat, Catalogos.class);
				for (Catalogo catalogo : cat.getLista()) {
					if (catalogo.getId().split("\\|")[0].equalsIgnoreCase(codigoCuidad)) {
						ciudad = catalogo.getDescripcion();
						break;
					}
				}
			}
		} catch (ExceptionLI | IOException e) {
			String mensaje = "Error consultando el catálogo ciudad " + e.getMessage();
			logger.error(mensaje, e);
		}
		return ciudad;
	}

	public boolean validarPreguntas(ValidacionPreguntasRQ validacionPreguntasRQ) {

		for (Iterator<?> iterator = validacionPreguntasRQ.getPreguntas().iterator(); iterator.hasNext();) {
			PreguntaDto type = (PreguntaDto) iterator.next();
			if (type.getValidacion().equalsIgnoreCase(ConstantesLI.ERROR_RESPUESTA)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param respuestaService
	 * @param resp
	 */
	public void obtenerPlazosCredito(GestionCreditoConsumoRS respuestaService, OfertaDigital resp) {
		List<Integer> plazos = new ArrayList<>();
		for (Tasas listaTasas : resp.getOferta().getProducto().get(0).getCategoria().get(0).getSubproducto().get(0)
				.getPlanes().get(0).getTasas()) {
			plazos.add(listaTasas.getPlazoSuperior());
		}
		Set<Integer> hs = new HashSet<>();
		hs.addAll(plazos);
		plazos.clear();
		plazos.addAll(hs);
		Collections.sort(plazos);
		respuestaService.setPlazos(plazos);
	}

	public String generarSharedKey(String tipoDocumento, String numerodocumento) {
		return tipoDocumento.substring(tipoDocumento.length() - 2, tipoDocumento.length()) + numerodocumento;
	}

	public String buscarTipoMensaje(GeneracionClaveRQ generacionClaveRQ) {
		if (StringUtils.isNotBlank(generacionClaveRQ.getMetodoEnvioOTPODA())) {
			if (generacionClaveRQ.getMetodoEnvioOTPODA().equals(ConstantesLI.TIPO_MENSAJE_TEXTO)) {
				return ConstantesLI.TIPO_MENSAJE_M;
			} else if (generacionClaveRQ.getMetodoEnvioOTPODA().equals(ConstantesLI.TIPO_MENSAJE_CORREO)) {
				return ConstantesLI.TIPO_MENSAJE_C;
			} else if (generacionClaveRQ.getMetodoEnvioOTPODA().equals(ConstantesLI.TIPO_MENSAJE_MIXTO)) {
				return ConstantesLI.TIPO_MENSAJE_C;
			}
		} else if (generacionClaveRQ.getMecanismo().equals(ConstantesLI.VAR_CLAVE_SOFTOKEN)) {
			return ConstantesLI.TIPO_MENSAJE_C;
		}
		return null;
	}

	/**
	 * 
	 * @return {@link XMLGregorianCalendar}
	 * @throws ParseException
	 * @throws DatatypeConfigurationException
	 */
	public static String xmlGregorianCalendar2String(XMLGregorianCalendar xmlGregorianCalendar) {
		String result = null;
		if (null != xmlGregorianCalendar) {
			Date date = xmlGregorianCalendar.toGregorianCalendar().getTime();
			result = DATE_FORMAT.format(date);
		}
		return result;
	}

	public RecuperarDatosBasicosClienteResponse transformarRespuestaDatosCliente(
			com.grupobancolombia.intf.clientes.gestionclientes.recuperardatoscliente.v4.RecuperarDatosBasicosClienteResponse rep) {
		RecuperarDatosBasicosClienteResponse salida;

		XMLGregorianCalendar estadoVinculacion = rep.getFechaEstadoVinculacion();
		rep.setFechaEstadoVinculacion(null);
		XMLGregorianCalendar fechaCambioEstado = rep.getFechaCambioEstado();
		rep.setFechaCambioEstado(null);
		XMLGregorianCalendar fechaExpedicionCed = rep.getInformacionExpedicionIdentificacion() != null
				? rep.getInformacionExpedicionIdentificacion().getFechaExpedicion()
				: null;
		if (rep.getInformacionExpedicionIdentificacion() != null) {
			rep.getInformacionExpedicionIdentificacion().setFechaExpedicion(null);
		}
		XMLGregorianCalendar fechaNacimiento = rep.getInformacionNacimientoCliente() != null
				? rep.getInformacionNacimientoCliente().getFecha()
				: null;
		if (rep.getInformacionNacimientoCliente() != null) {
			rep.getInformacionNacimientoCliente().setFecha(null);
		}
		XMLGregorianCalendar fechaUltimaActualizacion = rep.getFechaUltimaActualizacion();
		rep.setFechaUltimaActualizacion(null);

		salida = gson.fromJson(gson.toJson(rep), RecuperarDatosBasicosClienteResponse.class);
		salida.setFechaEstadoVinculacion(xmlGregorianCalendar2String(estadoVinculacion));
		salida.setFechaCambioEstado(xmlGregorianCalendar2String(fechaCambioEstado));
		if (rep.getInformacionExpedicionIdentificacion() != null) {
			salida.getInformacionExpedicionIdentificacion()
					.setFechaExpedicion(xmlGregorianCalendar2String(fechaExpedicionCed));
		}
		if (rep.getInformacionNacimientoCliente() != null) {
			salida.getInformacionNacimientoCliente().setFecha(xmlGregorianCalendar2String(fechaNacimiento));
		}
		salida.setFechaUltimaActualizacion(xmlGregorianCalendar2String(fechaUltimaActualizacion));
		return salida;
	}

	/**
	 * Fechadepagodelaprimeracuota: (día=5; mes= mes de desembolso más 3 meses;
	 * Fecha de desembolso + 3 meses (tomar año)
	 * 
	 * @param fechaDesembolso fechaHoraTransaccion
	 * @return fecha de primer pago aplicando la regla de 3 meses para iniciar pagos
	 *         el dia 5 de cada mes.
	 */
	public String fechaSiguientePagoColpensiones(Date fechaDesembolso) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaDesembolso);

		cal.add(Calendar.MONTH, 3);

		return "05/" + StringUtils.leftPad((cal.get(Calendar.MONTH) + 1) + "", 2, "0") + "/" + cal.get(Calendar.YEAR);
	}

	/**
	 * Validacion de caracteres Especiales sobre el objeto Informacion Dispositivo
	 **/
	public void validarCaracteresEspeciales(InformacionDispositivoDto infoDispositivo) {

		if (infoDispositivo != null) {
			if (infoDispositivo.getDeviceBrowser() != null
					&& !infoDispositivo.getDeviceBrowser().matches(ConstantesLI.REGINFODISPOSITIVO1)) {
				deviceBrowser = infoDispositivo.getDeviceBrowser().replaceAll(ConstantesLI.REGINFODISPOSITIVO1A, "");
				infoDispositivo.setDeviceBrowser(deviceBrowser);
			}
			if (infoDispositivo.getDeviceOS() != null
					&& !infoDispositivo.getDeviceOS().matches(ConstantesLI.REGINFODISPOSITIVO1)) {
				deviceOs = infoDispositivo.getDeviceOS().replaceAll(ConstantesLI.REGINFODISPOSITIVO1A, "");
				infoDispositivo.setDeviceOS(deviceOs);
			}
			if (infoDispositivo.getSistemaOperativo() != null
					&& !infoDispositivo.getSistemaOperativo().matches(ConstantesLI.REGINFODISPOSITIVO1)) {
				sistemaOperativo = infoDispositivo.getSistemaOperativo().replaceAll(ConstantesLI.REGINFODISPOSITIVO1A,
						"");
				infoDispositivo.setSistemaOperativo(sistemaOperativo);
			}
			if (infoDispositivo.getDispositivo() != null
					&& !infoDispositivo.getDispositivo().matches(ConstantesLI.REGINFODISPOSITIVO2)) {
				dispositivo = infoDispositivo.getDispositivo().replaceAll(ConstantesLI.REGINFODISPOSITIVO2A, "");
				infoDispositivo.setDispositivo(dispositivo);
			}
			if (infoDispositivo.getIp() != null && !infoDispositivo.getIp().matches(ConstantesLI.REGINFODISPOSITIVO3)) {
				infoDispositivo.setIp("127.0.0.1");
			}
		}
	}

	/**
	 * Validacion de caracteres especiales sobre el DTO GestionCreditoConsumoRQ
	 */
	public void validarCaracteresGestionConsumoRQ(GestionCreditoConsumoRQ gestionCreditoConsumoRQ) {

		if (gestionCreditoConsumoRQ != null && gestionCreditoConsumoRQ.getProducto() != null) {
			for (ProductoCreditoDto productoCreditoDto : gestionCreditoConsumoRQ.getProducto()) {
				if (productoCreditoDto.getBeneficiario() != null) {
					for (BeneficiarioDto beneficiarioDto : productoCreditoDto.getBeneficiario()) {
						if (beneficiarioDto.getAsignacion() != null && !beneficiarioDto.getAsignacion().toString()
								.matches(ConstantesLI.REGEXMATCHASIGNACION)) {
							asignacion = beneficiarioDto.getAsignacion().toString()
									.replaceAll(ConstantesLI.REGEXREPLACEASIGNACION, "");
							beneficiarioDto.setAsignacion(new BigDecimal(asignacion));
						}
						if (beneficiarioDto.getNombreCompleto() != null && !beneficiarioDto.getNombreCompleto()
								.matches(ConstantesLI.REGEXMATCHNOMBRECOMPLETO)) {
							nombreCompleto = beneficiarioDto.getNombreCompleto()
									.replaceAll(ConstantesLI.REGEXREPLACENOMBRECOMPLETO, "");
							beneficiarioDto.setNombreCompleto(nombreCompleto);
						}
					}
				}
			}
		}
	}

}