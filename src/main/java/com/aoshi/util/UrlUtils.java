package com.aoshi.util;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * UrlUtils
 *
 * @author zf
 * @date 15/11/10
 */
public class UrlUtils {
    public static boolean isValidUrl(String urlString){
        URI uri = null;
        try {
            uri = new URI(urlString);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }

        if(uri.getHost() == null){
            return false;
        }
        if(uri.getScheme().equalsIgnoreCase("http") || uri.getScheme().equalsIgnoreCase("https")){
            return true;
        }
        return false;
    }
}
