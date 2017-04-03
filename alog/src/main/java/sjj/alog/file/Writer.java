package sjj.alog.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by SJJ on 2017/4/3.
 * 写文件
 */

class Writer {
    private BufferedWriter bufferedWriter;
    private File file;
    private SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss -- ", Locale.CHINA);

    Writer(File file) {
        if (!file.isFile()) throw new IllegalArgumentException("file 必须是文件类型");
        this.file = file;
    }

    synchronized void write(String string) throws IOException {
        if (bufferedWriter == null)
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        bufferedWriter.write(hms.format(new Date(System.currentTimeMillis())));
        bufferedWriter.write(string);
        bufferedWriter.newLine();
        bufferedWriter.flush();

    }

    synchronized void close() {
        if (bufferedWriter == null) return;
        try {
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bufferedWriter = null;
    }
}
