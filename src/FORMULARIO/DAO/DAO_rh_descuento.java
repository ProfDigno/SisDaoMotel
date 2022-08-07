	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.rh_descuento;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_rh_descuento {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "RH_DESCUENTO GUARDADO CORRECTAMENTE";
	private String mensaje_update = "RH_DESCUENTO MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO rh_descuento(idrh_descuento,fecha_creado,creado_por,descripcion,monto_descuento,estado,es_cerrado,monto_letra,fk_idpersona) VALUES (?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE rh_descuento SET fecha_creado=?,creado_por=?,descripcion=?,monto_descuento=?,estado=?,es_cerrado=?,monto_letra=?,fk_idpersona=? WHERE idrh_descuento=?;";
	private String sql_select = "SELECT idrh_descuento,fecha_creado,creado_por,descripcion,monto_descuento,estado,es_cerrado,monto_letra,fk_idpersona FROM rh_descuento order by 1 desc;";
	private String sql_cargar = "SELECT idrh_descuento,fecha_creado,creado_por,descripcion,monto_descuento,estado,es_cerrado,monto_letra,fk_idpersona FROM rh_descuento WHERE idrh_descuento=";
	public void insertar_rh_descuento(Connection conn, rh_descuento rhde){
		rhde.setC1idrh_descuento(eveconn.getInt_ultimoID_mas_uno(conn, rhde.getTb_rh_descuento(), rhde.getId_idrh_descuento()));
		String titulo = "insertar_rh_descuento";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,rhde.getC1idrh_descuento());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,rhde.getC3creado_por());
			pst.setString(4,rhde.getC4descripcion());
			pst.setDouble(5,rhde.getC5monto_descuento());
			pst.setString(6,rhde.getC6estado());
			pst.setBoolean(7,rhde.getC7es_cerrado());
			pst.setString(8,rhde.getC8monto_letra());
			pst.setInt(9,rhde.getC9fk_idpersona());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + rhde.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + rhde.toString(), titulo);
		}
	}
	public void update_rh_descuento(Connection conn, rh_descuento rhde){
		String titulo = "update_rh_descuento";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,rhde.getC3creado_por());
			pst.setString(3,rhde.getC4descripcion());
			pst.setDouble(4,rhde.getC5monto_descuento());
			pst.setString(5,rhde.getC6estado());
			pst.setBoolean(6,rhde.getC7es_cerrado());
			pst.setString(7,rhde.getC8monto_letra());
			pst.setInt(8,rhde.getC9fk_idpersona());
			pst.setInt(9,rhde.getC1idrh_descuento());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + rhde.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + rhde.toString(), titulo);
		}
	}
	public void cargar_rh_descuento(Connection conn, rh_descuento rhde,int idrh_descuento){
		String titulo = "Cargar_rh_descuento";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idrh_descuento, titulo);
			if(rs.next()){
				rhde.setC1idrh_descuento(rs.getInt(1));
				rhde.setC2fecha_creado(rs.getString(2));
				rhde.setC3creado_por(rs.getString(3));
				rhde.setC4descripcion(rs.getString(4));
				rhde.setC5monto_descuento(rs.getDouble(5));
				rhde.setC6estado(rs.getString(6));
				rhde.setC7es_cerrado(rs.getBoolean(7));
				rhde.setC8monto_letra(rs.getString(8));
				rhde.setC9fk_idpersona(rs.getInt(9));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + rhde.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + rhde.toString(), titulo);
		}
	}
	public void actualizar_tabla_rh_descuento(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_rh_descuento(tbltabla);
	}
	public void ancho_tabla_rh_descuento(JTable tbltabla){
		int Ancho[]={11,11,11,11,11,11,11,11,11};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
