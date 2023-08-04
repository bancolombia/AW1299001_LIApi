package bancolombia.dtd.vd.sucursales.api.datosCliente;

public class Constantes {
	
	public static String cargaUtilInfoAsesorCedula = "{'datoConsulta': '1018418391','tipoConsulta': '3','idSesion': '2werwd32rewr233de324d3f','pasoFuncional': 'paso_li_101'}";
	
	public static String cargaUtilInfoAsesorSucursal = "{'datoConsulta': '00005','tipoConsulta': '2','idSesion': '2werwd32rewr233de324d3f','pasoFuncional': 'paso_li_101'}";
	
	public static String respuestaCatalogo = "{\"esSatisfactorio\":\"true\",\"lista\":[{\"descripcion\":\"CAROL MELISSA JARAMILLO RODRIG|31105|00006|00000066|43583347|1020452408|CAJARAMI\",\"id\":\"1018418391\"},{\"descripcion\":\"SANTIAGO ANDRES LOPEZ ROJAS|9911|00005|00000066|43583347|71261558|SANTLOPE\",\"id\":\"1037644654\"}]}";
	
	public static String respuestaDatosCliente = "{\"informacionUbicacionCliente\": [\r\n" + "      {\r\n"
			+ "      \"codigoDireccionFuente\": \"0069421916\",\r\n"
			+ "      \"codigoTipoDireccionFuente\": \"Z001\",\r\n" + "      \"direccionPrincipal\": \"LABORAL 1\",\r\n"
			+ "      \"codigoCiudad\": \"8001000\",\r\n" + "      \"codigoDepartamento\": \"08\",\r\n"
			+ "      \"codigoPais\": {\"codigoISO3166Alfa2\": \"CO\"},\r\n" + "      \"telefonoFijo\": \"4556677\",\r\n"
			+ "      \"celular\": \"3015659286\",\r\n" + "      \"correoElectronico\": \"cgallego@co.ibm.com\",\r\n"
			+ "      \"codigoPostal\": \"0008001000\",\r\n" + "      \"barrioDireccionPrincipal\": \"Pescaito\"\r\n"
			+ "   },\r\n" + "      {\r\n" + "      \"codigoDireccionFuente\": \"0069421919\",\r\n"
			+ "      \"codigoTipoDireccionFuente\": \"Z002\",\r\n" + "      \"direccionPrincipal\": \"LABORAL 2\",\r\n"
			+ "      \"codigoCiudad\": \"17001000\",\r\n" + "      \"codigoDepartamento\": \"17\",\r\n"
			+ "      \"codigoPais\": {\"codigoISO3166Alfa2\": \"CO\"},\r\n"
			+ "      \"codigoPostal\": \"0017001000\"\r\n" + "   },\r\n" + "      {\r\n"
			+ "      \"codigoDireccionFuente\": \"0069421900\",\r\n"
			+ "      \"codigoTipoDireccionFuente\": \"Z001\",\r\n"
			+ "      \"direccionPrincipal\": \"Bulevar 12 # 212 Unidad Montañas Verdes Apto 234\",\r\n"
			+ "      \"codigoCiudad\": \"5001000\",\r\n" + "      \"codigoDepartamento\": \"05\",\r\n"
			+ "      \"codigoPais\": {\"codigoISO3166Alfa2\": \"CO\"},\r\n"
			+ "      \"codigoPostal\": \"0005001000\",\r\n" + "      \"codigoCorrespondencia\": \"X\"\r\n" + "   }\r\n"
			+ "]}";
	
	public static String cargaUtilSolicitudCreditoConsumoPaso1 = "{	\"idSesion\": \"sadsadsadsa\",\r\n" + 
			" 	\"pasoFuncional\": \"Paso1\",\r\n" + 
			"   	\"paso\": \"paso1\",\r\n" + 
			"   	\"tipoVenta\": \"P\",\r\n" + 
			" 	\"tipodocumento\": \"FS001\",\r\n" + 
			"   	\"numeroDocumento\": \"1111111111\",\r\n" + 
			"   	\"idProducto\": \"14\",\r\n" + 
			" 	\"sucursalRadicacion\": \"\",\r\n" + 
			"   	\"codigoAsesorComercial\": \"\",\r\n" + 
			"   	\"codigoReferido\": \"\",\r\n" + 
			"   	\"producto\":[\r\n" + 
			"   		{\r\n" + 
			"   		\"idProducto\": \"14\"\r\n" + 
			"   		}\r\n" + 
			"   	]\r\n" + 
			"}";
	
