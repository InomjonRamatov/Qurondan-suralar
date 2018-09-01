package uz.newdevoloper.inomjon.tafsir_quron.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import uz.newdevoloper.inomjon.tafsir_quron.MainApp;
import uz.newdevoloper.inomjon.tafsir_quron.R;
import uz.newdevoloper.inomjon.tafsir_quron.model.Verses;
import uz.newdevoloper.inomjon.tafsir_quron.util.OnMediaListener;

import static android.provider.Settings.Global.getString;

/**
 * Created by Inomjon on 09.12.2017.
 */

public class VersesAdapter extends ArrayAdapter<Verses> {
    private int resource;
    private MediaPlayer mediaPlayer;
    private int playingPosition = -1;
    private OnMediaListener onMediaListener;

    public VersesAdapter(@NonNull Context context, int resource, OnMediaListener onMediaListener) {
        super(context, resource);
        this.resource = resource;
        this.onMediaListener = onMediaListener;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(resource, parent, false);
        }

        TextView txt = convertView.findViewById(R.id.txtV);
        final Button btn1 = convertView.findViewById(R.id.btn1);
        final Button btn2 = convertView.findViewById(R.id.btn2);


        final Verses verses = getItem(position);
        String path_file = "www/" + verses.fileName + "/" + verses.text;

        final String myText = openFile(path_file);
//        String[] lines = myText.split("\\d+");
//        final String html_tag_bold="<b><font color=\\\"%s\\\">%s</font>";
//        final String html_tag_italic="<i><font color=\\\"%s\\\">%s</font></i>";
//        txt.setText(Html.fromHtml(String.format(html_tag_bold,"#000",lines[0])));
//        txt2.setText(Html.fromHtml(String.format(html_tag_italic,"#000",lines[1])));
////        txt3.setText(Html.fromHtml(String.format(html_tag_italic,"#000",lines[2])));
        txt.setText(myText);
        btn1.setBackgroundResource(playingPosition == position ? R.drawable.img_btn_pause : R.drawable.img_btn_play);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMediaListener != null && onMediaListener.onVersesClick(verses)) return;

                if (mediaPlayer != null && mediaPlayer.isPlaying() && playingPosition == position) {
                    playingPosition = -1;
                    mediaPlayer.pause();
                } else {
                    stopPlaying();
                    mediaPlayer = MediaPlayer.create(getContext(),
                            Uri.parse("android.resource://" + MainApp.class.getPackage().getName() +
                                    "/raw/" + verses.fileName + (verses.recId + 1)));
                    playingPosition = position;
                    mediaPlayer.start();
                }
                notifyDataSetChanged();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = getContext();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, c.getString(R.string.share_subject));
                intent.putExtra(Intent.EXTRA_TEXT, String.format(c.getString(R.string.share_body), myText));
                c.startActivity(Intent.createChooser(intent, c.getString(R.string.share_title)));
            }
        });

        return convertView;
    }


    public void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public String openFile(String file_name) {
        String text = "";
        try {
            InputStream inStream = getContext().getResources().getAssets().open(file_name);
            if (inStream != null) {
                InputStreamReader tmp = new InputStreamReader(inStream);
                BufferedReader reader = new BufferedReader(tmp);

                StringBuffer buffer = new StringBuffer();
                while ((text = reader.readLine()) != null) {
                    buffer.append(text + "\n");
                }
                text = buffer.toString();
            }
        } catch (Throwable throwable) {
          //  Toast.makeText(getContext(), "Exception:" + throwable.toString(), Toast.LENGTH_LONG).show();
            Log.d("Exception:",throwable.toString());
        }
        return text;
    }
}
