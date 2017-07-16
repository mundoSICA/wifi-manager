package main.java.com.mundosica.wifi.manager.Model;

import java.io.File;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author @Fitorec <chanerec at gmail.com>
 */
public final class Profile {
    //
    private static final Map<String, Profile> LIST_PROFILE = new HashMap();
    public static boolean visiblePassword = false;

    private String fileName = null;
    private String name = "";
    /// SSIDConfig/SSID
    private String ssid_name = "";
    private String ssid_hex = "";
    /// connectionMode
    private String connectionType = "";
    private final BooleanProperty connectionMode = new SimpleBooleanProperty();
    /// MSM/security/authEncryption
    private String authentication = "";
    private String encryption = "";
    private String useOneX = "";
    /// MSM/security/security
    private String keyType = "";
    private String keyProtected = "";
    private String keyMaterial = "";
    // network data
    private Integer status = 0;
    private String macAddress = "";
    private Integer channel = 0;
    private String protocol = "";
    /**
     *
     * @param fileName
     */
    public Profile(String fileName) {
        Map<String, String> data = ParseProfile.get(fileName);
        this.fileName = fileName;
        this.setName(data.get("/name/"));
        //
        this.connectionMode.set(data.get("/connectionMode/").equals("auto"));
        String auth = data.get("/MSM/security/authEncryption/authentication/");
        String KeyMat = "";
        if (!auth.equals("open")) {
           KeyMat = data.get("/MSM/security/sharedKey/keyMaterial/");
        }
        this.setAuthentication(auth);
        this.setKeyMaterial(KeyMat);
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
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the ssid_name
     */
    public String getSsid_name() {
        return ssid_name;
    }

    /**
     * @param ssid_name the ssid_name to set
     */
    public void setSsid_name(String ssid_name) {
        this.ssid_name = ssid_name;
    }

    /**
     * @return the ssid_hex
     */
    public String getSsid_hex() {
        return ssid_hex;
    }

    /**
     * @param ssid_hex the ssid_hex to set
     */
    public void setSsid_hex(String ssid_hex) {
        this.ssid_hex = ssid_hex;
    }

    /**
     * @return the connectionType
     */
    public String getConnectionType() {
        return connectionType;
    }

    /**
     * @param connectionType the connectionType to set
     */
    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    /**
     * @return the connectionMode
     */
    public BooleanProperty getConnectionMode() {
        return connectionMode;
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
     * @return the authentication
     */
    public String getAuthentication() {
        return authentication;
    }

    /**
     * @param authentication the authentication to set
     */
    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    /**
     * @return the encryption
     */
    public String getEncryption() {
        return encryption;
    }

    /**
     * @param encryption the encryption to set
     */
    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    /**
     * @return the useOneX
     */
    public String getUseOneX() {
        return useOneX;
    }

    /**
     * @param useOneX the useOneX to set
     */
    public void setUseOneX(String useOneX) {
        this.useOneX = useOneX;
    }

    /**
     * @return the keyType
     */
    public String getKeyType() {
        return keyType;
    }

    /**
     * @param keyType the keyType to set
     */
    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    /**
     * @return the keyProtected
     */
    public String getKeyProtected() {
        return keyProtected;
    }

    /**
     * @param keyProtected the keyProtected to set
     */
    public void setKeyProtected(String keyProtected) {
        this.keyProtected = keyProtected;
    }

    /**
     * @return the keyMaterial
     */
    public String getKeyMaterial() {
        if ( visiblePassword) {
            return keyMaterial;
        }
        String hidden = "";
        for (int i=0; i<keyMaterial.length(); i++) {
            hidden += "●";
        }
        return hidden;
    }

    /**
     * @param keyMaterial the keyMaterial to set
     */
    public void setKeyMaterial(String keyMaterial) {
        this.keyMaterial = keyMaterial;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    /**
     * @return the status
     */
    public String getStatus() {
        return status + " %";
    }
    /**
     * @return the status
     */
    public Integer getStatusInt() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the macAddress
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * @param macAddress the macAddress to set
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * @return the channel
     */
    public Integer getChannel() {
        return channel;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
