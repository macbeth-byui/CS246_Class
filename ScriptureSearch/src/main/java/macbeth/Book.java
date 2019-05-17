package macbeth;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Book {
    @SerializedName(value="chapters", alternate = {"sections"})
    private List<Chapter> chapters;
    @SerializedName(value="book", alternate = {"title"})
    private String title;

    public Book() {
        chapters = new ArrayList<Chapter>();
    }

    public Chapter getChapter(int chapter) {
        if (chapter <= 0 || chapter > chapters.size())
            return null;
        return chapters.get(chapter-1);
    }

    public String getTitle() {
        return title;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }
}


