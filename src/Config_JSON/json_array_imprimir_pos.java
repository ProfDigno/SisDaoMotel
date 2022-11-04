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
public class json_array_imprimir_pos {

    private String ruta_json = "JSON\\json_array_imprimir_pos.json";
    EvenFecha evefec = new EvenFecha();
    private EvenMensajeJoptionpane evemen=new EvenMensajeJoptionpane();
    private json_config_json jsoncf=new json_config_json();
    ComputerInfo pcinf=new ComputerInfo();
//    private static String MacAddress_maquina;
    private static String nombre_computador;
    private static String linea_separador;
    private static String linea_ven_detalle;
    private static String linea_ven_categoria;
    private static String linea_cabezera;
    private static String linea_ven_top_1;
    private static String linea_ven_top_2;
    private static int cant_top_venta;
    private static int salto_linea_item;
    private static int sep_inicio;
    private static int sep_numero;
    private static int sep_fecha;
    private static int sep_item_cant;
    private static int sep_item_precio;
    private static int sep_item_subtotal;
    private static int sep_total_gral;
    private static int ven_item_idv;
    private static int ven_item_cliente;
    private static int ven_item_monto;
    private static int total_columna;
    private static int tt_fila_ccvg;
    private static int tt_fila_cc;
    private static int tt_fila_ven;
    private static int tt_fila_coman;
    private static int tt_fila_ven_ms;
    private static int tt_fila_com_in;
    private static int tt_fila_gas;
    private static int tt_fila_val;
    private static int tt_text_descrip;
    private static boolean print_comanda;
    private static boolean print_insumo;
    private static boolean error_carga_json;
    private static String emp_nombre;
    private static String emp_telefono;
    private static String emp_direccion;
    public void cargar_jsom_imprimir_pos() {
//         MacAddress_maquina=pcinf.getMacAddress();
        nombre_computador=pcinf.getNombrePC();
        JSONParser parser = new JSONParser();
        try {
            Object obj_maquina = parser.parse(new FileReader(ruta_json));
            JSONObject jsonObject_maquina = (JSONObject) obj_maquina;
            JSONArray Array_maquina = (JSONArray) jsonObject_maquina.get(nombre_computador);
            Iterator<String> iterator_maquina = Array_maquina.iterator();
            String datos_conexion = String.valueOf(iterator_maquina.next());
            System.out.println(datos_conexion);
            Object obj_conexion = parser.parse(datos_conexion);
            JSONObject jsonObject = (JSONObject) obj_conexion;
            String linea_separador = (String) jsonObject.get("linea_separador");
            String linea_ven_detalle = (String) jsonObject.get("linea_ven_detalle");
            String linea_ven_categoria = (String) jsonObject.get("linea_ven_categoria");
            String linea_cabezera = (String) jsonObject.get("linea_cabezera");
            String salto_linea_item = (String) jsonObject.get("salto_linea_item");
            String sep_inicio = (String) jsonObject.get("sep_inicio");
            String sep_numero = (String) jsonObject.get("sep_numero");
            String sep_fecha = (String) jsonObject.get("sep_fecha");
            String sep_item_cant = (String) jsonObject.get("sep_item_cant");
            String sep_item_precio = (String) jsonObject.get("sep_item_precio");
            String sep_item_subtotal = (String) jsonObject.get("sep_item_subtotal");
            String sep_total_gral = (String) jsonObject.get("sep_total_gral");
            String ven_item_idv = (String) jsonObject.get("ven_item_idv");
            String ven_item_cliente = (String) jsonObject.get("ven_item_cliente");
            String ven_item_monto = (String) jsonObject.get("ven_item_monto");
            String total_columna = (String) jsonObject.get("total_columna");
            String tt_text_descrip = (String) jsonObject.get("tt_text_descrip");
            String tt_fila_ccvg = (String) jsonObject.get("tt_fila_ccvg");
            String tt_fila_cc = (String) jsonObject.get("tt_fila_cc");
            String tt_fila_ven = (String) jsonObject.get("tt_fila_ven");
            String tt_fila_coman = (String) jsonObject.get("tt_fila_coman");
            String tt_fila_ven_ms = (String) jsonObject.get("tt_fila_ven_ms");
            String tt_fila_com_in = (String) jsonObject.get("tt_fila_com_in");
            String tt_fila_gas = (String) jsonObject.get("tt_fila_gas");
            String tt_fila_val = (String) jsonObject.get("tt_fila_val");
            String linea_ven_top_1 = (String) jsonObject.get("linea_ven_top_1");
            String linea_ven_top_2 = (String) jsonObject.get("linea_ven_top_2");
            String cant_top_venta = (String) jsonObject.get("cant_top_venta");
            String print_comanda = (String) jsonObject.get("print_comanda");
            String print_insumo = (String) jsonObject.get("print_insumo");
            String emp_nombre = (String) jsonObject.get("emp_nombre");
            String emp_telefono = (String) jsonObject.get("emp_telefono");
            String emp_direccion = (String) jsonObject.get("emp_direccion");
            setLinea_separador(linea_separador);
            setLinea_ven_detalle(linea_ven_detalle);
            setLinea_ven_categoria(linea_ven_categoria);
            setLinea_cabezera(linea_cabezera);
            setSalto_linea_item(Integer.parseInt(salto_linea_item));
            setSep_inicio(Integer.parseInt(sep_inicio));
            setSep_numero(Integer.parseInt(sep_numero));
            setSep_fecha(Integer.parseInt(sep_fecha));
            setSep_item_cant(Integer.parseInt(sep_item_cant));
            setSep_item_precio(Integer.parseInt(sep_item_precio));
            setSep_item_subtotal(Integer.parseInt(sep_item_subtotal));
            setVen_item_idv(Integer.parseInt(ven_item_idv));
            setVen_item_cliente(Integer.parseInt(ven_item_cliente));
            setVen_item_monto(Integer.parseInt(ven_item_monto));
            setSep_total_gral(Integer.parseInt(sep_total_gral));
            setTotal_columna(Integer.parseInt(total_columna));
            setTt_text_descrip(Integer.parseInt(tt_text_descrip));
            setTt_fila_ccvg(Integer.parseInt(tt_fila_ccvg));
            setTt_fila_cc(Integer.parseInt(tt_fila_cc));
            setTt_fila_ven(Integer.parseInt(tt_fila_ven));
            setTt_fila_coman(Integer.parseInt(tt_fila_coman));
            setTt_fila_ven_ms(Integer.parseInt(tt_fila_ven_ms));
            setTt_fila_com_in(Integer.parseInt(tt_fila_com_in));
            setTt_fila_gas(Integer.parseInt(tt_fila_gas));
            setTt_fila_val(Integer.parseInt(tt_fila_val));
            setLinea_ven_top_1(linea_ven_top_1);
            setLinea_ven_top_2(linea_ven_top_2);
            setCant_top_venta(Integer.parseInt(cant_top_venta));
            setEmp_nombre(emp_nombre);
            setEmp_telefono(emp_telefono);
            setEmp_direccion(emp_direccion);
            if (print_comanda.equals("true")) {
                setPrint_comanda(true);
            } else {
                setPrint_comanda(false);
            }
            if (print_insumo.equals("true")) {
                setPrint_insumo(true);
            } else {
                setPrint_insumo(false);
            }
            System.out.println("json imprimir pos:" + jsonObject);
            setError_carga_json(true);
        } catch (Exception ex) {
            System.err.println("Error: " + ex.toString()+"\nNombre Maquina:"+nombre_computador);
            JOptionPane.showMessageDialog(null, "Error: " + ex.toString()+"\nNombre Maquina:"+nombre_computador);
            if(evemen.getBooMensaje_warning("DESEA ABRIR EL ARCHIVO PARA CAMBIAR EL NOMBRE PARA ESTE EQUIPO","ABRIR JSON","ABRIR","CANCELAR")){
                abrir_este_json_array_imprimir_pos();
            }
            setError_carga_json(false);
        } finally {

        }
    }

