package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_patrimonio_producto;
import FORMULARIO.ENTIDAD.patrimonio_producto;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_patrimonio_producto {

    private DAO_patrimonio_producto pp_dao = new DAO_patrimonio_producto();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_patrimonio_producto(patrimonio_producto ENTpp, JTable tbltabla) {
        String titulo = "insertar_patrimonio_producto";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pp_dao.insertar_patrimonio_producto(conn, ENTpp);
            pp_dao.actualizar_tabla_patrimonio_producto(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTpp.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTpp.toString(), titulo);
            }
        }
    }

    public void update_patrimonio_producto(patrimonio_producto ENTpp, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR PATRIMONIO_PRODUCTO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_patrimonio_producto";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                pp_dao.update_patrimonio_producto(conn, ENTpp);
                pp_dao.actualizar_tabla_patrimonio_producto(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ENTpp.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ENTpp.toString(), titulo);
                }
            }
        }
    }
}
