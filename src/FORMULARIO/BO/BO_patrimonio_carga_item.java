package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_patrimonio_carga_item;
import FORMULARIO.ENTIDAD.patrimonio_carga_item;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_patrimonio_carga_item {

    private DAO_patrimonio_carga_item pci_dao = new DAO_patrimonio_carga_item();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_patrimonio_carga_item(patrimonio_carga_item ENTpci, JTable tbltabla) {
        String titulo = "insertar_patrimonio_carga_item";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pci_dao.insertar_patrimonio_carga_item(conn, ENTpci);
//            pci_dao.actualizar_tabla_patrimonio_carga_item(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTpci.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTpci.toString(), titulo);
            }
        }
    }

    public void update_patrimonio_carga_item(patrimonio_carga_item ENTpci, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR PATRIMONIO_CARGA_ITEM", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_patrimonio_carga_item";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                pci_dao.update_patrimonio_carga_item(conn, ENTpci);
//                pci_dao.actualizar_tabla_patrimonio_carga_item(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ENTpci.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ENTpci.toString(), titulo);
                }
            }
        }
    }
}
