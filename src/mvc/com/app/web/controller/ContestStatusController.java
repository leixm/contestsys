package com.app.web.controller;

import com.annotation.SystemControllerLog;
import com.app.service.impl.ContestStatusServiceImpl;
import com.code.model.LayResponse;
import com.code.model.User;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;


@Controller
public class ContestStatusController {
    @Resource
    private ContestStatusServiceImpl contestStatusService;

    /**
     * @author lxm
     * @create_date 2019.5.3
     * @description 学生考试作答情况pdf导出
     * */

    @ResponseBody
    @SystemControllerLog(description = "学生考试作答情况pdf导出")
    @RequestMapping(value="ContestStatus/Pdf")
    public String getContestStatusPdf(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String contestStatusId = request.getParameter("contestStatusId");
        String stuId = request.getParameter("stuid");

        LayResponse resp = new LayResponse();
        resp.setCode(1);
        //获取所登录用户的user对象
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int level = user.getLevel();
        if(level < 1) { // 学生用户，进行校验登录身份是否是操作本人试题导出
           stuId = user.getUserId();
        }

        String filename = stuId + "-contestSituation.pdf";
        // pdf文件存放路径
        String path = contestStatusService.GetContestStatusPdf(Integer.parseInt(contestStatusId));

        if(path!=null && !"".equals(path)) {
            File file = new File(path);
            response.addHeader("content-disposition", "attachment;filename="+filename);
            FileUtils.copyFile(file, response.getOutputStream());

            resp.setMsg("请求成功");
            resp.setCode(0);
            return JSONObject.fromObject(resp).toString();
        } else {
            resp.setMsg("导出pdf失败，请重试");
            return JSONObject.fromObject(resp).toString();
        }

    }
}
