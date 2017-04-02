package sjj.alog.simple;

import android.Manifest;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import sjj.alog.ALog;
import sjj.alog.Config;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Config config = new Config();
        config.enable = true;
        config.hold = true;
        Config.init(config);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> check = PermissionHelper.checkDenied(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (check.size() > 0) {
                requestPermissions(PermissionHelper.toArray(check), 1);
                ALog.e("aaa");
                ALog.e("aaa");
            } else {
                ALog.e("aaa");
                ALog.e("aaa");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                List<String> strings = PermissionHelper.grantFailed(permissions, grantResults);
                if (strings.size() > 0) {
                    Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT).show();
                } else {
                    ALog.e("aaa");
                    ALog.e("aaa");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
