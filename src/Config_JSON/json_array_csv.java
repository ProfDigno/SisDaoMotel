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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Digno
 */
public class json_array_csv {

    private String ruta_json = "JSON\\json_array_csv.json";
    EvenFecha evefec = new EvenFecha();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private json_config_json jsoncf = new json_config_json();
    ComputerInfo pcinf = new ComputerInfo();
    private static String nombre;
    private static boolean activar_exportar;
    private static String carpeta_raiz;
    private static String carpeta_raiz_ser;
    private static String carpeta_local;
    private static String carpeta_dropbox;
    private static String delimitador;
    private static String cli_nombre;
    private static String cli_pc;
    private static String ser_carpeta_raiz;
    private static String ser_carpeta_dropbox;
    private static String cli_carpeta;
    private static String dias_desde;
    private static String nombre_computador;

    public void cargar_jsom_array_csv() {
        nombre_computador = pcinf.getNombrePC();
        JSONParser parser = new JSONParser();
        try {
            Object obj_maquina = parser.parse(new FileReader(ruta_json));
            JSONObject jsonObject_maquina = (JSONObject) obj_maquina;
            JSONArray Array_maquina = (JSONArray) jsonObject_maquina.get(nombre_computador);
            Iterator<String> iterator_maquina = Array_maquina.iterator();
            String datos_csv = String.valueOf(iterator_maquina.next());
            System.out.println(nombre_computador);
            Object obj_csv = parser.parse(datos_csv);
            JSONObject jsonObject_csv = (JSONObject) obj_csv;
            setCarpeta_raiz((String)jsonObject_csv.get("carpeta_raiz"));
            setCarpeta_raiz_ser((String)jsonObject_csv.get("carpeta_raiz_ser"));
            setCarpeta_local((String)jsonObject_csv.get("carpeta_local"));
            setCarpeta_dropbox((String)jsonObject_csv.get("carpeta_dropbox"));
            setDelimitador((String)jsonObject_csv.get("delimitador"));
            setCli_pc((String)jsonObject_csv.get("cli_pc"));
            setCli_carpeta((String)jsonObject_csv.get("cli_carpeta"));
            setSer_carpeta_raiz((String)jsonObject_csv.get("ser_carpeta_raiz"));
            setSer_carpeta_dropbox((String)jsonObject_csv.get("ser_carpeta_dropbox"));
            setCli_nombre((String)jsonObject_csv.get("cli_nombre"));
            setActivar_exportar(getBoolean_SINO((String)jsonObject_csv.get("activar_exportar")));
            setDias_desde((String)jsonObject_csv.get("dias_desde"));
            jsoncf.imprimir_gson(obj_csv);
            
        } catch (Exception ex) {
            System.err.println("Error: " + ex.toString() + "\nNombre Maquina:" + nombre_computador);
            JOptionPane.showMessageDialog(null, "Error: " + ex.toString() + "\nNombre Maquina:" + nombre_computador);
            if (evemen.getBooMensaje_warning("DESEA ABRIR EL ARCHIVO PARA CAMBIAR EL NOMBRE PARA ESTE EQUIPO", "ABRIR JSON", "ABRIR", "CANCELAR")) {
                abrir_este_json_array_conexion();
            }
        } finally {

        }
    }
    private boolean getBoolean_SINO(String sino){
        if(sino.equals("SI")){
            return true;
        }else{
            return false;
        }
    }

    public static String getCli_nombre() {
        return cli_nombre;
    }

    public static void setCli_nombre(String cli_nombre) {
        json_array_csv.cli_nombre = cli_nombre;
    }

    public static String getCli_pc() {
        return cli_pc;
    }

    public static void setCli_pc(String cli_pc) {
        json_array_csv.cli_pc = cli_pc;
    }

    public static String getSer_carpeta_raiz() {
        return ser_carpeta_raiz;
    }

    public static void setSer_carpeta_raiz(String ser_carpeta_raiz) {
        json_array_csv.ser_carpeta_raiz = ser_carpeta_raiz;
    }

    public static String getSer_carpeta_dropbox() {
        return ser_carpeta_dropbox;
    }

    public static void setSer_carpeta_dropbox(String ser_carpeta_dropbox) {
        json_array_csv.ser_carpeta_dropbox = ser_carpeta_dropbox;
    }

    public static String getCli_carpeta() {
        return cli_carpeta;
    }

    public static void setCli_carpeta(String cli_carpeta) {
        json_array_csv.cli_carpeta = cli_carpeta;
    }

    public static String getCarpeta_raiz_ser() {
        return carpeta_raiz_ser;
    }

    public static void setCarpeta_raiz_ser(String carpeta_raiz_ser) {
        json_array_csv.carpeta_raiz_ser = carpeta_raiz_ser;
    }

    public static String getDias_desde() {
        return dias_desde;
    }

    public static void setDias_desde(String dias_desde) {
        json_array_csv.dias_desde = dias_desde;
    }
    
    public static boolean isActivar_exportar() {
        return activar_exportar;
    }

    public static void setActivar_exportar(boolean activar_exportar) {
        json_array_csv.activar_exportar = activar_exportar;
    }

    private void abrir_este_json_array_conexion() {
        jsoncf.abrirArchivo(ruta_json);
    }

    public static String getNombre() {
        return nombre;
    }

    public static void setNombre(String nombre) {
        json_array_csv.nombre = nombre;
    }

    public static String getCarpeta_raiz() {
        return carpeta_raiz;
    }

    public static void setCarpeta_raiz(String carpeta_raiz) {
        json_array_csv.carpeta_raiz = carpeta_raiz;
    }

    public static String getCarpeta_local() {
        return carpeta_local;
    }

    public static void setCarpeta_local(String carpeta_local) {
        json_array_csv.carpeta_local = carpeta_local;
    }

    public static String getCarpeta_dropbox() {
        return carpeta_dropbox;
    }

    public static void setCarpeta_dropbox(String carpeta_dropbox) {
        json_array_csv.carpeta_dropbox = carpeta_dropbox;
    }

    public static String getDelimitador() {
        return delimitador;
    }

    public static void setDelimitador(String delimitador) {
        json_array_csv.delimitador = delimitador;
    }

    public static String getNombre_computador() {
        return nombre_computador;
    }

    public static void setNombre_computador(String nombre_computador) {
        json_array_csv.nombre_computador = nombre_computador;
    }

}
