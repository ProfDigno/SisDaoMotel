drop  table caja_resumen_dia;
CREATE  TABLE caja_resumen_dia as
select date(cc.fecha_inicio) as fec_inicio,sum(case when cd.fk_idventa>0 then 1 else 0 end) as cant_venta,
sum((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-
(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)) as m_ingreso,
sum(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion) as m_egreso
from caja_cierre cc,caja_cierre_detalle cd,caja_cierre_item cci 
where cci.fk_idcaja_cierre_detalle=cd.idcaja_cierre_detalle
and cci.fk_idcaja_cierre=cc.idcaja_cierre
and cd.es_cerrado=true 
and date_part('month',cc.fecha_inicio)=7
group by cc.fecha_inicio;

select fec_inicio,sum(cant_venta) as cant,sum(m_ingreso)as ingreso,sum(m_egreso) as egreso 
from caja_resumen_dia group by fec_inicio order by 1 asc

