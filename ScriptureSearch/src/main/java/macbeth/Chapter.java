package macbeth;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
    private List<Verse> verses;
    @SerializedName(value="chapter", alternate = {"section"})
    private int chapter;

    public Verse getVerse(int verse) {
        if (verse <= 0 || verse > verses.size())
            return null;
        return verses.get(verse-1);

    }

    public List<String> getVerseText() {
        List<String> verseText = new ArrayList<String>();
        List<Verse> selectedList;
        selectedList = verses;
        for (int i=0; i<selectedList.size(); i++) {
            verseText.add(String.valueOf(selectedList.get(i).getVerse())+". "+selectedList.get(i).getText());
        }
        return verseText;
    }

    public int getChapter() {
        return chapter;
    }

    public List<Verse> getVerses() {
        return verses;
    }
}
