select
nro_habitacion,
tipo_habitacion,
estado,
minuto_cancelar,
case
when estado = 'LIBRE'     and puerta_limpieza = true  and puerta_cliente = true  then ''
when estado = 'LIBRE'     and puerta_limpieza = false and puerta_cliente = true  then '-LIMPIANDO'
when estado = 'LIBRE'     and puerta_limpieza = true  and puerta_cliente = false then '-CLI-INGRESO'
when estado = 'LIBRE'     and puerta_limpieza = false and puerta_cliente = false then '-MANTE'
when estado = 'OCUPADO'   and puerta_limpieza = true  and puerta_cliente = true  and (extract(epoch from (current_timestamp-fec_ocupado_inicio))>(minuto_cancelar * 60)) then ''
when estado = 'OCUPADO'   and puerta_limpieza = true  and puerta_cliente = true  and (extract(epoch from (current_timestamp-fec_ocupado_inicio))<(minuto_cancelar * 60)) then '-(CANCELAR)'
when estado = 'OCUPADO'   and puerta_limpieza = false and puerta_cliente = true  then '-LIMP-ABIERTO'
when estado = 'OCUPADO'   and puerta_limpieza = true  and puerta_cliente = false then '-CLI-ABIERTO'
when estado = 'OCUPADO'   and puerta_limpieza = false and puerta_cliente = false then '-MANTE'
when estado = 'SUCIO'     and puerta_limpieza = true  and puerta_cliente = true  then '-SUCIO'
when estado = 'SUCIO'     and puerta_limpieza = false and puerta_cliente = true  then '-LIMPIANDO'
when estado = 'SUCIO'     and puerta_limpieza = true  and puerta_cliente = false then '-CLI-ABIERTO'
when estado = 'SUCIO'     and puerta_limpieza = false and puerta_cliente = false then '-MANTE'
when estado = 'LIMPIANDO' and puerta_limpieza = true  and puerta_cliente = true  then ''
when estado = 'LIMPIANDO' and puerta_limpieza = false and puerta_cliente = true  then '-LIMPIANDO'
when estado = 'LIMPIANDO' and puerta_limpieza = true  and puerta_cliente = false then '-CLI-INGRESO'
when estado = 'LIMPIANDO' and puerta_limpieza = false and puerta_cliente = false then '-MANTE'
else '.'
end as desc_estado,
case
when estado = 'OCUPADO'   and (current_timestamp>(date(fec_ocupado_inicio)+2)) then to_char((current_timestamp-fec_ocupado_inicio), 'dd HH24:MI:ss')
when estado = 'OCUPADO'   then to_char((current_timestamp-fec_ocupado_inicio), 'HH24:MI:ss')
when estado = 'SUCIO'     then to_char((current_timestamp-fec_ocupado_fin), 'HH24:MI:ss')
when estado = 'LIMPIANDO' then to_char((current_timestamp-fec_ocupado_fin), 'HH24:MI:ss')
else '.'
end as tiempo,
case
when estado = 'LIBRE' then to_char((current_timestamp-fec_ocupado_inicio), 'yyyy-MM-dd HH24:MI:ss')
else '.'
end as fec_ingreso,
es_por_dormir as habilitar_dormir,
es_por_hora as habilitar_hora,
case
when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true --HORA mas de 24hs
	and (current_timestamp>(date(fec_ocupado_inicio)+2))
		then to_char((monto_por_hora_minimo + monto_consumision-(monto_descuento+monto_adelanto) +((cast((((extract(epoch from(current_timestamp - (fec_ocupado_inicio)))))/ 3600) as integer))* monto_por_hora_adicional)), '999G999G999')
when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true
	and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))<(minuto_minimo * 60))  
		then to_char((monto_por_hora_minimo + monto_consumision-(monto_descuento+monto_adelanto)), '999G999G999')
when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true
	and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))>(minuto_minimo * 60))
	and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))<(((minuto_minimo)* 60)+(minuto_adicional * 60)))  
		then to_char((monto_por_hora_minimo + monto_por_hora_adicional + monto_consumision-(monto_descuento+monto_adelanto)), '999G999G999')
when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true
	and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))>((minuto_minimo * 60)+(minuto_adicional * 60)))  
		then to_char((monto_por_hora_minimo + monto_por_hora_adicional + monto_consumision-(monto_descuento+monto_adelanto) + (cast(to_char((current_timestamp)-(fec_ocupado_inicio + (minuto_minimo || ' minutes')::interval), 'HH24') as integer)* monto_por_hora_adicional)), '999G999G999')
