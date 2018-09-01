package uz.newdevoloper.inomjon.tafsir_quron.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.Configuration;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;

import uz.newdevoloper.inomjon.tafsir_quron.MainApp;
import uz.newdevoloper.inomjon.tafsir_quron.R;
import uz.newdevoloper.inomjon.tafsir_quron.model.Surah;
import uz.newdevoloper.inomjon.tafsir_quron.model.Verses;

/**
 * Created by Jason on 1/17/2018.
 */

public class ModelGeneratorActivity extends Activity {

    private static String TAG = ModelGeneratorActivity.class.getSimpleName();

    private TextView txtLog;

    public static void exportDbToFile(File exportFile, File pathToExport, String fileName) {
        try {
            File sd = pathToExport == null ? Environment.getExternalStorageDirectory() : pathToExport;

            if (sd.canWrite()) {
//                        String backupDBPath = "/BackupFolder/DatabaseName";
                File backupDB = new File(sd, "export.sql");

//                            backupDB.mkdirs();

                FileChannel src = new FileInputStream(exportFile).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_generator);

        txtLog = (TextView) findViewById(R.id.txtLog);

    }

    public void onGenerate(View view) {
       /* ListA<Surah> list = Surah.list();
        Snackbar.make(txtLog, "list = " + list, 10000).show();*/

//        List<Verses> list = Verses.list();
//        Snackbar.make(txtLog, "list = " + list, 10000).show();

      /*  Configuration configuration = MainApp.getConfiguration();
        File db = configuration.getContext().getDatabasePath(configuration.getDatabaseName());

//                    DBUtil.exportDbToFile(myFile);
        exportDbToFile(db, null, configuration.getDatabaseName());*/
       /* Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        txtLog.setText(txtLog.getText() + "\nHelp");
                        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                        i.addCategory(Intent.CATEGORY_DEFAULT);
                        startActivityForResult(Intent.createChooser(i, "Choose directory"), 9999);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    }
                }).check();*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 9999:
                if (resultCode == RESULT_OK) {
                    Configuration configuration = MainApp.getConfiguration();
                    File db = configuration.getContext().getDatabasePath(configuration.getDatabaseName());

                    File myFile = new File(data.getData().getPath());
                    exportDbToFile(db, myFile, configuration.getDatabaseName());
//                    DBUtil.exportDbToFile(myFile);
//                    Toast.makeText(this, "myFile.getAbsolutePath() = " + myFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(this, "ActiveAndroid.getDatabase().getPath() =  " + ActiveAndroid.getDatabase().getPath(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
