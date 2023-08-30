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
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Digno
 */
public class FrmPatrimonio_carga extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private patrimonio_carga ENTpc = new patrimonio_carga();
    private DAO_patrimonio_carga DAOpc = new DAO_patrimonio_carga();
    private BO_patrimonio_carga BOpc = new BO_patrimonio_carga();
    private patrimonio_producto ENTpp = new patrimonio_producto();
    private DAO_patrimonio_producto DAOpp = new DAO_patrimonio_producto();
    private patrimonio_ubicacion ENTpu = new patrimonio_ubicacion();
    private DAO_patrimonio_ubicacion DAOpu = new DAO_patrimonio_ubicacion();
    private DAO_patrimonio_carga_item DAOpci=new DAO_patrimonio_carga_item();
    private DAO_caja_cierre DAOcc = new DAO_caja_cierre();
    private EvenJTextField evejtf = new EvenJTextField();
    private DefaultTableModel model_itemf = new DefaultTableModel();
    private EvenEstado eveest = new EvenEstado();
    Connection conn = ConnPostgres.getConnPosgres();
    usuario ENTusu = new usuario();
    private String nombreTabla_pri = "PATRIMONIO CARGA";
    private String nombreTabla_sec = "PATRIMONIO PRODUCTO";
    private String creado_por = "digno";
    private int idpatrimonio_producto;
    private String referencia_item;
    private String ubicacion_item;

    private void abrir_formulario() {
        this.setTitle(nombreTabla_pri);
        evetbl.centrar_formulario_internalframa(this);
        creado_por = ENTusu.getGlobal_nombre();
        DAOpc.actualizar_tabla_patrimonio_carga(conn, tblpatrimonio_carga);
        crear_item_producto();
        reestableser();
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
            cargar_dato_patrimonio_carga();
            DAOcc.exportar_excel_patrimonio_carga_N3(conn);
            reestableser();
        }
    }

    private void reestableser() {
        DAOpc.actualizar_tabla_patrimonio_carga(conn, tblpatrimonio_carga);
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

    private void buscar_producto_nombre() {
        if (txtpro_nombre.getText().trim().length() > 0) {
            jSbusca_producto.setVisible(true);
        }
        DAOpp.actualizar_tabla_patrimonio_producto_carga(conn, tblbusca_producto, "pp.nombre",txtpro_nombre);
    }
    private void buscar_producto_referencia() {
        if (txtpro_referencia.getText().trim().length() > 0) {
            jSbusca_producto.setVisible(true);
        }
        DAOpp.actualizar_tabla_patrimonio_producto_carga(conn, tblbusca_producto, "pp.referencia",txtpro_referencia);
    }
    private void seleccionar_producto() {
        int id = eveJtab.getInt_select_id(tblbusca_producto);
        String nombre = eveJtab.getString_select(tblbusca_producto, 1);
        jSbusca_producto.setVisible(false);
        DAOpp.cargar_patrimonio_producto(conn, ENTpp, id);
        idpatrimonio_producto = id;
        txtpro_nombre.setText(nombre);
        txtpro_referencia.setText(ENTpp.getC5referencia());
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
        String dato[] = {"ID", "DESCRIPCION", "UBICACION", "REFERENCIA", "CANT", "PRECIO", "SUBTOTAL", "Oprecio", "Osubtotal", "Oidpatrimonio_carga"};
        eveJtab.crear_tabla_datos(tblproducto_item, model_itemf, dato);
        ancho_item_producto();
    }

    private void ancho_item_producto() {
        int Ancho[] = {5, 50, 10, 10, 5, 10, 10, 1, 1, 1};
        eveJtab.setAnchoColumnaJtable(tblproducto_item, Ancho);
        eveJtab.alinear_derecha_columna(tblproducto_item, 4);
        eveJtab.alinear_derecha_columna(tblproducto_item, 5);
        eveJtab.alinear_derecha_columna(tblproducto_item, 6);
        eveJtab.ocultar_columna(tblproducto_item, 7);
        eveJtab.ocultar_columna(tblproducto_item, 8);
        eveJtab.ocultar_columna(tblproducto_item, 9);
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
            String Oidpatrimonio_carga = "1";
            String dato[] = {Sidpatrimonio_producto, Sdescripcion, Subicacion, Sreferencia, Scantidad, Sprecio_compra, Ssubtotal, Oprecio_compra, Osubtotal, Oidpatrimonio_carga};
            eveJtab.cargar_tabla_datos(tblproducto_item, model_itemf, dato);
            reestableser_item_producto();
            suma_total();
        }
    }

    private void reestableser_item_producto() {
        txtpro_nombre.setText(null);
        txtpro_referencia.setText(null);
        txtpro_cant.setText(null);
        txtpro_precio.setText(null);
        txtpro_subtotal.setText(null);
        ubicacion_item = "-";
        referencia_item = "-";
        idpatrimonio_producto = 0;
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

    private void cargar_dato_patrimonio_carga() {
        double Dmonto = eveJtab.getDouble_sumar_tabla(tblproducto_item, 8);
        ENTpc.setC3creado_por(creado_por);
        ENTpc.setC4estado(eveest.getEst_Cargado());
        ENTpc.setC5observacion(txtobservacion.getText());
        ENTpc.setC6monto(Dmonto);
        BOpc.insertar_patrimonio_carga(ENTpc, tblproducto_item);
    }
    private void select_patrimonio_carga(){
        int fk_idpatrimonio_carga=eveJtab.getInt_select_id(tblpatrimonio_carga);
        String estado=eveJtab.getString_select(tblpatrimonio_carga, 3);
        if(estado.equals(eveest.getEst_Anulado())){
            btnanular_carga.setEnabled(false);
        }else{
            btnanular_carga.setEnabled(true);
        }
        DAOpci.actualizar_tabla_patrimonio_carga_item(conn, tblpatrimonio_carga_item, fk_idpatrimonio_carga);
    }
    private void boton_anular_carga(){
        if(eveJtab.getBoolean_validar_select_mensaje(tblpatrimonio_carga,"SELECCIONE UNA CARGA PARA ANULAR")){
            
            int fk_idpatrimonio_carga=eveJtab.getInt_select_id(tblpatrimonio_carga);
            ENTpc.setC1idpatrimonio_carga(fk_idpatrimonio_carga);
            ENTpc.setC4estado(eveest.getEst_Anulado());
            BOpc.update_patrimonio_carga_anular(ENTpc);
            DAOpc.actualizar_tabla_patrimonio_carga(conn, tblpatrimonio_carga);
        }
    }
    public FrmPatrimonio_carga() {
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
        txtpro_referencia = new javax.swing.JTextField();
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
        tblpatrimonio_carga = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblpatrimonio_carga_item = new javax.swing.JTable();
        btnanular_carga = new javax.swing.JButton();

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

        txtpro_referencia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtpro_referencia.setBorder(javax.swing.BorderFactory.createTitledBorder("REFERENCIA:"));
        txtpro_referencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpro_referenciaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpro_referenciaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(txtpro_nombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpro_referencia, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpro_cant, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpro_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpro_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnnuevo_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtpro_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtpro_cant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtpro_precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtpro_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtpro_referencia, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(btnnuevo_producto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

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

        jLayeredPane1.add(jSbusca_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, 750, 220));

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

        jLayeredPane1.add(jSproducto_item, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 10, 830, 300));

        txtsuma_total.setFont(new java.awt.Font("Consolas", 0, 30)); // NOI18N
        txtsuma_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtsuma_total.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMA TOTAL:"));
        jLayeredPane1.add(txtsuma_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 360, 230, 50));

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 80, -1));

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, -1, -1));

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtid.setBorder(javax.swing.BorderFactory.createTitledBorder("ID:"));
        jLayeredPane1.add(txtid, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 360, 77, -1));

        btneliminar_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/eliminar.png"))); // NOI18N
        btneliminar_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_itemActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btneliminar_item, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 360, -1, 50));

        txtobservacion.setBorder(javax.swing.BorderFactory.createTitledBorder("OBSERBACION:"));
        jLayeredPane1.add(txtobservacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 315, 830, -1));

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
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1))
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

        tblpatrimonio_carga.setModel(new javax.swing.table.DefaultTableModel(
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
        tblpatrimonio_carga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblpatrimonio_cargaMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblpatrimonio_carga);

        tblpatrimonio_carga_item.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblpatrimonio_carga_item);

        btnanular_carga.setText("ANULAR CARGA");
        btnanular_carga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanular_cargaActionPerformed(evt);
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
                .addComponent(btnanular_carga, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnanular_carga)))
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
        DAOpc.ancho_tabla_patrimonio_carga(tblpatrimonio_carga);
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
        buscar_producto_nombre();
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

    private void tblpatrimonio_cargaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpatrimonio_cargaMouseReleased
        // TODO add your handling code here:
        select_patrimonio_carga();
    }//GEN-LAST:event_tblpatrimonio_cargaMouseReleased

    private void btnanular_cargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanular_cargaActionPerformed
        // TODO add your handling code here:
        boton_anular_carga();
    }//GEN-LAST:event_btnanular_cargaActionPerformed

    private void txtpro_referenciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_referenciaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpro_referenciaKeyPressed

    private void txtpro_referenciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_referenciaKeyReleased
        // TODO add your handling code here:
        buscar_producto_referencia();
    }//GEN-LAST:event_txtpro_referenciaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanular_carga;
    private javax.swing.JButton btneliminar_item;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnnuevo_producto;
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
    private javax.swing.JTable tblpatrimonio_carga;
    private javax.swing.JTable tblpatrimonio_carga_item;
    private javax.swing.JTable tblproducto_item;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtobservacion;
    private javax.swing.JTextField txtpro_cant;
    private javax.swing.JTextField txtpro_nombre;
    private javax.swing.JTextField txtpro_precio;
    private javax.swing.JTextField txtpro_referencia;
    private javax.swing.JTextField txtpro_subtotal;
    private javax.swing.JTextField txtsuma_total;
    // End of variables declaration//GEN-END:variables
}
