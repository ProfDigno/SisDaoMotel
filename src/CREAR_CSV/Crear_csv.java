/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CREAR_CSV;

import BASEDATO.EvenConexion;
import CONFIGURACION.ComputerInfo;
import Config_JSON.json_array_csv;
import Evento.Fecha.EvenFecha;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.caja_cierre_detalle;
import FORMULARIO.ENTIDAD.habitacion_recepcion_temp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author Digno
 */
public class Crear_csv {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    json_array_csv jscsv = new json_array_csv();
    ComputerInfo pcinf = new ComputerInfo();
    private String carpeta_raiz = jscsv.getCarpeta_raiz();
    private String carpeta_local = jscsv.getCarpeta_local();
    private String carpeta_dropbox = jscsv.getCarpeta_dropbox();
    private String delimitador = jscsv.getDelimitador();
    private static String nombre_columna = "";
    String extencion = ".csv";

    public void crear_todas_carpetas() {
        crear_carpeta_no_existe(jscsv.getCarpeta_raiz());
        crear_carpeta_no_existe(jscsv.getCarpeta_local());
        crear_carpeta_no_existe(jscsv.getCarpeta_raiz_ser());
        crear_carpeta_no_existe(jscsv.getCarpeta_dropbox());
    }
    public void leer_csv_servidor(){
        
        String cli_carpeta = jscsv.getCli_carpeta();
        String ser_carpeta_raiz = jscsv.getSer_carpeta_raiz();
        String ser_carpeta_dropbox = jscsv.getSer_carpeta_dropbox();
        String cli_pc = jscsv.getCli_pc();
        String[] vcli_pc = cli_pc.split("#");
        for (int i = 0; i < vcli_pc.length; i++) {
            System.out.println("PC: " + (i + 1) + ": " + vcli_pc[i]);
        }
        
    }
    public void Importar_csv_server_caja_cierre(Connection conn,String nombre_computador,String carpeta_raiz_ser,String carpeta_dropbox) {
        if (jscsv.isActivar_exportar()) {
//            String nombre_computador = pcinf.getNombrePC();
//            String carpeta_raiz_ser = jscsv.getCarpeta_raiz_ser();
//            String carpeta_dropbox = jscsv.getCarpeta_dropbox();
            Importar_csv_server(conn, "caja_cierre_temp", "caja_cierre", carpeta_raiz_ser, carpeta_dropbox, extencion);
            Importar_csv_server(conn, "caja_cierre_item_temp", "caja_cierre_item", carpeta_raiz_ser, carpeta_dropbox, extencion);
            Importar_csv_server(conn, "caja_cierre_detalle_temp", "caja_cierre_detalle", carpeta_raiz_ser, carpeta_dropbox, extencion);
            Importar_csv_server(conn, "gasto_temp", "gasto", carpeta_raiz_ser, carpeta_dropbox, extencion);
            Importar_csv_server(conn, "gasto_tipo_temp", "gasto_tipo", carpeta_raiz_ser, carpeta_dropbox, extencion);
            String select_detalle = "INSERT INTO admin_caja_cierre_detalle (idcaja_cierre_detalle, fecha_creado, creado_por, cerrado_por, \n"
                    + "       es_cerrado, monto_apertura_caja, monto_cierre_caja, monto_ocupa_minimo, \n"
                    + "       monto_ocupa_adicional, monto_ocupa_consumo, monto_ocupa_descuento, \n"
                    + "       monto_ocupa_adelanto, monto_gasto, monto_compra, monto_vale, \n"
                    + "       monto_liquidacion,monto_interno,monto_solo_adelanto, monto_garantia, \n"
                    + "       estado, descripcion, fk_idgasto, fk_idcompra, \n"
                    + "       fk_idventa, fk_idusuario, fk_idrh_vale, fk_idrh_liquidacion, \n"
                    + "       fk_idventa_interno,fk_idgarantia,maquina)\n"
                    + "SELECT idcaja_cierre_detalle, fecha_creado, creado_por, cerrado_por, \n"
                    + "       es_cerrado, monto_apertura_caja, monto_cierre_caja, monto_ocupa_minimo, \n"
                    + "       monto_ocupa_adicional, monto_ocupa_consumo, monto_ocupa_descuento, \n"
                    + "       monto_ocupa_adelanto, monto_gasto, monto_compra, monto_vale, \n"
                    + "       monto_liquidacion,monto_interno,monto_solo_adelanto, monto_garantia, \n"
                    + "       estado, descripcion, fk_idgasto, fk_idcompra, \n"
                    + "       fk_idventa, fk_idusuario, fk_idrh_vale, fk_idrh_liquidacion, \n"
                    + "       fk_idventa_interno,fk_idgarantia,maquina\n"
                    + "FROM caja_cierre_detalle_temp\n"
                    + "WHERE maquina='" + nombre_computador + "' and idcaja_cierre_detalle \n"
                    + "NOT IN (SELECT idcaja_cierre_detalle FROM admin_caja_cierre_detalle where maquina='" + nombre_computador + "');";
            String select_item = "INSERT INTO admin_caja_cierre_item (idcaja_cierre_item, fecha_creado, creado_por, \n"
                    + "       fk_idcaja_cierre, fk_idcaja_cierre_detalle,maquina)\n"
                    + "SELECT idcaja_cierre_item, fecha_creado, creado_por, \n"
                    + "       fk_idcaja_cierre, fk_idcaja_cierre_detalle,maquina\n"
                    + "FROM caja_cierre_item_temp\n"
                    + "WHERE maquina='" + nombre_computador + "' and idcaja_cierre_item \n"
                    + "NOT IN (SELECT idcaja_cierre_item FROM admin_caja_cierre_item where maquina='" + nombre_computador + "');";
            String select_cierre = "INSERT INTO admin_caja_cierre (idcaja_cierre, fecha_creado,creado_por,\n"
                    + "fecha_inicio,fecha_fin,estado,fk_idusuario,maquina)\n"
                    + "SELECT idcaja_cierre, fecha_creado,creado_por,\n"
                    + "fecha_inicio,fecha_fin,estado,fk_idusuario,maquina\n"
                    + "FROM caja_cierre_temp\n"
                    + "WHERE maquina='" + nombre_computador + "' and idcaja_cierre \n"
                    + "NOT IN (SELECT idcaja_cierre FROM admin_caja_cierre where maquina='" + nombre_computador + "');";
            String select_gasto = "INSERT INTO admin_gasto (idgasto, fecha_creado, creado_por, monto_gasto, monto_letra, descripcion, estado, fk_idusuario, fk_idgasto_tipo,maquina)\n"
                    + "SELECT idgasto, fecha_creado, creado_por, monto_gasto, monto_letra, descripcion, estado, fk_idusuario, fk_idgasto_tipo,maquina\n"
                    + "FROM gasto_temp\n"
                    + "WHERE maquina='" + nombre_computador + "' and idgasto \n"
                    + "NOT IN (SELECT idgasto FROM admin_gasto where maquina='" + nombre_computador + "');";
            String select_tgasto = "INSERT INTO admin_gasto_tipo (idgasto_tipo, fecha_creado, creado_por, nombre, activo,maquina)\n"
                    + "SELECT idgasto_tipo, fecha_creado, creado_por, nombre, activo,maquina\n"
                    + "FROM gasto_tipo_temp\n"
                    + "WHERE maquina='" + nombre_computador + "' and idgasto_tipo \n"
                    + "NOT IN (SELECT idgasto_tipo FROM admin_gasto_tipo where maquina='" + nombre_computador + "');";
            eveconn.SQL_execute_libre(conn, select_detalle);
            eveconn.SQL_execute_libre(conn, select_item);
            eveconn.SQL_execute_libre(conn, select_cierre);
            eveconn.SQL_execute_libre(conn, select_gasto);
            eveconn.SQL_execute_libre(conn, select_tgasto);
            eveconn.SQL_execute_commit(conn);
        }
    }

