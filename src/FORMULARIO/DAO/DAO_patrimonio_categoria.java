package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.patrimonio_categoria;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DAO_patrimonio_categoria {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PATRIMONIO_CATEGORIA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PATRIMONIO_CATEGORIA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO patrimonio_categoria(idpatrimonio_categoria,fecha_creado,creado_por,nombre,activo) VALUES (?,?,?,?,?);";
    private String sql_update = "UPDATE patrimonio_categoria SET fecha_creado=?,creado_por=?,nombre=?,activo=? WHERE idpatrimonio_categoria=?;";
    private String sql_select = "SELECT idpatrimonio_categoria as idpc,nombre as categoria,activo FROM patrimonio_categoria order by 1 desc;";
    private String sql_cargar = "SELECT idpatrimonio_categoria,fecha_creado,creado_por,nombre,activo FROM patrimonio_categoria WHERE idpatrimonio_categoria=";

    public void insertar_patrimonio_categoria(Connection conn, patrimonio_categoria ENTpc) {
        ENTpc.setC1idpatrimonio_categoria(eveconn.getInt_ultimoID_mas_uno(conn, ENTpc.getTb_patrimonio_categoria(), ENTpc.getId_idpatrimonio_categoria()));
        String titulo = "insertar_patrimonio_categoria";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ENTpc.getC1idpatrimonio_categoria());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ENTpc.getC3creado_por());
            pst.setString(4, ENTpc.getC4nombre());
            pst.setBoolean(5, ENTpc.getC5activo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ENTpc.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ENTpc.toString(), titulo);
        }
    }

    public void update_patrimonio_categoria(Connection conn, patrimonio_categoria ENTpc) {
        String titulo = "update_patrimonio_categoria";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, ENTpc.getC3creado_por());
            pst.setString(3, ENTpc.getC4nombre());
            pst.setBoolean(4, ENTpc.getC5activo());
            pst.setInt(5, ENTpc.getC1idpatrimonio_categoria());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ENTpc.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ENTpc.toString(), titulo);
        }
    }

    public void cargar_patrimonio_categoria(Connection conn, patrimonio_categoria ENTpc, int idpatrimonio_categoria) {
        String titulo = "Cargar_patrimonio_categoria";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idpatrimonio_categoria, titulo);
            if (rs.next()) {
                ENTpc.setC1idpatrimonio_categoria(rs.getInt(1));
                ENTpc.setC2fecha_creado(rs.getString(2));
                ENTpc.setC3creado_por(rs.getString(3));
                ENTpc.setC4nombre(rs.getString(4));
                ENTpc.setC5activo(rs.getBoolean(5));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ENTpc.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ENTpc.toString(), titulo);
        }
    }

    public void actualizar_tabla_patrimonio_categoria(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_patrimonio_categoria(tbltabla);
    }

    public void ancho_tabla_patrimonio_categoria(JTable tbltabla) {
        int Ancho[] = {10, 70, 20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_tabla_patrimonio_categoria_buscar_pro(Connection conn, JTable tbltabla, JTextField txtcategoria) {
        if (txtcategoria.getText().trim().length() > 0) {
            String categoria = txtcategoria.getText();
            String sql = "SELECT idpatrimonio_categoria as idpc,nombre as categoria "
                    + "FROM patrimonio_categoria "
                    + "where activo=true "
                    + "and nombre ilike '%" + categoria + "%' "
                    + "order by 1 desc;";
            eveconn.Select_cargar_jtable(conn, sql, tbltabla);
            ancho_tabla_patrimonio_categoria_buscar_pro(tbltabla);
        }
    }

    public void ancho_tabla_patrimonio_categoria_buscar_pro(JTable tbltabla) {
        int Ancho[] = {10, 90};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.ocultar_columna(tbltabla, 0);
    }
}
