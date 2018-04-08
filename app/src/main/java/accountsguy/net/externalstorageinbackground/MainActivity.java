package accountsguy.net.externalstorageinbackground;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextInputEditText textInputEditText;
    String result;
    TextView showData;
    int writeExternalPermission;
//    int readExternalPermission;
    String state;
    File directory;
    boolean taskstatus=false;
    int writePermission;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInputEditText = (TextInputEditText) findViewById(R.id.text);
        showData = (TextView) findViewById(R.id.text2);
        Button button = (Button) findViewById(R.id.button);
        Button deleteButton = (Button) findViewById(R.id.deletebutton);

        button.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        writeExternalPermission = checkSelfPermission(Manifest.permission
                .WRITE_EXTERNAL_STORAGE);
//        readExternalPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);


        if (writeExternalPermission != PackageManager.PERMISSION_GRANTED) { // ||readExternalPermission!= PackageManager.PERMISSION_GRANTED){
//            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1);
        }
    }


    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                if(writeExternalPermission == PackageManager.PERMISSION_GRANTED){
                    state = Environment.getExternalStorageState();
                    try {
                        directory = Environment.getExternalStorageDirectory();
                        if (Environment.MEDIA_MOUNTED.equals(state) && !textInputEditText.getText
                                ().toString().isEmpty()) {
                            File writeEx_File = new File(directory, "ExternalFile.txt");
                            FileWriter fileWriter = new FileWriter(writeEx_File);
                            fileWriter.append(textInputEditText.getText().toString().trim());
                            fileWriter.flush();
                            fileWriter.close();

                            FileTask fileTask = new FileTask(writeEx_File, showData, R.id.button);
                            fileTask.execute(new File[]{writeEx_File});

                        } else {
                            Toast.makeText(this, "No External Storage Found or You have not " +
                                            "entered data in the file." ,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(this, "Exception: "+e.getMessage(),Toast.LENGTH_SHORT)
                                .show();
                    }
                }
                else{
                    if(writeExternalPermission != PackageManager.PERMISSION_GRANTED){// ||readExternalPermission!= PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                        state = Environment.getExternalStorageState();
                        try {
                            directory = Environment.getExternalStorageDirectory();
                            if (Environment.MEDIA_MOUNTED.equals(state) && !textInputEditText.getText
                                    ().toString().isEmpty()) {
                                File writeEx_File = new File(directory, "ExternalFile.txt");
                                FileWriter fileWriter = new FileWriter(writeEx_File);
                                fileWriter.append(textInputEditText.getText().toString().trim());
                                fileWriter.flush();
                                fileWriter.close();

                                FileTask fileTask = new FileTask(writeEx_File, showData, R.id.button);
                                fileTask.execute(new File[]{writeEx_File});

                            } else {
                                Toast.makeText(this, "No External Storage Found or You have not " +
                                                "entered data in the file." ,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(this, "Exception: "+e.getMessage(),Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }
              break;

            case R.id.deletebutton:
                if(writeExternalPermission == PackageManager.PERMISSION_GRANTED){
                    state = Environment.getExternalStorageState();
                    try {
                        directory = Environment.getExternalStorageDirectory();
                        File delete_File = new File(directory, "ExternalFile.txt");
                        if(delete_File.exists()){
                            if (Environment.MEDIA_MOUNTED.equals(state)) {
                                delete_File = new File(directory, "ExternalFile.txt");

                                delete_File.delete();
                                showData.setText("");
                                Toast.makeText(this, "File Deleted!!",Toast
                                        .LENGTH_SHORT)
                                        .show();
                            } else {
                                Toast.makeText(this, "No External Storage Found",Toast
                                        .LENGTH_SHORT)
                                        .show();
                            }
                        }
                        else {
                            Toast.makeText(this, "No file found to Delete!!",Toast
                                    .LENGTH_SHORT)
                                    .show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(this, "Exception: "+e.getMessage(),Toast.LENGTH_SHORT)
                                .show();
                    }
                }
                else {
                    Toast.makeText(this, "No Permission Granted for External Storage",Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted to write External Storage",Toast
                            .LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "Permission Denied to write External Storage", Toast
                            .LENGTH_SHORT)
                            .show();
                }

//                if(grantResults[1] == PackageManager.PERMISSION_GRANTED){
//                    Toast.makeText(this, "Permission Granted to Read External Storage",Toast
//                            .LENGTH_LONG).show();
//                }
//                else {
//                    Toast.makeText(this, "Permission Denied to Read External Storage", Toast
//                            .LENGTH_SHORT)
//                            .show();
//                }
                return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
