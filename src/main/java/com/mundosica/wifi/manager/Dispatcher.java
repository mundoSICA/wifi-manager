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

import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.com.mundosica.wifi.manager.Model.NetshWlan;

/**
 *
 * @author @Fitorec <chanerec at gmail.com>
 */
public class Dispatcher extends Application {
    static Stage stage = null;

    @Override
    public void start(Stage st) throws Exception {
        Dispatcher.stage = st;
        if (!isAdmin()) {
            Alert alert = new Alert(AlertType.ERROR, "Es necesario ejecutar la aplicación como administrador");
            alert.showAndWait();
            Dispatcher.stage.close();
            return;
        }
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        Scene scene = new Scene(root);
        Dispatcher.stage.setScene(scene);
        Dispatcher.stage.setTitle("Conexiones Inalambricas");
        Dispatcher.stage.getIcons().add(new Image("file:icon.png"));
        //WifiManager.stage.initStyle(StageStyle.UNDECORATED);
        MainController.hostServices = getHostServices();
        Dispatcher.stage.show();
    }

    public static boolean isAdmin() {
        String path = NetshWlan.dataPath();
        if (path.toLowerCase().indexOf("projects") > 0) {
            return true;
        }
        Preferences prefs = Preferences.systemRoot();
        try {
            prefs.put("foo", "bar");
            prefs.remove("foo");
            prefs.flush();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
