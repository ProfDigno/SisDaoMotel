package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.patrimonio_baja_item;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.patrimonio_baja;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_patrimonio_baja_item {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PATRIMONIO_BAJA_ITEM GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PATRIMONIO_BAJA_ITEM MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO patrimonio_baja_item(idpatrimonio_baja_item,fecha_creado,creado_por,cantidad,descripcion,precio_compra,referencia,fk_idpatrimonio_producto,fk_idpatrimonio_baja,fk_idpatrimonio_baja_motivo) VALUES (?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE patrimonio_baja_item SET fecha_creado=?,creado_por=?,cantidad=?,descripcion=?,precio_compra=?,referencia=?,fk_idpatrimonio_producto=?,fk_idpatrimonio_baja=?,fk_idpatrimonio_baja_motivo=? WHERE idpatrimonio_baja_item=?;";
    private String sql_select = "SELECT idpatrimonio_baja_item,fecha_creado,creado_por,cantidad,descripcion,precio_compra,referencia,fk_idpatrimonio_producto,fk_idpatrimonio_baja,fk_idpatrimonio_baja_motivo FROM patrimonio_baja_item order by 1 desc;";
    private String sql_cargar = "SELECT idpatrimonio_baja_item,fecha_creado,creado_por,cantidad,descripcion,precio_compra,referencia,fk_idpatrimonio_producto,fk_idpatrimonio_baja,fk_idpatrimonio_baja_motivo FROM patrimonio_baja_item WHERE idpatrimonio_baja_item=";

    public void insertar_patrimonio_baja_item(Connection conn, patrimonio_baja_item ENTpbi) {
        ENTpbi.setC1idpatrimonio_baja_item(eveconn.getInt_ultimoID_mas_uno(conn, ENTpbi.getTb_patrimonio_baja_item(), ENTpbi.getId_idpatrimonio_baja_item()));
        String titulo = "insertar_patrimonio_baja_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ENTpbi.getC1idpatrimonio_baja_item());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ENTpbi.getC3creado_por());
            pst.setInt(4, ENTpbi.getC4cantidad());
            pst.setString(5, ENTpbi.getC5descripcion());
            pst.setDouble(6, ENTpbi.getC6precio_compra());
            pst.setString(7, ENTpbi.getC7referencia());
            pst.setInt(8, ENTpbi.getC8fk_idpatrimonio_producto());
            pst.setInt(9, ENTpbi.getC9fk_idpatrimonio_baja());
            pst.setInt(10, ENTpbi.getC10fk_idpatrimonio_baja_motivo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ENTpbi.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ENTpbi.toString(), titulo);
        }
    }

    public void update_patrimonio_baja_item(Connection conn, patrimonio_baja_item ENTpbi) {
        String titulo = "update_patrimonio_baja_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, ENTpbi.getC3creado_por());
            pst.setInt(3, ENTpbi.getC4cantidad());
            pst.setString(4, ENTpbi.getC5descripcion());
            pst.setDouble(5, ENTpbi.getC6precio_compra());
            pst.setString(6, ENTpbi.getC7referencia());
            pst.setInt(7, ENTpbi.getC8fk_idpatrimonio_producto());
            pst.setInt(8, ENTpbi.getC9fk_idpatrimonio_baja());
            pst.setInt(9, ENTpbi.getC10fk_idpatrimonio_baja_motivo());
            pst.setInt(10, ENTpbi.getC1idpatrimonio_baja_item());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ENTpbi.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ENTpbi.toString(), titulo);
        }
    }

    public void cargar_patrimonio_baja_item(Connection conn, patrimonio_baja_item ENTpbi, int idpatrimonio_baja_item) {
        String titulo = "Cargar_patrimonio_baja_item";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idpatrimonio_baja_item, titulo);
            if (rs.next()) {
                ENTpbi.setC1idpatrimonio_baja_item(rs.getInt(1));
                ENTpbi.setC2fecha_creado(rs.getString(2));
                ENTpbi.setC3creado_por(rs.getString(3));
                ENTpbi.setC4cantidad(rs.getInt(4));
                ENTpbi.setC5descripcion(rs.getString(5));
                ENTpbi.setC6precio_compra(rs.getDouble(6));
                ENTpbi.setC7referencia(rs.getString(7));
                ENTpbi.setC8fk_idpatrimonio_producto(rs.getInt(8));
                ENTpbi.setC9fk_idpatrimonio_baja(rs.getInt(9));
                ENTpbi.setC10fk_idpatrimonio_baja_motivo(rs.getInt(10));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ENTpbi.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ENTpbi.toString(), titulo);
        }
    }

