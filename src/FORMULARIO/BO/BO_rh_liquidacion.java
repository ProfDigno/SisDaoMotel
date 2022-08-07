	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_rh_liquidacion;
	import FORMULARIO.ENTIDAD.rh_liquidacion;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_rh_liquidacion {
private DAO_rh_liquidacion rhli_dao = new DAO_rh_liquidacion();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_rh_liquidacion(rh_liquidacion rhli, JTable tbltabla) {
		String titulo = "insertar_rh_liquidacion";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			rhli_dao.insertar_rh_liquidacion(conn, rhli);
			rhli_dao.actualizar_tabla_rh_liquidacion(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, rhli.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, rhli.toString(), titulo);
			}
		}
	}
	public void update_rh_liquidacion(rh_liquidacion rhli, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR RH_LIQUIDACION", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_rh_liquidacion";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				rhli_dao.update_rh_liquidacion(conn, rhli);
				rhli_dao.actualizar_tabla_rh_liquidacion(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, rhli.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, rhli.toString(), titulo);
				}
			}
		}
	}
}
