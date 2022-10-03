	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_usuario_formulario;
	import FORMULARIO.ENTIDAD.usuario_formulario;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_usuario_formulario {
private DAO_usuario_formulario usfo_dao = new DAO_usuario_formulario();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_usuario_formulario(usuario_formulario usfo, JTable tbltabla) {
		String titulo = "insertar_usuario_formulario";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			usfo_dao.insertar_usuario_formulario(conn, usfo);
			usfo_dao.actualizar_tabla_usuario_formulario(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, usfo.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, usfo.toString(), titulo);
			}
		}
	}
	public void update_usuario_formulario(usuario_formulario usfo, JTable tbltabla) {
		if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR USUARIO_FORMULARIO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_usuario_formulario";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				usfo_dao.update_usuario_formulario(conn, usfo);
				usfo_dao.actualizar_tabla_usuario_formulario(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, usfo.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, usfo.toString(), titulo);
				}
			}
		}
	}
}
