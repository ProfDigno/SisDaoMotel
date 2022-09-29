package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_cierre_detalle;
import FORMULARIO.DAO.DAO_compra;
import FORMULARIO.DAO.DAO_compra_item;
import FORMULARIO.ENTIDAD.caja_cierre_detalle;
import FORMULARIO.ENTIDAD.compra;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_compra {

    private DAO_compra DAOcom = new DAO_compra();
    private DAO_compra_item DAOcomi = new DAO_compra_item();
    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_compra(compra co,JTable tblitem_producto,caja_cierre_detalle ENTccd) {
        String titulo = "insertar_compra";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOcom.insertar_compra(conn, co);
            DAOcomi.insertar_item_compra_de_tabla(conn, tblitem_producto, co);
            DAOccd.insertar_caja_cierre_detalle(conn, ENTccd);
//            co_dao.actualizar_tabla_compra(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, co.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, co.toString(), titulo);
            }
        }
    }

    public void update_compra(compra co, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR COMPRA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_compra";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOcom.update_compra(conn, co);
                DAOcom.actualizar_tabla_compra(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, co.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, co.toString(), titulo);
                }
            }
        }
    }
}
