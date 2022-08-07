	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.usuario_item_rol;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_usuario_item_rol {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "USUARIO_ITEM_ROL GUARDADO CORRECTAMENTE";
	private String mensaje_update = "USUARIO_ITEM_ROL MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO usuario_item_rol(idusuario_item_rol,fecha_creado,creado_por,activo,fk_idusuario_rol,fk_idusuario_evento) VALUES (?,?,?,?,?,?);";
	private String sql_update = "UPDATE usuario_item_rol SET fecha_creado=?,creado_por=?,activo=?,fk_idusuario_rol=?,fk_idusuario_evento=? WHERE idusuario_item_rol=?;";
	private String sql_select = "SELECT idusuario_item_rol,fecha_creado,creado_por,activo,fk_idusuario_rol,fk_idusuario_evento FROM usuario_item_rol order by 1 desc;";
	private String sql_cargar = "SELECT idusuario_item_rol,fecha_creado,creado_por,activo,fk_idusuario_rol,fk_idusuario_evento FROM usuario_item_rol WHERE idusuario_item_rol=";
	public void insertar_usuario_item_rol(Connection conn, usuario_item_rol usitro){
		usitro.setC1idusuario_item_rol(eveconn.getInt_ultimoID_mas_uno(conn, usitro.getTb_usuario_item_rol(), usitro.getId_idusuario_item_rol()));
		String titulo = "insertar_usuario_item_rol";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,usitro.getC1idusuario_item_rol());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,usitro.getC3creado_por());
			pst.setBoolean(4,usitro.getC4activo());
			pst.setInt(5,usitro.getC5fk_idusuario_rol());
			pst.setInt(6,usitro.getC6fk_idusuario_evento());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + usitro.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + usitro.toString(), titulo);
		}
	}
	public void update_usuario_item_rol(Connection conn, usuario_item_rol usitro){
		String titulo = "update_usuario_item_rol";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,usitro.getC3creado_por());
			pst.setBoolean(3,usitro.getC4activo());
			pst.setInt(4,usitro.getC5fk_idusuario_rol());
			pst.setInt(5,usitro.getC6fk_idusuario_evento());
			pst.setInt(6,usitro.getC1idusuario_item_rol());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + usitro.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + usitro.toString(), titulo);
		}
	}
	public void cargar_usuario_item_rol(Connection conn, usuario_item_rol usitro,int idusuario_item_rol){
		String titulo = "Cargar_usuario_item_rol";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idusuario_item_rol, titulo);
			if(rs.next()){
				usitro.setC1idusuario_item_rol(rs.getInt(1));
				usitro.setC2fecha_creado(rs.getString(2));
				usitro.setC3creado_por(rs.getString(3));
				usitro.setC4activo(rs.getBoolean(4));
				usitro.setC5fk_idusuario_rol(rs.getInt(5));
				usitro.setC6fk_idusuario_evento(rs.getInt(6));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + usitro.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + usitro.toString(), titulo);
		}
	}
	public void actualizar_tabla_usuario_item_rol(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_usuario_item_rol(tbltabla);
	}
	public void ancho_tabla_usuario_item_rol(JTable tbltabla){
		int Ancho[]={16,16,16,16,16,16};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
