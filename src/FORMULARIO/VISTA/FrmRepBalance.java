/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import CONFIGURACION.ComputerInfo;
import CREAR_CSV.Crear_csv;
import Config_JSON.json_array_csv;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import FILTRO.ClaAuxFiltroVenta;
import FORMULARIO.DAO.DAO_balance;
import FORMULARIO.DAO.DAO_usuario;
import FORMULARIO.DAO.DAO_venta;
import FORMULARIO.ENTIDAD.habitacion_recepcion_temp;
//import FORMULARIO.DAO.DAO_cliente;
//import FORMULARIO.DAO.DAO_venta_alquiler;
//import FORMULARIO.ENTIDAD.cliente;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Digno
 */
public class FrmRepBalance extends javax.swing.JInternalFrame {

    private String nombre_formulario = "BALANCE GRAL";
    private EvenJFRAME evetbl = new EvenJFRAME();
    private Connection conn = ConnPostgres.getConnPosgres();
    private EvenJtable evejt = new EvenJtable();
    private EvenFecha evefec = new EvenFecha();
    private ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenConexion eveconn = new EvenConexion();
    private EvenRender everende = new EvenRender();
    private DAO_venta DAOven = new DAO_venta();
    private EvenCombobox evecmb = new EvenCombobox();
    private DAO_usuario DAOusu = new DAO_usuario();
    private DAO_balance DAObal = new DAO_balance();
    private Crear_csv ccsv = new Crear_csv();
    private json_array_csv jscsv = new json_array_csv();
    private ComputerInfo pcinf = new ComputerInfo();
    private habitacion_recepcion_temp ENThrt = new habitacion_recepcion_temp();
    private void abrir_formulario() {
        this.setTitle(nombre_formulario);
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
    }

//    private void boton_crear_csv() {
//        String carpeta_raiz = jscsv.getCarpeta_raiz();
//        String carpeta_local = jscsv.getCarpeta_local();
//        String carpeta_dropbox = jscsv.getCarpeta_dropbox();
//        int dias_filtro=45;
//        ccsv.limpiar_campo_tabla(conn, "caja_cierre_detalle", "fecha_creado", "descripcion", dias_filtro);
//        ccsv.CSV_eliminar_crear_copiar(conn, "caja_cierre_detalle", "fecha_creado", dias_filtro, carpeta_local, carpeta_raiz, carpeta_dropbox);
//        ccsv.CSV_eliminar_crear_copiar(conn, "caja_cierre_item", "fecha_creado", dias_filtro, carpeta_local, carpeta_raiz, carpeta_dropbox);
//        ccsv.CSV_eliminar_crear_copiar(conn, "caja_cierre", "fecha_creado", dias_filtro, carpeta_local, carpeta_raiz, carpeta_dropbox);
//    }
//
//    private void boton_progressbar() {
//        ENThrt.setHab_tiempo_menu(false);
//        String carpeta_local = jscsv.getCarpeta_local();
//        String nombre_computador = pcinf.getComputerName();
//        ccsv.leer_csv_insert_destino_timer_progressbar(conn, "caja_cierre_detalle", "admin_caja_cierre_detalle", "idadmin_caja_cierre_detalle",nombre_computador, carpeta_local);
//        ccsv.leer_csv_insert_destino_timer_progressbar(conn, "caja_cierre_item", "admin_caja_cierre_item", "idadmin_caja_cierre_item",nombre_computador, carpeta_local);
//        ccsv.leer_csv_insert_destino_timer_progressbar(conn, "caja_cierre", "admin_caja_cierre", "idadmin_caja_cierre",nombre_computador, carpeta_local);
//        JOptionPane.showMessageDialog(null,"INSERT DE TABLAS TERMINADO");
//        
//    }

    private void boton_buscar() {
        actualizar_tabla_caja_resumen();
        actualizar_tabla_gasto();
        actualizar_tabla_compra_carga();
        actualizar_tabla_producto_venta();
    }

