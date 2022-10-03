package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_habitacion_item_sensor_gpio;
import FORMULARIO.ENTIDAD.habitacion_item_sensor_gpio;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_habitacion_item_sensor_gpio {

    private DAO_habitacion_item_sensor_gpio haitsegp_dao = new DAO_habitacion_item_sensor_gpio();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_habitacion_item_sensor_gpio(habitacion_item_sensor_gpio haitsegp) {
        String titulo = "insertar_habitacion_item_sensor_gpio";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            haitsegp_dao.insertar_habitacion_item_sensor_gpio(conn, haitsegp);
//			haitsegp_dao.actualizar_tabla_habitacion_item_sensor_gpio(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, haitsegp.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, haitsegp.toString(), titulo);
            }
        }
    }

    public void update_habitacion_item_sensor_gpio(habitacion_item_sensor_gpio haitsegp) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE QUITAR HABITACION_ITEM_SENSOR_GPIO ", "QUITAR ITEM", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_habitacion_item_sensor_gpio";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                haitsegp_dao.update_habitacion_item_sensor_gpio(conn, haitsegp);
//                haitsegp_dao.actualizar_tabla_habitacion_item_sensor_gpio(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, haitsegp.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, haitsegp.toString(), titulo);
                }
            }
        }
    }
}
