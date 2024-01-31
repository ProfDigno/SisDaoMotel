/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IMPRESORA_POS;

import Config_JSON.json_array_imprimir_pos;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class ClaImpresoraPos {

    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    json_array_imprimir_pos jsprint = new json_array_imprimir_pos();
    private static FileInputStream inputStream = null;
    public static FileInputStream getInputStream() {
        return inputStream;
    }

    public static void setInputStream(FileInputStream aInputStream) {
        inputStream = aInputStream;
    }

    public void imprimir_ticket_Pos() {
        String titulo="imprimir_ticket_Pos";
        try {
            impresora_por_defecto(getInputStream());
        } catch (Exception e) {
            evemen.mensaje_error(e,titulo);
        }
    }
public void imprimir_ticket_Pos_por_nombre(String printerName) {
         String titulo="imprimir_ticket_Pos_por_nombre";
        try {
            impresora_por_nombre(printerName, getInputStream());
        } catch (Exception e) {
            evemen.mensaje_error(e,titulo);
        }
    }


    public static void impresora_por_defecto(FileInputStream inputStream) {
        DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc document = new SimpleDoc(inputStream, docFormat, null);
        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
        
        if (defaultPrintService != null) {
            DocPrintJob printJob = defaultPrintService.createPrintJob();
            try {
                printJob.print(document, attributeSet);
                cortarHoja();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.err.println("No existen impresoras instaladas");
            JOptionPane.showMessageDialog(null, "No existen impresoras instaladas");
        }

    }
    public static void impresora_por_nombre(String printerName,FileInputStream inputStream) {
        DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc document = new SimpleDoc(inputStream, docFormat, null);
        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
          PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        if (services != null) {
            DocPrintJob printJob = getServicioImpresion(services,printerName);
            try {
                printJob.print(document, attributeSet);
                cortarHoja();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.err.println("No existen impresoras instaladas");
            JOptionPane.showMessageDialog(null, "No existen impresoras instaladas");
        }

    }
    public static void cortarHoja() throws PrintException {
        DocPrintJob job = PrintServiceLookup.lookupDefaultPrintService().createPrintJob();
        byte[] PARTIAL_CUT_1 = {0x1B, 0x69}; // cortar el ticket
        // byte[] PARTIAL_CUT_1 = {0x1B, 'F'}; // cortar el ticket
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(PARTIAL_CUT_1, flavor, null);
        job.print(doc, null);
    }

    public static String alinearDerecha(String detalle, int posicionX) {
        String det = (detalle.trim());
        int i = posicionX;
        int y = i - det.length();

        if (det.length() < i) {
            for (int j = 0; j < y; j++) {
                det = (" " + det);
            }
        }
        return det;
    }
    

    private  static DocPrintJob getServicioImpresion(PrintService[] services,String nombreImpresora) {
        DocPrintJob job = null;
        try {
            if (services.length > 0) {
                for (PrintService service : services) {
                    if (service.getName().trim().toUpperCase().equals(nombreImpresora.trim().toUpperCase())) {
                        System.err.println(service.getName().trim() + " == " + nombreImpresora.trim());
                        job = service.createPrintJob();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return job;
    }
}
