package ru.avk.base.job.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avk.base.job.domain.TaskDetails;
import ru.avk.base.job.service.ScheduledTask;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ipopkov on 03/04/16.
 */
@Service
public class TaskRunnerService implements Runnable {

    private String status = "Hasn't run";
    private Date lastRun;

    private Logger LOG = LoggerFactory.getLogger(CronTaskExecutorService.class);

    @Autowired
    private ScheduledTask scheduledTask ;

    @Override
    public void run() {
        try {
            lastRun = Calendar.getInstance().getTime();
            status = "executing";
            scheduledTask.run();
            status = "executed";
            //FIXME: вынести в константы - в enum
        } catch (Exception e){
            status = "failed";
            LOG.error(e.getMessage(), e);
        }
    }

    public TaskDetails getJobExecutionDetails (){
        TaskDetails taskDetails = new TaskDetails();
        taskDetails.setLastRun(lastRun);
        taskDetails.setStatus(status);
        return taskDetails;
    }

}
