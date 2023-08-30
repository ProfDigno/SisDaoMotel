package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_patrimonio_ubicacion;
import FORMULARIO.ENTIDAD.patrimonio_ubicacion;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_patrimonio_ubicacion {

    private DAO_patrimonio_ubicacion pu_dao = new DAO_patrimonio_ubicacion();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_patrimonio_ubicacion(patrimonio_ubicacion ENTpu, JTable tbltabla) {
        String titulo = "insertar_patrimonio_ubicacion";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pu_dao.insertar_patrimonio_ubicacion(conn, ENTpu);
            pu_dao.actualizar_tabla_patrimonio_ubicacion(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTpu.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTpu.toString(), titulo);
            }
        }
    }

    public void update_patrimonio_ubicacion(patrimonio_ubicacion ENTpu, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR PATRIMONIO_UBICACION", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_patrimonio_ubicacion";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                pu_dao.update_patrimonio_ubicacion(conn, ENTpu);
                pu_dao.actualizar_tabla_patrimonio_ubicacion(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ENTpu.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ENTpu.toString(), titulo);
                }
            }
        }
    }
}
