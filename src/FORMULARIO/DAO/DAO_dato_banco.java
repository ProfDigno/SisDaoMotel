package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.dato_banco;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_dato_banco {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "DATO_BANCO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "DATO_BANCO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO dato_banco(iddato_banco,fecha_creado,creado_por,titular,documento,nro_cuenta,activo,es_guarani,es_dolar,fk_idbanco) VALUES (?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE dato_banco SET fecha_creado=?,creado_por=?,titular=?,documento=?,nro_cuenta=?,activo=?,es_guarani=?,es_dolar=?,fk_idbanco=? WHERE iddato_banco=?;";
    private String sql_select = "SELECT db.iddato_banco as iddb,b.nombre as banco,db.titular,db.nro_cuenta,db.activo "
            + "FROM dato_banco db,banco b \n"
            + "where db.fk_idbanco=b.idbanco \n"
            + "order by 1 desc;";
    private String sql_cargar = "SELECT iddato_banco,fecha_creado,creado_por,titular,documento,nro_cuenta,activo,es_guarani,es_dolar,fk_idbanco FROM dato_banco WHERE iddato_banco=";

    public void insertar_dato_banco(Connection conn, dato_banco db) {
        db.setC1iddato_banco(eveconn.getInt_ultimoID_mas_uno(conn, db.getTb_dato_banco(), db.getId_iddato_banco()));
        String titulo = "insertar_dato_banco";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, db.getC1iddato_banco());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, db.getC3creado_por());
            pst.setString(4, db.getC4titular());
            pst.setString(5, db.getC5documento());
            pst.setString(6, db.getC6nro_cuenta());
            pst.setBoolean(7, db.getC7activo());
            pst.setBoolean(8, db.getC8es_guarani());
            pst.setBoolean(9, db.getC9es_dolar());
            pst.setInt(10, db.getC10fk_idbanco());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + db.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + db.toString(), titulo);
        }
    }

    public void update_dato_banco(Connection conn, dato_banco db) {
        String titulo = "update_dato_banco";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, db.getC3creado_por());
            pst.setString(3, db.getC4titular());
            pst.setString(4, db.getC5documento());
            pst.setString(5, db.getC6nro_cuenta());
            pst.setBoolean(6, db.getC7activo());
            pst.setBoolean(7, db.getC8es_guarani());
            pst.setBoolean(8, db.getC9es_dolar());
            pst.setInt(9, db.getC10fk_idbanco());
            pst.setInt(10, db.getC1iddato_banco());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + db.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + db.toString(), titulo);
        }
    }

    public void cargar_dato_banco(Connection conn, dato_banco db, int iddato_banco) {
        String titulo = "Cargar_dato_banco";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + iddato_banco, titulo);
            if (rs.next()) {
                db.setC1iddato_banco(rs.getInt(1));
                db.setC2fecha_creado(rs.getString(2));
                db.setC3creado_por(rs.getString(3));
                db.setC4titular(rs.getString(4));
                db.setC5documento(rs.getString(5));
                db.setC6nro_cuenta(rs.getString(6));
                db.setC7activo(rs.getBoolean(7));
                db.setC8es_guarani(rs.getBoolean(8));
                db.setC9es_dolar(rs.getBoolean(9));
                db.setC10fk_idbanco(rs.getInt(10));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + db.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + db.toString(), titulo);
        }
    }

    public void actualizar_tabla_dato_banco(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_dato_banco(tbltabla);
    }

    public void ancho_tabla_dato_banco(JTable tbltabla) {
        int Ancho[] = {5, 15, 40, 30, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