	public static String respuestaOfertaSolicitudCreditoConsumoPaso1 = "{  \r\n" + "   \"cabecera\":{  \r\n"
			+ "      \"estadoTransaccion\":\"\",\r\n" + "      \"pasoFuncional\":\"paso1\",\r\n"
			+ "      \"numeroSolicitud\":\"VDI_2018_3795\",\r\n" + "      \"extensionFuncional\":[  \r\n"
			+ "         {  \r\n" + "            \"llave\":\"\",\r\n" + "            \"valor\":\"\"\r\n"
			+ "         }\r\n" + "      ]\r\n" + "   },\r\n" + "   \"oferta\":{  \r\n"
			+ "      \"informacionCliente\":{  \r\n" + "         \"idSolicitante\":\"53924\",\r\n"
			+ "         \"tipoDocumento\":\"FS001\",\r\n" + "         \"numeroDocumento\":\"1059786192\",\r\n"
			+ "         \"nombreCompletoCliente\":\" Juan Andres Bedoya Rios\",\r\n"
			+ "         \"direccionPrincipal\":\"PRINCIPAL\",\r\n" + "         \"codigoCiudad\":\"5001000\",\r\n"
			+ "         \"codigoDepartamento\":\"05\",\r\n" + "         \"codigoPais\":\"CO\"\r\n" + "      },\r\n"
			+ "      \"producto\":[  \r\n" + "         {  \r\n" + "            \"idProducto\":\"14\",\r\n"
			+ "            \"nombreProducto\":\"libre inversion\",\r\n"
			+ "            \"montoOtorgado\":\"500000000.00\",\r\n" + "            \"categoria\":[  \r\n"
			+ "               {  \r\n" + "                  \"codigoCategoria\":\"Credito de Consumo\",\r\n"
			+ "                  \"subproducto\":[  \r\n" + "                     {  \r\n"
			+ "                        \"idSubproducto\":\"174991\",\r\n"
			+ "                        \"nombreSubproducto\":\"LIBRE INVERSION\",\r\n"
			+ "                        \"descripcionSubproducto\":\"LIBRE INVERSION\",\r\n"
			+ "                        \"cupoMinimo\":\"0.00\",\r\n"
			+ "                        \"cupoMaximo\":\"500000000.00\",\r\n"
			+ "                        \"codigoImagen\":\"\",\r\n"
			+ "                        \"listaCondiciones\":[  \r\n" + "\r\n" + "                        ],\r\n"
			+ "                        \"tasas\":[  \r\n" + "                           {  \r\n"
			+ "                              \"grupoRiesgo\":\"G2\",\r\n"
			+ "                              \"montoInferiorProducto\":1000000.0,\r\n"
			+ "                              \"montoSuperiorProducto\":11999999.0,\r\n"
			+ "                              \"plazoInferior\":6,\r\n"
			+ "                              \"plazoSuperior\":36,\r\n"
			+ "                              \"tasaMesVencida\":1.775412,\r\n"
			+ "                              \"tasaNominalAnualMesVencido\":0.213049,\r\n"
			+ "                              \"tasaEfectivaAnual\":0.2351,\r\n"
			+ "                              \"tasaMora\":27.39\r\n" + "                           },\r\n"
			+ "                           {\r\n" + "					\"grupoRiesgo\": \"G2\",\r\n"
			+ "					\"montoInferiorProducto\": 1000000.00,\r\n"
			+ "					\"montoSuperiorProducto\": 11999999.00,\r\n"
			+ "					\"plazoInferior\": 6,\r\n" + "					\"plazoSuperior\": 59,\r\n"
			+ "					\"tasaMesVencida\": 2.099917,\r\n"
			+ "					\"tasaNominalAnualMesVencido\": 0.25199004,\r\n"
			+ "					\"tasaEfectivaAnual\": 0.2832,\r\n" + "					\"tasaMora\": 20.42\r\n"
			+ "				}\r\n" + "                        ],\r\n"
			+ "                        \"seguroProducto\":[  \r\n" + "                           {  \r\n"
			+ "                              \"montoMinimoSeguro\":1000000,\r\n"
			+ "                              \"montoMaximoSeguro\":20000000,\r\n"
			+ "                              \"factor\":0.00145\r\n" + "                           },\r\n"
			+ "                           {  \r\n" + "                              \"montoMinimoSeguro\":20000001,\r\n"
			+ "                              \"montoMaximoSeguro\":50000000,\r\n"
			+ "                              \"factor\":0.00149\r\n" + "                           },\r\n"
			+ "                           {  \r\n" + "                              \"montoMinimoSeguro\":50000001,\r\n"
			+ "                              \"montoMaximoSeguro\":100000000,\r\n"
			+ "                              \"factor\":0.00151\r\n" + "                           },\r\n"
			+ "                           {  \r\n"
			+ "                              \"montoMinimoSeguro\":100000001,\r\n"
			+ "                              \"montoMaximoSeguro\":200000000,\r\n"
			+ "                              \"factor\":0.00155\r\n" + "                           },\r\n"
			+ "                           {  \r\n"
			+ "                              \"montoMinimoSeguro\":200000001,\r\n"
			+ "                              \"montoMaximoSeguro\":500000001,\r\n"
			+ "                              \"factor\":0.00156\r\n" + "                           }\r\n"
			+ "                        ]\r\n" + "                     }\r\n" + "                  ]\r\n"
			+ "               }\r\n" + "            ]\r\n" + "         }\r\n" + "      ]\r\n" + "   }\r\n" + "}\r\n";

