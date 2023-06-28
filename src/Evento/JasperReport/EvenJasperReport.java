/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.JasperReport;

import BASEDATO.EvenConexion;
import Config_JSON.json_array_imprimir_pos;
import Evento.Mensaje.EvenMensajeJoptionpane;

import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvMetadataExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Digno
 */
public class EvenJasperReport {

    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenConexion eveconn = new EvenConexion();
    private static json_array_imprimir_pos jsprint = new json_array_imprimir_pos();
//    private static json_imprimir_pos jsprint=new json_imprimir_pos();

    public void imprimirjasper(Connection conexion, String sql, String titulonota, String direccion) {
        String titulo = "imprimirjasper";
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(direccion);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conexion);
            JasperViewer jviewer = new JasperViewer(jasperPrint, false);
            jviewer.setTitle(titulonota);
            jviewer.setVisible(true);
            evemen.Imprimir_serial_sql(sql, titulo);
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }

    }
//    public void imprimirjasper(Connection conexion,String sql,String titulonota,String direccion){
//        String titulo="imprimirjasper";
//        try{
//            JasperDesign jasperDesign = JRXmlLoader.load(direccion);
//            JRDesignQuery newQuery = new JRDesignQuery();
//            newQuery.setText(sql);
//            jasperDesign.setQuery(newQuery);
//            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,conexion);           
//            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
//            jviewer.setTitle(titulonota);
//            jviewer.setVisible(true); 
//            evemen.Imprimir_serial_sql(sql, titulo);
//        }catch(Exception e){
//            evemen.Imprimir_serial_sql_error(e, sql, titulo);
//        }
//        
//   } 

    public void imprimirjasper_tamano(Connection conexion, String sql, String titulonota, String direccion, int PageHeight) {
        String titulo = "imprimirjasper";
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(direccion);
            jasperDesign.setPageHeight(jasperDesign.getPageHeight() + PageHeight);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conexion);
            JasperViewer jviewer = new JasperViewer(jasperPrint, false);
            jviewer.setTitle(titulonota);
            jviewer.setVisible(true);
            evemen.Imprimir_serial_sql(sql, titulo);
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }

    }

    private int getInt_contar_cant_fila(Connection conn, String sql) {
        int cantidad = 0;
        String titulo = "getInt_contar_cant_fila";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                cantidad++;
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return cantidad;
    }

    public void imprimirExcel_exportar_appsheet_incremental(Connection conn, String sql, String titulonota,
            String direccion, String rutatemp, int PageHeight) {
        int cant_fila = getInt_contar_cant_fila(conn, sql);
        if (true) {
            System.out.println("titulonota:" + titulonota);
            System.out.println("sql:" + sql);
            System.out.println("direccion:" + direccion);
            System.out.println("rutatemp:" + rutatemp);
        }
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(direccion);
            jasperDesign.setPageHeight(jasperDesign.getPageHeight() + (PageHeight * cant_fila));
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
            JRExporter exp = new JRXlsxExporter();
            exp.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exp.setParameter(JRExporterParameter.OUTPUT_FILE, new File(rutatemp));
            exp.exportReport();
//            abrirArchivo(rutatemp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void imprimirjasper_por_nombre_impresora(Connection conexion, String sql, String titulonota, String direccion, boolean vista_previa) {
        String titulo = "imprimirjasper_por_nombre_impresora";
        String impresora = jsprint.getPrint_nombre_factura();
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(direccion);
//            jasperDesign.setPageHeight(jasperDesign.getPageHeight() + PageHeight);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conexion);
            if (vista_previa) {
                JasperViewer jviewer = new JasperViewer(jasperPrint, false);
                jviewer.setTitle(titulonota);
                jviewer.setVisible(true);
            } else {
                print_impresora_nombre(jasperPrint, impresora);
            }
            evemen.Imprimir_serial_sql(sql, titulo);
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql + "\nImpresora:" + impresora + "\nDireccion:" + direccion, titulo);
        }
    }

    public void print_impresora_nombre(JasperPrint jp, String impresora) {
        try {
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            printRequestAttributeSet.add(new Copies(1));
            PrinterName printerName = new PrinterName(impresora, null); //gets printer
            PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
            printServiceAttributeSet.add(printerName);
            JRPrintServiceExporter exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            exporter.exportReport();
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Cancelo Impresion\n" + ex);
        }
    }
}
