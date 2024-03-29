package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_cierre_detalle;
import FORMULARIO.DAO.DAO_caja_producto_item;
import FORMULARIO.ENTIDAD.caja_cierre_detalle;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class BO_caja_cierre_detalle {

    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    private DAO_caja_producto_item caprit_dao = new DAO_caja_producto_item();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_caja_cierre_detalle(caja_cierre_detalle cacide) {
        String titulo = "insertar_caja_cierre_detalle";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOccd.insertar_caja_cierre_detalle(conn, cacide);
//            caprit_dao.insertar_caja_producto_item_por_select_todos(conn);
            JOptionPane.showMessageDialog(null,"APERTURA DE CAJA CORRECTAMENTE","CAJA APERTURA",JOptionPane.INFORMATION_MESSAGE);
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
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR CAJA_CIERRE_DETALLE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_caja_cierre_detalle";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOccd.update_caja_cierre_detalle(conn, cacide);
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
    public void update_caja_cierre_detalle_corregir() {
            String titulo = "update_caja_cierre_detalle_corregir";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOccd.update_caja_cierre_detalle_corregir(conn);
//                DAOccd.update_monto_ocupa_minimo(conn);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, "corregir", titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, "corregir", titulo);
                }
            }
    }
}
