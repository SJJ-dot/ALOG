package sjj.alog.file;

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

public class LogFile {
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> schedule;
    private SimpleDateFormat ymd = new SimpleDateFormat("MM-dd-HH-mm", Locale.US);
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
            }
        }
        return file;
    }

    public synchronized void push(final String msg) {
        if (writer == null) {
            try {
                writer = new Writer(getLogFile());
            } catch (Exception e) {
                return;
            }
        }

        ScheduledFuture<?> schedule = LogFile.this.schedule;
        if (schedule != null) {
            schedule.cancel(true);
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    writer.write(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    LogFile.this.schedule = executorService.schedule(new Runnable() {
                        @Override
                        public void run() {
                            writer.close();
                            LogFile.this.schedule = null;
                        }
                    }, 10, TimeUnit.SECONDS);
                }
            }
        });
    }


}
