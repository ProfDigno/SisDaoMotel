select date(cca.fecha_fin),
sum((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-
                (cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)) as m_ingreso,
sum(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion) as m_egreso,
sum(((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-
                (cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto))-
                (cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion))  as m_saldo
from caja_cierre cca,caja_cierre_item cci,caja_cierre_detalle cd  
where cca.idcaja_cierre=cci.fk_idcaja_cierre 
and cci.fk_idcaja_cierre_detalle=cd.idcaja_cierre_detalle 
and cd.es_cerrado=true
group by 1
order by 1 desc