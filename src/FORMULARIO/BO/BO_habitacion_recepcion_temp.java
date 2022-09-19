package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_habitacion_recepcion_temp;
import FORMULARIO.ENTIDAD.habitacion_recepcion_temp;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_habitacion_recepcion_temp {

    private DAO_habitacion_recepcion_temp DAOhrt = new DAO_habitacion_recepcion_temp();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_habitacion_recepcion_temp(habitacion_recepcion_temp harete, JTable tbltabla) {
        String titulo = "insertar_habitacion_recepcion_temp";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOhrt.insertar_habitacion_recepcion_temp(conn, harete);
            DAOhrt.actualizar_tabla_habitacion_recepcion_temp(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, harete.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, harete.toString(), titulo);
            }
        }
    }

    public void update_habitacion_recepcion_temp(habitacion_recepcion_temp harete, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR HABITACION_RECEPCION_TEMP", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_habitacion_recepcion_temp";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOhrt.update_habitacion_recepcion_temp(conn, harete);
                DAOhrt.actualizar_tabla_habitacion_recepcion_temp(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, harete.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, harete.toString(), titulo);
                }
            }
        }
    }
    public void update_habitacion_recepcion_temp_puertas( int sensor_puerta_cliente, int sensor_puerta_limpieza) {
            String titulo = "update_habitacion_recepcion_temp";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOhrt.actualizar_estado_puerta_cliente_limpieza(conn, sensor_puerta_cliente, sensor_puerta_limpieza);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e,"error", titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1,"error", titulo);
                }
            }
    }
}
