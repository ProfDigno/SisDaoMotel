	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.venta_item_insumo;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_venta_item_insumo {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "VENTA_ITEM_INSUMO GUARDADO CORRECTAMENTE";
	private String mensaje_update = "VENTA_ITEM_INSUMO MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO venta_item_insumo(idventa_item_insumo,fecha_creado,creado_por,descripcion,cantidad,precio_venta,precio_compra,fk_idventa,fk_idproducto) VALUES (?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE venta_item_insumo SET fecha_creado=?,creado_por=?,descripcion=?,cantidad=?,precio_venta=?,precio_compra=?,fk_idventa=?,fk_idproducto=? WHERE idventa_item_insumo=?;";
	private String sql_select = "SELECT idventa_item_insumo,fecha_creado,creado_por,descripcion,cantidad,precio_venta,precio_compra,fk_idventa,fk_idproducto FROM venta_item_insumo order by 1 desc;";
	private String sql_cargar = "SELECT idventa_item_insumo,fecha_creado,creado_por,descripcion,cantidad,precio_venta,precio_compra,fk_idventa,fk_idproducto FROM venta_item_insumo WHERE idventa_item_insumo=";
	public void insertar_venta_item_insumo(Connection conn, venta_item_insumo veitin){
		veitin.setC1idventa_item_insumo(eveconn.getInt_ultimoID_mas_uno(conn, veitin.getTb_venta_item_insumo(), veitin.getId_idventa_item_insumo()));
		String titulo = "insertar_venta_item_insumo";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,veitin.getC1idventa_item_insumo());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,veitin.getC3creado_por());
			pst.setString(4,veitin.getC4descripcion());
			pst.setDouble(5,veitin.getC5cantidad());
			pst.setDouble(6,veitin.getC6precio_venta());
			pst.setDouble(7,veitin.getC7precio_compra());
			pst.setInt(8,veitin.getC8fk_idventa());
			pst.setInt(9,veitin.getC9fk_idproducto());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + veitin.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + veitin.toString(), titulo);
		}
	}
	public void update_venta_item_insumo(Connection conn, venta_item_insumo veitin){
		String titulo = "update_venta_item_insumo";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,veitin.getC3creado_por());
			pst.setString(3,veitin.getC4descripcion());
			pst.setDouble(4,veitin.getC5cantidad());
			pst.setDouble(5,veitin.getC6precio_venta());
			pst.setDouble(6,veitin.getC7precio_compra());
			pst.setInt(7,veitin.getC8fk_idventa());
			pst.setInt(8,veitin.getC9fk_idproducto());
			pst.setInt(9,veitin.getC1idventa_item_insumo());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + veitin.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + veitin.toString(), titulo);
		}
	}
	public void cargar_venta_item_insumo(Connection conn, venta_item_insumo veitin,int idventa_item_insumo){
		String titulo = "Cargar_venta_item_insumo";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idventa_item_insumo, titulo);
			if(rs.next()){
				veitin.setC1idventa_item_insumo(rs.getInt(1));
				veitin.setC2fecha_creado(rs.getString(2));
				veitin.setC3creado_por(rs.getString(3));
				veitin.setC4descripcion(rs.getString(4));
				veitin.setC5cantidad(rs.getDouble(5));
				veitin.setC6precio_venta(rs.getDouble(6));
				veitin.setC7precio_compra(rs.getDouble(7));
				veitin.setC8fk_idventa(rs.getInt(8));
				veitin.setC9fk_idproducto(rs.getInt(9));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + veitin.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + veitin.toString(), titulo);
		}
	}
	public void actualizar_tabla_venta_item_insumo(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_venta_item_insumo(tbltabla);
	}
	public void ancho_tabla_venta_item_insumo(JTable tbltabla){
		int Ancho[]={11,11,11,11,11,11,11,11,11};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
