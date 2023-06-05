package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_venta_eliminar;
import FORMULARIO.DAO.DAO_venta_item_eliminar;
import FORMULARIO.ENTIDAD.venta_eliminar;
import FORMULARIO.ENTIDAD.venta_item_eliminar;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_venta_eliminar {
    private DAO_venta_eliminar DAOvene = new DAO_venta_eliminar();
    private DAO_venta_item_eliminar DAOvenie = new DAO_venta_item_eliminar();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();
    public void insertar_venta_eliminar(venta_eliminar ENTvene,JTable tbltabla) {
        String titulo = "insertar_venta_eliminar";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOvene.insertar_venta_eliminar(conn, ENTvene);
            DAOvenie.insertar_venta_item_eliminar_de_tabla(conn, tbltabla, ENTvene);
//            DAOvene.actualizar_tabla_venta_eliminar(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTvene.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTvene.toString(), titulo);
            }
        }
    }

    public void update_venta_eliminar_anular(venta_eliminar ENTvene,venta_item_eliminar ENTvenie) {
//        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR VENTA_ELIMINAR", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_venta_eliminar_anular";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOvene.update_venta_eliminar(conn, ENTvene);
                DAOvenie.update_venta_item_eliminar_ingresar_stock_producto(conn, ENTvenie);
                DAOvenie.update_venta_item_eliminar_tipo_item(conn, ENTvenie);
//                DAOvene.actualizar_tabla_venta_eliminar(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ENTvene.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ENTvene.toString(), titulo);
                }
            }
//        }
    }
}
