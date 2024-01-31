/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.RRHH;

import FORMULARIO.VISTA.*;
import BASEDATO.LOCAL.ConnPostgres;
import ESTADOS.EvenEstado;
import Evento.Fecha.EvenFecha;
//import Evento.Color.cla_color_palete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenNumero_a_Letra;
import FORMULARIO.BO.BO_gasto_tipo;
import FORMULARIO.BO.BO_rh_descuento;
import FORMULARIO.BO.BO_rh_liquidacion;
import FORMULARIO.BO.BO_rh_vale;
import FORMULARIO.DAO.DAO_caja_cierre;
import FORMULARIO.DAO.DAO_gasto_tipo;
import FORMULARIO.DAO.DAO_persona;
import FORMULARIO.DAO.DAO_rh_liquidacion;
import FORMULARIO.DAO.DAO_rh_vale;
import FORMULARIO.ENTIDAD.caja_cierre_detalle;
import FORMULARIO.ENTIDAD.gasto_tipo;
import FORMULARIO.ENTIDAD.persona;
import FORMULARIO.ENTIDAD.rh_descuento;
import FORMULARIO.ENTIDAD.rh_liquidacion;
import FORMULARIO.ENTIDAD.rh_liquidacion_detalle;
import FORMULARIO.ENTIDAD.rh_vale;
import FORMULARIO.ENTIDAD.usuario;
import IMPRESORA_POS.PosImprimir_vale;
//import FORMULARIO.BO.*;
//import FORMULARIO.DAO.*;
//import FORMULARIO.ENTIDAD.*;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmRH_liquidacion extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private rh_liquidacion ENTrhli = new rh_liquidacion();
    private rh_liquidacion ENTrhli2 = new rh_liquidacion();
    private DAO_rh_liquidacion DAOrhli = new DAO_rh_liquidacion();
    private BO_rh_liquidacion BOrhli = new BO_rh_liquidacion();
    private DAO_persona DAOper = new DAO_persona();
    private persona ENTper = new persona();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenEstado eveest = new EvenEstado();
    private EvenNumero_a_Letra evenrolt = new EvenNumero_a_Letra();
    private PosImprimir_vale posvale = new PosImprimir_vale();
    private EvenMensajeJoptionpane evemsj = new EvenMensajeJoptionpane();
    private EvenFecha evefec = new EvenFecha();
    private DAO_rh_vale DAOrhv = new DAO_rh_vale();
    private rh_vale ENTrhv = new rh_vale();
    private BO_rh_vale BOrhv = new BO_rh_vale();
    private rh_descuento ENTrhd = new rh_descuento();
    private BO_rh_descuento BOrhd = new BO_rh_descuento();
    private rh_liquidacion_detalle ENTrhlde = new rh_liquidacion_detalle();
    private caja_cierre_detalle ENTccd = new caja_cierre_detalle();
    private DAO_caja_cierre DAOcc = new DAO_caja_cierre();
    Connection conn = ConnPostgres.getConnPosgres();
    private usuario ENTusu = new usuario();
    private String nombreTabla = "RH LIQUIDACION";
    private String creado_por = "digno";
    private int idpersona;
    private int idrh_liquidacion;
    private int idrh_liquidacion_abierto;
    private int fk_idusuario;

    private void abrir_formulario() {
        this.setTitle(nombreTabla);
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        DAOrhli.actualizar_tabla_rh_liquidacion(conn, tblliquidacion);
        DAOper.actualizar_tabla_persona_liquida(conn, tblpersonal);
    }
