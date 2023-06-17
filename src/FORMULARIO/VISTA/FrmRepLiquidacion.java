/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import FILTRO.ClaAuxFiltroVenta;
import FORMULARIO.DAO.DAO_rh_liquidacion;
import FORMULARIO.DAO.DAO_transaccion_banco;
import FORMULARIO.DAO.DAO_usuario;
import FORMULARIO.DAO.DAO_venta;
//import FORMULARIO.DAO.DAO_cliente;
//import FORMULARIO.DAO.DAO_venta_alquiler;
//import FORMULARIO.ENTIDAD.cliente;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Digno
 */
public class FrmRepLiquidacion extends javax.swing.JInternalFrame {

    private String nombre_formulario = "REPORTE LIQUIDACION";
    private EvenJFRAME evetbl = new EvenJFRAME();
    private Connection conn = ConnPostgres.getConnPosgres();
    private EvenJtable evejt = new EvenJtable();
    private EvenFecha evefec = new EvenFecha();
    private ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenConexion eveconn = new EvenConexion();
    private EvenRender everende = new EvenRender();
    private DAO_rh_liquidacion DAOv = new DAO_rh_liquidacion();
    private EvenCombobox evecmb = new EvenCombobox();
    private DAO_usuario DAOusu = new DAO_usuario();
    private EvenRender everend = new EvenRender();
    private int tipo_seleccionar_grupo;
    private int idpersona;
    private int imes;
    private String filtro_tb = "";

    private void abrir_formulario() {
        this.setTitle(nombre_formulario);
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        tabla_grupo();
        tabla_liquidacion();
    }

    private String filtro_fecha() {
        String filtro = "";
        String fecDesde = evefec.getfechaDCStringFormat(dcfecDesde, "yyyy-MM-dd");
        String fecHasta = evefec.getfechaDCStringFormat(dcfecHasta, "yyyy-MM-dd");
        filtro = "and date(v.fecha_creado)>='" + fecDesde + "' and date(v.fecha_creado)<='" + fecHasta + "' ";

        return filtro;
    }

    private void actualizar_tabla() {

        double monto_consumo = evejt.getDouble_sumar_tabla(tblliquidacion, 15);
        double monto_total = evejt.getDouble_sumar_tabla(tblliquidacion, 16);
        jFmonto_liquidacion.setValue(monto_consumo);
        int cant = tblliquidacion.getRowCount();
        jFcantidad.setValue(cant);
    }

