package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.FileReader;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) {
        Properties cfg = new Properties();
        try (FileReader in = new FileReader("./src/main/resources/rabbit.properties")) {
            cfg.load(in);
            Class.forName(cfg.getProperty("driver-class-name"));
            try (Connection cn = DriverManager.getConnection(
                        cfg.getProperty("url"),
                        cfg.getProperty("username"),
                        cfg.getProperty("password")
                )) {
                Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                scheduler.start();
                JobDataMap data = new JobDataMap();
                data.put("store", cn);
                JobDetail job = newJob(Rabbit.class)
                        .usingJobData(data)
                        .build();
                SimpleScheduleBuilder times = simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever();
                Trigger trigger = newTrigger()
                        .startNow()
                        .withSchedule(times)
                        .build();
                scheduler.scheduleJob(job, trigger);
                Thread.sleep(10000);
                scheduler.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Rabbit implements Job {

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            Connection cn = (Connection) context.getJobDetail().getJobDataMap().get("store");
            try (PreparedStatement st = cn.prepareStatement("insert into rabbit(created_date) values (?)")) {
                st.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                st.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
