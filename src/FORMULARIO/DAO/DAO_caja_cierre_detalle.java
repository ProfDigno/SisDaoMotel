package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.caja_cierre_detalle;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_caja_cierre_detalle {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "CAJA_CIERRE_DETALLE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "CAJA_CIERRE_DETALLE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO caja_cierre_detalle"
            + "(idcaja_cierre_detalle,fecha_creado,creado_por,cerrado_por,es_cerrado,"
            + "monto_apertura_caja,monto_cierre_caja,"
            + "monto_ocupa_minimo,monto_ocupa_adicional,monto_ocupa_consumo,"
            + "monto_ocupa_descuento,monto_ocupa_adelanto,"
            + "monto_gasto,monto_compra,monto_vale,monto_liquidacion,"
            + "estado,descripcion,"
            + "fk_idgasto,fk_idcompra,fk_idventa,fk_idusuario,fk_idrh_vale,fk_idrh_liquidacion,monto_solo_adelanto,"
            + "monto_interno,fk_idventa_interno,"
            + "monto_garantia,fk_idgarantia) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE caja_cierre_detalle SET creado_por=?,cerrado_por=?,es_cerrado=?,"
            + "monto_apertura_caja=?,monto_cierre_caja=?,"
            + "monto_ocupa_minimo=?,monto_ocupa_adicional=?,monto_ocupa_consumo=?,"
            + "monto_ocupa_descuento=?,monto_ocupa_adelanto=?,"
            + "monto_gasto=?,monto_compra=?,monto_vale=?,monto_liquidacion=?,"
            + "estado=?,descripcion=?,"
            + "fk_idgasto=?,fk_idcompra=?,fk_idventa=?,fk_idusuario=?,fk_idrh_vale=?,fk_idrh_liquidacion=?,monto_solo_adelanto=?,"
            + "monto_interno=?,fk_idventa_interno=?,"
            + "monto_garantia=?,fk_idgarantia=? "
            + "WHERE idcaja_cierre_detalle=?;";
    private String sql_select = "SELECT idcaja_cierre_detalle,fecha_creado,creado_por,cerrado_por,es_cerrado,"
            + "monto_apertura_caja,monto_cierre_caja,"
            + "monto_ocupa_minimo,monto_ocupa_adicional,monto_ocupa_consumo,"
            + "monto_ocupa_descuento,monto_ocupa_adelanto,"
            + "monto_gasto,monto_compra,monto_vale,monto_liquidacion,"
            + "estado,descripcion,"
            + "fk_idgasto,fk_idcompra,fk_idventa,fk_idusuario,fk_idrh_vale,fk_idrh_liquidacion "
            + "FROM caja_cierre_detalle order by 1 desc;";
    private String sql_cargar = "SELECT idcaja_cierre_detalle,fecha_creado,creado_por,cerrado_por,es_cerrado,"
            + "monto_apertura_caja,monto_cierre_caja,"
            + "monto_ocupa_minimo,monto_ocupa_adicional,monto_ocupa_consumo,"
            + "monto_ocupa_descuento,monto_ocupa_adelanto,"
            + "monto_gasto,monto_compra,monto_vale,monto_liquidacion,"
            + "estado,descripcion,"
            + "fk_idgasto,fk_idcompra,fk_idventa,fk_idusuario,fk_idrh_vale,fk_idrh_liquidacion,monto_solo_adelanto,"
            + "monto_interno,fk_idventa_interno,"
            + "monto_garantia,fk_idgarantia "
            + "FROM caja_cierre_detalle "
            + "WHERE idcaja_cierre_detalle=";

    public void insertar_caja_cierre_detalle(Connection conn, caja_cierre_detalle cacide) {
        cacide.setC1idcaja_cierre_detalle(eveconn.getInt_ultimoID_mas_uno(conn, cacide.getTb_caja_cierre_detalle(), cacide.getId_idcaja_cierre_detalle()));
        String titulo = "insertar_caja_cierre_detalle";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, cacide.getC1idcaja_cierre_detalle());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, cacide.getC3creado_por());
            pst.setString(4, cacide.getC4cerrado_por());
            pst.setBoolean(5, cacide.getC5es_cerrado());
            pst.setDouble(6, cacide.getC6monto_apertura_caja());
            pst.setDouble(7, cacide.getC7monto_cierre_caja());
            pst.setDouble(8, cacide.getC8monto_ocupa_minimo());
            pst.setDouble(9, cacide.getC9monto_ocupa_adicional());
            pst.setDouble(10, cacide.getC10monto_ocupa_consumo());
            pst.setDouble(11, cacide.getC11monto_ocupa_descuento());
            pst.setDouble(12, cacide.getC12monto_ocupa_adelanto());
            pst.setDouble(13, cacide.getC13monto_gasto());
            pst.setDouble(14, cacide.getC14monto_compra());
            pst.setDouble(15, cacide.getC15monto_vale());
            pst.setDouble(16, cacide.getC16monto_liquidacion());
            pst.setString(17, cacide.getC17estado());
            pst.setString(18, cacide.getC18descripcion());
            pst.setInt(19, cacide.getC19fk_idgasto());
            pst.setInt(20, cacide.getC20fk_idcompra());
            pst.setInt(21, cacide.getC21fk_idventa());
            pst.setInt(22, cacide.getC22fk_idusuario());
            pst.setInt(23, cacide.getC23fk_idrh_vale());
            pst.setInt(24, cacide.getC24fk_idrh_liquidacion());
            pst.setDouble(25, cacide.getC25monto_solo_adelanto());
            pst.setDouble(26, cacide.getC26monto_interno());
            pst.setInt(27, cacide.getC27fk_idventa_interno());
            pst.setDouble(28, cacide.getC28monto_garantia());
            pst.setInt(29, cacide.getC29fk_idgarantia());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + cacide.toString(), titulo);
