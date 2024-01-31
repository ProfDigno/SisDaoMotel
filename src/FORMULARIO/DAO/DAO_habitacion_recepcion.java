package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.habitacion_recepcion;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_habitacion_recepcion {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "HABITACION_RECEPCION GUARDADO CORRECTAMENTE";
    private String mensaje_update = "HABITACION_RECEPCION MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO habitacion_recepcion(idhabitacion_recepcion,fecha_creado,creado_por,"
            + "estado,nro_habitacion,fec_libre_inicio,fec_libre_fin,fec_ocupado_inicio,fec_ocupado_fin,"
            + "fec_sucio_inicio,fec_sucio_fin,fec_limpieza_inicio,fec_limpieza_fin,fec_mante_inicio,fec_mante_fin,"
            + "es_libre,es_ocupado,es_sucio,es_limpieza,es_mante,es_cancelado,es_por_hora,es_por_dormir,"
            + "es_boton_actual,es_pagado,es_terminado,"
            + "monto_por_hora_minimo,monto_por_hora_adicional,monto_por_dormir_minimo,monto_por_dormir_adicional,"
            + "monto_consumo,monto_descuento,monto_adelanto,cant_adicional,fk_idhabitacion_dato,"
            + "monto_por_hospedaje_minimo,fec_hospedaje_inicio,fec_hospedaje_fin,es_hospedaje) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE habitacion_recepcion SET fecha_creado=?,creado_por=?,"
            + "estado=?,nro_habitacion=?,fec_libre_inicio=?,fec_libre_fin=?,fec_ocupado_inicio=?,fec_ocupado_fin=?,"
            + "fec_sucio_inicio=?,fec_sucio_fin=?,fec_limpieza_inicio=?,fec_limpieza_fin=?,fec_mante_inicio=?,fec_mante_fin=?,"
            + "es_libre=?,es_ocupado=?,es_sucio=?,es_limpieza=?,es_mante=?,es_cancelado=?,es_por_hora=?,es_por_dormir=?,"
            + "es_boton_actual=?,es_pagado=?,es_terminado=?,"
            + "monto_por_hora_minimo=?,monto_por_hora_adicional=?,monto_por_dormir_minimo=?,monto_por_dormir_adicional=?,"
            + "monto_consumo=?,monto_descuento=?,monto_adelanto=?,cant_adicional=?,fk_idhabitacion_dato=?,"
            + "monto_por_hospedaje_minimo=?,fec_hospedaje_inicio=?,fec_hospedaje_fin=?,es_hospedaje=? "
            + "WHERE idhabitacion_recepcion=?;";
    private String sql_select = "SELECT idhabitacion_recepcion,fecha_creado,creado_por,estado,nro_habitacion,fec_libre_inicio,fec_libre_fin,fec_ocupado_inicio,fec_ocupado_fin,fec_sucio_inicio,fec_sucio_fin,fec_limpieza_inicio,fec_limpieza_fin,fec_mante_inicio,fec_mante_fin,es_libre,es_ocupado,es_sucio,es_limpieza,es_mante,es_cancelado,es_por_hora,es_por_dormir,es_boton_actual,es_pagado,es_terminado,monto_por_hora_minimo,monto_por_hora_adicional,monto_por_dormir_minimo,monto_por_dormir_adicional,monto_consumo,monto_descuento,monto_adelanto,cant_adicional,fk_idhabitacion_dato FROM habitacion_recepcion order by 1 desc;";
    private String sql_cargar = "SELECT idhabitacion_recepcion,fecha_creado,creado_por,"
            + "estado,nro_habitacion,fec_libre_inicio,fec_libre_fin,fec_ocupado_inicio,fec_ocupado_fin,"
            + "fec_sucio_inicio,fec_sucio_fin,fec_limpieza_inicio,fec_limpieza_fin,fec_mante_inicio,fec_mante_fin,"
            + "es_libre,es_ocupado,es_sucio,es_limpieza,es_mante,es_cancelado,es_por_hora,es_por_dormir,"
            + "es_boton_actual,es_pagado,es_terminado,"
            + "monto_por_hora_minimo,monto_por_hora_adicional,monto_por_dormir_minimo,monto_por_dormir_adicional,"
            + "monto_consumo,monto_descuento,monto_adelanto,cant_adicional,fk_idhabitacion_dato,"
            + "monto_por_hospedaje_minimo,fec_hospedaje_inicio,fec_hospedaje_fin,es_hospedaje "
            + "FROM habitacion_recepcion WHERE idhabitacion_recepcion=";

    public void insertar_habitacion_recepcion(Connection conn, habitacion_recepcion hare) {
        hare.setC1idhabitacion_recepcion(eveconn.getInt_ultimoID_mas_uno(conn, hare.getTb_habitacion_recepcion(), hare.getId_idhabitacion_recepcion()));
        String titulo = "insertar_habitacion_recepcion";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, hare.getC1idhabitacion_recepcion());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, hare.getC3creado_por());
            pst.setString(4, hare.getC4estado());
            pst.setInt(5, hare.getC5nro_habitacion());
            pst.setTimestamp(6, evefec.getTimestamp_sistema());
            pst.setTimestamp(7, evefec.getTimestamp_sistema());
            pst.setTimestamp(8, evefec.getTimestamp_fecha_cargado(hare.getC8fec_ocupado_inicio(), "hare.getC8fec_ocupado_inicio()"));
            pst.setTimestamp(9, evefec.getTimestamp_sistema());
            pst.setTimestamp(10, evefec.getTimestamp_sistema());
            pst.setTimestamp(11, evefec.getTimestamp_sistema());
            pst.setTimestamp(12, evefec.getTimestamp_sistema());
            pst.setTimestamp(13, evefec.getTimestamp_sistema());
            pst.setTimestamp(14, evefec.getTimestamp_sistema());
            pst.setTimestamp(15, evefec.getTimestamp_sistema());
            pst.setBoolean(16, hare.getC16es_libre());
            pst.setBoolean(17, hare.getC17es_ocupado());
            pst.setBoolean(18, hare.getC18es_sucio());
            pst.setBoolean(19, hare.getC19es_limpieza());
            pst.setBoolean(20, hare.getC20es_mante());
            pst.setBoolean(21, hare.getC21es_cancelado());
            pst.setBoolean(22, hare.getC22es_por_hora());
            pst.setBoolean(23, hare.getC23es_por_dormir());
            pst.setBoolean(24, hare.getC24es_boton_actual());
            pst.setBoolean(25, hare.getC25es_pagado());
            pst.setBoolean(26, hare.getC26es_terminado());
            pst.setDouble(27, hare.getC27monto_por_hora_minimo());
            pst.setDouble(28, hare.getC28monto_por_hora_adicional());
            pst.setDouble(29, hare.getC29monto_por_dormir_minimo());
            pst.setDouble(30, hare.getC30monto_por_dormir_adicional());
            pst.setDouble(31, hare.getC31monto_consumo());
            pst.setDouble(32, hare.getC32monto_descuento());
            pst.setDouble(33, hare.getC33monto_adelanto());
            pst.setDouble(34, hare.getC34cant_adicional());
            pst.setInt(35, hare.getC35fk_idhabitacion_dato());
            pst.setDouble(36, hare.getC36monto_por_hospedaje_minimo());
            pst.setTimestamp(37, evefec.getTimestamp_sistema());
            pst.setTimestamp(38, evefec.getTimestamp_sistema());
            pst.setBoolean(39, hare.getC39es_hospedaje());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + hare.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + hare.toString(), titulo);
        }
    }

    public void update_habitacion_recepcion(Connection conn, habitacion_recepcion hare) {
        String titulo = "update_habitacion_recepcion";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_fecha_cargado(hare.getC2fecha_creado(), "hare.getC2fecha_creado()"));
            pst.setString(2, hare.getC3creado_por());
            pst.setString(3, hare.getC4estado());
            pst.setInt(4, hare.getC5nro_habitacion());
            pst.setTimestamp(5, evefec.getTimestamp_fecha_cargado(hare.getC6fec_libre_inicio(), "hare.getC6fec_libre_inicio()"));
            pst.setTimestamp(6, evefec.getTimestamp_fecha_cargado(hare.getC7fec_libre_fin(), "hare.getC7fec_libre_fin()"));
            pst.setTimestamp(7, evefec.getTimestamp_fecha_cargado(hare.getC8fec_ocupado_inicio(), "hare.getC8fec_ocupado_inicio()"));
            pst.setTimestamp(8, evefec.getTimestamp_fecha_cargado(hare.getC9fec_ocupado_fin(), "hare.getC9fec_ocupado_fin()"));
            pst.setTimestamp(9, evefec.getTimestamp_fecha_cargado(hare.getC10fec_sucio_inicio(), "hare.getC10fec_sucio_inicio()"));
            pst.setTimestamp(10, evefec.getTimestamp_fecha_cargado(hare.getC11fec_sucio_fin(), "hare.getC11fec_sucio_fin()"));
            pst.setTimestamp(11, evefec.getTimestamp_fecha_cargado(hare.getC12fec_limpieza_inicio(), "hare.getC12fec_limpieza_inicio()"));
            pst.setTimestamp(12, evefec.getTimestamp_fecha_cargado(hare.getC13fec_limpieza_fin(), "hare.getC13fec_limpieza_fin()"));
            pst.setTimestamp(13, evefec.getTimestamp_fecha_cargado(hare.getC14fec_mante_inicio(), "hare.getC14fec_mante_inicio()"));
            pst.setTimestamp(14, evefec.getTimestamp_fecha_cargado(hare.getC15fec_mante_fin(), "hare.getC15fec_mante_fin()"));
            pst.setBoolean(15, hare.getC16es_libre());
            pst.setBoolean(16, hare.getC17es_ocupado());
            pst.setBoolean(17, hare.getC18es_sucio());
            pst.setBoolean(18, hare.getC19es_limpieza());
            pst.setBoolean(19, hare.getC20es_mante());
            pst.setBoolean(20, hare.getC21es_cancelado());
            pst.setBoolean(21, hare.getC22es_por_hora());
            pst.setBoolean(22, hare.getC23es_por_dormir());
            pst.setBoolean(23, hare.getC24es_boton_actual());
            pst.setBoolean(24, hare.getC25es_pagado());
            pst.setBoolean(25, hare.getC26es_terminado());
            pst.setDouble(26, hare.getC27monto_por_hora_minimo());
            pst.setDouble(27, hare.getC28monto_por_hora_adicional());
            pst.setDouble(28, hare.getC29monto_por_dormir_minimo());
            pst.setDouble(29, hare.getC30monto_por_dormir_adicional());
            pst.setDouble(30, hare.getC31monto_consumo());
            pst.setDouble(31, hare.getC32monto_descuento());
            pst.setDouble(32, hare.getC33monto_adelanto());
            pst.setDouble(33, hare.getC34cant_adicional());
            pst.setInt(34, hare.getC35fk_idhabitacion_dato());
            pst.setDouble(35, hare.getC36monto_por_hospedaje_minimo());
            pst.setTimestamp(36, evefec.getTimestamp_sistema());
            pst.setTimestamp(37, evefec.getTimestamp_sistema());
            pst.setBoolean(38, hare.getC39es_hospedaje());
            pst.setInt(39, hare.getC1idhabitacion_recepcion());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + hare.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + hare.toString(), titulo);
        }
    }

    public void cargar_habitacion_recepcion(Connection conn, habitacion_recepcion hare, int idhabitacion_recepcion) {
        String titulo = "Cargar_habitacion_recepcion";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idhabitacion_recepcion, titulo);
            if (rs.next()) {
                hare.setC1idhabitacion_recepcion(rs.getInt(1));
                hare.setC2fecha_creado(rs.getString(2));
                hare.setC3creado_por(rs.getString(3));
                hare.setC4estado(rs.getString(4));
                hare.setC5nro_habitacion(rs.getInt(5));
                hare.setC6fec_libre_inicio(rs.getString(6));
                hare.setC7fec_libre_fin(rs.getString(7));
                hare.setC8fec_ocupado_inicio(rs.getString(8));
                hare.setC9fec_ocupado_fin(rs.getString(9));
                hare.setC10fec_sucio_inicio(rs.getString(10));
                hare.setC11fec_sucio_fin(rs.getString(11));
                hare.setC12fec_limpieza_inicio(rs.getString(12));
                hare.setC13fec_limpieza_fin(rs.getString(13));
                hare.setC14fec_mante_inicio(rs.getString(14));
                hare.setC15fec_mante_fin(rs.getString(15));
                hare.setC16es_libre(rs.getBoolean(16));
                hare.setC17es_ocupado(rs.getBoolean(17));
                hare.setC18es_sucio(rs.getBoolean(18));
                hare.setC19es_limpieza(rs.getBoolean(19));
                hare.setC20es_mante(rs.getBoolean(20));
                hare.setC21es_cancelado(rs.getBoolean(21));
                hare.setC22es_por_hora(rs.getBoolean(22));
                hare.setC23es_por_dormir(rs.getBoolean(23));
                hare.setC24es_boton_actual(rs.getBoolean(24));
                hare.setC25es_pagado(rs.getBoolean(25));
                hare.setC26es_terminado(rs.getBoolean(26));
                hare.setC27monto_por_hora_minimo(rs.getDouble(27));
                hare.setC28monto_por_hora_adicional(rs.getDouble(28));
                hare.setC29monto_por_dormir_minimo(rs.getDouble(29));
                hare.setC30monto_por_dormir_adicional(rs.getDouble(30));
                hare.setC31monto_consumo(rs.getDouble(31));
                hare.setC32monto_descuento(rs.getDouble(32));
                hare.setC33monto_adelanto(rs.getDouble(33));
                hare.setC34cant_adicional(rs.getDouble(34));
                hare.setC35fk_idhabitacion_dato(rs.getInt(35));
                hare.setC36monto_por_hospedaje_minimo(rs.getDouble(36));
                hare.setC37fec_hospedaje_inicio(rs.getString(37));
                hare.setC38fec_hospedaje_fin(rs.getString(38));
                hare.setC39es_hospedaje(rs.getBoolean(39));
                
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + hare.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + hare.toString(), titulo);
        }
    }

    public void actualizar_tabla_habitacion_recepcion(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_habitacion_recepcion(tbltabla);
    }

    public void ancho_tabla_habitacion_recepcion(JTable tbltabla) {
        int Ancho[] = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void imprimir_filtro_habitacion_uso(Connection conn, String filtro) {
        String sql = "SELECT \n"
                + "('('||d.tipo_habitacion||')-'||h.nro_habitacion) as habitacion,\n"
                + "to_char(h.fec_ocupado_inicio,'yyyy-MM-dd') as fecha,\n"
                + "to_char(h.fec_ocupado_inicio,'HH24:MI:ss') as inicio,\n"
                + "to_char(h.fec_ocupado_fin,'HH24:MI:ss') as fin,\n"
                + "to_char((h.fec_ocupado_fin-h.fec_ocupado_inicio),'dd  HH24:MI:ss') as tiempo,\n"
                + "v.monto_minimo as minimo,\n"
                + "v.monto_adicional as adicional,\n"
                + "v.monto_consumo as consumo,\n"
                + "(v.monto_minimo+v.monto_adicional+v.monto_consumo) as monto_total\n"
                + "FROM\n"
                + "  habitacion_recepcion h,venta v,habitacion_dato d\n"
                + "  where h.idhabitacion_recepcion=v.fk_idhabitacion_recepcion\n"
                + "  and h.fk_idhabitacion_dato=d.idhabitacion_dato\n"
                + "  and v.estado='TERMINADO'\n"+filtro
                + "  order by 1 desc,2 desc,3 desc ";
        String titulonota = "HABITACION USO";
        String direccion = "src/REPORTE/OCUPACION/repHabUso.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
}
