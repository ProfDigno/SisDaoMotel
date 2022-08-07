package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.producto_categoria;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_producto_categoria {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PRODUCTO_CATEGORIA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO_CATEGORIA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO producto_categoria(idproducto_categoria,fecha_creado,creado_por,nombre,orden,activo,es_venta) VALUES (?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE producto_categoria SET fecha_creado=?,creado_por=?,nombre=?,orden=?,activo=?,es_venta=? WHERE idproducto_categoria=?;";
    private String sql_select = "SELECT idproducto_categoria as idc,nombre as categoria,orden,activo FROM producto_categoria order by 1 desc;";
    private String sql_cargar = "SELECT idproducto_categoria,fecha_creado,creado_por,nombre,orden,activo,es_venta FROM producto_categoria WHERE idproducto_categoria=";

    public void insertar_producto_categoria(Connection conn, producto_categoria prca) {
        prca.setC1idproducto_categoria(eveconn.getInt_ultimoID_mas_uno(conn, prca.getTb_producto_categoria(), prca.getId_idproducto_categoria()));
        String titulo = "insertar_producto_categoria";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, prca.getC1idproducto_categoria());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, prca.getC3creado_por());
            pst.setString(4, prca.getC4nombre());
            pst.setInt(5, prca.getC5orden());
            pst.setBoolean(6, prca.getC6activo());
            pst.setBoolean(7, prca.getC7es_venta());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + prca.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + prca.toString(), titulo);
        }
    }

    public void update_producto_categoria(Connection conn, producto_categoria prca) {
        String titulo = "update_producto_categoria";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, prca.getC3creado_por());
            pst.setString(3, prca.getC4nombre());
            pst.setInt(4, prca.getC5orden());
            pst.setBoolean(5, prca.getC6activo());
            pst.setBoolean(6, prca.getC7es_venta());
            pst.setInt(7, prca.getC1idproducto_categoria());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + prca.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + prca.toString(), titulo);
        }
    }

    public void cargar_producto_categoria(Connection conn, producto_categoria prca, int idproducto_categoria) {
        String titulo = "Cargar_producto_categoria";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idproducto_categoria, titulo);
            if (rs.next()) {
                prca.setC1idproducto_categoria(rs.getInt(1));
                prca.setC2fecha_creado(rs.getString(2));
                prca.setC3creado_por(rs.getString(3));
                prca.setC4nombre(rs.getString(4));
                prca.setC5orden(rs.getInt(5));
                prca.setC6activo(rs.getBoolean(6));
                prca.setC7es_venta(rs.getBoolean(7));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + prca.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + prca.toString(), titulo);
        }
    }

    public void actualizar_tabla_producto_categoria(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto_categoria(tbltabla);
    }

    public void ancho_tabla_producto_categoria(JTable tbltabla) {
        int Ancho[] = {10, 70, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
