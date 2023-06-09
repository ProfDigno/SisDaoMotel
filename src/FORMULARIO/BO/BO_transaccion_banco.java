package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_transaccion_banco;
import FORMULARIO.ENTIDAD.transaccion_banco;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_transaccion_banco {

    private DAO_transaccion_banco DAOtb = new DAO_transaccion_banco();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_transaccion_banco(transaccion_banco tb, JTable tbltabla) {
        String titulo = "insertar_transaccion_banco";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOtb.insertar_transaccion_banco(conn, tb);
            DAOtb.actualizar_tabla_transaccion_banco(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, tb.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, tb.toString(), titulo);
            }
        }
    }

    public void update_transaccion_banco(transaccion_banco tb, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR TRANSACCION_BANCO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_transaccion_banco";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOtb.update_transaccion_banco(conn, tb);
                DAOtb.actualizar_tabla_transaccion_banco(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, tb.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, tb.toString(), titulo);
                }
            }
        }
    }
    public void update_transaccion_banco_anular(transaccion_banco tb, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE ANULAR TRANSACCION_BANCO", "ANULAR TRNSACCION", "ANULAR", "CANCELAR")) {
            String titulo = "update_transaccion_banco_anular";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOtb.update_transaccion_banco_anular(conn, tb);
                DAOtb.actualizar_tabla_transaccion_banco(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, tb.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, tb.toString(), titulo);
                }
            }
        }
    }
}
