package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_patrimonio_baja_motivo;
import FORMULARIO.ENTIDAD.patrimonio_baja_motivo;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_patrimonio_baja_motivo {

    private DAO_patrimonio_baja_motivo pbm_dao = new DAO_patrimonio_baja_motivo();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_patrimonio_baja_motivo(patrimonio_baja_motivo ENTpbm, JTable tbltabla) {
        String titulo = "insertar_patrimonio_baja_motivo";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pbm_dao.insertar_patrimonio_baja_motivo(conn, ENTpbm);
            pbm_dao.actualizar_tabla_patrimonio_baja_motivo(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTpbm.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTpbm.toString(), titulo);
            }
        }
    }

    public void update_patrimonio_baja_motivo(patrimonio_baja_motivo ENTpbm, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR PATRIMONIO_BAJA_MOTIVO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_patrimonio_baja_motivo";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                pbm_dao.update_patrimonio_baja_motivo(conn, ENTpbm);
                pbm_dao.actualizar_tabla_patrimonio_baja_motivo(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ENTpbm.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ENTpbm.toString(), titulo);
                }
            }
        }
    }
}
