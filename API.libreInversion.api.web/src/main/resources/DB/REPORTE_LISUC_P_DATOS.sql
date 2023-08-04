DELETE FROM VENTASDBC.LI_SUC_DATOS_REPORTE
@

DROP PROCEDURE VENTASDBC.REPORTE_LISUC_P_DATOS
@
CREATE PROCEDURE VENTASDBC.REPORTE_LISUC_P_DATOS (
)
LANGUAGE SQL
SPECIFIC VENTASDBC.REPORTE_LISUC_P_DATOS
P1 : BEGIN
  
	
	  --PASO DE DATOS AL HISTORICO
  INSERT INTO VENTASDBC.JSONLI_HIST
    SELECT * FROM VENTASDBC.JSONLI
    WHERE FECHA < (CURRENT_TIMESTAMP - 60 DAYS);
    
  DELETE FROM VENTASDBC.JSONLI 
            WHERE ID IN (SELECT ID FROM VENTASDBC.JSONLI_HIST);

	DELETE FROM VENTASDBC.LI_SUC_DATOS_REPORTE 
	WHERE FECHA_HORA_INICIO_TRANSACCION BETWEEN (CURRENT_TIMESTAMP - 7 HOURS) AND (CURRENT_TIMESTAMP - 6 HOURS);
    
	INSERT INTO VENTASDBC.LI_SUC_DATOS_REPORTE
	 SELECT DISTINCT(LI.ID_SESSION) AS ID_SESION, 
		LIFM.FECHA_M AS FECHA_HORA_INICIO_TRANSACCION, 
		LIFF.FECHA_F AS FECHA_HORA_FIN_TRANSACCION, 
		LIPASO.PASO AS PASO_FUNCIONAL, 
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
	        WHEN 'paso_li_103_M' THEN 'Consumo servicio aprobacion motor' 
	        WHEN 'paso_li_103_M_E' THEN 'Error en consumo aprobacion motor' 
	        WHEN 'paso_li_103_M_R' THEN 'Respuesta datos aprobacion motor'       
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
	    END AS PASO_FUNCIONAL_DESC,
	    DOC.TIPO_DOCUMENTO_ID AS TIPO_DOCUMENTO_ID,
	    DOC.NUMERO_DOCUMENTO AS NUMERO_DOCUMENTO,
	    PRD.NOMBRE_PROD_SELECCIONADO AS NOMBRE_PROD_SELECCIONADO, 
	    SOL.NUMERO_SOLICITUD AS NUMERO_SOLICITUD,
	    PRD.ID_PROD_OFERTA AS ID_PROD_OFERTA, 
	    PRD.CUPO_PREAPROBADO AS CUPO_PREAPROBADO,
	    COALESCE(SOLI.COD_SOLICITANTE, PRDTDC.COD_SOLICITANTE )  AS COD_SOLICITANTE,
		COALESCE(SOLI.COD_PRD_SOLICITANTE, VARTDC.COD_SUBPRODUCTO) AS COD_PRD_SOLICITANTE,
		COALESCE(MONTO.MONTO_SOLICITADO, VARTDC.MONTO_SOLICITADO) AS MONTO_SOLICITADO,
		MONTO.PLAZO AS PLAZO,
		PRDSELECT.CUOTA_MENSUAL AS CUOTA_MENSUAL,
		COALESCE(PRDSELECT.TASA_MV, PRDSELECT.TASA_MV2) AS TASA_MV,
		COALESCE(PRDSELECT.TASA_NAMV, PRDSELECT.TASA_NAMV2) AS TASA_NAMV,
		PRDSELECT.TASA_EA AS TASA_EA,
		PRDSELECT.TASA_MORA AS TASA_MORA,
		PRDSELECT.VALOR_SEGURO AS VALOR_SEGURO,
		MAIL.CORREO_CLIENTE AS CORREO_CLIENTE,
		PRDSELECT.ACEPTAR_SEGURO AS ACEPTAR_SEGURO,
		COALESCE(CTA.DEBITO_AUTOMATICO, VARTDC.DEBITO_AUTOMATICO) AS DEBITO_AUTOMATICO,
		CTA.CTA_DESEMBOLSO AS CTA_DESEMBOLSO,
		COALESCE(CTA.CTA_DEBITO, VARTDC.CTA_DEBITO) AS CTA_DEBITO,
		BENEFA.ACEPTAR_BENEFICIARIOS AS ACEPTAR_BENEFICIARIOS,
		BENEFI.BENEFICIARIO_SEGURO AS BENEFICIARIO_SEGURO,
		PAG.ACEPTAR_PAGARE AS ACEPTAR_PAGARE,
		BIO.RESPUESTA_BIOMETRIA AS RESPUESTA_BIOMETRIA,
		SENDMAIL.ENVIO_CORREO AS ENVIO_CORREO,
		COALESCE(ASE.CODIGO_ASESOR,ASEAUX.CODIGO_ASESOR) AS CODIGO_ASESOR,
		SUC.CODIGO_SUCURSAL AS CODIGO_SUCURSAL,
		COALESCE(REF.CODIGO_REFERIDO, REF.CODIGO_REFERIDOLI) AS CODIGO_REFERIDO,
		ERROR.CODIGO_ERROR AS CODIGO_ERROR,
		ERROR.DESCRIPCION_ERROR AS DESCRIPCION_ERROR,
		(TIMESTAMPDIFF(2, TIMESTAMP(LIFF.FECHA_F) - TIMESTAMP(LIFM.FECHA_M))) AS DURACION,
		ERROR.OPERACION AS OPERACION,
		ERROR.SERVICIO AS SERVICIO,
		' ' AS TIPO_EXCEPCION,
		VENTASDBC.REPORTE_LISUCFN(EQUIPO.ID_SESSION) AS PASO_A_PASO,
		EQUIPO.ID AS ID,
		VARTDC.PRODUCTO AS PRODUCTO,
		VARTDC.COD_SUBPRODUCTO AS COD_SUBPRODUCTO,
		VARTDC.NOMBRE_SUBPRODUCTO AS NOMBRE_SUBPRODUCTO,
		PRDTDC.ENTREGA_TARJETA AS ENTREGA_TARJETA,
		RES_EMISION.RESULTADO_EMISION_INSTANTANEA AS RESULTADO_EMISION_INSTANTANEA,
		VARTDC.CICLO_PAGO AS CICLO_PAGO,
		SEG.SEGMENTO AS SEGMENTO,
		SEG.SUB_SEGMENTO AS SUB_SEGMENTO,
		PRDTDC.CODIGO_CIUDAD_ENTREGA_TARJETA AS CODIGO_CIUDAD_ENTREGA_TARJETA,
		CTA.SEGURO_DESEMPLEO AS SEGURO_DESEMPLEO,
		COALESCE(MECANISMO.METODO_DE_FIRMADO, BIO.METODO_DE_FIRMADO) AS METODO_DE_FIRMADO,
		EQUIPO.TIPO_DE_EQUIPO AS TIPO_DE_EQUIPO
	FROM VENTASDBC.JSONLI LI 
	INNER JOIN (SELECT MIN(FECHA) FECHA_M , ID_SESSION FROM VENTASDBC.JSONLI GROUP BY ID_SESSION) AS LIFM ON (LI.ID_SESSION = LIFM.ID_SESSION)
	INNER JOIN (SELECT MAX(FECHA) FECHA_F , ID_SESSION FROM VENTASDBC.JSONLI GROUP BY ID_SESSION) AS LIFF ON (LI.ID_SESSION = LIFF.ID_SESSION)
	INNER JOIN (SELECT PASO, ID_SESSION FROM VENTASDBC.JSONLI WHERE ID IN (SELECT MAX(ID) AS ID FROM VENTASDBC.JSONLI GROUP BY ID_SESSION)) AS LIPASO ON (LI.ID_SESSION = LIPASO.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, ID, SYSIBM.JSON_VAL(JSON,'informacionSucursal.id','s:100') TIPO_DE_EQUIPO FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_100' ORDER BY ID DESC) AS EQUIPO ON (LI.ID_SESSION = EQUIPO.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'tipoDocumento','s:100') TIPO_DOCUMENTO_ID, SYSIBM.JSON_VAL(JSON,'numeroDocumento','s:100') NUMERO_DOCUMENTO FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_102' ORDER BY ID DESC) AS DOC ON (LI.ID_SESSION = DOC.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'codigoSegmento','s:100') SEGMENTO, SYSIBM.JSON_VAL(JSON,'codigoSubSegmento','s:100') SUB_SEGMENTO FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_102_R' ORDER BY ID DESC) AS SEG ON (LI.ID_SESSION = SEG.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'nombreProducto','s:100') NOMBRE_PROD_SELECCIONADO, SYSIBM.JSON_VAL(JSON,'codigoProducto','s:100') ID_PROD_OFERTA , SYSIBM.JSON_VAL(JSON,'cupo','s:100') CUPO_PREAPROBADO FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_103_R' ORDER BY ID DESC ) PRD ON (LI.ID_SESSION = PRD.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'codigoCIFAsesor','s:100') CODIGO_ASESOR FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_101_R' AND ID IN (SELECT MAX(ID) FROM VENTASDBC.JSONLI LI WHERE PASO = 'paso_li_101_R' AND (SYSIBM.JSON_VAL(JSON,'tipoConsulta','s:100') IN ('3','1')) GROUP BY ID_SESSION)) ASE ON (LI.ID_SESSION = ASE.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'datoConsulta','s:100') CODIGO_ASESOR FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_101' AND ID IN (SELECT MIN(ID) FROM VENTASDBC.JSONLI LI WHERE PASO = 'paso_li_101' GROUP BY ID_SESSION)) ASEAUX ON (LI.ID_SESSION = ASEAUX.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'codigoCIFSucursal','s:100') CODIGO_SUCURSAL FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_101_R' AND ID IN (SELECT MAX(ID) FROM VENTASDBC.JSONLI LI WHERE PASO = 'paso_li_101_R' AND (SYSIBM.JSON_VAL(JSON,'codigoCIFSucursal','s:100') IS NOT NULL) GROUP BY ID_SESSION)) SUC ON (LI.ID_SESSION = SUC.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'informacionSucursal.codigoReferido','s:100') CODIGO_REFERIDO, SYSIBM.JSON_VAL(JSON,'codigoReferido','s:100') CODIGO_REFERIDOLI FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_107' ) REF ON (LI.ID_SESSION = REF.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'numeroSolicitud','s:100') NUMERO_SOLICITUD FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_107_Bizagi' ) SOL ON (LI.ID_SESSION = SOL.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'codSolicitante','s:100') COD_SOLICITANTE, SYSIBM.JSON_VAL(JSON,'idProducto','s:100') COD_PRD_SOLICITANTE FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_107_R' ) SOLI ON (LI.ID_SESSION = SOLI.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'producto.cupoElegido','l') MONTO_SOLICITADO, SYSIBM.JSON_VAL(JSON,'producto.plazo','l') PLAZO FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_202' AND ID IN (SELECT MAX(ID) FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_202' GROUP BY ID_SESSION)) MONTO ON (LI.ID_SESSION = MONTO.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, 'true' ACEPTAR_SEGURO, SYSIBM.JSON_VAL(JSON,'producto.cuota','f') CUOTA_MENSUAL, SYSIBM.JSON_VAL(JSON,'producto.tasaMV','f') TASA_MV, SYSIBM.JSON_VAL(JSON,'producto.tasaMV2','f') TASA_MV2, SYSIBM.JSON_VAL(JSON,'producto.tasaNAMV','f') TASA_NAMV, SYSIBM.JSON_VAL(JSON,'producto.tasaNAMV2','f') TASA_NAMV2, SYSIBM.JSON_VAL(JSON,'producto.tasaEA','f') TASA_EA, SYSIBM.JSON_VAL(JSON,'producto.tasaMora','f') TASA_MORA, SYSIBM.JSON_VAL(JSON,'producto.costoTotalSeguro','l') VALOR_SEGURO FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_202' ) PRDSELECT ON (LI.ID_SESSION = PRDSELECT.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'direcciones.correoElectronico','s:100') CORREO_CLIENTE FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_104_R' ORDER BY ID DESC ) MAIL ON (LI.ID_SESSION = MAIL.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'producto.aceptaDebitoAutomatico','s:100') DEBITO_AUTOMATICO, SYSIBM.JSON_VAL(JSON,'producto.cuentaDesembolso','s:100') CTA_DESEMBOLSO, SYSIBM.JSON_VAL(JSON,'producto.cuentaDesembolso','s:100') CTA_DEBITO, COALESCE(SYSIBM.JSON_VAL(JSON,'producto.seguros.idPlan','s:100') || '-',' ') || SYSIBM.JSON_VAL(JSON,'producto.seguros.nombreSeguro','s:100') SEGURO_DESEMPLEO FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_202' ORDER BY ID DESC ) CTA ON (LI.ID_SESSION = CTA.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, COALESCE(SYSIBM.JSON_VAL(JSON,'tipoCreacionTarjeta','s:100'),' ') ENTREGA_TARJETA, COALESCE(SYSIBM.JSON_VAL(JSON,'codigoSolicitante','s:100'),' ') AS COD_SOLICITANTE, COALESCE(SYSIBM.JSON_VAL(JSON,'municipioEntrega','s:100'),' ') CODIGO_CIUDAD_ENTREGA_TARJETA FROM VENTASDBC.JSONLI WHERE PASO IN ( 'paso_li_202B', 'paso_li_202_B') ORDER BY ID DESC ) PRDTDC ON (LI.ID_SESSION = PRDTDC.ID_SESSION)
	LEFT JOIN (SELECT LI.ID_SESSION, COALESCE(SYSIBM.JSON_VAL(SYSTOOLS.JSON2BSON (M.VALUE),'cupoElegido','s:100'),' ') AS MONTO_SOLICITADO, COALESCE(SYSIBM.JSON_VAL(SYSTOOLS.JSON2BSON (M.VALUE),'aceptaDebitoAutomatico','s:100'),' ') AS DEBITO_AUTOMATICO, COALESCE(SYSIBM.JSON_VAL(SYSTOOLS.JSON2BSON (M.VALUE),'cuentaDebitoAutomatico','s:100'),' ') AS CTA_DEBITO, COALESCE(SYSIBM.JSON_VAL(SYSTOOLS.JSON2BSON (M.VALUE),'codigoCategoria','s:100'),' ') AS PRODUCTO, COALESCE(SYSIBM.JSON_VAL(SYSTOOLS.JSON2BSON (M.VALUE),'idProducto','s:100'),' ') AS COD_SUBPRODUCTO, COALESCE(SYSIBM.JSON_VAL(SYSTOOLS.JSON2BSON (M.VALUE),'nombreSubProducto','s:100'),' ') AS NOMBRE_SUBPRODUCTO, COALESCE(SYSIBM.JSON_VAL(SYSTOOLS.JSON2BSON (M.VALUE),'tarjeta.cicloFactura','s:100'),' ') AS CICLO_PAGO FROM VENTASDBC.JSONLI LI, TABLE(SYSTOOLS.JSON_TABLE(LI.JSON, 'producto','s:1500')) M WHERE LI.PASO IN ( 'paso_li_202B', 'paso_li_202_B')) VARTDC ON (LI.ID_SESSION = VARTDC.ID_SESSION)
	
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'informacionCredito.isBeneficiarios','s:100') ACEPTAR_BENEFICIARIOS FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_300' ORDER BY ID DESC) AS BENEFA ON (LI.ID_SESSION = BENEFA.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, LISTAGG( X.VALUE,', ') BENEFICIARIO_SEGURO FROM VENTASDBC.JSONLI LIA, TABLE(SYSTOOLS.JSON_TABLE(JSON,'producto.beneficiario','s:500')) X WHERE LIA.PASO = 'paso_li_202' GROUP BY ID_SESSION) AS BENEFI ON (LI.ID_SESSION = BENEFI.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'informacionDocumentos.aceptaTerminos','s:100') ACEPTAR_PAGARE FROM VENTASDBC.JSONLI WHERE (PASO = 'paso_li_300_C' OR PASO = 'paso_li_400' ) ORDER BY ID DESC) AS PAG ON (LI.ID_SESSION = PAG.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, 'BIOMETRIA' AS METODO_DE_FIRMADO, 'status:' || COALESCE(SYSIBM.JSON_VAL(JSON,'informacionBiometria.status','s:100'),' ') || '| statusCodeH' || COALESCE(SYSIBM.JSON_VAL(JSON,'informacionBiometria.statusCodeH','s:100'),' ') || '| resultado:' || COALESCE(SYSIBM.JSON_VAL(JSON,'informacionBiometria.resultado','s:200'),SYSIBM.JSON_VAL(JSON,'informacionBiometria.statusMensaje','s:200')) || '| NotaCotejo:' || COALESCE(SYSIBM.JSON_VAL(JSON,'informacionBiometria.notaCotejo','s:200'),' ') RESPUESTA_BIOMETRIA FROM VENTASDBC.JSONLI WHERE PASO IN  ('paso_li_300A', 'paso_li_300_A') AND ID IN (SELECT MAX(ID) FROM VENTASDBC.JSONLI LI WHERE PASO IN  ('paso_li_300A', 'paso_li_300_A') GROUP BY ID_SESSION) ORDER BY ID DESC) AS BIO ON (LI.ID_SESSION = BIO.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'codigo','s:100') ENVIO_CORREO FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_401_R' ORDER BY ID DESC) AS SENDMAIL ON (LI.ID_SESSION = SENDMAIL.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'codError','s:100') CODIGO_ERROR, SYSIBM.JSON_VAL(JSON,'descError','s:600') DESCRIPCION_ERROR, SYSIBM.JSON_VAL(JSON,'operacion','s:100') OPERACION, SYSIBM.JSON_VAL(JSON,'servicio','s:100') SERVICIO FROM VENTASDBC.JSONLI WHERE ID IN (SELECT MAX(ID) AS ID FROM VENTASDBC.JSONLI WHERE PASO LIKE '%_E' GROUP BY ID_SESSION ORDER BY ID DESC)) AS ERROR ON (LI.ID_SESSION = ERROR.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'numCredito','s:100') NUM_CREDITO FROM VENTASDBC.JSONLI WHERE PASO LIKE 'paso_li_301_R' ORDER BY ID DESC) AS PASOFIN ON (LI.ID_SESSION = PASOFIN.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'codError','s:100') RESULTADO_EMISION_INSTANTANEA FROM VENTASDBC.JSONLI WHERE PASO LIKE 'paso_li_301_R'  ORDER BY ID DESC) AS RES_EMISION ON (LI.ID_SESSION = RES_EMISION.ID_SESSION)
	LEFT JOIN (SELECT ID_SESSION, SYSIBM.JSON_VAL(JSON,'mecanismo','s:100') METODO_DE_FIRMADO FROM VENTASDBC.JSONLI WHERE PASO = 'paso_li_300_AF_R') MECANISMO ON (LI.ID_SESSION = MECANISMO.ID_SESSION)
	WHERE 
	LI.ID_SESSION IN (SELECT DISTINCT(ID_SESSION)
						  FROM VENTASDBC.JSONLI)
	AND LI.ID_SESSION NOT IN (SELECT ID_SESION FROM VENTASDBC.LI_SUC_DATOS_REPORTE);
	
  
 
END P1
@

GRANT EXECUTE ON PROCEDURE VENTASDBC.REPORTE_LISUC_P_DATOS TO USER LANDINGZB1C
@

GRANT EXECUTE ON PROCEDURE VENTASDBC.REPORTE_LISUC_P_DATOS TO USER CNXVDBCP
@

GRANT EXECUTE ON PROCEDURE VENTASDBC.REPORTE_LISUC_P_DATOS TO USER DSRENDON
@

GRANT EXECUTE ON PROCEDURE VENTASDBC.REPORTE_LISUC_P_DATOS TO USER FLONDONO
@

