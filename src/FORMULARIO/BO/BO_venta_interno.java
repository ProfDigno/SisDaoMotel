package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_cierre_detalle;
import FORMULARIO.DAO.DAO_venta_interno;
import FORMULARIO.DAO.DAO_venta_item_interno;
import FORMULARIO.ENTIDAD.caja_cierre_detalle;
import FORMULARIO.ENTIDAD.venta_interno;
import FORMULARIO.ENTIDAD.venta_item_interno;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_venta_interno {

    private DAO_venta_interno DAOveni = new DAO_venta_interno();
    private DAO_venta_item_interno DAOvenii = new DAO_venta_item_interno();
    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_venta_interno(venta_interno vein, caja_cierre_detalle ENTccd, JTable tbltabla) {
        String titulo = "insertar_venta_interno";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOveni.insertar_venta_interno(conn, vein);
            DAOvenii.insertar_venta_item_interno_de_tabla(conn, tbltabla, vein);
            DAOccd.insertar_caja_cierre_detalle1(conn, ENTccd);
//            DAOveni.actualizar_tabla_venta_interno(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, vein.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, vein.toString(), titulo);
            }
        }
    }

    public void update_venta_interno_anular(venta_interno vein, venta_item_interno item, caja_cierre_detalle ENTccd) {
        String titulo = "update_venta_interno_anular";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOveni.update_venta_interno(conn, vein);
            DAOccd.update_caja_cierre_detalle(conn, ENTccd);
            DAOvenii.update_venta_item_interno_ingresar_stock_producto(conn, item);
            DAOvenii.update_venta_item_interno_tipo_item(conn, item);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, vein.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, vein.toString(), titulo);
            }
        }
    }
}
