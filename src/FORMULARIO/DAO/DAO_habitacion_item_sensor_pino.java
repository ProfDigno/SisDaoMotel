package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.habitacion_item_sensor_pino;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_habitacion_item_sensor_pino {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "HABITACION_ITEM_SENSOR_PINO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "HABITACION_ITEM_SENSOR_PINO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO habitacion_item_sensor_pino(idhabitacion_item_sensor_pino,fecha_update,alto_bajo,pino,activar,fk_idhabitacion_sensor,fk_idhabitacion_dato,fk_idhabitacion_arduino) VALUES (?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE habitacion_item_sensor_pino SET fecha_update=?,alto_bajo=?,pino=?,activar=?,fk_idhabitacion_sensor=?,fk_idhabitacion_dato=?,fk_idhabitacion_arduino=? WHERE idhabitacion_item_sensor_pino=?;";
//    private String sql_select = "SELECT idhabitacion_item_sensor_pino,fecha_update,alto_bajo,pino,activar,fk_idhabitacion_sensor,fk_idhabitacion_dato,fk_idhabitacion_arduino FROM habitacion_item_sensor_pino order by 1 desc;";
    private String sql_cargar = "SELECT idhabitacion_item_sensor_pino,fecha_update,alto_bajo,pino,activar,fk_idhabitacion_sensor,fk_idhabitacion_dato,fk_idhabitacion_arduino FROM habitacion_item_sensor_pino WHERE idhabitacion_item_sensor_pino=";

    public void insertar_habitacion_item_sensor_pino(Connection conn, habitacion_item_sensor_pino haitsepi) {
        haitsepi.setC1idhabitacion_item_sensor_pino(eveconn.getInt_ultimoID_mas_uno(conn, haitsepi.getTb_habitacion_item_sensor_pino(), haitsepi.getId_idhabitacion_item_sensor_pino()));
        String titulo = "insertar_habitacion_item_sensor_pino";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, haitsepi.getC1idhabitacion_item_sensor_pino());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setBoolean(3, haitsepi.getC3alto_bajo());
            pst.setInt(4, haitsepi.getC4pino());
            pst.setBoolean(5, haitsepi.getC5activar());
            pst.setInt(6, haitsepi.getC6fk_idhabitacion_sensor());
            pst.setInt(7, haitsepi.getC7fk_idhabitacion_dato());
            pst.setInt(8, haitsepi.getC8fk_idhabitacion_arduino());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + haitsepi.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + haitsepi.toString(), titulo);
        }
    }

    public void update_habitacion_item_sensor_pino(Connection conn, habitacion_item_sensor_pino haitsepi) {
        String titulo = "update_habitacion_item_sensor_pino";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setBoolean(2, haitsepi.getC3alto_bajo());
            pst.setInt(3, haitsepi.getC4pino());
            pst.setBoolean(4, haitsepi.getC5activar());
            pst.setInt(5, haitsepi.getC6fk_idhabitacion_sensor());
            pst.setInt(6, haitsepi.getC7fk_idhabitacion_dato());
            pst.setInt(7, haitsepi.getC8fk_idhabitacion_arduino());
            pst.setInt(8, haitsepi.getC1idhabitacion_item_sensor_pino());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + haitsepi.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + haitsepi.toString(), titulo);
        }
    }

    public void cargar_habitacion_item_sensor_pino(Connection conn, habitacion_item_sensor_pino haitsepi, int idhabitacion_item_sensor_pino) {
        String titulo = "Cargar_habitacion_item_sensor_pino";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idhabitacion_item_sensor_pino, titulo);
            if (rs.next()) {
                haitsepi.setC1idhabitacion_item_sensor_pino(rs.getInt(1));
                haitsepi.setC2fecha_update(rs.getString(2));
                haitsepi.setC3alto_bajo(rs.getBoolean(3));
                haitsepi.setC4pino(rs.getInt(4));
                haitsepi.setC5activar(rs.getBoolean(5));
                haitsepi.setC6fk_idhabitacion_sensor(rs.getInt(6));
                haitsepi.setC7fk_idhabitacion_dato(rs.getInt(7));
                haitsepi.setC8fk_idhabitacion_arduino(rs.getInt(8));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + haitsepi.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + haitsepi.toString(), titulo);
        }
    }

    public void actualizar_tabla_habitacion_item_sensor_pino(Connection conn, JTable tbltabla, int fk_idhabitacion_dato) {
        String sql = "select ig.idhabitacion_item_sensor_pino as idg,ig.pino,hs.nombre as sensor,"
                + "ha.nombre as arduino,ha.usb_puerto,"
                + "pc.placa_nombre,pc.placa_ip \n"
                + "from habitacion_item_sensor_pino ig,habitacion_sensor hs,habitacion_mini_pc pc,habitacion_arduino ha\n"
                + "where ig.fk_idhabitacion_sensor=hs.idhabitacion_sensor\n"
                + "and ig.fk_idhabitacion_arduino=ha.idhabitacion_arduino\n"
                + "and ha.fk_idhabitacion_mini_pc=pc.idhabitacion_mini_pc\n"
                + "and ig.activar=true\n"
                + "and ig.fk_idhabitacion_dato=" + fk_idhabitacion_dato
                + " order by ig.pino desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_habitacion_item_sensor_pino(tbltabla);
    }

    public void ancho_tabla_habitacion_item_sensor_pino(JTable tbltabla) {
        int Ancho[] = {5, 5, 15, 20, 20, 20, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_tabla_habitacion_item_sensor_pino_arduino(Connection conn, JTable tbltabla, int fk_idhabitacion_arduino) {
        String sql = "select ig.idhabitacion_item_sensor_pino as idg,ig.pino,hs.nombre as sensor,\n"
                + "ha.nombre as arduino,ha.usb_puerto,pc.placa_nombre,pc.placa_ip,hd.nro_habitacion,hd.tipo_habitacion \n"
                + "from habitacion_item_sensor_pino ig,habitacion_sensor hs,\n"
                + "habitacion_mini_pc pc,habitacion_arduino ha,habitacion_dato hd\n"
                + "where ig.fk_idhabitacion_sensor=hs.idhabitacion_sensor\n"
                + "and ig.fk_idhabitacion_dato=hd.idhabitacion_dato\n"
                + "and ig.fk_idhabitacion_arduino=ha.idhabitacion_arduino\n"
                + "and ha.fk_idhabitacion_mini_pc=pc.idhabitacion_mini_pc\n"
                + "and ig.activar=true\n"
                + "and ig.fk_idhabitacion_arduino="+fk_idhabitacion_arduino
                + " order by ig.pino desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
//        ancho_tabla_habitacion_item_sensor_pino(tbltabla);
    }
}
