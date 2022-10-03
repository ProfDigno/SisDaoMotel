	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_gasto_tipo;
	import FORMULARIO.ENTIDAD.gasto_tipo;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_gasto_tipo {
private DAO_gasto_tipo gati_dao = new DAO_gasto_tipo();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_gasto_tipo(gasto_tipo gati, JTable tbltabla) {
		String titulo = "insertar_gasto_tipo";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			gati_dao.insertar_gasto_tipo(conn, gati);
			gati_dao.actualizar_tabla_gasto_tipo(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, gati.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, gati.toString(), titulo);
			}
		}
	}
	public void update_gasto_tipo(gasto_tipo gati, JTable tbltabla) {
		if (evmen.getBooMensaje_warning("ESTAS SEGURO DE MODIFICAR GASTO_TIPO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_gasto_tipo";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				gati_dao.update_gasto_tipo(conn, gati);
				gati_dao.actualizar_tabla_gasto_tipo(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, gati.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, gati.toString(), titulo);
				}
			}
		}
	}
}
