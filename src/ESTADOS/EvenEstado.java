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
    private static String cond_Contado;
    private static String cond_Credito;
    private static String forPago_Efectivo;
    private static String forPago_TarjetaDebito;
    private static String forPago_TarjetaCredito;
    private static String mone_PYG;//Gs
    private static String mone_USD;//Dolar
    private static String mone_BRL;//R$
    private static String mone_ARS;//peso

    public EvenEstado() {
        setEst_Anulado("ANULADO");
        setEst_Emitido("EMITIDO");
        setCond_Contado("CONTADO");
        setCond_Credito("CREDITO");
        setForPago_Efectivo("EFECTIVO");
        setForPago_TarjetaDebito("TARJETA_DEBITO");
        setForPago_TarjetaCredito("TARJETA_CREDITO");
        setMone_PYG("Gs");
        setMone_USD("Dolar");
        setMone_BRL("R$");
        setMone_ARS("peso");
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
    
}
