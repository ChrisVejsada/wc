//Christopher Vejsada
//CSC 131 Individual project
import java.io.*;
//import picocli.CommandLine;
import java.util.LinkedList;

public class MetricsApp {

    public static void main(String[] args) throws IOException {
        Metric myMetrics = new Metric();
        ((Metric) myMetrics).run(args);
    }

}
