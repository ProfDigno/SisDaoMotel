/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config_JSON;

import CONFIGURACION.ComputerInfo;
import Evento.Fecha.EvenFecha;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Digno
 */
public class json_array_formulario {

    private String ruta_json = "JSON\\json_array_formulario.json";
    EvenFecha evefec = new EvenFecha();
    private EvenMensajeJoptionpane evemen=new EvenMensajeJoptionpane();
    private json_config_json jsoncf=new json_config_json();
    ComputerInfo pcinf=new ComputerInfo();
//    private static String MacAddress_maquina;
    private static String nombre_computador;
    private static boolean boo_frmventa_ven_aux;
    private static boolean boo_frmventa_zona_nro;
    private static boolean boo_frmventa_zona_nombre;
    private static boolean boo_rasp_1;
    private static boolean boo_rasp_2;
    private static boolean boo_rasp_3;
    private static int app_tiempo_min_exp;
    private static String app_nom_report;
    private static boolean app_act_exp;
    private static boolean act_roll_usu;
    
    public void cargar_jsom_array_formulario() {
//         MacAddress_maquina=pcinf.getMacAddress();
        nombre_computador=pcinf.getNombrePC();
        JSONParser parser = new JSONParser();
        try {
            Object obj_maquina = parser.parse(new FileReader(ruta_json));
            JSONObject jsonObject_maquina = (JSONObject) obj_maquina;
            JSONArray Array_maquina = (JSONArray) jsonObject_maquina.get(nombre_computador);
            Iterator<String> iterator_maquina = Array_maquina.iterator();
            String datos_conexion = String.valueOf(iterator_maquina.next());
            System.out.println(nombre_computador);
            Object obj_conexion = parser.parse(datos_conexion);
            JSONObject jsonObject_conexion = (JSONObject) obj_conexion;
            String  frmventa_ven_aux = (String) jsonObject_conexion.get("frmventa_ven_aux");
            String  frmventa_zona_nro = (String) jsonObject_conexion.get("frmventa_zona_nro");
            String  frmventa_zona_nombre = (String) jsonObject_conexion.get("frmventa_zona_nombre");
            String  rasp_1 = (String) jsonObject_conexion.get("rasp_1");
            String  rasp_2 = (String) jsonObject_conexion.get("rasp_2");
            String  rasp_3 = (String) jsonObject_conexion.get("rasp_3");
            String  app_tiempo_min_exp = (String) jsonObject_conexion.get("app_tiempo_min_exp");
            String  app_nom_report = (String) jsonObject_conexion.get("app_nom_report");
            String  app_act_exp = (String) jsonObject_conexion.get("app_act_exp");
            String  act_roll_usu = (String) jsonObject_conexion.get("act_roll_usu");
            setBoo_frmventa_ven_aux(getBoo_si_no(frmventa_ven_aux));
            setBoo_frmventa_zona_nro(getBoo_si_no(frmventa_zona_nro));
            setBoo_frmventa_zona_nombre(getBoo_si_no(frmventa_zona_nombre));
            setBoo_rasp_1(getBoo_si_no(rasp_1));
            setBoo_rasp_2(getBoo_si_no(rasp_2));
            setBoo_rasp_3(getBoo_si_no(rasp_3));
            setApp_tiempo_min_exp(Integer.parseInt(app_tiempo_min_exp));
            setApp_nom_report(app_nom_report);
            setApp_act_exp(getBoo_si_no(app_act_exp));
            setAct_roll_usu(getBoo_si_no(act_roll_usu));
            jsoncf.imprimir_gson(obj_conexion);
        } catch (Exception ex) {
            System.err.println("Error: " + ex.toString()+"\nNombre Maquina:"+nombre_computador);
            JOptionPane.showMessageDialog(null, "Error: " + ex.toString()+"\nNombre Maquina:"+nombre_computador);
            if(evemen.getBooMensaje_warning("DESEA ABRIR EL ARCHIVO PARA CAMBIAR EL MAC PARA ESTE EQUIPO","ABRIR JSON","ABRIR","CANCELAR")){
                abrir_este_json_array_formulario();
            }
        } finally {

        }
    }
    public void abrir_este_json_array_formulario(){
        jsoncf.abrirArchivo(ruta_json);
    }
    boolean getBoo_si_no(String nombre){
        boolean sino=false;
        boolean validar=true;
        if(nombre.equals("NO")){
            sino=false;
            validar=false;
        }
        if(nombre.equals("SI")){
            sino=true;
            validar=false;
        }
        if(validar){
            JOptionPane.showMessageDialog(null,"NO SE CARGO CORRECTAMENTE EL SI NO: \nESTE VALOR:"+nombre);
            abrir_este_json_array_formulario();
            System.exit(0);
        }
        return sino;
    }

    public static boolean isApp_act_exp() {
        return app_act_exp;
    }

    public static void setApp_act_exp(boolean app_act_exp) {
        json_array_formulario.app_act_exp = app_act_exp;
    }

    public static boolean isAct_roll_usu() {
        return act_roll_usu;
    }

    public static void setAct_roll_usu(boolean act_roll_usu) {
        json_array_formulario.act_roll_usu = act_roll_usu;
    }

    public static int getApp_tiempo_min_exp() {
        return app_tiempo_min_exp;
    }

    public static void setApp_tiempo_min_exp(int app_tiempo_min_exp) {
        json_array_formulario.app_tiempo_min_exp = app_tiempo_min_exp;
    }

    public static String getApp_nom_report() {
        return app_nom_report;
    }

    public static void setApp_nom_report(String app_nom_report) {
        json_array_formulario.app_nom_report = app_nom_report;
    }
    
    public static boolean isBoo_frmventa_ven_aux() {
        return boo_frmventa_ven_aux;
    }

    public static void setBoo_frmventa_ven_aux(boolean boo_frmventa_ven_aux) {
        json_array_formulario.boo_frmventa_ven_aux = boo_frmventa_ven_aux;
    }

    public static boolean isBoo_frmventa_zona_nro() {
        return boo_frmventa_zona_nro;
    }

    public static void setBoo_frmventa_zona_nro(boolean boo_frmventa_zona_nro) {
        json_array_formulario.boo_frmventa_zona_nro = boo_frmventa_zona_nro;
    }

    public static boolean isBoo_frmventa_zona_nombre() {
        return boo_frmventa_zona_nombre;
    }

    public static void setBoo_frmventa_zona_nombre(boolean boo_frmventa_zona_nombre) {
        json_array_formulario.boo_frmventa_zona_nombre = boo_frmventa_zona_nombre;
    }

    public static boolean isBoo_rasp_1() {
        return boo_rasp_1;
    }

    public static void setBoo_rasp_1(boolean boo_rasp_1) {
        json_array_formulario.boo_rasp_1 = boo_rasp_1;
    }

    public static boolean isBoo_rasp_2() {
        return boo_rasp_2;
    }

    public static void setBoo_rasp_2(boolean boo_rasp_2) {
        json_array_formulario.boo_rasp_2 = boo_rasp_2;
    }

    public static boolean isBoo_rasp_3() {
        return boo_rasp_3;
    }

    public static void setBoo_rasp_3(boolean boo_rasp_3) {
        json_array_formulario.boo_rasp_3 = boo_rasp_3;
    }

   

}
