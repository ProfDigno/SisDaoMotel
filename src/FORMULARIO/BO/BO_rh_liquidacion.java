package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_rh_liquidacion;
import FORMULARIO.ENTIDAD.rh_liquidacion;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_rh_liquidacion {

    private DAO_rh_liquidacion DAOrhl = new DAO_rh_liquidacion();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_rh_liquidacion(rh_liquidacion ENTrhl, JTable tbltabla) {
        String titulo = "insertar_rh_liquidacion";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOrhl.insertar_rh_liquidacion(conn, ENTrhl);
            DAOrhl.actualizar_tabla_rh_liquidacion(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTrhl.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTrhl.toString(), titulo);
            }
        }
    }

    public void update_rh_liquidacion_cerrar(rh_liquidacion ENTrhl,rh_liquidacion ENTrhl2) {
        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE CERRAR RH_LIQUIDACION", "CERRAR LIQUIDACION", "CERRAR", "CANCELAR")) {
            String titulo = "update_rh_liquidacion_cerrar";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOrhl.update_rh_liquidacion(conn, ENTrhl);
                DAOrhl.cerrar_entradar_de_liquidacion(conn, ENTrhl);
                DAOrhl.cerrar_vale_de_liquidacion(conn, ENTrhl);
                DAOrhl.cerrar_descuento_de_liquidacion(conn, ENTrhl);
                DAOrhl.insertar_rh_liquidacion(conn, ENTrhl2);
//                DAOrhl.actualizar_tabla_rh_liquidacion(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ENTrhl.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ENTrhl.toString(), titulo);
                }
            }
        }
    }
}
