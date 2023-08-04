
-- Borrar función reporte:
DROP FUNCTION schvtdig.reporte_lisuc
;

-- Borrar type de reporte:
DROP TYPE schvtdig.reporte_lisuc_type
;

-- Borrar función:
DROP FUNCTION schvtdig.reporte_lisucfn
;

-- Creación de función:
CREATE OR REPLACE FUNCTION schvtdig.reporte_lisucfn(IN param_id_session character varying)
    RETURNS text
    LANGUAGE 'plpgsql'
    VOLATILE
    PARALLEL UNSAFE
    COST 100
AS $BODY$
DECLARE
	VAR_RESPUESTA text = '';
	VAR_PASO VARCHAR(100) := '';
	VAR_PASO_B VARCHAR(1) := 'N';
	VAR_FECHA TIMESTAMP;
	REC_DATA RECORD;--
 
	CUR_DATA cursor(p_id_session character varying(100)) FOR
		SELECT FECHA, PASO
		FROM schvtdig.JSONLI
		WHERE ID_SESSION = p_id_session
		ORDER BY FECHA asc;
BEGIN
	OPEN CUR_DATA(PARAM_ID_SESSION);
	LOOP
		fetch CUR_DATA into REC_DATA;
		EXIT when not found;
		IF (VAR_PASO_B = 'N') THEN
			VAR_RESPUESTA := COALESCE(VAR_RESPUESTA, '');
			VAR_FECHA := REC_DATA.FECHA;
			VAR_PASO := REC_DATA.PASO;
			VAR_PASO_B := 'Y';
		ELSE 
			--SET VAR_PASO_B = 'N';
			VAR_RESPUESTA := COALESCE(VAR_RESPUESTA, '') || VAR_PASO || ' - ' || REC_DATA.PASO || ' : Tiempo: ' || (SELECT
			CAST(EXTRACT(EPOCH FROM (REC_DATA.FECHA::timestamp - VAR_FECHA::timestamp)) AS integer)) || ' :::::> ';
			VAR_FECHA := REC_DATA.FECHA;
			VAR_PASO := REC_DATA.PASO;
		END IF; 
	END LOOP; 
	CLOSE CUR_DATA;
	RETURN VAR_RESPUESTA;
END;
$BODY$
;

-- Creación de type de reporte:
CREATE TYPE schvtdig.reporte_lisuc_type AS (
	id_sesion character varying(100),
	fecha_hora_inicio_transaccion timestamp without time zone,
	fecha_hora_fin_transaccion timestamp without time zone,
	paso_funcional character varying(30),
	paso_funcional_desc text,
	tipo_documento_id character varying(100),
	numero_documento character varying(100),
	nombre_prod_seleccionado character varying(100),
	numero_solicitud character varying(100),
	id_prod_oferta character varying(100),
	cupo_preaprobado character varying(100),
	cod_solicitante character varying,
	cod_prd_solicitante character varying,
	monto_solicitado integer,
	plazo integer,
	cuota_mensual double precision,
	tasa_mv double precision,
	tasa_namv double precision,
	tasa_ea double precision,
	tasa_mora double precision,
	valor_seguro double precision,
	correo_cliente character varying(150),
	aceptar_seguro text,
	debito_automatico character varying,
	cta_desembolso character varying(100),
	cta_debito character varying,
	aceptar_beneficiarios character varying(100),
	beneficiario_seguro character varying(10000),
	aceptar_pagare character varying(100),
	respuesta_biometria text,
	envio_correo character varying(100),
	codigo_asesor character varying(100),
	codigo_sucursal character varying(100),
	codigo_referido character varying(100),
	codigo_error character varying(100),
	descripcion_error character varying(600),
	duracion integer,
	operacion character varying(100),
	servicio character varying(100),
	tipo_excepcion text,
	paso_a_paso text,
	id bigint,
	producto character varying,
	cod_subproducto character varying,
	nombre_subproducto character varying,
	entrega_tarjeta character varying,
	resultado_emision_instantanea character varying(100),
	ciclo_pago character varying,
	segmento character varying(100),
	sub_segmento character varying(100),
	codigo_ciudad_entrega_tarjeta character varying,
	seguro_desempleo text,
	metodo_de_firmado character varying,
	tipo_de_equipo character varying(100)
	)
	;