    private void actualizar_tabla_caja_resumen() {
        String fec_caja = evefec.getFiltroSql_desde_hasta_campo(dcfecDesde, dcfecHasta, "cc.fecha_inicio");
        DAObal.actualizar_tabla_caja_resumen(conn, tblresumen_caja, fec_caja);
        double monto_ocupacion = evejt.getDouble_sumar_tabla(tblresumen_caja, 9);
        jFtotal_ocupacion.setValue(monto_ocupacion);
        double monto_consumo = evejt.getDouble_sumar_tabla(tblresumen_caja, 10);
        jFtotal_consumo.setValue(monto_consumo);
        double monto_egreso = evejt.getDouble_sumar_tabla(tblresumen_caja, 11);
        jFtotal_egreso.setValue(monto_egreso);
        double monto_saldo = evejt.getDouble_sumar_tabla(tblresumen_caja, 12);
        jFtotal_saldo.setValue(monto_saldo);
    }

    private void imprimir_tabla_caja_resumen() {
        String fec_caja = evefec.getFiltroSql_desde_hasta_campo(dcfecDesde, dcfecHasta, "cc.fecha_inicio");
        DAObal.imprimir_caja_resumen(conn, fec_caja);
    }

    private void actualizar_tabla_gasto() {
        String filtro_fecha = evefec.getFiltroSql_desde_hasta_campo(dcfecDesde, dcfecHasta, "g.fecha_creado");
        DAObal.actualizar_tabla_gasto(conn, tblgastos, filtro_fecha);
        double monto_caja = evejt.getDouble_sumar_tabla(tblgastos, 8);
        jFtotal_gasto_caja.setValue(monto_caja);
        double monto_admin = evejt.getDouble_sumar_tabla(tblgastos, 9);
        jFtotal_gasto_admin.setValue(monto_admin);
    }

    private void actualizar_tabla_compra_carga() {
        String filtro_fecha = evefec.getFiltroSql_desde_hasta_campo(dcfecDesde, dcfecHasta, "c.fecha_creado");
        DAObal.actualizar_tabla_compra(conn, tblcompra_carga_st, filtro_fecha);
        double monto_subtotal = evejt.getDouble_sumar_tabla(tblcompra_carga_st, 8);
        jFtotal_compra_subtotal.setValue(monto_subtotal);
    }

    private void imprimir_tabla_gasto_dia() {
        String filtro_fecha = evefec.getFiltroSql_desde_hasta_campo(dcfecDesde, dcfecHasta, "g.fecha_creado");
        DAObal.imprimir_gasto_dia(conn, filtro_fecha);
    }

    private void imprimir_tabla_gasto_tipo() {
        String filtro_fecha = evefec.getFiltroSql_desde_hasta_campo(dcfecDesde, dcfecHasta, "g.fecha_creado");
        DAObal.imprimir_gasto_tipo(conn, filtro_fecha);
    }

    private void imprimir_tabla_compra_dia() {
        String filtro_fecha = evefec.getFiltroSql_desde_hasta_campo(dcfecDesde, dcfecHasta, "c.fecha_creado");
        DAObal.imprimir_compra_dia(conn, filtro_fecha);
    }

    private void imprimir_tabla_compra_prod() {
        String filtro_fecha = evefec.getFiltroSql_desde_hasta_campo(dcfecDesde, dcfecHasta, "c.fecha_creado");
        DAObal.imprimir_compra_prod(conn, filtro_fecha);
    }

    private void actualizar_tabla_producto_venta() {
        String fec_caja = evefec.getFiltroSql_desde_hasta_campo(dcfecDesde, dcfecHasta, "cc.fecha_inicio");
        DAObal.actualizar_tabla_producto_venta(conn, tblproducto_venta, fec_caja);
        double monto_venta = evejt.getDouble_sumar_tabla(tblproducto_venta, 8);
        jFtotal_prod_venta.setValue(monto_venta);
        double monto_compra = evejt.getDouble_sumar_tabla(tblproducto_venta, 9);
        jFtotal_prod_compra.setValue(monto_compra);
        double monto_ganancia = evejt.getDouble_sumar_tabla(tblproducto_venta, 10);
        jFtotal_prod_ganancia.setValue(monto_ganancia);
    }

