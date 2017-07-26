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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author @Fitorec <chanerec at gmail.com>
 */
public class NetworkTest {
    
    public NetworkTest() {
    }

    /**
     * Test of val method, of class Network.
     */
    /*@Test
    public void testVal() {
        String signalLine = "         Señal              : 43%";
        String esperado = "43%";
        assertEquals("Error", esperado, Network.val(signalLine));
    }*/

    /**
     * Test of toString method, of class Network.
     */
    @Test
    public void testToString() {
    }

    /**
     * Test of indexOf method, of class Network.
     */
    @Test
    public void testIndexOf_String() {
    }

    /**
     * Test of byName method, of class Network.
     */
    @Test
    public void testByName() {
    }

    /**
     * Test of indexOf method, of class Network.
     */
    @Test
    public void testIndexOf_Network() {
    }

    /**
     * Test of get method, of class Network.
     */
    @Test
    public void testGet() {
        Network.update();
        Network.nets.forEach(net -> {
            Network otherNet = Network.byName(net.name);
            assertEquals("Elementos no compatibles: ", net, otherNet);
        });
    }

    /**
     * Test of put method, of class Network.
     */
    @Test
    public void testPut() {
    }

    /**
     * Test of update method, of class Network.
     */
    @Test
    public void testUpdate() {
    }

    /**
     * Test of bestBSSID method, of class Network.
     */
    @Test
    public void testBestBSSID() {
    }
    
}
