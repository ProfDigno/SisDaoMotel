	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.habitacion_recepcion;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_habitacion_recepcion {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "HABITACION_RECEPCION GUARDADO CORRECTAMENTE";
	private String mensaje_update = "HABITACION_RECEPCION MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO habitacion_recepcion(idhabitacion_recepcion,fecha_creado,creado_por,estado,fec_libre_inicio,fec_libre_fin,fec_ocupado_inicio,fec_ocupado_fin,fec_sucio_inicio,fec_sucio_fin,fec_limpieza_inicio,fec_limpieza_fin,fec_mante_inicio,fec_mante_fin,es_libre,es_ocupado,es_sucio,es_limpieza,es_mante,es_cancelado,es_por_hora,es_por_dormir,es_boton_actual,es_pagado,es_terminado,monto_por_hora_minimo,monto_por_hora_adicional,monto_por_dormir_minimo,monto_por_dormir_adicional,monto_consumision,monto_descuento,fk_idhabitacion_dato) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE habitacion_recepcion SET fecha_creado=?,creado_por=?,estado=?,fec_libre_inicio=?,fec_libre_fin=?,fec_ocupado_inicio=?,fec_ocupado_fin=?,fec_sucio_inicio=?,fec_sucio_fin=?,fec_limpieza_inicio=?,fec_limpieza_fin=?,fec_mante_inicio=?,fec_mante_fin=?,es_libre=?,es_ocupado=?,es_sucio=?,es_limpieza=?,es_mante=?,es_cancelado=?,es_por_hora=?,es_por_dormir=?,es_boton_actual=?,es_pagado=?,es_terminado=?,monto_por_hora_minimo=?,monto_por_hora_adicional=?,monto_por_dormir_minimo=?,monto_por_dormir_adicional=?,monto_consumision=?,monto_descuento=?,fk_idhabitacion_dato=? WHERE idhabitacion_recepcion=?;";
	private String sql_select = "SELECT idhabitacion_recepcion,fecha_creado,creado_por,estado,fec_libre_inicio,fec_libre_fin,fec_ocupado_inicio,fec_ocupado_fin,fec_sucio_inicio,fec_sucio_fin,fec_limpieza_inicio,fec_limpieza_fin,fec_mante_inicio,fec_mante_fin,es_libre,es_ocupado,es_sucio,es_limpieza,es_mante,es_cancelado,es_por_hora,es_por_dormir,es_boton_actual,es_pagado,es_terminado,monto_por_hora_minimo,monto_por_hora_adicional,monto_por_dormir_minimo,monto_por_dormir_adicional,monto_consumision,monto_descuento,fk_idhabitacion_dato FROM habitacion_recepcion order by 1 desc;";
	private String sql_cargar = "SELECT idhabitacion_recepcion,fecha_creado,creado_por,estado,fec_libre_inicio,fec_libre_fin,fec_ocupado_inicio,fec_ocupado_fin,fec_sucio_inicio,fec_sucio_fin,fec_limpieza_inicio,fec_limpieza_fin,fec_mante_inicio,fec_mante_fin,es_libre,es_ocupado,es_sucio,es_limpieza,es_mante,es_cancelado,es_por_hora,es_por_dormir,es_boton_actual,es_pagado,es_terminado,monto_por_hora_minimo,monto_por_hora_adicional,monto_por_dormir_minimo,monto_por_dormir_adicional,monto_consumision,monto_descuento,fk_idhabitacion_dato FROM habitacion_recepcion WHERE idhabitacion_recepcion=";
	public void insertar_habitacion_recepcion(Connection conn, habitacion_recepcion hare){
		hare.setC1idhabitacion_recepcion(eveconn.getInt_ultimoID_mas_uno(conn, hare.getTb_habitacion_recepcion(), hare.getId_idhabitacion_recepcion()));
		String titulo = "insertar_habitacion_recepcion";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,hare.getC1idhabitacion_recepcion());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,hare.getC3creado_por());
			pst.setString(4,hare.getC4estado());
			pst.setTimestamp(5,evefec.getTimestamp_sistema());
			pst.setTimestamp(6,evefec.getTimestamp_sistema());
			pst.setTimestamp(7,evefec.getTimestamp_sistema());
			pst.setTimestamp(8,evefec.getTimestamp_sistema());
			pst.setTimestamp(9,evefec.getTimestamp_sistema());
			pst.setTimestamp(10,evefec.getTimestamp_sistema());
			pst.setTimestamp(11,evefec.getTimestamp_sistema());
			pst.setTimestamp(12,evefec.getTimestamp_sistema());
			pst.setTimestamp(13,evefec.getTimestamp_sistema());
			pst.setTimestamp(14,evefec.getTimestamp_sistema());
			pst.setBoolean(15,hare.getC15es_libre());
			pst.setBoolean(16,hare.getC16es_ocupado());
			pst.setBoolean(17,hare.getC17es_sucio());
			pst.setBoolean(18,hare.getC18es_limpieza());
			pst.setBoolean(19,hare.getC19es_mante());
			pst.setBoolean(20,hare.getC20es_cancelado());
			pst.setBoolean(21,hare.getC21es_por_hora());
			pst.setBoolean(22,hare.getC22es_por_dormir());
			pst.setBoolean(23,hare.getC23es_boton_actual());
			pst.setBoolean(24,hare.getC24es_pagado());
			pst.setBoolean(25,hare.getC25es_terminado());
			pst.setDouble(26,hare.getC26monto_por_hora_minimo());
			pst.setDouble(27,hare.getC27monto_por_hora_adicional());
			pst.setDouble(28,hare.getC28monto_por_dormir_minimo());
			pst.setDouble(29,hare.getC29monto_por_dormir_adicional());
			pst.setDouble(30,hare.getC30monto_consumision());
			pst.setDouble(31,hare.getC31monto_descuento());
			pst.setInt(32,hare.getC32fk_idhabitacion_dato());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + hare.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + hare.toString(), titulo);
		}
	}
	public void update_habitacion_recepcion(Connection conn, habitacion_recepcion hare){
		String titulo = "update_habitacion_recepcion";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,hare.getC3creado_por());
			pst.setString(3,hare.getC4estado());
			pst.setTimestamp(4,evefec.getTimestamp_sistema());
			pst.setTimestamp(5,evefec.getTimestamp_sistema());
			pst.setTimestamp(6,evefec.getTimestamp_sistema());
			pst.setTimestamp(7,evefec.getTimestamp_sistema());
			pst.setTimestamp(8,evefec.getTimestamp_sistema());
			pst.setTimestamp(9,evefec.getTimestamp_sistema());
			pst.setTimestamp(10,evefec.getTimestamp_sistema());
			pst.setTimestamp(11,evefec.getTimestamp_sistema());
			pst.setTimestamp(12,evefec.getTimestamp_sistema());
			pst.setTimestamp(13,evefec.getTimestamp_sistema());
			pst.setBoolean(14,hare.getC15es_libre());
			pst.setBoolean(15,hare.getC16es_ocupado());
			pst.setBoolean(16,hare.getC17es_sucio());
			pst.setBoolean(17,hare.getC18es_limpieza());
			pst.setBoolean(18,hare.getC19es_mante());
			pst.setBoolean(19,hare.getC20es_cancelado());
			pst.setBoolean(20,hare.getC21es_por_hora());
			pst.setBoolean(21,hare.getC22es_por_dormir());
			pst.setBoolean(22,hare.getC23es_boton_actual());
			pst.setBoolean(23,hare.getC24es_pagado());
			pst.setBoolean(24,hare.getC25es_terminado());
			pst.setDouble(25,hare.getC26monto_por_hora_minimo());
			pst.setDouble(26,hare.getC27monto_por_hora_adicional());
			pst.setDouble(27,hare.getC28monto_por_dormir_minimo());
			pst.setDouble(28,hare.getC29monto_por_dormir_adicional());
			pst.setDouble(29,hare.getC30monto_consumision());
			pst.setDouble(30,hare.getC31monto_descuento());
			pst.setInt(31,hare.getC32fk_idhabitacion_dato());
			pst.setInt(32,hare.getC1idhabitacion_recepcion());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + hare.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + hare.toString(), titulo);
		}
	}
	public void cargar_habitacion_recepcion(Connection conn, habitacion_recepcion hare,int idhabitacion_recepcion){
		String titulo = "Cargar_habitacion_recepcion";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idhabitacion_recepcion, titulo);
			if(rs.next()){
				hare.setC1idhabitacion_recepcion(rs.getInt(1));
				hare.setC2fecha_creado(rs.getString(2));
				hare.setC3creado_por(rs.getString(3));
				hare.setC4estado(rs.getString(4));
				hare.setC5fec_libre_inicio(rs.getString(5));
				hare.setC6fec_libre_fin(rs.getString(6));
				hare.setC7fec_ocupado_inicio(rs.getString(7));
				hare.setC8fec_ocupado_fin(rs.getString(8));
				hare.setC9fec_sucio_inicio(rs.getString(9));
				hare.setC10fec_sucio_fin(rs.getString(10));
				hare.setC11fec_limpieza_inicio(rs.getString(11));
				hare.setC12fec_limpieza_fin(rs.getString(12));
				hare.setC13fec_mante_inicio(rs.getString(13));
				hare.setC14fec_mante_fin(rs.getString(14));
				hare.setC15es_libre(rs.getBoolean(15));
				hare.setC16es_ocupado(rs.getBoolean(16));
				hare.setC17es_sucio(rs.getBoolean(17));
				hare.setC18es_limpieza(rs.getBoolean(18));
				hare.setC19es_mante(rs.getBoolean(19));
				hare.setC20es_cancelado(rs.getBoolean(20));
				hare.setC21es_por_hora(rs.getBoolean(21));
				hare.setC22es_por_dormir(rs.getBoolean(22));
				hare.setC23es_boton_actual(rs.getBoolean(23));
				hare.setC24es_pagado(rs.getBoolean(24));
				hare.setC25es_terminado(rs.getBoolean(25));
				hare.setC26monto_por_hora_minimo(rs.getDouble(26));
				hare.setC27monto_por_hora_adicional(rs.getDouble(27));
				hare.setC28monto_por_dormir_minimo(rs.getDouble(28));
				hare.setC29monto_por_dormir_adicional(rs.getDouble(29));
				hare.setC30monto_consumision(rs.getDouble(30));
				hare.setC31monto_descuento(rs.getDouble(31));
				hare.setC32fk_idhabitacion_dato(rs.getInt(32));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + hare.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + hare.toString(), titulo);
		}
	}
	public void actualizar_tabla_habitacion_recepcion(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_habitacion_recepcion(tbltabla);
	}
	public void ancho_tabla_habitacion_recepcion(JTable tbltabla){
		int Ancho[]={3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
