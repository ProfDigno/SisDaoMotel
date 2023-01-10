package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_producto_item;
import FORMULARIO.ENTIDAD.caja_producto_item;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_caja_producto_item {

    private DAO_caja_producto_item caprit_dao = new DAO_caja_producto_item();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_caja_producto_item(caja_producto_item caprit, JTable tbltabla) {
        String titulo = "insertar_caja_producto_item";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            caprit_dao.insertar_caja_producto_item(conn, caprit);
            caprit_dao.actualizar_tabla_caja_producto_item(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, caprit.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, caprit.toString(), titulo);
            }
        }
    }

    public void update_caja_producto_item(caja_producto_item caprit, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR CAJA_PRODUCTO_ITEM", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_caja_producto_item";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                caprit_dao.update_caja_producto_item(conn, caprit);
                caprit_dao.actualizar_tabla_caja_producto_item(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, caprit.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, caprit.toString(), titulo);
                }
            }
        }
    }
}
