package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_factura;
import FORMULARIO.DAO.DAO_factura_item;
import FORMULARIO.ENTIDAD.factura;
import FORMULARIO.ENTIDAD.factura_item;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_factura {

    private DAO_factura DAOf = new DAO_factura();
    private DAO_factura_item DAOfi = new DAO_factura_item();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_factura(factura ENTf,factura_item ENTfi, JTable tbltabla) {
        String titulo = "insertar_factura";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOf.insertar_factura(conn, ENTf);
            DAOfi.insertar_factura_item(conn, ENTfi);
            if(tbltabla.getRowCount()>0){
                DAOfi.insertar_factura_item_de_tabla(conn, tbltabla, ENTfi, ENTf);
            }
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTf.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTf.toString(), titulo);
            }
        }
    }

    public void update_factura(factura f, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR FACTURA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_factura";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOf.update_factura(conn, f);
                DAOf.actualizar_tabla_factura(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, f.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, f.toString(), titulo);
                }
            }
        }
    }
    public void insertar_factura_de_factura(factura ENTf,factura_item ENTfi, JTable tbltabla) {
        String titulo = "insertar_factura_de_factura";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOf.insertar_factura(conn, ENTf);
            DAOfi.insertar_factura_item_de_factura(conn, tbltabla, ENTfi, ENTf);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTf.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTf.toString(), titulo);
            }
        }
    }
}
