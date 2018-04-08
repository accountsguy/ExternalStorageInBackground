package accountsguy.net.externalstorageinbackground;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by advic on 02/04/2018.
 */



public class FileTask extends AsyncTask<File, String, String> {
//public class FileTask extends AsyncTask<Parameter, Progress, Result>


    TextView textView = null;
    File inputFile = null;
    String backggroundMessage ="No Data";
    int buttonId;

    public FileTask(File inputFile, TextView showData, int id){
        this.inputFile = inputFile;
        textView = showData;
        buttonId = id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //    Prarameter are only taken in doInBackground Method
    @Override
    protected String doInBackground(File... files) {
        inputFile = files[0];
        try{
            FileInputStream inputStreamReader = new FileInputStream(inputFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    (inputStreamReader)));
            if(!inputFile.exists()){
                backggroundMessage = "File or Directory not found";
            }
            else{
                String line = null;
                String values = "";
                while((line = bufferedReader.readLine()) != null )
                {
                    values = values.concat(line);
                }
                backggroundMessage = values;
            }
        } catch (Exception e){
            backggroundMessage = e.getMessage().toString();
            return backggroundMessage;
        }
        return backggroundMessage;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        textView.setText(backggroundMessage);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(inputFile.exists()){

        }
        textView.setText(s);
    }
}
