/**
 *
 */
package bancolombia.dtd.vd.sucursales.api.datosCliente.util;

import java.io.Serializable;

/**
 * @author cgallego Clase para manejo de constantes usados en la aplicacion
 *         consumo libranza sucursales
 *
 */
public class ConstantesLI implements Serializable {

    private static final long serialVersionUID = -5759269057712347543L;

    public static final String ENDPOINT_PERSISTENCE="ENDPOINT_PERSISTENCE";
    public static final String API_ENDPOINT_ENVIO_CORREO = "PROXY_ENDPOINT_ENVIO_CORREO_ELECTRONICO";

    public static final String API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE = "API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE";
    public static final String API_ENDPOINT_ERROR_BYCODE = "API_ENDPOINT_ERROR_BYCODE";


    // constantes de servicios consumidos back

    public static final String SERVICIO_OFERTA_DIGITAL_CREAR_SOL = "crearSolicitudCreditoConsumo";
    public static final String SERVICIO_OFERTA_DIGITAL_AVANZAR_SOL = "avanzarSolicitudCreditoConsumo";
    public static final String SERVICIO_CATALOGOS = "/rest/servicio/obtenercatalogos";
    public static final String SERVICIO_RECUPERAR_UBICACION_CLIENTE = "recuperarDatosUbicacionCliente";
    public static final String SERVICIO_RECUPERAR_DATOS_BASICOS_CLIENTE = "recuperarDatosBasicosCliente";

    // Constantes para persistencia
    public static final String DB_ESQUEMA = "ESQUEMA";
    public static final String DB_TABLA = "TABLA";
    public static final String DB_ID_SESION = "ID_SESION";
    public static final String DB_PASO = "PASO";
    public static final String ESQUEMA_DB_NAME = "SCHVTDIG";

    public static final String TITULARCUENTA = "T";
    public static final String CUENTA_CORRIENTE_LETRA = "D";
    public static final String CUENTA_CORRIENTE_NUM = "1";
    public static final String CUENTA_AHORROS_LETRA = "S";
    public static final String CUENTA_AHORROS_NUM = "2";

    public static final String CUENTACANCELADA = "N";
    public static final String TIPO_CEDULA_CC = "FS001";

    public static final String MSG_ERROR_HTTP_CALL = "Failed : HTTP error code : ";
    public static final String TXT_SESION_ID = "sessionId";


    public static final String ID_HEADER_MENSAJE_ERROR = "string";
    public static final String TYPE_HEADER_MENSAJE_ERROR = "string";
    public static final String MSG_DEFAULT_API = "MSG_DEFAULT_API";

    protected static final String[] MARCASNOPERMITIDAS = {"Cuenta CATS", "Cuenta AFC", "Cuenta de Pensión",
            "Cuenta de Concordato", "Cuenta Maestra", "Adelanto de Ingreso", "Tiene Embargo",
            "Tiene Bloqueo por InvestigaciÃ³n", "Tiene Bloqueo por SucesiÃ³n o Fallecimiento"};

    public static final String VAL_CONSUMO_COD_ASESOR_DATOS_ASESOR = "1,3";

    public static final String VAL_CONSUMO_COD_ASESOR_DATOS_SUCURSAL = "2";

    public static final String TRUE = "true";

    public static final String PASO1 = "paso1";

    public static final String PASO2 = "paso2";

    public static final String PASO3 = "paso3";

    public static final Object COD_DIRECCION_PPAL = "Z001";
    public static final Object COD_DIRECCION_LABORAL = "Z002";

    public static final String ERROR_401 = "401";
    public static final String ERROR_600 = "600";
    public static final String ERROR_500 = "500";
    public static final String ERROR_APM = "APM-500";

    public static final String ALFANUMERICO = "^[a-zA-Z ]*$";
    public static final String VALIDAR_CARACTERES_NOMBRE = "VALIDAR_CARACTERES_NOMBRE";

    public static final int MIL = 1000;

    public static final String LETRA_Y = "Y";
    public static final String RESPUESTA_R = "_R";
    public static final String RESPUESTA_E = "_E";
    public static final String ERROR_GCC_500 = "GCC-500";