    public static String getEmp_nombre() {
        return emp_nombre;
    }

    public static void setEmp_nombre(String emp_nombre) {
        json_array_imprimir_pos.emp_nombre = emp_nombre;
    }

    public static String getEmp_telefono() {
        return emp_telefono;
    }

    public static void setEmp_telefono(String emp_telefono) {
        json_array_imprimir_pos.emp_telefono = emp_telefono;
    }

    public static String getEmp_direccion() {
        return emp_direccion;
    }

    public static void setEmp_direccion(String emp_direccion) {
        json_array_imprimir_pos.emp_direccion = emp_direccion;
    }
    
    public void abrir_este_json_array_imprimir_pos(){
        jsoncf.abrirArchivo(ruta_json);
    }

    public static boolean isError_carga_json() {
        return error_carga_json;
    }

    public static void setError_carga_json(boolean error_carga_json) {
        json_array_imprimir_pos.error_carga_json = error_carga_json;
    }

    public static String getLinea_separador() {
        return linea_separador;
    }

    public static void setLinea_separador(String linea_separador) {
        json_array_imprimir_pos.linea_separador = linea_separador;
    }

    public static String getLinea_ven_detalle() {
        return linea_ven_detalle;
    }

    public static void setLinea_ven_detalle(String linea_ven_detalle) {
        json_array_imprimir_pos.linea_ven_detalle = linea_ven_detalle;
    }

