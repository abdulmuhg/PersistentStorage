package id.co.persistentstorage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class ExternalActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String FILENAME = "default_file_name.txt";
    public static final int REQUEST_CODE_STORAGE = 100;
    public int selectEvent = 0;
    Button btnBuatFile, btnUbahFile, btnBacaFile, btnDeleteFile;
    TextView tvBaca;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external);

        Objects.requireNonNull(getSupportActionBar()).setTitle("External Storage Activity");

        btnBuatFile = findViewById(R.id.btn_buatFile_);
        btnUbahFile = findViewById(R.id.btn_ubahFile_);
        btnBacaFile = findViewById(R.id.btn_bacaFile_);
        btnDeleteFile = findViewById(R.id.btn_hapusFile_);
        tvBaca = findViewById(R.id.tv_hasilBaca_);

        btnBuatFile.setOnClickListener(this);
        btnUbahFile.setOnClickListener(this);
        btnBacaFile.setOnClickListener(this);
        btnDeleteFile.setOnClickListener(this);
    }

    private void buatFile(){
        String isiFile = "(EXTERNAL) file_text";
        String state = Environment.getExternalStorageState();

        if (!Environment.MEDIA_MOUNTED.equals(state)){
            return;
        }

        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
        FileOutputStream outputStream;

        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, true);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bacaFile(){
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, FILENAME);

        if (file.exists()){
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null){
                    text.append(line);
                    line = br.readLine();
                }
                br.close();

            } catch (FileNotFoundException e) {
                tvBaca.setText(R.string.file_not_found);
            } catch (IOException e) {
                e.printStackTrace();
            }
            tvBaca.setText(text.toString());
        }
    }

    private void hapusFile(){
        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
        if (file.exists()) {
            file.delete();
            Toast.makeText(this, "File dihapus", Toast.LENGTH_SHORT).show();
        }
    }

    private void ubahFile(){
        String newFile = "(EXTERNAL) new_file_has_been_update";
        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);

        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(file, false);
            outputStream.write(newFile.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        jalankanPerintah(view.getId());
    }

    public void jalankanPerintah(int id){
        switch (id){
            case R.id.btn_buatFile_:
                buatFile();
                break;
            case R.id.btn_bacaFile_:
                bacaFile();
                break;
            case R.id.btn_ubahFile_:
                ubahFile();
                break;
            case R.id.btn_hapusFile_:
                hapusFile();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                jalankanPerintah(selectEvent);
            }
        }
    }
}