	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_venta_item_eliminar;
	import FORMULARIO.ENTIDAD.venta_item_eliminar;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_venta_item_eliminar {
private DAO_venta_item_eliminar vie_dao = new DAO_venta_item_eliminar();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_venta_item_eliminar(venta_item_eliminar vie, JTable tbltabla) {
		String titulo = "insertar_venta_item_eliminar";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			vie_dao.insertar_venta_item_eliminar(conn, vie);
			vie_dao.actualizar_tabla_venta_item_eliminar(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, vie.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, vie.toString(), titulo);
			}
		}
	}
	public void update_venta_item_eliminar(venta_item_eliminar vie, JTable tbltabla) {
		if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR VENTA_ITEM_ELIMINAR", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_venta_item_eliminar";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				vie_dao.update_venta_item_eliminar(conn, vie);
				vie_dao.actualizar_tabla_venta_item_eliminar(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, vie.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, vie.toString(), titulo);
				}
			}
		}
	}
}
