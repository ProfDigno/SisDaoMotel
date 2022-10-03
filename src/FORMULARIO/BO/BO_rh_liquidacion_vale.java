	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_rh_liquidacion_vale;
	import FORMULARIO.ENTIDAD.rh_liquidacion_vale;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_rh_liquidacion_vale {
private DAO_rh_liquidacion_vale rhliva_dao = new DAO_rh_liquidacion_vale();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_rh_liquidacion_vale(rh_liquidacion_vale rhliva, JTable tbltabla) {
		String titulo = "insertar_rh_liquidacion_vale";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			rhliva_dao.insertar_rh_liquidacion_vale(conn, rhliva);
			rhliva_dao.actualizar_tabla_rh_liquidacion_vale(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, rhliva.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, rhliva.toString(), titulo);
			}
		}
	}
	public void update_rh_liquidacion_vale(rh_liquidacion_vale rhliva, JTable tbltabla) {
		if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR RH_LIQUIDACION_VALE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_rh_liquidacion_vale";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				rhliva_dao.update_rh_liquidacion_vale(conn, rhliva);
				rhliva_dao.actualizar_tabla_rh_liquidacion_vale(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, rhliva.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, rhliva.toString(), titulo);
				}
			}
		}
	}
}
