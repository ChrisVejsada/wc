import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.io.*;

public class wc {
    /*int charCount;
    int wordCount;
    int lineCount;
    */
    private static final int readAheadLimit = 1000000;
    private static final Pattern nonWord = Pattern.compile("\\W");
    /*
    This program is meant to count words,characters and lines in a given file and show that number. If the user
    inputs wc then the program should spit out
    */
    static void usage(){
        System.out.println("How to use: \n" + "Enter in wc -l <filename> will print the line count of a file\n" +
                "wc -c <filename> will print the character count\n" +
                "wc -w <filename> will print the word count\n" +
                "wc <filename> will print all of the above");

    }
    public static void main(String[] args) throws IOException{
        if (args.length != 1){
            usage();
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            reader.mark(readAheadLimit);
            readingdata(reader);
            /*
            multiple reads is inefficient
             */
            reader.reset();
            readingdata(reader);
        }
        catch(FileNotFoundException e){
            usage();
            System.err.println(e);
        }

    }
    private static void readingdata(BufferedReader reader) throws IOException{
        reader.reset();
        System.out.println("Character count = " + reader.lines().flatMapToInt(String::chars).count());

        reader.reset();
        System.out.println("Word count = " + reader.lines().flatMap(nonWord::splitAsStream)
                .filter(str -> !str.isEmpty()).count());

        reader.reset();
        System.out.println("Line count = " + reader.lines().count());

        wcdata wc = reader.lines().parallel().collect(wcdata::new,wcdata::accept,wcdata::list);
        System.out.println(wc);

    }
    private static void readingdataone(BufferedReader reader){
        wcdata wc = reader.lines().parallel().collect(wcdata::new,wcdata::accept,wcdata::list);
        System.out.println(wc);
    }

    private static class wcdata implements Consumer<String>{
        private long charCount;
        private long lineCount;
        private long wordCount;


        public void accept(String line){
            charCount += line.length();
            lineCount++;

            wordCount += nonWord.splitAsStream(line).filter(str -> !str.isEmpty()).count();
        }

        public void list(wcdata stat){
            wordCount += stat.wordCount;
            lineCount += stat.lineCount;
            charCount += stat.charCount;
        }


    }

}
