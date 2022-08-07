	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_rh_vale;
	import FORMULARIO.ENTIDAD.rh_vale;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_rh_vale {
private DAO_rh_vale rhva_dao = new DAO_rh_vale();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_rh_vale(rh_vale rhva, JTable tbltabla) {
		String titulo = "insertar_rh_vale";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			rhva_dao.insertar_rh_vale(conn, rhva);
			rhva_dao.actualizar_tabla_rh_vale(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, rhva.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, rhva.toString(), titulo);
			}
		}
	}
	public void update_rh_vale(rh_vale rhva, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR RH_VALE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_rh_vale";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				rhva_dao.update_rh_vale(conn, rhva);
				rhva_dao.actualizar_tabla_rh_vale(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, rhva.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, rhva.toString(), titulo);
				}
			}
		}
	}
}
