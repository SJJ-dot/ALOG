package sjj.alog.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

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
        if (bufferedWriter == null)
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "utf-8"));
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
