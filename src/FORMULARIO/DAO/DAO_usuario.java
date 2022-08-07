	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.usuario;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_usuario {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "USUARIO GUARDADO CORRECTAMENTE";
	private String mensaje_update = "USUARIO MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO usuario(idusuario,fecha_creado,creado_por,nombre,usuario,password,fk_idusuario_rol,fk_idpersona) VALUES (?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE usuario SET fecha_creado=?,creado_por=?,nombre=?,usuario=?,password=?,fk_idusuario_rol=?,fk_idpersona=? WHERE idusuario=?;";
	private String sql_select = "SELECT idusuario,fecha_creado,creado_por,nombre,usuario,password,fk_idusuario_rol,fk_idpersona FROM usuario order by 1 desc;";
	private String sql_cargar = "SELECT idusuario,fecha_creado,creado_por,nombre,usuario,password,fk_idusuario_rol,fk_idpersona FROM usuario WHERE idusuario=";
	public void insertar_usuario(Connection conn, usuario us){
		us.setC1idusuario(eveconn.getInt_ultimoID_mas_uno(conn, us.getTb_usuario(), us.getId_idusuario()));
		String titulo = "insertar_usuario";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,us.getC1idusuario());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,us.getC3creado_por());
			pst.setString(4,us.getC4nombre());
			pst.setString(5,us.getC5usuario());
			pst.setString(6,us.getC6password());
			pst.setInt(7,us.getC7fk_idusuario_rol());
			pst.setInt(8,us.getC8fk_idpersona());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + us.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + us.toString(), titulo);
		}
	}
	public void update_usuario(Connection conn, usuario us){
		String titulo = "update_usuario";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,us.getC3creado_por());
			pst.setString(3,us.getC4nombre());
			pst.setString(4,us.getC5usuario());
			pst.setString(5,us.getC6password());
			pst.setInt(6,us.getC7fk_idusuario_rol());
			pst.setInt(7,us.getC8fk_idpersona());
			pst.setInt(8,us.getC1idusuario());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + us.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + us.toString(), titulo);
		}
	}
	public void cargar_usuario(Connection conn, usuario us,int idusuario){
		String titulo = "Cargar_usuario";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idusuario, titulo);
			if(rs.next()){
				us.setC1idusuario(rs.getInt(1));
				us.setC2fecha_creado(rs.getString(2));
				us.setC3creado_por(rs.getString(3));
				us.setC4nombre(rs.getString(4));
				us.setC5usuario(rs.getString(5));
				us.setC6password(rs.getString(6));
				us.setC7fk_idusuario_rol(rs.getInt(7));
				us.setC8fk_idpersona(rs.getInt(8));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + us.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + us.toString(), titulo);
		}
	}
	public void actualizar_tabla_usuario(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_usuario(tbltabla);
	}
	public void ancho_tabla_usuario(JTable tbltabla){
		int Ancho[]={12,12,12,12,12,12,12,12};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
