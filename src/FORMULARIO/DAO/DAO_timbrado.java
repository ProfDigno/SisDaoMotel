	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.timbrado;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_timbrado {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "TIMBRADO GUARDADO CORRECTAMENTE";
	private String mensaje_update = "TIMBRADO MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO timbrado(idtimbrado,fecha_creado,creado_por,nombre,numero_timbrado,ruc,fec_fin_vigente,cod_establecimiento,punto_expedicion,numero_inicio,numero_fin,numero_actual,activo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE timbrado SET fecha_creado=?,creado_por=?,nombre=?,numero_timbrado=?,ruc=?,fec_fin_vigente=?,cod_establecimiento=?,punto_expedicion=?,numero_inicio=?,numero_fin=?,numero_actual=?,activo=? WHERE idtimbrado=?;";
	private String sql_select = "SELECT idtimbrado,fecha_creado,creado_por,nombre,numero_timbrado,ruc,fec_fin_vigente,cod_establecimiento,punto_expedicion,numero_inicio,numero_fin,numero_actual,activo FROM timbrado order by 1 desc;";
	private String sql_cargar = "SELECT idtimbrado,fecha_creado,creado_por,nombre,numero_timbrado,ruc,fec_fin_vigente,cod_establecimiento,punto_expedicion,numero_inicio,numero_fin,numero_actual,activo FROM timbrado WHERE idtimbrado=";
	public void insertar_timbrado(Connection conn, timbrado t){
		t.setC1idtimbrado(eveconn.getInt_ultimoID_mas_uno(conn, t.getTb_timbrado(), t.getId_idtimbrado()));
		String titulo = "insertar_timbrado";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,t.getC1idtimbrado());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,t.getC3creado_por());
			pst.setString(4,t.getC4nombre());
			pst.setInt(5,t.getC5numero_timbrado());
			pst.setString(6,t.getC6ruc());
			pst.setDate(7,evefec.getDateSQL_sistema());
			pst.setString(8,t.getC8cod_establecimiento());
			pst.setString(9,t.getC9punto_expedicion());
			pst.setInt(10,t.getC10numero_inicio());
			pst.setInt(11,t.getC11numero_fin());
			pst.setInt(12,t.getC12numero_actual());
			pst.setBoolean(13,t.getC13activo());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + t.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + t.toString(), titulo);
		}
	}
	public void update_timbrado(Connection conn, timbrado t){
		String titulo = "update_timbrado";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,t.getC3creado_por());
			pst.setString(3,t.getC4nombre());
			pst.setInt(4,t.getC5numero_timbrado());
			pst.setString(5,t.getC6ruc());
			pst.setDate(6,evefec.getDateSQL_sistema());
			pst.setString(7,t.getC8cod_establecimiento());
			pst.setString(8,t.getC9punto_expedicion());
			pst.setInt(9,t.getC10numero_inicio());
			pst.setInt(10,t.getC11numero_fin());
			pst.setInt(11,t.getC12numero_actual());
			pst.setBoolean(12,t.getC13activo());
			pst.setInt(13,t.getC1idtimbrado());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + t.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + t.toString(), titulo);
		}
	}
	public void cargar_timbrado(Connection conn, timbrado t,int idtimbrado){
		String titulo = "Cargar_timbrado";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idtimbrado, titulo);
			if(rs.next()){
				t.setC1idtimbrado(rs.getInt(1));
				t.setC2fecha_creado(rs.getString(2));
				t.setC3creado_por(rs.getString(3));
				t.setC4nombre(rs.getString(4));
				t.setC5numero_timbrado(rs.getInt(5));
				t.setC6ruc(rs.getString(6));
				t.setC7fec_fin_vigente(rs.getString(7));
				t.setC8cod_establecimiento(rs.getString(8));
				t.setC9punto_expedicion(rs.getString(9));
				t.setC10numero_inicio(rs.getInt(10));
				t.setC11numero_fin(rs.getInt(11));
				t.setC12numero_actual(rs.getInt(12));
				t.setC13activo(rs.getBoolean(13));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + t.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + t.toString(), titulo);
		}
	}
	public void actualizar_tabla_timbrado(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_timbrado(tbltabla);
	}
	public void ancho_tabla_timbrado(JTable tbltabla){
		int Ancho[]={7,7,7,7,7,7,7,7,7,7,7,7,7};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
