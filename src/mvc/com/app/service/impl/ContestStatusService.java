/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 考试状态管理服务
 * */
package com.app.service.impl;

import com.app.tools.PdfHelper;
import org.springframework.stereotype.Service;

@Service
public class ContestStatusService {
        public String GetContestStatusPdf(int contestStatusId){
            return PdfHelper.pdf_dir + "/" + contestStatusId + ".pdf";
        }
}