    private void tabla_grupo() {
        String sql = "";
        tipo_seleccionar_grupo++;
        if (tipo_seleccionar_grupo > 3) {
            tipo_seleccionar_grupo = 3;
        }
        if (tipo_seleccionar_grupo == 1) {
            sql = "select p.idpersona as idp,(p.nombre||'-'||p.ruc) as persona,\n"
                    + "TRIM(to_char(sum(v.monto_liquidacion),'999G999G999')) as monto\n"
                    + "from rh_liquidacion v,persona p\n"
                    + "where v.fk_idpersona=p.idpersona\n"
                    + "and (v.estado='CERRADO' or v.estado='EMITIDO')\n" + filtro_fecha()
                    + " group by 1,2\n"
                    + "order by 2 desc";
            actualizar_tabla_liquidacion_grupo(conn, tblgrupo, sql);
        } else if (tipo_seleccionar_grupo == 2) {
            sql = "select \n"
                    + "TRIM(to_char(date_part('month',v.fecha_creado),'99')) as imes,\n"
                    + "case \n"
                    + "when date_part('month',v.fecha_creado)=1 then 'ENERO'\n"
                    + "when date_part('month',v.fecha_creado)=2 then 'FEBRERO'\n"
                    + "when date_part('month',v.fecha_creado)=3 then 'MARZO'\n"
                    + "when date_part('month',v.fecha_creado)=4 then 'ABRIL'\n"
                    + "when date_part('month',v.fecha_creado)=5 then 'MAYO'\n"
                    + "when date_part('month',v.fecha_creado)=6 then 'JUNIO'\n"
                    + "when date_part('month',v.fecha_creado)=7 then 'JULIO'\n"
                    + "when date_part('month',v.fecha_creado)=8 then 'AGOSTO'\n"
                    + "when date_part('month',v.fecha_creado)=9 then 'SEPTIEMBRE'\n"
                    + "when date_part('month',v.fecha_creado)=10 then 'OCTUBRE'\n"
                    + "when date_part('month',v.fecha_creado)=11 then 'NOVIEMBRE'\n"
                    + "when date_part('month',v.fecha_creado)=12 then 'DICIEMBRE'\n"
                    + "else 'error' end as mes,\n"
                    + "TRIM(to_char(sum(v.monto_liquidacion),'999G999G999')) as monto\n"
                    + "from rh_liquidacion v,persona p\n"
                    + "where v.fk_idpersona=p.idpersona\n"
                    + "and (v.estado='CERRADO' or v.estado='EMITIDO')\n" + filtro_fecha()
                    + "and v.fk_idpersona=" + idpersona
                    + " group by 1,2\n"
                    + "order by 1 desc";
            actualizar_tabla_liquidacion_grupo(conn, tblgrupo, sql);
        } else if (tipo_seleccionar_grupo == 3) {
            sql = "select (0) as id,date(v.fecha_creado) as fecha,\n"
                    + "TRIM(to_char(sum(v.monto_liquidacion),'999G999G999')) as monto\n"
                    + "from rh_liquidacion v,persona p\n"
                    + "where v.fk_idpersona=p.idpersona\n"
                    + "and (v.estado='CERRADO' or v.estado='EMITIDO')\n" + filtro_fecha()
                    + "and v.fk_idpersona=" + idpersona
                    + " and date_part('month',v.fecha_creado)=" + imes
                    + " group by 1,2\n"
                    + "order by date(v.fecha_creado) desc;";
            actualizar_tabla_liquidacion_grupo(conn, tblgrupo, sql);
        }
    }

    private void tabla_liquidacion() {
        String sql = "select v.idrh_liquidacion as idv,"
                + "to_char(v.fecha_creado,'yyyy-MM-dd') as fecha,\n"
                + "(p.nombre||'-'||p.ruc) as persona,\n"
                + "v.descripcion as descripcion,\n"
                + "TRIM(to_char(v.salario_base,'999G999G999')) as salario,\n"
                + "TRIM(to_char(v.monto_vale,'999G999G999')) as vale,\n"
                + "TRIM(to_char(v.monto_descuento,'999G999G999')) as descuento,\n"
                + "TRIM(to_char(v.monto_liquidacion,'999G999G999')) as liquidacion,\n"
                + "v.salario_base as salario,\n"
                + "v.monto_vale as vale,\n"
                + "v.monto_descuento as descuento,\n"
                + "v.monto_liquidacion as liquidacion\n"
                + "from rh_liquidacion v,persona p\n"
                + "where v.fk_idpersona=p.idpersona\n"
                + "and (v.estado='CERRADO' or v.estado='EMITIDO')\n" + filtro_fecha() + filtro_tb
                + " order by 2 desc";
        actualizar_tabla_liquidacion(conn, tblliquidacion, sql);
        double salario = evejt.getDouble_sumar_tabla(tblliquidacion, 8);
        jFmonto_salario.setValue(salario);
        double vale = evejt.getDouble_sumar_tabla(tblliquidacion, 9);
        jFmonto_vale.setValue(vale);
        double descuento = evejt.getDouble_sumar_tabla(tblliquidacion, 10);
        jFmonto_descuento.setValue(descuento);
        double liquidacion = evejt.getDouble_sumar_tabla(tblliquidacion, 11);
        jFmonto_liquidacion.setValue(liquidacion);
    }

