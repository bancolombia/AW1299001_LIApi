package bancolombia.dtd.vd.sucursales.api.datosCliente;

import com.google.gson.Gson;

import bancolombia.dtd.vd.li.dto.proxy.datosCliente.RecuperarDatosBasicosClienteResponse;
import bancolombia.dtd.vd.li.dto.proxy.datosCliente.RecuperarDatosUbicacionClienteResponse;


public class ConstantesTest {

	public static String CARGAUTILINFOASESORCEDULA = "{'datoConsulta': '1018418391','tipoConsulta': '3','idSesion': '2werwd32rewr233de324d3f','pasoFuncional': 'paso_li_101'}";
	
	public static String CARGAUTILCONSULTAMOTOR = "{\"tipoDocumento\":\"FS001\",\"numeroDocumento\":\"1234567\",\"idSesion\":\"123abcd4321\",\"pasoFuncional\":\"paso_li_103_M\"}";
	
	public static String CARGAUTILINFOASESORSUCURSAL = "{'datoConsulta': '00005','tipoConsulta': '2','idSesion': '2werwd32rewr233de324d3f','pasoFuncional': 'paso_li_101'}";

	public static String RESPUESTACATALOGO = "{\"esSatisfactorio\":\"true\",\"lista\":[{\"descripcion\":\"CAROL MELISSA JARAMILLO RODRIG|31105|00006|00000066|43583347|1020452408|CAJARAMI\",\"id\":\"1018418391\"},{\"descripcion\":\"SANTIAGO ANDRES LOPEZ ROJAS|9911|00005|00000066|43583347|71261558|SANTLOPE\",\"id\":\"1037644654\"}]}";

	public static RecuperarDatosUbicacionClienteResponse RESPUESTADATOSCLIENTE = new Gson().fromJson("{\"informacionUbicacionCliente\": [\r\n" + "      {\r\n"
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
			+ "]}", RecuperarDatosUbicacionClienteResponse.class);

	public static String CARGAUTILSOLICITUDCREDITOCONSUMOPASO1 = "{	\"idSesion\": \"sadsadsadsa\",\r\n"
			+ " 	\"pasoFuncional\": \"Paso1\",\r\n" + "   	\"paso\": \"paso1\",\r\n"
			+ "   	\"tipoVenta\": \"P\",\r\n" + " 	\"tipodocumento\": \"FS001\",\r\n"
			+ "   	\"numeroDocumento\": \"1111111111\",\r\n" + "   	\"idProducto\": \"14\",\r\n"
			+ " 	\"sucursalRadicacion\": \"\",\r\n" + "   	\"codigoAsesorComercial\": \"\",\r\n"
			+ "   	\"codigoReferido\": \"\",\r\n" + "   	\"producto\":[\r\n" + "   		{\r\n"
			+ "   		\"idProducto\": \"14\"\r\n" + "   		}\r\n" + "   	]\r\n" + "}";

	public static String RESPUESTAOFERTASOLICITUDCREDITOCONSUMOPASO1 = "{\r\n" + 
			"    \"cabecera\":\r\n" + 
			"    {\r\n" + 
			"        \"estadoTransaccion\": \"\",\r\n" + 
			"        \"pasoFuncional\": \"paso1\",\r\n" + 
			"        \"numeroSolicitud\": \"VDI_2018_3795\",\r\n" + 
			"        \"extensionFuncional\": [\r\n" + 
			"        {\r\n" + 
			"            \"llave\": \"\",\r\n" + 
			"            \"valor\": \"\"\r\n" + 
			"        }]\r\n" + 
			"    },\r\n" + 
			"    \"oferta\":\r\n" + 
			"    {\r\n" + 
			"        \"informacionCliente\":\r\n" + 
			"        {\r\n" + 
			"            \"idSolicitante\": \"53924\",\r\n" + 
			"            \"tipoDocumento\": \"FS001\",\r\n" + 
			"            \"numeroDocumento\": \"1059786192\",\r\n" + 
			"            \"nombreCompletoCliente\": \" Juan Andres Bedoya Rios\",\r\n" + 
			"            \"direccionPrincipal\": \"PRINCIPAL\",\r\n" + 
			"            \"codigoCiudad\": \"5001000\",\r\n" + 
			"            \"codigoDepartamento\": \"05\",\r\n" + 
			"            \"codigoPais\": \"CO\"\r\n" + 
			"        },\r\n" + 
			"        \"producto\": [\r\n" + 
			"        {\r\n" + 
			"            \"idProducto\": \"14\",\r\n" + 
			"            \"nombreProducto\": \"libre inversion\",\r\n" + 
			"            \"montoOtorgado\": \"500000000.00\",\r\n" + 
			"            \"categoria\": [\r\n" + 
			"            {\r\n" + 
			"                \"codigoCategoria\": \"Credito de Consumo\",\r\n" + 
			"                \"subproducto\": [\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"174991\",\r\n" + 
			"                    \"nombreSubproducto\": \"LIBRE INVERSION\",\r\n" + 
			"                    \"descripcionSubproducto\": \"LIBRE INVERSION\",\r\n" + 
			"                    \"cupoMinimo\": \"0.00\",\r\n" + 
			"                    \"cupoMaximo\": \"500000000.00\",\r\n" + 
			"                    \"codigoImagen\": \"\",\r\n" + 
			"                    \"listaCondiciones\": [],\r\n" + 
			"                    \"planes\": [\r\n" + 
			"                    {\r\n" + 
			"                        \"idPlan\": \"P59\",\r\n" + 
			"                        \"factorSeguroDesempleo\": 0.00145,\r\n" + 
			"	                    \"tasas\": [\r\n" + 
			"	                    {\r\n" + 
			"	                        \"grupoRiesgo\": \"G2\",\r\n" + 
			"	                        \"montoInferiorProducto\": 1000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 11999999.0,\r\n" + 
			"	                        \"plazoInferior\": 6,\r\n" + 
			"	                        \"plazoSuperior\": 36,\r\n" + 
			"	                        \"tasaMesVencida\": 1.775412,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.213049,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.2351,\r\n" + 
			"	                        \"tasaMora\": 27.39\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"grupoRiesgo\": \"G2\",\r\n" + 
			"	                        \"montoInferiorProducto\": 1000000.00,\r\n" + 
			"	                        \"montoSuperiorProducto\": 11999999.00,\r\n" + 
			"	                        \"plazoInferior\": 6,\r\n" + 
			"	                        \"plazoSuperior\": 59,\r\n" + 
			"	                        \"tasaMesVencida\": 2.099917,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.25199004,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.2832,\r\n" + 
			"	                        \"tasaMora\": 20.42\r\n" + 
			"	                    }],\r\n" + 
			"	                    \"seguroProducto\": [\r\n" + 
			"	                    {\r\n" + 
			"	                        \"montoMinimoSeguro\": 1000000,\r\n" + 
			"	                        \"montoMaximoSeguro\": 20000000,\r\n" + 
			"	                        \"factor\": 0.00145\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"montoMinimoSeguro\": 20000001,\r\n" + 
			"	                        \"montoMaximoSeguro\": 50000000,\r\n" + 
			"	                        \"factor\": 0.00149\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"montoMinimoSeguro\": 50000001,\r\n" + 
			"	                        \"montoMaximoSeguro\": 100000000,\r\n" + 
			"	                        \"factor\": 0.00151\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"montoMinimoSeguro\": 100000001,\r\n" + 
			"	                        \"montoMaximoSeguro\": 200000000,\r\n" + 
			"	                        \"factor\": 0.00155\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"montoMinimoSeguro\": 200000001,\r\n" + 
			"	                        \"montoMaximoSeguro\": 500000001,\r\n" + 
			"	                        \"factor\": 0.00156\r\n" + 
			"	                    }]\r\n" + 
			"	                }]\r\n" + 
			"                }]\r\n" + 
			"            }]\r\n" + 
			"        }]\r\n" + 
			"    }\r\n" + 
			"}";

	public static String CARGAUTILSOLICITUDCREDITOCONSUMOPASO2 = "{  	\"idSesion\":\"3123123123213\",\r\n"
			+ "	\"paso\":\"paso2\",\r\n" + "	\"tipoVenta\":\"Preaprobado\",\r\n"
			+ "	\"tipodocumento\":\"FS001\",\r\n" + "	\"numeroDocumento\":\"98768001\",\r\n"
			+ "	\"idSesion\":\"sadsadsadsa\",\r\n" + "	\"idProducto\":\"14\",\r\n"
			+ "	\"sucursalRadicacion\":\"245\",\r\n" + "	\"codigoAsesorComercial\":\"423\",\r\n"
			+ "	\"codigoReferido\":\"1112\",\r\n" + "	\"numeroSolicitud\":\"1111111111-9153-14\",\r\n"
			+ "	\"nombreTarea\":\"\",\r\n" + "	\"codigoSolicitante\":\"\",\r\n" + "	\"firmaCliente\":\"\",\r\n"
			+ "	\"direccionEntrega\":\"\",\r\n" + "	\"municipioEntrega\":\"\",\r\n"
			+ "	 \"producto\": [{\"idProducto\":\"14\",\r\n" + "			       \"cupoElegido\":\"4000000\",\r\n"
			+ "			               \"plazo\":\"36\",\r\n" + "\r\n" + "			       \"cuota\":\"430000\",\r\n"
			+ "			               \"tasaMV\":\"0.6\",\r\n" + "			               \"tasaNAMV\":\"0.7\",\r\n"
			+ "\r\n" + "			       \"tasaEA\":\"1.9\",\r\n"
			+ "			               \"tasaMora\":\"3.9\",\r\n" + "\r\n"
			+ "			       \"factorSeguro\":\"3212\",\r\n"
			+ "			               \"costoTotalSeguro\":\"300000\",\r\n" + "\r\n"
			+ "			       \"tipoCuentaDesembolso\":\"2\",\r\n"
			+ "			               \"cuentaDesembolso\":\"D-00003424234\",\r\n" + "\r\n"
			+ "			       \"aceptaDebitoAutomatico\":\"true\",\r\n"
			+ "			               \"tipoCuentaDebitoAutomatico\":\"\",\r\n" + "\r\n"
			+ "			       \"cuentaDebitoAutomatico\":\"\",\r\n"
			+ "			               \"beneficiario\":[{\r\n"
			+ "								\"tipoDocumento\":\"FS001\",\r\n"
			+ "								\"numeroDocumento\":\"12344354\",\r\n" + "\r\n"
			+ "								\"nombreCompleto\":\"FFFFFFFFFFFFF\",\r\n"
			+ "								\"asignacion\":\"50\"\r\n" + "								},\r\n"
			+ "								{\"tipoDocumento\":\"FS001\",\r\n" + "\r\n"
			+ "								\"numeroDocumento\":\"77777\",\r\n"
			+ "								\"nombreCompleto\":\"AAAAAAAAAAA\",\r\n" + "\r\n"
			+ "								\"asignacion\":\"50\"       \r\n" + "								}]\r\n"
			+ "			         }]\r\n" + "}";

