	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_habitacion_estado_gpio_temp;
	import FORMULARIO.ENTIDAD.habitacion_estado_gpio_temp;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_habitacion_estado_gpio_temp {
private DAO_habitacion_estado_gpio_temp haesgpte_dao = new DAO_habitacion_estado_gpio_temp();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_habitacion_estado_gpio_temp(habitacion_estado_gpio_temp haesgpte, JTable tbltabla) {
		String titulo = "insertar_habitacion_estado_gpio_temp";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			haesgpte_dao.insertar_habitacion_estado_gpio_temp(conn, haesgpte);
			haesgpte_dao.actualizar_tabla_habitacion_estado_gpio_temp(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, haesgpte.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, haesgpte.toString(), titulo);
			}
		}
	}
	public void update_habitacion_estado_gpio_temp(habitacion_estado_gpio_temp haesgpte, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR HABITACION_ESTADO_GPIO_TEMP", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_habitacion_estado_gpio_temp";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				haesgpte_dao.update_habitacion_estado_gpio_temp(conn, haesgpte);
				haesgpte_dao.actualizar_tabla_habitacion_estado_gpio_temp(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, haesgpte.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, haesgpte.toString(), titulo);
				}
			}
		}
	}
}
