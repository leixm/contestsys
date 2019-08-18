package com.app.service;

import java.util.List;

import com.code.model.Contest;
import com.code.model.OnePaper;
import com.code.model.OneSimproblem;
import com.code.model.Response;
import com.code.model.User;

public interface TeacherService {
	/**
	 * ���һ���µĿ���  
	 * @return 0��ӿ���ʧ��  1��ӿ��Գɹ�  -1Ȩ�޲���
	 */
	public Response addContest(Contest contest,String className);
	
	/**
	 * ��ѯ��ʦ�������о���
	 * @param user
	 * @return ����ͬ��userId�����еľ���
	 */
	public Response selAllpaper(User user);
	
	/**
	 * ����µ��Ծ�
	 * @param newpaper ��Response��ȡ����OnePaper 
	 * @return 1��ӳɹ�     0���ʧ��
	 */
	public Response addNewpaper(OnePaper newpaper,User user,List<OneSimproblem> oneSimps);
	
	/**
	 * ��ʦ��ѯ���п���Status
	 * @return Response
	 * 
	 */
	public Response selContestStatus(User user);
	
	
	
}
