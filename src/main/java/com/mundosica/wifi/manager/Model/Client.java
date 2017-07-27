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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * @author @Fitorec <chanerec at gmail.com>
 */
public class Client extends ClientAbstract {
    private static final Map<String, Client> LIST_CLIENTS = new HashMap();
    private static int numClients = 0;

    private Client(String _mac, String _ip, String _ipType) {
        this.mac = _mac;
        this.ip = _ip;
        this.num = ++numClients;
        this.ipType = _ipType;
        try {
            InetAddress addr = InetAddress.getByName(this.ip);
            this.setHostName(addr.getHostName());
        } catch (UnknownHostException ue) {}
        Client.LIST_CLIENTS.put(_mac, this);
    }

    /**
     * Borrando tablas ARP:
     *      netsh interface ip delete arpcache
     */
    public static void loadClients() {
        LIST_CLIENTS.clear();
        numClients = 0;
        Pattern p = Pattern.compile("^\\s+([A-F0-9:]{17})\\s+.*$");
        Stream<String> info = NetshWlan.exec("show hostednetwork");
        ArrayList<String> macs = new ArrayList<>();
        info.forEach(line -> {
            Matcher m = p.matcher(line.toUpperCase());
            if (m.find()) {
                macs.add(m.group(1));
            }
        });
        Map<String, String[]> macsIps = new HashMap();
        Pattern patIPs = Pattern.compile("^\\s+([0-9.]{7,15})\\s+([-A-F0-9]{17})\\s+(.+)$");
        info = NetshWlan.basicExec("arp -a");
        info.forEach(line -> {
            Matcher m = patIPs.matcher(line.toUpperCase());
            if (m.find()) {
                String mac = m.group(2).replaceAll("-", ":");
                String ipData[] = {m.group(1), m.group(3)};
                macsIps.put(mac, ipData);
            }
        });
        macs.forEach(mac -> {
            if (macsIps.containsKey(mac)) {
                String ipData[] = macsIps.get(mac);
                new Client(mac, ipData[0], ipData[1]);
            }
        });
    }

    @Override
    public String toString() {
        return num + " client: " + mac + "\t" + ip + "\t" + ipType + "\t" + hostName + "\n";
    }

    /**
     * set normalized hotsName
     *
     * @param _hostName
     */
    public void setHostName(String _hostName) {
        if (_hostName.length()>11) {
            _hostName = _hostName.replaceAll("(.*)\\.mshome\\.net$", "$1");
        }
        this.hostName = _hostName;
    }

    /**
     * @param ipType the ipType to set
     */
    public void setIpType(String ipType) {
        this.ipType = (ipType.indexOf("stat") == -1)?
                "static" : "DHCP";
    }

    /**
     * @return the LIST_CLIENTS
     */
    public static Map<String, Client> getLIST_CLIENTS() {
        return LIST_CLIENTS;
    }
    public static ObservableList list() {
        ObservableList<Client> listClients = FXCollections.observableArrayList();
        LIST_CLIENTS.forEach((mac, client) -> {
            if (listClients.add(client)) {
                System.out.print("Cliente agregado:" + client);
            }
        });
        return listClients;
    }
}
