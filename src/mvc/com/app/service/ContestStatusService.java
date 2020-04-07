/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 考试状态管理服务
 * */
package com.app.service;

import com.app.dao.*;
import com.app.tools.PdfHelper;
import com.code.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface ContestStatusService {

    public String GetContestStatusPdf(int contestStatusId);

}
