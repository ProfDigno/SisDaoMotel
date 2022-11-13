package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_cierre_detalle;
import FORMULARIO.DAO.DAO_habitacion_recepcion;
import FORMULARIO.DAO.DAO_habitacion_recepcion_temp;
import FORMULARIO.DAO.DAO_venta;
import FORMULARIO.ENTIDAD.caja_cierre_detalle;
import FORMULARIO.ENTIDAD.habitacion_recepcion;
import FORMULARIO.ENTIDAD.habitacion_recepcion_temp;
import FORMULARIO.ENTIDAD.venta;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_venta {

    private DAO_venta DAOven = new DAO_venta();
    private DAO_habitacion_recepcion_temp DAOhrt = new DAO_habitacion_recepcion_temp();
    private DAO_habitacion_recepcion DAOhr = new DAO_habitacion_recepcion();
    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_venta(venta ve, habitacion_recepcion ENThr, habitacion_recepcion_temp ENThrt) {
        String titulo = "insertar_venta";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOhr.insertar_habitacion_recepcion(conn, ENThr);
            DAOven.insertar_venta(conn, ve);
            DAOhrt.update_habitacion_recepcion_temp(conn, ENThrt);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ve.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ve.toString(), titulo);
            }
        }
    }

    public void update_venta1(venta ve, habitacion_recepcion ENThr, habitacion_recepcion_temp ENThrt, caja_cierre_detalle ENTccd,
            boolean es_conventa, boolean es_caja, boolean up_caja) {
        String titulo = "update_venta";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            if (es_conventa) {
                DAOven.update_venta(conn, ve);
            }
            DAOhr.update_habitacion_recepcion(conn, ENThr);
            DAOhrt.update_habitacion_recepcion_temp(conn, ENThrt);
            if (es_caja) {
                DAOccd.insertar_caja_cierre_detalle1(conn, ENTccd);
            }
            if (up_caja) {
                if (ENTccd.getC1idcaja_cierre_detalle() > 0) {
                    DAOccd.update_caja_cierre_detalle(conn, ENTccd);
                }
            }
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ve.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ve.toString(), titulo);
            }
        }
    }

    public void update_venta_sin_commit1(Connection conn, venta ve, habitacion_recepcion ENThr, habitacion_recepcion_temp ENThrt, caja_cierre_detalle ENTccd,
            boolean es_conventa, boolean es_caja, boolean up_caja) {
        String titulo = "update_venta_sin_commit";
        if (es_conventa) {
            DAOven.update_venta(conn, ve);
        }
        DAOhr.update_habitacion_recepcion(conn, ENThr);
        DAOhrt.update_habitacion_recepcion_temp(conn, ENThrt);
        if (es_caja) {
            DAOccd.insertar_caja_cierre_detalle1(conn, ENTccd);
        }
        if (up_caja) {
            if (ENTccd.getC1idcaja_cierre_detalle() > 0) {
                DAOccd.update_caja_cierre_detalle(conn, ENTccd);
            }
        }
    }

    public void insertar_venta_sin_commit(Connection conn, venta ve, habitacion_recepcion ENThr, habitacion_recepcion_temp ENThrt,
           caja_cierre_detalle ENTccd ) {
        String titulo = "insertar_venta";
        DAOhr.insertar_habitacion_recepcion(conn, ENThr);
        DAOven.insertar_venta(conn, ve);
        DAOhrt.update_habitacion_recepcion_temp(conn, ENThrt);
        if (ENTccd.getC1idcaja_cierre_detalle() > 0) {
                DAOccd.update_caja_cierre_detalle(conn, ENTccd);
            }
    }
}
