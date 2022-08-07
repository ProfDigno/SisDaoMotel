	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.habitacion_estado_gpio_temp;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_habitacion_estado_gpio_temp {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "HABITACION_ESTADO_GPIO_TEMP GUARDADO CORRECTAMENTE";
	private String mensaje_update = "HABITACION_ESTADO_GPIO_TEMP MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO habitacion_estado_gpio_temp(idhabitacion_estado_gpio_temp,fecha_update,es_update,nro_habitacion,alto_bajo,sensor,gpio) VALUES (?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE habitacion_estado_gpio_temp SET fecha_update=?,es_update=?,nro_habitacion=?,alto_bajo=?,sensor=?,gpio=? WHERE idhabitacion_estado_gpio_temp=?;";
	private String sql_select = "SELECT idhabitacion_estado_gpio_temp,fecha_update,es_update,nro_habitacion,alto_bajo,sensor,gpio FROM habitacion_estado_gpio_temp order by 1 desc;";
	private String sql_cargar = "SELECT idhabitacion_estado_gpio_temp,fecha_update,es_update,nro_habitacion,alto_bajo,sensor,gpio FROM habitacion_estado_gpio_temp WHERE idhabitacion_estado_gpio_temp=";
	public void insertar_habitacion_estado_gpio_temp(Connection conn, habitacion_estado_gpio_temp haesgpte){
		haesgpte.setC1idhabitacion_estado_gpio_temp(eveconn.getInt_ultimoID_mas_uno(conn, haesgpte.getTb_habitacion_estado_gpio_temp(), haesgpte.getId_idhabitacion_estado_gpio_temp()));
		String titulo = "insertar_habitacion_estado_gpio_temp";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,haesgpte.getC1idhabitacion_estado_gpio_temp());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setBoolean(3,haesgpte.getC3es_update());
			pst.setInt(4,haesgpte.getC4nro_habitacion());
			pst.setBoolean(5,haesgpte.getC5alto_bajo());
			pst.setInt(6,haesgpte.getC6sensor());
			pst.setInt(7,haesgpte.getC7gpio());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + haesgpte.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + haesgpte.toString(), titulo);
		}
	}
	public void update_habitacion_estado_gpio_temp(Connection conn, habitacion_estado_gpio_temp haesgpte){
		String titulo = "update_habitacion_estado_gpio_temp";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setBoolean(2,haesgpte.getC3es_update());
			pst.setInt(3,haesgpte.getC4nro_habitacion());
			pst.setBoolean(4,haesgpte.getC5alto_bajo());
			pst.setInt(5,haesgpte.getC6sensor());
			pst.setInt(6,haesgpte.getC7gpio());
			pst.setInt(7,haesgpte.getC1idhabitacion_estado_gpio_temp());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + haesgpte.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + haesgpte.toString(), titulo);
		}
	}
	public void cargar_habitacion_estado_gpio_temp(Connection conn, habitacion_estado_gpio_temp haesgpte,int idhabitacion_estado_gpio_temp){
		String titulo = "Cargar_habitacion_estado_gpio_temp";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idhabitacion_estado_gpio_temp, titulo);
			if(rs.next()){
				haesgpte.setC1idhabitacion_estado_gpio_temp(rs.getInt(1));
				haesgpte.setC2fecha_update(rs.getString(2));
				haesgpte.setC3es_update(rs.getBoolean(3));
				haesgpte.setC4nro_habitacion(rs.getInt(4));
				haesgpte.setC5alto_bajo(rs.getBoolean(5));
				haesgpte.setC6sensor(rs.getInt(6));
				haesgpte.setC7gpio(rs.getInt(7));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + haesgpte.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + haesgpte.toString(), titulo);
		}
	}
	public void actualizar_tabla_habitacion_estado_gpio_temp(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_habitacion_estado_gpio_temp(tbltabla);
	}
	public void ancho_tabla_habitacion_estado_gpio_temp(JTable tbltabla){
		int Ancho[]={14,14,14,14,14,14,14};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
