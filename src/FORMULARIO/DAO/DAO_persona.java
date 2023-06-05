package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import ESTADOS.EvenEstado;
import FORMULARIO.ENTIDAD.persona;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class DAO_persona {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private EvenEstado eveest = new EvenEstado();
    private String mensaje_insert = "PERSONA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PERSONA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO persona(idpersona,fecha_creado,creado_por,"
            + "nombre,ruc,direccion,telefono,tipo_persona,"
            + "dia_libre,salario_base,fk_idpersona_cargo,nro_tarjeta,limite_vale) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE persona SET fecha_creado=?,creado_por=?,"
            + "nombre=?,ruc=?,direccion=?,telefono=?,tipo_persona=?,"
            + "dia_libre=?,salario_base=?,fk_idpersona_cargo=?,nro_tarjeta=?,limite_vale=? "
            + "WHERE idpersona=?;";
    private String sql_select = "SELECT idpersona as idper,nombre as persona,ruc,tipo_persona as tipo FROM persona order by 1 desc;";
    private String sql_cargar = "SELECT idpersona,fecha_creado,creado_por,"
            + "nombre,ruc,direccion,telefono,tipo_persona,"
            + "dia_libre,salario_base,fk_idpersona_cargo,nro_tarjeta,limite_vale "
            + "FROM persona WHERE idpersona=";
//    private String sql_cargar_nro_tar = "SELECT idpersona,fecha_creado,creado_por,"
//            + "nombre,ruc,direccion,telefono,tipo_persona,"
//            + "dia_libre,salario_base,fk_idpersona_cargo,nro_tarjeta,limite_vale "
//            + "FROM persona WHERE nro_tarjeta= ";
    public void insertar_persona(Connection conn, persona pe) {
        pe.setC1idpersona(eveconn.getInt_ultimoID_mas_uno(conn, pe.getTb_persona(), pe.getId_idpersona()));
        String titulo = "insertar_persona";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, pe.getC1idpersona());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, pe.getC3creado_por());
            pst.setString(4, pe.getC4nombre());
            pst.setString(5, pe.getC5ruc());
            pst.setString(6, pe.getC6direccion());
            pst.setString(7, pe.getC7telefono());
            pst.setString(8, pe.getC8tipo_persona());
            pst.setInt(9, pe.getC9dia_libre());
            pst.setDouble(10, pe.getC10salario_base());
            pst.setInt(11, pe.getC11fk_idpersona_cargo());
            pst.setString(12, pe.getC12nro_tarjeta());
            pst.setDouble(13, pe.getC13limite_vale());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + pe.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + pe.toString(), titulo);
        }
    }

    public void update_persona(Connection conn, persona pe) {
        String titulo = "update_persona";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, pe.getC3creado_por());
            pst.setString(3, pe.getC4nombre());
            pst.setString(4, pe.getC5ruc());
            pst.setString(5, pe.getC6direccion());
            pst.setString(6, pe.getC7telefono());
            pst.setString(7, pe.getC8tipo_persona());
            pst.setInt(8, pe.getC9dia_libre());
            pst.setDouble(9, pe.getC10salario_base());
            pst.setInt(10, pe.getC11fk_idpersona_cargo());
            pst.setString(11, pe.getC12nro_tarjeta());
            pst.setDouble(12, pe.getC13limite_vale());
            pst.setInt(13, pe.getC1idpersona());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + pe.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + pe.toString(), titulo);
        }
    }

    public void cargar_persona(Connection conn, persona pe, int idpersona) {
        String titulo = "Cargar_persona";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idpersona, titulo);
            if (rs.next()) {
                pe.setC1idpersona(rs.getInt(1));
                pe.setC2fecha_creado(rs.getString(2));
                pe.setC3creado_por(rs.getString(3));
                pe.setC4nombre(rs.getString(4));
                pe.setC5ruc(rs.getString(5));
                pe.setC6direccion(rs.getString(6));
                pe.setC7telefono(rs.getString(7));
                pe.setC8tipo_persona(rs.getString(8));
                pe.setC9dia_libre(rs.getInt(9));
                pe.setC10salario_base(rs.getDouble(10));
                pe.setC11fk_idpersona_cargo(rs.getInt(11));
                pe.setC12nro_tarjeta(rs.getString(12));
                pe.setC13limite_vale(rs.getDouble(13));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + pe.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + pe.toString(), titulo);
        }
    }
    public boolean getBoolean_buscar_persona_nro_tarjeta(Connection conn, persona pe) {
        String titulo = "getBoolean_buscar_persona_nro_tarjeta";
        String sql = "SELECT idpersona,fecha_creado,creado_por,"
            + "nombre,ruc,direccion,telefono,tipo_persona,"
            + "dia_libre,salario_base,fk_idpersona_cargo,nro_tarjeta,limite_vale "
            + "FROM persona WHERE nro_tarjeta ilike'%"+pe.getC12nro_tarjeta()+"%';";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                pe.setC1idpersona(rs.getInt(1));
                pe.setC2fecha_creado(rs.getString(2));
                pe.setC3creado_por(rs.getString(3));
                pe.setC4nombre(rs.getString(4));
                pe.setC5ruc(rs.getString(5));
                pe.setC6direccion(rs.getString(6));
                pe.setC7telefono(rs.getString(7));
                pe.setC8tipo_persona(rs.getString(8));
                pe.setC9dia_libre(rs.getInt(9));
                pe.setC10salario_base(rs.getDouble(10));
                pe.setC11fk_idpersona_cargo(rs.getInt(11));
                pe.setC12nro_tarjeta(rs.getString(12));
                pe.setC13limite_vale(rs.getDouble(13));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "PERSONAL NO ENCONTRADO", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return false;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
            return false;
        }
    }
    public void actualizar_tabla_persona(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_persona(tbltabla);
    }

    public void ancho_tabla_persona(JTable tbltabla) {
        int Ancho[] = {5,55,20,20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void actualizar_tabla_persona_liquida(Connection conn, JTable tbltabla) {
        String sql_select = "SELECT idpersona as idper,nombre as persona "
                + "FROM persona "
                + "where tipo_persona='"+eveest.getPer_PERSONAL()+"'"
                + " order by nombre desc;";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_persona_liquida(tbltabla);
    }

    public void ancho_tabla_persona_liquida(JTable tbltabla) {
        int Ancho[] = {20,80};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
