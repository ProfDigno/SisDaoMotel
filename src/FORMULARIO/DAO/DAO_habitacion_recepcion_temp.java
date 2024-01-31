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
            + "puerta_cliente,puerta_limpieza,tipo_habitacion,monto_adelanto,idhabitacion_dato,es_manual,orden,activo,"
            + "monto_por_hospedaje_minimo,fec_hospedaje_inicio,fec_hospedaje_fin,es_hospedaje,minuto_hospedaje) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
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
            + "puerta_cliente=?,puerta_limpieza=?,tipo_habitacion=?,monto_adelanto=?,idhabitacion_dato=?,es_manual=?,orden=?,activo=?,"
            + "monto_por_hospedaje_minimo=?,fec_hospedaje_inicio=?,fec_hospedaje_fin=?,es_hospedaje=?,minuto_hospedaje=? "
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
            + "tipo_habitacion) as descrip_caja_desocupa,"
            + "monto_por_hospedaje_minimo,fec_hospedaje_inicio,fec_hospedaje_fin,es_hospedaje,minuto_hospedaje "
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
    private String sql_ocupacion_boton = "";
    private String sql_ocupacion_boton_FUERTE = "";
    private String sql_ocupacion_boton_LIBIANO = "";
    private String sql_ocupacion_boton_CARGAR = "";
    private String Est_Ocu_POR_HORA =   "when estado = 'OCUPADO' and es_por_dormir = false and es_por_hora = true\n";
    private String Est_Ocu_POR_DORMIR = "when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = false\n";
    private String Est_Ocu_POR_HORA_MAS_DORMIR = "when estado = 'OCUPADO' and es_por_dormir = true and es_por_hora = true\n";
    private String Est_Ocu_HOSPEDAJE = "when estado = 'OCUPADO' and es_hospedaje = true\n";
    private String fec_inicio_menor_que_23hs = "    and (fec_ocupado_inicio<(date(fec_ocupado_inicio)+ time '23:59:59'))\n";
    private String fec_actual_mayor_Inicio_00hs = "    and (current_timestamp>(date(fec_ocupado_inicio) + time '00:00:01'))\n";
    private String fec_inicio_mayor_Inicio_00hs = "    and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + time '00:00:01'))\n";
    private String fec_inicio_menor_Dormir_Salida = "    and (fec_ocupado_inicio<(date(fec_ocupado_inicio) + hs_dormir_salida_final))\n";
    private String fec_inicio_mayor_Dormir_Inicio=  "    and (fec_ocupado_inicio>(date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio))\n";
    private String seg_transcurrido_desde_inicio="(extract(epoch from(current_timestamp-fec_ocupado_inicio)))";
    private String seg_minimo="(minuto_minimo*60)";
    private String seg_transcurrido_menor_seg_minimo = "    and ("+seg_transcurrido_desde_inicio+"<"+seg_minimo+")\n";
    private String seg_transcurrido_mayor_seg_minimo = "    and ("+seg_transcurrido_desde_inicio+">"+seg_minimo+")\n";
    private String fec_actual_menor_Dormir_Salida="    and (current_timestamp<((date(fec_ocupado_inicio)) + hs_dormir_salida_final))\n";
    private String fec_actual_mayor_Inicio="    and (current_timestamp>(fec_ocupado_inicio))\n";
    private String fec_actual_menos_Inicio="(current_timestamp-fec_ocupado_inicio)";
    private String fec_actual_menos_Fin="(current_timestamp-fec_ocupado_fin)";
    private String fec_actual_mayor_Dormir_final_mas1="    and (current_timestamp>((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final))\n ";
    private String fec_actual_mayor_Dormir_final="    and (current_timestamp>((date(fec_ocupado_inicio)) + hs_dormir_salida_final))\n";
    private String fec_actual_menor_Dormir_final_mas1="    and (current_timestamp<((date(fec_ocupado_inicio)+ 1) + hs_dormir_salida_final))\n";
    private String monto_dormir_minimo_Double=" monto_por_dormir_minimo + monto_consumision-(monto_descuento+monto_adelanto) ";
    private String monto_porhora_minimo_Double=" monto_por_hora_minimo + monto_consumision-(monto_descuento+monto_adelanto) ";
    private String monto_hospedaje_minimo_Double=" monto_por_hospedaje_minimo + monto_consumision-(monto_descuento+monto_adelanto) ";
    private String fec_actual_mayor_Inicio_mas2="    and (current_timestamp>(date(fec_ocupado_inicio)+2))\n";
    private String fec_actual_mayor_00hs=" (current_timestamp>(date(current_timestamp) + time '00:00:01')) ";
    private String fec_actual_menor_23hs=" (current_timestamp<((date(current_timestamp))+ time '23:59:59')) ";
    private String fec_actual_mayor_Dormir_inicio=" (current_timestamp>(date(current_timestamp) + hs_dormir_ingreso_inicio)) ";
    private String fec_actual_menor_Dormir_final=" (current_timestamp<(date(current_timestamp) + hs_dormir_salida_final)) ";
    private String seg_inicio_a_Dormir_inicio=" (extract(epoch from((date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio) - fec_ocupado_inicio))) ";
    private String hora_actual_Dormir_final_mas1="cast((((extract(epoch from(current_timestamp-((date(fec_ocupado_inicio)+1)+hs_dormir_salida_final)))))/3600) as integer)";
         private String hora_actual_Dormir_final="cast((((extract(epoch from(current_timestamp-((date(fec_ocupado_inicio))+hs_dormir_salida_final)))))/3600) as integer)";
    private String seg_minimo_mas_adicional=" ((minuto_minimo * 60)+(minuto_adicional * 60)) ";
    private String seg_cancelar="(minuto_cancelar*60)";
    private String seg_hospedaje="(minuto_hospedaje*60)";
    private String seg_transcurrido_Dormir_Final_mas1=" FLOOR((((extract(epoch from(current_timestamp - ((date(fec_ocupado_inicio)+1)+ hs_dormir_salida_final)))))/60)/minuto_adicional) ";
    private String seg_transcurrido_Dormir_Final=" FLOOR((((extract(epoch from(current_timestamp-((date(fec_ocupado_inicio))+ hs_dormir_salida_final)))))/60)/minuto_adicional) ";
    private String fec_actual_Dormir_final=" ((date(current_timestamp))+hs_dormir_salida_final) ";
    private String fec_actual_Dormir_final_mas1=" ((date(current_timestamp)+1)+hs_dormir_salida_final) ";
    private String Est_Ocupado=" when estado = 'OCUPADO' ";
    private String Est_Libre=" when estado = 'LIBRE' ";
    private String Est_Sucio=" when estado = 'SUCIO' ";
    private String Est_Limpiando=" when estado = 'LIMPIANDO' ";
    private String Text_Limpiando="-LIMPIANDO";
    private String Text_Mante="-MANTE";
    private String Text_CliIngreso="-CLI-INGRESO";
    private String Text_CliAbierto="-CLI-ABIERTO";
    private String Text_LimAbierto="-LIMP-ABIERTO";
    private String Text_Sucio="-SUCIO";
    private String Text_Cancelar="-(CANCELAR)";
    private String Puerta_1_1=" and puerta_limpieza = true and puerta_cliente = true ";
    private String Puerta_1_0=" and puerta_limpieza = true  and puerta_cliente = false ";
    private String Puerta_0_1=" and puerta_limpieza = false  and puerta_cliente = true ";
    private String Puerta_0_0=" and puerta_limpieza = false  and puerta_cliente = false ";
    private String fec_actual_mayor_Dormir_Salida_Tolerancia="(current_timestamp>((date(fec_ocupado_inicio)) + (hs_dormir_salida_final + (minuto_tolerancia || ' minutes')::interval)))";
    private String fec_actual_mayor_Dormir_Salida_Tolerancia_mas1="(current_timestamp>((date(fec_ocupado_inicio)+ 1) + (hs_dormir_salida_final + (minuto_tolerancia || ' minutes')::interval)))";
    private String hora_transcurrido_desde_inicio_minimo=          "((extract(epoch from(current_timestamp-(fec_ocupado_inicio+((minuto_minimo)    * INTERVAL '1 minute')))))/60)/minuto_adicional)";
    private String hora_transcurrido_desde_inicio_hospedaje=" FLOOR(((extract(epoch from(current_timestamp-(fec_ocupado_inicio+((minuto_hospedaje) * INTERVAL '1 minute')))))/60)/minuto_adicional)";
    private String format_hora="HH24:MI:ss";
    private String hora_Dormir_minimo="(1 + (FLOOR(extract(epoch from((date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio)-(fec_ocupado_inicio+(minuto_minimo || ' minutes')::interval)))/3600) * 1))";
    private String Por_Hora_1=Est_Ocu_POR_HORA+fec_actual_mayor_Inicio_mas2;
    private String Por_Hora_2=Est_Ocu_POR_HORA+seg_transcurrido_menor_seg_minimo;
    private String Por_Hora_3=Est_Ocu_POR_HORA+seg_transcurrido_mayor_seg_minimo+" and ("+seg_transcurrido_desde_inicio+"<"+seg_minimo_mas_adicional+")\n";
    private String Por_Hora_4=Est_Ocu_POR_HORA+" and ("+seg_transcurrido_desde_inicio+">"+seg_minimo_mas_adicional+")\n";
    private String Por_Dormir_1=Est_Ocu_POR_DORMIR+fec_actual_mayor_Inicio_mas2;
    private String Por_Dormir_2=Est_Ocu_POR_DORMIR+fec_inicio_mayor_Dormir_Inicio+fec_inicio_menor_que_23hs+fec_actual_mayor_Inicio_00hs+fec_actual_menor_Dormir_final_mas1;
    private String Por_Dormir_3=Est_Ocu_POR_DORMIR+fec_inicio_mayor_Inicio_00hs+fec_inicio_menor_Dormir_Salida+fec_actual_mayor_Inicio+fec_actual_menor_Dormir_Salida;
    private String Por_Dormir_4=Est_Ocu_POR_DORMIR+fec_inicio_mayor_Dormir_Inicio+fec_inicio_menor_que_23hs+" and "+fec_actual_mayor_Dormir_Salida_Tolerancia_mas1+"\n";
    private String Por_Dormir_5=Est_Ocu_POR_DORMIR+fec_inicio_mayor_Inicio_00hs+fec_inicio_menor_Dormir_Salida+" and "+fec_actual_mayor_Dormir_Salida_Tolerancia+"\n";
    private String Por_Dormir_6=Est_Ocu_POR_DORMIR+fec_inicio_mayor_Dormir_Inicio+fec_inicio_menor_que_23hs+fec_actual_mayor_Dormir_final_mas1;
    private String Por_Dormir_7=Est_Ocu_POR_DORMIR+fec_inicio_mayor_Inicio_00hs+fec_inicio_menor_Dormir_Salida+fec_actual_mayor_Dormir_final;
    private String Por_Hora_Dormir_1=Est_Ocu_POR_HORA_MAS_DORMIR+fec_inicio_menor_que_23hs+fec_actual_mayor_Inicio_00hs+fec_actual_menor_Dormir_final_mas1;
    private String Por_Hora_Dormir_2=Est_Ocu_POR_HORA_MAS_DORMIR+fec_inicio_mayor_Inicio_00hs+fec_inicio_menor_Dormir_Salida+fec_actual_mayor_Inicio+fec_actual_menor_Dormir_Salida;
    private String Por_Hora_Dormir_3=Est_Ocu_POR_HORA_MAS_DORMIR+fec_inicio_menor_que_23hs+" and "+fec_actual_mayor_Dormir_Salida_Tolerancia_mas1+"\n";
    private String Por_Hora_Dormir_4=Est_Ocu_POR_HORA_MAS_DORMIR+fec_inicio_mayor_Inicio_00hs+fec_inicio_menor_Dormir_Salida+fec_actual_mayor_Dormir_final;
    private String Por_Hora_Dormir_5=Est_Ocu_POR_HORA_MAS_DORMIR+fec_inicio_mayor_Dormir_Inicio+fec_inicio_menor_que_23hs+fec_actual_mayor_Dormir_final_mas1;
    private String Por_Hora_Dormir_6=Est_Ocu_POR_HORA_MAS_DORMIR+fec_actual_mayor_Inicio_mas2;
    private String Por_Hora_Dormir_7=Est_Ocu_POR_HORA_MAS_DORMIR+fec_inicio_menor_que_23hs+fec_actual_mayor_Dormir_final_mas1;
    private String Por_hospedaje_1=Est_Ocu_HOSPEDAJE+" and ("+seg_transcurrido_desde_inicio+"<"+seg_hospedaje+")\n";
    private String Por_hospedaje_2=Est_Ocu_HOSPEDAJE+" and ("+seg_transcurrido_desde_inicio+">"+seg_hospedaje+")\n";
    private String es_Por_Hora_1="when descripcion_habitacion = 'Por_Hora_1' ";
    private String es_Por_Hora_2="when descripcion_habitacion = 'Por_Hora_2' ";
    private String es_Por_Hora_3="when descripcion_habitacion = 'Por_Hora_3' ";
    private String es_Por_Hora_4="when descripcion_habitacion = 'Por_Hora_4' ";
    private String es_Por_Dormir_1="when descripcion_habitacion = 'Por_Dormir_1' ";
    private String es_Por_Dormir_2="when descripcion_habitacion = 'Por_Dormir_2' ";
    private String es_Por_Dormir_3="when descripcion_habitacion = 'Por_Dormir_3' ";
    private String es_Por_Dormir_4="when descripcion_habitacion = 'Por_Dormir_4' ";
    private String es_Por_Dormir_5="when descripcion_habitacion = 'Por_Dormir_5' ";
    private String es_Por_Dormir_6="when descripcion_habitacion = 'Por_Dormir_6' ";
    private String es_Por_Dormir_7="when descripcion_habitacion = 'Por_Dormir_7' ";
    private String es_Por_Hora_Dormir_1="when descripcion_habitacion = 'Por_Hora_Dormir_1' ";
    private String es_Por_Hora_Dormir_2="when descripcion_habitacion = 'Por_Hora_Dormir_2' ";
    private String es_Por_Hora_Dormir_3="when descripcion_habitacion = 'Por_Hora_Dormir_3' ";
    private String es_Por_Hora_Dormir_4="when descripcion_habitacion = 'Por_Hora_Dormir_4' ";
    private String es_Por_Hora_Dormir_5="when descripcion_habitacion = 'Por_Hora_Dormir_5' ";
    private String es_Por_Hora_Dormir_6="when descripcion_habitacion = 'Por_Hora_Dormir_6' ";
    private String es_Por_Hora_Dormir_7="when descripcion_habitacion = 'Por_Hora_Dormir_7' ";
    private String es_Por_hospedaje_1="when descripcion_habitacion = 'Por_hospedaje_1' ";
    private String es_Por_hospedaje_2="when descripcion_habitacion = 'Por_hospedaje_2' ";
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
            pst.setDouble(46, harete.getC46monto_por_hospedaje_minimo());
            pst.setTimestamp(47, evefec.getTimestamp_fecha_cargado(harete.getC47fec_hospedaje_inicio(), "getC47fec_hospedaje_inicio"));
            pst.setTimestamp(48, evefec.getTimestamp_fecha_cargado(harete.getC48fec_hospedaje_fin(), "getC48fec_hospedaje_fin"));
            pst.setBoolean(49, harete.getC49es_hospedaje());
            pst.setInt(50, harete.getC50minuto_hospedaje());
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
            pst.setDouble(45, harete.getC46monto_por_hospedaje_minimo());
            pst.setTimestamp(46, evefec.getTimestamp_fecha_cargado(harete.getC47fec_hospedaje_inicio(), "getC47fec_hospedaje_inicio"));
            pst.setTimestamp(47, evefec.getTimestamp_fecha_cargado(harete.getC48fec_hospedaje_fin(), "getC48fec_hospedaje_fin"));
            pst.setBoolean(48, harete.getC49es_hospedaje());
            pst.setInt(49, harete.getC50minuto_hospedaje());
            pst.setInt(50, harete.getC42idhabitacion_dato());
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
                harete.setC46monto_por_hospedaje_minimo(rs.getDouble(47));
                harete.setC47fec_hospedaje_inicio(rs.getString(48));
                harete.setC48fec_hospedaje_fin(rs.getString(49));
                harete.setC49es_hospedaje(rs.getBoolean(50));
                harete.setC50minuto_hospedaje(rs.getInt(51));
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
        String titulo = "actualizar_estado_puerta_cliente_limpieza";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, sensor_puerta_cliente);
            pst.setInt(2, sensor_puerta_limpieza);
            pst.execute();
            pst.close();
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
                + "hs_dormir_salida_final=hc.hs_dormir_salida_final, \n"
                + "minuto_tolerancia=hc.minuto_tolerancia, \n"
                + "monto_por_hospedaje_minimo=hc.monto_por_hospedaje_minimo, \n"
                + "minuto_hospedaje=hc.minuto_hospedaje "
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

    public void update_habitacion_recepcion_temp_salida_final(Connection conn, int idhabitacion_dato) {
        String sql = "update habitacion_recepcion_temp hrt \n"
                + "set hs_dormir_salida_final=hc.hs_dormir_salida_final \n"
                + "from habitacion_costo hc,habitacion_dato hd  \n"
                + "where hd.fk_idhabitacion_costo=hc.idhabitacion_costo \n"
                + "and hd.idhabitacion_dato=hrt.idhabitacion_dato \n"
                + "and hrt.idhabitacion_dato=" + idhabitacion_dato;
        eveconn.SQL_execute_libre(conn, sql);
    }

    public String getSql_ocupacion_boton_resumen() {
        String sql = "select "
                + "case \n"
                + Est_Ocupado+" "+Puerta_1_1+" \n"
                + "	and ("+seg_transcurrido_desde_inicio+"<"+seg_cancelar+") then true\n"
                + "else false \n"
                + "end as cancelar_habitacion,\n"
                + "es_por_dormir as habilitar_dormir,\n"
                + "estado,\n"
                + "tipo_habitacion,\n"
                + "case\n"
                + Est_Libre+"     "+Puerta_1_0+" then true\n"
                + Est_Sucio+"     and puerta_limpieza = false then true\n"
                + Est_Limpiando+" and puerta_limpieza = true  then true\n"
                + "else false\n"
                + "end as cambiar_estado,\n"
                + "case\n"
                + Est_Libre+"     "+Puerta_1_0+" then 'OCUPADO'\n"
                + Est_Sucio+"     and puerta_limpieza = false then 'LIMPIANDO'\n"
                + Est_Limpiando+" and puerta_limpieza = true then 'LIBRE'\n"
                + "else 'sin'\n"
                + "end as est_nuevo,\n"
                + "nro_habitacion,\n"
                + "idhabitacion_dato,\n"
                + "idhabitacion_recepcion_actual,\n"
                + "case\n"
                + Est_Ocupado+"   and (current_timestamp>(date(fec_ocupado_inicio)+2)) "
                + " then to_char("+fec_actual_menos_Inicio+", 'dd "+format_hora+"')\n"
                + Est_Ocupado+"   then to_char("+fec_actual_menos_Inicio+", '"+format_hora+"')\n"
                + Est_Sucio+"     then to_char("+fec_actual_menos_Fin+", '"+format_hora+"')\n"
                + Est_Limpiando+" then to_char("+fec_actual_menos_Fin+", '"+format_hora+"')\n"
                + "else '.'\n"
                + "end as tiempo \n"
                + "from\n"
                + "habitacion_recepcion_temp \n"
                + "where activo=true \n"
                + "order by orden asc;";
        return sql;
    }

    public String getSql_ocupacion_boton() {
        sql_ocupacion_boton = "select\n"
                + "nro_habitacion,\n"
                + "tipo_habitacion,\n"
                + "estado,\n"
                + "minuto_cancelar,\n"
                + "case\n"
                + Est_Libre+"     "+Puerta_1_1+" then ''\n"
                + Est_Libre+"     "+Puerta_0_1+" then '"+Text_Limpiando+"'\n"
                + Est_Libre+"     "+Puerta_1_0+" then '"+Text_CliIngreso+"'\n"
                + Est_Libre+"     "+Puerta_0_0+" then '"+Text_Mante+"'\n"
                + Est_Ocupado+"   "+Puerta_1_1+"  and ("+seg_transcurrido_desde_inicio+">"+seg_cancelar+") then ''\n"
                + Est_Ocupado+"   "+Puerta_1_1+"  and ("+seg_transcurrido_desde_inicio+"<"+seg_cancelar+") then '"+Text_Cancelar+"'\n"
                + Est_Ocupado+"   "+Puerta_0_1+" then '"+Text_LimAbierto+"'\n"
                + Est_Ocupado+"   "+Puerta_1_0+" then '"+Text_CliAbierto+"'\n"
                + Est_Ocupado+"   "+Puerta_0_0+" then '"+Text_Mante+"'\n"
                + Est_Sucio+"     "+Puerta_1_1+" then '"+Text_Sucio+"'\n"
                + Est_Sucio+"     "+Puerta_0_1+" then '"+Text_Limpiando+"'\n"
                + Est_Sucio+"     "+Puerta_1_0+" then '"+Text_CliAbierto+"'\n"
                + Est_Sucio+"     "+Puerta_0_0+" then '"+Text_Mante+"'\n"
                + Est_Limpiando+" "+Puerta_1_1+" then ''\n"
                + Est_Limpiando+" "+Puerta_0_1+" then '"+Text_Limpiando+"'\n"
                + Est_Limpiando+" "+Puerta_1_0+" then '"+Text_CliIngreso+"'\n"
                + Est_Limpiando+" "+Puerta_0_0+" then '"+Text_Mante+"'\n"
                + "else '.'\n"
                + "end as desc_estado,\n"
                + "case\n"
                + Est_Ocupado+"   and (current_timestamp>(date(fec_ocupado_inicio)+2))  then to_char("+fec_actual_menos_Inicio+", 'dd "+format_hora+"')\n"
                + Est_Ocupado+"   then to_char("+fec_actual_menos_Inicio+", '"+format_hora+"')\n"
                + Est_Sucio+"     then to_char("+fec_actual_menos_Fin+", '"+format_hora+"')\n"
                + Est_Limpiando+" then to_char("+fec_actual_menos_Fin+", '"+format_hora+"')\n"
                + "else '.'\n"
                + "end as tiempo,\n"
                + "case\n"
                + Est_Libre+" then to_char("+fec_actual_menos_Inicio+", 'yyyy-MM-dd "+format_hora+"')\n"
                + "else '.'\n"
                + "end as fec_ingreso,\n"
                + "es_por_dormir as habilitar_dormir,\n"
                + "es_por_hora as habilitar_hora,\n"
                + "case\n"
                + es_Por_Hora_1
                + "  then to_char(("+monto_porhora_minimo_Double+" + ((cast((("+seg_transcurrido_desde_inicio+")/3600) as integer))* monto_por_hora_adicional)), '999G999G999')\n"
                + es_Por_Hora_2
                + "  then to_char(("+monto_porhora_minimo_Double+"), '999G999G999')\n"
                + es_Por_Hora_3
                + "  then to_char((monto_por_hora_adicional + "+monto_porhora_minimo_Double+"), '999G999G999')\n"
                + es_Por_Hora_4
                + "  then to_char((monto_por_hora_adicional + "+monto_porhora_minimo_Double+" + "
                + "     (cast(to_char((current_timestamp)-(fec_ocupado_inicio + (minuto_minimo || ' minutes')::interval), 'HH24') as integer)* monto_por_hora_adicional)), '999G999G999')\n"
                + "else '.'\n"
                + "end as tarifa_gral_hora,\n"
                + "case\n"
                + es_Por_Dormir_1
                + "  then to_char(("+monto_dormir_minimo_Double+" + (("+hora_actual_Dormir_final_mas1+")* monto_por_dormir_adicional)), '999G999G999')\n"
                + es_Por_Dormir_2
                + "  then to_char("+monto_dormir_minimo_Double+", '999G999G999')\n"
                + es_Por_Dormir_3
                + "  then to_char("+monto_dormir_minimo_Double+", '999G999G999')\n"
                + es_Por_Dormir_6
                + "  then to_char(("+monto_dormir_minimo_Double+" + ("+hora_actual_Dormir_final_mas1+" * monto_por_dormir_adicional)) , '999G999G999')\n"
                + es_Por_Dormir_7
                + "  then to_char(("+monto_dormir_minimo_Double+" + ("+hora_actual_Dormir_final+" * monto_por_dormir_adicional)), '999G999G999')\n"
                + es_Por_Hora_Dormir_1
                + "  then to_char("+monto_dormir_minimo_Double+", '999G999G999')\n"
                + es_Por_Hora_Dormir_2
                + "  then to_char("+monto_dormir_minimo_Double+", '999G999G999')\n"
                + es_Por_Hora_Dormir_7
                + "  then to_char(("+monto_dormir_minimo_Double+" + ("+hora_actual_Dormir_final_mas1+" * monto_por_dormir_adicional)) , '999G999G999')\n"
                + es_Por_Hora_Dormir_4
                + "  then to_char(("+monto_dormir_minimo_Double+" + ("+hora_actual_Dormir_final+" * monto_por_dormir_adicional)), '999G999G999')\n"
                + "else '.'\n"
                + "end as tarifa_gral_dormir,\n"
                + "case\n"
                + "when "+fec_actual_mayor_Dormir_inicio + " and "+fec_actual_menor_23hs+ " and "+fec_actual_mayor_00hs+ " and (current_timestamp<"+fec_actual_Dormir_final_mas1+")\n"
                + "  then true\n"
                + "when "+fec_actual_mayor_00hs + " and "+fec_actual_menor_Dormir_final+ " and (current_timestamp<"+fec_actual_Dormir_final+")\n"
                + "  then true\n"
                + "when "+fec_actual_mayor_Dormir_inicio + " and "+fec_actual_menor_23hs+ " and (current_timestamp>"+fec_actual_Dormir_final_mas1+")\n"
                + "  then true\n"
                + "when "+fec_actual_mayor_00hs + " and "+fec_actual_menor_Dormir_final + " and (current_timestamp>"+fec_actual_Dormir_final+")\n"
                + "  then true\n"
                + "else false\n"
                + "end as permitir_dormir,\n"
                + "case\n"
                + Est_Libre+"     "+Puerta_1_0+" then true\n"
                + Est_Sucio+"     and puerta_limpieza = false then true\n"
                + Est_Limpiando+" and puerta_limpieza = true  then true\n"
                + "else false\n"
                + "end as cambiar_estado,\n"
                + "case\n"
                + Est_Libre+"     "+Puerta_1_0+" then 'OCUPADO'\n"
                + Est_Sucio+"     and puerta_limpieza = false then 'LIMPIANDO'\n"
                + Est_Limpiando+" and puerta_limpieza = true then 'LIBRE'\n"
                + "else 'sin'\n"
                + "end as est_nuevo,\n"
                + "idhabitacion_recepcion_actual as ult_recepcion,\n"
                + "('NO') as ruta_sonido,\n"
                + ""+seg_transcurrido_desde_inicio+" as ocupado_inicio_seg,\n"
                + "case\n"
                + "when puerta_limpieza = true then 1\n"
                + "else 0\n"
                + "end as puerta_lim,\n"
                + "case\n"
                + "when puerta_cliente = true then 1\n"
                + "else 0\n"
                + "end as puerta_ocu,\n"
                + "to_char((fec_ocupado_inicio), 'yyyy-MM-dd') as fecha_ingreso,\n"
                + "to_char((fec_ocupado_inicio), '"+format_hora+"') as hora_ingreso,\n"
                + "minuto_minimo,\n"
                + "case\n"
                + es_Por_Hora_1
                + "  then (cast((("+seg_transcurrido_desde_inicio+")/ 3600) as integer))         \n"
                + es_Por_Hora_Dormir_6
                + "  then ("+hora_actual_Dormir_final_mas1+")\n"
                + es_Por_Hora_3
                + "  then 1 \n"
                + es_Por_Hora_4
                + "  then FLOOR(("+hora_transcurrido_desde_inicio_minimo+"+1)\n"
                + es_Por_Dormir_6
                + "  then ("+hora_actual_Dormir_final_mas1+")\n"
                + es_Por_Dormir_7
                + "  then ("+hora_actual_Dormir_final+")\n"
                + es_Por_Hora_Dormir_5
                + "  then ("+hora_actual_Dormir_final_mas1+")\n"
                + es_Por_Hora_Dormir_4
                + "  then ("+hora_actual_Dormir_final+")\n"
                + "else 0\n"
                + "end as cant_add_tarifa_hora,\n"
                + "idhabitacion_dato,\n"
                + "idhabitacion_recepcion_actual,\n"
                + "monto_adelanto, \n"
                + "case \n"
                + Est_Ocupado+" "+Puerta_1_1+ "	and ("+seg_transcurrido_desde_inicio+"<"+seg_cancelar+") then true\n"
                + "  else false \n"
                + "end as cancelar_habitacion,\n"
                + "case \n"
                + "when ("+seg_cancelar+"-"+seg_transcurrido_desde_inicio+")>0\n"
                + "  then to_char(((("+seg_cancelar+"-"+seg_transcurrido_desde_inicio+")*100)/"+seg_cancelar+"),'99D99%')\n"
                + "else '0%' \n"
                + "end as por_cancelar,\n"
                + "TRIM(to_char(monto_por_hora_minimo,'999G999G999')) as  monto_por_hora_minimo,\n"
                + "case\n"
                + Est_Ocupado + " and ("+seg_inicio_a_Dormir_inicio+"<"+seg_minimo+")\n"
                + "  then (0)\n"
                + Est_Ocupado + " and ("+seg_inicio_a_Dormir_inicio+">"+seg_minimo+") and ("+seg_inicio_a_Dormir_inicio+"<"+seg_minimo_mas_adicional+")\n"
                + "  then (1)\n"
                + Est_Ocupado + " and ("+seg_inicio_a_Dormir_inicio+">"+seg_minimo_mas_adicional+")\n"
                + "  then "+hora_Dormir_minimo+"\n"
                + "else 0\n"
                + "end as cant_hasta_dormir,\n"
                + "case \n"
                + Est_Ocupado + " and ("+seg_inicio_a_Dormir_inicio+">(0)) \n"
                + "  then to_char(((date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio) - fec_ocupado_inicio),'"+format_hora+"')\n"
                + "else '00:00' \n"
                + "end as hs_hasta_dormir, \n"
                + "case  \n"
                + "when (date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio)>fec_ocupado_inicio and (date(fec_ocupado_inicio) + hs_dormir_salida_final)<fec_ocupado_inicio\n"
                + "  then false else true \n"
                + "end as es_hora_dormir \n"
                + "from\n"
                + "habitacion_recepcion_temp \n"
                + "where activo=true \n"
                + "order by orden asc;";
        return sql_ocupacion_boton;
    }

    public String getSql_ocupacion_boton_FUERTE() {
        
        sql_ocupacion_boton_FUERTE = "update  habitacion_recepcion_temp set monto_gral=\n"
                + "case\n"
                + es_Por_Hora_1
                + "  then ("+monto_porhora_minimo_Double+" +  (FLOOR((((("+seg_transcurrido_desde_inicio+"))/60)/minuto_adicional)) * monto_por_hora_adicional))\n"
                + es_Por_Hora_2
                + "  then ("+monto_porhora_minimo_Double+")\n"
                + es_Por_Hora_3
                + "  then (monto_por_hora_adicional + "+monto_porhora_minimo_Double+")\n"
                + es_Por_Hora_4
                + "  then (monto_por_hora_adicional + "+monto_porhora_minimo_Double+" + (FLOOR("+hora_transcurrido_desde_inicio_minimo+" * monto_por_hora_adicional))\n"
                + es_Por_Dormir_1
                + "  then ("+monto_dormir_minimo_Double+" +  ("+seg_transcurrido_Dormir_Final_mas1+" * monto_por_hora_adicional))\n"
                + es_Por_Dormir_2
                + "  then "+monto_dormir_minimo_Double+"\n"
                + es_Por_Dormir_3
                + "  then "+monto_dormir_minimo_Double+"\n"
                + es_Por_Dormir_4 //
                + "  then (monto_por_dormir_adicional+"+monto_dormir_minimo_Double+" +  ("+seg_transcurrido_Dormir_Final_mas1+"* monto_por_dormir_adicional))\n"
                + es_Por_Dormir_5
                + "  then (monto_por_dormir_adicional+"+monto_dormir_minimo_Double+" +  ("+seg_transcurrido_Dormir_Final+"* monto_por_dormir_adicional))\n"
                + es_Por_Dormir_6
                + "  then "+monto_dormir_minimo_Double+"\n"
                + es_Por_Dormir_7
                + "  then "+monto_dormir_minimo_Double+"\n"
                + es_Por_Hora_Dormir_1
                + "  then "+monto_dormir_minimo_Double+"\n"
                + es_Por_Hora_Dormir_2
                + "  then "+monto_dormir_minimo_Double+"\n"
                + es_Por_Hora_Dormir_3
                + "  then (monto_por_dormir_adicional+"+monto_dormir_minimo_Double+" +  ("+seg_transcurrido_Dormir_Final_mas1+" * monto_por_dormir_adicional)) \n"
                + es_Por_Hora_Dormir_4
                + "  then (monto_por_dormir_adicional+"+monto_dormir_minimo_Double+" +  ("+seg_transcurrido_Dormir_Final+" * monto_por_dormir_adicional))\n"
                + es_Por_Hora_Dormir_7
                + "  then ("+monto_dormir_minimo_Double+" + ("+hora_actual_Dormir_final_mas1+" * monto_por_dormir_adicional))\n"
                + es_Por_hospedaje_1
                + "  then ("+monto_hospedaje_minimo_Double+")\n"
                + es_Por_hospedaje_2
                + "  then (monto_por_hora_adicional + "+monto_hospedaje_minimo_Double+" + ("+hora_transcurrido_desde_inicio_hospedaje+" * monto_por_hora_adicional))\n"
                + "else 0\n"
                + "end,"
                + "cant_hora_adicional=\n"
                + "case\n"
                + es_Por_Hora_1
                + "  then FLOOR(((("+seg_transcurrido_desde_inicio+")/60)/minuto_adicional))\n"
                + es_Por_Hora_2
                + "  then 0\n"
                + es_Por_Hora_3
                + "  then 1\n"
                + es_Por_Hora_4
                + "  then FLOOR("+hora_transcurrido_desde_inicio_minimo+" + 1\n"
                + es_Por_Dormir_1
                + "  then "+seg_transcurrido_Dormir_Final_mas1+"\n"
                + es_Por_Dormir_2
                + "  then 0\n"
                + es_Por_Dormir_3
                + "  then 0\n"
                + es_Por_Dormir_4 
                + "  then "+seg_transcurrido_Dormir_Final_mas1+"+1\n"
                + es_Por_Dormir_5
                + "  then "+seg_transcurrido_Dormir_Final+"+1\n"
                + es_Por_Dormir_6
                + "  then 0\n"
                + es_Por_Dormir_7
                + "  then 0\n"
                + es_Por_Hora_Dormir_1
                + "  then 0\n"
                + es_Por_Hora_Dormir_2
                + "  then 0\n"
                + es_Por_Hora_Dormir_3
                + "  then "+seg_transcurrido_Dormir_Final_mas1+"+1\n"
                + es_Por_Hora_Dormir_4
                + "  then "+seg_transcurrido_Dormir_Final+"+1\n"
                + es_Por_hospedaje_1
                + "  then 0\n"
                + es_Por_hospedaje_2
                + "  then (1+("+hora_transcurrido_desde_inicio_hospedaje+"))\n"
                + "else 0\n"
                + "end,"
                + "descripcion_habitacion=\n"
                + "case\n"
                + Por_Hora_1
                + "  then 'Por_Hora_1'\n"
                + Por_Hora_2
                + "  then 'Por_Hora_2'\n"
                + Por_Hora_3
                + "  then 'Por_Hora_3'\n"
                + Por_Hora_4
                + "  then 'Por_Hora_4'\n"
                + Por_Dormir_1
                + "  then 'Por_Dormir_1'\n"
                + Por_Dormir_2
                + "  then 'Por_Dormir_2'\n"
                + Por_Dormir_3
                + "  then 'Por_Dormir_3'\n"
                + Por_Dormir_4 //
                + "  then 'Por_Dormir_4'\n"
                + Por_Dormir_5
                + "  then 'Por_Dormir_5'\n"
                + Por_Dormir_6
                + "  then 'Por_Dormir_6'\n"
                + Por_Dormir_7
                + "  then 'Por_Dormir_7'\n"
                + Por_Hora_Dormir_1
                + "  then 'Por_Hora_Dormir_1'\n"
                + Por_Hora_Dormir_2
                + "  then 'Por_Hora_Dormir_2'\n"
                + Por_Hora_Dormir_3
                + "  then 'Por_Hora_Dormir_3'\n"
                + Por_Hora_Dormir_4
                + "  then 'Por_Hora_Dormir_4'\n"
                + Por_Hora_Dormir_5
                + "  then 'Por_Hora_Dormir_5'\n"
                + Por_Hora_Dormir_6
                + "  then 'Por_Hora_Dormir_6'\n"
                + Por_Hora_Dormir_7
                + "  then 'Por_Hora_Dormir_7'\n"
                + Por_hospedaje_1
                + "  then 'Por_hospedaje_1'\n"
                + Por_hospedaje_2
                + "  then 'Por_hospedaje_2'\n"
                + "else 'ninguno'\n"
                + "end;";
        return sql_ocupacion_boton_FUERTE;
    }

    public String getSql_ocupacion_boton_LIBIANO() {
        sql_ocupacion_boton_LIBIANO = "update  habitacion_recepcion_temp "
                + "set tiempo_estado="
                + "case\n"
                + Est_Ocupado+"   and (("+seg_transcurrido_desde_inicio+"/60)>1440)  then to_char("+fec_actual_menos_Inicio+", 'dd "+format_hora+"')\n"
                + Est_Ocupado+"   then to_char("+fec_actual_menos_Inicio+", '"+format_hora+"')\n"
                + Est_Sucio+"     then to_char("+fec_actual_menos_Fin+", '"+format_hora+"')\n"
                + Est_Limpiando+" then to_char("+fec_actual_menos_Fin+", '"+format_hora+"')\n"
                + "else '.'\n"
                + "end,"
                + "ruta_icono=\n"
                + "case "
                + Est_Libre+"       "+Puerta_1_1+" then '/graficos/libre.png'\n"
                + Est_Libre+"       "+Puerta_0_0+" then '/graficos/48_mante.png'\n"
                + Est_Sucio+"       then '/graficos/limpieza.png'\n"
                + Est_Limpiando+"   then '/graficos/escoba.png'\n"
                + Est_Ocu_POR_HORA+"            "+Puerta_1_1+" then '/iconos/motel/48_reloj.png'\n"
                + Est_Ocu_POR_DORMIR+"          "+Puerta_1_1+" then '/iconos/motel/48_dormir.png'\n"
                + Est_Ocu_POR_HORA_MAS_DORMIR+" "+Puerta_1_1+" then '/iconos/motel/48_dormir.png'\n"
                + Est_Ocupado+"     and es_hospedaje = true  "+Puerta_1_1+" then '/graficos/48_maleta.png'\n"
                + Est_Ocupado+"     and puerta_limpieza = true and puerta_cliente = false then '/graficos/48_puerta.png'\n"
                + "else 'no' end,"
                + "descrip_estado=\n"
                + "case\n"
                + Est_Libre+"     "+Puerta_1_1+" then estado||''\n"
                + Est_Libre+"     "+Puerta_0_1+" then estado||'"+Text_Limpiando+"'\n"
                + Est_Libre+"     "+Puerta_1_0+" then estado||'"+Text_CliIngreso+"'\n"
                + Est_Libre+"     "+Puerta_0_0+" then estado||'"+Text_Mante+"'\n"
                + Est_Ocupado+"   "+Puerta_1_1+"  and ("+seg_transcurrido_desde_inicio+">"+seg_cancelar+") then estado||''\n"
                + Est_Ocupado+"   "+Puerta_1_1+"  and ("+seg_transcurrido_desde_inicio+"<"+seg_cancelar+") then estado||'"+Text_Cancelar+"'\n"
                + Est_Ocupado+"   "+Puerta_0_1+" then estado||'"+Text_LimAbierto+"'\n"
                + Est_Ocupado+"   "+Puerta_1_0+" then estado||'"+Text_CliAbierto+"'\n"
                + Est_Ocupado+"   "+Puerta_0_0+" then estado||'"+Text_Mante+"'\n"
                + Est_Sucio+"     "+Puerta_1_1+" then estado||'"+Text_Sucio+"'\n"
                + Est_Sucio+"     "+Puerta_0_1+" then estado||'"+Text_Limpiando+"'\n"
                + Est_Sucio+"     "+Puerta_1_0+" then estado||'"+Text_CliAbierto+"'\n"
                + Est_Sucio+"     "+Puerta_0_0+" then estado||'"+Text_Mante+"'\n"
                + Est_Limpiando+" "+Puerta_1_1+" then estado||''\n"
                + Est_Limpiando+" "+Puerta_0_1+" then estado||'"+Text_Limpiando+"'\n"
                + Est_Limpiando+" "+Puerta_1_0+" then estado||'"+Text_CliIngreso+"'\n"
                + Est_Limpiando+" "+Puerta_0_0+" then estado||'"+Text_Mante+"'\n"
                + "else '.'\n"
                + "end,"
                + "color_fondo="
                + "case "
                + Est_Libre+"     "+Puerta_0_0+" then '#DFD3C3'\n"
                + "when (descripcion_habitacion='Por_Dormir_6' or descripcion_habitacion='Por_Dormir_7' or descripcion_habitacion='Por_Hora_Dormir_7') "//'Por_Hora_Dormir_7'
                + "     then (case when MOD((cast( "+seg_transcurrido_desde_inicio+" as integer)/5),2)=0 then '#F11A7B' else '#FCF5ED' end)"//#DFD3C3
                + Est_Ocupado+"   and cant_hora_adicional>0 then '#FFCCFF'\n"
                + Est_Ocupado+"   "+Puerta_1_0+" then '#FF003F'\n"
                + Est_Ocupado+"   and es_por_dormir = true  "+Puerta_1_1+" and monto_adelanto=0 then '#F0EDD4'\n"
                + Est_Ocupado+"   and es_por_dormir = true  "+Puerta_1_1+" and monto_adelanto>0 then '#FEFF86'\n"
                + Est_Ocupado+"   and es_por_dormir = false and es_por_hora = true  "+Puerta_1_1+"  and ("+seg_transcurrido_desde_inicio+"<"+seg_cancelar+")\n"
                + "     then (case when MOD((cast("+seg_transcurrido_desde_inicio+" as integer)/5),2)=0 then '#A85CF9' else '#6FDFDF' end)"
                + Est_Ocupado+"   and es_hospedaje = true  "+Puerta_1_1+" and ("+seg_transcurrido_desde_inicio+">("+seg_hospedaje+"-(minuto_tolerancia*60)))\n"
                + "     then (case when MOD((cast( "+seg_transcurrido_desde_inicio+" as integer)/5),2)=0 then '#F11A7B' else '#FCF5ED' end)"//#DFD3C3
                + Est_Libre+" and es_manual=true then '#B9F3FC' \n"
                + "     else '#F0F0F0' end,"
                + "color_texto="
                + "case "
                + Est_Libre+" then '#006532' \n"
                + Est_Ocupado+" "+Puerta_1_0+" then '#000000' \n"
                + Est_Ocupado+" "+Puerta_1_1+" then '#FF0000' \n"
                + Est_Sucio+" then '#CB9800' \n"
                + "     else '#000000' end;";
        return sql_ocupacion_boton_LIBIANO;
    }

    public String getSql_ocupacion_boton_CARGAR(int idhabitacion_dato) {
        return sql_ocupacion_boton_CARGAR = "select estado,nro_habitacion,idhabitacion_recepcion_actual,tipo_habitacion,"
                + "es_por_dormir,es_por_hora,es_hospedaje, "
                + "case\n"
                + Est_Ocupado + " and ("+seg_inicio_a_Dormir_inicio+"<"+seg_minimo+")\n"
                + "  then (0)\n"
                + Est_Ocupado + " and ("+seg_inicio_a_Dormir_inicio+">"+seg_minimo+") and ("+seg_inicio_a_Dormir_inicio+"<"+seg_minimo_mas_adicional+")\n"
                + "  then (1)\n"
                + Est_Ocupado + " and ("+seg_inicio_a_Dormir_inicio+">"+seg_minimo_mas_adicional+")\n"
                + "  then "+hora_Dormir_minimo+"\n"
                + "else 0\n"
                + "end as cant_hasta_dormir, \n"
                + "case \n"
                + Est_Ocupado + " and ("+seg_inicio_a_Dormir_inicio+">(0))\n"
                + "  then to_char(((date(fec_ocupado_inicio) + hs_dormir_ingreso_inicio) - fec_ocupado_inicio),'"+format_hora+"') \n"
                + "else '00:00' \n"
                + "end as hs_hasta_dormir,\n"
                + "cant_hora_adicional,"
                + "case\n"
                + "when "+fec_actual_mayor_Dormir_inicio + " and "+fec_actual_menor_23hs+ " and "+fec_actual_mayor_00hs+ " and (current_timestamp<"+fec_actual_Dormir_final_mas1+")\n"
                + "  then true\n"
                + "when "+fec_actual_mayor_00hs+ " and "+fec_actual_menor_Dormir_final+ " and (current_timestamp<"+fec_actual_Dormir_final+")\n"
                + "  then true\n"
                + "when "+fec_actual_mayor_Dormir_inicio+ " and "+fec_actual_menor_23hs+ " and (current_timestamp>"+fec_actual_Dormir_final_mas1+")\n"
                + "  then true\n"
                + "when "+fec_actual_mayor_00hs+ " and "+fec_actual_menor_Dormir_final+ " and (current_timestamp>"+fec_actual_Dormir_final+")\n"
                + "  then true\n"
                + "else false\n"
                + "end as permitir_dormir, \n"
                + "tiempo_estado,\n "
                + "to_char((fec_ocupado_inicio), 'yyyy-MM-dd') as fecha_ingreso,\n"
                + "to_char((fec_ocupado_inicio), '"+format_hora+"') as hora_ingreso, \n"
                + "case \n"
                + Est_Ocupado+" "+Puerta_1_1+ " and ("+seg_transcurrido_desde_inicio+"<"+seg_cancelar+")\n"
                + "  then true\n"
                + "else false \n"
                + "end as cancelar_habitacion, \n"
                + "case \n"
                + "when ("+seg_cancelar+"-"+seg_transcurrido_desde_inicio+")>0 \n"
                + "  then to_char(((("+seg_cancelar+"-"+seg_transcurrido_desde_inicio+")*100)/"+seg_cancelar+"),'99D99%') \n"
                + "else '0%' \n"
                + "end as por_cancelar, \n"
                + "("+seg_transcurrido_desde_inicio+") as ocupado_inicio_seg,\n"
                + "minuto_minimo,monto_por_hora_minimo \n"
                + "from habitacion_recepcion_temp "
                + "where activo=true and idhabitacion_dato=" + idhabitacion_dato;
    }

    public void update_habitacion_recepcion_temp_es_manual(Connection conn, boolean manual, int idhabitacion_dato) {
        String sql = "update habitacion_dato \n"
                + "set es_manual=" + manual
                + " where  idhabitacion_dato=" + idhabitacion_dato + ";";
        eveconn.SQL_execute_libre(conn, sql);
        String sql1 = "update habitacion_recepcion_temp \n"
                + "set es_manual=" + manual
                + " where  idhabitacion_dato=" + idhabitacion_dato + ";";
        eveconn.SQL_execute_libre(conn, sql1);
    }
}
