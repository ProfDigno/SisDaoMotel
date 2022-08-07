package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.habitacion_item_sensor_gpio;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_habitacion_item_sensor_gpio {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "HABITACION_ITEM_SENSOR_GPIO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "HABITACION_ITEM_SENSOR_GPIO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO habitacion_item_sensor_gpio(idhabitacion_item_sensor_gpio,fecha_update,alto_bajo,gpio,activar,fk_idhabitacion_dato,fk_idhabitacion_sensor,fk_idhabitacion_mini_pc) VALUES (?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE habitacion_item_sensor_gpio SET fecha_update=?,alto_bajo=?,gpio=?,activar=?,fk_idhabitacion_dato=?,fk_idhabitacion_sensor=?,fk_idhabitacion_mini_pc=? WHERE idhabitacion_item_sensor_gpio=?;";

    private String sql_cargar = "SELECT idhabitacion_item_sensor_gpio,fecha_update,alto_bajo,gpio,activar,fk_idhabitacion_dato,fk_idhabitacion_sensor,fk_idhabitacion_mini_pc FROM habitacion_item_sensor_gpio WHERE idhabitacion_item_sensor_gpio=";

    public void insertar_habitacion_item_sensor_gpio(Connection conn, habitacion_item_sensor_gpio haitsegp) {
        haitsegp.setC1idhabitacion_item_sensor_gpio(eveconn.getInt_ultimoID_mas_uno(conn, haitsegp.getTb_habitacion_item_sensor_gpio(), haitsegp.getId_idhabitacion_item_sensor_gpio()));
        String titulo = "insertar_habitacion_item_sensor_gpio";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, haitsegp.getC1idhabitacion_item_sensor_gpio());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setBoolean(3, haitsegp.getC3alto_bajo());
            pst.setInt(4, haitsegp.getC4gpio());
            pst.setBoolean(5, haitsegp.getC5activar());
            pst.setInt(6, haitsegp.getC6fk_idhabitacion_dato());
            pst.setInt(7, haitsegp.getC7fk_idhabitacion_sensor());
            pst.setInt(8, haitsegp.getC8fk_idhabitacion_mini_pc());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + haitsegp.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + haitsegp.toString(), titulo);
        }
    }

    public void update_habitacion_item_sensor_gpio(Connection conn, habitacion_item_sensor_gpio haitsegp) {
        String titulo = "update_habitacion_item_sensor_gpio";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setBoolean(2, haitsegp.getC3alto_bajo());
            pst.setInt(3, haitsegp.getC4gpio());
            pst.setBoolean(4, haitsegp.getC5activar());
            pst.setInt(5, haitsegp.getC6fk_idhabitacion_dato());
            pst.setInt(6, haitsegp.getC7fk_idhabitacion_sensor());
            pst.setInt(7, haitsegp.getC8fk_idhabitacion_mini_pc());
            pst.setInt(8, haitsegp.getC1idhabitacion_item_sensor_gpio());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + haitsegp.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + haitsegp.toString(), titulo);
        }
    }

    public void cargar_habitacion_item_sensor_gpio(Connection conn, habitacion_item_sensor_gpio haitsegp, int idhabitacion_item_sensor_gpio) {
        String titulo = "Cargar_habitacion_item_sensor_gpio";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idhabitacion_item_sensor_gpio, titulo);
            if (rs.next()) {
                haitsegp.setC1idhabitacion_item_sensor_gpio(rs.getInt(1));
                haitsegp.setC2fecha_update(rs.getString(2));
                haitsegp.setC3alto_bajo(rs.getBoolean(3));
                haitsegp.setC4gpio(rs.getInt(4));
                haitsegp.setC5activar(rs.getBoolean(5));
                haitsegp.setC6fk_idhabitacion_dato(rs.getInt(6));
                haitsegp.setC7fk_idhabitacion_sensor(rs.getInt(7));
                haitsegp.setC8fk_idhabitacion_mini_pc(rs.getInt(8));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + haitsegp.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + haitsegp.toString(), titulo);
        }
    }

    public void actualizar_tabla_habitacion_item_sensor_gpio(Connection conn, JTable tbltabla, int fk_idhabitacion_dato) {
        String sql_select = "select ig.idhabitacion_item_sensor_gpio as idg,ig.gpio,hs.nombre as sensor,"
                + "pc.placa_nombre,pc.placa_ip,pc.placa_ubicacion \n"
                + "from habitacion_item_sensor_gpio ig,habitacion_sensor hs,habitacion_mini_pc pc\n"
                + "where ig.fk_idhabitacion_sensor=hs.idhabitacion_sensor\n"
                + "and ig.fk_idhabitacion_mini_pc=pc.idhabitacion_mini_pc\n"
                + "and ig.activar=true\n"
                + "and ig.fk_idhabitacion_dato=" + fk_idhabitacion_dato
                + " order by ig.gpio desc;";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_habitacion_item_sensor_gpio(tbltabla);
    }

    public void ancho_tabla_habitacion_item_sensor_gpio(JTable tbltabla) {
        int Ancho[] = {10, 10, 15, 20, 20, 25};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_tabla_habitacion_item_sensor_gpio_minipc(Connection conn, JTable tbltabla, int idhabitacion_mini_pc) {
        String sql_select = "select ig.idhabitacion_item_sensor_gpio as idg,pc.placa_nombre,ig.gpio,\n"
                + "hs.nombre as sensor, hd.nro_habitacion,hd.tipo_habitacion\n"
                + "from habitacion_item_sensor_gpio ig,habitacion_sensor hs,\n"
                + "habitacion_mini_pc pc,habitacion_dato hd\n"
                + "where ig.fk_idhabitacion_sensor=hs.idhabitacion_sensor\n"
                + "and ig.fk_idhabitacion_mini_pc=pc.idhabitacion_mini_pc\n"
                + "and ig.fk_idhabitacion_dato=hd.idhabitacion_dato\n"
                + "and ig.activar=true\n"
                + "and pc.idhabitacion_mini_pc="+idhabitacion_mini_pc
                + " order by ig.gpio desc;";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
//        ancho_tabla_habitacion_item_sensor_gpio(tbltabla);
    }
}