	public static String RESPUESTAOFERTASOLICITUDCREDITOCONSUMOPASO2 = "{\r\n" + "	\"cabecera\":{\r\n"
			+ "		\"estadoTransaccion\": \"success\",\r\n" + "		\"pasoFuncional\": \"paso2\",\r\n"
			+ "		\"numeroSolicitud\": \"XXXXXXX\"\r\n" + "	},\r\n" + "	\"documentos\": [{\r\n"
			+ "		\"tipoDocumento\": \"1\",\r\n" + "		\"documento\": \"ASDASD\",\r\n"
			+ "		\"descripcion\": \"sin descripcion1\"\r\n" + "	},\r\n" + "	{\r\n"
			+ "		\"tipoDocumento\": \"2\",\r\n" + "		\"documento\": \"ASDASD\",\r\n"
			+ "		\"descripcion\": \"sin descripcion2\"\r\n" + "	}]		\r\n" + "}";

	public static String CARGAUTILSOLICITUDCREDITOCONSUMOPASO3 = "{\r\n" + "    \"codigoAsesorComercial\": \"423\",\r\n"
			+ "    \"codigoReferido\": \"1112\",\r\n" + "    \"codigoSolicitante\": \"\",\r\n"
			+ "    \"direccionEntrega\": \"\",\r\n" + "    \"firmaCliente\": \"\",\r\n"
			+ "    \"idProducto\": \"14\",\r\n" + "    \"idSesion\": \"sadsadsadsa\",\r\n"
			+ "    \"municipioEntrega\": \"\",\r\n" + "    \"nombreTarea\": \"\",\r\n"
			+ "    \"numeroDocumento\": \"98768001\",\r\n" + "    \"numeroSolicitud\": \"1111111111-9153-14\",\r\n"
			+ "    \"paso\": \"paso3\",\r\n" + "    \"producto\": [{\r\n"
			+ "        \"aceptaDebitoAutomatico\": \"0\",\r\n" + "        \"beneficiario\": [\r\n" + "            {\r\n"
			+ "                \"asignacion\": \"50\",\r\n"
			+ "                \"nombreCompleto\": \"FFFFFFFFFFFFF\",\r\n"
			+ "                \"numeroDocumento\": \"12344354\",\r\n"
			+ "                \"tipoDocumento\": \"FS001\"\r\n" + "            },\r\n" + "            {\r\n"
			+ "                \"asignacion\": \"50\",\r\n" + "                \"nombreCompleto\": \"AAAAAAAAAAA\",\r\n"
			+ "                \"numeroDocumento\": \"77777\",\r\n" + "                \"tipoDocumento\": \"FS001\"\r\n"
			+ "            }\r\n" + "        ],\r\n" + "        \"costoTotalSeguro\": \"300000\",\r\n"
			+ "        \"cuentaDebitoAutomatico\": \"\",\r\n" + "        \"cuentaDesembolso\": \"D-00003424234\",\r\n"
			+ "        \"cuota\": \"430000\",\r\n" + "        \"cupoElegido\": \"4000000\",\r\n"
			+ "        \"factorSeguro\": \"3212\",\r\n" + "        \"idProducto\": \"14\",\r\n"
			+ "        \"plazo\": \"36\",\r\n" + "        \"tasaEA\": \"1.9\",\r\n" + "        \"tasaMV\": \"0.6\",\r\n"
			+ "        \"tasaMora\": \"3.9\",\r\n" + "        \"tasaNAMV\": \"0.7\",\r\n"
			+ "        \"tipoCuentaDebitoAutomatico\": \"\",\r\n" + "        \"tipoCuentaDesembolso\": \"2\"\r\n"
			+ "    }],\r\n" + "    \"sucursalRadicacion\": \"245\",\r\n" + "    \"tipoVenta\": \"Preaprobado\",\r\n"
			+ "    \"tipodocumento\": \"FS001\"\r\n" + "}";

	public static String RESPUESTAOFERTASOLICITUDCREDITOCONSUMOPASO3 = "{\r\n" + "	\"cabecera\":{\r\n"
			+ "		\"estadoTransaccion\": \"success\",\r\n" + "		\"pasoFuncional\": \"paso3\",\r\n"
			+ "		\"numeroSolicitud\": \"33254-TDCP\"\r\n" + "	}, \r\n" + "     \"confirmacionTransaccion\":{\r\n"
			+ "           \"codigo\":\"ok\",\r\n" + "           \"descripcion\":\"34323233\"\r\n" + "     }\r\n" + "}";

	public static String CARGAUTILRECALCULO = "{\r\n" + " \"idSesion\":\"3123123123213\",\r\n"
			+ " \"paso\":\"paso3\",\r\n" + " \"tipoVenta\":\"Preaprobado\",\r\n" + " \"tipodocumento\":\"FS001\",\r\n"
			+ " \"numeroDocumento\":\"98768001\",\r\n" + " \"idSesion\":\"sadsadsadsa\",\r\n"
			+ " \"idProducto\":\"14\",\r\n" + " \"sucursalRadicacion\":\"245\",\r\n"
			+ " \"codigoAsesorComercial\":\"423\",\r\n" + " \"codigoReferido\":\"1112\",\r\n"
			+ " \"numeroSolicitud\":\"1111111111-9153-14\",\r\n" + " \"nombreTarea\":\"\",\r\n"
			+ " \"codigoSolicitante\":\"\",\r\n" + " \"firmaCliente\":\"\",\r\n" + " \"direccionEntrega\":\"\",\r\n"
			+ " \"municipioEntrega\":\"\",\r\n" + " \"producto\":[{\r\n" + "       \"idProducto\":\"14\",\r\n"
			+ "       \"cupoElegido\":\"4000000\",\r\n" + "       \"plazo\":\"36\",\r\n"
			+ "       \"cuota\":\"430000\",\r\n" + "       \"tasaMV\":\"0.6\",\r\n" + "       \"tasaNAMV\":\"0.7\",\r\n"
			+ "       \"tasaEA\":\"1.9\",\r\n" + "       \"tasaMora\":\"3.9\",\r\n"
			+ "       \"factorSeguro\":\"3212\",\r\n" + "       \"costoTotalSeguro\":\"300000\",\r\n"
			+ "       \"tipoCuentaDesembolso\":\"2\",\r\n" + "       \"cuentaDesembolso\":\"D-00003424234\",\r\n"
			+ "       \"aceptaDebitoAutomatico\":\"0\",\r\n" + "       \"tipoCuentaDebitoAutomatico\":\"\",\r\n"
			+ "       \"cuentaDebitoAutomatico\":\"\",\r\n" + "       \"beneficiario\":[{\r\n"
			+ "         \"tipoDocumento\":\"FS001\",\r\n" + "         \"numeroDocumento\":\"12344354\",\r\n"
			+ "         \"nombreCompleto\":\"FFFFFFFFFFFFF\",\r\n" + "         \"asignacion\":\"50\"\r\n"
			+ "       },{\r\n" + "         \"tipoDocumento\":\"FS001\",\r\n"
			+ "         \"numeroDocumento\":\"77777\",\r\n" + "         \"nombreCompleto\":\"AAAAAAAAAAA\",\r\n"
			+ "         \"asignacion\":\"50\"\r\n" + "       }]\r\n" + "  }]\r\n" + "}";

