	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.gasto;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_gasto {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "GASTO GUARDADO CORRECTAMENTE";
	private String mensaje_update = "GASTO MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO gasto(idgasto,fecha_creado,creado_por,monto_gasto,monto_letra,descripcion,estado,fk_idgasto_tipo,fk_idusuario) VALUES (?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE gasto SET fecha_creado=?,creado_por=?,monto_gasto=?,monto_letra=?,descripcion=?,estado=?,fk_idgasto_tipo=?,fk_idusuario=? WHERE idgasto=?;";
	private String sql_select = "SELECT idgasto,fecha_creado,creado_por,monto_gasto,monto_letra,descripcion,estado,fk_idgasto_tipo,fk_idusuario FROM gasto order by 1 desc;";
	private String sql_cargar = "SELECT idgasto,fecha_creado,creado_por,monto_gasto,monto_letra,descripcion,estado,fk_idgasto_tipo,fk_idusuario FROM gasto WHERE idgasto=";
	public void insertar_gasto(Connection conn, gasto ga){
		ga.setC1idgasto(eveconn.getInt_ultimoID_mas_uno(conn, ga.getTb_gasto(), ga.getId_idgasto()));
		String titulo = "insertar_gasto";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,ga.getC1idgasto());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,ga.getC3creado_por());
			pst.setDouble(4,ga.getC4monto_gasto());
			pst.setString(5,ga.getC5monto_letra());
			pst.setString(6,ga.getC6descripcion());
			pst.setString(7,ga.getC7estado());
			pst.setInt(8,ga.getC8fk_idgasto_tipo());
			pst.setInt(9,ga.getC9fk_idusuario());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + ga.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + ga.toString(), titulo);
		}
	}
	public void update_gasto(Connection conn, gasto ga){
		String titulo = "update_gasto";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,ga.getC3creado_por());
			pst.setDouble(3,ga.getC4monto_gasto());
			pst.setString(4,ga.getC5monto_letra());
			pst.setString(5,ga.getC6descripcion());
			pst.setString(6,ga.getC7estado());
			pst.setInt(7,ga.getC8fk_idgasto_tipo());
			pst.setInt(8,ga.getC9fk_idusuario());
			pst.setInt(9,ga.getC1idgasto());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + ga.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + ga.toString(), titulo);
		}
	}
	public void cargar_gasto(Connection conn, gasto ga,int idgasto){
		String titulo = "Cargar_gasto";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idgasto, titulo);
			if(rs.next()){
				ga.setC1idgasto(rs.getInt(1));
				ga.setC2fecha_creado(rs.getString(2));
				ga.setC3creado_por(rs.getString(3));
				ga.setC4monto_gasto(rs.getDouble(4));
				ga.setC5monto_letra(rs.getString(5));
				ga.setC6descripcion(rs.getString(6));
				ga.setC7estado(rs.getString(7));
				ga.setC8fk_idgasto_tipo(rs.getInt(8));
				ga.setC9fk_idusuario(rs.getInt(9));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + ga.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + ga.toString(), titulo);
		}
	}
	public void actualizar_tabla_gasto(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_gasto(tbltabla);
	}
	public void ancho_tabla_gasto(JTable tbltabla){
		int Ancho[]={11,11,11,11,11,11,11,11,11};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
