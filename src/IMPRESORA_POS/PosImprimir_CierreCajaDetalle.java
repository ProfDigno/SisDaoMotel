/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IMPRESORA_POS;

import BASEDATO.EvenConexion;
//import Config_JSON.json_config;
import Config_JSON.json_array_imprimir_pos;
import Evento.Mensaje.EvenMensajeJoptionpane;
//import FORMULARIO.DAO.DAO_empresa;
//import FORMULARIO.ENTIDAD.empresa;
import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.print.PrintException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Digno
 */
public class PosImprimir_CierreCajaDetalle {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private static ClaImpresoraPos pos = new ClaImpresoraPos();
    private static json_array_imprimir_pos jsprint = new json_array_imprimir_pos();
    private static int cant_fila = 200;
    private static String tk_ruta_archivo = "ticket_cajaDetalle.txt";
    private static String[] icd_ocupacion = new String[cant_fila];
    private static String[] icd_consumo = new String[cant_fila];
    private static String[] icd_ingreso = new String[cant_fila];
    private static String[] icd_descripcion = new String[cant_fila];
    private static String[] icd_fecha = new String[cant_fila];
    private static FileInputStream inputStream = null;
    private static int tk_iv_fila_ocupa = 0;
    private static int tk_iv_fila_consu = 0;
    private static int tk_ivi_fila_consu = 0;
    private static int tk_fila_gasto = 0;
    private static int tk_fila_garantia = 0;
    private static String[] iv_cant = new String[cant_fila];
    private static String[] iv_descrip = new String[cant_fila];
    private static String[] iv_subsotal = new String[cant_fila];
    private static String[] ivi_cant = new String[cant_fila];
    private static String[] ivi_descrip = new String[cant_fila];
    private static String[] ivi_subsotal = new String[cant_fila];
    private static String[] g_hora = new String[cant_fila];
    private static String[] g_descripcion = new String[cant_fila];
    private static String[] g_total = new String[cant_fila];
    private static String[] ga_estado = new String[cant_fila];
    private static String[] ga_descripcion = new String[cant_fila];
    private static String[] ga_total = new String[cant_fila];
    private String nombre_ticket = "ticket Caja Detalle";
    private static String cd_fk_idcaja_cierre = "0";
    private static String cd_fec_inicio = "0";
    private static String cd_fec_fin = "0";
    private static String cd_tt_adelanto = "0";
    private static String cd_tt_descuento = "00:00";
    private static String cd_tt_minimo = "0";
    private static String cd_tt_adicional = "0";
    private static String cd_tt_consumo = "0";
    private static String cd_tt_ingreso = "0";
    private static String cd_tt_gasto = "0";
    private static String cd_tt_compra = "0";
    private static String cd_tt_vale = "0";
    private static String cd_tt_liquida = "0";
    private static String cd_tt_ven_interno = "0";
    private static String cd_tt_garantia = "0";
    private static String cd_tt_egreso = "0";
    private static String cd_tt_saldo = "0";
//    private static boolean es_consumo;
    private static String cd_creado_por = "digno";
    private static int cant_char = 10;

