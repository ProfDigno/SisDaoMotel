/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

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
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Digno
 */
public class FrmPatrimonio_baja extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private patrimonio_baja ENTpb = new patrimonio_baja();
    private DAO_patrimonio_baja DAOpb= new DAO_patrimonio_baja();
    private BO_patrimonio_baja BOpb = new BO_patrimonio_baja();
    private patrimonio_producto ENTpp = new patrimonio_producto();
    private DAO_patrimonio_producto DAOpp = new DAO_patrimonio_producto();
    private patrimonio_ubicacion ENTpu = new patrimonio_ubicacion();
    private DAO_patrimonio_ubicacion DAOpu = new DAO_patrimonio_ubicacion();
    private DAO_patrimonio_baja_item DAOpbi=new DAO_patrimonio_baja_item();
    private DAO_caja_cierre DAOcc = new DAO_caja_cierre();
    private EvenJTextField evejtf = new EvenJTextField();
    private DefaultTableModel model_itemf = new DefaultTableModel();
    private EvenEstado eveest = new EvenEstado();
    private EvenCombobox evecmb=new EvenCombobox();
    Connection conn = ConnPostgres.getConnPosgres();
    usuario ENTusu = new usuario();
    private String nombreTabla_pri = "PATRIMONIO BAJA";
    private String nombreTabla_sec = "PATRIMONIO PRODUCTO";
    private String creado_por = "digno";
    private int idpatrimonio_producto;
    private String referencia_item;
    private String ubicacion_item;

    private void abrir_formulario() {
        this.setTitle(nombreTabla_pri);
        evetbl.centrar_formulario_internalframa(this);
        creado_por = ENTusu.getGlobal_nombre();
        DAOpb.actualizar_tabla_patrimonio_baja(conn, tblpatrimonio_baja);
        crear_item_producto();
        cargar_motivo();
        reestableser();
    }
    private void cargar_motivo(){
        evecmb.cargarCombobox(conn, cmbmotivo, "idpatrimonio_baja_motivo", "nombre", "patrimonio_baja_motivo", "where activo=true ");
    }
    private void titulo_formulario(String fecha_creado, String creado_por) {
        this.setTitle(nombreTabla_pri + " / fecha creado: " + fecha_creado + " / Creado Por: " + creado_por);
    }

    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtobservacion, "DEBE CARGAR UNA OBSERVACION")) {
            return false;
        }
        if (eveJtab.getBoolean_validar_cant_cargado(tblproducto_item)) {
            return false;
        }
        return true;
    }

    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato_patrimonio_baja();
            DAOcc.exportar_excel_patrimonio_baja_N3(conn);
            reestableser();
        }
    }

    private void reestableser() {
        DAOpb.actualizar_tabla_patrimonio_baja(conn, tblpatrimonio_baja);
        this.setTitle(nombreTabla_pri);
        jTab_principal.setTitleAt(0, nombreTabla_pri);
        jTab_principal.setTitleAt(1, nombreTabla_sec);
        eveJtab.limpiar_tabla_datos(model_itemf);
        reestableser_item_producto();
        suma_total();
        jSbusca_producto.setVisible(false);
        txtobservacion.setText("Ninguna");
        txtid.setText(null);
        btnguardar.setEnabled(true);
    }

    private void boton_nuevo() {
        reestableser();
    }

    private void buscar_producto() {
        if (txtpro_nombre.getText().trim().length() > 0) {
            jSbusca_producto.setVisible(true);
        }
        DAOpp.actualizar_tabla_patrimonio_producto_carga(conn, tblbusca_producto,"pp.nombre", txtpro_nombre);
    }

    private void seleccionar_producto() {
        int id = eveJtab.getInt_select_id(tblbusca_producto);
        String nombre = eveJtab.getString_select(tblbusca_producto, 1);
        jSbusca_producto.setVisible(false);
        DAOpp.cargar_patrimonio_producto(conn, ENTpp, id);
        idpatrimonio_producto = id;
        txtpro_nombre.setText(nombre);
        String precio_1 = evejtf.getString_format_nro_decimal(ENTpp.getC6precio_compra());
        referencia_item = ENTpp.getC5referencia();
        DAOpu.cargar_patrimonio_ubicacion(conn, ENTpu, ENTpp.getC10fk_idpatrimonio_ubicacion());
        ubicacion_item = ENTpu.getC4nombre();
        txtpro_cant.setText("1");
        txtpro_precio.setText(precio_1);
        txtpro_subtotal.setText(precio_1);
        txtpro_cant.grabFocus();
    }

    private void calcular_subtotal() {
        int icant = txtpro_cant.getText().trim().length();
        int iprecio = txtpro_precio.getText().trim().length();
        if (icant > 0 && iprecio > 0) {
            double dcantidad = evejtf.getDouble_format_nro_entero(txtpro_cant);
            double dprecio = evejtf.getDouble_format_nro_entero(txtpro_precio);
            double subtotal = dcantidad * dprecio;
            String Ssubtotal = evejtf.getString_format_nro_decimal(subtotal);
            txtpro_subtotal.setText(Ssubtotal);
        }
    }

    private void crear_item_producto() {
        String dato[] = {"ID", "DESCRIPCION", "UBICACION", "REFERENCIA", "CANT", "PRECIO", "SUBTOTAL", "Oprecio", "Osubtotal", "Oidpatrimonio_baja","Oidpatrimonio_baja_motivo"};
        eveJtab.crear_tabla_datos(tblproducto_item, model_itemf, dato);
        ancho_item_producto();
    }

    private void ancho_item_producto() {
        int Ancho[] = {5, 50, 10, 10, 5, 10, 10, 1, 1, 1,1};
        eveJtab.setAnchoColumnaJtable(tblproducto_item, Ancho);
        eveJtab.alinear_derecha_columna(tblproducto_item, 4);
        eveJtab.alinear_derecha_columna(tblproducto_item, 5);
        eveJtab.alinear_derecha_columna(tblproducto_item, 6);
        eveJtab.ocultar_columna(tblproducto_item, 7);
        eveJtab.ocultar_columna(tblproducto_item, 8);
        eveJtab.ocultar_columna(tblproducto_item, 9);
        eveJtab.ocultar_columna(tblproducto_item, 10);
    }

    private boolean validar_item() {
        if (idpatrimonio_producto == 0) {
            JOptionPane.showMessageDialog(null, "NO SE CARGO CORRECTAMENTE EL PRODUCTO");
            txtpro_nombre.setText(null);
            txtpro_nombre.grabFocus();
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtpro_nombre, "CARGUE UNA DESCRIPCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtpro_cant, "CARGUE UNA CANTIDAD")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtpro_precio, "CARGUE UN PRECIO")) {
            return false;
        }
        if(evecmb.getBoo_JCombobox_seleccionar(cmbmotivo, "SELECCIONE UN MOTIVO DE BAJA")){
            return false;
        }
        return true;
    }

    private void cargar_item_producto() {
        if (validar_item()) {
            String Sidpatrimonio_producto = String.valueOf(idpatrimonio_producto);
            String Sdescripcion = txtpro_nombre.getText();
            String Subicacion = ubicacion_item;
            String Sreferencia = referencia_item;
            String Scantidad = txtpro_cant.getText();
            String Sprecio_compra = txtpro_precio.getText();
            String Ssubtotal = txtpro_subtotal.getText();
            String Oprecio_compra = evejtf.getString_format_nro_entero1(txtpro_precio);
            String Osubtotal = evejtf.getString_format_nro_entero1(txtpro_subtotal);
            int idpatrimonio_baja_motivo=evecmb.getInt_seleccionar_COMBOBOX(conn, cmbmotivo, "idpatrimonio_baja_motivo", "nombre", "patrimonio_baja_motivo");
            String Oidpatrimonio_baja_motivo=String.valueOf(idpatrimonio_baja_motivo);
            String Oidpatrimonio_baja = "1";
            String dato[] = {Sidpatrimonio_producto, Sdescripcion, Subicacion, Sreferencia, Scantidad, Sprecio_compra, Ssubtotal, Oprecio_compra, Osubtotal, Oidpatrimonio_baja,Oidpatrimonio_baja_motivo};
            eveJtab.cargar_tabla_datos(tblproducto_item, model_itemf, dato);
            reestableser_item_producto();
            suma_total();
        }
    }

    private void reestableser_item_producto() {
        txtpro_nombre.setText(null);
        txtpro_cant.setText(null);
        txtpro_precio.setText(null);
        txtpro_subtotal.setText(null);
        ubicacion_item = "-";
        referencia_item = "-";
        idpatrimonio_producto = 0;
        cmbmotivo.setSelectedIndex(0);
        txtpro_nombre.grabFocus();
    }

    private void suma_total() {
        double Dsum_total = eveJtab.getDouble_sumar_tabla(tblproducto_item, 8);
        String Ssum_total = evejtf.getString_format_nro_decimal(Dsum_total);
        txtsuma_total.setText(Ssum_total);
    }

    private void boton_eliminar_item() {
        if (tblproducto_item.getSelectedRow() >= 0) {
            eveJtab.getBoolean_Eliminar_Fila(tblproducto_item, model_itemf);
            suma_total();
        } else {
            JOptionPane.showMessageDialog(this, "SELECCIONAR EL ITEM PARA ELIMINAR");
        }
    }

    private void cargar_dato_patrimonio_baja() {
        double Dmonto = eveJtab.getDouble_sumar_tabla(tblproducto_item, 8);
        ENTpb.setC3creado_por(creado_por);
        ENTpb.setC4estado(eveest.getEst_DE_BAJA());
        ENTpb.setC5observacion(txtobservacion.getText());
        ENTpb.setC6monto(Dmonto);
        BOpb.insertar_patrimonio_baja(ENTpb, tblproducto_item);
    }
    private void select_patrimonio_baja(){
        int fk_idpatrimonio_baja=eveJtab.getInt_select_id(tblpatrimonio_baja);
        String estado=eveJtab.getString_select(tblpatrimonio_baja, 3);
        if(estado.equals(eveest.getEst_Anulado())){
            btnanular_baja.setEnabled(false);
        }else{
            btnanular_baja.setEnabled(true);
        }
        DAOpbi.actualizar_tabla_patrimonio_baja_item(conn, tblpatrimonio_baja_item, fk_idpatrimonio_baja);
    }
    private void boton_anular_baja(){
        if(eveJtab.getBoolean_validar_select_mensaje(tblpatrimonio_baja,"SELECCIONE UNA BAJA PARA ANULAR")){
            int fk_idpatrimonio_baja=eveJtab.getInt_select_id(tblpatrimonio_baja);
            ENTpb.setC1idpatrimonio_baja(fk_idpatrimonio_baja);
            ENTpb.setC4estado(eveest.getEst_Anulado());
            BOpb.update_patrimonio_baja_anular(ENTpb);
            DAOpb.actualizar_tabla_patrimonio_baja(conn, tblpatrimonio_baja);
        }
    }
    public FrmPatrimonio_baja() {
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
        jPanel4 = new javax.swing.JPanel();
        txtpro_nombre = new javax.swing.JTextField();
        txtpro_cant = new javax.swing.JTextField();
        txtpro_precio = new javax.swing.JTextField();
        txtpro_subtotal = new javax.swing.JTextField();
        btnnuevo_producto = new javax.swing.JButton();
        cmbmotivo = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnnuevo_motivo = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jSbusca_producto = new javax.swing.JScrollPane();
        tblbusca_producto = new javax.swing.JTable();
        jSproducto_item = new javax.swing.JScrollPane();
        tblproducto_item = new javax.swing.JTable();
        txtsuma_total = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        txtid = new javax.swing.JTextField();
        btneliminar_item = new javax.swing.JButton();
        txtobservacion = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblpatrimonio_baja = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblpatrimonio_baja_item = new javax.swing.JTable();
        btnanular_baja = new javax.swing.JButton();

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

        panel_insertar.setBackground(new java.awt.Color(255, 204, 51));
        panel_insertar.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("PATRIMONIO PRODUCTO"));

        txtpro_nombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtpro_nombre.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRE:"));
        txtpro_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpro_nombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpro_nombreKeyReleased(evt);
            }
        });

        txtpro_cant.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        txtpro_cant.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtpro_cant.setBorder(javax.swing.BorderFactory.createTitledBorder("CANT:"));
        txtpro_cant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpro_cantKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpro_cantKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtpro_cantKeyTyped(evt);
            }
        });

        txtpro_precio.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        txtpro_precio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtpro_precio.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO:"));
        txtpro_precio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpro_precioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpro_precioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtpro_precioKeyTyped(evt);
            }
        });

        txtpro_subtotal.setEditable(false);
        txtpro_subtotal.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        txtpro_subtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtpro_subtotal.setBorder(javax.swing.BorderFactory.createTitledBorder("SUBTOTAL:"));

        btnnuevo_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_productoActionPerformed(evt);
            }
        });

        cmbmotivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("MOTIVO:");

        btnnuevo_motivo.setText("NUEVO");
        btnnuevo_motivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_motivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbmotivo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtpro_nombre, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtpro_cant, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtpro_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtpro_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnnuevo_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnnuevo_motivo)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtpro_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtpro_cant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtpro_precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtpro_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnnuevo_producto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbmotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnnuevo_motivo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPane1.setBackground(new java.awt.Color(255, 153, 102));
        jLayeredPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblbusca_producto.setModel(new javax.swing.table.DefaultTableModel(
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
        tblbusca_producto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblbusca_productoMouseReleased(evt);
            }
        });
        tblbusca_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblbusca_productoKeyPressed(evt);
            }
        });
        jSbusca_producto.setViewportView(tblbusca_producto);

        jLayeredPane1.add(jSbusca_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, 620, 220));

        tblproducto_item.setModel(new javax.swing.table.DefaultTableModel(
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
        jSproducto_item.setViewportView(tblproducto_item);

        jLayeredPane1.add(jSproducto_item, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 10, 830, 260));

        txtsuma_total.setFont(new java.awt.Font("Consolas", 0, 30)); // NOI18N
        txtsuma_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtsuma_total.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMA TOTAL:"));
        jLayeredPane1.add(txtsuma_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 320, 230, 50));

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 80, -1));

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, -1, -1));

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtid.setBorder(javax.swing.BorderFactory.createTitledBorder("ID:"));
        jLayeredPane1.add(txtid, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 320, 77, -1));

        btneliminar_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/eliminar.png"))); // NOI18N
        btneliminar_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_itemActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btneliminar_item, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 320, -1, 50));

        txtobservacion.setBorder(javax.swing.BorderFactory.createTitledBorder("OBSERBACION:"));
        jLayeredPane1.add(txtobservacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 830, -1));

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        tblpatrimonio_baja.setModel(new javax.swing.table.DefaultTableModel(
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
        tblpatrimonio_baja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblpatrimonio_bajaMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblpatrimonio_baja);

        tblpatrimonio_baja_item.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblpatrimonio_baja_item);

        btnanular_baja.setText("ANULAR BAJA");
        btnanular_baja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanular_bajaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnanular_baja, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnanular_baja)))
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
            .addComponent(jTab_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 872, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTab_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        ancho_item_producto();
        DAOpb.ancho_tabla_patrimonio_baja(tblpatrimonio_baja);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btnnuevo_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_productoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmPatrimonio_producto());
    }//GEN-LAST:event_btnnuevo_productoActionPerformed

    private void txtpro_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_nombreKeyReleased
        // TODO add your handling code here:
        buscar_producto();
    }//GEN-LAST:event_txtpro_nombreKeyReleased

    private void tblbusca_productoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbusca_productoMouseReleased
        // TODO add your handling code here:
        seleccionar_producto();
    }//GEN-LAST:event_tblbusca_productoMouseReleased

    private void txtpro_cantKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_cantKeyReleased
        // TODO add your handling code here:
        calcular_subtotal();
    }//GEN-LAST:event_txtpro_cantKeyReleased

    private void txtpro_precioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_precioKeyReleased
        // TODO add your handling code here:
