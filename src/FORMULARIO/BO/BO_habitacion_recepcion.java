	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_habitacion_recepcion;
	import FORMULARIO.ENTIDAD.habitacion_recepcion;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_habitacion_recepcion {
private DAO_habitacion_recepcion hare_dao = new DAO_habitacion_recepcion();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_habitacion_recepcion(habitacion_recepcion hare, JTable tbltabla) {
		String titulo = "insertar_habitacion_recepcion";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			hare_dao.insertar_habitacion_recepcion(conn, hare);
			hare_dao.actualizar_tabla_habitacion_recepcion(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, hare.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, hare.toString(), titulo);
			}
		}
	}
	public void update_habitacion_recepcion(habitacion_recepcion hare, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR HABITACION_RECEPCION", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_habitacion_recepcion";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				hare_dao.update_habitacion_recepcion(conn, hare);
				hare_dao.actualizar_tabla_habitacion_recepcion(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, hare.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, hare.toString(), titulo);
				}
			}
		}
	}
}
