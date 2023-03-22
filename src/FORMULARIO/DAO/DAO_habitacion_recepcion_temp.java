package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.habitacion_recepcion_temp;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_habitacion_recepcion_temp {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "HABITACION_RECEPCION_TEMP GUARDADO CORRECTAMENTE";
    private String mensaje_update = "HABITACION_RECEPCION_TEMP MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO habitacion_recepcion_temp"
            + "(idhabitacion_recepcion_temp,idhabitacion_recepcion_actual,fecha_creado,creado_por,"
            + "nro_habitacion,descripcion_habitacion,estado,"
            + "fec_libre_inicio,fec_libre_fin,"
            + "fec_ocupado_inicio,fec_ocupado_fin,"
            + "fec_sucio_inicio,fec_sucio_fin,"
            + "fec_limpieza_inicio,fec_limpieza_fin,"
            + "fec_mante_inicio,fec_mante_fin,"
            + "es_libre,es_ocupado,es_sucio,es_limpieza,es_mante,es_cancelado,es_por_hora,es_por_dormir,"
            + "monto_por_hora_minimo,monto_por_hora_adicional,"
            + "monto_por_dormir_minimo,monto_por_dormir_adicional,"
            + "monto_consumision,monto_descuento,"
            + "minuto_minimo,minuto_adicional,minuto_cancelar,"
            + "hs_dormir_ingreso_inicio,hs_dormir_ingreso_final,hs_dormir_salida_final,"
            + "puerta_cliente,puerta_limpieza,tipo_habitacion,monto_adelanto,idhabitacion_dato,es_manual,orden,activo) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE habitacion_recepcion_temp "
            + "SET idhabitacion_recepcion_actual=?,fecha_creado=?,creado_por=?,"
            + "nro_habitacion=?,descripcion_habitacion=?,estado=?,"
            + "fec_libre_inicio=?,fec_libre_fin=?,"
            + "fec_ocupado_inicio=?,fec_ocupado_fin=?,"
            + "fec_sucio_inicio=?,fec_sucio_fin=?,"
            + "fec_limpieza_inicio=?,fec_limpieza_fin=?,"
            + "fec_mante_inicio=?,fec_mante_fin=?,"
            + "es_libre=?,es_ocupado=?,es_sucio=?,es_limpieza=?,es_mante=?,es_cancelado=?,es_por_hora=?,es_por_dormir=?,"
            + "monto_por_hora_minimo=?,monto_por_hora_adicional=?,"
            + "monto_por_dormir_minimo=?,monto_por_dormir_adicional=?,"
            + "monto_consumision=?,monto_descuento=?,"
            + "minuto_minimo=?,minuto_adicional=?,minuto_cancelar=?,"
            + "hs_dormir_ingreso_inicio=?,hs_dormir_ingreso_final=?,hs_dormir_salida_final=?,"
            + "puerta_cliente=?,puerta_limpieza=?,tipo_habitacion=?,monto_adelanto=?,idhabitacion_dato=?,es_manual=?,orden=?,activo=? "
            + "WHERE idhabitacion_dato=?;";
    private String sql_select = "SELECT idhabitacion_recepcion_temp,idhabitacion_recepcion_actual,fecha_creado,creado_por,nro_habitacion,descripcion_habitacion,estado,fec_libre_inicio,fec_libre_fin,fec_ocupado_inicio,fec_ocupado_fin,fec_sucio_inicio,fec_sucio_fin,fec_limpieza_inicio,fec_limpieza_fin,fec_mante_inicio,fec_mante_fin,es_libre,es_ocupado,es_sucio,es_limpieza,es_mante,es_cancelado,es_por_hora,es_por_dormir,monto_por_hora_minimo,monto_por_hora_adicional,monto_por_dormir_minimo,monto_por_dormir_adicional,monto_consumision,monto_descuento,minuto_minimo,minuto_adicional,minuto_cancelar,hs_dormir_ingreso_inicio,hs_dormir_ingreso_final,hs_dormir_salida_final FROM habitacion_recepcion_temp order by 1 desc;";
    private String sql_cargar = "SELECT idhabitacion_recepcion_temp,idhabitacion_recepcion_actual,fecha_creado,creado_por,"
            + "nro_habitacion,descripcion_habitacion,estado,"
            + "fec_libre_inicio,fec_libre_fin,"
            + "fec_ocupado_inicio,fec_ocupado_fin,"
            + "fec_sucio_inicio,fec_sucio_fin,"
            + "fec_limpieza_inicio,fec_limpieza_fin,"
            + "fec_mante_inicio,fec_mante_fin,"
            + "es_libre,es_ocupado,es_sucio,es_limpieza,es_mante,es_cancelado,es_por_hora,es_por_dormir,"
            + "monto_por_hora_minimo,monto_por_hora_adicional,monto_por_dormir_minimo,monto_por_dormir_adicional,"
            + "monto_consumision,monto_descuento,"
            + "minuto_minimo,minuto_adicional,minuto_cancelar,"
            + "hs_dormir_ingreso_inicio,hs_dormir_ingreso_final,hs_dormir_salida_final,"
            + "puerta_cliente,puerta_limpieza,tipo_habitacion,"
            + "monto_adelanto,idhabitacion_dato,es_manual,orden,activo,"
            + "(TRIM(to_char(nro_habitacion,'009'))||' |'||\n"
            + "to_char(fec_ocupado_inicio,'HH24:MI')||' |'||\n"
            + "to_char(current_timestamp,'HH24:MI')||' |'||\n"
            + "to_char((current_timestamp-fec_ocupado_inicio),'HH24:MI')||' |'||\n"
            + "tipo_habitacion) as descrip_caja_desocupa "
            + "FROM habitacion_recepcion_temp WHERE idhabitacion_dato=";
    private String sql_update_dato = "UPDATE habitacion_recepcion_temp "
            + "SET "
            + "descripcion_habitacion=?,"
            + "monto_por_hora_minimo=?,"
            + "monto_por_hora_adicional=?,"
            + "monto_por_dormir_minimo=?,"
            + "monto_por_dormir_adicional=?,"
            + "minuto_minimo=?,"
            + "minuto_adicional=?,"
            + "minuto_cancelar=?,"
            + "hs_dormir_ingreso_inicio=?,"
            + "hs_dormir_ingreso_final=?,"
            + "hs_dormir_salida_final=?,"
            + "tipo_habitacion=?,"
            + "idhabitacion_dato=?, "
            + "es_manual=?,"
            + "nro_habitacion=?,"
            + "orden=?,activo=? "
            + "WHERE idhabitacion_dato=?;";

    public void insertar_habitacion_recepcion_temp(Connection conn, habitacion_recepcion_temp harete) {
        harete.setC1idhabitacion_recepcion_temp(eveconn.getInt_ultimoID_mas_uno(conn, harete.getTb_habitacion_recepcion_temp(), harete.getId_idhabitacion_recepcion_temp()));
        String titulo = "insertar_habitacion_recepcion_temp";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, harete.getC1idhabitacion_recepcion_temp());
            pst.setInt(2, harete.getC2idhabitacion_recepcion_actual());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setString(4, harete.getC4creado_por());
            pst.setInt(5, harete.getC5nro_habitacion());
            pst.setString(6, harete.getC6descripcion_habitacion());
            pst.setString(7, harete.getC7estado());
            pst.setTimestamp(8, evefec.getTimestamp_sistema());
            pst.setTimestamp(9, evefec.getTimestamp_sistema());
            pst.setTimestamp(10, evefec.getTimestamp_sistema());
            pst.setTimestamp(11, evefec.getTimestamp_sistema());
            pst.setTimestamp(12, evefec.getTimestamp_sistema());
            pst.setTimestamp(13, evefec.getTimestamp_sistema());
            pst.setTimestamp(14, evefec.getTimestamp_sistema());
            pst.setTimestamp(15, evefec.getTimestamp_sistema());
            pst.setTimestamp(16, evefec.getTimestamp_sistema());
            pst.setTimestamp(17, evefec.getTimestamp_sistema());
            pst.setBoolean(18, harete.getC18es_libre());
            pst.setBoolean(19, harete.getC19es_ocupado());
            pst.setBoolean(20, harete.getC20es_sucio());
            pst.setBoolean(21, harete.getC21es_limpieza());
            pst.setBoolean(22, harete.getC22es_mante());
            pst.setBoolean(23, harete.getC23es_cancelado());
            pst.setBoolean(24, harete.getC24es_por_hora());
            pst.setBoolean(25, harete.getC25es_por_dormir());
            pst.setDouble(26, harete.getC26monto_por_hora_minimo());
            pst.setDouble(27, harete.getC27monto_por_hora_adicional());
            pst.setDouble(28, harete.getC28monto_por_dormir_minimo());
            pst.setDouble(29, harete.getC29monto_por_dormir_adicional());
            pst.setDouble(30, harete.getC30monto_consumision());
            pst.setDouble(31, harete.getC31monto_descuento());
            pst.setInt(32, harete.getC32minuto_minimo());
            pst.setInt(33, harete.getC33minuto_adicional());
            pst.setInt(34, harete.getC34minuto_cancelar());
            pst.setTime(35, evefec.getTime_sistema_cargado(harete.getC35hs_dormir_ingreso_inicio()));
            pst.setTime(36, evefec.getTime_sistema_cargado(harete.getC36hs_dormir_ingreso_final()));
            pst.setTime(37, evefec.getTime_sistema_cargado(harete.getC37hs_dormir_salida_final()));
            pst.setBoolean(38, harete.getC38puerta_cliente());
            pst.setBoolean(39, harete.getC39puerta_limpieza());
            pst.setString(40, harete.getC40tipo_habitacion());
            pst.setDouble(41, harete.getC41monto_adelanto());
            pst.setInt(42, harete.getC42idhabitacion_dato());
            pst.setBoolean(43, harete.getC43es_manual());
            pst.setInt(44, harete.getC44orden());
            pst.setBoolean(45, harete.getC45activo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + harete.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + harete.toString(), titulo);
        }
    }

    public void update_habitacion_recepcion_temp(Connection conn, habitacion_recepcion_temp harete) {
        String titulo = "update_habitacion_recepcion_temp";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setInt(1, harete.getC2idhabitacion_recepcion_actual());
            pst.setTimestamp(2, evefec.getTimestamp_fecha_cargado(harete.getC3fecha_creado(), "harete.getC3fecha_creado()"));
            pst.setString(3, harete.getC4creado_por());
            pst.setInt(4, harete.getC5nro_habitacion());
            pst.setString(5, harete.getC6descripcion_habitacion());
            pst.setString(6, harete.getC7estado());
            pst.setTimestamp(7, evefec.getTimestamp_fecha_cargado(harete.getC8fec_libre_inicio(), "harete.getC8fec_libre_inicio()"));
            pst.setTimestamp(8, evefec.getTimestamp_fecha_cargado(harete.getC9fec_libre_fin(), "harete.getC9fec_libre_fin()"));
            pst.setTimestamp(9, evefec.getTimestamp_fecha_cargado(harete.getC10fec_ocupado_inicio(), "harete.getC10fec_ocupado_inicio()"));
            pst.setTimestamp(10, evefec.getTimestamp_fecha_cargado(harete.getC11fec_ocupado_fin(), "harete.getC11fec_ocupado_fin()"));
            pst.setTimestamp(11, evefec.getTimestamp_fecha_cargado(harete.getC12fec_sucio_inicio(), "harete.getC12fec_sucio_inicio()"));
            pst.setTimestamp(12, evefec.getTimestamp_fecha_cargado(harete.getC13fec_sucio_fin(), "harete.getC13fec_sucio_fin()"));
            pst.setTimestamp(13, evefec.getTimestamp_fecha_cargado(harete.getC14fec_limpieza_inicio(), "harete.getC14fec_limpieza_inicio()"));
            pst.setTimestamp(14, evefec.getTimestamp_fecha_cargado(harete.getC15fec_limpieza_fin(), "harete.getC15fec_limpieza_fin()"));
            pst.setTimestamp(15, evefec.getTimestamp_fecha_cargado(harete.getC16fec_mante_inicio(), "harete.getC16fec_mante_inicio()"));
            pst.setTimestamp(16, evefec.getTimestamp_fecha_cargado(harete.getC17fec_mante_fin(), "harete.getC17fec_mante_fin()"));
            pst.setBoolean(17, harete.getC18es_libre());
            pst.setBoolean(18, harete.getC19es_ocupado());
            pst.setBoolean(19, harete.getC20es_sucio());
            pst.setBoolean(20, harete.getC21es_limpieza());
            pst.setBoolean(21, harete.getC22es_mante());
            pst.setBoolean(22, harete.getC23es_cancelado());
            pst.setBoolean(23, harete.getC24es_por_hora());
            pst.setBoolean(24, harete.getC25es_por_dormir());
            pst.setDouble(25, harete.getC26monto_por_hora_minimo());
            pst.setDouble(26, harete.getC27monto_por_hora_adicional());
            pst.setDouble(27, harete.getC28monto_por_dormir_minimo());
            pst.setDouble(28, harete.getC29monto_por_dormir_adicional());
            pst.setDouble(29, harete.getC30monto_consumision());
            pst.setDouble(30, harete.getC31monto_descuento());
            pst.setInt(31, harete.getC32minuto_minimo());
            pst.setInt(32, harete.getC33minuto_adicional());
            pst.setInt(33, harete.getC34minuto_cancelar());
            pst.setTime(34, evefec.getTime_sistema_cargado(harete.getC35hs_dormir_ingreso_inicio()));
            pst.setTime(35, evefec.getTime_sistema_cargado(harete.getC36hs_dormir_ingreso_final()));
            pst.setTime(36, evefec.getTime_sistema_cargado(harete.getC37hs_dormir_salida_final()));
            pst.setBoolean(37, harete.getC38puerta_cliente());
            pst.setBoolean(38, harete.getC39puerta_limpieza());
            pst.setString(39, harete.getC40tipo_habitacion());
            pst.setDouble(40, harete.getC41monto_adelanto());
            pst.setInt(41, harete.getC42idhabitacion_dato());
            pst.setBoolean(42, harete.getC43es_manual());
            pst.setInt(43, harete.getC44orden());
            pst.setBoolean(44, harete.getC45activo());
            pst.setInt(45, harete.getC42idhabitacion_dato());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + harete.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + harete.toString(), titulo);
        }
    }

    public void update_habitacion_recepcion_temp_dato(Connection conn, habitacion_recepcion_temp harete) {
        String titulo = "update_habitacion_recepcion_temp_dato";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_dato);
            pst.setString(1, harete.getC6descripcion_habitacion());
            pst.setDouble(2, harete.getC26monto_por_hora_minimo());
            pst.setDouble(3, harete.getC27monto_por_hora_adicional());
            pst.setDouble(4, harete.getC28monto_por_dormir_minimo());
            pst.setDouble(5, harete.getC29monto_por_dormir_adicional());
            pst.setInt(6, harete.getC32minuto_minimo());
            pst.setInt(7, harete.getC33minuto_adicional());
            pst.setInt(8, harete.getC34minuto_cancelar());
            pst.setTime(9, evefec.getTime_sistema_cargado(harete.getC35hs_dormir_ingreso_inicio()));
            pst.setTime(10, evefec.getTime_sistema_cargado(harete.getC36hs_dormir_ingreso_final()));
            pst.setTime(11, evefec.getTime_sistema_cargado(harete.getC37hs_dormir_salida_final()));
            pst.setString(12, harete.getC40tipo_habitacion());
            pst.setInt(13, harete.getC42idhabitacion_dato());
            pst.setBoolean(14, harete.getC43es_manual());
            pst.setInt(15, harete.getC5nro_habitacion());
            pst.setInt(16, harete.getC44orden());
            pst.setBoolean(17, harete.getC45activo());
            pst.setInt(18, harete.getC42idhabitacion_dato());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_dato + "\n" + harete.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_dato + "\n" + harete.toString(), titulo);
        }
    }

    public void cargar_habitacion_recepcion_temp(Connection conn, habitacion_recepcion_temp harete, int idhabitacion_dato) {
        String titulo = "Cargar_habitacion_recepcion_temp";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idhabitacion_dato, titulo);
            if (rs.next()) {
                harete.setC1idhabitacion_recepcion_temp(rs.getInt(1));
                harete.setC2idhabitacion_recepcion_actual(rs.getInt(2));
                harete.setC3fecha_creado(rs.getString(3));
                harete.setC4creado_por(rs.getString(4));
                harete.setC5nro_habitacion(rs.getInt(5));
                harete.setC6descripcion_habitacion(rs.getString(6));
                harete.setC7estado(rs.getString(7));
                harete.setC8fec_libre_inicio(rs.getString(8));
                harete.setC9fec_libre_fin(rs.getString(9));
                harete.setC10fec_ocupado_inicio(rs.getString(10));
                harete.setC11fec_ocupado_fin(rs.getString(11));
                harete.setC12fec_sucio_inicio(rs.getString(12));
                harete.setC13fec_sucio_fin(rs.getString(13));
                harete.setC14fec_limpieza_inicio(rs.getString(14));
                harete.setC15fec_limpieza_fin(rs.getString(15));
                harete.setC16fec_mante_inicio(rs.getString(16));
                harete.setC17fec_mante_fin(rs.getString(17));
                harete.setC18es_libre(rs.getBoolean(18));
                harete.setC19es_ocupado(rs.getBoolean(19));
                harete.setC20es_sucio(rs.getBoolean(20));
                harete.setC21es_limpieza(rs.getBoolean(21));
                harete.setC22es_mante(rs.getBoolean(22));
                harete.setC23es_cancelado(rs.getBoolean(23));
                harete.setC24es_por_hora(rs.getBoolean(24));
                harete.setC25es_por_dormir(rs.getBoolean(25));
                harete.setC26monto_por_hora_minimo(rs.getDouble(26));
                harete.setC27monto_por_hora_adicional(rs.getDouble(27));
                harete.setC28monto_por_dormir_minimo(rs.getDouble(28));
                harete.setC29monto_por_dormir_adicional(rs.getDouble(29));
                harete.setC30monto_consumision(rs.getDouble(30));
                harete.setC31monto_descuento(rs.getDouble(31));
                harete.setC32minuto_minimo(rs.getInt(32));
                harete.setC33minuto_adicional(rs.getInt(33));
                harete.setC34minuto_cancelar(rs.getInt(34));
                harete.setC35hs_dormir_ingreso_inicio(rs.getString(35));
                harete.setC36hs_dormir_ingreso_final(rs.getString(36));
                harete.setC37hs_dormir_salida_final(rs.getString(37));
                harete.setC38puerta_cliente(rs.getBoolean(38));
                harete.setC39puerta_limpieza(rs.getBoolean(39));
                harete.setC40tipo_habitacion(rs.getString(40));
                harete.setC41monto_adelanto(rs.getDouble(41));
                harete.setC42idhabitacion_dato(rs.getInt(42));
                harete.setC43es_manual(rs.getBoolean(43));
                harete.setC44orden(rs.getInt(44));
                harete.setC45activo(rs.getBoolean(45));
                harete.setDescrip_caja_desocupa(rs.getString(46));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + harete.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + harete.toString(), titulo);
        }
    }

    public void actualizar_tabla_habitacion_recepcion_temp(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_habitacion_recepcion_temp(tbltabla);
    }

    public void ancho_tabla_habitacion_recepcion_temp(JTable tbltabla) {
        int Ancho[] = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_estado_puerta_cliente_limpieza(Connection conn, int sensor_puerta_cliente, int sensor_puerta_limpieza) {
        //case when hd.es_manual=true then false else ig.alto_bajo end
        String sql = "update habitacion_recepcion_temp  set "
                + "puerta_cliente=(select case when hd.es_manual=true then true else ig.alto_bajo end  \n"
                + "from habitacion_item_sensor_gpio ig,habitacion_dato hd  \n"
                + "where ig.fk_idhabitacion_sensor=? \n"
                + "and ig.fk_idhabitacion_dato=hd.idhabitacion_dato \n"
                + "and hd.idhabitacion_dato=habitacion_recepcion_temp.idhabitacion_dato),\n"
                + "puerta_limpieza=(select case when hd.es_manual=true then true else ig.alto_bajo end  \n"
                + "from habitacion_item_sensor_gpio ig,habitacion_dato hd  \n"
                + "where ig.fk_idhabitacion_sensor=?\n"
                + "and ig.fk_idhabitacion_dato=hd.idhabitacion_dato \n"
                + "and hd.idhabitacion_dato=habitacion_recepcion_temp.idhabitacion_dato);";
//        eveconn.SQL_execute_libre_sin_print(conn, sql);
        String titulo = "actualizar_estado_puerta_cliente_limpieza";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, sensor_puerta_cliente);
            pst.setInt(2, sensor_puerta_limpieza);
            pst.execute();
            pst.close();
//            evemen.Imprimir_serial_sql(sql + "\n", titulo);
//            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql + "\n", titulo);
        }
    }

    public void update_habitacion_recepcion_temp_costos(Connection conn) {
        String sql = "update habitacion_recepcion_temp hrt \n"
                + "set monto_por_hora_minimo=hc.monto_por_hora_minimo,\n"
                + "monto_por_hora_adicional=hc.monto_por_hora_adicional,\n"
                + "monto_por_dormir_minimo=hc.monto_por_dormir_minimo,\n"
                + "monto_por_dormir_adicional=hc.monto_por_dormir_adicional,\n"
                + "minuto_minimo=hc.minuto_minimo,\n"
                + "minuto_adicional=hc.minuto_adicional,\n"
                + "minuto_cancelar=hc.minuto_cancelar,\n"
                + "hs_dormir_ingreso_inicio=hc.hs_dormir_ingreso_inicio,\n"
                + "hs_dormir_ingreso_final=hc.hs_dormir_ingreso_final,\n"
                + "hs_dormir_salida_final=hc.hs_dormir_salida_final \n"
                + "from habitacion_costo hc,habitacion_dato hd  \n"
                + "where hd.fk_idhabitacion_costo=hc.idhabitacion_costo \n"
                + "and hd.idhabitacion_dato=hrt.idhabitacion_dato;";
        eveconn.SQL_execute_libre(conn, sql);
    }

    public void update_habitacion_recepcion_temp_pasar_dato(Connection conn) {
        String sql = "update habitacion_recepcion_temp hrt \n"
                + "set activo=hd.activo \n"
                + "from habitacion_dato hd  \n"
                + "where  hd.idhabitacion_dato=hrt.idhabitacion_dato;";
        eveconn.SQL_execute_libre(conn, sql);
    }
    public void update_habitacion_recepcion_temp_salida_final(Connection conn,int idhabitacion_dato) {
        String sql = "update habitacion_recepcion_temp hrt \n"
                + "set hs_dormir_salida_final=hc.hs_dormir_salida_final \n"
                + "from habitacion_costo hc,habitacion_dato hd  \n"
                + "where hd.fk_idhabitacion_costo=hc.idhabitacion_costo \n"
                + "and hd.idhabitacion_dato=hrt.idhabitacion_dato \n"
                + "and hrt.idhabitacion_dato="+idhabitacion_dato;
        eveconn.SQL_execute_libre(conn, sql);
    }
}
