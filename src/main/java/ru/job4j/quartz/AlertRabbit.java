package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlertRabbit {
    private static final Logger LOG = LoggerFactory.getLogger(AlertRabbit.class.getName());

    public static void init() {
        try (InputStream inputStream = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            Properties settings = new Properties();
            settings.load(inputStream);
            Class.forName(settings.getProperty("driver-class-name"));
            try (Connection connection = DriverManager.getConnection(
                    settings.getProperty("url"),
                    settings.getProperty("username"),
                    settings.getProperty("password")
            )) {
                try {
                    Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                    scheduler.start();
                    JobDataMap data = new JobDataMap();
                    data.put("store", connection);
                    JobDetail job = newJob(Rabbit.class)
                            .usingJobData(data)
                            .build();
                    SimpleScheduleBuilder times = simpleSchedule()
                            .withIntervalInSeconds(Integer.parseInt(settings.getProperty("rabbit.interval")))
                            .repeatForever();
                    Trigger trigger = newTrigger()
                            .startNow()
                            .withSchedule(times)
                            .build();
                    scheduler.scheduleJob(job, trigger);
                    Thread.sleep(10000);
                    scheduler.shutdown();
                } catch (Exception se) {
                    LOG.error("Error run Scheduler", se);
                }
            } catch (Exception ec) {
                LOG.error("DriverManager.getConnection", ec);
            }
        } catch (Exception e) {
            LOG.error("Error opening file rabbit.properties", e);
        }
    }

    public static void add(Connection cn) {
        try (PreparedStatement statement = cn.prepareStatement("insert into rabbit(create_date) values(?)")) {
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement.execute();
        } catch (Exception e) {
            LOG.error("Error add date for tables SQL", e);
        }
    }

    public static void main(String[] args) {
        init();
    }

    public static class Rabbit implements Job {

        public Rabbit() {
            LOG.info("Print now hashCode: {}", hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) {
            Connection cn = (Connection) context.getJobDetail().getJobDataMap().get("store");
            try {
                LOG.info("connection.getMetaData().getMaxTablesInSelect() {}", cn.getMetaData().getMaxTablesInSelect());
            } catch (SQLException e) {
                LOG.error("Error getMetaData()", e);
            }
            add(cn);
        }
    }
}