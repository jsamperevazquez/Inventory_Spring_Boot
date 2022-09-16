package com.company.inventory.response;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Jose Angel Sampere Vazquez
 * @version 0.0.1
 * Class for create the structure to metaData response
 */
public class ResponseRest {
    /**
     * ArrayList with hashMap to assign the metadata response
     */
    private ArrayList <HashMap<String, String>> metadata = new ArrayList<>();

    /**
     * Get method
     * @return metadata response
     */
    public ArrayList<HashMap<String, String>> getMetadata() {
        return metadata;
    }

    public void setMetadata(String type, String code, String date) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", type);
        map.put("code", code);
        map.put("date", date);

        metadata.add(map);
    }
}
