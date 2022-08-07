	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_habitacion_sensor;
	import FORMULARIO.ENTIDAD.habitacion_sensor;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_habitacion_sensor {
private DAO_habitacion_sensor hase_dao = new DAO_habitacion_sensor();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_habitacion_sensor(habitacion_sensor hase, JTable tbltabla) {
		String titulo = "insertar_habitacion_sensor";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			hase_dao.insertar_habitacion_sensor(conn, hase);
			hase_dao.actualizar_tabla_habitacion_sensor(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, hase.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, hase.toString(), titulo);
			}
		}
	}
	public void update_habitacion_sensor(habitacion_sensor hase, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR HABITACION_SENSOR", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_habitacion_sensor";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				hase_dao.update_habitacion_sensor(conn, hase);
				hase_dao.actualizar_tabla_habitacion_sensor(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, hase.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, hase.toString(), titulo);
				}
			}
		}
	}
}
