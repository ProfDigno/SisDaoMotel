package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.caja_producto_item;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.usuario;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_caja_producto_item {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    public usuario ENTusu = new usuario();
    private String mensaje_insert = "CAJA_PRODUCTO_ITEM GUARDADO CORRECTAMENTE";
    private String mensaje_update = "CAJA_PRODUCTO_ITEM MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO caja_producto_item(idcaja_producto_item,fecha_creado,creado_por,descripcion,precio_venta,stock_actual,cant_vendido,cant_cargado,fk_idcaja_cierre,fk_idproducto,fk_idusuario) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE caja_producto_item SET fecha_creado=?,creado_por=?,descripcion=?,precio_venta=?,stock_actual=?,cant_vendido=?,cant_cargado=?,fk_idcaja_cierre=?,fk_idproducto=?,fk_idusuario=? WHERE idcaja_producto_item=?;";
    private String sql_select = "SELECT idcaja_producto_item,fecha_creado,creado_por,descripcion,precio_venta,stock_actual,cant_vendido,cant_cargado,fk_idcaja_cierre,fk_idproducto,fk_idusuario FROM caja_producto_item order by 1 desc;";
    private String sql_cargar = "SELECT idcaja_producto_item,fecha_creado,creado_por,descripcion,precio_venta,stock_actual,cant_vendido,cant_cargado,fk_idcaja_cierre,fk_idproducto,fk_idusuario FROM caja_producto_item WHERE idcaja_producto_item=";

    public void insertar_caja_producto_item(Connection conn, caja_producto_item caprit) {
        caprit.setC1idcaja_producto_item(eveconn.getInt_ultimoID_mas_uno(conn, caprit.getTb_caja_producto_item(), caprit.getId_idcaja_producto_item()));
        String titulo = "insertar_caja_producto_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, caprit.getC1idcaja_producto_item());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, caprit.getC3creado_por());
            pst.setString(4, caprit.getC4descripcion());
            pst.setDouble(5, caprit.getC5precio_venta());
            pst.setDouble(6, caprit.getC6stock_actual());
            pst.setDouble(7, caprit.getC7cant_vendido());
            pst.setDouble(8, caprit.getC8cant_cargado());
            pst.setInt(9, caprit.getC9fk_idcaja_cierre());
            pst.setInt(10, caprit.getC10fk_idproducto());
            pst.setInt(11, caprit.getC11fk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + caprit.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + caprit.toString(), titulo);
        }
    }

    public void update_caja_producto_item(Connection conn, caja_producto_item caprit) {
        String titulo = "update_caja_producto_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, caprit.getC3creado_por());
            pst.setString(3, caprit.getC4descripcion());
            pst.setDouble(4, caprit.getC5precio_venta());
            pst.setDouble(5, caprit.getC6stock_actual());
            pst.setDouble(6, caprit.getC7cant_vendido());
            pst.setDouble(7, caprit.getC8cant_cargado());
            pst.setInt(8, caprit.getC9fk_idcaja_cierre());
            pst.setInt(9, caprit.getC10fk_idproducto());
            pst.setInt(10, caprit.getC11fk_idusuario());
            pst.setInt(11, caprit.getC1idcaja_producto_item());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + caprit.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + caprit.toString(), titulo);
        }
    }

    public void cargar_caja_producto_item(Connection conn, caja_producto_item caprit, int idcaja_producto_item) {
        String titulo = "Cargar_caja_producto_item";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idcaja_producto_item, titulo);
            if (rs.next()) {
                caprit.setC1idcaja_producto_item(rs.getInt(1));
                caprit.setC2fecha_creado(rs.getString(2));
                caprit.setC3creado_por(rs.getString(3));
                caprit.setC4descripcion(rs.getString(4));
                caprit.setC5precio_venta(rs.getDouble(5));
                caprit.setC6stock_actual(rs.getDouble(6));
                caprit.setC7cant_vendido(rs.getDouble(7));
                caprit.setC8cant_cargado(rs.getDouble(8));
                caprit.setC9fk_idcaja_cierre(rs.getInt(9));
                caprit.setC10fk_idproducto(rs.getInt(10));
                caprit.setC11fk_idusuario(rs.getInt(11));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + caprit.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + caprit.toString(), titulo);
        }
    }

    public void actualizar_tabla_caja_producto_item(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_caja_producto_item(tbltabla);
    }

    public void ancho_tabla_caja_producto_item(JTable tbltabla) {
        int Ancho[] = {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void insertar_caja_producto_item_por_select_todos(Connection conn, String creado_por, int fk_idusuario) {
        String sql = "INSERT INTO public.caja_producto_item(\n"
                + "            idcaja_producto_item, fecha_creado, creado_por, descripcion, \n"
                + "            precio_venta, stock_actual, cant_vendido,cant_interno, cant_cargado, fk_idcaja_cierre, \n"
                + "            fk_idproducto, fk_idusuario)\n"
                + "select \n"
                + "nextval('id_seq_cpi')  as idcaja_producto_item,\n"
                + "current_timestamp as fecha_creado,\n"
                + "('" + creado_por + "') as creado_por,\n"
                + "p.nombre as descripcion,\n"
                + "p.precio_venta,p.stock_actual,(0) as cant_vendido,(0) as cant_interno,(0) as cant_cargado,\n"
                + "coalesce ((select max(cc.idcaja_cierre) from caja_cierre cc),1)  as fk_idcaja_cierre,\n"
                + "p.idproducto as fk_idproducto,(" + fk_idusuario + ") as fk_idusuario\n"
                + "from producto p \n"
                + "where p.es_venta=true and p.es_compra=true \n"
                + "order by p.fk_idproducto_categoria desc";
        eveconn.SQL_execute_libre(conn, sql);
    }

    public void actualizar_tabla_caja_producto_item_por_caja(Connection conn, JTable tbltabla, int fk_idcaja_cierre) {
        String sql = "select cpi.fk_idproducto as idp,pc.nombre as categoria,\n"
                + "cpi.descripcion,"
                + "TRIM(to_char(cpi.precio_venta,'9G999G999')) as pventa,\n"
                + "((cpi.stock_actual+cpi.cant_cargado)-(cpi.cant_vendido+cpi.cant_interno)) as stock_ini,\n"
                + "cpi.cant_vendido as c_venta,cpi.cant_interno as c_interno,cpi.cant_cargado as c_carga,\n"
                + "cpi.stock_actual as stock_act\n"
                + "from caja_producto_item cpi,producto p,producto_categoria pc\n"
                + "where cpi.fk_idproducto=p.idproducto \n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and cpi.fk_idcaja_cierre=" + fk_idcaja_cierre
                + " order by pc.nombre asc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_caja_producto_item_por_caja(tbltabla);
    }

    public void ancho_tabla_caja_producto_item_por_caja(JTable tbltabla) {
        int Ancho[] = {5, 13, 37, 10, 7, 7, 7, 7, 7};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 3);
    }

    public void update_caja_producto_item_stock_venta(Connection conn, int fk_idcaja_cierre) {
        String sql = "update caja_producto_item set cant_vendido=coalesce((select (0-(sum(vi.cantidad))) as cant\n"
                + "from venta v,caja_cierre_item cci,caja_cierre_detalle ccd,venta_item vi  \n"
                + "where  ccd.idcaja_cierre_detalle=cci.fk_idcaja_cierre_detalle \n"
                + "and ccd.fk_idventa=v.idventa \n"
                + "and v.idventa=vi.fk_idventa \n"
                + "and v.estado='TERMINADO'\n"
                + "and ccd.cerrado_por='DESOCUPADO'\n"
                + "and v.monto_consumo>0\n"
                + "and cci.fk_idcaja_cierre=caja_producto_item.fk_idcaja_cierre \n"
                + "and vi.fk_idproducto=caja_producto_item.fk_idproducto),0)"
                + "where fk_idcaja_cierre=" + fk_idcaja_cierre + " ;";
        eveconn.SQL_execute_libre(conn, sql);
    }

    public void update_caja_producto_item_stock_venta_interna(Connection conn, int fk_idcaja_cierre) {
        String sql = "update caja_producto_item set cant_interno=coalesce ((select (0-(sum(vii.cantidad))) as cant\n"
                + "from venta_interno vi,caja_cierre_item cci,caja_cierre_detalle ccd,venta_item_interno vii  \n"
                + "where  ccd.idcaja_cierre_detalle=cci.fk_idcaja_cierre_detalle \n"
                + "and ccd.fk_idventa_interno=vi.idventa_interno \n"
                + "and vi.idventa_interno=vii.fk_idventa_interno \n"
                + "and vi.estado='TERMINADO'\n"
                + "and ccd.cerrado_por='VEN_INTERNA'\n"
                + "and cci.fk_idcaja_cierre=caja_producto_item.fk_idcaja_cierre \n"
                + "and vii.fk_idproducto=caja_producto_item.fk_idproducto),0) \n"
                + "where fk_idcaja_cierre=" + fk_idcaja_cierre + " ;";
        eveconn.SQL_execute_libre(conn, sql);
    }

    public void update_caja_producto_item_stock_compra(Connection conn, int fk_idcaja_cierre) {
        String sql = "update caja_producto_item set cant_cargado=coalesce ((select ((sum(ci.cantidad))) as cant\n"
                + "from compra co,caja_cierre_item cci,caja_cierre_detalle ccd,compra_item ci  \n"
                + "where  ccd.idcaja_cierre_detalle=cci.fk_idcaja_cierre_detalle \n"
                + "and ccd.fk_idcompra=co.idcompra \n"
                + "and co.idcompra=ci.fk_idcompra \n"
                + "and co.estado='CARGADO_ST'\n"
                + "and ccd.cerrado_por='COMPRA'\n"
                + "and cci.fk_idcaja_cierre=caja_producto_item.fk_idcaja_cierre\n"
                + "and ci.fk_idproducto=caja_producto_item.fk_idproducto),0) \n"
                + "where fk_idcaja_cierre=" + fk_idcaja_cierre + " ;";
        eveconn.SQL_execute_libre(conn, sql);
    }
}
