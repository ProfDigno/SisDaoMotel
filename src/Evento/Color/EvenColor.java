/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Color;

import java.awt.Color;

/**
 *
 * @author Digno
 */
public class EvenColor {
     public EvenColor() {
        setColor_boton_mouseMoved(new java.awt.Color(167,232,178));
        setColor_boton_mouseExited(new java.awt.Color(153,204,255));
        setColor_boton_mouseExited_neutro(new java.awt.Color(240, 240, 240));
    }
    
    public static Color color_boton_mouseMoved; 
    public static Color color_boton_mouseExited; 
    public static Color color_boton_mouseExited_neutro;

    public static Color getColor_boton_mouseExited_neutro() {
        return color_boton_mouseExited_neutro;
    }

    public static void setColor_boton_mouseExited_neutro(Color color_boton_mouseExited_neutro) {
        EvenColor.color_boton_mouseExited_neutro = color_boton_mouseExited_neutro;
    }
    
    public static Color getColor_boton_mouseMoved() {
        return color_boton_mouseMoved;
    }

    public static void setColor_boton_mouseMoved(Color color_boton_mouseMoved) {
        EvenColor.color_boton_mouseMoved = color_boton_mouseMoved;
    }

    public static Color getColor_boton_mouseExited() {
        return color_boton_mouseExited;
    }

    public static void setColor_boton_mouseExited(Color color_boton_mouseExited) {
        EvenColor.color_boton_mouseExited = color_boton_mouseExited;
    }
    private static Color color_libre=new Color(79, 236, 110);
    private static  Color color_ocupado=new Color(203, 62, 51);
    private static Color color_sucio=new Color(245, 176, 65);
    private static Color color_limpieza=new Color(245, 245, 27);
    public static Color[] color_grafico_estados = new Color[]{
        color_libre,color_ocupado, color_sucio,color_limpieza, 
        new Color(108, 77, 146),new Color(46, 154, 183), new Color(151, 64, 64)};

    public static Color[] getColor_grafico_estados() {
        return color_grafico_estados;
    }

    public static void setColor_grafico_estados(Color[] color_grafico_estados) {
        EvenColor.color_grafico_estados = color_grafico_estados;
    }
}