	public static String cargaUtilSolicitudCreditoConsumoPaso2 = "{  	\"idSesion\":\"3123123123213\",\r\n" + 
			"	\"paso\":\"paso2\",\r\n" + 
			"	\"tipoVenta\":\"Preaprobado\",\r\n" + 
			"	\"tipodocumento\":\"FS001\",\r\n" + 
			"	\"numeroDocumento\":\"98768001\",\r\n" + 
			"	\"idSesion\":\"sadsadsadsa\",\r\n" + 
			"	\"idProducto\":\"14\",\r\n" + 
			"	\"sucursalRadicacion\":\"245\",\r\n" + 
			"	\"codigoAsesorComercial\":\"423\",\r\n" + 
			"	\"codigoReferido\":\"1112\",\r\n" + 
			"	\"numeroSolicitud\":\"1111111111-9153-14\",\r\n" + 
			"	\"nombreTarea\":\"\",\r\n" + 
			"	\"codigoSolicitante\":\"\",\r\n" + 
			"	\"firmaCliente\":\"\",\r\n" + 
			"	\"direccionEntrega\":\"\",\r\n" + 
			"	\"municipioEntrega\":\"\",\r\n" + 
			"	 \"producto\": [{\"idProducto\":\"14\",\r\n" + 
			"			       \"cupoElegido\":\"4000000\",\r\n" + 
			"			               \"plazo\":\"36\",\r\n" + 
			"\r\n" + 
			"			       \"cuota\":\"430000\",\r\n" + 
			"			               \"tasaMV\":\"0.6\",\r\n" + 
			"			               \"tasaNAMV\":\"0.7\",\r\n" + 
			"\r\n" + 
			"			       \"tasaEA\":\"1.9\",\r\n" + 
			"			               \"tasaMora\":\"3.9\",\r\n" + 
			"\r\n" + 
			"			       \"factorSeguro\":\"3212\",\r\n" + 
			"			               \"costoTotalSeguro\":\"300000\",\r\n" + 
			"\r\n" + 
			"			       \"tipoCuentaDesembolso\":\"2\",\r\n" + 
			"			               \"cuentaDesembolso\":\"D-00003424234\",\r\n" + 
			"\r\n" + 
			"			       \"aceptaDebitoAutomatico\":\"true\",\r\n" + 
			"			               \"tipoCuentaDebitoAutomatico\":\"\",\r\n" + 
			"\r\n" + 
			"			       \"cuentaDebitoAutomatico\":\"\",\r\n" + 
			"			               \"beneficiario\":[{\r\n" + 
			"								\"tipoDocumento\":\"FS001\",\r\n" + 
			"								\"numeroDocumento\":\"12344354\",\r\n" + 
			"\r\n" + 
			"								\"nombreCompleto\":\"FFFFFFFFFFFFF\",\r\n" + 
			"								\"asignacion\":\"50\"\r\n" + 
			"								},\r\n" + 
			"								{\"tipoDocumento\":\"FS001\",\r\n" + 
			"\r\n" + 
			"								\"numeroDocumento\":\"77777\",\r\n" + 
			"								\"nombreCompleto\":\"AAAAAAAAAAA\",\r\n" + 
			"\r\n" + 
			"								\"asignacion\":\"50\"       \r\n" + 
			"								}]\r\n" + 
			"			         }]\r\n" + 
			"}";

