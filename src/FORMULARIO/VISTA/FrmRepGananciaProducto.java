/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FILTRO.ClaAuxFiltroVenta;
//import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_producto;
import FORMULARIO.DAO.DAO_producto_categoria;
import FORMULARIO.DAO.DAO_producto_marca;
import FORMULARIO.DAO.DAO_usuario;
import FORMULARIO.DAO.DAO_venta;
//import FORMULARIO.ENTIDAD.cliente;
import FORMULARIO.ENTIDAD.producto_categoria;
import FORMULARIO.ENTIDAD.producto_marca;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Digno
 */
public class FrmRepGananciaProducto extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmRepVenta
     */
    EvenJFRAME evetbl = new EvenJFRAME();
    Connection conn = ConnPostgres.getConnPosgres();
    private EvenFecha evefec = new EvenFecha();
    private ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private EvenConexion eveconn = new EvenConexion();
    private DAO_producto_categoria DAOpc = new DAO_producto_categoria();
    private DAO_producto_marca DAOpm = new DAO_producto_marca();
    private DAO_producto DAOp = new DAO_producto();
    private producto_categoria ENTpc = new producto_categoria();
    private producto_marca ENTpm = new producto_marca();
    private cla_color_pelete clacolor = new cla_color_pelete();
    private DAO_usuario DAOusu = new DAO_usuario();
    private int fk_idproducto_categoria_local;
    private int fk_idproducto_marca_local;

    private void abrir_formulario() {
        this.setTitle("REPORTE INVENTARIO VALORIZADO POR VENTA");
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        DAOusu.cargar_usuario_combo(conn, cmbusuario);
        evefec.cargar_combobox_intervalo_fecha(cmbfecha_caja_cierre);
    }

    private String suma_filtro() {
        String suma_filtro = "";
        String filtro_categoria = "";
        String filtro_stock = "";
        String filtro_usu = "";
        String filtro_fec = "";
        String filtro_marca = "";
        if (fk_idproducto_categoria_local > 0) {
            filtro_categoria = " and pc.idproducto_categoria=" + fk_idproducto_categoria_local + "\n";
        }
        if (fk_idproducto_marca_local > 0) {
            filtro_marca = " and pm.idproducto_marca=" + fk_idproducto_marca_local + "\n";
        }
        int idusuario = DAOusu.getInt_idusuario_combo(conn, cmbusuario);
        filtro_fec = evefec.getIntervalo_fecha_combobox(cmbfecha_caja_cierre, "v.fecha_creado");
        if (idusuario > 0) {
            filtro_usu = " and v.fk_idusuario=" + idusuario;
        }
        suma_filtro = filtro_categoria + filtro_marca + filtro_stock + filtro_usu + filtro_fec;
        return suma_filtro;
    }

    private void boton_imprimir() {
        boton_calcular();
        String desc_filtro = "FILTRO: Fecha:" + cmbfecha_caja_cierre.getSelectedItem().toString() + "/ Usuario:" + cmbusuario.getSelectedItem().toString();
        DAOp.imprimir_rep_ganancia_producto(conn, suma_filtro(), desc_filtro);
    }

    private void boton_calcular() {
        suma_inventario_valor_precio(conn, suma_filtro());
    }

    private void reestableser() {
        txtbucar_categoria.setText(null);
        txtbucar_marca.setText(null);
        fk_idproducto_categoria_local = 0;
        fk_idproducto_marca_local = 0;
        suma_inventario_valor_precio(conn, suma_filtro());
    }

    private void seleccionar_cargar_categoria() {
        fk_idproducto_categoria_local = eveconn.getInt_Solo_seleccionar_JLista(conn, jList_categoria, "producto_categoria", "nombre", "idproducto_categoria");
        DAOpc.cargar_producto_categoria(conn, ENTpc, fk_idproducto_categoria_local);
        txtbucar_categoria.setText(ENTpc.getC4nombre());
        txtbucar_marca.grabFocus();
        suma_inventario_valor_precio(conn, suma_filtro());
    }

    private void seleccionar_cargar_marca() {
        fk_idproducto_marca_local = eveconn.getInt_Solo_seleccionar_JLista(conn, jList_marca, "producto_marca", "nombre", "idproducto_marca");
        DAOpm.cargar_producto_marca(conn, ENTpm, fk_idproducto_marca_local);
        txtbucar_marca.setText(ENTpm.getC4nombre());
        btnimprimir.grabFocus();
        suma_inventario_valor_precio(conn, suma_filtro());
    }
    private boolean getboo_validar_suma_precio(){
        boolean es_suma=false;
        if(txtbucar_marca.getText().trim().length() == 0){
            es_suma=true;
        }
        if(txtbucar_categoria.getText().trim().length() == 0){
            es_suma=true;
        }
        if(cmbfecha_caja_cierre.getSelectedIndex()==0){
            es_suma=true;
        }
        if(cmbusuario.getSelectedIndex()==0){
            es_suma=true;
        }
        return es_suma;
    }
    private void suma_inventario_valor_precio(Connection conn, String filtro) {
        if (getboo_validar_suma_precio()) {
            String titulo = "suma_inventario_valor_precio";
            String sql = "select \n"
                    + "sum(vi.cantidad) as cant,\n"
                    + "sum(vi.cantidad*vi.precio_venta) as ttventa,\n"
                    + "sum(vi.cantidad*vi.precio_compra) as ttcompra,\n"
                    + "((sum(vi.cantidad*vi.precio_venta))-(sum(vi.cantidad*vi.precio_compra))) as ttsaldo\n"
                    + "from venta v,venta_item vi,producto p, producto_categoria pc,producto_marca pm,usuario u  \n"
                    + "where v.estado='TERMINADO'\n"
                    + "and v.idventa=vi.fk_idventa \n"
                    + "and vi.fk_idproducto=p.idproducto \n"
                    + "and p.fk_idproducto_categoria=pc.idproducto_categoria \n"
                    + "and p.fk_idproducto_marca=pm.idproducto_marca \n"
                    + "and v.fk_idusuario=u.idusuario \n" + filtro;
            try {
                ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
                if (rs.next()) {
                    double tt_venta = rs.getDouble("ttventa");
                    double tt_compra = rs.getDouble("ttcompra");
                    double ttsaldo = rs.getDouble("ttsaldo");
                    jFtotal_inventario_venta.setValue(tt_venta);
                    jFtotal_inventario_compra.setValue(tt_compra);
                    jFtotal_ganancia.setValue(ttsaldo);
                }
            } catch (Exception e) {
                evemen.mensaje_error(e, sql, titulo);
            }
        } else {
            jFtotal_inventario_venta.setValue(0);
            jFtotal_inventario_compra.setValue(0);
            jFtotal_ganancia.setValue(0);
        }
    }

    public FrmRepGananciaProducto() {
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

        panel_cate_mar = new javax.swing.JPanel();
        jList_categoria = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        txtbucar_categoria = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtbucar_marca = new javax.swing.JTextField();
        jList_marca = new javax.swing.JList<>();
        btnimprimir = new javax.swing.JButton();
        jFtotal_inventario_venta = new javax.swing.JFormattedTextField();
        jFtotal_inventario_compra = new javax.swing.JFormattedTextField();
        jFtotal_ganancia = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        cmbfecha_caja_cierre = new javax.swing.JComboBox<>();
        cmbusuario = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

        setClosable(true);

        panel_cate_mar.setBorder(javax.swing.BorderFactory.createTitledBorder("INVENTARIO VALORIZADO"));

        jList_categoria.setBackground(new java.awt.Color(204, 204, 255));
        jList_categoria.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_categoria.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_categoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_categoriaMouseReleased(evt);
            }
        });
        jList_categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_categoriaKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("CATEGORIA:");

        txtbucar_categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucar_categoriaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucar_categoriaKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("MARCA:");

        txtbucar_marca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucar_marcaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucar_marcaKeyReleased(evt);
            }
        });

        jList_marca.setBackground(new java.awt.Color(204, 204, 255));
        jList_marca.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_marca.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_marca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_marcaMouseReleased(evt);
            }
        });
        jList_marca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_marcaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panel_cate_marLayout = new javax.swing.GroupLayout(panel_cate_mar);
        panel_cate_mar.setLayout(panel_cate_marLayout);
        panel_cate_marLayout.setHorizontalGroup(
            panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_cate_marLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jList_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_cate_marLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbucar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_cate_marLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbucar_marca, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
                    .addComponent(jList_marca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_cate_marLayout.setVerticalGroup(
            panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cate_marLayout.createSequentialGroup()
                .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_cate_marLayout.createSequentialGroup()
                        .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtbucar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jList_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_cate_marLayout.createSequentialGroup()
                        .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtbucar_marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jList_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 21, Short.MAX_VALUE))
        );

        btnimprimir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnimprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ult_print.png"))); // NOI18N
        btnimprimir.setText("IMPRIMIR");
        btnimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirActionPerformed(evt);
            }
        });

        jFtotal_inventario_venta.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMA STOCK X VENTA"));
        jFtotal_inventario_venta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFtotal_inventario_venta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_inventario_venta.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        jFtotal_inventario_compra.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMA STOCK X COMPRA"));
        jFtotal_inventario_compra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFtotal_inventario_compra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_inventario_compra.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        jFtotal_ganancia.setBorder(javax.swing.BorderFactory.createTitledBorder("GANANCIA"));
        jFtotal_ganancia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFtotal_ganancia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ganancia.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        jLabel1.setText("Fecha:");

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

        jLabel2.setText("Usuario:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cmbfecha_caja_cierre, javax.swing.GroupLayout.Alignment.LEADING, 0, 159, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(panel_cate_mar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jFtotal_inventario_venta, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                            .addComponent(jFtotal_inventario_compra, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                            .addComponent(jFtotal_ganancia, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                        .addGap(11, 11, 11))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnimprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel_cate_mar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbfecha_caja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jFtotal_inventario_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_inventario_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_ganancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnimprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirActionPerformed
        // TODO add your handling code here:
        boton_imprimir();
    }//GEN-LAST:event_btnimprimirActionPerformed

    private void jList_categoriaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_categoriaMouseReleased
        // TODO add your handling code here:
        seleccionar_cargar_categoria();
    }//GEN-LAST:event_jList_categoriaMouseReleased

    private void jList_categoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_categoriaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_cargar_categoria();
        }
    }//GEN-LAST:event_jList_categoriaKeyPressed

    private void txtbucar_categoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucar_categoriaKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_categoria);
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            txtbucarCliente_telefono.grabFocus();
//        }
    }//GEN-LAST:event_txtbucar_categoriaKeyPressed

    private void txtbucar_categoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucar_categoriaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtbucar_categoria, jList_categoria, "producto_categoria", "nombre", "nombre", 4);
        if (txtbucar_categoria.getText().trim().length() == 0) {
            fk_idproducto_categoria_local = 0;
            suma_inventario_valor_precio(conn, suma_filtro());
        }
    }//GEN-LAST:event_txtbucar_categoriaKeyReleased

    private void jList_marcaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_marcaMouseReleased
        // TODO add your handling code here:
        seleccionar_cargar_marca();
    }//GEN-LAST:event_jList_marcaMouseReleased

    private void jList_marcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_marcaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_cargar_marca();
        }
    }//GEN-LAST:event_jList_marcaKeyPressed

    private void txtbucar_marcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucar_marcaKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_marca);
    }//GEN-LAST:event_txtbucar_marcaKeyPressed

    private void txtbucar_marcaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucar_marcaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtbucar_marca, jList_marca, "producto_marca", "nombre", "nombre", 4);
        if (txtbucar_marca.getText().trim().length() == 0) {
            fk_idproducto_marca_local = 0;
            suma_inventario_valor_precio(conn, suma_filtro());
        }
    }//GEN-LAST:event_txtbucar_marcaKeyReleased

    private void cmbfecha_caja_cierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbfecha_caja_cierreActionPerformed
        // TODO add your handling code here:
        suma_inventario_valor_precio(conn, suma_filtro());
//        actualizar_tabla_caja_cierre();
    }//GEN-LAST:event_cmbfecha_caja_cierreActionPerformed

    private void cmbusuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbusuarioActionPerformed
        // TODO add your handling code here:
        suma_inventario_valor_precio(conn, suma_filtro());
//        actualizar_tabla_caja_cierre();
    }//GEN-LAST:event_cmbusuarioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnimprimir;
    private javax.swing.JComboBox<String> cmbfecha_caja_cierre;
    private javax.swing.JComboBox<String> cmbusuario;
    private javax.swing.JFormattedTextField jFtotal_ganancia;
    private javax.swing.JFormattedTextField jFtotal_inventario_compra;
    private javax.swing.JFormattedTextField jFtotal_inventario_venta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList_categoria;
    private javax.swing.JList<String> jList_marca;
    private javax.swing.JPanel panel_cate_mar;
    private javax.swing.JTextField txtbucar_categoria;
    private javax.swing.JTextField txtbucar_marca;
    // End of variables declaration//GEN-END:variables
}