    public static String getLinea_ven_categoria() {
        return linea_ven_categoria;
    }

    public static void setLinea_ven_categoria(String linea_ven_categoria) {
        json_array_imprimir_pos.linea_ven_categoria = linea_ven_categoria;
    }

    public static String getLinea_cabezera() {
        return linea_cabezera;
    }

    public static void setLinea_cabezera(String linea_cabezera) {
        json_array_imprimir_pos.linea_cabezera = linea_cabezera;
    }

    public static String getLinea_ven_top_1() {
        return linea_ven_top_1;
    }

    public static void setLinea_ven_top_1(String linea_ven_top_1) {
        json_array_imprimir_pos.linea_ven_top_1 = linea_ven_top_1;
    }

    public static String getLinea_ven_top_2() {
        return linea_ven_top_2;
    }

    public static void setLinea_ven_top_2(String linea_ven_top_2) {
        json_array_imprimir_pos.linea_ven_top_2 = linea_ven_top_2;
    }

    public static int getCant_top_venta() {
        return cant_top_venta;
    }

    public static void setCant_top_venta(int cant_top_venta) {
        json_array_imprimir_pos.cant_top_venta = cant_top_venta;
    }

    public static int getSalto_linea_item() {
        return salto_linea_item;
    }

    public static void setSalto_linea_item(int salto_linea_item) {
        json_array_imprimir_pos.salto_linea_item = salto_linea_item;
    }

    public static int getSep_inicio() {
        return sep_inicio;
    }

    public static void setSep_inicio(int sep_inicio) {
        json_array_imprimir_pos.sep_inicio = sep_inicio;
    }

    public static int getSep_numero() {
        return sep_numero;
    }

    public static void setSep_numero(int sep_numero) {
        json_array_imprimir_pos.sep_numero = sep_numero;
    }

    public static int getSep_fecha() {
        return sep_fecha;
    }

    public static void setSep_fecha(int sep_fecha) {
        json_array_imprimir_pos.sep_fecha = sep_fecha;
    }

    public static int getSep_item_cant() {
        return sep_item_cant;
    }

    public static void setSep_item_cant(int sep_item_cant) {
        json_array_imprimir_pos.sep_item_cant = sep_item_cant;
    }

    public static int getSep_item_precio() {
        return sep_item_precio;
    }

    public static void setSep_item_precio(int sep_item_precio) {
        json_array_imprimir_pos.sep_item_precio = sep_item_precio;
    }

