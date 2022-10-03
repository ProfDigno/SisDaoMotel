	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_usuario;
	import FORMULARIO.ENTIDAD.usuario;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_usuario {
private DAO_usuario us_dao = new DAO_usuario();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_usuario(usuario us, JTable tbltabla) {
		String titulo = "insertar_usuario";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			us_dao.insertar_usuario(conn, us);
			us_dao.actualizar_tabla_usuario(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, us.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, us.toString(), titulo);
			}
		}
	}
	public void update_usuario(usuario us, JTable tbltabla) {
		if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR USUARIO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_usuario";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				us_dao.update_usuario(conn, us);
				us_dao.actualizar_tabla_usuario(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, us.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, us.toString(), titulo);
				}
			}
		}
	}
}
