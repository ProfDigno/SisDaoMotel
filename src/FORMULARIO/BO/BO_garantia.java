package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_cierre_detalle;
import FORMULARIO.DAO.DAO_garantia;
import FORMULARIO.ENTIDAD.caja_cierre_detalle;
import FORMULARIO.ENTIDAD.garantia;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_garantia {

    private DAO_garantia DAOgar = new DAO_garantia();
    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_garantia(garantia ga, caja_cierre_detalle ENTccd) {
        String titulo = "insertar_garantia";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOgar.insertar_garantia(conn, ga);
            DAOccd.insertar_caja_cierre_detalle(conn, ENTccd);
//            ga_dao.actualizar_tabla_garantia(conn, tbltabla);
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

    public void update_garantia(garantia ga, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR GARANTIA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_garantia";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOgar.update_garantia(conn, ga);
                DAOgar.actualizar_tabla_garantia(conn, tbltabla);
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
