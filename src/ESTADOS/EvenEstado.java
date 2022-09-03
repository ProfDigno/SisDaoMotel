/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ESTADOS;

/**
 *
 * @author Digno
 */
public class EvenEstado {
    private static String est_Emitido;
    private static String est_Anulado;
    private static String est_Pagado;
    private static String est_Cargado;
    private static String est_Libre = "LIBRE";
    private static String est_Ocupado = "OCUPADO";
    private static String est_Sucio = "SUCIO";
    private static String est_Limpiando = "LIMPIANDO";
    private static String est_Terminar = "TERMINADO";
    private static String est_Cancelado = "CANCELADO";
    private static String est_Desocupado = "DESOCUPADO";
    private static String est_Adelanto = "ADELANTO";
    private static String est_Cerrado = "CERRADO";
    private static String cond_Contado;
    private static String cond_Credito;
    private static String forPago_Efectivo;
    private static String forPago_TarjetaDebito;
    private static String forPago_TarjetaCredito;
    private static String mone_PYG;//Gs
    private static String mone_USD;//Dolar
    private static String mone_BRL;//R$
    private static String mone_ARS;//peso
    private static String tipo_hab_estandar;
    private static String tipo_hab_vip;
    private static String tipo_hab_luxury;
    private static String tipo_hab_penthouse;

    public EvenEstado() {
        setEst_Anulado("ANULADO");
        setEst_Emitido("EMITIDO");
        setEst_Libre("LIBRE");
        setEst_Ocupado("OCUPADO");
        setEst_Sucio("SUCIO");
        setEst_Limpiando("LIMPIANDO");
        setEst_Terminar("TERMINADO");
        setEst_Cancelado("CANCELADO");
        setEst_Desocupado("DESOCUPADO");
        setEst_Adelanto("ADELANTO");
        setEst_Cerrado("CERRADO");
        setEst_Pagado("PAGADO");
        setEst_Cargado("CARGADO");
        setCond_Contado("CONTADO");
        setCond_Credito("CREDITO");
        setForPago_Efectivo("EFECTIVO");
        setForPago_TarjetaDebito("TARJETA_DEBITO");
        setForPago_TarjetaCredito("TARJETA_CREDITO");
        setMone_PYG("Gs");
        setMone_USD("Dolar");
        setMone_BRL("R$");
        setMone_ARS("peso");
        setTipo_hab_estandar("ESTANDAR");
        setTipo_hab_vip("VIP");
        setTipo_hab_luxury("LUXURY");
        setTipo_hab_penthouse("PENTHOUSE");
    }

    public static String getEst_Cargado() {
        return est_Cargado;
    }

    public static void setEst_Cargado(String est_Cargado) {
        EvenEstado.est_Cargado = est_Cargado;
    }

    public static String getEst_Pagado() {
        return est_Pagado;
    }

    public static void setEst_Pagado(String est_Pagado) {
        EvenEstado.est_Pagado = est_Pagado;
    }

    public static String getEst_Cerrado() {
        return est_Cerrado;
    }

    public static void setEst_Cerrado(String est_Cerrado) {
        EvenEstado.est_Cerrado = est_Cerrado;
    }

    public static String getEst_Adelanto() {
        return est_Adelanto;
    }

    public static void setEst_Adelanto(String est_Adelanto) {
        EvenEstado.est_Adelanto = est_Adelanto;
    }

    public static String getTipo_hab_estandar() {
        return tipo_hab_estandar;
    }

    public static void setTipo_hab_estandar(String tipo_hab_estandar) {
        EvenEstado.tipo_hab_estandar = tipo_hab_estandar;
    }

    public static String getTipo_hab_vip() {
        return tipo_hab_vip;
    }

    public static void setTipo_hab_vip(String tipo_hab_vip) {
        EvenEstado.tipo_hab_vip = tipo_hab_vip;
    }

    public static String getTipo_hab_luxury() {
        return tipo_hab_luxury;
    }

    public static void setTipo_hab_luxury(String tipo_hab_luxury) {
        EvenEstado.tipo_hab_luxury = tipo_hab_luxury;
    }

