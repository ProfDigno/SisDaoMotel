package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.venta_item_eliminar;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.venta_eliminar;
import FORMULARIO.ENTIDAD.venta_item_eliminar;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_venta_item_eliminar {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "VENTA_ITEM_ELIMINAR GUARDADO CORRECTAMENTE";
    private String mensaje_update = "VENTA_ITEM_ELIMINAR MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO venta_item_eliminar(idventa_item_eliminar,fecha_creado,creado_por,tipo_item,descripcion,cantidad,precio_venta,precio_compra,fk_idproducto,fk_idventa_eliminar) VALUES (?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE venta_item_eliminar SET fecha_creado=?,creado_por=?,tipo_item=?,descripcion=?,cantidad=?,precio_venta=?,precio_compra=?,fk_idproducto=?,fk_idventa_eliminar=? WHERE idventa_item_eliminar=?;";
    private String sql_select = "SELECT idventa_item_eliminar,fecha_creado,creado_por,tipo_item,descripcion,cantidad,precio_venta,precio_compra,fk_idproducto,fk_idventa_eliminar FROM venta_item_eliminar order by 1 desc;";
    private String sql_cargar = "SELECT idventa_item_eliminar,fecha_creado,creado_por,tipo_item,descripcion,cantidad,precio_venta,precio_compra,fk_idproducto,fk_idventa_eliminar FROM venta_item_eliminar WHERE idventa_item_eliminar=";

    public void insertar_venta_item_eliminar(Connection conn, venta_item_eliminar vie) {
        vie.setC1idventa_item_eliminar(eveconn.getInt_ultimoID_mas_uno(conn, vie.getTb_venta_item_eliminar(), vie.getId_idventa_item_eliminar()));
        String titulo = "insertar_venta_item_eliminar";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, vie.getC1idventa_item_eliminar());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, vie.getC3creado_por());
            pst.setString(4, vie.getC4tipo_item());
            pst.setString(5, vie.getC5descripcion());
            pst.setDouble(6, vie.getC6cantidad());
            pst.setDouble(7, vie.getC7precio_venta());
            pst.setDouble(8, vie.getC8precio_compra());
            pst.setInt(9, vie.getC9fk_idproducto());
            pst.setInt(10, vie.getC10fk_idventa_eliminar());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + vie.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + vie.toString(), titulo);
        }
    }

    public void update_venta_item_eliminar(Connection conn, venta_item_eliminar vie) {
        String titulo = "update_venta_item_eliminar";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, vie.getC3creado_por());
            pst.setString(3, vie.getC4tipo_item());
            pst.setString(4, vie.getC5descripcion());
            pst.setDouble(5, vie.getC6cantidad());
            pst.setDouble(6, vie.getC7precio_venta());
            pst.setDouble(7, vie.getC8precio_compra());
            pst.setInt(8, vie.getC9fk_idproducto());
            pst.setInt(9, vie.getC10fk_idventa_eliminar());
            pst.setInt(10, vie.getC1idventa_item_eliminar());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + vie.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + vie.toString(), titulo);
        }
    }

    public void cargar_venta_item_eliminar(Connection conn, venta_item_eliminar vie, int idventa_item_eliminar) {
        String titulo = "Cargar_venta_item_eliminar";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idventa_item_eliminar, titulo);
            if (rs.next()) {
                vie.setC1idventa_item_eliminar(rs.getInt(1));
                vie.setC2fecha_creado(rs.getString(2));
                vie.setC3creado_por(rs.getString(3));
                vie.setC4tipo_item(rs.getString(4));
                vie.setC5descripcion(rs.getString(5));
                vie.setC6cantidad(rs.getDouble(6));
                vie.setC7precio_venta(rs.getDouble(7));
                vie.setC8precio_compra(rs.getDouble(8));
                vie.setC9fk_idproducto(rs.getInt(9));
                vie.setC10fk_idventa_eliminar(rs.getInt(10));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + vie.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + vie.toString(), titulo);
        }
    }

    public void actualizar_tabla_venta_item_eliminar(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_venta_item_eliminar(tbltabla);
    }

    public void ancho_tabla_venta_item_eliminar(JTable tbltabla) {
        int Ancho[] = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void insertar_venta_item_eliminar_de_tabla(Connection conn, JTable tblitem_producto, venta_eliminar ven) {
        //String dato[] = {Sidproducto, Sdescripcion, Sprecio_interno, Scantidad, Ssubtotal, Sprecio_interno2, Sprecio_compra, Stipo, Ssubtotal2, Siva};
        String titulo = "insertar_venta_item_interno_de_tabla";
        venta_item_eliminar item = new venta_item_eliminar();
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String idproducto = ((tblitem_producto.getModel().getValueAt(row, 0).toString()));
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String cantidad = ((tblitem_producto.getModel().getValueAt(row, 3).toString()));
            String precio_venta = ((tblitem_producto.getModel().getValueAt(row, 5).toString()));
            String precio_compra = ((tblitem_producto.getModel().getValueAt(row, 6).toString()));
            String tipo_item = ((tblitem_producto.getModel().getValueAt(row, 7).toString()));
            try {
                item.setC3creado_por(ven.getC3creado_por());
                item.setC4tipo_item(tipo_item);
                item.setC5descripcion(descripcion);
                item.setC6cantidad(Double.parseDouble(cantidad));
                item.setC7precio_venta(Double.parseDouble(precio_venta));
                item.setC8precio_compra(Double.parseDouble(precio_compra));
                item.setC10fk_idventa_eliminar(ven.getC1idventa_eliminar());
                item.setC9fk_idproducto(Integer.parseInt(idproducto));
                insertar_venta_item_eliminar(conn, item);
                update_producto_descontar_stock(conn, item);
            } catch (Exception e) {
                evemen.mensaje_error(e, titulo);
                break;
            }

        }
    }
    private void update_producto_descontar_stock(Connection conn, venta_item_eliminar item) {
        String sql_desc_stock = "UPDATE producto SET stock_actual=(stock_actual-?) WHERE idproducto=?;";
        String titulo = "update_producto_descontar_stock";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_desc_stock);
            pst.setDouble(1, item.getC6cantidad());
            pst.setInt(2, item.getC9fk_idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_desc_stock + "\n" + item.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_desc_stock + "\n" + item.toString(), titulo);
        }
    }
    public void update_venta_item_eliminar_ingresar_stock_producto(Connection conn, venta_item_eliminar item) {
        String titulo = "update_venta_item_eliminar_ingresar_stock_producto";
        String sql = "update producto set stock_actual=(stock_actual+\n"
                + "(select sum(ci2.cantidad) as cant from venta_item_eliminar ci2 \n"
                + "where ci2.fk_idproducto=ci1.fk_idproducto \n"
                + "and ci2.fk_idventa_eliminar=ci1.fk_idventa_eliminar)) \n"
                + "from venta_item_eliminar ci1\n"
                + "where producto.idproducto=ci1.fk_idproducto \n"
                + "and ci1.fk_idventa_eliminar=?;";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, item.getC10fk_idventa_eliminar());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql, titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }
    public void update_venta_item_eliminar_tipo_item(Connection conn, venta_item_eliminar item) {
        String sql_desc_stock = "UPDATE venta_item_eliminar SET tipo_item=? WHERE fk_idventa_eliminar=?;";
        String titulo = "update_venta_item_eliminar_tipo_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_desc_stock);
            pst.setString(1, item.getC4tipo_item());
            pst.setInt(2, item.getC10fk_idventa_eliminar());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_desc_stock + "\n" + item.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_desc_stock + "\n" + item.toString(), titulo);
        }
    }
    public void actualizar_tabla_venta_item_eliminar_creado(Connection conn, JTable tbltabla, int idventa_interna) {
        String sql = "select vii.idventa_item_eliminar as idvii, \n"
                + "vii.descripcion as descripcion,\n"
                + "TRIM(to_char(vii.precio_venta,'999G999G999')) as pventa,\n"
                + "vii.cantidad as cant,\n"
                + "TRIM(to_char((vii.precio_venta*vii.cantidad),'999G999G999')) as sutotal,\n"
                + "vii.tipo_item as tipo\n"
                + "from venta_item_eliminar vii \n"
                + "where vii.fk_idventa_eliminar="+idventa_interna
                + " order by vii.descripcion asc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_venta_item_eliminar_creado(tbltabla);
    }
    public void ancho_tabla_venta_item_eliminar_creado(JTable tbltabla) {
        int Ancho[] = {5, 50, 12, 5, 13,15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
