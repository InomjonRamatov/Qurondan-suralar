package uz.newdevoloper.inomjon.tafsir_quron.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Inomjon on 09.12.2017.
 */

@Table(name = "Verses")
public class Verses extends Model {

    @Column
    public Surah surah;

    @Column
    public String text;

    @Column
    public String fileName;

    @Column
    public int recId;

    public static List<Verses> list(long surah) {
        return new Select()
                .from(Verses.class)
                .where("surah = ?", surah)
                .execute();
    }

    public Verses surah(Surah surah) {
        this.surah = surah;
        return this;
    }

    public Verses text(String text) {
        this.text = text;
        return this;
    }

    public Verses fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public Verses recId(int recId) {
        this.recId = recId;
        return this;
    }
}
