	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.venta;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_venta {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "VENTA GUARDADO CORRECTAMENTE";
	private String mensaje_update = "VENTA MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO venta(idventa,fecha_creado,creado_por,monto_letra,estado,observacion,tipo_persona,motivo_anulacion,motivo_mudar_habitacion,monto_minimo,monto_adicional,monto_consumo,monto_total,monto_pagado,fk_idhabitacion_recepcion,fk_idpersona,fk_idusuario) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE venta SET fecha_creado=?,creado_por=?,monto_letra=?,estado=?,observacion=?,tipo_persona=?,motivo_anulacion=?,motivo_mudar_habitacion=?,monto_minimo=?,monto_adicional=?,monto_consumo=?,monto_total=?,monto_pagado=?,fk_idhabitacion_recepcion=?,fk_idpersona=?,fk_idusuario=? WHERE idventa=?;";
	private String sql_select = "SELECT idventa,fecha_creado,creado_por,monto_letra,estado,observacion,tipo_persona,motivo_anulacion,motivo_mudar_habitacion,monto_minimo,monto_adicional,monto_consumo,monto_total,monto_pagado,fk_idhabitacion_recepcion,fk_idpersona,fk_idusuario FROM venta order by 1 desc;";
	private String sql_cargar = "SELECT idventa,fecha_creado,creado_por,monto_letra,estado,observacion,tipo_persona,motivo_anulacion,motivo_mudar_habitacion,monto_minimo,monto_adicional,monto_consumo,monto_total,monto_pagado,fk_idhabitacion_recepcion,fk_idpersona,fk_idusuario FROM venta WHERE idventa=";
	public void insertar_venta(Connection conn, venta ve){
		ve.setC1idventa(eveconn.getInt_ultimoID_mas_uno(conn, ve.getTb_venta(), ve.getId_idventa()));
		String titulo = "insertar_venta";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,ve.getC1idventa());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,ve.getC3creado_por());
			pst.setString(4,ve.getC4monto_letra());
			pst.setString(5,ve.getC5estado());
			pst.setString(6,ve.getC6observacion());
			pst.setString(7,ve.getC7tipo_persona());
			pst.setString(8,ve.getC8motivo_anulacion());
			pst.setString(9,ve.getC9motivo_mudar_habitacion());
			pst.setDouble(10,ve.getC10monto_minimo());
			pst.setDouble(11,ve.getC11monto_adicional());
			pst.setDouble(12,ve.getC12monto_consumo());
			pst.setDouble(13,ve.getC13monto_total());
			pst.setDouble(14,ve.getC14monto_pagado());
			pst.setInt(15,ve.getC15fk_idhabitacion_recepcion());
			pst.setInt(16,ve.getC16fk_idpersona());
			pst.setInt(17,ve.getC17fk_idusuario());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + ve.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + ve.toString(), titulo);
		}
	}
	public void update_venta(Connection conn, venta ve){
		String titulo = "update_venta";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,ve.getC3creado_por());
			pst.setString(3,ve.getC4monto_letra());
			pst.setString(4,ve.getC5estado());
			pst.setString(5,ve.getC6observacion());
			pst.setString(6,ve.getC7tipo_persona());
			pst.setString(7,ve.getC8motivo_anulacion());
			pst.setString(8,ve.getC9motivo_mudar_habitacion());
			pst.setDouble(9,ve.getC10monto_minimo());
			pst.setDouble(10,ve.getC11monto_adicional());
			pst.setDouble(11,ve.getC12monto_consumo());
			pst.setDouble(12,ve.getC13monto_total());
			pst.setDouble(13,ve.getC14monto_pagado());
			pst.setInt(14,ve.getC15fk_idhabitacion_recepcion());
			pst.setInt(15,ve.getC16fk_idpersona());
			pst.setInt(16,ve.getC17fk_idusuario());
			pst.setInt(17,ve.getC1idventa());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + ve.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + ve.toString(), titulo);
		}
	}
	public void cargar_venta(Connection conn, venta ve,int idventa){
		String titulo = "Cargar_venta";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idventa, titulo);
			if(rs.next()){
				ve.setC1idventa(rs.getInt(1));
				ve.setC2fecha_creado(rs.getString(2));
				ve.setC3creado_por(rs.getString(3));
				ve.setC4monto_letra(rs.getString(4));
				ve.setC5estado(rs.getString(5));
				ve.setC6observacion(rs.getString(6));
				ve.setC7tipo_persona(rs.getString(7));
				ve.setC8motivo_anulacion(rs.getString(8));
				ve.setC9motivo_mudar_habitacion(rs.getString(9));
				ve.setC10monto_minimo(rs.getDouble(10));
				ve.setC11monto_adicional(rs.getDouble(11));
				ve.setC12monto_consumo(rs.getDouble(12));
				ve.setC13monto_total(rs.getDouble(13));
				ve.setC14monto_pagado(rs.getDouble(14));
				ve.setC15fk_idhabitacion_recepcion(rs.getInt(15));
				ve.setC16fk_idpersona(rs.getInt(16));
				ve.setC17fk_idusuario(rs.getInt(17));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + ve.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + ve.toString(), titulo);
		}
	}
	public void actualizar_tabla_venta(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_venta(tbltabla);
	}
	public void ancho_tabla_venta(JTable tbltabla){
		int Ancho[]={5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
