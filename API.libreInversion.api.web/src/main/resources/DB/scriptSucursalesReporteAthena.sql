CREATE OR REPLACE VIEW preaprobados_suc AS 

	SELECT DISTINCT(LI.ID_SESSION) "ID_SESION", 
			LIFM.FECHA_M "FECHA_HORA_INICIO_TRANSACCION",
			LIFF.FECHA_F "FECHA_HORA_FIN_TRANSACCION",
			LIPASO.PASO "PASO_FUNCIONAL",
			CASE LIPASO.PASO
				WHEN 'paso_li_100' THEN 'Inicio experiencia Li Sucursales desde el contenedor' 
				WHEN 'paso_li_101' THEN 'Consumo Servicio Autenticacion Asesor' 
				WHEN 'paso_li_101_E' THEN 'Error en Servicio Autenticacion Asesor' 
				WHEN 'paso_li_101_R' THEN 'Autenticacion Asesor Exitosa' 
				WHEN 'paso_li_102' THEN 'Consumo servicio Datos Basicos Cliente'
				WHEN 'paso_li_102_E' THEN 'Datos Basicos Cliente Error'
				WHEN 'paso_li_102_R' THEN 'Respuesta Datos Basicos Cliente exitosa' 
				WHEN 'paso_li_103' THEN 'Consumo servicio preaprobado' 
				WHEN 'paso_li_103_E' THEN 'Error en consumo Consulta preaprobado' 
				WHEN 'paso_li_103_R' THEN 'Respuesta datos preaprobado Exitosa'     
				WHEN 'paso_li_104' THEN 'Consumo servicio Datos ubicacion Cliente' 
				WHEN 'paso_li_104_E' THEN 'Datos ubicacion Error' 
				WHEN 'paso_li_104_R' THEN 'Respuesta Datos ubicacion Cliente exitosa'   
				WHEN 'paso_li_105' THEN 'Consumo servicio Consulta cuentas' 
				WHEN 'paso_li_105_E' THEN 'Error en consumo Consulta cuentas' 
				WHEN 'paso_li_105_R' THEN 'Respuesta datos cuentas Exitosa'
				WHEN 'paso_li_106' THEN 'Validación de preguntas' 
				WHEN 'paso_li_106_E' THEN 'Error en validación de preguntas' 
				WHEN 'paso_li_106_R' THEN 'Respuesta en validación de preguntas' 
				WHEN 'paso_li_107' THEN 'Consumo servicio Gestion credito' 
				WHEN 'paso_li_107_E' THEN 'Error en consumo Gestion credito' 
				WHEN 'paso_li_107A' THEN 'Consumo servicio Gestion credito y redirect TDC' 
				WHEN 'paso_li_107_A' THEN 'Consumo servicio Gestion credito y redirect TDC' 
				WHEN 'paso_li_107A_R' THEN 'Respuesta servicio Gestion credito TDC' 
				WHEN 'paso_li_107_A_R' THEN 'Respuesta servicio Gestion credito TDC' 
				WHEN 'paso_li_107A_E' THEN 'Error servicio Gestion credito TDC' 
				WHEN 'paso_li_107_A_E' THEN 'Error servicio Gestion credito TDC' 
				WHEN 'paso_li_107_Bizagi' THEN 'Respuesta Gestion credito creacion' 
				WHEN 'paso_li_107_R' THEN 'Respuesta Gestion credito creacion caso Exitosa' 
				WHEN 'paso_li_200' THEN 'Paso a pantalla Oferta' 
				WHEN 'paso_li_201' THEN 'Calculo cuota/ Ver Tarjetas' 
				WHEN 'paso_li_201_R' THEN 'Respuesta Calculo cuota/ Ver Tarjetas Exitosa' 
				WHEN 'paso_li_201_E' THEN 'Respuesta Calculo cuota/ Ver Tarjetas Erronea'

				WHEN 'paso_li_201_A' THEN 'Pantalla Confirmacion Credito Consumo'	

				WHEN 'paso_li_202' THEN 'Consumo servicio Avanzar solicitud credito Momento 1' 
				WHEN 'paso_li_202_E' THEN 'Error en consumo Avanzar solicitud credito Momento 1' 
				WHEN 'paso_li_202_R' THEN 'Respuesta Avanzar solicitud credito Exitosa Front' 
				WHEN 'paso_li_202_Bizagi' THEN 'Respuesta Avanzar solicitud credito desde Bizagi'        
				WHEN 'paso_li_202A' THEN 'Pantalla Confirmacion Tarjeta' 
				WHEN 'paso_li_202_A' THEN 'Pantalla Confirmacion Tarjeta' 
				WHEN 'paso_li_202B' THEN 'Consumo servicio Avanzar Solicitud Credito Momento 1 TDC' 
				WHEN 'paso_li_202_B' THEN 'Consumo servicio Avanzar Solicitud Credito Momento 1 TDC' 
				WHEN 'paso_li_202B_Bizagi' THEN 'Respuesta Avanzar Solicitud Credito Momento 1 desde Bizagi TDC' 
				WHEN 'paso_li_202_B_Bizagi' THEN 'Respuesta Avanzar Solicitud Credito Momento 1 desde Bizagi TDC' 
				WHEN 'paso_li_202B_R' THEN 'Respuesta Avanzar solicitud credito Exitosa Front TDC'
				WHEN 'paso_li_202_B_R' THEN 'Respuesta Avanzar solicitud credito Exitosa Front TDC'
				WHEN 'paso_li_202B_E' THEN 'Error en consumo Avanzar solicitud credito Momento 1 TDC'        
				WHEN 'paso_li_202_B_E' THEN 'Error en consumo Avanzar solicitud credito Momento 1 TDC'        
				WHEN 'paso_li_300' THEN 'Paso a pantalla Documentos' 
				WHEN 'paso_li_300A' THEN 'Respuesta Biometria'
				WHEN 'paso_li_300_A' THEN 'Respuesta Biometria'
				WHEN 'paso_li_300_B' THEN 'Fallo Biometria, Continua con Firmado por Token'
				WHEN 'paso_li_300_AF' THEN 'Consumo servicio Autentiacion Fuerte' 
				WHEN 'paso_li_300_AF_R' THEN 'Respuesta servicio Autentiacion Fuerte' 
				WHEN 'paso_li_300_AF_E' THEN 'Error servicio Autentiacion Fuerte' 
				WHEN 'paso_li_300_GT' THEN 'Generacion token intento 1' 
				WHEN 'paso_li_300_GT_R' THEN 'Respuesta Generacion token intento 1' 
				WHEN 'paso_li_300_GT_E' THEN 'Error Generacion token intento 1'
				WHEN 'paso_li_300_GT1' THEN 'Generacion token intento 2' 
				WHEN 'paso_li_300_GT1_R' THEN 'Respuesta Generacion token intento 2' 
				WHEN 'paso_li_300_GT1_E' THEN 'Error Generacion token intento 2'
				WHEN 'paso_li_300_GT2' THEN 'Generacion token intento 3' 
				WHEN 'paso_li_300_GT2_R' THEN 'Respuesta Generacion token intento 3' 
				WHEN 'paso_li_300_GT2_E' THEN 'Error Generacion token intento 3'
				WHEN 'paso_li_300_VT' THEN 'Consumo servicio Validacion token intento 1' 
				WHEN 'paso_li_300_VT_R' THEN 'Respuesta servicio Validacion token intento 1' 
				WHEN 'paso_li_300_VT_E' THEN 'Error servicio Validacion token intento 1' 
				WHEN 'paso_li_300_VT1' THEN 'Consumo servicio Validacion token intento 2' 
				WHEN 'paso_li_300_VT1_R' THEN 'Respuesta servicio Validacion token intento 2' 
				WHEN 'paso_li_300_VT1_E' THEN 'Error servicio Validacion token intento 2' 
				WHEN 'paso_li_300_VT2' THEN 'Consumo servicio Validacion token intento 3' 
				WHEN 'paso_li_300_VT2_R' THEN 'Respuesta servicio Validacion token intento 3' 
				WHEN 'paso_li_300_VT2_E' THEN 'Error servicio Validacion token intento 3' 
				WHEN 'paso_li_301' THEN 'Consumo servicio Avanzar solicitud credito Momento 2'
				WHEN 'paso_li_301_E' THEN 'Error en consumo Avanzar solicitud credito Momento 2' 
				WHEN 'paso_li_301_Bizagi' THEN 'Respuesta Avanzar Momento 2 Exitoso desde Bizagi' 
				WHEN 'paso_li_301_R' THEN COALESCE('Desembolsado con numero de credito: ' || PASOFIN.NUM_CREDITO, 'Respuesta Avanzar solicitud credito Momento 2 Front') 
				WHEN 'paso_li_400' THEN 'Paso a pantalla Bienvenida' 
				WHEN 'paso_li_401' THEN 'Enviar Correo' 
				WHEN 'paso_li_401_E' THEN COALESCE('Credito Desembolsado con numero de credito: ' || PASOFIN.NUM_CREDITO, 'Enviar Correo No Exitoso') 
				WHEN 'paso_li_401_R' THEN COALESCE('Correo Enviado y Credito Desembolsado con numero de credito: ' || PASOFIN.NUM_CREDITO, 'Enviar Correo Exitoso')
				WHEN 'paso_li_401A' THEN COALESCE('Documentos Descargados y Credito Desembolsado con numero de credito: ' || PASOFIN.NUM_CREDITO, 'Descarga de Documentos')
				WHEN 'paso_li_401A_R' THEN COALESCE('Documentos Descargados y Credito Desembolsado con numero de credito: ' || PASOFIN.NUM_CREDITO, 'Descarga de Documentos')
				WHEN 'paso_li_401_A' THEN COALESCE('Documentos Descargados y Credito Desembolsado con numero de credito: ' || PASOFIN.NUM_CREDITO, 'Descarga de Documentos')
				WHEN 'paso_li_401_A_E' THEN COALESCE('Credito Desembolsado con numero de credito: ' || PASOFIN.NUM_CREDITO, 'Descarga de Documentos')
				WHEN 'paso_li_401_A_R' THEN COALESCE('Documentos Descargados y Credito Desembolsado con numero de credito: ' || PASOFIN.NUM_CREDITO, 'Descarga de Documentos')
				WHEN 'paso_li_402' THEN COALESCE('Finalizo Experiencia. Correo Enviado y Credito Desembolsado con numero de credito: ' || PASOFIN.NUM_CREDITO, 'Finalizo Experiencia')
				WHEN 'paso_li_200_C' THEN 'Cancelado en pantalla Oferta' 
				WHEN 'paso_li_202A_C' THEN 'Cancelado en pantalla Confirmacion Tarjeta' 
				WHEN 'paso_li_202_A_C' THEN 'Cancelado en pantalla Confirmacion Tarjeta' 
				WHEN 'paso_li_201_C' THEN 'Cancelado en pantalla Oferta' 
				WHEN 'paso_li_203_C' THEN 'Cancelado en pantalla Oferta' 
				WHEN 'paso_li_300_C' THEN 'Cancelado en pantalla Documentos'
				WHEN 'paso_li_300_A_C' THEN 'Cancelado en pantalla Documentos'
				WHEN 'paso_li_300A_C' THEN 'Cancelado en pantalla Documentos'
				WHEN 'paso_li_300_GT3_E' THEN 'Máximo intentos generacion clave'
				WHEN 'paso_li_300_VT3_E' THEN 'Máximo intentos validación clave'
				WHEN 'paso_li_300A_C_C_' THEN 'Cancelado en pantalla Documentos'
			END "PASO_FUNCIONAL_DESC",
			DOC.TIPO_DOCUMENTO_ID "TIPO_DOCUMENTO_ID",
			DOC.NUMERO_DOCUMENTO "NUMERO_DOCUMENTO",
			PRD.NOMBRE_PROD_SELECCIONADO "NOMBRE_PROD_SELECCIONADO",
			SOL.NUMERO_SOLICITUD "NUMERO_SOLICITUD",
			PRD.ID_PROD_OFERTA "ID_PROD_OFERTA",
			PRD.CUPO_PREAPROBADO "CUPO_PREAPROBADO",
			COALESCE(SOLI.COD_SOLICITANTE, PRDTDC.COD_SOLICITANTE )  "COD_SOLICITANTE",
			COALESCE(SOLI.COD_PRD_SOLICITANTE, VARTDC.COD_SUBPRODUCTO) "COD_PRD_SOLICITANTE",
			COALESCE(PRDSELECT.MONTO_SOLICITADO, VARTDC.MONTO_SOLICITADO) "MONTO_SOLICITADO",
			PRDSELECT.PLAZO "PLAZO",
			PRDSELECT.CUOTA_MENSUAL "CUOTA_MENSUAL",
			COALESCE(PRDSELECT.TASA_MV, PRDSELECT.TASA_MV2) "TASA_MV",
			COALESCE(PRDSELECT.TASA_NAMV, PRDSELECT.TASA_NAMV2) "TASA_NAMV",
			PRDSELECT.TASA_EA "TASA_EA",
			PRDSELECT.TASA_MORA "TASA_MORA",
			PRDSELECT.VALOR_SEGURO "VALOR_SEGURO",
			MAIL.CORREO_CLIENTE "CORREO_CLIENTE",
			PRDSELECT.ACEPTAR_SEGURO "ACEPTAR_SEGURO",
			COALESCE(PRDSELECT.DEBITO_AUTOMATICO, VARTDC.DEBITO_AUTOMATICO) "DEBITO_AUTOMATICO",
			PRDSELECT.CTA_DESEMBOLSO "CTA_DESEMBOLSO",
			COALESCE(PRDSELECT.CTA_DEBITO, VARTDC.CTA_DEBITO) "CTA_DEBITO",
			BENEFA.ACEPTAR_BENEFICIARIOS "ACEPTAR_BENEFICIARIOS",
			BENEFI.BENEFICIARIO_SEGURO "BENEFICIARIO_SEGURO",
			PAG.ACEPTAR_PAGARE "ACEPTAR_PAGARE",
			BIO.RESPUESTA_BIOMETRIA "RESPUESTA_BIOMETRIA",
			SENDMAIL.ENVIO_CORREO "ENVIO_CORREO",
			COALESCE(ASE.CODIGO_ASESOR,ASEAUX.CODIGO_ASESOR) "CODIGO_ASESOR",
			SUC.CODIGO_SUCURSAL "CODIGO_SUCURSAL",
			COALESCE(REF.CODIGO_REFERIDO, REF.CODIGO_REFERIDOLI) "CODIGO_REFERIDO",
			ERROR.CODIGO_ERROR "CODIGO_ERROR",
			ERROR.DESCRIPCION_ERROR "DESCRIPCION_ERROR",
			date_diff('minute', LIFM.FECHA_M, LIFF.FECHA_F ) "DURACION",
			ERROR.OPERACION "OPERACION",
			ERROR.SERVICIO "SERVICIO",
			' ' "TIPO_EXCEPCION",
			' ' "PASO_A_PASO",
			EQUIPO.ID "ID",
			VARTDC.PRODUCTO "PRODUCTO",
			VARTDC.COD_SUBPRODUCTO "COD_SUBPRODUCTO",
			VARTDC.NOMBRE_SUBPRODUCTO "NOMBRE_SUBPRODUCTO",
			PRDTDC.ENTREGA_TARJETA "ENTREGA_TARJETA",
			RES_EMISION.RESULTADO_EMISION_INSTANTANEA "RESULTADO_EMISION_INSTANTANEA",
			VARTDC.CICLO_PAGO "CICLO_PAGO",
			SEG.SEGMENTO "SEGMENTO",
			SEG.SUB_SEGMENTO "SUB_SEGMENTO",
			PRDTDC.CODIGO_CIUDAD_ENTREGA_TARJETA "CODIGO_CIUDAD_ENTREGA_TARJETA",
			CTA.SEGURO_DESEMPLEO "SEGURO_DESEMPLEO",
			COALESCE(MECANISMO.METODO_DE_FIRMADO, BIO.METODO_DE_FIRMADO) "METODO_DE_FIRMADO",
			EQUIPO.TIPO_DE_EQUIPO "TIPO_DE_EQUIPO"
		FROM parket_jsonli LI
		INNER JOIN (SELECT CAST(MIN(FECHA) AS TIMESTAMP) FECHA_M , ID_SESSION FROM parket_jsonli GROUP BY ID_SESSION) LIFM ON (LI.ID_SESSION = LIFM.ID_SESSION)
		INNER JOIN (SELECT CAST(MAX(FECHA) AS TIMESTAMP) FECHA_F , ID_SESSION FROM parket_jsonli GROUP BY ID_SESSION) AS LIFF ON (LI.ID_SESSION = LIFF.ID_SESSION)
		INNER JOIN (SELECT PASO, ID_SESSION FROM parket_jsonli WHERE ID IN (SELECT ALI.ID FROM parket_jsonli ALI, (SELECT MAX(LIA.FECHA) AS FECHA, LIA.ID_SESSION AS ID_SESSION FROM parket_jsonli LIA GROUP BY LIA.ID_SESSION) DAT1 WHERE ALI.FECHA = DAT1.FECHA AND ALI.ID_SESSION = DAT1.ID_SESSION)) AS LIPASO ON (LI.ID_SESSION = LIPASO.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, cast(json_extract(JSON , '$.numCredito') AS VARCHAR) NUM_CREDITO FROM parket_jsonli WHERE PASO = 'paso_li_301_R') PASOFIN ON (LI.ID_SESSION = PASOFIN.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, CAST(json_extract(JSON , '$.tipoDocumento') AS VARCHAR) TIPO_DOCUMENTO_ID, CAST(json_extract(JSON , '$.numeroDocumento') AS VARCHAR) NUMERO_DOCUMENTO FROM parket_jsonli WHERE PASO = 'paso_li_102' ORDER BY ID DESC) AS DOC ON (LI.ID_SESSION = DOC.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION,  CAST(json_extract(JSON , '$.nombreProducto') AS VARCHAR) AS NOMBRE_PROD_SELECCIONADO, CAST(json_extract(JSON , '$.codigoProducto') AS VARCHAR) AS ID_PROD_OFERTA , CAST(json_extract(JSON , '$.cupo') AS VARCHAR) AS CUPO_PREAPROBADO FROM parket_jsonli WHERE PASO = 'paso_li_103_R' ORDER BY ID DESC ) PRD ON (LI.ID_SESSION = PRD.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, CAST(json_extract(JSON , '$.numeroSolicitud') AS VARCHAR) AS NUMERO_SOLICITUD FROM parket_jsonli WHERE PASO = 'paso_li_107_Bizagi' ) SOL ON (LI.ID_SESSION = SOL.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, CAST(json_extract(JSON , '$.codSolicitante') AS VARCHAR) AS COD_SOLICITANTE, CAST(json_extract(JSON , '$.idProducto') AS VARCHAR) AS COD_PRD_SOLICITANTE FROM parket_jsonli WHERE PASO = 'paso_li_107_R' ) SOLI ON (LI.ID_SESSION = SOLI.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, COALESCE(CAST(json_extract(JSON , '$.tipoCreacionTarjeta') AS VARCHAR),' ') ENTREGA_TARJETA, COALESCE(CAST(json_extract(JSON , '$.codigoSolicitante') AS VARCHAR),' ') AS COD_SOLICITANTE, COALESCE(CAST(json_extract(JSON , '$.municipioEntrega') AS VARCHAR),' ') CODIGO_CIUDAD_ENTREGA_TARJETA FROM parket_jsonli WHERE PASO IN ( 'paso_li_202B', 'paso_li_202_B') ORDER BY ID DESC ) PRDTDC ON (LI.ID_SESSION = PRDTDC.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, COALESCE(CAST(json_extract(prd , '$.cupoElegido') AS integer)) AS MONTO_SOLICITADO, COALESCE(CAST(json_extract(prd , '$.aceptaDebitoAutomatico') AS VARCHAR),' ') AS DEBITO_AUTOMATICO, COALESCE(CAST(json_extract(prd , '$.cuentaDebitoAutomatico') AS VARCHAR),' ') AS CTA_DEBITO, COALESCE(CAST(json_extract(prd , '$.codigoCategoria') AS VARCHAR),' ') AS PRODUCTO, COALESCE(CAST(json_extract(prd , '$.idProducto') AS VARCHAR),' ') AS COD_SUBPRODUCTO, COALESCE(CAST(json_extract(prd , '$.nombreSubProducto') AS VARCHAR),' ') AS NOMBRE_SUBPRODUCTO, COALESCE(CAST(json_extract(prd , '$.tarjeta.cicloFactura') AS VARCHAR),' ') AS CICLO_PAGO FROM parket_jsonli CROSS JOIN UNNEST(CAST(json_extract(json , '$.producto') AS array(json))) as t(prd) WHERE PASO IN ( 'paso_li_202B', 'paso_li_202_B')) VARTDC ON (LI.ID_SESSION = VARTDC.ID_SESSION)
		LEFT JOIN (SELECT LITMP.ID_SESSION, 'true' AS ACEPTAR_SEGURO, CAST(json_extract(M.VALUE , '$.cupoElegido') AS integer) AS MONTO_SOLICITADO, CAST(json_extract(M.VALUE , '$.plazo') AS integer) AS PLAZO, CAST(json_extract(M.VALUE , '$.cuota') AS double) AS CUOTA_MENSUAL,  CAST(json_extract(M.VALUE , '$.tasaMV') AS double) AS TASA_MV, CAST(json_extract(M.VALUE , '$.tasaMV2') AS double) AS TASA_MV2,  CAST(json_extract(M.VALUE , '$.tasaNAMV') AS double) AS TASA_NAMV, CAST(json_extract(M.VALUE , '$.tasaNAMV2') AS double) AS TASA_NAMV2,  CAST(json_extract(M.VALUE , '$.tasaEA') AS double) AS TASA_EA, CAST(json_extract(M.VALUE , '$.tasaMora') AS double) AS TASA_MORA,  CAST(json_extract(M.VALUE , '$.costoTotalSeguro') AS double) AS VALOR_SEGURO, CAST(json_extract(M.VALUE , '$.aceptaDebitoAutomatico') AS VARCHAR) AS DEBITO_AUTOMATICO, CAST(json_extract(M.VALUE , '$.cuentaDesembolso') AS VARCHAR) AS CTA_DESEMBOLSO, CAST(json_extract(M.VALUE , '$.cuentaDebitoAutomatico') AS VARCHAR) AS CTA_DEBITO FROM parket_jsonli LITMP, (SELECT ID_SESSION, json_array_get(json_extract(json , '$.producto'),0)  AS VALUE FROM parket_jsonli WHERE PASO IN ( 'paso_li_202' )) M WHERE PASO = 'paso_li_202' AND M.ID_SESSION = LITMP.ID_SESSION) PRDSELECT ON (LI.ID_SESSION = PRDSELECT.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, CAST(json_extract(JSON , '$.direcciones[0].correoElectronico') AS VARCHAR) CORREO_CLIENTE FROM parket_jsonli WHERE PASO = 'paso_li_104_R') MAIL ON (LI.ID_SESSION = MAIL.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, CAST(json_extract(JSON , '$.informacionCredito.isBeneficiarios') AS VARCHAR) AS ACEPTAR_BENEFICIARIOS FROM parket_jsonli WHERE PASO = 'paso_li_300' ORDER BY ID DESC) AS BENEFA ON (LI.ID_SESSION = BENEFA.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, CAST(json_format(json_extract(json , '$.producto[0].beneficiario')) AS VARCHAR) AS BENEFICIARIO_SEGURO FROM parket_jsonli WHERE PASO IN ( 'paso_li_202')) AS BENEFI ON (LI.ID_SESSION = BENEFI.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, CAST(json_extract(JSON , '$.informacionDocumentos.aceptaTerminos') AS VARCHAR) AS  ACEPTAR_PAGARE FROM parket_jsonli WHERE (PASO = 'paso_li_300_C' OR PASO = 'paso_li_400' ) ORDER BY ID DESC) AS PAG ON (LI.ID_SESSION = PAG.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, 'BIOMETRIA' AS METODO_DE_FIRMADO, 'status:' || COALESCE(CAST(json_extract(JSON , '$.informacionBiometria.status') AS VARCHAR),' ') || '| statusCodeH' || COALESCE(CAST(json_extract(JSON , '$.informacionBiometria.statusCodeH') AS VARCHAR),' ') || '| resultado:' || COALESCE(CAST(json_extract(JSON , '$.informacionBiometria.resultado') AS VARCHAR)) || CAST(json_extract(JSON , '$.informacionBiometria.statusMensaje') AS VARCHAR) || '| NotaCotejo:' || COALESCE(CAST(json_extract(JSON , '$.informacionBiometria.notaCotejo') AS VARCHAR),' ') RESPUESTA_BIOMETRIA FROM parket_jsonli WHERE PASO IN  ('paso_li_300A', 'paso_li_300_A') AND ID IN (SELECT BLI.ID FROM parket_jsonli BLI, (SELECT MAX(BLI2.FECHA) AS FECHA, BLI2.ID_SESSION AS ID_SESSION FROM parket_jsonli BLI2 WHERE BLI2.PASO IN  ('paso_li_300A', 'paso_li_300_A') GROUP BY ID_SESSION) AS DAT1 WHERE BLI.FECHA = DAT1.FECHA AND BLI.ID_SESSION = DAT1.ID_SESSION AND BLI.PASO IN  ('paso_li_300A', 'paso_li_300_A')) ORDER BY ID DESC) AS BIO ON (LI.ID_SESSION = BIO.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, CAST(json_extract(JSON , '$.codigo') AS VARCHAR) ENVIO_CORREO FROM parket_jsonli WHERE PASO = 'paso_li_401_R' ORDER BY ID DESC) AS SENDMAIL ON (LI.ID_SESSION = SENDMAIL.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, CAST(json_extract(JSON , '$.codigoCIFAsesor') AS VARCHAR) AS CODIGO_ASESOR FROM parket_jsonli JLI WHERE JLI.PASO = 'paso_li_101_R' AND JLI.ID IN (SELECT JLI1.ID FROM parket_jsonli JLI1, (SELECT MAX(LIX.FECHA) AS FECHA, LIX.ID_SESSION AS ID_SESSION FROM parket_jsonli LIX WHERE LIX.PASO = 'paso_li_101_R' AND (CAST(json_extract(JSON , '$.tipoConsulta') AS VARCHAR) IN ('3','1')) GROUP BY ID_SESSION) AS DAT1 WHERE JLI1.FECHA = DAT1.FECHA AND JLI1.ID_SESSION = DAT1.ID_SESSION AND JLI1.PASO = 'paso_li_101_R')) ASE ON (LI.ID_SESSION = ASE.ID_SESSION)
		LEFT JOIN (SELECT JLI.ID_SESSION, CAST(json_extract(JLI.JSON , '$.datoConsulta') AS VARCHAR) AS CODIGO_ASESOR FROM parket_jsonli JLI WHERE JLI.PASO = 'paso_li_101' AND JLI.ID IN (SELECT ALI.ID FROM parket_jsonli ALI, (SELECT MIN(ALI2.FECHA) AS FECHA, ALI2.ID_SESSION AS ID_SESSION FROM parket_jsonli ALI2 WHERE ALI2.PASO = 'paso_li_101' GROUP BY ALI2.ID_SESSION) DAT1 WHERE ALI.FECHA = DAT1.FECHA AND ALI.ID_SESSION = DAT1.ID_SESSION AND ALI.PASO = 'paso_li_101')) ASEAUX ON (LI.ID_SESSION = ASEAUX.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, CAST(json_extract(JSON , '$.codigoCIFSucursal') AS VARCHAR) AS  CODIGO_SUCURSAL FROM parket_jsonli WHERE PASO = 'paso_li_101_R' AND FECHA IN (SELECT MAX(FLI.FECHA) FROM parket_jsonli FLI WHERE FLI.PASO = 'paso_li_101_R' AND CAST(json_extract(FLI.JSON , '$.codigoCIFSucursal') AS VARCHAR) IS NOT NULL GROUP BY ID_SESSION)) SUC ON (LI.ID_SESSION = SUC.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, CAST(json_extract(JSON , '$.informacionSucursal.codigoReferido') AS VARCHAR) AS CODIGO_REFERIDO, CAST(json_extract(JSON , '$.codigoReferido') AS VARCHAR) AS CODIGO_REFERIDOLI FROM parket_jsonli WHERE PASO = 'paso_li_107' ) REF ON (LI.ID_SESSION = REF.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, CAST(json_extract(JSON , '$.codError') AS VARCHAR) CODIGO_ERROR, CAST(json_extract(JSON , '$.descError') AS VARCHAR) DESCRIPCION_ERROR, CAST(json_extract(JSON , '$.operacion') AS VARCHAR) OPERACION, CAST(json_extract(JSON , '$.servicio') AS VARCHAR) SERVICIO FROM parket_jsonli WHERE FECHA IN (SELECT MAX(FECHA) AS ID FROM parket_jsonli WHERE PASO LIKE '%_E' GROUP BY ID_SESSION ORDER BY ID DESC)) AS ERROR ON (LI.ID_SESSION = ERROR.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, ID, CAST(json_extract(JSON , '$.informacionSucursal.id') AS VARCHAR) AS TIPO_DE_EQUIPO FROM parket_jsonli WHERE PASO = 'paso_li_100' ORDER BY ID DESC ) AS EQUIPO ON (LI.ID_SESSION = EQUIPO.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, CAST(json_extract(JSON , '$.codError') AS VARCHAR) RESULTADO_EMISION_INSTANTANEA FROM parket_jsonli WHERE PASO = 'paso_li_301_R') AS RES_EMISION ON (LI.ID_SESSION = RES_EMISION.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, CAST(json_extract(JSON , '$.codigoSegmento') AS VARCHAR) AS  SEGMENTO, CAST(json_extract(JSON , '$.codigoSubSegmento') AS VARCHAR) AS  SUB_SEGMENTO FROM parket_jsonli WHERE PASO = 'paso_li_102_R' ORDER BY ID DESC) AS SEG ON (LI.ID_SESSION = SEG.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, CAST(json_extract(json , '$.producto[0].seguros[0].idPlan') AS VARCHAR) || '-' || CAST(json_extract(json , '$.producto[0].seguros[0].nombreSeguro') AS VARCHAR) AS SEGURO_DESEMPLEO FROM parket_jsonli LS WHERE PASO = 'paso_li_202' ORDER BY ID DESC) CTA ON (LI.ID_SESSION = CTA.ID_SESSION)
		LEFT JOIN (SELECT ID_SESSION, CAST(json_extract(JSON , '$.mecanismo') AS VARCHAR) METODO_DE_FIRMADO FROM parket_jsonli WHERE PASO = 'paso_li_300_AF_R') MECANISMO ON (LI.ID_SESSION = MECANISMO.ID_SESSION)
		ORDER BY "FECHA_HORA_INICIO_TRANSACCION" DESC