    private void seleccionar_grupo() {

        if (tipo_seleccionar_grupo == 1) {
            idpersona = evejt.getInt_select_id(tblgrupo);
            filtro_tb = filtro_tb + " and v.fk_idpersona=" + idpersona;
            tabla_grupo();
            tabla_liquidacion();
        } else if (tipo_seleccionar_grupo == 2) {
            imes = evejt.getInt_select_id(tblgrupo);
            filtro_tb = filtro_tb + " and date_part('month',v.fecha_creado)=" + imes;
            tabla_grupo();
            tabla_liquidacion();
        }

    }

    private void actualizar_tabla_liquidacion_grupo(Connection conn, JTable tbltabla, String sql) {
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_liquidacion_grupo(tbltabla);
    }

    private void ancho_tabla_liquidacion_grupo(JTable tbltabla) {
        int Ancho[] = {1, 80, 20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.ocultar_columna(tbltabla, 0);
        evejt.alinear_derecha_columna(tbltabla, 2);
    }

    private void actualizar_tabla_liquidacion(Connection conn, JTable tbltabla, String sql) {
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_liquidacion(tbltabla);
    }

    private void ancho_tabla_liquidacion(JTable tbltabla) {
        int Ancho[] = {5, 10, 20, 20, 10,10,10, 10,1,1,1,1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.ocultar_columna(tbltabla, 8);
        evejt.ocultar_columna(tbltabla, 9);
        evejt.ocultar_columna(tbltabla, 10);
        evejt.ocultar_columna(tbltabla, 11);
        evejt.alinear_derecha_columna(tbltabla, 4);
        evejt.alinear_derecha_columna(tbltabla, 5);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
    }

    private void reestableser() {
        evefec.setFechaDCcargado(dcfecDesde,evefec.getString_fecha_dia1());
        evefec.setFechaDCSistema(dcfecHasta);
    }

    private void boton_imprimir_vale() {
        String filtro = filtro_tb + filtro_fecha();
        DAOv.imprimir_filtro_liquidacion(conn, filtro);
    }

    public FrmRepLiquidacion() {
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblliquidacion = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblgrupo = new javax.swing.JTable();
        btnatras = new javax.swing.JButton();
        jFmonto_liquidacion = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        btnbuscar_fecha = new javax.swing.JButton();
        dcfecDesde = new com.toedter.calendar.JDateChooser();
        dcfecHasta = new com.toedter.calendar.JDateChooser();
        btnimprimir_filtro_depo_banco = new javax.swing.JButton();
        jFcantidad = new javax.swing.JFormattedTextField();
        jFmonto_vale = new javax.swing.JFormattedTextField();
        jFmonto_descuento = new javax.swing.JFormattedTextField();
        jFmonto_salario = new javax.swing.JFormattedTextField();

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA LIQUIDACION"));

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
        jScrollPane1.setViewportView(tblliquidacion);

        tblgrupo.setModel(new javax.swing.table.DefaultTableModel(
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
        tblgrupo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblgrupoMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblgrupo);

        btnatras.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnatras.setText("ATRAS");
        btnatras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnatrasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnatras, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnatras))
        );

        jFmonto_liquidacion.setEditable(false);
        jFmonto_liquidacion.setBackground(new java.awt.Color(255, 255, 255));
        jFmonto_liquidacion.setBorder(javax.swing.BorderFactory.createTitledBorder("SUM.LIQUIDACION:"));
        jFmonto_liquidacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_liquidacion.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("FILTRO LIQUIDACION"));

