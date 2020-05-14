package com.ruoyi.activiti.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.ruoyi.activiti.domain.BizSubjectVo;
import com.ruoyi.activiti.service.IProcessService;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.SysUserMapper;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.activiti.mapper.BizSubjectMapper;
import com.ruoyi.activiti.domain.BizSubject;
import com.ruoyi.activiti.service.IBizSubjectService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.util.CollectionUtils;

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

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Autowired
    private IProcessService processService;

    /**
     * 查询课题申请
     * 
     * @param id 课题申请ID
     * @return 课题申请
     */
    @Override
    public BizSubjectVo selectBizSubjectById(Long id)
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
    public List<BizSubjectVo> selectBizSubjectList(BizSubject bizSubject) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();

        // PageHelper 仅对第一个 List 分页
        Page<BizSubjectVo> list = (Page<BizSubjectVo>) bizSubjectMapper.selectBizSubjectList(bizSubject);
        Page<BizSubjectVo> returnList = new Page<>();
        for (BizSubjectVo subject: list) {
            SysUser sysUser = userMapper.selectUserByLoginName(subject.getCreateBy());
            if (sysUser != null) {
                subject.setCreateUserName(sysUser.getUserName());
            }
            SysUser sysUser2 = userMapper.selectUserByLoginName(subject.getApplyUser());
            if (sysUser2 != null) {
                subject.setApplyUserName(sysUser2.getUserName());
            }
            // 当前环节
            if (StringUtils.isNotBlank(subject.getInstanceId())) {
                List<Task> taskList = taskService.createTaskQuery()
                        .processInstanceId(subject.getInstanceId())
//                        .singleResult();
                        .list();    // 例如请假会签，会同时拥有多个任务
                if (!CollectionUtils.isEmpty(taskList)) {
                    TaskEntityImpl task = (TaskEntityImpl) taskList.get(0);
                    subject.setTaskId(task.getId());
                    if (task.getSuspensionState() == 2) {
                        subject.setTaskName("已挂起");
                        subject.setSuspendState("2");
                    } else {
                        subject.setTaskName(task.getName());
                        subject.setSuspendState("1");
                    }
                } else {
                    // 已办结或者已撤销
                    subject.setTaskName("已结束");
                }
            } else {
                subject.setTaskName("未启动");
            }
            returnList.add(subject);
        }
        returnList.setTotal(CollectionUtils.isEmpty(list) ? 0 : list.getTotal());
        returnList.setPageNum(pageNum);
        returnList.setPageSize(pageSize);
        return returnList;
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
        bizSubject.setCreateBy(ShiroUtils.getLoginName());
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

    /**
     * 启动流程
     * @param entity
     * @param applyUserId
     * @return
     */
    @Override
    public ProcessInstance submitApply(BizSubjectVo entity, String applyUserId, String key, Map<String, Object> variables) {
        entity.setApplyUser(applyUserId);
        entity.setApplyTime(DateUtils.getNowDate());
        entity.setUpdateBy(applyUserId);
        bizSubjectMapper.updateBizSubject(entity);
        String businessKey = entity.getId().toString(); // 实体类 ID，作为流程的业务 key

        ProcessInstance processInstance = processService.submitApply(applyUserId, businessKey, applyUserId+"课题申报流程", "Reason", key, variables);

        String processInstanceId = processInstance.getId();
        entity.setInstanceId(processInstanceId); // 建立双向关系
        bizSubjectMapper.updateBizSubject(entity);

        return processInstance;
    }

    @Override
    public List<BizSubjectVo> findTodoTasks(BizSubjectVo subject, String userId) {
        // 手动分页
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Page<BizSubjectVo> list = new Page<>();

        List<BizSubjectVo> results = new ArrayList<>();
        List<Task> tasks = processService.findTodoTasks(userId, "subject");
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            TaskEntityImpl taskImpl = (TaskEntityImpl) task;
            String processInstanceId = taskImpl.getProcessInstanceId();
            // 条件过滤 1
            if (StringUtils.isNotBlank(subject.getInstanceId()) && !subject.getInstanceId().equals(processInstanceId)) {
                continue;
            }
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = processInstance.getBusinessKey();
            BizSubjectVo subject2 = bizSubjectMapper.selectBizSubjectById(new Long(businessKey));
            subject2.setTaskId(taskImpl.getId());
            if (taskImpl.getSuspensionState() == 2) {
                subject2.setTaskName("已挂起");
            } else {
                subject2.setTaskName(taskImpl.getName());
            }
            SysUser sysUser = userMapper.selectUserByLoginName(subject2.getApplyUser());
            subject2.setApplyUserName(sysUser.getUserName());
            results.add(subject2);
        }

        List<BizSubjectVo> tempList;
        if (pageNum != null && pageSize != null) {
            int maxRow = (pageNum - 1) * pageSize + pageSize > results.size() ? results.size() : (pageNum - 1) * pageSize + pageSize;
            tempList = results.subList((pageNum - 1) * pageSize, maxRow);
            list.setTotal(results.size());
            list.setPageNum(pageNum);
            list.setPageSize(pageSize);
        } else {
            tempList = results;
        }

        list.addAll(tempList);
        return list;

    }


}
