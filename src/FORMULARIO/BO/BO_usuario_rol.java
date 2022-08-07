	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_usuario_rol;
	import FORMULARIO.ENTIDAD.usuario_rol;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_usuario_rol {
private DAO_usuario_rol usro_dao = new DAO_usuario_rol();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_usuario_rol(usuario_rol usro, JTable tbltabla) {
		String titulo = "insertar_usuario_rol";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			usro_dao.insertar_usuario_rol(conn, usro);
			usro_dao.actualizar_tabla_usuario_rol(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, usro.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, usro.toString(), titulo);
			}
		}
	}
	public void update_usuario_rol(usuario_rol usro, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR USUARIO_ROL", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_usuario_rol";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				usro_dao.update_usuario_rol(conn, usro);
				usro_dao.actualizar_tabla_usuario_rol(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, usro.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, usro.toString(), titulo);
				}
			}
		}
	}
}
