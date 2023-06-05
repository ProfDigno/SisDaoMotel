package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_rh_descuento;
import FORMULARIO.DAO.DAO_rh_liquidacion_descuento;
import FORMULARIO.DAO.DAO_rh_liquidacion_detalle;
import FORMULARIO.ENTIDAD.rh_descuento;
import FORMULARIO.ENTIDAD.rh_liquidacion_descuento;
import FORMULARIO.ENTIDAD.rh_liquidacion_detalle;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_rh_descuento {

    private DAO_rh_descuento DAOrhd = new DAO_rh_descuento();
    private DAO_rh_liquidacion_descuento DAOrhlv = new DAO_rh_liquidacion_descuento();
    private DAO_rh_liquidacion_detalle DAOrhlde = new DAO_rh_liquidacion_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_rh_descuento(rh_descuento ENTrhd,rh_liquidacion_descuento ENTrhld,rh_liquidacion_detalle ENTrhlde) {
        String titulo = "insertar_rh_descuento";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOrhd.insertar_rh_descuento(conn, ENTrhd);
            DAOrhlv.insertar_rh_liquidacion_descuento(conn, ENTrhld);
            DAOrhlde.insertar_rh_liquidacion_detalle(conn, ENTrhlde);
//            rhde_dao.actualizar_tabla_rh_descuento(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTrhd.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTrhd.toString(), titulo);
            }
        }
    }

    public void update_rh_descuento(rh_descuento rhde, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR RH_DESCUENTO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_rh_descuento";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOrhd.update_rh_descuento(conn, rhde);
                DAOrhd.actualizar_tabla_rh_descuento(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, rhde.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, rhde.toString(), titulo);
                }
            }
        }
    }
}
