	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_producto_categoria;
	import FORMULARIO.ENTIDAD.producto_categoria;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_producto_categoria {
private DAO_producto_categoria prca_dao = new DAO_producto_categoria();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_producto_categoria(producto_categoria prca, JTable tbltabla) {
		String titulo = "insertar_producto_categoria";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			prca_dao.insertar_producto_categoria(conn, prca);
			prca_dao.actualizar_tabla_producto_categoria(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, prca.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, prca.toString(), titulo);
			}
		}
	}
	public void update_producto_categoria(producto_categoria prca, JTable tbltabla) {
		if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR PRODUCTO_CATEGORIA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_producto_categoria";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				prca_dao.update_producto_categoria(conn, prca);
				prca_dao.actualizar_tabla_producto_categoria(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, prca.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, prca.toString(), titulo);
				}
			}
		}
	}
}
