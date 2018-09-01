package uz.newdevoloper.inomjon.tafsir_quron.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Jason on 1/17/2018.
 */

@Table(name = "Surah")
public class Surah extends Model {

    /*@Column
    public SurahGroup surahGroup;*/

    @Column
    public String title;

    @Column
    public String path;

    public static Surah get(long id) {
        return new Select()
                .from(Surah.class)
                .where("id = ?", id)
                .executeSingle();
    }

    public static List<Surah> list() {
        return new Select()
                .from(Surah.class)
                .execute();
    }

    public Surah title(String title) {
        this.title = title;
        return this;
    }

    public Surah path(String path) {
        this.path = path;
        return this;
    }

    @Override
    public String toString() {
        return "Surah{" +
                "title='" + title + '\'' +
                "path='" + path + '\'' +
                '}';
    }
}
