package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bean.ScheduleJob;
import utils.ScheduleJobUtils;
 
 
/**
 * Job实现类：
 * 		计划任务执行处（反射执行）
 */
public class QuartzJobFactory implements Job {
	public final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		// 获取创建Job时传递的数据
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		// 利用反射去执行
		ScheduleJobUtils.invokMethod(scheduleJob);
	}

}