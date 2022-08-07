package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.habitacion_mini_pc;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_habitacion_mini_pc {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "HABITACION_MINI_PC GUARDADO CORRECTAMENTE";
    private String mensaje_update = "HABITACION_MINI_PC MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO habitacion_mini_pc(idhabitacion_mini_pc,fecha_creado,creado_por,placa_nombre,placa_ip,placa_ubicacion,ssh_usuario,ssh_password) VALUES (?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE habitacion_mini_pc SET fecha_creado=?,creado_por=?,placa_nombre=?,placa_ip=?,placa_ubicacion=?,ssh_usuario=?,ssh_password=? WHERE idhabitacion_mini_pc=?;";
    private String sql_select = "SELECT idhabitacion_mini_pc as idpc,placa_nombre,placa_ip FROM habitacion_mini_pc order by 1 desc;";
    private String sql_cargar = "SELECT idhabitacion_mini_pc,fecha_creado,creado_por,placa_nombre,placa_ip,placa_ubicacion,ssh_usuario,ssh_password FROM habitacion_mini_pc WHERE idhabitacion_mini_pc=";

    public void insertar_habitacion_mini_pc(Connection conn, habitacion_mini_pc hamipc) {
        hamipc.setC1idhabitacion_mini_pc(eveconn.getInt_ultimoID_mas_uno(conn, hamipc.getTb_habitacion_mini_pc(), hamipc.getId_idhabitacion_mini_pc()));
        String titulo = "insertar_habitacion_mini_pc";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, hamipc.getC1idhabitacion_mini_pc());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, hamipc.getC3creado_por());
            pst.setString(4, hamipc.getC4placa_nombre());
            pst.setString(5, hamipc.getC5placa_ip());
            pst.setString(6, hamipc.getC6placa_ubicacion());
            pst.setString(7, hamipc.getC7ssh_usuario());
            pst.setString(8, hamipc.getC8ssh_password());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + hamipc.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + hamipc.toString(), titulo);
        }
    }

    public void update_habitacion_mini_pc(Connection conn, habitacion_mini_pc hamipc) {
        String titulo = "update_habitacion_mini_pc";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, hamipc.getC3creado_por());
            pst.setString(3, hamipc.getC4placa_nombre());
            pst.setString(4, hamipc.getC5placa_ip());
            pst.setString(5, hamipc.getC6placa_ubicacion());
            pst.setString(6, hamipc.getC7ssh_usuario());
            pst.setString(7, hamipc.getC8ssh_password());
            pst.setInt(8, hamipc.getC1idhabitacion_mini_pc());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + hamipc.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + hamipc.toString(), titulo);
        }
    }

    public void cargar_habitacion_mini_pc(Connection conn, habitacion_mini_pc hamipc, int idhabitacion_mini_pc) {
        String titulo = "Cargar_habitacion_mini_pc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idhabitacion_mini_pc, titulo);
            if (rs.next()) {
                hamipc.setC1idhabitacion_mini_pc(rs.getInt(1));
                hamipc.setC2fecha_creado(rs.getString(2));
                hamipc.setC3creado_por(rs.getString(3));
                hamipc.setC4placa_nombre(rs.getString(4));
                hamipc.setC5placa_ip(rs.getString(5));
                hamipc.setC6placa_ubicacion(rs.getString(6));
                hamipc.setC7ssh_usuario(rs.getString(7));
                hamipc.setC8ssh_password(rs.getString(8));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + hamipc.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + hamipc.toString(), titulo);
        }
    }

    public void actualizar_tabla_habitacion_mini_pc(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_habitacion_mini_pc(tbltabla);
    }

    public void ancho_tabla_habitacion_mini_pc(JTable tbltabla) {
        int Ancho[] = {10, 50, 40};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
