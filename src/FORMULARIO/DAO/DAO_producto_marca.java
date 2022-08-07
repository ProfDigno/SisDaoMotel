package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.producto_marca;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_producto_marca {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PRODUCTO_MARCA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO_MARCA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO producto_marca(idproducto_marca,fecha_creado,creado_por,nombre,orden,activo) VALUES (?,?,?,?,?,?);";
    private String sql_update = "UPDATE producto_marca SET fecha_creado=?,creado_por=?,nombre=?,orden=?,activo=? WHERE idproducto_marca=?;";
    private String sql_select = "SELECT idproducto_marca idpm,nombre as marca,orden,activo FROM producto_marca order by 1 desc;";
    private String sql_cargar = "SELECT idproducto_marca,fecha_creado,creado_por,nombre,orden,activo FROM producto_marca WHERE idproducto_marca=";

    public void insertar_producto_marca(Connection conn, producto_marca prma) {
        prma.setC1idproducto_marca(eveconn.getInt_ultimoID_mas_uno(conn, prma.getTb_producto_marca(), prma.getId_idproducto_marca()));
        String titulo = "insertar_producto_marca";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, prma.getC1idproducto_marca());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, prma.getC3creado_por());
            pst.setString(4, prma.getC4nombre());
            pst.setInt(5, prma.getC5orden());
            pst.setBoolean(6, prma.getC6activo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + prma.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + prma.toString(), titulo);
        }
    }

    public void update_producto_marca(Connection conn, producto_marca prma) {
        String titulo = "update_producto_marca";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, prma.getC3creado_por());
            pst.setString(3, prma.getC4nombre());
            pst.setInt(4, prma.getC5orden());
            pst.setBoolean(5, prma.getC6activo());
            pst.setInt(6, prma.getC1idproducto_marca());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + prma.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + prma.toString(), titulo);
        }
    }

    public void cargar_producto_marca(Connection conn, producto_marca prma, int idproducto_marca) {
        String titulo = "Cargar_producto_marca";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idproducto_marca, titulo);
            if (rs.next()) {
                prma.setC1idproducto_marca(rs.getInt(1));
                prma.setC2fecha_creado(rs.getString(2));
                prma.setC3creado_por(rs.getString(3));
                prma.setC4nombre(rs.getString(4));
                prma.setC5orden(rs.getInt(5));
                prma.setC6activo(rs.getBoolean(6));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + prma.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + prma.toString(), titulo);
        }
    }

    public void actualizar_tabla_producto_marca(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto_marca(tbltabla);
    }

    public void ancho_tabla_producto_marca(JTable tbltabla) {
        int Ancho[] = {10, 70, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