    public static int getSep_item_subtotal() {
        return sep_item_subtotal;
    }

    public static void setSep_item_subtotal(int sep_item_subtotal) {
        json_array_imprimir_pos.sep_item_subtotal = sep_item_subtotal;
    }

    public static int getSep_total_gral() {
        return sep_total_gral;
    }

    public static void setSep_total_gral(int sep_total_gral) {
        json_array_imprimir_pos.sep_total_gral = sep_total_gral;
    }

    public static int getVen_item_idv() {
        return ven_item_idv;
    }

    public static void setVen_item_idv(int ven_item_idv) {
        json_array_imprimir_pos.ven_item_idv = ven_item_idv;
    }

    public static int getVen_item_cliente() {
        return ven_item_cliente;
    }

    public static void setVen_item_cliente(int ven_item_cliente) {
        json_array_imprimir_pos.ven_item_cliente = ven_item_cliente;
    }

    public static int getVen_item_monto() {
        return ven_item_monto;
    }

    public static void setVen_item_monto(int ven_item_monto) {
        json_array_imprimir_pos.ven_item_monto = ven_item_monto;
    }

    public static int getTotal_columna() {
        return total_columna;
    }

    public static void setTotal_columna(int total_columna) {
        json_array_imprimir_pos.total_columna = total_columna;
    }

    public static int getTt_fila_ccvg() {
        return tt_fila_ccvg;
    }

    public static void setTt_fila_ccvg(int tt_fila_ccvg) {
        json_array_imprimir_pos.tt_fila_ccvg = tt_fila_ccvg;
    }

    public static int getTt_fila_cc() {
        return tt_fila_cc;
    }

    public static void setTt_fila_cc(int tt_fila_cc) {
        json_array_imprimir_pos.tt_fila_cc = tt_fila_cc;
    }

    public static int getTt_fila_ven() {
        return tt_fila_ven;
    }

    public static void setTt_fila_ven(int tt_fila_ven) {
        json_array_imprimir_pos.tt_fila_ven = tt_fila_ven;
    }

    public static int getTt_fila_coman() {
        return tt_fila_coman;
    }

    public static void setTt_fila_coman(int tt_fila_coman) {
        json_array_imprimir_pos.tt_fila_coman = tt_fila_coman;
    }

    public static int getTt_fila_ven_ms() {
        return tt_fila_ven_ms;
    }

    public static void setTt_fila_ven_ms(int tt_fila_ven_ms) {
        json_array_imprimir_pos.tt_fila_ven_ms = tt_fila_ven_ms;
    }

    public static int getTt_fila_com_in() {
        return tt_fila_com_in;
    }

    public static void setTt_fila_com_in(int tt_fila_com_in) {
        json_array_imprimir_pos.tt_fila_com_in = tt_fila_com_in;
    }

    public static int getTt_fila_gas() {
        return tt_fila_gas;
    }

    public static void setTt_fila_gas(int tt_fila_gas) {
        json_array_imprimir_pos.tt_fila_gas = tt_fila_gas;
    }

    public static int getTt_fila_val() {
        return tt_fila_val;
    }

    public static void setTt_fila_val(int tt_fila_val) {
        json_array_imprimir_pos.tt_fila_val = tt_fila_val;
    }

    public static int getTt_text_descrip() {
        return tt_text_descrip;
    }

    public static void setTt_text_descrip(int tt_text_descrip) {
        json_array_imprimir_pos.tt_text_descrip = tt_text_descrip;
    }

    public static boolean isPrint_comanda() {
        return print_comanda;
    }

    public static void setPrint_comanda(boolean print_comanda) {
        json_array_imprimir_pos.print_comanda = print_comanda;
    }

    public static boolean isPrint_insumo() {
        return print_insumo;
    }

    public static void setPrint_insumo(boolean print_insumo) {
        json_array_imprimir_pos.print_insumo = print_insumo;
    }
    

}
