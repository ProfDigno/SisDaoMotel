package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_usuario_item_rol;
import FORMULARIO.ENTIDAD.usuario_item_rol;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_usuario_item_rol {

    private DAO_usuario_item_rol DAOuir = new DAO_usuario_item_rol();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_usuario_item_rol(usuario_item_rol usitro, JTable tbltabla) {
        String titulo = "insertar_usuario_item_rol";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOuir.insertar_usuario_item_rol(conn, usitro);
            DAOuir.actualizar_tabla_usuario_item_rol(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, usitro.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, usitro.toString(), titulo);
            }
        }
    }

    public void update_usuario_item_rol( JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR USUARIO_ITEM_ROL", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_usuario_item_rol";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOuir.update_item_usuario_rol_tabla(conn, tbltabla);
//                DAOuir.actualizar_tabla_usuario_item_rol(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, "update_item_usuario_rol_tabla", titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, "update_item_usuario_rol_tabla", titulo);
                }
            }
        }
    }
}
