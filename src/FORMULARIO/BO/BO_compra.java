	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_compra;
	import FORMULARIO.ENTIDAD.compra;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_compra {
private DAO_compra co_dao = new DAO_compra();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_compra(compra co, JTable tbltabla) {
		String titulo = "insertar_compra";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			co_dao.insertar_compra(conn, co);
			co_dao.actualizar_tabla_compra(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, co.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, co.toString(), titulo);
			}
		}
	}
	public void update_compra(compra co, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR COMPRA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_compra";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				co_dao.update_compra(conn, co);
				co_dao.actualizar_tabla_compra(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, co.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, co.toString(), titulo);
				}
			}
		}
	}
}
