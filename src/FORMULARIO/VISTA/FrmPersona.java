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
public class FrmPersona extends javax.swing.JInternalFrame {
    EvenConexion eveconn = new EvenConexion();
    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private persona ENTper=new persona();
    private DAO_persona DAOper=new DAO_persona();
    private BO_persona BOper=new BO_persona();
    private rh_liquidacion ENTrhli=new rh_liquidacion();
    private DAO_rh_liquidacion DAOrhli=new DAO_rh_liquidacion();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenCombobox evecmb = new EvenCombobox();
    private EvenEstado eveest = new EvenEstado();
    Connection conn = ConnPostgres.getConnPosgres();
    usuario ENTusu = new usuario();
    private String nombreTabla_pri="PERSONA"; 
    private String nombreTabla_sec="FILTRO"; 
    private String creado_por="digno";
    String car_id = "idpersona_cargo";
    String car_nombre = "nombre";
    String car_tabla = "persona_cargo";
    String car_where = "where activo=true ";
    private void abrir_formulario() {
        this.setTitle(nombreTabla_pri);
        evetbl.centrar_formulario_internalframa(this);  
        creado_por=ENTusu.getGlobal_nombre();
        reestableser();
        DAOper.actualizar_tabla_persona(conn, tbltabla_pri);
        evecmb.cargarCombobox(conn, cmbpersonal_cargo, car_id, car_nombre, car_tabla, car_where);
    }
    private void titulo_formulario(String fecha_creado,String creado_por){
        this.setTitle(nombreTabla_pri+" / fecha creado: "+fecha_creado+" / Creado Por: "+creado_por);
    }
    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtnro_tarjeta, "DEBE CARGAR UN NUMERO TARJETA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtnombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtruc, "DEBE CARGAR UN RUC")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtdireccion, "DEBE CARGAR UNA DIRECCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txttelefono, "DEBE CARGAR UN TELEFONO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtsalario, "DEBE CARGAR UN SALARIO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtlimite_vale, "DEBE CARGAR UN LIMITE DE VALE")) {
            return false;
        }
        if(evecmb.getBoo_JCombobox_seleccionar(cmbpersonal_cargo,"SELECCIONE UN CARGO")){
            return false;
        }
        return true;
    }
    private String getTipo_persona(){
        String tipo="nulo";
        if(jRtp_cliente.isSelected()){
            tipo=eveest.getPer_CLIENTE();
        }
        if(jRtp_personal.isSelected()){
            tipo=eveest.getPer_PERSONAL();
        }
        if(jRtp_proveedor.isSelected()){
            tipo=eveest.getPer_PROVEEDOR();
        }
        if(jRtp_usuario.isSelected()){
            tipo=eveest.getPer_USUARIO();
        }
        return tipo;
    }
    private void cargar_tipo_persona(String tipo_persona){
        if(tipo_persona.equals(eveest.getPer_CLIENTE())){
            jRtp_cliente.setSelected(true);
        }
        if(tipo_persona.equals(eveest.getPer_PERSONAL())){
            jRtp_personal.setSelected(true);
        }
        if(tipo_persona.equals(eveest.getPer_PROVEEDOR())){
            jRtp_proveedor.setSelected(true);
        }
        if(tipo_persona.equals(eveest.getPer_USUARIO())){
            jRtp_usuario.setSelected(true);
        }
    }
    private void cargar_dato(){
        ENTper.setC3creado_por(creado_por);
        ENTper.setC4nombre(txtnombre.getText());
        ENTper.setC5ruc(txtruc.getText());
        ENTper.setC6direccion(txtdireccion.getText());
        ENTper.setC7telefono(txttelefono.getText());
        ENTper.setC8tipo_persona(getTipo_persona());
        ENTper.setC9dia_libre(1);
        double salario_base=evejtf.getDouble_format_nro_entero(txtsalario);
        ENTper.setC10salario_base(salario_base);
        int fk_idpersona_cargo=evecmb.getInt_seleccionar_COMBOBOX(conn, cmbpersonal_cargo, car_id, car_nombre, car_tabla);
        ENTper.setC11fk_idpersona_cargo(fk_idpersona_cargo);
        ENTper.setC12nro_tarjeta(txtnro_tarjeta.getText());
        double limite_vale=evejtf.getDouble_format_nro_entero(txtlimite_vale);
        ENTper.setC13limite_vale(limite_vale);
    }
    private void cargar_dato_liquidacion(){
        int idpersona=(eveconn.getInt_ultimoID_mas_uno(conn, ENTper.getTb_persona(), ENTper.getId_idpersona()));
        ENTrhli.setC3creado_por(creado_por);
        ENTrhli.setC6estado(eveest.getEst_ABIERTO());
        ENTrhli.setC7es_cerrado(false);
        ENTrhli.setC8monto_vale(0);
        ENTrhli.setC9monto_descuento(0);
        ENTrhli.setC10monto_liquidacion(0);
        double salario_base=evejtf.getDouble_format_nro_entero(txtsalario);
        ENTrhli.setC11salario_base(salario_base);
        ENTrhli.setC12monto_letra("cero");
        ENTrhli.setC13fk_idpersona(idpersona);
        ENTrhli.setC14descripcion("PAGO DE SALARIO");
    }
    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato();
            cargar_dato_liquidacion();
            BOper.insertar_persona(ENTper,ENTrhli, tbltabla_pri);
            reestableser();
        }
    }

    private void boton_editar() {
        if (validar_guardar()) {
            ENTper.setC1idpersona(Integer.parseInt(txtid.getText()));
            cargar_dato();
            BOper.update_persona(ENTper, tbltabla_pri);
        }
    }

    private void seleccionar_tabla() {
        int id = eveJtab.getInt_select_id(tbltabla_pri);
        DAOper.cargar_persona(conn, ENTper, id);
        txtid.setText(String.valueOf(ENTper.getC1idpersona()));
        txtnombre.setText(ENTper.getC4nombre());
        txtruc.setText(ENTper.getC5ruc());
        txtdireccion.setText(ENTper.getC6direccion());
        txttelefono.setText(ENTper.getC7telefono());
        String salario=evejtf.getString_format_nro_decimal(ENTper.getC10salario_base());
        txtsalario.setText(salario);
        txtnro_tarjeta.setText(ENTper.getC12nro_tarjeta());
        String limite=evejtf.getString_format_nro_decimal(ENTper.getC13limite_vale());
        txtlimite_vale.setText(limite);
        cargar_tipo_persona(ENTper.getC8tipo_persona());
        titulo_formulario(ENTper.getC2fecha_creado(), ENTper.getC3creado_por());
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }
    private void reestableser(){
        this.setTitle(nombreTabla_pri);
        jTab_principal.setTitleAt(0, nombreTabla_pri);
        jTab_principal.setTitleAt(1, nombreTabla_sec);
        txtid.setText(null);
        txtnombre.setText(null);
        txtruc.setText(null);
        txtdireccion.setText(null);
        txttelefono.setText(null);
        txtsalario.setText("0");
        txtnro_tarjeta.setText(null);
        txtlimite_vale.setText("0");
        jRtp_cliente.setSelected(true);
        cmbpersonal_cargo.setSelectedIndex(0);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        txtnombre.grabFocus();
    }

    private void boton_nuevo(){
        reestableser();
    }
    public FrmPersona() {
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

        gru_tipo = new javax.swing.ButtonGroup();
        jTab_principal = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panel_insertar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        txtruc = new javax.swing.JTextField();
        txtdireccion = new javax.swing.JTextField();
        txttelefono = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jRtp_cliente = new javax.swing.JRadioButton();
        jRtp_personal = new javax.swing.JRadioButton();
        jRtp_proveedor = new javax.swing.JRadioButton();
        jRtp_usuario = new javax.swing.JRadioButton();
        txtsalario = new javax.swing.JTextField();
        cmbpersonal_cargo = new javax.swing.JComboBox<>();
        txtnro_tarjeta = new javax.swing.JTextField();
        txtlimite_vale = new javax.swing.JTextField();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltabla_pri = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtbuscar = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbltabla_sec = new javax.swing.JTable();

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

        txtnombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtnombre.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRE"));
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnombreKeyReleased(evt);
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

        txtruc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtruc.setBorder(javax.swing.BorderFactory.createTitledBorder("RUC"));
        txtruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrucKeyPressed(evt);
            }
        });

        txtdireccion.setBorder(javax.swing.BorderFactory.createTitledBorder("DIRECCION"));
        txtdireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdireccionKeyPressed(evt);
            }
        });

        txttelefono.setBorder(javax.swing.BorderFactory.createTitledBorder("TELEFONO"));
        txttelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttelefonoKeyPressed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("TIPO"));

        gru_tipo.add(jRtp_cliente);
        jRtp_cliente.setSelected(true);
        jRtp_cliente.setText("CLIENTE");

        gru_tipo.add(jRtp_personal);
        jRtp_personal.setText("PERSONAL");

        gru_tipo.add(jRtp_proveedor);
        jRtp_proveedor.setText("PROVEEDOR");

        gru_tipo.add(jRtp_usuario);
        jRtp_usuario.setText("USUARIO");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jRtp_cliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRtp_personal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRtp_proveedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRtp_usuario)
                .addGap(0, 108, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jRtp_cliente)
                .addComponent(jRtp_personal)
                .addComponent(jRtp_proveedor)
                .addComponent(jRtp_usuario))
        );

        txtsalario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtsalario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtsalario.setBorder(javax.swing.BorderFactory.createTitledBorder("SALARIO"));
        txtsalario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtsalarioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtsalarioKeyReleased(evt);
            }
        });

        cmbpersonal_cargo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbpersonal_cargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbpersonal_cargo.setBorder(javax.swing.BorderFactory.createTitledBorder("CARGO"));

        txtnro_tarjeta.setBorder(javax.swing.BorderFactory.createTitledBorder("NUMERO TARJETA"));
        txtnro_tarjeta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnro_tarjetaKeyPressed(evt);
            }
        });

        txtlimite_vale.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtlimite_vale.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtlimite_vale.setBorder(javax.swing.BorderFactory.createTitledBorder("LIMITE VALE:"));
        txtlimite_vale.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtlimite_valeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtlimite_valeKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnombre)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtnro_tarjeta))
                    .addComponent(txtdireccion, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttelefono))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(txtsalario, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtlimite_vale, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(btnnuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnguardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneditar))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cmbpersonal_cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtnro_tarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtruc)
                    .addComponent(txttelefono))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtsalario, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(txtlimite_vale))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbpersonal_cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btneditar)))
        );

        panel_tabla.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

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
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbltabla_priMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbltabla_pri);

        jLabel3.setText("BUSCAR NOMBRE:");

        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTab_principal.addTab("PRINCIPAL", jPanel1);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tbltabla_sec.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tbltabla_sec);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
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
            .addComponent(jTab_principal)
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
        DAOper.ancho_tabla_persona(tbltabla_pri);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tbltabla_priMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltabla_priMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tbltabla_priMouseReleased

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
        evejtf.saltar_campo_enter(evt, txtnombre, txtruc);
    }//GEN-LAST:event_txtnombreKeyPressed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        // TODO add your handling code here:
//        DAOgt.actualizar_tabla_persona_buscar(conn, tbltabla_pri, txtbuscar);
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void txtnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyReleased
        // TODO add your handling code here:
        txtnombre.setText(txtnombre.getText().toUpperCase());
    }//GEN-LAST:event_txtnombreKeyReleased

    private void txtnro_tarjetaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnro_tarjetaKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtnro_tarjeta, txtnombre);
    }//GEN-LAST:event_txtnro_tarjetaKeyPressed

    private void txtrucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrucKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtruc, txttelefono);
    }//GEN-LAST:event_txtrucKeyPressed

    private void txttelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtruc, txtdireccion);
    }//GEN-LAST:event_txttelefonoKeyPressed

    private void txtdireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdireccionKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtdireccion, txtsalario);
    }//GEN-LAST:event_txtdireccionKeyPressed

    private void txtsalarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsalarioKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtsalario, txtlimite_vale);
    }//GEN-LAST:event_txtsalarioKeyPressed

    private void txtsalarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsalarioKeyReleased
        // TODO add your handling code here:
        evejtf.getDouble_format_nro_entero(txtsalario);
    }//GEN-LAST:event_txtsalarioKeyReleased

    private void txtlimite_valeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtlimite_valeKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtlimite_valeKeyPressed

    private void txtlimite_valeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtlimite_valeKeyReleased
        // TODO add your handling code here:
        evejtf.getDouble_format_nro_entero(txtlimite_vale);
    }//GEN-LAST:event_txtlimite_valeKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JComboBox<String> cmbpersonal_cargo;
    private javax.swing.ButtonGroup gru_tipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRtp_cliente;
    private javax.swing.JRadioButton jRtp_personal;
    private javax.swing.JRadioButton jRtp_proveedor;
    private javax.swing.JRadioButton jRtp_usuario;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTab_principal;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tbltabla_pri;
    private javax.swing.JTable tbltabla_sec;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtdireccion;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtlimite_vale;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtnro_tarjeta;
    private javax.swing.JTextField txtruc;
    private javax.swing.JTextField txtsalario;
    private javax.swing.JTextField txttelefono;
    // End of variables declaration//GEN-END:variables
}