	public static String respuestaOfertaSolicitudCreditoConsumoPaso2 = "{\r\n" + "	\"cabecera\":{\r\n"
			+ "		\"estadoTransaccion\": \"success\",\r\n" + "		\"pasoFuncional\": \"paso2\",\r\n"
			+ "		\"numeroSolicitud\": \"XXXXXXX\"\r\n" + "	},\r\n" + "	\"documentos\": [{\r\n"
			+ "		\"tipoDocumento\": \"1\",\r\n"
			+ "		\"documento\": \"ASDASD\",\r\n"
			+ "		\"descripcion\": \"sin descripcion1\"\r\n" + "	},\r\n" + "	{\r\n"
			+ "		\"tipoDocumento\": \"2\",\r\n"
			+ "		\"documento\": \"ASDASD\",\r\n"
			+ "		\"descripcion\": \"sin descripcion2\"\r\n" + "	}]		\r\n" + "}";

	public static String cargaUtilSolicitudCreditoConsumoPaso3 = "{\r\n" + 
			"    \"codigoAsesorComercial\": \"423\",\r\n" + 
			"    \"codigoReferido\": \"1112\",\r\n" + 
			"    \"codigoSolicitante\": \"\",\r\n" + 
			"    \"direccionEntrega\": \"\",\r\n" + 
			"    \"firmaCliente\": \"\",\r\n" + 
			"    \"idProducto\": \"14\",\r\n" + 
			"    \"idSesion\": \"sadsadsadsa\",\r\n" + 
			"    \"municipioEntrega\": \"\",\r\n" + 
			"    \"nombreTarea\": \"\",\r\n" + 
			"    \"numeroDocumento\": \"98768001\",\r\n" + 
			"    \"numeroSolicitud\": \"1111111111-9153-14\",\r\n" + 
			"    \"paso\": \"paso3\",\r\n" + 
			"    \"producto\": [{\r\n" + 
			"        \"aceptaDebitoAutomatico\": \"0\",\r\n" + 
			"        \"beneficiario\": [\r\n" + 
			"            {\r\n" + 
			"                \"asignacion\": \"50\",\r\n" + 
			"                \"nombreCompleto\": \"FFFFFFFFFFFFF\",\r\n" + 
			"                \"numeroDocumento\": \"12344354\",\r\n" + 
			"                \"tipoDocumento\": \"FS001\"\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"asignacion\": \"50\",\r\n" + 
			"                \"nombreCompleto\": \"AAAAAAAAAAA\",\r\n" + 
			"                \"numeroDocumento\": \"77777\",\r\n" + 
			"                \"tipoDocumento\": \"FS001\"\r\n" + 
			"            }\r\n" + 
			"        ],\r\n" + 
			"        \"costoTotalSeguro\": \"300000\",\r\n" + 
			"        \"cuentaDebitoAutomatico\": \"\",\r\n" + 
			"        \"cuentaDesembolso\": \"D-00003424234\",\r\n" + 
			"        \"cuota\": \"430000\",\r\n" + 
			"        \"cupoElegido\": \"4000000\",\r\n" + 
			"        \"factorSeguro\": \"3212\",\r\n" + 
			"        \"idProducto\": \"14\",\r\n" + 
			"        \"plazo\": \"36\",\r\n" + 
			"        \"tasaEA\": \"1.9\",\r\n" + 
			"        \"tasaMV\": \"0.6\",\r\n" + 
			"        \"tasaMora\": \"3.9\",\r\n" + 
			"        \"tasaNAMV\": \"0.7\",\r\n" + 
			"        \"tipoCuentaDebitoAutomatico\": \"\",\r\n" + 
			"        \"tipoCuentaDesembolso\": \"2\"\r\n" + 
			"    }],\r\n" + 
			"    \"sucursalRadicacion\": \"245\",\r\n" + 
			"    \"tipoVenta\": \"Preaprobado\",\r\n" + 
			"    \"tipodocumento\": \"FS001\"\r\n" + 
			"}";

	public static String respuestaOfertaSolicitudCreditoConsumoPaso3 = "{\r\n" + "	\"cabecera\":{\r\n"
			+ "		\"estadoTransaccion\": \"success\",\r\n" + "		\"pasoFuncional\": \"paso3\",\r\n"
			+ "		\"numeroSolicitud\": \"33254-TDCP\"\r\n" + "	}, \r\n" + "     \"confirmacionTransaccion\":{\r\n"
			+ "           \"codigo\":\"ok\",\r\n" + "           \"descripcion\":\"34323233\"\r\n" + "     }\r\n" + "}";

