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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * 
 * @author @Fitorec <chanerec at gmail.com>
 */
abstract class ProfileAbstract {
    protected String fileName = null;
    protected String name = "";
    /// SSIDConfig/SSID
    protected String ssid_name = "";
    protected String ssid_hex = "";
    /// connectionMode
    protected String connectionType = "";
    protected BooleanProperty connectionMode = new SimpleBooleanProperty();
    /// MSM/security/authEncryption
    protected String authentication = "";
    protected String encryption = "";
    protected String useOneX = "";
    /// MSM/security/security
    protected String keyType = "";
    protected String keyProtected = "";
    protected String keyMaterial = "";
    // network data
    protected Integer status = 0;
    protected String macAddress = "";
    protected Integer channel = 0;
    protected String protocol = "";

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
     * @return the name
     */
    public String getName() {
        return name;
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
     * @param connectionMode the connectionMode to set
     */
    public void setConnectionMode(BooleanProperty connectionMode) {
        this.connectionMode = connectionMode;
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
        return keyMaterial;
    }

    /**
     * @param keyMaterial the keyMaterial to set
     */
    public void setKeyMaterial(String keyMaterial) {
        this.keyMaterial = keyMaterial;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
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
