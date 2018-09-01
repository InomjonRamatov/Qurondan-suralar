package uz.newdevoloper.inomjon.tafsir_quron.Activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import uz.newdevoloper.inomjon.tafsir_quron.R;
import uz.newdevoloper.inomjon.tafsir_quron.model.Surah;

public class HadislarActivity extends AppCompatActivity {
    long mTaskId = 0;
    private WebView webView;
    private AdView mAdView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadislar);
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, getString(R.string.ad_unit_id_init));

        webView = findViewById(R.id.webView);
        mAdView = findViewById(R.id.adView);


        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setDisplayZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);

    //    mAdView.loadAd(new AdRequest.Builder()/*.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)*/.build());

        Long surahId = getIntent().getLongExtra("surah_id", -1);
        new LoadTask(++mTaskId, surahId).execute();
    }

    class LoadTask extends AsyncTask<Void, Void, Surah> {

        long mId;
        long surahId;

        public LoadTask(long mId, long surahId) {
            this.mId = mId;
            this.surahId = surahId;
        }

        @Override
        protected Surah doInBackground(Void... params) {
            return Surah.get(surahId);
        }

        @Override
        protected void onPostExecute(Surah result) {
            super.onPostExecute(result);
            if (mId != mTaskId || result == null) return;
            String url = "file:///android_asset/www/" + result.path + ".htm";
            webView.loadUrl(url);
        }
    }
}