	public static String cargaUtilRecalculo = "{\r\n" + 
			" \"idSesion\":\"3123123123213\",\r\n" + 
			" \"paso\":\"paso3\",\r\n" + 
			" \"tipoVenta\":\"Preaprobado\",\r\n" + 
			" \"tipodocumento\":\"FS001\",\r\n" + 
			" \"numeroDocumento\":\"98768001\",\r\n" + 
			" \"idSesion\":\"sadsadsadsa\",\r\n" + 
			" \"idProducto\":\"14\",\r\n" + 
			" \"sucursalRadicacion\":\"245\",\r\n" + 
			" \"codigoAsesorComercial\":\"423\",\r\n" + 
			" \"codigoReferido\":\"1112\",\r\n" + 
			" \"numeroSolicitud\":\"1111111111-9153-14\",\r\n" + 
			" \"nombreTarea\":\"\",\r\n" + 
			" \"codigoSolicitante\":\"\",\r\n" + 
			" \"firmaCliente\":\"\",\r\n" + 
			" \"direccionEntrega\":\"\",\r\n" + 
			" \"municipioEntrega\":\"\",\r\n" + 
			" \"producto\":[{\r\n" + 
			"       \"idProducto\":\"14\",\r\n" + 
			"       \"cupoElegido\":\"4000000\",\r\n" + 
			"       \"plazo\":\"36\",\r\n" + 
			"       \"cuota\":\"430000\",\r\n" + 
			"       \"tasaMV\":\"0.6\",\r\n" + 
			"       \"tasaNAMV\":\"0.7\",\r\n" + 
			"       \"tasaEA\":\"1.9\",\r\n" + 
			"       \"tasaMora\":\"3.9\",\r\n" + 
			"       \"factorSeguro\":\"3212\",\r\n" + 
			"       \"costoTotalSeguro\":\"300000\",\r\n" + 
			"       \"tipoCuentaDesembolso\":\"2\",\r\n" + 
			"       \"cuentaDesembolso\":\"D-00003424234\",\r\n" + 
			"       \"aceptaDebitoAutomatico\":\"0\",\r\n" + 
			"       \"tipoCuentaDebitoAutomatico\":\"\",\r\n" + 
			"       \"cuentaDebitoAutomatico\":\"\",\r\n" + 
			"       \"beneficiario\":[{\r\n" + 
			"         \"tipoDocumento\":\"FS001\",\r\n" + 
			"         \"numeroDocumento\":\"12344354\",\r\n" + 
			"         \"nombreCompleto\":\"FFFFFFFFFFFFF\",\r\n" + 
			"         \"asignacion\":\"50\"\r\n" + 
			"       },{\r\n" + 
			"         \"tipoDocumento\":\"FS001\",\r\n" + 
			"         \"numeroDocumento\":\"77777\",\r\n" + 
			"         \"nombreCompleto\":\"AAAAAAAAAAA\",\r\n" + 
			"         \"asignacion\":\"50\"\r\n" + 
			"       }]\r\n" + 
			"  }]\r\n" + 
			"}";

