package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.usuario_formulario;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_usuario_formulario {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "USUARIO_FORMULARIO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "USUARIO_FORMULARIO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO usuario_formulario(idusuario_formulario,fecha_creado,creado_por,nombre) VALUES (?,?,?,?);";
    private String sql_update = "UPDATE usuario_formulario SET fecha_creado=?,creado_por=?,nombre=? WHERE idusuario_formulario=?;";
    private String sql_select = "SELECT idusuario_formulario as iduf,nombre as nom_formulario FROM usuario_formulario order by 2 asc;";
    private String sql_cargar = "SELECT idusuario_formulario,fecha_creado,creado_por,nombre FROM usuario_formulario WHERE idusuario_formulario=";

    public void insertar_usuario_formulario(Connection conn, usuario_formulario usfo) {
        usfo.setC1idusuario_formulario(eveconn.getInt_ultimoID_mas_uno(conn, usfo.getTb_usuario_formulario(), usfo.getId_idusuario_formulario()));
        String titulo = "insertar_usuario_formulario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, usfo.getC1idusuario_formulario());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, usfo.getC3creado_por());
            pst.setString(4, usfo.getC4nombre());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + usfo.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + usfo.toString(), titulo);
        }
    }

    public void update_usuario_formulario(Connection conn, usuario_formulario usfo) {
        String titulo = "update_usuario_formulario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, usfo.getC3creado_por());
            pst.setString(3, usfo.getC4nombre());
            pst.setInt(4, usfo.getC1idusuario_formulario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + usfo.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + usfo.toString(), titulo);
        }
    }

    public void cargar_usuario_formulario(Connection conn, usuario_formulario usfo, int idusuario_formulario) {
        String titulo = "Cargar_usuario_formulario";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idusuario_formulario, titulo);
            if (rs.next()) {
                usfo.setC1idusuario_formulario(rs.getInt(1));
                usfo.setC2fecha_creado(rs.getString(2));
                usfo.setC3creado_por(rs.getString(3));
                usfo.setC4nombre(rs.getString(4));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + usfo.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + usfo.toString(), titulo);
        }
    }

    public void actualizar_tabla_usuario_formulario(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_usuario_formulario(tbltabla);
    }

    public void ancho_tabla_usuario_formulario(JTable tbltabla) {
        int Ancho[] = {20,80};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
