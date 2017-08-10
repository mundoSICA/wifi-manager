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

import java.io.IOException;
import java.net.URL;
import main.java.com.mundosica.wifi.manager.View.ProfileTabController;
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
    static public Stage stage = null;

    @Override
    public void start(Stage st) throws Exception {
        Dispatcher.stage = st;
        if (!isAdmin()) {
            Alert alert = new Alert(AlertType.ERROR, "Es necesario ejecutar la aplicación como administrador");
            alert.showAndWait();
            Dispatcher.stage.close();
            return;
        }
        if (!isValidWindowsVersion()) {
            Alert alert = new Alert(AlertType.ERROR, "El host virtual esta disponible para windows 7 o superior");
            alert.showAndWait();
            Dispatcher.stage.close();
            return;
        }
        Parent root = FXMLLoader.load(getClass().getResource("View/MainView.fxml"));
        Scene scene = new Scene(root);
        Dispatcher.stage.setScene(scene);
        Dispatcher.stage.setTitle("Conexiones Inalambricas");
        Dispatcher.stage.getIcons().add(new Image("file:icon.png"));
        //Dispatcher.stage.initStyle(StageStyle.UNDECORATED);
        //Dispatcher.stage.initStyle(StageStyle.UTILITY);
        ProfileTabController.hostServices = getHostServices();
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
     * Validate the windows version:
     * https://stackoverflow.com/questions/6109679/how-to-check-windows-edition-in-java/45622250#45622250
     * https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
     *
     * Windows versions: https://en.wikipedia.org/wiki/List_of_Microsoft_Windows_versions#Client_versions
     *
     * @return true if is valid windows.
     */
    public static boolean isValidWindowsVersion() {
        String version[] = System.getProperty("os.version").trim().split("\\.");
        int verNum = Integer.parseInt(version[0]) * 10;
        verNum += Integer.parseInt(version[1]);
        return verNum>60; //Windows 7 is 61
    }

    public static void openNewWindow(String fileNameFXML) {
        try {
            URL file = Dispatcher.class.getResource("View/" + fileNameFXML);
            Parent root = FXMLLoader.load(file);
            Stage _stage = new Stage();
            Scene scene = new Scene(root);
            _stage.setScene(scene);
            _stage.show();
        } catch(IOException e) {}
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
