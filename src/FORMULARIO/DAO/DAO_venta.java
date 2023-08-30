package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import ESTADOS.EvenEstado;
import FORMULARIO.ENTIDAD.venta;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import Evento.Jtable.EvenRender;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_venta {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    EvenEstado eveest = new EvenEstado();
    EvenRender everen = new EvenRender();
    private String mensaje_insert = "VENTA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "VENTA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO venta(idventa,fecha_creado,creado_por,monto_letra,estado,observacion,tipo_persona,motivo_anulacion,motivo_mudar_habitacion,monto_minimo,monto_adicional,cant_adicional,monto_consumo,monto_insumo,monto_descuento,monto_adelanto,fk_idhabitacion_recepcion,fk_idpersona,fk_idusuario) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE venta SET fecha_creado=?,creado_por=?,"
            + "monto_letra=?,estado=?,observacion=?,"
            + "tipo_persona=?,motivo_anulacion=?,motivo_mudar_habitacion=?,"
            + "monto_minimo=?,monto_adicional=?,"
            + "cant_adicional=?,"
            + "monto_consumo=?,monto_insumo=?,"
            + "monto_descuento=?,monto_adelanto=?,"
            + "fk_idhabitacion_recepcion=?,fk_idpersona=?,fk_idusuario=? "
            + "WHERE fk_idhabitacion_recepcion=?;";
    private String sql_update_obs = "UPDATE venta SET observacion=? WHERE fk_idhabitacion_recepcion=?;";
    private String sql_select = "select v.idventa as idv,\n"
            + "TRIM(to_char(hr.fec_ocupado_inicio ,'yyyy-MM-dd HH24:MI')) as fec_ini,\n"
            + "case when v.estado='OCUPADO' then 'XD' else TRIM(to_char(hr.fec_ocupado_fin,'yyyy-MM-dd HH24:MI')) end as fec_fin,\n"
            + "hr.nro_habitacion as hab,hd.tipo_habitacion as tipo,\n"
            + "TRIM(to_char((hr.fec_ocupado_fin-hr.fec_ocupado_inicio), 'HH24:MI:ss')) as tiempo,\n"
            + "case when hr.es_por_hora=true and hr.es_por_dormir=false then 'HORA'\n"
            + "     when hr.es_por_hora=false and hr.es_por_dormir=true then 'DORMIR'\n"
            + "     when hr.es_por_hora=true and hr.es_por_dormir=true then 'HS+DORMIR'\n"
            + "     else 'sin-tipo' end as ocu_por,\n"
            + "TRIM(to_char(v.monto_minimo,'999G999G999')) as minimo,\n"
            + "TRIM(to_char(v.monto_adicional,'999G999G999')) as adicional,\n"
            + "TRIM(to_char(v.monto_consumo,'999G999G999')) as consumo,\n"
            + "TRIM(to_char(v.monto_descuento,'999G999G999')) as descu,\n"
            + "TRIM(to_char(v.monto_adelanto,'999G999G999')) as adelanto,\n"
            + "TRIM(to_char(((v.monto_minimo+v.monto_adicional+v.monto_consumo)-(v.monto_descuento+v.monto_adelanto)),'999G999G999')) as total,\n"
            + "v.estado, \n"
            + "u.nombre as usuario,\n"
            + "((v.monto_minimo+v.monto_adicional+v.monto_consumo)-(v.monto_descuento+v.monto_adelanto)) as itotal,"
            + "v.fk_idhabitacion_recepcion,v.motivo_mudar_habitacion,v.motivo_anulacion \n"
            + "from venta v,habitacion_recepcion hr,usuario u,habitacion_dato hd  \n"
            + "where v.fk_idhabitacion_recepcion=hr.idhabitacion_recepcion\n"
            + "and hr.fk_idhabitacion_dato=hd.idhabitacion_dato \n"
            + "and v.fk_idusuario=u.idusuario \n"
            + " ";
    private String sql_cargar_idhr = "SELECT idventa,fecha_creado,creado_por,"
            + "monto_letra,estado,observacion,"
            + "tipo_persona,motivo_anulacion,motivo_mudar_habitacion,"
            + "monto_minimo,monto_adicional,"
            + "cant_adicional,"
            + "monto_consumo,monto_insumo,"
            + "monto_descuento,monto_adelanto,"
            + "fk_idhabitacion_recepcion,"
            + "fk_idpersona,fk_idusuario "
            + "FROM venta WHERE fk_idhabitacion_recepcion=";
    private String sql_cargar_idventa = "SELECT idventa,fecha_creado,creado_por,"
            + "monto_letra,estado,observacion,"
            + "tipo_persona,motivo_anulacion,motivo_mudar_habitacion,"
            + "monto_minimo,monto_adicional,"
            + "cant_adicional,"
            + "monto_consumo,monto_insumo,"
            + "monto_descuento,monto_adelanto,"
            + "fk_idhabitacion_recepcion,"
            + "fk_idpersona,fk_idusuario "
            + "FROM venta WHERE idventa=";

    public void insertar_venta(Connection conn, venta ve) {
        ve.setC1idventa(eveconn.getInt_ultimoID_mas_uno(conn, ve.getTb_venta(), ve.getId_idventa()));
        String titulo = "insertar_venta";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ve.getC1idventa());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ve.getC3creado_por());
            pst.setString(4, ve.getC4monto_letra());
            pst.setString(5, ve.getC5estado());
            pst.setString(6, ve.getC6observacion());
            pst.setString(7, ve.getC7tipo_persona());
            pst.setString(8, ve.getC8motivo_anulacion());
            pst.setString(9, ve.getC9motivo_mudar_habitacion());
            pst.setDouble(10, ve.getC10monto_minimo());
            pst.setDouble(11, ve.getC11monto_adicional());
            pst.setDouble(12, ve.getC12cant_adicional());
            pst.setDouble(13, ve.getC13monto_consumo());
            pst.setDouble(14, ve.getC14monto_insumo());
            pst.setDouble(15, ve.getC15monto_descuento());
            pst.setDouble(16, ve.getC16monto_adelanto());
            pst.setInt(17, ve.getC17fk_idhabitacion_recepcion());
            pst.setInt(18, ve.getC18fk_idpersona());
            pst.setInt(19, ve.getC19fk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ve.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ve.toString(), titulo);
        }
    }

    public void update_venta(Connection conn, venta ve) {
        String titulo = "update_venta";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_fecha_cargado(ve.getC2fecha_creado(), "ve.getC2fecha_creado()"));
            pst.setString(2, ve.getC3creado_por());
            pst.setString(3, ve.getC4monto_letra());
            pst.setString(4, ve.getC5estado());
            pst.setString(5, ve.getC6observacion());
            pst.setString(6, ve.getC7tipo_persona());
            pst.setString(7, ve.getC8motivo_anulacion());
            pst.setString(8, ve.getC9motivo_mudar_habitacion());
            pst.setDouble(9, ve.getC10monto_minimo());
            pst.setDouble(10, ve.getC11monto_adicional());
            pst.setDouble(11, ve.getC12cant_adicional());
            pst.setDouble(12, ve.getC13monto_consumo());
            pst.setDouble(13, ve.getC14monto_insumo());
            pst.setDouble(14, ve.getC15monto_descuento());
            pst.setDouble(15, ve.getC16monto_adelanto());
            pst.setInt(16, ve.getC17fk_idhabitacion_recepcion());
            pst.setInt(17, ve.getC18fk_idpersona());
            pst.setInt(18, ve.getC19fk_idusuario());
            pst.setInt(19, ve.getC17fk_idhabitacion_recepcion());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ve.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ve.toString(), titulo);
        }
    }

    public void update_venta_obs(Connection conn, venta ve) {
        String titulo = "update_venta_obs";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_obs);
            pst.setString(1, ve.getC6observacion());
            pst.setInt(2, ve.getC17fk_idhabitacion_recepcion());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_obs + "\n" + ve.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_obs + "\n" + ve.toString(), titulo);
        }
    }

    public void cargar_venta_idhabitacion_recepcion(Connection conn, venta ve, int idhabitacion_recepcion) {
        String titulo = "cargar_venta_idhabitacion_recepcion";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar_idhr + idhabitacion_recepcion, titulo);
            if (rs.next()) {
                ve.setC1idventa(rs.getInt(1));
                ve.setC2fecha_creado(rs.getString(2));
                ve.setC3creado_por(rs.getString(3));
                ve.setC4monto_letra(rs.getString(4));
                ve.setC5estado(rs.getString(5));
                ve.setC6observacion(rs.getString(6));
                ve.setC7tipo_persona(rs.getString(7));
                ve.setC8motivo_anulacion(rs.getString(8));
                ve.setC9motivo_mudar_habitacion(rs.getString(9));
                ve.setC10monto_minimo(rs.getDouble(10));
                ve.setC11monto_adicional(rs.getDouble(11));
                ve.setC12cant_adicional(rs.getDouble(12));
                ve.setC13monto_consumo(rs.getDouble(13));
                ve.setC14monto_insumo(rs.getDouble(14));
                ve.setC15monto_descuento(rs.getDouble(15));
                ve.setC16monto_adelanto(rs.getDouble(16));
                ve.setC17fk_idhabitacion_recepcion(rs.getInt(17));
                ve.setC18fk_idpersona(rs.getInt(18));
                ve.setC19fk_idusuario(rs.getInt(19));
                evemen.Imprimir_serial_sql(sql_cargar_idhr + "\n" + ve.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar_idhr + "\n" + ve.toString(), titulo);
        }
    }

    public void cargar_venta_idventa(Connection conn, venta ve, int idventa) {
        String titulo = "cargar_venta_idventa";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar_idventa + idventa, titulo);
            if (rs.next()) {
                ve.setC1idventa(rs.getInt(1));
                ve.setC2fecha_creado(rs.getString(2));
                ve.setC3creado_por(rs.getString(3));
                ve.setC4monto_letra(rs.getString(4));
                ve.setC5estado(rs.getString(5));
                ve.setC6observacion(rs.getString(6));
                ve.setC7tipo_persona(rs.getString(7));
                ve.setC8motivo_anulacion(rs.getString(8));
                ve.setC9motivo_mudar_habitacion(rs.getString(9));
                ve.setC10monto_minimo(rs.getDouble(10));
                ve.setC11monto_adicional(rs.getDouble(11));
                ve.setC12cant_adicional(rs.getDouble(12));
                ve.setC13monto_consumo(rs.getDouble(13));
                ve.setC14monto_insumo(rs.getDouble(14));
                ve.setC15monto_descuento(rs.getDouble(15));
                ve.setC16monto_adelanto(rs.getDouble(16));
                ve.setC17fk_idhabitacion_recepcion(rs.getInt(17));
                ve.setC18fk_idpersona(rs.getInt(18));
                ve.setC19fk_idusuario(rs.getInt(19));
                evemen.Imprimir_serial_sql(sql_cargar_idventa + "\n" + ve.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar_idventa + "\n" + ve.toString(), titulo);
        }
    }

    public void actualizar_tabla_venta(Connection conn, JTable tbltabla, String filtro, String orden) {
        eveconn.Select_cargar_jtable(conn, sql_select + filtro + orden, tbltabla);
        everen.rendertabla_estados_venta_habitacion(tbltabla, 13);
        ancho_tabla_venta(tbltabla);

    }

    public void ancho_tabla_venta(JTable tbltabla) {
        int Ancho[] = {7, 9, 9, 5, 8, 5, 5, 5, 5, 5, 5, 5, 6, 9, 14, 1, 1, 1, 1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_centro_columna(tbltabla, 1);
        evejt.alinear_centro_columna(tbltabla, 2);
        evejt.alinear_centro_columna(tbltabla, 3);
        evejt.alinear_centro_columna(tbltabla, 4);
        evejt.alinear_centro_columna(tbltabla, 5);
        evejt.alinear_centro_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
        evejt.alinear_derecha_columna(tbltabla, 8);
        evejt.alinear_derecha_columna(tbltabla, 9);
        evejt.alinear_derecha_columna(tbltabla, 10);
        evejt.alinear_derecha_columna(tbltabla, 11);
        evejt.alinear_derecha_columna(tbltabla, 12);
//        evejt.alinear_centro_columna(tbltabla, 13);
        evejt.ocultar_columna(tbltabla, 15);
        evejt.ocultar_columna(tbltabla, 16);
        evejt.ocultar_columna(tbltabla, 17);
        evejt.ocultar_columna(tbltabla, 18);
    }

    public void terminar_venta_en_caja(Connection conn, int fk_idcaja_cierre) {
        String sql = "update venta set estado='" + eveest.getEst_Terminado() + "' from caja_cierre_item ,caja_cierre_detalle \n"
                + "where caja_cierre_item.fk_idcaja_cierre_detalle=caja_cierre_detalle.idcaja_cierre_detalle \n"
                + "and caja_cierre_detalle.fk_idventa=venta.idventa \n"
                + "and venta.estado='" + eveest.getEst_Desocupado() + "'\n"
                + "and caja_cierre_item.fk_idcaja_cierre=" + fk_idcaja_cierre;
        eveconn.SQL_execute_libre(conn, sql);
    }

    public void actualizar_tabla_venta_desde_caja_cierre(Connection conn, JTable tbltabla, int fk_idcaja_cierre) {
        String sql = "select v.idventa as idv,\n"
                + "TRIM(to_char(hr.fec_ocupado_fin,'yyyy-MM-dd HH24:MI'))  as fec_fin,\n"
                + " hr.nro_habitacion as hab,hd.tipo_habitacion as tipo,\n"
                + " TRIM(to_char((hr.fec_ocupado_fin-hr.fec_ocupado_inicio), 'HH24:MI:ss')) as tiempo,\n"
                + " case when hr.es_por_hora=true then 'HORA'\n"
                + "      when hr.es_por_dormir=true then 'DORMIR'\n"
                + "      else 'sin-tipo' end as ocu_por,\n"
                + " TRIM(to_char(v.monto_minimo,'999G999G999')) as minimo,\n"
                + " TRIM(to_char(v.monto_adicional,'999G999G999')) as adicional,\n"
                + " TRIM(to_char(v.monto_consumo,'999G999G999')) as consumo,\n"
                + " TRIM(to_char(v.monto_descuento,'999G999G999')) as descu,\n"
                + " TRIM(to_char(v.monto_adelanto,'999G999G999')) as adelanto,\n"
                + " TRIM(to_char(((v.monto_minimo+v.monto_adicional+v.monto_consumo)-(v.monto_descuento+v.monto_adelanto)),'999G999G999')) as total,\n"
                + "(v.monto_minimo) as i_minimo, \n"
                + "(v.monto_adicional) as i_adicional, \n"
                + "(v.monto_descuento+v.monto_adelanto) as i_descuento \n"
                + " from venta v,habitacion_recepcion hr,habitacion_dato hd,caja_cierre_item cci,caja_cierre_detalle ccd  \n"
                + " where v.fk_idhabitacion_recepcion=hr.idhabitacion_recepcion\n"
                + " and hr.fk_idhabitacion_dato=hd.idhabitacion_dato \n"
                + " and ccd.idcaja_cierre_detalle=cci.fk_idcaja_cierre_detalle \n"
                + " and ccd.fk_idventa=v.idventa \n"
                + " and ccd.monto_ocupa_adelanto=0 \n"
                + " and cci.fk_idcaja_cierre=" + fk_idcaja_cierre
                + " order by v.idventa desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_venta_desde_caja_cierre(tbltabla);
    }

    public void ancho_tabla_venta_desde_caja_cierre(JTable tbltabla) {
        int Ancho[] = {5, 13, 4, 10, 5, 6, 7, 7, 7, 7, 7, 7, 1, 1, 1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_centro_columna(tbltabla, 2);
        evejt.alinear_centro_columna(tbltabla, 3);
        evejt.alinear_centro_columna(tbltabla, 5);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
        evejt.alinear_derecha_columna(tbltabla, 8);
        evejt.alinear_derecha_columna(tbltabla, 9);
        evejt.alinear_derecha_columna(tbltabla, 10);
        evejt.alinear_derecha_columna(tbltabla, 11);
        evejt.ocultar_columna(tbltabla, 12);
        evejt.ocultar_columna(tbltabla, 13);
        evejt.ocultar_columna(tbltabla, 14);
    }

    public void actualizar_tabla_venta_item_desde_caja_cierre(Connection conn, JTable tbltabla, int fk_idcaja_cierre) {
        String sql = "select vi.fk_idproducto as idp ,vi.descripcion , sum(vi.cantidad) as cant ,\n"
                + "to_char(avg(vi.precio_venta),'999G999') as pventa, \n"
                + "to_char(sum(vi.cantidad*vi.precio_venta),'999G999G999') as subtotal,\n"
                + "sum(vi.cantidad*vi.precio_venta) as Isubtotal\n"
                + " from venta v,caja_cierre_item cci,caja_cierre_detalle ccd,venta_item vi  \n"
                + " where  ccd.idcaja_cierre_detalle=cci.fk_idcaja_cierre_detalle \n"
                + " and ccd.fk_idventa=v.idventa \n"
                + " and v.idventa=vi.fk_idventa \n"
                + " and v.estado='TERMINADO'\n"
                + " and ccd.cerrado_por='DESOCUPADO'\n"
                + " and v.monto_consumo>0\n"
                + " and cci.fk_idcaja_cierre=" + fk_idcaja_cierre
                + " group by 1,2\n"
                + " order by 3 desc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_venta_item_desde_caja_cierre(tbltabla);
    }

    public void ancho_tabla_venta_item_desde_caja_cierre(JTable tbltabla) {
        int Ancho[] = {5, 50, 10, 15, 20, 1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 2);
        evejt.alinear_derecha_columna(tbltabla, 3);
        evejt.alinear_derecha_columna(tbltabla, 4);
        evejt.ocultar_columna(tbltabla, 5);

    }

    public void actualizar_tabla_filtro_venta(Connection conn, JTable tbltabla, String filtro) {
        String sql = "select v.idventa as idv,\n"
                + "TRIM(to_char(hr.fec_ocupado_inicio ,'yyyy-MM-dd HH24:MI')) as fec_ini,\n"
                + "case when v.estado='OCUPADO' then 'XD' else TRIM(to_char(hr.fec_ocupado_fin,'yyyy-MM-dd HH24:MI')) end as fec_fin,\n"
                + "hr.nro_habitacion as hab,hd.tipo_habitacion as tipo,\n"
                + "TRIM(to_char((hr.fec_ocupado_fin-hr.fec_ocupado_inicio), 'HH24:MI:ss')) as tiempo,\n"
                + "case when hr.es_por_hora=true and hr.es_por_dormir=false then 'HORA'\n"
                + "     when hr.es_por_hora=false and hr.es_por_dormir=true then 'DORMIR'\n"
                + "     when hr.es_por_hora=true and hr.es_por_dormir=true then 'HS+DORMIR'\n"
                + "     else 'sin-tipo' end as ocu_por,\n"
                + "v.estado,u.nombre as usuario,\n"
                + "TRIM(to_char(v.monto_minimo,'999G999G999')) as minimo,\n"
                + "TRIM(to_char(v.monto_adicional,'999G999G999')) as adicional,\n"
                + "TRIM(to_char(v.monto_consumo,'999G999G999')) as consumo,\n"
                + "TRIM(to_char(((v.monto_minimo+v.monto_adicional+v.monto_consumo)),'999G999G999')) as total,\n"
                + "v.monto_minimo as iminimo,v.monto_adicional as iadicional,v.monto_consumo as iconsumo,\n"
                + "((v.monto_minimo+v.monto_adicional+v.monto_consumo)) as itotal\n"
                + "from venta v,habitacion_recepcion hr,usuario u,habitacion_dato hd  \n"
                + "where v.fk_idhabitacion_recepcion=hr.idhabitacion_recepcion\n"
                + "and hr.fk_idhabitacion_dato=hd.idhabitacion_dato \n"
                + "and v.fk_idusuario=u.idusuario\n"
                + "and v.estado='TERMINADO' \n" + filtro
                + " order by v.idventa desc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_filtro_venta(tbltabla);
    }

    public void ancho_tabla_filtro_venta(JTable tbltabla) {
        int Ancho[] = {5, 10, 10, 5, 6, 6, 7, 7, 11, 7, 7, 7, 7, 1, 1, 1, 1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 9);
        evejt.alinear_derecha_columna(tbltabla, 10);
        evejt.alinear_derecha_columna(tbltabla, 11);
        evejt.alinear_derecha_columna(tbltabla, 12);
        evejt.ocultar_columna(tbltabla, 13);
        evejt.ocultar_columna(tbltabla, 14);
        evejt.ocultar_columna(tbltabla, 15);
        evejt.ocultar_columna(tbltabla, 16);

    }

    public void imprimir_filtro_venta(Connection conn, String filtro,String nom_filtro) {
        String sql = "select v.idventa as idv,\n"
                + "TRIM(to_char(hr.fec_ocupado_inicio ,'yyyy-MM-dd HH24:MI')) as fec_ini,\n"
                + "case when v.estado='OCUPADO' then 'XD' else TRIM(to_char(hr.fec_ocupado_fin,'yyyy-MM-dd HH24:MI')) end as fec_fin,\n"
                + "hr.nro_habitacion as hab,hd.tipo_habitacion as tipo,\n"
                + "TRIM(to_char((hr.fec_ocupado_fin-hr.fec_ocupado_inicio), 'HH24:MI:ss')) as tiempo,\n"
                + "case when hr.es_por_hora=true and hr.es_por_dormir=false then 'HORA'\n"
                + "     when hr.es_por_hora=false and hr.es_por_dormir=true then 'DORMIR'\n"
                + "     when hr.es_por_hora=true and hr.es_por_dormir=true then 'HS+DORMIR'\n"
                + "     else 'sin-tipo' end as ocu_por,\n"
                + "u.nombre as usuario,\n"
                + "v.monto_minimo as minimo,v.monto_adicional as adicional,v.monto_consumo as consumo,\n"
                + "((v.monto_minimo+v.monto_adicional+v.monto_consumo)) as total,('"+nom_filtro+"') as filtro\n"
                + "from venta v,habitacion_recepcion hr,usuario u,habitacion_dato hd  \n"
                + "where v.fk_idhabitacion_recepcion=hr.idhabitacion_recepcion\n"
                + "and hr.fk_idhabitacion_dato=hd.idhabitacion_dato \n"
                + "and v.fk_idusuario=u.idusuario\n"
                + "and v.estado='TERMINADO' \n"+filtro
                + "\n"
                + "order by v.idventa desc;";
        String titulonota = "FILTRO OCUPACION";
        String direccion = "src/REPORTE/VENTA/repFiltroOcupacion.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
}
