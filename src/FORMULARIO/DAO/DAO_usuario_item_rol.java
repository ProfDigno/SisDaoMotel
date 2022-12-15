package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.usuario_item_rol;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.usuario;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DAO_usuario_item_rol {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private usuario ENTusu = new usuario();
    private String mensaje_insert = "USUARIO_ITEM_ROL GUARDADO CORRECTAMENTE";
    private String mensaje_update = "USUARIO_ITEM_ROL MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO usuario_item_rol(idusuario_item_rol,fecha_creado,creado_por,activo,fk_idusuario_rol,fk_idusuario_evento) VALUES (?,?,?,?,?,?);";
    private String sql_update = "UPDATE usuario_item_rol SET fecha_creado=?,creado_por=?,activo=?,fk_idusuario_rol=?,fk_idusuario_evento=? WHERE idusuario_item_rol=?;";
    private String sql_select = "SELECT idusuario_item_rol,fecha_creado,creado_por,activo,fk_idusuario_rol,fk_idusuario_evento FROM usuario_item_rol order by 1 desc;";
    private String sql_cargar = "SELECT idusuario_item_rol,fecha_creado,creado_por,activo,fk_idusuario_rol,fk_idusuario_evento FROM usuario_item_rol WHERE idusuario_item_rol=";

    public void insertar_usuario_item_rol(Connection conn, usuario_item_rol usitro) {
        usitro.setC1idusuario_item_rol(eveconn.getInt_ultimoID_mas_uno(conn, usitro.getTb_usuario_item_rol(), usitro.getId_idusuario_item_rol()));
        String titulo = "insertar_usuario_item_rol";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, usitro.getC1idusuario_item_rol());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, usitro.getC3creado_por());
            pst.setBoolean(4, usitro.getC4activo());
            pst.setInt(5, usitro.getC5fk_idusuario_rol());
            pst.setInt(6, usitro.getC6fk_idusuario_evento());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + usitro.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + usitro.toString(), titulo);
        }
    }

    public void update_usuario_item_rol(Connection conn, usuario_item_rol usitro) {
        String titulo = "update_usuario_item_rol";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, usitro.getC3creado_por());
            pst.setBoolean(3, usitro.getC4activo());
            pst.setInt(4, usitro.getC5fk_idusuario_rol());
            pst.setInt(5, usitro.getC6fk_idusuario_evento());
            pst.setInt(6, usitro.getC1idusuario_item_rol());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + usitro.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + usitro.toString(), titulo);
        }
    }

    public void cargar_usuario_item_rol(Connection conn, usuario_item_rol usitro, int idusuario_item_rol) {
        String titulo = "Cargar_usuario_item_rol";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idusuario_item_rol, titulo);
            if (rs.next()) {
                usitro.setC1idusuario_item_rol(rs.getInt(1));
                usitro.setC2fecha_creado(rs.getString(2));
                usitro.setC3creado_por(rs.getString(3));
                usitro.setC4activo(rs.getBoolean(4));
                usitro.setC5fk_idusuario_rol(rs.getInt(5));
                usitro.setC6fk_idusuario_evento(rs.getInt(6));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + usitro.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + usitro.toString(), titulo);
        }
    }

    public void actualizar_tabla_usuario_item_rol(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_usuario_item_rol(tbltabla);
    }

    public void ancho_tabla_usuario_item_rol(JTable tbltabla) {
        int Ancho[] = {16, 16, 16, 16, 16, 16};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void insertar_usuario_item_rol_por_usuario_rol(Connection conn) {

        String creado_por = ENTusu.getGlobal_nombre();
        String titulo = "insertar_usuario_item_rol_por_evento";
        String sql = "select ur.idusuario_rol from usuario_rol ur order by 1 desc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                int idusuario_rol = rs.getInt("idusuario_rol");
                insertar_usuario_item_rol_por_evento(conn, idusuario_rol, creado_por);
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void insertar_usuario_item_rol_por_evento(Connection conn, int idusuario_rol, String creado_por) {
        usuario_item_rol usitro = new usuario_item_rol();
        String titulo = "insertar_usuario_item_rol_por_evento";
        String sql = "select ue.idusuario_evento,("+idusuario_rol+") as idusuario_rol,\n"
                + "case when (select count(*) from usuario_item_rol uir \n"
                + "where uir.fk_idusuario_rol="+idusuario_rol+" and uir.fk_idusuario_evento=ue.idusuario_evento)=0 then true "
                + "else false end as insertar \n"
                + "from usuario_evento ue order by 1 asc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                int idusuario_evento = rs.getInt("idusuario_evento");
                boolean insertar = rs.getBoolean("insertar");
                if (insertar) {
                    usitro.setC3creado_por(creado_por);
                    usitro.setC4activo(false);
                    usitro.setC5fk_idusuario_rol(idusuario_rol);
                    usitro.setC6fk_idusuario_evento(idusuario_evento);
                    insertar_usuario_item_rol(conn, usitro);
                }
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    public void cargar_usuario_evento_en_rol_seleccionar(Connection conn, DefaultTableModel model_urol, JTable tbllocal_evento, int fk_idusuario_rol) {
        String titulo = "cargar_usuario_evento_en_rol_seleccionar";
        String sql_item_rol = "select uir.idusuario_item_rol as iduir,\n"
                + "ue.codigo,ue.nombre,ue.descripcion,  \n"
                + "uir.activo  \n"
                + "from usuario_item_rol uir,usuario_evento ue \n"
                + "where uir.fk_idusuario_evento=ue.idusuario_evento  \n"
                + "and uir.fk_idusuario_rol="+fk_idusuario_rol
                + " order by ue.codigo desc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_item_rol, titulo);
            while (rs.next()) {
                String iduir = (rs.getString("iduir"));
                String codigo = (rs.getString("codigo"));
                String nombre = (rs.getString("nombre"));
                String descripcion = (rs.getString("descripcion"));
                boolean activo = (rs.getBoolean("activo"));
                Object dato[] = {iduir, codigo, nombre, descripcion,activo};
                model_urol = (DefaultTableModel) tbllocal_evento.getModel();
                model_urol.addRow(dato);
            }

        } catch (Exception e) {
            evemen.mensaje_error(e, sql_item_rol, titulo);
        }
    }
    public void ancho_tabla_evento_en_rol_seleccionar(JTable tbltabla) {
        int Ancho[] = {5, 10, 25, 50, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void update_item_usuario_rol_tabla(Connection conn, JTable tbllocal_evento) {
        int cantidad=tbllocal_evento.getRowCount();
        for (int row = 0; row < cantidad; row++) {
            usuario_item_rol usitro=new usuario_item_rol();
            String idusuario_item_rol = ((tbllocal_evento.getModel().getValueAt(row, 0).toString()));
            String activo = ((tbllocal_evento.getModel().getValueAt(row, 4).toString()));
            if(activo.equals("true")){
                usitro.setC4activo(true);
            }else{
                usitro.setC4activo(false);
            }
            usitro.setC1idusuario_item_rol(Integer.parseInt(idusuario_item_rol));
            update_usuario_item_rol_activo(conn, usitro);
        }
        JOptionPane.showMessageDialog(null,"CANTIDAD DE ITEM MODIFICADO:"+cantidad,"ACTUALIZADO",JOptionPane.INFORMATION_MESSAGE);
    }
    private void update_usuario_item_rol_activo(Connection conn, usuario_item_rol usitro) {
        String titulo = "update_usuario_item_rol";
        String sql_update = "UPDATE usuario_item_rol SET activo=? WHERE idusuario_item_rol=?;";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setBoolean(1, usitro.getC4activo());
            pst.setInt(2, usitro.getC1idusuario_item_rol());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + usitro.toString(), titulo);
//            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + usitro.toString(), titulo);
        }
    }
}
