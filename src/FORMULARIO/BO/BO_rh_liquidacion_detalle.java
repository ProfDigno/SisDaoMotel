package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_rh_liquidacion_detalle;
import FORMULARIO.ENTIDAD.rh_liquidacion_detalle;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_rh_liquidacion_detalle {

    private DAO_rh_liquidacion_detalle rld_dao = new DAO_rh_liquidacion_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_rh_liquidacion_detalle(rh_liquidacion_detalle rld, JTable tbltabla) {
        String titulo = "insertar_rh_liquidacion_detalle";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            rld_dao.insertar_rh_liquidacion_detalle(conn, rld);
            rld_dao.actualizar_tabla_rh_liquidacion_detalle(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, rld.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, rld.toString(), titulo);
            }
        }
    }

    public void update_rh_liquidacion_detalle(rh_liquidacion_detalle rld, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR RH_LIQUIDACION_DETALLE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_rh_liquidacion_detalle";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                rld_dao.update_rh_liquidacion_detalle(conn, rld);
                rld_dao.actualizar_tabla_rh_liquidacion_detalle(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, rld.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, rld.toString(), titulo);
                }
            }
        }
    }
}
