	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_persona;
	import FORMULARIO.ENTIDAD.persona;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_persona {
private DAO_persona pe_dao = new DAO_persona();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_persona(persona pe, JTable tbltabla) {
		String titulo = "insertar_persona";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			pe_dao.insertar_persona(conn, pe);
			pe_dao.actualizar_tabla_persona(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, pe.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, pe.toString(), titulo);
			}
		}
	}
	public void update_persona(persona pe, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR PERSONA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_persona";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				pe_dao.update_persona(conn, pe);
				pe_dao.actualizar_tabla_persona(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, pe.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, pe.toString(), titulo);
				}
			}
		}
	}
}
