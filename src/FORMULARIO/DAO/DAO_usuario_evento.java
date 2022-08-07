	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.usuario_evento;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_usuario_evento {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "USUARIO_EVENTO GUARDADO CORRECTAMENTE";
	private String mensaje_update = "USUARIO_EVENTO MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO usuario_evento(idusuario_evento,fecha_creado,creado_por,codigo,nombre,descripcion,fk_idusuario_tipo_evento,fk_idusuario_formulario) VALUES (?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE usuario_evento SET fecha_creado=?,creado_por=?,codigo=?,nombre=?,descripcion=?,fk_idusuario_tipo_evento=?,fk_idusuario_formulario=? WHERE idusuario_evento=?;";
	private String sql_select = "SELECT idusuario_evento,fecha_creado,creado_por,codigo,nombre,descripcion,fk_idusuario_tipo_evento,fk_idusuario_formulario FROM usuario_evento order by 1 desc;";
	private String sql_cargar = "SELECT idusuario_evento,fecha_creado,creado_por,codigo,nombre,descripcion,fk_idusuario_tipo_evento,fk_idusuario_formulario FROM usuario_evento WHERE idusuario_evento=";
	public void insertar_usuario_evento(Connection conn, usuario_evento usev){
		usev.setC1idusuario_evento(eveconn.getInt_ultimoID_mas_uno(conn, usev.getTb_usuario_evento(), usev.getId_idusuario_evento()));
		String titulo = "insertar_usuario_evento";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,usev.getC1idusuario_evento());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,usev.getC3creado_por());
			pst.setInt(4,usev.getC4codigo());
			pst.setString(5,usev.getC5nombre());
			pst.setString(6,usev.getC6descripcion());
			pst.setInt(7,usev.getC7fk_idusuario_tipo_evento());
			pst.setInt(8,usev.getC8fk_idusuario_formulario());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + usev.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + usev.toString(), titulo);
		}
	}
	public void update_usuario_evento(Connection conn, usuario_evento usev){
		String titulo = "update_usuario_evento";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,usev.getC3creado_por());
			pst.setInt(3,usev.getC4codigo());
			pst.setString(4,usev.getC5nombre());
			pst.setString(5,usev.getC6descripcion());
			pst.setInt(6,usev.getC7fk_idusuario_tipo_evento());
			pst.setInt(7,usev.getC8fk_idusuario_formulario());
			pst.setInt(8,usev.getC1idusuario_evento());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + usev.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + usev.toString(), titulo);
		}
	}
	public void cargar_usuario_evento(Connection conn, usuario_evento usev,int idusuario_evento){
		String titulo = "Cargar_usuario_evento";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idusuario_evento, titulo);
			if(rs.next()){
				usev.setC1idusuario_evento(rs.getInt(1));
				usev.setC2fecha_creado(rs.getString(2));
				usev.setC3creado_por(rs.getString(3));
				usev.setC4codigo(rs.getInt(4));
				usev.setC5nombre(rs.getString(5));
				usev.setC6descripcion(rs.getString(6));
				usev.setC7fk_idusuario_tipo_evento(rs.getInt(7));
				usev.setC8fk_idusuario_formulario(rs.getInt(8));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + usev.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + usev.toString(), titulo);
		}
	}
	public void actualizar_tabla_usuario_evento(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_usuario_evento(tbltabla);
	}
	public void ancho_tabla_usuario_evento(JTable tbltabla){
		int Ancho[]={12,12,12,12,12,12,12,12};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