    public void crear_csv_local_caja_cierre(Connection conn) {
        if (jscsv.isActivar_exportar()) {
            crear_todas_carpetas();
            String carpeta_raiz = jscsv.getCarpeta_raiz();
            String carpeta_local = jscsv.getCarpeta_local();
            String carpeta_dropbox = jscsv.getCarpeta_dropbox();
            String dias_filtro = jscsv.getDias_desde();
            String nombre_computador = pcinf.getNombrePC();
            String select_detalle = " idcaja_cierre_detalle, fecha_creado, creado_por, cerrado_por, \n"
                    + "       es_cerrado, monto_apertura_caja, monto_cierre_caja, monto_ocupa_minimo, \n"
                    + "       monto_ocupa_adicional, monto_ocupa_consumo, monto_ocupa_descuento, \n"
                    + "       monto_ocupa_adelanto, monto_gasto, monto_compra, monto_vale, \n"
                    + "       monto_liquidacion,monto_interno,monto_solo_adelanto, monto_garantia, \n"
                    + "       estado, descripcion, fk_idgasto, fk_idcompra, \n"
                    + "       fk_idventa, fk_idusuario, fk_idrh_vale, fk_idrh_liquidacion, \n"
                    + "       fk_idventa_interno,fk_idgarantia,('" + nombre_computador + "') as maquina ";
            String select_item = "  idcaja_cierre_item, fecha_creado, creado_por, \n"
                    + "       fk_idcaja_cierre, fk_idcaja_cierre_detalle,('" + nombre_computador + "') as maquina ";
            String select_cierre = "  idcaja_cierre, fecha_creado, creado_por, \n"
                    + "       fecha_inicio, fecha_fin, estado, fk_idusuario,('" + nombre_computador + "') as maquina ";
            limpiar_campo_tabla(conn, "caja_cierre_detalle", "fecha_creado", "descripcion", dias_filtro);
            CSV_eliminar_crear_copiar(conn, "caja_cierre_detalle", select_detalle, "fecha_creado",
                    dias_filtro, carpeta_local, carpeta_raiz, carpeta_dropbox);
            CSV_eliminar_crear_copiar(conn, "caja_cierre_item", select_item, "fecha_creado",
                    dias_filtro, carpeta_local, carpeta_raiz, carpeta_dropbox);
            CSV_eliminar_crear_copiar(conn, "caja_cierre", select_cierre, "fecha_creado",
                    dias_filtro, carpeta_local, carpeta_raiz, carpeta_dropbox);
            String select_gasto = " idgasto, fecha_creado, creado_por, monto_gasto, monto_letra, descripcion, estado,('" + nombre_computador + "') as maquina, fk_idgasto_tipo, fk_idusuario ";
            String select_tgasto = " idgasto_tipo, fecha_creado, creado_por, nombre, activo, ('" + nombre_computador + "') as maquina ";
            limpiar_campo_tabla(conn, "gasto", "fecha_creado", "descripcion", dias_filtro);
            CSV_eliminar_crear_copiar(conn, "gasto", select_gasto, "fecha_creado",
                    dias_filtro, carpeta_local, carpeta_raiz, carpeta_dropbox);
            CSV_eliminar_crear_copiar(conn, "gasto_tipo", select_tgasto, "fecha_creado",
                    dias_filtro, carpeta_local, carpeta_raiz, carpeta_dropbox);
        }
    }

