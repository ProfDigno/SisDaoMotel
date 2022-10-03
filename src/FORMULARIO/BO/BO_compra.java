package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_cierre_detalle;
import FORMULARIO.DAO.DAO_compra;
import FORMULARIO.DAO.DAO_compra_item;
import FORMULARIO.ENTIDAD.caja_cierre_detalle;
import FORMULARIO.ENTIDAD.compra;
import FORMULARIO.ENTIDAD.compra_item;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_compra {

    private DAO_compra DAOcom = new DAO_compra();
    private DAO_compra_item DAOcomi = new DAO_compra_item();
    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_compra(compra ENTcom, JTable tblitem_producto) {
        String titulo = "insertar_compra";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOcom.insertar_compra(conn, ENTcom);
            DAOcomi.insertar_item_compra_de_tabla(conn, tblitem_producto, ENTcom);
//            DAOccd.insertar_caja_cierre_detalle(conn, ENTccd);
//            co_dao.actualizar_tabla_compra(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTcom.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTcom.toString(), titulo);
            }
        }
    }

    public void update_compra_estado_pagado(compra ENTcom, caja_cierre_detalle ENTccd, compra_item ENTcomi) {
        String titulo = "update_compra_estado_pagado";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOcom.update_compra(conn, ENTcom);
            DAOccd.insertar_caja_cierre_detalle(conn, ENTccd);
            DAOcomi.update_compra_item_ingresar_stock_producto(conn, ENTcomi);
            DAOcomi.update_compra_item_tipo_item(conn, ENTcomi);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTcom.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTcom.toString(), titulo);
            }
        }
    }
    public void update_compra_estado_anulado(compra ENTcom, compra_item ENTcomi) {
        String titulo = "update_compra_estado_anulado";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOcom.update_compra(conn, ENTcom);
            DAOcomi.update_compra_item_tipo_item(conn, ENTcomi);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTcom.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTcom.toString(), titulo);
            }
        }
    }
}
