package com.example.widgetapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final int DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE = 1222;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri uri = getIntent().getData();
        if(uri !=null){
            String path = uri.toString();
            Toast.makeText(MainActivity.this,"Path"+path,Toast.LENGTH_LONG).show();
        }
        Intent it = getIntent();

    }

    public void NextScreen(View view){
        Intent intent = new Intent(MainActivity.this,screen2.class);
        startActivity(intent);
    }



    public void createFloatingWidget(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE);
        } else

            startFloatingWidgetService();

    }


    private void startFloatingWidgetService() {
        startService(new Intent(MainActivity.this, FloatingWidgetService.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE) {

            if (resultCode == RESULT_OK)

                startFloatingWidgetService();
            else
                Toast.makeText(this,
                        getResources().getString(R.string.draw_other_app_permission_denied),
                        Toast.LENGTH_SHORT).show();

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
