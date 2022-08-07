	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_rh_liquidacion_descuento;
	import FORMULARIO.ENTIDAD.rh_liquidacion_descuento;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_rh_liquidacion_descuento {
private DAO_rh_liquidacion_descuento rhlide_dao = new DAO_rh_liquidacion_descuento();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_rh_liquidacion_descuento(rh_liquidacion_descuento rhlide, JTable tbltabla) {
		String titulo = "insertar_rh_liquidacion_descuento";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			rhlide_dao.insertar_rh_liquidacion_descuento(conn, rhlide);
			rhlide_dao.actualizar_tabla_rh_liquidacion_descuento(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, rhlide.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, rhlide.toString(), titulo);
			}
		}
	}
	public void update_rh_liquidacion_descuento(rh_liquidacion_descuento rhlide, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR RH_LIQUIDACION_DESCUENTO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_rh_liquidacion_descuento";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				rhlide_dao.update_rh_liquidacion_descuento(conn, rhlide);
				rhlide_dao.actualizar_tabla_rh_liquidacion_descuento(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, rhlide.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, rhlide.toString(), titulo);
				}
			}
		}
	}
}