	public static String RESPUESTAOFERTARECALCULO = "{\r\n" + 
			"    \"cabecera\":\r\n" + 
			"    {\r\n" + 
			"        \"estadoTransaccion\": \"\",\r\n" + 
			"        \"pasoFuncional\": \"paso1\",\r\n" + 
			"        \"numeroSolicitud\": \"VDI_2018_3795\",\r\n" + 
			"        \"extensionFuncional\": [\r\n" + 
			"        {\r\n" + 
			"            \"llave\": \"\",\r\n" + 
			"            \"valor\": \"\"\r\n" + 
			"        }]\r\n" + 
			"    },\r\n" + 
			"    \"oferta\":\r\n" + 
			"    {\r\n" + 
			"    	\"convenio\":\r\n" + 
			"        {\r\n" + 
			"            \"ciudadConvenio\": \"\",\r\n" + 
			"            \"codigoConvenio\": \"70337\",\r\n" + 
			"            \"correoElectronico\": \"\",\r\n" + 
			"            \"diaPago\": 0,\r\n" + 
			"            \"nitEmpresa\": \"\",\r\n" + 
			"            \"nombreConvenio\": \"Bancolombia\"\r\n" + 
			"        },\r\n" + 
			"        \"informacionCliente\":\r\n" + 
			"        {\r\n" + 
			"            \"idSolicitante\": \"53924\",\r\n" + 
			"            \"tipoDocumento\": \"FS001\",\r\n" + 
			"            \"numeroDocumento\": \"1059786192\",\r\n" + 
			"            \"nombreCompletoCliente\": \" Juan Andres Bedoya Rios\",\r\n" + 
			"            \"direccionPrincipal\": \"PRINCIPAL\",\r\n" + 
			"            \"codigoCiudad\": \"5001000\",\r\n" + 
			"            \"codigoDepartamento\": \"05\",\r\n" + 
			"            \"codigoPais\": \"CO\"\r\n" + 
			"        },\r\n" + 
			"        \"producto\": [\r\n" + 
			"        {\r\n" + 
			"            \"idProducto\": \"4\",\r\n" + 
			"            \"nombreProducto\": \"libre inversion\",\r\n" + 
			"            \"montoOtorgado\": \"500000000.00\",\r\n" + 
			"            \"categoria\": [\r\n" + 
			"            {\r\n" + 
			"                \"codigoCategoria\": \"Credito de Consumo\",\r\n" + 
			"                \"subproducto\": [\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"174991\",\r\n" + 
			"                    \"nombreSubproducto\": \"LIBRE INVERSION\",\r\n" + 
			"                    \"descripcionSubproducto\": \"LIBRE INVERSION\",\r\n" + 
			"                    \"cupoMinimo\": \"0.00\",\r\n" + 
			"                    \"cupoMaximo\": \"500000000.00\",\r\n" + 
			"                    \"codigoImagen\": \"\",\r\n" + 
			"                    \"listaCondiciones\": [],\r\n" + 
			"                    \"planes\": [\r\n" + 
			"                    {\r\n" + 
			"                        \"idPlan\": \"P59\",\r\n" + 
			"                        \"factorSeguroDesempleo\": 0.00115,\r\n" + 
			"                        \"tasas\": [\r\n" + 
			"                        {\r\n" + 
			"                            \"grupoRiesgo\": \"G2\",\r\n" + 
			"                            \"montoInferiorProducto\": 1000000.0,\r\n" + 
			"                            \"montoSuperiorProducto\": 11999999.0,\r\n" + 
			"                            \"plazoInferior\": 6,\r\n" + 
			"                            \"plazoSuperior\": 36,\r\n" + 
			"                            \"tasaMesVencida\": 1.775412,\r\n" + 
			"                            \"tasaNominalAnualMesVencido\": 0.213049,\r\n" + 
			"                            \"tasaEfectivaAnual\": 0.2351,\r\n" + 
			"                            \"tasaMora\": 27.39\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"grupoRiesgo\": \"G2\",\r\n" + 
			"                            \"montoInferiorProducto\": 1000000.00,\r\n" + 
			"                            \"montoSuperiorProducto\": 11999999.00,\r\n" + 
			"                            \"plazoInferior\": 6,\r\n" + 
			"                            \"plazoSuperior\": 59,\r\n" + 
			"                            \"tasaMesVencida\": 2.099917,\r\n" + 
			"                            \"tasaNominalAnualMesVencido\": 0.25199004,\r\n" + 
			"                            \"tasaEfectivaAnual\": 0.2832,\r\n" + 
			"                            \"tasaMora\": 20.42\r\n" + 
			"                        }],\r\n" + 
			"                        \"seguroProducto\": [\r\n" + 
			"                        {\r\n" + 
			"                            \"montoMinimoSeguro\": 1000000,\r\n" + 
			"                            \"montoMaximoSeguro\": 20000000,\r\n" + 
			"                            \"factor\": 0.00145\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"montoMinimoSeguro\": 20000001,\r\n" + 
			"                            \"montoMaximoSeguro\": 50000000,\r\n" + 
			"                            \"factor\": 0.00149\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"montoMinimoSeguro\": 50000001,\r\n" + 
			"                            \"montoMaximoSeguro\": 100000000,\r\n" + 
			"                            \"factor\": 0.00151\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"montoMinimoSeguro\": 100000001,\r\n" + 
			"                            \"montoMaximoSeguro\": 200000000,\r\n" + 
			"                            \"factor\": 0.00155\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"montoMinimoSeguro\": 200000001,\r\n" + 
			"                            \"montoMaximoSeguro\": 500000001,\r\n" + 
			"                            \"factor\": 0.00156\r\n" + 
			"                        }]\r\n" + 
			"                    },\r\n" + 
			"	                {\r\n" + 
			"	                        \"idPlan\": \"P58\",\r\n" + 
			"	                        \"factorSeguroDesempleo\": 0.00199,\r\n" + 
			"	                        \"tasas\": [\r\n" + 
			"	                        {\r\n" + 
			"	                            \"grupoRiesgo\": \"G2\",\r\n" + 
			"	                            \"montoInferiorProducto\": 1000000.0,\r\n" + 
			"	                            \"montoSuperiorProducto\": 11999999.0,\r\n" + 
			"	                            \"plazoInferior\": 6,\r\n" + 
			"	                            \"plazoSuperior\": 36,\r\n" + 
			"	                            \"tasaMesVencida\": 1.775412,\r\n" + 
			"	                            \"tasaNominalAnualMesVencido\": 0.213049,\r\n" + 
			"	                            \"tasaEfectivaAnual\": 0.2351,\r\n" + 
			"	                            \"tasaMora\": 27.39\r\n" + 
			"	                        },\r\n" + 
			"	                        {\r\n" + 
			"	                            \"grupoRiesgo\": \"G2\",\r\n" + 
			"	                            \"montoInferiorProducto\": 1000000.00,\r\n" + 
			"	                            \"montoSuperiorProducto\": 11999999.00,\r\n" + 
			"	                            \"plazoInferior\": 6,\r\n" + 
			"	                            \"plazoSuperior\": 59,\r\n" + 
			"	                            \"tasaMesVencida\": 2.099917,\r\n" + 
			"	                            \"tasaNominalAnualMesVencido\": 0.25199004,\r\n" + 
			"	                            \"tasaEfectivaAnual\": 0.2832,\r\n" + 
			"	                            \"tasaMora\": 20.42\r\n" + 
			"	                        }],\r\n" + 
			"	                        \"seguroProducto\": [\r\n" + 
			"	                        {\r\n" + 
			"	                            \"montoMinimoSeguro\": 1000000,\r\n" + 
			"	                            \"montoMaximoSeguro\": 20000000,\r\n" + 
			"	                            \"factor\": 0.00145\r\n" + 
			"	                        },\r\n" + 
			"	                        {\r\n" + 
			"	                            \"montoMinimoSeguro\": 20000001,\r\n" + 
			"	                            \"montoMaximoSeguro\": 50000000,\r\n" + 
			"	                            \"factor\": 0.00149\r\n" + 
			"	                        },\r\n" + 
			"	                        {\r\n" + 
			"	                            \"montoMinimoSeguro\": 50000001,\r\n" + 
			"	                            \"montoMaximoSeguro\": 100000000,\r\n" + 
			"	                            \"factor\": 0.00151\r\n" + 
			"	                        },\r\n" + 
			"	                        {\r\n" + 
			"	                            \"montoMinimoSeguro\": 100000001,\r\n" + 
			"	                            \"montoMaximoSeguro\": 200000000,\r\n" + 
			"	                            \"factor\": 0.00155\r\n" + 
			"	                        },\r\n" + 
			"	                        {\r\n" + 
			"	                            \"montoMinimoSeguro\": 200000001,\r\n" + 
			"	                            \"montoMaximoSeguro\": 500000001,\r\n" + 
			"	                            \"factor\": 0.00156\r\n" + 
			"	                        }]\r\n" + 
			"	                    }]\r\n" + 
			"                }]\r\n" + 
			"            }]\r\n" + 
			"        }]\r\n" + 
			"    }\r\n" + 
			"}";

	public static String CARGAUTILENVIOCORREO = "{\"idSesion\":\"3123123123213\",\"nombreCliente\":\"PepitoPerez\",\"documentoCliente\":\"123456789\",\"correo\":\"leidy.roman@ibm.com\",\"documentoPdf\":\"hkhkghjkgkfjhbjklhop\",\"cupoSolicitado\":\"5000000\",\"nombreProducto\":\"Libreinversion\",\"pasoFuncional\":\"paso_li_401\",\"tasaNominal\":\"0.22\",\"plazoCredito\":\"36\",\"valorCuota\":\"34453\",\"cuentaDebito\":\"D-123456789087\",\"numeroCredito\":\"21312321\",\"seguroEmpleado\":false,\"numeroIntentosEnvio\":\"1\"}";

	public static String CARGAUTILGESTIONPROSPECTOCONSUMORQ = "{\r\n"
			+ "  \"idSesion\": \"987987uytyut765765ytrerte\",\r\n" + "  \"propiedades\": [\r\n" + "    {\r\n"
			+ "      \"llave\": \"PROXY_ENDPOINT_GESTIONAR_PROSPECTO\",\r\n"
			+ "      \"valor\": \"http://localhost:9081/GestionarProspectoLI/GestionarProspectoLI\"\r\n" + "    },\r\n"
			+ "    {\r\n" + "      \"llave\": \"PROXY_SYSTEM_ID_GESTIONAR_PROSPECTO\",\r\n"
			+ "      \"valor\": \"AW1177\"\r\n" + "    },\r\n" + "    {\r\n"
			+ "      \"llave\": \"PROXY_CLASSIFICATION_GESTIONAR_PROSPECTO\",\r\n"
			+ "      \"valor\": \"http://grupobancolombia.com/clas/AplicacionesActuales#\"\r\n" + "    },\r\n"
			+ "    {\r\n" + "      \"llave\": \"PROXY_GESTIONAR_PROSPECTO_NAMESPACE\",\r\n"
			+ "      \"valor\": \"http://grupobancolombia.com/intf/Clientes/GestionClientes/GestionarProspecto/V2.0\"\r\n"
			+ "    },\r\n" + "    {\r\n" + "      \"llave\": \"PROXY_REQUEST_TIMEOUT_GESTIONAR_PROSPECTO\",\r\n"
			+ "      \"valor\": \"600\"\r\n" + "    },\r\n" + "    {\r\n"
			+ "      \"llave\": \"PROXY_CONNECTION_TIMEOUT_GESTIONAR_PROSPECTO\",\r\n" + "      \"valor\": \"600\"\r\n"
			+ "    },\r\n" + "    {\r\n"
			+ "      \"llave\": \"PROXY_CODIGO_RESPUESTA_EXITOSA_GESTIONAR_PROSPECTO\",\r\n"
			+ "      \"valor\": \"000\"\r\n" + "    },\r\n" + "    {\r\n"
			+ "      \"llave\": \"PROXY_CODIGO_RESPUESTA_EXITOSA_GESTIONAR_PROSPECTO\",\r\n"
			+ "      \"valor\": \"000\"\r\n" + "    }\r\n" + "  ],\r\n" + "  \"tipoDocumento\": \"FS001\",\r\n"
			+ "  \"numeroDocumento\": \"220818104\",\r\n" + "  \"userName\": \"user\",\r\n"
			+ "  \"infoVeridica\": \"1\",\r\n" + "  \"mdActualizacion\": \"10\",\r\n"
			+ "  \"llaveDireccion\": \"0071011808\",\r\n" + "  \"correspondencia\": \"1\",\r\n"
			+ "  \"tipoDireccion\": \"Z001\",\r\n" + "  \"direccion\": \"Manzana 10 # 2\",\r\n"
			+ "  \"pais\": \"CO\",\r\n" + "  \"departamento\": \"05\",\r\n" + "  \"ciudad\": \"5045000\",\r\n"
			+ "  \"email\": \"prueba.pru.co\",\r\n" + "  \"celular\": \"3122222222\"\r\n" + "}";

	public static String CARGAUTILVERTARJETAS = "{  \r\n" + "   \"producto\":[  \r\n" + "      {  \r\n"
			+ "         \"beneficiario\":[  \r\n" + "\r\n" + "         ],\r\n"
			+ "         \"cupoElegido\":\"1000000\",\r\n" + "         \"idProducto\":\"12\"\r\n" + "      }\r\n"
			+ "   ],\r\n" + "   \"numeroSolicitud\":\"1111111-3554-12\",\r\n" + "   \"paso\":\"paso1\",\r\n"
			+ "   \"idSesion\":\"YbtMMy0D8-i4vdHfwTJTsw\",\r\n" + "   \"pasoFuncional\":\"paso_li_201\",\r\n"
			+ "   \"hash\":\"619ff3bb9e9e824e615f7eb88deb7d7e\"\r\n" + "}";

