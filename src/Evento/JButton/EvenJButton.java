/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.JButton;

import Evento.Color.EvenColor;
import javax.swing.JButton;

/**
 *
 * @author Digno
 */
public class EvenJButton {
    EvenColor evecol=new EvenColor();
    /**
     * Funcion para cambiar el color del bolon al acercar el cursor del mouse
     * @param btn boton q cambia color
     */
    public void setColor_mouseMoved(JButton btn){
        btn.setBackground(evecol.getColor_boton_mouseMoved());
    }
    /**
     * Funcion para retornar un color al alejar el cursor del mouse
     * @param btn retornar color boton
     */
    public void setColor_mouseExited(JButton btn){
        btn.setBackground(evecol.getColor_boton_mouseExited());
    }
    /**
     * Trae color estandar de java
     * Funcion para retornar un color al alejar el cursor del mouse
     * @param btn retornar color boton
     */
    public void setColor_mouseExited_neutro(JButton btn){
        btn.setBackground(evecol.getColor_boton_mouseExited_neutro());
    }
}
