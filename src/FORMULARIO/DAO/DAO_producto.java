package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.producto;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_producto {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PRODUCTO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO producto(idproducto,fecha_creado,creado_por,codigo_barra,nombre,controlar_stock,es_venta,es_compra,es_insumo,es_patrimonio,precio_venta,precio_compra,stock_actual,stock_minimo,stock_maximo,iva,fk_idproducto_categoria,fk_idproducto_unidad,fk_idproducto_marca) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE producto SET fecha_creado=?,creado_por=?,codigo_barra=?,nombre=?,controlar_stock=?,es_venta=?,es_compra=?,es_insumo=?,es_patrimonio=?,precio_venta=?,precio_compra=?,stock_actual=?,stock_minimo=?,stock_maximo=?,iva=?,fk_idproducto_categoria=?,fk_idproducto_unidad=?,fk_idproducto_marca=? WHERE idproducto=?;";
//    private String sql_select = "SELECT idproducto,fecha_creado,creado_por,codigo_barra,nombre,controlar_stock,es_venta,es_compra,es_insumo,es_patrimonio,precio_venta,precio_compra,stock_actual,stock_minimo,stock_maximo,iva,fk_idproducto_categoria,fk_idproducto_unidad,fk_idproducto_marca FROM producto order by 1 desc;";
    private String sql_cargar = "SELECT idproducto,fecha_creado,creado_por,codigo_barra,nombre,controlar_stock,es_venta,es_compra,es_insumo,es_patrimonio,precio_venta,precio_compra,stock_actual,stock_minimo,stock_maximo,iva,fk_idproducto_categoria,fk_idproducto_unidad,fk_idproducto_marca FROM producto WHERE idproducto=";

    public void insertar_producto(Connection conn, producto pr) {
        pr.setC1idproducto(eveconn.getInt_ultimoID_mas_uno(conn, pr.getTb_producto(), pr.getId_idproducto()));
        String titulo = "insertar_producto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, pr.getC1idproducto());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, pr.getC3creado_por());
            pst.setString(4, pr.getC4codigo_barra());
            pst.setString(5, pr.getC5nombre());
            pst.setBoolean(6, pr.getC6controlar_stock());
            pst.setBoolean(7, pr.getC7es_venta());
            pst.setBoolean(8, pr.getC8es_compra());
            pst.setBoolean(9, pr.getC9es_insumo());
            pst.setBoolean(10, pr.getC10es_patrimonio());
            pst.setDouble(11, pr.getC11precio_venta());
            pst.setDouble(12, pr.getC12precio_compra());
            pst.setDouble(13, pr.getC13stock_actual());
            pst.setDouble(14, pr.getC14stock_minimo());
            pst.setDouble(15, pr.getC15stock_maximo());
            pst.setDouble(16, pr.getC16iva());
            pst.setInt(17, pr.getC17fk_idproducto_categoria());
            pst.setInt(18, pr.getC18fk_idproducto_unidad());
            pst.setInt(19, pr.getC19fk_idproducto_marca());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + pr.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + pr.toString(), titulo);
        }
    }

    public void update_producto(Connection conn, producto pr) {
        String titulo = "update_producto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, pr.getC3creado_por());
            pst.setString(3, pr.getC4codigo_barra());
            pst.setString(4, pr.getC5nombre());
            pst.setBoolean(5, pr.getC6controlar_stock());
            pst.setBoolean(6, pr.getC7es_venta());
            pst.setBoolean(7, pr.getC8es_compra());
            pst.setBoolean(8, pr.getC9es_insumo());
            pst.setBoolean(9, pr.getC10es_patrimonio());
            pst.setDouble(10, pr.getC11precio_venta());
            pst.setDouble(11, pr.getC12precio_compra());
            pst.setDouble(12, pr.getC13stock_actual());
            pst.setDouble(13, pr.getC14stock_minimo());
            pst.setDouble(14, pr.getC15stock_maximo());
            pst.setDouble(15, pr.getC16iva());
            pst.setInt(16, pr.getC17fk_idproducto_categoria());
            pst.setInt(17, pr.getC18fk_idproducto_unidad());
            pst.setInt(18, pr.getC19fk_idproducto_marca());
            pst.setInt(19, pr.getC1idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + pr.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + pr.toString(), titulo);
        }
    }

    public void cargar_producto(Connection conn, producto pr, int idproducto) {
        String titulo = "Cargar_producto";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idproducto, titulo);
            if (rs.next()) {
                pr.setC1idproducto(rs.getInt(1));
                pr.setC2fecha_creado(rs.getString(2));
                pr.setC3creado_por(rs.getString(3));
                pr.setC4codigo_barra(rs.getString(4));
                pr.setC5nombre(rs.getString(5));
                pr.setC6controlar_stock(rs.getBoolean(6));
                pr.setC7es_venta(rs.getBoolean(7));
                pr.setC8es_compra(rs.getBoolean(8));
                pr.setC9es_insumo(rs.getBoolean(9));
                pr.setC10es_patrimonio(rs.getBoolean(10));
                pr.setC11precio_venta(rs.getDouble(11));
                pr.setC12precio_compra(rs.getDouble(12));
                pr.setC13stock_actual(rs.getDouble(13));
                pr.setC14stock_minimo(rs.getDouble(14));
                pr.setC15stock_maximo(rs.getDouble(15));
                pr.setC16iva(rs.getDouble(16));
                pr.setC17fk_idproducto_categoria(rs.getInt(17));
                pr.setC18fk_idproducto_unidad(rs.getInt(18));
                pr.setC19fk_idproducto_marca(rs.getInt(19));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + pr.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + pr.toString(), titulo);
        }
    }

    public void actualizar_tabla_producto(Connection conn, JTable tbltabla,String filtro,int orden) {
        String sql_select = "SELECT p.idproducto as idp,p.codigo_barra as codbarra,p.nombre as producto,\n"
                + "pc.nombre as categoria,pu.nombre as unidad,pm.nombre as marca,\n"
                + "TRIM(to_char(p.precio_venta,'999G999G999')) as pventa,\n"
                + "TRIM(to_char(p.precio_compra,'999G999G999')) as pcompra,\n"
                + "p.stock_actual as stock,p.iva \n"
                + "FROM producto p,producto_categoria pc,producto_unidad pu,producto_marca pm\n"
                + "where p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca\n"+filtro
                + " order by "+orden+" asc;";

        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto(tbltabla);
    }

    public void ancho_tabla_producto(JTable tbltabla) {
        int Ancho[] = {5, 15, 25,10,10,10, 8, 8, 5, 5};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
    }
}