	public static String RESPUESTAOFERTAVERTARJETAS = "{\r\n" + "    \"cabecera\": {\r\n"
			+ "        \"estadoTransaccion\": \"\",\r\n" + "        \"extensionFuncional\": [\r\n" + "            {\r\n"
			+ "                \"llave\": \"\",\r\n" + "                \"valor\": \"\"\r\n" + "            }\r\n"
			+ "        ],\r\n" + "        \"numeroSolicitud\": \"VDI_2019_10947\",\r\n"
			+ "        \"pasoFuncional\": \"paso1\"\r\n" + "    },\r\n" + "    \"oferta\": {\r\n"
			+ "        \"informacionCliente\": {\r\n" + "            \"codigoCiudad\": \"17001000\",\r\n"
			+ "            \"codigoDepartamento\": \"17\",\r\n" + "            \"codigoPais\": \"CO\",\r\n"
			+ "            \"direccionPrincipal\": \"CR 5 15 70 PS 3 LO 68\",\r\n"
			+ "            \"idSolicitante\": \"13353\",\r\n"
			+ "            \"nombreCompletoCliente\": \" Roberto Tadeo Espitia Cardona\",\r\n"
			+ "            \"numeroDocumento\": \"1234567890\",\r\n" + "            \"tipoDocumento\": \"FS001\"\r\n"
			+ "        },\r\n" + "        \"producto\": [\r\n" + "            {\r\n"
			+ "                \"categoria\": [\r\n" + "                    {\r\n"
			+ "                        \"codigoCategoria\": \"AMEX\",\r\n"
			+ "                        \"subproducto\": [\r\n" + "                            {\r\n"
			+ "                                \"codigoImagen\": \"304\",\r\n"
			+ "                                \"comisionDisponibilidad\": 0.0,\r\n"
			+ "                                \"cupoMaximo\": \"4900000.00\",\r\n"
			+ "                                \"cupoMinimo\": \"700000.00\",\r\n"
			+ "                                \"descripcionSubproducto\": \"AMERICAN EXPRESS AZUL\",\r\n"
			+ "                                \"idSubproducto\": \"34109\",\r\n"
			+ "                                \"isProductOwner\": false,\r\n"
			+ "                                \"listaCondiciones\": [\r\n"
			+ "                                    \"2x1 en boletas y cupón de descuento para crispetas, todos los días, en Cinemark.\",\r\n"
			+ "                                    \"2 meses gratis de Rappi Prime, con envío sin costo para pedidos superiores a $10.000 a través de Rappi.\",\r\n"
			+ "                                    \"Protege tus compras con un seguro hasta por $1.000 dólares por daño accidental o hurto.\",\r\n"
			+ "                                    \"6,600 COP \\\\u003d 6 Puntos\",\r\n"
			+ "                                    \"Cuota de Manejo Mensual: $23,700. \\\\n Exoneración de cuota de manejo por 12 meses. La exoneración se inicia desde la fecha de activación.\"\r\n"
			+ "                                ],\r\n"
			+ "                                \"nombreSubproducto\": \"AMERICAN EXPRESS AZUL\"\r\n"
			+ "                            },\r\n" + "                            {\r\n"
			+ "                                \"codigoImagen\": \"305\",\r\n"
			+ "                                \"comisionDisponibilidad\": 0.0,\r\n"
			+ "                                \"cupoMaximo\": \"15000000.00\",\r\n"
			+ "                                \"cupoMinimo\": \"3000000.00\",\r\n"
			+ "                                \"descripcionSubproducto\": \"AMERICAN EXPRESS VERDE\",\r\n"
			+ "                                \"idSubproducto\": \"34111\",\r\n"
			+ "                                \"isProductOwner\": false,\r\n"
			+ "                                \"listaCondiciones\": [\r\n"
			+ "                                    \"¡Bienvenido! Recibe 10.720 Puntos Colombia por facturación de mínimo 2 millones durante los primeros 6 meses.\",\r\n"
			+ "                                    \"2x1 en boletas y cupón de descuento para crispetas, todos los días, en Cinemark.\",\r\n"
			+ "                                    \"¡Más puntos! Recibe doble puntaje por compras en Spa’s, Gimnasios, Droguerías, Salones de belleza, tiendas deportivas, entre otros.\",\r\n"
			+ "                                    \"4,800 COP \\\\u003d 6 Puntos\",\r\n"
			+ "                                    \"Cuota de Manejo Mensual: $29,000. \\\\n Exoneración de cuota de manejo por 6 meses. La exoneración se inicia desde la fecha de activación.\"\r\n"
			+ "                                ],\r\n"
			+ "                                \"nombreSubproducto\": \"AMERICAN EXPRESS VERDE\"\r\n"
			+ "                            },\r\n" + "                        ]\r\n" + "                    }\r\n"
			+ "                ],\r\n" + "                \"idProducto\": \"12\",\r\n"
			+ "                \"montoOtorgado\": \"15000000.00\",\r\n"
			+ "                \"nombreProducto\": \"TARJETA DE CREDITO\"\r\n" + "            }\r\n" + "        ]\r\n"
			+ "    }\r\n" + "}";

	public static final String RESPUESTAOFERTASOLICITUDCREDITOCONSUMOPASO1LIBRANZA = "{\r\n" + 
			"    \"cabecera\":\r\n" + 
			"    {\r\n" + 
			"        \"estadoTransaccion\": \"\",\r\n" + 
			"        \"extensionFuncional\": [\r\n" + 
			"        {\r\n" + 
			"            \"llave\": \"\",\r\n" + 
			"            \"valor\": \"\"\r\n" + 
			"        }],\r\n" + 
			"        \"numeroSolicitud\": \"SUC_2019_3731\",\r\n" + 
			"        \"pasoFuncional\": \"paso1\"\r\n" + 
			"    },\r\n" + 
			"    \"oferta\":\r\n" + 
			"    {\r\n" + 
			"        \"convenio\":\r\n" + 
			"        {\r\n" + 
			"            \"ciudadConvenio\": \"MANIZALEZ                     \",\r\n" + 
			"            \"codigoConvenio\": \"70001\",\r\n" + 
			"            \"correoElectronico\": \"\",\r\n" + 
			"            \"diaPago\": 10,\r\n" + 
			"            \"nitEmpresa\": \"000000810000450\",\r\n" + 
			"            \"nombreConvenio\": \"CONTACTAMOS SAS               \"\r\n" + 
			"        },\r\n" + 
			"        \"informacionCliente\":\r\n" + 
			"        {\r\n" + 
			"            \"codigoCiudad\": \"17001000\",\r\n" + 
			"            \"codigoDepartamento\": \"17\",\r\n" + 
			"            \"codigoPais\": \"CO\",\r\n" + 
			"            \"direccionPrincipal\": \"CL 28 23 C 10 AP 204 C\",\r\n" + 
			"            \"idSolicitante\": \"6441\",\r\n" + 
			"            \"nombreCompletoCliente\": \" Juan Esteban Callejas Cardona\",\r\n" + 
			"            \"numeroDocumento\": \"18062995521\",\r\n" + 
			"            \"tipoDocumento\": \"FS001\"\r\n" + 
			"        },\r\n" + 
			"        \"producto\": [\r\n" + 
			"        {\r\n" + 
			"            \"categoria\": [\r\n" + 
			"            {\r\n" + 
			"                \"codigoCategoria\": \"Credito de Consumo\",\r\n" + 
			"                \"subproducto\": [\r\n" + 
			"                {\r\n" + 
			"                    \"codigoImagen\": \"P99\",\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"cupoMaximo\": \"10000000.00\",\r\n" + 
			"                    \"cupoMinimo\": \"1000000.00\",\r\n" + 
			"                    \"descripcionSubproducto\": \"Preaprobado Libranza\",\r\n" + 
			"                    \"idSubproducto\": \"9630\",\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"listaCondiciones\": [],\r\n" + 
			"                    \"nombreSubproducto\": \"Preaprobado Libranza\",\r\n" + 
			"                    \"planes\": [\r\n" + 
			"                    {\r\n" + 
			"                        \"idPlan\": \"P59\",\r\n" + 
			"                        \"factorSeguroDesempleo\": 0.00145,\r\n" + 
			"	                    \"seguroProducto\": [\r\n" + 
			"	                    {\r\n" + 
			"	                        \"factor\": 0.00145,\r\n" + 
			"	                        \"montoMaximoSeguro\": 20000000,\r\n" + 
			"	                        \"montoMinimoSeguro\": 1000000\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"factor\": 0.00149,\r\n" + 
			"	                        \"montoMaximoSeguro\": 50000000,\r\n" + 
			"	                        \"montoMinimoSeguro\": 20000001\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"factor\": 0.00151,\r\n" + 
			"	                        \"montoMaximoSeguro\": 100000000,\r\n" + 
			"	                        \"montoMinimoSeguro\": 50000001\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"factor\": 0.00155,\r\n" + 
			"	                        \"montoMaximoSeguro\": 200000000,\r\n" + 
			"	                        \"montoMinimoSeguro\": 100000001\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"factor\": 0.00156,\r\n" + 
			"	                        \"montoMaximoSeguro\": 500000000,\r\n" + 
			"	                        \"montoMinimoSeguro\": 200000001\r\n" + 
			"	                    }],\r\n" + 
			"	                    \"tasas\": [\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 10.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 1000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 11999999.0,\r\n" + 
			"	                        \"plazoInferior\": 61,\r\n" + 
			"	                        \"plazoSuperior\": 72,\r\n" + 
			"	                        \"puntosDtf\": 10.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 2.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 12000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 29999999.0,\r\n" + 
			"	                        \"plazoInferior\": 61,\r\n" + 
			"	                        \"plazoSuperior\": 72,\r\n" + 
			"	                        \"puntosDtf\": 1.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 3.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 150000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 299999999.0,\r\n" + 
			"	                        \"plazoInferior\": 85,\r\n" + 
			"	                        \"plazoSuperior\": 96,\r\n" + 
			"	                        \"puntosDtf\": 2.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 4.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 300000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 300000000.0,\r\n" + 
			"	                        \"plazoInferior\": 85,\r\n" + 
			"	                        \"plazoSuperior\": 96,\r\n" + 
			"	                        \"puntosDtf\": 3.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 5.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 30000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 59999999.0,\r\n" + 
			"	                        \"plazoInferior\": 61,\r\n" + 
			"	                        \"plazoSuperior\": 72,\r\n" + 
			"	                        \"puntosDtf\": 4.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 6.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 300000001.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 599999999.0,\r\n" + 
			"	                        \"plazoInferior\": 85,\r\n" + 
			"	                        \"plazoSuperior\": 96,\r\n" + 
			"	                        \"puntosDtf\": 5.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 7.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 60000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 149999999.0,\r\n" + 
			"	                        \"plazoInferior\": 61,\r\n" + 
			"	                        \"plazoSuperior\": 72,\r\n" + 
			"	                        \"puntosDtf\": 6.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 8.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 150000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 299999999.0,\r\n" + 
			"	                        \"plazoInferior\": 61,\r\n" + 
			"	                        \"plazoSuperior\": 72,\r\n" + 
			"	                        \"puntosDtf\": 7.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 9.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 300000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 300000000.0,\r\n" + 
			"	                        \"plazoInferior\": 61,\r\n" + 
			"	                        \"plazoSuperior\": 72,\r\n" + 
			"	                        \"puntosDtf\": 8.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 10.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 300000001.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 599999999.0,\r\n" + 
			"	                        \"plazoInferior\": 61,\r\n" + 
			"	                        \"plazoSuperior\": 72,\r\n" + 
			"	                        \"puntosDtf\": 9.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 11.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 1000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 11999999.0,\r\n" + 
			"	                        \"plazoInferior\": 73,\r\n" + 
			"	                        \"plazoSuperior\": 84,\r\n" + 
			"	                        \"puntosDtf\": 10.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 12.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 12000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 29999999.0,\r\n" + 
			"	                        \"plazoInferior\": 73,\r\n" + 
			"	                        \"plazoSuperior\": 84,\r\n" + 
			"	                        \"puntosDtf\": 9.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 1.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 60000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 149999999.0,\r\n" + 
			"	                        \"plazoInferior\": 73,\r\n" + 
			"	                        \"plazoSuperior\": 84,\r\n" + 
			"	                        \"puntosDtf\": 8.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 2.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 30000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 59999999.0,\r\n" + 
			"	                        \"plazoInferior\": 73,\r\n" + 
			"	                        \"plazoSuperior\": 84,\r\n" + 
			"	                        \"puntosDtf\": 7.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 3.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 150000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 299999999.0,\r\n" + 
			"	                        \"plazoInferior\": 73,\r\n" + 
			"	                        \"plazoSuperior\": 84,\r\n" + 
			"	                        \"puntosDtf\": 6.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 4.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 300000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 300000000.0,\r\n" + 
			"	                        \"plazoInferior\": 73,\r\n" + 
			"	                        \"plazoSuperior\": 84,\r\n" + 
			"	                        \"puntosDtf\": 5.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 5.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 300000001.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 599999999.0,\r\n" + 
			"	                        \"plazoInferior\": 73,\r\n" + 
			"	                        \"plazoSuperior\": 84,\r\n" + 
			"	                        \"puntosDtf\": 4.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 6.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 1000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 11999999.0,\r\n" + 
			"	                        \"plazoInferior\": 85,\r\n" + 
			"	                        \"plazoSuperior\": 96,\r\n" + 
			"	                        \"puntosDtf\": 3.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 7.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 12000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 29999999.0,\r\n" + 
			"	                        \"plazoInferior\": 85,\r\n" + 
			"	                        \"plazoSuperior\": 96,\r\n" + 
			"	                        \"puntosDtf\": 2.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 8.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 30000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 59999999.0,\r\n" + 
			"	                        \"plazoInferior\": 85,\r\n" + 
			"	                        \"plazoSuperior\": 96,\r\n" + 
			"	                        \"puntosDtf\": 1.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    },\r\n" + 
			"	                    {\r\n" + 
			"	                        \"dtf\": 9.0,\r\n" + 
			"	                        \"grupoRiesgo\": \"G4\",\r\n" + 
			"	                        \"montoInferiorProducto\": 60000000.0,\r\n" + 
			"	                        \"montoSuperiorProducto\": 149999999.0,\r\n" + 
			"	                        \"plazoInferior\": 85,\r\n" + 
			"	                        \"plazoSuperior\": 96,\r\n" + 
			"	                        \"puntosDtf\": 10.0,\r\n" + 
			"	                        \"tasaEfectivaAnual\": 0.3137,\r\n" + 
			"	                        \"tasaMesVencida\": 2.299917,\r\n" + 
			"	                        \"tasaMora\": 26.12,\r\n" + 
			"	                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n" + 
			"	                        \"tipoTasa\": \"F\"\r\n" + 
			"	                    }]\r\n" + 
			"					}]\r\n" + 
			"                }]\r\n" + 
			"            }],\r\n" + 
			"            \"idProducto\": \"4\",\r\n" + 
			"            \"montoOtorgado\": \"10000000.00\",\r\n" + 
			"            \"nombreProducto\": \"PREAPROBADOS DE LIBRANZA\"\r\n" + 
			"        }]\r\n" + 
			"    }\r\n" + 
			"}";

