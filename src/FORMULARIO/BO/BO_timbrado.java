package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_timbrado;
import FORMULARIO.ENTIDAD.timbrado;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_timbrado {

    private DAO_timbrado DAOt = new DAO_timbrado();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_timbrado(timbrado ENTt, JTable tbltabla) {
        String titulo = "insertar_timbrado";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOt.insertar_timbrado(conn, ENTt);
            DAOt.actualizar_tabla_timbrado(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTt.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTt.toString(), titulo);
            }
        }
    }

    public void update_timbrado(timbrado t, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR TIMBRADO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_timbrado";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOt.update_timbrado(conn, t);
                DAOt.actualizar_tabla_timbrado(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, t.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, t.toString(), titulo);
                }
            }
        }
    }
}
