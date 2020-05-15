package com.ruoyi.subject.mapper;

import java.util.List;
import com.ruoyi.subject.domain.SubSubject;

/**
 * 课题列表Mapper接口
 * 
 * @author ruoyi
 * @date 2020-05-14
 */
public interface SubSubjectMapper 
{
    /**
     * 查询课题列表
     * 
     * @param id 课题列表ID
     * @return 课题列表
     */
    public SubSubject selectSubSubjectById(Long id);

    /**
     * 查询课题列表列表
     * 
     * @param subSubject 课题列表
     * @return 课题列表集合
     */
    public List<SubSubject> selectSubSubjectList(SubSubject subSubject);

    /**
     * 新增课题列表
     * 
     * @param subSubject 课题列表
     * @return 结果
     */
    public int insertSubSubject(SubSubject subSubject);

    /**
     * 修改课题列表
     * 
     * @param subSubject 课题列表
     * @return 结果
     */
    public int updateSubSubject(SubSubject subSubject);

    /**
     * 删除课题列表
     * 
     * @param id 课题列表ID
     * @return 结果
     */
    public int deleteSubSubjectById(Long id);

    /**
     * 批量删除课题列表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSubSubjectByIds(String[] ids);
}
