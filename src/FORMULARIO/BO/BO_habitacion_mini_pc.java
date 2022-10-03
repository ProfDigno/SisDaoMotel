package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_habitacion_mini_pc;
import FORMULARIO.ENTIDAD.habitacion_mini_pc;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_habitacion_mini_pc {

    private DAO_habitacion_mini_pc hamipc_dao = new DAO_habitacion_mini_pc();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_habitacion_mini_pc(habitacion_mini_pc hamipc, JTable tbltabla) {
        String titulo = "insertar_habitacion_mini_pc";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            hamipc_dao.insertar_habitacion_mini_pc(conn, hamipc);
            hamipc_dao.actualizar_tabla_habitacion_mini_pc(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, hamipc.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, hamipc.toString(), titulo);
            }
        }
    }

    public void update_habitacion_mini_pc(habitacion_mini_pc hamipc, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR HABITACION_MINI_PC", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_habitacion_mini_pc";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                hamipc_dao.update_habitacion_mini_pc(conn, hamipc);
                hamipc_dao.actualizar_tabla_habitacion_mini_pc(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, hamipc.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, hamipc.toString(), titulo);
                }
            }
        }
    }
}
