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
package main.java.com.mundosica.wifi.manager;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import main.java.com.mundosica.wifi.manager.Model.Config;
import main.java.com.mundosica.wifi.manager.Model.Profile;

/**
 *
 * @author @Fitorec <chanerec at gmail.com>
 */
public class MainController implements Initializable {
    public static HostServices hostServices ;

    @FXML
    private TableView tableProfiles;
    @FXML
    private TableColumn columnSttus;
    @FXML
    private TableColumn columnName;
    @FXML
    private TableColumn columnMode;
    @FXML
    private TableColumn columnAuth;
    @FXML
    private TableColumn columnKey;
    @FXML
    private TableColumn columnMac;
    @FXML
    private TableColumn columnChannel;
    @FXML
    private TableColumn columnProtocol;
    @FXML
    private MenuButton buscarType;
    @FXML
    private TextField buscarField;
    @FXML
    public CheckBox pass_visible;

    // xml filter
    private static final ExtensionFilter XML_FILTER = new ExtensionFilter("Archivo de Configuración", "*.xml");
    ObservableList<Profile> profilesList = FXCollections.observableArrayList();

    /// Menu
    ContextMenu cm = new ContextMenu();

    private Profile currentProfile() {
        return (Profile) tableProfiles.getSelectionModel().getSelectedItem();
    }

    @SuppressWarnings("empty-statement")
    public void showItems() {
        String search = buscarField.getText();
        this.tableProfiles.getItems().clear();
        profilesList.clear();
        if (search!=null && search.length() >= 2) {
            this.profilesList = Profile.search(search);;
        } else {
            profilesList.addAll(Profile.list());
        }
        this.tableProfiles.setItems(profilesList);
    }

    /**
     *
     * @param event
     */
    @FXML
    public void visiblePassword(ActionEvent event) {
        Profile.visiblePassword = !Profile.visiblePassword;
        this.showItems();
    }

    /**
     *
     * @param ae
     */
    @FXML
    public void getSICA(ActionEvent ae) {
        System.out.println("Vamos a mundosica.com");
        hostServices.showDocument("http://mundosica.com");
    }

    /**
     *
     * @param ae
     */
    @FXML
    public void importar(ActionEvent ae) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importar Configuración");
        fileChooser.getExtensionFilters().add(XML_FILTER);
        File selectedFile = fileChooser.showOpenDialog(WifiManager.stage);
        if (selectedFile == null) {
            return;
        }
        Profile.importFile(selectedFile.getAbsolutePath());
        Profile.loadList();
        this.showItems();
    }

    public void exportar() {
        Profile p = currentProfile();
        FileChooser fileChooser = new FileChooser();
        System.out.println("Directorio: " + Config.getSavePath());
        fileChooser.setTitle("Exportar red " + p.getName());
        fileChooser.getExtensionFilters().add(XML_FILTER);
        fileChooser.setInitialDirectory(new File(Config.getSavePath()));
        fileChooser.setInitialFileName(p.getFileName());
        File selectedFile = fileChooser.showSaveDialog(WifiManager.stage);
        if (selectedFile == null) {
            return;
        }
        Profile.export(p, selectedFile.getAbsolutePath());
    }

    /**
     *
     * @param ke
     */
    @FXML
    public void buscar(KeyEvent ke) {
        this.showItems();
    }

    public void borrar() {
        Profile p = currentProfile();
        Alert alert = new Alert(AlertType.WARNING, "¿Estas seguro de borrar?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Profile.delete(p);
            this.showItems();
        }
    }

    /**
     * Se ejecuta al precionar una tecla en la tabla
     *
     * @param ke
     */
    @FXML
    public void tableOnKeyPressed(KeyEvent ke) {
        KeyCode code = ke.getCode();
        switch(code.toString()) {
            case "DELETE":
                this.borrar();
                break;
            case "CONTEXT_MENU":
                // Cambiar esto
                double x = WifiManager.stage.getX() + this.tableProfiles.getLayoutX();
                double y = WifiManager.stage.getY() + this.tableProfiles.getLayoutY();
                this.showTableRowMenu(x, y);
                break;
            default:
                System.out.println("Código: " + code.toString());
        }
    }

    /**
     * Se ejecuta al precionar una tecla en la tabla
     *
     * @param me
     */
    @FXML
    public void tableOnMouseClicked(MouseEvent me) {
        if (me.getButton() == MouseButton.SECONDARY) {
            this.showTableRowMenu(me.getScreenX() , me.getScreenY());
        }
    }

    public void showTableRowMenu(double x, double y) {
        Profile p = this.currentProfile();
        if (p == null) {
            return;
        }
        cm.show(tableProfiles , x, y);
    }

    /**
     * Se ejecuta al precionar una tecla en la tabla
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnSttus.setCellValueFactory(new PropertyValueFactory<>("status"));
        // columnSttus
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        // ColumnMode: auto
        columnMode.setCellFactory(CheckBoxTableCell.forTableColumn((Integer index) -> {
            Profile p = (Profile) tableProfiles.getItems().get(index);
            return p.getConnectionMode();
        }));
        columnMode.setEditable(true);
        // Tipo de autenticaion.
        columnAuth.setCellValueFactory(new PropertyValueFactory<>("authentication"));
        // Password
        columnKey.setCellFactory(TextFieldTableCell.forTableColumn());
        columnKey.setCellValueFactory(new PropertyValueFactory<>("keyMaterial"));
        //MAC address
        columnMac.setCellFactory(TextFieldTableCell.forTableColumn());
        columnMac.setCellValueFactory(new PropertyValueFactory<>("macAddress"));
        //
        columnChannel.setCellValueFactory(new PropertyValueFactory<>("channel"));
        columnProtocol.setCellValueFactory(new PropertyValueFactory<>("protocol"));
        // Menu
        MenuItem mi1 = new MenuItem("Exportar");
        mi1.setOnAction(event -> exportar());
        cm.getItems().add(mi1);
        MenuItem mi2 = new MenuItem("Editar");
        cm.getItems().add(mi2);
        MenuItem mi3 = new MenuItem("Eliminar");
        mi3.setOnAction(event -> borrar());
        cm.getItems().add(mi3);
        //
        profilesList = FXCollections.observableArrayList((Profile p) -> new Observable[] {p.getConnectionMode()});
        profilesList.addListener((ListChangeListener.Change<? extends Profile> c) -> {
            while (c.next()) {
                if (c.wasUpdated()) {
                    Profile p = profilesList.get(c.getFrom());
                    p.tooggleConnectionMode();
                    break;
                }
            }
        });
        //
        this.tableProfiles.setRowFactory(tv -> new TableRow<Profile>() {
            @Override
            public void updateItem(Profile profile, boolean empty) {
                super.updateItem(profile, empty) ;
                if (profile == null) {
                    return;
                }
                int st = profile.getStatusInt();
                if (st<10) {
                    return;
                }
                //-91/100, 17/100, -39/100
                int cls[] = {230-91*st/100,230+17*st/100,230-39*st/100};
                String col = "";
                for(int c: cls) {
                    col += Integer.toHexString(c);
                }
                setStyle("-fx-background-color: #" + col + ";");
            }
        });
        //
        Profile.loadList();
        this.showItems();
    }
}
