package sjj.alog.file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private Writer writer;
    private File dir;

    public LogFile(File dir) {
        this.dir = dir;
        if (dir != null) {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat hm = new SimpleDateFormat("HH：mm", Locale.getDefault());
            writer = new Writer(new File(getLogDir(), ymd.format(new Date()) + "/" + hm.format(new Date())+".txt"));
        }
    }

    public void deleteOldLogFile() {
        File dir = getLogDir();
        Calendar instance = Calendar.getInstance();
        Set<String> strings = new HashSet<>();
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        for (int i = 0; i < 7; i++) {
            instance.add(Calendar.DAY_OF_MONTH, -i);
            strings.add(ymd.format(instance.getTime()));
        }
        File[] oldLogs = dir.listFiles();
        if (oldLogs != null)
            for (File file : oldLogs) {
                if (!strings.contains(file.getName())) {
                    boolean delete = file.delete();
                }
            }
    }

    private File getLogDir() {
        File logFileRoot =dir;
        if (logFileRoot.exists() && logFileRoot.isFile()) logFileRoot.delete();
        if (!logFileRoot.exists()) {
            boolean mkdirs = logFileRoot.mkdirs();
        }
        return logFileRoot;
    }

    public synchronized void push(final String msg) {
        ScheduledFuture<?> schedule = LogFile.this.schedule;
        if (schedule != null&&!schedule.isDone()) {
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
