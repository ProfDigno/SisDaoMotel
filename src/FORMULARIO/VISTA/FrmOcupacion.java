/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import CONFIGURACION.ComputerInfo;
import Config_JSON.json_array_formulario;
import ConnRPI.JSchExampleSSHConnection;
import ESTADOS.EvenEstado;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
import Evento.Grafico.FunFreeChard;
import Evento.JButton.EvenJButton;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenNumero_a_Letra;
import Evento.Utilitario.EvenSonido;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import FORMULARIO.VISTA.BUSCAR.ClaVarBuscar;
import IMPRESORA_POS.PosImprimir_Factura;
import IMPRESORA_POS.PosImprimir_Garantia;
import IMPRESORA_POS.PosImprimir_Venta;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Tooltip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Digno
 */
public class FrmOcupacion extends javax.swing.JInternalFrame {

    private EvenJButton evebtn = new EvenJButton();
    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenFecha evefec = new EvenFecha();
    private FunFreeChard evechar = new FunFreeChard();
    private ClaVarBuscar vbus = new ClaVarBuscar();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenEstado eveest = new EvenEstado();
    private EvenCombobox evecmb = new EvenCombobox();
    private EvenConexion eveconn = new EvenConexion();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private EvenRender render = new EvenRender();
    private habitacion_costo ENThc = new habitacion_costo();
    private DAO_habitacion_costo DAOhc = new DAO_habitacion_costo();
    private habitacion_dato ENThd = new habitacion_dato();
    private DAO_habitacion_dato DAOhd = new DAO_habitacion_dato();
    private habitacion_recepcion_temp ENThrt = new habitacion_recepcion_temp();
    private DAO_habitacion_recepcion_temp DAOhrt = new DAO_habitacion_recepcion_temp();
    private BO_habitacion_recepcion_temp BOhrt = new BO_habitacion_recepcion_temp();
    private habitacion_recepcion ENThr = new habitacion_recepcion();
    private DAO_habitacion_recepcion DAOhr = new DAO_habitacion_recepcion();
    private caja_cierre_detalle ENTccd = new caja_cierre_detalle();
    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    private DAO_caja_cierre DAOcc = new DAO_caja_cierre();
    private PosImprimir_Venta posv = new PosImprimir_Venta();
    private PosImprimir_Garantia posgar = new PosImprimir_Garantia();
    private venta ENTven = new venta();
    private DAO_venta DAOven = new DAO_venta();
    private BO_venta BOven = new BO_venta();
    private venta_item ENTveni = new venta_item();
    private DAO_venta_item DAOveni = new DAO_venta_item();
    private BO_venta_item BOveni = new BO_venta_item();
    private habitacion_mini_pc ENThmp = new habitacion_mini_pc();
    private DAO_habitacion_mini_pc DAOhmp = new DAO_habitacion_mini_pc();
    private garantia ENTgar = new garantia();
    private DAO_garantia DAOgar = new DAO_garantia();
    private BO_garantia BOgar = new BO_garantia();
    private JSchExampleSSHConnection connRPI = new JSchExampleSSHConnection();
    private ComputerInfo pcinfo = new ComputerInfo();
    json_array_formulario jsfrm = new json_array_formulario();
    private usuario ENTusu = new usuario(); //creado_por = ENTusu.getGlobal_nombre();
    private DAO_usuario DAOusu = new DAO_usuario();
    Connection conn = ConnPostgres.getConnPosgres();
    private producto ENTp = new producto();
    private DAO_producto DAOp = new DAO_producto();
    private factura ENTf = new factura();
    private DAO_factura DAOf = new DAO_factura();
    private BO_factura BOf = new BO_factura();
    private factura_item ENTfi = new factura_item();
    private DAO_factura_item DAOfi = new DAO_factura_item();
    private EvenNumero_a_Letra evenrolt = new EvenNumero_a_Letra();
    private PosImprimir_Factura posfac = new PosImprimir_Factura();
    private java.util.List<JButton> botones_categoria;
    private java.util.List<JButton> botones_unidad;
    private java.util.List<JButton> botones_marca;
    private java.util.List<JButton> botones_nro_hab;
    private java.util.List<JButton> botones_teste;
    private java.util.List<JTextField> botones_puerta;
    DefaultTableModel model_itemf = new DefaultTableModel();
    private int cant_boton_cate;
    private int cant_boton_unid;
    private int cant_boton_marca;
    private String fk_idproducto_unidad;
    private String fk_idproducto_categoria;
    private String fk_idproducto_marca;
    private String nombreTabla_pri = "VENTA PRINCIPAL";
    private String nombreTabla_sec = "FILTRO";
    private String creado_por = "digno";
    private int idproducto;
    private String precio_venta_mostrar;
    private String precio_venta_oculto;
    private String precio_compra_oculto;
    private Timer tiempo_boton;
    private int nro_habitacion_select;
    private int jbar_tie_min_max = 92;
    private int tiempo_boton_hab = 0;
    private int tiempo_boton_hab_max = 40;
    private int fk_idhabitacion_dato_buscar;
    private int fk_idhabitacion_recepcion;
    private int fk_idhabitacion_dato_select;
    private int fk_idhabitacion_recepcion_actual_select;
    private boolean permitir_dormir_select;
    private String tiempo_select;
    private String fecha_ingreso_select;
    private String hora_ingreso_select;
    private int fk_idventa;
    private int fk_idusuario;
    private double monto_por_hora_minimo;
    private double monto_por_hora_adicional;
    private double monto_por_dormir_minimo;
    private double monto_por_dormir_adicional;
    private int cant_adicional;
    private double monto_minimo_A;//Automatico ocupar
    private double monto_minimo_D;//Desocupar habitacion
    private double monto_adicional_total;
    private double monto_consumo;
    private double monto_insumo;
    private double monto_descuento;
    private double monto_adelanto;
    private double monto_tarifa_ocupacion_total;
    private double monto_total_pagar;
    String[] Sa_tipo_habitacion;
    int[] Ia_nro_habitacion;
    String[] Sa_estado;
    String[] Sa_desc_estado;
    String[] Sa_tiempo;
    String[] Sa_monto;
    boolean[] Ba_habilitar_dormir;
    boolean[] Ba_habilitar_hora;
    int[] Ia_cant_add_tarifa_hora;
    int[] Ia_idhabitacion_dato;
    int[] Ia_idhabitacion_recepcion_actual;
    String[] Sa_fecha_ingreso;
    String[] Sa_hora_ingreso;
    int[] Ia_ocupado_inicio_seg;
    int[] Ia_minuto_minimo;
    boolean[] Ba_permitir_dormir;
    boolean[] Ba_es_hora_dormir;
    int[] Ia_monto_adelanto;
    private boolean[] Ba_cancelar_habitacion;
    private String[] Sa_por_cancelar;
    private String[] Sa_monto_por_hora_minimo;
    private String[] Sa_hs_hasta_dormir;
    int[] Ia_puerta_nro_habitacion;
    private int[] Ia_cant_hasta_dormir;
    boolean[] Ba_puerta_cliente;
    boolean[] Ba_puerta_limpieza;
    boolean[] Ba_es_manual;
    private int cant_de_habitacion;
    private int segundo_tiempo_esten;
    private int max_tiempo_esten = 60;
    private int limit_tiempo_esten = 5;
    private int segundo_tiempo_resumen;
    private int segundo_act_btn;
    private double monto_adicional;
    private int cant_boton_nrohab;
    private String suma_tiempo_titulo;
    private String motivo_anulacion;
    private String btnlibre_html = "<html><p style=\"color:green\"><font size=\"4\">LIBRE</font></p></html>";
    private String btnaceptar_html = "<html><p style=\"color:blue\"><font size=\"4\">ACEPTAR</font></p></html>";
    private String btncancelar_html = "<html><p style=\"color:purple\"><font size=\"4\">CANCELAR</font></p></html>";
    private String btnocupar_html = "<html><p style=\"color:red\"><font size=\"4\">OCUPAR</font></p></html>";
    private String btndesocupar_html = "<html><p style=\"color:green\"><font size=\"4\">DESOCUPAR</font></p></html>";
    private String btnreiniciar_html = "<html><p style=\"color:red\"><font size=\"4\">REINICIAR</font></p></html>";
    private String btnprint_ticket_html = "<html><p style=\"color:red\"><font size=\"4\">IMPRIMIR TICKET</font></p></html>";
    private String btnprint_factura_html = "<html><p style=\"color:blue\"><font size=\"4\">IMPRIMIR FACTURA</font></p></html>";
    private String btnmanual_html = "<html><p style=\"color:green\"><font size=\"4\">MANUAL</font></p></html>";
    private String btnautomatico_html = "<html><p style=\"color:blue\"><font size=\"4\">AUTOMATICO</font></p></html>";
    private String motivo_mudar_habitacion;
    private String fecha_hora_ahora;
    private int sensor_puerta_cliente = 2;
    private int sensor_puerta_limpieza = 3;
    private boolean hab_ruta_sonido[];
    private String string_ruta_sonido[];
    private boolean no_es_sonido_ocupado;
    private boolean carga_prod_codbarra = false;
    private boolean es_adelanto_cargado;
    private int select_tab_principal;
    private boolean boton_hab_unavez = true;
    private boolean boton_mudar_unavez = true;
    private boolean boton_puerta_unavez = true;
    private boolean es_por_hora;
    private boolean es_por_dormir;
    private int Icant_hasta_dormir;
    private String Shs_hasta_dormir;
    private int segundo_conn_rpi;
    private int cant_add_hab_boton;
    private double monto_adelanto_1;
    private String descripcion_consumo_adelantado = "";
    private double monto_garantia;
    private int fk_idgarantia;
    private int fk_idventa_select;
    private boolean btn_reboot_rp_1;
    private boolean btn_reboot_rp_2;
    private boolean btn_reboot_rp_3;
    private boolean est_btn_cancelar;
    private String observacion_venta = "";
    private String descripcion_caja_desocupa;
    private String hs_dormir_salida_final_select;
    private boolean es_por_hora_select;
    private boolean es_por_dormir_select;
    private String sql_ocupacion;
    private String[] Sa_ocu_nombre_boton;
    private String[] Sa_ocu_icono;
    private String[] Sa_ocu_color_fondo;
    private String[] Sa_ocu_color_texto;
    private String[] Sa_nombre_puerta;
    boolean seg_intercalar = false;
    private int fk_idfactura;
    private boolean hab_cancelar_habitacion;
    private int fk_idventa_ult_print;
    private int fk_idfactura_ult_print;
    private boolean es_por_hospedaje;
    private double monto_por_hospedaje_minimo;
    private boolean es_por_hospedaje_select;

    private void abrir_formulario() {
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        this.setTitle(nombreTabla_pri + " USUARIO:" + creado_por + " IP:" + pcinfo.getStringMiIP());
        evetbl.centrar_formulario_internalframa(this);
        cargar_array_habitacion();
        cargar_usuario_acceso();
        cargar_grafico_temperatura();
        botones_categoria = new ArrayList<>();
        botones_unidad = new ArrayList<>();
        botones_marca = new ArrayList<>();
        botones_nro_hab = new ArrayList<>();
        botones_teste = new ArrayList<>();
        botones_puerta = new ArrayList<>();
//        jPtiempo_hab.setMaximum(max_tiempo_esten);
        FrmMenuMotel.setHabilitar_sonido(true);
        FrmMenuMotel.setAbrir_frmventa(false);
        cargar_usuario();
        cargar_boton_categoria();
        crear_item_producto();
        limpiar_habitacion_select();
//        reestableser_garantia();
        boton_raspberry();
//        ejecutar_update_habitacion_recepcion_temp_FUERTE();
        ejecutar_update_habitacion_recepcion_temp_LIBIANO();
        cargar_cantidad_entrada_abierta();
        cargar_boton_habitacion(true);
        cargar_estados_puertas_gpio(true);
        cargar_boton_habitacion_mudar();
        txtgar_idventa.setText(null);
        txtgar_monto_ocupacion.setText(null);
        btngar_guardar.setEnabled(false);
        evefec.cargar_combobox_intervalo_fecha(cmbfecha_venta);
        jbar_tiempo_minimo.setMaximum(jbar_tie_min_max);
    }

    private void boton_raspberry() {
        btnrpi_1.setVisible(jsfrm.isBoo_rasp_1());
        btnrpi_2.setVisible(jsfrm.isBoo_rasp_2());
        btnrpi_3.setVisible(jsfrm.isBoo_rasp_3());
    }

    private void color_panel_venta(int tipo) {
        Color color_gral = null;
        if (tipo == 1) {
            Color color_select = new Color(232, 249, 253);
            color_gral = color_select;
        }
        if (tipo == 2) {
            Color color_no_select = new Color(33, 85, 205);
            color_gral = color_no_select;
        }
        jPanel_venta_principal.setBackground(color_gral);
        jPanel_item_venta.setBackground(color_gral);
        jPanel_tiempo_habitacion.setBackground(color_gral);
        panel_garantia_pendiente.setBackground(color_gral);
        jPanel_mudar_habitacion.setBackground(color_gral);
    }

    private void cargar_grafico_temperatura() {
        evechar.crear_grafico_temperatura(panel_temp_rpi_1, 53.3, "RPI - 1");
    }

    private void cargar_usuario_acceso() {
        if (fk_idusuario != ENTusu.getGlobal_idusuario()) {
            usuario usu = new usuario();
            creado_por = usu.getGlobal_nombre();
            fk_idusuario = usu.getGlobal_idusuario();
            this.setTitle(nombreTabla_pri + " USUARIO:" + creado_por);
        }
    }

    private void cargar_usuario() {
        DAOusu.cargar_usuario_combo(conn, cmbusuario);
    }

    public int getInt_cant_habitacion_activo(Connection conn) {
        String titulo = "getInt_ultimoID";
        int getid = 0;
        String sql = "select count(*) as cant from habitacion_recepcion_temp where activo=true;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                getid = rs.getInt("cant");
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return getid;
    }

    private void cargar_array_habitacion() {
        cant_de_habitacion = getInt_cant_habitacion_activo(conn);
        Sa_tipo_habitacion = new String[cant_de_habitacion];
        Ia_nro_habitacion = new int[cant_de_habitacion];
        Sa_estado = new String[cant_de_habitacion];
        Sa_desc_estado = new String[cant_de_habitacion];
        Sa_tiempo = new String[cant_de_habitacion];
        Sa_monto = new String[cant_de_habitacion];
        Ba_habilitar_dormir = new boolean[cant_de_habitacion];
        Ba_habilitar_hora = new boolean[cant_de_habitacion];
        Ia_cant_add_tarifa_hora = new int[cant_de_habitacion];
        Ia_idhabitacion_dato = new int[cant_de_habitacion];
        Ia_idhabitacion_recepcion_actual = new int[cant_de_habitacion];
        Sa_fecha_ingreso = new String[cant_de_habitacion];
        Sa_hora_ingreso = new String[cant_de_habitacion];
        Ia_ocupado_inicio_seg = new int[cant_de_habitacion];
        Ia_minuto_minimo = new int[cant_de_habitacion];
        Ba_permitir_dormir = new boolean[cant_de_habitacion];
        Ba_es_hora_dormir = new boolean[cant_de_habitacion];
        Ia_monto_adelanto = new int[cant_de_habitacion];
        Ba_cancelar_habitacion = new boolean[cant_de_habitacion];
        Sa_por_cancelar = new String[cant_de_habitacion];
        Ia_puerta_nro_habitacion = new int[cant_de_habitacion];
        Ba_puerta_cliente = new boolean[cant_de_habitacion];
        Ba_puerta_limpieza = new boolean[cant_de_habitacion];
        Ba_es_manual = new boolean[cant_de_habitacion];
        hab_ruta_sonido = new boolean[cant_de_habitacion + 1];//+1
        string_ruta_sonido = new String[cant_de_habitacion + 1];//+1
        Sa_monto_por_hora_minimo = new String[cant_de_habitacion];
        Ia_cant_hasta_dormir = new int[cant_de_habitacion];
        Sa_hs_hasta_dormir = new String[cant_de_habitacion];

        Sa_ocu_nombre_boton = new String[cant_de_habitacion];
        Sa_ocu_icono = new String[cant_de_habitacion];
        Sa_ocu_color_fondo = new String[cant_de_habitacion];
        Sa_ocu_color_texto = new String[cant_de_habitacion];
        Sa_nombre_puerta = new String[cant_de_habitacion];
        System.err.println("FORMULARIO.VISTA.FrmVenta.cargar_array_habitacion().cant_de_habitacion:" + cant_de_habitacion);
    }

    private void ejecutar_update_habitacion_recepcion_temp_FUERTE() {
        String sql = DAOhrt.getSql_ocupacion_boton_FUERTE();
        eveconn.SQL_execute_libre(conn, sql);
    }

    private void ejecutar_update_habitacion_recepcion_temp_LIBIANO() {
        String sql = DAOhrt.getSql_ocupacion_boton_LIBIANO();
        eveconn.SQL_execute_libre_sin_print(conn, sql);
    }

