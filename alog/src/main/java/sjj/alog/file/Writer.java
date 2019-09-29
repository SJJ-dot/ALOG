package sjj.alog.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by SJJ on 2017/4/3.
 * 写文件
 */

class Writer {
    private BufferedWriter bufferedWriter;
    private File file;


    Writer(File file) {
        initFile(file);
        this.file = file;
    }
    private void initFile(File file) {
        if (!file.isFile()) {
            file.delete();
        }
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized void write(String string) throws IOException {
        initFile(file);
        if (bufferedWriter == null) {
            Charset charset;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                charset = StandardCharsets.UTF_8;
            } else {
                charset = Charset.forName("UTF-8");
            }
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true),charset));
        }
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
