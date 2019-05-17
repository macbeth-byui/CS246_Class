package macbeth;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Volume {

    private List<Book> books;
    private String title;


    public Volume() {
        books = new ArrayList<Book>();
    }

    public Book getBook(String name) {
        for (Book book : books) {
            if (book.getTitle().toUpperCase().equals(name.toUpperCase())) {
                return book;
            }
        }
        return null;
    }

    public List<String> getBookNames() {
        List<String> bookNames = new ArrayList<String>();
        for (Book book : books) {
            bookNames.add(book.getTitle());
        }
        return bookNames;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Book> getBooks() {
        return books;
    }
}
