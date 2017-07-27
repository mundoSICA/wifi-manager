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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.mundosica.wifi.manager.Dispatcher;
import main.java.com.mundosica.wifi.manager.Model.Client;
import main.java.com.mundosica.wifi.manager.Model.HostedNetwork;
import main.java.com.mundosica.wifi.manager.Model.NetshWlan;

/**
 * 
 * @author @Fitorec <chanerec at gmail.com>
 */
public class HotspotTabController implements Initializable, Runnable {
    @FXML
    private SplitPane mainContainer;
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
    //
    @FXML
    private TableView tableClients;
    @FXML
    private TableColumn columnNum;
    @FXML
    private TableColumn columnMac;
    @FXML
    private TableColumn columnIP;
    @FXML
    private TableColumn columnIptype;
    @FXML
    private TableColumn columnHostName;
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
        HostedNetwork.toggleRun(ssid.getText(), passwordValue(), num_max_clients.getText());
        this.refleshInterfaceData();
    }

    /**
     *
     * @param event
     */
    @FXML
    public void openSystemConnections(ActionEvent event) {
        NetshWlan.basicExec("control ncpa.cpl");
    }

    public void refleshInterfaceData() {
        HostedNetwork.loadData();
        this.ssid.setText(HostedNetwork.getSsid());
        this.num_max_clients.setText(HostedNetwork.getNum_max_clients() + "");
        this.currentStatus = HostedNetwork.getStatus();
        this.pass_hidden.setText(HostedNetwork.getPassword());
        this.pass_text.setText(HostedNetwork.getPassword());
        // reflesh btn_start_stop
        this.btn_start_stop.getStyleClass().remove("stopped");
        this.btn_start_stop.getStyleClass().remove("started");
        if (currentStatus.equals("started")) {
            this.btn_start_stop.setText("Detener");
        } else {
            this.btn_start_stop.setText("Iniciar");
        }
        this.btn_start_stop.getStyleClass().add(currentStatus);
        // Load Clients
        tableClients.getItems().clear();
        tableClients.setItems(Client.list());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Dispatcher.stage.setTitle("Hotspot");
        // Table clients
        columnNum.setCellValueFactory(new PropertyValueFactory<>("num"));
        columnMac.setCellValueFactory(new PropertyValueFactory<>("mac"));
        columnIP.setCellValueFactory(new PropertyValueFactory<>("ip"));
        columnIptype.setCellValueFactory(new PropertyValueFactory<>("ipType"));
        columnHostName.setCellValueFactory(new PropertyValueFactory<>("hostName"));
        //
        this.refleshInterfaceData();
        this.togglevisiblePassword(null);
        // update table
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("Actualizando tabla");
        try {
            ObservableList list = Client.list();
            tableClients.getItems().clear();
            tableClients.setItems(list);
            Thread.sleep(3000);
        } catch (InterruptedException ie) {
            System.out.println("Child thread interrupted! " + ie);
        }
        if (Dispatcher.stage.isShowing()) {
            this.run();
        }
    }

}