	public static final String RESPUESTAOFERTASOLICITUDCREDITOCONSUMOPASO1LIBRANZANOPERMITIDA = "{\r\n"
			+ "    \"cabecera\": {\r\n" + "        \"estadoTransaccion\": \"\",\r\n"
			+ "        \"extensionFuncional\": [\r\n" + "            {\r\n" + "                \"llave\": \"\",\r\n"
			+ "                \"valor\": \"\"\r\n" + "            }\r\n" + "        ],\r\n"
			+ "        \"numeroSolicitud\": \"SUC_2019_3731\",\r\n" + "        \"pasoFuncional\": \"paso1\"\r\n"
			+ "    },\r\n" + "    \"oferta\": {\r\n" + "        \"convenio\": {\r\n"
			+ "            \"ciudadConvenio\": \"MANIZALEZ                     \",\r\n"
			+ "            \"codigoConvenio\": \"1212\",\r\n" + "            \"correoElectronico\": \"\",\r\n"
			+ "            \"diaPago\": 10,\r\n" + "            \"nitEmpresa\": \"000000810000450\",\r\n"
			+ "            \"nombreConvenio\": \"CONTACTAMOS SAS               \"\r\n" + "        },\r\n"
			+ "        \"informacionCliente\": {\r\n" + "            \"codigoCiudad\": \"17001000\",\r\n"
			+ "            \"codigoDepartamento\": \"17\",\r\n" + "            \"codigoPais\": \"CO\",\r\n"
			+ "            \"direccionPrincipal\": \"CL 28 23 C 10 AP 204 C\",\r\n"
			+ "            \"idSolicitante\": \"6441\",\r\n"
			+ "            \"nombreCompletoCliente\": \" Juan Esteban Callejas Cardona\",\r\n"
			+ "            \"numeroDocumento\": \"18062995521\",\r\n" + "            \"tipoDocumento\": \"FS001\"\r\n"
			+ "        },\r\n" + "        \"producto\": [\r\n" + "            {\r\n"
			+ "                \"categoria\": [\r\n" + "                    {\r\n"
			+ "                        \"codigoCategoria\": \"Credito de Consumo\",\r\n"
			+ "                        \"subproducto\": [\r\n" + "                            {\r\n"
			+ "                                \"codigoImagen\": \"P99\",\r\n"
			+ "                                \"comisionDisponibilidad\": 0.0,\r\n"
			+ "                                \"cupoMaximo\": \"10000000.00\",\r\n"
			+ "                                \"cupoMinimo\": \"1000000.00\",\r\n"
			+ "                                \"descripcionSubproducto\": \"Preaprobado Libranza\",\r\n"
			+ "                                \"idSubproducto\": \"9630\",\r\n"
			+ "                                \"isProductOwner\": false,\r\n"
			+ "                                \"listaCondiciones\": [],\r\n"
			+ "                                \"nombreSubproducto\": \"Preaprobado Libranza\",\r\n"
			+ "                                \"seguroProducto\": [\r\n" + "                                    {\r\n"
			+ "                                        \"factor\": 0.00145,\r\n"
			+ "                                        \"montoMaximoSeguro\": 20000000,\r\n"
			+ "                                        \"montoMinimoSeguro\": 1000000\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"factor\": 0.00149,\r\n"
			+ "                                        \"montoMaximoSeguro\": 50000000,\r\n"
			+ "                                        \"montoMinimoSeguro\": 20000001\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"factor\": 0.00151,\r\n"
			+ "                                        \"montoMaximoSeguro\": 100000000,\r\n"
			+ "                                        \"montoMinimoSeguro\": 50000001\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"factor\": 0.00155,\r\n"
			+ "                                        \"montoMaximoSeguro\": 200000000,\r\n"
			+ "                                        \"montoMinimoSeguro\": 100000001\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"factor\": 0.00156,\r\n"
			+ "                                        \"montoMaximoSeguro\": 500000000,\r\n"
			+ "                                        \"montoMinimoSeguro\": 200000001\r\n"
			+ "                                    }\r\n" + "                                ],\r\n"
			+ "                                \"tasas\": [\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 10.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 1000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 11999999.0,\r\n"
			+ "                                        \"plazoInferior\": 61,\r\n"
			+ "                                        \"plazoSuperior\": 72,\r\n"
			+ "                                        \"puntosDtf\": 10.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 2.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 12000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 29999999.0,\r\n"
			+ "                                        \"plazoInferior\": 61,\r\n"
			+ "                                        \"plazoSuperior\": 72,\r\n"
			+ "                                        \"puntosDtf\": 1.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 3.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 150000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 299999999.0,\r\n"
			+ "                                        \"plazoInferior\": 85,\r\n"
			+ "                                        \"plazoSuperior\": 96,\r\n"
			+ "                                        \"puntosDtf\": 2.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 4.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 300000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 300000000.0,\r\n"
			+ "                                        \"plazoInferior\": 85,\r\n"
			+ "                                        \"plazoSuperior\": 96,\r\n"
			+ "                                        \"puntosDtf\": 3.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 5.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 30000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 59999999.0,\r\n"
			+ "                                        \"plazoInferior\": 61,\r\n"
			+ "                                        \"plazoSuperior\": 72,\r\n"
			+ "                                        \"puntosDtf\": 4.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 6.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 300000001.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 599999999.0,\r\n"
			+ "                                        \"plazoInferior\": 85,\r\n"
			+ "                                        \"plazoSuperior\": 96,\r\n"
			+ "                                        \"puntosDtf\": 5.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 7.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 60000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 149999999.0,\r\n"
			+ "                                        \"plazoInferior\": 61,\r\n"
			+ "                                        \"plazoSuperior\": 72,\r\n"
			+ "                                        \"puntosDtf\": 6.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 8.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 150000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 299999999.0,\r\n"
			+ "                                        \"plazoInferior\": 61,\r\n"
			+ "                                        \"plazoSuperior\": 72,\r\n"
			+ "                                        \"puntosDtf\": 7.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 9.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 300000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 300000000.0,\r\n"
			+ "                                        \"plazoInferior\": 61,\r\n"
			+ "                                        \"plazoSuperior\": 72,\r\n"
			+ "                                        \"puntosDtf\": 8.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 10.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 300000001.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 599999999.0,\r\n"
			+ "                                        \"plazoInferior\": 61,\r\n"
			+ "                                        \"plazoSuperior\": 72,\r\n"
			+ "                                        \"puntosDtf\": 9.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 11.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 1000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 11999999.0,\r\n"
			+ "                                        \"plazoInferior\": 73,\r\n"
			+ "                                        \"plazoSuperior\": 84,\r\n"
			+ "                                        \"puntosDtf\": 10.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 12.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 12000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 29999999.0,\r\n"
			+ "                                        \"plazoInferior\": 73,\r\n"
			+ "                                        \"plazoSuperior\": 84,\r\n"
			+ "                                        \"puntosDtf\": 9.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 1.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 60000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 149999999.0,\r\n"
			+ "                                        \"plazoInferior\": 73,\r\n"
			+ "                                        \"plazoSuperior\": 84,\r\n"
			+ "                                        \"puntosDtf\": 8.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 2.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 30000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 59999999.0,\r\n"
			+ "                                        \"plazoInferior\": 73,\r\n"
			+ "                                        \"plazoSuperior\": 84,\r\n"
			+ "                                        \"puntosDtf\": 7.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 3.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 150000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 299999999.0,\r\n"
			+ "                                        \"plazoInferior\": 73,\r\n"
			+ "                                        \"plazoSuperior\": 84,\r\n"
			+ "                                        \"puntosDtf\": 6.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 4.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 300000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 300000000.0,\r\n"
			+ "                                        \"plazoInferior\": 73,\r\n"
			+ "                                        \"plazoSuperior\": 84,\r\n"
			+ "                                        \"puntosDtf\": 5.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 5.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 300000001.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 599999999.0,\r\n"
			+ "                                        \"plazoInferior\": 73,\r\n"
			+ "                                        \"plazoSuperior\": 84,\r\n"
			+ "                                        \"puntosDtf\": 4.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 6.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 1000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 11999999.0,\r\n"
			+ "                                        \"plazoInferior\": 85,\r\n"
			+ "                                        \"plazoSuperior\": 96,\r\n"
			+ "                                        \"puntosDtf\": 3.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 7.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 12000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 29999999.0,\r\n"
			+ "                                        \"plazoInferior\": 85,\r\n"
			+ "                                        \"plazoSuperior\": 96,\r\n"
			+ "                                        \"puntosDtf\": 2.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 8.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 30000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 59999999.0,\r\n"
			+ "                                        \"plazoInferior\": 85,\r\n"
			+ "                                        \"plazoSuperior\": 96,\r\n"
			+ "                                        \"puntosDtf\":1.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    },\r\n" + "                                    {\r\n"
			+ "                                        \"dtf\": 9.0,\r\n"
			+ "                                        \"grupoRiesgo\": \"G4\",\r\n"
			+ "                                        \"montoInferiorProducto\": 60000000.0,\r\n"
			+ "                                        \"montoSuperiorProducto\": 149999999.0,\r\n"
			+ "                                        \"plazoInferior\": 85,\r\n"
			+ "                                        \"plazoSuperior\": 96,\r\n"
			+ "                                        \"puntosDtf\": 10.0,\r\n"
			+ "                                        \"tasaEfectivaAnual\": 0.3137,\r\n"
			+ "                                        \"tasaMesVencida\": 2.299917,\r\n"
			+ "                                        \"tasaMora\": 26.12,\r\n"
			+ "                                        \"tasaNominalAnualMesVencido\": 0.27599,\r\n"
			+ "                                        \"tipoTasa\": \"F\"\r\n"
			+ "                                    }\r\n" + "                                ]\r\n"
			+ "                            }\r\n" + "                        ]\r\n" + "                    }\r\n"
			+ "                ],\r\n" + "                \"idProducto\": \"4\",\r\n"
			+ "                \"montoOtorgado\": \"10000000.00\",\r\n"
			+ "                \"nombreProducto\": \"PREAPROBADOS DE LIBRANZA\"\r\n" + "            }\r\n"
			+ "        ]\r\n" + "    }\r\n" + "}";
	
