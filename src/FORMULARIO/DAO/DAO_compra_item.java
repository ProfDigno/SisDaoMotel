package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.compra_item;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.compra;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_compra_item {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "COMPRA_ITEM GUARDADO CORRECTAMENTE";
    private String mensaje_update = "COMPRA_ITEM MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO compra_item(idcompra_item,fecha_creado,creado_por,tipo_item,descripcion,cantidad,precio_compra,fk_idproducto,fk_idcompra) VALUES (?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE compra_item SET fecha_creado=?,creado_por=?,tipo_item=?,descripcion=?,cantidad=?,precio_compra=?,fk_idproducto=?,fk_idcompra=? WHERE idcompra_item=?;";
    private String sql_select = "SELECT idcompra_item,fecha_creado,creado_por,tipo_item,descripcion,cantidad,precio_compra,fk_idproducto,fk_idcompra FROM compra_item order by 1 desc;";
    private String sql_cargar = "SELECT idcompra_item,fecha_creado,creado_por,tipo_item,descripcion,cantidad,precio_compra,fk_idproducto,fk_idcompra FROM compra_item WHERE idcompra_item=";

    public void insertar_compra_item(Connection conn, compra_item coit) {
        coit.setC1idcompra_item(eveconn.getInt_ultimoID_mas_uno(conn, coit.getTb_compra_item(), coit.getId_idcompra_item()));
        String titulo = "insertar_compra_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, coit.getC1idcompra_item());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, coit.getC3creado_por());
            pst.setString(4, coit.getC4tipo_item());
            pst.setString(5, coit.getC5descripcion());
            pst.setDouble(6, coit.getC6cantidad());
            pst.setDouble(7, coit.getC7precio_compra());
            pst.setInt(8, coit.getC8fk_idproducto());
            pst.setInt(9, coit.getC9fk_idcompra());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + coit.toString(), titulo);
//            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + coit.toString(), titulo);
        }
    }

    public void update_compra_item(Connection conn, compra_item coit) {
        String titulo = "update_compra_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, coit.getC3creado_por());
            pst.setString(3, coit.getC4tipo_item());
            pst.setString(4, coit.getC5descripcion());
            pst.setDouble(5, coit.getC6cantidad());
            pst.setDouble(6, coit.getC7precio_compra());
            pst.setInt(7, coit.getC8fk_idproducto());
            pst.setInt(8, coit.getC9fk_idcompra());
            pst.setInt(9, coit.getC1idcompra_item());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + coit.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + coit.toString(), titulo);
        }
    }

    public void cargar_compra_item(Connection conn, compra_item coit, int idcompra_item) {
        String titulo = "Cargar_compra_item";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idcompra_item, titulo);
            if (rs.next()) {
                coit.setC1idcompra_item(rs.getInt(1));
                coit.setC2fecha_creado(rs.getString(2));
                coit.setC3creado_por(rs.getString(3));
                coit.setC4tipo_item(rs.getString(4));
                coit.setC5descripcion(rs.getString(5));
                coit.setC6cantidad(rs.getDouble(6));
                coit.setC7precio_compra(rs.getDouble(7));
                coit.setC8fk_idproducto(rs.getInt(8));
                coit.setC9fk_idcompra(rs.getInt(9));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + coit.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + coit.toString(), titulo);
        }
    }

    public void actualizar_tabla_compra_item(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_compra_item(tbltabla);
    }

    public void ancho_tabla_compra_item(JTable tbltabla) {
        int Ancho[] = {11, 11, 11, 11, 11, 11, 11, 11, 11};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void insertar_item_compra_de_tabla(Connection conn, JTable tblitem_producto, compra com) {
        String titulo = "insertar_item_compra_de_tabla";
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String idproducto = ((tblitem_producto.getModel().getValueAt(row, 0).toString()));
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String cantidad = ((tblitem_producto.getModel().getValueAt(row, 3).toString()));
            String precio_venta = ((tblitem_producto.getModel().getValueAt(row, 5).toString()));
            String precio_compra = ((tblitem_producto.getModel().getValueAt(row, 6).toString()));
            String tipo = ((tblitem_producto.getModel().getValueAt(row, 7).toString()));
            try {
                compra_item item = new compra_item();
                item.setC3creado_por(com.getC3creado_por());
                item.setC4tipo_item(tipo);
                item.setC5descripcion(descripcion);
                item.setC6cantidad(Double.parseDouble(cantidad));
                item.setC7precio_compra(Double.parseDouble(precio_compra));
                item.setC8fk_idproducto(Integer.parseInt(idproducto));
                item.setC9fk_idcompra(com.getC1idcompra());
                insertar_compra_item(conn, item);
            } catch (Exception e) {
                evemen.mensaje_error(e, titulo);
                break;
            }

        }
    }

    public void update_compra_item_ingresar_stock_producto(Connection conn, compra_item coit) {
        String titulo = "update_compra_item_ingresar_stock_producto";
        String sql = "update producto set stock_actual=(stock_actual+\n"
                + "(select sum(ci2.cantidad) as cant from compra_item ci2 \n"
                + "where ci2.fk_idproducto=ci1.fk_idproducto \n"
                + "and ci2.fk_idcompra=ci1.fk_idcompra)) \n"
                + "from compra_item ci1\n"
                + "where producto.idproducto=ci1.fk_idproducto \n"
                + "and ci1.fk_idcompra=?;";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, coit.getC9fk_idcompra());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql, titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    public void update_compra_item_tipo_item(Connection conn, compra_item coit) {
        String titulo = "update_compra_item_ingresar_stock_producto";
        String sql = "update compra_item  set tipo_item=? where fk_idcompra=?;";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, coit.getC4tipo_item());
            pst.setInt(2, coit.getC9fk_idcompra());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql, titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    public void actualizar_tabla_compra_item_creado(Connection conn, JTable tbltabla, int fk_idcompra) {
        String sql = "select ci.idcompra_item as idci, ci.descripcion,\n"
                + "TRIM(to_char(ci.precio_compra,'999G999G999')) as pcompra,"
                + "ci.cantidad as cant,\n"
                + "TRIM(to_char((ci.precio_compra*ci.cantidad),'999G999G999')) as subtotal,"
                + "ci.tipo_item  \n"
                + "from compra_item ci where ci.fk_idcompra=" + fk_idcompra
                + " order by 1 desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_compra_item_creado(tbltabla);
    }

    public void ancho_tabla_compra_item_creado(JTable tbltabla) {
        int Ancho[] = {5, 45, 10, 5, 10, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 2);
        evejt.alinear_derecha_columna(tbltabla, 4);
    }
}