else '.'
end as tarifa_gral_hora,
case
when estado = 'OCUPADO'  and es_por_dormir = true and es_por_hora = false --HORA mas de 24hs
	and (current_timestamp>(date(fec_ocupado_inicio)+2))
		then to_char((monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +
		((cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+1)+ hs_dormir_salida_final)))))/ 3600) as integer))* monto_por_dormir_adicional)), '999G999G999')
when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = false
	and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio))
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))
	and (current_timestamp>(date(fec_ocupado_inicio) + time '00:00:01'))
	and (current_timestamp<((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) 
		then to_char(monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto), '999G999G999')
when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = false
	and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))
	and (current_timestamp>(fec_ocupado_inicio))
	and (current_timestamp<((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) 
		then to_char(monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto), '999G999G999')
when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = false
	and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio))
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))
	and (current_timestamp>((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) 
		then to_char((monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+ 1)+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional)) , '999G999G999')
when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = false
	and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))
	and (current_timestamp>((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) 
		then to_char((monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio))+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional)), '999G999G999')
when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = true
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))
	and (current_timestamp>(date(fec_ocupado_inicio) + time '00:00:01'))
	and (current_timestamp<((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) 
		then to_char(monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto), '999G999G999')
when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = true
	and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))
	and (current_timestamp>(fec_ocupado_inicio))
	and (current_timestamp<((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) 
		then to_char(monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto), '999G999G999')
when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = true
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))
	and (current_timestamp>((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) 
		then to_char((monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+ 1)+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional)) , '999G999G999')
when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = true
	and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))
	and (current_timestamp>((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) 
		then to_char((monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio))+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional)), '999G999G999')
else '.'
end as tarifa_gral_dormir,
case
when (current_timestamp>(date(current_timestamp) + hs_dormir_ingreso_inicio))
	and (current_timestamp<(date(current_timestamp)+ time '23:59:59'))
	and (current_timestamp>(date(current_timestamp) + time '00:00:01'))
	and (current_timestamp<((date(current_timestamp)+ 1) + hs_dormir_salida_final)) 
		then true
when  (current_timestamp>(date(current_timestamp) + time '00:00:01'))
	and (current_timestamp<(date(current_timestamp) + hs_dormir_salida_final))
	and (current_timestamp<((date(current_timestamp)) + hs_dormir_salida_final)) 
		then true
when  (current_timestamp>(date(current_timestamp) + hs_dormir_ingreso_inicio))
	and (current_timestamp<(date(current_timestamp)+ time '23:59:59'))
	and (current_timestamp>((date(current_timestamp)+ 1) + hs_dormir_salida_final)) 
		then true
when  (current_timestamp>(date(current_timestamp) + time '00:00:01'))
	and (current_timestamp<(date(current_timestamp) + hs_dormir_salida_final))
	and (current_timestamp>((date(current_timestamp)) + hs_dormir_salida_final)) 
		then true
else false
end as permitir_dormir,
case
when estado = 'LIBRE'     and puerta_limpieza = true  and puerta_cliente = false then true
when estado = 'SUCIO'     and puerta_limpieza = false then true
when estado = 'LIMPIANDO' and puerta_limpieza = true  then true
else false
end as cambiar_estado,
case
when estado = 'LIBRE'     and puerta_limpieza = true and puerta_cliente = false then 'OCUPADO'
when estado = 'SUCIO'     and puerta_limpieza = false then 'LIMPIANDO'
when estado = 'LIMPIANDO' and puerta_limpieza = true then 'LIBRE'
else 'sin'
end as est_nuevo,
idhabitacion_recepcion_actual as ult_recepcion,
('NO') as ruta_sonido,
(extract(epoch from (current_timestamp - fec_ocupado_inicio))) as ocupado_inicio_seg,
case
when puerta_limpieza = true then 1
else 0
end as puerta_lim,
case
when puerta_cliente = true then 1
else 0
end as puerta_ocu,
to_char((fec_ocupado_inicio), 'yyyy-MM-dd') as fecha_ingreso,
to_char((fec_ocupado_inicio), 'HH24:MI:ss') as hora_ingreso,
minuto_minimo,
case
when estado = 'OCUPADO' and es_por_hora=true and es_por_dormir=false --HORA mas de 24hs
	and (current_timestamp>(date(fec_ocupado_inicio)+2))
		then (cast((((extract(epoch from(current_timestamp - fec_ocupado_inicio))))/ 3600) as integer))   
