/*
 * Licencia MIT
 *
 * Copyright (c) 2017 @Fitorec <chanerec at gmail.com>.
 *
 * Se encarga de parsear un archivo XML del tipo:
 * URL:  http://www.microsoft.com/networking/WLAN/profile/v1
 *
 */

package main.java.com.mundosica.wifi.manager.Model;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;


/**
 *
 * @author @Fitorec <chanerec at gmail.com>
 */
class ParseProfile {

   private ParseProfile(){}

    /**
     *
     * @param fileName
     * @return
     */
    public static Map<String, String> get(String fileName) {
       if (fileName == null || fileName.length()< 4 ) {
            throw new NullPointerException("input cannot be null");
        }
        Stream<String> fContent = ParseProfile.readFile(fileName);
        ArrayList<String> instance = new ArrayList<>();
        Map<String, String> xmlData = new HashMap();
        fContent.forEach(line -> {
            for (int i=0; i< line.length() - 2; i++) {
                Character c = line.charAt(i);
                // Getting new instance: '<'Instance
                if (c == '<' && line.charAt(i+1) != '?') {
                    if (line.charAt(i+1) == '/') {
                        instance.remove(instance.size()-1);
                    } else {
                        String ins = line.substring(i+1, line.indexOf(">", i+2));
                        if (ins.indexOf("WLANProfile") == 0) {
                            ins = "WLANProfile";
                        }
                        instance.add(ins);
                    }
                    i = line.indexOf(">", i+1) - 1;
                }
                // Getting instance value:  '>'Value
                if (c == '>') {
                    String val = line.substring(i+1, line.indexOf("<", i+2));
                    String key = "/";
                    for (int k = 1; k<instance.size(); k++) {
                        key += instance.get(k) + "/";
                    }
                    xmlData.put(key, val);
                }
            }
        });
        return xmlData;
   }

   private static Stream<String> readFile(String fileName) {
       String fName = NetshWlan.dataPath() + File.separator +  fileName;
       try{
            return (new BufferedReader(new FileReader(new File(fName)))).lines();
       } catch (IOException e) { }
       return null;
   }
}
