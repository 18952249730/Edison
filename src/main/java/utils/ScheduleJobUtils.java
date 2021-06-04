package utils;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bean.ScheduleJob;
 
 
/**
 * Job执行工具类，利用反射执行相应的类的对应方法
 */
public class ScheduleJobUtils {
	
	public final static Logger log = LoggerFactory.getLogger(ScheduleJobUtils.class);
 
	/**
	 * 通过反射调用scheduleJob中定义的方法
	 */
	public static void invokMethod(ScheduleJob scheduleJob) {
		Object object = null;
		Class<?> clazz = null;
		if (scheduleJob.getSpringId() != null && !"".trim().equals(scheduleJob.getSpringId())) {
//			object = SpringUtils.getBean(scheduleJob.getSpringId());
			clazz = object.getClass();
		} else if (scheduleJob.getBeanClass() != null && !"".trim().equals(scheduleJob.getSpringId())) {
			try {
				clazz = Class.forName(scheduleJob.getBeanClass());
				object = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (object == null) {
			log.error(getNow() + " >任务名称 = [" + scheduleJob.getJobName() + "]--------未启动成功，请检查是否配置正确！！！");
			return;
		}
		
		try {
			// 如果是自定义任务
			if ("y".equals(scheduleJob.getIsZdy())) {
				Method method = clazz.getDeclaredMethod(scheduleJob.getMethodName(), new Class[] { ScheduleJob.class });
				method.invoke(object, scheduleJob);
			} else {
				Method method = clazz.getDeclaredMethod(scheduleJob.getMethodName(), new Class[] {});
				method.invoke(object, new Object[] {});
			}
		} catch (Exception e) {
			log.error(getNow() + " >任务名称 = [" + scheduleJob.getJobName() + "]--------未启动成功，方法名设置错误！！！");
		} 
		
		log.info(getNow() + " >任务名称 = [" + scheduleJob.getJobName() + "]--------启动成功");
	}
 
	/**
	 * 获取当前的时间字符串 ，格式为yyyy-MM-dd HH:mm:ss.SSS
	 */
	private static String getNow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return sdf.format(new Date());
	}
}
