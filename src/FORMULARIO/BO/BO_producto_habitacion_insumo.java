package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_producto_habitacion_insumo;
import FORMULARIO.ENTIDAD.producto_habitacion_insumo;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_producto_habitacion_insumo {

    private DAO_producto_habitacion_insumo prhain_dao = new DAO_producto_habitacion_insumo();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_producto_habitacion_insumo(producto_habitacion_insumo prhain) {
        String titulo = "insertar_producto_habitacion_insumo";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            prhain_dao.insertar_producto_habitacion_insumo(conn, prhain);
//            prhain_dao.actualizar_tabla_producto_habitacion_insumo(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, prhain.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, prhain.toString(), titulo);
            }
        }
    }

    public void update_producto_habitacion_insumo(producto_habitacion_insumo prhain) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE QUITAR PRODUCTO_HABITACION_INSUMO", "QUITAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_producto_habitacion_insumo";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                prhain_dao.update_producto_habitacion_insumo(conn, prhain);
//                prhain_dao.actualizar_tabla_producto_habitacion_insumo(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, prhain.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, prhain.toString(), titulo);
                }
            }
        }
    }
}
