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

package main.java.com.mundosica.wifi.manager.View;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.FileInputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * 
 * @author @Fitorec <chanerec at gmail.com>
 */
public class HotspotManualController implements Initializable {
    @FXML
    private Text foot;
    @FXML
    private ImageView stepsImg;
    @FXML
    private ImageView mainImg;
    @FXML
    private Button next;
    @FXML
    private Button prev;
    private static Integer currentStep = 0;
    private static final String steps[] = {
        "Configura los parametros de tu hotspot e inicia el servicio",
        "Accede a la \"lista de conexiones\" desde su bóton",
        "Identifica tu conexión por la que recibes Internet y la interfaz virtual creada por el hotspot",
        "Accede a las propiedades de tu conexión por la que recibes Internet",
        "En la pestaña de \"uso compartido\" comparte tu conexión a Internet a la conexión creada por el hotspot"
    };
    @FXML
    public void onNext(ActionEvent event) {
        currentStep = (currentStep+1)%steps.length;
        prev.setDisable(false);
        if (currentStep == (steps.length -1)) {
            next.setDisable(true);
        }
        reflesh();
    }
    @FXML
    public void onPrev(ActionEvent event) {
        if (currentStep > 0) {
            currentStep -= 1;
        }
        if (currentStep == 0) {
            prev.setDisable(true);
        }
        next.setDisable(false);
        reflesh();
    }

    private void reflesh() {
        foot.setText(steps[currentStep]);
        String path;
        try {
            path = "img/step_" + currentStep + "_foot.png";
            FileInputStream input = new FileInputStream(path);
            Image img = new Image(input);
            stepsImg.setImage(img);
        } catch (Exception ex) { ex.printStackTrace();}
        try {
            path = "img/step_" + currentStep + ".png";
            FileInputStream input = new FileInputStream(path);
            Image img = new Image(input);
            mainImg.setImage(img);
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.reflesh();
        prev.setDisable(true);
    }
}