//    private void color_formulario(){
//        panel_tabla.setBackground(clacolor.getColor_tabla());
//        panel_insertar.setBackground(clacolor.getColor_insertar_primario());
//    }

    private boolean validar_guardar() {
//        if (evejtf.getBoo_JTextField_vacio(txtfecha_desde, "DEBE CARGAR UN NOMBRE")) {
//            return false;
//        }

        return true;
    }

    private void cargar_dato_liquidacion_cerrar() {
        idrh_liquidacion = DAOrhli.getInt_idrh_liquidacion_rh_liquidacion_abierto(conn, idpersona);
        DAOrhli.cargar_rh_liquidacion(conn, ENTrhli, idrh_liquidacion);
        String fecha = evefec.getfechaDCStringFormat(dcfecha_hasta, "yyyy-MM-dd");
        double liquidacion = ENTrhli.getC11salario_base() - (ENTrhli.getSum_vale() + ENTrhli.getSum_descuento());
        int Imonto = (int) liquidacion;
        String Smonto = String.valueOf(Imonto);
        String monto_letra = evenrolt.Convertir(Smonto, true);
        ENTrhli.setC1idrh_liquidacion(idrh_liquidacion);
        ENTrhli.setC5fecha_hasta(fecha);
        ENTrhli.setC6estado(eveest.getEst_Cerrado());
        ENTrhli.setC7es_cerrado(true);
        ENTrhli.setC8monto_vale(ENTrhli.getSum_vale());
        ENTrhli.setC9monto_descuento(ENTrhli.getSum_descuento());
        ENTrhli.setC10monto_liquidacion(liquidacion);
        ENTrhli.setC11salario_base(ENTrhli.getC11salario_base());
        ENTrhli.setC12monto_letra(monto_letra);
        ENTrhli.setC13fk_idpersona(idpersona);
        ENTrhli.setC14descripcion(txtdescripcion.getText());
    }

    private void cargar_dato_caja_detalle() {
        double liquidacion = ENTrhli.getC11salario_base() - (ENTrhli.getSum_vale() + ENTrhli.getSum_descuento());
        ENTccd.setC3creado_por(creado_por);
        ENTccd.setC4cerrado_por(eveest.getCaja_LIQUIDACION());
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
        ENTccd.setC16monto_liquidacion(liquidacion);
        ENTccd.setC17estado(eveest.getEst_Emitido());
        ENTccd.setC18descripcion(txtdescripcion.getText());
        ENTccd.setC19fk_idgasto(0);
        ENTccd.setC20fk_idcompra(0);
        ENTccd.setC21fk_idventa(0);
        ENTccd.setC22fk_idusuario(fk_idusuario);
        ENTccd.setC23fk_idrh_vale(0);
        ENTccd.setC24fk_idrh_liquidacion(idrh_liquidacion);
        ENTccd.setC25monto_solo_adelanto(0);
        ENTccd.setC26monto_interno(0);
        ENTccd.setC27fk_idventa_interno(0);
        ENTccd.setC28monto_garantia(0);
        ENTccd.setC29fk_idgarantia(0);
    }

    private void cargar_dato_liquidacion_nuevo() {
        ENTrhli2.setC3creado_por(creado_por);
        ENTrhli2.setC6estado(eveest.getEst_ABIERTO());
        ENTrhli2.setC7es_cerrado(false);
        ENTrhli2.setC8monto_vale(0);
        ENTrhli2.setC9monto_descuento(0);
        ENTrhli2.setC10monto_liquidacion(0);
        ENTrhli2.setC11salario_base(ENTper.getC10salario_base());
        ENTrhli2.setC12monto_letra("cero");
        ENTrhli2.setC13fk_idpersona(idpersona);
        ENTrhli2.setC14descripcion("PAGO DE SALARIO");
    }

    private void boton_cerrar_liquidacion(boolean caja) {
        if (validar_guardar()) {
            cargar_dato_liquidacion_cerrar();
            cargar_dato_liquidacion_nuevo();
            if (caja) {
                cargar_dato_caja_detalle();
                BOrhli.update_rh_liquidacion_cerrar_caja(ENTrhli, ENTrhli2, ENTccd);
            } else {
                BOrhli.update_rh_liquidacion_cerrar(ENTrhli, ENTrhli2);
            }
            reestableser();
            DAOrhli.actualizar_tabla_rh_liquidacion(conn, tblliquidacion);
            cargar_liquidacion_abierto();
            DAOrhli.imprimir_rh_liquidacion(conn, idrh_liquidacion);
            DAOcc.exportar_excel_liquidacion_vale_N3(conn);
        }
    }

    private void seleccionar_tabla() {
        int idrh_liquidacion = eveJtab.getInt_select_id(tblliquidacion);
        DAOrhli.cargar_rh_liquidacion(conn, ENTrhli, idrh_liquidacion);
//        txtid.setText(String.valueOf(ENTrhli.getC1idrh_liquidacion()));
        this.setTitle(nombreTabla + "/fecha creado:" + ENTrhli.getC2fecha_creado() + "/ Creado Por:" + ENTrhli.getC3creado_por());
//        btncerrar_liquidacion.setEnabled(false);
        DAOrhli.actualizar_tabla_rh_liquidacion_entrada(conn, tblentrada1, idrh_liquidacion);
        DAOrhli.actualizar_tabla_rh_liquidacion_vale(conn, tblvale1, idrh_liquidacion);
        DAOrhli.actualizar_tabla_rh_liquidacion_descuento(conn, tbldescuento1, idrh_liquidacion);
//        btneditar.setEnabled(true);
    }

    private void boton_imprimir_liquidacion() {
        if (eveJtab.getBoolean_validar_select_mensaje(tblliquidacion, "SELECCIONE UNA LIQUIDACION PARA IMPRIMIR")) {
            int idrh_liquidacion = eveJtab.getInt_select_id(tblliquidacion);
            DAOrhli.imprimir_rh_liquidacion(conn, idrh_liquidacion);
        }
    }

    private void seleccionar_tabla_personal() {
        idpersona = eveJtab.getInt_select_id(tblpersonal);
        ENTper.setIdpersona_global(idpersona);
        DAOper.cargar_persona(conn, ENTper, idpersona);
        txtpersona.setText(ENTper.getC4nombre());
        jfsalario_base.setValue(ENTper.getC10salario_base());
        cargar_liquidacion_abierto();

    }

    private void cargar_liquidacion_abierto() {
        idrh_liquidacion_abierto = DAOrhli.getInt_idrh_liquidacion_rh_liquidacion_abierto(conn, idpersona);
        DAOrhli.cargar_rh_liquidacion(conn, ENTrhli, idrh_liquidacion_abierto);
        txtid.setText(String.valueOf(ENTrhli.getC1idrh_liquidacion()));
        txtfecha_desde.setText(ENTrhli.getC4fecha_desde());
        txtestado.setText(ENTrhli.getC6estado());
        jfmonto_vale.setValue(ENTrhli.getSum_vale());
        jfmonto_descuento.setValue(ENTrhli.getSum_descuento());
        double liquidacion = ENTrhli.getC11salario_base() - (ENTrhli.getSum_vale() + ENTrhli.getSum_descuento());
        jfmonto_liquidacion.setValue(liquidacion);
        int Imonto = (int) liquidacion;
        String Smonto = String.valueOf(Imonto);
        String monto_letra = evenrolt.Convertir(Smonto, true);
        txtmonto_letra.setText(monto_letra);
        btnnuevo_vale.setEnabled(true);
        btnnuevo_descuento.setEnabled(true);
        DAOrhli.actualizar_tabla_rh_liquidacion_entrada(conn, tblentrada, idrh_liquidacion_abierto);
        DAOrhli.actualizar_tabla_rh_liquidacion_vale(conn, tblvale, idrh_liquidacion_abierto);
        DAOrhli.actualizar_tabla_rh_liquidacion_descuento(conn, tbldescuento, idrh_liquidacion_abierto);
    }

    private void reestableser() {
        this.setTitle(nombreTabla);
        txtid.setText(null);
        txtfecha_desde.setText(null);
        txtdescripcion.setText("PAGO DE SALARIO");
        evefec.setFechaDCSistema(dcfecha_hasta);
        btnnuevo_vale.setEnabled(false);
        btnnuevo_descuento.setEnabled(false);
        btncerrar_liquidacion_admin.setEnabled(true);
//        btneditar.setEnabled(false);
        txtfecha_desde.grabFocus();
    }

    private void boton_nuevo() {
        reestableser();
    }

    private void boton_anular_vale() {
        if (eveJtab.getBoolean_validar_select_mensaje(tblvale, "SELECCIONE UN VALE PARA ANULAR")) {
            int idrh_vale = eveJtab.getInt_select_id(tblvale);
            ENTrhv.setC5monto_vale(0);
            ENTrhv.setC6estado(eveest.getEst_Anulado());
            ENTrhv.setC8monto_letra("CERO");
            ENTrhv.setC1idrh_vale(idrh_vale);

            ENTrhlde.setC6monto_vale(0);
            ENTrhlde.setC8estado(eveest.getEst_Anulado());
            ENTrhlde.setC11fk_idrh_vale(idrh_vale);

            ENTccd.setC15monto_vale(0);
            ENTccd.setC17estado(eveest.getEst_Anulado());
            ENTccd.setC23fk_idrh_vale(idrh_vale);
            BOrhv.update_rh_vale_anular(ENTrhv, ENTrhlde, ENTccd);
            cargar_liquidacion_abierto();
        }

    }

    private void boton_anular_descuento() {
        if (eveJtab.getBoolean_validar_select_mensaje(tbldescuento, "SELECCIONE UN DESCUENTO PARA ANULAR")) {
            int idrh_descuento = eveJtab.getInt_select_id(tbldescuento);
            ENTrhd.setC5monto_descuento(0);
            ENTrhd.setC6estado(eveest.getEst_Anulado());
            ENTrhd.setC8monto_letra("CERO");
            ENTrhd.setC1idrh_descuento(idrh_descuento);

            ENTrhlde.setC5monto_descuento(0);
            ENTrhlde.setC8estado(eveest.getEst_Anulado());
            ENTrhlde.setC10fk_idrh_descuento(idrh_descuento);
            BOrhd.update_rh_descuento_anular(ENTrhd, ENTrhlde);
//            BOrhv.update_rh_vale_anular(ENTrhv,ENTrhlde);
            cargar_liquidacion_abierto();
        }

    }

    private void boton_imprimir_vale() {
        if (eveJtab.getBoolean_validar_select_mensaje(tblvale, "SELECCIONE UN VALE PARA IMPRIMIR")) {
            int mensaje = (evemsj.getIntMensaje_informacion_3btn("DESEA IMPRIMIR VALE", "VALE", "TICKET", "A4", "CANCELAR"));
            int idrh_vale = eveJtab.getInt_select_id(tblvale);
            if (mensaje == 0) {
                posvale.boton_imprimir_pos_vale(conn, idrh_vale);
            }
            if (mensaje == 1) {
                DAOrhv.imprimir_nota_rh_vale(conn, idrh_vale);
            }
        }
    }

    public FrmRH_liquidacion() {
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        panel_insertar = new javax.swing.JPanel();
        txtid = new javax.swing.JTextField();
        txtfecha_desde = new javax.swing.JTextField();
        btncerrar_liquidacion_admin = new javax.swing.JButton();
        dcfecha_hasta = new com.toedter.calendar.JDateChooser();
        txtestado = new javax.swing.JTextField();
        txtpersona = new javax.swing.JTextField();
        jfmonto_vale = new javax.swing.JFormattedTextField();
        jfmonto_descuento = new javax.swing.JFormattedTextField();
        jfmonto_liquidacion = new javax.swing.JFormattedTextField();
        jfsalario_base = new javax.swing.JFormattedTextField();
        txtmonto_letra = new javax.swing.JTextField();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblentrada = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblvale = new javax.swing.JTable();
        btnnuevo_vale = new javax.swing.JButton();
        btnimprimir_vale = new javax.swing.JButton();
        btnanular_vale = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbldescuento = new javax.swing.JTable();
        btnnuevo_descuento = new javax.swing.JButton();
        btnanular_descuento = new javax.swing.JButton();
        txtdescripcion = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblpersonal = new javax.swing.JTable();
        btncerrar_liquidacion_caja = new javax.swing.JButton();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblliquidacion = new javax.swing.JTable();
        btnimprimir_liquidacion = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblentrada1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblvale1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbldescuento1 = new javax.swing.JTable();

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

        panel_insertar.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtid.setBorder(javax.swing.BorderFactory.createTitledBorder("ID"));

        txtfecha_desde.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfecha_desde.setBorder(javax.swing.BorderFactory.createTitledBorder("FEC. DESDE:"));
        txtfecha_desde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_desdeKeyPressed(evt);
            }
        });

        btncerrar_liquidacion_admin.setBackground(new java.awt.Color(204, 255, 204));
        btncerrar_liquidacion_admin.setText("PAGAR POR ADMIN");
        btncerrar_liquidacion_admin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncerrar_liquidacion_admin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncerrar_liquidacion_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncerrar_liquidacion_adminActionPerformed(evt);
            }
        });

        dcfecha_hasta.setBorder(javax.swing.BorderFactory.createTitledBorder("FEC. HASTA:"));

        txtestado.setEditable(false);
        txtestado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtestado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtestado.setBorder(javax.swing.BorderFactory.createTitledBorder("ESTADO:"));

        txtpersona.setEditable(false);
        txtpersona.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtpersona.setBorder(javax.swing.BorderFactory.createTitledBorder("PERSONAL:"));

        jfmonto_vale.setEditable(false);
        jfmonto_vale.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMA VALE:"));
        jfmonto_vale.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jfmonto_vale.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jfmonto_descuento.setEditable(false);
        jfmonto_descuento.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMA DESCUENTO:"));
        jfmonto_descuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jfmonto_descuento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jfmonto_liquidacion.setEditable(false);
        jfmonto_liquidacion.setBorder(javax.swing.BorderFactory.createTitledBorder("LIQUIDACION:"));
        jfmonto_liquidacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jfmonto_liquidacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jfsalario_base.setBorder(javax.swing.BorderFactory.createTitledBorder("SALARIO BASE:"));
        jfsalario_base.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jfsalario_base.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtmonto_letra.setEditable(false);
        txtmonto_letra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtmonto_letra.setBorder(javax.swing.BorderFactory.createTitledBorder("LIQUIDACION LETRA:"));

        tblentrada.setModel(new javax.swing.table.DefaultTableModel(
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
        tblentrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblentradaMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblentrada);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 769, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 169, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("ENTRADA", jPanel1);

        tblvale.setModel(new javax.swing.table.DefaultTableModel(
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
        tblvale.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblvaleMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblvale);

        btnnuevo_vale.setText("NUEVO VALE");
        btnnuevo_vale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_valeActionPerformed(evt);
            }
        });

        btnimprimir_vale.setText("IMPRIMIR");
        btnimprimir_vale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_valeActionPerformed(evt);
            }
        });

        btnanular_vale.setBackground(new java.awt.Color(255, 51, 51));
        btnanular_vale.setText("ANULAR");
        btnanular_vale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanular_valeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(625, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnnuevo_vale, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(btnimprimir_vale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnanular_vale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 169, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnnuevo_vale)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnimprimir_vale)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnanular_vale)
                .addContainerGap(143, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("VALE", jPanel2);

        tbldescuento.setModel(new javax.swing.table.DefaultTableModel(
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
        tbldescuento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbldescuentoMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tbldescuento);

        btnnuevo_descuento.setText("DESCUENTO");
        btnnuevo_descuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_descuentoActionPerformed(evt);
            }
        });

        btnanular_descuento.setBackground(new java.awt.Color(255, 51, 51));
        btnanular_descuento.setText("ANULAR");
        btnanular_descuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanular_descuentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(615, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnnuevo_descuento, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .addComponent(btnanular_descuento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 169, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnnuevo_descuento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnanular_descuento)
                .addContainerGap(172, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("DESCUENTO", jPanel3);

        txtdescripcion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtdescripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCION"));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("PERSONAL"));

        tblpersonal.setModel(new javax.swing.table.DefaultTableModel(
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
        tblpersonal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblpersonalMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tblpersonal);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        btncerrar_liquidacion_caja.setBackground(new java.awt.Color(255, 255, 153));
        btncerrar_liquidacion_caja.setText("PAGAR POR CAJA");
        btncerrar_liquidacion_caja.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncerrar_liquidacion_caja.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncerrar_liquidacion_caja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncerrar_liquidacion_cajaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertarLayout.createSequentialGroup()
                                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtdescripcion, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtpersona, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_insertarLayout.createSequentialGroup()
                                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dcfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtestado, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jfsalario_base, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panel_insertarLayout.createSequentialGroup()
                                    .addComponent(txtmonto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(261, 261, 261))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jfmonto_vale, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jfmonto_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jfmonto_liquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncerrar_liquidacion_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btncerrar_liquidacion_caja, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 268, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertarLayout.createSequentialGroup()
                                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtpersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jfsalario_base, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(dcfecha_hasta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtestado)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane2))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmonto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jfmonto_vale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jfmonto_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jfmonto_liquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncerrar_liquidacion_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncerrar_liquidacion_caja, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("DATO", panel_insertar);

        panel_tabla.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblliquidacion.setModel(new javax.swing.table.DefaultTableModel(
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
        tblliquidacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblliquidacionMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblliquidacion);

        btnimprimir_liquidacion.setText("IMPRIMIR LIQUIDACION");
        btnimprimir_liquidacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_liquidacionActionPerformed(evt);
            }
        });

        tblentrada1.setModel(new javax.swing.table.DefaultTableModel(
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
        tblentrada1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblentrada1MouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(tblentrada1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 20, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 246, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("ENTRADA", jPanel4);

        tblvale1.setModel(new javax.swing.table.DefaultTableModel(
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
        tblvale1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblvale1MouseReleased(evt);
            }
        });
        jScrollPane7.setViewportView(tblvale1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("VALE", jPanel5);

        tbldescuento1.setModel(new javax.swing.table.DefaultTableModel(
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
        tbldescuento1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbldescuento1MouseReleased(evt);
            }
        });
        jScrollPane8.setViewportView(tbldescuento1);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("DESCUENTO", jPanel6);

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1087, Short.MAX_VALUE)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnimprimir_liquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_tablaLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(btnimprimir_liquidacion))))
        );

        jTabbedPane1.addTab("TABLA", panel_tabla);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btncerrar_liquidacion_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncerrar_liquidacion_adminActionPerformed
        // TODO add your handling code here:
        boton_cerrar_liquidacion(false);
    }//GEN-LAST:event_btncerrar_liquidacion_adminActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