    private void imprimir_tabla_producto_venta() {
        String filtro_fecha = evefec.getFiltroSql_desde_hasta_campo(dcfecDesde, dcfecHasta, "cc.fecha_inicio");
        String fecha_desde = evefec.getfechaDCStringFormat(dcfecDesde, "yyyy-MM-dd");
        String fecha_hasta = evefec.getfechaDCStringFormat(dcfecHasta, "yyyy-MM-dd");
        String fecha = "Fecha Desde: " + fecha_desde + " Hasta: " + fecha_hasta;
        DAObal.imprimir_producto_venta(conn, fecha, filtro_fecha);
    }

    private void reestableser() {
        evefec.setFechaDCSistema(dcfecDesde);
        evefec.setFechaDCSistema(dcfecHasta);
    }

    public FrmRepBalance() {
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

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblresumen_caja = new javax.swing.JTable();
        btnimprimir_resumen_caja = new javax.swing.JButton();
        jFtotal_saldo = new javax.swing.JFormattedTextField();
        jFtotal_egreso = new javax.swing.JFormattedTextField();
        jFtotal_consumo = new javax.swing.JFormattedTextField();
        jFtotal_ocupacion = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblgastos = new javax.swing.JTable();
        btnimprimir_gastos = new javax.swing.JButton();
        jFtotal_gasto_caja = new javax.swing.JFormattedTextField();
        jFtotal_gasto_admin = new javax.swing.JFormattedTextField();
        btnimprimir_gasto_tipo = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblcompra_carga_st = new javax.swing.JTable();
        btnimprimir_compra_cargast_dia = new javax.swing.JButton();
        jFtotal_compra_subtotal = new javax.swing.JFormattedTextField();
        btnimprimir_compra_cargast_prod = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblproducto_venta = new javax.swing.JTable();
        btnimprimir_producto = new javax.swing.JButton();
        jFtotal_prod_venta = new javax.swing.JFormattedTextField();
        jFtotal_prod_compra = new javax.swing.JFormattedTextField();
        jFtotal_prod_ganancia = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        btnbuscar_fecha = new javax.swing.JButton();
        dcfecDesde = new com.toedter.calendar.JDateChooser();
        dcfecHasta = new com.toedter.calendar.JDateChooser();

        setClosable(true);
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLAS"));

        tblresumen_caja.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblresumen_caja);

