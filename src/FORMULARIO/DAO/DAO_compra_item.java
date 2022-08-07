	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.compra_item;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_compra_item {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "COMPRA_ITEM GUARDADO CORRECTAMENTE";
	private String mensaje_update = "COMPRA_ITEM MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO compra_item(idcompra_item,fecha_creado,creado_por,tipo_item,descripcion,cantidad,precio_compra,fk_idproducto,fk_idcompra) VALUES (?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE compra_item SET fecha_creado=?,creado_por=?,tipo_item=?,descripcion=?,cantidad=?,precio_compra=?,fk_idproducto=?,fk_idcompra=? WHERE idcompra_item=?;";
	private String sql_select = "SELECT idcompra_item,fecha_creado,creado_por,tipo_item,descripcion,cantidad,precio_compra,fk_idproducto,fk_idcompra FROM compra_item order by 1 desc;";
	private String sql_cargar = "SELECT idcompra_item,fecha_creado,creado_por,tipo_item,descripcion,cantidad,precio_compra,fk_idproducto,fk_idcompra FROM compra_item WHERE idcompra_item=";
	public void insertar_compra_item(Connection conn, compra_item coit){
		coit.setC1idcompra_item(eveconn.getInt_ultimoID_mas_uno(conn, coit.getTb_compra_item(), coit.getId_idcompra_item()));
		String titulo = "insertar_compra_item";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,coit.getC1idcompra_item());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,coit.getC3creado_por());
			pst.setString(4,coit.getC4tipo_item());
			pst.setString(5,coit.getC5descripcion());
			pst.setDouble(6,coit.getC6cantidad());
			pst.setDouble(7,coit.getC7precio_compra());
			pst.setInt(8,coit.getC8fk_idproducto());
			pst.setInt(9,coit.getC9fk_idcompra());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + coit.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + coit.toString(), titulo);
		}
	}
	public void update_compra_item(Connection conn, compra_item coit){
		String titulo = "update_compra_item";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,coit.getC3creado_por());
			pst.setString(3,coit.getC4tipo_item());
			pst.setString(4,coit.getC5descripcion());
			pst.setDouble(5,coit.getC6cantidad());
			pst.setDouble(6,coit.getC7precio_compra());
			pst.setInt(7,coit.getC8fk_idproducto());
			pst.setInt(8,coit.getC9fk_idcompra());
			pst.setInt(9,coit.getC1idcompra_item());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + coit.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + coit.toString(), titulo);
		}
	}
	public void cargar_compra_item(Connection conn, compra_item coit,int idcompra_item){
		String titulo = "Cargar_compra_item";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idcompra_item, titulo);
			if(rs.next()){
				coit.setC1idcompra_item(rs.getInt(1));
				coit.setC2fecha_creado(rs.getString(2));
				coit.setC3creado_por(rs.getString(3));
				coit.setC4tipo_item(rs.getString(4));
				coit.setC5descripcion(rs.getString(5));
				coit.setC6cantidad(rs.getDouble(6));
				coit.setC7precio_compra(rs.getDouble(7));
				coit.setC8fk_idproducto(rs.getInt(8));
				coit.setC9fk_idcompra(rs.getInt(9));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + coit.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + coit.toString(), titulo);
		}
	}
	public void actualizar_tabla_compra_item(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_compra_item(tbltabla);
	}
	public void ancho_tabla_compra_item(JTable tbltabla){
		int Ancho[]={11,11,11,11,11,11,11,11,11};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
