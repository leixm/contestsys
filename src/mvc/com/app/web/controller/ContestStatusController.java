package com.app.web.controller;

import com.app.service.impl.ClassService;
import com.app.service.impl.ContestService;
import com.app.service.impl.ContestStatusService;
import com.app.service.impl.UserService;
import com.code.model.LayResponse;
import com.code.model.User;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * @author lxm
 * @create_date 2019.5.3
 * @description 学生考试作答情况pdf导出
 * */

@Controller
public class ContestStatusController {
    @Resource
    private ContestStatusService contestStatusService;

    @RequestMapping(value="ContestStatus/Pdf")
    @ResponseBody
    private void getContestStatusPdf(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
        String contestStatusId = request.getParameter("contestStatusId");
        String stuId = request.getParameter("stuid");
        String filename = stuId + "-contestSituation.pdf";

        String path = contestStatusService.GetContestStatusPdf(Integer.parseInt(contestStatusId));

        File file = new File(path);
        response.addHeader("content-disposition", "attachment;filename="+filename);
        FileUtils.copyFile(file, response.getOutputStream());
    }
}
