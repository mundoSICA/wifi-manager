/*
 * Licencia MIT
 *
 * Copyright (c) 2017 @Fitorec <chanerec at gmail.com>.
 *
 * Se concede permiso, de forma gratuita, a cualquier persona que obtenga una
 * copia de este software y de los archivos de documentación asociados
 * (el "Software"), para utilizar el Software sin restricción, incluyendo sin
 * limitación los derechos a usar, copiar, modificar, fusionar, publicar,
 * distribuir, sublicenciar, y/o vender copias del Software, y a permitir a las
 * personas a las que se les proporcione el Software a hacer lo mismo, sujeto a
 * las siguientes condiciones:
 *
 * El aviso de copyright anterior y este aviso de permiso se incluirán en todas
 * las copias o partes sustanciales del Software.
 *
 * EL SOFTWARE SE PROPORCIONA "TAL CUAL", SIN GARANTÍA DE NINGÚN TIPO, EXPRESA O
 * IMPLÍCITA, INCLUYENDO PERO NO LIMITADO A GARANTÍAS DE COMERCIALIZACIÓN,
 * IDONEIDAD PARA UN PROPÓSITO PARTICULAR Y NO INFRACCIÓN. EN NINGÚN CASO LOS
 * AUTORES O TITULARES DEL COPYRIGHT SERÁN RESPONSABLES DE NINGUNA RECLAMACIÓN,
 * DAÑOS U OTRAS RESPONSABILIDADES, YA SEA EN UNA ACCIÓN DE CONTRATO, AGRAVIO O
 * CUALQUIER OTRO MOTIVO, QUE SURJA DE O EN CONEXIÓN CON EL SOFTWARE O EL USO U
 * OTRO TIPO DE ACCIONES EN EL SOFTWARE.
 *
 */

package main.java.com.mundosica.wifi.manager.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author @Fitorec <chanerec at gmail.com>
 */
public class NetshWlan {

    /**
     *
     */
    public static final String PREFIX = "netsh wlan ";

    /**
     *
     * @return
     */
    public static String dataPath() {
        try {
            String path = new java.io.File(".").getCanonicalPath();
            return path + "\\data";
        } catch(IOException ioError) { }
        return null;
    }

    /**
     *
     * @param cmd
     * @return
     */
    public static Stream<String> exec(String cmd) {
        try {
            Process process = Runtime.getRuntime().exec(PREFIX + cmd);
            return  new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            ).lines();
        } catch(Exception e) { }
        return null;
    }

    /**
     *
     * @param cmd
     * @return
     */
    public static boolean simpleExec(String cmd) {
        try {
            Process process = Runtime.getRuntime().exec(PREFIX + cmd);
            return  true;
        } catch(Exception e) {}
        return false;
    }

    /**
     *
     */
    public static void showNetworks() {
        String cmd = "show networks";
        try {
            Stream<String> outCmd = NetshWlan.exec(cmd);
            outCmd.forEach(line -> System.out.println(line));
        } catch(Exception e) { }
    }

    /**
     * Regresa una lista con los nombres de archivos .xml de los perfiles
     *
     * @return lista de nombres de archivos que contienen perfiles
     */
    public static Stream<String> exportProfiles() {
        String cmd = "export profile key=clear folder=\"" + NetshWlan.dataPath()+"\"";
        System.out.println(cmd+"\n\n\n\n");
        List<String> files = new ArrayList<>();
        try {
            NetshWlan.exec(cmd).forEach( line -> {
                if (line.indexOf(".xml") > 1) {
                    String fName = line.substring(line.lastIndexOf("\\")+1, line.lastIndexOf("l")+1);
                    files.add(fName);
                }
            });
            return files.stream();
        } catch(Exception e) { }
        return null;
    }

    /**
     * networks
     * @return El resultado del scaneo.
     */
    protected static Stream<String> networks() {
        String cmd = "show network mode=bssid";
        return NetshWlan.exec(cmd);
    }
}
