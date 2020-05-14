package com.ruoyi.activiti.controller;

import java.util.HashMap;
import java.util.List;

import com.ruoyi.activiti.domain.BizLeaveVo;
import com.ruoyi.activiti.domain.BizSubjectVo;
import com.ruoyi.activiti.service.IProcessService;
import com.ruoyi.framework.util.ShiroUtils;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.activiti.domain.BizSubject;
import com.ruoyi.activiti.service.IBizSubjectService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 课题申请Controller
 * 
 * @author ruoyi
 * @date 2020-05-13
 */
@Controller
@RequestMapping("/subject")
public class BizSubjectController extends BaseController
{
    private String prefix = "subject";

    @Autowired
    private TaskService taskService;

    @Autowired
    private IBizSubjectService bizSubjectService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IProcessService processService;

//    @RequiresPermissions("system:subject:view")
    @GetMapping()
    public String subject(ModelMap mmap)
    {
        mmap.put("currentUser", ShiroUtils.getSysUser());
        return prefix + "/subject";
    }

    /**
     * 查询课题申请列表
     */
//    @RequiresPermissions("system:subject:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BizSubject bizSubject)
    {
        startPage();
        List<BizSubjectVo> list = bizSubjectService.selectBizSubjectList(bizSubject);
        return getDataTable(list);
    }

    /**
     * 导出课题申请列表
     */
//    @RequiresPermissions("system:subject:export")
    @Log(title = "课题申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BizSubject bizSubject)
    {
        List<BizSubjectVo> list = bizSubjectService.selectBizSubjectList(bizSubject);
        ExcelUtil<BizSubjectVo> util = new ExcelUtil<BizSubjectVo>(BizSubjectVo.class);
        return util.exportExcel(list, "subject");
    }

    /**
     * 新增课题申请
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存课题申请
     */
//    @RequiresPermissions("system:subject:add")
    @Log(title = "课题申请", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BizSubject bizSubject)
    {
        return toAjax(bizSubjectService.insertBizSubject(bizSubject));
    }

    /**
     * 修改课题申请
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BizSubject bizSubject = bizSubjectService.selectBizSubjectById(id);
        mmap.put("bizSubject", bizSubject);
        return prefix + "/edit";
    }

    /**
     * 修改保存课题申请
     */
//    @RequiresPermissions("system:subject:edit")
    @Log(title = "课题申请", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BizSubject bizSubject)
    {
        return toAjax(bizSubjectService.updateBizSubject(bizSubject));
    }

    /**
     * 删除课题申请
     */
//    @RequiresPermissions("system:subject:remove")
    @Log(title = "课题申请", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(bizSubjectService.deleteBizSubjectByIds(ids));
    }

    /**
     * 提交申请
     */
    @Log(title = "请假业务", businessType = BusinessType.UPDATE)
    @PostMapping( "/submitApply")
    @ResponseBody
    public AjaxResult submitApply(Long id) {
        BizSubjectVo subject = bizSubjectService.selectBizSubjectById(id);
        String applyUserId = ShiroUtils.getLoginName();
        HashMap<String, Object> map = new HashMap<>();
        map.put("firstTeacher", subject.getFirstteacherusername());
        map.put("secondTeacher", subject.getSecondteacherusername());
        bizSubjectService.submitApply(subject, applyUserId, "subject", map);
        return success();
    }
    @GetMapping("/subjectTodo")
    public String todoView() {
        return prefix + "/subjectTodo";
    }

    /**
     * 我的待办列表
     * @return
     */
    @PostMapping("/taskList")
    @ResponseBody
    public TableDataInfo taskList(BizSubjectVo subject) {
        List<BizSubjectVo> list = bizSubjectService.findTodoTasks(subject, ShiroUtils.getLoginName());
        return getDataTable(list);
    }

    /**
     * 加载审批弹窗
     * @param taskId
     * @param mmap
     * @return
     */
    @GetMapping("/showVerifyDialog/{taskId}")
    public String showVerifyDialog(@PathVariable("taskId") String taskId, ModelMap mmap) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        BizSubjectVo bizSubject = bizSubjectService.selectBizSubjectById(new Long(processInstance.getBusinessKey()));
        mmap.put("bizSubject", bizSubject);
        mmap.put("taskId", taskId);
        String verifyName = task.getTaskDefinitionKey().substring(0, 1).toUpperCase() + task.getTaskDefinitionKey().substring(1);
        return prefix + "/task" + verifyName;
    }

    /**
     * 完成任务
     *
     * @return
     */
    @RequestMapping(value = "/complete/{taskId}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AjaxResult complete(@PathVariable("taskId") String taskId, @RequestParam(value = "saveEntity", required = false) String saveEntity,
                               @ModelAttribute("preloadLeave") BizSubjectVo subject, HttpServletRequest request,ModelMap mmap) {
        boolean saveEntityBoolean = BooleanUtils.toBoolean(saveEntity);
        HashMap<String, Object> map = new HashMap<>();
        map.put("firstTeacher", subject.getFirstteacherusername());
        map.put("secondTeacher", subject.getSecondteacherusername());
        processService.complete(taskId, subject.getInstanceId(), subject.getApplyUser()+"课题申报流程","CONMENT", "subject", map, request);
        if (saveEntityBoolean) {
            bizSubjectService.updateBizSubject(subject);
        }
        return success("任务已完成");
    }

    /**
     * 自动绑定页面字段
     */
    @ModelAttribute("preloadLeave")
    public BizSubjectVo getLeave(@RequestParam(value = "id", required = false) Long id, HttpSession session) {
        if (id != null) {
            return bizSubjectService.selectBizSubjectById(id);
        }
        return new BizSubjectVo();
    }

    @GetMapping("/subjectDone")
    public String doneView() {
        return prefix + "/subjectDone";
    }

    /**
     * 我的已办列表
     * @param bizSubject
     * @return
     */
    @PostMapping("/taskDoneList")
    @ResponseBody
    public TableDataInfo taskDoneList(BizSubjectVo bizSubject) {
        List<BizSubjectVo> list = bizSubjectService.findDoneTasks(bizSubject, ShiroUtils.getLoginName());
        return getDataTable(list);
    }

}

