package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_inventario;
import FORMULARIO.ENTIDAD.inventario;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_inventario {

    private DAO_inventario DAOin = new DAO_inventario();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_inventario(inventario in) {
        String titulo = "insertar_inventario";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOin.insertar_inventario(conn, in);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, in.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, in.toString(), titulo);
            }
        }
    }

    public void update_inventario(inventario in, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR INVENTARIO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_inventario";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOin.update_inventario(conn, in);
                DAOin.actualizar_tabla_inventario(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, in.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, in.toString(), titulo);
                }
            }
        }
    }
    public void update_inventario_terminar(inventario inve) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE TERMINAR EL INVENTARIO", "TERMINAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_inventario_terminar";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOin.update_inventario_estado(conn, inve);
                DAOin.update_producto_stock_inventario(conn, inve);
                DAOin.update_estado_item_inventario(conn, inve);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, inve.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, inve.toString(), titulo);
                }
            }
        }
    }
}
