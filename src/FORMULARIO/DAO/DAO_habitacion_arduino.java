package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.habitacion_arduino;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_habitacion_arduino {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "HABITACION_ARDUINO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "HABITACION_ARDUINO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO habitacion_arduino(idhabitacion_arduino,fecha_creado,creado_por,nombre,usb_puerto,usb_bps,fk_idhabitacion_mini_pc) VALUES (?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE habitacion_arduino SET fecha_creado=?,creado_por=?,nombre=?,usb_puerto=?,usb_bps=?,fk_idhabitacion_mini_pc=? WHERE idhabitacion_arduino=?;";
    private String sql_select = "SELECT idhabitacion_arduino as idar,nombre,usb_puerto as puerto,usb_bps as bps FROM habitacion_arduino order by 1 desc;";
    private String sql_cargar = "SELECT idhabitacion_arduino,fecha_creado,creado_por,nombre,usb_puerto,usb_bps,fk_idhabitacion_mini_pc FROM habitacion_arduino WHERE idhabitacion_arduino=";

    public void insertar_habitacion_arduino(Connection conn, habitacion_arduino haar) {
        haar.setC1idhabitacion_arduino(eveconn.getInt_ultimoID_mas_uno(conn, haar.getTb_habitacion_arduino(), haar.getId_idhabitacion_arduino()));
        String titulo = "insertar_habitacion_arduino";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, haar.getC1idhabitacion_arduino());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, haar.getC3creado_por());
            pst.setString(4, haar.getC4nombre());
            pst.setString(5, haar.getC5usb_puerto());
            pst.setInt(6, haar.getC6usb_bps());
            pst.setInt(7, haar.getC7fk_idhabitacion_mini_pc());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + haar.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + haar.toString(), titulo);
        }
    }

    public void update_habitacion_arduino(Connection conn, habitacion_arduino haar) {
        String titulo = "update_habitacion_arduino";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, haar.getC3creado_por());
            pst.setString(3, haar.getC4nombre());
            pst.setString(4, haar.getC5usb_puerto());
            pst.setInt(5, haar.getC6usb_bps());
            pst.setInt(6, haar.getC7fk_idhabitacion_mini_pc());
            pst.setInt(7, haar.getC1idhabitacion_arduino());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + haar.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + haar.toString(), titulo);
        }
    }

    public void cargar_habitacion_arduino(Connection conn, habitacion_arduino haar, int idhabitacion_arduino) {
        String titulo = "Cargar_habitacion_arduino";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idhabitacion_arduino, titulo);
            if (rs.next()) {
                haar.setC1idhabitacion_arduino(rs.getInt(1));
                haar.setC2fecha_creado(rs.getString(2));
                haar.setC3creado_por(rs.getString(3));
                haar.setC4nombre(rs.getString(4));
                haar.setC5usb_puerto(rs.getString(5));
                haar.setC6usb_bps(rs.getInt(6));
                haar.setC7fk_idhabitacion_mini_pc(rs.getInt(7));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + haar.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + haar.toString(), titulo);
        }
    }

    public void actualizar_tabla_habitacion_arduino(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_habitacion_arduino(tbltabla);
    }

    public void ancho_tabla_habitacion_arduino(JTable tbltabla) {
        int Ancho[] = {10, 55, 20, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
