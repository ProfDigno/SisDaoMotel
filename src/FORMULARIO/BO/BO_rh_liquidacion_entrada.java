	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_rh_liquidacion_entrada;
	import FORMULARIO.ENTIDAD.rh_liquidacion_entrada;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_rh_liquidacion_entrada {
private DAO_rh_liquidacion_entrada rhlien_dao = new DAO_rh_liquidacion_entrada();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_rh_liquidacion_entrada(rh_liquidacion_entrada rhlien, JTable tbltabla) {
		String titulo = "insertar_rh_liquidacion_entrada";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			rhlien_dao.insertar_rh_liquidacion_entrada(conn, rhlien);
			rhlien_dao.actualizar_tabla_rh_liquidacion_entrada(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, rhlien.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, rhlien.toString(), titulo);
			}
		}
	}
	public void update_rh_liquidacion_entrada(rh_liquidacion_entrada rhlien, JTable tbltabla) {
		if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR RH_LIQUIDACION_ENTRADA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_rh_liquidacion_entrada";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				rhlien_dao.update_rh_liquidacion_entrada(conn, rhlien);
				rhlien_dao.actualizar_tabla_rh_liquidacion_entrada(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, rhlien.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, rhlien.toString(), titulo);
				}
			}
		}
	}
}
