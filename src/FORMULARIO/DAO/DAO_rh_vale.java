	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.rh_vale;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_rh_vale {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "RH_VALE GUARDADO CORRECTAMENTE";
	private String mensaje_update = "RH_VALE MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO rh_vale(idrh_vale,fecha_creado,creado_por,descripcion,monto_vale,estado,es_cerrado,monto_letra,fk_idpersona) VALUES (?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE rh_vale SET fecha_creado=?,creado_por=?,descripcion=?,monto_vale=?,estado=?,es_cerrado=?,monto_letra=?,fk_idpersona=? WHERE idrh_vale=?;";
	private String sql_select = "SELECT idrh_vale,fecha_creado,creado_por,descripcion,monto_vale,estado,es_cerrado,monto_letra,fk_idpersona FROM rh_vale order by 1 desc;";
	private String sql_cargar = "SELECT idrh_vale,fecha_creado,creado_por,descripcion,monto_vale,estado,es_cerrado,monto_letra,fk_idpersona FROM rh_vale WHERE idrh_vale=";
	public void insertar_rh_vale(Connection conn, rh_vale rhva){
		rhva.setC1idrh_vale(eveconn.getInt_ultimoID_mas_uno(conn, rhva.getTb_rh_vale(), rhva.getId_idrh_vale()));
		String titulo = "insertar_rh_vale";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,rhva.getC1idrh_vale());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,rhva.getC3creado_por());
			pst.setString(4,rhva.getC4descripcion());
			pst.setDouble(5,rhva.getC5monto_vale());
			pst.setString(6,rhva.getC6estado());
			pst.setBoolean(7,rhva.getC7es_cerrado());
			pst.setString(8,rhva.getC8monto_letra());
			pst.setInt(9,rhva.getC9fk_idpersona());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + rhva.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + rhva.toString(), titulo);
		}
	}
	public void update_rh_vale(Connection conn, rh_vale rhva){
		String titulo = "update_rh_vale";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,rhva.getC3creado_por());
			pst.setString(3,rhva.getC4descripcion());
			pst.setDouble(4,rhva.getC5monto_vale());
			pst.setString(5,rhva.getC6estado());
			pst.setBoolean(6,rhva.getC7es_cerrado());
			pst.setString(7,rhva.getC8monto_letra());
			pst.setInt(8,rhva.getC9fk_idpersona());
			pst.setInt(9,rhva.getC1idrh_vale());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + rhva.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + rhva.toString(), titulo);
		}
	}
	public void cargar_rh_vale(Connection conn, rh_vale rhva,int idrh_vale){
		String titulo = "Cargar_rh_vale";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idrh_vale, titulo);
			if(rs.next()){
				rhva.setC1idrh_vale(rs.getInt(1));
				rhva.setC2fecha_creado(rs.getString(2));
				rhva.setC3creado_por(rs.getString(3));
				rhva.setC4descripcion(rs.getString(4));
				rhva.setC5monto_vale(rs.getDouble(5));
				rhva.setC6estado(rs.getString(6));
				rhva.setC7es_cerrado(rs.getBoolean(7));
				rhva.setC8monto_letra(rs.getString(8));
				rhva.setC9fk_idpersona(rs.getInt(9));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + rhva.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + rhva.toString(), titulo);
		}
	}
	public void actualizar_tabla_rh_vale(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_rh_vale(tbltabla);
	}
	public void ancho_tabla_rh_vale(JTable tbltabla){
		int Ancho[]={11,11,11,11,11,11,11,11,11};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
