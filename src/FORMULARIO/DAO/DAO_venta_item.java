	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.venta_item;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_venta_item {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "VENTA_ITEM GUARDADO CORRECTAMENTE";
	private String mensaje_update = "VENTA_ITEM MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO venta_item(idventa_item,fecha_creado,creado_por,tipo_item,descripcion,cantidad,precio_venta,precio_compra,fk_idventa,fk_idproducto) VALUES (?,?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE venta_item SET fecha_creado=?,creado_por=?,tipo_item=?,descripcion=?,cantidad=?,precio_venta=?,precio_compra=?,fk_idventa=?,fk_idproducto=? WHERE idventa_item=?;";
	private String sql_select = "SELECT idventa_item,fecha_creado,creado_por,tipo_item,descripcion,cantidad,precio_venta,precio_compra,fk_idventa,fk_idproducto FROM venta_item order by 1 desc;";
	private String sql_cargar = "SELECT idventa_item,fecha_creado,creado_por,tipo_item,descripcion,cantidad,precio_venta,precio_compra,fk_idventa,fk_idproducto FROM venta_item WHERE idventa_item=";
	public void insertar_venta_item(Connection conn, venta_item veit){
		veit.setC1idventa_item(eveconn.getInt_ultimoID_mas_uno(conn, veit.getTb_venta_item(), veit.getId_idventa_item()));
		String titulo = "insertar_venta_item";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,veit.getC1idventa_item());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,veit.getC3creado_por());
			pst.setString(4,veit.getC4tipo_item());
			pst.setString(5,veit.getC5descripcion());
			pst.setDouble(6,veit.getC6cantidad());
			pst.setDouble(7,veit.getC7precio_venta());
			pst.setDouble(8,veit.getC8precio_compra());
			pst.setInt(9,veit.getC9fk_idventa());
			pst.setInt(10,veit.getC10fk_idproducto());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + veit.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + veit.toString(), titulo);
		}
	}
	public void update_venta_item(Connection conn, venta_item veit){
		String titulo = "update_venta_item";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,veit.getC3creado_por());
			pst.setString(3,veit.getC4tipo_item());
			pst.setString(4,veit.getC5descripcion());
			pst.setDouble(5,veit.getC6cantidad());
			pst.setDouble(6,veit.getC7precio_venta());
			pst.setDouble(7,veit.getC8precio_compra());
			pst.setInt(8,veit.getC9fk_idventa());
			pst.setInt(9,veit.getC10fk_idproducto());
			pst.setInt(10,veit.getC1idventa_item());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + veit.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + veit.toString(), titulo);
		}
	}
	public void cargar_venta_item(Connection conn, venta_item veit,int idventa_item){
		String titulo = "Cargar_venta_item";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idventa_item, titulo);
			if(rs.next()){
				veit.setC1idventa_item(rs.getInt(1));
				veit.setC2fecha_creado(rs.getString(2));
				veit.setC3creado_por(rs.getString(3));
				veit.setC4tipo_item(rs.getString(4));
				veit.setC5descripcion(rs.getString(5));
				veit.setC6cantidad(rs.getDouble(6));
				veit.setC7precio_venta(rs.getDouble(7));
				veit.setC8precio_compra(rs.getDouble(8));
				veit.setC9fk_idventa(rs.getInt(9));
				veit.setC10fk_idproducto(rs.getInt(10));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + veit.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + veit.toString(), titulo);
		}
	}
	public void actualizar_tabla_venta_item(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_venta_item(tbltabla);
	}
	public void ancho_tabla_venta_item(JTable tbltabla){
		int Ancho[]={10,10,10,10,10,10,10,10,10,10};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
