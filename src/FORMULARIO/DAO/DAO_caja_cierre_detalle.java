	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.caja_cierre_detalle;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_caja_cierre_detalle {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "CAJA_CIERRE_DETALLE GUARDADO CORRECTAMENTE";
	private String mensaje_update = "CAJA_CIERRE_DETALLE MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO caja_cierre_detalle(idcaja_cierre_detalle,fecha_creado,creado_por,cerrado_por,es_cerrado,monto_venta,monto_gasto,monto_compra,monto_apertura,monto_vale,monto_cierre,monto_liquidacion,estado,descripcion,fk_idgasto,fk_idcompra,fk_idventa,fk_idusuario,fk_idrh_vale,fk_idrh_liquidacion) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE caja_cierre_detalle SET fecha_creado=?,creado_por=?,cerrado_por=?,es_cerrado=?,monto_venta=?,monto_gasto=?,monto_compra=?,monto_apertura=?,monto_vale=?,monto_cierre=?,monto_liquidacion=?,estado=?,descripcion=?,fk_idgasto=?,fk_idcompra=?,fk_idventa=?,fk_idusuario=?,fk_idrh_vale=?,fk_idrh_liquidacion=? WHERE idcaja_cierre_detalle=?;";
	private String sql_select = "SELECT idcaja_cierre_detalle,fecha_creado,creado_por,cerrado_por,es_cerrado,monto_venta,monto_gasto,monto_compra,monto_apertura,monto_vale,monto_cierre,monto_liquidacion,estado,descripcion,fk_idgasto,fk_idcompra,fk_idventa,fk_idusuario,fk_idrh_vale,fk_idrh_liquidacion FROM caja_cierre_detalle order by 1 desc;";
	private String sql_cargar = "SELECT idcaja_cierre_detalle,fecha_creado,creado_por,cerrado_por,es_cerrado,monto_venta,monto_gasto,monto_compra,monto_apertura,monto_vale,monto_cierre,monto_liquidacion,estado,descripcion,fk_idgasto,fk_idcompra,fk_idventa,fk_idusuario,fk_idrh_vale,fk_idrh_liquidacion FROM caja_cierre_detalle WHERE idcaja_cierre_detalle=";
	public void insertar_caja_cierre_detalle(Connection conn, caja_cierre_detalle cacide){
		cacide.setC1idcaja_cierre_detalle(eveconn.getInt_ultimoID_mas_uno(conn, cacide.getTb_caja_cierre_detalle(), cacide.getId_idcaja_cierre_detalle()));
		String titulo = "insertar_caja_cierre_detalle";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,cacide.getC1idcaja_cierre_detalle());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,cacide.getC3creado_por());
			pst.setString(4,cacide.getC4cerrado_por());
			pst.setBoolean(5,cacide.getC5es_cerrado());
			pst.setDouble(6,cacide.getC6monto_venta());
			pst.setDouble(7,cacide.getC7monto_gasto());
			pst.setDouble(8,cacide.getC8monto_compra());
			pst.setDouble(9,cacide.getC9monto_apertura());
			pst.setDouble(10,cacide.getC10monto_vale());
			pst.setDouble(11,cacide.getC11monto_cierre());
			pst.setDouble(12,cacide.getC12monto_liquidacion());
			pst.setString(13,cacide.getC13estado());
			pst.setString(14,cacide.getC14descripcion());
			pst.setInt(15,cacide.getC15fk_idgasto());
			pst.setInt(16,cacide.getC16fk_idcompra());
			pst.setInt(17,cacide.getC17fk_idventa());
			pst.setInt(18,cacide.getC18fk_idusuario());
			pst.setInt(19,cacide.getC19fk_idrh_vale());
			pst.setInt(20,cacide.getC20fk_idrh_liquidacion());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + cacide.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + cacide.toString(), titulo);
		}
	}
	public void update_caja_cierre_detalle(Connection conn, caja_cierre_detalle cacide){
		String titulo = "update_caja_cierre_detalle";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,cacide.getC3creado_por());
			pst.setString(3,cacide.getC4cerrado_por());
			pst.setBoolean(4,cacide.getC5es_cerrado());
			pst.setDouble(5,cacide.getC6monto_venta());
			pst.setDouble(6,cacide.getC7monto_gasto());
			pst.setDouble(7,cacide.getC8monto_compra());
			pst.setDouble(8,cacide.getC9monto_apertura());
			pst.setDouble(9,cacide.getC10monto_vale());
			pst.setDouble(10,cacide.getC11monto_cierre());
			pst.setDouble(11,cacide.getC12monto_liquidacion());
			pst.setString(12,cacide.getC13estado());
			pst.setString(13,cacide.getC14descripcion());
			pst.setInt(14,cacide.getC15fk_idgasto());
			pst.setInt(15,cacide.getC16fk_idcompra());
			pst.setInt(16,cacide.getC17fk_idventa());
			pst.setInt(17,cacide.getC18fk_idusuario());
			pst.setInt(18,cacide.getC19fk_idrh_vale());
			pst.setInt(19,cacide.getC20fk_idrh_liquidacion());
			pst.setInt(20,cacide.getC1idcaja_cierre_detalle());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + cacide.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + cacide.toString(), titulo);
		}
	}
	public void cargar_caja_cierre_detalle(Connection conn, caja_cierre_detalle cacide,int idcaja_cierre_detalle){
		String titulo = "Cargar_caja_cierre_detalle";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idcaja_cierre_detalle, titulo);
			if(rs.next()){
				cacide.setC1idcaja_cierre_detalle(rs.getInt(1));
				cacide.setC2fecha_creado(rs.getString(2));
				cacide.setC3creado_por(rs.getString(3));
				cacide.setC4cerrado_por(rs.getString(4));
				cacide.setC5es_cerrado(rs.getBoolean(5));
				cacide.setC6monto_venta(rs.getDouble(6));
				cacide.setC7monto_gasto(rs.getDouble(7));
				cacide.setC8monto_compra(rs.getDouble(8));
				cacide.setC9monto_apertura(rs.getDouble(9));
				cacide.setC10monto_vale(rs.getDouble(10));
				cacide.setC11monto_cierre(rs.getDouble(11));
				cacide.setC12monto_liquidacion(rs.getDouble(12));
				cacide.setC13estado(rs.getString(13));
				cacide.setC14descripcion(rs.getString(14));
				cacide.setC15fk_idgasto(rs.getInt(15));
				cacide.setC16fk_idcompra(rs.getInt(16));
				cacide.setC17fk_idventa(rs.getInt(17));
				cacide.setC18fk_idusuario(rs.getInt(18));
				cacide.setC19fk_idrh_vale(rs.getInt(19));
				cacide.setC20fk_idrh_liquidacion(rs.getInt(20));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + cacide.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + cacide.toString(), titulo);
		}
	}
	public void actualizar_tabla_caja_cierre_detalle(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_caja_cierre_detalle(tbltabla);
	}
	public void ancho_tabla_caja_cierre_detalle(JTable tbltabla){
		int Ancho[]={5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
