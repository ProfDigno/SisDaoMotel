package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import ESTADOS.EvenEstado;
import FORMULARIO.ENTIDAD.venta_interno;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_venta_interno {

    private EvenConexion eveconn = new EvenConexion();
    private EvenJtable evejt = new EvenJtable();
    private EvenJasperReport rep = new EvenJasperReport();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private EvenFecha evefec = new EvenFecha();
    private EvenEstado eveest = new EvenEstado();
    private String mensaje_insert = "VENTA_INTERNO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "VENTA_INTERNO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO venta_interno(idventa_interno,fecha_creado,creado_por,"
            + "monto_letra,estado,observacion,motivo_anulacion,monto_interno,"
            + "fk_idusuario,fk_idpersona) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE venta_interno SET fecha_creado=?,creado_por=?,"
            + "monto_letra=?,estado=?,observacion=?,motivo_anulacion=?,monto_interno=?,"
            + "fk_idusuario=?,fk_idpersona=? "
            + " WHERE idventa_interno=?;";
//    private 
    private String sql_cargar = "SELECT idventa_interno,fecha_creado,creado_por,"
            + "monto_letra,estado,observacion,motivo_anulacion,monto_interno,"
            + "fk_idusuario,fk_idpersona "
            + "FROM venta_interno WHERE idventa_interno=";

    public void insertar_venta_interno(Connection conn, venta_interno vein) {
        vein.setC1idventa_interno(eveconn.getInt_ultimoID_mas_uno(conn, vein.getTb_venta_interno(), vein.getId_idventa_interno()));
        String titulo = "insertar_venta_interno";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, vein.getC1idventa_interno());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, vein.getC3creado_por());
            pst.setString(4, vein.getC4monto_letra());
            pst.setString(5, vein.getC5estado());
            pst.setString(6, vein.getC6observacion());
            pst.setString(7, vein.getC7motivo_anulacion());
            pst.setDouble(8, vein.getC8monto_interno());
            pst.setInt(9, vein.getC9fk_idusuario());
            pst.setInt(10, vein.getC10fk_idpersona());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + vein.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + vein.toString(), titulo);
        }
    }

    public void update_venta_interno(Connection conn, venta_interno vein) {
        String titulo = "update_venta_interno";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, vein.getC3creado_por());
            pst.setString(3, vein.getC4monto_letra());
            pst.setString(4, vein.getC5estado());
            pst.setString(5, vein.getC6observacion());
            pst.setString(6, vein.getC7motivo_anulacion());
            pst.setDouble(7, vein.getC8monto_interno());
            pst.setInt(8, vein.getC9fk_idusuario());
            pst.setInt(9, vein.getC10fk_idpersona());
            pst.setInt(10, vein.getC1idventa_interno());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + vein.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + vein.toString(), titulo);
        }
    }

    public void cargar_venta_interno(Connection conn, venta_interno vein, int idventa_interno) {
        String titulo = "Cargar_venta_interno";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idventa_interno, titulo);
            if (rs.next()) {
                vein.setC1idventa_interno(rs.getInt(1));
                vein.setC2fecha_creado(rs.getString(2));
                vein.setC3creado_por(rs.getString(3));
                vein.setC4monto_letra(rs.getString(4));
                vein.setC5estado(rs.getString(5));
                vein.setC6observacion(rs.getString(6));
                vein.setC7motivo_anulacion(rs.getString(7));
                vein.setC8monto_interno(rs.getDouble(8));
                vein.setC9fk_idusuario(rs.getInt(9));
                vein.setC10fk_idpersona(rs.getInt(10));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + vein.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + vein.toString(), titulo);
        }
    }

    public void actualizar_tabla_venta_interno(Connection conn, JTable tbltabla,String filtro) {
        String sql_select = "select vi.idventa_interno as idvi,\n"
            + "to_char(vi.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha,\n"
            + "p.nombre as cliente,p.direccion as direccion,\n"
            + "vi.estado as estado,\n"
            + "TRIM(to_char(vi.monto_interno,'999G999G999')) as monto,\n"
            + "vi.creado_por as usuario\n"
            + "from venta_interno vi,persona p \n"
            + "where vi.fk_idpersona=p.idpersona \n"+filtro
            + " order by vi.idventa_interno desc;";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_venta_interno(tbltabla);
    }

    public void ancho_tabla_venta_interno(JTable tbltabla) {
        int Ancho[] = {5, 15, 20, 25, 10, 10, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void terminar_venta_interno_en_caja(Connection conn, int fk_idcaja_cierre) {
        String sql = "update venta_interno set estado='" + eveest.getEst_Terminar() + "' from caja_cierre_item ,caja_cierre_detalle \n"
                + "where caja_cierre_item.fk_idcaja_cierre_detalle=caja_cierre_detalle.idcaja_cierre_detalle \n"
                + "and caja_cierre_detalle.fk_idventa_interno=venta_interno.idventa_interno \n"
                + "and venta_interno.estado='" + eveest.getEst_Emitido() + "'\n"
                + "and caja_cierre_item.fk_idcaja_cierre=" + fk_idcaja_cierre;
        eveconn.SQL_execute_libre(conn, sql);
    }
}
