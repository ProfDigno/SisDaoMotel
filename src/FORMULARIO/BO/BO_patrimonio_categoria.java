package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_patrimonio_categoria;
import FORMULARIO.ENTIDAD.patrimonio_categoria;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_patrimonio_categoria {

    private DAO_patrimonio_categoria pc_dao = new DAO_patrimonio_categoria();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_patrimonio_categoria(patrimonio_categoria ENTpc, JTable tbltabla) {
        String titulo = "insertar_patrimonio_categoria";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pc_dao.insertar_patrimonio_categoria(conn, ENTpc);
            pc_dao.actualizar_tabla_patrimonio_categoria(conn, tbltabla);
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

    public void update_patrimonio_categoria(patrimonio_categoria ENTpc, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR PATRIMONIO_CATEGORIA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_patrimonio_categoria";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                pc_dao.update_patrimonio_categoria(conn, ENTpc);
                pc_dao.actualizar_tabla_patrimonio_categoria(conn, tbltabla);
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