        btnimprimir_resumen_caja.setText("IMPRIMIR");
        btnimprimir_resumen_caja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_resumen_cajaActionPerformed(evt);
            }
        });

        jFtotal_saldo.setBorder(javax.swing.BorderFactory.createTitledBorder("T.SALDO:"));
        jFtotal_saldo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_saldo.setFont(new java.awt.Font("Consolas", 1, 15)); // NOI18N

        jFtotal_egreso.setBorder(javax.swing.BorderFactory.createTitledBorder("T.EGRESO:"));
        jFtotal_egreso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_egreso.setFont(new java.awt.Font("Consolas", 1, 15)); // NOI18N

        jFtotal_consumo.setBorder(javax.swing.BorderFactory.createTitledBorder("T.CONSUMO:"));
        jFtotal_consumo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_consumo.setFont(new java.awt.Font("Consolas", 1, 15)); // NOI18N

        jFtotal_ocupacion.setBorder(javax.swing.BorderFactory.createTitledBorder("T.OCUPACION:"));
        jFtotal_ocupacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ocupacion.setFont(new java.awt.Font("Consolas", 1, 15)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnimprimir_resumen_caja, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 412, Short.MAX_VALUE)
                        .addComponent(jFtotal_ocupacion, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_egreso, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_saldo, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFtotal_saldo)
                            .addComponent(jFtotal_egreso)
                            .addComponent(jFtotal_consumo)
                            .addComponent(jFtotal_ocupacion)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnimprimir_resumen_caja, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("RESUMEN CAJA", jPanel3);

        tblgastos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblgastos);

        btnimprimir_gastos.setText("IMPRIMIR-DIA");
        btnimprimir_gastos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_gastosActionPerformed(evt);
            }
        });

        jFtotal_gasto_caja.setBorder(javax.swing.BorderFactory.createTitledBorder("T.CAJA:"));
        jFtotal_gasto_caja.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_gasto_caja.setFont(new java.awt.Font("Consolas", 1, 15)); // NOI18N

        jFtotal_gasto_admin.setBorder(javax.swing.BorderFactory.createTitledBorder("T.ADMIN:"));
        jFtotal_gasto_admin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_gasto_admin.setFont(new java.awt.Font("Consolas", 1, 15)); // NOI18N

        btnimprimir_gasto_tipo.setText("IMPRIMIR-TIPO");
        btnimprimir_gasto_tipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_gasto_tipoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnimprimir_gastos, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btnimprimir_gasto_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(859, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGap(844, 844, 844)
                            .addComponent(jFtotal_gasto_caja, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jFtotal_gasto_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap()))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(447, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnimprimir_gasto_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnimprimir_gastos, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10, 10, 10)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFtotal_gasto_admin)
                        .addComponent(jFtotal_gasto_caja))
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("GASTOS", jPanel4);

        tblcompra_carga_st.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblcompra_carga_st);

        btnimprimir_compra_cargast_dia.setText("IMPRIMIR-DIA");
        btnimprimir_compra_cargast_dia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_compra_cargast_diaActionPerformed(evt);
            }
        });

        jFtotal_compra_subtotal.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL:"));
        jFtotal_compra_subtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_compra_subtotal.setFont(new java.awt.Font("Consolas", 1, 15)); // NOI18N

        btnimprimir_compra_cargast_prod.setText("IMPRIMIR-PROD");
        btnimprimir_compra_cargast_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_compra_cargast_prodActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(btnimprimir_compra_cargast_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(867, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(btnimprimir_compra_cargast_dia, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(856, 856, 856)
                            .addComponent(jFtotal_compra_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap()))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(444, 444, 444)
                .addComponent(btnimprimir_compra_cargast_prod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(jFtotal_compra_subtotal))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnimprimir_compra_cargast_dia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("COMPRA CARGA STOCK", jPanel5);

        tblproducto_venta.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblproducto_venta);

        btnimprimir_producto.setText("IMPRIMIR");
        btnimprimir_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_productoActionPerformed(evt);
            }
        });

        jFtotal_prod_venta.setBorder(javax.swing.BorderFactory.createTitledBorder("T.VENTA:"));
        jFtotal_prod_venta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_prod_venta.setFont(new java.awt.Font("Consolas", 1, 15)); // NOI18N

        jFtotal_prod_compra.setBorder(javax.swing.BorderFactory.createTitledBorder("T.COMPRA:"));
        jFtotal_prod_compra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_prod_compra.setFont(new java.awt.Font("Consolas", 1, 15)); // NOI18N

        jFtotal_prod_ganancia.setBorder(javax.swing.BorderFactory.createTitledBorder("T.GANANCIA:"));
        jFtotal_prod_ganancia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_prod_ganancia.setFont(new java.awt.Font("Consolas", 1, 15)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1154, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane4)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(btnimprimir_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(560, 560, 560)
                            .addComponent(jFtotal_prod_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jFtotal_prod_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jFtotal_prod_ganancia, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap()))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 510, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jFtotal_prod_ganancia)
                                .addComponent(jFtotal_prod_compra)
                                .addComponent(jFtotal_prod_venta)))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnimprimir_producto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("PRODUCTO VENDIDO", jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("FILTRO FECHA"));

        btnbuscar_fecha.setText("BUSCAR");
        btnbuscar_fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_fechaActionPerformed(evt);
            }
        });

        dcfecDesde.setBorder(javax.swing.BorderFactory.createTitledBorder("DESDE:"));
        dcfecDesde.setDateFormatString("yyyy-MM-dd");
        dcfecDesde.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        dcfecHasta.setBorder(javax.swing.BorderFactory.createTitledBorder("HASTA:"));
        dcfecHasta.setDateFormatString("yyyy-MM-dd");
        dcfecHasta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dcfecDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dcfecHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnbuscar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(336, 336, 336))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dcfecDesde, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(dcfecHasta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 9, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnbuscar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
//        DAOva.ancho_tabla_venta_alquiler_rep_1(tblfiltro_venta_alquiler);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnbuscar_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_fechaActionPerformed
        // TODO add your handling code here:
        boton_buscar();
    }//GEN-LAST:event_btnbuscar_fechaActionPerformed

    private void btnimprimir_resumen_cajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_resumen_cajaActionPerformed
        // TODO add your handling code here:
        imprimir_tabla_caja_resumen();
    }//GEN-LAST:event_btnimprimir_resumen_cajaActionPerformed

    private void btnimprimir_gastosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_gastosActionPerformed
        // TODO add your handling code here:
        imprimir_tabla_gasto_dia();
    }//GEN-LAST:event_btnimprimir_gastosActionPerformed

    private void btnimprimir_gasto_tipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_gasto_tipoActionPerformed
        // TODO add your handling code here:
        imprimir_tabla_gasto_tipo();
    }//GEN-LAST:event_btnimprimir_gasto_tipoActionPerformed

    private void btnimprimir_compra_cargast_diaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_compra_cargast_diaActionPerformed
        // TODO add your handling code here:
        imprimir_tabla_compra_dia();
    }//GEN-LAST:event_btnimprimir_compra_cargast_diaActionPerformed

    private void btnimprimir_compra_cargast_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_compra_cargast_prodActionPerformed
        // TODO add your handling code here:
        imprimir_tabla_compra_prod();
    }//GEN-LAST:event_btnimprimir_compra_cargast_prodActionPerformed

    private void btnimprimir_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_productoActionPerformed
        // TODO add your handling code here:
        imprimir_tabla_producto_venta();
    }//GEN-LAST:event_btnimprimir_productoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscar_fecha;
    private javax.swing.JButton btnimprimir_compra_cargast_dia;
    private javax.swing.JButton btnimprimir_compra_cargast_prod;
    private javax.swing.JButton btnimprimir_gasto_tipo;
    private javax.swing.JButton btnimprimir_gastos;
    private javax.swing.JButton btnimprimir_producto;
    private javax.swing.JButton btnimprimir_resumen_caja;
    private com.toedter.calendar.JDateChooser dcfecDesde;
    private com.toedter.calendar.JDateChooser dcfecHasta;
    private javax.swing.JFormattedTextField jFtotal_compra_subtotal;
    private javax.swing.JFormattedTextField jFtotal_consumo;
    private javax.swing.JFormattedTextField jFtotal_egreso;
    private javax.swing.JFormattedTextField jFtotal_gasto_admin;
    private javax.swing.JFormattedTextField jFtotal_gasto_caja;
    private javax.swing.JFormattedTextField jFtotal_ocupacion;
    private javax.swing.JFormattedTextField jFtotal_prod_compra;
    private javax.swing.JFormattedTextField jFtotal_prod_ganancia;
    private javax.swing.JFormattedTextField jFtotal_prod_venta;
    private javax.swing.JFormattedTextField jFtotal_saldo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblcompra_carga_st;
    private javax.swing.JTable tblgastos;
    private javax.swing.JTable tblproducto_venta;
    private javax.swing.JTable tblresumen_caja;
    // End of variables declaration//GEN-END:variables
}
