package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.producto_habitacion_frigobar;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_producto_habitacion_frigobar {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PRODUCTO_HABITACION_FRIGOBAR GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO_HABITACION_FRIGOBAR MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO producto_habitacion_frigobar(idproducto_habitacion_frigobar,fecha_creado,creado_por,cantidad,descripcion,precio_venta,precio_compra,activo,fk_idhabitacion_dato,fk_idproducto) VALUES (?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE producto_habitacion_frigobar SET fecha_creado=?,creado_por=?,cantidad=?,descripcion=?,precio_venta=?,precio_compra=?,activo=?,fk_idhabitacion_dato=?,fk_idproducto=? WHERE idproducto_habitacion_frigobar=?;";
    private String sql_select = "SELECT idproducto_habitacion_frigobar,fecha_creado,creado_por,cantidad,descripcion,precio_venta,precio_compra,activo,fk_idhabitacion_dato,fk_idproducto FROM producto_habitacion_frigobar order by 1 desc;";
    private String sql_cargar = "SELECT idproducto_habitacion_frigobar,fecha_creado,creado_por,cantidad,descripcion,precio_venta,precio_compra,activo,fk_idhabitacion_dato,fk_idproducto FROM producto_habitacion_frigobar WHERE idproducto_habitacion_frigobar=";

    public void insertar_producto_habitacion_frigobar(Connection conn, producto_habitacion_frigobar prhafr) {
        prhafr.setC1idproducto_habitacion_frigobar(eveconn.getInt_ultimoID_mas_uno(conn, prhafr.getTb_producto_habitacion_frigobar(), prhafr.getId_idproducto_habitacion_frigobar()));
        String titulo = "insertar_producto_habitacion_frigobar";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, prhafr.getC1idproducto_habitacion_frigobar());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, prhafr.getC3creado_por());
            pst.setDouble(4, prhafr.getC4cantidad());
            pst.setString(5, prhafr.getC5descripcion());
            pst.setDouble(6, prhafr.getC6precio_venta());
            pst.setDouble(7, prhafr.getC7precio_compra());
            pst.setBoolean(8, prhafr.getC8activo());
            pst.setInt(9, prhafr.getC9fk_idhabitacion_dato());
            pst.setInt(10, prhafr.getC10fk_idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + prhafr.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + prhafr.toString(), titulo);
        }
    }

    public void update_producto_habitacion_frigobar(Connection conn, producto_habitacion_frigobar prhafr) {
        String titulo = "update_producto_habitacion_frigobar";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, prhafr.getC3creado_por());
            pst.setDouble(3, prhafr.getC4cantidad());
            pst.setString(4, prhafr.getC5descripcion());
            pst.setDouble(5, prhafr.getC6precio_venta());
            pst.setDouble(6, prhafr.getC7precio_compra());
            pst.setBoolean(7, prhafr.getC8activo());
            pst.setInt(8, prhafr.getC9fk_idhabitacion_dato());
            pst.setInt(9, prhafr.getC10fk_idproducto());
            pst.setInt(10, prhafr.getC1idproducto_habitacion_frigobar());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + prhafr.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + prhafr.toString(), titulo);
        }
    }

    public void cargar_producto_habitacion_frigobar(Connection conn, producto_habitacion_frigobar prhafr, int idproducto_habitacion_frigobar) {
        String titulo = "Cargar_producto_habitacion_frigobar";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idproducto_habitacion_frigobar, titulo);
            if (rs.next()) {
                prhafr.setC1idproducto_habitacion_frigobar(rs.getInt(1));
                prhafr.setC2fecha_creado(rs.getString(2));
                prhafr.setC3creado_por(rs.getString(3));
                prhafr.setC4cantidad(rs.getDouble(4));
                prhafr.setC5descripcion(rs.getString(5));
                prhafr.setC6precio_venta(rs.getDouble(6));
                prhafr.setC7precio_compra(rs.getDouble(7));
                prhafr.setC8activo(rs.getBoolean(8));
                prhafr.setC9fk_idhabitacion_dato(rs.getInt(9));
                prhafr.setC10fk_idproducto(rs.getInt(10));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + prhafr.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + prhafr.toString(), titulo);
        }
    }

    public void actualizar_tabla_producto_habitacion_frigobar(Connection conn, JTable tbltabla,int fk_idhabitacion_dato) {
        String sql = "select idproducto_habitacion_frigobar as id,descripcion,\n"
                + "cantidad as cant,precio_venta as preciov,(cantidad*precio_venta) as subtotal\n"
                + "from producto_habitacion_frigobar\n"
                + "where activo=true\n"
                + "and fk_idhabitacion_dato="+fk_idhabitacion_dato;
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_producto_habitacion_frigobar(tbltabla);
    }

    public void ancho_tabla_producto_habitacion_frigobar(JTable tbltabla) {
        int Ancho[] = {10, 50, 10, 15, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 2);
        evejt.alinear_derecha_columna(tbltabla, 3);
        evejt.alinear_derecha_columna(tbltabla, 4);
    }
}
