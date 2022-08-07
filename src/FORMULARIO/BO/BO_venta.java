	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_venta;
	import FORMULARIO.ENTIDAD.venta;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_venta {
private DAO_venta ve_dao = new DAO_venta();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_venta(venta ve, JTable tbltabla) {
		String titulo = "insertar_venta";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			ve_dao.insertar_venta(conn, ve);
			ve_dao.actualizar_tabla_venta(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, ve.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, ve.toString(), titulo);
			}
		}
	}
	public void update_venta(venta ve, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR VENTA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_venta";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				ve_dao.update_venta(conn, ve);
				ve_dao.actualizar_tabla_venta(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, ve.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, ve.toString(), titulo);
				}
			}
		}
	}
}
