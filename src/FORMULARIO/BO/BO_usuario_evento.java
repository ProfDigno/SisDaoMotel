	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_usuario_evento;
	import FORMULARIO.ENTIDAD.usuario_evento;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_usuario_evento {
private DAO_usuario_evento usev_dao = new DAO_usuario_evento();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_usuario_evento(usuario_evento usev, JTable tbltabla) {
		String titulo = "insertar_usuario_evento";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			usev_dao.insertar_usuario_evento(conn, usev);
			usev_dao.actualizar_tabla_usuario_evento(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, usev.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, usev.toString(), titulo);
			}
		}
	}
	public void update_usuario_evento(usuario_evento usev, JTable tbltabla) {
		if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR USUARIO_EVENTO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_usuario_evento";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				usev_dao.update_usuario_evento(conn, usev);
				usev_dao.actualizar_tabla_usuario_evento(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, usev.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, usev.toString(), titulo);
				}
			}
		}
	}
}
