package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.usuario_tipo_evento;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_usuario_tipo_evento {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "USUARIO_TIPO_EVENTO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "USUARIO_TIPO_EVENTO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO usuario_tipo_evento(idusuario_tipo_evento,fecha_creado,creado_por,nombre) VALUES (?,?,?,?);";
    private String sql_update = "UPDATE usuario_tipo_evento SET fecha_creado=?,creado_por=?,nombre=? WHERE idusuario_tipo_evento=?;";
    private String sql_select = "SELECT idusuario_tipo_evento as idute,nombre nom_tipo_evento FROM usuario_tipo_evento order by 1 desc;";
    private String sql_cargar = "SELECT idusuario_tipo_evento,fecha_creado,creado_por,nombre FROM usuario_tipo_evento WHERE idusuario_tipo_evento=";

    public void insertar_usuario_tipo_evento(Connection conn, usuario_tipo_evento ustiev) {
        ustiev.setC1idusuario_tipo_evento(eveconn.getInt_ultimoID_mas_uno(conn, ustiev.getTb_usuario_tipo_evento(), ustiev.getId_idusuario_tipo_evento()));
        String titulo = "insertar_usuario_tipo_evento";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ustiev.getC1idusuario_tipo_evento());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ustiev.getC3creado_por());
            pst.setString(4, ustiev.getC4nombre());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ustiev.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ustiev.toString(), titulo);
        }
    }

    public void update_usuario_tipo_evento(Connection conn, usuario_tipo_evento ustiev) {
        String titulo = "update_usuario_tipo_evento";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, ustiev.getC3creado_por());
            pst.setString(3, ustiev.getC4nombre());
            pst.setInt(4, ustiev.getC1idusuario_tipo_evento());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ustiev.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ustiev.toString(), titulo);
        }
    }

    public void cargar_usuario_tipo_evento(Connection conn, usuario_tipo_evento ustiev, int idusuario_tipo_evento) {
        String titulo = "Cargar_usuario_tipo_evento";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idusuario_tipo_evento, titulo);
            if (rs.next()) {
                ustiev.setC1idusuario_tipo_evento(rs.getInt(1));
                ustiev.setC2fecha_creado(rs.getString(2));
                ustiev.setC3creado_por(rs.getString(3));
                ustiev.setC4nombre(rs.getString(4));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ustiev.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ustiev.toString(), titulo);
        }
    }

    public void actualizar_tabla_usuario_tipo_evento(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_usuario_tipo_evento(tbltabla);
    }

    public void ancho_tabla_usuario_tipo_evento(JTable tbltabla) {
        int Ancho[] = {20,80};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
