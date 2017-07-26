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
import java.util.stream.Stream;

/**
 * 
 * @author @Fitorec <chanerec at gmail.com>
--------------- English -------------------------------
Hosted network settings
-----------------------
Mode                   : Allowed
SSID name              : "happy"
Max number of clients  : 100
Authentication         : WPA2-Personal
Cipher                 : CCMP

Hosted network status
---------------------
Status                 : Started
BSSID                  : 68:94:23:b9:86:9d
Radio type             : 802.11n
Channel                : 11
Number of clients      : 0
------------------------  Spanish -------------------------
 */
public final class HostedNetwork extends HostedNetworkAbstract {
    private HostedNetwork() {}

    public static void loadData() {
        ArrayList net = new ArrayList<>();
        Stream<String> data = NetshWlan.exec("show hostednetwork");
        data.forEach(l -> {
            if (l.contains(":")) {
                net.add(l);
            }
        });
        //Order Result: (0)mode, (1)ssid, (2)max clients, (3)auth, (4)cipher, (5)status
        if (net.size() < 6) {
            return;
        }
        HostedNetwork.ssid = NetshWlan.val(net.get(1).toString()).trim();
        HostedNetwork.ssid = HostedNetwork.ssid.replaceAll("\"", "");
        String nClients = NetshWlan.val(net.get(2).toString()).trim();
        HostedNetwork.num_max_clients = Integer.parseInt(nClients);
        HostedNetwork.setStatus(NetshWlan.val(net.get(5).toString()));
        // security
        data = NetshWlan.exec("show hostednetwork security");
        net.clear();
        data.forEach(l -> {
            if (l.contains(":")) {
                net.add(l);
            }
        });
        //  Autenticación                  : WPA2-Personal
        //Order Result: (0)auth, cipher(1), system security(2), user security(3)
        if (net.size() < 4) {
            return;
        }
        HostedNetwork.setPassword(NetshWlan.val(net.get(3).toString()).trim());
        Client.loadClients();
    }

    public static void setStatus(String _status) {
        ArrayList<String> startedElements = new ArrayList<>();
        startedElements.add("Started");
        startedElements.add("Iniciado");
        _status = HostedNetwork.camelCase(_status);
        if (startedElements.indexOf(_status) != -1) {
            HostedNetwork.status = "started";
        } else {
            HostedNetwork.status = "stopped";
        }
    }

    public static String camelCase(String in) {
        if (in == null || in.length() < 1) { return ""; } //validate in
        String out = "";
        for (String part : in.toLowerCase().split(" ")) {
            if (part.length() < 1) { //validate length
                continue;
            }
            out += part.substring(0, 1).toUpperCase();
            if (part.length() > 1) { //validate length
                out += part.substring(1);
            }
        }
        return out;
    }
 
    public static boolean toggleRun(String _ssid, String _password, String maxClients) {
        String cmd = "set hostednetwork mode=allow"
                    + " ssid=\""+_ssid+"\""
                    + " key=\""+_password+"\""
                    + " keyUsage=persistent ";
        System.out.println("Comando: netsh wlan "+cmd);
        if (!NetshWlan.simpleExec(cmd)) {
            System.out.println("No se pudo cambiar los parametros de la red");
            return false;
        }
        String action = HostedNetwork.getStatus().equals("started")? "stop": "start";
        System.out.println("Comando a ejecutar: " + action + " hostednetwork");
        return NetshWlan.simpleExec(action + " hostednetwork");
    }
}
