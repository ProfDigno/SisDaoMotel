package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.producto_unidad;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_producto_unidad {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PRODUCTO_UNIDAD GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO_UNIDAD MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO producto_unidad(idproducto_unidad,fecha_creado,creado_por,nombre,orden,activo) VALUES (?,?,?,?,?,?);";
    private String sql_update = "UPDATE producto_unidad SET fecha_creado=?,creado_por=?,nombre=?,orden=?,activo=? WHERE idproducto_unidad=?;";
    private String sql_select = "SELECT idproducto_unidad as idpu,nombre as unidad,orden,activo FROM producto_unidad order by 1 desc;";
    private String sql_cargar = "SELECT idproducto_unidad,fecha_creado,creado_por,nombre,orden,activo FROM producto_unidad WHERE idproducto_unidad=";

    public void insertar_producto_unidad(Connection conn, producto_unidad prun) {
        prun.setC1idproducto_unidad(eveconn.getInt_ultimoID_mas_uno(conn, prun.getTb_producto_unidad(), prun.getId_idproducto_unidad()));
        String titulo = "insertar_producto_unidad";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, prun.getC1idproducto_unidad());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, prun.getC3creado_por());
            pst.setString(4, prun.getC4nombre());
            pst.setInt(5, prun.getC5orden());
            pst.setBoolean(6, prun.getC6activo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + prun.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + prun.toString(), titulo);
        }
    }

    public void update_producto_unidad(Connection conn, producto_unidad prun) {
        String titulo = "update_producto_unidad";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, prun.getC3creado_por());
            pst.setString(3, prun.getC4nombre());
            pst.setInt(4, prun.getC5orden());
            pst.setBoolean(5, prun.getC6activo());
            pst.setInt(6, prun.getC1idproducto_unidad());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + prun.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + prun.toString(), titulo);
        }
    }

    public void cargar_producto_unidad(Connection conn, producto_unidad prun, int idproducto_unidad) {
        String titulo = "Cargar_producto_unidad";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idproducto_unidad, titulo);
            if (rs.next()) {
                prun.setC1idproducto_unidad(rs.getInt(1));
                prun.setC2fecha_creado(rs.getString(2));
                prun.setC3creado_por(rs.getString(3));
                prun.setC4nombre(rs.getString(4));
                prun.setC5orden(rs.getInt(5));
                prun.setC6activo(rs.getBoolean(6));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + prun.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + prun.toString(), titulo);
        }
    }

    public void actualizar_tabla_producto_unidad(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto_unidad(tbltabla);
    }

    public void ancho_tabla_producto_unidad(JTable tbltabla) {
        int Ancho[] = {10, 70, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
