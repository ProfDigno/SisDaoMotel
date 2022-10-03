package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_habitacion_dato;
import FORMULARIO.DAO.DAO_habitacion_recepcion_temp;
import FORMULARIO.ENTIDAD.habitacion_dato;
import FORMULARIO.ENTIDAD.habitacion_recepcion_temp;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_habitacion_dato {

    private DAO_habitacion_dato hada_dao = new DAO_habitacion_dato();
    private DAO_habitacion_recepcion_temp DAOhrt=new DAO_habitacion_recepcion_temp();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_habitacion_dato(habitacion_dato hada,habitacion_recepcion_temp harete) {
        String titulo = "insertar_habitacion_dato";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            hada_dao.insertar_habitacion_dato(conn, hada);
            DAOhrt.insertar_habitacion_recepcion_temp(conn, harete);
//            hada_dao.actualizar_tabla_habitacion_dato(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, hada.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, hada.toString(), titulo);
            }
        }
    }

    public void update_habitacion_dato(habitacion_dato hada,habitacion_recepcion_temp harete) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR HABITACION_DATO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_habitacion_dato";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                hada_dao.update_habitacion_dato(conn, hada);
                DAOhrt.update_habitacion_recepcion_temp_dato(conn, harete);
//                hada_dao.actualizar_tabla_habitacion_dato(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, hada.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, hada.toString(), titulo);
                }
            }
        }
    }
}
