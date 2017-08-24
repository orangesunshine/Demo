package com.orangesunshine.ndk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class LameActivity extends Activity {
    private EditText etWav, etMp3;
    private Button btnConvert,btnVersion;
    private ProgressDialog pb;
    static {
        System.loadLibrary("hello");
    }

    public native void convertMp3(String wav, String mp3);
    public native String getLameVersion();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lame);
        etMp3 = (EditText) findViewById(R.id.et_mp3);
        etWav = (EditText) findViewById(R.id.et_wav);
        btnConvert = (Button) findViewById(R.id.btn_convert);
        btnVersion = (Button) findViewById(R.id.btn_version);
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convert();
            }
        });
        btnVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LameActivity.this, getLameVersion(), Toast.LENGTH_LONG).show();
            }
        });
        pb = new ProgressDialog(LameActivity.this);
        pb.setMessage("Convertting");
        etWav.setText(Environment.getExternalStorageDirectory() + File.separator + "voice.wav");
        etMp3.setText(Environment.getExternalStorageDirectory() + File.separator + "voice.mp3");
    }

    private void convert() {
        final String mp3 = etMp3.getText().toString().trim();
        final String wav = etWav.getText().toString().trim();
        if (TextUtils.isEmpty(wav) || TextUtils.isEmpty(mp3)) {
            Toast.makeText(LameActivity.this, "路径不能为空！", Toast.LENGTH_LONG).show();
            return;
        }
        pb.show();
        new Thread(){
            @Override
            public void run() {
                convertMp3(wav, mp3);
                pb.dismiss();
            }
        }.start();
    }
}
