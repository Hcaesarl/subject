package com.ruoyi.subject.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.subject.mapper.SubSubjectMapper;
import com.ruoyi.subject.domain.SubSubject;
import com.ruoyi.subject.service.ISubSubjectService;
import com.ruoyi.common.core.text.Convert;

/**
 * 课题列表Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-05-14
 */
@Service
public class SubSubjectServiceImpl implements ISubSubjectService 
{
    @Autowired
    private SubSubjectMapper subSubjectMapper;

    /**
     * 查询课题列表
     * 
     * @param id 课题列表ID
     * @return 课题列表
     */
    @Override
    public SubSubject selectSubSubjectById(Long id)
    {
        return subSubjectMapper.selectSubSubjectById(id);
    }

    /**
     * 查询课题列表列表
     * 
     * @param subSubject 课题列表
     * @return 课题列表
     */
    @Override
    public List<SubSubject> selectSubSubjectList(SubSubject subSubject)
    {
        return subSubjectMapper.selectSubSubjectList(subSubject);
    }

    /**
     * 新增课题列表
     * 
     * @param subSubject 课题列表
     * @return 结果
     */
    @Override
    public int insertSubSubject(SubSubject subSubject)
    {
        subSubject.setCreateTime(DateUtils.getNowDate());
        return subSubjectMapper.insertSubSubject(subSubject);
    }

    /**
     * 修改课题列表
     * 
     * @param subSubject 课题列表
     * @return 结果
     */
    @Override
    public int updateSubSubject(SubSubject subSubject)
    {
        subSubject.setUpdateTime(DateUtils.getNowDate());
        return subSubjectMapper.updateSubSubject(subSubject);
    }

    /**
     * 删除课题列表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSubSubjectByIds(String ids)
    {
        return subSubjectMapper.deleteSubSubjectByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除课题列表信息
     * 
     * @param id 课题列表ID
     * @return 结果
     */
    @Override
    public int deleteSubSubjectById(Long id)
    {
        return subSubjectMapper.deleteSubSubjectById(id);
    }
}