	public static String respuestaOfertaRecalculo = "{  \r\n" + "   \"cabecera\":{  \r\n"
			+ "      \"estadoTransaccion\":\"\",\r\n" + "      \"pasoFuncional\":\"paso1\",\r\n"
			+ "      \"numeroSolicitud\":\"VDI_2018_3795\",\r\n" + "      \"extensionFuncional\":[  \r\n"
			+ "         {  \r\n" + "            \"llave\":\"\",\r\n" + "            \"valor\":\"\"\r\n"
			+ "         }\r\n" + "      ]\r\n" + "   },\r\n" + "   \"oferta\":{  \r\n"
			+ "      \"informacionCliente\":{  \r\n" + "         \"idSolicitante\":\"53924\",\r\n"
			+ "         \"tipoDocumento\":\"FS001\",\r\n" + "         \"numeroDocumento\":\"1059786192\",\r\n"
			+ "         \"nombreCompletoCliente\":\" Juan Andres Bedoya Rios\",\r\n"
			+ "         \"direccionPrincipal\":\"PRINCIPAL\",\r\n" + "         \"codigoCiudad\":\"5001000\",\r\n"
			+ "         \"codigoDepartamento\":\"05\",\r\n" + "         \"codigoPais\":\"CO\"\r\n" + "      },\r\n"
			+ "      \"producto\":[  \r\n" + "         {  \r\n" + "            \"idProducto\":\"14\",\r\n"
			+ "            \"nombreProducto\":\"libre inversion\",\r\n"
			+ "            \"montoOtorgado\":\"500000000.00\",\r\n" + "            \"categoria\":[  \r\n"
			+ "               {  \r\n" + "                  \"codigoCategoria\":\"Credito de Consumo\",\r\n"
			+ "                  \"subproducto\":[  \r\n" + "                     {  \r\n"
			+ "                        \"idSubproducto\":\"174991\",\r\n"
			+ "                        \"nombreSubproducto\":\"LIBRE INVERSION\",\r\n"
			+ "                        \"descripcionSubproducto\":\"LIBRE INVERSION\",\r\n"
			+ "                        \"cupoMinimo\":\"0.00\",\r\n"
			+ "                        \"cupoMaximo\":\"500000000.00\",\r\n"
			+ "                        \"codigoImagen\":\"\",\r\n"
			+ "                        \"listaCondiciones\":[  \r\n" + "\r\n" + "                        ],\r\n"
			+ "                        \"tasas\":[  \r\n" + "                           {  \r\n"
			+ "                              \"grupoRiesgo\":\"G2\",\r\n"
			+ "                              \"montoInferiorProducto\":1000000.0,\r\n"
			+ "                              \"montoSuperiorProducto\":11999999.0,\r\n"
			+ "                              \"plazoInferior\":6,\r\n"
			+ "                              \"plazoSuperior\":36,\r\n"
			+ "                              \"tasaMesVencida\":1.775412,\r\n"
			+ "                              \"tasaNominalAnualMesVencido\":0.213049,\r\n"
			+ "                              \"tasaEfectivaAnual\":0.2351,\r\n"
			+ "                              \"tasaMora\":27.39\r\n" + "                           },\r\n"
			+ "                           {\r\n" + "					\"grupoRiesgo\": \"G2\",\r\n"
			+ "					\"montoInferiorProducto\": 1000000.00,\r\n"
			+ "					\"montoSuperiorProducto\": 11999999.00,\r\n"
			+ "					\"plazoInferior\": 6,\r\n" + "					\"plazoSuperior\": 59,\r\n"
			+ "					\"tasaMesVencida\": 2.099917,\r\n"
			+ "					\"tasaNominalAnualMesVencido\": 0.25199004,\r\n"
			+ "					\"tasaEfectivaAnual\": 0.2832,\r\n" + "					\"tasaMora\": 20.42\r\n"
			+ "				}\r\n" + "                        ],\r\n"
			+ "                        \"seguroProducto\":[  \r\n" + "                           {  \r\n"
			+ "                              \"montoMinimoSeguro\":1000000,\r\n"
			+ "                              \"montoMaximoSeguro\":20000000,\r\n"
			+ "                              \"factor\":0.00145\r\n" + "                           },\r\n"
			+ "                           {  \r\n" + "                              \"montoMinimoSeguro\":20000001,\r\n"
			+ "                              \"montoMaximoSeguro\":50000000,\r\n"
			+ "                              \"factor\":0.00149\r\n" + "                           },\r\n"
			+ "                           {  \r\n" + "                              \"montoMinimoSeguro\":50000001,\r\n"
			+ "                              \"montoMaximoSeguro\":100000000,\r\n"
			+ "                              \"factor\":0.00151\r\n" + "                           },\r\n"
			+ "                           {  \r\n"
			+ "                              \"montoMinimoSeguro\":100000001,\r\n"
			+ "                              \"montoMaximoSeguro\":200000000,\r\n"
			+ "                              \"factor\":0.00155\r\n" + "                           },\r\n"
			+ "                           {  \r\n"
			+ "                              \"montoMinimoSeguro\":200000001,\r\n"
			+ "                              \"montoMaximoSeguro\":500000001,\r\n"
			+ "                              \"factor\":0.00156\r\n" + "                           }\r\n"
			+ "                        ]\r\n" + "                     }\r\n" + "                  ]\r\n"
			+ "               }\r\n" + "            ]\r\n" + "         }\r\n" + "      ]\r\n" + "   }\r\n" + "}\r\n";
	
