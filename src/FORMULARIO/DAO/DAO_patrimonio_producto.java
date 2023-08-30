package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.patrimonio_producto;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.patrimonio_baja;
import FORMULARIO.ENTIDAD.patrimonio_carga;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DAO_patrimonio_producto {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PATRIMONIO_PRODUCTO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PATRIMONIO_PRODUCTO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO patrimonio_producto(idpatrimonio_producto,fecha_creado,creado_por,nombre,referencia,precio_compra,tipo,estado,stock,fk_idpatrimonio_ubicacion,fk_idpatrimonio_categoria) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE patrimonio_producto SET fecha_creado=?,creado_por=?,nombre=?,referencia=?,precio_compra=?,tipo=?,estado=?,stock=?,fk_idpatrimonio_ubicacion=?,fk_idpatrimonio_categoria=? WHERE idpatrimonio_producto=?;";
//    private String sql_select = "SELECT idpatrimonio_producto as idpp,nombre as producto,referencia as refe,"
//            + "TRIM(to_char(precio_compra,'999G999G999')) as precio,estado,stock as st "
//            + " FROM patrimonio_producto order by 1 desc;";
    private String sql_cargar = "SELECT idpatrimonio_producto,fecha_creado,creado_por,nombre,referencia,precio_compra,tipo,estado,stock,fk_idpatrimonio_ubicacion,fk_idpatrimonio_categoria FROM patrimonio_producto WHERE idpatrimonio_producto=";

    public void insertar_patrimonio_producto(Connection conn, patrimonio_producto ENTpp) {
        ENTpp.setC1idpatrimonio_producto(eveconn.getInt_ultimoID_mas_uno(conn, ENTpp.getTb_patrimonio_producto(), ENTpp.getId_idpatrimonio_producto()));
        String titulo = "insertar_patrimonio_producto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ENTpp.getC1idpatrimonio_producto());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ENTpp.getC3creado_por());
            pst.setString(4, ENTpp.getC4nombre());
            pst.setString(5, ENTpp.getC5referencia());
            pst.setDouble(6, ENTpp.getC6precio_compra());
            pst.setString(7, ENTpp.getC7tipo());
            pst.setString(8, ENTpp.getC8estado());
            pst.setInt(9, ENTpp.getC9stock());
            pst.setInt(10, ENTpp.getC10fk_idpatrimonio_ubicacion());
            pst.setInt(11, ENTpp.getC11fk_idpatrimonio_categoria());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ENTpp.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ENTpp.toString(), titulo);
        }
    }

    public void update_patrimonio_producto(Connection conn, patrimonio_producto ENTpp) {
        String titulo = "update_patrimonio_producto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, ENTpp.getC3creado_por());
            pst.setString(3, ENTpp.getC4nombre());
            pst.setString(4, ENTpp.getC5referencia());
            pst.setDouble(5, ENTpp.getC6precio_compra());
            pst.setString(6, ENTpp.getC7tipo());
            pst.setString(7, ENTpp.getC8estado());
            pst.setInt(8, ENTpp.getC9stock());
            pst.setInt(9, ENTpp.getC10fk_idpatrimonio_ubicacion());
            pst.setInt(10, ENTpp.getC11fk_idpatrimonio_categoria());
            pst.setInt(11, ENTpp.getC1idpatrimonio_producto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ENTpp.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ENTpp.toString(), titulo);
        }
    }

    public void cargar_patrimonio_producto(Connection conn, patrimonio_producto ENTpp, int idpatrimonio_producto) {
        String titulo = "Cargar_patrimonio_producto";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idpatrimonio_producto, titulo);
            if (rs.next()) {
                ENTpp.setC1idpatrimonio_producto(rs.getInt(1));
                ENTpp.setC2fecha_creado(rs.getString(2));
                ENTpp.setC3creado_por(rs.getString(3));
                ENTpp.setC4nombre(rs.getString(4));
                ENTpp.setC5referencia(rs.getString(5));
                ENTpp.setC6precio_compra(rs.getDouble(6));
                ENTpp.setC7tipo(rs.getString(7));
                ENTpp.setC8estado(rs.getString(8));
                ENTpp.setC9stock(rs.getInt(9));
                ENTpp.setC10fk_idpatrimonio_ubicacion(rs.getInt(10));
                ENTpp.setC11fk_idpatrimonio_categoria(rs.getInt(11));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ENTpp.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ENTpp.toString(), titulo);
        }
    }

    public void actualizar_tabla_patrimonio_producto(Connection conn, JTable tbltabla) {
        String sql_select = "SELECT idpatrimonio_producto as idpp,nombre as producto,referencia as refe,"
                + "TRIM(to_char(precio_compra,'999G999G999')) as precio,estado,stock as st "
                + " FROM patrimonio_producto "
                + "order by 1 desc;";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_patrimonio_producto(tbltabla);
    }

    public void actualizar_tabla_patrimonio_producto(Connection conn, JTable tbltabla, JTextField txtcategoria) {
        if (txtcategoria.getText().trim().length() > 0) {
            String categoria = txtcategoria.getText();
            String sql_select = "SELECT idpatrimonio_producto as idpp,nombre as producto,referencia as refe,"
                    + "TRIM(to_char(precio_compra,'999G999G999')) as precio,estado,stock as st "
                    + " FROM patrimonio_producto "
                    + " where nombre ilike '%" + categoria + "%' "
                    + "order by 1 desc;";
            eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
            ancho_tabla_patrimonio_producto(tbltabla);
        }
    }

    public void ancho_tabla_patrimonio_producto(JTable tbltabla) {
        int Ancho[] = {5, 50, 15, 12, 12, 6};
        evejt.alinear_derecha_columna(tbltabla, 3);
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_tabla_patrimonio_producto_carga(Connection conn, JTable tbltabla,String campo, JTextField txtbuscar) {
        if (txtbuscar.getText().trim().length() > 0) {
            String categoria = txtbuscar.getText();
            String sql_select = "select pp.idpatrimonio_producto as idpp,\n"
                    + "(pp.nombre||'-('||pp.tipo||')') as producto,\n"
                    + "pu.nombre as ubicacion,pp.referencia,\n"
                    + "to_char(pp.precio_compra,'999G999G999') as precio,\n"
                    + "pp.stock,pp.estado as estado \n"
                    + "from patrimonio_producto pp,patrimonio_ubicacion pu\n"
                    + "where pp.fk_idpatrimonio_ubicacion=pu.idpatrimonio_ubicacion\n"
                    + "and "+campo+" ilike '%" + categoria + "%' "
                    + "order by pp.nombre asc";
            eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
            ancho_tabla_patrimonio_producto_carga(tbltabla);
        }
    }

    public void ancho_tabla_patrimonio_producto_carga(JTable tbltabla) {
        int Ancho[] = {5, 40,15, 15, 10, 5, 10};
        evejt.alinear_derecha_columna(tbltabla, 4);
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void update_patrimonio_producto_anular_carga(Connection conn, patrimonio_carga ENTpc) {
        String titulo = "update_patrimonio_producto_anular_carga";
        PreparedStatement pst = null;
        String sql = "update patrimonio_producto set stock=(stock-patrimonio_carga_item.cantidad)\n"
                + "from patrimonio_carga_item\n"
                + "where patrimonio_carga_item.fk_idpatrimonio_producto=idpatrimonio_producto\n"
                + "and fk_idpatrimonio_carga=?;";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, ENTpc.getC1idpatrimonio_carga());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql + "\n" + ENTpc.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql + "\n" + ENTpc.toString(), titulo);
        }
    }
    public void update_patrimonio_producto_anular_baja(Connection conn, patrimonio_baja ENTpb) {
        String titulo = "update_patrimonio_producto_anular_carga";
        PreparedStatement pst = null;
        String sql = "update patrimonio_producto set stock=(stock+patrimonio_baja_item.cantidad)\n"
                + "from patrimonio_baja_item\n"
                + "where patrimonio_baja_item.fk_idpatrimonio_producto=idpatrimonio_producto\n"
                + "and fk_idpatrimonio_baja=?;";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, ENTpb.getC1idpatrimonio_baja());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql + "\n" + ENTpb.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql + "\n" + ENTpb.toString(), titulo);
        }
    }
}
