package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.patrimonio_baja_motivo;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_patrimonio_baja_motivo {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PATRIMONIO_BAJA_MOTIVO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PATRIMONIO_BAJA_MOTIVO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO patrimonio_baja_motivo(idpatrimonio_baja_motivo,fecha_creado,creado_por,nombre,activo) VALUES (?,?,?,?,?);";
    private String sql_update = "UPDATE patrimonio_baja_motivo SET fecha_creado=?,creado_por=?,nombre=?,activo=? WHERE idpatrimonio_baja_motivo=?;";
    private String sql_select = "SELECT idpatrimonio_baja_motivo as idpbm,nombre as motivo,activo FROM patrimonio_baja_motivo order by 1 desc;";
    private String sql_cargar = "SELECT idpatrimonio_baja_motivo,fecha_creado,creado_por,nombre,activo FROM patrimonio_baja_motivo WHERE idpatrimonio_baja_motivo=";

    public void insertar_patrimonio_baja_motivo(Connection conn, patrimonio_baja_motivo ENTpbm) {
        ENTpbm.setC1idpatrimonio_baja_motivo(eveconn.getInt_ultimoID_mas_uno(conn, ENTpbm.getTb_patrimonio_baja_motivo(), ENTpbm.getId_idpatrimonio_baja_motivo()));
        String titulo = "insertar_patrimonio_baja_motivo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ENTpbm.getC1idpatrimonio_baja_motivo());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ENTpbm.getC3creado_por());
            pst.setString(4, ENTpbm.getC4nombre());
            pst.setBoolean(5, ENTpbm.getC5activo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ENTpbm.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ENTpbm.toString(), titulo);
        }
    }

    public void update_patrimonio_baja_motivo(Connection conn, patrimonio_baja_motivo ENTpbm) {
        String titulo = "update_patrimonio_baja_motivo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, ENTpbm.getC3creado_por());
            pst.setString(3, ENTpbm.getC4nombre());
            pst.setBoolean(4, ENTpbm.getC5activo());
            pst.setInt(5, ENTpbm.getC1idpatrimonio_baja_motivo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ENTpbm.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ENTpbm.toString(), titulo);
        }
    }

    public void cargar_patrimonio_baja_motivo(Connection conn, patrimonio_baja_motivo ENTpbm, int idpatrimonio_baja_motivo) {
        String titulo = "Cargar_patrimonio_baja_motivo";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idpatrimonio_baja_motivo, titulo);
            if (rs.next()) {
                ENTpbm.setC1idpatrimonio_baja_motivo(rs.getInt(1));
                ENTpbm.setC2fecha_creado(rs.getString(2));
                ENTpbm.setC3creado_por(rs.getString(3));
                ENTpbm.setC4nombre(rs.getString(4));
                ENTpbm.setC5activo(rs.getBoolean(5));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ENTpbm.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ENTpbm.toString(), titulo);
        }
    }

    public void actualizar_tabla_patrimonio_baja_motivo(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_patrimonio_baja_motivo(tbltabla);
    }

    public void ancho_tabla_patrimonio_baja_motivo(JTable tbltabla) {
        int Ancho[] = {10, 70, 20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
