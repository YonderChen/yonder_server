package com.yonder.game.quartz;

import java.text.ParseException;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.yonder.game.tools.DateTimeTools;

/** *//** 
 * @Title:Quartz管理类 
 *  
 * @Description: 
 *  
 * @Copyright:  
 * @version 1.00.000 
 * 
 */  
public class QuartzManager {
	
	private static final Logger logger = Logger.getLogger(QuartzManager.class);
	
	private static SchedulerFactory sf = new StdSchedulerFactory();  
	private static String JOB_GROUP_NAME = "group1";  
	private static String TRIGGER_GROUP_NAME = "trigger1";  

	private static final String RefreshGameProfileToDBTime = "0/5 * * ? * *";
	
   /** *//** 
    *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 
    * @param jobName 任务名 
    * @param job     任务 
    * @param time    时间设置，参考quartz说明文档 
    * @throws SchedulerException 
    * @throws ParseException 
    */  
   public static void addJob(String jobName,Job job,String time)   
                               throws SchedulerException, ParseException{  
       Scheduler sched = sf.getScheduler(); 
       JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, job.getClass());//任务名，任务组，任务执行类  
       //触发器  
       CronTrigger  trigger =   
            new CronTrigger(jobName, TRIGGER_GROUP_NAME);//触发器名,触发器组  
       trigger.setCronExpression(time);//触发器时间设定  
       trigger.setTimeZone(DateTimeTools.getTimeZone());
       sched.scheduleJob(jobDetail,trigger);  
       //启动  
       if(!sched.isShutdown())  
          sched.start();
   }  
   
   public static void shutdown() {
	   try {
			Scheduler sched = sf.getScheduler();
			sched.shutdown(true);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
   }
   
   /**
	 * 关于time的配置
	 * 	0 0 12 * * ?     每天12点触发
	   	0 15 10 ? * *     每天10点15分触发
	   	0 15 10 * * ?     每天10点15分触发
	   	0 15 10 * * ? *     每天10点15分触发
      	0 15 10 * * ? 2005     2005年每天10点15分触发
	   	0 * 14 * * ?     每天下午的 2点到2点59分每分触发
		0 0/5 14 * * ?     每天下午的 2点到2点59分(整点开始，每隔5分触发)
		0 0/5 14,18 * * ?     每天下午的 2点到2点59分(整点开始，每隔5分触发) 每天下午的 18点到18点59分(整点开始，每隔5分触发)
		0 0-5 14 * * ?     每天下午的 2点到2点05分每分触发
		0 10,44 14 ? 3 WED     3月分每周三下午的 2点10分和2点44分触发
		0 15 10 ? * MON-FRI     从周一到周五每天上午的10点15分触发
		0 15 10 15 * ?     每月15号上午10点15分触发
		0 15 10 L * ?     每月最后一天的10点15分触发
		0 15 10 ? * 6L     每月最后一周的星期五的10点15分触发
		0 15 10 ? * 6L 2002-2005     从2002年到2005年每月最后一周的星期五的10点15分触发
		0 15 10 ? * 6#3     每月的第三周的星期五开始触发
		0 0 12 1/5 * ?     每月的第一个中午开始每隔5天触发一次
	 */
   public static void start(){
		try {
			logger.info("将缓存玩家数据写入数据库时间：" + RefreshGameProfileToDBTime);
//			QuartzManager.addJob("refreshGameProfileToDBJob", new RefreshGameProfileToDBJob(), RefreshGameProfileToDBTime);
		} catch (Exception e) {
			logger.error("定时器加载异常！", e);
		}
   }
   
   	public static void modifyJobTimeByRefreshGameConfig() {
   		try {
			logger.info("刷新定部分定时器触发时间");
			logger.info("定时器触发时间刷新成功");
		} catch (Exception e) {
			logger.error("刷新定时器触发时间异常！", e);
		}
   	}
   
   /** *//** 
    *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 
    * @param jobName 任务名 
    * @param job     任务 
    * @param time    时间设置，参考quartz说明文档 
    * @param data    带入的数据
    * @throws SchedulerException 
    * @throws ParseException 
    */  
   public static void addJob(String jobName,Job job,String time, JobDataMap data)   
                               throws SchedulerException, ParseException{  
       Scheduler sched = sf.getScheduler();  
       JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, job.getClass());//任务名，任务组，任务执行类  
       jobDetail.setJobDataMap(data);
       //触发器  
       CronTrigger  trigger =   
            new CronTrigger(jobName, TRIGGER_GROUP_NAME);//触发器名,触发器组  
       trigger.setCronExpression(time);//触发器时间设定  
       trigger.setTimeZone(DateTimeTools.getTimeZone());
       sched.scheduleJob(jobDetail,trigger);  
       //启动  
       if(!sched.isShutdown())  
          sched.start();  
   }  
     
   /** *//** 
    * 添加一个定时任务 
    * @param jobName 任务名 
    * @param jobGroupName 任务组名 
    * @param triggerName  触发器名 
    * @param triggerGroupName 触发器组名 
    * @param job     任务 
    * @param time    时间设置，参考quartz说明文档 
    * @throws SchedulerException 
    * @throws ParseException 
    */  
   public static void addJob(String jobName,String jobGroupName,  
                             String triggerName,String triggerGroupName,  
                             Job job,String time)   
                               throws SchedulerException, ParseException{  
       Scheduler sched = sf.getScheduler();  
       JobDetail jobDetail = new JobDetail(jobName, jobGroupName, job.getClass());//任务名，任务组，任务执行类  
       //触发器  
       CronTrigger  trigger =   
            new CronTrigger(triggerName, triggerGroupName);//触发器名,触发器组  
       trigger.setCronExpression(time);//触发器时间设定  
       trigger.setTimeZone(DateTimeTools.getTimeZone());
       sched.scheduleJob(jobDetail,trigger);  
       if(!sched.isShutdown())  
          sched.start();  
   }  
     
   /** *//** 
    * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名) 
    * @param jobName 
    * @param time 
    * @throws SchedulerException 
    * @throws ParseException 
    */  
   public static void modifyJobTime(String jobName,String time)   
                                  throws SchedulerException, ParseException{  
       Scheduler sched = sf.getScheduler();  
       Trigger trigger =  sched.getTrigger(jobName,TRIGGER_GROUP_NAME);  
       if(trigger != null){  
           CronTrigger  ct = (CronTrigger)trigger;  
           ct.setCronExpression(time);  
           sched.resumeTrigger(jobName,TRIGGER_GROUP_NAME);  
       }  
   }  
     
   /** *//** 
    * 修改一个任务的触发时间 
    * @param triggerName 
    * @param triggerGroupName 
    * @param time 
    * @throws SchedulerException 
    * @throws ParseException 
    */  
   public static void modifyJobTime(String triggerName,String triggerGroupName,  
                                    String time)   
                                  throws SchedulerException, ParseException{  
       Scheduler sched = sf.getScheduler();  
       Trigger trigger =  sched.getTrigger(triggerName,triggerGroupName);  
       if(trigger != null){  
           CronTrigger  ct = (CronTrigger)trigger;  
           //修改时间  
           ct.setCronExpression(time);  
           //重启触发器  
           sched.resumeTrigger(triggerName,triggerGroupName);  
       }  
   }  
     
   /** *//** 
    * 移除一个任务(使用默认的任务组名，触发器名，触发器组名) 
    * @param jobName 
    * @throws SchedulerException 
    */  
   public static void removeJob(String jobName)   
                               throws SchedulerException{  
       Scheduler sched = sf.getScheduler();  
       sched.pauseTrigger(jobName,TRIGGER_GROUP_NAME);//停止触发器  
       sched.unscheduleJob(jobName,TRIGGER_GROUP_NAME);//移除触发器  
       sched.deleteJob(jobName,JOB_GROUP_NAME);//删除任务  
   }  
     
   /** *//** 
    * 移除一个任务 
    * @param jobName 
    * @param jobGroupName 
    * @param triggerName 
    * @param triggerGroupName 
    * @throws SchedulerException 
    */  
   public static void removeJob(String jobName,String jobGroupName,  
                                String triggerName,String triggerGroupName)   
                               throws SchedulerException{  
       Scheduler sched = sf.getScheduler();  
       sched.pauseTrigger(triggerName,triggerGroupName);//停止触发器  
       sched.unscheduleJob(triggerName,triggerGroupName);//移除触发器  
       sched.deleteJob(jobName,jobGroupName);//删除任务  
   }  
}  
