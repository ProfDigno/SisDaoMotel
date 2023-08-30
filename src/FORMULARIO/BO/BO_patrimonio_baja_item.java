package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_patrimonio_baja_item;
import FORMULARIO.ENTIDAD.patrimonio_baja_item;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_patrimonio_baja_item {

    private DAO_patrimonio_baja_item pbi_dao = new DAO_patrimonio_baja_item();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_patrimonio_baja_item(patrimonio_baja_item ENTpbi, JTable tbltabla) {
        String titulo = "insertar_patrimonio_baja_item";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pbi_dao.insertar_patrimonio_baja_item(conn, ENTpbi);
//			pbi_dao.actualizar_tabla_patrimonio_baja_item(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTpbi.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTpbi.toString(), titulo);
            }
        }
    }

    public void update_patrimonio_baja_item(patrimonio_baja_item ENTpbi, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR PATRIMONIO_BAJA_ITEM", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_patrimonio_baja_item";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                pbi_dao.update_patrimonio_baja_item(conn, ENTpbi);
//				pbi_dao.actualizar_tabla_patrimonio_baja_item(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ENTpbi.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ENTpbi.toString(), titulo);
                }
            }
        }
    }
}
