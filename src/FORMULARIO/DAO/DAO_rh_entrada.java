	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.rh_entrada;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_rh_entrada {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "RH_ENTRADA GUARDADO CORRECTAMENTE";
	private String mensaje_update = "RH_ENTRADA MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO rh_entrada(idrh_entrada,fecha_creado,creado_por,fecha_entrada,fecha_salida,turno,es_cerrado,fk_idpersona,fk_idusuario,fk_idrh_turno) VALUES (?,?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE rh_entrada SET fecha_creado=?,creado_por=?,fecha_entrada=?,fecha_salida=?,turno=?,es_cerrado=?,fk_idpersona=?,fk_idusuario=?,fk_idrh_turno=? WHERE idrh_entrada=?;";
	private String sql_select = "SELECT idrh_entrada,fecha_creado,creado_por,fecha_entrada,fecha_salida,turno,es_cerrado,fk_idpersona,fk_idusuario,fk_idrh_turno FROM rh_entrada order by 1 desc;";
	private String sql_cargar = "SELECT idrh_entrada,fecha_creado,creado_por,fecha_entrada,fecha_salida,turno,es_cerrado,fk_idpersona,fk_idusuario,fk_idrh_turno FROM rh_entrada WHERE idrh_entrada=";
	public void insertar_rh_entrada(Connection conn, rh_entrada rhen){
		rhen.setC1idrh_entrada(eveconn.getInt_ultimoID_mas_uno(conn, rhen.getTb_rh_entrada(), rhen.getId_idrh_entrada()));
		String titulo = "insertar_rh_entrada";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,rhen.getC1idrh_entrada());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,rhen.getC3creado_por());
			pst.setTimestamp(4,evefec.getTimestamp_sistema());
			pst.setTimestamp(5,evefec.getTimestamp_sistema());
			pst.setString(6,rhen.getC6turno());
			pst.setBoolean(7,rhen.getC7es_cerrado());
			pst.setInt(8,rhen.getC8fk_idpersona());
			pst.setInt(9,rhen.getC9fk_idusuario());
			pst.setInt(10,rhen.getC10fk_idrh_turno());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + rhen.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + rhen.toString(), titulo);
		}
	}
	public void update_rh_entrada(Connection conn, rh_entrada rhen){
		String titulo = "update_rh_entrada";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,rhen.getC3creado_por());
			pst.setTimestamp(3,evefec.getTimestamp_sistema());
			pst.setTimestamp(4,evefec.getTimestamp_sistema());
			pst.setString(5,rhen.getC6turno());
			pst.setBoolean(6,rhen.getC7es_cerrado());
			pst.setInt(7,rhen.getC8fk_idpersona());
			pst.setInt(8,rhen.getC9fk_idusuario());
			pst.setInt(9,rhen.getC10fk_idrh_turno());
			pst.setInt(10,rhen.getC1idrh_entrada());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + rhen.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + rhen.toString(), titulo);
		}
	}
	public void cargar_rh_entrada(Connection conn, rh_entrada rhen,int idrh_entrada){
		String titulo = "Cargar_rh_entrada";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idrh_entrada, titulo);
			if(rs.next()){
				rhen.setC1idrh_entrada(rs.getInt(1));
				rhen.setC2fecha_creado(rs.getString(2));
				rhen.setC3creado_por(rs.getString(3));
				rhen.setC4fecha_entrada(rs.getString(4));
				rhen.setC5fecha_salida(rs.getString(5));
				rhen.setC6turno(rs.getString(6));
				rhen.setC7es_cerrado(rs.getBoolean(7));
				rhen.setC8fk_idpersona(rs.getInt(8));
				rhen.setC9fk_idusuario(rs.getInt(9));
				rhen.setC10fk_idrh_turno(rs.getInt(10));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + rhen.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + rhen.toString(), titulo);
		}
	}
	public void actualizar_tabla_rh_entrada(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_rh_entrada(tbltabla);
	}
	public void ancho_tabla_rh_entrada(JTable tbltabla){
		int Ancho[]={10,10,10,10,10,10,10,10,10,10};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
