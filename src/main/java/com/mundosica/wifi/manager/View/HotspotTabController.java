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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.mundosica.wifi.manager.Model.HostedNetwork;
import main.java.com.mundosica.wifi.manager.Model.NetshWlan;

/**
 * 
 * @author @Fitorec <chanerec at gmail.com>
 */
public class HotspotTabController implements Initializable {
    @FXML
    private TextField ssid;
    @FXML
    private PasswordField pass_hidden;
    @FXML
    private TextField pass_text;
    @FXML
    private CheckBox pass_toggle;
    @FXML
    private TextField num_max_clients;
    @FXML
    private Button btn_start_stop;
    private String currentStatus = "";
    private HostedNetwork hotspot;
    /**
     * Controls the visibility of the Password field
     * @param event
     */
    @FXML
    public void togglevisiblePassword(ActionEvent event) {
        if (pass_toggle.isSelected()) {
            pass_text.setText(pass_hidden.getText());
            pass_text.setVisible(true);
            pass_hidden.setVisible(false);
            return;
        }
        pass_hidden.setText(pass_text.getText());
        pass_hidden.setVisible(true);
        pass_text.setVisible(false);
    }


    private String passwordValue() {
        return pass_toggle.isSelected()?
           pass_text.getText(): pass_hidden.getText();
    }
    /**
     *
     * @param event
     */
    @FXML
    public void toggleRun(ActionEvent event) {
        this.updateStatus();
    }
    /**
     *
     * @param event
     */
    @FXML
    public void openSystemConnections(ActionEvent event) {
        NetshWlan.basicExec("control ncpa.cpl");
    }

    public void updateStatus() {
        this.btn_start_stop.getStyleClass().remove("stopped");
        this.btn_start_stop.getStyleClass().remove("started");
        if (currentStatus.equals("started")) {
            this.btn_start_stop.setText("Detener");
        } else {
            this.btn_start_stop.setText("Iniciar");
        }
        this.btn_start_stop.getStyleClass().add(currentStatus);
    }
 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.togglevisiblePassword(null);
        //Set values
        hotspot = new HostedNetwork();
        this.ssid.setText(hotspot.getSsid());
        this.num_max_clients.setText(hotspot.getNum_max_clients() + "");
        this.currentStatus = hotspot.getStatus();
        this.updateStatus();
        this.pass_hidden.setText(hotspot.getPassword());
        this.pass_text.setText(hotspot.getPassword());
    }

}
