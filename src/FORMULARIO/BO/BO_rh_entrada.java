	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_rh_entrada;
	import FORMULARIO.ENTIDAD.rh_entrada;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_rh_entrada {
private DAO_rh_entrada rhen_dao = new DAO_rh_entrada();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_rh_entrada(rh_entrada rhen, JTable tbltabla) {
		String titulo = "insertar_rh_entrada";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			rhen_dao.insertar_rh_entrada(conn, rhen);
			rhen_dao.actualizar_tabla_rh_entrada(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, rhen.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, rhen.toString(), titulo);
			}
		}
	}
	public void update_rh_entrada(rh_entrada rhen, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR RH_ENTRADA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_rh_entrada";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				rhen_dao.update_rh_entrada(conn, rhen);
				rhen_dao.actualizar_tabla_rh_entrada(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, rhen.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, rhen.toString(), titulo);
				}
			}
		}
	}
}
