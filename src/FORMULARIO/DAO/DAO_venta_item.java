package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import ESTADOS.EvenEstado;
import FORMULARIO.ENTIDAD.venta_item;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.producto;
import FORMULARIO.ENTIDAD.venta;
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
    EvenEstado eveest = new EvenEstado();
    private String mensaje_insert = "VENTA_ITEM GUARDADO CORRECTAMENTE";
    private String mensaje_update = "VENTA_ITEM MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO venta_item(idventa_item,fecha_creado,creado_por,tipo_item,descripcion,cantidad,precio_venta,precio_compra,fk_idventa,fk_idproducto) VALUES (?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE venta_item SET fecha_creado=?,creado_por=?,tipo_item=?,descripcion=?,cantidad=?,precio_venta=?,precio_compra=?,fk_idventa=?,fk_idproducto=? WHERE idventa_item=?;";
    private String sql_select = "SELECT idventa_item,fecha_creado,creado_por,tipo_item,descripcion,cantidad,precio_venta,precio_compra,fk_idventa,fk_idproducto FROM venta_item order by 1 desc;";
    private String sql_cargar = "SELECT idventa_item,fecha_creado,creado_por,tipo_item,descripcion,cantidad,precio_venta,precio_compra,fk_idventa,fk_idproducto FROM venta_item WHERE idventa_item=";
    private String sql_desc_stock = "UPDATE producto SET stock_actual=(stock_actual-?) WHERE idproducto=?;";

    public void insertar_venta_item(Connection conn, venta_item veit) {
        veit.setC1idventa_item(eveconn.getInt_ultimoID_mas_uno(conn, veit.getTb_venta_item(), veit.getId_idventa_item()));
        String titulo = "insertar_venta_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, veit.getC1idventa_item());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, veit.getC3creado_por());
            pst.setString(4, veit.getC4tipo_item());
            pst.setString(5, veit.getC5descripcion());
            pst.setDouble(6, veit.getC6cantidad());
            pst.setDouble(7, veit.getC7precio_venta());
            pst.setDouble(8, veit.getC8precio_compra());
            pst.setInt(9, veit.getC9fk_idventa());
            pst.setInt(10, veit.getC10fk_idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + veit.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + veit.toString(), titulo);
        }
    }

    public void insertar_item_venta_de_tabla(Connection conn, JTable tblitem_producto, venta_item item, venta ven,
            boolean es_mudar, boolean es_desc_stock, boolean es_agre_stock) {
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String idproducto = ((tblitem_producto.getModel().getValueAt(row, 0).toString()));
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String cantidad = ((tblitem_producto.getModel().getValueAt(row, 3).toString()));
            String precio_venta = ((tblitem_producto.getModel().getValueAt(row, 5).toString()));
            String precio_compra = ((tblitem_producto.getModel().getValueAt(row, 6).toString()));
            String tipo = ((tblitem_producto.getModel().getValueAt(row, 7).toString()));
            try {
                item.setC3creado_por(ven.getC3creado_por());
                item.setC5descripcion(descripcion);
                item.setC6cantidad(Double.parseDouble(cantidad));
                item.setC7precio_venta(Double.parseDouble(precio_venta));
                item.setC8precio_compra(Double.parseDouble(precio_compra));
                item.setC9fk_idventa(ven.getC1idventa());
                item.setC10fk_idproducto(Integer.parseInt(idproducto));
                if (tipo.equals(item.getTipo_temporal()) || es_mudar) {
                    item.setC4tipo_item(eveest.getEst_Cargado());
                    insertar_venta_item(conn, item);
                }
                if (es_desc_stock) {
                    update_producto_descontar_stock(conn, item);
                }
            } catch (Exception e) {
                evemen.mensaje_error(e, "insertar_item_venta_de_tabla");
                break;
            }

        }
    }

    public void update_venta_item(Connection conn, venta_item veit) {
        String titulo = "update_venta_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, veit.getC3creado_por());
            pst.setString(3, veit.getC4tipo_item());
            pst.setString(4, veit.getC5descripcion());
            pst.setDouble(5, veit.getC6cantidad());
            pst.setDouble(6, veit.getC7precio_venta());
            pst.setDouble(7, veit.getC8precio_compra());
            pst.setInt(8, veit.getC9fk_idventa());
            pst.setInt(9, veit.getC10fk_idproducto());
            pst.setInt(10, veit.getC1idventa_item());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + veit.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + veit.toString(), titulo);
        }
    }
    public void update_venta_item_CANCELAR(Connection conn, venta_item veit) {
        String titulo = "update_venta_item_CANCELAR";
        String sql="UPDATE venta_item SET tipo_item=? WHERE fk_idventa=?;";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, veit.getC4tipo_item());
            pst.setInt(2, veit.getC9fk_idventa());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql + "\n" + veit.toString(), titulo);
