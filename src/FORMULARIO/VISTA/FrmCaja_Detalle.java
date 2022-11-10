/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import ESTADOS.EvenEstado;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import IMPRESORA_POS.PosImprimir_CierreCajaDetalle;
import IMPRESORA_POS.PosImprimir_Venta;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class FrmCaja_Detalle extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenFecha evefec = new EvenFecha();
    private EvenCombobox evecmb = new EvenCombobox();
    private caja_cierre_detalle ENTccd = new caja_cierre_detalle();
    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    private caja_cierre ENTcc = new caja_cierre();
    private DAO_caja_cierre DAOcc = new DAO_caja_cierre();
    private BO_caja_cierre BOcc = new BO_caja_cierre();
    private caja_cierre_item ENTcci = new caja_cierre_item();
    private DAO_caja_cierre_item DAOcci = new DAO_caja_cierre_item();
    private DAO_venta DAOven = new DAO_venta();
    private DAO_gasto DAOg = new DAO_gasto();
    private DAO_compra DAOcom = new DAO_compra();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenConexion eveconn = new EvenConexion();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private EvenEstado eveest = new EvenEstado();
    private PosImprimir_Venta posv = new PosImprimir_Venta();
    private PosImprimir_CierreCajaDetalle poscd = new PosImprimir_CierreCajaDetalle();
    usuario ENTusu = new usuario();
    private DAO_usuario DAOusu = new DAO_usuario();
    Connection conn = ConnPostgres.getConnPosgres();
    private String nombreTabla = "CAJA CIERRE";
    int fk_idusuario = ENTusu.getGlobal_idusuario();
    private String fec_inicio;
    private String fec_final;
    private String creado_por;
    private int idcaja_cierre;
    private double suma_ingreso;
    private double suma_egreso;
    private double suma_saldo;

    private void abrir_formulario() {
        creado_por = ENTusu.getGlobal_nombre();
        this.setTitle(nombreTabla + " USUARIO:" + creado_por);
        evetbl.centrar_formulario_internalframa(this);
        idcaja_cierre = (eveconn.getInt_ultimoID_mas_uno(conn, ENTcc.getTb_caja_cierre(), ENTcc.getId_idcaja_cierre()));
        fk_idusuario = ENTusu.getGlobal_idusuario();
        evefec.cargar_combobox_intervalo_fecha(cmbfecha_caja_cierre);
        DAOusu.cargar_usuario_combo(conn, cmbusuario);
        actualizar_tabla_caja_cierre_detalle_ABIERTO();
        actualizar_tabla_caja_cierre();
    }
    private void actualizar_tabla_caja_cierre_detalle_ABIERTO() {
        DAOccd.actualizar_tabla_caja_cierre_detalle_ABIERTO_VENTA(conn, tblcaja_abierto, fk_idusuario);
        DAOccd.actualizar_tabla_caja_cierre_detalle_ABIERTO_GASTO(conn, tblgasto_abierto, fk_idusuario);
        DAOccd.actualizar_tabla_caja_cierre_detalle_ABIERTO_COMPRA(conn, tblcompra_abierto, fk_idusuario);
        suma_total_caja_detalle_abierto(fk_idusuario);
    }

    private void actualizar_tabla_caja_cierre() {
        String filtro="";
        int idusuario = DAOusu.getInt_idusuario_combo(conn, cmbusuario);
        String filtro_fec = evefec.getIntervalo_fecha_combobox(cmbfecha_caja_cierre, "cc.fecha_creado");
        String filtro_usu="";
        if (idusuario > 0) {
            filtro =  " and cc.fk_idusuario=" + idusuario;
        }
        String filtro_truno="";
        if(jRturno_todo.isSelected()){
            filtro_truno="";
        }
        if(jRturno_manana.isSelected()){
            filtro_truno=" and cast(cc.fecha_inicio as time) > time '05:00:00' and cast(cc.fecha_inicio as time) < time '07:00:00' ";
        }
        if(jRturno_tarde.isSelected()){
            filtro_truno=" and cast(cc.fecha_inicio as time) > time '13:00:00' and cast(cc.fecha_inicio as time) < time '15:00:00' ";
        }
        if(jRturno_noche.isSelected()){
            filtro_truno=" and cast(cc.fecha_inicio as time) > time '21:00:00' and cast(cc.fecha_inicio as time) < time '23:00:00' ";
        }
        filtro=filtro_fec+filtro_usu+filtro_truno;
        DAOcc.actualizar_tabla_caja_cierre(conn, tblresumen_caja_cierre, filtro);
        suma_total_caja_detalle_cerrado(filtro);
    }

    private void suma_total_caja_detalle_abierto(int idusuario) {
        String titulo = "suma_total_caja_detalle_abierto";
        String sql = "select \n"
                + "sum(cd.monto_apertura_caja) as apertura_caja,\n"
                + "sum(cd.monto_solo_adelanto) as adelanto,\n"
                + "sum(cd.monto_ocupa_minimo) as minimo,\n"
                + "sum(cd.monto_ocupa_adicional) as adicional,\n"
                + "sum(cd.monto_ocupa_consumo) as consumo,\n"
                + "sum(cd.monto_gasto) as gasto,\n"
                + "sum(cd.monto_compra) as compra,\n"
                + "sum((0-(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto))) as descuento,\n"
                + "sum(((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto))) as ingreso, \n"
                + "sum(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion) as egreso,\n"
                + "sum(((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto))-\n"
                + "(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion)) as saldo,\n"
                + "to_char(min(fecha_creado),'yyyy-MM-dd HH24:MI:ss') as fec_inicio, "
                + "to_char(max(fecha_creado),'yyyy-MM-dd HH24:MI:ss') as fec_final "
                + "from caja_cierre_detalle cd\n"
                + "where cd.es_cerrado=false \n"
                + "and cd.estado='EMITIDO'\n"
                + "and cd.fk_idusuario=" + idusuario;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                double adelanto = rs.getDouble("adelanto");
                double minimo = rs.getDouble("minimo");
                double adicional = rs.getDouble("adicional");
                double consumo = rs.getDouble("consumo");
                double descuento = rs.getDouble("descuento");
                suma_ingreso = rs.getDouble("ingreso");
                double gasto = rs.getDouble("gasto");
                double compra = rs.getDouble("compra");
                suma_egreso = rs.getDouble("egreso");
                suma_saldo = rs.getDouble("saldo");
                double apertura_caja = rs.getDouble("apertura_caja");
                jFtotal_ocupa_adelanto.setValue(adelanto);
                jFtotal_ocupa_minimo.setValue(minimo);
                jFtotal_ocupa_adicional.setValue(adicional);
                jFtotal_ocupa_consumo.setValue(consumo);
                jFtotal_ocupa_descuento.setValue(descuento);
                jFtotal_ocupa_ingreso.setValue(suma_ingreso);
                jFtotal_resumen_ingreso.setValue(suma_ingreso);
                jFtotal_resumen_egreso.setValue(suma_egreso);
                jFtotal_resumen_saldo.setValue(suma_saldo);
                jFmonto_apertura_caja.setValue(apertura_caja);
                fec_inicio = rs.getString("fec_inicio");
                txtfecha_inicio.setText(fec_inicio);
                fec_final = rs.getString("fec_final");
                txtfecha_final.setText(fec_final);
                jFtotal_gasto.setValue(gasto);
                jFtotal_compra.setValue(compra);
            }

        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }
    private void bloqueo_inicio() {
        FrmMenuMotel.barra_menu_principal.setEnabled(false);
    }
    private void abrir_login() {
        bloqueo_inicio();
        JDiaLogin log = new JDiaLogin(null, true);
        log.setVisible(true);
    }
    private void boton_cerrar_caja() {
        if (tblcaja_abierto.getRowCount() > 0) {
            String mensaje = "<html><p style=\"color:red\"><font size=\"8\">CAJA NRO: " + idcaja_cierre + "</font></p>"
                    + "<p style=\"color:blue\"><font size=\"5\">" + creado_por + "</font></p>"
                    + "<p style=\"color:red\"><font size=\"4\">=>SUMA INGRESO:</font></p>"
                    + "<p><font size=\"6\">" + evejtf.getString_format_nro_decimal(suma_ingreso) + "</font></p>"
                    + "<p style=\"color:red\"><font size=\"4\">=>SUMA EGRESO:</font></p>"
                    + "<p><font size=\"6\">" + evejtf.getString_format_nro_decimal(suma_egreso) + "</font></p>"
                    + "<p><font size=\"4\">====================</font></p>"
                    + "<p style=\"color:red\"><font size=\"4\">SUMA SALDO:   </font></p>"
                    + "<p><font size=\"8\">" + evejtf.getString_format_nro_decimal(suma_saldo) + "</font></p>"
                    + "</html>";
            if (evemen.getBooMensaje_warning(mensaje,"CERRAR CAJA", "ACEPTAR", "CANCELAR")) {
                ENTcc.setC3creado_por(creado_por);
                ENTcc.setC4fecha_inicio(fec_inicio);
                ENTcc.setC5fecha_fin(fec_final);
                ENTcc.setC6estado(eveest.getEst_Cerrado());
                ENTcc.setC7fk_idusuario(fk_idusuario);
                BOcc.insertar_caja_cierre(ENTcc);
//                actualizar_tabla_caja_cierre_detalle_ABIERTO();
//                actualizar_tabla_caja_cierre();
                if (evemen.getBooMensaje_question("DESEA IMPRIMIR EL REPORTE DE CIERRE DE CAJA ", "IMPRIMIR CAJA", "IMPRIMIR", "CANCELAR")) {
                    select_imprimir_caja_cierre(ENTcc.getC1idcaja_cierre());
                    this.dispose();
                    abrir_login();
                }
            }
        }
    }

    private void seleccionar_caja_cierre() {
        if (tblresumen_caja_cierre.getSelectedRow() >= 0) {
            int idcaja_cierre = eveJtab.getInt_select_id(tblresumen_caja_cierre);
            String filtro_gasto="";
            String filtro_compra="";
            if(jCsolo_terminado_gasto.isSelected()){
                filtro_gasto=" and g.estado='"+eveest.getEst_Terminar()+"' ";
            }
            if(jCsolo_terminado_comp.isSelected()){
                filtro_compra=" and c.estado='"+eveest.getEst_Terminar()+"' ";
            }
            DAOven.actualizar_tabla_venta_desde_caja_cierre(conn, tblventa, idcaja_cierre);
            DAOven.actualizar_tabla_venta_item_desde_caja_cierre(conn, tblventa_consumo, idcaja_cierre);
            DAOg.actualizar_tabla_gasto_caja_cerrado(conn, tblcc_gasto, idcaja_cierre,filtro_gasto);
            DAOcom.actualizar_tabla_compra_caja_cerrado(conn, tblcc_compra, idcaja_cierre,filtro_compra);
            double total_consumo = eveJtab.getDouble_sumar_tabla(tblventa_consumo, 5);
            double total_minimo = eveJtab.getDouble_sumar_tabla(tblventa, 12);
            double total_adicional = eveJtab.getDouble_sumar_tabla(tblventa, 13);
            double total_descuento = eveJtab.getDouble_sumar_tabla(tblventa, 14);
            double total_gasto = eveJtab.getDouble_sumar_tabla(tblcc_gasto, 7);
            double total_compra = eveJtab.getDouble_sumar_tabla(tblcc_compra, 7);
            jFtotal_cc_consumo.setValue(total_consumo);
            jFtotal_cc_minimo.setValue(total_minimo);
            jFtotal_cc_adicional.setValue(total_adicional);
            jFtotal_cc_gasto.setValue(total_gasto);
            jFtotal_cc_compra.setValue(total_compra);
        }
    }

    private void select_imprimir_caja_cierre(int idcaja_cierre) {
        Object[] botones = {"TICKET RESUMEN", "TICKET DETALLE", "REPORTE A4", "CANCELAR"};
        int eleccion_comando = JOptionPane.showOptionDialog(null, "SELECCIONA UNA PARA IMPRIMIR ",
                "CIERRE CAJA",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, botones, "TICKET DETALLE");
        if (eleccion_comando == 0) {
            poscd.boton_imprimir_pos_CAJA_DETALLE(conn, idcaja_cierre, false);
        }
        if (eleccion_comando == 1) {
            poscd.boton_imprimir_pos_CAJA_DETALLE(conn, idcaja_cierre, true);
        }
        if (eleccion_comando == 2) {
//            DAOcc.imprimir_caja_cierre_jasper(conn, idcaja_cierre);
            DAOcc.imprimir_caja_cierre_jasper_resumen(conn, idcaja_cierre);
        }
    }

    private void boton_imprimir_caja_cierre() {
        if (eveJtab.getBoolean_select_tabla_mensaje(tblresumen_caja_cierre, "SELECCIONAR LA TABLA CAJA CIERRE")) {
            int idcaja_cierre = eveJtab.getInt_select_id(tblresumen_caja_cierre);
            select_imprimir_caja_cierre(idcaja_cierre);
        }
    }

    private void suma_total_caja_detalle_cerrado(String filtro) {
        String titulo = "suma_total_caja_detalle_abierto";
        String sql = "select \n"
                + "sum((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)) as ingreso,\n"
                + "sum(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion) as egreso,\n"
                + "sum(((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto))-\n"
                + "(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion)) as saldo\n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci,caja_cierre cc \n"
                + "where cci.fk_idcaja_cierre_detalle=cd.idcaja_cierre_detalle \n"
                + "and cd.es_cerrado=true \n"
                + "and cci.fk_idcaja_cierre=cc.idcaja_cierre \n" + filtro
                + " ";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                double ingreso = rs.getDouble("ingreso");
                double egreso = rs.getDouble("egreso");
                double saldo = rs.getDouble("saldo");
                jFtotal_ingreso.setValue(ingreso);
                jFtotal_egreso.setValue(egreso);
                jFtotal_saldo.setValue(saldo);
            }

        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    private void boton_imprimir_ticket_venta(JTable tblventa) {
        if (eveJtab.getBoolean_select_tabla_mensaje(tblventa, "SELECCIONAR LA TABLA DE VENTA")) {
            int idventa = eveJtab.getInt_select_id(tblventa);
            posv.boton_imprimir_pos_VENTA(conn, idventa);
        }
    }

    public FrmCaja_Detalle() {
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

        gru_turno = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txtfecha_inicio = new javax.swing.JTextField();
        txtfecha_final = new javax.swing.JTextField();
        btncarrar_caja = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblcaja_abierto = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jFtotal_ocupa_minimo = new javax.swing.JFormattedTextField();
        jFtotal_ocupa_adelanto = new javax.swing.JFormattedTextField();
        jFtotal_ocupa_adicional = new javax.swing.JFormattedTextField();
        jFtotal_ocupa_consumo = new javax.swing.JFormattedTextField();
        jFtotal_ocupa_descuento = new javax.swing.JFormattedTextField();
        jFtotal_ocupa_ingreso = new javax.swing.JFormattedTextField();
        btnimprimir_ticket = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblgasto_abierto = new javax.swing.JTable();
        jFtotal_gasto = new javax.swing.JFormattedTextField();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblcompra_abierto = new javax.swing.JTable();
        jFtotal_compra = new javax.swing.JFormattedTextField();
        jPanel11 = new javax.swing.JPanel();
        jFtotal_resumen_ingreso = new javax.swing.JFormattedTextField();
        jFtotal_resumen_egreso = new javax.swing.JFormattedTextField();
        jFtotal_resumen_saldo = new javax.swing.JFormattedTextField();
        jFmonto_apertura_caja = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblresumen_caja_cierre = new javax.swing.JTable();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblventa = new javax.swing.JTable();
        btnimprimir_ticket1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblventa_consumo = new javax.swing.JTable();
        jFtotal_cc_consumo = new javax.swing.JFormattedTextField();
        jFtotal_cc_minimo = new javax.swing.JFormattedTextField();
        jFtotal_cc_adicional = new javax.swing.JFormattedTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblcc_gasto = new javax.swing.JTable();
        jFtotal_cc_gasto = new javax.swing.JFormattedTextField();
        jCsolo_terminado_gasto = new javax.swing.JCheckBox();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblcc_compra = new javax.swing.JTable();
        jFtotal_cc_compra = new javax.swing.JFormattedTextField();
        jCsolo_terminado_comp = new javax.swing.JCheckBox();
        btnimprimir_caja_cierre = new javax.swing.JButton();
        cmbfecha_caja_cierre = new javax.swing.JComboBox<>();
        cmbusuario = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jFtotal_ingreso = new javax.swing.JFormattedTextField();
        jFtotal_egreso = new javax.swing.JFormattedTextField();
        jFtotal_saldo = new javax.swing.JFormattedTextField();
        jPanel15 = new javax.swing.JPanel();
        jRturno_todo = new javax.swing.JRadioButton();
        jRturno_manana = new javax.swing.JRadioButton();
        jRturno_tarde = new javax.swing.JRadioButton();
        jRturno_noche = new javax.swing.JRadioButton();

        setClosable(true);
        setIconifiable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
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

        txtfecha_inicio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_inicio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtfecha_inicio.setText("jTextField1");
        txtfecha_inicio.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA INICIO"));

        txtfecha_final.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_final.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtfecha_final.setText("jTextField1");
        txtfecha_final.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA FINAL"));

        btncarrar_caja.setText("CERRAR CAJA");
        btncarrar_caja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncarrar_cajaActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("CAJA ABIERTO"));

        tblcaja_abierto.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblcaja_abierto);

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESOS"));

        jFtotal_ocupa_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL MINIMO"));
        jFtotal_ocupa_minimo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_ocupa_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ocupa_minimo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jFtotal_ocupa_adelanto.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL ADELANTO"));
        jFtotal_ocupa_adelanto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_ocupa_adelanto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ocupa_adelanto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jFtotal_ocupa_adicional.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL ADICIONAL"));
        jFtotal_ocupa_adicional.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_ocupa_adicional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ocupa_adicional.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jFtotal_ocupa_consumo.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL CONSUMO"));
        jFtotal_ocupa_consumo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_ocupa_consumo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ocupa_consumo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jFtotal_ocupa_descuento.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL DESCUENTO"));
        jFtotal_ocupa_descuento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_ocupa_descuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ocupa_descuento.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jFtotal_ocupa_ingreso.setBackground(new java.awt.Color(255, 255, 102));
        jFtotal_ocupa_ingreso.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL INGRESO"));
        jFtotal_ocupa_ingreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_ocupa_ingreso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ocupa_ingreso.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        btnimprimir_ticket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ult_print.png"))); // NOI18N
        btnimprimir_ticket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_ticketActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jFtotal_ocupa_adelanto, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jFtotal_ocupa_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_ocupa_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_ocupa_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_ocupa_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_ocupa_ingreso, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addComponent(btnimprimir_ticket, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jFtotal_ocupa_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jFtotal_ocupa_adelanto, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jFtotal_ocupa_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jFtotal_ocupa_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jFtotal_ocupa_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jFtotal_ocupa_ingreso, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(btnimprimir_ticket, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("HABITACION", jPanel8);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("CAJA GASTO"));

        tblgasto_abierto.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblgasto_abierto);

        jFtotal_gasto.setBackground(new java.awt.Color(255, 255, 102));
        jFtotal_gasto.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL GASTO"));
        jFtotal_gasto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_gasto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_gasto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 935, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addComponent(jFtotal_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jFtotal_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("GASTO", jPanel9);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("CAJA COMPRA"));

        tblcompra_abierto.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(tblcompra_abierto);

        jFtotal_compra.setBackground(new java.awt.Color(255, 255, 102));
        jFtotal_compra.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL COMPRA"));
        jFtotal_compra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_compra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_compra.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 935, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addComponent(jFtotal_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jFtotal_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("COMPRA", jPanel12);

        jPanel11.setBackground(new java.awt.Color(204, 204, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("RESUMEN SALDO"));

        jFtotal_resumen_ingreso.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL INGRESO"));
        jFtotal_resumen_ingreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_resumen_ingreso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_resumen_ingreso.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jFtotal_resumen_egreso.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL EGRESO"));
        jFtotal_resumen_egreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_resumen_egreso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_resumen_egreso.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jFtotal_resumen_saldo.setBackground(new java.awt.Color(255, 255, 153));
        jFtotal_resumen_saldo.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL SALDO"));
        jFtotal_resumen_saldo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_resumen_saldo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_resumen_saldo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jFtotal_resumen_ingreso, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_resumen_egreso, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_resumen_saldo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jFtotal_resumen_ingreso, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jFtotal_resumen_egreso, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jFtotal_resumen_saldo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jFmonto_apertura_caja.setBorder(javax.swing.BorderFactory.createTitledBorder("APERTURA CAJA"));
        jFmonto_apertura_caja.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_apertura_caja.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_apertura_caja.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtfecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfecha_final, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncarrar_caja, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFmonto_apertura_caja, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtfecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtfecha_final, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btncarrar_caja, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFmonto_apertura_caja, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("DETALLE CAJA ABIERTO", jPanel1);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("RESUMEN CAJA CERRADO"));

        tblresumen_caja_cierre.setModel(new javax.swing.table.DefaultTableModel(
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
        tblresumen_caja_cierre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblresumen_caja_cierreMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblresumen_caja_cierre);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
        );

        tblventa.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblventa);

        btnimprimir_ticket1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ult_print.png"))); // NOI18N
        btnimprimir_ticket1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_ticket1ActionPerformed(evt);
            }
        });

        tblventa_consumo.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblventa_consumo);

        jFtotal_cc_consumo.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL CONSUMO"));
        jFtotal_cc_consumo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_cc_consumo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_cc_consumo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jFtotal_cc_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL MINIMO"));
        jFtotal_cc_minimo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_cc_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_cc_minimo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jFtotal_cc_adicional.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL ADICIONAL"));
        jFtotal_cc_adicional.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_cc_adicional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_cc_adicional.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 813, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jFtotal_cc_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_cc_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnimprimir_ticket1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                        .addComponent(jFtotal_cc_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnimprimir_ticket1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFtotal_cc_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFtotal_cc_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFtotal_cc_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("DETALLE OCUPACION", jPanel6);

        tblcc_gasto.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(tblcc_gasto);

        jFtotal_cc_gasto.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL GASTO"));
        jFtotal_cc_gasto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_cc_gasto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_cc_gasto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jCsolo_terminado_gasto.setText("SOLO TERMINADO");
        jCsolo_terminado_gasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCsolo_terminado_gastoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(852, Short.MAX_VALUE)
                .addComponent(jCsolo_terminado_gasto)
                .addGap(271, 271, 271))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jFtotal_cc_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 813, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCsolo_terminado_gasto)
                .addContainerGap(350, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jFtotal_cc_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jTabbedPane2.addTab("GASTOS", jPanel7);

        tblcc_compra.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(tblcc_compra);

        jFtotal_cc_compra.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL COMPRA"));
        jFtotal_cc_compra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_cc_compra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_cc_compra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jCsolo_terminado_comp.setText("SOLO TERMINADO");
        jCsolo_terminado_comp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCsolo_terminado_compActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(845, Short.MAX_VALUE)
                .addComponent(jCsolo_terminado_comp)
                .addGap(278, 278, 278))
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 813, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFtotal_cc_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCsolo_terminado_comp)
                .addContainerGap(350, Short.MAX_VALUE))
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jFtotal_cc_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jTabbedPane2.addTab("COMPRA", jPanel14);

        btnimprimir_caja_cierre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_imprimir.png"))); // NOI18N
        btnimprimir_caja_cierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_caja_cierreActionPerformed(evt);
            }
        });

        cmbfecha_caja_cierre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbfecha_caja_cierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbfecha_caja_cierreActionPerformed(evt);
            }
        });

        cmbusuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbusuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbusuarioActionPerformed(evt);
            }
        });

        jLabel1.setText("Fecha:");

        jLabel2.setText("Usuario:");

        jFtotal_ingreso.setEditable(false);
        jFtotal_ingreso.setBackground(new java.awt.Color(255, 255, 204));
        jFtotal_ingreso.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL INGRESO"));
        jFtotal_ingreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_ingreso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ingreso.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jFtotal_egreso.setEditable(false);
        jFtotal_egreso.setBackground(new java.awt.Color(255, 255, 204));
        jFtotal_egreso.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL EGRESO"));
        jFtotal_egreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_egreso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_egreso.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jFtotal_saldo.setEditable(false);
        jFtotal_saldo.setBackground(new java.awt.Color(255, 255, 204));
        jFtotal_saldo.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL SALDO"));
        jFtotal_saldo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_saldo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_saldo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("TURNO"));

        gru_turno.add(jRturno_todo);
        jRturno_todo.setSelected(true);
        jRturno_todo.setText("TODO");
        jRturno_todo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRturno_todoActionPerformed(evt);
            }
        });

        gru_turno.add(jRturno_manana);
        jRturno_manana.setText("MAÑANA");
        jRturno_manana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRturno_mananaActionPerformed(evt);
            }
        });

        gru_turno.add(jRturno_tarde);
        jRturno_tarde.setText("TARDE");
        jRturno_tarde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRturno_tardeActionPerformed(evt);
            }
        });

        gru_turno.add(jRturno_noche);
        jRturno_noche.setText("NOCHE");
        jRturno_noche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRturno_nocheActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRturno_todo)
                    .addComponent(jRturno_tarde))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRturno_noche)
                    .addComponent(jRturno_manana))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRturno_todo)
                    .addComponent(jRturno_manana))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRturno_tarde)
                    .addComponent(jRturno_noche)))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jFtotal_ingreso, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbfecha_caja_cierre, javax.swing.GroupLayout.Alignment.LEADING, 0, 159, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFtotal_egreso, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFtotal_saldo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbusuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnimprimir_caja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jTabbedPane2)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbfecha_caja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnimprimir_caja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jFtotal_ingreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFtotal_egreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFtotal_saldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2))
        );

        jTabbedPane1.addTab("CAJA CERRADO", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        DAOccd.ancho_tabla_caja_cierre_detalle_ABIERTO(tblcaja_abierto);
        DAOccd.ancho_tabla_caja_cierre_detalle_ABIERTO_EGRESO(tblgasto_abierto);
        DAOccd.ancho_tabla_caja_cierre_detalle_ABIERTO_EGRESO(tblcompra_abierto);
        DAOcc.ancho_tabla_caja_cierre(tblresumen_caja_cierre);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btncarrar_cajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncarrar_cajaActionPerformed
        // TODO add your handling code here:
        boton_cerrar_caja();
    }//GEN-LAST:event_btncarrar_cajaActionPerformed

    private void tblresumen_caja_cierreMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblresumen_caja_cierreMouseReleased
        // TODO add your handling code here:
        jCsolo_terminado_gasto.setSelected(true);
        jCsolo_terminado_comp.setSelected(true);
        seleccionar_caja_cierre();
    }//GEN-LAST:event_tblresumen_caja_cierreMouseReleased

    private void btnimprimir_caja_cierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_caja_cierreActionPerformed
        // TODO add your handling code here:
        boton_imprimir_caja_cierre();
    }//GEN-LAST:event_btnimprimir_caja_cierreActionPerformed

    private void cmbfecha_caja_cierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbfecha_caja_cierreActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_caja_cierre();
    }//GEN-LAST:event_cmbfecha_caja_cierreActionPerformed

    private void cmbusuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbusuarioActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_caja_cierre();
    }//GEN-LAST:event_cmbusuarioActionPerformed

    private void btnimprimir_ticketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_ticketActionPerformed
        // TODO add your handling code here:
        boton_imprimir_ticket_venta(tblcaja_abierto);
    }//GEN-LAST:event_btnimprimir_ticketActionPerformed

    private void btnimprimir_ticket1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_ticket1ActionPerformed
        // TODO add your handling code here:
        boton_imprimir_ticket_venta(tblventa);
    }//GEN-LAST:event_btnimprimir_ticket1ActionPerformed

    private void jCsolo_terminado_gastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCsolo_terminado_gastoActionPerformed
        // TODO add your handling code here:
        seleccionar_caja_cierre();
    }//GEN-LAST:event_jCsolo_terminado_gastoActionPerformed

    private void jCsolo_terminado_compActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCsolo_terminado_compActionPerformed
        // TODO add your handling code here:
        seleccionar_caja_cierre();
    }//GEN-LAST:event_jCsolo_terminado_compActionPerformed

    private void jRturno_todoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRturno_todoActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_caja_cierre();
    }//GEN-LAST:event_jRturno_todoActionPerformed

    private void jRturno_mananaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRturno_mananaActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_caja_cierre();
    }//GEN-LAST:event_jRturno_mananaActionPerformed

    private void jRturno_tardeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRturno_tardeActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_caja_cierre();
    }//GEN-LAST:event_jRturno_tardeActionPerformed

    private void jRturno_nocheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRturno_nocheActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_caja_cierre();
    }//GEN-LAST:event_jRturno_nocheActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncarrar_caja;
    private javax.swing.JButton btnimprimir_caja_cierre;
    private javax.swing.JButton btnimprimir_ticket;
    private javax.swing.JButton btnimprimir_ticket1;
    private javax.swing.JComboBox<String> cmbfecha_caja_cierre;
    private javax.swing.JComboBox<String> cmbusuario;
    private javax.swing.ButtonGroup gru_turno;
    private javax.swing.JCheckBox jCsolo_terminado_comp;
    private javax.swing.JCheckBox jCsolo_terminado_gasto;
    private javax.swing.JFormattedTextField jFmonto_apertura_caja;
    private javax.swing.JFormattedTextField jFtotal_cc_adicional;
    private javax.swing.JFormattedTextField jFtotal_cc_compra;
    private javax.swing.JFormattedTextField jFtotal_cc_consumo;
    private javax.swing.JFormattedTextField jFtotal_cc_gasto;
    private javax.swing.JFormattedTextField jFtotal_cc_minimo;
    private javax.swing.JFormattedTextField jFtotal_compra;
    private javax.swing.JFormattedTextField jFtotal_egreso;
    private javax.swing.JFormattedTextField jFtotal_gasto;
    private javax.swing.JFormattedTextField jFtotal_ingreso;
    private javax.swing.JFormattedTextField jFtotal_ocupa_adelanto;
    private javax.swing.JFormattedTextField jFtotal_ocupa_adicional;
    private javax.swing.JFormattedTextField jFtotal_ocupa_consumo;
    private javax.swing.JFormattedTextField jFtotal_ocupa_descuento;
    private javax.swing.JFormattedTextField jFtotal_ocupa_ingreso;
    private javax.swing.JFormattedTextField jFtotal_ocupa_minimo;
    private javax.swing.JFormattedTextField jFtotal_resumen_egreso;
    private javax.swing.JFormattedTextField jFtotal_resumen_ingreso;
    private javax.swing.JFormattedTextField jFtotal_resumen_saldo;
    private javax.swing.JFormattedTextField jFtotal_saldo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRturno_manana;
    private javax.swing.JRadioButton jRturno_noche;
    private javax.swing.JRadioButton jRturno_tarde;
    private javax.swing.JRadioButton jRturno_todo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable tblcaja_abierto;
    private javax.swing.JTable tblcc_compra;
    private javax.swing.JTable tblcc_gasto;
    private javax.swing.JTable tblcompra_abierto;
    private javax.swing.JTable tblgasto_abierto;
    private javax.swing.JTable tblresumen_caja_cierre;
    private javax.swing.JTable tblventa;
    private javax.swing.JTable tblventa_consumo;
    private javax.swing.JTextField txtfecha_final;
    private javax.swing.JTextField txtfecha_inicio;
    // End of variables declaration//GEN-END:variables
}
