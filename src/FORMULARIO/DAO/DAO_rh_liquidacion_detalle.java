	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.rh_liquidacion_detalle;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_rh_liquidacion_detalle {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "RH_LIQUIDACION_DETALLE GUARDADO CORRECTAMENTE";
	private String mensaje_update = "RH_LIQUIDACION_DETALLE MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO rh_liquidacion_detalle(idrh_liquidacion_detalle,fecha_creado,creado_por,descripcion,monto_descuento,monto_vale,tabla,estado,fk_idrh_liquidacion,fk_idrh_descuento,fk_idrh_vale) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE rh_liquidacion_detalle SET fecha_creado=?,creado_por=?,descripcion=?,monto_descuento=?,monto_vale=?,tabla=?,estado=?,fk_idrh_liquidacion=?,fk_idrh_descuento=?,fk_idrh_vale=? WHERE idrh_liquidacion_detalle=?;";
	private String sql_select = "SELECT idrh_liquidacion_detalle,fecha_creado,creado_por,descripcion,monto_descuento,monto_vale,tabla,estado,fk_idrh_liquidacion,fk_idrh_descuento,fk_idrh_vale FROM rh_liquidacion_detalle order by 1 desc;";
	private String sql_cargar = "SELECT idrh_liquidacion_detalle,fecha_creado,creado_por,descripcion,monto_descuento,monto_vale,tabla,estado,fk_idrh_liquidacion,fk_idrh_descuento,fk_idrh_vale FROM rh_liquidacion_detalle WHERE idrh_liquidacion_detalle=";
	public void insertar_rh_liquidacion_detalle(Connection conn, rh_liquidacion_detalle rld){
		rld.setC1idrh_liquidacion_detalle(eveconn.getInt_ultimoID_mas_uno(conn, rld.getTb_rh_liquidacion_detalle(), rld.getId_idrh_liquidacion_detalle()));
		String titulo = "insertar_rh_liquidacion_detalle";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,rld.getC1idrh_liquidacion_detalle());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,rld.getC3creado_por());
			pst.setString(4,rld.getC4descripcion());
			pst.setDouble(5,rld.getC5monto_descuento());
			pst.setDouble(6,rld.getC6monto_vale());
			pst.setString(7,rld.getC7tabla());
			pst.setString(8,rld.getC8estado());
			pst.setInt(9,rld.getC9fk_idrh_liquidacion());
			pst.setInt(10,rld.getC10fk_idrh_descuento());
			pst.setInt(11,rld.getC11fk_idrh_vale());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + rld.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + rld.toString(), titulo);
		}
	}
	public void update_rh_liquidacion_detalle(Connection conn, rh_liquidacion_detalle rld){
		String titulo = "update_rh_liquidacion_detalle";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,rld.getC3creado_por());
			pst.setString(3,rld.getC4descripcion());
			pst.setDouble(4,rld.getC5monto_descuento());
			pst.setDouble(5,rld.getC6monto_vale());
			pst.setString(6,rld.getC7tabla());
			pst.setString(7,rld.getC8estado());
			pst.setInt(8,rld.getC9fk_idrh_liquidacion());
			pst.setInt(9,rld.getC10fk_idrh_descuento());
			pst.setInt(10,rld.getC11fk_idrh_vale());
			pst.setInt(11,rld.getC1idrh_liquidacion_detalle());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + rld.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + rld.toString(), titulo);
		}
	}
	public void cargar_rh_liquidacion_detalle(Connection conn, rh_liquidacion_detalle rld,int idrh_liquidacion_detalle){
		String titulo = "Cargar_rh_liquidacion_detalle";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idrh_liquidacion_detalle, titulo);
			if(rs.next()){
				rld.setC1idrh_liquidacion_detalle(rs.getInt(1));
				rld.setC2fecha_creado(rs.getString(2));
				rld.setC3creado_por(rs.getString(3));
				rld.setC4descripcion(rs.getString(4));
				rld.setC5monto_descuento(rs.getDouble(5));
				rld.setC6monto_vale(rs.getDouble(6));
				rld.setC7tabla(rs.getString(7));
				rld.setC8estado(rs.getString(8));
				rld.setC9fk_idrh_liquidacion(rs.getInt(9));
				rld.setC10fk_idrh_descuento(rs.getInt(10));
				rld.setC11fk_idrh_vale(rs.getInt(11));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + rld.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + rld.toString(), titulo);
		}
	}
	public void actualizar_tabla_rh_liquidacion_detalle(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_rh_liquidacion_detalle(tbltabla);
	}
	public void ancho_tabla_rh_liquidacion_detalle(JTable tbltabla){
		int Ancho[]={9,9,9,9,9,9,9,9,9,9,9};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
