select
	nro_habitacion,tipo_habitacion,
	case
		
		when estado = 'LIBRE'
		and puerta_limpieza = false
		and puerta_cliente = false then 'MANTENIMIENTO'
		else estado
	end as estado_gral,
	case
		when estado = 'OCUPADO' then to_char((current_timestamp-fec_ocupado_inicio), 'HH24:MI:ss')
		when estado = 'SUCIO' then to_char((current_timestamp-fec_ocupado_fin), 'HH24:MI:ss')
		when estado = 'LIMPIANDO' then to_char((current_timestamp-fec_ocupado_fin), 'HH24:MI:ss')
		else '.'
	end as tiempo,

	case
		when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true
		and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))<(minuto_minimo * 60))  
         then (monto_por_hora_minimo + monto_consumision-(monto_descuento+monto_adelanto))
		when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true
		and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))>(minuto_minimo * 60))
		and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))<(((minuto_minimo)* 60)+(minuto_adicional * 60)))  
         then (monto_por_hora_minimo + monto_por_hora_adicional + monto_consumision-(monto_descuento+monto_adelanto))
		when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true
		and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))>((minuto_minimo * 60)+(minuto_adicional * 60)))  
         then (monto_por_hora_minimo + monto_por_hora_adicional + monto_consumision-(monto_descuento+monto_adelanto) + 
              (cast(to_char((current_timestamp)-(fec_ocupado_inicio + (minuto_minimo || ' minutes')::interval), 'HH24') as integer)* monto_por_hora_adicional))
                when es_por_dormir = true and es_por_hora = false
		and estado = 'OCUPADO'
		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio))
		and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))
		and (current_timestamp>(date(fec_ocupado_inicio) + time '00:00:01'))
		and (current_timestamp<((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) 
          then monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto)
		when es_por_dormir = true and es_por_hora = false
		and estado = 'OCUPADO'
		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))
		and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))
		and (current_timestamp>(fec_ocupado_inicio))
		and (current_timestamp<((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) 
          then monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto)
		when es_por_dormir = true and es_por_hora = false
		and estado = 'OCUPADO'
		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio))
		and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))
		and (current_timestamp>((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) 
          then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+ 1)+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional))
		when es_por_dormir = true and es_por_hora = false
		and estado = 'OCUPADO'
		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))
		and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))
		and (current_timestamp>((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) 
          then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio))+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional))
		when es_por_dormir = true and es_por_hora = true
		and estado = 'OCUPADO'
		and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))
		and (current_timestamp>(date(fec_ocupado_inicio) + time '00:00:01'))
		and (current_timestamp<((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) 
          then monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto)
		when es_por_dormir = true and es_por_hora = true
		and estado = 'OCUPADO'
		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))
		and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))
		and (current_timestamp>(fec_ocupado_inicio))
		and (current_timestamp<((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) 
          then monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto)
		when es_por_dormir = true and es_por_hora = true
		and estado = 'OCUPADO'
		and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))
		and (current_timestamp>((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) 
          then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+ 1)+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional))
		when es_por_dormir = true and es_por_hora = true
		and estado = 'OCUPADO'
		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))
		and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))
		and (current_timestamp>((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) 
          then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio))+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional))
		else 0
	end as tarifa_gral,
	case
	    when estado = 'LIBRE' and puerta_limpieza = false and puerta_cliente = false then 'franco_db_hab_estado_Images/35NORMAL.nombre_imagen.053841.png'
            when estado = 'OCUPADO' and es_por_hora = true and es_por_dormir = false then 'franco_db_hab_estado_Images/31NORMAL.nombre_imagen.052958.png'
            when estado = 'OCUPADO' and es_por_hora = false and es_por_dormir = true then 'franco_db_hab_estado_Images/32NORMAL.nombre_imagen.053601.png'
            when estado = 'OCUPADO' and es_por_hora = true and es_por_dormir = true then 'franco_db_hab_estado_Images/32NORMAL.nombre_imagen.053601.png'
            when estado = 'LIMPIANDO' then 'franco_db_hab_estado_Images/33NORMAL.nombre_imagen.053652.png'
	    when estado = 'SUCIO' then 'franco_db_hab_estado_Images/34NORMAL.nombre_imagen.053800.png'
            when estado = 'LIBRE' then 'franco_db_hab_estado_Images/30NORMAL.nombre_imagen.050601.png'
	    else 'SIN-IMAGEN'
	end as nombre_imagen
 from
	habitacion_recepcion_temp 
where activo=true 
order by orden asc;