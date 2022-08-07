	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.rh_liquidacion_descuento;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_rh_liquidacion_descuento {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "RH_LIQUIDACION_DESCUENTO GUARDADO CORRECTAMENTE";
	private String mensaje_update = "RH_LIQUIDACION_DESCUENTO MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO rh_liquidacion_descuento(idrh_liquidacion_descuento,fecha_creado,creado_por,fk_idrh_liquidacion,fk_idrh_descuento) VALUES (?,?,?,?,?);";
	private String sql_update = "UPDATE rh_liquidacion_descuento SET fecha_creado=?,creado_por=?,fk_idrh_liquidacion=?,fk_idrh_descuento=? WHERE idrh_liquidacion_descuento=?;";
	private String sql_select = "SELECT idrh_liquidacion_descuento,fecha_creado,creado_por,fk_idrh_liquidacion,fk_idrh_descuento FROM rh_liquidacion_descuento order by 1 desc;";
	private String sql_cargar = "SELECT idrh_liquidacion_descuento,fecha_creado,creado_por,fk_idrh_liquidacion,fk_idrh_descuento FROM rh_liquidacion_descuento WHERE idrh_liquidacion_descuento=";
	public void insertar_rh_liquidacion_descuento(Connection conn, rh_liquidacion_descuento rhlide){
		rhlide.setC1idrh_liquidacion_descuento(eveconn.getInt_ultimoID_mas_uno(conn, rhlide.getTb_rh_liquidacion_descuento(), rhlide.getId_idrh_liquidacion_descuento()));
		String titulo = "insertar_rh_liquidacion_descuento";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,rhlide.getC1idrh_liquidacion_descuento());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,rhlide.getC3creado_por());
			pst.setInt(4,rhlide.getC4fk_idrh_liquidacion());
			pst.setInt(5,rhlide.getC5fk_idrh_descuento());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + rhlide.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + rhlide.toString(), titulo);
		}
	}
	public void update_rh_liquidacion_descuento(Connection conn, rh_liquidacion_descuento rhlide){
		String titulo = "update_rh_liquidacion_descuento";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,rhlide.getC3creado_por());
			pst.setInt(3,rhlide.getC4fk_idrh_liquidacion());
			pst.setInt(4,rhlide.getC5fk_idrh_descuento());
			pst.setInt(5,rhlide.getC1idrh_liquidacion_descuento());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + rhlide.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + rhlide.toString(), titulo);
		}
	}
	public void cargar_rh_liquidacion_descuento(Connection conn, rh_liquidacion_descuento rhlide,int idrh_liquidacion_descuento){
		String titulo = "Cargar_rh_liquidacion_descuento";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idrh_liquidacion_descuento, titulo);
			if(rs.next()){
				rhlide.setC1idrh_liquidacion_descuento(rs.getInt(1));
				rhlide.setC2fecha_creado(rs.getString(2));
				rhlide.setC3creado_por(rs.getString(3));
				rhlide.setC4fk_idrh_liquidacion(rs.getInt(4));
				rhlide.setC5fk_idrh_descuento(rs.getInt(5));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + rhlide.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + rhlide.toString(), titulo);
		}
	}
	public void actualizar_tabla_rh_liquidacion_descuento(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_rh_liquidacion_descuento(tbltabla);
	}
	public void ancho_tabla_rh_liquidacion_descuento(JTable tbltabla){
		int Ancho[]={20,20,20,20,20};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
