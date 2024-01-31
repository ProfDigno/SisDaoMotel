/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import ESTADOS.EvenEstado;
import Evento.Fecha.EvenFecha;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.sql.Connection;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_balance {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    EvenEstado eveest = new EvenEstado();
    EvenRender everen = new EvenRender();

    public void actualizar_tabla_caja_resumen(Connection conn, JTable tbltabla, String fec_caja) {
        String sql = "select cc.idcaja_cierre as nro_cierre,\n"
                + "to_char(cc.fecha_inicio,'yy-MM-dd HH24:MI') as fec_inicio,\n"
                + "to_char(cc.fecha_fin,'yy-MM-dd HH24:MI') as fec_fin,\n"
                + "cc.creado_por as usuario, \n"
                + "(select count(*) as m_egreso\n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci \n"
                + "where cci.fk_idcaja_cierre_detalle=cd.idcaja_cierre_detalle \n"
                + "and cd.es_cerrado=true \n"
                + "and cd.fk_idventa>0\n"
                + "and cci.fk_idcaja_cierre=cc.idcaja_cierre) as cant_ocupa,\n"
                + "to_char(sum((ccd.monto_solo_adelanto+ccd.monto_ocupa_minimo+ccd.monto_ocupa_adicional+ccd.monto_interno+ccd.monto_garantia)-\n"
                + "(ccd.monto_ocupa_descuento+ccd.monto_ocupa_adelanto)),'999G999G999') as ocupacion,\n"
                + "to_char(sum(ccd.monto_ocupa_consumo),'999G999G999') as consumo,\n"
                + "to_char(sum(ccd.monto_gasto+ccd.monto_compra+ccd.monto_vale+ccd.monto_liquidacion),'999G999G999') as egreso,\n"
                + "to_char(sum(((ccd.monto_solo_adelanto+ccd.monto_ocupa_minimo+ccd.monto_ocupa_adicional+ccd.monto_ocupa_consumo+ccd.monto_interno+ccd.monto_garantia)-\n"
                + "(ccd.monto_ocupa_descuento+ccd.monto_ocupa_adelanto))-\n"
                + "(ccd.monto_gasto+ccd.monto_compra+ccd.monto_vale+ccd.monto_liquidacion)),'999G999G999') as saldo,\n"
                + "sum((ccd.monto_solo_adelanto+ccd.monto_ocupa_minimo+ccd.monto_ocupa_adicional+ccd.monto_interno+ccd.monto_garantia)-\n"
                + "(ccd.monto_ocupa_descuento+ccd.monto_ocupa_adelanto)) as o_ocupacion,\n"
                + "sum(ccd.monto_ocupa_consumo) as o_consumo,\n"
                + "sum(ccd.monto_gasto+ccd.monto_compra+ccd.monto_vale+ccd.monto_liquidacion) as o_egreso,\n"
                + "sum(((ccd.monto_solo_adelanto+ccd.monto_ocupa_minimo+ccd.monto_ocupa_adicional+ccd.monto_ocupa_consumo+ccd.monto_interno+ccd.monto_garantia)-\n"
                + "(ccd.monto_ocupa_descuento+ccd.monto_ocupa_adelanto))-\n"
                + "(ccd.monto_gasto+ccd.monto_compra+ccd.monto_vale+ccd.monto_liquidacion)) as o_saldo\n"
                + "from caja_cierre cc,caja_cierre_item cci,caja_cierre_detalle ccd\n"
                + "where cc.idcaja_cierre=cci.fk_idcaja_cierre\n"
                + "and cci.fk_idcaja_cierre_detalle=ccd.idcaja_cierre_detalle \n" + fec_caja
                + " \n"
                + "group by 1,2,3,4 \n"
                + "order by 1 asc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_caja_resumen(tbltabla);
    }

    public void ancho_tabla_caja_resumen(JTable tbltabla) {
        int Ancho[] = {5, 15, 15, 15, 10, 10, 10, 10, 10, 1, 1, 1, 1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 4);
        evejt.alinear_derecha_columna(tbltabla, 5);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
        evejt.alinear_derecha_columna(tbltabla, 8);
        evejt.ocultar_columna(tbltabla, 9);
        evejt.ocultar_columna(tbltabla, 10);
        evejt.ocultar_columna(tbltabla, 11);
        evejt.ocultar_columna(tbltabla, 12);
    }

    public void imprimir_caja_resumen(Connection conn, String fec_caja) {
        String sql = "select cc.idcaja_cierre as nro_cierre,\n"
                + "to_char(cc.fecha_inicio,'yy-MM-dd HH24:MI') as fec_inicio,\n"
                + "to_char(cc.fecha_fin,'yy-MM-dd HH24:MI') as fec_fin,\n"
                + "cc.creado_por as usuario, \n"
                + "(select count(*) as m_egreso\n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci \n"
                + "where cci.fk_idcaja_cierre_detalle=cd.idcaja_cierre_detalle \n"
                + "and cd.es_cerrado=true \n"
                + "and cd.fk_idventa>0\n"
                + "and cci.fk_idcaja_cierre=cc.idcaja_cierre) as cant_ocupa,\n"
                + "sum((ccd.monto_solo_adelanto+ccd.monto_ocupa_minimo+ccd.monto_ocupa_adicional+ccd.monto_interno+ccd.monto_garantia)-\n"
                + "(ccd.monto_ocupa_descuento+ccd.monto_ocupa_adelanto)) as ocupacion,\n"
                + "sum(ccd.monto_ocupa_consumo) as consumo,\n"
                + "sum(ccd.monto_gasto+ccd.monto_compra+ccd.monto_vale+ccd.monto_liquidacion) as egreso,\n"
                + "sum(((ccd.monto_solo_adelanto+ccd.monto_ocupa_minimo+ccd.monto_ocupa_adicional+ccd.monto_ocupa_consumo+ccd.monto_interno+ccd.monto_garantia)-\n"
                + "(ccd.monto_ocupa_descuento+ccd.monto_ocupa_adelanto))-\n"
                + "(ccd.monto_gasto+ccd.monto_compra+ccd.monto_vale+ccd.monto_liquidacion)) as saldo\n"
                + "from caja_cierre cc,caja_cierre_item cci,caja_cierre_detalle ccd\n"
                + "where cc.idcaja_cierre=cci.fk_idcaja_cierre\n"
                + "and cci.fk_idcaja_cierre_detalle=ccd.idcaja_cierre_detalle\n" + fec_caja
                + "\n"
                + "group by 1,2,3,4 \n"
                + "order by 1 asc;";
        String titulonota = "CAJA RESUMEN";
        String direccion = "src/REPORTE/BALANCE/repBal_resumen_caja.jrxml";
        String rutatemp = "repBal_resumen_caja";
        rep.imprimir_jasper_pdf(conn, sql, titulonota, direccion, rutatemp);
    }

    public void actualizar_tabla_gasto(Connection conn, JTable tbltabla, String filtro_fecha) {
        String sql = "select g.idgasto as idg,to_char(g.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha,g.creado_por as usuario,\n"
                + "gt.nombre as tipo,g.descripcion as descripcion,g.pago as pago,\n"
                + "case when pago='CAJA' then to_char(g.monto_gasto,'999G999G999') else '0' end as m_caja,\n"
                + "case when pago='ADMIN' then to_char(g.monto_gasto,'999G999G999') else '0' end as m_admin,\n"
                + "case when pago='CAJA' then g.monto_gasto else 0 end as o_caja,\n"
                + "case when pago='ADMIN' then g.monto_gasto else 0 end as o_admin   \n"
                + "from gasto g,gasto_tipo gt\n"
                + "where g.fk_idgasto_tipo=gt.idgasto_tipo \n" + filtro_fecha
                + " and g.estado!='ANULADO'\n"
                + " order by g.idgasto desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_gasto(tbltabla);
    }

    public void ancho_tabla_gasto(JTable tbltabla) {
        int Ancho[] = {5, 10, 10, 20, 25, 5, 10, 10, 1, 1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
        evejt.ocultar_columna(tbltabla, 8);
        evejt.ocultar_columna(tbltabla, 9);
    }

    public void imprimir_gasto_dia(Connection conn, String filtro_fecha) {
        String sql = "select g.idgasto as idg,\n"
                + "to_char(g.fecha_creado,'yyyy-MM-dd') as fecha,\n"
                + "to_char(g.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha_hs,\n"
                + "g.creado_por as usuario,\n"
                + "gt.nombre as tipo,g.descripcion as descripcion,g.pago as pago,\n"
                + "case when pago='CAJA' then g.monto_gasto else 0 end as caja,\n"
                + "case when pago='ADMIN' then g.monto_gasto else 0 end as admin   \n"
                + "from gasto g,gasto_tipo gt\n"
                + "where g.fk_idgasto_tipo=gt.idgasto_tipo \n" + filtro_fecha
                + " and g.estado!='ANULADO'\n"
                + " order by g.pago desc,date(g.fecha_creado) desc;";
        String titulonota = "CAJA GASTO";
        String direccion = "src/REPORTE/BALANCE/repBal_gasto_dia.jrxml";
        String rutatemp = "repBal_gasto_dia";
        rep.imprimir_jasper_pdf(conn, sql, titulonota, direccion, rutatemp);
    }

    public void imprimir_gasto_tipo(Connection conn, String filtro_fecha) {
        String sql = "select g.idgasto as idg,\n"
                + "to_char(g.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha_hs,\n"
                + "g.creado_por as usuario,\n"
                + "gt.nombre as tipo,g.descripcion as descripcion,g.pago as pago,\n"
                + "case when pago='CAJA' then g.monto_gasto else 0 end as caja,\n"
                + "case when pago='ADMIN' then g.monto_gasto else 0 end as admin   \n"
                + "from gasto g,gasto_tipo gt\n"
                + "where g.fk_idgasto_tipo=gt.idgasto_tipo \n" + filtro_fecha
                + " and g.estado!='ANULADO'\n"
                + " order by g.pago desc,gt.nombre desc,date(g.fecha_creado) desc;";
        String titulonota = "CAJA GASTO";
        String direccion = "src/REPORTE/BALANCE/repBal_gasto_tipo.jrxml";
        String rutatemp = "repBal_gasto_tipo";
        rep.imprimir_jasper_pdf(conn, sql, titulonota, direccion, rutatemp);
    }

    public void actualizar_tabla_compra(Connection conn, JTable tbltabla, String filtro_fecha) {
        String sql = "select c.idcompra as idcompra,\n"
                + "to_char(c.fecha_creado,'yyyy-MM-dd HH24:MI:ss') as fecha_hs,\n"
                + "c.nro_factura as factura,\n"
                + "c.creado_por as usuario,\n"
                + "ci.descripcion as descripcion,\n"
                + "to_char(ci.precio_compra,'999G999G999') as pre_compra,\n"
                + "ci.cantidad as cant,\n"
                + "to_char((ci.precio_compra*ci.cantidad),'999G999G999') as subtotal,\n"
                + "(ci.precio_compra*ci.cantidad) as o_subtotal\n"
                + "from compra c,compra_item ci\n"
                + "where c.idcompra=ci.fk_idcompra\n"
                + "and c.estado='CARGADO_ST'\n" + filtro_fecha
                + " order by c.idcompra desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_compra(tbltabla);
    }

    public void ancho_tabla_compra(JTable tbltabla) {
        int Ancho[] = {5, 15, 5, 15, 35, 10, 5, 10, 1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 5);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
        evejt.ocultar_columna(tbltabla, 8);
    }

    public void imprimir_compra_dia(Connection conn, String filtro_fecha) {
        String sql = "select c.idcompra as idcompra,\n"
                + "to_char(c.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha_hs,\n"
                + "to_char(c.fecha_creado,'yyyy-MM-dd') as fecha,\n"
                + "c.nro_factura as factura,\n"
                + "c.creado_por as usuario,\n"
                + "ci.descripcion as descripcion,\n"
                + "ci.precio_compra as pre_compra,\n"
                + "ci.cantidad as cant,\n"
                + "(ci.precio_compra*ci.cantidad) as subtotal\n"
                + "from compra c,compra_item ci\n"
                + "where c.idcompra=ci.fk_idcompra\n" + filtro_fecha
                + " and c.estado='CARGADO_ST'\n"
                + "order by date(c.fecha_creado) desc;";
        String titulonota = "COMPRA DIA";
        String direccion = "src/REPORTE/BALANCE/repBal_compra_dia.jrxml";
        String rutatemp = "repBal_compra_dia";
        rep.imprimir_jasper_pdf(conn, sql, titulonota, direccion, rutatemp);
    }

    public void imprimir_compra_prod(Connection conn, String filtro_fecha) {
        String sql = "select c.idcompra as idcompra,\n"
                + "to_char(c.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha_hs,\n"
                + "c.nro_factura as factura,\n"
                + "c.creado_por as usuario,\n"
                + "ci.descripcion as descripcion,\n"
                + "ci.precio_compra as pre_compra,\n"
                + "ci.cantidad as cant,\n"
                + "(ci.precio_compra*ci.cantidad) as subtotal\n"
                + "from compra c,compra_item ci\n"
                + "where c.idcompra=ci.fk_idcompra\n" + filtro_fecha
                + " and c.estado='CARGADO_ST'\n"
                + "order by ci.descripcion asc,c.idcompra desc;";
        String titulonota = "COMPRA PRODUCTO";
        String direccion = "src/REPORTE/BALANCE/repBal_compra_prod.jrxml";
        String rutatemp = "repBal_compra_prod";
        rep.imprimir_jasper_pdf(conn, sql, titulonota, direccion, rutatemp);
    }

    public void actualizar_tabla_producto_venta(Connection conn, JTable tbltabla, String filtro_fecha) {
        String sql = "select  \n"
                + "p.idproducto as idpro,pc.nombre as categoria,\n"
                + "vi.descripcion as descripcion,\n"
                + "to_char(vi.precio_venta,'999G999G999') as precio_v,\n"
                + "sum(vi.cantidad) as cant,\n"
                + "to_char(sum(vi.precio_venta*vi.cantidad),'999G999G999') as total_venta,\n"
                + "to_char(sum(vi.precio_compra*vi.cantidad),'999G999G999') as total_compra,\n"
                + "to_char(sum((vi.precio_venta*vi.cantidad)-(vi.precio_compra*vi.cantidad)),'999G999G999') as ganancia,\n"
                + "sum(vi.precio_venta*vi.cantidad) as ototal_venta,\n"
                + "sum(vi.precio_compra*vi.cantidad) as ototal_compra,\n"
                + "sum((vi.precio_venta*vi.cantidad)-(vi.precio_compra*vi.cantidad)) as oganancia\n"
                + "from caja_cierre cc,caja_cierre_item cci,caja_cierre_detalle ccd,venta v,venta_item vi,producto p,producto_categoria pc\n"
                + "where cc.idcaja_cierre=cci.fk_idcaja_cierre\n"
                + "and cci.fk_idcaja_cierre_detalle=ccd.idcaja_cierre_detalle\n"
                + "and ccd.fk_idventa=v.idventa\n"
                + "and v.idventa=vi.fk_idventa\n"
                + "and vi.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and ccd.cerrado_por='DESOCUPADO'\n"
                + "and vi.cantidad>0\n" + filtro_fecha
                + " \n"
                + "group by 1,2,3,4\n"
                + "order by 5 desc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_producto_venta(tbltabla);
    }

    public void ancho_tabla_producto_venta(JTable tbltabla) {
        int Ancho[] = {5, 15, 25, 10, 5, 10, 10, 10, 1, 1, 1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 3);
        evejt.alinear_derecha_columna(tbltabla, 4);
        evejt.alinear_derecha_columna(tbltabla, 5);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
        evejt.ocultar_columna(tbltabla, 8);
        evejt.ocultar_columna(tbltabla, 9);
        evejt.ocultar_columna(tbltabla, 10);
    }

    public void imprimir_producto_venta(Connection conn, String fecha, String filtro_fecha) {
        String sql = "select  ('" + fecha + "') as fecha,\n"
                + "p.idproducto as idpro,pc.nombre as categoria,\n"
                + "vi.descripcion as descripcion,\n"
                + "vi.precio_venta as precio_v,\n"
                + "sum(vi.cantidad) as cant,\n"
                + "sum(vi.precio_venta*vi.cantidad) as ototal_venta,\n"
                + "sum(vi.precio_compra*vi.cantidad) as ototal_compra,\n"
                + "sum((vi.precio_venta*vi.cantidad)-(vi.precio_compra*vi.cantidad)) as oganancia\n"
                + "from caja_cierre cc,caja_cierre_item cci,caja_cierre_detalle ccd,venta v,venta_item vi,producto p,producto_categoria pc\n"
                + "where cc.idcaja_cierre=cci.fk_idcaja_cierre\n"
                + "and cci.fk_idcaja_cierre_detalle=ccd.idcaja_cierre_detalle\n"
                + "and ccd.fk_idventa=v.idventa\n"
                + "and v.idventa=vi.fk_idventa\n"
                + "and vi.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and ccd.cerrado_por='DESOCUPADO'\n"
                + "and vi.cantidad>0\n" + filtro_fecha
                + " \n"
                + "group by 1,2,3,4,5\n"
                + "order by 6 desc;";
        String titulonota = "VENTA PRODUCTO";
        String direccion = "src/REPORTE/BALANCE/repBal_producto_venta.jrxml";
        String rutatemp = "repBal_producto_venta";
        rep.imprimir_jasper_pdf(conn, sql, titulonota, direccion, rutatemp);
    }

    public void actualizar_tabla_caja_resumen_admin(Connection conn, JTable tbltabla, String fec_caja, String maquina) {
        String sql = "select cc.idcaja_cierre as nro_cierre,\n"
                + "to_char(cc.fecha_inicio,'yy-MM-dd HH24:MI') as fec_inicio,\n"
                + "to_char(cc.fecha_fin,'yy-MM-dd HH24:MI') as fec_fin,\n"
                + "cc.creado_por as usuario, \n"
                + "(select count(*) as m_egreso\n"
                + "from admin_caja_cierre_detalle cd,admin_caja_cierre_item cci \n"
                + "where cci.fk_idcaja_cierre_detalle=cd.idcaja_cierre_detalle \n"
                + "and cd.es_cerrado=true \n"
                + "and cd.fk_idventa>0\n"
                + "and cci.fk_idcaja_cierre=cc.idcaja_cierre) as cant_ocupa,\n"
                + "to_char(sum((ccd.monto_solo_adelanto+ccd.monto_ocupa_minimo+ccd.monto_ocupa_adicional+ccd.monto_interno+ccd.monto_garantia)-\n"
                + "(ccd.monto_ocupa_descuento+ccd.monto_ocupa_adelanto)),'999G999G999') as ocupacion,\n"
                + "to_char(sum(ccd.monto_ocupa_consumo),'999G999G999') as consumo,\n"
                + "to_char(sum(ccd.monto_gasto+ccd.monto_compra+ccd.monto_vale+ccd.monto_liquidacion),'999G999G999') as egreso,\n"
                + "to_char(sum(((ccd.monto_solo_adelanto+ccd.monto_ocupa_minimo+ccd.monto_ocupa_adicional+ccd.monto_ocupa_consumo+ccd.monto_interno+ccd.monto_garantia)-\n"
                + "(ccd.monto_ocupa_descuento+ccd.monto_ocupa_adelanto))-\n"
                + "(ccd.monto_gasto+ccd.monto_compra+ccd.monto_vale+ccd.monto_liquidacion)),'999G999G999') as saldo,\n"
                + "sum((ccd.monto_solo_adelanto+ccd.monto_ocupa_minimo+ccd.monto_ocupa_adicional+ccd.monto_interno+ccd.monto_garantia)-\n"
                + "(ccd.monto_ocupa_descuento+ccd.monto_ocupa_adelanto)) as o_ocupacion,\n"
                + "sum(ccd.monto_ocupa_consumo) as o_consumo,\n"
                + "sum(ccd.monto_gasto+ccd.monto_compra+ccd.monto_vale+ccd.monto_liquidacion) as o_egreso,\n"
                + "sum(((ccd.monto_solo_adelanto+ccd.monto_ocupa_minimo+ccd.monto_ocupa_adicional+ccd.monto_ocupa_consumo+ccd.monto_interno+ccd.monto_garantia)-\n"
                + "(ccd.monto_ocupa_descuento+ccd.monto_ocupa_adelanto))-\n"
                + "(ccd.monto_gasto+ccd.monto_compra+ccd.monto_vale+ccd.monto_liquidacion)) as o_saldo\n"
                + "from admin_caja_cierre cc,admin_caja_cierre_item cci,admin_caja_cierre_detalle ccd\n"
                + "where cc.idcaja_cierre=cci.fk_idcaja_cierre\n"
                + "and cci.fk_idcaja_cierre_detalle=ccd.idcaja_cierre_detalle \n"
                + "and cc.maquina='"+maquina+"'\n"
                + "and cci.maquina='"+maquina+"'\n"
                + "and ccd.maquina='"+maquina+"'\n"
                + "  "+fec_caja
                + " \ngroup by 1,2,3,4 \n"
                + " order by 1 asc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_caja_resumen(tbltabla);
    }
}