    private void cargar_datos_caja(Connection conn, int fk_idcaja_cierre) {
        String titulo = "cargar_datos_caja";
        String sql = "select cci.fk_idcaja_cierre as fk_idcaja_cierre,\n"
                + "to_char(cca.fecha_inicio,'yyyy-MM-dd HH24:MI') as fec_inicio, \n"
                + "to_char(cca.fecha_fin,'yyyy-MM-dd HH24:MI') as fec_fin,\n"
                + "cca.creado_por as creado_por,\n"
                + "TRIM(to_char(sum(cd.monto_solo_adelanto),'999G999G999')) as tt_adelanto,\n"
                + "TRIM(to_char(sum(cd.monto_ocupa_descuento),'999G999G999')) as tt_descuento,\n"
                + "TRIM(to_char(sum(cd.monto_ocupa_minimo-cd.monto_ocupa_adelanto),'999G999G999')) as tt_minimo,\n"
                + "TRIM(to_char(sum(cd.monto_ocupa_adicional),'999G999G999')) as tt_adicional,\n"
                + "TRIM(to_char(sum(cd.monto_ocupa_consumo),'999G999G999')) as tt_consumo,\n"
                + "TRIM(to_char(sum(cd.monto_interno),'999G999G999')) as tt_interno,\n"
                + "TRIM(to_char(sum(cd.monto_garantia),'999G999G999')) as tt_garantia,\n"
                + "TRIM(to_char(sum((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)),'999G999G999')) as tt_ingreso,\n"
                + "TRIM(to_char(sum(cd.monto_gasto),'999G999G999')) as tt_gasto,\n"
                + "TRIM(to_char(sum(cd.monto_compra),'999G999G999')) as tt_compra,\n"
                + "TRIM(to_char(sum(cd.monto_vale),'999G999G999')) as tt_vale,\n"
                + "TRIM(to_char(sum(cd.monto_liquidacion),'999G999G999')) as tt_liquida,\n"
                + "TRIM(to_char(sum(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion),'999G999G999')) as tt_egreso,\n"
                + "TRIM(to_char(sum(((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto))-\n"
                + "(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion)),'999G999G999')) as tt_saldo\n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci,caja_cierre cca\n"
                + "where  cd.idcaja_cierre_detalle=cci.fk_idcaja_cierre_detalle\n"
                + "and cci.fk_idcaja_cierre=cca.idcaja_cierre \n"
                + "and cci.fk_idcaja_cierre=" + fk_idcaja_cierre
                + " group by 1,2,3,4 ;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                cd_fk_idcaja_cierre = rs.getString("fk_idcaja_cierre");
                cd_fec_inicio = rs.getString("fec_inicio");
                cd_fec_fin = rs.getString("fec_fin");
                cd_tt_adelanto = rs.getString("tt_adelanto");
                cd_tt_descuento = rs.getString("tt_descuento");
                cd_tt_minimo = rs.getString("tt_minimo");
                cd_tt_adicional = rs.getString("tt_adicional");
                cd_tt_consumo = rs.getString("tt_consumo");
                cd_tt_ingreso = rs.getString("tt_ingreso");
                cd_tt_gasto = rs.getString("tt_gasto");
                cd_tt_compra = rs.getString("tt_compra");
                cd_tt_vale = rs.getString("tt_vale");
                cd_tt_liquida = rs.getString("tt_liquida");
                cd_tt_ven_interno = rs.getString("tt_interno");
                cd_tt_garantia = rs.getString("tt_garantia");
                cd_tt_egreso = rs.getString("tt_egreso");
                cd_tt_saldo = rs.getString("tt_saldo");
                cd_creado_por = rs.getString("creado_por");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void cargar_datos_item_caja_detalle_venta(Connection conn, int fk_idcaja_cierre) {
        String titulo = "cargar_datos_item_caja_detalle";
        String sql = "select cd.idcaja_cierre_detalle as idc,\n"
                + "to_char(cd.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha,\n"
                + "cd.descripcion as descripcion,\n"
                + "TRIM(to_char(((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)),'999G999G999')) as m_ocupacion,\n"
                + "TRIM(to_char((cd.monto_ocupa_consumo+cd.monto_interno),'999G999G999')) as m_consumo,\n"
                + "TRIM(to_char(((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo+cd.monto_interno+cd.monto_garantia)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)),'999G999G999')) as tt_ingreso\n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci,caja_cierre cca\n"
                + "where  cd.idcaja_cierre_detalle=cci.fk_idcaja_cierre_detalle\n"
                + "and cci.fk_idcaja_cierre=cca.idcaja_cierre \n"
                + "and cd.fk_idventa>0 "
                + "and cci.fk_idcaja_cierre=" + fk_idcaja_cierre
                + " order by cd.idcaja_cierre_detalle desc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_iv_fila_ocupa = 0;
            while (rs.next()) {
                icd_fecha[tk_iv_fila_ocupa] = rs.getString("fecha");
                icd_descripcion[tk_iv_fila_ocupa] = rs.getString("descripcion");
                icd_ocupacion[tk_iv_fila_ocupa] = rs.getString("m_ocupacion");
                icd_consumo[tk_iv_fila_ocupa] = rs.getString("m_consumo");
                icd_ingreso[tk_iv_fila_ocupa] = rs.getString("tt_ingreso");
                tk_iv_fila_ocupa++;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }

    }

    private void cargar_datos_item_venta(Connection conn, int fk_idcaja_cierre) {
        String titulo = "cargar_datos_venta_item";
        String sql = "select sum(vi.cantidad) as cant , vi.descripcion , \n"
                + "to_char(sum(vi.cantidad*vi.precio_venta),'999G999G999') as subtotal\n"
                + " from venta v,caja_cierre_item cci,caja_cierre_detalle ccd,venta_item vi  \n"
                + " where  ccd.idcaja_cierre_detalle=cci.fk_idcaja_cierre_detalle \n"
                + " and ccd.fk_idventa=v.idventa \n"
                + " and v.idventa=vi.fk_idventa \n"
                + " and v.estado='TERMINADO'\n"
                + " and ccd.cerrado_por='DESOCUPADO'\n"
                + " and v.monto_consumo>0\n"
                + " and cci.fk_idcaja_cierre=" + fk_idcaja_cierre
                + " group by 2\n"
                + " order by 1 desc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_iv_fila_consu = 0;
            while (rs.next()) {
                iv_cant[tk_iv_fila_consu] = rs.getString("cant");
                iv_descrip[tk_iv_fila_consu] = rs.getString("descripcion");
                iv_subsotal[tk_iv_fila_consu] = rs.getString("subtotal");
                tk_iv_fila_consu++;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }

    }

    private void cargar_datos_item_venta_interna(Connection conn, int fk_idcaja_cierre) {
        String titulo = "cargar_datos_venta_item";
        String sql = "select sum(vi.cantidad) as cant , vi.descripcion , \n"
                + "to_char(sum(vi.cantidad*vi.precio_venta),'999G999G999') as subtotal\n"
                + "from venta_interno v,caja_cierre_item cci,caja_cierre_detalle ccd,venta_item_interno vi  \n"
                + "where  ccd.idcaja_cierre_detalle=cci.fk_idcaja_cierre_detalle \n"
                + "and ccd.fk_idventa_interno=v.idventa_interno \n"
                + "and v.idventa_interno=vi.fk_idventa_interno \n"
                + "and v.estado='TERMINADO'\n"
                + "and ccd.cerrado_por='VEN_INTERNA'\n"
                + "and v.monto_interno>0\n"
                + "and cci.fk_idcaja_cierre=" + fk_idcaja_cierre
                + " group by 2\n"
                + "order by 1 desc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_ivi_fila_consu = 0;
            while (rs.next()) {
                ivi_cant[tk_ivi_fila_consu] = rs.getString("cant");
                ivi_descrip[tk_ivi_fila_consu] = rs.getString("descripcion");
                ivi_subsotal[tk_ivi_fila_consu] = rs.getString("subtotal");
                tk_ivi_fila_consu++;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }

    }

    private void cargar_datos_gasto(Connection conn, int fk_idcaja_cierre) {
        String titulo = "cargar_datos_gasto";
        String sql = "select to_char(g.fecha_creado,'HH24:MI') as hora,(gt.nombre||'-('||g.descripcion||')') as descripcion,\n"
                + "TRIM(to_char(g.monto_gasto,'999G999G999')) as monto \n"
                + "from gasto g,gasto_tipo gt,caja_cierre_item cci,caja_cierre_detalle ccd\n"
                + "where g.fk_idgasto_tipo=gt.idgasto_tipo\n"
                + "and ccd.idcaja_cierre_detalle=cci.fk_idcaja_cierre_detalle\n"
                + "and ccd.fk_idgasto=g.idgasto\n"
                + "and cci.fk_idcaja_cierre=" + fk_idcaja_cierre
                + " order by g.fecha_creado desc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_fila_gasto = 0;
            while (rs.next()) {
                g_hora[tk_fila_gasto] = rs.getString("hora");
                g_descripcion[tk_fila_gasto] = rs.getString("descripcion");
                g_total[tk_fila_gasto] = rs.getString("monto");
                tk_fila_gasto++;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }

    }

    private void cargar_datos_garantia(Connection conn, int fk_idcaja_cierre) {
        String titulo = "cargar_datos_garantia";
        String sql = "select (g.responsable||'-'||g.descripcion_objeto) as descripcion,\n"
                + "g.estado,TRIM(to_char(g.monto_garantia,'999G999G999'))  as monto \n"
                + "from garantia g,caja_cierre_item cci,caja_cierre_detalle ccd\n"
                + "where  ccd.idcaja_cierre_detalle=cci.fk_idcaja_cierre_detalle\n"
                + "and ccd.fk_idgarantia=g.idgarantia\n"
                + "and cci.fk_idcaja_cierre=" + fk_idcaja_cierre
                + " order by g.fecha_creado desc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_fila_garantia = 0;
            while (rs.next()) {
                ga_estado[tk_fila_garantia] = rs.getString("estado");
                ga_descripcion[tk_fila_garantia] = rs.getString("descripcion");
                ga_total[tk_fila_garantia] = rs.getString("monto");
                tk_fila_garantia++;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }

    }

    private String cargar_datos_para_mensaje_textarea() {
        String mensaje_impresora = "";
        String saltolinea = "\n";
        String tabular = "\t";
        String separador = "==========================================";
        mensaje_impresora = mensaje_impresora + "===============" + jsprint.getEmp_nombre() + "================" + saltolinea;
        mensaje_impresora = mensaje_impresora + jsprint.getEmp_telefono() + saltolinea;
        mensaje_impresora = mensaje_impresora + jsprint.getEmp_direccion() + saltolinea;
        mensaje_impresora = mensaje_impresora + "NRO CIERRE:" + cd_fk_idcaja_cierre + saltolinea;
        mensaje_impresora = mensaje_impresora + "FEC-INI: " + cd_fec_inicio + saltolinea;
        mensaje_impresora = mensaje_impresora + "FEC-FIN: " + cd_fec_fin + saltolinea;
        mensaje_impresora = mensaje_impresora + "USUARIO:" + cd_creado_por + saltolinea;
        mensaje_impresora = mensaje_impresora + separador + saltolinea;
        for (int i = 0; i < tk_iv_fila_ocupa; i++) {
            mensaje_impresora = mensaje_impresora + icd_fecha[i] + saltolinea;
            mensaje_impresora = mensaje_impresora + icd_descripcion[i] + saltolinea;
            String item = icd_ocupacion[i] + tabular + icd_consumo[i] + tabular + icd_ingreso[i] + saltolinea;
            mensaje_impresora = mensaje_impresora + item;
        }
        mensaje_impresora = mensaje_impresora + separador + saltolinea;
        for (int i = 0; i < tk_iv_fila_consu; i++) {
            mensaje_impresora = mensaje_impresora + iv_descrip[i] + saltolinea;
            String item = iv_cant[i] + tabular + iv_subsotal[i] + saltolinea;
            mensaje_impresora = mensaje_impresora + item;
        }
        mensaje_impresora = mensaje_impresora + separador + saltolinea;
        for (int i = 0; i < tk_ivi_fila_consu; i++) {
            mensaje_impresora = mensaje_impresora + ivi_descrip[i] + saltolinea;
            String item = ivi_cant[i] + tabular + ivi_subsotal[i] + saltolinea;
            mensaje_impresora = mensaje_impresora + item;
        }
        mensaje_impresora = mensaje_impresora + separador + saltolinea;
        mensaje_impresora = mensaje_impresora + "TT. MINIMO: " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + pos.alinearDerecha(cd_tt_minimo, cant_char) + saltolinea;
        mensaje_impresora = mensaje_impresora + "TT. ADICIONAL: " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + pos.alinearDerecha(cd_tt_adicional, cant_char) + saltolinea;
        mensaje_impresora = mensaje_impresora + "TT. CONSUMO: " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + pos.alinearDerecha(cd_tt_consumo, cant_char) + saltolinea;
        mensaje_impresora = mensaje_impresora + "TT. DESCUENTO: " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + pos.alinearDerecha(cd_tt_descuento, cant_char) + saltolinea;
        mensaje_impresora = mensaje_impresora + "TT. ADELANTO: " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + pos.alinearDerecha(cd_tt_adelanto, cant_char) + saltolinea;
        mensaje_impresora = mensaje_impresora + "TT. VEN-INTERNO: " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + pos.alinearDerecha(cd_tt_ven_interno, cant_char) + saltolinea;
        mensaje_impresora = mensaje_impresora + "TT. GARANTIA: " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + pos.alinearDerecha(cd_tt_garantia, cant_char) + saltolinea;
        mensaje_impresora = mensaje_impresora + "===>TOTAL  INGRESO: " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + pos.alinearDerecha(cd_tt_ingreso, cant_char) + saltolinea;
        mensaje_impresora = mensaje_impresora + separador + saltolinea;
        mensaje_impresora = mensaje_impresora + "TT. GASTO : " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + cd_tt_gasto + saltolinea;
        mensaje_impresora = mensaje_impresora + "TT. COMPRA : " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + cd_tt_compra + saltolinea;
        mensaje_impresora = mensaje_impresora + "TT. VALE : " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + cd_tt_vale + saltolinea;
        mensaje_impresora = mensaje_impresora + "TT. LIQUIDACION : " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + cd_tt_liquida + saltolinea;
        mensaje_impresora = mensaje_impresora + "===>TOTAL EGRESO : " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + cd_tt_egreso + saltolinea;
        mensaje_impresora = mensaje_impresora + separador + saltolinea;
        mensaje_impresora = mensaje_impresora + "===>TOTAL SALDO : " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + pos.alinearDerecha(cd_tt_saldo, cant_char) + saltolinea;
        return mensaje_impresora;
    }

    private static void crear_archivo_texto_impresion() throws PrintException, FileNotFoundException, IOException {
        int totalColumna = jsprint.getTotal_columna();
        PrinterMatrix printer = new PrinterMatrix();
        Extenso e = new Extenso();
        e.setNumber(101.85);
        //Definir el tamanho del papel 
        int tempfila = 0;
        int totalfila = jsprint.getTt_fila_cc() + (tk_iv_fila_ocupa * 2) + (tk_iv_fila_consu * 2) + (tk_ivi_fila_consu * 2) + (tk_fila_gasto * 2)+ (tk_fila_garantia * 2);
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(1 + tempfila, 1, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_cabezera() + jsprint.getEmp_nombre() + jsprint.getLinea_cabezera());
        printer.printTextWrap(2 + tempfila, 2, jsprint.getSep_inicio(), totalColumna, jsprint.getEmp_telefono());
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_inicio(), totalColumna, jsprint.getEmp_direccion());
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_inicio(), totalColumna, "NRO CAJA:" + cd_fk_idcaja_cierre);
        printer.printTextWrap(5 + tempfila, 5, jsprint.getSep_inicio(), totalColumna, "FEC INI: " + cd_fec_inicio);
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_inicio(), totalColumna, "FEC FIN: " + cd_fec_fin);
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_inicio(), totalColumna, "USUARIO:" + cd_creado_por);
        printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        if (tk_iv_fila_ocupa > 0) {
            printer.printTextWrap(9 + tempfila, 9, jsprint.getSep_inicio(), totalColumna, "FEC-DESCRIP-OCUPA-CONSU-TOTAL");
        } else {
            tempfila = tempfila - 3;
        }
        for (int i = 0; i < tk_iv_fila_ocupa; i++) {
//            printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_inicio(), totalColumna, icd_fecha[i]);
            printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_inicio(), jsprint.getTt_text_descrip(), icd_descripcion[i]);
            printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_inicio(), totalColumna, pos.alinearDerecha(icd_ocupacion[i], cant_char));
            printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_item_precio(), totalColumna, pos.alinearDerecha(icd_consumo[i], cant_char));
            printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_item_subtotal(), totalColumna, pos.alinearDerecha(icd_ingreso[i], cant_char));
            tempfila = tempfila + 2;
        }
        tk_iv_fila_ocupa = 0;
        if (tk_iv_fila_consu > 0) {
            printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
            printer.printTextWrap(13 + tempfila, 13, jsprint.getSep_inicio(), totalColumna, "=>CANT - (V.CONSUMO) - SUBTOTAL");
        } else {
            tempfila = tempfila - 2;
        }
        for (int i = 0; i < tk_iv_fila_consu; i++) {
            printer.printTextWrap(14 + tempfila, 14, jsprint.getSep_inicio(), jsprint.getTt_text_descrip(), iv_descrip[i]);
            printer.printTextWrap(15 + tempfila, 15, jsprint.getSep_inicio(), totalColumna, pos.alinearDerecha(iv_cant[i], 3));
            printer.printTextWrap(15 + tempfila, 15, jsprint.getSep_item_subtotal(), totalColumna, pos.alinearDerecha(iv_subsotal[i], cant_char));
            tempfila = tempfila + 2;
        }
        tk_iv_fila_consu = 0;
        if (tk_ivi_fila_consu > 0) {
            printer.printTextWrap(15 + tempfila, 15, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
            printer.printTextWrap(16 + tempfila, 16, jsprint.getSep_inicio(), totalColumna, "=>CANT - (V.INTERNO) - SUBTOTAL");
        } else {
            tempfila = tempfila - 2;
        }
        for (int i = 0; i < tk_ivi_fila_consu; i++) {
            printer.printTextWrap(17 + tempfila, 17, jsprint.getSep_inicio(), jsprint.getTt_text_descrip(), ivi_descrip[i]);
            printer.printTextWrap(18 + tempfila, 18, jsprint.getSep_inicio(), totalColumna, pos.alinearDerecha(ivi_cant[i], 3));
            printer.printTextWrap(18 + tempfila, 18, jsprint.getSep_item_subtotal(), totalColumna, pos.alinearDerecha(ivi_subsotal[i], cant_char));
            tempfila = tempfila + 2;
        }
        tk_ivi_fila_consu = 0;
        if (tk_fila_gasto > 0) {
            printer.printTextWrap(18 + tempfila, 18, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
            printer.printTextWrap(19 + tempfila, 19, jsprint.getSep_inicio(), totalColumna, "=>HORA - (GASTOS-CAJA) - TOTAL");
        } else {
            tempfila = tempfila - 2;
        }
        for (int i = 0; i < tk_fila_gasto; i++) {
            printer.printTextWrap(20 + tempfila, 20, jsprint.getSep_inicio(), jsprint.getTt_text_descrip(), g_descripcion[i]);
            printer.printTextWrap(21 + tempfila, 21, jsprint.getSep_inicio(), totalColumna, pos.alinearDerecha(g_hora[i], 6));
            printer.printTextWrap(21 + tempfila, 21, jsprint.getSep_item_subtotal(), totalColumna, pos.alinearDerecha(g_total[i], cant_char));
            tempfila = tempfila + 2;
        }
        tk_fila_gasto = 0;
        if (tk_fila_garantia > 0) {
            printer.printTextWrap(21 + tempfila, 21, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
            printer.printTextWrap(22 + tempfila, 22, jsprint.getSep_inicio(), totalColumna, "=>ESTADO - (GARANTIA-CAJA) - TOTAL");
        } else {
            tempfila = tempfila - 2;
        }
        for (int i = 0; i < tk_fila_garantia; i++) {
            printer.printTextWrap(23 + tempfila, 23, jsprint.getSep_inicio(), jsprint.getTt_text_descrip(), ga_descripcion[i]);
            printer.printTextWrap(24 + tempfila, 24, jsprint.getSep_inicio(), totalColumna, ga_estado[i]);
            printer.printTextWrap(24 + tempfila, 24, jsprint.getSep_item_subtotal(), totalColumna, pos.alinearDerecha(ga_total[i], cant_char));
            tempfila = tempfila + 2;
        }
        tk_fila_garantia = 0;
        printer.printTextWrap(25 + tempfila, 25, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(26 + tempfila, 26, jsprint.getSep_inicio(), totalColumna, "###-DATOS INGRESO-###");
        printer.printTextWrap(27 + tempfila, 27, jsprint.getSep_inicio(), totalColumna, "TT. MINIMO:");
        printer.printTextWrap(27 + tempfila, 27, jsprint.getSep_item_subtotal(), totalColumna, pos.alinearDerecha(cd_tt_minimo, cant_char));
        printer.printTextWrap(28 + tempfila, 28, jsprint.getSep_inicio(), totalColumna, "TT. ADICIONAL:");
        printer.printTextWrap(28 + tempfila, 28, jsprint.getSep_item_subtotal(), totalColumna, pos.alinearDerecha(cd_tt_adicional, cant_char));
        printer.printTextWrap(29 + tempfila, 29, jsprint.getSep_inicio(), totalColumna, "TT. CONSUMO:");
        printer.printTextWrap(29 + tempfila, 29, jsprint.getSep_item_subtotal(), totalColumna, pos.alinearDerecha(cd_tt_consumo, cant_char));
        printer.printTextWrap(30 + tempfila, 30, jsprint.getSep_inicio(), totalColumna, "TT. DESCUENTO:");
        printer.printTextWrap(30 + tempfila, 30, jsprint.getSep_item_subtotal(), totalColumna, pos.alinearDerecha(cd_tt_descuento, cant_char));
        printer.printTextWrap(31 + tempfila, 31, jsprint.getSep_inicio(), totalColumna, "TT. ADELANTO:");
        printer.printTextWrap(31 + tempfila, 31, jsprint.getSep_item_subtotal(), totalColumna, pos.alinearDerecha(cd_tt_adelanto, cant_char));
        printer.printTextWrap(32 + tempfila, 32, jsprint.getSep_inicio(), totalColumna, "TT. VEN-INTERNO:");
        printer.printTextWrap(32 + tempfila, 32, jsprint.getSep_item_subtotal(), totalColumna, pos.alinearDerecha(cd_tt_ven_interno, cant_char));
        printer.printTextWrap(33 + tempfila, 33, jsprint.getSep_inicio(), totalColumna, "TT. GARANTIA:");
        printer.printTextWrap(33 + tempfila, 33, jsprint.getSep_item_subtotal(), totalColumna, pos.alinearDerecha(cd_tt_garantia, cant_char));
        printer.printTextWrap(34 + tempfila, 34, jsprint.getSep_inicio(), totalColumna, "===>TOTAL INGRESO: ");
        printer.printTextWrap(34 + tempfila, 34, jsprint.getSep_total_gral(), totalColumna, pos.alinearDerecha(cd_tt_ingreso, cant_char));
        printer.printTextWrap(35 + tempfila, 35, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(36 + tempfila, 36, jsprint.getSep_inicio(), totalColumna, "###-DATOS EGRESO-###");
        printer.printTextWrap(37 + tempfila, 37, jsprint.getSep_inicio(), totalColumna, "TT. COMPRA : ");
        printer.printTextWrap(37 + tempfila, 37, jsprint.getSep_item_subtotal(), totalColumna, pos.alinearDerecha(cd_tt_compra, cant_char));
        printer.printTextWrap(38 + tempfila, 38, jsprint.getSep_inicio(), totalColumna, "TT. VALE : ");
        printer.printTextWrap(38 + tempfila, 38, jsprint.getSep_item_subtotal(), totalColumna, pos.alinearDerecha(cd_tt_vale, cant_char));
        printer.printTextWrap(39 + tempfila, 39, jsprint.getSep_inicio(), totalColumna, "TT. LIQUIDACION : ");
        printer.printTextWrap(39 + tempfila, 39, jsprint.getSep_item_subtotal(), totalColumna, pos.alinearDerecha(cd_tt_liquida, cant_char));
        printer.printTextWrap(40 + tempfila, 40, jsprint.getSep_inicio(), totalColumna, "TT. GASTO : ");
        printer.printTextWrap(40 + tempfila, 40, jsprint.getSep_item_subtotal(), totalColumna, pos.alinearDerecha(cd_tt_gasto, cant_char));
        printer.printTextWrap(41 + tempfila, 41, jsprint.getSep_inicio(), totalColumna, "===>TOTAL EGRESO : ");
        printer.printTextWrap(41 + tempfila, 41, jsprint.getSep_total_gral(), totalColumna, pos.alinearDerecha(cd_tt_egreso, cant_char));
        printer.printTextWrap(42 + tempfila, 42, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(43 + tempfila, 43, jsprint.getSep_inicio(), totalColumna, "===>TOTAL SALDO : ");
        printer.printTextWrap(43 + tempfila, 43, jsprint.getSep_total_gral(), totalColumna, pos.alinearDerecha(cd_tt_saldo, cant_char));
        printer.toFile(tk_ruta_archivo);
        try {
            inputStream = new FileInputStream(tk_ruta_archivo);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.err.println(ex);
        }
        if (inputStream == null) {
            return;
        }

    }

    private void crear_archivo_enviar_impresora() {
        String titulo = "crear_archivo_enviar_impresora";
        if (jsprint.isError_carga_json()) {
            try {
                crear_archivo_texto_impresion();
                pos.setInputStream(inputStream);
                pos.imprimir_ticket_Pos();
            } catch (Exception e) {
                evemen.mensaje_error(e, titulo);
            }
        } else {
            jsprint.cargar_jsom_imprimir_pos();
        }
    }

    private void crear_mensaje_textarea_y_confirmar() {
        JTextArea ta = new JTextArea(30, 30);
        ta.setText(cargar_datos_para_mensaje_textarea());
        System.out.println(cargar_datos_para_mensaje_textarea());
        Object[] opciones = {"IMPRIMIR", "CANCELAR"};
        int eleccion = JOptionPane.showOptionDialog(null, new JScrollPane(ta), tk_ruta_archivo,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, "IMPRIMIR");
        if (eleccion == JOptionPane.YES_OPTION) {
            crear_archivo_enviar_impresora();
        }
    }

    public void boton_imprimir_pos_CAJA_DETALLE(Connection conn, int fk_idcaja_cierre, boolean con_detalle) {
        cargar_datos_caja(conn, fk_idcaja_cierre);
        if (con_detalle) {
            cargar_datos_item_caja_detalle_venta(conn, fk_idcaja_cierre);
            cargar_datos_item_venta(conn, fk_idcaja_cierre);
            cargar_datos_item_venta_interna(conn, fk_idcaja_cierre);
            cargar_datos_gasto(conn, fk_idcaja_cierre);
            cargar_datos_garantia(conn, fk_idcaja_cierre);
        }
        crear_mensaje_textarea_y_confirmar();
    }
}
