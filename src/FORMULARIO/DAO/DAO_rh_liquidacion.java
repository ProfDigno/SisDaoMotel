	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.rh_liquidacion;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_rh_liquidacion {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "RH_LIQUIDACION GUARDADO CORRECTAMENTE";
	private String mensaje_update = "RH_LIQUIDACION MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO rh_liquidacion(idrh_liquidacion,fecha_creado,creado_por,fecha_desde,fecha_hasta,monto_vale,monto_descuento,monto_liquidacion,monto_letra) VALUES (?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE rh_liquidacion SET fecha_creado=?,creado_por=?,fecha_desde=?,fecha_hasta=?,monto_vale=?,monto_descuento=?,monto_liquidacion=?,monto_letra=? WHERE idrh_liquidacion=?;";
	private String sql_select = "SELECT idrh_liquidacion,fecha_creado,creado_por,fecha_desde,fecha_hasta,monto_vale,monto_descuento,monto_liquidacion,monto_letra FROM rh_liquidacion order by 1 desc;";
	private String sql_cargar = "SELECT idrh_liquidacion,fecha_creado,creado_por,fecha_desde,fecha_hasta,monto_vale,monto_descuento,monto_liquidacion,monto_letra FROM rh_liquidacion WHERE idrh_liquidacion=";
	public void insertar_rh_liquidacion(Connection conn, rh_liquidacion rhli){
		rhli.setC1idrh_liquidacion(eveconn.getInt_ultimoID_mas_uno(conn, rhli.getTb_rh_liquidacion(), rhli.getId_idrh_liquidacion()));
		String titulo = "insertar_rh_liquidacion";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,rhli.getC1idrh_liquidacion());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,rhli.getC3creado_por());
			pst.setDate(4,evefec.getDateSQL_sistema());
			pst.setDate(5,evefec.getDateSQL_sistema());
			pst.setDouble(6,rhli.getC6monto_vale());
			pst.setDouble(7,rhli.getC7monto_descuento());
			pst.setDouble(8,rhli.getC8monto_liquidacion());
			pst.setString(9,rhli.getC9monto_letra());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + rhli.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + rhli.toString(), titulo);
		}
	}
	public void update_rh_liquidacion(Connection conn, rh_liquidacion rhli){
		String titulo = "update_rh_liquidacion";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,rhli.getC3creado_por());
			pst.setDate(3,evefec.getDateSQL_sistema());
			pst.setDate(4,evefec.getDateSQL_sistema());
			pst.setDouble(5,rhli.getC6monto_vale());
			pst.setDouble(6,rhli.getC7monto_descuento());
			pst.setDouble(7,rhli.getC8monto_liquidacion());
			pst.setString(8,rhli.getC9monto_letra());
			pst.setInt(9,rhli.getC1idrh_liquidacion());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + rhli.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + rhli.toString(), titulo);
		}
	}
	public void cargar_rh_liquidacion(Connection conn, rh_liquidacion rhli,int idrh_liquidacion){
		String titulo = "Cargar_rh_liquidacion";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idrh_liquidacion, titulo);
			if(rs.next()){
				rhli.setC1idrh_liquidacion(rs.getInt(1));
				rhli.setC2fecha_creado(rs.getString(2));
				rhli.setC3creado_por(rs.getString(3));
				rhli.setC4fecha_desde(rs.getString(4));
				rhli.setC5fecha_hasta(rs.getString(5));
				rhli.setC6monto_vale(rs.getDouble(6));
				rhli.setC7monto_descuento(rs.getDouble(7));
				rhli.setC8monto_liquidacion(rs.getDouble(8));
				rhli.setC9monto_letra(rs.getString(9));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + rhli.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + rhli.toString(), titulo);
		}
	}
	public void actualizar_tabla_rh_liquidacion(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_rh_liquidacion(tbltabla);
	}
	public void ancho_tabla_rh_liquidacion(JTable tbltabla){
		int Ancho[]={11,11,11,11,11,11,11,11,11};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