-- Creación de función reporte:
CREATE OR REPLACE FUNCTION SCHVTDIG.REPORTE_LISUC (
         FECHA_INI TIMESTAMP WITHOUT TIME ZONE,
         FECHA_FIN TIMESTAMP WITHOUT TIME ZONE
)
RETURNS SETOF schvtdig.reporte_lisuc_type
    LANGUAGE 'plpgsql'
AS $BODY$
DECLARE 
	pcv1 schvtdig.reporte_lisuc_type;
BEGIN

	FOR pcv1 IN SELECT DISTINCT(LI.ID_SESSION) AS ID_SESION, 
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
						COALESCE(PRDSELECT.MONTO_SOLICITADO, VARTDC.MONTO_SOLICITADO) AS MONTO_SOLICITADO,
						PRDSELECT.PLAZO AS PLAZO,
						PRDSELECT.CUOTA_MENSUAL AS CUOTA_MENSUAL,
						COALESCE(PRDSELECT.TASA_MV, PRDSELECT.TASA_MV2) AS TASA_MV,
						COALESCE(PRDSELECT.TASA_NAMV, PRDSELECT.TASA_NAMV2) AS TASA_NAMV,
						PRDSELECT.TASA_EA AS TASA_EA,
						PRDSELECT.TASA_MORA AS TASA_MORA,
						PRDSELECT.VALOR_SEGURO AS VALOR_SEGURO,
						MAIL.CORREO_CLIENTE AS CORREO_CLIENTE,
						PRDSELECT.ACEPTAR_SEGURO AS ACEPTAR_SEGURO,
						COALESCE(PRDSELECT.DEBITO_AUTOMATICO, VARTDC.DEBITO_AUTOMATICO) AS DEBITO_AUTOMATICO,
						PRDSELECT.CTA_DESEMBOLSO AS CTA_DESEMBOLSO,
						COALESCE(PRDSELECT.CTA_DEBITO, VARTDC.CTA_DEBITO) AS CTA_DEBITO,
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
						(SELECT CAST( EXTRACT(EPOCH FROM (LIFF.FECHA_F::timestamp - LIFM.FECHA_M::timestamp)) AS integer)) AS DURACION,
						ERROR.OPERACION AS OPERACION,
						ERROR.SERVICIO AS SERVICIO,
						' ' AS TIPO_EXCEPCION,
						SCHVTDIG.REPORTE_LISUCFN(EQUIPO.ID_SESSION) AS PASO_A_PASO,
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
					FROM SCHVTDIG.JSONLI LI 
					INNER JOIN (SELECT MIN(FECHA) FECHA_M , ID_SESSION FROM SCHVTDIG.JSONLI GROUP BY ID_SESSION) AS LIFM ON (LI.ID_SESSION = LIFM.ID_SESSION)
					INNER JOIN (SELECT MAX(FECHA) FECHA_F , ID_SESSION FROM SCHVTDIG.JSONLI GROUP BY ID_SESSION) AS LIFF ON (LI.ID_SESSION = LIFF.ID_SESSION)
					INNER JOIN (SELECT PASO, ID_SESSION FROM SCHVTDIG.JSONLI WHERE ID IN (SELECT ALI.ID FROM SCHVTDIG.JSONLI ALI, (SELECT MAX(LIA.FECHA) AS FECHA, LIA.ID_SESSION AS ID_SESSION FROM SCHVTDIG.JSONLI LIA GROUP BY LIA.ID_SESSION) DAT1 WHERE ALI.FECHA = DAT1.FECHA AND ALI.ID_SESSION = DAT1.ID_SESSION)) AS LIPASO ON (LI.ID_SESSION = LIPASO.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, ID, CAST(JSON -> 'informacionSucursal' ->> 'id' AS varchar(100)) AS TIPO_DE_EQUIPO FROM SCHVTDIG.JSONLI WHERE PASO = 'paso_li_100' ORDER BY ID DESC ) AS EQUIPO ON (LI.ID_SESSION = EQUIPO.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, CAST(JSON ->> 'tipoDocumento' AS varchar(100)) AS TIPO_DOCUMENTO_ID, CAST(JSON ->> 'numeroDocumento' AS varchar(100)) AS  NUMERO_DOCUMENTO FROM SCHVTDIG.JSONLI WHERE PASO = 'paso_li_102' ORDER BY ID DESC) AS DOC ON (LI.ID_SESSION = DOC.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, CAST(JSON ->> 'codigoSegmento' AS varchar(100)) AS  SEGMENTO, CAST(JSON ->> 'codigoSubSegmento' AS varchar(100)) AS  SUB_SEGMENTO FROM SCHVTDIG.JSONLI WHERE PASO = 'paso_li_102_R' ORDER BY ID DESC) AS SEG ON (LI.ID_SESSION = SEG.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION,  CAST(JSON ->> 'nombreProducto' AS varchar(100)) AS NOMBRE_PROD_SELECCIONADO, CAST(JSON ->> 'codigoProducto' AS varchar(100)) AS ID_PROD_OFERTA , CAST(JSON ->> 'cupo' AS varchar(100)) AS CUPO_PREAPROBADO FROM SCHVTDIG.JSONLI WHERE PASO = 'paso_li_103_R' ORDER BY ID DESC ) PRD ON (LI.ID_SESSION = PRD.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, CAST(JSON ->> 'codigoCIFAsesor' AS varchar(100)) AS CODIGO_ASESOR FROM SCHVTDIG.JSONLI JLI WHERE JLI.PASO = 'paso_li_101_R' AND JLI.ID IN (SELECT JLI1.ID FROM SCHVTDIG.JSONLI JLI1, (SELECT MAX(LIX.FECHA) AS FECHA, LIX.ID_SESSION AS ID_SESSION FROM SCHVTDIG.JSONLI LIX WHERE LIX.PASO = 'paso_li_101_R' AND (CAST(JSON ->> 'tipoConsulta' AS varchar(100)) IN ('3','1')) GROUP BY ID_SESSION) AS DAT1 WHERE JLI1.FECHA = DAT1.FECHA AND JLI1.ID_SESSION = DAT1.ID_SESSION AND JLI1.PASO = 'paso_li_101_R')) ASE ON (LI.ID_SESSION = ASE.ID_SESSION)
					LEFT JOIN (SELECT JLI.ID_SESSION, CAST(JLI.JSON ->> 'datoConsulta' AS varchar(100)) AS CODIGO_ASESOR FROM SCHVTDIG.JSONLI JLI WHERE JLI.PASO = 'paso_li_101' AND JLI.ID IN (SELECT ALI.ID FROM SCHVTDIG.JSONLI ALI, (SELECT MIN(ALI2.FECHA) AS FECHA, ALI2.ID_SESSION AS ID_SESSION FROM SCHVTDIG.JSONLI ALI2 WHERE ALI2.PASO = 'paso_li_101' GROUP BY ALI2.ID_SESSION) DAT1 WHERE ALI.FECHA = DAT1.FECHA AND ALI.ID_SESSION = DAT1.ID_SESSION AND ALI.PASO = 'paso_li_101')) ASEAUX ON (LI.ID_SESSION = ASEAUX.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, CAST(JSON ->> 'codigoCIFSucursal' AS varchar(100)) AS  CODIGO_SUCURSAL FROM SCHVTDIG.JSONLI WHERE PASO = 'paso_li_101_R' AND FECHA IN (SELECT MAX(FLI.FECHA) FROM SCHVTDIG.JSONLI FLI WHERE FLI.PASO = 'paso_li_101_R' AND (CAST(FLI.JSON -> 'codigoCIFSucursal' AS varchar(100)) IS NOT NULL) GROUP BY ID_SESSION)) SUC ON (LI.ID_SESSION = SUC.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, CAST(JSON -> 'informacionSucursal' ->> 'codigoReferido' AS varchar(100)) AS CODIGO_REFERIDO, CAST(JSON ->> 'codigoReferido' AS varchar(100)) AS CODIGO_REFERIDOLI FROM SCHVTDIG.JSONLI WHERE PASO = 'paso_li_107' ) REF ON (LI.ID_SESSION = REF.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, CAST(JSON ->> 'numeroSolicitud' AS varchar(100)) AS NUMERO_SOLICITUD FROM SCHVTDIG.JSONLI WHERE PASO = 'paso_li_107_Bizagi' ) SOL ON (LI.ID_SESSION = SOL.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, CAST(JSON ->> 'codSolicitante' AS varchar(100)) AS COD_SOLICITANTE, CAST(JSON ->> 'idProducto' AS varchar(100)) AS COD_PRD_SOLICITANTE FROM SCHVTDIG.JSONLI WHERE PASO = 'paso_li_107_R' ) SOLI ON (LI.ID_SESSION = SOLI.ID_SESSION)
					LEFT JOIN (SELECT LITMP.ID_SESSION, 'true' AS ACEPTAR_SEGURO, CAST(M.VALUE ->> 'cupoElegido' AS integer) AS MONTO_SOLICITADO, CAST(M.VALUE ->> 'plazo' AS integer) AS PLAZO, CAST(M.VALUE ->> 'cuota' AS float) AS CUOTA_MENSUAL,  CAST(M.VALUE ->> 'tasaMV' AS float) AS TASA_MV, CAST(M.VALUE  ->> 'tasaMV2' AS float) AS TASA_MV2,  CAST(M.VALUE ->> 'tasaNAMV' AS float) AS TASA_NAMV, CAST(M.VALUE ->> 'tasaNAMV2' AS float) AS TASA_NAMV2,  CAST(M.VALUE  ->>  'tasaEA' AS float) AS TASA_EA, CAST(M.VALUE ->>  'tasaMora' AS float) AS TASA_MORA,  CAST(M.VALUE ->> 'costoTotalSeguro' AS float) AS VALOR_SEGURO, CAST(M.VALUE ->> 'aceptaDebitoAutomatico' AS varchar(100)) AS DEBITO_AUTOMATICO, CAST(M.VALUE ->> 'cuentaDesembolso' AS varchar(100)) AS CTA_DESEMBOLSO, CAST(M.VALUE ->> 'cuentaDebitoAutomatico' AS varchar(100)) AS CTA_DEBITO FROM SCHVTDIG.JSONLI LITMP, (SELECT ID_SESSION, VALUE.* AS VALUE FROM SCHVTDIG.JSONLI, jsonb_array_elements(json->'producto') AS VALUE WHERE PASO IN ( 'paso_li_202' )) M WHERE PASO = 'paso_li_202' AND M.ID_SESSION = LITMP.ID_SESSION) PRDSELECT ON (LI.ID_SESSION = PRDSELECT.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, CAST((jsonb_array_elements(jsonb_array_elements(json->'producto') -> 'seguros')) ->> 'idPlan' AS varchar(100)) || '-' || CAST((jsonb_array_elements(jsonb_array_elements(json->'producto') -> 'seguros')) ->>  'nombreSeguro' AS varchar(100)) AS SEGURO_DESEMPLEO FROM SCHVTDIG.JSONLI LS WHERE PASO = 'paso_li_202' ORDER BY ID DESC  ) CTA ON (LI.ID_SESSION = CTA.ID_SESSION)
					LEFT JOIN (SELECT LIC.ID_SESSION, (CE.DIR->>'correoElectronico')::varchar(100) CORREO_CLIENTE FROM schvtdig.JSONLI LIC INNER JOIN (SELECT id_session, jsonb_array_elements(JSON->'direcciones') AS DIR FROM schvtdig.jsonli) CE ON (CE.ID_SESSION = LIC.ID_SESSION) WHERE LIC.PASO = 'paso_li_104_R' AND (CE.DIR->>'tipoDireccion') = 'Z001' ORDER BY LIC.ID DESC) MAIL ON (LI.ID_SESSION = MAIL.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, COALESCE(CAST(JSON ->> 'tipoCreacionTarjeta' AS varchar(100)),' ') ENTREGA_TARJETA, COALESCE(CAST(JSON ->> 'codigoSolicitante' AS varchar(100)),' ') AS COD_SOLICITANTE, COALESCE(CAST(JSON ->> 'municipioEntrega' AS varchar(100)),' ') CODIGO_CIUDAD_ENTREGA_TARJETA FROM SCHVTDIG.JSONLI WHERE PASO IN ( 'paso_li_202B', 'paso_li_202_B') ORDER BY ID DESC ) PRDTDC ON (LI.ID_SESSION = PRDTDC.ID_SESSION)
					LEFT JOIN (SELECT LIT.ID_SESSION, COALESCE(CAST(M.VALUE ->> 'cupoElegido' AS integer)) AS MONTO_SOLICITADO, COALESCE(CAST(M.VALUE ->> 'aceptaDebitoAutomatico' AS varchar(100)),' ') AS DEBITO_AUTOMATICO, COALESCE(CAST(M.VALUE ->> 'cuentaDebitoAutomatico' AS varchar(100)),' ') AS CTA_DEBITO, COALESCE(CAST(M.VALUE ->> 'codigoCategoria' AS varchar(100)),' ') AS PRODUCTO, COALESCE(CAST(M.VALUE ->> 'idProducto' AS varchar(100)),' ') AS COD_SUBPRODUCTO, COALESCE(CAST(M.VALUE ->> 'nombreSubProducto' AS varchar(100)),' ') AS NOMBRE_SUBPRODUCTO, COALESCE(CAST(M.VALUE->'tarjeta' ->> 'cicloFactura' AS varchar(100)),' ') AS CICLO_PAGO FROM SCHVTDIG.JSONLI LIT, (SELECT ID_SESSION, VALUE.* AS VALUE FROM SCHVTDIG.JSONLI , jsonb_array_elements(json->'producto') AS VALUE WHERE PASO IN ( 'paso_li_202B', 'paso_li_202_B')) M WHERE LIT.PASO IN ( 'paso_li_202B', 'paso_li_202_B') AND M.ID_SESSION = LIT.ID_SESSION) VARTDC ON (LI.ID_SESSION = VARTDC.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, CAST(JSON -> 'informacionCredito' ->> 'isBeneficiarios' AS varchar(100)) AS ACEPTAR_BENEFICIARIOS FROM SCHVTDIG.JSONLI WHERE PASO = 'paso_li_300' ORDER BY ID DESC) AS BENEFA ON (LI.ID_SESSION = BENEFA.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, CAST(VALUE ->> 'beneficiario' AS varchar(10000)) AS BENEFICIARIO_SEGURO FROM SCHVTDIG.JSONLI , jsonb_array_elements(json->'producto') AS VALUE WHERE PASO IN ( 'paso_li_202')) AS BENEFI ON (LI.ID_SESSION = BENEFI.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, CAST(JSON -> 'informacionDocumentos'  ->>  'aceptaTerminos' AS varchar(100)) AS  ACEPTAR_PAGARE FROM SCHVTDIG.JSONLI WHERE (PASO = 'paso_li_300_C' OR PASO = 'paso_li_400' ) ORDER BY ID DESC) AS PAG ON (LI.ID_SESSION = PAG.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, 'BIOMETRIA' AS METODO_DE_FIRMADO, 'status:' || COALESCE(CAST(JSON -> 'informacionBiometria' ->> 'status' AS varchar(100)),' ') || '| statusCodeH' || COALESCE(CAST(JSON -> 'informacionBiometria' ->> 'statusCodeH' AS varchar(100)),' ') || '| resultado:' || COALESCE(CAST(JSON->'informacionBiometria' ->> 'resultado' AS varchar(100))) || CAST(JSON->'informacionBiometria' ->> 'statusMensaje' AS varchar(100)) || '| NotaCotejo:' || COALESCE(CAST(JSON->'informacionBiometria' ->> 'notaCotejo' AS varchar(100)),' ') RESPUESTA_BIOMETRIA FROM SCHVTDIG.JSONLI WHERE PASO IN  ('paso_li_300A', 'paso_li_300_A') AND ID IN (SELECT BLI.ID FROM SCHVTDIG.JSONLI BLI, (SELECT MAX(BLI2.FECHA) AS FECHA, BLI2.ID_SESSION AS ID_SESSION FROM SCHVTDIG.JSONLI BLI2 WHERE BLI2.PASO IN  ('paso_li_300A', 'paso_li_300_A') GROUP BY ID_SESSION) AS DAT1 WHERE BLI.FECHA = DAT1.FECHA AND BLI.ID_SESSION = DAT1.ID_SESSION AND BLI.PASO IN  ('paso_li_300A', 'paso_li_300_A')) ORDER BY ID DESC) AS BIO ON (LI.ID_SESSION = BIO.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, CAST(JSON ->> 'codigo' AS varchar(100)) ENVIO_CORREO FROM SCHVTDIG.JSONLI WHERE PASO = 'paso_li_401_R' ORDER BY ID DESC) AS SENDMAIL ON (LI.ID_SESSION = SENDMAIL.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, CAST(JSON ->> 'codError' AS varchar(100)) CODIGO_ERROR, CAST(JSON ->> 'descError' AS varchar(600)) DESCRIPCION_ERROR, CAST(JSON ->> 'operacion' AS varchar(100)) OPERACION, CAST(JSON ->> 'servicio' AS varchar(100)) SERVICIO FROM SCHVTDIG.JSONLI WHERE FECHA IN (SELECT MAX(FECHA) AS ID FROM SCHVTDIG.JSONLI WHERE PASO LIKE '%_E' GROUP BY ID_SESSION ORDER BY ID DESC)) AS ERROR ON (LI.ID_SESSION = ERROR.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, CAST(JSON ->> 'numCredito' AS varchar(100)) NUM_CREDITO FROM SCHVTDIG.JSONLI WHERE PASO = 'paso_li_301_R') AS PASOFIN ON (LI.ID_SESSION = PASOFIN.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, CAST(JSON ->> 'codError' AS varchar(100)) RESULTADO_EMISION_INSTANTANEA FROM SCHVTDIG.JSONLI WHERE PASO = 'paso_li_301_R') AS RES_EMISION ON (LI.ID_SESSION = RES_EMISION.ID_SESSION)
					LEFT JOIN (SELECT ID_SESSION, CAST(JSON ->> 'mecanismo' AS varchar(100)) METODO_DE_FIRMADO FROM SCHVTDIG.JSONLI WHERE PASO = 'paso_li_300_AF_R') MECANISMO ON (LI.ID_SESSION = MECANISMO.ID_SESSION)
					WHERE LI.ID_SESSION IN (SELECT DISTINCT(ID_SESSION)
										  FROM SCHVTDIG.JSONLI)
					AND LIFM.FECHA_M BETWEEN FECHA_INI AND FECHA_FIN
					ORDER BY LIFM.FECHA_M DESC
	LOOP

      RETURN NEXT pcv1;

    END LOOP;
END;
$BODY$
;
