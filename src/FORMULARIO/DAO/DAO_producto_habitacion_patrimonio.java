package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.producto_habitacion_patrimonio;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_producto_habitacion_patrimonio {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PRODUCTO_HABITACION_PATRIMONIO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO_HABITACION_PATRIMONIO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO producto_habitacion_patrimonio(idproducto_habitacion_patrimonio,fecha_creado,creado_por,cantidad,descripcion,precio_compra,activo,fecha_ingreso,fecha_salida,motivo_salida,fk_idproducto,fk_idhabitacion_dato) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE producto_habitacion_patrimonio SET fecha_creado=?,creado_por=?,cantidad=?,descripcion=?,precio_compra=?,activo=?,fecha_ingreso=?,fecha_salida=?,motivo_salida=?,fk_idproducto=?,fk_idhabitacion_dato=? WHERE idproducto_habitacion_patrimonio=?;";
    private String sql_select = "SELECT idproducto_habitacion_patrimonio,fecha_creado,creado_por,cantidad,descripcion,precio_compra,activo,fecha_ingreso,fecha_salida,motivo_salida,fk_idproducto,fk_idhabitacion_dato FROM producto_habitacion_patrimonio order by 1 desc;";
    private String sql_cargar = "SELECT idproducto_habitacion_patrimonio,fecha_creado,creado_por,cantidad,descripcion,precio_compra,activo,fecha_ingreso,fecha_salida,motivo_salida,fk_idproducto,fk_idhabitacion_dato FROM producto_habitacion_patrimonio WHERE idproducto_habitacion_patrimonio=";

    public void insertar_producto_habitacion_patrimonio(Connection conn, producto_habitacion_patrimonio prhapa) {
        prhapa.setC1idproducto_habitacion_patrimonio(eveconn.getInt_ultimoID_mas_uno(conn, prhapa.getTb_producto_habitacion_patrimonio(), prhapa.getId_idproducto_habitacion_patrimonio()));
        String titulo = "insertar_producto_habitacion_patrimonio";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, prhapa.getC1idproducto_habitacion_patrimonio());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, prhapa.getC3creado_por());
            pst.setDouble(4, prhapa.getC4cantidad());
            pst.setString(5, prhapa.getC5descripcion());
            pst.setDouble(6, prhapa.getC6precio_compra());
            pst.setBoolean(7, prhapa.getC7activo());
            pst.setDate(8, evefec.getDate_fecha_cargado(prhapa.getC8fecha_ingreso()));
            pst.setDate(9, evefec.getDateSQL_sistema());
            pst.setString(10, prhapa.getC10motivo_salida());
            pst.setInt(11, prhapa.getC11fk_idproducto());
            pst.setInt(12, prhapa.getC12fk_idhabitacion_dato());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + prhapa.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + prhapa.toString(), titulo);
        }
    }

    public void update_producto_habitacion_patrimonio(Connection conn, producto_habitacion_patrimonio prhapa) {
        String titulo = "update_producto_habitacion_patrimonio";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, prhapa.getC3creado_por());
            pst.setDouble(3, prhapa.getC4cantidad());
            pst.setString(4, prhapa.getC5descripcion());
            pst.setDouble(5, prhapa.getC6precio_compra());
            pst.setBoolean(6, prhapa.getC7activo());
            pst.setDate(7, evefec.getDate_fecha_cargado(prhapa.getC8fecha_ingreso()));
            pst.setDate(8, evefec.getDate_fecha_cargado(prhapa.getC9fecha_salida()));
            pst.setString(9, prhapa.getC10motivo_salida());
            pst.setInt(10, prhapa.getC11fk_idproducto());
            pst.setInt(11, prhapa.getC12fk_idhabitacion_dato());
            pst.setInt(12, prhapa.getC1idproducto_habitacion_patrimonio());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + prhapa.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + prhapa.toString(), titulo);
        }
    }

    public void cargar_producto_habitacion_patrimonio(Connection conn, producto_habitacion_patrimonio prhapa, int idproducto_habitacion_patrimonio) {
        String titulo = "Cargar_producto_habitacion_patrimonio";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idproducto_habitacion_patrimonio, titulo);
            if (rs.next()) {
                prhapa.setC1idproducto_habitacion_patrimonio(rs.getInt(1));
                prhapa.setC2fecha_creado(rs.getString(2));
                prhapa.setC3creado_por(rs.getString(3));
                prhapa.setC4cantidad(rs.getDouble(4));
                prhapa.setC5descripcion(rs.getString(5));
                prhapa.setC6precio_compra(rs.getDouble(6));
                prhapa.setC7activo(rs.getBoolean(7));
                prhapa.setC8fecha_ingreso(rs.getString(8));
                prhapa.setC9fecha_salida(rs.getString(9));
                prhapa.setC10motivo_salida(rs.getString(10));
                prhapa.setC11fk_idproducto(rs.getInt(11));
                prhapa.setC12fk_idhabitacion_dato(rs.getInt(12));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + prhapa.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + prhapa.toString(), titulo);
        }
    }

    public void actualizar_tabla_producto_habitacion_patrimonio(Connection conn, JTable tbltabla, int fk_idhabitacion_dato) {
        String sql = "select idproducto_habitacion_patrimonio as id,descripcion,\n"
                + "cantidad as cant,precio_compra as precioc,(cantidad*precio_compra) as subtotal,"
                + "to_char(fecha_ingreso,'yyyy-MM-dd') as ingreso\n"
                + "from producto_habitacion_patrimonio\n"
                + "where activo=true\n"
                + "and fk_idhabitacion_dato="+fk_idhabitacion_dato;
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_producto_habitacion_patrimonio(tbltabla);
    }

    public void ancho_tabla_producto_habitacion_patrimonio(JTable tbltabla) {
        int Ancho[] = {5, 55,5, 10, 10, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 2);
        evejt.alinear_derecha_columna(tbltabla, 3);
        evejt.alinear_derecha_columna(tbltabla, 4);
    }
}