    private void cargar_boton_habitacion(boolean es_inicio) {
        String titulo = "cargar_boton_habitacion";
        String sql1 = "select ('<html><p><font size=\"4\">'||nro_habitacion||'</font>-'||tipo_habitacion||"
                + "'</p><p>'||idhabitacion_dato||':'||descrip_estado||'</p>'||\n"
                + "'<p><font size=\"4\">'||tiempo_estado||'</font></p>'||\n"
                + "'<p>'||"
                + "(case when monto_gral>0 then TRIM(to_char(monto_gral,'999G999G999')) else '.' end)"
                + "||'</p></html>') as nom_boton,"
                + "idhabitacion_dato, \n"
                + "ruta_icono as icono, \n"
                + "color_fondo, \n"
                + "color_texto, \n"
                + "nro_habitacion,"
                + "case\n"
                + "when estado = 'LIBRE'     and puerta_limpieza = true  and puerta_cliente = false then true\n"
                + "when estado = 'SUCIO'     and puerta_limpieza = false then true\n"
                + "when estado = 'LIMPIANDO' and puerta_limpieza = true  and puerta_cliente = true then true\n"
                + "else false\n"
                + "end as cambiar_estado,\n"
                + "case\n"
                + "when estado = 'LIBRE'     and puerta_limpieza = true and puerta_cliente = false then 'OCUPADO'\n"
                + "when estado = 'SUCIO'     and puerta_limpieza = false then 'LIMPIANDO'\n"
                + "when estado = 'LIMPIANDO' and puerta_limpieza = true and puerta_cliente = true then 'LIBRE'\n"
                + "else 'sin'\n"
                + "end as est_nuevo,\n"
                + "idhabitacion_recepcion_actual \n"
                + "from habitacion_recepcion_temp \n"
                + "where activo=true \n"
                + "order by orden asc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL_sinprint(conn, sql1, titulo);
            if (es_inicio) {
                panel_habitaciones.removeAll();
                botones_nro_hab.clear();
            }
            int fila = 0;
            while (rs.next()) {
                String textboton = rs.getString("nom_boton");
                String nameboton = rs.getString("idhabitacion_dato");
                String icono = rs.getString("icono");
                String color_fondo = rs.getString("color_fondo");
                String color_texto = rs.getString("color_texto");
                int Inro_habitacion = rs.getInt("nro_habitacion");//
                boolean cambiar_estado = rs.getBoolean("cambiar_estado");//
                String est_nuevo = rs.getString("est_nuevo");//
                int idhabitacion_dato = rs.getInt("idhabitacion_dato");//
                int idhabitacion_recepcion_actual = rs.getInt("idhabitacion_recepcion_actual");//
                Sa_ocu_nombre_boton[fila] = textboton;
                Sa_ocu_icono[fila] = icono;
                Sa_ocu_color_fondo[fila] = color_fondo;
                Sa_ocu_color_texto[fila] = color_texto;
                if (es_inicio) {
                    crear_boton_habitacion(textboton, nameboton, icono, color_fondo, color_texto);
                }

                ejecutar_limpieza_automatico(cambiar_estado, est_nuevo, idhabitacion_recepcion_actual, idhabitacion_dato);
                ejecutar_libre_automatico(cambiar_estado, est_nuevo, idhabitacion_recepcion_actual, idhabitacion_dato);
                ejecutar_ocupar_automatico(cambiar_estado, est_nuevo, idhabitacion_dato, Inro_habitacion, idhabitacion_recepcion_actual);
                fila++;
            }
            if (es_inicio) {
                panel_habitaciones.updateUI();
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql1, titulo);
        }
    }

    private void cargar_boton_habitacion_mudar() {
        String titulo = "cargar_boton_habitacion_mudar";
        String sql1 = "select ('<html><p><font size=\"4\">'||nro_habitacion||'</font>-'||tipo_habitacion||"
                + "'</p><p>'||idhabitacion_dato||':'||descrip_estado||'</p>'||\n"
                + "'<p><font size=\"4\">'||tiempo_estado||'</font></p>'||\n"
                + "'<p>'||TRIM(to_char(monto_gral,'999G999G999'))||'</p></html>') as nom_boton,"
                + "idhabitacion_dato, \n"
                + "estado, \n"
                + "nro_habitacion "
                + "from habitacion_recepcion_temp \n"
                + "where activo=true \n"
                + "order by idhabitacion_dato asc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL_sinprint(conn, sql1, titulo);
            panel_habitaciones_libre.removeAll();
            int fila = 0;
            while (rs.next()) {
                String textboton = rs.getString("nom_boton");
                String nameboton = rs.getString("idhabitacion_dato");
                int Inro_habitacion = rs.getInt("nro_habitacion");//
                String estado = rs.getString("estado");//
                int idhabitacion_dato = rs.getInt("idhabitacion_dato");//
                crear_unitario_boton_habitacion_mudar(textboton, nameboton, Inro_habitacion, estado, idhabitacion_dato);
                fila++;
            }
            panel_habitaciones_libre.updateUI();
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql1, titulo);
        }
    }

    private void crear_unitario_boton_habitacion_mudar(String textboton, String nameboton, int nro_habitacion, String estado, int idhabitacion_dato) {
        JButton boton_lib;
        if (estado.equals(eveest.getEst_Libre())) {
            boton_lib = new JButton(textboton);
            boton_lib.setFont(new Font("Arial", Font.BOLD, 12));
            boton_lib.setName(nameboton);
            boton_lib.setForeground(new java.awt.Color(0, 102, 0));
            boton_lib.setIcon(new ImageIcon(this.getClass().getResource(eveest.getIco_libre())));
        } else {
            boton_lib = new JButton("NO-DISPONIBLE");
            boton_lib.setFont(new Font("Arial", Font.BOLD, 12));
            boton_lib.setName(nameboton);
            boton_lib.setForeground(Color.black);
            boton_lib.setBackground(Color.orange);
            boton_lib.setIcon(new ImageIcon(this.getClass().getResource(eveest.getIco_candado())));
        }
        panel_habitaciones_libre.add(boton_lib);
        boton_lib.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((JButton) e.getSource()).setBackground(new java.awt.Color(153, 153, 255));
                String getName = ((JButton) e.getSource()).getName();
                System.out.println("getName:" + getName);
                if (estado.equals(eveest.getEst_Libre())) {
                    if (validar_habitacion_select()) {
                        if (evemen.getBooMensaje_question("<html><p><font size=\"6\">HABITACION NRO:   " + nro_habitacion + "</font></p>"
                                + "<p>ESTA HABITACION ESTA LIBRE DESEA PASAR COMO OCUPADO</p>"
                                + "<p><font size=\"6\">--MUDAR DEL " + nro_habitacion_select + " AL NRO: " + nro_habitacion + "--</font></p>"
                                + "</html>", "MUDAR HABITACION", "MUDAR", btncancelar_html)) {
                            tiempo_boton_hab = 0;
                            JTextArea txtacancel = new JTextArea(15, 30);
                            int idventa = (eveconn.getInt_ultimoID_mas_uno(conn, ENTven.getTb_venta(), ENTven.getId_idventa()));
                            String mensaje_inicio = "MUDAR DEL " + nro_habitacion_select + " AL NRO: " + nro_habitacion + " IDVENTA:" + idventa;
                            txtacancel.setText(mensaje_inicio);
                            Object[] opciones = {"MUDAR", btncancelar_html};
                            int eleccion = JOptionPane.showOptionDialog(null, new JScrollPane(txtacancel), "MOTIVO PARA MUDAR(Obligatorio)",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE, null, opciones, "MUDAR");
                            if (eleccion == JOptionPane.YES_OPTION) {
                                if (txtacancel.getText().trim().length() > 0) {
                                    motivo_mudar_habitacion = txtacancel.getText().toUpperCase();
                                    cargar_observacion_venta("Motivo:" + motivo_mudar_habitacion, false);
                                    ejecutar_mudar_habitacion(idhabitacion_dato, nro_habitacion);
                                    cargar_observacion_venta("##==>>CONFIRMAR MUDAR", false);
                                    limpiar_habitacion_select();
                                    boton_mudar_unavez = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "NO SE ENCONTRO NINGUN MOTIVO DE MUDAR INTENTE DE NUEVO",
                                            "ERROR MUDANZA", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "<html><p><font size=\"6\">HABITACION NRO:   " + nro_habitacion + "</font></p>"
                            + "<p><font size=\"6\">--NO DISPONIBLE--</font></p></html>", "HABITACION LIBRE", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        boton_lib.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                evebtn.setColor_mouseMoved(((JButton) evt.getSource()));
            }
        });
        boton_lib.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                evebtn.setColor_mouseExited_neutro(((JButton) evt.getSource()));
            }
        });
    }

    private Color getCol_Exa(String hexColor) {
        return Color.decode(hexColor);
    }

    private void actualizar_boton_habitacion() {
        String titulo = "actualizar_boton_habitacion";
        for (int fila = 0; fila < cant_de_habitacion; fila++) {
            try {
                botones_nro_hab.get(fila).setText(Sa_ocu_nombre_boton[fila]);
                botones_nro_hab.get(fila).setBackground(getCol_Exa(Sa_ocu_color_fondo[fila]));
                botones_nro_hab.get(fila).setForeground(getCol_Exa(Sa_ocu_color_texto[fila]));
                if (!Sa_ocu_icono[fila].equals("no")) {
                    botones_nro_hab.get(fila).setIcon(new ImageIcon(this.getClass().getResource(Sa_ocu_icono[fila])));
                }
            } catch (Exception e) {
                evemen.mensaje_error(e, titulo);
            }
        }
    }

    private void crear_boton_habitacion(String textboton, String nameboton, String icono, String color_fondo, String color_texto) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setName(nameboton);
        if (!icono.equals("no")) {
            boton.setIcon(new ImageIcon(this.getClass().getResource(icono)));
        }
        boton.setBackground(getCol_Exa(color_fondo));
        boton.setForeground(getCol_Exa(color_texto));
        panel_habitaciones.add(boton);
        botones_nro_hab.add(boton);
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Sidhabitacion_dato = ((JButton) e.getSource()).getName();
                System.out.println("idhabitacion_dato:" + Sidhabitacion_dato);
                int idhabitacion_dato = Integer.parseInt(Sidhabitacion_dato);
                cargar_habitacion_recepcion_temp(idhabitacion_dato);
            }
        });
        boton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                evebtn.setColor_mouseMoved(((JButton) evt.getSource()));

            }
        });
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                evebtn.setColor_mouseExited_neutro(((JButton) evt.getSource()));
            }
        });
    }

    private void cargar_habitacion_recepcion_temp(int idhabitacion_dato) {
        String titulo = "cargar_habitacion_recepcion_temp";
        String sql = DAOhrt.getSql_ocupacion_boton_CARGAR(idhabitacion_dato);
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                String estado = rs.getString("estado");
                String tipo_habitacion = rs.getString("tipo_habitacion");
                int nro_habitacion = rs.getInt("nro_habitacion");
                int idhabitacion_recepcion_actual = rs.getInt("idhabitacion_recepcion_actual");
                boolean habilitar_dormir = rs.getBoolean("es_por_dormir");
                boolean habilitar_hora = rs.getBoolean("es_por_hora");
                boolean habilitar_hospedaje = rs.getBoolean("es_hospedaje");
                int cant_hasta_dormir = rs.getInt("cant_hasta_dormir");
                String hs_hasta_dormir = rs.getString("hs_hasta_dormir");
                int cant_hora_adicional = rs.getInt("cant_hora_adicional");
                boolean permitir_dormir = rs.getBoolean("permitir_dormir");
                String tiempo_estado = rs.getString("tiempo_estado");
                String fecha_ingreso = rs.getString("fecha_ingreso");
                String hora_ingreso = rs.getString("hora_ingreso");
                boolean cancelar_habitacion = rs.getBoolean("cancelar_habitacion");
                String por_cancelar = rs.getString("por_cancelar");
                int ocupado_inicio_seg = rs.getInt("ocupado_inicio_seg");
                int minuto_minimo = rs.getInt("minuto_minimo");
                monto_por_hora_minimo = rs.getDouble("monto_por_hora_minimo");
                if (estado.equals(eveest.getEst_Libre())) {
                    String minimo = evejtf.getString_format_nro_decimal(monto_por_hora_minimo);
                    String mensaje = "<html>"
                            + evemen.getHtml_negro(6, "HABITACION NRO:   " + nro_habitacion)
                            + evemen.getHtml_negro(3, "ESTA HABITACION ESTA LIBRE DESEA PASAR COMO OCUPADO")
                            + evemen.getHtml_rojo(6, "--OCUPAR--")
                            + evemen.getHtml_azul(5, "--MONTO MINIMO: " + minimo)
                            + "</html>";
                    int boton = evemen.getIntMensaje_informacion_4btn(mensaje, "HABITACION LIBRE", btnocupar_html, btnmanual_html,btnautomatico_html, btncancelar_html);
                    if (boton == 0) {
                        cargar_observacion_venta("#==>>OCUPAR POR BOTON MANUAL", false);
                        boton_libre_a_ocupar(nro_habitacion, idhabitacion_dato);
                        ejecutar_update_habitacion_recepcion_temp_FUERTE();
                    }
                    if (boton == 1) {
                        if (DAOusu.gB_boton_es_manual()) {
                            cargar_observacion_venta("#==>>HABITACION MANUAL (CENSOR) por roll", false);
                            DAOhrt.update_habitacion_recepcion_temp_es_manual(conn, true, idhabitacion_dato);
                        }
                    }
                    if (boton == 2) {
                        if (DAOusu.gB_boton_es_manual()) {
                            cargar_observacion_venta("#==>>HABITACION AUTOMATICO (CENSOR) por roll", false);
                            DAOhrt.update_habitacion_recepcion_temp_es_manual(conn, false, idhabitacion_dato);
                        }
                    }
                }
                if (estado.equals(eveest.getEst_Sucio())) {
                    if (evemen.getBooMensaje_question("<html><p><font size=\"6\">HABITACION NRO:   " + nro_habitacion + "</font></p>"
                            + "<p>ESTA HABITACION ESTA EN SUCIO DESEA PASAR A COMO LIBRE</p>"
                            + "<p><font size=\"6\">--LIBERAR--</font></p>"
                            + "</html>", "HABITACION SUCIO", btnlibre_html, btncancelar_html)) {
                        boton_sucio_a_libre(idhabitacion_recepcion_actual, idhabitacion_dato);
                        ejecutar_update_habitacion_recepcion_temp_FUERTE();
                    }
                }
                if (estado.equals(eveest.getEst_Limpiando())) {
                    if (evemen.getBooMensaje_question("<html><p><font size=\"6\">HABITACION NRO:   " + nro_habitacion + "</font></p>"
                            + "<p>ESTA HABITACION ESTA EN LIMPIEZA DESEA PASAR A COMO LIBRE</p>"
                            + "<p><font size=\"6\">--LIBERAR--</font></p>"
                            + "</html>", "HABITACION LIMPIEZA", btnlibre_html, btncancelar_html)) {
                        boton_limpieza_a_libre(idhabitacion_recepcion_actual, idhabitacion_dato);
                        segundo_tiempo_esten = max_tiempo_esten - limit_tiempo_esten;
                    }
                }
                if (estado.equals(eveest.getEst_Ocupado())) {
                    segundo_tiempo_esten = 0;
                    Icant_hasta_dormir = cant_hasta_dormir;
                    Shs_hasta_dormir = hs_hasta_dormir;
                    tiempo_boton_hab = 0;
                    boton_mudar_unavez = true;
                    color_panel_venta(1);
                    eveJtab.mostrar_JTabbedPane(jTab_principal, 1);
                    jFtotal_consumo.setValue(0);
                    icono_tipo_habitacion(tipo_habitacion);
                    cargar_costos(idhabitacion_dato, habilitar_dormir, habilitar_hora, habilitar_hospedaje, cant_hora_adicional);
                    nro_habitacion_select = nro_habitacion;
                    fk_idhabitacion_dato_select = idhabitacion_dato;
                    permitir_dormir_select = permitir_dormir;
                    tiempo_select = tiempo_estado;
                    fecha_ingreso_select = fecha_ingreso;
                    hora_ingreso_select = hora_ingreso;
                    fk_idhabitacion_recepcion_actual_select = idhabitacion_recepcion_actual;
                    DAOven.cargar_venta_idhabitacion_recepcion(conn, ENTven, idhabitacion_recepcion_actual);
                    fk_idventa = ENTven.getC1idventa();
                    DAOveni.actualizar_tabla_venta_item(conn, tblitem_consumo_cargado, fk_idventa);
                    limpiar_cargar_tabla_venta_item(conn, tblitem_producto, fk_idventa);
                    txtrecepcion_actual.setText(String.valueOf(fk_idhabitacion_recepcion_actual_select));
                    txtnro_hab_grande.setText(String.valueOf(nro_habitacion));
                    txtnro_hab_grande_salir.setText(String.valueOf(nro_habitacion));
                    txttipo_habitacion.setText(tipo_habitacion);
                    txtidventa.setText(String.valueOf(fk_idventa));
                    txtfec_ocupado_inicio.setText(fecha_ingreso);
                    txtfec_ocupado_inicio_hora.setText(hora_ingreso);
                    txttiempo_transcurrido.setText(tiempo_estado);
                    txttiempo_transcurrido_salir.setText(tiempo_estado);
                    hab_cancelar_habitacion = cancelar_habitacion;
                    jRpor_dormir.setEnabled(permitir_dormir_select);//es_hora_dormir
                    //jRpor_hora_mas_dormir.setEnabled(!permitir_dormir_select);
                    if (cancelar_habitacion) {
                        lblmensaje_cancelar.setText("...");
                    } else {
                        lblmensaje_cancelar.setText("CERRAR PUERTA: " + nro_habitacion);
                    }
                    btncancelar.setText("CANCELAR-" + por_cancelar);
                    observacion_venta = "";
                    cargar_observacion_venta("Seleccion BTN OCUPADO; fk_idventa=" + fk_idventa + ",nro_hab=" + nro_habitacion + ",tiempo=" + tiempo_estado, false);
                    cargar_jbar_tiempo_minimo(ocupado_inicio_seg, minuto_minimo);
                }
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void cargar_boton_categoria() {
        String titulo = "cargar_boton_categoria";
        String sql = "SELECT  c.idproducto_categoria, c.nombre,c.orden \n"
                + "From producto_categoria c,producto p \n"
                + "where c.idproducto_categoria=p.fk_idproducto_categoria \n"
                + "and c.activo=true \n"
                + "and p.es_venta=true \n"
                + "group by 1,2,3\n"
                + "order by c.orden desc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            panel_referencia_categoria.removeAll();
            botones_categoria.clear();
            int cant = 0;
            while (rs.next()) {
                cant++;
                String textboton = rs.getString("nombre");
                String nameboton = rs.getString("idproducto_categoria");
                crear_boton_categoria(textboton, nameboton);
                if (cant == 1) {
                    fk_idproducto_categoria = nameboton;
                    actualizar_tabla_producto(1);
                }
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void crear_boton_categoria(String textboton, String nameboton) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setName(nameboton);
        panel_referencia_categoria.add(boton);
        botones_categoria.add(boton);
        cant_boton_cate++;
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int p = 0; p < cant_boton_cate; p++) {
                    botones_categoria.get(p).setBackground(new java.awt.Color(255, 255, 255));
                }
                ((JButton) e.getSource()).setBackground(new java.awt.Color(153, 153, 255));
                fk_idproducto_categoria = ((JButton) e.getSource()).getName();
                cargar_boton_unidad(fk_idproducto_categoria);
                cargar_boton_marca(fk_idproducto_categoria);
                System.out.println("fk_idproducto_categoria:" + fk_idproducto_categoria);
                actualizar_tabla_producto(1);
            }
        });
        panel_referencia_categoria.updateUI();
    }

    private void cargar_boton_unidad(String idproducto_categoria) {
        String titulo = "cargar_boton_unidad";
        panel_referencia_unidad.removeAll();
        botones_unidad.clear();
        cant_boton_unid = 0;
        String sql = "select u.idproducto_unidad, u.nombre \n"
                + "from producto p,producto_categoria c,producto_unidad u \n"
                + "where p.fk_idproducto_categoria=c.idproducto_categoria \n"
                + "and p.fk_idproducto_unidad=u.idproducto_unidad\n"
                + "and c.idproducto_categoria=" + idproducto_categoria
                + " group by 1,2 order by u.nombre asc  limit 8;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String textboton = rs.getString("nombre");
                String nameboton = rs.getString("idproducto_unidad");
                boton_agregar_unidad(textboton, nameboton);
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void boton_agregar_unidad(String textboton, String nameboton) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 10));
        boton.setName(nameboton);
        panel_referencia_unidad.add(boton);
        botones_unidad.add(boton);
        cant_boton_unid++;
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int p = 0; p < cant_boton_unid; p++) {
                    botones_unidad.get(p).setBackground(new java.awt.Color(255, 255, 255));
                }
                ((JButton) e.getSource()).setBackground(new java.awt.Color(153, 153, 255));
                fk_idproducto_unidad = ((JButton) e.getSource()).getName();
                System.out.println("fk_idproducto_unidad: " + fk_idproducto_unidad);
                actualizar_tabla_producto(2);
            }
        });
        panel_referencia_unidad.updateUI();
    }

    private void cargar_boton_marca(String idproducto_categoria) {
        String titulo = "cargar_boton_marca";
        panel_referencia_marca.removeAll();
        botones_marca.clear();
        cant_boton_marca = 0;
        String sql = "select u.idproducto_marca, u.nombre \n"
                + "from producto p,producto_categoria c,producto_marca u \n"
                + "where p.fk_idproducto_categoria=c.idproducto_categoria \n"
                + "and p.fk_idproducto_marca=u.idproducto_marca\n"
                + "and c.idproducto_categoria=" + idproducto_categoria
                + " group by 1,2 order by u.nombre asc  limit 8;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String textboton = rs.getString("nombre");
                String nameboton = rs.getString("idproducto_marca");
                boton_agregar_marca(textboton, nameboton);
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void boton_agregar_marca(String textboton, String nameboton) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 10));
        boton.setName(nameboton);
        panel_referencia_marca.add(boton);
        botones_marca.add(boton);
        cant_boton_marca++;
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int p = 0; p < cant_boton_marca; p++) {
                    botones_marca.get(p).setBackground(new java.awt.Color(255, 255, 255));
                }
                ((JButton) e.getSource()).setBackground(new java.awt.Color(153, 153, 255));
                fk_idproducto_marca = ((JButton) e.getSource()).getName();
                System.out.println("fk_idproducto_marca: " + fk_idproducto_marca);
                actualizar_tabla_producto(4);
            }
        });
        panel_referencia_marca.updateUI();
    }

    private void actualizar_tabla_producto(int tipo) {
        String filtro_categoria = "";
        String filtro_unidad = "";
        String filtro_producto = "";
        String filtro_marca = "";
        if (tipo == 1) {
            filtro_categoria = " and p.fk_idproducto_categoria=" + fk_idproducto_categoria;
        }
        if (tipo == 2) {
            filtro_categoria = " and p.fk_idproducto_categoria=" + fk_idproducto_categoria;
            filtro_unidad = " and p.fk_idproducto_unidad=" + fk_idproducto_unidad;
        }
        if (tipo == 3) {
            if (txtbuscar_producto.getText().trim().length() > 0) {
                String por = "%";
                String temp = por + txtbuscar_producto.getText() + por;
                filtro_producto = " and p.nombre ilike'" + temp + "' ";
            }
        }
        if (tipo == 4) {
            filtro_categoria = " and p.fk_idproducto_categoria=" + fk_idproducto_categoria;
            filtro_marca = " and p.fk_idproducto_marca=" + fk_idproducto_marca;
        }
        if (tipo == 5) {
            if (txtcod_barra.getText().trim().length() > 0) {
                String temp = txtcod_barra.getText();
                filtro_producto = " and p.codigo_barra='" + temp + "' ";
            }
        }

        String sql = "select p.idproducto as idp,p.codigo_barra as cb,\n"
                + "(p.nombre) as producto,u.nombre as unidad,\n"
                + "p.stock_actual as stock, \n"
                + "TRIM(to_char(p.precio_venta,'" + eveest.getForm_nro_9D() + "')) as pventa,"
                + "p.precio_venta as oculto_venta,p.precio_compra as oculto_compra, \n"
                + "case when p.stock_actual>0 then 'SI' "
                + "     else 'NO' end as tiene_stock "
                + "from producto p,producto_categoria c,producto_unidad u,producto_marca pm \n"
                + "where p.fk_idproducto_categoria=c.idproducto_categoria \n"
                + "and p.fk_idproducto_unidad=u.idproducto_unidad \n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca \n"
                + "and c.activo=true \n"
                + "and p.es_venta=true \n" + filtro_categoria + filtro_unidad + filtro_producto + filtro_marca
                + " order by p.idproducto  asc;";
        eveconn.Select_cargar_jtable(conn, sql, tblproducto);
        ancho_tabla_producto();
        tiempo_boton_hab = 0;
    }

    private void ancho_tabla_producto() {
        int Ancho[] = {5, 13, 45, 15, 10, 12, 1, 1, 1};
        eveJtab.setAnchoColumnaJtable(tblproducto, Ancho);
        eveJtab.alinear_derecha_columna(tblproducto, 5);
        eveJtab.ocultar_columna(tblproducto, 6);
        eveJtab.ocultar_columna(tblproducto, 7);
        eveJtab.ocultar_columna(tblproducto, 8);
        render.rendertabla_venta_producto(tblproducto, 8);
    }

    private void crear_item_producto() {
        String dato[] = {"id", "descripcion", "precio", "C", "total", "Opventa", "Opcompra", "Otipo", "Ototal"};
        eveJtab.crear_tabla_datos(tblitem_producto, model_itemf, dato);
        ancho_item_producto();
    }

    private void ancho_item_producto() {
        int Ancho[] = {5, 53, 15, 6, 14, 1, 1, 1, 1};
        eveJtab.setAnchoColumnaJtable(tblitem_producto, Ancho);
        eveJtab.ocultar_columna(tblitem_producto, 5);
        eveJtab.ocultar_columna(tblitem_producto, 6);
        eveJtab.ocultar_columna(tblitem_producto, 7);
        eveJtab.ocultar_columna(tblitem_producto, 8);
        eveJtab.alinear_derecha_columna(tblitem_producto, 2);
        eveJtab.alinear_derecha_columna(tblitem_producto, 3);
        eveJtab.alinear_derecha_columna(tblitem_producto, 4);
    }

    private void precargar_producto(int idproducto) {
        DAOp.cargar_producto(conn, ENTp, idproducto);
        txtcod_barra.setText(ENTp.getC4codigo_barra());
        txtbuscar_producto.setText(ENTp.getC5nombre());
        precio_venta_mostrar = ENTp.getPrecio_venta_mostrar();
        precio_venta_oculto = String.valueOf((int) ENTp.getC11precio_venta());
        precio_compra_oculto = String.valueOf((int) ENTp.getC12precio_compra());
        txtcantidad_producto.setText("1");
        txtcantidad_producto.grabFocus();
    }

    private void seleccionar_producto() {
        if (tblproducto.getSelectedRow() >= 0) {
            idproducto = eveJtab.getInt_select_id(tblproducto);
            precargar_producto(idproducto);
            carga_prod_codbarra = false;
        }
    }

    private boolean validar_cargar_item() {
        if (!carga_prod_codbarra) {
            if (eveJtab.getBoolean_validar_select(tblproducto)) {
                return false;
            }
        }
        if (evejtf.getBoo_JTextField_vacio(txtbuscar_producto, "DEBE CARGAR UNA DESCRIPCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcantidad_producto, "DEBE CARGAR UNA CANTIDAD")) {
            return false;
        }
        return true;
    }

    private void cargar_item_producto() {
        if (validar_cargar_item()) {
            String Sidproducto = String.valueOf(idproducto);
            String Sdescripcion = txtbuscar_producto.getText();
            String Sprecio_venta1 = precio_venta_mostrar;
            String Scantidad = txtcantidad_producto.getText();
            String Sprecio_venta2 = precio_venta_oculto;
            String Sprecio_compra = precio_compra_oculto;
            String Ssubtotal = "0";
            String Ssubtotal2 = "0";
            String Stipo = ENTveni.getTipo_temporal();
            String dato[] = {Sidproducto, Sdescripcion, Sprecio_venta1, Scantidad, Ssubtotal, Sprecio_venta2, Sprecio_compra, Stipo, Ssubtotal2};
            eveJtab.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            eveJtab.calcular_subtotal(tblitem_producto, model_itemf, 3, 5, 4, true);
            eveJtab.calcular_subtotal(tblitem_producto, model_itemf, 3, 5, 8, false);
            jFtotal_consumo.setValue(eveJtab.getDouble_sumar_tabla(tblitem_producto, 8));
            descripcion_consumo_adelantado = descripcion_consumo_adelantado + "(" + Scantidad + "," + Sdescripcion + ")\n";
            cargar_observacion_venta("Prod:" + Sdescripcion + " (" + Scantidad + ")-(" + Sprecio_venta1 + ")", false);
            reestableser_item_venta();
        }
    }

    private void cargar_item_producto_adicional_hasta_dormir() {
        String Sidproducto = "1000";
        String Sdescripcion = "OCUPACION POR HORA";
        String Sprecio_venta1 = evejtf.getString_format_nro_decimal(monto_por_hora_minimo);
        String Scantidad = "1";
        String Sprecio_venta2 = String.valueOf(monto_por_hora_minimo);
        String Sprecio_compra = "0";
        String Ssubtotal = "0";
        String Ssubtotal2 = "0";
        String Stipo = ENTveni.getTipo_temporal();
        String dato[] = {Sidproducto, Sdescripcion, Sprecio_venta1, Scantidad, Ssubtotal, Sprecio_venta2, Sprecio_compra, Stipo, Ssubtotal2};
        eveJtab.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
        if (Icant_hasta_dormir > 0) {
            Sidproducto = "1001";
            Sdescripcion = "CANT ADICIONAL";
            Sprecio_venta1 = evejtf.getString_format_nro_decimal(monto_por_hora_adicional);
            Scantidad = String.valueOf(Icant_hasta_dormir);
            Sprecio_venta2 = String.valueOf(monto_por_hora_adicional);
            Sprecio_compra = "0";
            Ssubtotal = "0";
            Ssubtotal2 = "0";
            Stipo = ENTveni.getTipo_temporal();
            String dato2[] = {Sidproducto, Sdescripcion, Sprecio_venta1, Scantidad, Ssubtotal, Sprecio_venta2, Sprecio_compra, Stipo, Ssubtotal2};
            eveJtab.cargar_tabla_datos(tblitem_producto, model_itemf, dato2);
        }
        eveJtab.calcular_subtotal(tblitem_producto, model_itemf, 3, 5, 4, true);
        eveJtab.calcular_subtotal(tblitem_producto, model_itemf, 3, 5, 8, false);
        jFtotal_consumo.setValue(eveJtab.getDouble_sumar_tabla(tblitem_producto, 8));
    }

    private void reestableser_item_venta() {
        txtcod_barra.setText(null);
        txtbuscar_producto.setText(null);
        precio_venta_mostrar = "0";
        precio_venta_oculto = "0";
        precio_compra_oculto = "0";
        txtcantidad_producto.setText("1");
        carga_prod_codbarra = false;
        actualizar_tabla_producto(1);
        tiempo_boton_hab = 0;
        txtcod_barra.grabFocus();
    }

    private void cargar_con_cantidad(int cant) {
        txtcantidad_producto.setText(String.valueOf(cant));
        cargar_item_producto();
    }

    private void actualizar_estado_puerta_cliente_limpieza() {
        BOhrt.update_habitacion_recepcion_temp_puertas(sensor_puerta_cliente, sensor_puerta_limpieza);
    }

    private void cargar_estados_puertas_gpio(boolean es_inicio) {
        String titulo = "cargar_estados_puertas_gpio";
        String sql = "select hd.idhabitacion_dato,hd.es_manual,\n"
                + "(select case when hd.es_manual=true then true else ig.alto_bajo end from habitacion_item_sensor_gpio ig \n"
                + "where ig.fk_idhabitacion_sensor=" + sensor_puerta_cliente + " \n"
                + "and ig.fk_idhabitacion_dato=hd.idhabitacion_dato ) as cliente,\n"
                + "(select case when hd.es_manual=true then true else ig.alto_bajo end from habitacion_item_sensor_gpio ig \n"
                + "where ig.fk_idhabitacion_sensor=" + sensor_puerta_limpieza + " \n"
                + "and ig.fk_idhabitacion_dato=hd.idhabitacion_dato ) as limpieza\n"
                + "from habitacion_dato hd \n"
                + "where hd.activo=true "
                + "order by hd.idhabitacion_dato asc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL_sinprint(conn, sql, titulo);
            int fila = 0;
            panel_puerta.removeAll();
            while (rs.next()) {
                int idhabitacion_dato = rs.getInt("idhabitacion_dato");
                boolean cliente = rs.getBoolean("cliente");
                boolean limpieza = rs.getBoolean("limpieza");
                boolean es_manual = rs.getBoolean("es_manual");
                Ia_puerta_nro_habitacion[fila] = idhabitacion_dato;
                Ba_puerta_cliente[fila] = cliente;
                Ba_puerta_limpieza[fila] = limpieza;
                Ba_es_manual[fila] = es_manual;
                agregar_label_puertas(idhabitacion_dato, cliente, limpieza, es_manual);
                fila++;
            }
            panel_puerta.updateUI();
        } catch (SQLException e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    private void agregar_label_puertas(int idhabitacion_dato, boolean cliente, boolean limpieza, boolean es_manual) {
//        JButton boton = new JButton(textboton);
//        boton.setFont(new Font("Arial", Font.BOLD, 10));
        JTextField txtpuerta_cliente = new JTextField("C:" + idhabitacion_dato);
        JTextField txtpuerta_limpieza = new JTextField("L:" + idhabitacion_dato);
        txtpuerta_cliente.setFont(new Font("Arial", Font.BOLD, 11));
        txtpuerta_limpieza.setFont(new Font("Arial", Font.BOLD, 11));
        if (es_manual) {
            txtpuerta_cliente.setBackground(Color.white);
            txtpuerta_limpieza.setBackground(Color.white);
        } else {
            if (cliente) {
                txtpuerta_cliente.setBackground(Color.green);
            }
            if (!cliente) {
                txtpuerta_cliente.setBackground(Color.red);
            }
            if (limpieza) {
                txtpuerta_limpieza.setBackground(Color.green);
            }
            if (!limpieza) {
                txtpuerta_limpieza.setBackground(Color.yellow);
            }
        }
        panel_puerta.add(txtpuerta_cliente);
        panel_puerta.add(txtpuerta_limpieza);
//        botones_puerta.add(txtpuerta_cliente);
//        botones_puerta.add(txtpuerta_limpieza);
    }

    private void limpiar_panel(JPanel panel) {
        String titulo = "limpiar_panel_puertas";
        try {
            panel.removeAll();
        } catch (Exception e) {
            evemen.mensaje_error(e, titulo);
        }
    }

    private void imprimir_sql_recepcion_temp() {
        evemen.mensaje_JTextArea(DAOhrt.getSql_ocupacion_boton());
    }

//    private void cargar_array_habitacion_datos_mudar() {
//        if (boton_mudar_unavez) {
//            limpiar_panel(panel_habitaciones_libre);
//        }
//        for (int fila = 0; fila < cant_de_habitacion; fila++) {
//            crear_unitario_boton_habitacion_mudar(fila, Sa_tipo_habitacion[fila], Ia_nro_habitacion[fila], Sa_estado[fila],
//                    Sa_desc_estado[fila], Sa_tiempo[fila], Sa_monto[fila], Ia_idhabitacion_dato[fila]);
//        }
//        panel_habitaciones_libre.updateUI();
//        boton_mudar_unavez = false;
//    }
    private void cargar_jbar_tiempo_minimo(int ocupado_inicio_seg, int minuto_minimo) {
        int ocupado_inicio_min = (ocupado_inicio_seg / 60);
        if (ocupado_inicio_min <= minuto_minimo) {
            jbar_tiempo_minimo.setValue(ocupado_inicio_min);
            int min_regresivo = minuto_minimo - ocupado_inicio_min;
            jbar_tiempo_minimo.setString(min_regresivo + " m");
            jbar_tiempo_minimo.setForeground(new Color(102, 204, 255));
            if (ocupado_inicio_min > (minuto_minimo - (minuto_minimo / 3))) {
                jbar_tiempo_minimo.setForeground(Color.yellow);
            }
        } else {
            jbar_tiempo_minimo.setValue(minuto_minimo);
            jbar_tiempo_minimo.setForeground(Color.red);
            jbar_tiempo_minimo.setString("Tiempo Agotado");
        }
    }

    public void limpiar_cargar_tabla_venta_item(Connection conn, JTable tbltabla, int fk_idventa) {
        eveJtab.limpiar_tabla_datos(model_itemf);
        String titulo = "limpiar_cargar_tabla_venta_item";
        String sql = "SELECT fk_idproducto as id,descripcion,"
                + "to_char(precio_venta,'" + eveest.getForm_nro_9D() + "') as precio,"
                + "cantidad as cant,"
                + "to_char(precio_venta*cantidad,'" + eveest.getForm_nro_9D() + "') as total,"
                + "precio_venta as opventa,"
                + "precio_compra as opcompra,"
                + "tipo_item as otipo,"
                + "(precio_venta*cantidad) as ototal "
                + "FROM venta_item "
                + "where fk_idventa=" + fk_idventa
                + " and cantidad>0 "
                + "order by 1 desc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL_sinprint(conn, sql, titulo);
            while (rs.next()) {
                String Sidproducto = rs.getString("id");
                String Sdescripcion = rs.getString("descripcion");
                String Sprecio_venta1 = rs.getString("precio");
                String Scantidad = rs.getString("cant");
                String Ssubtotal = rs.getString("total");
                String Sprecio_venta2 = rs.getString("opventa");
                String Sprecio_compra = rs.getString("opcompra");
                String Stipo = rs.getString("otipo");
                String Ssubtotal2 = rs.getString("ototal");
                String dato[] = {Sidproducto, Sdescripcion, Sprecio_venta1, Scantidad, Ssubtotal, Sprecio_venta2, Sprecio_compra, Stipo, Ssubtotal2};
                eveJtab.cargar_tabla_datos(tbltabla, model_itemf, dato);
            }
            monto_consumo = eveJtab.getDouble_sumar_tabla(tbltabla, 8);
            jFtotal_consumo.setValue(monto_consumo);
            jFmonto_consumo.setValue(monto_consumo);
        } catch (SQLException e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }

    }

    private void iniciarTiempo() {
        tiempo_boton = new Timer();
        tiempo_boton.schedule(new FrmOcupacion.clasetiempo(), 0, 1000 * 1);
        System.out.println("Timer INICIAR");
    }

    class clasetiempo extends TimerTask {

        public void run() {
            try {
                segundo_tiempo_esten++;
                segundo_tiempo_resumen++;
                segundo_conn_rpi++;
                segundo_act_btn++;
                int tab_select = jTab_principal.getSelectedIndex();
                fecha_hora_ahora = evefec.getString_formato_fecha_hora();
                txttiempo_ahora.setText(fecha_hora_ahora);
                if (segundo_tiempo_esten >= max_tiempo_esten) {
                    ejecutar_update_habitacion_recepcion_temp_FUERTE();
                    cargar_usuario_acceso();
                    jPanel_estado_habitacion.setBackground(new Color(255, 216, 204));
                    segundo_tiempo_esten = 0;
                } else {
                    no_es_sonido_ocupado = true;
                    jPanel_estado_habitacion.setBackground(new Color(240, 240, 240));
                    if (seg_intercalar) {
                        ejecutar_update_habitacion_recepcion_temp_LIBIANO();
                        cargar_boton_habitacion(false);
                        cargar_estados_puertas_gpio(false);
                        seg_intercalar = false;
                    } else {
                        actualizar_boton_habitacion();
                        seg_intercalar = true;
                    }
                    if (tab_select == 0) {

//                        cargar_array_habitacion_puertas();
                    }
                    if (tab_select == 4) {
                        if (seg_intercalar) {
                            cargar_boton_habitacion_mudar();
                            seg_intercalar = false;
                        } else {
                            seg_intercalar = true;
                        }

//                        cargar_array_habitacion_datos_mudar();
                    }
                }
                if (segundo_conn_rpi > 5) {
                    boton_raspberry_ultima_conexion();
                    segundo_conn_rpi = 0;
                }
                if (segundo_act_btn > 10) {
                    cant_add_hab_boton = 0;
                    segundo_act_btn = 0;
                }
                mostrar_boton_hab(tab_select);
            } catch (RuntimeException e) {
                System.err.println("Uncaught Runtime Exception" + e);
                JOptionPane.showMessageDialog(null, "Error:" + e + "\nEL SISTEMA SE REINICIA", "ERROR TIMER", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
                return; // Keep working
            } catch (Throwable e) {
                System.err.println("Unrecoverable error" + e);
                JOptionPane.showMessageDialog(null, "Error:" + e + "\nEL SISTEMA SE REINICIA", "ERROR TIMER", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
                throw e;
            } finally {
//                System.err.print("final");
            }

        }//fin run
    }

    private void boton_raspberry_ultima_conexion() {
        String titulo = "boton_raspberry_ultima_conexion";
        String sql = "select idhabitacion_mini_pc, \n"
                + "case when (((extract(epoch from (current_timestamp-ult_conexion)))>(-2)) \n"
                + "and ((extract(epoch from (current_timestamp-ult_conexion)))<(70)))  \n"
                + "then true else false end as est_boton,"
                + "TRIM(to_char((extract(epoch from (current_timestamp-ult_conexion))),'9G990D99')) as tiempo\n"
                + " from habitacion_mini_pc order by idhabitacion_mini_pc asc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL_sinprint(conn, sql, titulo);
            while (rs.next()) {
                int idhabitacion_mini_pc = rs.getInt("idhabitacion_mini_pc");
                boolean est_boton = rs.getBoolean("est_boton");
                String tiempo = rs.getString("tiempo");
                color_boton_rpi(idhabitacion_mini_pc, 2, est_boton, btnrpi_1, tiempo, btn_reboot_rp_1);
                color_boton_rpi(idhabitacion_mini_pc, 3, est_boton, btnrpi_2, tiempo, btn_reboot_rp_2);
                color_boton_rpi(idhabitacion_mini_pc, 4, est_boton, btnrpi_3, tiempo, btn_reboot_rp_3);
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void color_boton_rpi(int idhabitacion_mini_pc, int select, boolean est_boton, JButton btnrpi, String tiempo, boolean reboot_rp) {
        if (idhabitacion_mini_pc == select) {
            if (est_boton) {
                btnrpi.setBackground(Color.white);
                btnrpi.setForeground(Color.black);
                if (idhabitacion_mini_pc == 2) {
                    btn_reboot_rp_1 = false;
                }
                if (idhabitacion_mini_pc == 3) {
                    btn_reboot_rp_2 = false;
                }
                if (idhabitacion_mini_pc == 4) {
                    btn_reboot_rp_3 = false;
                }
                btnrpi.setText("RPI-" + select + ":" + tiempo);
            } else {
                btnrpi.setBackground(Color.red);
                btnrpi.setForeground(Color.white);
                btnrpi.setText("OFF-LINE");
            }
            if (reboot_rp) {
                btnrpi.setText("REINICIO");
                btnrpi.setBackground(Color.ORANGE);
                btnrpi.setForeground(Color.BLUE);
            } else {
//                btnrpi.setText("RPI-" + select + ":" + tiempo);
            }

        }
    }

    private void mostrar_boton_hab(int tab_select) {
        if (tab_select == 1 || tab_select == 2 || tab_select == 3 || tab_select == 4) {
            if (tiempo_boton_hab >= tiempo_boton_hab_max) {
                tiempo_boton_hab = 0;
                suma_tiempo_titulo = "";
                eveJtab.mostrar_JTabbedPane(jTab_principal, 0);
//                limpiar_habitacion_select();
            } else {
                tiempo_boton_hab++;
                if (tiempo_boton_hab == 2) {
                    suma_tiempo_titulo = "";
                }
                suma_tiempo_titulo = suma_tiempo_titulo + "#";
                this.setTitle(nombreTabla_pri + "/ USUARIO:" + creado_por + " / " + tiempo_boton_hab + ":" + tiempo_boton_hab_max + " / " + suma_tiempo_titulo);
            }
        } else {
            tiempo_boton_hab = 0;
        }
    }

    private void pararTimer() {
        FrmMenuMotel.setHabilitar_sonido(false);
        tiempo_boton.cancel();
        System.out.println("Timer PARADO:");
    }

    private void icono_tipo_habitacion(String tipo_habitacion) {
        if (tipo_habitacion.equals(eveest.getTipo_hab_estandar())) {
            lblicono_tipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/96_camaamor.png")));
        }
        if (tipo_habitacion.equals(eveest.getTipo_hab_vip())) {
            lblicono_tipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/96_corona.png")));
        }
        if (tipo_habitacion.equals(eveest.getTipo_hab_luxury())) {
            lblicono_tipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/96_diamante.png")));
        }
        if (tipo_habitacion.equals(eveest.getTipo_hab_penthouse())) {
            lblicono_tipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/96_motel.png")));
        }
    }

    private void cargar_costos(int idhabitacion_dato, boolean es_por_dormir, boolean es_por_hora, boolean es_por_hospedaje, int cant_add_tarifa_hora) {
        DAOhrt.cargar_habitacion_recepcion_temp(conn, ENThrt, idhabitacion_dato);
        monto_por_hora_minimo = ENThrt.getC26monto_por_hora_minimo();
        monto_por_hora_adicional = ENThrt.getC27monto_por_hora_adicional();
        monto_por_hospedaje_minimo = ENThrt.getC46monto_por_hospedaje_minimo();
        monto_por_dormir_minimo = ENThrt.getC28monto_por_dormir_minimo();
        monto_por_dormir_adicional = ENThrt.getC29monto_por_dormir_adicional();
        monto_descuento = ENThrt.getC31monto_descuento();
        monto_consumo = ENThrt.getC30monto_consumision();
        monto_adelanto = ENThrt.getC41monto_adelanto();
        monto_adelanto_1 = ENThrt.getC41monto_adelanto();
        cant_adicional = cant_add_tarifa_hora;
        jFmonto_hora_minimo.setValue(monto_por_hora_minimo);
        jFmonto_hora_adicional.setValue(monto_por_hora_adicional);
        jFmonto_dormir_minimo.setValue(monto_por_dormir_minimo);
        jFmonto_dormir_adicional.setValue(ENThrt.getC29monto_por_dormir_adicional());
        jFmonto_hospedaje.setValue(ENThrt.getC46monto_por_hospedaje_minimo());
        txtminumo_minimo.setText(String.valueOf(ENThrt.getC32minuto_minimo()));
        txtminuto_adicional.setText(String.valueOf(ENThrt.getC33minuto_adicional()));
        txtminuto_cancelar.setText(String.valueOf(ENThrt.getC34minuto_cancelar()));
        txtdescripcion_habitacion.setText(ENThrt.getC6descripcion_habitacion());
        jFdormir_ingreso_inicio.setText(ENThrt.getC35hs_dormir_ingreso_inicio());
        jFdormir_ingreso_final.setText(ENThrt.getC36hs_dormir_ingreso_final());
        jFdormir_salida_final.setText(ENThrt.getC37hs_dormir_salida_final());
        hs_dormir_salida_final_select = ENThrt.getC37hs_dormir_salida_final();
        es_por_hora_select = ENThrt.getC24es_por_hora();
        es_por_dormir_select = ENThrt.getC25es_por_dormir();
        es_por_hospedaje_select = ENThrt.getC49es_hospedaje();
        String tipo_ocupa = "";
        if (!es_por_hora && es_por_dormir) {
            tipo_ocupa = "POR DORMIR";
            jRpor_dormir.setSelected(true);
            jFdormir_salida_final.setBackground(Color.red);
            jFdormir_salida_final.setForeground(Color.yellow);
            btncambiar_salida_final.setEnabled(true);
            lbltipo_tarifa_icono.setIcon(new javax.swing.ImageIcon(getClass().getResource(eveest.getIco_dormir())));
            monto_adicional = monto_por_dormir_adicional;
            monto_minimo_A = monto_por_dormir_minimo;
        }
        if (es_por_hora && !es_por_dormir) {
            tipo_ocupa = "POR HORA";
            jRpor_hora.setSelected(true);
            jFdormir_salida_final.setBackground(Color.white);
            jFdormir_salida_final.setForeground(Color.BLACK);
            btncambiar_salida_final.setEnabled(false);
            lbltipo_tarifa_icono.setIcon(new javax.swing.ImageIcon(getClass().getResource(eveest.getIco_ocupa())));
            monto_adicional = monto_por_hora_adicional;
            monto_minimo_A = monto_por_hora_minimo;
        }
        if (es_por_hora && es_por_dormir) {
            tipo_ocupa = "POR HORA MAS DORMIR";
            jRpor_hora_mas_dormir.setSelected(true);
            jFdormir_salida_final.setBackground(Color.red);
            jFdormir_salida_final.setForeground(Color.yellow);
            btncambiar_salida_final.setEnabled(true);
            lbltipo_tarifa_icono.setIcon(new javax.swing.ImageIcon(getClass().getResource(eveest.getIco_dormir())));
            monto_adicional = monto_por_dormir_adicional;
            monto_minimo_A = monto_por_dormir_minimo;
        }
        if (!es_por_hora && !es_por_dormir && es_por_hospedaje) {
            tipo_ocupa = "POR HOSPEDAJE";
            jRpor_hospedaje.setSelected(true);
            jFdormir_salida_final.setBackground(Color.red);
            jFdormir_salida_final.setForeground(Color.yellow);
            btncambiar_salida_final.setEnabled(true);
            lbltipo_tarifa_icono.setIcon(new javax.swing.ImageIcon(getClass().getResource(eveest.getIco_dormir())));
            monto_adicional = monto_por_hora_adicional;
            monto_minimo_A = monto_por_hospedaje_minimo;
        }
        monto_adicional_total = cant_adicional * (int) monto_adicional;
        monto_tarifa_ocupacion_total = monto_adicional_total + (int) monto_minimo_A;
        jFmonto_tarifa_ocupacion_total.setValue(monto_tarifa_ocupacion_total);
        jFmonto_minimo.setValue(monto_minimo_A);
        jFmonto_adicional.setValue(monto_adicional);
        txtcant_adicional.setText(String.valueOf(cant_adicional));
        jFmonto_adicional_total.setValue(monto_adicional_total);
        txtmonto_adelanto.setText(null);
        calculo_monto_pagar();
        txtmonto_descontar.setText(String.valueOf((int) monto_descuento));
        cargar_observacion_venta("#==>>cargar_costos<<"
                + "\n1-monto_por_hora_minimo:" + monto_por_hora_minimo
                + "\n2-monto_por_hora_adicional:" + monto_por_hora_adicional
                + "\n3-monto_por_dormir_minimo:" + monto_por_dormir_minimo
                + "\n4-monto_por_dormir_adicional:" + monto_por_dormir_adicional
                + "\n5-cant_adicional:" + cant_adicional
                + "\n6-monto_adicional:" + monto_adicional
                + "\n7-monto_minimo_A:" + monto_minimo_A
                + "\n8-tipo_ocupa:" + tipo_ocupa
                + "\n9-monto_total_pagar:" + monto_total_pagar, false
        );//monto_total_pagar
        if (monto_adelanto > 0) {
            btnadelanto.setEnabled(false);
            es_adelanto_cargado = true;
            btnadelanto.setText("EDITAR-ADELANTO");
            txtmonto_adelanto.setText(evejtf.getString_format_nro_decimal(monto_adelanto));
            txtmonto_adelanto.setBackground(Color.yellow);
        } else {
            es_adelanto_cargado = false;
            btnadelanto.setEnabled(true);
            btnadelanto.setText("NUEVO-ADELANTO");

        }
    }

    private void limpiar_habitacion_select() {
        segundo_tiempo_esten = max_tiempo_esten - limit_tiempo_esten;
        cargar_usuario_acceso();
        limpiar_variable();
        boton_hab_unavez = true;
        nro_habitacion_select = 0;
        motivo_anulacion = "-";
        txtnro_hab_grande.setText("0");
        txtnro_hab_grande_salir.setText("0");
        txtrecepcion_actual.setText("0");
        jFmonto_hora_minimo.setValue(0);
        jFmonto_hora_adicional.setValue(0);
        jFmonto_dormir_minimo.setValue(0);
        jFmonto_dormir_adicional.setValue(0);
        jFmonto_hospedaje.setValue(0);
        txtminumo_minimo.setText(null);
        txtminuto_adicional.setText(null);
        txtminuto_cancelar.setText(null);
        jFdormir_ingreso_inicio.setText(null);
        jFdormir_ingreso_final.setText(null);
        jFdormir_salida_final.setText(null);
        txtfec_ocupado_inicio.setText(null);
        txtfec_ocupado_inicio_hora.setText(null);
        txttiempo_transcurrido.setText(null);
        txttiempo_transcurrido_salir.setText(null);
        txtmonto_descontar.setText(null);
        txtmonto_adelanto.setText(null);
        jFmonto_minimo.setValue(0);
        txtcant_adicional.setText(null);
        jFmonto_adicional.setValue(0);
        jFmonto_adicional_total.setValue(0);
        jFmonto_tarifa_ocupacion_total.setValue(0);
        jFmonto_total_pagar.setValue(0);
        jFmonto_total_pagar_salir.setValue(0);
        color_panel_venta(2);
//        jRpor_hora.setSelected(true);
        this.setTitle(nombreTabla_pri + " USUARIO:" + creado_por);
        DAOveni.actualizar_tabla_venta_item(conn, tblitem_consumo_cargado, fk_idventa);
        limpiar_cargar_tabla_venta_item(conn, tblitem_producto, fk_idventa);
        eveJtab.mostrar_JTabbedPane(jTab_principal, select_tab_principal);
    }

    private void cargar_dato_habitacion_recepcion_ocupar(int nro_habitacion, int idhabitacion_dato) {
        ENThr.setC3creado_por(creado_por);
        ENThr.setC4estado(eveest.getEst_Ocupado());
        ENThr.setC5nro_habitacion(nro_habitacion);
        ENThr.setC8fec_ocupado_inicio(evefec.getString_formato_fecha_hora());
        ENThr.setC16es_libre(false);
        ENThr.setC17es_ocupado(true);
        ENThr.setC18es_sucio(false);
        ENThr.setC19es_limpieza(false);
        ENThr.setC20es_mante(false);
        ENThr.setC21es_cancelado(false);
        ENThr.setC22es_por_hora(true);
        ENThr.setC23es_por_dormir(false);
        ENThr.setC24es_boton_actual(true);
        ENThr.setC25es_pagado(false);
        ENThr.setC26es_terminado(false);
        ENThr.setC27monto_por_hora_minimo(monto_por_hora_minimo);
        ENThr.setC28monto_por_hora_adicional(monto_por_hora_adicional);
        ENThr.setC29monto_por_dormir_minimo(monto_por_dormir_minimo);
        ENThr.setC30monto_por_dormir_adicional(monto_por_dormir_adicional);
        ENThr.setC31monto_consumo(0);
        ENThr.setC32monto_descuento(0);
        ENThr.setC33monto_adelanto(0);
        ENThr.setC34cant_adicional(0);
        ENThr.setC35fk_idhabitacion_dato(idhabitacion_dato);
        ENThr.setC36monto_por_hospedaje_minimo(monto_por_hospedaje_minimo);
        ENThr.setC37fec_hospedaje_inicio(evefec.getString_formato_fecha_hora());
        ENThr.setC38fec_hospedaje_fin(evefec.getString_formato_fecha_hora());
        ENThr.setC39es_hospedaje(false);
    }

    private void cargar_dato_venta_ocupar(int fk_idhabitacion_recepcion) {
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        ENTven.setC3creado_por(creado_por);
        ENTven.setC4monto_letra("cero");
        ENTven.setC5estado(eveest.getEst_Ocupado());
        ENTven.setC6observacion(observacion_venta);
        ENTven.setC7tipo_persona("cliente");
        ENTven.setC8motivo_anulacion(motivo_anulacion);
        ENTven.setC9motivo_mudar_habitacion("sin");
        ENTven.setC10monto_minimo(monto_minimo_A);
        ENTven.setC11monto_adicional(0);
        ENTven.setC12cant_adicional(0);
        ENTven.setC13monto_consumo(0);
        ENTven.setC14monto_insumo(0);
        ENTven.setC15monto_descuento(0);
        ENTven.setC16monto_adelanto(0);
        ENTven.setC17fk_idhabitacion_recepcion(fk_idhabitacion_recepcion);
        ENTven.setC18fk_idpersona(0);
        ENTven.setC19fk_idusuario(fk_idusuario);
    }

    private void cargar_dato_hab_temp_ocupar(int nro_habitacion, int fk_idhabitacion_recepcion, int idhabitacion_dato) {
        if (true) {
            DAOhd.cargar_habitacion_dato(conn, ENThd, idhabitacion_dato);
            DAOhc.cargar_habitacion_costo(conn, ENThc, ENThd.getC11fk_idhabitacion_costo());
            hs_dormir_salida_final_select = ENThc.getC16hs_dormir_salida_final();
        }
        ENThrt.setC7estado(eveest.getEst_Ocupado());
        ENThrt.setC2idhabitacion_recepcion_actual(fk_idhabitacion_recepcion);
        ENThrt.setC5nro_habitacion(nro_habitacion);
        ENThrt.setC10fec_ocupado_inicio(evefec.getString_formato_fecha_hora());
        ENThrt.setC19es_ocupado(true);
        ENThrt.setC24es_por_hora(true);
        ENThrt.setC25es_por_dormir(false);
        ENThrt.setC30monto_consumision(0);
        ENThrt.setC31monto_descuento(0);
        ENThrt.setC41monto_adelanto(0);
        ENThrt.setC42idhabitacion_dato(idhabitacion_dato);
        ENThrt.setC49es_hospedaje(false);
        ENThrt.setC47fec_hospedaje_inicio(evefec.getString_formato_fecha_hora());
    }

    private void boton_libre_a_ocupar(int nro_habitacion, int idhabitacion_dato) {
        DAOhrt.cargar_habitacion_recepcion_temp(conn, ENThrt, idhabitacion_dato);
        monto_minimo_A = ENThrt.getC26monto_por_hora_minimo();
        int idhabitacion_recepcion = (eveconn.getInt_ultimoID_mas_uno(conn, ENThr.getTb_habitacion_recepcion(), ENThr.getId_idhabitacion_recepcion()));
        cargar_dato_venta_ocupar(idhabitacion_recepcion);
        cargar_dato_habitacion_recepcion_ocupar(nro_habitacion, idhabitacion_dato);
        cargar_dato_hab_temp_ocupar(nro_habitacion, idhabitacion_recepcion, idhabitacion_dato);
        BOven.insertar_venta(ENTven, ENThr, ENThrt);
        boton_hab_unavez = true;
        boton_mudar_unavez = true;
    }

    private void cargar_dato_venta_desocupar(int idhabitacion_recepcion_actual) {
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        DAOven.cargar_venta_idhabitacion_recepcion(conn, ENTven, idhabitacion_recepcion_actual);
        double monto_total = ((monto_minimo_D + monto_adicional_total + monto_consumo) - (monto_descuento + monto_adelanto));
        int Imonto = (int) monto_total;
        String Smonto = String.valueOf(Imonto);
        String monto_letra = evenrolt.Convertir(Smonto, true);
        ENTven.setC4monto_letra(monto_letra);
        ENTven.setC5estado(eveest.getEst_Desocupado());
        ENTven.setC6observacion(ENTven.getC6observacion() + observacion_venta);
        ENTven.setC7tipo_persona("cliente");
        ENTven.setC8motivo_anulacion(motivo_anulacion);
        ENTven.setC9motivo_mudar_habitacion("sin");
        ENTven.setC10monto_minimo(monto_minimo_D);
        ENTven.setC11monto_adicional(monto_adicional_total);
        ENTven.setC12cant_adicional(cant_adicional);
        ENTven.setC13monto_consumo(monto_consumo);
        ENTven.setC14monto_insumo(monto_insumo);
        ENTven.setC15monto_descuento(monto_descuento);
        ENTven.setC16monto_adelanto(monto_adelanto);
        ENTven.setC17fk_idhabitacion_recepcion(idhabitacion_recepcion_actual);
        ENTven.setC18fk_idpersona(1);
        ENTven.setC19fk_idusuario(fk_idusuario);
    }

    private void cargar_dato_factura() {
        fk_idfactura = (eveconn.getInt_ultimoID_mas_uno(conn, ENTf.getTb_factura(), ENTf.getId_idfactura()));
        double monto_total = ((monto_minimo_D + monto_adicional_total + monto_consumo) - (monto_descuento + monto_adelanto));
        int Imonto = (int) monto_total;
        String Smonto = String.valueOf(Imonto);
        String monto_letra = evenrolt.Convertir(Smonto, true);
        int Inro = DAOf.getInt_ult_nro_factura(conn);
        String Snro = String.valueOf(Inro);
        String nro_7 = String.format("%7s", Snro).replace(' ', '0');
        String nro_factura = "001-001-" + nro_7;
        ENTf.setC3creado_por(creado_por);
        ENTf.setC4nro_factura(nro_factura);
        ENTf.setC5fecha_nota("current_date");
        ENTf.setC6estado(eveest.getEst_Emitido());
        ENTf.setC7condicion(eveest.getCond_Contado());
        ENTf.setC8monto_total(monto_total);
        ENTf.setC9monto_iva5(0);
        ENTf.setC10monto_iva10(monto_total / 11);
        ENTf.setC11monto_letra(monto_letra);
        ENTf.setC12fk_idtimbrado(1);
        ENTf.setC13fk_idpersona(1);
        ENTf.setC14fk_idventa(fk_idventa);
        ENTf.setC15numero(Inro);
    }

    private void cargar_dato_factura_item_ocupa() {
        double monto_total = ((monto_minimo_D + monto_adicional_total) - (monto_descuento + monto_adelanto));
        String descripcion = "HOSPEDAJE: " + descripcion_caja_desocupa;
        ENTfi.setC3creado_por(creado_por);
        ENTfi.setC4descripcion(descripcion);
        ENTfi.setC5cantidad(1);
        ENTfi.setC6precio_iva5(0);
        ENTfi.setC7precio_iva10(monto_total);
        ENTfi.setC8precio_exenta(0);
        ENTfi.setC9tipo_item("OCUPACION");
        ENTfi.setC10fk_idfactura(fk_idfactura);
        ENTfi.setC11fk_idproducto(0);
    }

    private void cargar_dato_habitacion_recepcion_desocupar(int idhabitacion_recepcion_actual) {
        DAOhr.cargar_habitacion_recepcion(conn, ENThr, idhabitacion_recepcion_actual);
        ENThr.setC1idhabitacion_recepcion(idhabitacion_recepcion_actual);
        ENThr.setC4estado(eveest.getEst_Sucio());
        ENThr.setC9fec_ocupado_fin(evefec.getString_formato_fecha_hora());
        ENThr.setC10fec_sucio_inicio(evefec.getString_formato_fecha_hora());
        ENThr.setC18es_sucio(true);
        ENThr.setC25es_pagado(true);
        ENThr.setC27monto_por_hora_minimo(monto_por_hora_minimo);
        ENThr.setC28monto_por_hora_adicional(monto_por_hora_adicional);
        ENThr.setC29monto_por_dormir_minimo(monto_por_dormir_minimo);
        ENThr.setC30monto_por_dormir_adicional(monto_por_dormir_adicional);
        ENThr.setC31monto_consumo(monto_consumo);
        ENThr.setC32monto_descuento(monto_descuento);
        ENThr.setC33monto_adelanto(monto_adelanto);
        ENThr.setC34cant_adicional(cant_adicional);
        ENThr.setC38fec_hospedaje_fin(evefec.getString_formato_fecha_hora());
    }

    private void cargar_dato_habitacion_recepcion_temp_desocupar(int idhabitacion_dato) {
        DAOhrt.cargar_habitacion_recepcion_temp(conn, ENThrt, idhabitacion_dato);
        ENThrt.setC7estado(eveest.getEst_Sucio());
        ENThrt.setC11fec_ocupado_fin(evefec.getString_formato_fecha_hora());
        ENThrt.setC12fec_sucio_inicio(evefec.getString_formato_fecha_hora());
        ENThrt.setC20es_sucio(true);
        ENThrt.setC49es_hospedaje(false);
        ENThrt.setC48fec_hospedaje_fin(evefec.getString_formato_fecha_hora());
    }

    private void cargar_observacion_venta(String evento, boolean update_obs) {
        String salto_linea = "\n";
        String fecha_hora = evefec.getString_formato_fecha_hora();
        observacion_venta = observacion_venta + fecha_hora + "=" + evento + salto_linea;
        if (update_obs) {
            ENTven.setC6observacion(ENTven.getC6observacion() + observacion_venta);
            ENTven.setC17fk_idhabitacion_recepcion(fk_idhabitacion_recepcion_actual_select);
            DAOven.update_venta_obs(conn, ENTven);
        }
    }

    private void limpiar_variable() {
        fk_idventa = 0;
        monto_minimo_A = 0;
        monto_adicional_total = 0;
        cant_adicional = 0;
        monto_descuento = 0;
        monto_adelanto = 0;
        monto_consumo = 0;
        monto_total_pagar = 0;
        descripcion_consumo_adelantado = "";
        motivo_anulacion = "";
        monto_por_hora_minimo = 0;
        monto_por_hora_adicional = 0;
        monto_por_dormir_minimo = 0;
        monto_por_dormir_adicional = 0;
    }

    private void reestableser_venta() {
        cargar_usuario_acceso();
        limpiar_variable();
        observacion_venta = "";
        txtidventa.setText(null);
        eveJtab.limpiar_tabla_datos(model_itemf);
        jFtotal_consumo.setValue(0);
        if (fk_idventa_ult_print > 0) {
            btnimprimir_ultimo.setEnabled(true);
            btnimprimir_ultimo.setText("IMP. TICKET (" + nro_habitacion_select + ")");
        }
        if (fk_idfactura_ult_print > 0) {
            btnimprimir_ultimo.setEnabled(true);
            btnimprimir_ultimo.setText("IMP. FACTURA (" + nro_habitacion_select + ")");
        }
        if (fk_idventa_ult_print == 0 && fk_idfactura_ult_print == 0) {
            btnimprimir_ultimo.setEnabled(false);
        }
    }

    private void ejecutar_mudar_habitacion(int idhabitacion_dato, int nro_habitacion) {
        String titulo = "ejecutar_mudar_habitacion";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            //#####MUDAR_SALIR#######
            cargar_dato_venta_desocupar_MUDAR_SALIR(fk_idhabitacion_recepcion_actual_select);
            cargar_dato_habitacion_recepcion_desocupar_MUDAR_SALIR(fk_idhabitacion_recepcion_actual_select);
            cargar_dato_hab_temp_sucio_MUDAR_SALIR(fk_idhabitacion_dato_select);
            cargar_dato_caja_detalle_desocupar_MUDAR_SALIR(fk_idhabitacion_recepcion_actual_select);
            BOven.update_venta_sin_commit1(conn, ENTven, ENThr, ENThrt, ENTccd, true, false, true);
            //#####OCUPAR-ENTRAR#######
            DAOhrt.cargar_habitacion_recepcion_temp(conn, ENThrt, idhabitacion_dato);
            monto_minimo_A = ENThrt.getC26monto_por_hora_minimo();
            int idhabitacion_recepcion = (eveconn.getInt_ultimoID_mas_uno(conn, ENThr.getTb_habitacion_recepcion(), ENThr.getId_idhabitacion_recepcion()));
            int idventa = (eveconn.getInt_ultimoID_max(conn, ENTven.getTb_venta(), ENTven.getId_idventa()));
            cargar_dato_venta_ocupar_MUDAR_ENTRAR(idhabitacion_recepcion);
            cargar_dato_habitacion_recepcion_ocupar_MUDAR_ENTRAR(nro_habitacion, idhabitacion_dato);
            cargar_dato_hab_temp_ocupar_MUDAR_ENTRAR(nro_habitacion, idhabitacion_recepcion, idhabitacion_dato);
            cargar_dato_caja_detalle_ADELANTO_MUDAR(idhabitacion_recepcion, nro_habitacion, idventa);
            BOven.insertar_venta_sin_commit(conn, ENTven, ENThr, ENThrt, ENTccd);
            int idventa2 = (eveconn.getInt_ultimoID_max(conn, ENTven.getTb_venta(), ENTven.getId_idventa()));
            ENTven.setC1idventa(idventa2);
            ENTven.setC3creado_por(creado_por);
            BOveni.insertar_venta_item_sin_commit(conn, ENTveni, ENTven, tblitem_producto, true, false, false);
            boton_hab_unavez = true;
            conn.commit();
        } catch (SQLException e) {
            evemen.mensaje_error(e, ENTven.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evemen.Imprimir_serial_sql_error(e1, ENTven.toString(), titulo);
            }
        }
    }

    private void cargar_dato_caja_detalle_ADELANTO_MUDAR(int idhabitacion_recepcion, int nro_habitacion, int idventa) {
        int idcaja_cierre_detalle = DAOccd.getInt_idcaja_cierre_detalle_por_otro_id(conn, "fk_idventa", idventa);
        if (idcaja_cierre_detalle > 0) {
            creado_por = ENTusu.getGlobal_nombre();
            fk_idusuario = ENTusu.getGlobal_idusuario();
            ENTccd.setC1idcaja_cierre_detalle(idcaja_cierre_detalle);
            DAOccd.cargar_caja_cierre_detalle(conn, ENTccd, idcaja_cierre_detalle);
            String descripcion = idhabitacion_recepcion + "-(" + eveest.getEst_Adelanto() + ")-HAB: " + nro_habitacion
                    + ", TIEMPO:(" + tiempo_select + ") MUDADO: " + motivo_mudar_habitacion;
            ENTccd.setC3creado_por(creado_por);
            ENTccd.setC4cerrado_por(eveest.getEst_Adelanto());
            ENTccd.setC5es_cerrado(false);
            ENTccd.setC6monto_apertura_caja(0);
            ENTccd.setC7monto_cierre_caja(0);
            ENTccd.setC8monto_ocupa_minimo(0);
            ENTccd.setC9monto_ocupa_adicional(0);
            ENTccd.setC10monto_ocupa_consumo(0);
            ENTccd.setC11monto_ocupa_descuento(0);
            ENTccd.setC12monto_ocupa_adelanto(0);
            ENTccd.setC13monto_gasto(0);
            ENTccd.setC14monto_compra(0);
            ENTccd.setC15monto_vale(0);
            ENTccd.setC16monto_liquidacion(0);
            ENTccd.setC17estado(eveest.getEst_Emitido());
            ENTccd.setC18descripcion(descripcion);
            ENTccd.setC19fk_idgasto(0);
            ENTccd.setC20fk_idcompra(0);
            ENTccd.setC21fk_idventa(idventa);
            ENTccd.setC22fk_idusuario(fk_idusuario);
            ENTccd.setC23fk_idrh_vale(0);
            ENTccd.setC24fk_idrh_liquidacion(0);
            ENTccd.setC25monto_solo_adelanto(monto_adelanto);
            ENTccd.setC26monto_interno(0);
            ENTccd.setC27fk_idventa_interno(0);
            ENTccd.setC28monto_garantia(0);
            ENTccd.setC29fk_idgarantia(0);
        }
    }

    private void cargar_dato_hab_temp_ocupar_MUDAR_ENTRAR(int nro_habitacion, int fk_idhabitacion_recepcion, int idhabitacion_dato) {
        ENThrt.setC7estado(eveest.getEst_Ocupado());
        ENThrt.setC2idhabitacion_recepcion_actual(fk_idhabitacion_recepcion);
        ENThrt.setC10fec_ocupado_inicio(fecha_ingreso_select + " " + hora_ingreso_select);
        ENThrt.setC19es_ocupado(true);
//        ENThrt.setC24es_por_hora(true);
//        ENThrt.setC25es_por_dormir(false);
        ENThrt.setC5nro_habitacion(nro_habitacion);
        ENThrt.setC30monto_consumision(monto_consumo);
        ENThrt.setC31monto_descuento(monto_descuento);
        ENThrt.setC41monto_adelanto(monto_adelanto);
        ENThrt.setC42idhabitacion_dato(idhabitacion_dato);
        ENThrt.setC37hs_dormir_salida_final(hs_dormir_salida_final_select);
        ENThrt.setC24es_por_hora(es_por_hora_select);
        ENThrt.setC25es_por_dormir(es_por_dormir_select);
        ENThrt.setC49es_hospedaje(es_por_hospedaje_select);
    }

    private void cargar_dato_habitacion_recepcion_ocupar_MUDAR_ENTRAR(int nro_habitacion, int idhabitacion_dato) {
        ENThr.setC3creado_por(creado_por);
        ENThr.setC4estado(eveest.getEst_Ocupado());
        ENThr.setC5nro_habitacion(nro_habitacion);
        ENThr.setC8fec_ocupado_inicio(evefec.getString_formato_fecha_hora());
        ENThr.setC16es_libre(false);
        ENThr.setC17es_ocupado(true);
        ENThr.setC18es_sucio(false);
        ENThr.setC19es_limpieza(false);
        ENThr.setC20es_mante(false);
        ENThr.setC21es_cancelado(false);
        ENThr.setC22es_por_hora(true);
        ENThr.setC23es_por_dormir(false);
        ENThr.setC24es_boton_actual(true);
        ENThr.setC25es_pagado(false);
        ENThr.setC26es_terminado(false);
        ENThr.setC27monto_por_hora_minimo(monto_por_hora_minimo);
        ENThr.setC28monto_por_hora_adicional(monto_por_hora_adicional);
        ENThr.setC29monto_por_dormir_minimo(monto_por_dormir_minimo);
        ENThr.setC30monto_por_dormir_adicional(monto_por_dormir_adicional);
        ENThr.setC31monto_consumo(monto_consumo);
        ENThr.setC32monto_descuento(monto_descuento);
        ENThr.setC33monto_adelanto(monto_adelanto);
        ENThr.setC34cant_adicional(cant_adicional);
        ENThr.setC35fk_idhabitacion_dato(idhabitacion_dato);
        ENThr.setC36monto_por_hospedaje_minimo(monto_por_hospedaje_minimo);
        ENThr.setC37fec_hospedaje_inicio(evefec.getString_formato_fecha_hora());
    }

    private void cargar_dato_venta_ocupar_MUDAR_ENTRAR(int fk_idhabitacion_recepcion) {
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        ENTven.setC3creado_por(creado_por);
        ENTven.setC4monto_letra("cero");
        ENTven.setC5estado(eveest.getEst_Ocupado());
        ENTven.setC6observacion(ENTven.getC6observacion() + observacion_venta);
        ENTven.setC7tipo_persona("cliente");
        ENTven.setC8motivo_anulacion(motivo_anulacion);
        ENTven.setC9motivo_mudar_habitacion(motivo_mudar_habitacion);
        ENTven.setC10monto_minimo(monto_minimo_A);
        ENTven.setC11monto_adicional(monto_adicional_total);
        ENTven.setC12cant_adicional(cant_adicional);
        ENTven.setC13monto_consumo(monto_consumo);
        ENTven.setC14monto_insumo(0);
        ENTven.setC15monto_descuento(monto_descuento);
        ENTven.setC16monto_adelanto(monto_adelanto);
        ENTven.setC17fk_idhabitacion_recepcion(fk_idhabitacion_recepcion);
        ENTven.setC18fk_idpersona(0);
        ENTven.setC19fk_idusuario(fk_idusuario);
    }

    private void cargar_dato_caja_detalle_desocupar_MUDAR_SALIR(int idhabitacion_recepcion_actual) {
        int idcaja_cierre_detalle = DAOccd.getInt_idcaja_cierre_detalle_por_otro_id(conn, "fk_idventa", fk_idventa);
        if (idcaja_cierre_detalle > 0) {
            creado_por = ENTusu.getGlobal_nombre();
            fk_idusuario = ENTusu.getGlobal_idusuario();
            ENTccd.setC1idcaja_cierre_detalle(idcaja_cierre_detalle);
            DAOccd.cargar_caja_cierre_detalle(conn, ENTccd, idcaja_cierre_detalle);
            String descripcion = idhabitacion_recepcion_actual + "-(" + eveest.getEst_Mudar() + ")-HAB: " + nro_habitacion_select + ", TIEMPO:(" + tiempo_select + ")";
            ENTccd.setC3creado_por(creado_por);
            ENTccd.setC4cerrado_por(eveest.getEst_Mudar());
            ENTccd.setC5es_cerrado(false);
            ENTccd.setC6monto_apertura_caja(0);
            ENTccd.setC7monto_cierre_caja(0);
            ENTccd.setC8monto_ocupa_minimo(0);
            ENTccd.setC9monto_ocupa_adicional(0);
            ENTccd.setC10monto_ocupa_consumo(0);
            ENTccd.setC11monto_ocupa_descuento(0);
            ENTccd.setC12monto_ocupa_adelanto(0);
            ENTccd.setC13monto_gasto(0);
            ENTccd.setC14monto_compra(0);
            ENTccd.setC15monto_vale(0);
            ENTccd.setC16monto_liquidacion(0);
            ENTccd.setC17estado(eveest.getEst_Mudar());
            ENTccd.setC18descripcion(descripcion);
            ENTccd.setC19fk_idgasto(0);
            ENTccd.setC20fk_idcompra(0);
            ENTccd.setC21fk_idventa(fk_idventa);
            ENTccd.setC22fk_idusuario(fk_idusuario);
            ENTccd.setC23fk_idrh_vale(0);
            ENTccd.setC24fk_idrh_liquidacion(0);
            ENTccd.setC25monto_solo_adelanto(0);
            ENTccd.setC26monto_interno(0);
            ENTccd.setC27fk_idventa_interno(0);
            ENTccd.setC28monto_garantia(0);
            ENTccd.setC29fk_idgarantia(0);
        }
    }

    private void cargar_dato_hab_temp_sucio_MUDAR_SALIR(int idhabitacion_dato) {
        DAOhrt.cargar_habitacion_recepcion_temp(conn, ENThrt, idhabitacion_dato);
        ENThrt.setC7estado(eveest.getEst_Sucio());
        ENThrt.setC11fec_ocupado_fin(evefec.getString_formato_fecha_hora());
        ENThrt.setC12fec_sucio_inicio(evefec.getString_formato_fecha_hora());
        ENThrt.setC20es_sucio(true);
    }

    private void cargar_dato_habitacion_recepcion_desocupar_MUDAR_SALIR(int idhabitacion_recepcion_actual) {
        DAOhr.cargar_habitacion_recepcion(conn, ENThr, idhabitacion_recepcion_actual);
        ENThr.setC1idhabitacion_recepcion(idhabitacion_recepcion_actual);
        ENThr.setC4estado(eveest.getEst_Mudar());
        ENThr.setC9fec_ocupado_fin(evefec.getString_formato_fecha_hora());
        ENThr.setC21es_cancelado(true);
        ENThr.setC27monto_por_hora_minimo(0);
        ENThr.setC28monto_por_hora_adicional(0);
        ENThr.setC29monto_por_dormir_minimo(0);
        ENThr.setC30monto_por_dormir_adicional(0);
        ENThr.setC31monto_consumo(0);
        ENThr.setC32monto_descuento(0);
        ENThr.setC33monto_adelanto(0);
        ENThr.setC34cant_adicional(0);
        ENThr.setC36monto_por_hospedaje_minimo(0);
        ENThr.setC38fec_hospedaje_fin(evefec.getString_formato_fecha_hora());
    }

    private void cargar_dato_venta_desocupar_MUDAR_SALIR(int idhabitacion_recepcion_actual) {
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        DAOven.cargar_venta_idhabitacion_recepcion(conn, ENTven, idhabitacion_recepcion_actual);
        ENTven.setC4monto_letra("cero");
        ENTven.setC5estado(eveest.getEst_Mudar());
        ENTven.setC6observacion(ENTven.getC6observacion() + observacion_venta);
        ENTven.setC7tipo_persona("cliente");
        ENTven.setC8motivo_anulacion(motivo_anulacion);
        ENTven.setC9motivo_mudar_habitacion(motivo_mudar_habitacion);
        ENTven.setC10monto_minimo(0);
        ENTven.setC11monto_adicional(0);
        ENTven.setC12cant_adicional(0);
        ENTven.setC13monto_consumo(0);
        ENTven.setC14monto_insumo(0);
        ENTven.setC15monto_descuento(0);
        ENTven.setC16monto_adelanto(0);
        ENTven.setC17fk_idhabitacion_recepcion(idhabitacion_recepcion_actual);
        ENTven.setC18fk_idpersona(0);
        ENTven.setC19fk_idusuario(fk_idusuario);
    }

    private void boton_desocupar_pagar() {
        if (validar_habitacion_select()) {
            tiempo_boton_hab = 0;
            String Smonto_minimo = evejtf.getString_format_nro_decimal(monto_minimo_A);
            String Smonto_adicional = evejtf.getString_format_nro_decimal(monto_adicional_total);
            String Smonto_descuento = evejtf.getString_format_nro_decimal(monto_descuento);
            String Smonto_adelanto = evejtf.getString_format_nro_decimal(monto_adelanto);
            String Smonto_consumo = evejtf.getString_format_nro_decimal(monto_consumo);
            String Smonto_total_pagar = evejtf.getString_format_nro_decimal(monto_total_pagar);
            String html_descuento = "";
            String html_adelanto = "";
            String html_linea = "";
            if (monto_descuento > 0) {
                html_descuento = "<p style=\"color:blue\"><font size=\"4\">=>DESCUENTO:</font></p>"
                        + "<p><font size=\"6\">" + Smonto_descuento + "</font></p>";
                html_linea = "<p><font size=\"4\">====================</font></p>";
            }
            if (monto_adelanto > 0) {
                html_adelanto = "<p style=\"color:blue\"><font size=\"4\">=>ADELANTO:</font></p>"
                        + "<p><font size=\"6\">" + Smonto_adelanto + "</font></p>";
                html_linea = "<p><font size=\"4\">====================</font></p>";
            }
            String mensaje = "<html><p style=\"color:red\"><font size=\"8\">HABITACION: " + nro_habitacion_select + "</font></p>"
                    + "<p style=\"color:red\"><font size=\"4\">=>TARIFA MINIMA:</font></p>"
                    + "<p><font size=\"6\">" + Smonto_minimo + "</font></p>"
                    + "<p style=\"color:red\"><font size=\"4\">=>TOTAL ADICIONAL:</font></p>"
                    + "<p><font size=\"6\">" + Smonto_adicional + "</font></p>"
                    + "<p style=\"color:red\"><font size=\"4\">=>TOTAL CONSUMO:</font></p>"
                    + "<p><font size=\"6\">" + Smonto_consumo + "</font></p>"
                    + "<p><font size=\"4\">====================</font></p>"
                    + html_descuento
                    + html_adelanto
                    + html_linea
                    + "<p style=\"color:red\"><font size=\"4\">TOTAL A PAGAR:   </font></p>"
                    + "<p><font size=\"8\">" + Smonto_total_pagar + "</font></p>"
                    + "</html>";
            int eleccion = evemen.getIntMensaje_informacion_4btn(mensaje, "DESOCUPAR HABITACION ID:" + nro_habitacion_select + ",fk_idhabitacion_dato:" + fk_idhabitacion_dato_select,
                    btndesocupar_html, btnprint_ticket_html, btnprint_factura_html, btncancelar_html);
            if (eleccion == 0 || eleccion == 1 || eleccion == 2) {
                recargar_monto_minimo_habitacion_temp(fk_idhabitacion_dato_select);
                cargar_observacion_venta("#==>>CONFIRMA SU DESOCUPACION:" + Smonto_total_pagar, false);
                cargar_dato_venta_desocupar(fk_idhabitacion_recepcion_actual_select);
                cargar_dato_habitacion_recepcion_desocupar(fk_idhabitacion_recepcion_actual_select);
                cargar_dato_habitacion_recepcion_temp_desocupar(fk_idhabitacion_dato_select);
                cargar_dato_caja_detalle_DESOCUPAR();
                DAOhrt.update_habitacion_recepcion_temp_salida_final(conn, fk_idhabitacion_dato_select);
                BOven.update_venta1(ENTven, ENThr, ENThrt, ENTccd, true, true, false);
                BOveni.insertar_venta_item(ENTveni, ENTven, tblitem_producto, false, true, false);
                if (eleccion == 0) {
//                    fk_idventa_ult_print = 0;
//                    fk_idfactura_ult_print = 0;
                }
                if (eleccion == 1) {
                    posv.boton_imprimir_pos_VENTA(conn, fk_idventa);
                    fk_idventa_ult_print = fk_idventa;
                    fk_idfactura_ult_print = 0;
                }
                if (eleccion == 2) {
                    cargar_dato_factura();
                    cargar_dato_factura_item_ocupa();
                    BOf.insertar_factura(ENTf, ENTfi, tblitem_producto);
//                    DAOf.imprimir_nota_factura(conn, fk_idfactura);
                    posfac.boton_imprimir_pos_FACTURA(conn, fk_idfactura);
                    fk_idfactura_ult_print = fk_idfactura;
                    fk_idventa_ult_print = 0;
                    DAOf.actualizar_tabla_factura_con_venta(conn, tblfactura, "");
                }
                no_es_sonido_ocupado = false;
                cargar_cantidad_entrada_abierta();
                ejecutar_update_habitacion_recepcion_temp_FUERTE();
                reestableser_venta();
                limpiar_habitacion_select();

                boton_hab_unavez = true;
            }
        }
    }

    private void cargar_dato_caja_detalle_DESOCUPAR() {
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        String descripcion = descripcion_caja_desocupa + "|";
        ENTccd.setC3creado_por(creado_por);
        ENTccd.setC4cerrado_por(eveest.getEst_Desocupado());
        ENTccd.setC5es_cerrado(false);
        ENTccd.setC6monto_apertura_caja(0);
        ENTccd.setC7monto_cierre_caja(0);
        ENTccd.setC8monto_ocupa_minimo(monto_minimo_D);
        ENTccd.setC9monto_ocupa_adicional(monto_adicional_total);
        ENTccd.setC10monto_ocupa_consumo(monto_consumo);
        ENTccd.setC11monto_ocupa_descuento(monto_descuento);
        ENTccd.setC12monto_ocupa_adelanto(monto_adelanto);
        ENTccd.setC13monto_gasto(0);
        ENTccd.setC14monto_compra(0);
        ENTccd.setC15monto_vale(0);
        ENTccd.setC16monto_liquidacion(0);
        ENTccd.setC17estado(eveest.getEst_Emitido());
        ENTccd.setC18descripcion(descripcion);
        ENTccd.setC19fk_idgasto(0);
        ENTccd.setC20fk_idcompra(0);
        ENTccd.setC21fk_idventa(fk_idventa);
        ENTccd.setC22fk_idusuario(fk_idusuario);
        ENTccd.setC23fk_idrh_vale(0);
        ENTccd.setC24fk_idrh_liquidacion(0);
        ENTccd.setC25monto_solo_adelanto(0);
        ENTccd.setC26monto_interno(0);
        ENTccd.setC27fk_idventa_interno(0);
        ENTccd.setC28monto_garantia(0);
        ENTccd.setC29fk_idgarantia(0);
    }

    private void cargar_dato_caja_detalle_ADELANTO() {
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        String descripcion = fk_idhabitacion_recepcion_actual_select + "-(" + eveest.getEst_Adelanto() + ")-HAB: " + nro_habitacion_select + ",T:(" + tiempo_select + ")";
        descripcion = descripcion + descripcion_consumo_adelantado;
        ENTccd.setC3creado_por(creado_por);
        ENTccd.setC4cerrado_por(eveest.getEst_Adelanto());
        ENTccd.setC5es_cerrado(false);
        ENTccd.setC6monto_apertura_caja(0);
        ENTccd.setC7monto_cierre_caja(0);
        ENTccd.setC8monto_ocupa_minimo(0);
        ENTccd.setC9monto_ocupa_adicional(0);
        ENTccd.setC10monto_ocupa_consumo(0);
        ENTccd.setC11monto_ocupa_descuento(0);
        ENTccd.setC12monto_ocupa_adelanto(0);
        ENTccd.setC13monto_gasto(0);
        ENTccd.setC14monto_compra(0);
        ENTccd.setC15monto_vale(0);
        ENTccd.setC16monto_liquidacion(0);
        ENTccd.setC17estado(eveest.getEst_Emitido());
        ENTccd.setC18descripcion(descripcion);
        ENTccd.setC19fk_idgasto(0);
        ENTccd.setC20fk_idcompra(0);
        ENTccd.setC21fk_idventa(fk_idventa);
        ENTccd.setC22fk_idusuario(fk_idusuario);
        ENTccd.setC23fk_idrh_vale(0);
        ENTccd.setC24fk_idrh_liquidacion(0);
        ENTccd.setC25monto_solo_adelanto(monto_adelanto);
        ENTccd.setC26monto_interno(0);
        ENTccd.setC27fk_idventa_interno(0);
        ENTccd.setC28monto_garantia(0);
        ENTccd.setC29fk_idgarantia(0);
    }

    private void boton_cargar_consumo() {
        if (validar_habitacion_select()) {
            tiempo_boton_hab = 0;
            ENTven.setC1idventa(fk_idventa);
            ENTven.setC3creado_por(creado_por);
            BOveni.insertar_venta_item(ENTveni, ENTven, tblitem_producto, false, false, false);
            JOptionPane.showMessageDialog(null, "CONSUMO CARGADO CORRECTAMENTE");
            DAOveni.actualizar_tabla_venta_item(conn, tblitem_consumo_cargado, fk_idventa);
            limpiar_cargar_tabla_venta_item(conn, tblitem_producto, fk_idventa);
            cargar_observacion_venta("#==>>GUARDAR CONSUMO:" + monto_consumo, false);
            update_carga_monto_adicional(fk_idhabitacion_recepcion_actual_select, fk_idhabitacion_dato_select);
            BOven.update_venta1(ENTven, ENThr, ENThrt, ENTccd, true, false, false);
            calculo_monto_pagar();
            observacion_venta = "";
            ejecutar_update_habitacion_recepcion_temp_FUERTE();
            limpiar_habitacion_select();
            boton_hab_unavez = true;
//            eveJtab.mostrar_JTabbedPane(jTab_principal, 1);
        }
    }

    private void cargar_dato_habitacion_recepcion_sucio_a_libre(int idhabitacion_recepcion_actual) {
        DAOhr.cargar_habitacion_recepcion(conn, ENThr, idhabitacion_recepcion_actual);
        ENThr.setC1idhabitacion_recepcion(idhabitacion_recepcion_actual);
        ENThr.setC4estado(eveest.getEst_Terminado());
        ENThr.setC11fec_sucio_fin(evefec.getString_formato_fecha_hora());
        ENThr.setC24es_boton_actual(false);
        ENThr.setC26es_terminado(true);
    }

    private void cargar_dato_hab_temp_sucio_a_libre(int idhabitacion_dato) {
        DAOhrt.cargar_habitacion_recepcion_temp(conn, ENThrt, idhabitacion_dato);
        ENThrt.setC7estado(eveest.getEst_Libre());
        ENThrt.setC13fec_sucio_fin(evefec.getString_formato_fecha_hora());
        ENThrt.setC30monto_consumision(0);
        ENThrt.setC31monto_descuento(0);
        ENThrt.setC41monto_adelanto(0);
        ENThrt.setC2idhabitacion_recepcion_actual(0);
        ENThrt.setC18es_libre(true);
    }

    private void boton_sucio_a_libre(int idhabitacion_recepcion_actual, int idhabitacion_dato) {
        cargar_dato_habitacion_recepcion_sucio_a_libre(idhabitacion_recepcion_actual);
        cargar_dato_hab_temp_sucio_a_libre(idhabitacion_dato);
        BOven.update_venta1(ENTven, ENThr, ENThrt, ENTccd, false, false, false);
        boton_hab_unavez = true;
        boton_mudar_unavez = true;
    }

    private void cargar_dato_habitacion_recepcion_limpieza_a_libre(int idhabitacion_recepcion_actual) {
        DAOhr.cargar_habitacion_recepcion(conn, ENThr, idhabitacion_recepcion_actual);
        ENThr.setC1idhabitacion_recepcion(idhabitacion_recepcion_actual);
        ENThr.setC4estado(eveest.getEst_Terminado());
        ENThr.setC13fec_limpieza_fin(evefec.getString_formato_fecha_hora());
        ENThr.setC24es_boton_actual(false);
        ENThr.setC26es_terminado(true);
    }

    private void cargar_dato_hab_temp_limpieza_a_libre(int idhabitacion_dato) {
        DAOhrt.cargar_habitacion_recepcion_temp(conn, ENThrt, idhabitacion_dato);
        ENThrt.setC7estado(eveest.getEst_Libre());
        ENThrt.setC15fec_limpieza_fin(evefec.getString_formato_fecha_hora());
        ENThrt.setC30monto_consumision(0);
        ENThrt.setC31monto_descuento(0);
        ENThrt.setC41monto_adelanto(0);
        ENThrt.setC2idhabitacion_recepcion_actual(0);
        ENThrt.setC18es_libre(true);
    }

    private void boton_limpieza_a_libre(int idhabitacion_recepcion_actual, int idhabitacion_dato) {
        cargar_dato_habitacion_recepcion_limpieza_a_libre(idhabitacion_recepcion_actual);
        cargar_dato_hab_temp_limpieza_a_libre(idhabitacion_dato);
        BOven.update_venta1(ENTven, ENThr, ENThrt, ENTccd, false, false, false);
        boton_hab_unavez = true;
        boton_mudar_unavez = true;
    }

    private void update_carga_monto_adicional(int idhabitacion_recepcion_actual, int idhabitacion_dato) {
        DAOven.cargar_venta_idhabitacion_recepcion(conn, ENTven, idhabitacion_recepcion_actual);
        ENTven.setC10monto_minimo(monto_minimo_A);
        ENTven.setC11monto_adicional(monto_adicional_total);
        ENTven.setC12cant_adicional(cant_adicional);
        ENTven.setC13monto_consumo(monto_consumo);
        ENTven.setC15monto_descuento(monto_descuento);
        ENTven.setC16monto_adelanto(monto_adelanto);
        ENTven.setC6observacion(ENTven.getC6observacion() + observacion_venta);
        DAOhr.cargar_habitacion_recepcion(conn, ENThr, idhabitacion_recepcion_actual);
        ENThr.setC1idhabitacion_recepcion(idhabitacion_recepcion_actual);
        ENThr.setC31monto_consumo(monto_consumo);
        ENThr.setC32monto_descuento(monto_descuento);
        ENThr.setC33monto_adelanto(monto_adelanto);
        DAOhrt.cargar_habitacion_recepcion_temp(conn, ENThrt, idhabitacion_dato);
        ENThrt.setC30monto_consumision(monto_consumo);
        ENThrt.setC31monto_descuento(monto_descuento);
        ENThrt.setC41monto_adelanto(monto_adelanto);
    }

    private void update_cambio_tipo_ocupacion(int idhabitacion_recepcion_actual, int idhabitacion_dato) {
        DAOven.cargar_venta_idhabitacion_recepcion(conn, ENTven, idhabitacion_recepcion_actual);
        if (jRpor_dormir.isSelected()) {
            es_por_hora = false;
            es_por_dormir = true;
            es_por_hospedaje = false;
            monto_adicional = monto_por_dormir_adicional;
            monto_minimo_A = monto_por_dormir_minimo;
            monto_adicional_total = cant_adicional * (int) monto_adicional;
        }
        if (jRpor_hora.isSelected()) {
            es_por_hora = true;
            es_por_dormir = false;
            es_por_hospedaje = false;
            monto_adicional = monto_por_hora_adicional;
            monto_minimo_A = monto_por_hora_minimo;
            monto_adicional_total = cant_adicional * (int) monto_adicional;
        }
        if (jRpor_hora_mas_dormir.isSelected()) {
            es_por_hora = true;
            es_por_dormir = true;
            es_por_hospedaje = false;
            monto_adicional = monto_por_dormir_adicional;
            monto_minimo_A = monto_por_dormir_minimo;
            monto_adicional_total = cant_adicional * (int) monto_adicional;
        }
        if (jRpor_hospedaje.isSelected()) {
            es_por_hora = false;
            es_por_dormir = false;
            es_por_hospedaje = true;
            monto_adicional = monto_por_hora_adicional;
            monto_minimo_A = monto_por_hospedaje_minimo;
            monto_adicional_total = cant_adicional * (int) monto_adicional;

        }
        ENTven.setC10monto_minimo(monto_minimo_A);
        ENTven.setC11monto_adicional(monto_adicional_total);
        ENTven.setC12cant_adicional(cant_adicional);
        ENTven.setC13monto_consumo(monto_consumo);
        ENTven.setC15monto_descuento(monto_descuento);
        ENTven.setC16monto_adelanto(monto_adelanto);
        DAOhr.cargar_habitacion_recepcion(conn, ENThr, idhabitacion_recepcion_actual);
        ENThr.setC22es_por_hora(es_por_hora);
        ENThr.setC23es_por_dormir(es_por_dormir);
        ENThr.setC39es_hospedaje(es_por_hospedaje);
        DAOhrt.cargar_habitacion_recepcion_temp(conn, ENThrt, idhabitacion_dato);
        ENThrt.setC24es_por_hora(es_por_hora);
        ENThrt.setC25es_por_dormir(es_por_dormir);
        ENThrt.setC46monto_por_hospedaje_minimo(monto_por_hospedaje_minimo);
        ENThrt.setC49es_hospedaje(es_por_hospedaje);
        BOven.update_venta1(ENTven, ENThr, ENThrt, ENTccd, true, false, false);
    }

    private void seleccionar_tipo_ocupacion() {
        if (validar_habitacion_select()) {
            if (jRpor_dormir.isSelected()) {
                cargar_observacion_venta("Marca Por Dormir", false);
                if (permitir_dormir_select) {//monto_por_dormir_minimo
                    String dormir_minimo = evejtf.getString_format_nro_decimal(monto_por_dormir_minimo);
                    String dormir_adicional = evejtf.getString_format_nro_decimal(monto_por_dormir_adicional);
                    String mensaje = "<HTML>"
                            + evemen.getHtml_negro(4, "ESTAS SEGURO DE MUDAR EL TIPO DE OCUPACION A DORMIR")
                            + evemen.getHtml_azul(4, "MINIMO DORMIR:")
                            + evemen.getHtml_rojo(6, dormir_minimo)
                            + evemen.getHtml_azul(4, "ADICIONAL DORMIR:")
                            + evemen.getHtml_rojo(6, dormir_adicional)
                            + "</HTML>";
                    if (evemen.getBooMensaje_warning(mensaje, "OCUPACION DORMIR", btnaceptar_html, btncancelar_html)) {
                        cargar_observacion_venta("Acepta: Por dormir", false);
                        update_cambio_tipo_ocupacion(fk_idhabitacion_recepcion_actual_select, fk_idhabitacion_dato_select);
                        lbltipo_tarifa_icono.setIcon(new javax.swing.ImageIcon(getClass().getResource(eveest.getIco_dormir())));
                        ejecutar_update_habitacion_recepcion_temp_FUERTE();
                        limpiar_habitacion_select();
                    } else {
                        jRpor_hora.setSelected(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "EL RANGO DE HORA NO ES PERMITIDO PARA DORMIR", "FUERA HORARIO", JOptionPane.WARNING_MESSAGE);
                    jRpor_hora.setSelected(true);
                }

            } else {
                if (jRpor_hora.isSelected()) {
                    if (evemen.getBooMensaje_warning("ESTAS SEGURO DE MUDAR EL TIPO DE OCUPACION A POR HORA", "OCUPACION POR HORA", btnaceptar_html, btncancelar_html)) {
                        update_cambio_tipo_ocupacion(fk_idhabitacion_recepcion_actual_select, fk_idhabitacion_dato_select);
                        lbltipo_tarifa_icono.setIcon(new javax.swing.ImageIcon(getClass().getResource(eveest.getIco_ocupa())));
                        limpiar_habitacion_select();
                    }
                }
            }
            if (jRpor_hora_mas_dormir.isSelected()) {
                cargar_observacion_venta("Marca Por Hora mas Dormir", false);
                String mensaje = "<HTML>"
                        + evemen.getHtml_negro(4, "ESTAS SEGURO DE MUDAR EL TIPO DE OCUPACION A:")
                        + evemen.getHtml_negro(4, "(POR HORA MAS DORMIR)")
                        + evemen.getHtml_azul(5, "CANTIDAD ADICIONAL:")
                        + evemen.getHtml_rojo(6, " ==>> " + String.valueOf(Icant_hasta_dormir))
                        + evemen.getHtml_azul(5, "HORAS ADICIONAL:")
                        + evemen.getHtml_rojo(6, " ==>> " + Shs_hasta_dormir)
                        + "</HTML>";
                if (evemen.getBooMensaje_warning(mensaje,
                        "OCUPACION POR HORA MAS DORMIR", btnaceptar_html, btncancelar_html)) {
                    cargar_observacion_venta("Acepta: Por Hora mas Dormir", false);
                    update_cambio_tipo_ocupacion(fk_idhabitacion_recepcion_actual_select, fk_idhabitacion_dato_select);
                    lbltipo_tarifa_icono.setIcon(new javax.swing.ImageIcon(getClass().getResource(eveest.getIco_dormir())));
                    cargar_item_producto_adicional_hasta_dormir();
                    boton_cargar_consumo();
                    limpiar_habitacion_select();

                }
            }
            if (jRpor_hospedaje.isSelected()) {
                if (evemen.getBooMensaje_warning("ESTAS SEGURO DE MUDAR EL TIPO DE OCUPACION A POR HOSPEDAJE", "OCUPACION HOPEDAJE", btnaceptar_html, btncancelar_html)) {
                    update_cambio_tipo_ocupacion(fk_idhabitacion_recepcion_actual_select, fk_idhabitacion_dato_select);
                    lbltipo_tarifa_icono.setIcon(new javax.swing.ImageIcon(getClass().getResource(eveest.getIco_ocupa())));
                    limpiar_habitacion_select();
                }
            }
        }
    }

    private void boton_ir_consumo() {
        if (validar_habitacion_select()) {
            tiempo_boton_hab = 0;
            color_panel_venta(1);
            cargar_observacion_venta("Boton Para Cargar Consumo", false);
            eveJtab.mostrar_JTabbedPane(jTab_principal, 2);

        }
    }

    private void calculo_monto_pagar() {
        monto_total_pagar = ((monto_adicional_total + monto_minimo_A + monto_consumo) - (monto_descuento + monto_adelanto));
        jFmonto_total_pagar.setValue(monto_total_pagar);
        jFmonto_total_pagar_salir.setValue(monto_total_pagar);
    }

    private void boton_cargar_descuento() {
        if ((validar_habitacion_select()) && txtmonto_descontar.getText().trim().length() > 0) {
            tiempo_boton_hab = 0;
            monto_descuento = Double.parseDouble(txtmonto_descontar.getText());
            if (monto_descuento > 0) {
                update_carga_monto_adicional(fk_idhabitacion_recepcion_actual_select, fk_idhabitacion_dato_select);
                BOven.update_venta1(ENTven, ENThr, ENThrt, ENTccd, true, false, false);
                JOptionPane.showMessageDialog(null, "DESCUENTO CARGADO CORRECTAMENTE");
                calculo_monto_pagar();
            }
        }
    }

    private void boton_cargar_adelanto() {
        if (!evejtf.getBoo_JTextField_vacio(txtmonto_adelanto, "DEBE CARGAR EL CAMPO MONTO ADELANTO")) {
            if ((validar_habitacion_select())) {
                monto_total_pagar = ((monto_adicional_total + monto_minimo_A + monto_consumo) - (monto_descuento + 0));
                tiempo_boton_hab = 0;
                String Smonto_adelanto = evejtf.getString_format_nro_entero1(txtmonto_adelanto);
                monto_adelanto = Double.parseDouble(Smonto_adelanto);
                if (monto_total_pagar < monto_adelanto) {
                    JOptionPane.showMessageDialog(null, "EL MONTO DE ADELANTO NO PUEDE SUPERAR EL MONTO TOTAL\n"
                            + "monto_total_pagar:" + monto_total_pagar + "\n"
                            + "monto_adelanto:" + monto_adelanto, "ERROR ADELANTO", JOptionPane.ERROR_MESSAGE);
                    txtmonto_adelanto.setText(null);
                    txtmonto_adelanto.grabFocus();
                } else {
                    if (monto_adelanto > 0) {
                        String mensaje = "<HTML>"
                                + evemen.getHtml_negro(4, "ESTAS SEGURO DE DAR UN ADELANTO")
                                + evemen.getHtml_azul(4, "HABITACION NRO:")
                                + evemen.getHtml_rojo(6, String.valueOf(nro_habitacion_select))
                                + evemen.getHtml_azul(4, "MONTO ADELANTO:")
                                + evemen.getHtml_rojo(6, txtmonto_adelanto.getText())
                                + "</HTML>";
                        if (evemen.getBooMensaje_question(mensaje, "ADELANTO", btnaceptar_html, btncancelar_html)) {
                            cargar_observacion_venta("#==>>BOTON CONFIRMAR ADELANTO:" + monto_total_pagar, false);
                            update_carga_monto_adicional(fk_idhabitacion_recepcion_actual_select, fk_idhabitacion_dato_select);
                            cargar_dato_caja_detalle_ADELANTO();
                            if (es_adelanto_cargado) {
//                            BOven.update_venta1(ENTven, ENThr, ENThrt, ENTccd, true, false, true);
//                            JOptionPane.showMessageDialog(null, "AGREGAR ADELANTO CARGADO CORRECTAMENTE");
                            } else {
                                BOven.update_venta1(ENTven, ENThr, ENThrt, ENTccd, true, true, false);
                                JOptionPane.showMessageDialog(null, "NUEVO ADELANTO CARGADO CORRECTAMENTE");
                            }
                            calculo_monto_pagar();
                            ejecutar_update_habitacion_recepcion_temp_FUERTE();
                            limpiar_habitacion_select();
                        }
                    }
                }
            }
        }

    }

    private void boton_cargar_consumo_adelanto() {
        if (validar_habitacion_select()) {
            if (evemen.getBooMensaje_question("ESTAS SEGURO DE CONSUMO POR ADELANTO A ESTA HABITACION NRO:" + nro_habitacion_select, "ADELANTO", btnaceptar_html, btncancelar_html)) {
                tiempo_boton_hab = 0;
                ENTven.setC1idventa(fk_idventa);
                ENTven.setC3creado_por(creado_por);
                BOveni.insertar_venta_item(ENTveni, ENTven, tblitem_producto, false, false, false);
                DAOveni.actualizar_tabla_venta_item(conn, tblitem_consumo_cargado, fk_idventa);
                limpiar_cargar_tabla_venta_item(conn, tblitem_producto, fk_idventa);
                monto_total_pagar = ((monto_adicional_total + monto_minimo_A + monto_consumo) - (monto_descuento + 0));
                monto_adelanto = monto_total_pagar;
                cargar_observacion_venta("#==>>GUARDAR CONSUMO ADELANTADO:" + monto_adelanto, false);
                update_carga_monto_adicional(fk_idhabitacion_recepcion_actual_select, fk_idhabitacion_dato_select);
                monto_adelanto = monto_total_pagar - monto_adelanto_1;
                cargar_dato_caja_detalle_ADELANTO();
                BOven.update_venta1(ENTven, ENThr, ENThrt, ENTccd, true, true, false);
                JOptionPane.showMessageDialog(null, "CONSUMO ADELANTO CARGADO CORRECTAMENTE");
                ejecutar_update_habitacion_recepcion_temp_FUERTE();
                limpiar_habitacion_select();
            }
        }

    }

    private void cargar_dato_habitacion_recepcion_limpieza_auto(int idhabitacion_recepcion_actual) {
        DAOhr.cargar_habitacion_recepcion(conn, ENThr, idhabitacion_recepcion_actual);
        ENThr.setC1idhabitacion_recepcion(idhabitacion_recepcion_actual);
        ENThr.setC4estado(eveest.getEst_Limpiando());
        ENThr.setC11fec_sucio_fin(evefec.getString_formato_fecha_hora());
        ENThr.setC12fec_limpieza_inicio(evefec.getString_formato_fecha_hora());
        ENThr.setC19es_limpieza(true);
    }

    private void cargar_dato_hab_temp_limpieza_auto(int idhabitacion_dato) {
        DAOhrt.cargar_habitacion_recepcion_temp(conn, ENThrt, idhabitacion_dato);
        ENThrt.setC7estado(eveest.getEst_Limpiando());
        ENThrt.setC13fec_sucio_fin(evefec.getString_formato_fecha_hora());
        ENThrt.setC14fec_limpieza_inicio(evefec.getString_formato_fecha_hora());
        ENThrt.setC21es_limpieza(true);
    }

    private void ejecutar_limpieza_automatico(boolean cambiar_estado, String est_nuevo, int idhabitacion_recepcion_actual, int idhabitacion_dato) {
        if (cambiar_estado && est_nuevo.equals(eveest.getEst_Limpiando())) {
            cargar_observacion_venta("#==>ENTRADA LIMPIEZA AUTO", false);
            cargar_dato_habitacion_recepcion_limpieza_auto(idhabitacion_recepcion_actual);
            cargar_dato_hab_temp_limpieza_auto(idhabitacion_dato);
            BOven.update_venta1(ENTven, ENThr, ENThrt, ENTccd, false, false, false);
            boton_hab_unavez = true;
            segundo_tiempo_esten = max_tiempo_esten - limit_tiempo_esten;
        }
    }

    private void cargar_dato_habitacion_recepcion_libre_auto(int idhabitacion_recepcion_actual) {
        DAOhr.cargar_habitacion_recepcion(conn, ENThr, idhabitacion_recepcion_actual);
        ENThr.setC1idhabitacion_recepcion(idhabitacion_recepcion_actual);
        ENThr.setC4estado(eveest.getEst_Terminado());
        ENThr.setC13fec_limpieza_fin(evefec.getString_formato_fecha_hora());
        ENThr.setC24es_boton_actual(false);
        ENThr.setC26es_terminado(true);
    }

    private void cargar_dato_hab_temp_libre_auto(int idhabitacion_dato) {
        DAOhrt.cargar_habitacion_recepcion_temp(conn, ENThrt, idhabitacion_dato);
        ENThrt.setC7estado(eveest.getEst_Libre());
        ENThrt.setC15fec_limpieza_fin(evefec.getString_formato_fecha_hora());
        ENThrt.setC30monto_consumision(0);
        ENThrt.setC31monto_descuento(0);
        ENThrt.setC41monto_adelanto(0);
        ENThrt.setC2idhabitacion_recepcion_actual(0);
    }

    private void ejecutar_libre_automatico(boolean cambiar_estado, String est_nuevo, int idhabitacion_recepcion_actual, int idhabitacion_dato) {
        if (cambiar_estado && est_nuevo.equals(eveest.getEst_Libre())) {
            cargar_observacion_venta("#==>>LIBRE AUTOMATICO", false);
            cargar_dato_habitacion_recepcion_libre_auto(idhabitacion_recepcion_actual);
            cargar_dato_hab_temp_libre_auto(idhabitacion_dato);
            BOven.update_venta1(ENTven, ENThr, ENThrt, ENTccd, false, false, false);
            boton_hab_unavez = true;
            segundo_tiempo_esten = max_tiempo_esten - limit_tiempo_esten;
        }
    }

    private void ejecutar_ocupar_automatico(boolean cambiar_estado, String est_nuevo, int idhabitacion_dato, int nro_habitacion, int idhabitacion_recepcion_actual) {
        if (cambiar_estado && est_nuevo.equals(eveest.getEst_Ocupado())) {
            cargar_observacion_venta("#==>>OCUPAR AUTOMATICO", false);
            boton_libre_a_ocupar(nro_habitacion, idhabitacion_dato);
            observacion_venta = "";
            boton_hab_unavez = true;
            segundo_tiempo_esten = max_tiempo_esten - limit_tiempo_esten;
        }
    }

    private void cargar_dato_habitacion_recepcion_ocupado_a_libre_CANCELAR(int idhabitacion_recepcion_actual) {
        DAOhr.cargar_habitacion_recepcion(conn, ENThr, idhabitacion_recepcion_actual);
        ENThr.setC1idhabitacion_recepcion(idhabitacion_recepcion_actual);
        ENThr.setC4estado(eveest.getEst_Cancelado());
        ENThr.setC9fec_ocupado_fin(evefec.getString_formato_fecha_hora());
        ENThr.setC24es_boton_actual(false);
        ENThr.setC21es_cancelado(true);
        ENThr.setC26es_terminado(true);
        ENThr.setC38fec_hospedaje_fin(evefec.getString_formato_fecha_hora());
    }

    private void cargar_dato_hab_temp_ocupado_a_libre_CANCELAR(int idhabitacion_dato) {
        DAOhrt.cargar_habitacion_recepcion_temp(conn, ENThrt, idhabitacion_dato);
        ENThrt.setC7estado(eveest.getEst_Libre());
        ENThrt.setC13fec_sucio_fin(evefec.getString_formato_fecha_hora());
        ENThrt.setC30monto_consumision(0);
        ENThrt.setC31monto_descuento(0);
        ENThrt.setC41monto_adelanto(0);
        ENThrt.setC2idhabitacion_recepcion_actual(0);
        ENThrt.setC18es_libre(true);
    }

    private void cargar_dato_venta_ocupado_a_libre_CANCELAR(int idhabitacion_recepcion_actual) {
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        DAOven.cargar_venta_idhabitacion_recepcion(conn, ENTven, idhabitacion_recepcion_actual);
        ENTven.setC4monto_letra("cero");
        ENTven.setC5estado(eveest.getEst_Cancelado());
        ENTven.setC6observacion(ENTven.getC6observacion() + observacion_venta);
        ENTven.setC7tipo_persona("cliente");
        ENTven.setC8motivo_anulacion(motivo_anulacion);
        ENTven.setC9motivo_mudar_habitacion("sin");
        ENTven.setC10monto_minimo(0);
        ENTven.setC11monto_adicional(0);
        ENTven.setC12cant_adicional(0);
        ENTven.setC13monto_consumo(0);
        ENTven.setC14monto_insumo(0);
        ENTven.setC15monto_descuento(0);
        ENTven.setC16monto_adelanto(0);
        ENTven.setC17fk_idhabitacion_recepcion(idhabitacion_recepcion_actual);
        ENTven.setC18fk_idpersona(0);
        ENTven.setC19fk_idusuario(fk_idusuario);
    }

    private void cargar_dato_caja_detalle_CANCELAR(int fk_idventa) {
        int idcaja_cierre_detalle = DAOccd.getInt_idcaja_cierre_detalle_por_otro_id(conn, "fk_idventa", fk_idventa);
        if (idcaja_cierre_detalle > 0) {
            ENTccd.setC1idcaja_cierre_detalle(idcaja_cierre_detalle);
            DAOccd.cargar_caja_cierre_detalle(conn, ENTccd, idcaja_cierre_detalle);
            ENTccd.setC5es_cerrado(false);
            ENTccd.setC6monto_apertura_caja(0);
            ENTccd.setC7monto_cierre_caja(0);
            ENTccd.setC8monto_ocupa_minimo(0);
            ENTccd.setC9monto_ocupa_adicional(0);
            ENTccd.setC10monto_ocupa_consumo(0);
            ENTccd.setC11monto_ocupa_descuento(0);
            ENTccd.setC12monto_ocupa_adelanto(0);
            ENTccd.setC13monto_gasto(0);
            ENTccd.setC14monto_compra(0);
            ENTccd.setC15monto_vale(0);
            ENTccd.setC16monto_liquidacion(0);
            ENTccd.setC17estado(eveest.getEst_Cancelado());
            ENTccd.setC18descripcion(ENTccd.getC18descripcion() + " / " + eveest.getEst_Cancelado() + " / " + motivo_anulacion);
            ENTccd.setC19fk_idgasto(0);
            ENTccd.setC20fk_idcompra(0);
            ENTccd.setC23fk_idrh_vale(0);
            ENTccd.setC24fk_idrh_liquidacion(0);
            ENTccd.setC25monto_solo_adelanto(0);
            ENTccd.setC26monto_interno(0);
            ENTccd.setC27fk_idventa_interno(0);
            ENTccd.setC28monto_garantia(0);
            ENTccd.setC29fk_idgarantia(0);
        }
    }

    private void cargar_dato_venta_item_CANCELAR(int fk_idventa) {
        ENTveni.setC4tipo_item(eveest.getEst_Cancelado());
        ENTveni.setC9fk_idventa(fk_idventa);
        DAOveni.update_venta_item_CANCELAR(conn, ENTveni);
    }

    private void ejecutar_ocupado_a_libre_CANCELAR(int idhabitacion_recepcion_actual, int fk_idventa, int idhabitacion_dato) {
        cargar_dato_venta_item_CANCELAR(fk_idventa);
        cargar_dato_venta_ocupado_a_libre_CANCELAR(idhabitacion_recepcion_actual);
        cargar_dato_habitacion_recepcion_ocupado_a_libre_CANCELAR(idhabitacion_recepcion_actual);
        cargar_dato_hab_temp_ocupado_a_libre_CANCELAR(idhabitacion_dato);
        cargar_dato_caja_detalle_CANCELAR(fk_idventa);
        BOven.update_venta1(ENTven, ENThr, ENThrt, ENTccd, true, false, true);
        boton_hab_unavez = true;
    }

    private void boton_cancelar() {
        boolean hab_cancelar = false;
        if (hab_cancelar_habitacion) {
            hab_cancelar = true;
        } else {
            if (DAOusu.gB_boton_cancelar_ocupacion()) {
                hab_cancelar = true;
            }
        }

        if (validar_habitacion_select() && hab_cancelar) {
            tiempo_boton_hab = 0;
            if (evemen.getBooMensaje_warning("ESTAS SEGURO DE CANCELAR ESTA HABITACION", "CANCELAR Y LIBERAR", btnaceptar_html, btncancelar_html)) {
                tiempo_boton_hab = 0;
                JTextArea txtacancel = new JTextArea(15, 30);
                txtacancel.setText("");
                Object[] opciones = {btnaceptar_html, btncancelar_html};
                int eleccion = JOptionPane.showOptionDialog(null, new JScrollPane(txtacancel), "MOTIVO PARA CANCELAR(Obligatorio)",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, opciones, btnaceptar_html);
                if (eleccion == JOptionPane.YES_OPTION) {
                    if (txtacancel.getText().trim().length() > 0) {
                        motivo_anulacion = txtacancel.getText().toUpperCase();
                        ejecutar_ocupado_a_libre_CANCELAR(fk_idhabitacion_recepcion_actual_select, fk_idventa, fk_idhabitacion_dato_select);
                        cargar_observacion_venta("#:( CANCELAR OCUPACION:" + motivo_anulacion, false);
                        ejecutar_update_habitacion_recepcion_temp_FUERTE();
                        limpiar_habitacion_select();
                    } else {
                        JOptionPane.showMessageDialog(null, "NO SE ENCONTRO NINGUN MOTIVO DE CANCELAR INTENTE DE NUEVO",
                                "ERROR CANCELAR", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        }
    }

    private void recargar_monto_minimo_habitacion_temp(int idhabitacion_dato) {
        DAOhrt.cargar_habitacion_recepcion_temp(conn, ENThrt, idhabitacion_dato);
        descripcion_caja_desocupa = ENThrt.getDescrip_caja_desocupa();
        if (jRpor_dormir.isSelected()) {
            monto_por_dormir_minimo = ENThrt.getC28monto_por_dormir_minimo();
            monto_minimo_D = monto_por_dormir_minimo;

        }
        if (jRpor_hora.isSelected()) {
            monto_por_hora_minimo = ENThrt.getC26monto_por_hora_minimo();
            monto_minimo_D = monto_por_hora_minimo;
        }
        if (jRpor_hora_mas_dormir.isSelected()) {
            monto_por_dormir_minimo = ENThrt.getC28monto_por_dormir_minimo();
            monto_minimo_D = monto_por_dormir_minimo;
        }
        if (jRpor_hospedaje.isSelected()) {
            monto_por_hospedaje_minimo = ENThrt.getC46monto_por_hospedaje_minimo();
            monto_minimo_D = monto_por_hospedaje_minimo;
        }
    }

    private boolean validar_habitacion_select() {
        if (fk_idventa > 0) {

            return true;
        } else {
            limpiar_habitacion_select();
            return false;
        }
    }

    private void boton_imprimir_ticket() {
        if (tblfiltro_venta.getSelectedRow() >= 0) {
            int idventa = eveJtab.getInt_select_id(tblfiltro_venta);
            posv.boton_imprimir_pos_VENTA(conn, idventa);
        }
    }

    private void boton_imprimir_ticket_ocupado() {
        if (validar_habitacion_select()) {
            recargar_monto_minimo_habitacion_temp(fk_idhabitacion_dato_select);
            cargar_observacion_venta("#==>>IMPRIMIR TEMPORAL:", false);
            cargar_dato_venta_desocupar(fk_idhabitacion_recepcion_actual_select);
            BOven.update_venta_pre_impreso(ENTven);
            posv.boton_imprimir_pos_VENTA(conn, fk_idventa);
        }
    }

    private void boton_imprimir_ticket_garantia() {
        if (tblgarantia.getSelectedRow() >= 0) {
            int idgarantia = eveJtab.getInt_select_id(tblgarantia);
            posgar.boton_imprimir_pos_GARANTIA(conn, idgarantia);
        }
    }

    private void seleccionar_venta() {
        if (tblfiltro_venta.getSelectedRow() >= 0) {
            fk_idventa_select = eveJtab.getInt_select_id(tblfiltro_venta);
            String estado = eveJtab.getString_select(tblfiltro_venta, 13);
            String monto_ocupacion = eveJtab.getString_select(tblfiltro_venta, 12);
            String motivo_mudar = eveJtab.getString_select(tblfiltro_venta, 17);
            String motivo_anular = eveJtab.getString_select(tblfiltro_venta, 18);
            if (estado.equals(eveest.getEst_Cancelado())) {
                btnimprimir_ticket.setEnabled(false);
            } else {
                btnimprimir_ticket.setEnabled(true);
            }
            txtmotivo_mudar_cancelar.setVisible(false);
            if (estado.equals(eveest.getEst_Mudar())) {
                txtmotivo_mudar_cancelar.setVisible(true);
                txtmotivo_mudar_cancelar.setText(motivo_mudar);
            }
            if (estado.equals(eveest.getEst_Cancelado())) {
                txtmotivo_mudar_cancelar.setVisible(true);
                txtmotivo_mudar_cancelar.setText(motivo_anular);
            }
            if (estado.equals(eveest.getEst_Desocupado())) {
                btncargar_garantia.setEnabled(true);
                txtgar_idventa.setText(String.valueOf(fk_idventa_select));
                txtgar_monto_ocupacion.setText(monto_ocupacion);
                reestableser_garantia();
            } else {
                txtgar_idventa.setText(null);
                txtgar_monto_ocupacion.setText(null);
                btncargar_garantia.setEnabled(false);
                btngar_guardar.setEnabled(false);
            }
        }
    }

    private void cargar_boton_nro_habitacion() {
        String titulo = "cargar_boton_nro_habitacion";
        panel_nro_habitacion.removeAll();
        botones_nro_hab.clear();
        cant_boton_nrohab = 0;
        String sql = "select hd.idhabitacion_dato,hd.nro_habitacion  "
                + "from habitacion_dato hd "
                + "where hd.activo=true "
                + "order by hd.nro_habitacion asc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL_sinprint(conn, sql, titulo);
            while (rs.next()) {
                String textboton = rs.getString("idhabitacion_dato");
                String nameboton = rs.getString("nro_habitacion");
                boton_agregar_nro_habitacion(textboton, nameboton);
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void boton_agregar_nro_habitacion(String textboton, String nameboton) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 11));
        boton.setName(nameboton);
        panel_nro_habitacion.add(boton);
        botones_nro_hab.add(boton);
        cant_boton_nrohab++;
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int p = 0; p < cant_boton_nrohab; p++) {
                    botones_nro_hab.get(p).setBackground(new java.awt.Color(255, 255, 255));
                }
                ((JButton) e.getSource()).setBackground(new java.awt.Color(153, 153, 255));
                String fk_idhabitacion_dato = ((JButton) e.getSource()).getName();
                fk_idhabitacion_dato_buscar = Integer.parseInt(fk_idhabitacion_dato);
                System.out.println("fk_idhabitacion_dato: " + fk_idhabitacion_dato_buscar);
                actualizar_tabla_venta_buscar();
            }
        });
        panel_nro_habitacion.updateUI();
    }

    private void actualizar_tabla_venta_buscar() {
        if (jTab_principal.getSelectedIndex() == 3) {
            tiempo_boton_hab = 0;
            String filtro = "";
            String usuario = "";
            String fecha = evefec.getIntervalo_fecha_combobox(cmbfecha_venta, "v.fecha_creado");
            String habitacion = " and hd.idhabitacion_dato=" + fk_idhabitacion_dato_buscar;
            String estado = filtro_estado(jCest_terminado, jCest_ocupado, jCest_cancelado, jCest_desocupado, jCest_mudar);
            if (fk_idhabitacion_dato_buscar > 0) {
                habitacion = " and hd.idhabitacion_dato=" + fk_idhabitacion_dato_buscar;
            } else {
                habitacion = "";
            }
//        int idusuario = evecmb.getInt_seleccionar_COMBOBOX(conn, cmbusuario, usu_id, usu_nombre, usu_tabla);
            int idusuario = DAOusu.getInt_idusuario_combo(conn, cmbusuario);
            if (idusuario > 0) {
                usuario = " and v.fk_idusuario=" + idusuario;
            }
            filtro = habitacion + fecha + usuario + estado;
            String orden = " order by v.idventa desc;";
            DAOven.actualizar_tabla_venta(conn, tblfiltro_venta, filtro, orden);
            double total_suma = eveJtab.getDouble_sumar_tabla(tblfiltro_venta, 15);
            jFtotal_filtro.setValue(total_suma);
            int cant_venta = tblfiltro_venta.getRowCount();
            txtcant_venta.setText(String.valueOf(cant_venta));
        }
    }

    private void boton_eliminar_item_ya_creado() {
        if (tblitem_consumo_cargado.getSelectedRow() >= 0) {
            if (evemen.getBooMensaje_warning("ESTAS SEGURO DE ELIMINAR ESTE ITEM", "ELIMINAR PRODUCTO", "ELIMINAR", btnaceptar_html)) {
                int fk_idproducto = eveJtab.getInt_select_id(tblitem_consumo_cargado);
                ENTveni.setC4tipo_item(eveest.getEst_Anulado());
                ENTveni.setC6cantidad(0);
                ENTveni.setC7precio_venta(0);
                ENTveni.setC8precio_compra(0);
                ENTveni.setC9fk_idventa(fk_idventa);
                ENTveni.setC10fk_idproducto(fk_idproducto);
                DAOveni.eliminar_venta_item(conn, ENTveni);
                DAOveni.actualizar_tabla_venta_item(conn, tblitem_consumo_cargado, fk_idventa);
                limpiar_cargar_tabla_venta_item(conn, tblitem_producto, fk_idventa);
                update_carga_monto_adicional(fk_idhabitacion_recepcion_actual_select, fk_idhabitacion_dato_select);
                BOven.update_venta1(ENTven, ENThr, ENThrt, ENTccd, true, false, false);
                calculo_monto_pagar();
            }
        }
    }

    private void boton_eliminar_item_venta() {
        if (tblitem_producto.getSelectedRow() >= 0) {
            String tipo = eveJtab.getString_select(tblitem_producto, 7);
            if (tipo.equals(eveest.getEst_Cargado())) {
                JOptionPane.showMessageDialog(null, "NO SE PUEDE ELIMINAR UN PRODUCTO YA CARGADO\n"
                        + "ELIMINAR DESDE VENTANA HABITACION", "ERROR", JOptionPane.ERROR_MESSAGE);
                eveJtab.mostrar_JTabbedPane(jTab_principal, 1);
            } else {
                cargar_observacion_venta("ITEM ELIMINADO", true);
                eveJtab.getBoolean_Eliminar_Fila(tblitem_producto, model_itemf);
                jFtotal_consumo.setValue(eveJtab.getDouble_sumar_tabla(tblitem_producto, 8));

            }

        }
    }

    private void seleccionar_item_venta_temp() {
        if (tblitem_producto.getSelectedRow() >= 0) {
//            String tipo=eveJtab.getString_select(tblitem_producto, 7);
//            if(tipo.equals(eveest.getEst_Cargado())){
//                btneliminar_item_temp.setEnabled(false);
//            }else{
//                btneliminar_item_temp.setEnabled(true);
//            }
        }
    }

    public String filtro_estado(JCheckBox jCest_terminado, JCheckBox jCest_ocupado,
            JCheckBox jCest_cancelado, JCheckBox jCest_desocupado, JCheckBox jCest_mudar) {
        String estado = "";
        String sumaestado = "";
        int contestado = 0;
        String condi = "";
        String fin = "";
        if (jCest_terminado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.estado='" + eveest.getEst_Terminado() + "' ";
            sumaestado = sumaestado + estado;
        }
        if (jCest_ocupado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.estado='" + eveest.getEst_Ocupado() + "' ";
            sumaestado = sumaestado + estado;
        }
        if (jCest_desocupado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.estado='" + eveest.getEst_Desocupado() + "' ";
            sumaestado = sumaestado + estado;
        }
        if (jCest_cancelado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.estado='" + eveest.getEst_Cancelado() + "' ";
            sumaestado = sumaestado + estado;
        }
        if (jCest_mudar.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.estado='" + eveest.getEst_Mudar() + "' ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }

    private void boton_mudar() {
        if (validar_habitacion_select()) {
            cargar_observacion_venta("Boton Mudar", true);
            cargar_boton_habitacion_mudar();
            eveJtab.mostrar_JTabbedPane(jTab_principal, 4);
            boton_mudar_unavez = true;
        }
    }

    private void boton_comando_raspberry(int idhabitacion_mini_pc) {
        DAOhmp.cargar_habitacion_mini_pc(conn, ENThmp, idhabitacion_mini_pc);
        String comando_enviar = "";
        boolean enviar_ssh = false;
        boolean con_retorno = false;
        int largo = 30;
        int ancho = 30;
        Object[] botones = {"INFORMACION", "MODELO", btnreiniciar_html, "TEMP C", btnaceptar_html};
        int eleccion_comando = JOptionPane.showOptionDialog(null, "SELECCIONA UN COMANDO PARA LA RASPBERRY:\n" + ENThmp.getC4placa_nombre()
                + "\nIP:" + ENThmp.getC5placa_ip()
                + "\nUBICACION:" + ENThmp.getC6placa_ubicacion(),
                "ENVIAR COMANDO",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, botones, "INFORMACION");
        if (eleccion_comando == 0) {
            comando_enviar = "pinout";
            con_retorno = true;
            enviar_ssh = true;
            largo = 35;
        }
        if (eleccion_comando == 1) {
            comando_enviar = "cat /proc/device-tree/model";
            con_retorno = true;
            enviar_ssh = true;
            largo = 15;
        }
        if (eleccion_comando == 2) {
            comando_enviar = "sudo reboot -h now";
            con_retorno = false;
            enviar_ssh = true;
            largo = 15;
            if (idhabitacion_mini_pc == 2) {
                btn_reboot_rp_1 = true;
            }
            if (idhabitacion_mini_pc == 3) {
                btn_reboot_rp_2 = true;
            }
            if (idhabitacion_mini_pc == 4) {
                btn_reboot_rp_3 = true;
            }
        }
        if (eleccion_comando == 3) {
            comando_enviar = "/usr/bin/vcgencmd measure_temp";
            con_retorno = true;
            enviar_ssh = true;
            largo = 15;
        }
        if (enviar_ssh) {
//            String mensaje = "DESCONECTADO";
            String mensaje = connRPI.getStringEnviar_ssh_raspberry(ENThmp.getC5placa_ip(), ENThmp.getC7ssh_usuario(), ENThmp.getC8ssh_password(), comando_enviar, con_retorno);
            JTextArea txtacancel = new JTextArea(largo, ancho);
            txtacancel.setText(mensaje);
            Object[] opciones = {btnaceptar_html, btncancelar_html};
            int eleccion = JOptionPane.showOptionDialog(null, new JScrollPane(txtacancel), "INFORMACION RASPBERRY",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, opciones, btnaceptar_html);
        }
    }

    private void buscar_producto_cod_barra() {
        if (txtcod_barra.getText().trim().length() > 0) {
            String cod_barra = txtcod_barra.getText();
            String titulo = "buscar_producto_cod_barra";
            String sql = "select p.idproducto  "
                    + "from producto p "
                    + "where p.codigo_barra='" + cod_barra + "' ";
            try {
                ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
                int cant = 0;
                idproducto = 0;
                while (rs.next()) {
                    cant++;
                    idproducto = rs.getInt("idproducto");
                }
                if (cant == 1) {
                    precargar_producto(idproducto);
                    carga_prod_codbarra = true;
                }
                if (cant == 0) {
                    JOptionPane.showMessageDialog(this, "NO SE ENCONTRO NINGUN PRODUCTO", "ERROR DE BUSCAR", JOptionPane.ERROR_MESSAGE);
                    txtcod_barra.setText(null);
                    txtcod_barra.grabFocus();
                }
                if (cant > 1) {
                    JOptionPane.showMessageDialog(this, "SE ENCONTRO MAS DE UN PRODUCTO CON EL MISMO CODIGO DE BARRA"
                            + "\nSE DEBE MANTENER UN SOLO CODIGO DE BARRA PARA UN PRODUCTO", "ERROR DE BUSCAR", JOptionPane.ERROR_MESSAGE);
                    txtcod_barra.setText(null);
                    txtcod_barra.grabFocus();
                }
            } catch (Exception e) {
                evemen.mensaje_error(e, sql, titulo);
            }
        }
    }

    private void boton_actualizar_monto_adelanto() {
        double Dmonto_adelanto = (monto_adicional_total + monto_minimo_A + monto_consumo);
        txtmonto_adelanto.setText(evejtf.getString_format_nro_decimal(Dmonto_adelanto));
        txtmonto_adelanto.setBackground(Color.yellow);
        cargar_observacion_venta("Boton Calcular Adelanto:" + Dmonto_adelanto, true);
    }

    private void cargar_dato_garantia() {
        fk_idgarantia = (eveconn.getInt_ultimoID_mas_uno(conn, ENTgar.getTb_garantia(), ENTgar.getId_idgarantia()));
        double Dmonto_garantia = evejtf.getDouble_format_nro_entero(txtgar_monto);
        monto_garantia = (0 - Dmonto_garantia);
        String gar_responsable = txtgar_responsable.getText().toUpperCase();
        String gar_descripcion = txtgar_descripcion.getText().toUpperCase();
        ENTgar.setC3creado_por(creado_por);
        ENTgar.setC6responsable(gar_responsable);
        ENTgar.setC7descripcion_objeto(gar_descripcion);
        ENTgar.setC8monto_garantia(monto_garantia);
        ENTgar.setC9estado(eveest.getEst_Emitido());
        ENTgar.setC10fk_idusuario(fk_idusuario);
        ENTgar.setC11fk_idventa(fk_idventa_select);
    }

    private void cargar_dato_caja_detalle_GARANTIA(boolean es_ingreso) {
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        String descripcion = "";
        String gar_responsable = "";
        String gar_descripcion = "";
        if (es_ingreso) {
            gar_responsable = txtgar_responsable.getText().toUpperCase();
            gar_descripcion = txtgar_descripcion.getText().toUpperCase();
            descripcion = fk_idventa_select + "-(" + eveest.getCaja_GARANTIA() + ")-" + gar_responsable + ",(" + gar_descripcion + ")";
        } else {
            gar_responsable = eveJtab.getString_select(tblgarantia, 2);
            gar_descripcion = eveJtab.getString_select(tblgarantia, 3);
            descripcion = fk_idgarantia + "-(" + eveest.getCaja_GARANTIA() + ")-" + gar_responsable + ",(" + gar_descripcion + ")-" + eveest.getEst_Pagado();
        }
        ENTccd.setC3creado_por(creado_por);
        ENTccd.setC4cerrado_por(eveest.getCaja_GARANTIA());
        ENTccd.setC5es_cerrado(false);
        ENTccd.setC6monto_apertura_caja(0);
        ENTccd.setC7monto_cierre_caja(0);
        ENTccd.setC8monto_ocupa_minimo(0);
        ENTccd.setC9monto_ocupa_adicional(0);
        ENTccd.setC10monto_ocupa_consumo(0);
        ENTccd.setC11monto_ocupa_descuento(0);
        ENTccd.setC12monto_ocupa_adelanto(0);
        ENTccd.setC13monto_gasto(0);
        ENTccd.setC14monto_compra(0);
        ENTccd.setC15monto_vale(0);
        ENTccd.setC16monto_liquidacion(0);
        ENTccd.setC17estado(eveest.getEst_Emitido());
        ENTccd.setC18descripcion(descripcion);
        ENTccd.setC19fk_idgasto(0);
        ENTccd.setC20fk_idcompra(0);
        ENTccd.setC21fk_idventa(0);
        ENTccd.setC22fk_idusuario(fk_idusuario);
        ENTccd.setC23fk_idrh_vale(0);
        ENTccd.setC24fk_idrh_liquidacion(0);
        ENTccd.setC25monto_solo_adelanto(0);
        ENTccd.setC26monto_interno(0);
        ENTccd.setC27fk_idventa_interno(0);
        ENTccd.setC28monto_garantia(monto_garantia);
        ENTccd.setC29fk_idgarantia(fk_idgarantia);
    }

    private boolean validar_garantia() {
        if (!eveJtab.getBoolean_validar_select_mensaje(tblfiltro_venta, "DEBE SELECCIONAR UNA OCUPACION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtgar_responsable, "DEBE CARGAR UN RESPONSABLE")) {
            return false;
        }
        if (evejtf.getBoo_JTextarea_vacio(txtgar_descripcion, "DEBE CARGAR UNA DESCRIPCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtgar_monto, "DEBE CARGAR UN MONTO")) {
            return false;
        }
        return true;
    }

    private void reestableser_garantia() {
        DAOgar.actualizar_tabla_garantia(conn, tblgarantia);
        txtgar_responsable.setText(null);
        txtgar_descripcion.setText(null);
        txtgar_monto.setText(null);
        btngar_guardar.setEnabled(true);
        txtgar_responsable.grabFocus();
    }

    //caja_cierre_detalle ENTccd
    private void boton_guardar_garantia() {
        if (validar_garantia()) {
            cargar_dato_garantia();
            cargar_dato_caja_detalle_GARANTIA(true);
            BOgar.insertar_garantia(ENTgar, ENTccd);
            reestableser_garantia();
            txtgar_idventa.setText(null);
            txtgar_monto_ocupacion.setText(null);
            btngar_guardar.setEnabled(false);
        }
    }

    private void seleccionar_garantia() {
        if (tblgarantia.getSelectedRow() >= 0) {
            fk_idgarantia = eveJtab.getInt_select_id(tblgarantia);
            DAOgar.cargar_garantia(conn, ENTgar, fk_idgarantia);
            monto_garantia = Math.abs(ENTgar.getC8monto_garantia());
            String estado = eveJtab.getString_select(tblgarantia, 6);
            if (estado.equals(eveest.getEst_PENDIENTE()) || estado.equals(eveest.getEst_Emitido())) {
                btngar_pagar_garantia.setEnabled(true);
            } else {
                btngar_pagar_garantia.setEnabled(false);
            }
        }
    }

    private void boton_guardar_garantia_pagar() {
        if (tblgarantia.getSelectedRow() >= 0) {
            ENTgar.setC1idgarantia(fk_idgarantia);
            ENTgar.setC8monto_garantia(monto_garantia);
            ENTgar.setC9estado(eveest.getEst_Pagado());
            cargar_dato_caja_detalle_GARANTIA(false);
            BOgar.update_garantia_pagar(ENTgar, ENTccd);
            reestableser_garantia();
            txtgar_idventa.setText(null);
            txtgar_monto_ocupacion.setText(null);
            btngar_guardar.setEnabled(false);
        }
    }

    private void cargar_cantidad_entrada_abierta() {
        String titulo = "cargar_cantidad_entrada_abierta";
        String sql = "select sum(case when  (cd.es_cerrado=false and cd.cerrado_por='DESOCUPADO') then 1\n"
                + "else 0 end) as entrada_actual \n"
                + "from caja_cierre_detalle cd;";
        try {
            ResultSet rs = eveconn.getResulsetSQL_sinprint(conn, sql, titulo);
            while (rs.next()) {
                String entrada_actual = rs.getString("entrada_actual");
                txtentrada_actual.setText(entrada_actual);
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void boton_cambar_salida_final() {
        String html_1 = "<html><p style=\"color:red\"><font size=\"6\">";
        String html_2 = "</font></p></html>";
        String hora_junto = "08:00:00#09:00:00#10:00:00#11:00:00#12:00:00#13:00:00#14:00:00";
        Object[] Array_hora_junto = hora_junto.split("#");
        Object[] Ohorarios = new Object[Array_hora_junto.length];
        for (int i = 0; i < Array_hora_junto.length; i++) {
            Object hora = Array_hora_junto[i];
            Ohorarios[i] = html_1 + hora + html_2;
        }
        JComboBox comboBox = new JComboBox(Ohorarios);
        comboBox.setSelectedIndex(1);
        JOptionPane.showMessageDialog(null, comboBox, "NUEVO HORARIO SALIDA DORMIR",
                JOptionPane.QUESTION_MESSAGE);
        String horario = comboBox.getSelectedItem().toString();
        Object hora = Array_hora_junto[comboBox.getSelectedIndex()];
//        JOptionPane.showMessageDialog(this, horario+"\n"+hora);
        if (evemen.getBooMensaje_warning("ESTAS SEGURO DE CAMBIAR EL HORARIO DE SALIDA FINAL A :\n" + horario, "CAMBIO DE HORA", btnaceptar_html, btncancelar_html)) {
            DAOhrt.cargar_habitacion_recepcion_temp(conn, ENThrt, fk_idhabitacion_dato_select);
            String hs_dormir_salida_final = String.valueOf(hora);
            ENThrt.setC37hs_dormir_salida_final(hs_dormir_salida_final);
            cargar_observacion_venta("Cambio de Horario de Salida Dormir a: " + hs_dormir_salida_final, true);
            BOhrt.update_salida_final_habitacion(ENThrt);
            jFdormir_salida_final.setText(ENThrt.getC37hs_dormir_salida_final());

        }
    }

    private void boton_ver_observacion_venta() {
        JTextArea ta = new JTextArea(30, 50);
        ta.setText(ENTven.getC6observacion());
        Object[] opciones = {btnaceptar_html, btncancelar_html};
        int eleccion = JOptionPane.showOptionDialog(null, new JScrollPane(ta), "OBSERVACION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, btnaceptar_html);
        if (eleccion == JOptionPane.YES_OPTION) {
            System.out.println(ENTven.getC6observacion());
        }
    }

    private void boton_imprimir_ultimo_registro() {
        if (fk_idventa_ult_print > 0) {
            posv.boton_imprimir_pos_VENTA(conn, fk_idventa_ult_print);
        }
        if (fk_idfactura_ult_print > 0) {
            posfac.boton_imprimir_pos_FACTURA(conn, fk_idfactura_ult_print);
        }
    }

    private void boton_imprimir_nota_factura() {
        if (eveJtab.getBoolean_validar_select_mensaje(tblfactura, "SELECCIONE UNA FACTURA PARA IMPRIMIR")) {
            int idfactura = eveJtab.getInt_select_id(tblfactura);
//            DAOf.imprimir_nota_factura(conn, idfactura);
            posfac.boton_imprimir_pos_FACTURA(conn, idfactura);
        }
    }

    public FrmOcupacion() {
        initComponents();
        abrir_formulario();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gru_tarifa = new javax.swing.ButtonGroup();
        jTab_principal = new javax.swing.JTabbedPane();
        jPanel_estado_habitacion = new javax.swing.JPanel();
        panel_puerta = new javax.swing.JPanel();
        panel_habitaciones = new javax.swing.JPanel();
        txttiempo_ahora = new javax.swing.JTextField();
        btnrpi_1 = new javax.swing.JButton();
        btnrpi_2 = new javax.swing.JButton();
        btnrpi_3 = new javax.swing.JButton();
        btnventa_interna = new javax.swing.JButton();
        lblentrada_actual = new javax.swing.JLabel();
        txtentrada_actual = new javax.swing.JTextField();
        btnimprimir_ultimo = new javax.swing.JButton();
        jPanel_tiempo_habitacion = new javax.swing.JPanel();
        txtnro_hab_grande = new javax.swing.JTextField();
        lblicono_tipo = new javax.swing.JLabel();
        txttipo_habitacion = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jFmonto_hora_minimo = new javax.swing.JFormattedTextField();
        jFmonto_hora_adicional = new javax.swing.JFormattedTextField();
        jFmonto_dormir_minimo = new javax.swing.JFormattedTextField();
        jFmonto_dormir_adicional = new javax.swing.JFormattedTextField();
        txtdescripcion_habitacion = new javax.swing.JTextField();
        jFmonto_hospedaje = new javax.swing.JFormattedTextField();
        jPanel8 = new javax.swing.JPanel();
        jFdormir_ingreso_inicio = new javax.swing.JFormattedTextField();
        jFdormir_ingreso_final = new javax.swing.JFormattedTextField();
        jFdormir_salida_final = new javax.swing.JFormattedTextField();
        btncambiar_salida_final = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        txtminumo_minimo = new javax.swing.JTextField();
        txtminuto_adicional = new javax.swing.JTextField();
        txtminuto_cancelar = new javax.swing.JTextField();
        txtfec_ocupado_inicio = new javax.swing.JTextField();
        txtfec_ocupado_inicio_hora = new javax.swing.JTextField();
        txttiempo_transcurrido = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jFmonto_minimo = new javax.swing.JFormattedTextField();
        jbar_tiempo_minimo = new javax.swing.JProgressBar();
        txtcant_adicional = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jFmonto_adicional = new javax.swing.JFormattedTextField();
        jFmonto_adicional_total = new javax.swing.JFormattedTextField();
        jFmonto_tarifa_ocupacion_total = new javax.swing.JFormattedTextField();
        txtrecepcion_actual = new javax.swing.JTextField();
        btnobservacion = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jRpor_hora = new javax.swing.JRadioButton();
        jRpor_dormir = new javax.swing.JRadioButton();
        jRpor_hora_mas_dormir = new javax.swing.JRadioButton();
        jRpor_hospedaje = new javax.swing.JRadioButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblitem_consumo_cargado = new javax.swing.JTable();
        jFmonto_consumo = new javax.swing.JFormattedTextField();
        btnconsumo = new javax.swing.JButton();
        btneliminar_item = new javax.swing.JButton();
        btndesocupar_pagar = new javax.swing.JButton();
        txtmonto_descontar = new javax.swing.JTextField();
        txtmonto_adelanto = new javax.swing.JTextField();
        jFmonto_total_pagar = new javax.swing.JFormattedTextField();
        btndescontar = new javax.swing.JButton();
        btnadelanto = new javax.swing.JButton();
        btnsalir_ocupacion = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        lbltipo_tarifa_icono = new javax.swing.JLabel();
        btnmudar_habitacion = new javax.swing.JButton();
        btnimprimir_ticket_ocupado = new javax.swing.JButton();
        btnactualizar_total_pago_adelanto = new javax.swing.JButton();
        lblmensaje_cancelar = new javax.swing.JLabel();
        jPanel_venta_principal = new javax.swing.JPanel();
        jScrol_referencia_categoria = new javax.swing.JScrollPane();
        panel_referencia_categoria = new javax.swing.JPanel();
        panel_referencia_marca = new javax.swing.JPanel();
        panel_referencia_unidad = new javax.swing.JPanel();
        jTab_producto_ingrediente = new javax.swing.JTabbedPane();
        panel_insertar_pri_producto = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtbuscar_producto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblproducto = new javax.swing.JTable();
        txtcantidad_producto = new javax.swing.JTextField();
        btnagregar_cantidad = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        txtcod_barra = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btncantidad_1 = new javax.swing.JButton();
        btncantidad_2 = new javax.swing.JButton();
        btncantidad_3 = new javax.swing.JButton();
        btncantidad_4 = new javax.swing.JButton();
        btncantidad_5 = new javax.swing.JButton();
        btncantidad_6 = new javax.swing.JButton();
        jPanel_item_venta = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblitem_producto = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        txtidventa = new javax.swing.JTextField();
        jFtotal_consumo = new javax.swing.JFormattedTextField();
        btncargar_consumo = new javax.swing.JButton();
        btneliminar_item_temp = new javax.swing.JButton();
        btnconsumo_adelantado = new javax.swing.JButton();
        jPanel_filtro_habitacion = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblfiltro_venta = new javax.swing.JTable();
        btnimprimir_ticket = new javax.swing.JButton();
        panel_nro_habitacion = new javax.swing.JPanel();
        btnvertodoventa = new javax.swing.JButton();
        cmbfecha_venta = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jCest_terminado = new javax.swing.JCheckBox();
        jCest_ocupado = new javax.swing.JCheckBox();
        jCest_cancelado = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        cmbusuario = new javax.swing.JComboBox<>();
        jCest_desocupado = new javax.swing.JCheckBox();
        jFtotal_filtro = new javax.swing.JFormattedTextField();
        txtcant_venta = new javax.swing.JTextField();
        txtmotivo_mudar_cancelar = new javax.swing.JTextField();
        jCest_mudar = new javax.swing.JCheckBox();
        btncargar_garantia = new javax.swing.JButton();
        jPanel_mudar_habitacion = new javax.swing.JPanel();
        panel_habitaciones_libre = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        txtnro_hab_grande_salir = new javax.swing.JTextField();
        jFmonto_total_pagar_salir = new javax.swing.JFormattedTextField();
        txttiempo_transcurrido_salir = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        panel_temp_rpi_1 = new javax.swing.JPanel();
        btnrecepcion_temp = new javax.swing.JButton();
        panel_garantia_pendiente = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtgar_responsable = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtgar_descripcion = new javax.swing.JTextArea();
        txtgar_monto = new javax.swing.JTextField();
        btngar_guardar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtgar_idventa = new javax.swing.JTextField();
        txtgar_monto_ocupacion = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblgarantia = new javax.swing.JTable();
        btngar_pagar_garantia = new javax.swing.JButton();
        btnimprimir_garantia = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblfactura = new javax.swing.JTable();
        btnimprimir_factura = new javax.swing.JButton();

        setClosable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jTab_principal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTab_principalMouseReleased(evt);
            }
        });

        panel_puerta.setBorder(javax.swing.BorderFactory.createTitledBorder("PUERTAS"));
        panel_puerta.setLayout(new java.awt.GridLayout(0, 4));

        panel_habitaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("HABITACIONES"));
        panel_habitaciones.setLayout(new java.awt.GridLayout(0, 5));

        txttiempo_ahora.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txttiempo_ahora.setForeground(new java.awt.Color(255, 0, 0));
        txttiempo_ahora.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txttiempo_ahora.setText("jTextField3");

        btnrpi_1.setText("RPI - 1");
        btnrpi_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrpi_1ActionPerformed(evt);
            }
        });

        btnrpi_2.setText("RPI - 2");
        btnrpi_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrpi_2ActionPerformed(evt);
            }
        });

        btnrpi_3.setText("RPI - 3");
        btnrpi_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrpi_3ActionPerformed(evt);
            }
        });

        btnventa_interna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_venta_interna.png"))); // NOI18N
        btnventa_interna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnventa_internaActionPerformed(evt);
            }
        });

        lblentrada_actual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblentrada_actual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_taxi.png"))); // NOI18N
        lblentrada_actual.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtentrada_actual.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtentrada_actual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtentrada_actual.setText("50");

        btnimprimir_ultimo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ult_print.png"))); // NOI18N
        btnimprimir_ultimo.setText("PRINT");
        btnimprimir_ultimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_ultimoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_estado_habitacionLayout = new javax.swing.GroupLayout(jPanel_estado_habitacion);
        jPanel_estado_habitacion.setLayout(jPanel_estado_habitacionLayout);
        jPanel_estado_habitacionLayout.setHorizontalGroup(
            jPanel_estado_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_estado_habitacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_puerta, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_estado_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_estado_habitacionLayout.createSequentialGroup()
                        .addComponent(txttiempo_ahora, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnventa_interna)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblentrada_actual, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtentrada_actual, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnimprimir_ultimo, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnrpi_1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnrpi_2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnrpi_3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_estado_habitacionLayout.createSequentialGroup()
                        .addComponent(panel_habitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 1063, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(1266, 1266, 1266))
        );
        jPanel_estado_habitacionLayout.setVerticalGroup(
            jPanel_estado_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_estado_habitacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_estado_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_estado_habitacionLayout.createSequentialGroup()
                        .addComponent(panel_habitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addGroup(jPanel_estado_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txttiempo_ahora, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnventa_interna, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblentrada_actual, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtentrada_actual)
                            .addComponent(btnimprimir_ultimo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel_estado_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel_puerta, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.CENTER, jPanel_estado_habitacionLayout.createSequentialGroup()
                            .addGap(566, 566, 566)
                            .addGroup(jPanel_estado_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnrpi_3, javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(btnrpi_2, javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(btnrpi_1, javax.swing.GroupLayout.Alignment.CENTER)))))
                .addGap(5, 5, 5))
        );

        jTab_principal.addTab("ESTADO HABITACION", jPanel_estado_habitacion);

        jPanel_tiempo_habitacion.setBackground(new java.awt.Color(204, 255, 204));

        txtnro_hab_grande.setEditable(false);
        txtnro_hab_grande.setBackground(new java.awt.Color(204, 204, 255));
        txtnro_hab_grande.setFont(new java.awt.Font("Stencil", 0, 130)); // NOI18N
        txtnro_hab_grande.setForeground(new java.awt.Color(255, 51, 51));
        txtnro_hab_grande.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtnro_hab_grande.setText("5");
        txtnro_hab_grande.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "NUMERO HABITACION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        lblicono_tipo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblicono_tipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/96_camaamor.png"))); // NOI18N
        lblicono_tipo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txttipo_habitacion.setBackground(new java.awt.Color(0, 51, 255));
        txttipo_habitacion.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txttipo_habitacion.setForeground(new java.awt.Color(255, 255, 0));
        txttipo_habitacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO"));

        jFmonto_hora_minimo.setEditable(false);
        jFmonto_hora_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("HORA MINIMO"));
        jFmonto_hora_minimo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_hora_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_hora_minimo.setText("1");
        jFmonto_hora_minimo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jFmonto_hora_adicional.setEditable(false);
        jFmonto_hora_adicional.setBorder(javax.swing.BorderFactory.createTitledBorder("HORA ADICIONAL"));
        jFmonto_hora_adicional.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_hora_adicional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_hora_adicional.setText("1");
        jFmonto_hora_adicional.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jFmonto_dormir_minimo.setEditable(false);
        jFmonto_dormir_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("DORMIR MINIMO"));
        jFmonto_dormir_minimo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_dormir_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_dormir_minimo.setText("1");
        jFmonto_dormir_minimo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jFmonto_dormir_adicional.setEditable(false);
        jFmonto_dormir_adicional.setBorder(javax.swing.BorderFactory.createTitledBorder("DORMIR ADICIONAL"));
        jFmonto_dormir_adicional.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_dormir_adicional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_dormir_adicional.setText("1");
        jFmonto_dormir_adicional.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        txtdescripcion_habitacion.setText("jTextField3");

        jFmonto_hospedaje.setEditable(false);
        jFmonto_hospedaje.setBorder(javax.swing.BorderFactory.createTitledBorder("HOSPEDAJE MINIMO"));
        jFmonto_hospedaje.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_hospedaje.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_hospedaje.setText("1");
        jFmonto_hospedaje.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jFmonto_hora_adicional, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                        .addComponent(jFmonto_hora_minimo))
                    .addComponent(txtdescripcion_habitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFmonto_hospedaje)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jFmonto_dormir_minimo, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                        .addComponent(jFmonto_dormir_adicional)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFmonto_hora_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFmonto_dormir_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFmonto_hora_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFmonto_dormir_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtdescripcion_habitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jFmonto_hospedaje, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("TIEMPO DORMIR"));

        jFdormir_ingreso_inicio.setEditable(false);
        jFdormir_ingreso_inicio.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESO INICIO"));
        try {
            jFdormir_ingreso_inicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFdormir_ingreso_inicio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFdormir_ingreso_inicio.setText("00:00:00");
        jFdormir_ingreso_inicio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jFdormir_ingreso_final.setEditable(false);
        jFdormir_ingreso_final.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESO FINAL"));
        try {
            jFdormir_ingreso_final.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFdormir_ingreso_final.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFdormir_ingreso_final.setText("00:00:00");
        jFdormir_ingreso_final.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jFdormir_salida_final.setEditable(false);
        jFdormir_salida_final.setBorder(javax.swing.BorderFactory.createTitledBorder("SALIDA FINAL"));
        try {
            jFdormir_salida_final.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFdormir_salida_final.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFdormir_salida_final.setText("00:00:00");
        jFdormir_salida_final.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        btncambiar_salida_final.setText("CAMBIAR HS");
        btncambiar_salida_final.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncambiar_salida_finalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFdormir_ingreso_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFdormir_ingreso_final, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jFdormir_salida_final, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncambiar_salida_final, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jFdormir_ingreso_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFdormir_ingreso_final, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btncambiar_salida_final, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFdormir_salida_final)))
        );

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("TIEMPO TOLERANCIA"));

        txtminumo_minimo.setEditable(false);
        txtminumo_minimo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtminumo_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtminumo_minimo.setText("10");
        txtminumo_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("MIN MINIMO"));

        txtminuto_adicional.setEditable(false);
        txtminuto_adicional.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtminuto_adicional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtminuto_adicional.setText("10");
        txtminuto_adicional.setBorder(javax.swing.BorderFactory.createTitledBorder("MIN ADICIONAL"));

        txtminuto_cancelar.setEditable(false);
        txtminuto_cancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtminuto_cancelar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtminuto_cancelar.setText("10");
        txtminuto_cancelar.setBorder(javax.swing.BorderFactory.createTitledBorder("MIN CANCELAR"));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtminuto_adicional, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(txtminumo_minimo)
                    .addComponent(txtminuto_cancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(txtminumo_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtminuto_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtminuto_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtfec_ocupado_inicio.setEditable(false);
        txtfec_ocupado_inicio.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtfec_ocupado_inicio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtfec_ocupado_inicio.setText("jTextField3");
        txtfec_ocupado_inicio.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA INGRESO"));

        txtfec_ocupado_inicio_hora.setEditable(false);
        txtfec_ocupado_inicio_hora.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txtfec_ocupado_inicio_hora.setForeground(new java.awt.Color(0, 0, 255));
        txtfec_ocupado_inicio_hora.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtfec_ocupado_inicio_hora.setText("jTextField3");
        txtfec_ocupado_inicio_hora.setBorder(javax.swing.BorderFactory.createTitledBorder("HORA INGRESO"));

        txttiempo_transcurrido.setEditable(false);
        txttiempo_transcurrido.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txttiempo_transcurrido.setForeground(new java.awt.Color(255, 0, 0));
        txttiempo_transcurrido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txttiempo_transcurrido.setText("jTextField3");
        txttiempo_transcurrido.setBorder(javax.swing.BorderFactory.createTitledBorder("TIEMPO TRANSCURRIDO"));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("TARIFA"));

        jFmonto_minimo.setEditable(false);
        jFmonto_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO MINIMO"));
        jFmonto_minimo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_minimo.setText("1");
        jFmonto_minimo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jbar_tiempo_minimo.setForeground(new java.awt.Color(102, 204, 255));
        jbar_tiempo_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("TIEMPO MINIMO"));
        jbar_tiempo_minimo.setStringPainted(true);

        txtcant_adicional.setEditable(false);
        txtcant_adicional.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtcant_adicional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtcant_adicional.setText("10");
        txtcant_adicional.setBorder(javax.swing.BorderFactory.createTitledBorder("CANT"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("X");

        jFmonto_adicional.setEditable(false);
        jFmonto_adicional.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO ADICIONAL"));
        jFmonto_adicional.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_adicional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_adicional.setText("1");
        jFmonto_adicional.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jFmonto_adicional_total.setEditable(false);
        jFmonto_adicional_total.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL ADICIONAL"));
        jFmonto_adicional_total.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_adicional_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_adicional_total.setText("1");
        jFmonto_adicional_total.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jFmonto_tarifa_ocupacion_total.setEditable(false);
        jFmonto_tarifa_ocupacion_total.setBackground(new java.awt.Color(255, 255, 153));
        jFmonto_tarifa_ocupacion_total.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL TARIFA OCUPACION"));
        jFmonto_tarifa_ocupacion_total.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_tarifa_ocupacion_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_tarifa_ocupacion_total.setText("1");
        jFmonto_tarifa_ocupacion_total.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        txtrecepcion_actual.setEditable(false);
        txtrecepcion_actual.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtrecepcion_actual.setText("jTextField3");
        txtrecepcion_actual.setBorder(javax.swing.BorderFactory.createTitledBorder("NRO RECEPCION"));

        btnobservacion.setText("OBS");
        btnobservacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnobservacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFmonto_minimo)
                    .addComponent(jbar_tiempo_minimo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtcant_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFmonto_adicional, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
                    .addComponent(jFmonto_adicional_total, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(jFmonto_tarifa_ocupacion_total, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtrecepcion_actual, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnobservacion)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jFmonto_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbar_tiempo_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(txtcant_adicional)
                    .addComponent(jFmonto_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFmonto_adicional_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFmonto_tarifa_ocupacion_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtrecepcion_actual)
                    .addComponent(btnobservacion)))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("TIPO TARIFA"));

        gru_tarifa.add(jRpor_hora);
        jRpor_hora.setSelected(true);
        jRpor_hora.setText("POR HORA");
        jRpor_hora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRpor_horaActionPerformed(evt);
            }
        });

        gru_tarifa.add(jRpor_dormir);
        jRpor_dormir.setText("POR DORMIR");
        jRpor_dormir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRpor_dormirActionPerformed(evt);
            }
        });

        gru_tarifa.add(jRpor_hora_mas_dormir);
        jRpor_hora_mas_dormir.setText("POR HORA + DORMIR");
        jRpor_hora_mas_dormir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRpor_hora_mas_dormirActionPerformed(evt);
            }
        });

        gru_tarifa.add(jRpor_hospedaje);
        jRpor_hospedaje.setText("HOSPEDAJE");
        jRpor_hospedaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRpor_hospedajeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRpor_hora)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRpor_dormir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRpor_hora_mas_dormir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRpor_hospedaje)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRpor_hora)
                    .addComponent(jRpor_dormir)
                    .addComponent(jRpor_hora_mas_dormir)
                    .addComponent(jRpor_hospedaje))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("ITEM CONSUMISION"));

        tblitem_consumo_cargado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblitem_consumo_cargado);

        jFmonto_consumo.setEditable(false);
        jFmonto_consumo.setBackground(new java.awt.Color(255, 255, 153));
        jFmonto_consumo.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL CONSUMO"));
        jFmonto_consumo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_consumo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_consumo.setText("1");
        jFmonto_consumo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        btnconsumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_carrito.png"))); // NOI18N
        btnconsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconsumoActionPerformed(evt);
            }
        });

        btneliminar_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_basurero.png"))); // NOI18N
        btneliminar_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_itemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(btnconsumo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btneliminar_item)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFmonto_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFmonto_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnconsumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btneliminar_item, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        btndesocupar_pagar.setBackground(new java.awt.Color(153, 255, 153));
        btndesocupar_pagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_guardar.png"))); // NOI18N
        btndesocupar_pagar.setText("DESOCUPAR Y PAGAR");
        btndesocupar_pagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndesocupar_pagarActionPerformed(evt);
            }
        });

        txtmonto_descontar.setBackground(new java.awt.Color(153, 153, 153));
        txtmonto_descontar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtmonto_descontar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtmonto_descontar.setText("jTextField3");
        txtmonto_descontar.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCONTAR"));
        txtmonto_descontar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmonto_descontarKeyTyped(evt);
            }
        });

        txtmonto_adelanto.setBackground(new java.awt.Color(204, 204, 204));
        txtmonto_adelanto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtmonto_adelanto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtmonto_adelanto.setText("jTextField3");
        txtmonto_adelanto.setBorder(javax.swing.BorderFactory.createTitledBorder("ADELANTO"));
        txtmonto_adelanto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtmonto_adelantoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmonto_adelantoKeyReleased(evt);
            }
        });

        jFmonto_total_pagar.setEditable(false);
        jFmonto_total_pagar.setBackground(new java.awt.Color(255, 255, 153));
        jFmonto_total_pagar.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL PAGAR:"));
        jFmonto_total_pagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_total_pagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_total_pagar.setText("1");
        jFmonto_total_pagar.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        btndescontar.setText("DESCONTAR");
        btndescontar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndescontarActionPerformed(evt);
            }
        });

        btnadelanto.setBackground(new java.awt.Color(255, 0, 255));
        btnadelanto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnadelanto.setForeground(new java.awt.Color(255, 255, 255));
        btnadelanto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/32_pago.png"))); // NOI18N
        btnadelanto.setText("ADELANTO");
        btnadelanto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnadelantoActionPerformed(evt);
            }
        });

        btnsalir_ocupacion.setBackground(new java.awt.Color(255, 153, 0));
        btnsalir_ocupacion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnsalir_ocupacion.setText("SALIR");
        btnsalir_ocupacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalir_ocupacionActionPerformed(evt);
            }
        });

        btncancelar.setBackground(new java.awt.Color(255, 255, 255));
        btncancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btncancelar.setForeground(new java.awt.Color(255, 0, 0));
        btncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/48_cancelar.png"))); // NOI18N
        btncancelar.setText("CANCELAR");
        btncancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarActionPerformed(evt);
            }
        });

        lbltipo_tarifa_icono.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnmudar_habitacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/32_mudar.png"))); // NOI18N
        btnmudar_habitacion.setText("MUDAR");
        btnmudar_habitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmudar_habitacionActionPerformed(evt);
            }
        });

        btnimprimir_ticket_ocupado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ult_print.png"))); // NOI18N
        btnimprimir_ticket_ocupado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_ticket_ocupadoActionPerformed(evt);
            }
        });

        btnactualizar_total_pago_adelanto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/48_recargar.png"))); // NOI18N
        btnactualizar_total_pago_adelanto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizar_total_pago_adelantoActionPerformed(evt);
            }
        });

        lblmensaje_cancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblmensaje_cancelar.setForeground(new java.awt.Color(255, 0, 51));
        lblmensaje_cancelar.setText("jLabel6");

        javax.swing.GroupLayout jPanel_tiempo_habitacionLayout = new javax.swing.GroupLayout(jPanel_tiempo_habitacion);
        jPanel_tiempo_habitacion.setLayout(jPanel_tiempo_habitacionLayout);
        jPanel_tiempo_habitacionLayout.setHorizontalGroup(
            jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_tiempo_habitacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_tiempo_habitacionLayout.createSequentialGroup()
                        .addGroup(jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel_tiempo_habitacionLayout.createSequentialGroup()
                                .addComponent(txtnro_hab_grande, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblicono_tipo, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                                    .addComponent(txttipo_habitacion)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_tiempo_habitacionLayout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 416, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtfec_ocupado_inicio, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                            .addComponent(txtfec_ocupado_inicio_hora, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                            .addComponent(txttiempo_transcurrido, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel_tiempo_habitacionLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btncancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblmensaje_cancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jFmonto_total_pagar)
                                    .addComponent(btndesocupar_pagar, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
                                .addGap(18, 18, 18))
                            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(12, Short.MAX_VALUE))
                    .addGroup(jPanel_tiempo_habitacionLayout.createSequentialGroup()
                        .addComponent(txtmonto_descontar, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(btndescontar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtmonto_adelanto, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnactualizar_total_pago_adelanto, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnadelanto, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnmudar_habitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnimprimir_ticket_ocupado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbltipo_tarifa_icono, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnsalir_ocupacion, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel_tiempo_habitacionLayout.setVerticalGroup(
            jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_tiempo_habitacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_tiempo_habitacionLayout.createSequentialGroup()
                        .addGroup(jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel_tiempo_habitacionLayout.createSequentialGroup()
                                .addComponent(txttipo_habitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblicono_tipo))
                            .addComponent(txtnro_hab_grande, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel_tiempo_habitacionLayout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(txttiempo_transcurrido, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_tiempo_habitacionLayout.createSequentialGroup()
                        .addGroup(jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtfec_ocupado_inicio, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfec_ocupado_inicio_hora, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFmonto_total_pagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblmensaje_cancelar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btndesocupar_pagar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btncancelar))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_tiempo_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnsalir_ocupacion, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnimprimir_ticket_ocupado, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbltipo_tarifa_icono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnmudar_habitacion)
                    .addComponent(btnadelanto, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnactualizar_total_pago_adelanto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtmonto_adelanto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndescontar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtmonto_descontar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        jTab_principal.addTab("TIEMPO HABITACION", jPanel_tiempo_habitacion);

        panel_referencia_categoria.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_categoria.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_categoria.setLayout(new java.awt.GridLayout(0, 1));
        jScrol_referencia_categoria.setViewportView(panel_referencia_categoria);

        panel_referencia_marca.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_marca.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_marca.setLayout(new java.awt.GridLayout(1, 0));

        panel_referencia_unidad.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_unidad.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_unidad.setLayout(new java.awt.GridLayout(1, 0));

        panel_insertar_pri_producto.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_pri_producto.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("BUSCAR PRODUCTO:");

        txtbuscar_producto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtbuscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_productoKeyReleased(evt);
            }
        });

        tblproducto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblproducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblproducto.setRowHeight(25);
        tblproducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblproductoMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblproducto);

        txtcantidad_producto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtcantidad_producto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtcantidad_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcantidad_productoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcantidad_productoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcantidad_productoKeyTyped(evt);
            }
        });

        btnagregar_cantidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/flecha_derecha.png"))); // NOI18N
        btnagregar_cantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregar_cantidadActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setText("COD.BARRA:");

        txtcod_barra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtcod_barra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcod_barraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcod_barraKeyReleased(evt);
            }
        });

        jLabel2.setText("CANTIDAD");

        btncantidad_1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btncantidad_1.setText("1");
        btncantidad_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_1ActionPerformed(evt);
            }
        });

        btncantidad_2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btncantidad_2.setText("2");
        btncantidad_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_2ActionPerformed(evt);
            }
        });

        btncantidad_3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btncantidad_3.setText("3");
        btncantidad_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_3ActionPerformed(evt);
            }
        });

        btncantidad_4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btncantidad_4.setText("4");
        btncantidad_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_4ActionPerformed(evt);
            }
        });

        btncantidad_5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btncantidad_5.setText("5");
        btncantidad_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_5ActionPerformed(evt);
            }
        });

        btncantidad_6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btncantidad_6.setText("6");
        btncantidad_6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertar_pri_productoLayout = new javax.swing.GroupLayout(panel_insertar_pri_producto);
        panel_insertar_pri_producto.setLayout(panel_insertar_pri_productoLayout);
        panel_insertar_pri_productoLayout.setHorizontalGroup(
            panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(txtcod_barra, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_producto))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(71, 71, 71)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnagregar_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btncantidad_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtcantidad_producto, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                        .addComponent(btncantidad_2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncantidad_3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncantidad_4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncantidad_5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncantidad_6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_insertar_pri_productoLayout.setVerticalGroup(
            panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel29)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtbuscar_producto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addComponent(txtcod_barra, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(txtcantidad_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(btncantidad_1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addComponent(btnagregar_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTab_producto_ingrediente.addTab("PRODUCTOS", panel_insertar_pri_producto);

        jPanel_item_venta.setBorder(javax.swing.BorderFactory.createTitledBorder("ITEM VENTA"));

        tblitem_producto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblitem_producto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblitem_productoMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblitem_producto);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("CLIENTE"));

        jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRE"));

        jTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder("RUC"));

        txtidventa.setEditable(false);
        txtidventa.setBackground(new java.awt.Color(0, 0, 255));
        txtidventa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtidventa.setForeground(new java.awt.Color(255, 255, 51));
        txtidventa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtidventa.setBorder(javax.swing.BorderFactory.createTitledBorder("IDVENTA"));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtidventa, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtidventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jFtotal_consumo.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL"));
        jFtotal_consumo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_consumo.setText("0");
        jFtotal_consumo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btncargar_consumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_guardar.png"))); // NOI18N
        btncargar_consumo.setText("GUARDAR");
        btncargar_consumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncargar_consumoActionPerformed(evt);
            }
        });

        btneliminar_item_temp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_basurero.png"))); // NOI18N
        btneliminar_item_temp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_item_tempActionPerformed(evt);
            }
        });

        btnconsumo_adelantado.setBackground(new java.awt.Color(204, 0, 204));
        btnconsumo_adelantado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnconsumo_adelantado.setForeground(new java.awt.Color(255, 255, 255));
        btnconsumo_adelantado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/32_pago.png"))); // NOI18N
        btnconsumo_adelantado.setText("ADELANTADO");
        btnconsumo_adelantado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconsumo_adelantadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_item_ventaLayout = new javax.swing.GroupLayout(jPanel_item_venta);
        jPanel_item_venta.setLayout(jPanel_item_ventaLayout);
        jPanel_item_ventaLayout.setHorizontalGroup(
            jPanel_item_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_item_ventaLayout.createSequentialGroup()
                .addGroup(jPanel_item_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_item_ventaLayout.createSequentialGroup()
                        .addComponent(btncargar_consumo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnconsumo_adelantado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneliminar_item_temp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel_item_ventaLayout.setVerticalGroup(
            jPanel_item_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_item_ventaLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(jPanel_item_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btncargar_consumo)
                    .addComponent(btnconsumo_adelantado, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btneliminar_item_temp, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFtotal_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel_venta_principalLayout = new javax.swing.GroupLayout(jPanel_venta_principal);
        jPanel_venta_principal.setLayout(jPanel_venta_principalLayout);
        jPanel_venta_principalLayout.setHorizontalGroup(
            jPanel_venta_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_venta_principalLayout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_item_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
            .addGroup(jPanel_venta_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_venta_principalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrol_referencia_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel_venta_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(panel_referencia_marca, javax.swing.GroupLayout.DEFAULT_SIZE, 1091, Short.MAX_VALUE)
                        .addComponent(panel_referencia_unidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(42, 42, 42)))
        );
        jPanel_venta_principalLayout.setVerticalGroup(
            jPanel_venta_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_venta_principalLayout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addGroup(jPanel_venta_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTab_producto_ingrediente)
                    .addComponent(jPanel_item_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel_venta_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_venta_principalLayout.createSequentialGroup()
                    .addGap(2, 2, 2)
                    .addGroup(jPanel_venta_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrol_referencia_categoria)
                        .addGroup(jPanel_venta_principalLayout.createSequentialGroup()
                            .addComponent(panel_referencia_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(panel_referencia_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 534, Short.MAX_VALUE)))
                    .addContainerGap()))
        );

        jTab_principal.addTab("VENTA PRINCIPAL", jPanel_venta_principal);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA OCUPACION"));

        tblfiltro_venta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblfiltro_venta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblfiltro_ventaMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblfiltro_venta);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1232, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
        );

        btnimprimir_ticket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ult_print.png"))); // NOI18N
        btnimprimir_ticket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_ticketActionPerformed(evt);
            }
        });

        panel_nro_habitacion.setBackground(new java.awt.Color(102, 153, 255));
        panel_nro_habitacion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_nro_habitacion.setLayout(new java.awt.GridLayout(1, 0));

        btnvertodoventa.setText("VER TODO");
        btnvertodoventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnvertodoventaActionPerformed(evt);
            }
        });

        cmbfecha_venta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbfecha_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbfecha_ventaActionPerformed(evt);
            }
        });

        jLabel4.setText("Fecha:");

        jCest_terminado.setText("TERMINADO");
        jCest_terminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCest_terminadoActionPerformed(evt);
            }
        });

        jCest_ocupado.setText("OCUPADO");
        jCest_ocupado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCest_ocupadoActionPerformed(evt);
            }
        });

        jCest_cancelado.setText("CANCELADO");
        jCest_cancelado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCest_canceladoActionPerformed(evt);
            }
        });

        jLabel5.setText("Usuario:");

        cmbusuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbusuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbusuarioActionPerformed(evt);
            }
        });

        jCest_desocupado.setText("DESOCUPADO");
        jCest_desocupado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCest_desocupadoActionPerformed(evt);
            }
        });

        jFtotal_filtro.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL FILTRO"));
        jFtotal_filtro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_filtro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_filtro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtcant_venta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcant_venta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtcant_venta.setBorder(javax.swing.BorderFactory.createTitledBorder("CANT"));

        txtmotivo_mudar_cancelar.setText("jTextField3");

        jCest_mudar.setText("MUDAR");
        jCest_mudar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCest_mudarActionPerformed(evt);
            }
        });

        btncargar_garantia.setBackground(new java.awt.Color(255, 153, 0));
        btncargar_garantia.setText("GARANTIA");
        btncargar_garantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncargar_garantiaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_filtro_habitacionLayout = new javax.swing.GroupLayout(jPanel_filtro_habitacion);
        jPanel_filtro_habitacion.setLayout(jPanel_filtro_habitacionLayout);
        jPanel_filtro_habitacionLayout.setHorizontalGroup(
            jPanel_filtro_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_filtro_habitacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_filtro_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel_filtro_habitacionLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbfecha_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCest_terminado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCest_ocupado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCest_desocupado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCest_cancelado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCest_mudar))
                    .addGroup(jPanel_filtro_habitacionLayout.createSequentialGroup()
                        .addComponent(btnimprimir_ticket, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnvertodoventa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncargar_garantia, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtmotivo_mudar_cancelar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcant_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel_filtro_habitacionLayout.createSequentialGroup()
                .addGroup(jPanel_filtro_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel_nro_habitacion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 54, Short.MAX_VALUE))
        );
        jPanel_filtro_habitacionLayout.setVerticalGroup(
            jPanel_filtro_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_filtro_habitacionLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(4, 4, 4)
                .addComponent(panel_nro_habitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel_filtro_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_filtro_habitacionLayout.createSequentialGroup()
                        .addGroup(jPanel_filtro_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbfecha_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(cmbusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jCest_terminado)
                            .addComponent(jCest_ocupado)
                            .addComponent(jCest_cancelado)
                            .addComponent(jCest_desocupado)
                            .addComponent(jCest_mudar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_filtro_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnimprimir_ticket, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtmotivo_mudar_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel_filtro_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnvertodoventa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btncargar_garantia))))
                    .addGroup(jPanel_filtro_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtcant_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFtotal_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jTab_principal.addTab("FILTRO HABITACION ", jPanel_filtro_habitacion);

        panel_habitaciones_libre.setBorder(javax.swing.BorderFactory.createTitledBorder("HABITACIONES"));
        panel_habitaciones_libre.setLayout(new java.awt.GridLayout(0, 5));

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("HABITACION SALIR"));

        txtnro_hab_grande_salir.setEditable(false);
        txtnro_hab_grande_salir.setBackground(new java.awt.Color(204, 204, 255));
        txtnro_hab_grande_salir.setFont(new java.awt.Font("Stencil", 0, 100)); // NOI18N
        txtnro_hab_grande_salir.setForeground(new java.awt.Color(255, 51, 51));
        txtnro_hab_grande_salir.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtnro_hab_grande_salir.setText("5");
        txtnro_hab_grande_salir.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "NUMERO HABITACION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jFmonto_total_pagar_salir.setEditable(false);
        jFmonto_total_pagar_salir.setBackground(new java.awt.Color(255, 255, 153));
        jFmonto_total_pagar_salir.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL PAGAR:"));
        jFmonto_total_pagar_salir.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_total_pagar_salir.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_total_pagar_salir.setText("1");
        jFmonto_total_pagar_salir.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        txttiempo_transcurrido_salir.setEditable(false);
        txttiempo_transcurrido_salir.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txttiempo_transcurrido_salir.setForeground(new java.awt.Color(255, 0, 0));
        txttiempo_transcurrido_salir.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txttiempo_transcurrido_salir.setText("jTextField3");
        txttiempo_transcurrido_salir.setBorder(javax.swing.BorderFactory.createTitledBorder("TIEMPO TRANSCURRIDO"));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtnro_hab_grande_salir)
            .addComponent(jFmonto_total_pagar_salir)
            .addComponent(txttiempo_transcurrido_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(txtnro_hab_grande_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFmonto_total_pagar_salir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txttiempo_transcurrido_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel_mudar_habitacionLayout = new javax.swing.GroupLayout(jPanel_mudar_habitacion);
        jPanel_mudar_habitacion.setLayout(jPanel_mudar_habitacionLayout);
        jPanel_mudar_habitacionLayout.setHorizontalGroup(
            jPanel_mudar_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_mudar_habitacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1020, Short.MAX_VALUE))
            .addGroup(jPanel_mudar_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_mudar_habitacionLayout.createSequentialGroup()
                    .addContainerGap(335, Short.MAX_VALUE)
                    .addComponent(panel_habitaciones_libre, javax.swing.GroupLayout.PREFERRED_SIZE, 953, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanel_mudar_habitacionLayout.setVerticalGroup(
            jPanel_mudar_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_mudar_habitacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(297, Short.MAX_VALUE))
            .addGroup(jPanel_mudar_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_mudar_habitacionLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel_habitaciones_libre, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTab_principal.addTab("MUDAR DE HABITACION", jPanel_mudar_habitacion);

        panel_temp_rpi_1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        panel_temp_rpi_1.setOpaque(false);
        panel_temp_rpi_1.setPreferredSize(new java.awt.Dimension(200, 400));
        panel_temp_rpi_1.setRequestFocusEnabled(false);
        panel_temp_rpi_1.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout panel_temp_rpi_1Layout = new javax.swing.GroupLayout(panel_temp_rpi_1);
        panel_temp_rpi_1.setLayout(panel_temp_rpi_1Layout);
        panel_temp_rpi_1Layout.setHorizontalGroup(
            panel_temp_rpi_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 194, Short.MAX_VALUE)
        );
        panel_temp_rpi_1Layout.setVerticalGroup(
            panel_temp_rpi_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );

        btnrecepcion_temp.setText("ver sql recepcion temp");
        btnrecepcion_temp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrecepcion_tempActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_temp_rpi_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnrecepcion_temp)
                .addContainerGap(941, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnrecepcion_temp)
                    .addComponent(panel_temp_rpi_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(205, Short.MAX_VALUE))
        );

        jTab_principal.addTab("DIAGNOSTICO RASPBERRY", jPanel16);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS GARANTIA"));

        txtgar_responsable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtgar_responsable.setBorder(javax.swing.BorderFactory.createTitledBorder("RESPONSABLE"));

        txtgar_descripcion.setColumns(20);
        txtgar_descripcion.setRows(5);
        txtgar_descripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCION DEL OBJETO"));
        jScrollPane5.setViewportView(txtgar_descripcion);

        txtgar_monto.setFont(new java.awt.Font("Stencil", 0, 48)); // NOI18N
        txtgar_monto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtgar_monto.setText("45.000");
        txtgar_monto.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO DE GARANTIA"));
        txtgar_monto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtgar_montoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtgar_montoKeyTyped(evt);
            }
        });

        btngar_guardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btngar_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_guardar.png"))); // NOI18N
        btngar_guardar.setText("GUARDAR ");
        btngar_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngar_guardarActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("DATO OCUPACION"));

        txtgar_idventa.setEditable(false);
        txtgar_idventa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtgar_idventa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtgar_idventa.setText("jTextField3");
        txtgar_idventa.setBorder(javax.swing.BorderFactory.createTitledBorder("ID OCUPACION"));

        txtgar_monto_ocupacion.setEditable(false);
        txtgar_monto_ocupacion.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        txtgar_monto_ocupacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtgar_monto_ocupacion.setText("jTextField4");
        txtgar_monto_ocupacion.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO OCUPACION"));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtgar_idventa, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtgar_monto_ocupacion))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtgar_idventa)
            .addComponent(txtgar_monto_ocupacion)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtgar_responsable)
            .addComponent(jScrollPane5)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(txtgar_monto, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btngar_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtgar_responsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtgar_monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btngar_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA DE GARANTIA"));

        tblgarantia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblgarantia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblgarantiaMousePressed(evt);
            }
        });
        jScrollPane6.setViewportView(tblgarantia);

        btngar_pagar_garantia.setText("PAGAR GARANTIA");
        btngar_pagar_garantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngar_pagar_garantiaActionPerformed(evt);
            }
        });

        btnimprimir_garantia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ult_print.png"))); // NOI18N
        btnimprimir_garantia.setText("GARANTIA");
        btnimprimir_garantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_garantiaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 965, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btngar_pagar_garantia, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnimprimir_garantia, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btngar_pagar_garantia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnimprimir_garantia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_garantia_pendienteLayout = new javax.swing.GroupLayout(panel_garantia_pendiente);
        panel_garantia_pendiente.setLayout(panel_garantia_pendienteLayout);
        panel_garantia_pendienteLayout.setHorizontalGroup(
            panel_garantia_pendienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_garantia_pendienteLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_garantia_pendienteLayout.setVerticalGroup(
            panel_garantia_pendienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_garantia_pendienteLayout.createSequentialGroup()
                .addGroup(panel_garantia_pendienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTab_principal.addTab("GARANTIA/PENDIENTE", panel_garantia_pendiente);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA FACTURA"));

        tblfactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(tblfactura);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1254, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
        );

        btnimprimir_factura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ult_print.png"))); // NOI18N
        btnimprimir_factura.setText("IMPRIMIR FACTURA");
        btnimprimir_factura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_facturaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnimprimir_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnimprimir_factura)
                .addContainerGap())
        );

        jTab_principal.addTab("FILTRO FACTURA", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTab_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 1271, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTab_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtbuscar_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_productoKeyReleased
        // TODO add your handling code here:
        actualizar_tabla_producto(3);
    }//GEN-LAST:event_txtbuscar_productoKeyReleased

    private void tblproductoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblproductoMouseReleased
        // TODO add your handling code here:
        seleccionar_producto();
    }//GEN-LAST:event_tblproductoMouseReleased

    private void txtcantidad_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_productoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_item_producto();
        }
    }//GEN-LAST:event_txtcantidad_productoKeyPressed

    private void txtcantidad_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_productoKeyReleased
        // TODO add your handling code here:
