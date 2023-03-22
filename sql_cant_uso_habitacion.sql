SELECT to_char(hr.fecha_creado,'yyyy') as fecha, hr.nro_habitacion,hr.fk_idhabitacion_dato,hd.tipo_habitacion, 
sum(hr.monto_consumo) as consumo, sum(hr.monto_descuento) as descuento, 
sum(hr.monto_adelanto) as adelanto, sum(hr.cant_adicional) as adicional,count(*) as cant       
FROM public.habitacion_recepcion hr,habitacion_dato hd
where hr.fk_idhabitacion_dato=hd.idhabitacion_dato
and hr.estado='TERMINADO' 
group by 1,2,3,4
order by 1 desc,9 desc;

--select * from habitacion_dato
