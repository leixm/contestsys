package com.app.dao;

import com.code.model.Contestpaper;
import com.code.model.ContestpaperExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ContestpaperMapper {
	int countByExample(ContestpaperExample example);

	int deleteByExample(ContestpaperExample example);

	int deleteByPrimaryKey(Integer paperId);

	int insert(Contestpaper record);

	int insertSelective(Contestpaper record);

	List<Contestpaper> selectByExample(ContestpaperExample example);

	Contestpaper selectByPrimaryKey(Integer paperId);

	int updateByExampleSelective(@Param("record") Contestpaper record, @Param("example") ContestpaperExample example);

	int updateByExample(@Param("record") Contestpaper record, @Param("example") ContestpaperExample example);

	int updateByPrimaryKeySelective(Contestpaper record);

	int updateByPrimaryKey(Contestpaper record);

	List<Map<String, Object>> listAll();

	List<Map<String, Object>> listAllByKeyword(String keyword);
	
	List<Map<String, Object>> listAllByDate(String startTime,String endTime);
	
	List<Map<String, Object>> listAllByKeywordAndDate(String keyword,String startTime,String endTime);
	
    @Select("select max(paper_id) from contestpaper")
    int selMaxPaperId();
    
    @Select("select  count(*) from contestpaper")
    int selPaperCount();

}