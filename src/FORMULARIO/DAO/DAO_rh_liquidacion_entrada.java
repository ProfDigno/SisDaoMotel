	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.rh_liquidacion_entrada;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_rh_liquidacion_entrada {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "RH_LIQUIDACION_ENTRADA GUARDADO CORRECTAMENTE";
	private String mensaje_update = "RH_LIQUIDACION_ENTRADA MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO rh_liquidacion_entrada(idrh_liquidacion_entrada,fecha_creado,creado_por,fk_idrh_liquidacion,fk_idrh_entrada) VALUES (?,?,?,?,?);";
	private String sql_update = "UPDATE rh_liquidacion_entrada SET fecha_creado=?,creado_por=?,fk_idrh_liquidacion=?,fk_idrh_entrada=? WHERE idrh_liquidacion_entrada=?;";
	private String sql_select = "SELECT idrh_liquidacion_entrada,fecha_creado,creado_por,fk_idrh_liquidacion,fk_idrh_entrada FROM rh_liquidacion_entrada order by 1 desc;";
	private String sql_cargar = "SELECT idrh_liquidacion_entrada,fecha_creado,creado_por,fk_idrh_liquidacion,fk_idrh_entrada FROM rh_liquidacion_entrada WHERE idrh_liquidacion_entrada=";
	public void insertar_rh_liquidacion_entrada(Connection conn, rh_liquidacion_entrada rhlien){
		rhlien.setC1idrh_liquidacion_entrada(eveconn.getInt_ultimoID_mas_uno(conn, rhlien.getTb_rh_liquidacion_entrada(), rhlien.getId_idrh_liquidacion_entrada()));
		String titulo = "insertar_rh_liquidacion_entrada";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,rhlien.getC1idrh_liquidacion_entrada());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,rhlien.getC3creado_por());
			pst.setInt(4,rhlien.getC4fk_idrh_liquidacion());
			pst.setInt(5,rhlien.getC5fk_idrh_entrada());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + rhlien.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + rhlien.toString(), titulo);
		}
	}
	public void update_rh_liquidacion_entrada(Connection conn, rh_liquidacion_entrada rhlien){
		String titulo = "update_rh_liquidacion_entrada";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,rhlien.getC3creado_por());
			pst.setInt(3,rhlien.getC4fk_idrh_liquidacion());
			pst.setInt(4,rhlien.getC5fk_idrh_entrada());
			pst.setInt(5,rhlien.getC1idrh_liquidacion_entrada());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + rhlien.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + rhlien.toString(), titulo);
		}
	}
	public void cargar_rh_liquidacion_entrada(Connection conn, rh_liquidacion_entrada rhlien,int idrh_liquidacion_entrada){
		String titulo = "Cargar_rh_liquidacion_entrada";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idrh_liquidacion_entrada, titulo);
			if(rs.next()){
				rhlien.setC1idrh_liquidacion_entrada(rs.getInt(1));
				rhlien.setC2fecha_creado(rs.getString(2));
				rhlien.setC3creado_por(rs.getString(3));
				rhlien.setC4fk_idrh_liquidacion(rs.getInt(4));
				rhlien.setC5fk_idrh_entrada(rs.getInt(5));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + rhlien.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + rhlien.toString(), titulo);
		}
	}
	public void actualizar_tabla_rh_liquidacion_entrada(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_rh_liquidacion_entrada(tbltabla);
	}
	public void ancho_tabla_rh_liquidacion_entrada(JTable tbltabla){
		int Ancho[]={20,20,20,20,20};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
