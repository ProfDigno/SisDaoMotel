package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.persona_cargo;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_persona_cargo {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PERSONA_CARGO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PERSONA_CARGO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO persona_cargo(idpersona_cargo,fecha_creado,creado_por,nombre,activo) VALUES (?,?,?,?,?);";
    private String sql_update = "UPDATE persona_cargo SET fecha_creado=?,creado_por=?,nombre=?,activo=? WHERE idpersona_cargo=?;";
    private String sql_select = "SELECT idpersona_cargo as idpc,nombre as cargo,activo FROM persona_cargo order by 1 desc;";
    private String sql_cargar = "SELECT idpersona_cargo,fecha_creado,creado_por,nombre,activo FROM persona_cargo WHERE idpersona_cargo=";

    public void insertar_persona_cargo(Connection conn, persona_cargo peca) {
        peca.setC1idpersona_cargo(eveconn.getInt_ultimoID_mas_uno(conn, peca.getTb_persona_cargo(), peca.getId_idpersona_cargo()));
        String titulo = "insertar_persona_cargo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, peca.getC1idpersona_cargo());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, peca.getC3creado_por());
            pst.setString(4, peca.getC4nombre());
            pst.setBoolean(5, peca.getC5activo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + peca.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + peca.toString(), titulo);
        }
    }

    public void update_persona_cargo(Connection conn, persona_cargo peca) {
        String titulo = "update_persona_cargo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, peca.getC3creado_por());
            pst.setString(3, peca.getC4nombre());
            pst.setBoolean(4, peca.getC5activo());
            pst.setInt(5, peca.getC1idpersona_cargo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + peca.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + peca.toString(), titulo);
        }
    }

    public void cargar_persona_cargo(Connection conn, persona_cargo peca, int idpersona_cargo) {
        String titulo = "Cargar_persona_cargo";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idpersona_cargo, titulo);
            if (rs.next()) {
                peca.setC1idpersona_cargo(rs.getInt(1));
                peca.setC2fecha_creado(rs.getString(2));
                peca.setC3creado_por(rs.getString(3));
                peca.setC4nombre(rs.getString(4));
                peca.setC5activo(rs.getBoolean(5));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + peca.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + peca.toString(), titulo);
        }
    }

    public void actualizar_tabla_persona_cargo(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_persona_cargo(tbltabla);
    }

    public void ancho_tabla_persona_cargo(JTable tbltabla) {
        int Ancho[] = {10, 70, 20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
