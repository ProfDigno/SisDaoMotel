package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.habitacion_sensor;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_habitacion_sensor {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "HABITACION_SENSOR GUARDADO CORRECTAMENTE";
    private String mensaje_update = "HABITACION_SENSOR MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO habitacion_sensor(idhabitacion_sensor,fecha_creado,creado_por,nombre,es_digital_entrada,es_digital_salida,activo) VALUES (?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE habitacion_sensor SET fecha_creado=?,creado_por=?,nombre=?,es_digital_entrada=?,es_digital_salida=?,activo=? WHERE idhabitacion_sensor=?;";
    private String sql_select = "SELECT idhabitacion_sensor as ids,nombre,es_digital_entrada as entrada,es_digital_salida as salida,activo "
            + "FROM habitacion_sensor order by 1 desc;";
    private String sql_cargar = "SELECT idhabitacion_sensor,fecha_creado,creado_por,nombre,es_digital_entrada,es_digital_salida,activo FROM habitacion_sensor WHERE idhabitacion_sensor=";

    public void insertar_habitacion_sensor(Connection conn, habitacion_sensor hase) {
        hase.setC1idhabitacion_sensor(eveconn.getInt_ultimoID_mas_uno(conn, hase.getTb_habitacion_sensor(), hase.getId_idhabitacion_sensor()));
        String titulo = "insertar_habitacion_sensor";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, hase.getC1idhabitacion_sensor());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, hase.getC3creado_por());
            pst.setString(4, hase.getC4nombre());
            pst.setBoolean(5, hase.getC5es_digital_entrada());
            pst.setBoolean(6, hase.getC6es_digital_salida());
            pst.setBoolean(7, hase.getC7activo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + hase.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + hase.toString(), titulo);
        }
    }

    public void update_habitacion_sensor(Connection conn, habitacion_sensor hase) {
        String titulo = "update_habitacion_sensor";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, hase.getC3creado_por());
            pst.setString(3, hase.getC4nombre());
            pst.setBoolean(4, hase.getC5es_digital_entrada());
            pst.setBoolean(5, hase.getC6es_digital_salida());
            pst.setBoolean(6, hase.getC7activo());
            pst.setInt(7, hase.getC1idhabitacion_sensor());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + hase.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + hase.toString(), titulo);
        }
    }

    public void cargar_habitacion_sensor(Connection conn, habitacion_sensor hase, int idhabitacion_sensor) {
        String titulo = "Cargar_habitacion_sensor";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idhabitacion_sensor, titulo);
            if (rs.next()) {
                hase.setC1idhabitacion_sensor(rs.getInt(1));
                hase.setC2fecha_creado(rs.getString(2));
                hase.setC3creado_por(rs.getString(3));
                hase.setC4nombre(rs.getString(4));
                hase.setC5es_digital_entrada(rs.getBoolean(5));
                hase.setC6es_digital_salida(rs.getBoolean(6));
                hase.setC7activo(rs.getBoolean(7));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + hase.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + hase.toString(), titulo);
        }
    }

    public void actualizar_tabla_habitacion_sensor(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_habitacion_sensor(tbltabla);
    }

    public void ancho_tabla_habitacion_sensor(JTable tbltabla) {
        int Ancho[] = {10, 60, 10, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
