/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import ESTADOS.EvenEstado;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
//import Evento.Color.cla_color_palete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmTransaccion_banco extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private transaccion_banco ENTtb = new transaccion_banco();
    private DAO_transaccion_banco DAOtb = new DAO_transaccion_banco();
    private BO_transaccion_banco BOtb = new BO_transaccion_banco();
    private dato_banco ENTdb = new dato_banco();
    private DAO_dato_banco DAOdb = new DAO_dato_banco();
    private DAO_caja_cierre DAOcc=new DAO_caja_cierre();
//    private 
//    private DAO_producto DAOp = new DAO_producto();
    private EvenJTextField evejtf = new EvenJTextField();
    EvenCombobox evecmb = new EvenCombobox();
    EvenFecha evefec = new EvenFecha();
    EvenEstado eveest = new EvenEstado();
    Connection conn = ConnPostgres.getConnPosgres();
    usuario ENTusu = new usuario(); //creado_por = ENTusu.getGlobal_nombre();
    private String nombreTabla_pri = "BANCO";
    private String nombreTabla_sec = "DATO-BANCO";
    private String creado_por = "digno";
    private int fk_iddato_banco;
    private boolean hab_carga_dato_banco;
    String db_id="db.iddato_banco";
    String db_nombre="b.nombre||'-'||db.nro_cuenta";
    String db_tabla="banco b,dato_banco db";
    String db_where="db.fk_idbanco=b.idbanco and db.activo=true ";
    private void abrir_formulario() {
        this.setTitle(nombreTabla_pri);
        evetbl.centrar_formulario_internalframa(this);
        creado_por = ENTusu.getGlobal_nombre();
        reestableser();
        cargar_dato_banco();
        DAOtb.actualizar_tabla_transaccion_banco(conn, tbltabla_pri);
    }

    private void titulo_formulario(String fecha_creado, String creado_por) {
        this.setTitle(nombreTabla_pri + " / fecha creado: " + fecha_creado + " / Creado Por: " + creado_por);
    }

    private void cargar_dato_banco() {
        evecmb.cargarCombobox_2(conn, cmbdato_banco, db_id,db_nombre, db_tabla,"where "+ db_where);
        hab_carga_dato_banco = true;
    }

    private void select_dato_banco() {
        if (hab_carga_dato_banco) {
            fk_iddato_banco = evecmb.getInt_seleccionar_COMBOBOX_where(conn, cmbdato_banco,db_id,
                    "b.nombre,'-',db.nro_cuenta", db_tabla,"and "+ db_where);
            System.out.println("fk_iddato_banco:" + fk_iddato_banco);
            DAOdb.cargar_dato_banco(conn, ENTdb, fk_iddato_banco);
            if (ENTdb.getC8es_guarani()) {
//                txtmonto_guarani.setText("0");
                txtmonto_dolar.setEnabled(false);
                txtmonto_guarani.setEnabled(true);
            } else {
//                txtmonto_dolar.setText("0");
                txtmonto_dolar.setEnabled(true);
                txtmonto_guarani.setEnabled(false);
            }
        }
    }

    private boolean validar_guardar() {
        if (evecmb.getBoo_JCombobox_seleccionar(cmbdato_banco, "SELECCIONE UNA CUENTA DE BANCO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtnro_transaccion, "DEBE CARGAR UN NRO REFERENCIA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtconcepto, "DEBE CARGAR UN CONCEPTO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtobservacion, "DEBE CARGAR OBSERVACION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtmonto_guarani, "DEBE CARGAR UN MONTO GUARANI")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtmonto_dolar, "DEBE CARGAR UN MONTO DOLAR")) {
            return false;
        }
        return true;
    }

    private void cargar_dato() {
        ENTtb.setC3creado_por(creado_por);
        String fecha = evefec.getfechaDCStringFormat(jDfecha_transaccion, "yyyy-MM-dd");
        ENTtb.setC4fecha_transaccion(fecha);
        ENTtb.setC5nro_transaccion(txtnro_transaccion.getText());
        ENTtb.setC6monto_guarani(evejtf.getDouble_format_nro_entero(txtmonto_guarani));
        ENTtb.setC7monto_dolar(evejtf.getDouble_format_nro_entero(txtmonto_dolar));
        ENTtb.setC8observacion(txtobservacion.getText());
        ENTtb.setC9concepto(txtconcepto.getText());
        ENTtb.setC10estado(eveest.getEst_Emitido());
        ENTtb.setC11fk_iddato_banco(fk_iddato_banco);
    }

    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato();
            BOtb.insertar_transaccion_banco(ENTtb, tbltabla_pri);
            DAOcc.exportar_excel_deposito_banco_N3(conn);
            reestableser();
        }
    }

    private void boton_editar() {
        if (validar_guardar()) {
            ENTtb.setC1idtransaccion_banco(Integer.parseInt(txtid.getText()));
            cargar_dato();
            BOtb.update_transaccion_banco(ENTtb, tbltabla_pri);
        }
    }

    private void seleccionar_tabla() {
        int idtransaccion_banco = eveJtab.getInt_select_id(tbltabla_pri);
        DAOtb.cargar_transaccion_banco(conn, ENTtb, idtransaccion_banco);
        txtid.setText(String.valueOf(ENTtb.getC1idtransaccion_banco()));
        txtnro_transaccion.setText(ENTtb.getC5nro_transaccion());
        txtmonto_guarani.setText(evejtf.getString_format_nro_decimal(ENTtb.getC6monto_guarani()));
        txtmonto_dolar.setText(evejtf.getString_format_nro_decimal(ENTtb.getC7monto_dolar()));
        txtobservacion.setText(ENTtb.getC8observacion());
        txtconcepto.setText(ENTtb.getC9concepto());
        evefec.setFechaDCcargado(jDfecha_transaccion, ENTtb.getC4fecha_transaccion());
        evecmb.setSeleccionarCombobox_where(conn, cmbdato_banco, db_id, db_nombre, db_tabla,"where "+db_where, ENTtb.getC11fk_iddato_banco());
//        DAOdb.cargar_dato_banco(conn, ENTdb, ENTtb.getC11fk_iddato_banco());
//        if (ENTdb.getC8es_guarani()) {
//            txtmonto_dolar.setEnabled(false);
//            txtmonto_guarani.setEnabled(true);
//        } else {
//            txtmonto_dolar.setEnabled(true);
//            txtmonto_guarani.setEnabled(false);
//        }
        titulo_formulario(ENTtb.getC2fecha_creado(), ENTtb.getC3creado_por());
        String filtro = "and pm.idtransaccion_banco=" + ENTtb.getC1idtransaccion_banco();
//        DAOp.actualizar_tabla_producto(conn, tbltabla_sec, filtro,"3","");
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }
    private void boton_anular(){
        if(eveJtab.getBoolean_validar_select_mensaje(tbltabla_pri,"SELECCIONE UNA TRANSACCION PARA ANULAR")){
            int idtransaccion_banco = eveJtab.getInt_select_id(tbltabla_pri);
            ENTtb.setC6monto_guarani(0);
            ENTtb.setC7monto_dolar(0);
            ENTtb.setC10estado(eveest.getEst_Anulado());
            ENTtb.setC1idtransaccion_banco(idtransaccion_banco);
            BOtb.update_transaccion_banco_anular(ENTtb, tbltabla_pri);
        }
    }
    private void reestableser() {
        this.setTitle(nombreTabla_pri);
        jTab_principal.setTitleAt(0, nombreTabla_pri);
        jTab_principal.setTitleAt(1, nombreTabla_sec);
        txtid.setText(null);
        txtnro_transaccion.setText("SIN-REF");
        cmbdato_banco.setSelectedIndex(0);
        evefec.setFechaDCSistema(jDfecha_transaccion);
        txtconcepto.setText("DEPOSITO DE CAJA");
        txtobservacion.setText("Ninguna");
        txtmonto_guarani.setText("0");
        txtmonto_dolar.setText("0");
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        txtnro_transaccion.grabFocus();
    }

    private void boton_nuevo() {
        reestableser();
    }

    public FrmTransaccion_banco() {
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

        jTab_principal = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panel_insertar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        txtnro_transaccion = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        jDfecha_transaccion = new com.toedter.calendar.JDateChooser();
        cmbdato_banco = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        txtmonto_guarani = new javax.swing.JTextField();
        txtmonto_dolar = new javax.swing.JTextField();
        txtobservacion = new javax.swing.JTextField();
        txtconcepto = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbltabla_pri = new javax.swing.JTable();
        btnanular = new javax.swing.JButton();

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("ID:");

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtnro_transaccion.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtnro_transaccion.setBorder(javax.swing.BorderFactory.createTitledBorder("NRO REFERENCIA:"));
        txtnro_transaccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnro_transaccionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnro_transaccionKeyReleased(evt);
            }
        });

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar.setText("EDITAR");
        btneditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });

        jDfecha_transaccion.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA TRANSACCION"));
        jDfecha_transaccion.setDateFormatString("yyyy-MM-dd");
        jDfecha_transaccion.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        cmbdato_banco.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cmbdato_banco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbdato_banco.setBorder(javax.swing.BorderFactory.createTitledBorder("CUENTA BANCO"));
        cmbdato_banco.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbdato_bancoItemStateChanged(evt);
            }
        });
        cmbdato_banco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cmbdato_bancoMouseReleased(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("MONEDA"));

        txtmonto_guarani.setFont(new java.awt.Font("Stencil", 0, 36)); // NOI18N
        txtmonto_guarani.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtmonto_guarani.setText("1000");
        txtmonto_guarani.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO GUARANI"));
        txtmonto_guarani.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmonto_guaraniKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmonto_guaraniKeyTyped(evt);
            }
        });

        txtmonto_dolar.setFont(new java.awt.Font("Stencil", 0, 36)); // NOI18N
        txtmonto_dolar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtmonto_dolar.setText("1000");
        txtmonto_dolar.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO DOLAR"));
        txtmonto_dolar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmonto_dolarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmonto_dolarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtmonto_guarani, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(txtmonto_dolar, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(txtmonto_guarani, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtmonto_dolar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtobservacion.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtobservacion.setBorder(javax.swing.BorderFactory.createTitledBorder("OBSERVACION"));
        txtobservacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtobservacionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtobservacionKeyReleased(evt);
            }
        });

        txtconcepto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtconcepto.setBorder(javax.swing.BorderFactory.createTitledBorder("CONCEPTO"));
        txtconcepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtconceptoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtconceptoKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtobservacion)
                        .addGroup(panel_insertarLayout.createSequentialGroup()
                            .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panel_insertarLayout.createSequentialGroup()
                                    .addComponent(btnnuevo)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnguardar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btneditar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1)))
                                .addComponent(cmbdato_banco, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtnro_transaccion, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jDfecha_transaccion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtconcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(cmbdato_banco, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDfecha_transaccion, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnro_transaccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtconcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtobservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btneditar)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTab_principal.addTab("PRINCIPAL", jPanel1);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tbltabla_pri.setModel(new javax.swing.table.DefaultTableModel(
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
        tbltabla_pri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbltabla_priMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbltabla_priMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbltabla_pri);

        btnanular.setBackground(new java.awt.Color(255, 51, 51));
        btnanular.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnanular.setText("ANULAR");
        btnanular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnanular, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnanular, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTab_principal.addTab("SECUNDARIO", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTab_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 807, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTab_principal)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        DAOtb.ancho_tabla_transaccion_banco(tbltabla_pri);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtnro_transaccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnro_transaccionKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
    }//GEN-LAST:event_txtnro_transaccionKeyPressed

    private void txtnro_transaccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnro_transaccionKeyReleased
        // TODO add your handling code here:
        txtnro_transaccion.setText(txtnro_transaccion.getText().toUpperCase());
    }//GEN-LAST:event_txtnro_transaccionKeyReleased

    private void txtobservacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtobservacionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtobservacionKeyPressed

    private void txtobservacionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtobservacionKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtobservacionKeyReleased

    private void txtconceptoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtconceptoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtconceptoKeyPressed

    private void txtconceptoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtconceptoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtconceptoKeyReleased

    private void cmbdato_bancoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbdato_bancoMouseReleased
        // TODO add your handling code here:
//        select_dato_banco();
    }//GEN-LAST:event_cmbdato_bancoMouseReleased

    private void cmbdato_bancoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbdato_bancoItemStateChanged
        // TODO add your handling code here:
        select_dato_banco();
    }//GEN-LAST:event_cmbdato_bancoItemStateChanged

    private void txtmonto_guaraniKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_guaraniKeyReleased
        // TODO add your handling code here:
        evejtf.getDouble_format_nro_entero(txtmonto_guarani);
    }//GEN-LAST:event_txtmonto_guaraniKeyReleased

    private void txtmonto_dolarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_dolarKeyReleased
        // TODO add your handling code here:
        evejtf.getDouble_format_nro_entero(txtmonto_dolar);
    }//GEN-LAST:event_txtmonto_dolarKeyReleased

    private void txtmonto_guaraniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_guaraniKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtmonto_guaraniKeyTyped

    private void txtmonto_dolarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_dolarKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtmonto_dolarKeyTyped

    private void tbltabla_priMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltabla_priMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tbltabla_priMouseReleased

    private void tbltabla_priMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltabla_priMouseClicked
        // TODO add your handling code here:
//        seleccionar_tabla();
    }//GEN-LAST:event_tbltabla_priMouseClicked

    private void btnanularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanularActionPerformed
        // TODO add your handling code here:
        boton_anular();
    }//GEN-LAST:event_btnanularActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanular;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JComboBox<String> cmbdato_banco;
    private com.toedter.calendar.JDateChooser jDfecha_transaccion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTab_principal;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JTable tbltabla_pri;
    private javax.swing.JTextField txtconcepto;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtmonto_dolar;
    private javax.swing.JTextField txtmonto_guarani;
    private javax.swing.JTextField txtnro_transaccion;
    private javax.swing.JTextField txtobservacion;
    // End of variables declaration//GEN-END:variables
}
