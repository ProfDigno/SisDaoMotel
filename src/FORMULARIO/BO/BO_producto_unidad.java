	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_producto_unidad;
	import FORMULARIO.ENTIDAD.producto_unidad;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_producto_unidad {
private DAO_producto_unidad prun_dao = new DAO_producto_unidad();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_producto_unidad(producto_unidad prun, JTable tbltabla) {
		String titulo = "insertar_producto_unidad";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			prun_dao.insertar_producto_unidad(conn, prun);
			prun_dao.actualizar_tabla_producto_unidad(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, prun.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, prun.toString(), titulo);
			}
		}
	}
	public void update_producto_unidad(producto_unidad prun, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR PRODUCTO_UNIDAD", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_producto_unidad";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				prun_dao.update_producto_unidad(conn, prun);
				prun_dao.actualizar_tabla_producto_unidad(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, prun.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, prun.toString(), titulo);
				}
			}
		}
	}
}
