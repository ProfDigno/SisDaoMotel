package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_patrimonio_baja;
import FORMULARIO.DAO.DAO_patrimonio_baja_item;
import FORMULARIO.DAO.DAO_patrimonio_producto;
import FORMULARIO.ENTIDAD.patrimonio_baja;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_patrimonio_baja {

    private DAO_patrimonio_baja DAOpb = new DAO_patrimonio_baja();
    private DAO_patrimonio_baja_item DAOpbi = new DAO_patrimonio_baja_item();
    private DAO_patrimonio_producto DAOpp=new DAO_patrimonio_producto();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_patrimonio_baja(patrimonio_baja ENTpb, JTable tbltabla) {
        String titulo = "insertar_patrimonio_baja";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOpb.insertar_patrimonio_baja(conn, ENTpb);
            DAOpbi.insertar_patrimonio_baja_item_de_tabla(conn, tbltabla, ENTpb);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTpb.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTpb.toString(), titulo);
            }
        }
    }

    public void update_patrimonio_baja_anular(patrimonio_baja ENTpb) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE ANULAR PATRIMONIO_BAJA", "ANULAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_patrimonio_baja";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOpb.update_patrimonio_baja_anular(conn, ENTpb);
                DAOpp.update_patrimonio_producto_anular_baja(conn, ENTpb);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ENTpb.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ENTpb.toString(), titulo);
                }
            }
        }
    }
}
