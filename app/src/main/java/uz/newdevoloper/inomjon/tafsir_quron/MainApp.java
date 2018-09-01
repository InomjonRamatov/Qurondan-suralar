package uz.newdevoloper.inomjon.tafsir_quron;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

/**
 * Created by Jason on 1/17/2018.
 */

public class MainApp extends Application {
    private static Configuration configuration;
    @Override
    public void onCreate() {
        super.onCreate();

        configuration = new Configuration.Builder(this).create();
        ActiveAndroid.initialize(configuration);
    }

    public static Configuration getConfiguration() {
        return configuration;
    }
}
