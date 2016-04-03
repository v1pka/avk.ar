package ru.avk.base.job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.avk.base.job.domain.TaskDetails;
import ru.avk.base.job.service.impl.CronTaskExecutorService;

/**
 * Created by ipopkov on 02/04/16.
 */
@RestController
public class TaskController {

    @Autowired
    private CronTaskExecutorService cronTaskExecutorService;

    @RequestMapping(value = "/task/details", method = RequestMethod.GET)
    public TaskDetails getTaskDetails() {
        return cronTaskExecutorService.getJobExecutionDetails();
    }

    @RequestMapping(value = "/task/newCron", method = RequestMethod.POST)
    public void setNewExecutionTime (@RequestBody String cron) {
      cronTaskExecutorService.scheduleJob(cron);
    }
}
