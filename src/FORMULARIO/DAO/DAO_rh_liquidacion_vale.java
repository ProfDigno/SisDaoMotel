package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.rh_liquidacion_vale;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_rh_liquidacion_vale {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "RH_LIQUIDACION_VALE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "RH_LIQUIDACION_VALE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO rh_liquidacion_vale(idrh_liquidacion_vale,fecha_creado,creado_por,fk_idrh_liquidacion,fk_idrh_vale) VALUES (?,?,?,?,?);";
    private String sql_update = "UPDATE rh_liquidacion_vale SET fecha_creado=?,creado_por=?,fk_idrh_liquidacion=?,fk_idrh_vale=? WHERE idrh_liquidacion_vale=?;";
    private String sql_select = "SELECT idrh_liquidacion_vale,fecha_creado,creado_por,fk_idrh_liquidacion,fk_idrh_vale FROM rh_liquidacion_vale order by 1 desc;";
    private String sql_cargar = "SELECT idrh_liquidacion_vale,fecha_creado,creado_por,fk_idrh_liquidacion,fk_idrh_vale FROM rh_liquidacion_vale WHERE idrh_liquidacion_vale=";

    public void insertar_rh_liquidacion_vale(Connection conn, rh_liquidacion_vale rhliva) {
        rhliva.setC1idrh_liquidacion_vale(eveconn.getInt_ultimoID_mas_uno(conn, rhliva.getTb_rh_liquidacion_vale(), rhliva.getId_idrh_liquidacion_vale()));
        String titulo = "insertar_rh_liquidacion_vale";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, rhliva.getC1idrh_liquidacion_vale());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, rhliva.getC3creado_por());
            pst.setInt(4, rhliva.getC4fk_idrh_liquidacion());
            pst.setInt(5, rhliva.getC5fk_idrh_vale());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + rhliva.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + rhliva.toString(), titulo);
        }
    }

    public void update_rh_liquidacion_vale(Connection conn, rh_liquidacion_vale rhliva) {
        String titulo = "update_rh_liquidacion_vale";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, rhliva.getC3creado_por());
            pst.setInt(3, rhliva.getC4fk_idrh_liquidacion());
            pst.setInt(4, rhliva.getC5fk_idrh_vale());
            pst.setInt(5, rhliva.getC1idrh_liquidacion_vale());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + rhliva.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + rhliva.toString(), titulo);
        }
    }

    public void cargar_rh_liquidacion_vale(Connection conn, rh_liquidacion_vale rhliva, int idrh_liquidacion_vale) {
        String titulo = "Cargar_rh_liquidacion_vale";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idrh_liquidacion_vale, titulo);
            if (rs.next()) {
                rhliva.setC1idrh_liquidacion_vale(rs.getInt(1));
                rhliva.setC2fecha_creado(rs.getString(2));
                rhliva.setC3creado_por(rs.getString(3));
                rhliva.setC4fk_idrh_liquidacion(rs.getInt(4));
                rhliva.setC5fk_idrh_vale(rs.getInt(5));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + rhliva.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + rhliva.toString(), titulo);
        }
    }

    public void actualizar_tabla_rh_liquidacion_vale(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_rh_liquidacion_vale(tbltabla);
    }

    public void ancho_tabla_rh_liquidacion_vale(JTable tbltabla) {
        int Ancho[] = {20, 20, 20, 20, 20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
