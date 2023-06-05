	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.venta_eliminar;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_venta_eliminar {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "VENTA_ELIMINAR GUARDADO CORRECTAMENTE";
	private String mensaje_update = "VENTA_ELIMINAR MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO venta_eliminar(idventa_eliminar,fecha_creado,creado_por,monto_letra,estado,observacion,motivo_anulacion,monto,fk_idusuario,fk_idpersona) VALUES (?,?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE venta_eliminar SET fecha_creado=?,creado_por=?,monto_letra=?,estado=?,observacion=?,motivo_anulacion=?,monto=?,fk_idusuario=?,fk_idpersona=? WHERE idventa_eliminar=?;";
	private String sql_select = "SELECT idventa_eliminar,fecha_creado,creado_por,monto_letra,estado,observacion,motivo_anulacion,monto,fk_idusuario,fk_idpersona FROM venta_eliminar order by 1 desc;";
	private String sql_cargar = "SELECT idventa_eliminar,fecha_creado,creado_por,monto_letra,estado,observacion,motivo_anulacion,monto,fk_idusuario,fk_idpersona FROM venta_eliminar WHERE idventa_eliminar=";
	public void insertar_venta_eliminar(Connection conn, venta_eliminar ve){
		ve.setC1idventa_eliminar(eveconn.getInt_ultimoID_mas_uno(conn, ve.getTb_venta_eliminar(), ve.getId_idventa_eliminar()));
		String titulo = "insertar_venta_eliminar";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,ve.getC1idventa_eliminar());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,ve.getC3creado_por());
			pst.setString(4,ve.getC4monto_letra());
			pst.setString(5,ve.getC5estado());
			pst.setString(6,ve.getC6observacion());
			pst.setString(7,ve.getC7motivo_anulacion());
			pst.setDouble(8,ve.getC8monto());
			pst.setInt(9,ve.getC9fk_idusuario());
			pst.setInt(10,ve.getC10fk_idpersona());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + ve.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + ve.toString(), titulo);
		}
	}
	public void update_venta_eliminar(Connection conn, venta_eliminar ve){
		String titulo = "update_venta_eliminar";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,ve.getC3creado_por());
			pst.setString(3,ve.getC4monto_letra());
			pst.setString(4,ve.getC5estado());
			pst.setString(5,ve.getC6observacion());
			pst.setString(6,ve.getC7motivo_anulacion());
			pst.setDouble(7,ve.getC8monto());
			pst.setInt(8,ve.getC9fk_idusuario());
			pst.setInt(9,ve.getC10fk_idpersona());
			pst.setInt(10,ve.getC1idventa_eliminar());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + ve.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + ve.toString(), titulo);
		}
	}
	public void cargar_venta_eliminar(Connection conn, venta_eliminar ve,int idventa_eliminar){
		String titulo = "Cargar_venta_eliminar";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idventa_eliminar, titulo);
			if(rs.next()){
				ve.setC1idventa_eliminar(rs.getInt(1));
				ve.setC2fecha_creado(rs.getString(2));
				ve.setC3creado_por(rs.getString(3));
				ve.setC4monto_letra(rs.getString(4));
				ve.setC5estado(rs.getString(5));
				ve.setC6observacion(rs.getString(6));
				ve.setC7motivo_anulacion(rs.getString(7));
				ve.setC8monto(rs.getDouble(8));
				ve.setC9fk_idusuario(rs.getInt(9));
				ve.setC10fk_idpersona(rs.getInt(10));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + ve.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + ve.toString(), titulo);
		}
	}
	public void actualizar_tabla_venta_eliminar(Connection conn,JTable tbltabla,String filtro){
            String sql_select = "select vi.idventa_eliminar as idvi,\n"
            + "to_char(vi.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha,\n"
            + "p.nombre as cliente,p.direccion as direccion,\n"
            + "vi.estado as estado,\n"
            + "TRIM(to_char(vi.monto,'999G999G999')) as monto,\n"
            + "vi.creado_por as usuario\n"
            + "from venta_eliminar vi,persona p \n"
            + "where vi.fk_idpersona=p.idpersona \n"+filtro
            + " order by vi.idventa_eliminar desc;";
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_venta_eliminar(tbltabla);
	}
	public void ancho_tabla_venta_eliminar(JTable tbltabla){
		int Ancho[] = {5, 15, 20, 25, 10, 10, 15};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