//            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql + "\n" + veit.toString(), titulo);
        }
    }
    public void cargar_venta_item(Connection conn, venta_item veit, int idventa_item) {
        String titulo = "Cargar_venta_item";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idventa_item, titulo);
            if (rs.next()) {
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

    public void actualizar_tabla_venta_item(Connection conn, JTable tbltabla, int fk_idventa) {
//        String dato[] = {"id", "descripcion", "precio", "C", "total", "Opventa", "Opcompra", "Otipo", "Ototal"};
        sql_select = "SELECT fk_idproducto as id,descripcion,"
                + "to_char(precio_venta,'9G999G999') as precio,"
                + "cantidad as cant,"
                + "to_char(precio_venta*cantidad,'9G999G999') as total "
                + "FROM venta_item "
                + "where tipo_item='" + eveest.getEst_Cargado() + "' and fk_idventa=" + fk_idventa
                + " order by 1 desc;";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_venta_item(tbltabla);
    }

    public void ancho_tabla_venta_item(JTable tbltabla) {
        int Ancho[] = {5, 60, 15, 5, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void eliminar_venta_item(Connection conn, venta_item veit) {
        String titulo = "eliminar_venta_item";
        String sql = "UPDATE venta_item SET "
                + "tipo_item=?,cantidad=?,"
                + "precio_venta=?,precio_compra=? "
                + "WHERE fk_idventa=? and fk_idproducto=?;";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, veit.getC4tipo_item());
            pst.setDouble(2, veit.getC6cantidad());
            pst.setDouble(3, veit.getC7precio_venta());
            pst.setDouble(4, veit.getC8precio_compra());
            pst.setInt(5, veit.getC9fk_idventa());
            pst.setInt(6, veit.getC10fk_idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql + "\n" + veit.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql + "\n" + veit.toString(), titulo);
        }
    }

    private void update_producto_descontar_stock(Connection conn, venta_item veit) {
        String titulo = "update_producto_descontar_stock";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_desc_stock);
            pst.setDouble(1, veit.getC6cantidad());
            pst.setInt(2, veit.getC10fk_idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_desc_stock + "\n" + veit.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_desc_stock + "\n" + veit.toString(), titulo);
        }
    }

    public void actualizar_tabla_venta_item_producto(Connection conn, JTable tbltabla, String filtro) {
        String sql_select = "select vi.idventa_item as idvi,\n"
                + "to_char(vi.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha,\n"
                + "vi.creado_por,vi.descripcion,"
                + "TRIM(to_char(vi.precio_venta,'999G999G999')) as pventa,\n"
                + "vi.cantidad as cant,\n"
                + "TRIM(to_char((vi.cantidad*vi.precio_venta),'999G999G999')) as subtotal,\n"
                + "(vi.cantidad*vi.precio_venta) as osubtotal "
                + "from venta_item vi,venta v "
                + "where vi.fk_idventa=v.idventa "
                + "and vi.tipo_item='" + eveest.getEst_Cargado() + "' "+filtro
                + " order by 1 desc";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_venta_item_producto(tbltabla);
    }

    public void ancho_tabla_venta_item_producto(JTable tbltabla) {
        int Ancho[] = {4,15,15,40, 10, 5, 10,1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.ocultar_columna(tbltabla, 7);
    }
}
