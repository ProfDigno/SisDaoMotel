--select ('<html><p><font size="6">'||nro_habitacion||'</font>-'||tipo_habitacion||'</p><p>'||idhabitacion_dato||':'||estado||'</p>'||'<p><font size="4">'||('23:55')||'</font></p>'||'<p>'||('50.000')||'</p></html>') as nom_boton 
--from habitacion_recepcion_temp order by idhabitacion_dato asc
--select descrip_estado,tiempo_estado,tarifa_gral from habitacion_recepcion_temp order by idhabitacion_dato asc

ALTER TABLE habitacion_recepcion_temp ADD COLUMN descrip_estado TEXT DEFAULT 'NO';
ALTER TABLE habitacion_recepcion_temp ADD COLUMN tiempo_estado TEXT DEFAULT '0';
ALTER TABLE habitacion_recepcion_temp ADD COLUMN monto_gral NUMERIC(14,0) DEFAULT 0;
ALTER TABLE habitacion_recepcion_temp ADD COLUMN ruta_icono TEXT DEFAULT '0';
ALTER TABLE habitacion_recepcion_temp ADD COLUMN cant_hora_adicional integer DEFAULT 0;


update habitacion_recepcion_temp set descrip_estado=
case
when estado = 'LIBRE'     and puerta_limpieza = true  and puerta_cliente = true  then estado||''
when estado = 'LIBRE'     and puerta_limpieza = false and puerta_cliente = true  then estado||'-LIMPIANDO'
when estado = 'LIBRE'     and puerta_limpieza = true  and puerta_cliente = false then estado||'-CLI-INGRESO'
when estado = 'LIBRE'     and puerta_limpieza = false and puerta_cliente = false then estado||'-MANTE'
when estado = 'OCUPADO'   and puerta_limpieza = true  and puerta_cliente = true  and (extract(epoch from (current_timestamp-fec_ocupado_inicio))>(minuto_cancelar * 60)) then estado||''
when estado = 'OCUPADO'   and puerta_limpieza = true  and puerta_cliente = true  and (extract(epoch from (current_timestamp-fec_ocupado_inicio))<(minuto_cancelar * 60)) then estado||'-(CANCELAR)'
when estado = 'OCUPADO'   and puerta_limpieza = false and puerta_cliente = true  then estado||'-LIMP-ABIERTO'
when estado = 'OCUPADO'   and puerta_limpieza = true  and puerta_cliente = false then estado||'-CLI-ABIERTO'
when estado = 'OCUPADO'   and puerta_limpieza = false and puerta_cliente = false then estado||'-MANTE'
when estado = 'SUCIO'     and puerta_limpieza = true  and puerta_cliente = true  then estado||'-SUCIO'
when estado = 'SUCIO'     and puerta_limpieza = false and puerta_cliente = true  then estado||'-LIMPIANDO'
when estado = 'SUCIO'     and puerta_limpieza = true  and puerta_cliente = false then estado||'-CLI-ABIERTO'
when estado = 'SUCIO'     and puerta_limpieza = false and puerta_cliente = false then estado||'-MANTE'
when estado = 'LIMPIANDO' and puerta_limpieza = true  and puerta_cliente = true  then estado||''
when estado = 'LIMPIANDO' and puerta_limpieza = false and puerta_cliente = true  then estado||'-LIMPIANDO'
when estado = 'LIMPIANDO' and puerta_limpieza = true  and puerta_cliente = false then estado||'-CLI-INGRESO'
when estado = 'LIMPIANDO' and puerta_limpieza = false and puerta_cliente = false then estado||'-MANTE'
else '.'
end;



update habitacion_recepcion_temp set tiempo_estado=case
when estado = 'OCUPADO'   and (current_timestamp>(date(fec_ocupado_inicio)+2)) then to_char((current_timestamp-fec_ocupado_inicio), 'dd HH24:MI:ss')
when estado = 'OCUPADO'   then to_char((current_timestamp-fec_ocupado_inicio), 'HH24:MI:ss')
when estado = 'SUCIO'     then to_char((current_timestamp-fec_ocupado_fin), 'HH24:MI:ss')
when estado = 'LIMPIANDO' then to_char((current_timestamp-fec_ocupado_fin), 'HH24:MI:ss')
else '.'
end;



