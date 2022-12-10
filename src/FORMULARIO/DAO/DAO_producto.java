package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import ESTADOS.EvenEstado;
import FORMULARIO.ENTIDAD.producto;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import Evento.Jtable.EvenRender;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_producto {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenEstado eveest = new EvenEstado();
    EvenFecha evefec = new EvenFecha();
    EvenRender render=new EvenRender();
    private String mensaje_insert = "PRODUCTO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO producto(idproducto,fecha_creado,creado_por,"
            + "codigo_barra,nombre,"
            + "controlar_stock,es_venta,es_compra,es_insumo,es_patrimonio,"
            + "precio_venta,precio_compra,stock_actual,stock_minimo,stock_maximo,"
            + "iva,fk_idproducto_categoria,fk_idproducto_unidad,fk_idproducto_marca,precio_interno) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE producto SET fecha_creado=?,creado_por=?,"
            + "codigo_barra=?,nombre=?,"
            + "controlar_stock=?,es_venta=?,es_compra=?,es_insumo=?,es_patrimonio=?,"
            + "precio_venta=?,precio_compra=?,stock_actual=?,stock_minimo=?,stock_maximo=?,"
            + "iva=?,fk_idproducto_categoria=?,fk_idproducto_unidad=?,fk_idproducto_marca=?,precio_interno=? "
            + "WHERE idproducto=?;";
    private String sql_cargar = "SELECT idproducto,fecha_creado,creado_por,codigo_barra,"
            + "nombre,controlar_stock,es_venta,es_compra,es_insumo,"
            + "es_patrimonio,precio_venta,precio_compra,stock_actual,"
            + "stock_minimo,stock_maximo,iva,"
            + "fk_idproducto_categoria,fk_idproducto_unidad,"
            + "fk_idproducto_marca,precio_interno,"
            + "TRIM(to_char(precio_venta,'999G999G999')) as precio_venta_mostrar, "
            + "TRIM(to_char(precio_compra,'999G999G999')) as precio_compra_mostrar "
            + "FROM producto WHERE idproducto=";
    private String sql_update_pcompra = "UPDATE producto SET precio_compra=? WHERE idproducto=?;";

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
            pst.setDouble(20, pr.getC20precio_interno());
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
            pst.setDouble(19, pr.getC20precio_interno());
            pst.setInt(20, pr.getC1idproducto());
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
                pr.setC20precio_interno(rs.getDouble(20));
                pr.setPrecio_venta_mostrar(rs.getString(21));
                pr.setPrecio_compra_mostrar(rs.getString(22));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + pr.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + pr.toString(), titulo);
        }
    }

    public void actualizar_tabla_producto(Connection conn, JTable tbltabla, String filtro, String orden, String filtro_item) {
        String sql_select = "SELECT p.idproducto as idp,p.codigo_barra as codbarra,"
                + "p.nombre as producto,p.iva,\n"
                + "pc.nombre as categoria,pu.nombre as unidad,pm.nombre as marca,\n"
                + "TRIM(to_char(p.precio_venta,'999G999G999')) as pventa,\n"
                + "TRIM(to_char(p.precio_compra,'999G999G999')) as pcompra,\n"
                + "p.stock_actual as stock, \n"
                + "coalesce((select sum(vi.cantidad) as cant from venta_item vi,venta v "
                + "where vi.fk_idventa=v.idventa "
                + "and vi.tipo_item='" + eveest.getEst_Cargado() + "' " + filtro_item
                + " and vi.fk_idproducto=p.idproducto),0) as cv, "
                + "coalesce((select sum(vi.cantidad) as cant from compra_item vi,compra v "
                + "where vi.fk_idcompra=v.idcompra "
                + "and vi.tipo_item='" + eveest.getEst_INGRESADO() + "' " + filtro_item
                + " and vi.fk_idproducto=p.idproducto),0) as cc "
                + "FROM producto p,producto_categoria pc,producto_unidad pu,producto_marca pm\n"
                + "where p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca\n" + filtro
                + " order by " + orden + ";";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto(tbltabla);
        render.rendertabla_stock_producto(tbltabla, 9);
    }

    public void ancho_tabla_producto(JTable tbltabla) {
        int Ancho[] = {5, 12, 24, 3, 10, 10, 10, 8, 8, 5, 4, 4};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 7);
        evejt.alinear_derecha_columna(tbltabla, 8);
        evejt.alinear_derecha_columna(tbltabla, 9);
    }

    public void imprimir_rep_inventario_volorizado(Connection conn, String filtro) {
        String sql = "SELECT p.idproducto as idpro,p.codigo_barra as codbarra,pc.nombre as categoria,pm.nombre as marca,\n"
                + "(pu.nombre||'-'||p.nombre) as producto,\n"
                + "p.precio_venta as pventa,\n"
                + "p.precio_compra  as pcompra,\n"
                + "p.stock_actual  as stock,\n"
                + "(p.precio_venta*p.stock_actual) as tt_venta,\n"
                + "(p.precio_compra*p.stock_actual) as tt_compra\n"
                + "FROM producto p,producto_unidad pu,producto_categoria pc, producto_marca pm\n"
                + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca \n" + filtro
                + " order by pc.nombre asc,pm.nombre asc;";
        String titulonota = "INVENTARIO VALORIZADO";
        String direccion = "src/REPORTE/PRODUCTO/repInventarioValorizado.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public void imprimir_rep_ganancia_producto(Connection conn, String filtro, String desc_filtro) {
        String sql = "select date(v.fecha_creado) as fecha,u.nombre as usuario,\n"
                + "pc.nombre as categoria,pm.nombre as marca,\n"
                + "vi.descripcion as descripcion,\n"
                + "sum(vi.cantidad) as cant,\n"
                + "sum(vi.cantidad*vi.precio_venta) as ttventa,\n"
                + "sum(vi.cantidad*vi.precio_compra) as ttcompra,\n"
                + "((sum(vi.cantidad*vi.precio_venta))-(sum(vi.cantidad*vi.precio_compra))) as ttsaldo,\n"
                + "('" + desc_filtro + "') as filtro "
                + "from venta v,venta_item vi,producto p, producto_categoria pc,producto_marca pm,usuario u  \n"
                + "where v.estado='TERMINADO'\n"
                + "and v.idventa=vi.fk_idventa \n"
                + "and vi.fk_idproducto=p.idproducto \n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria \n"
                + "and v.fk_idusuario=u.idusuario \n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca \n" + filtro
                + " group by 1,2,3,4,5\n"
                + "order by 1 desc,2 desc,3 desc,4 desc";
        String titulonota = "GANANCIA PRODUCTO";
        String direccion = "src/REPORTE/PRODUCTO/repGanaciaProducto.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public void update_producto_precio_compra(Connection conn, producto pr) {
        String titulo = "update_producto_precio_compra";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_pcompra);
            pst.setDouble(1, pr.getC12precio_compra());
            pst.setInt(2, pr.getC1idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_pcompra + "\n" + pr.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_pcompra + "\n" + pr.toString(), titulo);
        }
    }

    public boolean getBoolean_cargar_producto_por_codbarra(Connection conn, producto pr, String cod_barra) {
        String sql_cargar_codbarra = "SELECT idproducto,fecha_creado,creado_por,codigo_barra,"
                + "nombre,controlar_stock,es_venta,es_compra,es_insumo,"
                + "es_patrimonio,precio_venta,precio_compra,stock_actual,"
                + "stock_minimo,stock_maximo,iva,"
                + "fk_idproducto_categoria,fk_idproducto_unidad,"
                + "fk_idproducto_marca,precio_interno,"
                + "TRIM(to_char(precio_venta,'999G999G999')) as precio_venta_mostrar, "
                + "TRIM(to_char(precio_compra,'999G999G999')) as precio_compra_mostrar "
                + "FROM producto WHERE codigo_barra='" + cod_barra + "';";
        String titulo = "getBoolean_cargar_producto_por_codbarra";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar_codbarra, titulo);
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
                pr.setC20precio_interno(rs.getDouble(20));
                pr.setPrecio_venta_mostrar(rs.getString(21));
                pr.setPrecio_compra_mostrar(rs.getString(22));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + pr.toString(), titulo);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + pr.toString(), titulo);
            return false;
        }
    }

    public void imprimir_rep_inventario_simple(Connection conn, String filtro) {
        String sql = "select p.codigo_barra as cod_barra,pc.nombre as categoria,pm.nombre as marca,pu.nombre as unidad, \n"
                + "p.nombre as producto,p.stock_actual as stock,p.precio_venta as pventa\n"
                + "from producto p,producto_categoria pc,producto_marca pm,producto_unidad pu  \n"
                + "where p.fk_idproducto_categoria=pc.idproducto_categoria \n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca \n"
                + "and p.fk_idproducto_unidad=pu.idproducto_unidad \n"+filtro
                + "order by pc.nombre asc,pm.nombre desc,pu.nombre desc;";
        String titulonota = "INVENTARIO SIMPLE";
        String direccion = "src/REPORTE/PRODUCTO/repInventarioSimple.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
}
