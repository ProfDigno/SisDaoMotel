package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import ESTADOS.EvenEstado;
import FORMULARIO.ENTIDAD.garantia;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_garantia {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private EvenEstado eveest = new EvenEstado();
    private String mensaje_insert = "GARANTIA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "GARANTIA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO garantia(idgarantia,fecha_creado,creado_por,fecha_inicio,fecha_fin,"
            + "responsable,descripcion_objeto,monto_garantia,estado,fk_idusuario,fk_idventa) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE garantia SET fecha_creado=?,creado_por=?,fecha_inicio=?,fecha_fin=?,responsable=?,descripcion_objeto=?,monto_garantia=?,estado=?,fk_idusuario=?,fk_idventa=? WHERE idgarantia=?;";
    private String sql_select = "SELECT idgarantia as idg,"
            + "to_char(fecha_inicio,'yyyy-MM-dd HH24:MI') as fecha,"
            + "responsable,descripcion_objeto as descripcion,"
            + "TRIM(to_char(monto_garantia,'999G999G999')) as monto,"
            + "estado,fk_idventa as idv,creado_por as usuario "
            + "FROM garantia order by 1 desc;";
    private String sql_cargar = "SELECT idgarantia,fecha_creado,creado_por,fecha_inicio,fecha_fin,responsable,descripcion_objeto,monto_garantia,estado,fk_idusuario,fk_idventa FROM garantia WHERE idgarantia=";

    public void insertar_garantia(Connection conn, garantia ga) {
        ga.setC1idgarantia(eveconn.getInt_ultimoID_mas_uno(conn, ga.getTb_garantia(), ga.getId_idgarantia()));
        String titulo = "insertar_garantia";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ga.getC1idgarantia());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ga.getC3creado_por());
            pst.setTimestamp(4, evefec.getTimestamp_sistema());
            pst.setTimestamp(5, evefec.getTimestamp_sistema());
            pst.setString(6, ga.getC6responsable());
            pst.setString(7, ga.getC7descripcion_objeto());
            pst.setDouble(8, ga.getC8monto_garantia());
            pst.setString(9, ga.getC9estado());
            pst.setInt(10, ga.getC10fk_idusuario());
            pst.setInt(11, ga.getC11fk_idventa());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ga.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ga.toString(), titulo);
        }
    }

    public void update_garantia(Connection conn, garantia ga) {
        String titulo = "update_garantia";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, ga.getC3creado_por());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setTimestamp(4, evefec.getTimestamp_sistema());
            pst.setString(5, ga.getC6responsable());
            pst.setString(6, ga.getC7descripcion_objeto());
            pst.setDouble(7, ga.getC8monto_garantia());
            pst.setString(8, ga.getC9estado());
            pst.setInt(9, ga.getC10fk_idusuario());
            pst.setInt(10, ga.getC11fk_idventa());
            pst.setInt(11, ga.getC1idgarantia());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ga.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ga.toString(), titulo);
        }
    }

    public void cargar_garantia(Connection conn, garantia ga, int idgarantia) {
        String titulo = "Cargar_garantia";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idgarantia, titulo);
            if (rs.next()) {
                ga.setC1idgarantia(rs.getInt(1));
                ga.setC2fecha_creado(rs.getString(2));
                ga.setC3creado_por(rs.getString(3));
                ga.setC4fecha_inicio(rs.getString(4));
                ga.setC5fecha_fin(rs.getString(5));
                ga.setC6responsable(rs.getString(6));
                ga.setC7descripcion_objeto(rs.getString(7));
                ga.setC8monto_garantia(rs.getDouble(8));
                ga.setC9estado(rs.getString(9));
                ga.setC10fk_idusuario(rs.getInt(10));
                ga.setC11fk_idventa(rs.getInt(11));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ga.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ga.toString(), titulo);
        }
    }

    public void actualizar_tabla_garantia(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_garantia(tbltabla);
    }

    public void ancho_tabla_garantia(JTable tbltabla) {
        int Ancho[] = {5, 12, 12, 30, 10, 10,8,13};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void terminar_garantia_en_caja(Connection conn, int fk_idcaja_cierre) {
        String sql = "update garantia set estado='" + eveest.getEst_PENDIENTE() + "' from caja_cierre_item ,caja_cierre_detalle \n"
                + "where caja_cierre_item.fk_idcaja_cierre_detalle=caja_cierre_detalle.idcaja_cierre_detalle \n"
                + "and caja_cierre_detalle.fk_idgarantia=garantia.idgarantia \n"
                + "and garantia.estado='" + eveest.getEst_Emitido() + "'\n"
                + "and caja_cierre_item.fk_idcaja_cierre=" + fk_idcaja_cierre;
        eveconn.SQL_execute_libre(conn, sql);
    }
}
