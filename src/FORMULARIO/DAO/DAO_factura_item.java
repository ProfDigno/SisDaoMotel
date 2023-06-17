package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.factura_item;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.factura;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_factura_item {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "FACTURA_ITEM GUARDADO CORRECTAMENTE";
    private String mensaje_update = "FACTURA_ITEM MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO factura_item(idfactura_item,fecha_creado,creado_por,descripcion,cantidad,precio_iva5,precio_iva10,precio_exenta,tipo_item,fk_idfactura,fk_idproducto) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE factura_item SET fecha_creado=?,creado_por=?,descripcion=?,cantidad=?,precio_iva5=?,precio_iva10=?,precio_exenta=?,tipo_item=?,fk_idfactura=?,fk_idproducto=? WHERE idfactura_item=?;";
    private String sql_select = "SELECT idfactura_item,fecha_creado,creado_por,descripcion,cantidad,precio_iva5,precio_iva10,precio_exenta,tipo_item,fk_idfactura,fk_idproducto FROM factura_item order by 1 desc;";
    private String sql_cargar = "SELECT idfactura_item,fecha_creado,creado_por,descripcion,cantidad,precio_iva5,precio_iva10,precio_exenta,tipo_item,fk_idfactura,fk_idproducto FROM factura_item WHERE idfactura_item=";

    public void insertar_factura_item(Connection conn, factura_item fi) {
        fi.setC1idfactura_item(eveconn.getInt_ultimoID_mas_uno(conn, fi.getTb_factura_item(), fi.getId_idfactura_item()));
        String titulo = "insertar_factura_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, fi.getC1idfactura_item());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, fi.getC3creado_por());
            pst.setString(4, fi.getC4descripcion());
            pst.setDouble(5, fi.getC5cantidad());
            pst.setDouble(6, fi.getC6precio_iva5());
            pst.setDouble(7, fi.getC7precio_iva10());
            pst.setDouble(8, fi.getC8precio_exenta());
            pst.setString(9, fi.getC9tipo_item());
            pst.setInt(10, fi.getC10fk_idfactura());
            pst.setInt(11, fi.getC11fk_idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + fi.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + fi.toString(), titulo);
        }
    }

    public void update_factura_item(Connection conn, factura_item fi) {
        String titulo = "update_factura_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, fi.getC3creado_por());
            pst.setString(3, fi.getC4descripcion());
            pst.setDouble(4, fi.getC5cantidad());
            pst.setDouble(5, fi.getC6precio_iva5());
            pst.setDouble(6, fi.getC7precio_iva10());
            pst.setDouble(7, fi.getC8precio_exenta());
            pst.setString(8, fi.getC9tipo_item());
            pst.setInt(9, fi.getC10fk_idfactura());
            pst.setInt(10, fi.getC11fk_idproducto());
            pst.setInt(11, fi.getC1idfactura_item());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + fi.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + fi.toString(), titulo);
        }
    }

    public void cargar_factura_item(Connection conn, factura_item fi, int idfactura_item) {
        String titulo = "Cargar_factura_item";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idfactura_item, titulo);
            if (rs.next()) {
                fi.setC1idfactura_item(rs.getInt(1));
                fi.setC2fecha_creado(rs.getString(2));
                fi.setC3creado_por(rs.getString(3));
                fi.setC4descripcion(rs.getString(4));
                fi.setC5cantidad(rs.getDouble(5));
                fi.setC6precio_iva5(rs.getDouble(6));
                fi.setC7precio_iva10(rs.getDouble(7));
                fi.setC8precio_exenta(rs.getDouble(8));
                fi.setC9tipo_item(rs.getString(9));
                fi.setC10fk_idfactura(rs.getInt(10));
                fi.setC11fk_idproducto(rs.getInt(11));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + fi.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + fi.toString(), titulo);
        }
    }

    public void actualizar_tabla_factura_item(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_factura_item(tbltabla);
    }

    public void ancho_tabla_factura_item(JTable tbltabla) {
        int Ancho[] = {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void insertar_factura_item_de_tabla(Connection conn, JTable tblitem_producto, factura_item ENTfi, factura ENTf) {
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String idproducto = ((tblitem_producto.getModel().getValueAt(row, 0).toString()));
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String cantidad = ((tblitem_producto.getModel().getValueAt(row, 3).toString()));
            String precio_venta = ((tblitem_producto.getModel().getValueAt(row, 5).toString()));
            String precio_compra = ((tblitem_producto.getModel().getValueAt(row, 6).toString()));
            String tipo = ((tblitem_producto.getModel().getValueAt(row, 7).toString()));
            try {
                ENTfi.setC3creado_por(ENTf.getC3creado_por());
                ENTfi.setC4descripcion(descripcion);
                ENTfi.setC5cantidad(Double.parseDouble(cantidad));
                ENTfi.setC6precio_iva5(0);
                ENTfi.setC7precio_iva10(Double.parseDouble(precio_venta));
                ENTfi.setC8precio_exenta(0);
                ENTfi.setC9tipo_item(tipo);
                ENTfi.setC10fk_idfactura(ENTf.getC1idfactura());
                ENTfi.setC11fk_idproducto(Integer.parseInt(idproducto));
                insertar_factura_item(conn, ENTfi);
            } catch (Exception e) {
                evemen.mensaje_error(e, "insertar_item_venta_de_tabla");
                break;
            }

        }
    }
    public void insertar_factura_item_de_factura(Connection conn, JTable tblitem_producto, factura_item ENTfi, factura ENTf) {
        String dato[] = {"ID", "DESCRIPCION", "IVA", "PRECIO", "CANT", "SUBTOTAL",
            "Oprecio", "OpIvaExe", "OpIva5", "OpIva10", "OtIvaExe", "OtIva5", "OtIva10", "Osubtotal"};
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String idproducto = ((tblitem_producto.getModel().getValueAt(row, 0).toString()));
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String cantidad = ((tblitem_producto.getModel().getValueAt(row, 4).toString()));
            String precio_exe = ((tblitem_producto.getModel().getValueAt(row, 7).toString()));
            String precio_iva5 = ((tblitem_producto.getModel().getValueAt(row, 8).toString()));
            String precio_iva10 = ((tblitem_producto.getModel().getValueAt(row, 9).toString()));
            String tipo = "FACTURA";
            try {
                ENTfi.setC3creado_por(ENTf.getC3creado_por());
                ENTfi.setC4descripcion(descripcion);
                ENTfi.setC5cantidad(Double.parseDouble(cantidad));
                ENTfi.setC6precio_iva5(Double.parseDouble(precio_iva5));
                ENTfi.setC7precio_iva10(Double.parseDouble(precio_iva10));
                ENTfi.setC8precio_exenta(Double.parseDouble(precio_exe));
                ENTfi.setC9tipo_item(tipo);
                ENTfi.setC10fk_idfactura(ENTf.getC1idfactura());
                ENTfi.setC11fk_idproducto(Integer.parseInt(idproducto));
                insertar_factura_item(conn, ENTfi);
            } catch (Exception e) {
                evemen.mensaje_error(e, "insertar_item_venta_de_tabla");
                break;
            }

        }
    }
}
