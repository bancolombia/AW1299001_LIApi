--procesamiento de datos a las 6, 12, 18 y 24 horas
CALL SYSPROC.ADMIN_TASK_ADD('REPORTE_LISUC_P_DATOS', CURRENT_TIMESTAMP , null, null, '0 */6 * * *', 'VENTASDBC', 'REPORTE_LISUC_P_DATOS', null, null, null)
@


--Si se quiere remover el job
--CALL SYSPROC.ADMIN_TASK_REMOVE('REPORTE_LISUC_P_DATOS', NULL)

