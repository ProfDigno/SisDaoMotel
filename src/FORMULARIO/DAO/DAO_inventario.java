package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.inventario;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_inventario {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "INVENTARIO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "INVENTARIO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO inventario(idinventario,fecha_creado,creado_por,fecha_inicio,fecha_fin,descripcion,es_abierto,es_cerrado,total_precio_venta,total_precio_compra,fk_idusuario) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE inventario SET fecha_creado=?,creado_por=?,fecha_inicio=?,fecha_fin=?,descripcion=?,es_abierto=?,es_cerrado=?,total_precio_venta=?,total_precio_compra=?,fk_idusuario=? WHERE idinventario=?;";
    private String sql_select = "select i.idinventario as idin,\n"
            + "to_char(i.fecha_inicio,'yyyy-MM-dd HH24:MI') as f_inicio,\n"
            + "to_char(i.fecha_fin,'yyyy-MM-dd HH24:MI') as f_fin,\n"
            + "case when i.es_abierto=true then 'ABIERTO' \n"
            + "when i.es_cerrado=true then 'CERRADO' \n"
            + "else 'otro' end as estado,\n"
            + "i.creado_por\n"
            + "from inventario i\n"
            + "order by 1 desc;";
    private String sql_cargar = "SELECT idinventario,fecha_creado,creado_por,fecha_inicio,fecha_fin,descripcion,es_abierto,es_cerrado,total_precio_venta,total_precio_compra,fk_idusuario FROM inventario WHERE idinventario=";
    private String sql_update_estado = "update inventario \n"
            + "set total_precio_venta=\n"
            + "(SELECT sum(ii.precio_venta*ii.stock_contado) as precioventa FROM inventario_item ii where ii.fk_idinventario=idinventario),\n"
            + "total_precio_compra=\n"
            + "(SELECT sum(ii.precio_compra*ii.stock_contado) as preciocompra FROM inventario_item ii where ii.fk_idinventario=idinventario),\n"
            + "fecha_fin=?,es_abierto=?,es_cerrado=? \n"
            + "where idinventario=?;";
    private String sql_update_stock_inventario = "update producto set stock_actual=ii.stock_contado \n"
            + "from inventario_item ii,inventario i\n"
            + "where idproducto=ii.fk_idproducto\n"
            + "and i.idinventario=ii.fk_idinventario\n"
            + "and i.idinventario=?;";
    private String sql_update_estado_item_inve = "update inventario_item set es_temp=false,es_cargado=true \n"
            + " where  fk_idinventario=?;";

    public void insertar_inventario(Connection conn, inventario in) {
        in.setC1idinventario(eveconn.getInt_ultimoID_mas_uno(conn, in.getTb_inventario(), in.getId_idinventario()));
        String titulo = "insertar_inventario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, in.getC1idinventario());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, in.getC3creado_por());
            pst.setTimestamp(4, evefec.getTimestamp_sistema());
            pst.setTimestamp(5, evefec.getTimestamp_sistema());
            pst.setString(6, in.getC6descripcion());
            pst.setBoolean(7, in.getC7es_abierto());
            pst.setBoolean(8, in.getC8es_cerrado());
            pst.setDouble(9, in.getC9total_precio_venta());
            pst.setDouble(10, in.getC10total_precio_compra());
            pst.setInt(11, in.getC11fk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + in.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + in.toString(), titulo);
        }
    }

    public void update_inventario(Connection conn, inventario in) {
        String titulo = "update_inventario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, in.getC3creado_por());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setTimestamp(4, evefec.getTimestamp_sistema());
            pst.setString(5, in.getC6descripcion());
            pst.setBoolean(6, in.getC7es_abierto());
            pst.setBoolean(7, in.getC8es_cerrado());
            pst.setDouble(8, in.getC9total_precio_venta());
            pst.setDouble(9, in.getC10total_precio_compra());
            pst.setInt(10, in.getC11fk_idusuario());
            pst.setInt(11, in.getC1idinventario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + in.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + in.toString(), titulo);
        }
    }

    public void cargar_inventario(Connection conn, inventario in, int idinventario) {
        String titulo = "Cargar_inventario";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idinventario, titulo);
            if (rs.next()) {
                in.setC1idinventario(rs.getInt(1));
                in.setC2fecha_creado(rs.getString(2));
                in.setC3creado_por(rs.getString(3));
                in.setC4fecha_inicio(rs.getString(4));
                in.setC5fecha_fin(rs.getString(5));
                in.setC6descripcion(rs.getString(6));
                in.setC7es_abierto(rs.getBoolean(7));
                in.setC8es_cerrado(rs.getBoolean(8));
                in.setC9total_precio_venta(rs.getDouble(9));
                in.setC10total_precio_compra(rs.getDouble(10));
                in.setC11fk_idusuario(rs.getInt(11));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + in.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + in.toString(), titulo);
        }
    }

    public void actualizar_tabla_inventario(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_inventario(tbltabla);
    }

    public void ancho_tabla_inventario(JTable tbltabla) {
        int Ancho[] = {5, 25, 25, 15,30};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void update_inventario_estado(Connection conn, inventario inve) {
        String titulo = "update_inventario_estado";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_estado);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setBoolean(2, inve.getC7es_abierto());
            pst.setBoolean(3, inve.getC8es_cerrado());
            pst.setInt(4, inve.getC1idinventario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_estado + "\n" + inve.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_estado + "\n" + inve.toString(), titulo);
        }
    }

    public void update_producto_stock_inventario(Connection conn, inventario inve) {
        String titulo = "update_producto_stock_inventario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_stock_inventario);
            pst.setInt(1, inve.getC1idinventario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_stock_inventario + "\n" + inve.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_stock_inventario + "\n" + inve.toString(), titulo);
        }
    }

    public void update_estado_item_inventario(Connection conn, inventario inve) {
        String titulo = "update_estado_item_inventario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_estado_item_inve);
            pst.setInt(1, inve.getC1idinventario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_estado_item_inve + "\n" + inve.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_estado_item_inve + "\n" + inve.toString(), titulo);
        }
    }

    public int getInt_idinventario_iniciado(Connection conn) {
        String titulo = "getInt_idinventario_iniciado";
        String sql = "select idinventario from inventario where es_abierto=true;";
        int idinventario = -1;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                idinventario = rs.getInt("idinventario");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return idinventario;
    }
}
