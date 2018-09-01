package uz.newdevoloper.inomjon.tafsir_quron.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.List;
import java.util.Random;

import uz.newdevoloper.inomjon.tafsir_quron.Adapters.VersesAdapter;
import uz.newdevoloper.inomjon.tafsir_quron.R;
import uz.newdevoloper.inomjon.tafsir_quron.model.Verses;
import uz.newdevoloper.inomjon.tafsir_quron.util.OnMediaListener;

public class OyatlarActivity extends AppCompatActivity implements OnMediaListener {
    private VersesAdapter mAdapter;
    private ListView listView;
    private long mTaskId = 0;
    private int mAds;
    private long mAddShow = -1;
    private InterstitialAd mInterstitialAd;
    private AdRequest mAdRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyatlar);
        MobileAds.initialize(this, getString(R.string.fit_ad_unit_id_init));
        mAds=5;
        listView = findViewById(R.id.listV);
        listView.setAdapter(mAdapter = new VersesAdapter(OyatlarActivity.this, R.layout.layout, this));

        mAdRequest = new AdRequest.Builder()/*.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)*/.build();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.fit_ad_unit_id_load));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(mAdRequest);
            }
        });
     //   mInterstitialAd.loadAd(mAdRequest);

        Long surahId = getIntent().getLongExtra("surah_id", -1);
        new LoadTask(++mTaskId, surahId).execute();

    }

    @Override
    public boolean onVersesClick(Verses verses) {
        boolean isLoaded = mInterstitialAd.isLoaded() && mAddShow % mAds == 0;

        if (isLoaded) {
            mInterstitialAd.show();
            if(mAds==20){
                mAds=5;
            }
            else
            mAds+=5;
        }

        mAddShow++;

        return isLoaded;
    }

    class LoadTask extends AsyncTask<Void, Void, List<Verses>> {

        long mId;
        long surahId;

        public LoadTask(long mId, long surahId) {
            this.mId = mId;
            this.surahId = surahId;
            mAdapter.clear();
        }

        @Override
        protected List<Verses> doInBackground(Void... params) {
            return Verses.list(surahId);
        }

        @Override
        protected void onPostExecute(List<Verses> result) {
            super.onPostExecute(result);
            if (mId != mTaskId || result == null || result.isEmpty()) return;

            mAdapter.addAll(result);
            mAdapter.notifyDataSetChanged();
        }
    }


}
