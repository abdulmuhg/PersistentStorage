package id.co.persistentstorage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class InternalActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String FILENAME = "default_file_name.txt";
    Button btnBuatFile, btnUbahFile, btnBacaFile, btnDeleteFile;
    TextView tvBaca;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Internal Storage Activity");

        btnBuatFile = findViewById(R.id.btn_buatFile);
        btnUbahFile = findViewById(R.id.btn_ubahFile);
        btnBacaFile = findViewById(R.id.btn_bacaFile);
        btnDeleteFile = findViewById(R.id.btn_hapusFile);
        tvBaca = findViewById(R.id.tv_hasilBaca);

        btnBuatFile.setOnClickListener(this);
        btnUbahFile.setOnClickListener(this);
        btnBacaFile.setOnClickListener(this);
        btnDeleteFile.setOnClickListener(this);
    }

    private void buatFile(){
        String isiFile = "file_text";
        File file = new File(getFilesDir(), FILENAME);

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

    private void hapusFile(){
        File file = new File(getFilesDir(), FILENAME);
        if (file.exists()) {
            file.delete();
            Toast.makeText(this, "File dihapus", Toast.LENGTH_SHORT).show();
        }
    }

    private void bacaFile(){
        File file = new File(getFilesDir(), FILENAME);
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
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            tvBaca.setText(text.toString());
        } else {
            tvBaca.setText("file not found");
        }
    }

    private void ubahFile(){
        String newFile = "new_file_has_been_update";
        File file = new File(getFilesDir(), FILENAME);

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
            case R.id.btn_buatFile:
                buatFile();
                break;
            case R.id.btn_bacaFile:
                bacaFile();
                break;
            case R.id.btn_ubahFile:
                ubahFile();
                break;
            case R.id.btn_hapusFile:
                hapusFile();
                break;
        }
    }
}