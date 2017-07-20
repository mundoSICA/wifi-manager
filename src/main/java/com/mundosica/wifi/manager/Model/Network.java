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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author @Fitorec <chanerec at gmail.com>
 */
public class Network extends NetworkAbstract{

    public static List<Network> nets = new ArrayList<>();

    public static int indexOf(String name) {
        for (int i=0; i < Network.nets.size(); i++) {
           if (Network.nets.get(i).name.equals(name)) {
               return i;
           }
       }
        return -1;
    }

    /**
     * Regresa un onbjeto Network de la configuración en caso de existir.
     *
     * @param name el nombre de la red.
     * @return un objeto Network o null en caso de error.
     */
    static Network byName(String name) {
        int index = Network.indexOf(name);
        if (index >= 0) {
            return Network.nets.get(index);
        }
        return null;
    }

    public static int indexOf(Network n) {
        return Network.indexOf(n.name);
    }

    protected static boolean put(Network n) {
        int index = Network.indexOf(n.name);
        if (index>=0) { //update
           Network.nets.remove(index);
        }
       return Network.nets.add(n);
    }

    /**
     * Construye un nuevo elemento.
     *
     * @param dataNet una lista de String que contiene la información sobre la red.
     *
    [0] SSID 12 : Internet 953 1040756 A
    [1] Tipo de red             : Infraestructura
    [2] Autenticación           : Abierta
    [3] Cifrado                 : Ninguna
    [4]     BSSID 0: ......
     */
    private Network(List<String> dataNet) {
        this.name = Network.val(dataNet.get(0));
        this.type = Network.val(dataNet.get(1));
        this.auth = Network.val(dataNet.get(2));
        this.encryption = Network.val(dataNet.get(3));
        List<String> dataBSSID = new ArrayList<>();
        for (int i = 4; i<dataNet.size(); i++) {
            String line = dataNet.get(i).trim();
            if (line.indexOf("BSSID ") == 0) {
                if (dataBSSID.size() > 1) {
                    BSSIDS.add(new BSSID(dataBSSID));
                }
                dataBSSID.clear();
            }
            dataBSSID.add(line);
        }
        if (dataBSSID.size()>1) {
            BSSIDS.add(new BSSID(dataBSSID));
        }
    }

    protected static String val(String line) {
        if (line == null) {
            return null;
        }
        int index = line.indexOf(":");
        if (index<1 || index+2>line.length()) {
            return "";
        }
        line = line.trim();
        return line.substring(index+2);
    }

    /**
     * Muestra un objeto Network.
     *
     * @return Una cadena descriptiva del network.
     */
    @Override
    public String toString() {
        String out =  "===================== Network ============\n"+
            "name: ["+this.name+"]\n" +
            "type: ["+this.type+"]\n" +
            "auth: ["+this.auth+"]\n" +
            "encryption: ["+this.encryption+"\n";
        for(int i = 0; i<this.BSSIDS.size(); i++) {
            out += "\t=== BSSID : " + (i+1) + "======";
            out += this.BSSIDS.get(i);
        }
        return out;
    }

    /**
     * Se encarga de cargar las redes existentes
     *
     */
    protected static void update() {
        Object[] lines = NetshWlan.networks().toArray();
        List<String> dataNet = new ArrayList<>();
        Network.nets.clear();
        for (int i=3; i<lines.length; i++) {
            String line = lines[i].toString().trim();
            if (line.length()==0 && dataNet.size()>0) {
                Network.put(new Network(dataNet));
            }
            if (line.indexOf("SSID ") == 0) {
                dataNet.clear();
            }
            dataNet.add(line);
        }
    }

    /**
     * Regresa el mejor BSSID de una red, considerando como parametro principal
     * el rango de señal.
     *
     *
     * @return el mejor BSSID o null.
     */
    public BSSID bestBSSID() {
        BSSID bestBssid = null;
        for (Object bssid1 : this.BSSIDS.toArray()) {
            BSSID bssid = (BSSID) bssid1;
            if (bestBssid == null || bssid.signal > bestBssid.signal) {
                bestBssid = bssid;
            }
        }
        return bestBssid;
    }
}
