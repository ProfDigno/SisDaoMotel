package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import ESTADOS.EvenEstado;
import FORMULARIO.ENTIDAD.venta_item_interno;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.venta_interno;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_venta_item_interno {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    EvenEstado eveest = new EvenEstado();
    private String mensaje_insert = "VENTA_ITEM_INTERNO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "VENTA_ITEM_INTERNO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO venta_item_interno(idventa_item_interno,fecha_creado,creado_por,"
            + "tipo_item,descripcion,cantidad,precio_venta,precio_compra,"
            + "fk_idventa_interno,fk_idproducto) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE venta_item_interno SET fecha_creado=?,creado_por=?,"
            + "tipo_item=?,descripcion=?,cantidad=?,precio_venta=?,precio_compra=?,"
            + "fk_idventa_interno=?,fk_idproducto=? "
            + "WHERE idventa_item_interno=?;";
    private String sql_select = "SELECT idventa_item_interno,fecha_creado,creado_por,"
            + "tipo_item,descripcion,cantidad,precio_venta,precio_compra,"
            + "fk_idventa_interno,fk_idproducto "
            + "FROM venta_item_interno order by 1 desc;";
    private String sql_cargar = "SELECT idventa_item_interno,fecha_creado,creado_por,"
            + "tipo_item,descripcion,cantidad,precio_venta,precio_compra,"
            + "fk_idventa_interno,fk_idproducto "
            + "FROM venta_item_interno WHERE idventa_item_interno=";

    public void insertar_venta_item_interno(Connection conn, venta_item_interno veitin) {
        veitin.setC1idventa_item_interno(eveconn.getInt_ultimoID_mas_uno(conn, veitin.getTb_venta_item_interno(), veitin.getId_idventa_item_interno()));
        String titulo = "insertar_venta_item_interno";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, veitin.getC1idventa_item_interno());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, veitin.getC3creado_por());
            pst.setString(4, veitin.getC4tipo_item());
            pst.setString(5, veitin.getC5descripcion());
            pst.setDouble(6, veitin.getC6cantidad());
            pst.setDouble(7, veitin.getC7precio_venta());
            pst.setDouble(8, veitin.getC8precio_compra());
            pst.setInt(9, veitin.getC9fk_idventa_interno());
            pst.setInt(10, veitin.getC10fk_idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + veitin.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + veitin.toString(), titulo);
        }
    }

    public void update_venta_item_interno(Connection conn, venta_item_interno veitin) {
        String titulo = "update_venta_item_interno";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, veitin.getC3creado_por());
            pst.setString(3, veitin.getC4tipo_item());
            pst.setString(4, veitin.getC5descripcion());
            pst.setDouble(5, veitin.getC6cantidad());
            pst.setDouble(6, veitin.getC7precio_venta());
            pst.setDouble(7, veitin.getC8precio_compra());
            pst.setInt(8, veitin.getC9fk_idventa_interno());
            pst.setInt(9, veitin.getC10fk_idproducto());
            pst.setInt(10, veitin.getC1idventa_item_interno());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + veitin.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + veitin.toString(), titulo);
        }
    }

    public void cargar_venta_item_interno(Connection conn, venta_item_interno veitin, int idventa_item_interno) {
        String titulo = "Cargar_venta_item_interno";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idventa_item_interno, titulo);
            if (rs.next()) {
                veitin.setC1idventa_item_interno(rs.getInt(1));
                veitin.setC2fecha_creado(rs.getString(2));
                veitin.setC3creado_por(rs.getString(3));
                veitin.setC4tipo_item(rs.getString(4));
                veitin.setC5descripcion(rs.getString(5));
                veitin.setC6cantidad(rs.getDouble(6));
                veitin.setC7precio_venta(rs.getDouble(7));
                veitin.setC8precio_compra(rs.getDouble(8));
                veitin.setC9fk_idventa_interno(rs.getInt(9));
                veitin.setC10fk_idproducto(rs.getInt(10));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + veitin.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + veitin.toString(), titulo);
        }
    }

    public void actualizar_tabla_venta_item_interno(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_venta_item_interno(tbltabla);
    }

    public void ancho_tabla_venta_item_interno(JTable tbltabla) {
        int Ancho[] = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void insertar_venta_item_interno_de_tabla(Connection conn, JTable tblitem_producto, venta_interno ven) {
        //String dato[] = {Sidproducto, Sdescripcion, Sprecio_interno, Scantidad, Ssubtotal, Sprecio_interno2, Sprecio_compra, Stipo, Ssubtotal2, Siva};
        String titulo = "insertar_venta_item_interno_de_tabla";
        venta_item_interno item = new venta_item_interno();
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
                item.setC9fk_idventa_interno(ven.getC1idventa_interno());
                item.setC10fk_idproducto(Integer.parseInt(idproducto));
                insertar_venta_item_interno(conn, item);
                update_producto_descontar_stock(conn, item);
            } catch (Exception e) {
                evemen.mensaje_error(e, titulo);
                break;
            }

        }
    }

    public void actualizar_tabla_venta_item_interno_creado(Connection conn, JTable tbltabla, int idventa_interna) {
        String sql = "select vii.idventa_item_interno as idvii, \n"
                + "vii.descripcion as descripcion,\n"
                + "TRIM(to_char(vii.precio_venta,'999G999G999')) as pventa,\n"
                + "vii.cantidad as cant,\n"
                + "TRIM(to_char((vii.precio_venta*vii.cantidad),'999G999G999')) as sutotal,\n"
                + "vii.tipo_item as tipo\n"
                + "from venta_item_interno vii \n"
                + "where vii.fk_idventa_interno="+idventa_interna
                + " order by vii.descripcion asc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_venta_item_interno_creado(tbltabla);
    }

    public void ancho_tabla_venta_item_interno_creado(JTable tbltabla) {
        int Ancho[] = {5, 50, 12, 5, 13,15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    private void update_producto_descontar_stock(Connection conn, venta_item_interno item) {
        String sql_desc_stock = "UPDATE producto SET stock_actual=(stock_actual-?) WHERE idproducto=?;";
        String titulo = "update_producto_descontar_stock";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_desc_stock);
            pst.setDouble(1, item.getC6cantidad());
            pst.setInt(2, item.getC10fk_idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_desc_stock + "\n" + item.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_desc_stock + "\n" + item.toString(), titulo);
        }
    }
    public void update_venta_item_interno_ingresar_stock_producto(Connection conn, venta_item_interno item) {
        String titulo = "update_venta_item_interno_ingresar_stock_producto";
        String sql = "update producto set stock_actual=(stock_actual+\n"
                + "(select sum(ci2.cantidad) as cant from venta_item_interno ci2 \n"
                + "where ci2.fk_idproducto=ci1.fk_idproducto \n"
                + "and ci2.fk_idventa_interno=ci1.fk_idventa_interno)) \n"
                + "from venta_item_interno ci1\n"
                + "where producto.idproducto=ci1.fk_idproducto \n"
                + "and ci1.fk_idventa_interno=?;";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, item.getC9fk_idventa_interno());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql, titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }
    public void update_venta_item_interno_tipo_item(Connection conn, venta_item_interno item) {
        String sql_desc_stock = "UPDATE venta_item_interno SET tipo_item=? WHERE fk_idventa_interno=?;";
        String titulo = "update_venta_item_interno_tipo_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_desc_stock);
            pst.setString(1, item.getC4tipo_item());
            pst.setInt(2, item.getC9fk_idventa_interno());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_desc_stock + "\n" + item.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_desc_stock + "\n" + item.toString(), titulo);
        }
    }
}
