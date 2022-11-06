package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.inventario_item;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_inventario_item {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "INVENTARIO_ITEM GUARDADO CORRECTAMENTE";
    private String mensaje_update = "INVENTARIO_ITEM MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO inventario_item(idinventario_item,fecha_creado,creado_por,stock_sistema,stock_contado,precio_venta,precio_compra,es_temp,es_cargado,fk_idinventario,fk_idproducto) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE inventario_item SET fecha_creado=?,creado_por=?,stock_sistema=?,stock_contado=?,precio_venta=?,precio_compra=?,es_temp=?,es_cargado=?,fk_idinventario=?,fk_idproducto=? WHERE idinventario_item=?;";
    private String sql_select = "SELECT idinventario_item,fecha_creado,creado_por,stock_sistema,stock_contado,precio_venta,precio_compra,es_temp,es_cargado,fk_idinventario,fk_idproducto FROM inventario_item order by 1 desc;";
    private String sql_cargar = "SELECT idinventario_item,fecha_creado,creado_por,stock_sistema,stock_contado,precio_venta,precio_compra,es_temp,es_cargado,fk_idinventario,fk_idproducto FROM inventario_item WHERE idinventario_item=";
    private String sql_deletar = "delete from inventario_item  WHERE idinventario_item=?;";

    public void insertar_inventario_item(Connection conn, inventario_item init) {
        init.setC1idinventario_item(eveconn.getInt_ultimoID_mas_uno(conn, init.getTb_inventario_item(), init.getId_idinventario_item()));
        String titulo = "insertar_inventario_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, init.getC1idinventario_item());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, init.getC3creado_por());
            pst.setInt(4, init.getC4stock_sistema());
            pst.setInt(5, init.getC5stock_contado());
            pst.setDouble(6, init.getC6precio_venta());
            pst.setDouble(7, init.getC7precio_compra());
            pst.setBoolean(8, init.getC8es_temp());
            pst.setBoolean(9, init.getC9es_cargado());
            pst.setInt(10, init.getC10fk_idinventario());
            pst.setInt(11, init.getC11fk_idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + init.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + init.toString(), titulo);
        }
    }

    public void update_inventario_item(Connection conn, inventario_item init) {
        String titulo = "update_inventario_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, init.getC3creado_por());
            pst.setInt(3, init.getC4stock_sistema());
            pst.setInt(4, init.getC5stock_contado());
            pst.setDouble(5, init.getC6precio_venta());
            pst.setDouble(6, init.getC7precio_compra());
            pst.setBoolean(7, init.getC8es_temp());
            pst.setBoolean(8, init.getC9es_cargado());
            pst.setInt(9, init.getC10fk_idinventario());
            pst.setInt(10, init.getC11fk_idproducto());
            pst.setInt(11, init.getC1idinventario_item());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + init.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + init.toString(), titulo);
        }
    }

    public void cargar_inventario_item(Connection conn, inventario_item init, int idinventario_item) {
        String titulo = "Cargar_inventario_item";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idinventario_item, titulo);
            if (rs.next()) {
                init.setC1idinventario_item(rs.getInt(1));
                init.setC2fecha_creado(rs.getString(2));
                init.setC3creado_por(rs.getString(3));
                init.setC4stock_sistema(rs.getInt(4));
                init.setC5stock_contado(rs.getInt(5));
                init.setC6precio_venta(rs.getDouble(6));
                init.setC7precio_compra(rs.getDouble(7));
                init.setC8es_temp(rs.getBoolean(8));
                init.setC9es_cargado(rs.getBoolean(9));
                init.setC10fk_idinventario(rs.getInt(10));
                init.setC11fk_idproducto(rs.getInt(11));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + init.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + init.toString(), titulo);
        }
    }

    public void actualizar_tabla_inventario_item(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_inventario_item(tbltabla);
    }

    public void ancho_tabla_inventario_item(JTable tbltabla) {
        int Ancho[] = {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_tabla_item_inventario_diferencia(Connection conn, int idinventario, JTable tbltabla) {
        String sql_diferencia = "SELECT ii.idinventario_item as idii,p.codigo_barra,\n"
                + "(pm.nombre||'-'||pu.nombre||'-'||p.nombre) as marca_unid_nombre,\n"
                + "ii.stock_sistema as st_sis,ii.stock_contado as st_con,(ii.stock_contado-ii.stock_sistema) as st_dif,"
                + "case when ii.es_temp=true and ii.es_cargado=false then 'TEMP' "
                + "when ii.es_temp=false and ii.es_cargado=true then 'CARGA' else 'otro' end as estado,\n"
                + "case when (ii.stock_contado-ii.stock_sistema)>0 then 'P' "
                + "when (ii.stock_contado-ii.stock_sistema)<0 then 'N' else '0' end  as tipo "
                + "FROM producto p,producto_unidad pu,producto_categoria pc,\n"
                + " producto_marca pm,inventario_item ii,inventario i \n"
                + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
                + "and i.idinventario=ii.fk_idinventario\n"
                + "and ii.fk_idproducto=p.idproducto\n"
                //                + "and (ii.estado='TEMP' or ii.estado='CARGA')\n"
                + "and i.idinventario=" + idinventario
                + " order by 1 desc;";
        eveconn.Select_cargar_jtable(conn, sql_diferencia, tbltabla);
        ancho_tabla_item_inventario_diferencia(tbltabla);
//        render.rendertabla_tipo_item_inventario(tbltabla, 7);
    }

    public void ancho_tabla_item_inventario_diferencia(JTable tbltabla) {
        int Ancho[] = {5, 15, 35, 9, 9, 9, 14, 4};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public boolean getBoolean_buscar_codbarra_de_item_inventario(Connection conn, int idinventario, String codbarra) {
        boolean escodbarra = false;
        String titulo = "getBoolean_buscar_codbarra_de_item_inventario";
        String sql = "SELECT p.codigo_barra\n"
                + "FROM producto p,producto_unidad pu,producto_categoria pc,\n"
                + " producto_marca pm,inventario_item ii,inventario i \n"
                + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
                + "and i.idinventario=ii.fk_idinventario\n"
                + "and ii.fk_idproducto=p.idproducto\n"
                //                + "and (ii.estado='TEMP' or ii.estado='CARGA')\n"
                + "and i.idinventario=" + idinventario
                + " and p.codigo_barra='" + codbarra + "'";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                escodbarra = true;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return escodbarra;
    }

    public void deletar_item_inventario(Connection conn, inventario_item iinven) {
        String titulo = "deletar_item_inventario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_deletar);
            pst.setInt(1, iinven.getC1idinventario_item());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_deletar + "\n" + iinven.toString(), titulo);
//            evemen.modificado_correcto(mensaje_delete, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_deletar + "\n" + iinven.toString(), titulo);
        }
    }

    public void imprimir_inventario_cargado(Connection conn,int idinventario) {
        String sql = "select i.idinventario as idin,\n"
                + "to_char(i.fecha_inicio,'yyyy-MM-dd HH24:MI') as f_inicio,\n"
                + "to_char(i.fecha_fin,'yyyy-MM-dd HH24:MI') as f_fin,\n"
                + "case when i.es_abierto=true then 'ABIERTO' \n"
                + "when i.es_cerrado=true then 'CERRADO' \n"
                + "else 'otro' end as estado,\n"
                + "i.creado_por as usuario,i.descripcion as desc_inven,\n"
                + "p.codigo_barra as codbarra,"
                + "p.idproducto as idp,pc.nombre as categoria,\n"
                + "pm.nombre as marca,pu.nombre as unidad,\n"
                + "p.nombre as producto,\n"
                + "ii.stock_sistema as stock,ii.stock_contado as contado,\n"
                + "(ii.stock_contado-ii.stock_sistema) as diferen\n"
                + "from inventario i,inventario_item ii,\n"
                + "producto p,producto_categoria pc,producto_marca pm,producto_unidad pu  \n"
                + "where i.idinventario="+idinventario
                + " and i.idinventario=ii.fk_idinventario\n"
                + "and ii.fk_idproducto=p.idproducto \n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca \n"
                + "and p.fk_idproducto_unidad=pu.idproducto_unidad \n"
                + "order by pc.nombre desc,pm.nombre desc,pu.nombre desc; ";
         String titulonota = "INVENTARIO";
        String direccion = "src/REPORTE/PRODUCTO/repInventarioCargaST.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
}