    public static final int CERO = 0;
    public static final int UNO = 1;
    public static final String UNO_TXT = "1";
    public static final int DOS = 2;
    public static final int TRES = 3;
    public static final String TRES_TXT = "3";
    public static final int CUATRO = 4;
    public static final int CINCO = 5;
    public static final int ONCE = 11;
    public static final int DOCE = 12;
    public static final int TRECE = 13;
    public static final int CATORCE = 14;
    public static final int VEINTIUNO = 21;
    public static final int VEINTIDOS = 22;
    public static final int VEINTITRES = 23;
    public static final int VEINTICUATRO = 24;

    public static final int DOCIENTOS = 200;

    public static final String OTP_ATALLA = "ATALLA";

    public static final String VAR_CLAVE_OTPODA = "ODA";

    public static final String VAR_CLAVE_SOFTOKEN = "SFTKN";

    public static final String CLAVE_IDENTIFICACION_CANAL = "PROXY_VALOR_CANAL";


    public static final String CLAVE_COD_OPERACION = "CLAVE_COD_OPERACION";

    public static final String CLAVE_ASUNTO = "CLAVE_ASUNTO";

    public static final String CLAVE_CONTENIDO_MENSAJE = "CLAVE_CONTENIDO_MENSAJE";

    public static final String CLAVE_COD_APLICACION = "CLAVE_COD_APLICACION";

    public static final String TOKEN_ID_SERVIDOR = "TOKEN_ID_SERVIDOR";

    public static final String TOKEN_SISTEMA_FUENTE = "TOKEN_SISTEMA_FUENTE";

    public static final String TOKEN_GENERACION_TOKEN_CIFRADO = "TOKEN_GENERACION_TOKEN_CIFRADO";

    public static final String TOKEN_ENVIAR_TOKEN = "TOKEN_ENVIAR_TOKEN";

    public static final String TOKEN_CIFRADO = "TOKEN_CIFRADO";

    public static final String TXT_IP_CLIENTE = "ipCliente";

    public static final String CODIGO_LIBRANZA_BANCOLOMBIA = "CODIGO_LIBRANZA_BANCOLOMBIA";

    public static final String VAL_API_COD_PRODUCTO_LIBRANZA = "VAL_API_COD_PRODUCTO_LIBRANZA";

    protected static final String[] NOMBRES_SEGUROS = {"Seguro de Vida Deudor",
            "Seguro de Vida Deudor y Empleado protegido"};

    public static final String NOMBRE_CARTA_SEGURO = "FWK_ENVIAR_CORREO_NOMBRE_CARTA";

    public static final String DEP_NUMEROPAGINA = "1";
    public static final String DEP_CANTIDADREGISTRO = "30";

    public static final String PREGUNTAS_CAT_1 = "PREGUNTAS_CAT_1";
    public static final String PREGUNTAS_CAT_2 = "PREGUNTAS_CAT_2";
    public static final String PREGUNTAS_CAT_3 = "PREGUNTAS_CAT_3";

    public static final String MSG_SIN_CUENTAS = "No tiene";

    public static final String ERROR_RESPUESTA = "false";

    public static final int INTENTOS_PREGUNTAS = 20;

    public static final String TIEMPO_GENERACION_TOKEN_NUEVO = "TIEMPO_GENERACION_TOKEN_NUEVO";

    public static final String COD_ERROR_003 = "003";

    public static final String COD_ERROR_006 = "006";


    public static final String TIPO_MENSAJE_CORREO = "EML";
    public static final String TIPO_MENSAJE_TEXTO = "SMS";
    public static final String TIPO_MENSAJE_MIXTO = "EML/SMS";
    public static final String TIPO_MENSAJE_C = "C";
    public static final String TIPO_MENSAJE_M = "M";

    public static final String RUTA_FILE_TEMP = "RUTA_FILE_TEMP";
    public static final String RUTA_IMG = "RUTA_IMG";

    public static final String MSJ_ERROR_GEN = "El proceso se interrumpio por error: ";
    public static final String MSJ_SESION_INVAL = "SesiÃ³n InvÃ¡lida.";

    public static final String MSG_VAL_SESSION = "validarSesion: ";
    public static final String RET303 = "303";

    public static final String SOWIND = "WINDOWS";

