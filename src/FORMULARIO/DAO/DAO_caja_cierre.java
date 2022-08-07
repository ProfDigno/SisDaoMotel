	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.caja_cierre;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_caja_cierre {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "CAJA_CIERRE GUARDADO CORRECTAMENTE";
	private String mensaje_update = "CAJA_CIERRE MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO caja_cierre(idcaja_cierre,fecha_creado,creado_por,fecha_inicio,fecha_fin,estado,fk_idusuario) VALUES (?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE caja_cierre SET fecha_creado=?,creado_por=?,fecha_inicio=?,fecha_fin=?,estado=?,fk_idusuario=? WHERE idcaja_cierre=?;";
	private String sql_select = "SELECT idcaja_cierre,fecha_creado,creado_por,fecha_inicio,fecha_fin,estado,fk_idusuario FROM caja_cierre order by 1 desc;";
	private String sql_cargar = "SELECT idcaja_cierre,fecha_creado,creado_por,fecha_inicio,fecha_fin,estado,fk_idusuario FROM caja_cierre WHERE idcaja_cierre=";
	public void insertar_caja_cierre(Connection conn, caja_cierre caci){
		caci.setC1idcaja_cierre(eveconn.getInt_ultimoID_mas_uno(conn, caci.getTb_caja_cierre(), caci.getId_idcaja_cierre()));
		String titulo = "insertar_caja_cierre";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,caci.getC1idcaja_cierre());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,caci.getC3creado_por());
			pst.setTimestamp(4,evefec.getTimestamp_sistema());
			pst.setTimestamp(5,evefec.getTimestamp_sistema());
			pst.setString(6,caci.getC6estado());
			pst.setInt(7,caci.getC7fk_idusuario());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + caci.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + caci.toString(), titulo);
		}
	}
	public void update_caja_cierre(Connection conn, caja_cierre caci){
		String titulo = "update_caja_cierre";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,caci.getC3creado_por());
			pst.setTimestamp(3,evefec.getTimestamp_sistema());
			pst.setTimestamp(4,evefec.getTimestamp_sistema());
			pst.setString(5,caci.getC6estado());
			pst.setInt(6,caci.getC7fk_idusuario());
			pst.setInt(7,caci.getC1idcaja_cierre());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + caci.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + caci.toString(), titulo);
		}
	}
	public void cargar_caja_cierre(Connection conn, caja_cierre caci,int idcaja_cierre){
		String titulo = "Cargar_caja_cierre";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idcaja_cierre, titulo);
			if(rs.next()){
				caci.setC1idcaja_cierre(rs.getInt(1));
				caci.setC2fecha_creado(rs.getString(2));
				caci.setC3creado_por(rs.getString(3));
				caci.setC4fecha_inicio(rs.getString(4));
				caci.setC5fecha_fin(rs.getString(5));
				caci.setC6estado(rs.getString(6));
				caci.setC7fk_idusuario(rs.getInt(7));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + caci.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + caci.toString(), titulo);
		}
	}
	public void actualizar_tabla_caja_cierre(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_caja_cierre(tbltabla);
	}
	public void ancho_tabla_caja_cierre(JTable tbltabla){
		int Ancho[]={14,14,14,14,14,14,14};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
