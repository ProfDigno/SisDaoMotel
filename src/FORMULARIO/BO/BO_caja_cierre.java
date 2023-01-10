package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_cierre;
import FORMULARIO.DAO.DAO_caja_cierre_detalle;
import FORMULARIO.DAO.DAO_caja_cierre_item;
import FORMULARIO.DAO.DAO_caja_producto_item;
import FORMULARIO.DAO.DAO_compra;
import FORMULARIO.DAO.DAO_garantia;
import FORMULARIO.DAO.DAO_gasto;
import FORMULARIO.DAO.DAO_venta;
import FORMULARIO.DAO.DAO_venta_interno;
import FORMULARIO.ENTIDAD.caja_cierre;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_caja_cierre {

    private DAO_caja_cierre DAOcc = new DAO_caja_cierre();
    private DAO_caja_cierre_item DAOcci = new DAO_caja_cierre_item();
    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    private DAO_venta DAOven = new DAO_venta();
    private DAO_gasto DAOg = new DAO_gasto();
    private DAO_compra DAOcom=new DAO_compra();
    private DAO_venta_interno DAOveni=new DAO_venta_interno();
    private DAO_garantia DAOgar=new DAO_garantia();
    private DAO_caja_producto_item DAOcpi = new DAO_caja_producto_item();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_caja_cierre(caja_cierre caci) {
        String titulo = "insertar_caja_cierre";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOcc.insertar_caja_cierre(conn, caci);
            DAOcci.insertar_tabla_caja_cierre_item_CERRAR(conn, caci.getC3creado_por(), caci.getC7fk_idusuario());
            DAOcpi.insertar_caja_producto_item_por_select_todos(conn, caci.getC3creado_por(), caci.getC7fk_idusuario());
            DAOccd.cerrar_todo_caja_detalle(conn,caci.getC7fk_idusuario());
            DAOven.terminar_venta_en_caja(conn,caci.getC1idcaja_cierre());
            DAOg.terminar_gasto_en_caja(conn,caci.getC1idcaja_cierre());
            DAOcom.terminar_compra_en_caja(conn,caci.getC1idcaja_cierre());
            DAOveni.terminar_venta_interno_en_caja(conn, caci.getC1idcaja_cierre());
            DAOgar.terminar_garantia_en_caja(conn,caci.getC1idcaja_cierre());
            DAOcpi.update_caja_producto_item_stock_venta(conn,caci.getC1idcaja_cierre());
            DAOcpi.update_caja_producto_item_stock_venta_interna(conn,caci.getC1idcaja_cierre());
            DAOcpi.update_caja_producto_item_stock_compra(conn,caci.getC1idcaja_cierre());
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, caci.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, caci.toString(), titulo);
            }
        }
    }

    public void update_caja_cierre(caja_cierre caci, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR CAJA_CIERRE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_caja_cierre";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOcc.update_caja_cierre(conn, caci);
//                DAOcc.actualizar_tabla_caja_cierre(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, caci.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, caci.toString(), titulo);
                }
            }
        }
    }
    
}
