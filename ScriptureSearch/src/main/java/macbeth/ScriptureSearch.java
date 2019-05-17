package macbeth;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ScriptureSearch {

    private Library library;

    public ScriptureSearch() {
        library = new Library();
    }

    public void downloadFullLibrary() {

        new Thread() {
            public void run() {
                library.loadVolume("Book of Mormon", "https://raw.githubusercontent.com/bcbooks/scriptures-json/master/book-of-mormon.json");
            }
        }.start();
        new Thread() {
            public void run() {
                library.loadVolume("Old Testament", "https://raw.githubusercontent.com/bcbooks/scriptures-json/master/old-testament.json");
            }
        }.start();
        new Thread() {
            public void run() {
                library.loadVolume("New Testament", "https://raw.githubusercontent.com/bcbooks/scriptures-json/master/new-testament.json");
            }
        }.start();
        new Thread() {
            public void run() {
                library.loadVolume("Pearl of Great Price", "https://raw.githubusercontent.com/bcbooks/scriptures-json/master/pearl-of-great-price.json");
            }
        }.start();
        new Thread() {
            public void run() {
                library.loadBook("D&C","https://raw.githubusercontent.com/bcbooks/scriptures-json/master/doctrine-and-covenants.json");
            }
        }.start();

    }

    public void searchWord(String word) {
        word = word.toUpperCase();
        int count = 0;
        System.out.println();
        System.out.println("Search Results:");
        System.out.println("-------------------------");
        for (Volume volume : library.getVolumes()) {
            for (Book book : volume.getBooks()) {
                for (Chapter chapter : book.getChapters()) {
                    for (Verse verse : chapter.getVerses()) {
                        if (verse.getText().toUpperCase().contains(word)) {
                            System.out.println(verse.getReference() + " - " + verse.getText());
                            count++;
                        }
                    }
                }
            }
        }
        System.out.println();
        System.out.println("Total Count: " + count);
    }

    public void displayScripture(String book, String chapter, String verse) {
        Book bookObj = null;
        System.out.println();
        for (Volume volume : library.getVolumes()) {
            bookObj = volume.getBook(book);
            if (bookObj != null) {
                break;
            }
        }
        if (bookObj == null) {
            System.out.println("Invalid book name.");
            return;
        }
        try {
            Chapter chapterObj = bookObj.getChapter(Integer.parseInt(chapter));
            if (chapterObj == null) {
                System.out.println("Invalid chapter.");
            }
            Verse verseObj = chapterObj.getVerse(Integer.parseInt(verse));
            if (verseObj == null) {
                System.out.println("Invalid verse.");
            }
            System.out.println(verseObj.getReference());
            System.out.println(verseObj.getText());
        }
        catch (NumberFormatException nfe) {
            System.out.println("Chapter and Verse must both be numbers.");
        }
    }

    public void displayChapter(String book, String chapter) {
        Book bookObj = null;
        System.out.println();
        for (Volume volume : library.getVolumes()) {
            bookObj = volume.getBook(book);
            if (bookObj != null) {
                break;
            }
        }
        if (bookObj == null) {
            System.out.println("Invalid book name.");
            return;
        }
        try {
            Chapter chapterObj = bookObj.getChapter(Integer.parseInt(chapter));
            if (chapterObj == null) {
                System.out.println("Invalid chapter.");
            }
            System.out.println(book + " Chapter " + chapter);
            for (Verse verse : chapterObj.getVerses()) {
                System.out.print(verse.getVerse() + ". ");
                System.out.println(verse.getText());
            }
        }
        catch (NumberFormatException nfe) {
            System.out.println("Chapter must both be a number.");
        }
    }

    public void displayStats() {
        int numVolumes = 0;
        int numBooks = 0;
        int numChapters = 0;
        int numVerses = 0;
        int numWords = 0;

        for (Volume volume : library.getVolumes()) {
            numVolumes++;
            for (Book book : volume.getBooks()) {
                numBooks++;
                for (Chapter chapter : book.getChapters()) {
                    numChapters++;
                    for (Verse verse : chapter.getVerses()) {
                        numVerses++;
                        numWords += verse.getText().split(" ").length;
                    }
                }
            }
        }
        System.out.println();
        System.out.println("Statistics:");
        System.out.println("-------------------------");
        System.out.println("Volumes: " + numVolumes);
        System.out.println("Books: " + numBooks);
        System.out.println("Chapters: " + numChapters);
        System.out.println("Verses: " + numVerses);
        System.out.println("Words: " + numWords);
    }

    public void randomScripture() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(library.getVolumes().size());
        Volume volume = library.getVolumes().get(randomIndex);
        randomIndex = rand.nextInt(volume.getBooks().size());
        Book book = volume.getBooks().get(randomIndex);
        randomIndex = rand.nextInt(book.getChapters().size());
        Chapter chapter = book.getChapters().get(randomIndex);
        randomIndex = rand.nextInt(chapter.getVerses().size() - 1);
        Verse verse = chapter.getVerses().get(randomIndex);

        System.out.println();
        System.out.println(verse.getReference());
        System.out.println(verse.getText());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ScriptureSearch ss = new ScriptureSearch();
        ss.downloadFullLibrary();
        String choice;
        do {
            System.out.println();
            System.out.println("Scripture Search menu");
            System.out.println("1) Search for Word");
            System.out.println("2) Get Single Scripture");
            System.out.println("3) Get Whole Chapter");
            System.out.println("4) Stats");
            System.out.println("5) Random Scripture");
            System.out.println("9) Exit");
            System.out.print("> ");
            choice = scanner.nextLine();
            if (choice.equals("1")) {
                System.out.print("Enter word to search: ");
                String word = scanner.nextLine();
                ss.searchWord(word);
                System.out.println();
            }
            else if (choice.equals("2")) {
                System.out.print("Enter book: ");
                String book = scanner.nextLine();
                System.out.print("Enter chapter: ");
                String chapter = scanner.nextLine();
                System.out.print("Enter verse: ");
                String verse = scanner.nextLine();
                ss.displayScripture(book, chapter, verse);
            }
            else if (choice.equals("3")) {
                System.out.print("Enter book: ");
                String book = scanner.nextLine();
                System.out.print("Enter chapter: ");
                String chapter = scanner.nextLine();
                ss.displayChapter(book, chapter);
            }
            else if (choice.equals("4")) {
                ss.displayStats();
            }
            else if (choice.equals("5")) {
                ss.randomScripture();
            }
            else if (choice.equals("9")) {
                System.out.println("Good Bye!");
            }
            else {
                System.out.println("Invalid option");
            }
        }
        while (!choice.equals("9"));
    }
}
