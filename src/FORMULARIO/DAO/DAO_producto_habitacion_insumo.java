package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.producto_habitacion_insumo;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_producto_habitacion_insumo {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PRODUCTO_HABITACION_INSUMO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO_HABITACION_INSUMO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO producto_habitacion_insumo(idproducto_habitacion_insumo,fecha_creado,creado_por,cantidad,descripcion,precio_venta,precio_compra,activo,fk_idproducto,fk_idhabitacion_dato) VALUES (?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE producto_habitacion_insumo SET fecha_creado=?,creado_por=?,cantidad=?,descripcion=?,precio_venta=?,precio_compra=?,activo=?,fk_idproducto=?,fk_idhabitacion_dato=? WHERE idproducto_habitacion_insumo=?;";
    private String sql_select = "SELECT idproducto_habitacion_insumo,fecha_creado,creado_por,cantidad,descripcion,precio_venta,precio_compra,activo,fk_idproducto,fk_idhabitacion_dato FROM producto_habitacion_insumo order by 1 desc;";
    private String sql_cargar = "SELECT idproducto_habitacion_insumo,fecha_creado,creado_por,cantidad,descripcion,precio_venta,precio_compra,activo,fk_idproducto,fk_idhabitacion_dato FROM producto_habitacion_insumo WHERE idproducto_habitacion_insumo=";

    public void insertar_producto_habitacion_insumo(Connection conn, producto_habitacion_insumo prhain) {
        prhain.setC1idproducto_habitacion_insumo(eveconn.getInt_ultimoID_mas_uno(conn, prhain.getTb_producto_habitacion_insumo(), prhain.getId_idproducto_habitacion_insumo()));
        String titulo = "insertar_producto_habitacion_insumo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, prhain.getC1idproducto_habitacion_insumo());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, prhain.getC3creado_por());
            pst.setDouble(4, prhain.getC4cantidad());
            pst.setString(5, prhain.getC5descripcion());
            pst.setDouble(6, prhain.getC6precio_venta());
            pst.setDouble(7, prhain.getC7precio_compra());
            pst.setBoolean(8, prhain.getC8activo());
            pst.setInt(9, prhain.getC9fk_idproducto());
            pst.setInt(10, prhain.getC10fk_idhabitacion_dato());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + prhain.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + prhain.toString(), titulo);
        }
    }

    public void update_producto_habitacion_insumo(Connection conn, producto_habitacion_insumo prhain) {
        String titulo = "update_producto_habitacion_insumo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, prhain.getC3creado_por());
            pst.setDouble(3, prhain.getC4cantidad());
            pst.setString(4, prhain.getC5descripcion());
            pst.setDouble(5, prhain.getC6precio_venta());
            pst.setDouble(6, prhain.getC7precio_compra());
            pst.setBoolean(7, prhain.getC8activo());
            pst.setInt(8, prhain.getC9fk_idproducto());
            pst.setInt(9, prhain.getC10fk_idhabitacion_dato());
            pst.setInt(10, prhain.getC1idproducto_habitacion_insumo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + prhain.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + prhain.toString(), titulo);
        }
    }

    public void cargar_producto_habitacion_insumo(Connection conn, producto_habitacion_insumo prhain, int idproducto_habitacion_insumo) {
        String titulo = "Cargar_producto_habitacion_insumo";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idproducto_habitacion_insumo, titulo);
            if (rs.next()) {
                prhain.setC1idproducto_habitacion_insumo(rs.getInt(1));
                prhain.setC2fecha_creado(rs.getString(2));
                prhain.setC3creado_por(rs.getString(3));
                prhain.setC4cantidad(rs.getDouble(4));
                prhain.setC5descripcion(rs.getString(5));
                prhain.setC6precio_venta(rs.getDouble(6));
                prhain.setC7precio_compra(rs.getDouble(7));
                prhain.setC8activo(rs.getBoolean(8));
                prhain.setC9fk_idproducto(rs.getInt(9));
                prhain.setC10fk_idhabitacion_dato(rs.getInt(10));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + prhain.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + prhain.toString(), titulo);
        }
    }

    public void actualizar_tabla_producto_habitacion_insumo(Connection conn, JTable tbltabla,int fk_idhabitacion_dato) {
        String sql = "select idproducto_habitacion_insumo as id,descripcion,\n"
                + "cantidad as cant,precio_venta as preciov,(cantidad*precio_venta) as subtotal\n"
                + "from producto_habitacion_insumo\n"
                + "where activo=true\n"
                + "and fk_idhabitacion_dato="+fk_idhabitacion_dato;
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_producto_habitacion_insumo(tbltabla);
    }

    public void ancho_tabla_producto_habitacion_insumo(JTable tbltabla) {
        int Ancho[] = {10, 50, 10, 15, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 2);
        evejt.alinear_derecha_columna(tbltabla, 3);
        evejt.alinear_derecha_columna(tbltabla, 4);
    }
}
