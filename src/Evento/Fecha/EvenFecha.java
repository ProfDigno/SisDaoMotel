/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Fecha;

import java.awt.Color;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.sql.Date;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.SimpleTimeZone;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class EvenFecha {

    String formato_fecha = "yyyy-MM-dd";
    String fecha_dia1 = "yyyy-MM-01";
    String formato_fechaHora = "yyyy-MM-dd HH:mm:ss";
    private String formato_hora_min_seg = "HH:mm:ss";
    private String formato_hora_min = "HH:mm";

    public String getString_validar_fecha(String fechaStr) {
        String Sfecha = "";
        try {
            SimpleDateFormat formato = new SimpleDateFormat(formato_fecha);
            java.util.Date fechaDate = formato.parse(fechaStr);
            Sfecha = String.valueOf(formato.format(fechaDate));
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: AñO-MES-DIA\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            Sfecha = getString_formato_fecha();
        }
        return Sfecha;
    }

    public String getString_validar_fecha_hora(String fechaStr) {
        String Sfecha = "";
        try {
            SimpleDateFormat formato = new SimpleDateFormat(formato_fechaHora);
            java.util.Date fechaDate = formato.parse(fechaStr);
            Sfecha = String.valueOf(formato.format(fechaDate));
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: AñO-MES-DIA HORA:MINUTO\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            Sfecha = getString_formato_fecha_hora();
        }
        return Sfecha;
    }

    public java.sql.Date getDate_fecha_cargado(String fechaStr) {
        java.sql.Date dateSql = null;
        java.util.Date dateUtil = new java.util.Date();
        try {
            SimpleDateFormat formato = new SimpleDateFormat(formato_fecha);
            dateUtil = formato.parse(fechaStr);
            dateSql = new java.sql.Date(dateUtil.getTime());
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: AñO-MES-DIA\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);

        }
        return dateSql;
    }
    public java.sql.Date getDate_fecha_hora_cargado(String fechaStr) {
        java.sql.Date dateSql = null;
        java.util.Date dateUtil = new java.util.Date();
        try {
            SimpleDateFormat formato = new SimpleDateFormat(formato_fechaHora);
            dateUtil = formato.parse(fechaStr);
            dateSql = new java.sql.Date(dateUtil.getTime());
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: AñO-MES-DIA\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);

        }
        return dateSql;
    }
    public java.sql.Timestamp getTimestamp_fecha_cargado(String fechaStr,String origen) {
        java.sql.Timestamp dateSql = null;
        java.util.Date dateUtil = new java.util.Date();
        try {
            SimpleDateFormat formato = new SimpleDateFormat(formato_fechaHora);
            dateUtil = formato.parse(fechaStr);
            dateSql = new java.sql.Timestamp(dateUtil.getTime());
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: AñO-MES-DIA HORA:MINUTO\n" + e
                    + "\nFormato Ingresado:" + fechaStr
                    + "\nORIGEN:" + origen
                    + "\nFormato requerido:" + formato_fechaHora;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            dateSql = getTimestamp_sistema();
        }
        return dateSql;
    }

    public java.sql.Time getTime_sistema() {
        java.sql.Time time = new java.sql.Time(0L);
        time.setTime(new java.util.Date().getTime());
        return time;
    }

    public java.sql.Time getTime_sistema_cargado(String timeStr) {
        java.sql.Time sqlTime1 = null;
        try {
            sqlTime1 = java.sql.Time.valueOf(timeStr);
//            System.out.println("SqlTime1: " + sqlTime1);
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA HORA NO ES CORRECTA\n FORMATO: HH:mm:ss \n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            sqlTime1 = getTime_sistema();
        }
        return sqlTime1;
    }

    public boolean getboolean_time_correcto(JFormattedTextField txtcampo) {
        boolean corrento = false;
        java.sql.Time sqlTime1 = null;
        try {
            String timeStr = txtcampo.getText();
            sqlTime1 = java.sql.Time.valueOf(timeStr);
            System.out.println("SqlTime1: " + sqlTime1);
            txtcampo.setBackground(Color.WHITE);
            corrento = false;
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA HORA NO ES CORRECTA\n FORMATO: HH:mm:ss \n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            txtcampo.setBackground(Color.ORANGE);
            txtcampo.grabFocus();
            corrento = true;
        }
        return corrento;
    }

    public String getString_formato_fecha() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formato_fecha);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public String getString_formato_hora() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formato_hora_min);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public int getInt_segundos_ahora() {
        java.util.Date utilDate = new java.util.Date(); //fecha actual
        SimpleDateFormat sdf_hora = new SimpleDateFormat("HH");
        int hora = Integer.parseInt(sdf_hora.format(utilDate));
        SimpleDateFormat sdf_min = new SimpleDateFormat("mm");
        int min = Integer.parseInt(sdf_min.format(utilDate));
        SimpleDateFormat sdf_seg = new SimpleDateFormat("ss");
        int seg = Integer.parseInt(sdf_seg.format(utilDate));
        int resul = ((hora * 3600) + (min * 60) + seg);
        return resul;
    }

    public int getInt_diferencia_en_segundo(int tiempo_sql) {
        int resul = getInt_segundos_ahora() - tiempo_sql;
        return resul;
    }

    public String getString_convertir_segundo_hora(int num) {
        int hor, min, seg;
        hor = num / 3600;
        min = (num - (3600 * hor)) / 60;
        seg = num - ((hor * 3600) + (min * 60));
        String horaformada = hor + "h " + min + "m " + seg + "s";
//        System.out.println(horaformada);
        return horaformada;
    }

    public String getString_fecha_dia1() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(fecha_dia1);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public String getString_formato_fecha_hora() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formato_fechaHora);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public String getString_formato_hora_min_seg() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formato_hora_min_seg);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public java.util.Date getDate_sistema() {
        java.util.Date fechaDate;
        try {
            fechaDate = new java.util.Date();
            return fechaDate;
        } catch (Exception e) {
            System.out.println("getDate_sistema:" + e);
            return null;
        }
    }

    public java.sql.Date getDateSQL_sistema() {
        java.sql.Date fechaDate;
        try {
            fechaDate = new java.sql.Date(new java.util.Date().getTime());
            return fechaDate;
        } catch (Exception e) {
            System.out.println("" + e);
            return null;
        }
    }

    public Timestamp getTimestamp_sistema() {
        long timeNow = Calendar.getInstance().getTimeInMillis();
        java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
        return ts;
    }

    public void cargar_combobox_intervalo_fecha(JComboBox combo) {
        combo.removeAllItems();
        String fechas[] = {"HOY", "AYER",
            "ESTA SEMANA", "SEMANA  ANTERIOR",
            "ESTE MES", "MES ANTERIOR",
            "PRIMER TRIMESTRE", "SEGUNDO TRIMESTRE", "TERCER TRIMESTRE", "CUARTO TRIMESTRE", "TODO EL AÑO"};
        for (int i = 0; i < fechas.length; i++) {
            String fecha = fechas[i];
            combo.addItem(fecha);
        }
        combo.setSelectedIndex(4);
    }

    public String getIntervalo_fecha_combobox(JComboBox combo, String campofecha) {
        //date_part('year',(current_date - interval '1 year')) un ano menos

        String fecha = "";
        if (combo.getSelectedIndex() == 0) {//HOY
            fecha = "\n and date(" + campofecha + ")=date(current_date) ";
        }
        if (combo.getSelectedIndex() == 1) {//AYER
            fecha = "\n and date(" + campofecha + ")=date(current_date-1) ";
        }
        if (combo.getSelectedIndex() == 2) {//ESTA SEMANA
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('week'," + campofecha + ")=date_part('week',current_date) ";
        }
        if (combo.getSelectedIndex() == 3) {//SEMANA  ANTERIOR
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('week'," + campofecha + ")=date_part('week',(current_date - interval '1 week')) ";
        }
        if (combo.getSelectedIndex() == 4) {//ESTE MES
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('month'," + campofecha + ")=date_part('month',current_date) ";
        }
        if (combo.getSelectedIndex() == 5) {//MES ANTERIOR
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('month'," + campofecha + ")=date_part('month',(current_date - interval '1 month')) ";
        }
        if (combo.getSelectedIndex() == 6) {//PRIMER TRIMESTRE
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('quarter'," + campofecha + ")=1 ";
        }
        if (combo.getSelectedIndex() == 7) {//SEGUNDO TRIMESTRE
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('quarter'," + campofecha + ")=2 ";
        }
        if (combo.getSelectedIndex() == 8) {//TERCER TRIMESTRE
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('quarter'," + campofecha + ")=3 ";
        }
        if (combo.getSelectedIndex() == 9) {//CUARTO TRIMESTRE
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('quarter'," + campofecha + ")=4 ";
        }
        if (combo.getSelectedIndex() == 10) {//todo el año
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)";
        }
        return fecha;
    }

    public void cargar_combobox_mes(JComboBox combo) {
        String fechas[] = {"TODOS", "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
        for (int i = 0; i < fechas.length; i++) {
            String fecha = fechas[i];
            combo.addItem(fecha);
        }
        combo.setSelectedIndex(esteMes());
    }

    public String getmes_combobox(JComboBox combo, String campofecha) {
        //date_part('year',(current_date - interval '1 year')) un ano menos

        String fecha = "";
        if (combo.getSelectedIndex() > 0) {//HOY
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('month'," + campofecha + ")=" + combo.getSelectedIndex();
        }
        return fecha;
    }

    int esteMes() {
        java.util.Date date = new java.util.Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month + 1;
    }

    public String getString_tiempo_transcurrido(String fecha_hora_ingreso) {
        String tiempo_transcurrido = "";
        try {
            long lantes = getDate_fecha_hora_cargado(fecha_hora_ingreso).getTime();
            long lahora = getDate_sistema().getTime();
            long diferencia = (lahora - lantes);
            long diferenciaseg=diferencia/(1000);
//            System.out.println("lantes:"+lantes+"\tlahora:"+lahora+"\tdiferencia:"+diferenciaseg);
            tiempo_transcurrido=getString_convertir_segundo_hora((int)diferenciaseg);
        } catch (Exception e) {
            System.out.println("getString_tiempo_transcurrido:" + e);
            tiempo_transcurrido="error:"+e;
        }
        return tiempo_transcurrido;
    }
    public String getString_turno() {
        String Sturno="";
        java.util.Date date = new java.util.Date();
        int hora=date.getHours();
        int minuto=date.getMinutes();
        int hs_min=(hora*60)+minuto;
        int t1_1=(6*60+1);
        int t1_2=(14*60);
        int t2_1=(14*60+1);
        int t2_2=(22*60);
        int t3_1=(22*60+1);
        int t3_2=(23*60+59);
        int t4_1=1;
        int t4_2=(6*60);
        if(hs_min>t1_1 && hs_min<t1_2){
            Sturno="Turno: MAÑANA";
        }
        if(hs_min>t2_1 && hs_min<t2_2){
            Sturno="Turno: TARDE";
        }
        if(hs_min>t3_1 && hs_min<t3_2){
            Sturno="Turno: NOCHE";
        }
        if(hs_min>t4_1 && hs_min<t4_2){
            Sturno="Turno: MADRUGADA";
        }
//        Sturno="hs_min:"+hs_min;
        return Sturno;
    }
}