//            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + cacide.toString(), titulo);
        }
    }

    public void update_caja_cierre_detalle(Connection conn, caja_cierre_detalle cacide) {
        String titulo = "update_caja_cierre_detalle";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
//            pst.setTimestamp(1, evefec.getTimestamp_fecha_cargado(cacide.getC2fecha_creado(),"cacide.getC2fecha_creado()"));
            pst.setString(1, cacide.getC3creado_por());
            pst.setString(2, cacide.getC4cerrado_por());
            pst.setBoolean(3, cacide.getC5es_cerrado());
            pst.setDouble(4, cacide.getC6monto_apertura_caja());
            pst.setDouble(5, cacide.getC7monto_cierre_caja());
            pst.setDouble(6, cacide.getC8monto_ocupa_minimo());
            pst.setDouble(7, cacide.getC9monto_ocupa_adicional());
            pst.setDouble(8, cacide.getC10monto_ocupa_consumo());
            pst.setDouble(9, cacide.getC11monto_ocupa_descuento());
            pst.setDouble(10, cacide.getC12monto_ocupa_adelanto());
            pst.setDouble(11, cacide.getC13monto_gasto());
            pst.setDouble(12, cacide.getC14monto_compra());
            pst.setDouble(13, cacide.getC15monto_vale());
            pst.setDouble(14, cacide.getC16monto_liquidacion());
            pst.setString(15, cacide.getC17estado());
            pst.setString(16, cacide.getC18descripcion());
            pst.setInt(17, cacide.getC19fk_idgasto());
            pst.setInt(18, cacide.getC20fk_idcompra());
            pst.setInt(19, cacide.getC21fk_idventa());
            pst.setInt(20, cacide.getC22fk_idusuario());
            pst.setInt(21, cacide.getC23fk_idrh_vale());
            pst.setInt(22, cacide.getC24fk_idrh_liquidacion());
            pst.setDouble(23, cacide.getC25monto_solo_adelanto());
            pst.setDouble(24, cacide.getC26monto_interno());
            pst.setInt(25, cacide.getC27fk_idventa_interno());
            pst.setDouble(26, cacide.getC28monto_garantia());
            pst.setInt(27, cacide.getC29fk_idgarantia());
            pst.setInt(28, cacide.getC1idcaja_cierre_detalle());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + cacide.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + cacide.toString(), titulo);
        }
    }

    public void cargar_caja_cierre_detalle(Connection conn, caja_cierre_detalle cacide, int idcaja_cierre_detalle) {
        String titulo = "Cargar_caja_cierre_detalle";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idcaja_cierre_detalle, titulo);
            if (rs.next()) {
                cacide.setC1idcaja_cierre_detalle(rs.getInt(1));
                cacide.setC2fecha_creado(rs.getString(2));
                cacide.setC3creado_por(rs.getString(3));
                cacide.setC4cerrado_por(rs.getString(4));
                cacide.setC5es_cerrado(rs.getBoolean(5));
                cacide.setC6monto_apertura_caja(rs.getDouble(6));
                cacide.setC7monto_cierre_caja(rs.getDouble(7));
                cacide.setC8monto_ocupa_minimo(rs.getDouble(8));
                cacide.setC9monto_ocupa_adicional(rs.getDouble(9));
                cacide.setC10monto_ocupa_consumo(rs.getDouble(10));
                cacide.setC11monto_ocupa_descuento(rs.getDouble(11));
                cacide.setC12monto_ocupa_adelanto(rs.getDouble(12));
                cacide.setC13monto_gasto(rs.getDouble(13));
                cacide.setC14monto_compra(rs.getDouble(14));
                cacide.setC15monto_vale(rs.getDouble(15));
                cacide.setC16monto_liquidacion(rs.getDouble(16));
                cacide.setC17estado(rs.getString(17));
                cacide.setC18descripcion(rs.getString(18));
                cacide.setC19fk_idgasto(rs.getInt(19));
                cacide.setC20fk_idcompra(rs.getInt(20));
                cacide.setC21fk_idventa(rs.getInt(21));
                cacide.setC22fk_idusuario(rs.getInt(22));
                cacide.setC23fk_idrh_vale(rs.getInt(23));
                cacide.setC24fk_idrh_liquidacion(rs.getInt(24));
                cacide.setC25monto_solo_adelanto(rs.getDouble(25));
                cacide.setC26monto_interno(rs.getDouble(26));
                cacide.setC27fk_idventa_interno(rs.getInt(27));
                cacide.setC28monto_garantia(rs.getDouble(28));
                cacide.setC29fk_idgarantia(rs.getInt(29));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + cacide.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + cacide.toString(), titulo);
        }
    }

    public void actualizar_tabla_caja_cierre_detalle_ABIERTO_VENTA(Connection conn, JTable tbltabla, int fk_idusuario) {
        String sql = "select cd.fk_idventa as idventa,\n"
                + "to_char(cd.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha, \n"
                + "cd.descripcion as descripcion,\n"
                + "to_char(cd.monto_solo_adelanto,'999G999G999') as adelanto,\n"
                + "to_char(cd.monto_ocupa_minimo,'999G999G999') as minimo,\n"
                + "to_char(cd.monto_ocupa_adicional,'999G999G999') as adicional,\n"
                + "to_char(cd.monto_ocupa_consumo,'999G999G999') as consumo,\n"
                + "to_char((0-(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)),'999G999G999') as descuento,\n"
                + "to_char(((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)),'999G999G999') as total,\n"
                + "cd.estado,cd.creado_por as usuario,cd.idcaja_cierre_detalle as idcaja \n"
                + "from caja_cierre_detalle cd\n"
                + "where cd.es_cerrado=false \n"
                + "and cd.fk_idventa>0 \n"
                + "and cd.fk_idusuario=" + fk_idusuario
                + " order by cd.idcaja_cierre_detalle desc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_caja_cierre_detalle_ABIERTO(tbltabla);
    }

    public void actualizar_tabla_caja_cierre_detalle_ABIERTO_GASTO(Connection conn, JTable tbltabla, int fk_idusuario) {
        String sql = "select cd.fk_idgasto as idgasto,\n"
                + "to_char(cd.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha, \n"
                + "cd.descripcion as descripcion,\n"
                + "to_char(cd.monto_gasto,'999G999G999') as monto_gasto,\n"
                + "cd.estado,cd.creado_por as usuario \n"
                + "from caja_cierre_detalle cd\n"
                + "where cd.es_cerrado=false \n"
                + "and cd.fk_idgasto>0 \n"
                + "and cd.fk_idusuario=" + fk_idusuario
                + " order by cd.idcaja_cierre_detalle desc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_caja_cierre_detalle_ABIERTO_EGRESO(tbltabla);
    }

    public void actualizar_tabla_caja_cierre_detalle_ABIERTO_COMPRA(Connection conn, JTable tbltabla, int fk_idusuario) {
        String sql = "select cd.fk_idcompra as idcompra,\n"
                + "to_char(cd.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha, \n"
                + "cd.descripcion as descripcion,\n"
                + "to_char(cd.monto_compra,'999G999G999') as monto_compra,\n"
                + "cd.estado,cd.creado_por as usuario \n"
                + "from caja_cierre_detalle cd\n"
                + "where cd.es_cerrado=false \n"
                + "and cd.fk_idcompra>0 \n"
                + "and cd.fk_idusuario=" + fk_idusuario
                + " order by cd.idcaja_cierre_detalle desc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_caja_cierre_detalle_ABIERTO_EGRESO(tbltabla);
    }

    public void actualizar_tabla_caja_cierre_detalle_ABIERTO_VENTA_INTERNO(Connection conn, JTable tbltabla, int fk_idusuario) {
        String sql = "select cd.fk_idventa_interno as idv_interno,\n"
                + "to_char(cd.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha, \n"
                + "cd.descripcion as descripcion,\n"
                + "to_char(cd.monto_interno,'999G999G999') as monto_interno,\n"
                + "cd.estado,cd.creado_por as usuario \n"
                + "from caja_cierre_detalle cd\n"
                + "where cd.es_cerrado=false \n"
                + "and cd.fk_idventa_interno>0 \n"
                + "and cd.fk_idusuario=" + fk_idusuario
                + " order by cd.idcaja_cierre_detalle desc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_caja_cierre_detalle_ABIERTO_EGRESO(tbltabla);
    }

    public void actualizar_tabla_caja_cierre_detalle_ABIERTO_GARANTIA(Connection conn, JTable tbltabla, int fk_idusuario) {
        String sql = "select cd.fk_idgarantia as idgarantia,\n"
                + "to_char(cd.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha, \n"
                + "cd.descripcion as descripcion,\n"
                + "to_char(cd.monto_garantia,'999G999G999') as monto_garantia,\n"
                + "cd.estado,cd.creado_por as usuario \n"
                + "from caja_cierre_detalle cd\n"
                + "where cd.es_cerrado=false \n"
                + "and cd.fk_idgarantia>0 \n"
                + "and cd.fk_idusuario=" + fk_idusuario
                + " order by cd.idcaja_cierre_detalle desc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_caja_cierre_detalle_ABIERTO_EGRESO(tbltabla);
    }

    public void ancho_tabla_caja_cierre_detalle_ABIERTO_EGRESO(JTable tbltabla) {
        int Ancho[] = {5, 12, 48, 10, 10, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 3);
    }

    public void ancho_tabla_caja_cierre_detalle_ABIERTO(JTable tbltabla) {
        int Ancho[] = {5, 9, 25, 6, 6, 6, 7, 7, 7, 7, 10, 5};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 3);
        evejt.alinear_derecha_columna(tbltabla, 4);
        evejt.alinear_derecha_columna(tbltabla, 5);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
        evejt.alinear_derecha_columna(tbltabla, 8);
    }

    public void cerrar_todo_caja_detalle(Connection conn, int fk_idusuario) {
        String sql = "update caja_cierre_detalle set es_cerrado=true "
                + "where es_cerrado=false "
                + "and fk_idusuario=" + fk_idusuario;
        eveconn.SQL_execute_libre(conn, sql);
    }

    public int getInt_idcaja_cierre_detalle_por_otro_id(Connection conn, String idcampo, int iddato) {
        String titulo = "getInt_idcaja_cierre_detalle_por_otro_id";
        String sql = "select idcaja_cierre_detalle "
                + "from caja_cierre_detalle "
                + "where " + idcampo + "=" + iddato;
        int idcaja_cierre_detalle_venta = 0;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                idcaja_cierre_detalle_venta = rs.getInt("idcaja_cierre_detalle");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return idcaja_cierre_detalle_venta;
    }

    public boolean getBoo_es_caja_cerrado_por_usu(Connection conn, int fk_idusuario) {
        String titulo = "getBoo_es_caja_cerrado_por_usu";
        String sql = "select count(*) as cant "
                + "from caja_cierre_detalle "
                + "where es_cerrado=false and fk_idusuario=" + fk_idusuario;
        boolean es_cerrado = false;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                int cant = rs.getInt("cant");
                if (cant == 0) {
                    es_cerrado = true;
                } else {
                    es_cerrado = false;
                }
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return es_cerrado;
    }

    public void update_caja_cierre_detalle_corregir(Connection conn) {
        String sql = "update caja_cierre_detalle set es_cerrado=true from caja_cierre_item\n"
                + "where idcaja_cierre_detalle=caja_cierre_item.fk_idcaja_cierre_detalle \n"
                + "and es_cerrado=false;";
        eveconn.SQL_execute_libre(conn, sql);
    }
}