//    public void actualizar_tabla_patrimonio_baja_item(Connection conn, JTable tbltabla) {
//        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
//        ancho_tabla_patrimonio_baja_item(tbltabla);
//    }
//
//    public void ancho_tabla_patrimonio_baja_item(JTable tbltabla) {
//        int Ancho[] = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
//        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
//    }
    public void insertar_patrimonio_baja_item_de_tabla(Connection conn, JTable tblitem_producto, patrimonio_baja ven) {
        //{"ID", "DESCRIPCION", "UBICACION", "REFERENCIA", "CANT", "PRECIO", "SUBTOTAL", "Oprecio", "Osubtotal", "Oidpatrimonio_carga"};
        String titulo = "insertar_patrimonio_carga_item_de_tabla";
        patrimonio_baja_item item = new patrimonio_baja_item();
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String sfk_idpatrimonio_producto = ((tblitem_producto.getModel().getValueAt(row, 0).toString()));
            int fk_idpatrimonio_producto = Integer.parseInt(sfk_idpatrimonio_producto);
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String referencia = ((tblitem_producto.getModel().getValueAt(row, 3).toString()));
            String scantidad = ((tblitem_producto.getModel().getValueAt(row, 4).toString()));
            int cantidad = Integer.parseInt(scantidad);
            String sprecio_compra = ((tblitem_producto.getModel().getValueAt(row, 7).toString()));
            double precio_compra = Double.parseDouble(sprecio_compra);
            String sfk_idpatrimonio_baja_motivo = ((tblitem_producto.getModel().getValueAt(row, 10).toString()));
            int fk_idpatrimonio_baja_motivo = Integer.parseInt(sfk_idpatrimonio_baja_motivo);
            try {
                item.setC3creado_por(ven.getC3creado_por());
                item.setC4cantidad(cantidad);
                item.setC5descripcion(descripcion);
                item.setC6precio_compra(precio_compra);
                item.setC7referencia(referencia);
                item.setC9fk_idpatrimonio_baja(ven.getC1idpatrimonio_baja());
                item.setC8fk_idpatrimonio_producto(fk_idpatrimonio_producto);
                item.setC10fk_idpatrimonio_baja_motivo(fk_idpatrimonio_baja_motivo);
                insertar_patrimonio_baja_item(conn, item);
                update_producto_baja_stock(conn, item);
            } catch (Exception e) {
                evemen.mensaje_error(e, titulo);
                break;
            }

        }
    }
    private void update_producto_baja_stock(Connection conn, patrimonio_baja_item ENTpci) {
        String titulo = "update_producto_descontar_stock";
        String sql_desc_stock = "UPDATE patrimonio_producto SET stock=(stock-?) WHERE idpatrimonio_producto=?;";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_desc_stock);
            pst.setInt(1, ENTpci.getC4cantidad());
            pst.setInt(2, ENTpci.getC8fk_idpatrimonio_producto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_desc_stock + "\n" + ENTpci.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_desc_stock + "\n" + ENTpci.toString(), titulo);
        }
    }
    public void actualizar_tabla_patrimonio_baja_item(Connection conn, JTable tbltabla, int fk_idpatrimonio_baja) {
        String sql = "SELECT pci.idpatrimonio_baja_item as idpbi,pci.descripcion,\n"
                + "pu.nombre as ubicacion,\n"
                + "pci.referencia,pci.cantidad as cant,\n"
                + "to_char(pci.precio_compra,'999G999G999') as precio,\n"
                + "to_char((pci.cantidad*pci.precio_compra),'999G999G999') as subtotal\n"
                + "FROM patrimonio_baja_item pci,patrimonio_producto pp,patrimonio_ubicacion pu\n"
                + "where pci.fk_idpatrimonio_baja="+fk_idpatrimonio_baja
                + " and pci.fk_idpatrimonio_producto=pp.idpatrimonio_producto\n"
                + "and pp.fk_idpatrimonio_ubicacion=pu.idpatrimonio_ubicacion\n"
                + "order by 1 desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_patrimonio_baja_item(tbltabla);
    }

    public void ancho_tabla_patrimonio_baja_item(JTable tbltabla) {
        int Ancho[] = {5, 36, 15, 15, 5, 12, 12};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 5);
        evejt.alinear_derecha_columna(tbltabla, 6);
    }
}
