package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_patrimonio_carga;
import FORMULARIO.DAO.DAO_patrimonio_carga_item;
import FORMULARIO.DAO.DAO_patrimonio_producto;
import FORMULARIO.ENTIDAD.patrimonio_carga;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_patrimonio_carga {

    private DAO_patrimonio_carga DAOpc = new DAO_patrimonio_carga();
    private DAO_patrimonio_carga_item DAOpci=new DAO_patrimonio_carga_item();
    private DAO_patrimonio_producto DAOpp=new DAO_patrimonio_producto();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_patrimonio_carga(patrimonio_carga ENTpc, JTable tbltabla) {
        String titulo = "insertar_patrimonio_carga";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOpc.insertar_patrimonio_carga(conn, ENTpc);
            DAOpci.insertar_patrimonio_carga_item_de_tabla(conn, tbltabla, ENTpc);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTpc.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTpc.toString(), titulo);
            }
        }
    }

    public void update_patrimonio_carga_anular(patrimonio_carga ENTpc) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE ANULAR PATRIMONIO_CARGA", "ANULAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_patrimonio_carga_anular";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOpc.update_patrimonio_carga_anular(conn, ENTpc);
                DAOpp.update_patrimonio_producto_anular_carga(conn, ENTpc);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ENTpc.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ENTpc.toString(), titulo);
                }
            }
        }
    }
}
