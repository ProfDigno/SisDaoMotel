package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_banco;
import FORMULARIO.ENTIDAD.banco;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_banco {

    private DAO_banco DAOb = new DAO_banco();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_banco(banco b, JTable tbltabla) {
        String titulo = "insertar_banco";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOb.insertar_banco(conn, b);
            DAOb.actualizar_tabla_banco(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, b.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, b.toString(), titulo);
            }
        }
    }

    public void update_banco(banco b, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR BANCO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_banco";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOb.update_banco(conn, b);
                DAOb.actualizar_tabla_banco(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, b.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, b.toString(), titulo);
                }
            }
        }
    }
}
