/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import ESTADOS.EvenEstado;
//import Evento.Color.cla_color_palete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class FrmPatrimonio_producto extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private patrimonio_producto ENTpp=new patrimonio_producto();
    private DAO_patrimonio_producto DAOpp=new DAO_patrimonio_producto();
    private BO_patrimonio_producto BOpp=new BO_patrimonio_producto();
    private DAO_patrimonio_categoria DAOpc=new DAO_patrimonio_categoria();
    private patrimonio_categoria ENTpc=new patrimonio_categoria();
    private patrimonio_ubicacion ENTpu=new patrimonio_ubicacion();
    private DAO_patrimonio_ubicacion DAOpu=new DAO_patrimonio_ubicacion();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenEstado eveest = new EvenEstado();
    Connection conn = ConnPostgres.getConnPosgres();
    usuario ENTusu = new usuario();
    private String nombreTabla_pri="PATRIMONIO PRODUCTO"; 
    private String nombreTabla_sec="PATRIMONIO CARGA"; 
    private String creado_por="digno";
    private int fk_idpatrimonio_ubicacion;
    private int fk_idpatrimonio_categoria;
    private void abrir_formulario() {
        this.setTitle(nombreTabla_pri);
        evetbl.centrar_formulario_internalframa(this);  
        creado_por=ENTusu.getGlobal_nombre();
        reestableser();
        DAOpp.actualizar_tabla_patrimonio_producto(conn, tbltabla_pri);
    }
    private void titulo_formulario(String fecha_creado,String creado_por){
        this.setTitle(nombreTabla_pri+" / fecha creado: "+fecha_creado+" / Creado Por: "+creado_por);
    }
    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtnombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtreferencia, "DEBE CARGAR UNA REFERENCIA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtprecio_compra, "DEBE CARGAR UN PRECIO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtstock, "DEBE CARGAR UN STOCK")) {
            return false;
        }
        if(fk_idpatrimonio_categoria==0){
            JOptionPane.showMessageDialog(this,"NO SE CARGO LA CATEGORIA");
            txtpatrimonio_categoria.setText(null);
            txtpatrimonio_categoria.grabFocus();
            return false;
        }
        if(fk_idpatrimonio_ubicacion==0){
            JOptionPane.showMessageDialog(this,"NO SE CARGO LA UBICACION");
            txtpatrimonio_ubicacion.setText(null);
            txtpatrimonio_ubicacion.grabFocus();
            return false;
        }
        return true;
    }
    private void cargar_dato(){
        ENTpp.setC3creado_por(creado_por);
        ENTpp.setC4nombre(txtnombre.getText());
        ENTpp.setC5referencia(txtreferencia.getText());
        double precio=evejtf.getDouble_format_nro_entero(txtprecio_compra);
        ENTpp.setC6precio_compra(precio);
        String tipo="";
        if(jRtipo_insumo.isSelected()){
            tipo=eveest.getEst_INSUMO();
        }
        if(jRtipo_patrimonio.isSelected()){
            tipo=eveest.getEst_PATRIMONIO();
        }
        ENTpp.setC7tipo(tipo);
        ENTpp.setC8estado(eveest.getEst_EN_USO());
        ENTpp.setC9stock(Integer.parseInt(txtstock.getText()));
        ENTpp.setC10fk_idpatrimonio_ubicacion(fk_idpatrimonio_ubicacion);
        ENTpp.setC11fk_idpatrimonio_categoria(fk_idpatrimonio_categoria);
    }
    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato();
            BOpp.insertar_patrimonio_producto(ENTpp, tbltabla_pri);
            reestableser();
        }
    }

    private void boton_editar() {
        if (validar_guardar()) {
            ENTpp.setC1idpatrimonio_producto(Integer.parseInt(txtid.getText()));
            cargar_dato();
            BOpp.update_patrimonio_producto(ENTpp, tbltabla_pri);
        }
    }

    private void seleccionar_tabla() {
        int id = eveJtab.getInt_select_id(tbltabla_pri);
        DAOpp.cargar_patrimonio_producto(conn, ENTpp, id);
        txtid.setText(String.valueOf(ENTpp.getC1idpatrimonio_producto()));
        txtnombre.setText(ENTpp.getC4nombre());
        txtreferencia.setText(ENTpp.getC5referencia());
        String precio=evejtf.getString_format_nro_decimal(ENTpp.getC6precio_compra());
        txtprecio_compra.setText(precio);
        txtestado.setText(ENTpp.getC8estado());
        txtstock.setText(String.valueOf(ENTpp.getC9stock()));
        fk_idpatrimonio_ubicacion=ENTpp.getC10fk_idpatrimonio_ubicacion();
        fk_idpatrimonio_categoria=ENTpp.getC11fk_idpatrimonio_categoria();
        if(ENTpp.getC7tipo().equals(eveest.getEst_INSUMO())){
            jRtipo_insumo.setSelected(true);
        }
        if(ENTpp.getC7tipo().equals(eveest.getEst_PATRIMONIO())){
            jRtipo_patrimonio.setSelected(true);
        }
        DAOpc.cargar_patrimonio_categoria(conn, ENTpc, fk_idpatrimonio_categoria);
        txtpatrimonio_categoria.setText(ENTpc.getC4nombre());
        DAOpu.cargar_patrimonio_ubicacion(conn, ENTpu, fk_idpatrimonio_ubicacion);
        txtpatrimonio_ubicacion.setText(ENTpu.getC4nombre());
        titulo_formulario(ENTpp.getC2fecha_creado(), ENTpp.getC3creado_por());
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }
    private void reestableser(){
        this.setTitle(nombreTabla_pri);
        jTab_principal.setTitleAt(0, nombreTabla_pri);
        jTab_principal.setTitleAt(1, nombreTabla_sec);
        jScategoria.setVisible(false);
        jSubicacion.setVisible(false);
        txtid.setText(null);
        txtnombre.setText(null);
        txtreferencia.setText("sin-ref");
        txtprecio_compra.setText(null);
        txtestado.setText(null);
        txtstock.setText("0");
        txtpatrimonio_categoria.setText(null);
        txtpatrimonio_ubicacion.setText(null);
        fk_idpatrimonio_ubicacion=0;
        fk_idpatrimonio_categoria=0;
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        txtnombre.grabFocus();
    }
    private void buscar_categoria(){
        jScategoria.setVisible(true);
        DAOpc.actualizar_tabla_patrimonio_categoria_buscar_pro(conn, tbltabla_categoria, txtpatrimonio_categoria);
    }
    private void seleccionar_categoria(){
        int id=eveJtab.getInt_select_id(tbltabla_categoria);
        fk_idpatrimonio_categoria=id;
        jScategoria.setVisible(false);
        DAOpc.cargar_patrimonio_categoria(conn, ENTpc, fk_idpatrimonio_categoria);
        txtpatrimonio_categoria.setText(ENTpc.getC4nombre());
        txtpatrimonio_ubicacion.grabFocus();
    }
    private void buscar_ubicacion(){
        jSubicacion.setVisible(true);
        DAOpu.actualizar_tabla_patrimonio_ubicacion_buscar_pro(conn, tbltabla_ubicacion, txtpatrimonio_ubicacion);
    }
    private void seleccionar_ubicacion(){
        int id=eveJtab.getInt_select_id(tbltabla_ubicacion);
        fk_idpatrimonio_ubicacion=id;
        jSubicacion.setVisible(false);
        DAOpu.cargar_patrimonio_ubicacion(conn, ENTpu, fk_idpatrimonio_ubicacion);
        txtpatrimonio_ubicacion.setText(ENTpu.getC4nombre());
//        txtpatrimonio_ubicacion.grabFocus();
    }
    private void boton_nuevo(){
        reestableser();
    }
    public FrmPatrimonio_producto() {
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
        txtid = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jScategoria = new javax.swing.JScrollPane();
        tbltabla_categoria = new javax.swing.JTable();
        jSubicacion = new javax.swing.JScrollPane();
        tbltabla_ubicacion = new javax.swing.JTable();
        txtpatrimonio_ubicacion = new javax.swing.JTextField();
        txtpatrimonio_categoria = new javax.swing.JTextField();
        btnnuevo_categoria = new javax.swing.JButton();
        btnnuevo_ubicacion = new javax.swing.JButton();
        txtreferencia = new javax.swing.JTextField();
        txtprecio_compra = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jRtipo_insumo = new javax.swing.JRadioButton();
        jRtipo_patrimonio = new javax.swing.JRadioButton();
        txtestado = new javax.swing.JTextField();
        txtstock = new javax.swing.JTextField();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltabla_pri = new javax.swing.JTable();
        txtbuscar_producto = new javax.swing.JTextField();
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

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtid.setBorder(javax.swing.BorderFactory.createTitledBorder("ID:"));

        txtnombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtnombre.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRE PRODUCTO:"));
        txtnombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreActionPerformed(evt);
            }
        });
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

        jPanel4.setBackground(new java.awt.Color(153, 153, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("RELACION"));

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbltabla_categoria.setModel(new javax.swing.table.DefaultTableModel(
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
        tbltabla_categoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbltabla_categoriaMouseReleased(evt);
            }
        });
        jScategoria.setViewportView(tbltabla_categoria);

        jLayeredPane1.add(jScategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 190, 280));

        tbltabla_ubicacion.setModel(new javax.swing.table.DefaultTableModel(
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
        tbltabla_ubicacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbltabla_ubicacionMouseReleased(evt);
            }
        });
        jSubicacion.setViewportView(tbltabla_ubicacion);

        jLayeredPane1.add(jSubicacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 200, 290));

        txtpatrimonio_ubicacion.setBorder(javax.swing.BorderFactory.createTitledBorder("UBICACION:"));
        txtpatrimonio_ubicacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpatrimonio_ubicacionKeyReleased(evt);
            }
        });
        jLayeredPane1.add(txtpatrimonio_ubicacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 210, 40));

        txtpatrimonio_categoria.setBorder(javax.swing.BorderFactory.createTitledBorder("CATEGORIA:"));
        txtpatrimonio_categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpatrimonio_categoriaKeyReleased(evt);
            }
        });
        jLayeredPane1.add(txtpatrimonio_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 210, 40));

        btnnuevo_categoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_categoriaActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, -1, 40));

        btnnuevo_ubicacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_ubicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_ubicacionActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo_ubicacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, -1, 40));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
        );

        txtreferencia.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtreferencia.setBorder(javax.swing.BorderFactory.createTitledBorder("REFERENCIA:"));

        txtprecio_compra.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        txtprecio_compra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprecio_compra.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO COMPRA:"));
        txtprecio_compra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprecio_compraKeyReleased(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("TIPO:"));

        gru_tipo.add(jRtipo_insumo);
        jRtipo_insumo.setSelected(true);
        jRtipo_insumo.setText("INSUMO");

        gru_tipo.add(jRtipo_patrimonio);
        jRtipo_patrimonio.setText("PATRIMONIO");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jRtipo_insumo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRtipo_patrimonio)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jRtipo_insumo)
                .addComponent(jRtipo_patrimonio))
        );

        txtestado.setEditable(false);
        txtestado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtestado.setBorder(javax.swing.BorderFactory.createTitledBorder("ESTADO:"));

        txtstock.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        txtstock.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtstock.setBorder(javax.swing.BorderFactory.createTitledBorder("STOCK:"));

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtreferencia, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_insertarLayout.createSequentialGroup()
                                .addComponent(btnnuevo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnguardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btneditar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtprecio_compra, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtestado, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtstock, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtnombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(134, 134, 134))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtreferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtprecio_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtestado, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtstock, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnnuevo)
                        .addComponent(btnguardar)
                        .addComponent(btneditar))
                    .addComponent(txtid, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        txtbuscar_producto.setBorder(javax.swing.BorderFactory.createTitledBorder("BUSCAR:"));
        txtbuscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_productoKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(txtbuscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panel_insertar, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1246, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
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
        DAOpp.ancho_tabla_patrimonio_producto(tbltabla_pri);
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
    }//GEN-LAST:event_txtnombreKeyPressed

    private void txtbuscar_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_productoKeyReleased
        // TODO add your handling code here:
//        DAOgt.actualizar_tabla_patrimonio_producto_buscar(conn, tbltabla_pri, txtbuscar);
        DAOpp.actualizar_tabla_patrimonio_producto(conn, tbltabla_pri, txtbuscar_producto);
    }//GEN-LAST:event_txtbuscar_productoKeyReleased

    private void txtnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyReleased
        // TODO add your handling code here:
        txtnombre.setText(txtnombre.getText().toUpperCase());
    }//GEN-LAST:event_txtnombreKeyReleased

    private void txtnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreActionPerformed

    private void tbltabla_ubicacionMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltabla_ubicacionMouseReleased
        // TODO add your handling code here:
        seleccionar_ubicacion();
    }//GEN-LAST:event_tbltabla_ubicacionMouseReleased

    private void tbltabla_categoriaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltabla_categoriaMouseReleased
        // TODO add your handling code here:
        seleccionar_categoria();
    }//GEN-LAST:event_tbltabla_categoriaMouseReleased

    private void txtprecio_compraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_compraKeyReleased
        // TODO add your handling code here:
        evejtf.getDouble_format_nro_entero(txtprecio_compra);
    }//GEN-LAST:event_txtprecio_compraKeyReleased

    private void txtpatrimonio_categoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpatrimonio_categoriaKeyReleased
        // TODO add your handling code here:
        buscar_categoria();
    }//GEN-LAST:event_txtpatrimonio_categoriaKeyReleased

    private void txtpatrimonio_ubicacionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpatrimonio_ubicacionKeyReleased
        // TODO add your handling code here:
        buscar_ubicacion();
    }//GEN-LAST:event_txtpatrimonio_ubicacionKeyReleased

    private void btnnuevo_categoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_categoriaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmPatrimonio_categoria());
    }//GEN-LAST:event_btnnuevo_categoriaActionPerformed

    private void btnnuevo_ubicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_ubicacionActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmPatrimonio_ubicacion());
    }//GEN-LAST:event_btnnuevo_ubicacionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnnuevo_categoria;
    private javax.swing.JButton btnnuevo_ubicacion;
    private javax.swing.ButtonGroup gru_tipo;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRtipo_insumo;
    private javax.swing.JRadioButton jRtipo_patrimonio;
    private javax.swing.JScrollPane jScategoria;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jSubicacion;
    private javax.swing.JTabbedPane jTab_principal;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tbltabla_categoria;
    private javax.swing.JTable tbltabla_pri;
    private javax.swing.JTable tbltabla_sec;
    private javax.swing.JTable tbltabla_ubicacion;
    private javax.swing.JTextField txtbuscar_producto;
    private javax.swing.JTextField txtestado;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtpatrimonio_categoria;
    private javax.swing.JTextField txtpatrimonio_ubicacion;
    private javax.swing.JTextField txtprecio_compra;
    private javax.swing.JTextField txtreferencia;
    private javax.swing.JTextField txtstock;
    // End of variables declaration//GEN-END:variables
}
