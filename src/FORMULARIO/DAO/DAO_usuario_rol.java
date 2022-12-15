package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.usuario_rol;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_usuario_rol {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "USUARIO_ROL GUARDADO CORRECTAMENTE";
    private String mensaje_update = "USUARIO_ROL MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO usuario_rol(idusuario_rol,fecha_creado,creado_por,nombre,descripcion) VALUES (?,?,?,?,?);";
    private String sql_update = "UPDATE usuario_rol SET fecha_creado=?,creado_por=?,nombre=?,descripcion=? WHERE idusuario_rol=?;";
    private String sql_select = "SELECT idusuario_rol as idur,nombre,descripcion FROM usuario_rol order by 1 desc;";
    private String sql_cargar = "SELECT idusuario_rol,fecha_creado,creado_por,nombre,descripcion FROM usuario_rol WHERE idusuario_rol=";

    public void insertar_usuario_rol(Connection conn, usuario_rol usro) {
        usro.setC1idusuario_rol(eveconn.getInt_ultimoID_mas_uno(conn, usro.getTb_usuario_rol(), usro.getId_idusuario_rol()));
        String titulo = "insertar_usuario_rol";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, usro.getC1idusuario_rol());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, usro.getC3creado_por());
            pst.setString(4, usro.getC4nombre());
            pst.setString(5, usro.getC5descripcion());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + usro.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + usro.toString(), titulo);
        }
    }

    public void update_usuario_rol(Connection conn, usuario_rol usro) {
        String titulo = "update_usuario_rol";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, usro.getC3creado_por());
            pst.setString(3, usro.getC4nombre());
            pst.setString(4, usro.getC5descripcion());
            pst.setInt(5, usro.getC1idusuario_rol());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + usro.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + usro.toString(), titulo);
        }
    }

    public void cargar_usuario_rol(Connection conn, usuario_rol usro, int idusuario_rol) {
        String titulo = "Cargar_usuario_rol";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idusuario_rol, titulo);
            if (rs.next()) {
                usro.setC1idusuario_rol(rs.getInt(1));
                usro.setC2fecha_creado(rs.getString(2));
                usro.setC3creado_por(rs.getString(3));
                usro.setC4nombre(rs.getString(4));
                usro.setC5descripcion(rs.getString(5));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + usro.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + usro.toString(), titulo);
        }
    }

    public void actualizar_tabla_usuario_rol(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_usuario_rol(tbltabla);
    }

    public void ancho_tabla_usuario_rol(JTable tbltabla) {
        int Ancho[] = {10, 40, 50};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
