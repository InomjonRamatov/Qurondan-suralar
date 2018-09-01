package uz.newdevoloper.inomjon.tafsir_quron.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import uz.newdevoloper.inomjon.tafsir_quron.Adapters.ExpandableListAdapter;
import uz.newdevoloper.inomjon.tafsir_quron.R;
import uz.newdevoloper.inomjon.tafsir_quron.model.Surah;

public class MainActivity extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    HashMap<String, List<String>> listDataChild;

    MediaPlayer mediaPlayer;
    private InterstitialAd mInterstitialAd;
    private AdRequest mAdRequest;
    private int[] recId=new int[10];
    private int[] dua=new int[10];
    private long back_pressed;
    Handler handler=new Handler();
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_body));
                startActivity(Intent.createChooser(intent,getString(R.string.share_title)));
            }
        });

        //My code
        recId= new int[]{R.raw.fotiha, R.raw.yasin, R.raw.kavsar, R.raw.ixlos,R.raw.falaq,R.raw.nas};
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        List<Surah> listDataHeader = prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list mAdapter
        expListView.setAdapter(listAdapter);
        //to here
      //
        MobileAds.initialize(this, getString(R.string.fit_ad_unit_id_init));
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

    }

    private List<Surah> prepareListData() {
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        List<String> s1 = new ArrayList<String>();
        s1.add(getString(R.string.hadis));
        s1.add(getString(R.string.tafsir));
        s1.add(getString(R.string.tinglash));

        List<String> s36 = new ArrayList<String>();
        s36.add(getString(R.string.hadis));
        s36.add(getString(R.string.tafsir));
        s36.add(getString(R.string.tinglash));

        List<String> s108 = new ArrayList<String>();
        s108.add(getString(R.string.hadis));
        s108.add(getString(R.string.tafsir));
        s108.add(getString(R.string.tinglash));

        List<String> s112 = new ArrayList<String>();
        s112.add(getString(R.string.hadis));
        s112.add(getString(R.string.tafsir));
        s112.add(getString(R.string.tinglash));

        List<String> s113 = new ArrayList<String>();
        s113.add(getString(R.string.hadis));
        s113.add(getString(R.string.tafsir));
        s113.add(getString(R.string.tinglash));

        List<String> s114 = new ArrayList<String>();
        s114.add(getString(R.string.hadis));
        s114.add(getString(R.string.tafsir));
        s114.add(getString(R.string.tinglash));



        final List<Surah> listDataHeader = Surah.list();
        listDataChild.put(listDataHeader.get(0).title, s1);
        listDataChild.put(listDataHeader.get(1).title, s36);
        listDataChild.put(listDataHeader.get(2).title, s108);
        listDataChild.put(listDataHeader.get(3).title, s112);
        listDataChild.put(listDataHeader.get(4).title, s113);
        listDataChild.put(listDataHeader.get(5).title, s114);


        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                if (listDataHeader == null) return false;

                    runnable=new Runnable() {
                        @Override
                        public void run() {
                            if(mInterstitialAd.isLoaded()){
                                mInterstitialAd.show();
                            }
                        }
                    };
                    handler.postDelayed(runnable,600000);
                if(childPosition==2){
                    if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                        Toast.makeText(getApplicationContext(),"Sura qiroati davom etyapti...", Toast.LENGTH_LONG).show();
                    }else{
                        if(mediaPlayer!=null){
                            mediaPlayer.release();
                        }
                        mediaPlayer=MediaPlayer.create(MainActivity.this,recId[groupPosition]);
                        mediaPlayer.start();
                        snackBar("Sura qiroati","Tugatish","Tugatildi",mediaPlayer.getDuration());

                    }
                }
                else
                    {
                Class activityClass = childPosition == 0 ? HadislarActivity.class : OyatlarActivity.class;
                Intent intent = new Intent(v.getContext(), activityClass);
                intent.putExtra("surah_id", listDataHeader.get(groupPosition).getId());
                startActivity(intent);
                }
                return false;
            }
        });

        return listDataHeader;

    }

private void snackBar(String message, String btn_txt, final String last_message, final int time){
    Snackbar snackbar = Snackbar
            .make(getCurrentFocus(), message, time)
            .setAction(btn_txt, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(getCurrentFocus(),last_message,Snackbar.LENGTH_LONG).show();
                    mediaPlayer.release();
                    mediaPlayer=null;
                }
            });

    snackbar.setActionTextColor(Color.RED);
    View sbView = snackbar.getView();
    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
    textView.setTextColor(Color.GREEN);
    snackbar.show();
}

    public void setLanguage(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }
    public void onBackPressed(){
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
            super.onBackPressed();
        }
        else{
            Toast.makeText(getBaseContext(),
                    "Dasturdan hiqish uchun yana bir marta bosing!", Toast.LENGTH_SHORT)
                    .show();
        }
        back_pressed = System.currentTimeMillis();
    }
}
