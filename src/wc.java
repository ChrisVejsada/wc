import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class wc {
    private static final int readAheadLimit = 1000000;
    /*
    This programm is meant to count words,characters and lines in a given file and show that number. If the user
    inputs wc then the program should spit out
    */
    static void usage(){
        System.out.println("Usage: " + "Enter in wc -l <filename> will print the line count of a file\n" +
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
            collectinfourpasses(reader);
            /*
            multiple reads is inefficient
             */
            reader.reset();
            collectinonepass(reader);
        }
        catch(FileNotFoundException e){
            usage();
            System.err.println(e);
        }

    }
}
