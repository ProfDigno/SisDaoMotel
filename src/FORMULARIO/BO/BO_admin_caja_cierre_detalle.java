package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_admin_caja_cierre_detalle;
import FORMULARIO.ENTIDAD.admin_caja_cierre_detalle;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_admin_caja_cierre_detalle {

    private DAO_admin_caja_cierre_detalle DAOaccd = new DAO_admin_caja_cierre_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_admin_caja_cierre_detalle(admin_caja_cierre_detalle ENTaccd, JTable tbltabla) {
        String titulo = "insertar_admin_caja_cierre_detalle";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOaccd.insertar_admin_caja_cierre_detalle(conn, ENTaccd);
            DAOaccd.actualizar_tabla_admin_caja_cierre_detalle(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTaccd.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTaccd.toString(), titulo);
            }
        }
    }

    public void update_admin_caja_cierre_detalle(admin_caja_cierre_detalle ENTaccd, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR ADMIN_CAJA_CIERRE_DETALLE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_admin_caja_cierre_detalle";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOaccd.update_admin_caja_cierre_detalle(conn, ENTaccd);
                DAOaccd.actualizar_tabla_admin_caja_cierre_detalle(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ENTaccd.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ENTaccd.toString(), titulo);
                }
            }
        }
    }
}
