package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_venta_item;
import FORMULARIO.ENTIDAD.venta;
import FORMULARIO.ENTIDAD.venta_item;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_venta_item {

    private DAO_venta_item DAOveni = new DAO_venta_item();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_venta_item(venta_item item, venta ven, JTable tbltabla, boolean es_mudar,boolean es_desc_stock,boolean es_agre_stock) {
        String titulo = "insertar_venta_item";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOveni.insertar_item_venta_de_tabla(conn, tbltabla, item, ven, es_mudar,es_desc_stock,es_agre_stock);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, item.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, item.toString(), titulo);
            }
        }
    }

    public void update_venta_item(venta_item veit, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR VENTA_ITEM", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_venta_item";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOveni.update_venta_item(conn, veit);
//                DAOveni.actualizar_tabla_venta_item(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, veit.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, veit.toString(), titulo);
                }
            }
        }
    }

    public void insertar_venta_item_sin_commit(Connection conn, venta_item item, venta ven, JTable tbltabla, boolean es_mudar,boolean es_desc_stock,boolean es_agre_stock) {
        String titulo = "insertar_venta_item";
        DAOveni.insertar_item_venta_de_tabla(conn, tbltabla, item, ven, es_mudar,es_desc_stock,es_agre_stock);
    }
}
