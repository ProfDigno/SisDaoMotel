	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_rh_descuento;
	import FORMULARIO.ENTIDAD.rh_descuento;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_rh_descuento {
private DAO_rh_descuento rhde_dao = new DAO_rh_descuento();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_rh_descuento(rh_descuento rhde, JTable tbltabla) {
		String titulo = "insertar_rh_descuento";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			rhde_dao.insertar_rh_descuento(conn, rhde);
			rhde_dao.actualizar_tabla_rh_descuento(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, rhde.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, rhde.toString(), titulo);
			}
		}
	}
	public void update_rh_descuento(rh_descuento rhde, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR RH_DESCUENTO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_rh_descuento";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				rhde_dao.update_rh_descuento(conn, rhde);
				rhde_dao.actualizar_tabla_rh_descuento(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, rhde.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, rhde.toString(), titulo);
				}
			}
		}
	}
}
