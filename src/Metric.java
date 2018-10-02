import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.regex.Pattern;
//import picocli.CommandLine.Option;
//import picocli.CommandLine.Parameters;
import java.io.File;
import java.io.*;

public class Metric {

    /*int charCount;
    int java;
    int wordCount;
    int lineCount;
    */
    private static final int readAheadLimit = 1000000;
    private static final Pattern nonWord = Pattern.compile("\\W");
    /*
    This program is meant to count words,characters and lines in a given file and show that number. If the user
    inputs Metric then the program should spit out
    */
    static void usage(){
        System.out.println("How to use: \n" + "Enter in Metric -l <filename> will print the line count of a file\n" +
                "wc -c <filename> will print the character count\n" +
                "wc -w <filename> will print the word count\n" +
                "wc <filename> will print all of the above");

    }
    //where the program takes the input from the args
    public static void main(String[] args) throws IOException{
        if (args.length != 1){
            usage();
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            reader.mark(readAheadLimit);
            readingdata(reader);

        }
        if (args[0].equals("-l")) {
            usage();
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))){
            reader.mark(readAheadLimit);
            //System.out.println("Line count = " + reader.lines().count());
        }
        if (args[0].equals("-w")) {
            usage();
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            reader.mark(readAheadLimit);
            reader.reset();
            //System.out.println("Word count = " + reader.lines().flatMap(nonWord::splitAsStream)
            //        .filter(str -> !str.isEmpty()).count());
        }
        if (args[0].equals("-c")) {
            usage();
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            reader.mark(readAheadLimit);
            reader.reset();
            //System.out.println("Character count = " + reader.lines().flatMapToInt(String::chars).count());
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

    }

    //Private seemed to be the only way to do this part much like the example shown in class
    private static class Metricdata implements Consumer<String>{
        private long charCount;
        private long lineCount;
        private long wordCount;

        @Override
        public void accept(String line){
            charCount += line.length();
            lineCount++;

            wordCount += nonWord.splitAsStream(line).filter(str -> !str.isEmpty()).count();
        }

        public void list(Metricdata stat){
            wordCount += stat.wordCount;
            lineCount += stat.lineCount;
            charCount += stat.charCount;
        }

    }

}