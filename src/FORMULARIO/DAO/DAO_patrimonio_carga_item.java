package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.patrimonio_carga_item;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.patrimonio_carga;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_patrimonio_carga_item {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PATRIMONIO_CARGA_ITEM GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PATRIMONIO_CARGA_ITEM MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO patrimonio_carga_item(idpatrimonio_carga_item,fecha_creado,creado_por,cantidad,descripcion,precio_compra,referencia,fk_idpatrimonio_carga,fk_idpatrimonio_producto) VALUES (?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE patrimonio_carga_item SET fecha_creado=?,creado_por=?,cantidad=?,descripcion=?,precio_compra=?,referencia=?,fk_idpatrimonio_carga=?,fk_idpatrimonio_producto=? WHERE idpatrimonio_carga_item=?;";
    private String sql_select = "SELECT idpatrimonio_carga_item,fecha_creado,creado_por,cantidad,descripcion,precio_compra,referencia,fk_idpatrimonio_carga,fk_idpatrimonio_producto FROM patrimonio_carga_item order by 1 desc;";
    private String sql_cargar = "SELECT idpatrimonio_carga_item,fecha_creado,creado_por,cantidad,descripcion,precio_compra,referencia,fk_idpatrimonio_carga,fk_idpatrimonio_producto FROM patrimonio_carga_item WHERE idpatrimonio_carga_item=";

    public void insertar_patrimonio_carga_item(Connection conn, patrimonio_carga_item ENTpci) {
        ENTpci.setC1idpatrimonio_carga_item(eveconn.getInt_ultimoID_mas_uno(conn, ENTpci.getTb_patrimonio_carga_item(), ENTpci.getId_idpatrimonio_carga_item()));
        String titulo = "insertar_patrimonio_carga_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ENTpci.getC1idpatrimonio_carga_item());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ENTpci.getC3creado_por());
            pst.setInt(4, ENTpci.getC4cantidad());
            pst.setString(5, ENTpci.getC5descripcion());
            pst.setDouble(6, ENTpci.getC6precio_compra());
            pst.setString(7, ENTpci.getC7referencia());
            pst.setInt(8, ENTpci.getC8fk_idpatrimonio_carga());
            pst.setInt(9, ENTpci.getC9fk_idpatrimonio_producto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ENTpci.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ENTpci.toString(), titulo);
        }
    }

    public void update_patrimonio_carga_item(Connection conn, patrimonio_carga_item ENTpci) {
        String titulo = "update_patrimonio_carga_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, ENTpci.getC3creado_por());
            pst.setInt(3, ENTpci.getC4cantidad());
            pst.setString(4, ENTpci.getC5descripcion());
            pst.setDouble(5, ENTpci.getC6precio_compra());
            pst.setString(6, ENTpci.getC7referencia());
            pst.setInt(7, ENTpci.getC8fk_idpatrimonio_carga());
            pst.setInt(8, ENTpci.getC9fk_idpatrimonio_producto());
            pst.setInt(9, ENTpci.getC1idpatrimonio_carga_item());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ENTpci.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ENTpci.toString(), titulo);
        }
    }

    public void cargar_patrimonio_carga_item(Connection conn, patrimonio_carga_item ENTpci, int idpatrimonio_carga_item) {
        String titulo = "Cargar_patrimonio_carga_item";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idpatrimonio_carga_item, titulo);
            if (rs.next()) {
                ENTpci.setC1idpatrimonio_carga_item(rs.getInt(1));
                ENTpci.setC2fecha_creado(rs.getString(2));
                ENTpci.setC3creado_por(rs.getString(3));
                ENTpci.setC4cantidad(rs.getInt(4));
                ENTpci.setC5descripcion(rs.getString(5));
                ENTpci.setC6precio_compra(rs.getDouble(6));
                ENTpci.setC7referencia(rs.getString(7));
                ENTpci.setC8fk_idpatrimonio_carga(rs.getInt(8));
                ENTpci.setC9fk_idpatrimonio_producto(rs.getInt(9));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ENTpci.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ENTpci.toString(), titulo);
        }
    }

    public void actualizar_tabla_patrimonio_carga_item(Connection conn, JTable tbltabla, int fk_idpatrimonio_carga) {
        String sql = "SELECT pci.idpatrimonio_carga_item as idpci,pci.descripcion,\n"
                + "pu.nombre as ubicacion,\n"
                + "pci.referencia,pci.cantidad as cant,\n"
                + "to_char(pci.precio_compra,'999G999G999') as precio,\n"
                + "to_char((pci.cantidad*pci.precio_compra),'999G999G999') as subtotal\n"
                + "FROM patrimonio_carga_item pci,patrimonio_producto pp,patrimonio_ubicacion pu\n"
                + "where pci.fk_idpatrimonio_carga="+fk_idpatrimonio_carga
                + " and pci.fk_idpatrimonio_producto=pp.idpatrimonio_producto\n"
                + "and pp.fk_idpatrimonio_ubicacion=pu.idpatrimonio_ubicacion\n"
                + "order by 1 desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_patrimonio_carga_item(tbltabla);
    }

    public void ancho_tabla_patrimonio_carga_item(JTable tbltabla) {
        int Ancho[] = {5, 36, 15, 15, 5, 12, 12};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 5);
        evejt.alinear_derecha_columna(tbltabla, 6);
    }

    public void insertar_patrimonio_carga_item_de_tabla(Connection conn, JTable tblitem_producto, patrimonio_carga ven) {
        //{"ID", "DESCRIPCION", "UBICACION", "REFERENCIA", "CANT", "PRECIO", "SUBTOTAL", "Oprecio", "Osubtotal", "Oidpatrimonio_carga"};
        String titulo = "insertar_patrimonio_carga_item_de_tabla";
        patrimonio_carga_item item = new patrimonio_carga_item();
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String sfk_idpatrimonio_producto = ((tblitem_producto.getModel().getValueAt(row, 0).toString()));
            int fk_idpatrimonio_producto = Integer.parseInt(sfk_idpatrimonio_producto);
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String referencia = ((tblitem_producto.getModel().getValueAt(row, 3).toString()));
            String scantidad = ((tblitem_producto.getModel().getValueAt(row, 4).toString()));
            int cantidad = Integer.parseInt(scantidad);
            String sprecio_compra = ((tblitem_producto.getModel().getValueAt(row, 7).toString()));
            double precio_compra = Double.parseDouble(sprecio_compra);
            try {
                item.setC3creado_por(ven.getC3creado_por());
                item.setC4cantidad(cantidad);
                item.setC5descripcion(descripcion);
                item.setC6precio_compra(precio_compra);
                item.setC7referencia(referencia);
                item.setC8fk_idpatrimonio_carga(ven.getC1idpatrimonio_carga());
                item.setC9fk_idpatrimonio_producto(fk_idpatrimonio_producto);
                insertar_patrimonio_carga_item(conn, item);
                update_producto_cargar_stock(conn, item);
            } catch (Exception e) {
                evemen.mensaje_error(e, titulo);
                break;
            }

        }
    }
    private void update_producto_cargar_stock(Connection conn, patrimonio_carga_item ENTpci) {
        String titulo = "update_producto_descontar_stock";
        String sql_desc_stock = "UPDATE patrimonio_producto SET stock=(stock+?) WHERE idpatrimonio_producto=?;";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_desc_stock);
            pst.setInt(1, ENTpci.getC4cantidad());
            pst.setInt(2, ENTpci.getC9fk_idpatrimonio_producto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_desc_stock + "\n" + ENTpci.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_desc_stock + "\n" + ENTpci.toString(), titulo);
        }
    }
}
