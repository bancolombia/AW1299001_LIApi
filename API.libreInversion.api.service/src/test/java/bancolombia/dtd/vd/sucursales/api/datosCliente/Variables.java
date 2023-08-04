package bancolombia.dtd.vd.sucursales.api.datosCliente;

import java.util.HashMap;
import java.util.Map;

public class Variables {
	private static Map<String, String> propiedades;
	private static Map<String, String> propiedades2;
	private static Map<String, String> propiedades3;
	private static Map<String, String> propiedadesProspecto;

	private static void InicializarPropiedades() {
		propiedades = new HashMap<String, String>() {
			{
				put("API_DATOS_COMERCIALES_SUCURSAL_COD_ASESOR", "true");
				put("API_GESTION_PRECALCULADO_CLIENTE_TIPO_PRODUCTO", "4,15,14");
				put("VAL_TIEMPO_ESPERA_INICIAL_GESTION_CRED", "5");
				put("VAL_TIEMPO_REINTENTO_GESTION_CRED", "5");
				put("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_TAREA_M1", "EsperarInformacionFinal");
				put("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_TAREA_M2", "EsperarAceptacionCliente");
				put("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_ARCHIVO_TRAZA",
						"TrazaLibreInversionSucursales.txt");
				put("VAL_API_ERRORES_ACTIVO", "true");
				put("VAL_API_ERRORES_COD_APLICACION", "99");
				put("MSG_DEFAULT_API", "En este momento el sistema no está disponible. Por favor intenta más tarde.");
				put("API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE", "API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE");
				put("VAL_API_COD_PRODUCTO_CREDIAGIL", "13,47");
				put("VAL_API_COD_PRODUCTO_LIBRANZA", "4,48");
				put("VAL_API_COD_PRODUCTO_LIBRANZA", "4");
				put("COD_PRD_SIN_TASAS", "13,47,12");
				put("VAL_API_COD_PRODUCTO_TDC", "12,49");
				put("VALIDAR_SUCURSAL_PILOTO", "false");
				put("FWK_ENVIAR_CORREO_NOMBRE_PLANTILLA", "pdf_plantillacorreopdf.vm");
				put("FWK_ENVIAR_CORREO_RUTA_PLANTILLA_LIBREINV_SUCURSALES",
						"/b1c/wrkdirr/ventasDigitales/plantillas/plantillaLibreInversionSuc");
				put("FWK_ENVIAR_CORREO_RUTA_PLANTILLA_CREDIAGIL_SUCURSALES",
						"/b1c/wrkdirr/ventasDigitales/plantillas/plantillaCrediAgilSuc");
				put("FWK_ENVIAR_CORREO_RUTA_PLANTILLA_LIBRANZA_SUCURSALES",
						"/b1c/wrkdirr/ventasDigitales/plantillas/plantillaLibranzaSuc");
				put("VALIDAR_CARACTERES_NOMBRE", "true");
				put("API_ENDPOINT_ERROR_BYCODE", "error");
				put("API_ENDPOINT_CALL_AUTENTICACION", "auth");
				put("PROXY_ENDPOINT_ENVIO_CORREO_ELECTRONICO", "mail");
				put("CODIGO_LIBRANZA_BANCOLOMBIA", "70337");
				put("PREGUNTAS_CAT_1", "Número de cédula:1, Fecha de Nacimiento:2, Lugar de Nacimiento:3, Ciudad de Expedición de la cedula:4");
				put("PREGUNTAS_CAT_2", "Direccion de residencia:11,Teléfono de residencia:12,Teléfono fijo:13,Correo Electrónico:14");
				put("PREGUNTAS_CAT_3", "Tiempo de vinculación:21,Número de cuenta:22");//,Año apertura de la cuenta:23,6 últimos dígitos de la tarjeta debito:24
				put("RUTA_FILE_TEMP","C:/tmp/");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED", "20");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED_MOM1","30");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED_MOM2","50");
				put("VAL_API_COD_PRODUCTO_LIBRANZA_COLPENSIONES","53");
				put("VAL_API_COD_PRODUCTO_LIBRANZA","4");
				put("VAL_API_COD_PRODUCTO_CREDIAGIL","13");
				put("API_ESTUDIO_CREDITO_CONSUMO_PRODUCTO_ANCLA","31,34,35,38,39,41,42,46,49,50,51,52,53,54,56,60,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,136,137,138,139,140,141,142,143,144,186,187,201,272,313,ZFS1101600010,ZFS1101600016,ZFS1101600008,ZFS1101600005,ZFS1101600012,ZFS1101600013,ZFS1103900003");
				put("VAL_SHOW_LIBRANZA","true");

			}
		};
	}

	private static void InicializarPropiedades2() {
		propiedades2 = new HashMap<String, String>() {
			{
				put("API_DATOS_COMERCIALES_SUCURSAL_COD_ASESOR", "false");
				put("API_GESTION_PRECALCULADO_CLIENTE_TIPO_PRODUCTO", "14,12");
				put("VAL_TIEMPO_ESPERA_INICIAL_GESTION_CRED", "5");
				put("VAL_TIEMPO_REINTENTO_GESTION_CRED", "5");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED", "20");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED_MOM1","30");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED_MOM2","50");				put("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_TAREA_M1", "EsperarInformacionFinal");
				put("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_TAREA_M2", "EsperarAceptacionCliente");
				put("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_ARCHIVO_TRAZA",
						"TrazaLibreInversionSucursales.txt");
				put("VAL_API_ERRORES_ACTIVO", "true");
				put("VAL_API_ERRORES_COD_APLICACION", "99");
				put("MSG_DEFAULT_API", "En este momento el sistema no está disponible. Por favor intenta más tarde.");
				put("API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE", "API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE");
				put("VAL_API_COD_PRODUCTO_CREDIAGIL", "13,47");
				put("COD_PRD_SIN_TASAS", "13,47,12");
				put("VAL_API_COD_PRODUCTO_TDC", "12,49");
				put("VALIDAR_SUCURSAL_PILOTO", "true");
				put("SUCURSALES_HABILITADAS", "862, 915, 511");
				put("VALIDAR_CARACTERES_NOMBRE", "false");
				put("API_ENDPOINT_ERROR_BYCODE", "/error/");
				put("API_ENDPOINT_CALL_AUTENTICACION", "/auth/");
				put("PROXY_ENDPOINT_ENVIO_CORREO_ELECTRONICO", "/mail/");
				
				put("CLAVE_SHARED_KEY", "2312");
				put("CLAVE_IDENTIFICACION_CANAL", "VDI");
				put("CLAVE_TIPO_MENSAJE", "SMS");
				put("CLAVE_COD_OPERACION", "1");
				put("CLAVE_ASUNTO", "Clave OTP");
				put("CLAVE_CONTENIDO_MENSAJE", "Nueva clave dinamica");
				put("CLAVE_COD_APLICACION", "VENTASDIGI");
				put("TOKEN_ID_SERVIDOR", "OTP");
				put("TOKEN_SISTEMA_FUENTE", "VTD");
				put("TOKEN_GENERACION_TOKEN_CIFRADO", "0");
				put("TOKEN_ENVIAR_TOKEN", "1");
				put("TOKEN_CIFRADO", "2");
				put("TIEMPO_GENERACION_TOKEN_NUEVO", "2");
				put("VAL_API_COD_PRODUCTO_LIBRANZA_COLPENSIONES","53");
				put("VAL_API_COD_PRODUCTO_LIBRANZA","4");
				put("VAL_API_COD_PRODUCTO_CREDIAGIL","13");

			}
		};
	}

	private static void InicializarPropiedades3() {
		propiedades3 = new HashMap<String, String>() {
			{
				put("PROXY_ENDPOINT_GESTIONAR_PROSPECTO",
						"http://localhost:9081/GestionarProspectoLI/GestionarProspectoLI");
				put("PROXY_SYSTEM_ID_GESTIONAR_PROSPECTO", "AW1177");
				put("PROXY_CLASSIFICATION_GESTIONAR_PROSPECTO",
						"http://grupobancolombia.com/clas/AplicacionesActuales#");
				put("PROXY_GESTIONAR_PROSPECTO_NAMESPACE",
						"http://grupobancolombia.com/intf/Clientes/GestionClientes/GestionarProspecto/Enlace/V2.0");
				put("PROXY_REQUEST_TIMEOUT_GESTIONAR_PROSPECTO", "600");
				put("PROXY_CONNECTION_TIMEOUT_GESTIONAR_PROSPECTO", "600");
				put("API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE", "API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE");
				put("VAL_API_COD_PRODUCTO_CREDIAGIL", "13,47");
				put("VAL_TIEMPO_ESPERA_INICIAL_GESTION_CRED", "5");
				put("VAL_TIEMPO_REINTENTO_GESTION_CRED", "5");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED", "20");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED_MOM1","30");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED_MOM2","50");				
				put("VAL_API_ERRORES_ACTIVO", "false");
				put("COD_CONVENIOS_LIB_NO_PERMITIDOS", "1212");
				put("MSG_DEFAULT_API", "En este momento el sistema no está disponible. Por favor intenta más tarde.");
				put("API_ENDPOINT_ERROR_BYCODE", "/error/");
				put("API_ENDPOINT_CALL_AUTENTICACION", "/auth/");
				put("PROXY_ENDPOINT_ENVIO_CORREO_ELECTRONICO", "/mail/");
				put("COD_PRD_SIN_TASAS", "13,47,12");
				put("VAL_API_COD_PRODUCTO_TDC", "12,49");
				put("VAL_API_COD_PRODUCTO_LIBRANZA_COLPENSIONES","53");
				put("VAL_API_COD_PRODUCTO_LIBRANZA","4");
				put("VAL_API_COD_PRODUCTO_CREDIAGIL","13");

			}
		};
	}

	@SuppressWarnings("serial")
	private static void InicializarPropiedadesProspecto() {
		propiedadesProspecto = new HashMap<String, String>() {
			{
				put("API_ENDPOINT_CALL_PROXY_GESTIONAR_PROSPECTO", "true");
				put("API_VAR_CALL_PROXY_GESTIONAR_PROSPECTO", "14");
				put("PROXY_ENDPOINT_GESTIONAR_PROSPECTO", "5");
				put("PROXY_SYSTEM_ID_GESTIONAR_PROSPECTO", "5");
				put("PROXY_CLASSIFICATION_GESTIONAR_PROSPECTO", "60");
				put("PROXY_GESTIONAR_PROSPECTO_NAMESPACE", "EsperarInformacionFinal");
				put("PROXY_REQUEST_TIMEOUT_GESTIONAR_PROSPECTO", "EsperarAceptacionCliente");
				put("PROXY_CONNECTION_TIMEOUT_GESTIONAR_PROSPECTO", "TrazaLibreInversionSucursales.txt");
				put("PROXY_CODIGO_RESPUESTA_EXITOSA_GESTIONAR_PROSPECTO", "TrazaLibreInversionSucursales.txt");
				put("VAL_GESTIONAR_PROSPECTO_INFOVERIDICA", "TrazaLibreInversionSucursales.txt");
				put("VAL_GESTIONAR_PROSPECTO_MDACTUALIZACION", "TrazaLibreInversionSucursales.txt");
				put("VAL_API_ERRORES_ACTIVO", "true");
				put("VAL_API_ERRORES_COD_APLICACION", "99");
				put("MSG_DEFAULT_API", "En este momento el sistema no está disponible. Por favor intenta más tarde.");
				put("API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE", "API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE");
				put("VAL_API_COD_PRODUCTO_CREDIAGIL", "13,47");
				put("COD_PRD_SIN_TASAS", "13,47,12");
				put("VAL_API_COD_PRODUCTO_TDC", "12,49");
				put("VAL_TIEMPO_LLAMADA_GESTION_CRED", "10");
				put("API_ENDPOINT_ERROR_BYCODE", "/error/");
				put("API_ENDPOINT_CALL_AUTENTICACION", "/auth/");
				put("PROXY_ENDPOINT_ENVIO_CORREO_ELECTRONICO", "/mail/");
				put("VAL_API_COD_PRODUCTO_LIBRANZA_COLPENSIONES","53");
				put("VAL_API_COD_PRODUCTO_LIBRANZA","4");
				put("VAL_API_COD_PRODUCTO_CREDIAGIL","13");

			}
		};
	}

	public static Map<String, String> getPropiedades() {
		if (propiedades == null) {
			InicializarPropiedades();
		}
		return propiedades;
	}

	public static Map<String, String> getPropiedades2() {
		if (propiedades2 == null) {
			InicializarPropiedades2();
		}
		return propiedades2;
	}

	public static Map<String, String> getPropiedades3() {
		if (propiedades3 == null) {
			InicializarPropiedades3();
		}
		return propiedades3;
	}

	public static Map<String, String> getPropiedadesProspecto() {
		if (propiedadesProspecto == null) {
			InicializarPropiedadesProspecto();
		}
		return propiedadesProspecto;
	}

}
