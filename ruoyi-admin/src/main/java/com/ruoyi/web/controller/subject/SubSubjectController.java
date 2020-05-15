package com.ruoyi.web.controller.subject;

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
import com.ruoyi.subject.domain.SubSubject;
import com.ruoyi.subject.service.ISubSubjectService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 课题列表Controller
 * 
 * @author ruoyi
 * @date 2020-05-14
 */
@Controller
@RequestMapping("/subject/subject")
public class SubSubjectController extends BaseController
{
    private String prefix = "subject/subject";

    @Autowired
    private ISubSubjectService subSubjectService;

    @RequiresPermissions("subject:subject:view")
    @GetMapping()
    public String subject()
    {
        return prefix + "/subject";
    }

    /**
     * 查询课题列表列表
     */
    @RequiresPermissions("subject:subject:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SubSubject subSubject)
    {
        startPage();
        List<SubSubject> list = subSubjectService.selectSubSubjectList(subSubject);
        return getDataTable(list);
    }

    /**
     * 导出课题列表列表
     */
    @RequiresPermissions("subject:subject:export")
    @Log(title = "课题列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SubSubject subSubject)
    {
        List<SubSubject> list = subSubjectService.selectSubSubjectList(subSubject);
        ExcelUtil<SubSubject> util = new ExcelUtil<SubSubject>(SubSubject.class);
        return util.exportExcel(list, "subject");
    }

    /**
     * 新增课题列表
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存课题列表
     */
    @RequiresPermissions("subject:subject:add")
    @Log(title = "课题列表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SubSubject subSubject)
    {
        return toAjax(subSubjectService.insertSubSubject(subSubject));
    }

    /**
     * 修改课题列表
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SubSubject subSubject = subSubjectService.selectSubSubjectById(id);
        mmap.put("subSubject", subSubject);
        return prefix + "/edit";
    }

    /**
     * 修改保存课题列表
     */
    @RequiresPermissions("subject:subject:edit")
    @Log(title = "课题列表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SubSubject subSubject)
    {
        return toAjax(subSubjectService.updateSubSubject(subSubject));
    }

    /**
     * 删除课题列表
     */
    @RequiresPermissions("subject:subject:remove")
    @Log(title = "课题列表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(subSubjectService.deleteSubSubjectByIds(ids));
    }


}
