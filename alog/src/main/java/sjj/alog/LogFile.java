package sjj.alog;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by SJJ on 2017/3/5.
 */

class LogFile {
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> schedule;
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private Writer writer;
    private File dir;

    public LogFile(File dir) {
        this.dir = dir;
        deleteOldLogFile();
        if (dir != null) {
            try {
                writer = new Writer(getLogFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteOldLogFile() {
        File dir = getLogDir();
        Calendar instance = Calendar.getInstance();
        Set<String> strings = new HashSet<>();
        for (int i = 0; i < 7; i++) {
            instance.add(Calendar.DAY_OF_MONTH, -i);
            strings.add(getLogFileName(instance.getTime()));
        }
        File[] oldLogs = dir.listFiles();
        if (oldLogs != null)
            for (File file : oldLogs) {
                if (!strings.contains(file.getName())) {
                    boolean delete = file.delete();
                }
            }
    }

    /**
     * 根据日期生成log 文件名
     *
     * @param date
     * @return
     */
    private String getLogFileName(Date date) {
        return ymd.format(date) + ".log";
    }

    private File getLogDir() {
        File dirRoot = dir;
        File logFileRoot = new File(dirRoot, "log");
        if (logFileRoot.exists() && logFileRoot.isFile()) logFileRoot.delete();
        if (!logFileRoot.exists()) {
            boolean mkdirs = logFileRoot.mkdirs();
        }
        return logFileRoot;
    }

    private File getLogFile() {
        File file = new File(getLogDir(), getLogFileName(new Date()));
        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("tag", file.toString());
            }
        }
        return file;
    }

    public void push(final String msg) {
        if (writer == null) return;
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                if (schedule != null) {
                    schedule.cancel(true);
                }
                try {
                    writer.prepare();
                    writer.write(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    schedule = executorService.schedule(new Runnable() {
                        @Override
                        public void run() {
                            writer.close();
                            schedule = null;
                        }
                    }, 10, TimeUnit.SECONDS);
                }
            }
        });
    }

    private static class Writer {
        private BufferedWriter bufferedWriter;
        private File file;
        private SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss -- ");

        private Writer(File file) {
            if (!file.isFile()) throw new IllegalArgumentException("file 必须是文件类型");
            this.file = file;
        }

        void write(String string) throws IOException {
            bufferedWriter.write(hms.format(new Date(System.currentTimeMillis())));
            bufferedWriter.write(string);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        }

        synchronized void prepare() throws IOException {
            if (bufferedWriter == null)
                bufferedWriter = new BufferedWriter(new FileWriter(file, true));
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
}
