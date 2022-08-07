	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_venta_item_insumo;
	import FORMULARIO.ENTIDAD.venta_item_insumo;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_venta_item_insumo {
private DAO_venta_item_insumo veitin_dao = new DAO_venta_item_insumo();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_venta_item_insumo(venta_item_insumo veitin, JTable tbltabla) {
		String titulo = "insertar_venta_item_insumo";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			veitin_dao.insertar_venta_item_insumo(conn, veitin);
			veitin_dao.actualizar_tabla_venta_item_insumo(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, veitin.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, veitin.toString(), titulo);
			}
		}
	}
	public void update_venta_item_insumo(venta_item_insumo veitin, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR VENTA_ITEM_INSUMO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_venta_item_insumo";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				veitin_dao.update_venta_item_insumo(conn, veitin);
				veitin_dao.actualizar_tabla_venta_item_insumo(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, veitin.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, veitin.toString(), titulo);
				}
			}
		}
	}
}
