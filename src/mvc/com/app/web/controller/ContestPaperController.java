/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 试卷管理 
 * */
package com.app.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.annotation.SystemControllerLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.service.impl.ContestPaperService;
import com.code.model.Contest;
import com.code.model.Contestpaper;
import com.code.model.LayResponse;
import com.code.model.User;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class ContestPaperController {

	@Resource
	private ContestPaperService contestpaperService;
	
	/**
	 * @author lxm
	 * @param Keyword: 关键字
	 * @param startTime: 开始时间
	 * @param endTime: 结束时间
	 * @description 查询试卷
	 * @return 试卷实例列表
	 */
	@RequestMapping(value = "ContestPaper/GetAllContestPaper", method = { RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String GetAllContestPaper(HttpServletRequest request, HttpServletResponse response, String Keyword,String startTime,String endTime)
			throws Exception {
		// 获取所属课程id
		int simCourseId = 0;
		if(request.getSession().getAttribute("course_id")!=null) {
			simCourseId = Integer.parseInt(request.getSession().getAttribute("course_id").toString());
		}

		String pageSize = request.getParameter("limit"); //一页多少个
		String pageNumber = request.getParameter("page");	//第几页
		List resultList = contestpaperService.GetAllContestPaper(Keyword,startTime,endTime,simCourseId,pageSize,pageNumber);
		JSONObject obj = new JSONObject();
		JSONArray arr = JSONArray.fromObject(resultList);
		//获取分页插件的数据只能通过PageInfo来获取
		PageInfo pInfo = new PageInfo(resultList);
		Long total = pInfo.getTotal();
		obj.put("code", 0);
		obj.put("msg", "返回成功");
		obj.put("count", total.intValue());
		obj.put("data", arr);
		return obj.toString();
	}
	
	/**
	 * @author lxm
	 * @param id: 考试id
	 * @description 跳转至更新试卷页面
	 * @return 视图、试卷信息
	 */
	@RequestMapping(value = "editcontestpaper.do", method = { RequestMethod.POST,RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView EditContestPaper(HttpServletRequest request, String id) {

		ModelAndView mav = new ModelAndView();
		mav.addObject("contestpaper", contestpaperService.GetContestPaper(id));
		mav.setViewName("contestpaper-edit.jsp");
		return mav;
	}
	
	/**
	 * @author lxm
	 * @param contestpaper: 试卷实例
	 * @description 更新试卷信息
	 * @return 响应状态
	 */
	@RequestMapping(value = "ContestPaper/UpdateContestPaper", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "更新试卷信息")
	public String UpdateContest(HttpServletRequest request,Contestpaper contestpaper) {

		LayResponse response = new LayResponse();
		response.setCode(1);
		if (contestpaperService.UpdateContestPaper(contestpaper) > 0) {
			response.setCode(0);
			return JSONObject.fromObject(response).toString();
		} else {
			response.setMsg("更新失败");
			return JSONObject.fromObject(response).toString();
		}
	}
	
	/**
	 * @author lxm
	 * @param id: 试卷id
	 * @description 删除试卷
	 * @return 响应状态
	 */
	@RequestMapping(value = "ContestPaper/DeleteContestPaper", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "删除试卷")
	public String DeleteContestPaper(HttpServletRequest request, String id) {

		LayResponse response = new LayResponse();
		response.setCode(1);
		
		int resultCode = contestpaperService.DeleteContestPaper(id);
		if (resultCode > 0) {
			response.setMsg("删除成功！");
			return "1";
			
		} else if(resultCode == -2) {
			response.setMsg("该试卷已经用于某场考试，无法进行删除！");
			return "-2";
		}
		else {
			response.setMsg("删除失败");
			return "-1";
		}
	}
	
	/**
	 * @author zzs
	 * @param 
	 * @description 批量删除试卷
	 * @return 响应状态
	 */
	@RequestMapping(value = "ContestPaper/DelAll", consumes = "application/json", produces = "text/html;charset=UTF-8", method = {RequestMethod.POST })
	@ResponseBody
	@SystemControllerLog(description = "批量删除试卷")
	public String DelAll(HttpServletRequest request, @RequestBody List<String> ids) {
		  
		  LayResponse response = new LayResponse(); 
		  response.setCode(1);
		  //获取所登录用户的user对象
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			int level = user.getLevel();
			if(level < 2) {
				response.setMsg("无操作权限");
				return JSONObject.fromObject(response).toString();
			}
		  
		  if(contestpaperService.DeleteAllContestPaper(ids)>0){
		   response.setCode(0); 
		   //System.out.println(JSONObject.fromObject(response).toString());
		   return JSONObject.fromObject(response).toString(); 
		   }
		  else{
			  response.setMsg("删除失败"); 
			  return JSONObject.fromObject(response).toString(); 
		  }
		 
	}
}
