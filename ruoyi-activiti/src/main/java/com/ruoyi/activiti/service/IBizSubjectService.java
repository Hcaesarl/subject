package com.ruoyi.activiti.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.activiti.domain.BizLeaveVo;
import com.ruoyi.activiti.domain.BizSubject;
import com.ruoyi.activiti.domain.BizSubjectVo;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 课题申请Service接口
 * 
 * @author ruoyi
 * @date 2020-05-13
 */
public interface IBizSubjectService 
{
    /**
     * 查询课题申请
     * 
     * @param id 课题申请ID
     * @return 课题申请
     */
    public BizSubjectVo selectBizSubjectById(Long id);

    /**
     * 查询课题申请列表
     * 
     * @param bizSubject 课题申请
     * @return 课题申请集合
     */
    public List<BizSubjectVo> selectBizSubjectList(BizSubject bizSubject);

    /**
     * 新增课题申请
     * 
     * @param bizSubject 课题申请
     * @return 结果
     */
    public int insertBizSubject(BizSubject bizSubject);

    /**
     * 修改课题申请
     * 
     * @param bizSubject 课题申请
     * @return 结果
     */
    public int updateBizSubject(BizSubject bizSubject);

    /**
     * 批量删除课题申请
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizSubjectByIds(String ids);

    /**
     * 删除课题申请信息
     * 
     * @param id 课题申请ID
     * @return 结果
     */
    public int deleteBizSubjectById(Long id);

    /**
     * 启动流程
     *
     * @param entity
     * @param applyUserId
     * @return
     */
    public ProcessInstance submitApply(BizSubjectVo entity, String applyUserId, String key, Map<String, Object> variables);

    /**
     * 查询我的待办列表
     * @param userId
     * @return
     */
    List<BizSubjectVo> findTodoTasks(BizSubjectVo subject, String userId);
}
