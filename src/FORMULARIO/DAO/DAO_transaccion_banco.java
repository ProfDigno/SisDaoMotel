package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.transaccion_banco;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import Evento.Jtable.EvenRender;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_transaccion_banco {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    EvenRender everend = new EvenRender();
    private String mensaje_insert = "TRANSACCION_BANCO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "TRANSACCION_BANCO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO transaccion_banco(idtransaccion_banco,fecha_creado,creado_por,fecha_transaccion,nro_transaccion,monto_guarani,monto_dolar,observacion,concepto,estado,fk_iddato_banco) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE transaccion_banco SET fecha_creado=?,creado_por=?,fecha_transaccion=?,nro_transaccion=?,monto_guarani=?,monto_dolar=?,observacion=?,concepto=?,estado=?,fk_iddato_banco=? WHERE idtransaccion_banco=?;";
    private String sql_select = "select tb.idtransaccion_banco as idtb,\n"
            + "tb.fecha_transaccion as fecha,\n"
            + "(b.nombre||'-'||db.nro_cuenta) as banco,\n"
            + "tb.nro_transaccion as referencia,\n"
            + "TRIM(to_char(tb.monto_guarani,'999G999G999')) as guarani,\n"
            + "TRIM(to_char(tb.monto_dolar,'999G999G999')) as dolar,\n"
            + "tb.estado \n"
            + "from transaccion_banco tb,dato_banco db,banco b\n"
            + "where tb.fk_iddato_banco=db.iddato_banco\n"
            + "and db.fk_idbanco=b.idbanco\n"
            + "order by tb.idtransaccion_banco desc;";
    private String sql_cargar = "SELECT idtransaccion_banco,fecha_creado,creado_por,fecha_transaccion,nro_transaccion,monto_guarani,monto_dolar,observacion,concepto,estado,fk_iddato_banco FROM transaccion_banco WHERE idtransaccion_banco=";
    private String sql_anular = "UPDATE transaccion_banco SET monto_guarani=?,monto_dolar=?,estado=? WHERE idtransaccion_banco=?;";

    public void insertar_transaccion_banco(Connection conn, transaccion_banco tb) {
        tb.setC1idtransaccion_banco(eveconn.getInt_ultimoID_mas_uno(conn, tb.getTb_transaccion_banco(), tb.getId_idtransaccion_banco()));
        String titulo = "insertar_transaccion_banco";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, tb.getC1idtransaccion_banco());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, tb.getC3creado_por());
            pst.setDate(4, evefec.getDate_fecha_cargado(tb.getC4fecha_transaccion()));
            pst.setString(5, tb.getC5nro_transaccion());
            pst.setDouble(6, tb.getC6monto_guarani());
            pst.setDouble(7, tb.getC7monto_dolar());
            pst.setString(8, tb.getC8observacion());
            pst.setString(9, tb.getC9concepto());
            pst.setString(10, tb.getC10estado());
            pst.setInt(11, tb.getC11fk_iddato_banco());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + tb.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + tb.toString(), titulo);
        }
    }

    public void update_transaccion_banco(Connection conn, transaccion_banco tb) {
        String titulo = "update_transaccion_banco";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, tb.getC3creado_por());
            pst.setDate(3, evefec.getDate_fecha_cargado(tb.getC4fecha_transaccion()));
            pst.setString(4, tb.getC5nro_transaccion());
            pst.setDouble(5, tb.getC6monto_guarani());
            pst.setDouble(6, tb.getC7monto_dolar());
            pst.setString(7, tb.getC8observacion());
            pst.setString(8, tb.getC9concepto());
            pst.setString(9, tb.getC10estado());
            pst.setInt(10, tb.getC11fk_iddato_banco());
            pst.setInt(11, tb.getC1idtransaccion_banco());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + tb.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + tb.toString(), titulo);
        }
    }

    public void cargar_transaccion_banco(Connection conn, transaccion_banco tb, int idtransaccion_banco) {
        String titulo = "Cargar_transaccion_banco";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idtransaccion_banco, titulo);
            if (rs.next()) {
                tb.setC1idtransaccion_banco(rs.getInt(1));
                tb.setC2fecha_creado(rs.getString(2));
                tb.setC3creado_por(rs.getString(3));
                tb.setC4fecha_transaccion(rs.getString(4));
                tb.setC5nro_transaccion(rs.getString(5));
                tb.setC6monto_guarani(rs.getDouble(6));
                tb.setC7monto_dolar(rs.getDouble(7));
                tb.setC8observacion(rs.getString(8));
                tb.setC9concepto(rs.getString(9));
                tb.setC10estado(rs.getString(10));
                tb.setC11fk_iddato_banco(rs.getInt(11));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + tb.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + tb.toString(), titulo);
        }
    }

    public void actualizar_tabla_transaccion_banco(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_transaccion_banco(tbltabla);
    }

    public void ancho_tabla_transaccion_banco(JTable tbltabla) {
        int Ancho[] = {5, 15, 25, 25, 10, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 4);
        evejt.alinear_derecha_columna(tbltabla, 5);
        everend.rendertabla_estados(tbltabla, 6);
    }

    public void update_transaccion_banco_anular(Connection conn, transaccion_banco tb) {
        String titulo = "update_transaccion_banco_anular";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_anular);
            pst.setDouble(1, tb.getC6monto_guarani());
            pst.setDouble(2, tb.getC7monto_dolar());
            pst.setString(3, tb.getC10estado());
            pst.setInt(4, tb.getC1idtransaccion_banco());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_anular + "\n" + tb.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_anular + "\n" + tb.toString(), titulo);
        }
    }

    public void imprimir_filtro_transaccion_banco(Connection conn, String filtro) {
        String sql = "select tb.idtransaccion_banco as idtb,\n"
                + "(b.nombre||'-'||db.nro_cuenta) as banco,\n"
                + "date_part('month',tb.fecha_transaccion) as imes,\n"
                + "case \n"
                + "when date_part('month',tb.fecha_transaccion)=1 then 'ENERO'\n"
                + "when date_part('month',tb.fecha_transaccion)=2 then 'FEBRERO'\n"
                + "when date_part('month',tb.fecha_transaccion)=3 then 'MARZO'\n"
                + "when date_part('month',tb.fecha_transaccion)=4 then 'ABRIL'\n"
                + "when date_part('month',tb.fecha_transaccion)=5 then 'MAYO'\n"
                + "when date_part('month',tb.fecha_transaccion)=6 then 'JUNIO'\n"
                + "when date_part('month',tb.fecha_transaccion)=7 then 'JULIO'\n"
                + "when date_part('month',tb.fecha_transaccion)=8 then 'AGOSTO'\n"
                + "when date_part('month',tb.fecha_transaccion)=9 then 'SEPTIEMBRE'\n"
                + "when date_part('month',tb.fecha_transaccion)=10 then 'OCTUBRE'\n"
                + "when date_part('month',tb.fecha_transaccion)=11 then 'NOVIEMBRE'\n"
                + "when date_part('month',tb.fecha_transaccion)=12 then 'DICIEMBRE'\n"
                + "else 'error' end as mes,\n"
                + "tb.fecha_transaccion as fecha,\n"
                + "tb.nro_transaccion as referencia,tb.concepto as concepto,\n"
                + "tb.monto_guarani as guarani,tb.monto_dolar as dolar\n"
                + "from transaccion_banco tb,dato_banco db,banco b\n"
                + "where tb.fk_iddato_banco=db.iddato_banco\n"
                + "and db.fk_idbanco=b.idbanco\n"+filtro
                + " order by 2 desc,3 desc,5 desc;";
        String titulonota = "FILTRO TRANSACCION BANCO";
        String direccion = "src/REPORTE/BANCO/repTransaccion_banco.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
}