        btnbuscar_fecha.setText("BUSCAR");
        btnbuscar_fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_fechaActionPerformed(evt);
            }
        });

        dcfecDesde.setBorder(javax.swing.BorderFactory.createTitledBorder("DESDE:"));
        dcfecDesde.setDateFormatString("yyyy-MM-dd");
        dcfecDesde.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        dcfecHasta.setBorder(javax.swing.BorderFactory.createTitledBorder("HASTA:"));
        dcfecHasta.setDateFormatString("yyyy-MM-dd");
        dcfecHasta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(362, 362, 362)
                .addComponent(dcfecDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dcfecHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnbuscar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        btnimprimir_filtro_depo_banco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_imprimir.png"))); // NOI18N
        btnimprimir_filtro_depo_banco.setText("FILTRO LIQUIDACION");
        btnimprimir_filtro_depo_banco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_filtro_depo_bancoActionPerformed(evt);
            }
        });

        jFcantidad.setEditable(false);
        jFcantidad.setBorder(javax.swing.BorderFactory.createTitledBorder("CANT:"));
        jFcantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFcantidad.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jFmonto_vale.setEditable(false);
        jFmonto_vale.setBackground(new java.awt.Color(255, 255, 255));
        jFmonto_vale.setBorder(javax.swing.BorderFactory.createTitledBorder("SUM.VALE:"));
        jFmonto_vale.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_vale.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jFmonto_descuento.setEditable(false);
        jFmonto_descuento.setBackground(new java.awt.Color(255, 255, 255));
        jFmonto_descuento.setBorder(javax.swing.BorderFactory.createTitledBorder("SUM.DESCUENTO"));
        jFmonto_descuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_descuento.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jFmonto_salario.setEditable(false);
        jFmonto_salario.setBackground(new java.awt.Color(255, 255, 255));
        jFmonto_salario.setBorder(javax.swing.BorderFactory.createTitledBorder("SUM.SALARIO:"));
        jFmonto_salario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_salario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnimprimir_filtro_depo_banco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFmonto_salario, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFmonto_vale, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFmonto_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jFmonto_liquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFmonto_liquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFmonto_vale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFmonto_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFmonto_salario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnimprimir_filtro_depo_banco))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
//        DAOva.ancho_tabla_venta_alquiler_rep_1(tblfiltro_venta_alquiler);
        ancho_tabla_liquidacion_grupo(tblgrupo);
        ancho_tabla_liquidacion(tblliquidacion);
//        ancho_tabla_transaccion_banco(tbltransaccion_banco);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnbuscar_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_fechaActionPerformed
        // TODO add your handling code here:
        tipo_seleccionar_grupo = 0;
        filtro_tb = "";
        tabla_grupo();
        tabla_liquidacion();
    }//GEN-LAST:event_btnbuscar_fechaActionPerformed

    private void btnimprimir_filtro_depo_bancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_filtro_depo_bancoActionPerformed
        // TODO add your handling code here:
        boton_imprimir_vale();
    }//GEN-LAST:event_btnimprimir_filtro_depo_bancoActionPerformed

    private void tblgrupoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblgrupoMouseReleased
        // TODO add your handling code here:
        seleccionar_grupo();
    }//GEN-LAST:event_tblgrupoMouseReleased

    private void btnatrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnatrasActionPerformed
        // TODO add your handling code here:
        tipo_seleccionar_grupo = 0;
        filtro_tb = "";
        tabla_grupo();
        tabla_liquidacion();
    }//GEN-LAST:event_btnatrasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnatras;
    private javax.swing.JButton btnbuscar_fecha;
    private javax.swing.JButton btnimprimir_filtro_depo_banco;
    private com.toedter.calendar.JDateChooser dcfecDesde;
    private com.toedter.calendar.JDateChooser dcfecHasta;
    private javax.swing.JFormattedTextField jFcantidad;
    private javax.swing.JFormattedTextField jFmonto_descuento;
    private javax.swing.JFormattedTextField jFmonto_liquidacion;
    private javax.swing.JFormattedTextField jFmonto_salario;
    private javax.swing.JFormattedTextField jFmonto_vale;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblgrupo;
    private javax.swing.JTable tblliquidacion;
    // End of variables declaration//GEN-END:variables
}