update  habitacion_recepcion_temp set tarifa_gral=
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
when estado = 'OCUPADO'  and es_por_dormir = true and es_por_hora = false --HORA mas de 24hs
	and (current_timestamp>(date(fec_ocupado_inicio)+2))
		then to_char((monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +             ((cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+1)+ hs_dormir_salida_final)))))/ 3600) as integer))* monto_por_dormir_adicional)), '999G999G999')
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
end;




update  habitacion_recepcion_temp set tarifa_gral=
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
when estado = 'OCUPADO'  and es_por_dormir = true and es_por_hora = false --HORA mas de 24hs
	and (current_timestamp>(date(fec_ocupado_inicio)+2))
		then to_char((monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +((cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+1)+ hs_dormir_salida_final)))))/ 3600) as integer))* monto_por_dormir_adicional)), '999G999G999')
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
end,
descrip_estado=
case
when estado = 'LIBRE'     and puerta_limpieza = true  and puerta_cliente = true  then estado||''
when estado = 'LIBRE'     and puerta_limpieza = false and puerta_cliente = true  then estado||'-LIMPIANDO'
when estado = 'LIBRE'     and puerta_limpieza = true  and puerta_cliente = false then estado||'-CLI-INGRESO'
when estado = 'LIBRE'     and puerta_limpieza = false and puerta_cliente = false then estado||'-MANTE'
when estado = 'OCUPADO'   and puerta_limpieza = true  and puerta_cliente = true  and (extract(epoch from (current_timestamp-fec_ocupado_inicio))>(minuto_cancelar * 60)) then estado||''
when estado = 'OCUPADO'   and puerta_limpieza = true  and puerta_cliente = true  and (extract(epoch from (current_timestamp-fec_ocupado_inicio))<(minuto_cancelar * 60)) then estado||'-(CANCELAR)'
when estado = 'OCUPADO'   and puerta_limpieza = false and puerta_cliente = true  then estado||'-LIMP-ABIERTO'
when estado = 'OCUPADO'   and puerta_limpieza = true  and puerta_cliente = false then estado||'-CLI-ABIERTO'
when estado = 'OCUPADO'   and puerta_limpieza = false and puerta_cliente = false then estado||'-MANTE'
when estado = 'SUCIO'     and puerta_limpieza = true  and puerta_cliente = true  then estado||'-SUCIO'
when estado = 'SUCIO'     and puerta_limpieza = false and puerta_cliente = true  then estado||'-LIMPIANDO'
when estado = 'SUCIO'     and puerta_limpieza = true  and puerta_cliente = false then estado||'-CLI-ABIERTO'
when estado = 'SUCIO'     and puerta_limpieza = false and puerta_cliente = false then estado||'-MANTE'
when estado = 'LIMPIANDO' and puerta_limpieza = true  and puerta_cliente = true  then estado||''
when estado = 'LIMPIANDO' and puerta_limpieza = false and puerta_cliente = true  then estado||'-LIMPIANDO'
when estado = 'LIMPIANDO' and puerta_limpieza = true  and puerta_cliente = false then estado||'-CLI-INGRESO'
when estado = 'LIMPIANDO' and puerta_limpieza = false and puerta_cliente = false then estado||'-MANTE'
else '.'
end,
tiempo_estado=case
when estado = 'OCUPADO'   and (current_timestamp>(date(fec_ocupado_inicio)+2)) then to_char((current_timestamp-fec_ocupado_inicio), 'dd HH24:MI:ss')
when estado = 'OCUPADO'   then to_char((current_timestamp-fec_ocupado_inicio), 'HH24:MI:ss')
when estado = 'SUCIO'     then to_char((current_timestamp-fec_ocupado_fin), 'HH24:MI:ss')
when estado = 'LIMPIANDO' then to_char((current_timestamp-fec_ocupado_fin), 'HH24:MI:ss')
else '.'
end;


