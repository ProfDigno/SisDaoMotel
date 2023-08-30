package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.habitacion_costo;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_habitacion_costo {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "HABITACION_COSTO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "HABITACION_COSTO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO habitacion_costo(idhabitacion_costo,fecha_creado,creado_por,"
            + "activo,nombre,nivel_lujo,monto_por_hora_minimo,monto_por_hora_adicional,monto_por_dormir_minimo,monto_por_dormir_adicional,"
            + "minuto_minimo,minuto_adicional,minuto_cancelar,"
            + "hs_dormir_ingreso_inicio,hs_dormir_ingreso_final,hs_dormir_salida_final,minuto_tolerancia"
            + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE habitacion_costo SET fecha_creado=?,creado_por=?,"
            + "activo=?,nombre=?,nivel_lujo=?,monto_por_hora_minimo=?,monto_por_hora_adicional=?,monto_por_dormir_minimo=?,monto_por_dormir_adicional=?,"
            + "minuto_minimo=?,minuto_adicional=?,minuto_cancelar=?,"
            + "hs_dormir_ingreso_inicio=?,hs_dormir_ingreso_final=?,hs_dormir_salida_final=?,minuto_tolerancia=? WHERE idhabitacion_costo=?;";
    private String sql_select = "SELECT idhabitacion_costo as idhc,nombre,nivel_lujo as tipo,activo "
            + "FROM habitacion_costo order by nombre desc;";
    private String sql_cargar = "SELECT idhabitacion_costo,fecha_creado,creado_por,"
            + "activo,nombre,nivel_lujo,"
            + "monto_por_hora_minimo,monto_por_hora_adicional,monto_por_dormir_minimo,monto_por_dormir_adicional,"
            + "minuto_minimo,minuto_adicional,minuto_cancelar,"
            + "hs_dormir_ingreso_inicio,hs_dormir_ingreso_final,hs_dormir_salida_final,minuto_tolerancia "
            + "FROM habitacion_costo WHERE idhabitacion_costo=";

    public void insertar_habitacion_costo(Connection conn, habitacion_costo haco) {
        haco.setC1idhabitacion_costo(eveconn.getInt_ultimoID_mas_uno(conn, haco.getTb_habitacion_costo(), haco.getId_idhabitacion_costo()));
        String titulo = "insertar_habitacion_costo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, haco.getC1idhabitacion_costo());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, haco.getC3creado_por());
            pst.setBoolean(4, haco.getC4activo());
            pst.setString(5, haco.getC5nombre());
            pst.setString(6, haco.getC6nivel_lujo());
            pst.setDouble(7, haco.getC7monto_por_hora_minimo());
            pst.setDouble(8, haco.getC8monto_por_hora_adicional());
            pst.setDouble(9, haco.getC9monto_por_dormir_minimo());
            pst.setDouble(10, haco.getC10monto_por_dormir_adicional());
            pst.setInt(11, haco.getC11minuto_minimo());
            pst.setInt(12, haco.getC12minuto_adicional());
            pst.setInt(13, haco.getC13minuto_cancelar());
            pst.setTime(14, evefec.getTime_sistema_cargado(haco.getC14hs_dormir_ingreso_inicio()));
            pst.setTime(15, evefec.getTime_sistema_cargado(haco.getC15hs_dormir_ingreso_final()));
            pst.setTime(16, evefec.getTime_sistema_cargado(haco.getC16hs_dormir_salida_final()));
            pst.setInt(17, haco.getC17minuto_tolerancia());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + haco.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + haco.toString(), titulo);
        }
    }

    public void update_habitacion_costo(Connection conn, habitacion_costo haco) {
        String titulo = "update_habitacion_costo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, haco.getC3creado_por());
            pst.setBoolean(3, haco.getC4activo());
            pst.setString(4, haco.getC5nombre());
            pst.setString(5, haco.getC6nivel_lujo());
            pst.setDouble(6, haco.getC7monto_por_hora_minimo());
            pst.setDouble(7, haco.getC8monto_por_hora_adicional());
            pst.setDouble(8, haco.getC9monto_por_dormir_minimo());
            pst.setDouble(9, haco.getC10monto_por_dormir_adicional());
            pst.setInt(10, haco.getC11minuto_minimo());
            pst.setInt(11, haco.getC12minuto_adicional());
            pst.setInt(12, haco.getC13minuto_cancelar());
            pst.setTime(13, evefec.getTime_sistema_cargado(haco.getC14hs_dormir_ingreso_inicio()));
            pst.setTime(14, evefec.getTime_sistema_cargado(haco.getC15hs_dormir_ingreso_final()));
            pst.setTime(15, evefec.getTime_sistema_cargado(haco.getC16hs_dormir_salida_final()));
            pst.setInt(16, haco.getC17minuto_tolerancia());
            pst.setInt(17, haco.getC1idhabitacion_costo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + haco.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + haco.toString(), titulo);
        }
    }

    public void cargar_habitacion_costo(Connection conn, habitacion_costo haco, int idhabitacion_costo) {
        String titulo = "Cargar_habitacion_costo";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idhabitacion_costo, titulo);
            if (rs.next()) {
                haco.setC1idhabitacion_costo(rs.getInt(1));
                haco.setC2fecha_creado(rs.getString(2));
                haco.setC3creado_por(rs.getString(3));
                haco.setC4activo(rs.getBoolean(4));
                haco.setC5nombre(rs.getString(5));
                haco.setC6nivel_lujo(rs.getString(6));
                haco.setC7monto_por_hora_minimo(rs.getDouble(7));
                haco.setC8monto_por_hora_adicional(rs.getDouble(8));
                haco.setC9monto_por_dormir_minimo(rs.getDouble(9));
                haco.setC10monto_por_dormir_adicional(rs.getDouble(10));
                haco.setC11minuto_minimo(rs.getInt(11));
                haco.setC12minuto_adicional(rs.getInt(12));
                haco.setC13minuto_cancelar(rs.getInt(13));
                haco.setC14hs_dormir_ingreso_inicio(rs.getString(14));
                haco.setC15hs_dormir_ingreso_final(rs.getString(15));
                haco.setC16hs_dormir_salida_final(rs.getString(16));
                haco.setC17minuto_tolerancia(rs.getInt(17));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + haco.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + haco.toString(), titulo);
        }
    }

    public void actualizar_tabla_habitacion_costo(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_habitacion_costo(tbltabla);
    }

    public void ancho_tabla_habitacion_costo(JTable tbltabla) {
        int Ancho[] = {10, 40, 40, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void actualizar_tabla_habitacion_costo_por_hab(Connection conn, JTable tbltabla,int idhabitacion_costo){
        String sql = "select hd.idhabitacion_dato as idhd,hd.nro_habitacion as nro_hab,hd.tipo_habitacion,hd.estado_actual as estado,\n"
            + "TRIM(to_char(hc.monto_por_hora_minimo,'999G999G999')) as hs_mini,"
            + "TRIM(to_char(hc.monto_por_hora_adicional,'999G999G999')) as hs_add,\n"
            + "TRIM(to_char(hc.monto_por_dormir_minimo,'999G999G999')) as dor_mini,"
            + "TRIM(to_char(hc.monto_por_dormir_adicional,'999G999G999')) as dor_add,\n"
            + "hc.minuto_minimo as min_mini,hc.minuto_adicional as min_add,"
                + "hc.minuto_cancelar as min_cancel,hc.minuto_tolerancia as min_tolera,\n"
            + "hd.activo \n"
            + "from habitacion_dato hd,habitacion_costo hc\n"
            + "where hd.fk_idhabitacion_costo=hc.idhabitacion_costo\n"
            + "and hc.idhabitacion_costo="+idhabitacion_costo
            + " order by hd.nro_habitacion desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_habitacion_dato(tbltabla);
    }
    public void ancho_tabla_habitacion_dato(JTable tbltabla) {
        int Ancho[] = {4, 4,7,7, 7,7, 7,7, 7,7,7,7,5};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 4);
        evejt.alinear_derecha_columna(tbltabla, 5);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
    }
}
