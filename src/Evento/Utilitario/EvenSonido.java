/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Utilitario;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class EvenSonido {
    public static void reproducir_vos(String ruta_archivo) {
//        String carpeta="sounds/";
        int tiempoespera=2000;
        try {
            Clip sonido = AudioSystem.getClip();
            File archivo_sonido = null;
            archivo_sonido = new File(new File(ruta_archivo).getAbsolutePath());
            sonido.open(AudioSystem.getAudioInputStream(archivo_sonido));
            sonido.start();
            System.out.println("REPRODUCIR SONIDO..."+ruta_archivo+" Tiempo ms:"+tiempoespera);
            Thread.sleep(tiempoespera); // 10000 milisegundos (10 segundos)
            sonido.close();
        } catch (Exception tipoError) {
            System.out.println("reproducir_vos:" + tipoError);
        }
    }
}