	public static final String RESPUESTAOFERTASOLICITUDCREDITOCONSUMOPASO3ERR = "{" + "	'cabecera':{"
			+ "		'estadoTransaccion': 'success'," + "		'pasoFuncional': 'paso3',"
			+ "		'numeroSolicitud': '33254-TDCP'" + "	}, " + "     'confirmacionTransaccion':{"
			+ "           'codigo':'error'," + "           'descripcion':'34323233'" + "     }" + "}";
	
	public static final RecuperarDatosBasicosClienteResponse RESPUESTADATOSBASICOSCLIENTE = new Gson().fromJson("{\"cliente\":{\"personaNatural\":{\"nombreLargoCliente\":\"FENEY ABELARDO MARULANDA ARIAS\",\"nombreCortoCliente\":\"FERNEY MARULANDA\",\"nombreCliente\":{\"primerNombre\":\"FERNEY\",\"segundoNombre\":\"ABELARDO\",\"primerApellido\":\"MARULANDA\",\"segundoApellido\":\"ARIAS\"}},\"tipoPersona\":\"\"},\"codigoCIIU\":\"00010\",\"codigoSegmento\":\"1\",\"gerenteComercial\":{\"nombreLargo\":\"Carlos Alberto Valderrama Palacios\",\"codigo\":\"EMP001\"},\"codigoRol\":\"02\",\"estadoVinculacion\":\"00\",\"codigoOficina\":\"BTA001\",\"informacionExpedicionIdentificacion\":{\"fechaExpedicion\":\"1996-08-21\",\"codigoCiudadExpedicion\":\"15001000\",\"codigoPaisExpedicion\":\"CO\"},\"informacionNacimientoCliente\":{\"fecha\":\"1978-07-05\",\"codigoCiudad\":\"15001000\",\"codigoPais\":\"CO\"},\"ocupacion\":\"06\",\"fechaUltimaActualizacion\":\"2019-09-08\",\"codigoGrupoPersona\":\"2\"}", RecuperarDatosBasicosClienteResponse.class);

	public static final String CARGAUTILENVIOCORREOEXCEP = "{\"idSesion\":\"3123123123213\",\"nombreCliente\":\"PepitoPerez\",\"documentoCliente\":\"123456789\",\"correo\":\"leidy.roman@ibm.com\",\"documentoPdf\":\"hkhkghjkgkfjhbjklhop\",\"cupoSolicitado\":\"5000000\",\"nombreProducto\":\"Libranza\",\"pasoFuncional\":\"paso_li_401\",\"tasaNominal\":\"0.22\",\"plazoCredito\":\"36\",\"valorCuota\":\"34453\",\"cuentaDebito\":\"D-123456789087\",\"numeroCredito\":\"21312321\",\"seguroEmpleado\":false,\"numeroIntentosEnvio\":\"1\"}";
	
	
	public static final String CARGAUTILENVIOCORREOCOLP = "{\"idSesion\":\"3123123123213\",\"nombreCliente\":\"PepitoPerez\",\"documentoCliente\":\"123456789\",\"correo\":\"leidy.roman@ibm.com\",\"documentoPdf\":\"hkhkghjkgkfjhbjklhop\",\"cupoSolicitado\":\"5000000\",\"nombreProducto\":\"Libranza\",\"pasoFuncional\":\"paso_li_401\",\"tasaNominal\":\"0.22\",\"plazoCredito\":\"36\",\"valorCuota\":\"34453\",\"cuentaDebito\":\"D-123456789087\",\"numeroCredito\":\"21312321\",\"seguroEmpleado\":false,\"numeroIntentosEnvio\":\"1\"}";
	
	
	public static final String CARGAUTILRECALCULOEXC = "{" + " 'idSesion':'3123123123213'," + " 'pasoFuncional':'paso3',"
			+ " 'tipoVenta':'Preaprobado'," + " 'tipodocumento':'FS001'," + " 'numeroDocumento':'98768001',"
			+ " 'idSesion':'sadsadsadsa'," + " 'idProducto':'14'," + " 'sucursalRadicacion':'245'" + "}";
	
	public static final String CARGAUTILENVIOCORREOCREDIAGIL = "{'idSesion':'3123123123213',"
			+ "'nombreCliente':'Pepito Perez', 'documentoCliente':'123456789' ,"
			+ "'correo':'leidy.roman@ibm.com', 'documentoPdf':'',"
			+ "'cupoSolicitado':'5000000', 'nombreProducto':'Libre inversion'," + "'pasoFuncional':'Paso5',"
			+ "'tasaNominal':'0.22'," + "'plazoCredito':'36'," + "'valorCuota':'34453'," + "'codigoProducto':'13',"
			+ "'cuentaDebito':'D-123456789087'," + "'numeroCredito':'21312321'," + "'seguroEmpleado':'false'}";
	