update habitacion_recepcion_temp set ruta_icono=
case when estado='LIBRE'       and puerta_limpieza = true and puerta_cliente = true then '/graficos/libre.png'
     when estado='LIBRE'       and puerta_limpieza = false and puerta_cliente = false then '/graficos/48_mante.png'
     when estado='SUCIO'       then '/graficos/limpieza.png'
     when estado='LIMPIANDO'   then '/graficos/escoba.png'
     when estado='OCUPADO'     and es_por_dormir = false and es_por_hora = true  and puerta_limpieza = true and puerta_cliente = true then '/iconos/motel/48_reloj.png'
     when estado='OCUPADO'     and es_por_dormir = true  and es_por_hora = false and puerta_limpieza = true and puerta_cliente = true then '/iconos/motel/48_dormir.png'
     when estado='OCUPADO'     and puerta_limpieza = true and puerta_cliente = false then '/graficos/48_puerta.png'
else 'no' end;




--select idhabitacion_dato,estado,case when estado='LIBRE'   and puerta_limpieza = false and puerta_cliente = false then '#DFD3C3' else '#F0F0F0' end as color,puerta_limpieza,puerta_cliente  from habitacion_recepcion_temp order by idhabitacion_dato asc

select idhabitacion_dato,estado,
case
when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true
	and (current_timestamp>(date(fec_ocupado_inicio)+2))
		then (monto_por_hora_minimo + monto_consumision-(monto_descuento+monto_adelanto) +((cast((((extract(epoch from(current_timestamp - (fec_ocupado_inicio)))))/ 3600) as integer))* monto_por_hora_adicional))
when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true
	and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))<(minuto_minimo * 60))  
		then (monto_por_hora_minimo + monto_consumision-(monto_descuento+monto_adelanto))
when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true
	and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))>(minuto_minimo * 60))
	and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))<(((minuto_minimo)* 60)+(minuto_adicional * 60)))  
		then (monto_por_hora_minimo + monto_por_hora_adicional + monto_consumision-(monto_descuento+monto_adelanto))
when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true
	and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))>((minuto_minimo * 60)+(minuto_adicional * 60)))  
		then (monto_por_hora_minimo + monto_por_hora_adicional + monto_consumision-(monto_descuento+monto_adelanto) + (cast(to_char((current_timestamp)-(fec_ocupado_inicio + (minuto_minimo || ' minutes')::interval), 'HH24') as integer)* monto_por_hora_adicional))
when estado = 'OCUPADO'  and es_por_dormir = true and es_por_hora = false --HORA mas de 24hs
	and (current_timestamp>(date(fec_ocupado_inicio)+2))
		then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +((cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+1)+ hs_dormir_salida_final)))))/ 3600) as integer))* monto_por_dormir_adicional))
when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = false
	and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio))
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))
	and (current_timestamp>(date(fec_ocupado_inicio) + time '00:00:01'))
	and (current_timestamp<((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) 
		then monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto)
when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = false
	and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))
	and (current_timestamp>(fec_ocupado_inicio))
	and (current_timestamp<((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) 
		then monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto)
when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = false
	and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio))
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))
	and (current_timestamp>((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) 
		then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+ 1)+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional)) 
when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = false
	and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))
	and (current_timestamp>((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) 
		then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio))+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional))
when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = true
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))
	and (current_timestamp>(date(fec_ocupado_inicio) + time '00:00:01'))
	and (current_timestamp<((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) 
		then monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto)
when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = true
	and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))
	and (current_timestamp>(fec_ocupado_inicio))
	and (current_timestamp<((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) 
		then monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto)
when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = true
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))
	and (current_timestamp>((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) 
		then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+ 1)+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional)) 
when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = true
	and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))
	and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))
	and (current_timestamp>((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) 
		then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio))+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional))
else 0
end as tarifa_gral from habitacion_recepcion_temp order by idhabitacion_dato asc