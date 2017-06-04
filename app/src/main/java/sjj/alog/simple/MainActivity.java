package sjj.alog.simple;

import android.Manifest;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import sjj.alog.Log;
import sjj.alog.Config;
import sjj.alog.file.LogFile;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Config config = new Config();
        config.enable = true;
        config.hold = true;
        config.holdLev = Config.DEBUG;
        config.holdMultiple = true;

//      config.dir = getCacheDir();
//        config.dirName = "myLog";
        LogFile logFile = new LogFile(new File(Environment.getExternalStorageDirectory(),"logFile"));
        logFile.push("logfilelogfilelogfilelogfilelogfilelogfilelogfilelogfilelogfilelogfilelogfilelogfilelogfil" +
                "elogfilelogfilelogfilelogfilelogfilelogfilelogfilelogfilelogf" +
                "ilelogfilelogfilelogfilelogfilelogfilelogfilelogfilelogfile");

        Config.init(config);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> check = PermissionHelper.checkDenied(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (check.size() > 0) {
                requestPermissions(PermissionHelper.toArray(check), 1);
                Log.e("aaa");
                Log.i("aaa");
            } else {
                Log.e("aaa");
                Log.i(1,"aaa");
                Log.d("aaa");
                Log.w("aaa");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                List<String> strings = PermissionHelper.grantFailed(permissions, grantResults);
                if (strings.size() > 0) {
                    Toast.makeText(this, "Failed to get permission", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("aaa");
                    Log.e("aaa");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void onClick(View view) {
        Log.e("日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志a" +
                "aa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志a" +
                "aa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日" +
                "志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa日志aaa");
        Log.e(Environment.getDataDirectory());
        Log.e(Environment.getRootDirectory());
        Log.e(Environment.getExternalStorageState());
        Log.e(Environment.getExternalStorageDirectory());
        Log.e(Environment.getDownloadCacheDirectory());

    }
}
