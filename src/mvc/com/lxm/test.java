package com.lxm;
import com.app.tools.PropertyFileUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.Date;
import java.util.List;

import com.code.model.Answer;
import com.code.model.Contestpaper;
import com.code.model.OnePaper;
import com.code.model.OneProblem;
import com.code.model.OneSimproblem;
import com.code.model.Options;
import com.code.model.ProblemWithBLOBs;
import com.code.model.Simproblem;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Calendar;

/**
 * @author Administrator
 *
 */
public class test {

    private static JSONObject createJSONObject(){   
        JSONObject jsonObject = new JSONObject();   
        jsonObject.put("username","huangwuyi");   
        jsonObject.put("sex", "��");   
        jsonObject.put("QQ", "999999999");   
        jsonObject.put("Min.score", new Integer(99));   
        jsonObject.put("nickname", "�����ľ�");   
        return jsonObject;   
    }
    
    private List<Date> GetPreWeek()
    {
    	List<Date> list = new ArrayList<Date>();
    	Date date = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	while(cal.get(Calendar.DAY_OF_WEEK)!=1)
    		cal.add(Calendar.DATE, -1);
    	
    	for(int i=0;i<7;i++)
    	{
    		date = cal.getTime();
    		list.add(date);
    		cal.add(Calendar.DATE, -1);
    	}
    	Collections.reverse(list);
    	return list;
    }

    private void Query() throws SQLException, ClassNotFoundException
    {
    	//Date date = new java.sql.Time(new Date().getTime());
    	String sql = "insert into course(courseid,aa) values(74,''{0}'')";
     //   jdbcHelper jd = new jdbcHelper();
        java.util.Date date = new java.util.Date();          // ��ȡһ��Date����
        Timestamp ts = new Timestamp(date.getTime());
       // jd.Insert(MessageFormat.format(sql,ts.toString()));
    }
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		PropertyFileUtil.init();
		String path = PropertyFileUtil.getProperty("pdf_dir");
		System.out.println(path);
	}  

}


