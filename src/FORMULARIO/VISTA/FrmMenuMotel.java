/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import CONFIGURACION.ComputerInfo;
import Config_JSON.json_array_conexion;
import Config_JSON.json_array_formulario;
import Config_JSON.json_array_imprimir_pos;
import Evento.Fecha.EvenFecha;
import Evento.Jframe.EvenJFRAME;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenSonido;
import FORMULARIO.BO.BO_habitacion_recepcion_temp;
import FORMULARIO.DAO.DAO_caja_cierre;
import FORMULARIO.ENTIDAD.habitacion_recepcion_temp;
import FORMULARIO.ENTIDAD.usuario;
import java.awt.Color;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class FrmMenuMotel extends javax.swing.JFrame {

    Connection conn = null;
    ConnPostgres conPs = new ConnPostgres();
    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenConexion eveconn = new EvenConexion();
    private EvenFecha evefec = new EvenFecha();
    private json_array_conexion jscon = new json_array_conexion();
    private json_array_imprimir_pos jsprint = new json_array_imprimir_pos();
    private json_array_formulario jsfrm = new json_array_formulario();
    private usuario ENTusu = new usuario();
    private habitacion_recepcion_temp ENThrt = new habitacion_recepcion_temp();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private ComputerInfo pcinfo = new ComputerInfo();
    private BO_habitacion_recepcion_temp BOhrt = new BO_habitacion_recepcion_temp();
    private DAO_caja_cierre DAOcc = new DAO_caja_cierre();
    private String version = "V.: 1.9.1";
    private String creado_por = "digno";
    public static boolean habilitar_sonido;
    private boolean no_es_sonido_ocupado;
    private boolean hab_ruta_sonido[];
    private String string_ruta_sonido[];
    private int cant_de_habitacion;
    private int sensor_puerta_cliente = 2;
    private int sensor_puerta_limpieza = 3;
    public static boolean abrir_frmventa;
    private boolean crear_exp_app;
    private int tiempo_exp_app;
    private int segundo_exp_app;

    public static boolean isHabilitar_sonido() {
        return habilitar_sonido;
    }

    public static void setHabilitar_sonido(boolean habilitar_sonido) {
        FrmMenuMotel.habilitar_sonido = habilitar_sonido;
    }
    private Timer tiempo_sonido;

//    FrmGasto frmgasto=new FrmGasto();
    private void abrir_formulario() {
        lblversion.setText(version);
        conPs.ConnectDBpostgres(conn, false);
        conn = conPs.getConnPosgres();
        jsprint.cargar_jsom_imprimir_pos();
        jsfrm.cargar_jsom_array_formulario();
        creado_por = ENTusu.getGlobal_nombre();
        tiempo_exp_app = jsfrm.getApp_tiempo_min_exp();
        segundo_exp_app = tiempo_exp_app - 5;
        crear_exp_app = true;
        lblnube.setVisible(false);
        setHabilitar_sonido(false);
        setAbrir_frmventa(true);
        iniciarTiempo();
        bloqueo_inicio();
        cargar_array_habitacion();
        titulo_sistema(this);
        actualizacion_version_v1();
        BOhrt.update_habitacion_recepcion_temp_puertas_activos();
    }

    private void bloqueo_inicio() {
        barra_menu_principal.setEnabled(false);
    }

    private void titulo_sistema(JFrame frame) {
        frame.setExtendedState(MAXIMIZED_BOTH);
        String titulo = jscon.getNombre()
                + " BD: " + jscon.getLocalhost() + " /" + jscon.getPort() + " /" + jscon.getBasedato() + " IP:" + pcinfo.getStringMiIP()
                + " Version: " + version;
        frame.setTitle(titulo);
    }

    private void abrir_login() {
        bloqueo_inicio();
        JDiaLogin log = new JDiaLogin(this, true);
        log.setVisible(true);
    }

    private void cerrar_formularios() {
//        FrmGasto frm=new FrmGasto();
//        FrmGasto().dispose();
//        evetbl.cerrar_TablaJinternal(new FrmGasto());
    }

    private void actualizacion_version_v1() {

        String sql = "DO $$ \n"
                + "    BEGIN\n"
                + "        BEGIN\n"
                //                + "         ALTER TABLE producto ADD COLUMN precio_interno NUMERIC(14,0) DEFAULT 0;\n"
                //                + "         update producto set precio_interno=precio_venta;"
                + "INSERT INTO public.usuario_evento(idusuario_evento, fecha_creado, creado_por, codigo, nombre, descripcion, fk_idusuario_tipo_evento, fk_idusuario_formulario, mensaje_error) \n"
                + "values\n"
                + "(1,'2023-01-09 15:36:21.209','PROGRAMADOR ADMIN',1001,'HABILITAR EDITAR PRODUCTO','permite editar todos los datos del producto',2,2,'TU ROLL NO PUEDE EDITAR EL PRODUCTO'),\n"
                + "(2,'2023-01-09 19:44:33.985','PROGRAMADOR ADMIN',1002,'HABILITAR TABBED CAJA CERRADO','muestra todas las cajas cerrados anteriormente',3,3,'TU ROLL NO PUEDE VER CAJA CERRADO');\n"
                + "\n"
                + "INSERT INTO  public.usuario_formulario (idusuario_formulario, fecha_creado, creado_por, nombre) values\n"
                + "(1,'2023-01-09 15:29:58.811','PROGRAMADOR ADMIN','SIN-DATO'),\n"
                + "(2,'2023-01-09 15:30:51','PROGRAMADOR ADMIN','PRODUCTO'),\n"
                + "(3,'2023-01-09 19:40:52.595','PROGRAMADOR ADMIN','CAJA');\n"
                + "\n"
                + "INSERT INTO public.usuario_item_rol (idusuario_item_rol, fecha_creado, creado_por, activo, fk_idusuario_rol, fk_idusuario_evento) VALUES\n"
                + "('2','2023-01-09 15:36:22.506','PROGRAMADOR ADMIN','f','3','1'),\n"
                + "('3','2023-01-09 15:36:22.514','PROGRAMADOR ADMIN','f','2','1'),\n"
                + "('4','2023-01-09 15:36:22.519','PROGRAMADOR ADMIN','f','1','1'),\n"
                + "('6','2023-01-09 19:44:35.164','PROGRAMADOR ADMIN','f','3','2'),\n"
                + "('7','2023-01-09 19:44:35.17','PROGRAMADOR ADMIN','f','2','2'),\n"
                + "('8','2023-01-09 19:44:35.174','PROGRAMADOR ADMIN','f','1','2'),\n"
                + "('5','2023-01-09 19:44:35.159','PROGRAMADOR ADMIN','t','4','2'),\n"
                + "('1','2023-01-09 15:36:22.499','PROGRAMADOR ADMIN','t','4','1');\n"
                + "\n"
                + "INSERT INTO  public.usuario_rol (idusuario_rol, fecha_creado, creado_por, nombre, descripcion) VALUES\n"
                + "('1','2023-01-09 15:32:55.673','PROGRAMADOR ADMIN','SIN-DATO','SIN'),\n"
                + "('2','2023-01-09 15:33:22.785','PROGRAMADOR ADMIN','CAJERO','USUARIO CAJA'),\n"
                + "('3','2023-01-09 15:33:58.057','PROGRAMADOR ADMIN','ADMINISTRADOR','NIVEL 2 DE ACCESO'),\n"
                + "('4','2023-01-09 15:34:22.025','PROGRAMADOR ADMIN','PROGRAMADOR','ADMINISTRA EL SISTEMA');\n"
                + "\n"
                + "INSERT INTO  public.usuario_tipo_evento (idusuario_tipo_evento, fecha_creado, creado_por, nombre) VALUES\n"
                + "('1','2023-01-09 15:31:38.432','PROGRAMADOR ADMIN','SIN-DATO'),\n"
                + "('2','2023-01-09 15:32:05.752','PROGRAMADOR ADMIN','BOTON EDITAR'),\n"
                + "('3','2023-01-09 19:42:36.337','PROGRAMADOR ADMIN','HABILITAR jTabbedPane');  "
                + "        EXCEPTION\n"
                + "            WHEN duplicate_column THEN RAISE NOTICE 'duplicate_column.';\n"
                + "        END;\n"
                + "    END;\n"
                + "$$ ";
//        eveconn.SQL_execute_libre(conn, sql);
    }

    private void actualizar_estado_puerta_cliente_limpieza() {
        BOhrt.update_habitacion_recepcion_temp_puertas(sensor_puerta_cliente, sensor_puerta_limpieza);
    }

    private void iniciarTiempo() {
        tiempo_sonido = new Timer();
        tiempo_sonido.schedule(new FrmMenuMotel.clasetiempo(), 0, 1000 * 1);
        System.out.println("Timer INICIAR SONIDO");
    }

    private void exportar_excel() {
        lblnube.setVisible(true);
        DAOcc.exportar_excel_caja_fecha_usu(conn);
        DAOcc.exportar_excel_estado_habitacion(conn);
        DAOcc.exportar_excel_lista_producto(conn);
        DAOcc.exportar_excel_caja_resumen(conn);
        DAOcc.exportar_excel_habitacion_estados(conn);
        DAOcc.exportar_excel_habitacion_estados_resumen(conn);
        crear_exp_app = false;
    }

    class clasetiempo extends TimerTask {

        public void run() {
            if (isHabilitar_sonido()) {
                lblhora.setText(evefec.getString_formato_hora_min_seg());
                lblturno.setText(evefec.getString_turno());
                cargar_sql_habitacion_recepcion_temp();
                actualizar_estado_puerta_cliente_limpieza();

            }
            segundo_exp_app++;
            if (segundo_exp_app > (tiempo_exp_app)) {
                if (crear_exp_app && jsfrm.isApp_act_exp()) {
                    exportar_excel();
                }
            }
            if (segundo_exp_app > ((tiempo_exp_app) + 10)) {
                lblnube.setVisible(false);
                crear_exp_app = true;
                segundo_exp_app = 0;
            }
        }
    }

    private void pararTimer() {
        tiempo_sonido.cancel();
        System.out.println("Timer PARADO:");
    }

    private void crear_sonido(String ruta_sonido, int idhabitacion_dato) {
//        if (no_es_sonido_ocupado) {
        string_ruta_sonido[idhabitacion_dato] = ruta_sonido;
        if (!ruta_sonido.equals("NO")) {
            if (!string_ruta_sonido[idhabitacion_dato].equals(ruta_sonido)) {
                hab_ruta_sonido[idhabitacion_dato] = true;
            }
            if (hab_ruta_sonido[idhabitacion_dato]) {
                EvenSonido.reproducir_vos(ruta_sonido);
                string_ruta_sonido[idhabitacion_dato] = ruta_sonido;
                hab_ruta_sonido[idhabitacion_dato] = false;
            }
        } else {
            hab_ruta_sonido[idhabitacion_dato] = true;
        }
//        }
    }

    private void cargar_sql_habitacion_recepcion_temp() {
        String titulo = "";
        String sql = "select\n"
                + "	case\n"
                + "             when estado = 'OCUPADO'\n"
                + "		and puerta_limpieza = true\n"
                + "		and puerta_cliente = false \n"
                + "             and (extract(epoch from (current_timestamp - fec_ocupado_inicio)))>10 \n"
                + "                          then 'sounds/puerta_cliente.wav'\n"
                + "             when estado = 'LIBRE'\n"
                + "		and puerta_limpieza = false\n"
                //+ "		and puerta_cliente = false \n"
                + "                          then 'sounds/puerta_limpieza.wav'\n"
                + "		when estado = 'OCUPADO'\n"
                + "		and puerta_limpieza = true\n"
                + "		and puerta_cliente = false \n"
                + "		and (extract(epoch from (current_timestamp - fec_ocupado_inicio)))<10  \n"
                + "                          then 'sounds/son_ocu/son_ocu_' || nro_habitacion || '.wav'\n"
                + "		when estado = 'LIBRE'\n"
                + "		and puerta_limpieza = true\n"
                + "		and puerta_cliente = true \n"
                + "		and (extract(epoch from (current_timestamp - fec_limpieza_fin)))<10  \n"
                + "                          then 'sounds/son_libre/son_libre_' || nro_habitacion || '.wav'\n"
                + "		when estado = 'SUCIO'\n"
                + "		and puerta_limpieza = true\n"
                + "		and puerta_cliente = true\n"
                + "		and (extract(epoch from (current_timestamp - fec_ocupado_fin)))<20  \n"
                + "                          then 'sounds/son_deso/son_deso_' || nro_habitacion || '.wav'\n"
                + "		when estado = 'LIMPIANDO'\n"
                + "		and puerta_limpieza = false \n"
                + "		and (extract(epoch from (current_timestamp - fec_limpieza_inicio)))<10  \n"
                + "                          then 'sounds/son_limpieza/son_limpieza_' || nro_habitacion || '.wav'\n"
                + "		else 'NO'\n"
                + "	end as ruta_sonido,\n"
                + "idhabitacion_dato "
                + "from\n"
                + "	habitacion_recepcion_temp \n"
                + "where activo=true \n"
                + "order by orden asc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL_sinprint(conn, sql, titulo);
            int fila = 0;
            while (rs.next()) {
                int idhabitacion_dato = rs.getInt("idhabitacion_dato");
                String ruta_sonido = rs.getString("ruta_sonido");
                crear_sonido(ruta_sonido, idhabitacion_dato);
                fila++;
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

//    public int getInt_cant_habitacion_activo(Connection conn) {
//        String titulo = "getInt_ultimoID";
//        int getid = 0;
//        String sql = "select count(*) as cant from habitacion_recepcion_temp where activo=true;";
//        try {
//            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
//            if (rs.next()) {
//                getid = rs.getInt("cant");
//            }
//        } catch (SQLException e) {
//            evemen.mensaje_error(e, sql, titulo);
//        }
//        return getid;
//    }
    private void cargar_array_habitacion() {
        cant_de_habitacion = (eveconn.getInt_ultimoID_max(conn, ENThrt.getTb_habitacion_recepcion_temp(), ENThrt.getId_idhabitacion_recepcion_temp()));
//        cant_de_habitacion = getInt_cant_habitacion_activo(conn);
        hab_ruta_sonido = new boolean[cant_de_habitacion + 1];//+1
        string_ruta_sonido = new String[cant_de_habitacion + 1];//+1
    }

    public static boolean isAbrir_frmventa() {
        return abrir_frmventa;
    }

    public static void setAbrir_frmventa(boolean abrir_frmventa) {
        FrmMenuMotel.abrir_frmventa = abrir_frmventa;
    }

    public FrmMenuMotel() {
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

        escritorio = new javax.swing.JDesktopPane();
        btncerrar_seccion = new javax.swing.JButton();
        lblusuario = new javax.swing.JLabel();
        panel_acceso_rapido = new javax.swing.JPanel();
        btncrear_habitacion = new javax.swing.JButton();
        btnproducto = new javax.swing.JButton();
        btnventa = new javax.swing.JButton();
        btncajacierre = new javax.swing.JButton();
        btngasto = new javax.swing.JButton();
        btnpersona = new javax.swing.JButton();
        btncargar_stock = new javax.swing.JButton();
        btnventa_interna = new javax.swing.JButton();
        lblversion = new javax.swing.JLabel();
        lblhora = new javax.swing.JLabel();
        lblturno = new javax.swing.JLabel();
        lblnube = new javax.swing.JLabel();
        barra_menu_principal = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenu12 = new javax.swing.JMenu();
        jMenuItem28 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btncerrar_seccion.setText("CERRAR SESION");
        btncerrar_seccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncerrar_seccionActionPerformed(evt);
            }
        });

        lblusuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblusuario.setForeground(new java.awt.Color(102, 102, 102));
        lblusuario.setText("usuario");

        panel_acceso_rapido.setBorder(javax.swing.BorderFactory.createTitledBorder("ACCESO RAPIDO"));

        btncrear_habitacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/72_conf_hab.png"))); // NOI18N
        btncrear_habitacion.setText("HABITACION");
        btncrear_habitacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncrear_habitacion.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btncrear_habitacion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncrear_habitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncrear_habitacionActionPerformed(evt);
            }
        });

        btnproducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/72_prod_bebida.png"))); // NOI18N
        btnproducto.setText("PRODUCTO");
        btnproducto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnproducto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnproductoActionPerformed(evt);
            }
        });

        btnventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/72_ocupar_hab.png"))); // NOI18N
        btnventa.setText("OCUPACION");
        btnventa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnventa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnventaActionPerformed(evt);
            }
        });

        btncajacierre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/72_caja.png"))); // NOI18N
        btncajacierre.setText("CAJA CIERRE");
        btncajacierre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncajacierre.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncajacierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncajacierreActionPerformed(evt);
            }
        });

        btngasto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/gasto.png"))); // NOI18N
        btngasto.setText("GASTO");
        btngasto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btngasto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btngasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngastoActionPerformed(evt);
            }
        });

        btnpersona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/cliente.png"))); // NOI18N
        btnpersona.setText("PERSONA");
        btnpersona.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnpersona.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnpersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpersonaActionPerformed(evt);
            }
        });

        btncargar_stock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/72_compra.png"))); // NOI18N
        btncargar_stock.setText("CARGAR ST");
        btncargar_stock.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncargar_stock.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncargar_stock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncargar_stockActionPerformed(evt);
            }
        });

        btnventa_interna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/72_ven_inter.png"))); // NOI18N
        btnventa_interna.setText("VEN INTERNA");
        btnventa_interna.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnventa_interna.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnventa_interna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnventa_internaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_acceso_rapidoLayout = new javax.swing.GroupLayout(panel_acceso_rapido);
        panel_acceso_rapido.setLayout(panel_acceso_rapidoLayout);
        panel_acceso_rapidoLayout.setHorizontalGroup(
            panel_acceso_rapidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_acceso_rapidoLayout.createSequentialGroup()
                .addComponent(btncrear_habitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(btnventa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncajacierre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btngasto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnpersona)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncargar_stock)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnventa_interna)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_acceso_rapidoLayout.setVerticalGroup(
            panel_acceso_rapidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btncajacierre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnventa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btngasto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btncrear_habitacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnpersona, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btncargar_stock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnventa_interna, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        lblversion.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblversion.setForeground(new java.awt.Color(102, 102, 102));
        lblversion.setText("version");

        lblhora.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblhora.setForeground(new java.awt.Color(0, 0, 102));
        lblhora.setText("HORA");

        lblturno.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblturno.setForeground(new java.awt.Color(0, 0, 102));
        lblturno.setText("HORA");

        lblnube.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblnube.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/72_nube.png"))); // NOI18N

        escritorio.setLayer(btncerrar_seccion, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(lblusuario, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(panel_acceso_rapido, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(lblversion, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(lblhora, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(lblturno, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(lblnube, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(escritorioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(escritorioLayout.createSequentialGroup()
                        .addComponent(btncerrar_seccion, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(escritorioLayout.createSequentialGroup()
                        .addComponent(panel_acceso_rapido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblversion, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblhora)
                            .addComponent(lblturno))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(lblnube, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(escritorioLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(escritorioLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(lblnube, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panel_acceso_rapido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(escritorioLayout.createSequentialGroup()
                        .addComponent(lblversion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblhora)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblturno)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btncerrar_seccion)
                    .addComponent(lblusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(337, Short.MAX_VALUE))
        );

        jMenu3.setText("HABITACION");

        jMenuItem3.setText("CREAR HABITACION");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem4.setText("COSTO HABITACION");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenu4.setText("AUTOMATIZACION");

        jMenuItem5.setText("SENSOR");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuItem6.setText("ARDUINO");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuItem7.setText("MINI PC");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem7);

        jMenu3.add(jMenu4);

        jMenuItem21.setText("PUERTAS MANUAL");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem21);

        barra_menu_principal.add(jMenu3);

        jMenu5.setText("PRODUCTO");

        jMenuItem8.setText("DATO PRODUCTO");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem8);

        jMenuItem9.setText("CATEGORIA");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem9);

        jMenuItem10.setText("UNIDAD");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem10);

        jMenuItem11.setText("MARCA");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem11);

        jMenu8.setText("REPORTE");

        jMenuItem15.setText("INVENTARIO VALORIZADO");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem15);

        jMenuItem16.setText("GANACIA POR PRODUCTOS");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem16);

        jMenuItem22.setText("INVENTARIO SIMPLE");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem22);

        jMenu5.add(jMenu8);

        jMenuItem20.setText("CARGA STOCK");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem20);

        jMenu9.setText("INVENTARIO");

        jMenuItem17.setText("CREAR INVENTARIO");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem17);

        jMenu5.add(jMenu9);

        barra_menu_principal.add(jMenu5);

        jMenu6.setText("USUARIO");

        jMenuItem12.setText("CREAR USUARIO");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem12);

        jMenuItem24.setText("USUARIO FORMULARIO");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem24);

        jMenuItem25.setText("USUARIO TIPO EVENTO");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem25);

        jMenuItem26.setText("USUARIO EVENTO");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem26);

        jMenuItem27.setText("USUARIO ROLL");
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem27);

        barra_menu_principal.add(jMenu6);

        jMenu2.setText("GASTO");

        jMenuItem1.setText("NUEVO GASTO");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("TIPO GASTO");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        barra_menu_principal.add(jMenu2);

        jMenu1.setText("OCUPACION");

        jMenuItem13.setText("ABRIR OCUPACION");
        jMenu1.add(jMenuItem13);

        barra_menu_principal.add(jMenu1);

        jMenu7.setText("CAJA");

        jMenuItem14.setText("CERRAR CAJA");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem14);

        barra_menu_principal.add(jMenu7);

        jMenu10.setText("PERSONA");

        jMenuItem18.setText("DATO PERSONA");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem18);

        jMenuItem19.setText("CARGO PERSONA");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem19);

        barra_menu_principal.add(jMenu10);

        jMenu11.setText("VENTA INTERNA");

        jMenuItem23.setText("NUEVA VENTA INTERNA");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem23);

        barra_menu_principal.add(jMenu11);

        jMenu12.setText("CONFIGURACION");

        jMenuItem28.setText("EXPORTAR EXECL APPSHEET");
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem28);

        barra_menu_principal.add(jMenu12);

        setJMenuBar(barra_menu_principal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmGasto_Tipo());
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmHab_crear());
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmHab_costo());
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmHab_sensor());
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmHab_arduino());
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmHab_miniPc());
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProd_marca());
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProd_unidad());
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProd_categoria());
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProd_dato());
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void btnproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnproductoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProd_dato());
    }//GEN-LAST:event_btnproductoActionPerformed

    private void btncrear_habitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncrear_habitacionActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmHab_crear());
    }//GEN-LAST:event_btncrear_habitacionActionPerformed

    private void btnventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnventaActionPerformed
        // TODO add your handling code here:
        if (isAbrir_frmventa()) {
            evetbl.abrir_TablaJinternal(new FrmVenta());
        } else {
            JOptionPane.showMessageDialog(null, "FORMULARIO OCUPACION YA ESTA ABIERTO");
        }
    }//GEN-LAST:event_btnventaActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmUsuario_crear());
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        abrir_login();
    }//GEN-LAST:event_formWindowOpened

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCaja_Detalle());
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmGasto());
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btncajacierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncajacierreActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCaja_Detalle());
    }//GEN-LAST:event_btncajacierreActionPerformed

    private void btngastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngastoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmGasto());
    }//GEN-LAST:event_btngastoActionPerformed

    private void btncerrar_seccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncerrar_seccionActionPerformed
        // TODO add your handling code here:
        cerrar_formularios();
        abrir_login();
    }//GEN-LAST:event_btncerrar_seccionActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepInventarioValorizado());
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepGananciaProducto());
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmPersona_cargo());
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void btnpersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpersonaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmPersona());
    }//GEN-LAST:event_btnpersonaActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmPersona());
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void btncargar_stockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncargar_stockActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCompra_reposicion());
    }//GEN-LAST:event_btncargar_stockActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCompra_reposicion());
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCrearInventario());
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        // TODO add your handling code here:
        FrmPuertas frm = new FrmPuertas();
        frm.setVisible(true);
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepInventarioSimple());
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmVenta_interna());
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void btnventa_internaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnventa_internaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmVenta_interna());
    }//GEN-LAST:event_btnventa_internaActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmUsuario_Formulario());
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmUsuario_tipo_evento());
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmUsuario_evento());
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmUsuario_roll());
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        // TODO add your handling code here:
        exportar_excel();
    }//GEN-LAST:event_jMenuItem28ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmMenuMotel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMenuMotel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMenuMotel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMenuMotel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMenuMotel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JMenuBar barra_menu_principal;
    private javax.swing.JButton btncajacierre;
    private javax.swing.JButton btncargar_stock;
    private javax.swing.JButton btncerrar_seccion;
    private javax.swing.JButton btncrear_habitacion;
    private javax.swing.JButton btngasto;
    private javax.swing.JButton btnpersona;
    private javax.swing.JButton btnproducto;
    private javax.swing.JButton btnventa;
    private javax.swing.JButton btnventa_interna;
    public static javax.swing.JDesktopPane escritorio;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JLabel lblhora;
    private javax.swing.JLabel lblnube;
    private javax.swing.JLabel lblturno;
    public static javax.swing.JLabel lblusuario;
    public static javax.swing.JLabel lblversion;
    public static javax.swing.JPanel panel_acceso_rapido;
    // End of variables declaration//GEN-END:variables
}
