/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Jframe;

import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.VISTA.FrmGasto;
import FORMULARIO.VISTA.FrmMenuMotel;
//import FORMULARIO.VISTA.FrmMenuRestaurante;
import java.awt.Dimension;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author Digno
 */
public class EvenJFRAME {
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    

    public void abrir_TablaJinternal(JInternalFrame formu) {
        FrmMenuMotel.escritorio.add(formu);
        formu.setVisible(true);
        System.out.println("Abrir Jinternal: "+formu.getName());
    }
    public void cerrar_TablaJinternal(JInternalFrame formu) {
//        formu.setVisible(false);
        try {
//            formu.setClosed(true);
//            FrmGasto frm
//            FrmMenu.escritorio.remove(FrmGasto());
        } catch (Exception e) {
            System.out.println("ERROR: "+formu.getName()+"\n"+e);
        }
        
        System.out.println("cerrar_TablaJinternal: "+formu.getName());
    }
    public  void centrar_formulario(javax.swing.JInternalFrame frame) {
        Dimension desktopSize = FrmMenuMotel.escritorio.getSize();
        Dimension jInternalFrameSize = frame.getSize();
        frame.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);
        System.out.println("CENTRAR Jinternal: "+frame.getTitle());
    }
    public void maximizar_jinternal(JInternalFrame formu){
        try {
            formu.setMaximum(true);
        } catch (Exception e) {
            evemen.mensaje_error(e, "error","maximizar_jinternal");
        }
    }
    public  void centrar_formulario_JDialog(javax.swing.JDialog frame) {
        Dimension desktopSize = FrmMenuMotel.escritorio.getSize();
        Dimension jInternalFrameSize = frame.getSize();
        frame.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);
        System.out.println("CENTRAR JDialog: "+frame.getTitle());
    }
    public  void centrar_formulario_internalframa(javax.swing.JInternalFrame frame) {
        Dimension desktopSize = FrmMenuMotel.escritorio.getSize();
        Dimension jInternalFrameSize = frame.getSize();
        frame.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);
        System.out.println("CENTRAR Jinternal: "+frame.getTitle());
    }
}