    public static final int ERRORNN_401 = 401;
    public static final int ERRORNN_QUINIENTOS = 500;

    public static final Object TRESCEROTRES = 303;

    public static final String COD_PRD_SIN_LIMITE_DESCARGA_DOC = "COD_PRD_SIN_LIMITE_DESCARGA_DOC";


    public static final String REGINFODISPOSITIVO1 = "^[A-Za-z0-9\\s]*$";
    public static final String REGINFODISPOSITIVO1A = "\\W";
    public static final String REGINFODISPOSITIVO2 = "[a-zA-Z]*";
    public static final String REGINFODISPOSITIVO2A = "[^a-zA-Z]*";
    public static final String REGINFODISPOSITIVO3 =
            "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";
    public static final String REGEXMATCHASIGNACION = "^[0-9]+([.|,][0-9]+)*$";
    public static final String REGEXREPLACEASIGNACION = "[a-zA-Z<>%$#&!?]*";
    public static final String REGEXMATCHNOMBRECOMPLETO = "[a-zA-ZÃ±Ã‘Ã¡Ã©Ã­Ã³ÃºÃ�Ã‰Ã�Ã“Ãš\\s]*";
    public static final String REGEXREPLACENOMBRECOMPLETO = "[^a-zA-ZÃ±Ã‘Ã¡Ã©Ã­Ã³ÃºÃ�Ã‰Ã�Ã“Ãš\\s]*";

    public static final String X_IBM_CLIENT_ID_ESTUDIO_CREDITO_CONSUMO = "X_IBM_CLIENT_ID_ESTUDIO_CREDITO_CONSUMO";
    public static final String X_IBM_CLIENT_SECRET_ESTUDIO_CREDITO_CONSUMO = "X_IBM_CLIENT_SECRET_ESTUDIO_CREDITO_CONSUMO";
    public static final String X_IBM_CLIENT_ID = "X-IBM-Client-Id";
    public static final String X_IBM_CLIENT_SECRET = "X-IBM-Client-Secret";
    public static final String URL_DOMAIN_MOTOR_API = "URL_DOMAIN_MOTOR_API";
    public static final String URL_BASE_MOTOR_API = "URL_BASE_MOTOR_API";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String MESSAGE_ID = "messageId";
    public static final String TLS_PROTOCOL = "TLS";
    public static final String VAL_SHOW_LIBRANZA = "VAL_SHOW_LIBRANZA";
    public static final String LIBRE_INVERSION = "Libre inversión";
    public static final String MICROCREDITO = "Microcrédito";
    public static final String IMAGENES_CORREO_CREDIAGIL = "header-logo.png,mail_Inversion_Virtual.jpg,cartas_bienvenida_consumo.png,cartas_bienvenida_consumo_2.png,legal.png,footer-logo.png,fb.png,tw.png,link.png,instragram.png,youtube.png";
    public static final String IMAGENES_CORREO_LIBRANZA = "header-logo.png,mail_Inversion_Virtual.jpg,cartas_bienvenida_consumo.png,cartas_bienvenida_consumo_16.png,cartas_bienvenida_consumo_19.png,icon_seguro.png,legal.png,footer-logo.png,fb.png,tw.png,link.png,instragram.png,youtube.png";
    public static final String IMAGENES_CORREO_LIBRE_INVERSION = "header-logo.png,mail_Inversion_Virtual_03.jpg,cartas_bienvenida_consumo_13.png,cartas_bienvenida_consumo_16.png,icon_seguro.png,legal.png,footer-logo.png,fb.png,tw.png,link.png,instragram.png,youtube.png";

    /** @return the marcasnopermitidas
     */
    public static String[] getMarcasnopermitidas() {
        return MARCASNOPERMITIDAS;
    }


    /**
     * @return the nombresSeguros
     */
    public static String[] getNombresSeguros() {
        return NOMBRES_SEGUROS;
    }

    protected static final String[] ERRORESSESSION = {"AUTH-400", "AUTH-401", "AUTH-403",
            "AUTH-500", "AUTH-503", "AUTH-407", "AUTH-404", "AUTH-409"};


    public static String[] getExcepcionesSession() {
        return ERRORESSESSION;
    }


}
