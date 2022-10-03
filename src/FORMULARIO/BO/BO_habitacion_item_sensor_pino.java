package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_habitacion_item_sensor_pino;
import FORMULARIO.ENTIDAD.habitacion_item_sensor_pino;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_habitacion_item_sensor_pino {

    private DAO_habitacion_item_sensor_pino haitsepi_dao = new DAO_habitacion_item_sensor_pino();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_habitacion_item_sensor_pino(habitacion_item_sensor_pino haitsepi) {
        String titulo = "insertar_habitacion_item_sensor_pino";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            haitsepi_dao.insertar_habitacion_item_sensor_pino(conn, haitsepi);
//            haitsepi_dao.actualizar_tabla_habitacion_item_sensor_pino(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, haitsepi.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, haitsepi.toString(), titulo);
            }
        }
    }

    public void update_habitacion_item_sensor_pino(habitacion_item_sensor_pino haitsepi) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE QUITAR HABITACION_ITEM_SENSOR_PINO", "QUITAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_habitacion_item_sensor_pino";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                haitsepi_dao.update_habitacion_item_sensor_pino(conn, haitsepi);
//                haitsepi_dao.actualizar_tabla_habitacion_item_sensor_pino(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, haitsepi.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, haitsepi.toString(), titulo);
                }
            }
        }
    }
}