//        calcular_subtotal_itemventa();
        //        calculo_cantidad(evt);
        evejtf.setsuma_cantidad_flecha(evt, txtcantidad_producto);
    }//GEN-LAST:event_txtcantidad_productoKeyReleased

    private void txtcantidad_productoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_productoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtcantidad_productoKeyTyped

    private void btnagregar_cantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregar_cantidadActionPerformed
        // TODO add your handling code here:
//        cargar_item_producto();
        cargar_item_producto();
    }//GEN-LAST:event_btnagregar_cantidadActionPerformed

    private void txtcod_barraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcod_barraKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buscar_producto_cod_barra();
        }
    }//GEN-LAST:event_txtcod_barraKeyPressed

    private void txtcod_barraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcod_barraKeyReleased
        // TODO add your handling code here:
        //        actualizar_tabla_producto(5);
    }//GEN-LAST:event_txtcod_barraKeyReleased

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        ancho_tabla_producto();
        ancho_item_producto();
//        DAOgar.ancho_tabla_garantia(tblgarantia);
        iniciarTiempo();
    }//GEN-LAST:event_formInternalFrameOpened

    private void btncantidad_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_1ActionPerformed
        // TODO add your handling code here:
        cargar_con_cantidad(1);
    }//GEN-LAST:event_btncantidad_1ActionPerformed

    private void btncantidad_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_2ActionPerformed
        // TODO add your handling code here:
        cargar_con_cantidad(2);
    }//GEN-LAST:event_btncantidad_2ActionPerformed

    private void btncantidad_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_3ActionPerformed
        // TODO add your handling code here:
        cargar_con_cantidad(3);
    }//GEN-LAST:event_btncantidad_3ActionPerformed

    private void btncantidad_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_4ActionPerformed
        // TODO add your handling code here:
        cargar_con_cantidad(4);
    }//GEN-LAST:event_btncantidad_4ActionPerformed

    private void btncantidad_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_5ActionPerformed
        // TODO add your handling code here:
        cargar_con_cantidad(5);
    }//GEN-LAST:event_btncantidad_5ActionPerformed

    private void btncantidad_6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_6ActionPerformed
        // TODO add your handling code here:
        cargar_con_cantidad(6);
    }//GEN-LAST:event_btncantidad_6ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        FrmMenuMotel.setAbrir_frmventa(true);
        pararTimer();
    }//GEN-LAST:event_formInternalFrameClosing

    private void btncargar_consumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncargar_consumoActionPerformed
        // TODO add your handling code here:
        boton_cargar_consumo();

    }//GEN-LAST:event_btncargar_consumoActionPerformed

    private void jTab_principalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTab_principalMouseReleased
        // TODO add your handling code here:
        if (jTab_principal.getSelectedIndex() == 3) {
            tiempo_boton_hab = 0;
//            cargar_boton_nro_habitacion();
            String filtro = "";
            String orden = "order by v.idventa desc;";
            DAOven.actualizar_tabla_venta(conn, tblfiltro_venta, filtro, orden);
        }
        if (jTab_principal.getSelectedIndex() == 1) {
            select_tab_principal = 1;
            limpiar_habitacion_select();
            select_tab_principal = 0;
        }
        if (jTab_principal.getSelectedIndex() == 4) {
            cargar_boton_habitacion_mudar();
        }
        if (jTab_principal.getSelectedIndex() == 6) {
            reestableser_garantia();
        }

        if (jTab_principal.getSelectedIndex() == 7) {
            DAOf.actualizar_tabla_factura_con_venta(conn, tblfactura, "");
        }

    }//GEN-LAST:event_jTab_principalMouseReleased

    private void btndesocupar_pagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndesocupar_pagarActionPerformed
        // TODO add your handling code here:
        boton_desocupar_pagar();
    }//GEN-LAST:event_btndesocupar_pagarActionPerformed

    private void btnconsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconsumoActionPerformed
        // TODO add your handling code here:
        boton_ir_consumo();
    }//GEN-LAST:event_btnconsumoActionPerformed

    private void btndescontarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndescontarActionPerformed
        // TODO add your handling code here:
        boton_cargar_descuento();
    }//GEN-LAST:event_btndescontarActionPerformed

    private void btnadelantoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnadelantoActionPerformed
        // TODO add your handling code here:
        boton_cargar_adelanto();
    }//GEN-LAST:event_btnadelantoActionPerformed

    private void txtmonto_descontarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_descontarKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtmonto_descontarKeyTyped

    private void txtmonto_adelantoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_adelantoKeyPressed
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtmonto_adelantoKeyPressed

    private void btnsalir_ocupacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalir_ocupacionActionPerformed
        // TODO add your handling code here:
        limpiar_habitacion_select();
    }//GEN-LAST:event_btnsalir_ocupacionActionPerformed

    private void jRpor_horaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRpor_horaActionPerformed
        // TODO add your handling code here:
        seleccionar_tipo_ocupacion();
    }//GEN-LAST:event_jRpor_horaActionPerformed

    private void jRpor_dormirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRpor_dormirActionPerformed
        // TODO add your handling code here:
        seleccionar_tipo_ocupacion();
    }//GEN-LAST:event_jRpor_dormirActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        // TODO add your handling code here:
        boton_cancelar();
    }//GEN-LAST:event_btncancelarActionPerformed

    private void btnimprimir_ticketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_ticketActionPerformed
        // TODO add your handling code here:
        boton_imprimir_ticket();
    }//GEN-LAST:event_btnimprimir_ticketActionPerformed

    private void tblfiltro_ventaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblfiltro_ventaMouseReleased
        // TODO add your handling code here:
        seleccionar_venta();
    }//GEN-LAST:event_tblfiltro_ventaMouseReleased

    private void btnvertodoventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnvertodoventaActionPerformed
        // TODO add your handling code here:
        cmbfecha_venta.setSelectedIndex(4);
        cmbusuario.setSelectedIndex(0);
        fk_idhabitacion_dato_buscar = 0;
        jCest_terminado.setSelected(false);
        jCest_desocupado.setSelected(false);
        jCest_ocupado.setSelected(false);
        jCest_cancelado.setSelected(false);
        actualizar_tabla_venta_buscar();
    }//GEN-LAST:event_btnvertodoventaActionPerformed

    private void btneliminar_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_itemActionPerformed
        // TODO add your handling code here:
        boton_eliminar_item_ya_creado();
    }//GEN-LAST:event_btneliminar_itemActionPerformed

    private void cmbfecha_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbfecha_ventaActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_venta_buscar();
    }//GEN-LAST:event_cmbfecha_ventaActionPerformed

    private void cmbusuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbusuarioActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_venta_buscar();
    }//GEN-LAST:event_cmbusuarioActionPerformed

    private void jCest_terminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCest_terminadoActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_venta_buscar();
    }//GEN-LAST:event_jCest_terminadoActionPerformed

    private void jCest_ocupadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCest_ocupadoActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_venta_buscar();
    }//GEN-LAST:event_jCest_ocupadoActionPerformed

    private void jCest_canceladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCest_canceladoActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_venta_buscar();
    }//GEN-LAST:event_jCest_canceladoActionPerformed

    private void jCest_desocupadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCest_desocupadoActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_venta_buscar();
    }//GEN-LAST:event_jCest_desocupadoActionPerformed

    private void btnmudar_habitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmudar_habitacionActionPerformed
        // TODO add your handling code here:
        boton_mudar();
    }//GEN-LAST:event_btnmudar_habitacionActionPerformed

    private void btnrpi_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrpi_1ActionPerformed
        // TODO add your handling code here:
        boton_comando_raspberry(2);
    }//GEN-LAST:event_btnrpi_1ActionPerformed

    private void btnrpi_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrpi_2ActionPerformed
        // TODO add your handling code here:
        boton_comando_raspberry(3);
    }//GEN-LAST:event_btnrpi_2ActionPerformed

    private void btnrpi_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrpi_3ActionPerformed
        // TODO add your handling code here:
        boton_comando_raspberry(4);
    }//GEN-LAST:event_btnrpi_3ActionPerformed

    private void jCest_mudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCest_mudarActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_venta_buscar();
    }//GEN-LAST:event_jCest_mudarActionPerformed

    private void txtmonto_adelantoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_adelantoKeyReleased
        // TODO add your handling code here:
        evejtf.getString_format_nro_entero1(txtmonto_adelanto);
    }//GEN-LAST:event_txtmonto_adelantoKeyReleased

    private void btneliminar_item_tempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_item_tempActionPerformed
        // TODO add your handling code here:
        boton_eliminar_item_venta();
    }//GEN-LAST:event_btneliminar_item_tempActionPerformed

    private void tblitem_productoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblitem_productoMouseReleased
        // TODO add your handling code here:
        seleccionar_item_venta_temp();
    }//GEN-LAST:event_tblitem_productoMouseReleased

    private void btnimprimir_ticket_ocupadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_ticket_ocupadoActionPerformed
        // TODO add your handling code here:
        boton_imprimir_ticket_ocupado();
    }//GEN-LAST:event_btnimprimir_ticket_ocupadoActionPerformed

    private void jRpor_hora_mas_dormirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRpor_hora_mas_dormirActionPerformed
        // TODO add your handling code here:
        seleccionar_tipo_ocupacion();
    }//GEN-LAST:event_jRpor_hora_mas_dormirActionPerformed

    private void btnactualizar_total_pago_adelantoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizar_total_pago_adelantoActionPerformed
        // TODO add your handling code here:
        boton_actualizar_monto_adelanto();
    }//GEN-LAST:event_btnactualizar_total_pago_adelantoActionPerformed

    private void btnventa_internaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnventa_internaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmVenta_interna());
    }//GEN-LAST:event_btnventa_internaActionPerformed

    private void btnconsumo_adelantadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconsumo_adelantadoActionPerformed
        // TODO add your handling code here:
        boton_cargar_consumo_adelanto();
    }//GEN-LAST:event_btnconsumo_adelantadoActionPerformed

    private void btngar_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngar_guardarActionPerformed
        // TODO add your handling code here:
        boton_guardar_garantia();
    }//GEN-LAST:event_btngar_guardarActionPerformed

    private void btncargar_garantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncargar_garantiaActionPerformed
        // TODO add your handling code here:
        eveJtab.mostrar_JTabbedPane(jTab_principal, 6);
    }//GEN-LAST:event_btncargar_garantiaActionPerformed

    private void txtgar_montoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtgar_montoKeyReleased
        // TODO add your handling code here:
        evejtf.getDouble_format_nro_entero(txtgar_monto);
    }//GEN-LAST:event_txtgar_montoKeyReleased

    private void txtgar_montoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtgar_montoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtgar_montoKeyTyped

    private void btngar_pagar_garantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngar_pagar_garantiaActionPerformed
        // TODO add your handling code here:
        boton_guardar_garantia_pagar();
    }//GEN-LAST:event_btngar_pagar_garantiaActionPerformed

    private void tblgarantiaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblgarantiaMousePressed
        // TODO add your handling code here:
        seleccionar_garantia();
    }//GEN-LAST:event_tblgarantiaMousePressed

    private void btnimprimir_garantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_garantiaActionPerformed
        // TODO add your handling code here:
        boton_imprimir_ticket_garantia();
    }//GEN-LAST:event_btnimprimir_garantiaActionPerformed

    private void btncambiar_salida_finalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncambiar_salida_finalActionPerformed
        // TODO add your handling code here:
        boton_cambar_salida_final();
    }//GEN-LAST:event_btncambiar_salida_finalActionPerformed

    private void btnobservacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnobservacionActionPerformed
        // TODO add your handling code here:
        boton_ver_observacion_venta();
    }//GEN-LAST:event_btnobservacionActionPerformed

    private void btnrecepcion_tempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrecepcion_tempActionPerformed
        // TODO add your handling code here:
        imprimir_sql_recepcion_temp();
    }//GEN-LAST:event_btnrecepcion_tempActionPerformed

    private void btnimprimir_ultimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_ultimoActionPerformed
        // TODO add your handling code here:
        boton_imprimir_ultimo_registro();
    }//GEN-LAST:event_btnimprimir_ultimoActionPerformed

    private void btnimprimir_facturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_facturaActionPerformed
        // TODO add your handling code here:
        boton_imprimir_nota_factura();
    }//GEN-LAST:event_btnimprimir_facturaActionPerformed

    private void jRpor_hospedajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRpor_hospedajeActionPerformed
        // TODO add your handling code here:
        seleccionar_tipo_ocupacion();
    }//GEN-LAST:event_jRpor_hospedajeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizar_total_pago_adelanto;
    private javax.swing.JButton btnadelanto;
    private javax.swing.JButton btnagregar_cantidad;
    private javax.swing.JButton btncambiar_salida_final;
    private javax.swing.JButton btncancelar;
    private javax.swing.JButton btncantidad_1;
    private javax.swing.JButton btncantidad_2;
    private javax.swing.JButton btncantidad_3;
    private javax.swing.JButton btncantidad_4;
    private javax.swing.JButton btncantidad_5;
    private javax.swing.JButton btncantidad_6;
    private javax.swing.JButton btncargar_consumo;
    private javax.swing.JButton btncargar_garantia;
    private javax.swing.JButton btnconsumo;
    private javax.swing.JButton btnconsumo_adelantado;
    private javax.swing.JButton btndescontar;
    private javax.swing.JButton btndesocupar_pagar;
    private javax.swing.JButton btneliminar_item;
    private javax.swing.JButton btneliminar_item_temp;
    private javax.swing.JButton btngar_guardar;
    private javax.swing.JButton btngar_pagar_garantia;
    private javax.swing.JButton btnimprimir_factura;
    private javax.swing.JButton btnimprimir_garantia;
    private javax.swing.JButton btnimprimir_ticket;
    private javax.swing.JButton btnimprimir_ticket_ocupado;
    private javax.swing.JButton btnimprimir_ultimo;
    private javax.swing.JButton btnmudar_habitacion;
    private javax.swing.JButton btnobservacion;
    private javax.swing.JButton btnrecepcion_temp;
    private javax.swing.JButton btnrpi_1;
    private javax.swing.JButton btnrpi_2;
    private javax.swing.JButton btnrpi_3;
    private javax.swing.JButton btnsalir_ocupacion;
    private javax.swing.JButton btnventa_interna;
    private javax.swing.JButton btnvertodoventa;
    private javax.swing.JComboBox<String> cmbfecha_venta;
    private javax.swing.JComboBox<String> cmbusuario;
    private javax.swing.ButtonGroup gru_tarifa;
    private javax.swing.JCheckBox jCest_cancelado;
    private javax.swing.JCheckBox jCest_desocupado;
    private javax.swing.JCheckBox jCest_mudar;
    private javax.swing.JCheckBox jCest_ocupado;
    private javax.swing.JCheckBox jCest_terminado;
    private javax.swing.JFormattedTextField jFdormir_ingreso_final;
    private javax.swing.JFormattedTextField jFdormir_ingreso_inicio;
    private javax.swing.JFormattedTextField jFdormir_salida_final;
    private javax.swing.JFormattedTextField jFmonto_adicional;
    private javax.swing.JFormattedTextField jFmonto_adicional_total;
    private javax.swing.JFormattedTextField jFmonto_consumo;
    private javax.swing.JFormattedTextField jFmonto_dormir_adicional;
    private javax.swing.JFormattedTextField jFmonto_dormir_minimo;
    private javax.swing.JFormattedTextField jFmonto_hora_adicional;
    private javax.swing.JFormattedTextField jFmonto_hora_minimo;
    private javax.swing.JFormattedTextField jFmonto_hospedaje;
    private javax.swing.JFormattedTextField jFmonto_minimo;
    private javax.swing.JFormattedTextField jFmonto_tarifa_ocupacion_total;
    private javax.swing.JFormattedTextField jFmonto_total_pagar;
    private javax.swing.JFormattedTextField jFmonto_total_pagar_salir;
    private javax.swing.JFormattedTextField jFtotal_consumo;
    private javax.swing.JFormattedTextField jFtotal_filtro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel_estado_habitacion;
    private javax.swing.JPanel jPanel_filtro_habitacion;
    private javax.swing.JPanel jPanel_item_venta;
    private javax.swing.JPanel jPanel_mudar_habitacion;
    private javax.swing.JPanel jPanel_tiempo_habitacion;
    private javax.swing.JPanel jPanel_venta_principal;
    private javax.swing.JRadioButton jRpor_dormir;
    private javax.swing.JRadioButton jRpor_hora;
    private javax.swing.JRadioButton jRpor_hora_mas_dormir;
    private javax.swing.JRadioButton jRpor_hospedaje;
    private javax.swing.JScrollPane jScrol_referencia_categoria;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTab_principal;
    private javax.swing.JTabbedPane jTab_producto_ingrediente;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JProgressBar jbar_tiempo_minimo;
    private javax.swing.JLabel lblentrada_actual;
    private javax.swing.JLabel lblicono_tipo;
    private javax.swing.JLabel lblmensaje_cancelar;
    private javax.swing.JLabel lbltipo_tarifa_icono;
    private javax.swing.JPanel panel_garantia_pendiente;
    private javax.swing.JPanel panel_habitaciones;
    private javax.swing.JPanel panel_habitaciones_libre;
    private javax.swing.JPanel panel_insertar_pri_producto;
    private javax.swing.JPanel panel_nro_habitacion;
    private javax.swing.JPanel panel_puerta;
    private javax.swing.JPanel panel_referencia_categoria;
    private javax.swing.JPanel panel_referencia_marca;
    private javax.swing.JPanel panel_referencia_unidad;
    private javax.swing.JPanel panel_temp_rpi_1;
    private javax.swing.JTable tblfactura;
    private javax.swing.JTable tblfiltro_venta;
    private javax.swing.JTable tblgarantia;
    private javax.swing.JTable tblitem_consumo_cargado;
    private javax.swing.JTable tblitem_producto;
    private javax.swing.JTable tblproducto;
    private javax.swing.JTextField txtbuscar_producto;
    private javax.swing.JTextField txtcant_adicional;
    private javax.swing.JTextField txtcant_venta;
    private javax.swing.JTextField txtcantidad_producto;
    private javax.swing.JTextField txtcod_barra;
    private javax.swing.JTextField txtdescripcion_habitacion;
    private javax.swing.JTextField txtentrada_actual;
    private javax.swing.JTextField txtfec_ocupado_inicio;
    private javax.swing.JTextField txtfec_ocupado_inicio_hora;
    private javax.swing.JTextArea txtgar_descripcion;
    private javax.swing.JTextField txtgar_idventa;
    private javax.swing.JTextField txtgar_monto;
    private javax.swing.JTextField txtgar_monto_ocupacion;
    private javax.swing.JTextField txtgar_responsable;
    private javax.swing.JTextField txtidventa;
    private javax.swing.JTextField txtminumo_minimo;
    private javax.swing.JTextField txtminuto_adicional;
    private javax.swing.JTextField txtminuto_cancelar;
    private javax.swing.JTextField txtmonto_adelanto;
    private javax.swing.JTextField txtmonto_descontar;
    private javax.swing.JTextField txtmotivo_mudar_cancelar;
    private javax.swing.JTextField txtnro_hab_grande;
    private javax.swing.JTextField txtnro_hab_grande_salir;
    private javax.swing.JTextField txtrecepcion_actual;
    private javax.swing.JTextField txttiempo_ahora;
    private javax.swing.JTextField txttiempo_transcurrido;
    private javax.swing.JTextField txttiempo_transcurrido_salir;
    private javax.swing.JTextField txttipo_habitacion;
    // End of variables declaration//GEN-END:variables
}