    public static String getTipo_hab_penthouse() {
        return tipo_hab_penthouse;
    }

    public static void setTipo_hab_penthouse(String tipo_hab_penthouse) {
        EvenEstado.tipo_hab_penthouse = tipo_hab_penthouse;
    }

    public static String getMone_PYG() {
        return mone_PYG;
    }

    public static void setMone_PYG(String mone_PYG) {
        EvenEstado.mone_PYG = mone_PYG;
    }

    public static String getMone_USD() {
        return mone_USD;
    }

    public static void setMone_USD(String mone_USD) {
        EvenEstado.mone_USD = mone_USD;
    }

    public static String getMone_BRL() {
        return mone_BRL;
    }

    public static void setMone_BRL(String mone_BRL) {
        EvenEstado.mone_BRL = mone_BRL;
    }

    public static String getMone_ARS() {
        return mone_ARS;
    }

    public static void setMone_ARS(String mone_ARS) {
        EvenEstado.mone_ARS = mone_ARS;
    }

    public static String getForPago_Efectivo() {
        return forPago_Efectivo;
    }

    public static void setForPago_Efectivo(String forPago_Efectivo) {
        EvenEstado.forPago_Efectivo = forPago_Efectivo;
    }

    public static String getForPago_TarjetaDebito() {
        return forPago_TarjetaDebito;
    }

    public static void setForPago_TarjetaDebito(String forPago_TarjetaDebito) {
        EvenEstado.forPago_TarjetaDebito = forPago_TarjetaDebito;
    }

    public static String getForPago_TarjetaCredito() {
        return forPago_TarjetaCredito;
    }

    public static void setForPago_TarjetaCredito(String forPago_TarjetaCredito) {
        EvenEstado.forPago_TarjetaCredito = forPago_TarjetaCredito;
    }

    public static String getEst_Emitido() {
        return est_Emitido;
    }

    public static void setEst_Emitido(String est_Emitido) {
        EvenEstado.est_Emitido = est_Emitido;
    }

    public static String getEst_Anulado() {
        return est_Anulado;
    }

    public static void setEst_Anulado(String est_Anulado) {
        EvenEstado.est_Anulado = est_Anulado;
    }

    public static String getCond_Contado() {
        return cond_Contado;
    }

    public static void setCond_Contado(String cond_Contado) {
        EvenEstado.cond_Contado = cond_Contado;
    }

    public static String getCond_Credito() {
        return cond_Credito;
    }

    public static void setCond_Credito(String cond_Credito) {
        EvenEstado.cond_Credito = cond_Credito;
    }

    public static String getEst_Libre() {
        return est_Libre;
    }

    public static void setEst_Libre(String est_Libre) {
        EvenEstado.est_Libre = est_Libre;
    }

    public static String getEst_Ocupado() {
        return est_Ocupado;
    }

    public static void setEst_Ocupado(String est_Ocupado) {
        EvenEstado.est_Ocupado = est_Ocupado;
    }

    public static String getEst_Sucio() {
        return est_Sucio;
    }

    public static void setEst_Sucio(String est_Sucio) {
        EvenEstado.est_Sucio = est_Sucio;
    }

    public static String getEst_Limpiando() {
        return est_Limpiando;
    }

    public static void setEst_Limpiando(String est_Limpiando) {
        EvenEstado.est_Limpiando = est_Limpiando;
    }

    public static String getEst_Terminar() {
        return est_Terminar;
    }

    public static void setEst_Terminar(String est_Terminar) {
        EvenEstado.est_Terminar = est_Terminar;
    }

    public static String getEst_Cancelado() {
        return est_Cancelado;
    }

    public static void setEst_Cancelado(String est_Cancelado) {
        EvenEstado.est_Cancelado = est_Cancelado;
    }

    public static String getEst_Desocupado() {
        return est_Desocupado;
    }

    public static void setEst_Desocupado(String est_Desocupado) {
        EvenEstado.est_Desocupado = est_Desocupado;
    }
    
}