	public static String cargaUtilEnvioCorreo = "{\"idSesion\":\"3123123123213\",\r\n" + 
			"\"nombreCliente\":\"Pepito Perez\", \"documentoCliente\":\"123456789\" ,\r\n" + 
			"\"correo\":\"leidy.roman@ibm.com\", \"documentoPdf\":\"hkhkghjkgkfjhbjklhop\",\r\n" + 
			"\"cupoSolicitado\":\"5000000\", \"nombreProducto\":\"Libre inversion\",\r\n" + 
			"\"pasoFuncional\":\"Paso5\",\r\n" + 
			"\"tasaNominal\":\"0.22\",\r\n" + 
			"\"plazoCredito\":\"36\",\r\n" + 
			"\"valorCuota\":\"34453\",\r\n" + 
			"\"cuentaDebito\":\"D-123456789087\",\r\n" + 
			"\"numeroCredito\":\"21312321\"}";
	
	public static String cargaUtilGestionProspectoConsumoRQ = "{\r\n" + 
			"  \"idSesion\": \"987987uytyut765765ytrerte\",\r\n" + 
			"  \"propiedades\": [\r\n" + 
			"    {\r\n" + 
			"      \"llave\": \"PROXY_ENDPOINT_GESTIONAR_PROSPECTO\",\r\n" + 
			"      \"valor\": \"http://localhost:9081/GestionarProspectoLI/GestionarProspectoLI\"\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"llave\": \"PROXY_SYSTEM_ID_GESTIONAR_PROSPECTO\",\r\n" + 
			"      \"valor\": \"AW1177\"\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"llave\": \"PROXY_CLASSIFICATION_GESTIONAR_PROSPECTO\",\r\n" + 
			"      \"valor\": \"http://grupobancolombia.com/clas/AplicacionesActuales#\"\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"llave\": \"PROXY_GESTIONAR_PROSPECTO_NAMESPACE\",\r\n" + 
			"      \"valor\": \"http://grupobancolombia.com/intf/Clientes/GestionClientes/GestionarProspecto/V2.0\"\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"llave\": \"PROXY_REQUEST_TIMEOUT_GESTIONAR_PROSPECTO\",\r\n" + 
			"      \"valor\": \"600\"\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"llave\": \"PROXY_CONNECTION_TIMEOUT_GESTIONAR_PROSPECTO\",\r\n" + 
			"      \"valor\": \"600\"\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"llave\": \"PROXY_CODIGO_RESPUESTA_EXITOSA_GESTIONAR_PROSPECTO\",\r\n" + 
			"      \"valor\": \"000\"\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"llave\": \"PROXY_CODIGO_RESPUESTA_EXITOSA_GESTIONAR_PROSPECTO\",\r\n" + 
			"      \"valor\": \"000\"\r\n" + 
			"    }\r\n" + 
			"  ],\r\n" + 
			"  \"tipoDocumento\": \"FS001\",\r\n" + 
			"  \"numeroDocumento\": \"220818104\",\r\n" + 
			"  \"userName\": \"user\",\r\n" + 
			"  \"infoVeridica\": \"1\",\r\n" + 
			"  \"mdActualizacion\": \"10\",\r\n" + 
			"  \"llaveDireccion\": \"0071011808\",\r\n" + 
			"  \"correspondencia\": \"1\",\r\n" + 
			"  \"tipoDireccion\": \"Z001\",\r\n" + 
			"  \"direccion\": \"Manzana 10 # 2\",\r\n" + 
			"  \"pais\": \"CO\",\r\n" + 
			"  \"departamento\": \"05\",\r\n" + 
			"  \"ciudad\": \"5045000\",\r\n" + 
			"  \"email\": \"prueba.pru.co\",\r\n" + 
			"  \"celular\": \"3122222222\"\r\n" + 
			"}";
	
	public static String cargaUtilVerTarjetas = "{  \r\n" + 
			"   \"producto\":[  \r\n" + 
			"      {  \r\n" + 
			"         \"beneficiario\":[  \r\n" + 
			"\r\n" + 
			"         ],\r\n" + 
			"         \"cupoElegido\":\"1000000\",\r\n" + 
			"         \"idProducto\":\"12\"\r\n" + 
			"      }\r\n" + 
			"   ],\r\n" + 
			"   \"numeroSolicitud\":\"1111111-3554-12\",\r\n" + 
			"   \"paso\":\"paso1\",\r\n" + 
			"   \"idSesion\":\"YbtMMy0D8-i4vdHfwTJTsw\",\r\n" + 
			"   \"pasoFuncional\":\"paso_li_201\",\r\n" + 
			"   \"hash\":\"619ff3bb9e9e824e615f7eb88deb7d7e\"\r\n" + 
			"}";

