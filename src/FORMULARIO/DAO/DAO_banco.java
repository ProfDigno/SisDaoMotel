package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.banco;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_banco {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "BANCO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "BANCO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO banco(idbanco,fecha_creado,creado_por,nombre) VALUES (?,?,?,?);";
    private String sql_update = "UPDATE banco SET fecha_creado=?,creado_por=?,nombre=? WHERE idbanco=?;";
    private String sql_select = "SELECT idbanco,nombre FROM banco order by 1 desc;";
    private String sql_cargar = "SELECT idbanco,fecha_creado,creado_por,nombre FROM banco WHERE idbanco=";

    public void insertar_banco(Connection conn, banco b) {
        b.setC1idbanco(eveconn.getInt_ultimoID_mas_uno(conn, b.getTb_banco(), b.getId_idbanco()));
        String titulo = "insertar_banco";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, b.getC1idbanco());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, b.getC3creado_por());
            pst.setString(4, b.getC4nombre());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + b.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + b.toString(), titulo);
        }
    }

    public void update_banco(Connection conn, banco b) {
        String titulo = "update_banco";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, b.getC3creado_por());
            pst.setString(3, b.getC4nombre());
            pst.setInt(4, b.getC1idbanco());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + b.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + b.toString(), titulo);
        }
    }

    public void cargar_banco(Connection conn, banco b, int idbanco) {
        String titulo = "Cargar_banco";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idbanco, titulo);
            if (rs.next()) {
                b.setC1idbanco(rs.getInt(1));
                b.setC2fecha_creado(rs.getString(2));
                b.setC3creado_por(rs.getString(3));
                b.setC4nombre(rs.getString(4));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + b.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + b.toString(), titulo);
        }
    }

    public void actualizar_tabla_banco(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_banco(tbltabla);
    }

    public void ancho_tabla_banco(JTable tbltabla) {
        int Ancho[] = {20,80};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
