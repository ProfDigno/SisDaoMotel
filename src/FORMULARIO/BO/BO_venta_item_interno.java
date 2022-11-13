package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_venta_item_interno;
import FORMULARIO.ENTIDAD.venta_item_interno;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_venta_item_interno {

    private DAO_venta_item_interno veitin_dao = new DAO_venta_item_interno();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_venta_item_interno(venta_item_interno veitin, JTable tbltabla) {
        String titulo = "insertar_venta_item_interno";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            veitin_dao.insertar_venta_item_interno(conn, veitin);
            veitin_dao.actualizar_tabla_venta_item_interno(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, veitin.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, veitin.toString(), titulo);
            }
        }
    }

    public void update_venta_item_interno(venta_item_interno veitin, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR VENTA_ITEM_INTERNO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_venta_item_interno";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                veitin_dao.update_venta_item_interno(conn, veitin);
                veitin_dao.actualizar_tabla_venta_item_interno(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, veitin.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, veitin.toString(), titulo);
                }
            }
        }
    }
}
