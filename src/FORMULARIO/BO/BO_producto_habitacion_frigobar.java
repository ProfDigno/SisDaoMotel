package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_producto_habitacion_frigobar;
import FORMULARIO.ENTIDAD.producto_habitacion_frigobar;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_producto_habitacion_frigobar {

    private DAO_producto_habitacion_frigobar prhafr_dao = new DAO_producto_habitacion_frigobar();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_producto_habitacion_frigobar(producto_habitacion_frigobar prhafr) {
        String titulo = "insertar_producto_habitacion_frigobar";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            prhafr_dao.insertar_producto_habitacion_frigobar(conn, prhafr);
//            prhafr_dao.actualizar_tabla_producto_habitacion_frigobar(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, prhafr.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, prhafr.toString(), titulo);
            }
        }
    }

    public void update_producto_habitacion_frigobar(producto_habitacion_frigobar prhafr) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE QUITAR PRODUCTO_HABITACION_FRIGOBAR", "QUITAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_producto_habitacion_frigobar";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                prhafr_dao.update_producto_habitacion_frigobar(conn, prhafr);
//                prhafr_dao.actualizar_tabla_producto_habitacion_frigobar(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, prhafr.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, prhafr.toString(), titulo);
                }
            }
        }
    }
}
