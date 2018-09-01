package uz.newdevoloper.inomjon.tafsir_quron.util;

import android.util.Log;

import com.activeandroid.ActiveAndroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by Jason on 12/12/2017.
 */

public class DBUtil {

    private static final String TAG = DBUtil.class.getSimpleName();

    public static void exportDbToFile(File path) {
        try {

//            configuration.getContext().getDatabasePath(configuration.getDatabaseName())
//            File sd = Environment.getExternalStorageDirectory();
            File exportFile = new File(ActiveAndroid.getDatabase().getPath());//CacheNew.loadDBFile();

            if (path.canWrite()) {
//                        String backupDBPath = "/BackupFolder/DatabaseName";
                File backupDB = new File(path, "export.sql");

//                            backupDB.mkdirs();

                FileChannel src = new FileInputStream(exportFile).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
//                        Toast.makeText(MainApp.getContext(), backupDB.toString(),
//                                Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
//                    Toast.makeText(MainApp.getContext(), e.toString(), Toast.LENGTH_LONG)
//                            .show();
            Log.e(TAG, e.getMessage(), e);

        }
    }

    public static void syncDbWithServer() {
       /* //todo connection test to kvikk_superserver
        if (true) {
            snack(v, "not finished yet!");//;R.string.please_on_internet);
//                    String superServerUrl = configHelper.superServerUrl();
//                    snack(v, "superServerUrl : " + superServerUrl);//;R.string.please_on_internet);
            return;
        }*/
    }
}
