package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_usuario_evento;
import FORMULARIO.DAO.DAO_usuario_item_rol;
import FORMULARIO.ENTIDAD.usuario_evento;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_usuario_evento {

    private DAO_usuario_evento DAOue = new DAO_usuario_evento();
    private DAO_usuario_item_rol DAOuir=new DAO_usuario_item_rol();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_usuario_evento(usuario_evento usev, JTable tbltabla) {
        String titulo = "insertar_usuario_evento";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOue.insertar_usuario_evento(conn, usev);
            DAOuir.insertar_usuario_item_rol_por_usuario_rol(conn);
            DAOue.actualizar_tabla_usuario_evento(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, usev.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, usev.toString(), titulo);
            }
        }
    }

    public void update_usuario_evento(usuario_evento usev, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR USUARIO_EVENTO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_usuario_evento";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOue.update_usuario_evento(conn, usev);
                DAOue.actualizar_tabla_usuario_evento(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, usev.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, usev.toString(), titulo);
                }
            }
        }
    }
}
