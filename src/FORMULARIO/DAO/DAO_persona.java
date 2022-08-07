	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.persona;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_persona {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "PERSONA GUARDADO CORRECTAMENTE";
	private String mensaje_update = "PERSONA MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO persona(idpersona,fecha_creado,creado_por,nombre,ruc,direccion,telefono,tipo_persona,dia_libre,salario_base,fk_idpersona_cargo) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE persona SET fecha_creado=?,creado_por=?,nombre=?,ruc=?,direccion=?,telefono=?,tipo_persona=?,dia_libre=?,salario_base=?,fk_idpersona_cargo=? WHERE idpersona=?;";
	private String sql_select = "SELECT idpersona,fecha_creado,creado_por,nombre,ruc,direccion,telefono,tipo_persona,dia_libre,salario_base,fk_idpersona_cargo FROM persona order by 1 desc;";
	private String sql_cargar = "SELECT idpersona,fecha_creado,creado_por,nombre,ruc,direccion,telefono,tipo_persona,dia_libre,salario_base,fk_idpersona_cargo FROM persona WHERE idpersona=";
	public void insertar_persona(Connection conn, persona pe){
		pe.setC1idpersona(eveconn.getInt_ultimoID_mas_uno(conn, pe.getTb_persona(), pe.getId_idpersona()));
		String titulo = "insertar_persona";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,pe.getC1idpersona());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,pe.getC3creado_por());
			pst.setString(4,pe.getC4nombre());
			pst.setString(5,pe.getC5ruc());
			pst.setString(6,pe.getC6direccion());
			pst.setString(7,pe.getC7telefono());
			pst.setString(8,pe.getC8tipo_persona());
			pst.setInt(9,pe.getC9dia_libre());
			pst.setDouble(10,pe.getC10salario_base());
			pst.setInt(11,pe.getC11fk_idpersona_cargo());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + pe.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + pe.toString(), titulo);
		}
	}
	public void update_persona(Connection conn, persona pe){
		String titulo = "update_persona";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,pe.getC3creado_por());
			pst.setString(3,pe.getC4nombre());
			pst.setString(4,pe.getC5ruc());
			pst.setString(5,pe.getC6direccion());
			pst.setString(6,pe.getC7telefono());
			pst.setString(7,pe.getC8tipo_persona());
			pst.setInt(8,pe.getC9dia_libre());
			pst.setDouble(9,pe.getC10salario_base());
			pst.setInt(10,pe.getC11fk_idpersona_cargo());
			pst.setInt(11,pe.getC1idpersona());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + pe.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + pe.toString(), titulo);
		}
	}
	public void cargar_persona(Connection conn, persona pe,int idpersona){
		String titulo = "Cargar_persona";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idpersona, titulo);
			if(rs.next()){
				pe.setC1idpersona(rs.getInt(1));
				pe.setC2fecha_creado(rs.getString(2));
				pe.setC3creado_por(rs.getString(3));
				pe.setC4nombre(rs.getString(4));
				pe.setC5ruc(rs.getString(5));
				pe.setC6direccion(rs.getString(6));
				pe.setC7telefono(rs.getString(7));
				pe.setC8tipo_persona(rs.getString(8));
				pe.setC9dia_libre(rs.getInt(9));
				pe.setC10salario_base(rs.getDouble(10));
				pe.setC11fk_idpersona_cargo(rs.getInt(11));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + pe.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + pe.toString(), titulo);
		}
	}
	public void actualizar_tabla_persona(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_persona(tbltabla);
	}
	public void ancho_tabla_persona(JTable tbltabla){
		int Ancho[]={9,9,9,9,9,9,9,9,9,9,9};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
