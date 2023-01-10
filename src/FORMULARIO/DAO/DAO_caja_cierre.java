package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import Config_JSON.json_array_formulario;
import FORMULARIO.ENTIDAD.caja_cierre;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import Evento.Jtable.EvenRender;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_caja_cierre {

    private EvenConexion eveconn = new EvenConexion();
    private EvenJtable evejt = new EvenJtable();
    private EvenJasperReport rep = new EvenJasperReport();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private EvenFecha evefec = new EvenFecha();
    private EvenRender eren = new EvenRender();
    private json_array_formulario jsfrm = new json_array_formulario();
    private String mensaje_insert = "CAJA_CIERRE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "CAJA_CIERRE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO caja_cierre(idcaja_cierre,fecha_creado,creado_por,fecha_inicio,fecha_fin,estado,fk_idusuario) VALUES (?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE caja_cierre SET fecha_creado=?,creado_por=?,fecha_inicio=?,fecha_fin=?,estado=?,fk_idusuario=? WHERE idcaja_cierre=?;";
    private String sql_select = "SELECT idcaja_cierre,fecha_creado,creado_por,fecha_inicio,fecha_fin,estado,fk_idusuario FROM caja_cierre order by 1 desc;";
    private String sql_cargar = "SELECT idcaja_cierre,fecha_creado,creado_por,fecha_inicio,fecha_fin,estado,fk_idusuario FROM caja_cierre WHERE idcaja_cierre=";

    public void insertar_caja_cierre(Connection conn, caja_cierre caci) {
        caci.setC1idcaja_cierre(eveconn.getInt_ultimoID_mas_uno(conn, caci.getTb_caja_cierre(), caci.getId_idcaja_cierre()));
        String titulo = "insertar_caja_cierre";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, caci.getC1idcaja_cierre());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, caci.getC3creado_por());
            pst.setTimestamp(4, evefec.getTimestamp_fecha_cargado(caci.getC4fecha_inicio(), "caci.getC4fecha_inicio()"));
            pst.setTimestamp(5, evefec.getTimestamp_fecha_cargado(caci.getC5fecha_fin(), "caci.getC5fecha_fin()"));
            pst.setString(6, caci.getC6estado());
            pst.setInt(7, caci.getC7fk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + caci.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + caci.toString(), titulo);
        }
    }

    public void update_caja_cierre(Connection conn, caja_cierre caci) {
        String titulo = "update_caja_cierre";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, caci.getC3creado_por());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setTimestamp(4, evefec.getTimestamp_sistema());
            pst.setString(5, caci.getC6estado());
            pst.setInt(6, caci.getC7fk_idusuario());
            pst.setInt(7, caci.getC1idcaja_cierre());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + caci.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + caci.toString(), titulo);
        }
    }

    public void cargar_caja_cierre(Connection conn, caja_cierre caci, int idcaja_cierre) {
        String titulo = "Cargar_caja_cierre";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idcaja_cierre, titulo);
            if (rs.next()) {
                caci.setC1idcaja_cierre(rs.getInt(1));
                caci.setC2fecha_creado(rs.getString(2));
                caci.setC3creado_por(rs.getString(3));
                caci.setC4fecha_inicio(rs.getString(4));
                caci.setC5fecha_fin(rs.getString(5));
                caci.setC6estado(rs.getString(6));
                caci.setC7fk_idusuario(rs.getInt(7));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + caci.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + caci.toString(), titulo);
        }
    }

    public void actualizar_tabla_caja_cierre(Connection conn, JTable tbltabla, String filtro) {
        String sql = "select cc.idcaja_cierre as idc, \n"
                + "to_char(cc.fecha_creado,'yyyy-MM-dd HH24:MI') as fec_cierre,\n"
                + "to_char(cc.fecha_inicio ,'yyyy-MM-dd HH24:MI') as fec_inicio,\n"
                + "to_char(cc.fecha_fin ,'yyyy-MM-dd HH24:MI') as fec_fin,\n"
                + "to_char((select \n"
                + "sum((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)) as m_ingreso\n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci \n"
                + "where cci.fk_idcaja_cierre_detalle=cd.idcaja_cierre_detalle \n"
                + "and cd.es_cerrado=true \n"
                + "and cci.fk_idcaja_cierre=cc.idcaja_cierre),'999G999G999') as ingreso,\n"
                + "to_char((select \n"
                + "sum(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion) as m_egreso\n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci \n"
                + "where cci.fk_idcaja_cierre_detalle=cd.idcaja_cierre_detalle \n"
                + "and cd.es_cerrado=true \n"
                + "and cci.fk_idcaja_cierre=cc.idcaja_cierre),'999G999G999') as egreso,\n"
                + "to_char((select \n"
                + "sum(((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto))-\n"
                + "(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion)) as m_saldo\n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci \n"
                + "where cci.fk_idcaja_cierre_detalle=cd.idcaja_cierre_detalle \n"
                + "and cd.es_cerrado=true \n"
                + "and cci.fk_idcaja_cierre=cc.idcaja_cierre),'999G999G999') as saldo,\n"
                + "cc.estado,u.nombre as usuario, \n"
                + "case \n"
                + "	when cast(cc.fecha_inicio as time) > time '05:00:00' and cast(cc.fecha_inicio as time) < time '07:00:00' then 'manana'\n"
                + "	when cast(cc.fecha_inicio as time) > time '13:00:00' and cast(cc.fecha_inicio as time) < time '15:00:00' then 'tarde'\n"
                + "	when cast(cc.fecha_inicio as time) > time '17:00:00' and cast(cc.fecha_inicio as time) < time '19:00:00' then 'tarde'\n"
                + "	when cast(cc.fecha_inicio as time) > time '21:00:00' and cast(cc.fecha_inicio as time) < time '23:00:00' then 'noche'\n"
                + "else to_char(cc.fecha_inicio ,'HH24:MI:ss') end as turno "
                + "from caja_cierre cc,usuario u \n"
                + "where cc.fk_idusuario=u.idusuario \n" + filtro
                + " order by cc.idcaja_cierre desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_caja_cierre(tbltabla);
        eren.rendertabla_turno_caja(tbltabla, 9);
    }

    public void ancho_tabla_caja_cierre(JTable tbltabla) {
        int Ancho[] = {5, 11, 13, 13, 9, 9, 9, 8, 15, 8};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 4);
        evejt.alinear_derecha_columna(tbltabla, 5);
        evejt.alinear_derecha_columna(tbltabla, 6);
    }

    public void imprimir_caja_cierre_jasper(Connection conn, int fk_idcaja_cierre) {
        String sql = "select cd.idcaja_cierre_detalle as idc,\n"
                + "to_char(cd.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha,\n"
                + "cd.descripcion as descripcion,\n"
                + "cd.monto_solo_adelanto as m_adelanto,\n"
                + "cd.monto_ocupa_minimo as m_minimo,\n"
                + "cd.monto_ocupa_adicional as m_adicional,\n"
                + "cd.monto_ocupa_consumo as m_consumo,\n"
                + "(0-(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)) as m_descuento,\n"
                + "((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)) as m_ingreso,\n"
                + "cd.monto_gasto as m_gasto,\n"
                + "cd.monto_compra as m_compra,\n"
                + "cd.monto_vale as m_vale,\n"
                + "cd.monto_liquidacion as m_liquida,\n"
                + "(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion) as m_egreso,\n"
                + "(((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto))-\n"
                + "(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion)) as m_saldo,\n"
                + "to_char(cca.fecha_inicio,'yyyy-MM-dd HH24:MI') as fec_inicio, \n"
                + "to_char(cca.fecha_fin,'yyyy-MM-dd HH24:MI') as fec_fin,\n"
                + "cca.creado_por as creado_por,cci.fk_idcaja_cierre as fk_idcaja_cierre \n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci,caja_cierre cca\n"
                + "where  cd.idcaja_cierre_detalle=cci.fk_idcaja_cierre_detalle\n"
                + "and cci.fk_idcaja_cierre=cca.idcaja_cierre \n"
                + "and cci.fk_idcaja_cierre=" + fk_idcaja_cierre
                + " order by cd.idcaja_cierre_detalle desc;";
        String titulonota = "CIERRE DE CAJA";
        String direccion = "src/REPORTE/OCUPACION/repCaja_Cierre.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public void imprimir_caja_cierre_jasper_resumen(Connection conn, int fk_idcaja_cierre) {
        String sql = "select cd.idcaja_cierre_detalle as idc,\n"
                + "to_char(cd.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha,\n"
                + "cd.descripcion as descripcion,\n"
                + "((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)) as ing_ocupado,\n"
                + "(cd.monto_ocupa_consumo+cd.monto_interno) as ing_consumo,\n"
                + "((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)) as ing_total,\n"
                + "(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion) as m_egreso,\n"
                + "cd.monto_gasto as m_gasto,\n"
                + "cd.monto_compra as m_compra,\n"
                + "cd.monto_vale as m_vale,\n"
                + "cd.monto_liquidacion as m_liquida,\n"
                + "cd.monto_apertura_caja as apertura_caja,\n"
                + "(((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto))-\n"
                + "(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion)) as m_saldo,\n"
                + "to_char(cca.fecha_inicio,'yyyy-MM-dd HH24:MI') as fec_inicio, \n"
                + "to_char(cca.fecha_fin,'yyyy-MM-dd HH24:MI') as fec_fin,\n"
                + "cca.creado_por as creado_por,cci.fk_idcaja_cierre as fk_idcaja_cierre \n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci,caja_cierre cca\n"
                + "where  cd.idcaja_cierre_detalle=cci.fk_idcaja_cierre_detalle\n"
                + "and cci.fk_idcaja_cierre=cca.idcaja_cierre \n"
                + "and cci.fk_idcaja_cierre=" + fk_idcaja_cierre
                + " order by cd.idcaja_cierre_detalle desc;";
        String titulonota = "CIERRE DE CAJA RESUMEN";
        String direccion = "src/REPORTE/CAJA/repCajaCierreResumen.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public void imprimir_caja_cierre_jasper_todo(Connection conn) {
        String sql = "select cc.idcaja_cierre as idc, \n"
                + "to_char(cc.fecha_creado,'yyyy-MM-dd HH24:MI') as fec_cierre,\n"
                + "to_char(cc.fecha_inicio ,'yyyy-MM-dd HH24:MI') as fec_inicio,\n"
                + "to_char(cc.fecha_fin ,'yyyy-MM-dd HH24:MI') as fec_fin,\n"
                + "to_char((select \n"
                + "sum((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)) as m_ingreso\n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci \n"
                + "where cci.fk_idcaja_cierre_detalle=cd.idcaja_cierre_detalle \n"
                + "and cd.es_cerrado=true \n"
                + "and cci.fk_idcaja_cierre=cc.idcaja_cierre),'999G999G999') as ingreso,\n"
                + "to_char((select \n"
                + "sum(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion) as m_egreso\n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci \n"
                + "where cci.fk_idcaja_cierre_detalle=cd.idcaja_cierre_detalle \n"
                + "and cd.es_cerrado=true \n"
                + "and cci.fk_idcaja_cierre=cc.idcaja_cierre),'999G999G999') as egreso,\n"
                + "to_char((select \n"
                + "sum(((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto))-\n"
                + "(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion)) as m_saldo\n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci \n"
                + "where cci.fk_idcaja_cierre_detalle=cd.idcaja_cierre_detalle \n"
                + "and cd.es_cerrado=true \n"
                + "and cci.fk_idcaja_cierre=cc.idcaja_cierre),'999G999G999') as saldo,\n"
                + "cc.estado,u.nombre as usuario \n"
                + "from caja_cierre cc,usuario u \n"
                + "where cc.fk_idusuario=u.idusuario \n"
                + "order by cc.idcaja_cierre desc;";
        String titulonota = "CIERRE DE CAJA";
        String direccion = "src/REPORTE/CAJA/repCajaCierreTodo.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public void exportar_excel_monto_usuario_todo_ano(Connection conn) {
        int band_Height = 20;
        String rutatemp = "APPSHEET/EXCEL/caja_cerrado_ano.xlsx";
        String sql = "select cc.idcaja_cierre as idc, \n"
                + "to_char(cc.fecha_inicio ,'yyyy-MM-dd HH24:MI') as fec_inicio,\n"
                + "to_char(cc.fecha_fin ,'yyyy-MM-dd HH24:MI') as fec_fin,\n"
                + "to_char(cc.fecha_fin ,'yyyy-MM-dd') as fecha_fin,\n"
                + "(select \n"
                + "sum((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)) as m_ingreso\n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci \n"
                + "where cci.fk_idcaja_cierre_detalle=cd.idcaja_cierre_detalle \n"
                + "and cd.es_cerrado=true \n"
                + "and cci.fk_idcaja_cierre=cc.idcaja_cierre) as ingreso,\n"
                + "(select \n"
                + "sum(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion) as m_egreso\n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci \n"
                + "where cci.fk_idcaja_cierre_detalle=cd.idcaja_cierre_detalle \n"
                + "and cd.es_cerrado=true \n"
                + "and cci.fk_idcaja_cierre=cc.idcaja_cierre) as egreso,\n"
                + "(select \n"
                + "sum(((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto))-\n"
                + "(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion)) as m_saldo\n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci \n"
                + "where cci.fk_idcaja_cierre_detalle=cd.idcaja_cierre_detalle \n"
                + "and cd.es_cerrado=true \n"
                + "and cci.fk_idcaja_cierre=cc.idcaja_cierre) as saldo,\n"
                + "cc.creado_por  as usuario \n"
                + "from caja_cierre cc\n"
                + "where   date_part('year',cc.fecha_fin)=date_part('year',current_date)\n"
                + " order by cc.idcaja_cierre desc;";
        String direccion = "src/REPORTE/APPSHEET/repCajaCerrado.jrxml";
        String titulo = "SUMA INGRESO USUARIO TODO ANO";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }

    public void exportar_excel_caja_fecha_usu(Connection conn) {
        int band_Height = 20;
        String sucursal = jsfrm.getApp_nom_report();
        String rutatemp = "APPSHEET/EXCEL/caja_fec_usu" + sucursal + ".xlsx";
        String sql = "select cd1.idcaja_cierre_detalle as id,date(cd.fecha_creado) as fecha,\n"
                + "to_char(cd1.fecha_creado,'HH24:MI:ss') as hs_inicio, \n"
                + "case \n"
                + "when cast(cd1.fecha_creado as time) > time '05:00:00' and cast(cd1.fecha_creado as time) < time '07:00:00' then (u.usuario||'-(M)')\n"
                + "when cast(cd1.fecha_creado as time) > time '13:00:00' and cast(cd1.fecha_creado as time) < time '19:00:00' then (u.usuario||'-(T)')\n"
                + "when cast(cd1.fecha_creado as time) > time '21:00:00' and cast(cd1.fecha_creado as time) < time '23:00:00' then (u.usuario||'-(N)')\n"
                + "else (u.usuario||'-('||to_char(cd1.fecha_creado,'HH24:MI')||')') end as usu_turno,\n"
                + "case\n"
                + "when extract(dow from date(cd1.fecha_creado)) = 0 then 'DOMINGO' \n"
                + "when extract(dow from date(cd1.fecha_creado)) = 1 then 'LUNES' \n"
                + "when extract(dow from date(cd1.fecha_creado)) = 2 then 'MARTES' \n"
                + "when extract(dow from date(cd1.fecha_creado)) = 3 then 'MIERCOLES' \n"
                + "when extract(dow from date(cd1.fecha_creado)) = 4 then 'JUEVES' \n"
                + "when extract(dow from date(cd1.fecha_creado)) = 5 then 'VIERNES' \n"
                + "when extract(dow from date(cd1.fecha_creado)) = 6 then 'SABADO' \n"
                + "else 'otro' end as dia,\n"
                + "sum((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+\n"
                + "cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)- \n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)) as ingreso, \n"
                + "sum(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion) as m_egreso, \n"
                + "sum(((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+\n"
                + "cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)- \n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto))- \n"
                + "(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion)) as m_saldo\n"
                + "from caja_cierre_detalle cd,caja_cierre_detalle cd1,usuario u \n"
                + "where date(cd.fecha_creado)=date(cd1.fecha_creado) \n"
                + "and date_part('year',cd.fecha_creado)=date_part('year',current_date)\n"
                + "and cd1.fk_idusuario=cd.fk_idusuario \n"
                + "and cd.fk_idusuario=u.idusuario\n"
                + "and cd1.cerrado_por='APERTURA'  \n"
                + "group by 1,2,3,4,5\n"
                + "order by 2 desc,3 asc;";
        String direccion = "src/REPORTE/APPSHEET/repCajaFechaUsu.jrxml";
        String titulo = "CAJA FECHA USUARIO";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }

    public void exportar_excel_estado_habitacion(Connection conn) {
        int band_Height = 20;
        String sucursal = jsfrm.getApp_nom_report();
        String rutatemp = "APPSHEET/EXCEL/estado_habitacion" + sucursal + ".xlsx";
        String sql = "select count(*) as cant,ht.estado as estado\n"
                + "from habitacion_recepcion_temp ht\n"
                + "where ht.activo=true\n"
                + "group by 2 order by 1 desc;";
        String direccion = "src/REPORTE/APPSHEET/repEstaHabi.jrxml";
        String titulo = "ESTADO HABITACION";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }

    public void exportar_excel_lista_producto(Connection conn) {
        int band_Height = 20;
        String sucursal = jsfrm.getApp_nom_report();
        String rutatemp = "APPSHEET/EXCEL/lista_producto" + sucursal + ".xlsx";
        String sql = "select p.codigo_barra as cod_barra,pc.nombre as categoria,pm.nombre as marca,pu.nombre as unidad, \n"
                + "p.nombre as producto,p.stock_actual as stock_actual,p.precio_venta as pventa\n"
                + "from producto p,producto_categoria pc,producto_marca pm,producto_unidad pu  \n"
                + "where p.fk_idproducto_categoria=pc.idproducto_categoria \n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca \n"
                + "and p.fk_idproducto_unidad=pu.idproducto_unidad \n"
                + "and p.es_venta=true\n"
                + "order by pc.nombre asc,p.nombre desc;";
        String direccion = "src/REPORTE/APPSHEET/repListaProducto.jrxml";
        String titulo = "LISTA PRODUCTO";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }
}
