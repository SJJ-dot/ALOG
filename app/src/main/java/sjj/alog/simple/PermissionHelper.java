package sjj.alog.simple;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SJJ on 2017/3/8.
 */

public class PermissionHelper {
    public static List<String> checkDenied(Context context, String... permissions) {
        List<String> deniedPermissions = new ArrayList<>(permissions.length);
        for (String s : permissions) {
            if (ActivityCompat.checkSelfPermission(context, s) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(s);
            }
        }
        return deniedPermissions;
    }
    public static List<String> grantFailed(String[] permission, int[] grantResults){
        List<String> failed = new ArrayList<>(grantResults.length);
        for (int i = 0; i < grantResults.length; i++) {
            if (!grant(grantResults[i])){
                failed.add(permission[i]);
            }
        }
        return failed;
    }
    public static boolean grant(int state){
        return state == PackageManager.PERMISSION_GRANTED;
    }
    public static String[] toArray(List<String> strings){
        return strings.toArray(new String[strings.size()]);

    }
}
