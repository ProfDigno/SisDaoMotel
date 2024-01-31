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
            pst.setTimestamp(5, evefec.getTimestamp_sistema());
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

    public void exportar_excel_caja_fecha_usu_N2(Connection conn) {
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

    public void exportar_excel_estado_habitacion_N1(Connection conn) {
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

    public void exportar_excel_lista_producto_N2(Connection conn) {
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

    public void exportar_excel_caja_resumen_N2(Connection conn) {
        int band_Height = 20;
        String sucursal = jsfrm.getApp_nom_report();
        String rutatemp = "APPSHEET/EXCEL/caja_resumen" + sucursal + ".xlsx";
        String sql = "select date(cd.fecha_creado) as fecha,\n"
                + "(case when date(cd.fecha_creado)=current_date then (select c1.creado_por from caja_cierre_detalle c1 where c1.idcaja_cierre_detalle=(select max(idcaja_cierre_detalle) from caja_cierre_detalle)) else 'TODOS' end) as usuario,"
                + "sum((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)) as ocupa_total,\n"
                + "sum(case \n"
                + "when  cd.es_cerrado=false then ((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto))\n"
                + "else 0 end) as ocupa_actual,\n"
                + "sum(cd.monto_gasto) as gasto_total,\n"
                + "sum(case when  cd.es_cerrado=false then (cd.monto_gasto)\n"
                + "else 0 end) as gasto_actual,\n"
                + "sum(case \n"
                + "when  cd.es_cerrado=false then ((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto+cd.monto_gasto))\n"
                + "else 0 end) as saldo_actual,\n"
                + "sum((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto+cd.monto_gasto)) as saldo_total,\n"
                + "sum(case when  (cd.es_cerrado=false and cd.cerrado_por='DESOCUPADO') then 1\n"
                + "else 0 end) as entrada_actual,\n"
                + "sum(case when  ( cd.cerrado_por='DESOCUPADO') then 1\n"
                + "else 0 end) as entrada_total\n"
                + "from caja_cierre_detalle cd\n"
                + "where date_part('year',cd.fecha_creado)=date_part('year',current_date)\n"
                + "group by 1,2\n"
                + "order by 1 desc;";
        String direccion = "src/REPORTE/APPSHEET/repCajaResumen.jrxml";
        String titulo = "RESUMEN CAJA";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }

    public void exportar_excel_habitacion_estados_N1(Connection conn) {
        int band_Height = 20;
        String sucursal = jsfrm.getApp_nom_report();
        String rutatemp = "APPSHEET/EXCEL/habitacion_estado" + sucursal + ".xlsx";
        String sql = "select\n"
                + "	idhabitacion_dato,nro_habitacion,tipo_habitacion,\n"
                + "	case\n"
                + "		\n"
                + "		when estado = 'LIBRE'\n"
                + "		and puerta_limpieza = false\n"
                + "		and puerta_cliente = false then 'MANTENIMIENTO'\n"
                + "		else estado\n"
                + "	end as estado_gral,\n"
                + "	case\n"
                + "		when estado = 'OCUPADO' then to_char((current_timestamp-fec_ocupado_inicio), 'HH24:MI:ss')\n"
                + "		when estado = 'SUCIO' then to_char((current_timestamp-fec_ocupado_fin), 'HH24:MI:ss')\n"
                + "		when estado = 'LIMPIANDO' then to_char((current_timestamp-fec_ocupado_fin), 'HH24:MI:ss')\n"
                + "		else '.'\n"
                + "	end as tiempo,\n"
                + "\n"
                + "	case\n"
                + "		when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true\n"
                + "		and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))<(minuto_minimo * 60))  \n"
                + "         then (monto_por_hora_minimo + monto_consumision-(monto_descuento+monto_adelanto))\n"
                + "		when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true\n"
                + "		and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))>(minuto_minimo * 60))\n"
                + "		and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))<(((minuto_minimo)* 60)+(minuto_adicional * 60)))  \n"
                + "         then (monto_por_hora_minimo + monto_por_hora_adicional + monto_consumision-(monto_descuento+monto_adelanto))\n"
                + "		when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true\n"
                + "		and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))>((minuto_minimo * 60)+(minuto_adicional * 60)))  \n"
                + "         then (monto_por_hora_minimo + monto_por_hora_adicional + monto_consumision-(monto_descuento+monto_adelanto) + \n"
                + "              (cast(to_char((current_timestamp)-(fec_ocupado_inicio + (minuto_minimo || ' minutes')::interval), 'HH24') as integer)* monto_por_hora_adicional))\n"
                + "                when es_por_dormir = true and es_por_hora = false\n"
                + "		and estado = 'OCUPADO'\n"
                + "		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio))\n"
                + "		and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))\n"
                + "		and (current_timestamp>(date(fec_ocupado_inicio) + time '00:00:01'))\n"
                + "		and (current_timestamp<((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) \n"
                + "          then monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto)\n"
                + "		when es_por_dormir = true and es_por_hora = false\n"
                + "		and estado = 'OCUPADO'\n"
                + "		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))\n"
                + "		and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))\n"
                + "		and (current_timestamp>(fec_ocupado_inicio))\n"
                + "		and (current_timestamp<((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) \n"
                + "          then monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto)\n"
                + "		when es_por_dormir = true and es_por_hora = false\n"
                + "		and estado = 'OCUPADO'\n"
                + "		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio))\n"
                + "		and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))\n"
                + "		and (current_timestamp>((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) \n"
                + "          then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+ 1)+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional))\n"
                + "		when es_por_dormir = true and es_por_hora = false\n"
                + "		and estado = 'OCUPADO'\n"
                + "		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))\n"
                + "		and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))\n"
                + "		and (current_timestamp>((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) \n"
                + "          then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio))+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional))\n"
                + "		when es_por_dormir = true and es_por_hora = true\n"
                + "		and estado = 'OCUPADO'\n"
                + "		and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))\n"
                + "		and (current_timestamp>(date(fec_ocupado_inicio) + time '00:00:01'))\n"
                + "		and (current_timestamp<((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) \n"
                + "          then monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto)\n"
                + "		when es_por_dormir = true and es_por_hora = true\n"
                + "		and estado = 'OCUPADO'\n"
                + "		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))\n"
                + "		and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))\n"
                + "		and (current_timestamp>(fec_ocupado_inicio))\n"
                + "		and (current_timestamp<((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) \n"
                + "          then monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto)\n"
                + "		when es_por_dormir = true and es_por_hora = true\n"
                + "		and estado = 'OCUPADO'\n"
                + "		and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))\n"
                + "		and (current_timestamp>((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) \n"
                + "          then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+ 1)+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional))\n"
                + "		when es_por_dormir = true and es_por_hora = true\n"
                + "		and estado = 'OCUPADO'\n"
                + "		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))\n"
                + "		and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))\n"
                + "		and (current_timestamp>((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) \n"
                + "          then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio))+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional))\n"
                + "		else 0\n"
                + "	end as tarifa_gral,\n"
                + "	case\n"
                + "	    when estado = 'LIBRE' and puerta_limpieza = false and puerta_cliente = false then 'franco_db_hab_estado_Images/35NORMAL.nombre_imagen.053841.png'\n"
                + "            when estado = 'OCUPADO' and es_por_hora = true and es_por_dormir = false then 'franco_db_hab_estado_Images/31NORMAL.nombre_imagen.052958.png'\n"
                + "            when estado = 'OCUPADO' and es_por_hora = false and es_por_dormir = true then 'franco_db_hab_estado_Images/32NORMAL.nombre_imagen.053601.png'\n"
                + "            when estado = 'OCUPADO' and es_por_hora = true and es_por_dormir = true then 'franco_db_hab_estado_Images/32NORMAL.nombre_imagen.053601.png'\n"
                + "            when estado = 'LIMPIANDO' then 'franco_db_hab_estado_Images/33NORMAL.nombre_imagen.053652.png'\n"
                + "	    when estado = 'SUCIO' then 'franco_db_hab_estado_Images/34NORMAL.nombre_imagen.053800.png'\n"
                + "            when estado = 'LIBRE' then 'franco_db_hab_estado_Images/30NORMAL.nombre_imagen.050601.png'\n"
                + "	    else 'SIN-IMAGEN'\n"
                + "	end as nombre_imagen\n"
                + " from\n"
                + "	habitacion_recepcion_temp \n"
                + "where activo=true \n"
                + "order by orden asc;";
        String direccion = "src/REPORTE/APPSHEET/repHabEstado.jrxml";
        String titulo = "HABITACION ESTADOS";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }

    public void exportar_excel_habitacion_estados_resumen_N1(Connection conn) {
        int band_Height = 20;
        String sucursal = jsfrm.getApp_nom_report();
        String rutatemp = "APPSHEET/EXCEL/habitacion_estado_resumen" + sucursal + ".xlsx";
        String sql = "select\n"
                + "	case\n"
                + "		when estado = 'LIBRE'\n"
                + "		and puerta_limpieza = false\n"
                + "		and puerta_cliente = false then 'MANTENIMIENTO'\n"
                + "		else estado\n"
                + "	end as estado_gral,\n"
                + "	sum(case\n"
                + "		when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true\n"
                + "		and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))<(minuto_minimo * 60))  \n"
                + "         then (monto_por_hora_minimo + monto_consumision-(monto_descuento+monto_adelanto))\n"
                + "		when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true\n"
                + "		and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))>(minuto_minimo * 60))\n"
                + "		and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))<(((minuto_minimo)* 60)+(minuto_adicional * 60)))  \n"
                + "         then (monto_por_hora_minimo + monto_por_hora_adicional + monto_consumision-(monto_descuento+monto_adelanto))\n"
                + "		when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true\n"
                + "		and ((extract(epoch from(current_timestamp - fec_ocupado_inicio)))>((minuto_minimo * 60)+(minuto_adicional * 60)))  \n"
                + "         then (monto_por_hora_minimo + monto_por_hora_adicional + monto_consumision-(monto_descuento+monto_adelanto) + \n"
                + "              (cast(to_char((current_timestamp)-(fec_ocupado_inicio + (minuto_minimo || ' minutes')::interval), 'HH24') as integer)* monto_por_hora_adicional))\n"
                + "                when es_por_dormir = true and es_por_hora = false\n"
                + "		and estado = 'OCUPADO'\n"
                + "		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio))\n"
                + "		and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))\n"
                + "		and (current_timestamp>(date(fec_ocupado_inicio) + time '00:00:01'))\n"
                + "		and (current_timestamp<((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) \n"
                + "          then monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto)\n"
                + "		when es_por_dormir = true and es_por_hora = false\n"
                + "		and estado = 'OCUPADO'\n"
                + "		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))\n"
                + "		and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))\n"
                + "		and (current_timestamp>(fec_ocupado_inicio))\n"
                + "		and (current_timestamp<((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) \n"
                + "          then monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto)\n"
                + "		when es_por_dormir = true and es_por_hora = false\n"
                + "		and estado = 'OCUPADO'\n"
                + "		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio))\n"
                + "		and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))\n"
                + "		and (current_timestamp>((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) \n"
                + "          then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+ 1)+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional))\n"
                + "		when es_por_dormir = true and es_por_hora = false\n"
                + "		and estado = 'OCUPADO'\n"
                + "		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))\n"
                + "		and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))\n"
                + "		and (current_timestamp>((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) \n"
                + "          then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio))+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional))\n"
                + "		when es_por_dormir = true and es_por_hora = true\n"
                + "		and estado = 'OCUPADO'\n"
                + "		and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))\n"
                + "		and (current_timestamp>(date(fec_ocupado_inicio) + time '00:00:01'))\n"
                + "		and (current_timestamp<((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) \n"
                + "          then monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto)\n"
                + "		when es_por_dormir = true and es_por_hora = true\n"
                + "		and estado = 'OCUPADO'\n"
                + "		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))\n"
                + "		and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))\n"
                + "		and (current_timestamp>(fec_ocupado_inicio))\n"
                + "		and (current_timestamp<((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) \n"
                + "          then monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto)\n"
                + "		when es_por_dormir = true and es_por_hora = true\n"
                + "		and estado = 'OCUPADO'\n"
                + "		and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))\n"
                + "		and (current_timestamp>((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final)) \n"
                + "          then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+ 1)+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional))\n"
                + "		when es_por_dormir = true and es_por_hora = true\n"
                + "		and estado = 'OCUPADO'\n"
                + "		and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))\n"
                + "		and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))\n"
                + "		and (current_timestamp>((date(fec_ocupado_inicio)) + hs_dormir_salida_final)) \n"
                + "          then (monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) +(cast((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio))+ hs_dormir_salida_final)))))/ 3600) as integer)* monto_por_dormir_adicional))\n"
                + "		else 0\n"
                + "	end) as tarifa_gral,\n"
                + "	case\n"
                + "	    when estado = 'LIBRE' and puerta_limpieza = false and puerta_cliente = false then 'franco_db_hab_estado_Images/35NORMAL.nombre_imagen.053841.png'\n"
                + "            when estado = 'OCUPADO' and es_por_hora = true and es_por_dormir = false then 'franco_db_hab_estado_Images/31NORMAL.nombre_imagen.052958.png'\n"
                + "            when estado = 'OCUPADO' and es_por_hora = false and es_por_dormir = true then 'franco_db_hab_estado_Images/32NORMAL.nombre_imagen.053601.png'\n"
                + "            when estado = 'OCUPADO' and es_por_hora = true and es_por_dormir = true then 'franco_db_hab_estado_Images/32NORMAL.nombre_imagen.053601.png'\n"
                + "            when estado = 'LIMPIANDO' then 'franco_db_hab_estado_Images/33NORMAL.nombre_imagen.053652.png'\n"
                + "	    when estado = 'SUCIO' then 'franco_db_hab_estado_Images/34NORMAL.nombre_imagen.053800.png'\n"
                + "            when estado = 'LIBRE' then 'franco_db_hab_estado_Images/30NORMAL.nombre_imagen.050601.png'\n"
                + "	    else 'SIN-IMAGEN'\n"
                + "	end as nombre_imagen,\n"
                + "	count(*) as cantidad\n"
                + " from\n"
                + "	habitacion_recepcion_temp \n"
                + "where activo=true \n"
                + "group by 1,3;";
        String direccion = "src/REPORTE/APPSHEET/repHabEstadoResumen.jrxml";
        String titulo = "HABITACION ESTADOS resumen";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }

    public void exportar_excel_caja_cierre_ingreso_lista_N2(Connection conn) {
        int band_Height = 20;
        String sucursal = jsfrm.getApp_nom_report();
        String rutatemp = "APPSHEET/EXCEL/caja_cierre_ingreso_lista" + sucursal + ".xlsx";
        String sql = "select cc.idcaja_cierre as idc, \n"
                + "to_char(cc.fecha_creado,'yyyy-MM-dd HH24:MI') as fec_cierre,\n"
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
                + "u.nombre as usuario, \n"
                + "case \n"
                + "	when cast(cc.fecha_inicio as time) > time '05:00:00' and cast(cc.fecha_inicio as time) < time '07:00:00' then 'manana'\n"
                + "	when cast(cc.fecha_inicio as time) > time '13:00:00' and cast(cc.fecha_inicio as time) < time '15:00:00' then 'tarde'\n"
                + "	when cast(cc.fecha_inicio as time) > time '17:00:00' and cast(cc.fecha_inicio as time) < time '19:00:00' then 'tarde'\n"
                + "	when cast(cc.fecha_inicio as time) > time '21:00:00' and cast(cc.fecha_inicio as time) < time '23:00:00' then 'noche'\n"
                + "else to_char(cc.fecha_inicio ,'HH24:MI:ss') end as turno \n"
                + "from caja_cierre cc,usuario u \n"
                + "where cc.fk_idusuario=u.idusuario \n"
                + " and date_part('year',cc.fecha_creado)=date_part('year',current_date)\n"
                + "order by cc.idcaja_cierre desc;";
        String direccion = "src/REPORTE/APPSHEET/repCajaCierreLista.jrxml";
        String titulo = "CAJA CIERRE INGRESO";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }

    public void exportar_excel_caja_cierre_gral_N2(Connection conn) {
        int band_Height = 20;
        String sucursal = jsfrm.getApp_nom_report();
        String rutatemp = "APPSHEET/EXCEL/caja_cierre_gral" + sucursal + ".xlsx";
        String sql = "select \n"
                + "to_char(cc.fecha_fin,'yyyy-MM-dd') as fec_cierre\n"
                + "from caja_cierre cc\n"
                + "where  date_part('year',cc.fecha_creado)=date_part('year',current_date)\n"
                + " group by 1 order by 1 desc;";
        String direccion = "src/REPORTE/APPSHEET/repCajaCierreGral.jrxml";
        String titulo = "CAJA CIERRE GRAL";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }

    public void update_cantidad_habitacion_todos_N2(Connection conn) {
        String sql = "update habitacion_dato set cant_hab=\n"
                + "(select count(*) as cant from habitacion_recepcion \n"
                + "where EXTRACT(YEAR FROM fecha_creado) = EXTRACT(YEAR FROM CURRENT_DATE)\n"
                + "and fk_idhabitacion_dato=habitacion_dato.idhabitacion_dato)";
        eveconn.SQL_execute_libre(conn, sql);
    }

    public void exportar_excel_uso_habitacion_N2(Connection conn) {
        int band_Height = 20;
        String sucursal = jsfrm.getApp_nom_report();
        String rutatemp = "APPSHEET/EXCEL/habitacion_mas_usado" + sucursal + ".xlsx";
        String sql = "SELECT \n"
                + "case \n"
                + "when EXTRACT(MONTH FROM h.fecha_creado)=1 then '1-ENERO'\n"
                + "when EXTRACT(MONTH FROM h.fecha_creado)=2 then '2-FEBRERO'\n"
                + "when EXTRACT(MONTH FROM h.fecha_creado)=3 then '3-MARZO'\n"
                + "when EXTRACT(MONTH FROM h.fecha_creado)=4 then '4-ABRIL'\n"
                + "when EXTRACT(MONTH FROM h.fecha_creado)=5 then '5-MAYO'\n"
                + "when EXTRACT(MONTH FROM h.fecha_creado)=6 then '6-JUNIO'\n"
                + "when EXTRACT(MONTH FROM h.fecha_creado)=7 then '7-JULIO'\n"
                + "when EXTRACT(MONTH FROM h.fecha_creado)=8 then '8-AGOSTO'\n"
                + "when EXTRACT(MONTH FROM h.fecha_creado)=9 then '9-SEPTIEMBRE'\n"
                + "when EXTRACT(MONTH FROM h.fecha_creado)=10 then '10-OCTUBRE'\n"
                + "when EXTRACT(MONTH FROM h.fecha_creado)=11 then '11-NOVIEMBRE'\n"
                + "when EXTRACT(MONTH FROM h.fecha_creado)=12 then '12-DICIEMBRE'\n"
                + "else 'error' end as mes,\n"
                + "to_char(h.fec_ocupado_inicio,'yyyy-MM-dd') as fec_dia,\n"
                + "h.nro_habitacion as nro_hab,('HAB:'||h.nro_habitacion) as habitacion,\n"
                + "to_char(h.fec_ocupado_inicio,'HH24:MI:ss') as inicio,\n"
                + "to_char(h.fec_ocupado_fin,'HH24:MI:ss') as fin,\n"
                + "to_char((h.fec_ocupado_fin-h.fec_ocupado_inicio),'HH24:MI:ss') as tiempo,\n"
                + "(v.monto_minimo+v.monto_adicional+v.monto_consumo) as total,\n"
                + "hd.cant_hab as cant_hab \n"
                + "FROM\n"
                + "  habitacion_recepcion h,venta v,habitacion_dato hd \n"
                + "  where h.idhabitacion_recepcion=v.fk_idhabitacion_recepcion \n"
                + " and h.fk_idhabitacion_dato=hd.idhabitacion_dato\n"
                + "  and v.estado='TERMINADO';";
        String direccion = "src/REPORTE/APPSHEET/repHabMasUsado.jrxml";
        String titulo = "USO HABITACION";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }

    public void exportar_excel_gastos_N2(Connection conn) {
        int band_Height = 13;
        String sucursal = jsfrm.getApp_nom_report();
        String rutatemp = "APPSHEET/EXCEL/caja_gastos" + sucursal + ".xlsx";
        String sql = "select g.idgasto as idg,to_char(g.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha,\n"
                + "gt.nombre as tipo,g.descripcion as descripcion,\n"
                + "g.monto_gasto as monto,g.estado as estado,g.creado_por as usuario\n"
                + "from gasto g,gasto_tipo gt\n"
                + "where g.fk_idgasto_tipo=gt.idgasto_tipo \n"
                + "and g.estado!='ANULADO'\n"
                + " order by g.idgasto desc;";
        String direccion = "src/REPORTE/APPSHEET/repGastos.jrxml";
        String titulo = "CAJA GASTOS";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }

    public void exportar_excel_producto_simple_N3(Connection conn) {
        int band_Height = 11;
        String sucursal = jsfrm.getApp_nom_report();
        String rutatemp = "APPSHEET/EXCEL/producto_simple" + sucursal + ".xlsx";
        String sql = "select p.idproducto as idproducto,p.codigo_barra as codbarra,\n"
                + "pc.nombre as categoria,p.nombre as producto,\n"
                + "p.precio_venta as pventa,\n"
                + "p.precio_interno as pinterno,\n"
                + "p.precio_compra as pcompra,\n"
                + "p.stock_actual as stock\n"
                + " FROM producto p,producto_categoria pc\n"
                + "where p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + " and p.es_venta=true and p.es_compra=true \n"
                + "order by p.idproducto desc;";
        String direccion = "src/REPORTE/APPSHEET/repProductoSimple.jrxml";
        String titulo = "PRODUCTO SIMPLE";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }

    public void exportar_excel_venta_item_N3(Connection conn) {
        int band_Height = 11;
        String sucursal = jsfrm.getApp_nom_report();
        String rutatemp = "APPSHEET/EXCEL/venta_item" + sucursal + ".xlsx";
        String sql = "select idventa_item as idventa_item,to_char(fecha_creado,'yyyy-MM-dd') as fecha,\n"
                + "cantidad as cantidad,precio_venta as pventa,fk_idproducto as fk_idproducto\n"
                + "from venta_item\n"
                + "where tipo_item='CARGADO'\n"
                + "order by idventa_item asc";
        String direccion = "src/REPORTE/APPSHEET/repVenta_Item.jrxml";
        String titulo = "VENTA ITEM";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }

    public void exportar_excel_patrimonio_carga_N3(Connection conn) {
        int band_Height = 15;
        String sucursal = jsfrm.getApp_nom_report();
        String rutatemp = "APPSHEET/EXCEL/patrimonio_carga" + sucursal + ".xlsx";
        String sql = "SELECT pp.idpatrimonio_producto as idpp,\n"
                + "to_char(pci.fecha_creado,'yyyy-MM-dd') as fecha,\n"
                + "pci.descripcion,ca.nombre as categoria,\n"
                + "pu.nombre as ubicacion,\n"
                + "pci.referencia,pci.cantidad as cant,\n"
                + "pci.precio_compra as precio,\n"
                + "(pci.cantidad*pci.precio_compra) as subtotal\n"
                + "FROM patrimonio_carga_item pci,patrimonio_producto pp,\n"
                + "patrimonio_ubicacion pu,patrimonio_carga pc,patrimonio_categoria ca\n"
                + "where  pci.fk_idpatrimonio_producto=pp.idpatrimonio_producto\n"
                + "and pp.fk_idpatrimonio_ubicacion=pu.idpatrimonio_ubicacion\n"
                + "and pci.fk_idpatrimonio_carga=pc.idpatrimonio_carga\n"
                + "and pp.fk_idpatrimonio_categoria=ca.idpatrimonio_categoria\n"
                + "and pc.estado='CARGADO'\n"
                //                + "and EXTRACT(YEAR FROM pci.fecha_creado) = EXTRACT(YEAR FROM CURRENT_DATE)\n"
                + "order by pci.idpatrimonio_carga_item desc;";
        String direccion = "src/REPORTE/APPSHEET/repPatrimonio_Carga.jrxml";
        String titulo = "PATRIMONIO CARGA";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }

    public void exportar_excel_patrimonio_baja_N3(Connection conn) {
        int band_Height = 15;
        String sucursal = jsfrm.getApp_nom_report();
        String rutatemp = "APPSHEET/EXCEL/patrimonio_baja" + sucursal + ".xlsx";
        String sql = "SELECT pp.idpatrimonio_producto as idpp,\n"
                + "to_char(pci.fecha_creado,'yyyy-MM-dd HH24:MI:ss') as fecha,\n"
                + "pci.descripcion as descripcion,pbm.nombre as motivo,\n"
                + "pu.nombre as ubicacion,\n"
                + "pci.referencia as referencia,pci.cantidad as cant,\n"
                + "pci.precio_compra as precio,\n"
                + "(pci.cantidad*pci.precio_compra) as subtotal\n"
                + "FROM patrimonio_baja_item pci,patrimonio_producto pp,patrimonio_ubicacion pu,patrimonio_baja pc,patrimonio_baja_motivo pbm\n"
                + "where  pci.fk_idpatrimonio_producto=pp.idpatrimonio_producto\n"
                + "and pp.fk_idpatrimonio_ubicacion=pu.idpatrimonio_ubicacion\n"
                + "and pci.fk_idpatrimonio_baja=pc.idpatrimonio_baja\n"
                + "and pci.fk_idpatrimonio_baja_motivo=pbm.idpatrimonio_baja_motivo\n"
                + "and pc.estado='DE_BAJA'  \n"
                + "and EXTRACT(YEAR FROM pci.fecha_creado) = EXTRACT(YEAR FROM CURRENT_DATE)\n"
                + " order by pci.idpatrimonio_baja_item desc;";
        String direccion = "src/REPORTE/APPSHEET/repPatrimonio_Baja.jrxml";
        String titulo = "PATRIMONIO BAJA";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }

    public void exportar_excel_deposito_banco_N3(Connection conn) {
        int band_Height = 15;
        String sucursal = jsfrm.getApp_nom_report();
        String rutatemp = "APPSHEET/EXCEL/deposito_banco" + sucursal + ".xlsx";
        String sql = "select tb.idtransaccion_banco as idtb,\n"
                + "to_char(tb.fecha_transaccion,'yyyy-MM-dd') as fecha,\n"
                + "(b.nombre||'-'||db.nro_cuenta) as banco,\n"
                + "tb.nro_transaccion as referencia,tb.concepto as concepto,\n"
                + "tb.observacion as observacion,\n"
                + "tb.monto_guarani as guarani,\n"
                + "tb.monto_dolar as dolar\n"
                + "from transaccion_banco tb,dato_banco db,banco b\n"
                + "where tb.fk_iddato_banco=db.iddato_banco \n"
                + " and db.fk_idbanco=b.idbanco\n"
                + "and tb.estado='EMITIDO' "
                + "and EXTRACT(YEAR FROM tb.fecha_transaccion) = EXTRACT(YEAR FROM CURRENT_DATE)\n"
                + " order by tb.fecha_transaccion desc;";
        String direccion = "src/REPORTE/APPSHEET/repDepositoBanco.jrxml";
        String titulo = "DEPOSITO BANCO";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }

    public void exportar_excel_liquidacion_vale_N3(Connection conn) {
        int band_Height = 15;
        String sucursal = jsfrm.getApp_nom_report();
        String rutatemp = "APPSHEET/EXCEL/liquidacion_vale" + sucursal + ".xlsx";
        String sql = "select l.idrh_liquidacion as idliq,\n"
                + "to_char(l.fecha_desde,'yyyy-MM-dd') as fec_desde,\n"
                + "to_char(l.fecha_hasta,'yyyy-MM-dd') as fec_hasta, \n"
                + "p.nombre as persona,\n"
                + "l.salario_base as salario,\n"
                + "l.monto_vale as vale,\n"
                + "l.monto_descuento as descuento,\n"
                + "l.monto_liquidacion as liquidacion,\n"
                + "l.estado as estado\n"
                + "from rh_liquidacion l,persona p\n"
                + "where l.fk_idpersona=p.idpersona\n"
                + "order by l.estado asc,l.fecha_hasta desc;";
        String direccion = "src/REPORTE/APPSHEET/repLiquidacionVale.jrxml";
        String titulo = "LIQUIDACION VALE";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }
}
