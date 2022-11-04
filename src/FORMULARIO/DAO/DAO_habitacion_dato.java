package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.habitacion_dato;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_habitacion_dato {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "HABITACION_DATO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "HABITACION_DATO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO habitacion_dato(idhabitacion_dato,fecha_creado,creado_por,"
            + "nro_habitacion,tipo_habitacion,estado_actual,descripcion,ubicacion,"
            + "activo,con_frigobar,fk_idhabitacion_costo,es_manual) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE habitacion_dato SET fecha_creado=?,creado_por=?,"
            + "nro_habitacion=?,tipo_habitacion=?,estado_actual=?,descripcion=?,ubicacion=?,"
            + "activo=?,con_frigobar=?,fk_idhabitacion_costo=?,es_manual=? WHERE idhabitacion_dato=?;";
    private String sql_select = "select hd.idhabitacion_dato as idhd,hd.nro_habitacion as nro_hab,"
            + "hd.tipo_habitacion,hd.estado_actual as estado,\n"
            + "TRIM(to_char(hc.monto_por_hora_minimo,'999G999G999')) as hs_mini,"
            + "TRIM(to_char(hc.monto_por_hora_adicional,'999G999G999')) as hs_add,\n"
            + "TRIM(to_char(hc.monto_por_dormir_minimo,'999G999G999')) as dor_mini,"
            + "TRIM(to_char(hc.monto_por_dormir_adicional,'999G999G999')) as dor_add,\n"
            + "hc.minuto_minimo as min_mini,hc.minuto_adicional as min_add,hc.minuto_cancelar as min_cancel,\n"
            + "hd.activo,hd.es_manual as manual,hrt.orden as ord \n"
            + "from habitacion_dato hd,habitacion_costo hc,habitacion_recepcion_temp hrt \n"
            + "where hd.fk_idhabitacion_costo=hc.idhabitacion_costo \n"
            + "and hd.idhabitacion_dato=hrt.idhabitacion_dato \n"
            + "order by hd.idhabitacion_dato asc;";
    private String sql_cargar = "SELECT idhabitacion_dato,fecha_creado,creado_por,"
            + "nro_habitacion,tipo_habitacion,estado_actual,descripcion,ubicacion,"
            + "activo,con_frigobar,fk_idhabitacion_costo,es_manual "
            + "FROM habitacion_dato WHERE idhabitacion_dato=";

    public void insertar_habitacion_dato(Connection conn, habitacion_dato hada) {
        hada.setC1idhabitacion_dato(eveconn.getInt_ultimoID_mas_uno(conn, hada.getTb_habitacion_dato(), hada.getId_idhabitacion_dato()));
        String titulo = "insertar_habitacion_dato";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, hada.getC1idhabitacion_dato());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, hada.getC3creado_por());
            pst.setInt(4, hada.getC4nro_habitacion());
            pst.setString(5, hada.getC5tipo_habitacion());
            pst.setString(6, hada.getC6estado_actual());
            pst.setString(7, hada.getC7descripcion());
            pst.setString(8, hada.getC8ubicacion());
            pst.setBoolean(9, hada.getC9activo());
            pst.setBoolean(10, hada.getC10con_frigobar());
            pst.setInt(11, hada.getC11fk_idhabitacion_costo());
            pst.setBoolean(12, hada.getC12es_manual());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + hada.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + hada.toString(), titulo);
        }
    }

    public void update_habitacion_dato(Connection conn, habitacion_dato hada) {
        String titulo = "update_habitacion_dato";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, hada.getC3creado_por());
            pst.setInt(3, hada.getC4nro_habitacion());
            pst.setString(4, hada.getC5tipo_habitacion());
            pst.setString(5, hada.getC6estado_actual());
            pst.setString(6, hada.getC7descripcion());
            pst.setString(7, hada.getC8ubicacion());
            pst.setBoolean(8, hada.getC9activo());
            pst.setBoolean(9, hada.getC10con_frigobar());
            pst.setInt(10, hada.getC11fk_idhabitacion_costo());
            pst.setBoolean(11, hada.getC12es_manual());
            pst.setInt(12, hada.getC1idhabitacion_dato());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + hada.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + hada.toString(), titulo);
        }
    }

    public void cargar_habitacion_dato(Connection conn, habitacion_dato hada, int idhabitacion_dato) {
        String titulo = "Cargar_habitacion_dato";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idhabitacion_dato, titulo);
            if (rs.next()) {
                hada.setC1idhabitacion_dato(rs.getInt(1));
                hada.setC2fecha_creado(rs.getString(2));
                hada.setC3creado_por(rs.getString(3));
                hada.setC4nro_habitacion(rs.getInt(4));
                hada.setC5tipo_habitacion(rs.getString(5));
                hada.setC6estado_actual(rs.getString(6));
                hada.setC7descripcion(rs.getString(7));
                hada.setC8ubicacion(rs.getString(8));
                hada.setC9activo(rs.getBoolean(9));
                hada.setC10con_frigobar(rs.getBoolean(10));
                hada.setC11fk_idhabitacion_costo(rs.getInt(11));
                hada.setC12es_manual(rs.getBoolean(12));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + hada.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + hada.toString(), titulo);
        }
    }

    public void actualizar_tabla_habitacion_dato(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_habitacion_dato(tbltabla);
    }

    public void ancho_tabla_habitacion_dato(JTable tbltabla) {
        int Ancho[] = {4, 3,8,8, 8,8, 8,8, 6,6,6,3,3,3};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 4);
        evejt.alinear_derecha_columna(tbltabla, 5);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
    }
}
