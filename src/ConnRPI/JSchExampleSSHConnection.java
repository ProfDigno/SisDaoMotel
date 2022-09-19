/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnRPI;

import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import javax.swing.JOptionPane;

public class JSchExampleSSHConnection {

    /**
     * JSch Example Tutorial Java SSH Connection Program
     */
    public Session getSession_conn_ssh_rpi(String host, String user, String password) {
        Session session = null;
        String mensaje = "\n---------------getSession_conn_ssh_rpi----------------";
        mensaje = mensaje + "\n host: " + host;
        mensaje = mensaje + "\n user: " + user;
        mensaje = mensaje + "\n password: " + password;
        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            mensaje = mensaje + "\n Conectado Correctamente\n";
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = mensaje + "\n Error:" + e;
            JOptionPane.showMessageDialog(null,"ERROR EN LA CONEXION SSH:\n"+ e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        System.out.println(mensaje);
        return session;
    }

    public String getStringEnviar_ssh_raspberry(String host, String user, String password, String command1, boolean con_retorno) {
        String mensaje = "\n---------------SSH----------------";
        mensaje = mensaje + "\n host: " + host;
        mensaje = mensaje + "\n user: " + user;
        mensaje = mensaje + "\n password: " + password;
        mensaje = mensaje + "\n command1: " + command1;
        try {
            Session session = getSession_conn_ssh_rpi(host, user, password);
            if (session != null && session.isConnected()) {
                System.out.println("Connected");
                mensaje = mensaje + "\n Conectado Correctamente\n";
                Channel channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command1);
                channel.setInputStream(null);
                ((ChannelExec) channel).setErrStream(System.err);
                InputStream in = channel.getInputStream();
                channel.connect();
                if (con_retorno) {
                    byte[] tmp = new byte[1024];
                    while (true) {
                        while (in.available() > 0) {
                            int i = in.read(tmp, 0, 1024);
                            if (i < 0) {
                                break;
                            }
                            System.out.print(new String(tmp, 0, i));
                            mensaje = mensaje + (new String(tmp, 0, i));
                        }
                        if (channel.isClosed()) {
                            System.out.println("\nexit-status: " + channel.getExitStatus());
                            mensaje = mensaje + "\n Salida:\n" + channel.getExitStatus();
                            break;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (Exception ee) {
                        }
                    }
                }
                channel.disconnect();
                session.disconnect();
            }else{
                mensaje = mensaje + "\n ERROR EN LA CONEXION";
            }
            
            mensaje = mensaje + "\n Terminado";
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = mensaje + "\n Error:" + e;
        }
        return mensaje;
    }
}
