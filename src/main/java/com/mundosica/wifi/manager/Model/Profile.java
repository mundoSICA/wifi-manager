package main.java.com.mundosica.wifi.manager.Model;

import java.io.File;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author @Fitorec <chanerec at gmail.com>
 */
public final class Profile extends ProfileAbstract {
    //
    private static final Map<String, Profile> LIST_PROFILE = new HashMap();
    private static boolean visiblePassword = false;

    public static void toggleVisiblePassword() {
        Profile.visiblePassword = !Profile.visiblePassword;
    }
    /**
     *
     * @param fileName
     */
    public Profile(String fileName) {
        Map<String, String> data = ProfileParser.get(fileName);
        this.fileName = fileName;
        this.name = data.get("/name/");
        //
        this.connectionMode.set(data.get("/connectionMode/").equals("auto"));
        String auth = data.get("/MSM/security/authEncryption/authentication/");
        String keyMat = "";
        if (!auth.equals("open")) {
           keyMat = data.get("/MSM/security/sharedKey/keyMaterial/");
        }
        this.authentication = auth;
        this.keyMaterial = keyMat;
    }

    public static boolean export(Profile f, String fileAbsolutePath) {
        try {
            File srcFile = new File(NetshWlan.dataPath() + File.separator +  f.getFileName());
            File dest = new File(fileAbsolutePath);
            Files.copy(srcFile.toPath(), dest.toPath(), REPLACE_EXISTING);
            Config.setSavePath(dest.getParent());
            return true;
        } catch(Exception e) {}
        return false;
    }

    /**
     *
     */
    public static void loadList() {
        Network.update();
        NetshWlan.exportProfiles().forEach(fileXML -> {
            Profile prof = new Profile(fileXML);
            Network net = Network.byName(prof.getName());
            if (net != null) {
                BSSID b  = net.bestBSSID();
                prof.status = b.signal;
                prof.macAddress = b.mac;
                prof.channel = b.chanel;
                prof.protocol = b.type;
            }
            Profile.LIST_PROFILE.put(prof.getName(), prof);
         });
    }

    /**
     *
     * @return
     */
    public static ObservableList list() {
        ObservableList<Profile> profilesList = FXCollections.observableArrayList();
        Profile.LIST_PROFILE.forEach((k, prof) -> {
            profilesList.add(prof);
        });
        return profilesList;
    }

    public static boolean delete(Profile p) {
        String cmd = "delete profile name=\""+p.getName()+"\"";
        if (NetshWlan.simpleExec(cmd)) {
            Profile.LIST_PROFILE.remove(p.getName());
            return true;
        }
        return false;
    }

    /**
     *
     * @param p
     * @return
     */
    public static ObservableList remove(Profile p) {
        if (p != null ) {
            Profile.LIST_PROFILE.remove(p.getName());
        }
        return Profile.list();
    }

    /**
     *
     * @param search
     * @return
     */
    public static ObservableList search(String search) {
        ObservableList<Profile> profilesList = FXCollections.observableArrayList();
        Profile.LIST_PROFILE.forEach((k, profile) -> {
            if (    profile.name.toLowerCase().contains(search.toLowerCase()) ||
                    profile.keyMaterial.toLowerCase().contains(search.toLowerCase()) ||
                    profile.authentication.toLowerCase().contains(search.toLowerCase())
                ) {
                profilesList.add(profile);
            }
        });
        return profilesList;
    }

    /**
     * Se encarga de intercambiar el valor de auto conexión.
     *
     */
    public void tooggleConnectionMode() {
        String mod = this.connectionMode.get()? "auto" : "manual";
        String cmd = baseChangeValue() + " connectionMode="+mod;
        NetshWlan.simpleExec(cmd);
    }

    public String baseChangeValue() {
        return "set profileparameter name=\"" + this.name + "\" ";
    }
   
    /**
     * @return the keyMaterial
     */
    @Override
    public String getKeyMaterial() {
        if (visiblePassword) {
            return keyMaterial;
        }
        String hidden = "";
        for (int i=0; i<keyMaterial.length(); i++) {
            hidden += "●";
        }
        return hidden;
    }

    public static boolean importFile(String fileName) {
        String cmd = "add profile"
                +" filename=\""+fileName+"\"";
        return NetshWlan.simpleExec(cmd);
    }
    
    /**
     * toString Method
     *
     * @return
     */
    @Override
    public String toString() {
        return "name: " + this.name + "\n" +
                "connectionMode: " + this.connectionMode + "\n";
    }

    public int getStatusInt() {
        return this.status;
    }
}
