package com.ruoyi.activiti.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.activiti.mapper.BizSubjectMapper;
import com.ruoyi.activiti.domain.BizSubject;
import com.ruoyi.activiti.service.IBizSubjectService;
import com.ruoyi.common.core.text.Convert;

/**
 * 课题申请Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-05-13
 */
@Service
public class BizSubjectServiceImpl implements IBizSubjectService 
{
    @Autowired
    private BizSubjectMapper bizSubjectMapper;

    /**
     * 查询课题申请
     * 
     * @param id 课题申请ID
     * @return 课题申请
     */
    @Override
    public BizSubject selectBizSubjectById(Long id)
    {
        return bizSubjectMapper.selectBizSubjectById(id);
    }

    /**
     * 查询课题申请列表
     * 
     * @param bizSubject 课题申请
     * @return 课题申请
     */
    @Override
    public List<BizSubject> selectBizSubjectList(BizSubject bizSubject)
    {
        return bizSubjectMapper.selectBizSubjectList(bizSubject);
    }

    /**
     * 新增课题申请
     * 
     * @param bizSubject 课题申请
     * @return 结果
     */
    @Override
    public int insertBizSubject(BizSubject bizSubject)
    {
        bizSubject.setCreateTime(DateUtils.getNowDate());
        return bizSubjectMapper.insertBizSubject(bizSubject);
    }

    /**
     * 修改课题申请
     * 
     * @param bizSubject 课题申请
     * @return 结果
     */
    @Override
    public int updateBizSubject(BizSubject bizSubject)
    {
        bizSubject.setUpdateTime(DateUtils.getNowDate());
        return bizSubjectMapper.updateBizSubject(bizSubject);
    }

    /**
     * 删除课题申请对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizSubjectByIds(String ids)
    {
        return bizSubjectMapper.deleteBizSubjectByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除课题申请信息
     * 
     * @param id 课题申请ID
     * @return 结果
     */
    @Override
    public int deleteBizSubjectById(Long id)
    {
        return bizSubjectMapper.deleteBizSubjectById(id);
    }
}