	public static final String CARGAUTILENVIOCORREOLIBRANZA = "{'idSesion':'3123123123213',"
			+ "'nombreCliente':'Pepito Perez', 'documentoCliente':'123456789' ,"
			+ "'correo':'leidy.roman@ibm.com', 'documentoPdf':'',"
			+ "'cupoSolicitado':'5000000', 'nombreProducto':'Libre inversion'," + "'pasoFuncional':'Paso5',"
			+ "'tasaNominal':'0.22'," + "'plazoCredito':'36'," + "'valorCuota':'34453'," + "'codigoProducto':'4',"
			+ "'cuentaDebito':'D-123456789087'," + "'puntosDTF':'1'," + "'numeroCredito':'21312321'," + "'seguroEmpleado':'false'}";
	
	
	public static final String DATOS_CON = "{" + "    'cabecera': {" + "        'estadoTransaccion': '',"
			+ "        'extensionFuncional': [" + "            {" + "                'llave': '',"
			+ "                'valor': ''" + "            }" + "        ],"
			+ "        'numeroSolicitud': 'SUC_2019_3731'," + "        'pasoFuncional': 'paso1'" + "    },"
			+ "    'oferta': {" + "        'convenio': {"
			+ "            'ciudadConvenio': 'MANIZALEZ                     ',"
			+ "            'codigoConvenio': '70001'," + "            'correoElectronico': '',"
			+ "            'diaPago': 10," + "            'nitEmpresa': '000000810000450',"
			+ "            'nombreConvenio': 'CONTACTAMOS SAS               '" + "        },"
			+ "        'informacionCliente': {" + "            'codigoCiudad': '17001000',"
			+ "            'codigoDepartamento': '17'," + "            'codigoPais': 'CO',"
			+ "            'direccionPrincipal': 'CL 28 23 C 10 AP 204 C'," + "            'idSolicitante': '6441',"
			+ "            'nombreCompletoCliente': ' Juan Esteban Callejas Cardona',"
			+ "            'numeroDocumento': '18062995521'," + "            'tipoDocumento': 'FS001'" + "        },"
			+ "        'producto': [" + "            {" + "                'categoria': [" + "                    {"
			+ "                        'codigoCategoria': 'Credito de Consumo',"
			+ "                        'subproducto': [" + "                            {"
			+ "                                'codigoImagen': 'P99',"
			+ "                                'comisionDisponibilidad': 0.0,"
			+ "                                'cupoMaximo': '10000000.00',"
			+ "                                'cupoMinimo': '1000000.00',"
			+ "                                'descripcionSubproducto': 'Preaprobado Libranza',"
			+ "                                'idSubproducto': '9630',"
			+ "                                'isProductOwner': false,"
			+ "                                'listaCondiciones': [],"
			+ "                                'nombreSubproducto': 'Preaprobado Libranza',"
			+ "                                'seguroProducto': [" + "                                    {"
			+ "                                        'factor': 0.00145,"
			+ "                                        'montoMaximoSeguro': 20000000,"
			+ "                                        'montoMinimoSeguro': 1000000"
			+ "                                    }," + "                                    {"
			+ "                                        'factor': 0.00149,"
			+ "                                        'montoMaximoSeguro': 50000000,"
			+ "                                        'montoMinimoSeguro': 20000001"
			+ "                                    }," + "                                    {"
			+ "                                        'factor': 0.00151,"
			+ "                                        'montoMaximoSeguro': 100000000,"
			+ "                                        'montoMinimoSeguro': 50000001"
			+ "                                    }," + "                                    {"
			+ "                                        'factor': 0.00155,"
			+ "                                        'montoMaximoSeguro': 200000000,"
			+ "                                        'montoMinimoSeguro': 100000001"
			+ "                                    }," + "                                    {"
			+ "                                        'factor': 0.00156,"
			+ "                                        'montoMaximoSeguro': 500000000,"
			+ "                                        'montoMinimoSeguro': 200000001"
			+ "                                    }" + "                                ],"
			+ "                                'tasas': [" + "                                    {"
			+ "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 1000000.0,"
			+ "                                        'montoSuperiorProducto': 11999999.0,"
			+ "                                        'plazoInferior': 61,"
			+ "                                        'plazoSuperior': 72,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 12000000.0,"
			+ "                                        'montoSuperiorProducto': 29999999.0,"
			+ "                                        'plazoInferior': 61,"
			+ "                                        'plazoSuperior': 72,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 150000000.0,"
			+ "                                        'montoSuperiorProducto': 299999999.0,"
			+ "                                        'plazoInferior': 85,"
			+ "                                        'plazoSuperior': 96,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 300000000.0,"
			+ "                                        'montoSuperiorProducto': 300000000.0,"
			+ "                                        'plazoInferior': 85,"
			+ "                                        'plazoSuperior': 96,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 30000000.0,"
			+ "                                        'montoSuperiorProducto': 59999999.0,"
			+ "                                        'plazoInferior': 61,"
			+ "                                        'plazoSuperior': 72,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 300000001.0,"
			+ "                                        'montoSuperiorProducto': 599999999.0,"
			+ "                                        'plazoInferior': 85,"
			+ "                                        'plazoSuperior': 96,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 60000000.0,"
			+ "                                        'montoSuperiorProducto': 149999999.0,"
			+ "                                        'plazoInferior': 61,"
			+ "                                        'plazoSuperior': 72,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 150000000.0,"
			+ "                                        'montoSuperiorProducto': 299999999.0,"
			+ "                                        'plazoInferior': 61,"
			+ "                                        'plazoSuperior': 72,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 300000000.0,"
			+ "                                        'montoSuperiorProducto': 300000000.0,"
			+ "                                        'plazoInferior': 61,"
			+ "                                        'plazoSuperior': 72,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 300000001.0,"
			+ "                                        'montoSuperiorProducto': 599999999.0,"
			+ "                                        'plazoInferior': 61,"
			+ "                                        'plazoSuperior': 72,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 1000000.0,"
			+ "                                        'montoSuperiorProducto': 11999999.0,"
			+ "                                        'plazoInferior': 73,"
			+ "                                        'plazoSuperior': 84,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 12000000.0,"
			+ "                                        'montoSuperiorProducto': 29999999.0,"
			+ "                                        'plazoInferior': 73,"
			+ "                                        'plazoSuperior': 84,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 60000000.0,"
			+ "                                        'montoSuperiorProducto': 149999999.0,"
			+ "                                        'plazoInferior': 73,"
			+ "                                        'plazoSuperior': 84,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 30000000.0,"
			+ "                                        'montoSuperiorProducto': 59999999.0,"
			+ "                                        'plazoInferior': 73,"
			+ "                                        'plazoSuperior': 84,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 150000000.0,"
			+ "                                        'montoSuperiorProducto': 299999999.0,"
			+ "                                        'plazoInferior': 73,"
			+ "                                        'plazoSuperior': 84,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 300000000.0,"
			+ "                                        'montoSuperiorProducto': 300000000.0,"
			+ "                                        'plazoInferior': 73,"
			+ "                                        'plazoSuperior': 84,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 300000001.0,"
			+ "                                        'montoSuperiorProducto': 599999999.0,"
			+ "                                        'plazoInferior': 73,"
			+ "                                        'plazoSuperior': 84,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 1000000.0,"
			+ "                                        'montoSuperiorProducto': 11999999.0,"
			+ "                                        'plazoInferior': 85,"
			+ "                                        'plazoSuperior': 96,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 12000000.0,"
			+ "                                        'montoSuperiorProducto': 29999999.0,"
			+ "                                        'plazoInferior': 85,"
			+ "                                        'plazoSuperior': 96,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 30000000.0,"
			+ "                                        'montoSuperiorProducto': 59999999.0,"
			+ "                                        'plazoInferior': 85,"
			+ "                                        'plazoSuperior': 96,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    },"
			+ "                                    {" + "                                        'dtf': 0.0,"
			+ "                                        'grupoRiesgo': 'G4',"
			+ "                                        'montoInferiorProducto': 60000000.0,"
			+ "                                        'montoSuperiorProducto': 149999999.0,"
			+ "                                        'plazoInferior': 85,"
			+ "                                        'plazoSuperior': 96,"
			+ "                                        'puntosDtf': 0.0,"
			+ "                                        'tasaEfectivaAnual': 0.3137,"
			+ "                                        'tasaMesVencida': 2.299917,"
			+ "                                        'tasaMora': 26.12,"
			+ "                                        'tasaNominalAnualMesVencido': 0.27599,"
			+ "                                        'tipoTasa': 'F'" + "                                    }"
			+ "                                ]" + "                            }" + "                        ]"
			+ "                    }" + "                ]," + "                'idProducto': '4',"
			+ "                'montoOtorgado': '10000000.00',"
			+ "                'nombreProducto': 'PREAPROBADOS DE LIBRANZA'" + "            }" + "        ]" + "    }"
			+ "}";
	
	public static final String CARGAUTILENVIOCORREOSEGURO = "{\"idSesion\":\"3123123123213\",\r\n"
			+ "\"nombreCliente\":\"Pepito Perez\", \"documentoCliente\":\"123456789\" ,\r\n"
			+ "\"correo\":\"leidy.roman@ibm.com\", \"documentoPdf\":\"hkhkghjkgkfjhbjklhop\",\r\n"
			+ "\"cupoSolicitado\":\"5000000\", \"nombreProducto\":\"Libre inversion\",\r\n"
			+ "\"pasoFuncional\":\"Paso5\",\r\n" + "\"tasaNominal\":\"0.22\",\r\n" + "\"plazoCredito\":\"36\",\r\n"
			+ "\"valorCuota\":\"34453\",\r\n" + "\"cuentaDebito\":\"D-123456789087\",\r\n"
			+ "\"numeroCredito\":\"21312321\",\r\n" + "\"seguroEmpleado\":\"true\"}";
	
