	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_habitacion_arduino;
	import FORMULARIO.ENTIDAD.habitacion_arduino;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_habitacion_arduino {
private DAO_habitacion_arduino haar_dao = new DAO_habitacion_arduino();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_habitacion_arduino(habitacion_arduino haar, JTable tbltabla) {
		String titulo = "insertar_habitacion_arduino";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			haar_dao.insertar_habitacion_arduino(conn, haar);
			haar_dao.actualizar_tabla_habitacion_arduino(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, haar.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, haar.toString(), titulo);
			}
		}
	}
	public void update_habitacion_arduino(habitacion_arduino haar, JTable tbltabla) {
		if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR HABITACION_ARDUINO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_habitacion_arduino";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				haar_dao.update_habitacion_arduino(conn, haar);
				haar_dao.actualizar_tabla_habitacion_arduino(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, haar.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, haar.toString(), titulo);
				}
			}
		}
	}
}