//        evejtf.getDouble_format_nro_entero(txtpro_precio);
        calcular_subtotal();
    }//GEN-LAST:event_txtpro_precioKeyReleased

    private void txtpro_cantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_cantKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtpro_cantKeyTyped

    private void txtpro_precioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_precioKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtpro_precioKeyTyped

    private void txtpro_cantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_cantKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_item_producto();
        }
    }//GEN-LAST:event_txtpro_cantKeyPressed

    private void txtpro_precioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_precioKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_item_producto();
        }
    }//GEN-LAST:event_txtpro_precioKeyPressed

    private void btneliminar_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_itemActionPerformed
        // TODO add your handling code here:
        boton_eliminar_item();
    }//GEN-LAST:event_btneliminar_itemActionPerformed

    private void txtpro_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_nombreKeyPressed
        // TODO add your handling code here:
        eveJtab.seleccionar_tabla_flecha_abajo(evt, tblbusca_producto);
    }//GEN-LAST:event_txtpro_nombreKeyPressed

    private void tblbusca_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblbusca_productoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_producto();
        }
    }//GEN-LAST:event_tblbusca_productoKeyPressed

    private void tblpatrimonio_bajaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpatrimonio_bajaMouseReleased
        // TODO add your handling code here:
        select_patrimonio_baja();
    }//GEN-LAST:event_tblpatrimonio_bajaMouseReleased

    private void btnanular_bajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanular_bajaActionPerformed
        // TODO add your handling code here:
        boton_anular_baja();
    }//GEN-LAST:event_btnanular_bajaActionPerformed

    private void btnnuevo_motivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_motivoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmPatrimonio_baja_motivo());
    }//GEN-LAST:event_btnnuevo_motivoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanular_baja;
    private javax.swing.JButton btneliminar_item;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnnuevo_motivo;
    private javax.swing.JButton btnnuevo_producto;
    private javax.swing.JComboBox<String> cmbmotivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jSbusca_producto;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jSproducto_item;
    private javax.swing.JTabbedPane jTab_principal;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JTable tblbusca_producto;
    private javax.swing.JTable tblpatrimonio_baja;
    private javax.swing.JTable tblpatrimonio_baja_item;
    private javax.swing.JTable tblproducto_item;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtobservacion;
    private javax.swing.JTextField txtpro_cant;
    private javax.swing.JTextField txtpro_nombre;
    private javax.swing.JTextField txtpro_precio;
    private javax.swing.JTextField txtpro_subtotal;
    private javax.swing.JTextField txtsuma_total;
    // End of variables declaration//GEN-END:variables
}