	public static final String CARGAUTILCUENTAS = "{\r\n" + 
			"    \"datosCliente\":\r\n" + 
			"    {\r\n" + 
			"        \"correoElectronico\": \"dabran@bancolombia.com.co\",\r\n" + 
			"        \"tipoIdentificacion\": \"FS001\",\r\n" + 
			"        \"numeroIdentificacion\": \"190311163056\",\r\n" + 
			"        \"fechaNacimiento\": \"1987-05-10\",\r\n" + 
			"        \"nombre\": \"Emilio Torres Herrera\",\r\n" + 
			"        \"fechaEstadoVinculacion\":\"2004-01-10\",\r\n" + 
			"        \"direcciones\": [{\r\n" + 
			"        	\"llaveDireccion\":\"0074470499\",\r\n" + 
			"			\"llaveDireccionCif\":\"11114444\",\r\n" + 
			"			\"correspondencia\":\"1\",\r\n" + 
			"			\"tipoDireccion\":\"Z001\",\r\n" + 
			"			\"direccion\":\"CQ 78 54\",\r\n" + 
			"			\"codigoPais\":\"CO\",\r\n" + 
			"			\"pais\":\"\",\r\n" + 
			"			\"departamento\":\"08\",\r\n" + 
			"			\"ciudad\":\"8078002\",\r\n" + 
			"			\"celular\":\"3135891878\",\r\n" + 
			"			\"telefonoFijo\":\"3234332\",\r\n" + 
			"			\"correoElectronico\":\"correo1@correo.co\"\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"        	\"llaveDireccion\":\"0074470499\",\r\n" + 
			"			\"llaveDireccionCif\":\"111112111\",\r\n" + 
			"			\"correspondencia\":\"1\",\r\n" + 
			"			\"tipoDireccion\":\"Z002\",\r\n" + 
			"			\"direccion\":\"CQ 78 542\",\r\n" + 
			"			\"codigoPais\":\"CO\",\r\n" + 
			"			\"pais\":\"\",\r\n" + 
			"			\"departamento\":\"08\",\r\n" + 
			"			\"ciudad\":\"8078002\",\r\n" + 
			"			\"celular\":\"3135891878\",\r\n" + 
			"			\"telefonoFijo\":\"3234332\",\r\n" + 
			"			\"correoElectronico\":\"correo2@correo.co\"\r\n" + 
			"        }]\r\n" + 
			"    },\r\n" + 
			"    \"informacionSucursal\":\r\n" + 
			"    {\r\n" + 
			"        \"tipoConsulta\": \"3\",\r\n" + 
			"        \"codigoSucursal\": \"005\",\r\n" + 
			"        \"numeroIdAsesor\": \"1037644654\",\r\n" + 
			"        \"codigoAsesor\": \"1037644654\",\r\n" + 
			"        \"codigoCRMSucursal\": \"00000066\",\r\n" + 
			"        \"numeroIdGerente\": \"69747747\",\r\n" + 
			"        \"codigoCIFAsesor\": \"04381\",\r\n" + 
			"        \"codigoCIFGerente\": \"15101\",\r\n" + 
			"        \"codigoCRMAsesor\": \"EM00000357\",\r\n" + 
			"        \"codigoCRMGerente\": \"EM00001859\",\r\n" + 
			"        \"tipoDocAsesor\": \"FS001\",\r\n" + 
			"        \"tipoDocGerente\": \"FS001\",\r\n" + 
			"        \"nombreCompleto\": \"MELISSA BUSTAMANTE NARVAEZ\",\r\n" + 
			"        \"usuarioRed\": \"MEBUSTA\"\r\n" + 
			"    },\r\n" + 
			"    \"informacionDocumentos\":\r\n" + 
			"    {\r\n" + 
			"        \"aceptaTerminos\": false\r\n" + 
			"    },\r\n" + 
			"    \"informacionDispositivo\":\r\n" + 
			"    {\r\n" + 
			"        \"deviceBrowser\": \"chrome\",\r\n" + 
			"        \"deviceOS\": \"windows\",\r\n" + 
			"        \"userAgent\": \"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36\",\r\n" + 
			"        \"sistemaOperativo\": \"windows\",\r\n" + 
			"        \"versionSistemaOperativo\": \"windows-10\",\r\n" + 
			"        \"dispositivo\": \"unknown\",\r\n" + 
			"        \"ip\": \"127.0.0.1\"\r\n" + 
			"    },\r\n" + 
			"    \"informacionBiometria\":\r\n" + 
			"    {\r\n" + 
			"        \"reintentosValidacionNoexitosa\": \"0\"\r\n" + 
			"    },\r\n" + 
			"    \"informacionCredito\":\r\n" + 
			"    {\r\n" + 
			"        \"cupoAprobadoLibreInversion\": \"300000000\",\r\n" + 
			"        \"idProducto\": \"15\",\r\n" + 
			"        \"idProceso_p1\": \"391802\",\r\n" + 
			"        \"idProceso_p2\": \"391802\",\r\n" + 
			"        \"numeroSolicitud_p1\": \"SUC_2020_86779\",\r\n" + 
			"        \"numeroSolicitud_p2\": \"SUC_2020_86779\",\r\n" + 
			"        \"montoSolicitado\": \"3000000\",\r\n" + 
			"        \"plazo\": \"36\",\r\n" + 
			"        \"isBeneficiarios\": false,\r\n" + 
			"        \"isDebitoAutomatico\": true,\r\n" + 
			"        \"plazos\": [24.0, 36.0, 48.0, 60.0],\r\n" + 
			"        \"codSolicitante_p1\": \"129502\",\r\n" + 
			"        \"productoCredito\": [\r\n" + 
			"        {\r\n" + 
			"            \"idProducto\": \"382501\",\r\n" + 
			"            \"cupoElegido\": 3000000,\r\n" + 
			"            \"plazo\": 36,\r\n" + 
			"            \"cuota\": 204659.583518,\r\n" + 
			"            \"tasaMV\": 1.925,\r\n" + 
			"            \"tasaNAMV\": 0.231,\r\n" + 
			"            \"tasaEA\": 0.257,\r\n" + 
			"            \"tasaMora\": 36.12,\r\n" + 
			"            \"factorSeguro\": 0.00145,\r\n" + 
			"            \"costoTotalSeguro\": 4350,\r\n" + 
			"            \"idSeguro\": 0,\r\n" + 
			"            \"cuentaDesembolso\": \"2-40673056005\",\r\n" + 
			"            \"aceptaDebitoAutomatico\": true,\r\n" + 
			"            \"cuentaDebitoAutomatico\": \"2-40673056005\",\r\n" + 
			"            \"beneficiario\": [],\r\n" + 
			"            \"dtf\": 0,\r\n" + 
			"            \"puntosDtf\": 0,\r\n" + 
			"            \"seguros\": [\r\n" + 
			"            {\r\n" + 
			"                \"aceptaSeguro\": \"true\",\r\n" + 
			"                \"nombreSeguro\": \"Con Seguro Empleado Protegido\",\r\n" + 
			"                \"idPlan\": \"PA2\"\r\n" + 
			"            }]\r\n" + 
			"        }]\r\n" + 
			"    },\r\n" + 
			"    \"informacionTransaccion\":\r\n" + 
			"    {\r\n" + 
			"        \"pasoFuncional\": \"paso_li_300\",\r\n" + 
			"        \"idSesion\": \"64b7e789e5e0-7bb86b7bb39746e287229969d\",\r\n" + 
			"        \"fechaHoraTransaccion\": \"\",\r\n" + 
			"        \"ipCliente\": \"\",\r\n" + 
			"        \"tokenActual\": \"Toooooooooooooooooken\"\r\n" + 
			"    },\r\n" + 
			"    \"cuentasDisponibles\": [\r\n" + 
			"    {\r\n" + 
			"        \"id\": \"2-40673056005\",\r\n" + 
			"        \"descripcion\": \"40673056005\",\r\n" + 
			"        \"diasInactividad\": 30\r\n" + 
			"    }]\r\n" + 
			"}";
	
	public static final String CARGAUTIL_VAL_PREGUNTAS = "{\r\n" + 
			"    \"idSesion\": \"aaaaaaaaaabbbbbbbbbbbbbcccccccc\",\r\n" + 
			"    \"pasoFuncional\": \"paos_106\",\r\n" + 
			"    \"preguntas\": [\r\n" + 
			"    {\r\n" + 
			"        \"pregunta\": \" Fecha de Nacimiento\",\r\n" + 
			"        \"respuesta\": \"1987-05-10\",\r\n" + 
			"        \"validacion\":\"true\"\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"        \"pregunta\": \"Teléfono fijo\",\r\n" + 
			"        \"respuesta\": \"3234332\",\r\n" + 
			"        \"validacion\":\"true\"\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"        \"pregunta\": \"Número de cuenta\",\r\n" + 
			"        \"respuesta\": \" 133211001016932 234324155501693\",\r\n" + 
			"        \"validacion\":\"true\"\r\n" + 
			"    }]\r\n" + 
			"}";
	
	public static final String CARGAUTIL_VAL_PREGUNTAS_ERROR = "{\"idSesion\":\"RVBHhUbb8UDhVHi7xk-9RA\",\"pasoFuncional\":\"paso_li_106\",\"preguntas\":[{\"pregunta\":\"Número de cédula\",\"respuesta\":\"121212\",\"validacion\":\"false\"},{\"pregunta\":\"Direccion de residencia\",\"respuesta\":\"Carrera 34 # 34 - 34\",\"validacion\":\"true\"}]}";

	public static String beneficiarioMatch = "{ \"producto\": [{ \"beneficiario\": "
			+ "[{\"nombreCompleto\": \"Nicolas31<>!#$\",\"asignacion\": 1252.3},"
			+ " {\"nombreCompleto\": \"!?_-abc<>!#$\",\"asignacion\": 123}]}]}";

	public static String beneficiarioNoMatch = "{ \"producto\": [{ \"beneficiario\":"
			+ " [{\"nombreCompleto\": \"Ññabcáéíóú\",\"asignacion\": 3652.159}]}]}";
	
	public static String consultarPrecalculadoMotor = "{\r\n" + 
			"    \"idSesion\": \"0uywKOtwOLIwN-MN-QH8PA\",\r\n" + 
			"    \"numeroDocumento\": \"1111807574\",\r\n" + 
			"    \"pasoFuncional\": \"paso_li_102\",\r\n" + 
			"    \"tipoDocumento\": \"FS001\"\r\n" + 
			"}";

	public static String responseApiConsumption = "{\"meta\":{\"_messageId\":\"c4e6bd04-5149-11e7-b114-b2f933d5fe66\",\"_requestDateTime\":\"2017-01-24T05:00:00.000Z\",\"_applicationId\":\"acxff62e-6f12-42de-9012-3e7304418abd\"},\"data\":{\"response\":{\"riskGroup\":\"0001\",\"minTerm\":0,\"maxTerm\":0,\"creditDirectAProduct\":50000000,\"creditDirectTwoProducts\":50000000,\"creditDirectThreeProducts\":50000000,\"investmentDirectAProduct\":80000000,\"investmentDirectTwoProducts\":80000000,\"investmentDirectThreeProducts\":80000000,\"payrollLoanWarrantyAProduct\":40000000,\"payrollLoanWarrantyTwoProducts\":40000000,\"payrollLoanWarrantyThreeProducts\":40000000,\"creditCardDirectAProduct\":90000000,\"creditCardDirectTwoProducts\":90000000,\"creditCardDirectThreeProducts\":90000000,\"idRequest\":\"12310381274176\",\"idType\":\"FS001\",\"idNumber\":\"1084539844\",\"product\":\"ZFS1101600010\"}}}";

	public static String responseApiConsumptionNotProduct = "{\"meta\":{\"_messageId\":\"c4e6bd04-5149-11e7-b114-b2f933d5fe66\",\"_requestDateTime\":\"2017-01-24T05:00:00.000Z\",\"_applicationId\":\"acxff62e-6f12-42de-9012-3e7304418abd\"},\"data\":{\"response\":{\"riskGroup\":\"0001\",\"minTerm\":0,\"maxTerm\":0,\"creditDirectAProduct\":50000000,\"creditDirectTwoProducts\":50000000,\"creditDirectThreeProducts\":50000000,\"investmentDirectAProduct\":80000000,\"investmentDirectTwoProducts\":80000000,\"investmentDirectThreeProducts\":80000000,\"payrollLoanWarrantyAProduct\":40000000,\"payrollLoanWarrantyTwoProducts\":40000000,\"payrollLoanWarrantyThreeProducts\":40000000,\"creditCardDirectAProduct\":90000000,\"creditCardDirectTwoProducts\":90000000,\"creditCardDirectThreeProducts\":90000000,\"idRequest\":\"12310381274176\",\"idType\":\"FS001\",\"idNumber\":\"1084539844\",\"product\":\"1234\"}}}";
}
