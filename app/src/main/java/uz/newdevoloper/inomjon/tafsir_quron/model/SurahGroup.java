package uz.newdevoloper.inomjon.tafsir_quron.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Jason on 1/17/2018.
 */

@Table(name = "SurahGroup")
public class SurahGroup extends Model {

    @Column
    public String title;

    public static SurahGroup get(long id) {
        return new Select()
                .from(SurahGroup.class)
                .where("id = ?", id)
                .executeSingle();
    }

    public static List<SurahGroup> list() {
        return new Select()
                .from(SurahGroup.class)
                .execute();
    }

    public SurahGroup title(String title) {
        this.title = title;
        return this;
    }
}
