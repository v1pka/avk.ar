package ru.avk.base.job.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import ru.avk.base.job.domain.TaskDetails;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by ipopkov on 03/04/16.
 */
@Service
public class CronTaskExecutorService {


    private TaskScheduler scheduler;
    private ScheduledExecutorService scheduledExecutor;
    private String currentCron;


    @Autowired
    private TaskRunnerService schedule;

    public TaskDetails getJobExecutionDetails (){
        TaskDetails taskDetails =  schedule.getJobExecutionDetails();
        taskDetails.setCurrentCron(currentCron);
        return taskDetails;
    }

    public void scheduleJob(String cronExpression){
        if(scheduler == null){
            initTaskScheduler();
        } else {
            scheduledExecutor.shutdown();
            initTaskScheduler();
        }
        currentCron = cronExpression;
        scheduler.schedule(schedule, new CronTrigger(cronExpression));
    }

    private void initTaskScheduler() {
        scheduledExecutor = Executors.newScheduledThreadPool(1);
        scheduler = new ConcurrentTaskScheduler();
    }


}
