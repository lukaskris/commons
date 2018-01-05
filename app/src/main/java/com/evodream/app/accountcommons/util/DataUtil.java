package com.evodream.app.accountcommons.util;

import android.net.Uri;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Erick Pranata
 * @since 2017/06/23
 */

public class DataUtil {

    public static String getCleanString(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static String getCleanPhoneString(String phoneString) {
        phoneString = phoneString.replaceAll("[ #*.,();\\-]", "");
        if(phoneString.length()>0) {
            phoneString = phoneString.substring(0, 1).equals("0") ? phoneString.substring(1) : phoneString;
            phoneString = phoneString.substring(0, 2).equals("62") ? phoneString.substring(2) : phoneString;
            phoneString = phoneString.substring(0, 3).equals("+62") ? phoneString.substring(3) : phoneString;
        }
        return phoneString;
    }

    public static Uri normalize(Uri uri) {
        if (uri.getScheme() == null || uri.getScheme().equals("file")) {
            return Uri.parse("file://" + uri.getPath());
        } else {
            return uri;
        }
    }

    public static String normalize(String uriString) {
        if (uriString.startsWith("/")) {
            return "file://" + uriString;
        } else {
            return uriString;
        }
    }

    public static void saveObject(File file, Serializable object) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(object);
        os.close();
        fos.close();
    }

    public static Serializable loadObject(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream is = new ObjectInputStream(fis);
        Serializable serializable = (Serializable) is.readObject();
        is.close();
        fis.close();

        return serializable;
    }

}
