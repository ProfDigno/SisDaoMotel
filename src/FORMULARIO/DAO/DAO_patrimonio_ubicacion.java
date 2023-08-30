package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.patrimonio_ubicacion;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DAO_patrimonio_ubicacion {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PATRIMONIO_UBICACION GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PATRIMONIO_UBICACION MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO patrimonio_ubicacion(idpatrimonio_ubicacion,fecha_creado,creado_por,nombre,activo) VALUES (?,?,?,?,?);";
    private String sql_update = "UPDATE patrimonio_ubicacion SET fecha_creado=?,creado_por=?,nombre=?,activo=? WHERE idpatrimonio_ubicacion=?;";
    private String sql_select = "SELECT idpatrimonio_ubicacion as idpu,nombre as ubicacion,activo FROM patrimonio_ubicacion order by 1 desc;";
    private String sql_cargar = "SELECT idpatrimonio_ubicacion,fecha_creado,creado_por,nombre,activo FROM patrimonio_ubicacion WHERE idpatrimonio_ubicacion=";

    public void insertar_patrimonio_ubicacion(Connection conn, patrimonio_ubicacion ENTpu) {
        ENTpu.setC1idpatrimonio_ubicacion(eveconn.getInt_ultimoID_mas_uno(conn, ENTpu.getTb_patrimonio_ubicacion(), ENTpu.getId_idpatrimonio_ubicacion()));
        String titulo = "insertar_patrimonio_ubicacion";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ENTpu.getC1idpatrimonio_ubicacion());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ENTpu.getC3creado_por());
            pst.setString(4, ENTpu.getC4nombre());
            pst.setBoolean(5, ENTpu.getC5activo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ENTpu.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ENTpu.toString(), titulo);
        }
    }

    public void update_patrimonio_ubicacion(Connection conn, patrimonio_ubicacion ENTpu) {
        String titulo = "update_patrimonio_ubicacion";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, ENTpu.getC3creado_por());
            pst.setString(3, ENTpu.getC4nombre());
            pst.setBoolean(4, ENTpu.getC5activo());
            pst.setInt(5, ENTpu.getC1idpatrimonio_ubicacion());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ENTpu.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ENTpu.toString(), titulo);
        }
    }

    public void cargar_patrimonio_ubicacion(Connection conn, patrimonio_ubicacion ENTpu, int idpatrimonio_ubicacion) {
        String titulo = "Cargar_patrimonio_ubicacion";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idpatrimonio_ubicacion, titulo);
            if (rs.next()) {
                ENTpu.setC1idpatrimonio_ubicacion(rs.getInt(1));
                ENTpu.setC2fecha_creado(rs.getString(2));
                ENTpu.setC3creado_por(rs.getString(3));
                ENTpu.setC4nombre(rs.getString(4));
                ENTpu.setC5activo(rs.getBoolean(5));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ENTpu.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ENTpu.toString(), titulo);
        }
    }

    public void actualizar_tabla_patrimonio_ubicacion(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_patrimonio_ubicacion(tbltabla);
    }

    public void ancho_tabla_patrimonio_ubicacion(JTable tbltabla) {
        int Ancho[] = {10, 70, 20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void actualizar_tabla_patrimonio_ubicacion_buscar_pro(Connection conn, JTable tbltabla, JTextField txtcategoria) {
        if (txtcategoria.getText().trim().length() > 0) {
            String categoria = txtcategoria.getText();
            String sql = "SELECT idpatrimonio_ubicacion as idpu,nombre as ubicacion "
                    + "FROM patrimonio_ubicacion "
                    + "where activo=true "
                    + "and nombre ilike '%" + categoria + "%' "
                    + "order by 1 desc;";
            eveconn.Select_cargar_jtable(conn, sql, tbltabla);
            ancho_tabla_patrimonio_ubicacion_buscar_pro(tbltabla);
        }
    }

    public void ancho_tabla_patrimonio_ubicacion_buscar_pro(JTable tbltabla) {
        int Ancho[] = {10, 90};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.ocultar_columna(tbltabla, 0);
    }
}
