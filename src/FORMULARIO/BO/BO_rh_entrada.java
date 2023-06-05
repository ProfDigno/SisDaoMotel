package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_rh_entrada;
import FORMULARIO.DAO.DAO_rh_liquidacion_entrada;
import FORMULARIO.ENTIDAD.rh_entrada;
import FORMULARIO.ENTIDAD.rh_liquidacion_entrada;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_rh_entrada {

    private DAO_rh_entrada re_dao = new DAO_rh_entrada();
    private DAO_rh_liquidacion_entrada DAOrhle=new DAO_rh_liquidacion_entrada();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_rh_entrada(rh_entrada re,rh_liquidacion_entrada ENTrhle) {
        String titulo = "insertar_rh_entrada";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            re_dao.insertar_rh_entrada(conn, re);
            DAOrhle.insertar_rh_liquidacion_entrada(conn, ENTrhle);
//            re_dao.actualizar_tabla_rh_entrada(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, re.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, re.toString(), titulo);
            }
        }
    }

    public void update_rh_entrada(rh_entrada re) {
//        if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR RH_ENTRADA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_rh_entrada";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                re_dao.update_rh_entrada_salir(conn, re);
//                re_dao.actualizar_tabla_rh_entrada(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, re.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, re.toString(), titulo);
                }
            }
//        }
    }
}
