package com.yonder.game.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.yonder.game.redis.RedisCache;

public abstract class AbstractJob implements Job {

	public abstract void doJob(JobExecutionContext context);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			doJob(context);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RedisCache.closeCacheConnection();
		}
	}

}
