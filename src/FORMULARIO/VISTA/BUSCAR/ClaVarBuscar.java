/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA.BUSCAR;

import javax.swing.JTextField;

/**
 *
 * @author Digno
 */
public class ClaVarBuscar {
    private static int tipo_tabla;
    private static String nombre_tabla;
    private static String nombre_columna;
    private static String pre_busqueda;
    private static int tipo_tabla_cliente;
    public void abrir_buscar(int tipo,String nombre,JTextField txtbuscar){
        if(txtbuscar.getText().trim().length()>=0){
            setTipo_tabla(tipo);
            setNombre_tabla(nombre);
            setPre_busqueda(txtbuscar.getText());
            JDiaBuscar frm=new JDiaBuscar(null, true);
            frm.setVisible(true);
        }
    }

    public static int getTipo_tabla_cliente() {
        return tipo_tabla_cliente;
    }

    public static void setTipo_tabla_cliente(int tipo_tabla_cliente) {
        ClaVarBuscar.tipo_tabla_cliente = tipo_tabla_cliente;
    }
    
    public static int getTipo_tabla() {
        return tipo_tabla;
    }

    public static void setTipo_tabla(int tipo_tabla) {
        ClaVarBuscar.tipo_tabla = tipo_tabla;
    }

    public static String getNombre_tabla() {
        return nombre_tabla;
    }

    public static void setNombre_tabla(String nombre_tabla) {
        ClaVarBuscar.nombre_tabla = nombre_tabla;
    }

    public static String getNombre_columna() {
        return nombre_columna;
    }

    public static void setNombre_columna(String nombre_columna) {
        ClaVarBuscar.nombre_columna = nombre_columna;
    }

    public static String getPre_busqueda() {
        return pre_busqueda;
    }

    public static void setPre_busqueda(String pre_busqueda) {
        ClaVarBuscar.pre_busqueda = pre_busqueda;
    }
    
}