    private void crear_carpeta_no_existe(String nombreCarpeta) {
        File carpeta = new File(nombreCarpeta);
        if (!carpeta.exists()) {
            try {
                boolean exito = carpeta.mkdir();
                if (exito) {
                    System.out.println("La carpeta se creó exitosamente. " + nombreCarpeta);
//                    JOptionPane.showMessageDialog(null, "La carpeta se creó exitosamente. " + nombreCarpeta);
                } else {
                    System.out.println("No se pudo crear la carpeta. " + nombreCarpeta);
//                    JOptionPane.showMessageDialog(null, "No se pudo crear la carpeta. " + nombreCarpeta);
                }
            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, "ERROR. " + nombreCarpeta+"\n"+e);
            }

        } else {
            System.out.println("La carpeta ya existe. " + nombreCarpeta);
//            JOptionPane.showMessageDialog(null, "La carpeta ya existe." + nombreCarpeta);
        }
    }

    private void limpiar_campo_tabla(Connection conn, String tabla_origen, String fecha, String compo_limpiar, String dias) {
        String sql_salto = "UPDATE " + tabla_origen + " \n"
                + "SET " + compo_limpiar + " = replace(" + compo_limpiar + ", E'\\n', ' ')\n"
                + "WHERE " + compo_limpiar + " LIKE E'%\\n%' "
                + "and " + fecha + " >= current_date - interval '" + dias + " days'\n"
                + "AND " + fecha + " <= current_date ;";
        eveconn.SQL_execute_libre(conn, sql_salto);
        String sql_comilla = "UPDATE " + tabla_origen + " \n"
                + "SET " + compo_limpiar + " = replace(" + compo_limpiar + ", E',', ' ')\n"
                + "WHERE " + compo_limpiar + " LIKE E'%,%' "
                + "and " + fecha + " >= current_date - interval '" + dias + " days'\n"
                + "AND " + fecha + " <= current_date ;";
        eveconn.SQL_execute_libre(conn, sql_comilla);
    }

    private void CSV_eliminar_crear_copiar(Connection conn, String tabla_origen, String select, String fecha, String dias,
            String carpeta_local, String carpeta_raiz, String carpeta_dropbox) {
//        crear_todas_carpetas();
        eliminar_archivo(tabla_origen, carpeta_local, extencion);
        String sql = "SELECT " + select
                + " FROM " + tabla_origen + " \n"
                + "WHERE " + fecha + " >= current_date - interval '" + dias + " days'\n"
                + "AND " + fecha + " <= current_date";
        String sqlcopy = "COPY (" + sql + ") TO '" + carpeta_raiz + tabla_origen + extencion + "' WITH CSV HEADER DELIMITER '" + delimitador + "';";
        eveconn.SQL_execute_libre(conn, sqlcopy);
        copiar_archivo(tabla_origen, carpeta_raiz, carpeta_dropbox, ".csv");
    }

    private void Importar_csv_server(Connection conn, String tabla_temp, String tabla_origen, String carpeta_raiz_ser, String carpeta_dropbox, String extencion) {
        eliminar_archivo(tabla_origen, carpeta_raiz_ser, extencion);
        copiar_archivo_server(tabla_origen, carpeta_raiz_ser, carpeta_dropbox, extencion);
        String sql_truncate = "TRUNCATE " + tabla_temp + ";";
        eveconn.SQL_execute_libre(conn, sql_truncate);
        String sql_copy = "COPY " + tabla_temp + " FROM '" + carpeta_raiz_ser + tabla_origen + extencion + "' DELIMITER '" + delimitador + "' CSV HEADER;";
        eveconn.SQL_execute_libre(conn, sql_copy);
        eveconn.SQL_execute_commit(conn);
    }

    private void copiar_archivo(String tabla_origen, String carpeta_raiz, String carpeta_dropbox, String extencion) {
        File origen = new File(carpeta_raiz + tabla_origen + extencion);
        File destino = new File(carpeta_dropbox);
        System.out.println("COPIAR origen:" + origen);
        System.out.println("COPIAR destino:" + destino);
        try {
            java.nio.file.Files.copy(origen.toPath(), destino.toPath().resolve(origen.getName()));
            System.out.println("Archivo copiado con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al copiar el archivo: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "ERROR AL COPIAR ORIGEN:" + origen + "\nDESTINO:" + destino, "COPIAR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void copiar_archivo_server(String tabla_origen, String carpeta_raiz_ser, String carpeta_dropbox, String extencion) {
        File origen = new File(carpeta_dropbox + tabla_origen + extencion);
        File destino = new File(carpeta_raiz_ser);
        System.out.println("COPIAR origen:" + origen);
        System.out.println("COPIAR destino:" + destino);
        try {
            java.nio.file.Files.copy(origen.toPath(), destino.toPath().resolve(origen.getName()));
            System.out.println("Archivo copiado con éxito.");
//            JOptionPane.showMessageDialog(null, "COPIA EXITOSA ORIGEN:" + origen + "\nDESTINO:" + destino, "COPIAR", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al copiar el archivo: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "ERROR AL COPIAR ORIGEN:" + origen + "\nDESTINO:" + destino, "COPIAR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar_archivo(String tabla_origen, String carpeta_local, String extencion) {
        String rutaArchivo = carpeta_local + tabla_origen + extencion; // Reemplaza con la ruta del archivo que deseas eliminar
        File archivo = new File(rutaArchivo);
        System.out.println("ELIMINAR archivo:" + archivo);
        if (archivo.exists()) {
            if (archivo.delete()) {
                System.out.println("El archivo se ha eliminado exitosamente.");
            } else {
                System.err.println("No se pudo eliminar el archivo.");
            }
        } else {
            System.err.println("El archivo no existe en la ruta especificada.");
//            JOptionPane.showMessageDialog(null, "ERROR AL ELIMININAR:" + rutaArchivo, "ELIMINAR", JOptionPane.ERROR_MESSAGE);
        }
    }

}
