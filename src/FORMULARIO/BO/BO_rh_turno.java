	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_rh_turno;
	import FORMULARIO.ENTIDAD.rh_turno;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_rh_turno {
private DAO_rh_turno rhtu_dao = new DAO_rh_turno();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_rh_turno(rh_turno rhtu, JTable tbltabla) {
		String titulo = "insertar_rh_turno";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			rhtu_dao.insertar_rh_turno(conn, rhtu);
			rhtu_dao.actualizar_tabla_rh_turno(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, rhtu.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, rhtu.toString(), titulo);
			}
		}
	}
	public void update_rh_turno(rh_turno rhtu, JTable tbltabla) {
		if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR RH_TURNO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_rh_turno";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				rhtu_dao.update_rh_turno(conn, rhtu);
				rhtu_dao.actualizar_tabla_rh_turno(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, rhtu.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, rhtu.toString(), titulo);
				}
			}
		}
	}
}
