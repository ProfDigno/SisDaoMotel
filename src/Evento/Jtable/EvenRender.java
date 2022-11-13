/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Jtable;

import ESTADOS.EvenEstado;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Digno
 */
public class EvenRender {

    private EvenEstado eveest = new EvenEstado();

    public void rendertabla_item_venta(JTable tblitem_producto) {
        System.out.println("-->rendertabla_item_venta");
        tblitem_producto.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //************************************************************
                int columnaRender = 1;
                Color color_fondo = Color.WHITE;
                Color color_text = Color.BLACK;
                Object texto1 = table.getValueAt(row, columnaRender);
                String campo1 = "P";
                String campo2 = "I";
                String campo3 = "D";
                String campo4 = "O";
                String campo5 = "S";
                if (texto1 != null && campo1.equals(texto1.toString())) {
                    color_fondo = Color.WHITE;
                    color_text = Color.RED;
                }
                if (texto1 != null && campo2.equals(texto1.toString())) {
                    color_fondo = Color.YELLOW;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo3.equals(texto1.toString())) {
                    color_fondo = Color.ORANGE;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo4.equals(texto1.toString())) {
                    color_fondo = Color.PINK;
                    color_text = Color.BLACK;
                }
                if (texto1 != null && campo5.equals(texto1.toString())) {
                    color_fondo = new Color(153, 153, 255);
                    color_text = Color.YELLOW;
                }
                label.setBackground(color_fondo);
                table.setSelectionForeground(color_text);

                return label;
            }
        });
    }

    public void rendertabla_estados(JTable tbltabla, final int columna) {
        System.out.println("-->rendertabla_venta");
        tbltabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //************************************************************
                int columnaRender = columna;
                Color color_fondo = Color.WHITE;
                Color color_text = Color.BLACK;
                Object texto1 = table.getValueAt(row, columnaRender);
                String campo1 = "EMITIDO";
                String campo2 = "TERMINADO";
                String campo3 = "ANULADO";
                String campo4 = "CONFIRMADO";
                String campo5 = "ANULADO_temp";
                if (texto1 != null && campo1.equals(texto1.toString())) {
                    color_fondo = Color.WHITE;
                    color_text = Color.RED;
                }
                if (texto1 != null && campo2.equals(texto1.toString())) {
                    color_fondo = Color.GREEN;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo3.equals(texto1.toString())) {
                    color_fondo = Color.RED;
                    color_text = Color.YELLOW;
                }
                if (texto1 != null && campo4.equals(texto1.toString())) {
                    color_fondo = Color.GREEN;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo5.equals(texto1.toString())) {
                    color_fondo = Color.RED;
                    color_text = Color.YELLOW;
                }
                label.setBackground(color_fondo);
                table.setSelectionForeground(color_text);

                return label;
            }
        });
    }

    public void rendertabla_estados_venta_habitacion(JTable tbltabla, final int columna) {
        System.out.println("-->rendertabla_estados_venta_habitacion");
        tbltabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //************************************************************
                int columnaRender = columna;
                Color color_fondo = Color.WHITE;
                Color color_text = Color.BLACK;
                Object OMudar = table.getValueAt(row, columnaRender);
                Object OTerminar = table.getValueAt(row, columnaRender);
                Object OCancelar = table.getValueAt(row, columnaRender);
                Object OOcupado = table.getValueAt(row, columnaRender);
                Object ODesocupado = table.getValueAt(row, columnaRender);
                String SMudar = eveest.getEst_Mudar();
                String STerminar = eveest.getEst_Terminar();
                String SCancelado = eveest.getEst_Cancelado();
                String SOcupado = eveest.getEst_Ocupado();
                String SDesocupado = eveest.getEst_Desocupado();
                if (OMudar != null && SMudar.equals(OMudar.toString())) {
                    color_fondo = Color.GRAY;
                    color_text = Color.RED;
                }
                if (OTerminar != null && STerminar.equals(OTerminar.toString())) {
                    color_fondo = Color.GREEN;
                    color_text = Color.BLUE;
                }
                if (OCancelar != null && SCancelado.equals(OCancelar.toString())) {
                    color_fondo = Color.RED;
                    color_text = Color.YELLOW;
                }
                if (OOcupado != null && SOcupado.equals(OOcupado.toString())) {
                    color_fondo = Color.ORANGE;
                    color_text = Color.black;
                }
                if (ODesocupado != null && SDesocupado.equals(ODesocupado.toString())) {
                    color_fondo = Color.YELLOW;
                    color_text = Color.black;
                }
                label.setBackground(color_fondo);
                table.setSelectionForeground(color_text);
                return label;
            }
        });
    }

    public class CustomTableCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table,
                Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(
                    table, obj, isSelected, hasFocus, row, column);
            if (isSelected) {
                cell.setBackground(Color.green);
            } else {
                if (row % 2 == 0) {
                    cell.setBackground(Color.cyan);
                } else {
                    cell.setBackground(Color.lightGray);
                }
            }
            return cell;
        }
    }
    public void rendertabla_estados_compra(JTable tbltabla, final int columna) {
        System.out.println("-->rendertabla_estados_venta_habitacion");
        tbltabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //************************************************************
                int columnaRender = columna;
                Color color_fondo = Color.WHITE;
                Color color_text = Color.BLACK;
                Object Opendiente = table.getValueAt(row, columnaRender);
                Object Opagado = table.getValueAt(row, columnaRender);
                Object OAnulado = table.getValueAt(row, columnaRender);
//                Object Oterminado = table.getValueAt(row, columnaRender);
                String Spendiente = eveest.getEst_PENDIENTE();
                String Scargado = eveest.getEst_CARGADOSTOCK();
                String SAnulado = eveest.getEst_Anulado();
//                String STerminado = eveest.getEst_Terminar();
                if (Opendiente != null && Spendiente.equals(Opendiente.toString())) {
                    color_fondo = Color.WHITE;
                    color_text = Color.RED;
                }
                if (Opagado != null && Scargado.equals(Opagado.toString())) {
                    color_fondo = Color.GREEN;
                    color_text = Color.BLUE;
                }
                if (OAnulado != null && SAnulado.equals(OAnulado.toString())) {
                    color_fondo = Color.RED;
                    color_text = Color.YELLOW;
                }
//                if (Oterminado != null && STerminado.equals(Oterminado.toString())) {
//                    color_fondo = Color.GRAY;
//                    color_text = Color.RED;
//                }
                label.setBackground(color_fondo);
                table.setSelectionForeground(color_text);
                return label;
            }
        });
    }
    public void rendertabla_turno_caja(JTable tbltabla, final int columna) {
        System.out.println("-->rendertabla_estados_venta_habitacion");
        tbltabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //************************************************************
                int columnaRender = columna;
                Color color_fondo = Color.WHITE;
                Color color_text = Color.BLACK;
                Object Omanana = table.getValueAt(row, columnaRender);
                Object Otarde = table.getValueAt(row, columnaRender);
                Object Onoche = table.getValueAt(row, columnaRender);
                String Smanana = "manana";
                String Starde = "tarde";
                String Snoche = "noche";
                if (Omanana != null && Smanana.equals(Omanana.toString())) {
                    color_fondo = new Color(255, 251, 193);
                    color_text = Color.BLACK;
                }
                if (Otarde != null && Starde.equals(Otarde.toString())) {
                    color_fondo = new Color(254, 190, 140);
                    color_text = Color.BLACK;
                }
                if (Onoche != null && Snoche.equals(Onoche.toString())) {
                    color_fondo = new Color(155, 161, 123);
                    color_text = Color.BLACK;
                }
                label.setBackground(color_fondo);
                table.setSelectionForeground(color_text);
                return label;
            }
        });
    }
    public void rendertabla_estados_venta_interno(JTable tbltabla, final int columna) {
        System.out.println("-->rendertabla_estados_venta_habitacion");
        tbltabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //************************************************************
                int columnaRender = columna;
                Color color_fondo = Color.WHITE;
                Color color_text = Color.BLACK;
                Object Oemitido = table.getValueAt(row, columnaRender);
                Object Oterminado = table.getValueAt(row, columnaRender);
                Object OAnulado = table.getValueAt(row, columnaRender);
//                Object Oterminado = table.getValueAt(row, columnaRender);
                String Semitido = eveest.getEst_Emitido();
                String Sterminado = eveest.getEst_Terminar();
                String SAnulado = eveest.getEst_Anulado();
//                String STerminado = eveest.getEst_Terminar();
                if (Oemitido != null && Semitido.equals(Oemitido.toString())) {
                    color_fondo = Color.WHITE;
                    color_text = Color.RED;
                }
                if (Oterminado != null && Sterminado.equals(Oterminado.toString())) {
                    color_fondo = Color.GREEN;
                    color_text = Color.BLUE;
                }
                if (OAnulado != null && SAnulado.equals(OAnulado.toString())) {
                    color_fondo = Color.RED;
                    color_text = Color.YELLOW;
                }
                label.setBackground(color_fondo);
                table.setSelectionForeground(color_text);
                return label;
            }
        });
    }
}
