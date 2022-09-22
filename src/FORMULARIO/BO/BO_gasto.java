package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_cierre_detalle;
import FORMULARIO.DAO.DAO_gasto;
import FORMULARIO.ENTIDAD.caja_cierre_detalle;
import FORMULARIO.ENTIDAD.gasto;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_gasto {

    private DAO_gasto DAOg = new DAO_gasto();
    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_gasto(gasto ga, caja_cierre_detalle ENTccd) {
        String titulo = "insertar_gasto";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOg.insertar_gasto(conn, ga);
            DAOccd.insertar_caja_cierre_detalle(conn, ENTccd);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ga.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ga.toString(), titulo);
            }
        }
    }

    public void update_gasto(gasto ga, caja_cierre_detalle ENTccd) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE ANULAR ESTE GASTO", "ANULAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_gasto";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOg.update_gasto(conn, ga);
                DAOccd.update_caja_cierre_detalle(conn, ENTccd);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ga.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ga.toString(), titulo);
                }
            }
        }
    }
}
