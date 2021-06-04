package sh.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.print.attribute.HashAttributeSet;
import javax.xml.transform.Source;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import bean.ScheduleJob;
import utils.QuartzManager;

public class MyTest {
	/**
	 * test1和test2一起使用,比对中英文配置文件。
	 * **/
	@Test
	public void test1() throws IOException {
		//新建对象
		FileInputStream fileInputStream1 = new FileInputStream("wms_web_en_US.properties");
		FileInputStream fileInputStream2 = new FileInputStream("wms_web_zh_CN.properties");
		FileOutputStream fileOutputStream = new FileOutputStream("wms_web_zh_CN.properties",true);
		Properties properties1 = new Properties();
		Properties properties2 = new Properties();
		//加载文件中数据
		properties1.load(fileInputStream1);
		Set<Object> keySet1 = properties1.keySet();
		properties2.load(fileInputStream2);
		Set<Object> keySet2 = properties2.keySet();
		//对比校验
		Set<Object> collect = keySet1.stream().filter(f -> !keySet2.contains(f)).collect(Collectors.toSet());
		System.out.println(collect.toString());
		//向properties文件写数据
		for(Object object : collect) {
			Properties properties = new Properties();
			properties.setProperty((String)object,(String)properties1.get(object));
			properties.store(fileOutputStream, "");
		}
		//关闭流
		fileInputStream1.close();
		fileInputStream2.close();
		fileOutputStream.close();
	}
	@Test
	public void test2() throws IOException {
		FileInputStream fileInputStream1 = new FileInputStream("wms_web_zh_CN.properties");
		FileInputStream fileInputStream2 = new FileInputStream("wms_web_en_US.properties");
		FileOutputStream fileOutputStream = new FileOutputStream("wms_web_en_US.properties",true);
		Properties properties1 = new Properties();
		Properties properties2 = new Properties();
		properties1.load(fileInputStream1);
		Set<Object> keySet1 = properties1.keySet();
		properties2.load(fileInputStream2);
		Set<Object> keySet2 = properties2.keySet();
		Set<Object> collect = keySet1.stream().filter(f -> !keySet2.contains(f)).collect(Collectors.toSet());
		System.out.println(collect.toString());
		//向properties文件写数据
		for(Object object : collect) {
			Properties properties = new Properties();
			properties.setProperty((String)object,(String)properties1.get(object));
			properties.store(fileOutputStream, "");
		}
		fileInputStream1.close();
		fileInputStream2.close();
		fileOutputStream.close();
	}
	/****
	 * 测试stringBuffer类中的部分功能
	 */
	@Test
	public void test3() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("abcd");
//		stringBuffer.insert(1, "ef");
		stringBuffer.delete(1, 2);
//		stringBuffer.replace(1, 3, "efghsss");
		System.out.println(stringBuffer.toString());
	}
	/**
	 * 测试JSON
	 */
	@Test
	public void test4() {
		String jsonContent = "[{\"BUKRS\":1010,\"EKGRP\":\"G11\",\"EBELN\":4000012118,\"EKORG\":1010,\"ITEMDATA\":[{\"NTGEW\":\"25.500\",\"VBELN\":\"B006000532\",\"UNTTO\":\"0\",\"PSTYP\":0,\"WERKS\":1010,\"ZFHNO\":\"90.KUKA.119000192\",\"MENGE\":\"10.000\",\"ZXH\":\"T2O7054\",\"WGBEZ\":\"产成品-沙发\",\"EINDT\":\"2020-08-27\",\"GEWEI\":\"KG\",\"KWMENG\":\"10.000\",\"T_MNG\":\"10.000\",\"T_MAT\":\"90.KUKA.119.C000191\",\"TXZ01\":\"90.KUKA.119.9007.C000007\",\"MATNR\":\"90.KUKA.119.9007.C000007\",\"MAKTX\":\"抱枕\",\"T_SNR\":\"01\",\"KUNNR\":\"0000001090\",\"BRGEW\":\"30.600\",\"UEBTO\":\"0\",\"EBELP\":\"00010\",\"UEPOS\":\"000001\",\"MEINS\":\"PC\",\"VOLUM\":\"0.005\",\"POSNR\":\"000002\",\"AUART\":\"WOR1\",\"VOLEH\":\"M3\",\"T_NUM\":\"T202006030000029534\",\"MATKL\":\"03-01-0\",\"ZGG\":\"抱枕\",\"T_SLT\":\"01\",“RETPO”:’X’}],\"BEDAT\":\"2020-06-04\",\"AEDAT\":\"2020-06-04\",\"PROCSTAT\":\"05\",\"BSART\":\"ZCP\",\"LIFNR\":\"OA00000001\",\"NAME1\":\"测试异常供应商\",\"ERNAM\":9043977}]";
    	JSONObject parseObject = JSONObject.parseObject(jsonContent);
    	System.out.println("parseObject:" + parseObject);
    	JSONArray jsonArray = JSONArray.parseArray(parseObject.toJSONString());
    	System.out.println("jsonArray:" + jsonArray);
	}
	/**
	 * 测试for循环遍历一个空集合会不会报错。结论：会报空指针异常，原因：底层用的迭代器：集合.iterator所以报错
	 */
	@SuppressWarnings("null")
	@Test
	public void test5() {
		List<String> list = null;
		for(@SuppressWarnings("unused") String s : list) {
			System.out.println("qq");
		}
		System.out.println("结束");
	}
	/**
	 * 数组的合并
	 */
	@Test
	public void test6() {
		List<String> list1 = new ArrayList<>();
		list1.add("aa");
		list1.add("bb");
		list1.add("cc");
		List<String> list2 = new ArrayList<>();
		list2.add("dd");
		list2.add("ee");
		list2.add("ff");
//		String[] s1 = {"aa","bb","cc"};
//		String[] s2 = {"dd","ee","ff"};
		String[] array1 = list1.toArray(new String[list1.size()]);
		String[] array2 = list2.toArray(new String[list1.size() + list2.size()]);
		System.out.println(Arrays.toString(array1));
		System.out.println(Arrays.toString(array2));
		System.arraycopy(array1, 0, array2,list2.size(),list1.size());
		System.out.println(Arrays.toString(array1));
		System.out.println(Arrays.toString(array2));
	}
	/**
	 * map
	 */
	@Test
	public void test7() {
		System.out.println("aaa");
		Map map = new HashMap<>();
		map.put(1, "1");
		Long long1 = 1L;
		System.out.println(map.get(long1));	
	}
	/**
	 * Integer.parseInt
	 */
	@Test
	public void test8() {
		String valueOf = String.valueOf(null);
		int parseInt = Integer.parseInt(valueOf);
		System.out.println(parseInt);
	}
	/**
	 * Long.toString()参数为空的情况 结论：会报空指针异常
	 */
	@Test
	public void test9() {
		String long1 = Long.toString(100L);
		Long long2 = null;
		Long.toString(long2);
		System.out.println(long1);
		System.out.println(long2);
	}
	/**
	 * 异常的堆栈信息
	 */
	@Test
	public void test10() {
		try {
			Integer result = 1/0;
		} catch (Exception e) {
	        StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw, true);
			e.printStackTrace(pw);
			System.out.println(sw.getBuffer().toString());
			System.out.println("getMessage:" + e.getMessage());
			System.out.println("getLocalizedMessage:" + e.getLocalizedMessage());
			System.err.println("getCause:" + e.getCause());
		}
	}
	/**
	 * 获取异常信息的cause by信息
	 */
	@Test
	public void test11() {
//			Integer result = 1/0;
			String string = "{6881=org.springframework.dao.InvalidDataAccessResourceUsageException: could not execute query; SQL [select T__.*,  rownum  as ROWNUM__ from (SELECT WMSD.SO_NO,\r\n" + 
					"       '' SO_ALLOC_ID,\r\n" + 
					"       WMSD.LINE_NO SO_LINE_NO,\r\n" + 
					"       WMSD.LOGISTIC_NO,\r\n" + 
					"       WMSD.LOGISTIC_LINE_NO,\r\n" + 
					"       WMSD.SALE_NO SALE_NO,\r\n" + 
					"       WMSD.DEF1 SALE_LINE_NO,\r\n" + 
					"       '' OOD_NO,\r\n" + 
					"       '' OOD_LINE_NO,\r\n" + 
					"       EBCU.EBCU_SUBSTR2 OWNER_CODE,\r\n" + 
					"       WMSD.SKU_CODE,\r\n" + 
					"       CWS.SKU_NAME,\r\n" + 
					"       decode(WMSD.OMS_PARENT_SKU_CODE,null,WMSD.PARENT_SKU_CODE,WMSD.OMS_PARENT_SKU_CODE) as PARENT_SKU_CODE,\r\n" + 
					"       CWSP.SKU_NAME PARENT_SKU_NAME,\r\n" + 
					"       WMSD.STATUS SP_STATUS,\r\n" + 
					"       WMSD.QTY_SO_EA QTY_PLAN_EA,\r\n" + 
					"       WMSD.QTY_SHIP_EA QTY_SP_EA,\r\n" + 
					"       TO_CHAR(WMSD.LOT_ATT01,'yyyy-mm-dd hh24:mi:ss') LOT_ATT01,\r\n" + 
					"       TO_CHAR(WMSD.LOT_ATT02,'yyyy-mm-dd hh24:mi:ss') LOT_ATT02,\r\n" + 
					"       TO_CHAR(WMSD.LOT_ATT03,'yyyy-mm-dd hh24:mi:ss') LOT_ATT03,\r\n" + 
					"       WMSD.LOT_ATT04,\r\n" + 
					"       WMSD.LOT_ATT05,\r\n" + 
					"       WMSD.LOT_ATT06,\r\n" + 
					"       WMSD.LOT_ATT07,\r\n" + 
					"       WMSD.LOT_ATT08,\r\n" + 
					"       --decode(WMSD.LOT_ATT08,null,null,WMSD.DEF1) LOT_ATT09,\r\n" + 
					"       CASE WMSD.OMS_TYPE_CODE WHEN 'WMTXDD' THEN WMSD.ZTXYTHH ELSE CASE WHEN WMSD.LOT_ATT08 is null THEN null ELSE WMSD.DEF1 END END LOT_ATT09,\r\n" + 
					"       WMSD.LOT_ATT10,\r\n" + 
					"       WMSD.LOT_ATT11,\r\n" + 
					"       WMSD.LOT_ATT12,\r\n" + 
					"       ESUS.ESUS_USER_NAME_CN SP_OP,\r\n" + 
					"       TO_CHAR(WMSD.MODIFY_TIME,'yyyy-mm-dd hh24:mi:ss') SP_TIME,\r\n" + 
					"       WMSD.DEF2 LOAD_NO,\r\n" + 
					"       WMSD.DEF1 LOAD_LINE_NO,\r\n" + 
					"       WMSD.PROJECT_ID,\r\n" + 
					"       WMSD.ORG_ID,\r\n" + 
					"       '' as part,\r\n" + 
					"       WMSD.ZTXYTHH\r\n" + 
					"  FROM WM_SO_DETAIL WMSD\r\n" + 
					"  LEFT JOIN CD_WH_SKU CWS ON CWS.SKU_CODE = WMSD.SKU_CODE AND CWS.OWNER_CODE = WMSD.OWNER_CODE AND CWS.PROJECT_ID = WMSD.PROJECT_ID\r\n" + 
					"  LEFT JOIN CD_WH_SKU CWSP ON CWSP.SKU_CODE = WMSD.PARENT_SKU_CODE AND CWSP.OWNER_CODE = WMSD.OWNER_CODE AND CWSP.PROJECT_ID = WMSD.PROJECT_ID\r\n" + 
					"  LEFT JOIN EB_CUSTOMER EBCU ON EBCU.PM_CODE = WMSD.OWNER_CODE AND EBCU.ORG_ID = WMSD.ORG_ID\r\n" + 
					"  LEFT JOIN ES_USER ESUS ON ESUS.ESUS_ID = WMSD.MODIFIER\r\n" + 
					"  LEFT JOIN WM_SO_HEADER WMSH ON WMSH.SO_NO = WMSD.SO_NO AND WMSH.PROJECT_ID = WMSD.PROJECT_ID\r\n" + 
					"WHERE WMSD.SO_NO = ?\r\n" + 
					"  AND WMSD.PROJECT_ID = ?\r\n" + 
					"union all\r\n" + 
					"SELECT WMSPD.SO_NO,\r\n" + 
					"       '' SO_ALLOC_ID,\r\n" + 
					"       WMSPD.LINE_NO SO_LINE_NO,\r\n" + 
					"       WMSPD.LOGISTIC_NO,\r\n" + 
					"       WMSPD.LOGISTIC_LINE_NO,\r\n" + 
					"       WMSPD.SALE_NO SALE_NO,\r\n" + 
					"       WMSPD.DEF1 SALE_LINE_NO,\r\n" + 
					"       '' OOD_NO,\r\n" + 
					"       '' OOD_LINE_NO,\r\n" + 
					"       EBCU.EBCU_SUBSTR2 OWNER_CODE,\r\n" + 
					"       WMSPD.SKU_CODE,\r\n" + 
					"       CWS.SKU_NAME,\r\n" + 
					"       WMSPD.PARENT_SKU_CODE,\r\n" + 
					"       CWSP.SKU_NAME PARENT_SKU_NAME,\r\n" + 
					"       WMSPD.STATUS SP_STATUS,\r\n" + 
					"       WMSPD.QTY_SO_EA QTY_PLAN_EA,\r\n" + 
					"       WMSPD.QTY_SHIP_EA QTY_SP_EA,\r\n" + 
					"       TO_CHAR(WMSPD.LOT_ATT01,'yyyy-mm-dd hh24:mi:ss') LOT_ATT01,\r\n" + 
					"       TO_CHAR(WMSPD.LOT_ATT02,'yyyy-mm-dd hh24:mi:ss') LOT_ATT02,\r\n" + 
					"       TO_CHAR(WMSPD.LOT_ATT03,'yyyy-mm-dd hh24:mi:ss') LOT_ATT03,\r\n" + 
					"       WMSPD.LOT_ATT04,\r\n" + 
					"       WMSPD.LOT_ATT05,\r\n" + 
					"       WMSPD.LOT_ATT06,\r\n" + 
					"       WMSPD.LOT_ATT07,\r\n" + 
					"       WMSPD.LOT_ATT08,\r\n" + 
					"       decode(WMSPD.LOT_ATT08,null,null,WMSPD.DEF1) LOT_ATT09,\r\n" + 
					"       WMSPD.LOT_ATT10,\r\n" + 
					"       WMSPD.LOT_ATT11,\r\n" + 
					"       WMSPD.LOT_ATT12,\r\n" + 
					"       ESUS.ESUS_USER_NAME_CN SP_OP,\r\n" + 
					"       TO_CHAR(WMSPD.MODIFY_TIME,'yyyy-mm-dd hh24:mi:ss') SP_TIME,\r\n" + 
					"       WMSPD.DEF2 LOAD_NO,\r\n" + 
					"       WMSPD.DEF1 LOAD_LINE_NO,\r\n" + 
					"       WMSPD.PROJECT_ID,\r\n" + 
					"       WMSPD.ORG_ID,\r\n" + 
					"       '05' as part,\r\n" + 
					"        '' as ZTXYTHH\r\n" + 
					"  FROM WM_SO_parts_DETAIL WMSPD\r\n" + 
					"  LEFT JOIN CD_WH_SKU CWS ON CWS.SKU_CODE = WMSPD.SKU_CODE AND CWS.OWNER_CODE = WMSPD.OWNER_CODE AND CWS.PROJECT_ID = WMSPD.PROJECT_ID\r\n" + 
					"  LEFT JOIN CD_WH_SKU CWSP ON CWSP.SKU_CODE = WMSPD.PARENT_SKU_CODE AND CWSP.OWNER_CODE = WMSPD.OWNER_CODE AND CWSP.PROJECT_ID = WMSPD.PROJECT_ID\r\n" + 
					"  LEFT JOIN EB_CUSTOMER EBCU ON EBCU.PM_CODE = WMSPD.OWNER_CODE AND EBCU.ORG_ID = WMSPD.ORG_ID\r\n" + 
					"  LEFT JOIN ES_USER ESUS ON ESUS.ESUS_ID = WMSPD.MODIFIER\r\n" + 
					"  LEFT JOIN WM_SO_HEADER WMSH ON WMSH.SO_NO = WMSPD.SO_NO AND WMSH.PROJECT_ID = WMSPD.PROJECT_ID\r\n" + 
					"WHERE WMSPD.SO_NO = ?\r\n" + 
					"  AND WMSPD.PROJECT_ID = ?) T__]; nested exception is org.hibernate.exception.SQLGrammarException: could not execute query\r\n" + 
					"	at org.springframework.orm.hibernate3.SessionFactoryUtils.convertHibernateAccessException(SessionFactoryUtils.java:635)\r\n" + 
					"	at org.springframework.orm.hibernate3.HibernateAccessor.convertHibernateAccessException(HibernateAccessor.java:412)\r\n" + 
					"	at org.springframework.orm.hibernate3.HibernateTemplate.doExecute(HibernateTemplate.java:412)\r\n" + 
					"	at org.springframework.orm.hibernate3.HibernateTemplate.executeWithNativeSession(HibernateTemplate.java:375)\r\n" + 
					"	at com.sinoservices.framework.core.support.CustomHibernateTemplate.findBySqlQueryAndValueBean(CustomHibernateTemplate.java:407)\r\n" + 
					"	at com.sinoservices.framework.core.support.CustomHibernateTemplate.findByNamedSqlQueryAndValueBean(CustomHibernateTemplate.java:540)\r\n" + 
					"	at com.sinoservices.framework.core.dao.impl.UniversalDaoImpl.query(UniversalDaoImpl.java:538)\r\n" + 
					"	at com.sinoservices.framework.core.dao.impl.UniversalDaoImpl.query(UniversalDaoImpl.java:520)\r\n" + 
					"	at com.sinoservices.framework.core.dao.impl.UniversalDaoImpl.query(UniversalDaoImpl.java:498)\r\n" + 
					"	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n" + 
					"	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n" + 
					"	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n" + 
					"	at java.lang.reflect.Method.invoke(Method.java:483)\r\n" + 
					"	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:317)\r\n" + 
					"	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:183)\r\n" + 
					"	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\r\n" + 
					"	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:91)\r\n" + 
					"	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\r\n" + 
					"	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:204)\r\n" + 
					"	at com.sun.proxy.$Proxy39.query(Unknown Source)\r\n" + 
					"	at com.sinoservices.example.database.service.impl.OmsOrderReturnManagerImpl.handleSoOrder(OmsOrderReturnManagerImpl.java:189)\r\n" + 
					"	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n" + 
					"	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n" + 
					"	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n" + 
					"	at java.lang.reflect.Method.invoke(Method.java:483)\r\n" + 
					"	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:317)\r\n" + 
					"	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:183)\r\n" + 
					"	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\r\n" + 
					"	at org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor.invoke(AfterReturningAdviceInterceptor.java:51)\r\n" + 
					"	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\r\n" + 
					"	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)\r\n" + 
					"	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)\r\n" + 
					"	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)\r\n" + 
					"	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\r\n" + 
					"	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:80)\r\n" + 
					"	at com.sinoservices.common.aop.AccessLogServiceAspect.around(AccessLogServiceAspect.java:56)\r\n" + 
					"	at sun.reflect.GeneratedMethodAccessor181.invoke(Unknown Source)\r\n" + 
					"	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n" + 
					"	at java.lang.reflect.Method.invoke(Method.java:483)\r\n" + 
					"	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:621)\r\n" + 
					"	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:610)\r\n" + 
					"	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:65)\r\n" + 
					"	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\r\n" + 
					"	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:91)\r\n" + 
					"	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\r\n" + 
					"	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:204)\r\n" + 
					"	at com.sun.proxy.$Proxy279.handleSoOrder(Unknown Source)\r\n" + 
					"	at com.sinoservices.example.job.OmsOrderReturnHandleJob.handleOrder(OmsOrderReturnHandleJob.java:130)\r\n" + 
					"	at com.sinoservices.example.job.OmsOrderReturnHandleJob.execute(OmsOrderReturnHandleJob.java:104)\r\n" + 
					"	at com.sinoservices.components.job.JobExecutor.execute(JobExecutor.java:35)\r\n" + 
					"	at com.sinoservices.components.timer.interfaces.impl.JobTimerAction.execute(JobTimerAction.java:22)\r\n" + 
					"	at com.sinoservices.components.timer.ActionJob.execute(ActionJob.java:46)\r\n" + 
					"	at org.quartz.core.JobRunShell.run(JobRunShell.java:202)\r\n" + 
					"	at org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:525)\r\n" + 
					"Caused by: org.hibernate.exception.SQLGrammarException: could not execute query\r\n" + 
					"	at org.hibernate.exception.SQLStateConverter.convert(SQLStateConverter.java:67)\r\n" + 
					"	at org.hibernate.exception.JDBCExceptionHelper.convert(JDBCExceptionHelper.java:43)\r\n" + 
					"	at org.hibernate.loader.Loader.doList(Loader.java:2216)\r\n" + 
					"	at org.hibernate.loader.Loader.listIgnoreQueryCache(Loader.java:2104)\r\n" + 
					"	at org.hibernate.loader.Loader.list(Loader.java:2099)\r\n" + 
					"	at org.hibernate.loader.custom.CustomLoader.list(CustomLoader.java:289)\r\n" + 
					"	at org.hibernate.impl.SessionImpl.listCustomQuery(SessionImpl.java:1695)\r\n" + 
					"	at org.hibernate.impl.AbstractSessionImpl.list(AbstractSessionImpl.java:142)\r\n" + 
					"	at org.hibernate.impl.SQLQueryImpl.list(SQLQueryImpl.java:152)\r\n" + 
					"	at com.sinoservices.framework.core.support.CustomHibernateTemplate$12.doInHibernate(CustomHibernateTemplate.java:440)\r\n" + 
					"	at org.springframework.orm.hibernate3.HibernateTemplate.doExecute(HibernateTemplate.java:407)\r\n" + 
					"	... 51 more\r\n" + 
					"Caused by: java.sql.SQLException: ORA-00904: \"CWS\".\"OWNER_CODE\": 标识符无效\r\n" + 
					"\r\n" + 
					"	at oracle.jdbc.driver.SQLStateMapping.newSQLException(SQLStateMapping.java:74)\r\n" + 
					"	at oracle.jdbc.driver.DatabaseError.newSQLException(DatabaseError.java:110)\r\n" + 
					"	at oracle.jdbc.driver.DatabaseError.throwSqlException(DatabaseError.java:171)\r\n" + 
					"	at oracle.jdbc.driver.T4CTTIoer.processError(T4CTTIoer.java:455)\r\n" + 
					"	at oracle.jdbc.driver.T4CTTIoer.processError(T4CTTIoer.java:413)\r\n" + 
					"	at oracle.jdbc.driver.T4C8Oall.receive(T4C8Oall.java:1030)\r\n" + 
					"	at oracle.jdbc.driver.T4CPreparedStatement.doOall8(T4CPreparedStatement.java:194)\r\n" + 
					"	at oracle.jdbc.driver.T4CPreparedStatement.executeForDescribe(T4CPreparedStatement.java:785)\r\n" + 
					"	at oracle.jdbc.driver.T4CPreparedStatement.executeMaybeDescribe(T4CPreparedStatement.java:860)\r\n" + 
					"	at oracle.jdbc.driver.OracleStatement.doExecuteWithTimeout(OracleStatement.java:1186)\r\n" + 
					"	at oracle.jdbc.driver.OraclePreparedStatement.executeInternal(OraclePreparedStatement.java:3381)\r\n" + 
					"	at oracle.jdbc.driver.OraclePreparedStatement.executeQuery(OraclePreparedStatement.java:3425)\r\n" + 
					"	at oracle.jdbc.driver.OraclePreparedStatementWrapper.executeQuery(OraclePreparedStatementWrapper.java:1202)\r\n" + 
					"	at net.sf.log4jdbc.PreparedStatementSpy.executeQuery(PreparedStatementSpy.java:610)\r\n" + 
					"	at com.alibaba.druid.filter.FilterChainImpl.preparedStatement_executeQuery(FilterChainImpl.java:2714)\r\n" + 
					"	at com.alibaba.druid.filter.FilterEventAdapter.preparedStatement_executeQuery(FilterEventAdapter.java:465)\r\n" + 
					"	at com.alibaba.druid.filter.FilterChainImpl.preparedStatement_executeQuery(FilterChainImpl.java:2711)\r\n" + 
					"	at com.alibaba.druid.proxy.jdbc.PreparedStatementProxyImpl.executeQuery(PreparedStatementProxyImpl.java:132)\r\n" + 
					"	at com.alibaba.druid.pool.DruidPooledPreparedStatement.executeQuery(DruidPooledPreparedStatement.java:227)\r\n" + 
					"	at org.hibernate.jdbc.AbstractBatcher.getResultSet(AbstractBatcher.java:186)\r\n" + 
					"	at org.hibernate.loader.Loader.getResultSet(Loader.java:1787)\r\n" + 
					"	at org.hibernate.loader.Loader.doQuery(Loader.java:674)\r\n" + 
					"	at org.hibernate.loader.Loader.doQueryAndInitializeNonLazyCollections(Loader.java:236)\r\n" + 
					"	at org.hibernate.loader.Loader.doList(Loader.java:2213)\r\n" + 
					"	... 59 more\r\n" + 
					"}";
		    String regEx = "Caused by:(.*)";
		    Pattern pat = Pattern.compile(regEx);  
		    Matcher mat = pat.matcher(string);  
		    boolean rs = mat.find();  
		    System.out.println("found?" + rs);
		    System.out.println(mat.group(0));
		    System.out.println(mat.group(1));
	}
	public String getExceptionStack(Throwable e, HashSet<String> set,int num){

        StackTraceElement[] stackTraceElements = e.getStackTrace();
        String prefix = "";
        if (num == 0){
            prefix = "Exception in thread "+"\""+Thread.currentThread().getName()+"\" ";
        } else {
            prefix = "Caused by: ";
        }
        String result = prefix+e.toString() + "\n";
        int lenth = stackTraceElements.length - 1;
        for (int i = 0;i<=lenth;i++){
            String err = stackTraceElements[i].getClassName()+"."+stackTraceElements[i].getMethodName()+"("+stackTraceElements[i].getFileName()+"."+stackTraceElements[i].getLineNumber()+")";
            if (set.contains(err)){
                continue;
            }
            set.add(err);
            result = result + "\tat "+err+"\n";

        }

        Throwable t = e.getCause();

        String cause = "";
        if (t!=null){
            num++;
            cause = getExceptionStack(t,set,num);
        }
        
        return result + cause;
    }
	/**
	 * 测试split
	 */
	@Test
	public void test12() {
		String string = "@aa";
		String[] split = string.split("@");
		System.out.println(split);
		System.out.println(split.length);
		System.out.println(split[0]);
		System.out.println(split[1]);
	}
	/**
	 * 测试String.format("%04d", lineNo)
	 */
	@Test
	public void test13() {
		String format = String.format("%04d", 30000000);
		System.out.println(format);
	}
	/**
	 * 测试定时器
	 */
	@Test
	public void test14() {
		ScheduleJob scheduleJob = new ScheduleJob();
		scheduleJob.setJobName("00");
		scheduleJob.setJobGroupName("TEST");
		scheduleJob.setTriggerName("00");
		scheduleJob.setTriggerGroupName("TEST");
		scheduleJob.setBeanClass("sh.test.MyQuartz");
		scheduleJob.setMethodName("show");
		scheduleJob.setCronExpression("0/4 * * * * ?");// 每秒钟执行一次
		
		QuartzManager.addJob(scheduleJob);
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		/*
		 * 测试修改任务时间
		 */
//		scheduleJob.setCronExpression("0/30 * * * * ?");// 每30秒执行一次
//		QuartzManager.modifyJobTime(scheduleJob);
	}
}