when estado = 'OCUPADO' and es_por_hora=true and es_por_dormir=true
	and (current_timestamp>(date(fec_ocupado_inicio)+2))
		then (cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+1)+ hs_dormir_salida_final)))))/ 3600) as integer))     
when estado = 'OCUPADO'  and es_por_hora=true and es_por_dormir=false
	and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))>(minuto_minimo * 60))
	and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))<(((minuto_minimo)* 60)+(minuto_adicional * 60)))  
		then 1 
when estado = 'OCUPADO'  and es_por_hora=true and es_por_dormir=false
	and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))>((minuto_minimo * 60)+(minuto_adicional * 60)))  
		then (cast(to_char((current_timestamp)-(fec_ocupado_inicio + (minuto_minimo || ' minutes')::interval), 'HH24') as integer)+1)
when estado = 'OCUPADO' and es_por_hora=false and es_por_dormir=true
	and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio))
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))
	and (current_timestamp>((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) 
		then ((cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+1)+ hs_dormir_salida_final)))))/ 3600) as integer)))
when estado = 'OCUPADO' and es_por_hora=false and es_por_dormir=true
	and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))
	and (current_timestamp>((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) 
		then ((cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio))+ hs_dormir_salida_final)))))/ 3600) as integer)))
when estado = 'OCUPADO' and es_por_hora=true and es_por_dormir=true
	and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio))
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))
	and (current_timestamp>((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) 
		then ((cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+1)+ hs_dormir_salida_final)))))/ 3600) as integer)))
when estado = 'OCUPADO' and es_por_hora=true and es_por_dormir=true
	and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))
	and (current_timestamp>((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) 
		then ((cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio))+ hs_dormir_salida_final)))))/ 3600) as integer)))
else 0
end as cant_add_tarifa_hora,
idhabitacion_dato,
idhabitacion_recepcion_actual,
monto_adelanto, 
case 
when estado = 'OCUPADO' and puerta_limpieza=true and puerta_cliente=true 
	and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))<(minuto_cancelar*60)) then true
else false 
end as cancelar_habitacion,
case 
when ((minuto_cancelar*60)-(extract(epoch from(current_timestamp - fec_ocupado_inicio))))>0 
		then to_char(((((minuto_cancelar*60)-(extract(epoch from(current_timestamp - fec_ocupado_inicio))))*100)/(minuto_cancelar * 60)),'99D99%') 
else '0%' 
end as por_cancelar,
TRIM(to_char(monto_por_hora_minimo,'999G999G999')) as  monto_por_hora_minimo,
case
when estado = 'OCUPADO'
	and ((extract(epoch from((date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio) - fec_ocupado_inicio)))<(minuto_minimo * 60))  
		then (0)
when estado = 'OCUPADO'
	and ((extract(epoch from((date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio) - fec_ocupado_inicio)))>(minuto_minimo * 60))
	and ((extract(epoch from((date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio) - fec_ocupado_inicio)))<(((minuto_minimo)* 60)+(minuto_adicional * 60)))  
		then (1)
when estado = 'OCUPADO'
	and ((extract(epoch from((date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio) - fec_ocupado_inicio)))>((minuto_minimo * 60)+(minuto_adicional * 60)))  
		then (1 + (cast(to_char(((date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio))-(fec_ocupado_inicio + (minuto_minimo || ' minutes')::interval), 'HH24') as integer)* 1))
else 0
end as cant_hasta_dormir,
case 
when estado = 'OCUPADO' 
	and ((extract(epoch from((date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio) - fec_ocupado_inicio)))>(0)) 
		then to_char(((date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio) - fec_ocupado_inicio),'HH24:MI:ss') 
else '00:00' 
end as hs_hasta_dormir, 
case  
when (date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio)>fec_ocupado_inicio 
	and  (date(fec_ocupado_inicio) + hs_dormir_salida_final)<fec_ocupado_inicio 
		then false else true 
end as es_hora_dormir 
from
habitacion_recepcion_temp 
where activo=true 
order by orden asc;