//        DAOtc.ancho_tabla_tercero_ciudad(tbltabla);
        DAOper.ancho_tabla_persona_liquida(tblpersonal);
        DAOrhli.ancho_tabla_rh_liquidacion(tblliquidacion);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblliquidacionMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblliquidacionMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tblliquidacionMouseReleased

    private void txtfecha_desdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_desdeKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
    }//GEN-LAST:event_txtfecha_desdeKeyPressed

    private void tblvaleMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblvaleMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblvaleMouseReleased

    private void tblentradaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblentradaMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblentradaMouseReleased

    private void tbldescuentoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldescuentoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbldescuentoMouseReleased

    private void tblpersonalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpersonalMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla_personal();
    }//GEN-LAST:event_tblpersonalMouseReleased

    private void btnnuevo_valeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_valeActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRH_vale());
    }//GEN-LAST:event_btnnuevo_valeActionPerformed

    private void btnnuevo_descuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_descuentoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRH_descuento());
    }//GEN-LAST:event_btnnuevo_descuentoActionPerformed

    private void btnimprimir_valeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_valeActionPerformed
        // TODO add your handling code here:
        boton_imprimir_vale();
    }//GEN-LAST:event_btnimprimir_valeActionPerformed

    private void btnimprimir_liquidacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_liquidacionActionPerformed
        // TODO add your handling code here:
        boton_imprimir_liquidacion();
    }//GEN-LAST:event_btnimprimir_liquidacionActionPerformed

    private void tblentrada1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblentrada1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblentrada1MouseReleased

    private void tblvale1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblvale1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblvale1MouseReleased

    private void tbldescuento1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldescuento1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbldescuento1MouseReleased

    private void btnanular_valeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanular_valeActionPerformed
        // TODO add your handling code here:
        boton_anular_vale();
    }//GEN-LAST:event_btnanular_valeActionPerformed

    private void btnanular_descuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanular_descuentoActionPerformed
        // TODO add your handling code here:
        boton_anular_descuento();
    }//GEN-LAST:event_btnanular_descuentoActionPerformed

    private void btncerrar_liquidacion_cajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncerrar_liquidacion_cajaActionPerformed
        // TODO add your handling code here:
        boton_cerrar_liquidacion(true);
    }//GEN-LAST:event_btncerrar_liquidacion_cajaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanular_descuento;
    private javax.swing.JButton btnanular_vale;
    private javax.swing.JButton btncerrar_liquidacion_admin;
    private javax.swing.JButton btncerrar_liquidacion_caja;
    private javax.swing.JButton btnimprimir_liquidacion;
    private javax.swing.JButton btnimprimir_vale;
    private javax.swing.JButton btnnuevo_descuento;
    private javax.swing.JButton btnnuevo_vale;
    private com.toedter.calendar.JDateChooser dcfecha_hasta;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
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
    public static javax.swing.JFormattedTextField jfmonto_descuento;
    public static javax.swing.JFormattedTextField jfmonto_liquidacion;
    public static javax.swing.JFormattedTextField jfmonto_vale;
    private javax.swing.JFormattedTextField jfsalario_base;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    public static javax.swing.JTable tbldescuento;
    public static javax.swing.JTable tbldescuento1;
    private javax.swing.JTable tblentrada;
    private javax.swing.JTable tblentrada1;
    private javax.swing.JTable tblliquidacion;
    private javax.swing.JTable tblpersonal;
    public static javax.swing.JTable tblvale;
    public static javax.swing.JTable tblvale1;
    private javax.swing.JTextField txtdescripcion;
    private javax.swing.JTextField txtestado;
    private javax.swing.JTextField txtfecha_desde;
    private javax.swing.JTextField txtid;
    public static javax.swing.JTextField txtmonto_letra;
    private javax.swing.JTextField txtpersona;
    // End of variables declaration//GEN-END:variables
}
