/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CREAR_CSV;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import java.util.Timer;
import java.util.TimerTask;

public class ProgressBarDemo {
    private static int cycles = 0;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Progress Bar Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 100);

            JProgressBar progressBar = new JProgressBar(0, 100);
            frame.add(progressBar);

            frame.setVisible(true);

            Timer timer = new Timer();
            
            int maxCycles = 100;

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                
                public void run() {
                    if (cycles < maxCycles) {
                        progressBar.setValue(cycles + 1);
                        cycles++;
                    } else {
                        timer.cancel();
                    }
                }
            }, 0, 1000); // 1000 milliseconds = 1 second

        });
    }
}
