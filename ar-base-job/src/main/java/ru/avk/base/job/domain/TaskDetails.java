package ru.avk.base.job.domain;

import java.util.Date;

/**
 * Created by ipopkov on 02/04/16.
 */
public class TaskDetails {

	private String currentCron;
	private String status;
	private Date lastRun;

	public String getCurrentCron() {
		return currentCron;
	}

	public void setCurrentCron(String currentCron) {
		this.currentCron = currentCron;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastRun() {
		return lastRun;
	}

	public void setLastRun(Date lastRun) {
		this.lastRun = lastRun;
	}

}
