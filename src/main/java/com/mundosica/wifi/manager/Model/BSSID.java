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

import java.util.List;

/**
 *
 * @author @Fitorec <chanerec at gmail.com>
 */
public class BSSID {
    public String mac;
    public int signal;
    public String type;
    public int chanel;
    public String vel_basic;
    public String vel_other;

    BSSID(List<String> dataBSSID) {
        this.mac = Network.val(dataBSSID.get(0));
        String sing = Network.val(dataBSSID.get(1));
        this.signal = Integer.parseInt(sing.substring(0, sing.length()-1));
        this.type = Network.val(dataBSSID.get(2));
        if (dataBSSID.size()>3) {
            this.chanel = Integer.parseInt(Network.val(dataBSSID.get(3)));
            this.vel_basic = Network.val(dataBSSID.get(4));
            this.vel_other = Network.val(dataBSSID.get(5));
        }
    }

    @Override
    public String toString() {
        return "\n\tMAC: [" + this.mac + "]" +
            "\n\tSignal: [" + this.signal + "%]" +
            "\n\ttype: [" + this.type + "]" +
            "\n\tchanel: [" + this.chanel + "]" +
            "\n\tvel_basic: [" + this.vel_basic + "]" +
            "\n\tvel_other: [" + this.vel_other + "]";
    }
}
