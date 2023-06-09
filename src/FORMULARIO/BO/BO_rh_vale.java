package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_cierre_detalle;
import FORMULARIO.DAO.DAO_rh_liquidacion_detalle;
import FORMULARIO.DAO.DAO_rh_liquidacion_vale;
import FORMULARIO.DAO.DAO_rh_vale;
import FORMULARIO.ENTIDAD.caja_cierre_detalle;
import FORMULARIO.ENTIDAD.rh_liquidacion_detalle;
import FORMULARIO.ENTIDAD.rh_liquidacion_vale;
import FORMULARIO.ENTIDAD.rh_vale;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_rh_vale {

    private DAO_rh_vale DAOrhv = new DAO_rh_vale();
    private DAO_rh_liquidacion_vale DAOrhlv = new DAO_rh_liquidacion_vale();
    private DAO_rh_liquidacion_detalle DAOrhld = new DAO_rh_liquidacion_detalle();
    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_rh_vale(rh_vale ENTrhv,rh_liquidacion_vale ENTrhlv,rh_liquidacion_detalle ENTrhld) {
        String titulo = "insertar_rh_vale";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOrhv.insertar_rh_vale(conn, ENTrhv);
            DAOrhlv.insertar_rh_liquidacion_vale(conn, ENTrhlv);
            DAOrhld.insertar_rh_liquidacion_detalle(conn, ENTrhld);
//            DAOrhv.actualizar_tabla_rh_vale(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTrhv.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTrhv.toString(), titulo);
            }
        }
    }
    public void insertar_rh_vale_caja(rh_vale ENTrhv,rh_liquidacion_vale ENTrhlv,rh_liquidacion_detalle ENTrhld,caja_cierre_detalle ENTccd) {
        String titulo = "insertar_rh_vale";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOrhv.insertar_rh_vale(conn, ENTrhv);
            DAOrhlv.insertar_rh_liquidacion_vale(conn, ENTrhlv);
            DAOrhld.insertar_rh_liquidacion_detalle(conn, ENTrhld);
            DAOccd.insertar_caja_cierre_detalle(conn, ENTccd);
//            DAOrhv.actualizar_tabla_rh_vale(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTrhv.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTrhv.toString(), titulo);
            }
        }
    }
    public void update_rh_vale(rh_vale rhva, JTable tbltabla) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR RH_VALE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_rh_vale";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOrhv.update_rh_vale(conn, rhva);
                DAOrhv.actualizar_tabla_rh_vale(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, rhva.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, rhva.toString(), titulo);
                }
            }
        }
    }
    public void update_rh_vale_anular(rh_vale rhva,rh_liquidacion_detalle ENTrhld,caja_cierre_detalle ENTccd) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE ANULAR ESTE RH_VALE", "ANULAR VALE", "ANULAR", "CANCELAR")) {
            String titulo = "update_rh_vale_anular";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOrhv.update_rh_vale_anular(conn, rhva);
                DAOrhld.update_rh_liquidacion_detalle_anular_vale(conn, ENTrhld);
                DAOccd.update_caja_cierre_detalle_anular_vale(conn, ENTccd);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, rhva.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, rhva.toString(), titulo);
                }
            }
        }
    }
}