	public static String respuestaOfertaVerTarjetas = "{\r\n" + 
			"    \"cabecera\": {\r\n" + 
			"        \"estadoTransaccion\": \"\",\r\n" + 
			"        \"extensionFuncional\": [\r\n" + 
			"            {\r\n" + 
			"                \"llave\": \"\",\r\n" + 
			"                \"valor\": \"\"\r\n" + 
			"            }\r\n" + 
			"        ],\r\n" + 
			"        \"numeroSolicitud\": \"VDI_2019_10947\",\r\n" + 
			"        \"pasoFuncional\": \"paso1\"\r\n" + 
			"    },\r\n" + 
			"    \"oferta\": {\r\n" + 
			"        \"informacionCliente\": {\r\n" + 
			"            \"codigoCiudad\": \"17001000\",\r\n" + 
			"            \"codigoDepartamento\": \"17\",\r\n" + 
			"            \"codigoPais\": \"CO\",\r\n" + 
			"            \"direccionPrincipal\": \"CR 5 15 70 PS 3 LO 68\",\r\n" + 
			"            \"idSolicitante\": \"13353\",\r\n" + 
			"            \"nombreCompletoCliente\": \" Roberto Tadeo Espitia Cardona\",\r\n" + 
			"            \"numeroDocumento\": \"1234567890\",\r\n" + 
			"            \"tipoDocumento\": \"FS001\"\r\n" + 
			"        },\r\n" + 
			"        \"producto\": [\r\n" + 
			"            {\r\n" + 
			"                \"categoria\": [\r\n" + 
			"                    {\r\n" + 
			"                        \"codigoCategoria\": \"AMEX\",\r\n" + 
			"                        \"subproducto\": [\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"304\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": \"4900000.00\",\r\n" + 
			"                                \"cupoMinimo\": \"700000.00\",\r\n" + 
			"                                \"descripcionSubproducto\": \"AMERICAN EXPRESS AZUL\",\r\n" + 
			"                                \"idSubproducto\": \"34109\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"2x1 en boletas y cupón de descuento para crispetas, todos los días, en Cinemark.\",\r\n" + 
			"                                    \"2 meses gratis de Rappi Prime, con envío sin costo para pedidos superiores a $10.000 a través de Rappi.\",\r\n" + 
			"                                    \"Protege tus compras con un seguro hasta por $1.000 dólares por daño accidental o hurto.\",\r\n" + 
			"                                    \"6,600 COP \\\\u003d 6 Puntos\",\r\n" + 
			"                                    \"Cuota de Manejo Mensual: $23,700. \\\\n Exoneración de cuota de manejo por 12 meses. La exoneración se inicia desde la fecha de activación.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"AMERICAN EXPRESS AZUL\"\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"305\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": \"15000000.00\",\r\n" + 
			"                                \"cupoMinimo\": \"3000000.00\",\r\n" + 
			"                                \"descripcionSubproducto\": \"AMERICAN EXPRESS VERDE\",\r\n" + 
			"                                \"idSubproducto\": \"34111\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"¡Bienvenido! Recibe 10.720 Puntos Colombia por facturación de mínimo 2 millones durante los primeros 6 meses.\",\r\n" + 
			"                                    \"2x1 en boletas y cupón de descuento para crispetas, todos los días, en Cinemark.\",\r\n" + 
			"                                    \"¡Más puntos! Recibe doble puntaje por compras en Spa’s, Gimnasios, Droguerías, Salones de belleza, tiendas deportivas, entre otros.\",\r\n" + 
			"                                    \"4,800 COP \\\\u003d 6 Puntos\",\r\n" + 
			"                                    \"Cuota de Manejo Mensual: $29,000. \\\\n Exoneración de cuota de manejo por 6 meses. La exoneración se inicia desde la fecha de activación.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"AMERICAN EXPRESS VERDE\"\r\n" + 
			"                            },\r\n" + 
			"                        ]\r\n" + 
			"                    }\r\n" + 
			"                ],\r\n" + 
			"                \"idProducto\": \"12\",\r\n" + 
			"                \"montoOtorgado\": \"15000000.00\",\r\n" + 
			"                \"nombreProducto\": \"TARJETA DE CREDITO\"\r\n" + 
			"            }\r\n" + 
			"        ]\r\n" + 
			"    }\r\n" + 
			"}";

}
