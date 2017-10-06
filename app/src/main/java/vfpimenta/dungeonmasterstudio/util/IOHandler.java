package vfpimenta.dungeonmasterstudio.util;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOHandler {
    private static final String CHARACTER_FILE = "__cnf_chr_data__.json";
    private static final String LOCATION_FILE = "__cnf_loc_data__.json";
    private static final String ITEM_FILE = "__cnf_itm_data__.json";
    private static final String NOTE_FILE = "__cnf_nte_data__.json";

    private static final String PERSISTENCE_EXCEPTION_TAG = "Persistence error";

    public static void storeCharacterData(Context context, String content){
        storeData(context, content, CHARACTER_FILE);
    }

    public static String loadCharacterData(Context context){
        return loadData(context, CHARACTER_FILE);
    }

    public static void storeLocationData(Context context, String content){
        storeData(context, content, LOCATION_FILE);
    }

    public static String loadLocationData(Context context){
        return loadData(context, LOCATION_FILE);
    }

    public static void storeItemData(Context context, String content){
        storeData(context, content, ITEM_FILE);
    }

    public static String loadItemData(Context context){
        return loadData(context, ITEM_FILE);
    }

    public static void storeNoteData(Context context, String content){
        storeData(context, content, NOTE_FILE);
    }

    public static String loadNoteData(Context context){
        return loadData(context, NOTE_FILE);
    }

    private static void storeData(Context context, String content, String file){
        try{
            FileOutputStream fos = context.openFileOutput(file, context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        } catch (FileNotFoundException fnfe){
            Log.e(PERSISTENCE_EXCEPTION_TAG, fnfe.getMessage());
        } catch (IOException ioe) {
            Log.e(PERSISTENCE_EXCEPTION_TAG, ioe.getMessage());
        }
    }

    private static String loadData(Context context, String file){
        try {
            FileInputStream fis = context.openFileInput(file);
            StringBuffer sb = new StringBuffer("");
            byte[] buffer = new byte[1024];
            int n;

            while ((n = fis.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, n));
            }
            fis.close();

            return sb.toString();
        } catch (FileNotFoundException fnfe){
            Log.e(PERSISTENCE_EXCEPTION_TAG, fnfe.getMessage());
        } catch (IOException ioe) {
            Log.e(PERSISTENCE_EXCEPTION_TAG, ioe.getMessage());
        }

        return "";
    }
}
