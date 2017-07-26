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

/**
 * 
 * @author @Fitorec <chanerec at gmail.com>
 */
public abstract class HostedNetworkAbstract {
    protected static String ssid;
    protected static int num_max_clients;
    protected static String status;
    protected static String password;

    /**
     * @return the ssid
     */
    public static String getSsid() {
        return ssid;
    }

    /**
     * @param ssid the ssid to set
     */
    public static void setSsid(String ssid) {
        HostedNetworkAbstract.ssid = ssid;
    }

    /**
     * @return the num_max_clients
     */
    public static int getNum_max_clients() {
        return num_max_clients;
    }

    /**
     * @param num_max_clients the num_max_clients to set
     */
    public static void setNum_max_clients(int num_max_clients) {
        HostedNetworkAbstract.num_max_clients = num_max_clients;
    }

    /**
     * @return the status
     */
    public static String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public static void setStatus(String status) {
        HostedNetworkAbstract.status = status;
    }

    /**
     * @return the password
     */
    public static String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public static void setPassword(String password) {
        HostedNetworkAbstract.password = password;
    }
}
