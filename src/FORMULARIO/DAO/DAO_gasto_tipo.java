package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.gasto_tipo;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DAO_gasto_tipo {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "GASTO_TIPO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "GASTO_TIPO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO gasto_tipo(idgasto_tipo,fecha_creado,creado_por,nombre,activo) VALUES (?,?,?,?,?);";
    private String sql_update = "UPDATE gasto_tipo SET fecha_creado=?,creado_por=?,nombre=?,activo=? WHERE idgasto_tipo=?;";
    private String sql_select = "SELECT idgasto_tipo as id,nombre as tipo_gasto,activo "
            + "FROM gasto_tipo order by nombre desc;";
    private String sql_cargar = "SELECT idgasto_tipo,fecha_creado,creado_por,nombre,activo FROM gasto_tipo WHERE idgasto_tipo=";

    public void insertar_gasto_tipo(Connection conn, gasto_tipo gati) {
        gati.setC1idgasto_tipo(eveconn.getInt_ultimoID_mas_uno(conn, gati.getTb_gasto_tipo(), gati.getId_idgasto_tipo()));
        String titulo = "insertar_gasto_tipo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, gati.getC1idgasto_tipo());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, gati.getC3creado_por());
            pst.setString(4, gati.getC4nombre());
            pst.setBoolean(5, gati.getC5activo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + gati.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + gati.toString(), titulo);
        }
    }

    public void update_gasto_tipo(Connection conn, gasto_tipo gati) {
        String titulo = "update_gasto_tipo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, gati.getC3creado_por());
            pst.setString(3, gati.getC4nombre());
            pst.setBoolean(4, gati.getC5activo());
            pst.setInt(5, gati.getC1idgasto_tipo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + gati.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + gati.toString(), titulo);
        }
    }

    public void cargar_gasto_tipo(Connection conn, gasto_tipo gati, int idgasto_tipo) {
        String titulo = "Cargar_gasto_tipo";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idgasto_tipo, titulo);
            if (rs.next()) {
                gati.setC1idgasto_tipo(rs.getInt(1));
                gati.setC2fecha_creado(rs.getString(2));
                gati.setC3creado_por(rs.getString(3));
                gati.setC4nombre(rs.getString(4));
                gati.setC5activo(rs.getBoolean(5));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + gati.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + gati.toString(), titulo);
        }
    }

    public void actualizar_tabla_gasto_tipo(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_gasto_tipo(tbltabla);
    }

    public void actualizar_tabla_gasto_tipo_buscar(Connection conn, JTable tbltabla, JTextField txtbuscar) {
        if (txtbuscar.getText().trim().length() > 0) {
            String busca = txtbuscar.getText();
            String sql = "SELECT idgasto_tipo as id,nombre as tipo_gasto,activo "
                    + "FROM gasto_tipo "
                    + "where nombre ilike'%" + busca + "%' "
                    + "order by nombre desc;";
            eveconn.Select_cargar_jtable(conn, sql, tbltabla);
            ancho_tabla_gasto_tipo(tbltabla);
        }
    }

    public void ancho_tabla_gasto_tipo(JTable tbltabla) {
        int Ancho[] = {10, 70, 20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
