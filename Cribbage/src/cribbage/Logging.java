package cribbage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class  Logging {
    private static Logging _instance = null;
    private final String fileName = "cribbage.log";

    private Logging(){
        // make a new file writer so that it will not keep the previous run
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))){
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logging getInstance(){
        if (_instance == null){
            _instance = new Logging();
        }
        return _instance;
    }


    public void addToLog(String logInput) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            pw.println(logInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}