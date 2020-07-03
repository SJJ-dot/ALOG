package sjj.alog.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

import sjj.alog.Config;
import sjj.alog.Log;
import sjj.alog.Logger;

import static sjj.alog.Config.ERROR;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        PermissionUtil.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionCallback() {
//            @Override
//            public void onGranted(Permission permissions) {
//                printLog();
////                privateConfig();
//            }
//
//            @Override
//            public void onDenied(Permission permissions) {
//
//            }
//        });

        printLog();
        Log.e("=================================");
        privateConfig();
        Log.e("=================================");
        Log.tag("hello").e("test tag ");
    }

    public void printLog() {

        Config config = Config.getDefaultConfig();
        config.printMethod = true;
        config.tag = "def global config";

        Log.i("log");
        Log.i("log", new Exception());
        Log.i(1, "log");
        Log.i(1, "log", new Exception());

        Log.d("log");
        Log.d("log", new Exception());
        Log.d(1, "log");
        Log.d(1, "log", new Exception());

        Log.w("log");
        Log.w("log", new Exception());
        Log.w(1, "log");
        Log.w(1, "log", new Exception());

        Log.e("log");
        Log.e("log", new Exception());
        Log.e(1, "log");
        Log.e(1, "log", new Exception());
    }

    public void privateConfig() {
        // 如果写入存储卡需要 WRITE_EXTERNAL_STORAGE
        Config config = new Config();
        //保存到文件 更多配置查看 Config 类
        config.writeToFile = true;
        config.writeToFileDir = new File(getExternalCacheDir(), "LogFile");
        config.writeToFileLev = ERROR;
        //只保存指定级别日志
        config.writeToFileMultiple = false;
        Logger logger = new Logger(config);
        logger.e("write file");
        //输出：2019-09-29 16:52:36.049 10325-10325/sjj.alog.simple E/Logger: privateConfig(MainActivity.java:48) write file
        logger.e("小于指定级别的日志不会写入文件");
        //输出：2019-09-29 16:52:36.049 10325-10325/sjj.alog.simple I/Logger: privateConfig(MainActivity.java:50) 小于指定级别的日志不会写入文件
    }

}
