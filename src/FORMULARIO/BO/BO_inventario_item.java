package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_inventario_item;
import FORMULARIO.ENTIDAD.inventario_item;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_inventario_item {

    private DAO_inventario_item DAOii = new DAO_inventario_item();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_inventario_item(inventario_item init) {
        String titulo = "insertar_inventario_item";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOii.insertar_inventario_item(conn, init);
//            init_dao.actualizar_tabla_inventario_item(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, init.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, init.toString(), titulo);
            }
        }
    }

    public void update_inventario_item(inventario_item init, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR INVENTARIO_ITEM", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_inventario_item";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOii.update_inventario_item(conn, init);
                DAOii.actualizar_tabla_inventario_item(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, init.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, init.toString(), titulo);
                }
            }
        }
    }
    public void delete_item_inventario(inventario_item iinven) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE ELIMINAR ITEM_INVENTARIO", "ELIMINAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "delete_item_inventario";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOii.deletar_item_inventario(conn, iinven);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, iinven.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, iinven.toString(), titulo);
                }
            }
        }
    }
}
