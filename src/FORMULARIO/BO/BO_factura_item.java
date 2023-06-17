package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_factura_item;
import FORMULARIO.ENTIDAD.factura_item;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_factura_item {

    private DAO_factura_item DAOfi = new DAO_factura_item();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_factura_item(factura_item ENTfi, JTable tbltabla) {
        String titulo = "insertar_factura_item";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOfi.insertar_factura_item(conn, ENTfi);
            DAOfi.actualizar_tabla_factura_item(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTfi.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTfi.toString(), titulo);
            }
        }
    }

    public void update_factura_item(factura_item fi, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR FACTURA_ITEM", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_factura_item";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOfi.update_factura_item(conn, fi);
                DAOfi.actualizar_tabla_factura_item(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, fi.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, fi.toString(), titulo);
                }
            }
        }
    }
}
