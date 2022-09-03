package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_cierre_detalle;
import FORMULARIO.ENTIDAD.caja_cierre_detalle;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_caja_cierre_detalle {

    private DAO_caja_cierre_detalle cacide_dao = new DAO_caja_cierre_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_caja_cierre_detalle(caja_cierre_detalle cacide, JTable tbltabla) {
        String titulo = "insertar_caja_cierre_detalle";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            cacide_dao.insertar_caja_cierre_detalle(conn, cacide);
//			cacide_dao.actualizar_tabla_caja_cierre_detalle(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, cacide.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, cacide.toString(), titulo);
            }
        }
    }

    public void update_caja_cierre_detalle(caja_cierre_detalle cacide, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR CAJA_CIERRE_DETALLE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_caja_cierre_detalle";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                cacide_dao.update_caja_cierre_detalle(conn, cacide);
//				cacide_dao.actualizar_tabla_caja_cierre_detalle(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, cacide.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, cacide.toString(), titulo);
                }
            }
        }
    }
}
