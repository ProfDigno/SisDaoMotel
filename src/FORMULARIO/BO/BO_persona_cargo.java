	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_persona_cargo;
	import FORMULARIO.ENTIDAD.persona_cargo;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_persona_cargo {
private DAO_persona_cargo peca_dao = new DAO_persona_cargo();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_persona_cargo(persona_cargo peca, JTable tbltabla) {
		String titulo = "insertar_persona_cargo";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			peca_dao.insertar_persona_cargo(conn, peca);
			peca_dao.actualizar_tabla_persona_cargo(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, peca.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, peca.toString(), titulo);
			}
		}
	}
	public void update_persona_cargo(persona_cargo peca, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR PERSONA_CARGO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_persona_cargo";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				peca_dao.update_persona_cargo(conn, peca);
				peca_dao.actualizar_tabla_persona_cargo(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, peca.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, peca.toString(), titulo);
				}
			}
		}
	}
}
