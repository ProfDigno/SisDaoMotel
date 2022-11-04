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
public class PosImprimir_Venta {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    ClaImpresoraPos pos = new ClaImpresoraPos();
    private static json_array_imprimir_pos jsprint = new json_array_imprimir_pos();
    private static int vec_limit=300;
    private static String tk_ruta_archivo = "ticket_ocupa.txt";
    private static String[] iv1_cantidad = new String[vec_limit];
    private static String[] iv2_precio = new String[vec_limit];
    private static String[] iv3_total = new String[vec_limit];
    private static String[] iv4_descripcion = new String[vec_limit];
    private static FileInputStream inputStream = null;
    private static int tk_iv_fila = 0;
    private String nombre_ticket = "ticket Ocupacion";
    private static String v1_idv = "0";
    private static String v2_fec_ini = "0";
    private static String v3_fec_fin = "0";
    private static String v4_habitacion;
    private static String v5_tiempo;
    private static String v6_tipo;
    private static String v7_m_minimo;
    private static String v8_cant_add;
    private static String v9_m_adicional;
    private static String v10_m_consumo;
    private static String v11_m_descuento;
    private static String v12_m_adelanto;
    private static String v13_m_pagar;
    private static boolean es_consumo;
    private static String v14_usuario;

    private void cargar_datos_venta(Connection conn, int idventa) {
        String titulo = "cargar_datos_venta";
        String sql = "select v.idventa as idv,\n"
                + "TRIM(to_char(hr.fec_ocupado_inicio ,'yyyy-MM-dd HH24:MI')) as fec_ini,\n"
                + "TRIM(to_char(hr.fec_ocupado_fin,'yyyy-MM-dd HH24:MI')) as fec_fin,\n"
                + "hr.nro_habitacion as habitacion,\n"
                + "TRIM(to_char((hr.fec_ocupado_fin-hr.fec_ocupado_inicio), 'HH24:MI:ss')) as tiempo,\n"
                + "case when hr.es_por_hora=true then 'POR HORA'\n"
                + "     when hr.es_por_dormir=true then 'DORMIR'\n"
                + "     else 'sin-tipo' end as tipo,\n"
                + "TRIM(to_char(v.monto_minimo,'999G999G999')) as m_minimo,\n"
                + "v.cant_adicional as cant_add,\n"
                + "TRIM(to_char(v.monto_adicional,'999G999G999')) as m_adicional,\n"
                + "TRIM(to_char(v.monto_consumo,'999G999G999')) as m_consumo,\n"
                + "TRIM(to_char(v.monto_descuento,'999G999G999')) as m_descuento,\n"
                + "TRIM(to_char(v.monto_adelanto,'999G999G999')) as m_adelanto,\n"
                + "TRIM(to_char(((v.monto_minimo+v.monto_adicional+v.monto_consumo)-"
                + "(v.monto_descuento+v.monto_adelanto)),'999G999G999')) as m_pagar,\n"
                + "case when v.monto_consumo>0 then true else false end as es_consumo,"
                + "u.nombre as usuario\n"
                + "from venta v,habitacion_recepcion hr,usuario u  \n"
                + "where v.fk_idhabitacion_recepcion=hr.idhabitacion_recepcion\n"
                + "and v.fk_idusuario=u.idusuario \n"
                + "and v.idventa=" + idventa;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                v1_idv = rs.getString("idv");
                v2_fec_ini = rs.getString("fec_ini");
                v3_fec_fin = rs.getString("fec_fin");
                v4_habitacion = rs.getString("habitacion");
                v5_tiempo = rs.getString("tiempo");
                v6_tipo = rs.getString("tipo");
                v7_m_minimo = rs.getString("m_minimo");
                v8_cant_add = rs.getString("cant_add");
                v9_m_adicional = rs.getString("m_adicional");
                v10_m_consumo = rs.getString("m_consumo");
                es_consumo = rs.getBoolean("es_consumo");
                v11_m_descuento = rs.getString("m_descuento");
                v12_m_adelanto = rs.getString("m_adelanto");
                v13_m_pagar = rs.getString("m_pagar");
                v14_usuario = rs.getString("usuario");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void cargar_datos_venta_item(Connection conn, int idventa) {
        if (es_consumo) {
            String titulo = "cargar_datos_venta_item";
            String sql = "select vi.descripcion as descripcion,\n"
                    + "vi.cantidad as cant,\n"
                    + "TRIM(to_char(vi.precio_venta,'999G999G999')) as pventa,\n"
                    + "TRIM(to_char((vi.cantidad * vi.precio_venta),'999G999G999')) as subtotal\n"
                    + "from venta_item vi where vi.fk_idventa=" + idventa;
            try {
                ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
                tk_iv_fila = 0;
                while (rs.next()) {
                    iv4_descripcion[tk_iv_fila] = rs.getString("descripcion");
                    iv1_cantidad[tk_iv_fila] = rs.getString("cant");
                    iv2_precio[tk_iv_fila] = rs.getString("pventa");
                    iv3_total[tk_iv_fila] = rs.getString("subtotal");
                    tk_iv_fila++;
                }
            } catch (Exception e) {
                evemen.mensaje_error(e, sql, titulo);
            }
        } else {
            tk_iv_fila = 0;
        }
    }

    private String cargar_datos_para_mensaje_textarea() {
        int totalfila = jsprint.getTt_fila_ven() + (tk_iv_fila * 2);
        String mensaje_impresora = "";
        String saltolinea = "\n";
        String tabular = "\t";
        String separador = "==========================================";
        mensaje_impresora = mensaje_impresora + "===============" + jsprint.getEmp_nombre() + "================" + saltolinea;
        mensaje_impresora = mensaje_impresora + jsprint.getEmp_telefono() + saltolinea;
        mensaje_impresora = mensaje_impresora + jsprint.getEmp_direccion() + saltolinea;
        mensaje_impresora = mensaje_impresora + "CODIGO VENTA:" + v1_idv + saltolinea;
        mensaje_impresora = mensaje_impresora + "FEC-INICIO: " + v2_fec_ini + saltolinea;
        mensaje_impresora = mensaje_impresora + "FEC-FIN: " + v3_fec_fin + saltolinea;
        mensaje_impresora = mensaje_impresora + "HAB: " + v4_habitacion + " TIEMPO:" + v5_tiempo + saltolinea;
        mensaje_impresora = mensaje_impresora + "TIPO: " + v6_tipo + saltolinea;
        mensaje_impresora = mensaje_impresora + "USUARIO:" + v14_usuario + saltolinea;
        mensaje_impresora = mensaje_impresora + "TOTAL FILA:" + totalfila + saltolinea;
        if (es_consumo) {
            mensaje_impresora = mensaje_impresora + separador + saltolinea;
            for (int i = 0; i < tk_iv_fila; i++) {
                mensaje_impresora = mensaje_impresora + iv4_descripcion[i] + saltolinea;
                String item = iv1_cantidad[i] + tabular + iv2_precio[i] + tabular + iv3_total[i] + saltolinea;
                mensaje_impresora = mensaje_impresora + item;
            }
        }
        mensaje_impresora = mensaje_impresora + separador + saltolinea;
        mensaje_impresora = mensaje_impresora + "MONTO MINIMO: " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + v7_m_minimo + saltolinea;
        mensaje_impresora = mensaje_impresora + "MONTO ADICIONAL: " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + v9_m_adicional + saltolinea;
        mensaje_impresora = mensaje_impresora + "MONTO CONSUMO: " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + v10_m_consumo + saltolinea;
        mensaje_impresora = mensaje_impresora + "MONTO DESCUENTO: " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + v11_m_descuento + saltolinea;
        mensaje_impresora = mensaje_impresora + "MONTO ADELANTO: " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + v12_m_adelanto + saltolinea;
        mensaje_impresora = mensaje_impresora + separador + saltolinea;
        mensaje_impresora = mensaje_impresora + "TOTAL PAGAR : " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + v13_m_pagar + saltolinea;
        return mensaje_impresora;
    }

    private static void crear_archivo_texto_impresion() throws PrintException, FileNotFoundException, IOException {
        int totalColumna = jsprint.getTotal_columna();
        PrinterMatrix printer = new PrinterMatrix();
        Extenso e = new Extenso();
        e.setNumber(101.85);
        //Definir el tamanho del papel 
        int tempfila = 0;
        int totalfila = jsprint.getTt_fila_ven() + (tk_iv_fila * 2);
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(1 + tempfila, 1, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_cabezera() + jsprint.getEmp_nombre() + jsprint.getLinea_cabezera());
        printer.printTextWrap(2 + tempfila, 2, jsprint.getSep_inicio(), totalColumna, jsprint.getEmp_telefono());
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_inicio(), totalColumna, jsprint.getEmp_direccion());
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_inicio(), totalColumna, "COD. VENTA:" + v1_idv);
        printer.printTextWrap(5 + tempfila, 5, jsprint.getSep_inicio(), totalColumna, "FEC INICIO:" + v2_fec_ini);
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_inicio(), totalColumna, "FEC FIN:" + v3_fec_fin);
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_inicio(), totalColumna, "HABITACION:" + v4_habitacion);
        printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_inicio(), totalColumna, "TIEMPO OCUPA:" + v5_tiempo);
        printer.printTextWrap(9 + tempfila, 9, jsprint.getSep_inicio(), totalColumna, "USUARIO:" + v14_usuario);
        printer.printTextWrap(10 + tempfila, 10, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_inicio(), totalColumna, "CANT-DESCRIPCION-PRECIO-TOTAL");
        if (es_consumo) {
            for (int i = 0; i < tk_iv_fila; i++) {
                printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_inicio(), jsprint.getTt_text_descrip(), iv4_descripcion[i]);
                printer.printTextWrap(13 + tempfila, 13, jsprint.getSep_item_cant(), totalColumna, iv1_cantidad[i] + " X");
                printer.printTextWrap(13 + tempfila, 13, jsprint.getSep_item_precio(), totalColumna, iv2_precio[i] + " =");
                printer.printTextWrap(13 + tempfila, 13, jsprint.getSep_item_subtotal(), totalColumna, iv3_total[i]);
                tempfila = tempfila + 2;
            }
            tk_iv_fila=0;
        }
        printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(13 + tempfila, 13, jsprint.getSep_inicio(), totalColumna, "TIPO : " + v6_tipo);
        printer.printTextWrap(14 + tempfila, 14, jsprint.getSep_inicio(), totalColumna, "MONTO MINIMO:");
        printer.printTextWrap(14 + tempfila, 14, jsprint.getSep_total_gral(), totalColumna, v7_m_minimo);
        printer.printTextWrap(15 + tempfila, 15, jsprint.getSep_inicio(), totalColumna, "MONTO ADICIONAL:");
        printer.printTextWrap(15 + tempfila, 15, jsprint.getSep_total_gral(), totalColumna, v9_m_adicional);
        printer.printTextWrap(16 + tempfila, 16, jsprint.getSep_inicio(), totalColumna, "MONTO CONSUMO:");
        printer.printTextWrap(16 + tempfila, 16, jsprint.getSep_total_gral(), totalColumna, v10_m_consumo);
        printer.printTextWrap(17 + tempfila, 17, jsprint.getSep_inicio(), totalColumna, "MONTO DESCUENTO:");
        printer.printTextWrap(17 + tempfila, 17, jsprint.getSep_total_gral(), totalColumna, v11_m_descuento);
        printer.printTextWrap(18 + tempfila, 18, jsprint.getSep_inicio(), totalColumna, "MONTO ADELANTO:");
        printer.printTextWrap(18 + tempfila, 18, jsprint.getSep_total_gral(), totalColumna, v12_m_adelanto);
        printer.printTextWrap(19 + tempfila, 19, jsprint.getSep_inicio(), totalColumna, "TOTAL PAGAR:");
        printer.printTextWrap(19 + tempfila, 19, jsprint.getSep_total_gral(), totalColumna, v13_m_pagar);
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
        if (jsprint.isError_carga_json()) {
            String titulo = "crear_archivo_enviar_impresora";
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

    public void boton_imprimir_pos_VENTA(Connection conn, int idventa) {
        cargar_datos_venta(conn, idventa);
        cargar_datos_venta_item(conn, idventa);
        crear_mensaje_textarea_y_confirmar();
    }
}
