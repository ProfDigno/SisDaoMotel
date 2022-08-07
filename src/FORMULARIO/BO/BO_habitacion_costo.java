package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_habitacion_costo;
import FORMULARIO.ENTIDAD.habitacion_costo;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_habitacion_costo {

    private DAO_habitacion_costo haco_dao = new DAO_habitacion_costo();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_habitacion_costo(habitacion_costo haco, JTable tbltabla) {
        String titulo = "insertar_habitacion_costo";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            haco_dao.insertar_habitacion_costo(conn, haco);
            haco_dao.actualizar_tabla_habitacion_costo(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, haco.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, haco.toString(), titulo);
            }
        }
    }

    public void update_habitacion_costo(habitacion_costo haco, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR HABITACION_COSTO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_habitacion_costo";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                haco_dao.update_habitacion_costo(conn, haco);
                haco_dao.actualizar_tabla_habitacion_costo(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, haco.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, haco.toString(), titulo);
                }
            }
        }
    }
}
