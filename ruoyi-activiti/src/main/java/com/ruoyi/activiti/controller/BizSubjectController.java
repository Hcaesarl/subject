package com.ruoyi.activiti.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.activiti.domain.BizSubject;
import com.ruoyi.activiti.service.IBizSubjectService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

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
    private IBizSubjectService bizSubjectService;

//    @RequiresPermissions("system:subject:view")
    @GetMapping()
    public String subject()
    {
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
        List<BizSubject> list = bizSubjectService.selectBizSubjectList(bizSubject);
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
        List<BizSubject> list = bizSubjectService.selectBizSubjectList(bizSubject);
        ExcelUtil<BizSubject> util = new ExcelUtil<BizSubject>(BizSubject.class);
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
}
