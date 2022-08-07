package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_producto_habitacion_patrimonio;
import FORMULARIO.ENTIDAD.producto_habitacion_patrimonio;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_producto_habitacion_patrimonio {

    private DAO_producto_habitacion_patrimonio prhapa_dao = new DAO_producto_habitacion_patrimonio();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_producto_habitacion_patrimonio(producto_habitacion_patrimonio prhapa) {
        String titulo = "insertar_producto_habitacion_patrimonio";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            prhapa_dao.insertar_producto_habitacion_patrimonio(conn, prhapa);
//            prhapa_dao.actualizar_tabla_producto_habitacion_patrimonio(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, prhapa.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, prhapa.toString(), titulo);
            }
        }
    }

    public void update_producto_habitacion_patrimonio(producto_habitacion_patrimonio prhapa) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE QUITAR PRODUCTO_HABITACION_PATRIMONIO", "QUITAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_producto_habitacion_patrimonio";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                prhapa_dao.update_producto_habitacion_patrimonio(conn, prhapa);
//                prhapa_dao.actualizar_tabla_producto_habitacion_patrimonio(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, prhapa.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, prhapa.toString(), titulo);
                }
            }
        }
    }
}
