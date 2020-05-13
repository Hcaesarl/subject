package com.ruoyi.activiti.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 课题申请对象 biz_subject
 * 
 * @author ruoyi
 * @date 2020-05-13
 */
public class BizSubject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 第一课题 */
    @Excel(name = "第一课题")
    private String firstsubject;

    /** 第一课题老师 */
    @Excel(name = "第一课题老师")
    private String firstteacher;

    /** 第一意向老师用户名 */
    @Excel(name = "第一意向老师用户名")
    private String firstteacherusername;

    /** 第二课题 */
    @Excel(name = "第二课题")
    private String secondsubject;

    /** 第二课题老师 */
    @Excel(name = "第二课题老师")
    private String secondteacher;

    /** 第二课题老师用户名 */
    @Excel(name = "第二课题老师用户名")
    private String secondteacherusername;

    /** 自主提交课题 */
    @Excel(name = "自主提交课题")
    private String thirdsubject;

    /** 自主课题教师 */
    @Excel(name = "自主课题教师")
    private String thirdteacher;

    /** 自主课题教师用户名 */
    @Excel(name = "自主课题教师用户名")
    private String thirdteacherusername;

    /** 流程实例ID */
    @Excel(name = "流程实例ID")
    private String instanceId;

    /** 申请人 */
    @Excel(name = "申请人")
    private String applyUser;

    /** 申请时间 */
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /** 实际开始时间 */
    @Excel(name = "实际开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date realityStartTime;

    /** 实际结束时间 */
    @Excel(name = "实际结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date realityEndTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFirstsubject(String firstsubject) 
    {
        this.firstsubject = firstsubject;
    }

    public String getFirstsubject() 
    {
        return firstsubject;
    }
    public void setFirstteacher(String firstteacher) 
    {
        this.firstteacher = firstteacher;
    }

    public String getFirstteacher() 
    {
        return firstteacher;
    }
    public void setFirstteacherusername(String firstteacherusername) 
    {
        this.firstteacherusername = firstteacherusername;
    }

    public String getFirstteacherusername() 
    {
        return firstteacherusername;
    }
    public void setSecondsubject(String secondsubject) 
    {
        this.secondsubject = secondsubject;
    }

    public String getSecondsubject() 
    {
        return secondsubject;
    }
    public void setSecondteacher(String secondteacher) 
    {
        this.secondteacher = secondteacher;
    }

    public String getSecondteacher() 
    {
        return secondteacher;
    }
    public void setSecondteacherusername(String secondteacherusername) 
    {
        this.secondteacherusername = secondteacherusername;
    }

    public String getSecondteacherusername() 
    {
        return secondteacherusername;
    }
    public void setThirdsubject(String thirdsubject) 
    {
        this.thirdsubject = thirdsubject;
    }

    public String getThirdsubject() 
    {
        return thirdsubject;
    }
    public void setThirdteacher(String thirdteacher) 
    {
        this.thirdteacher = thirdteacher;
    }

    public String getThirdteacher() 
    {
        return thirdteacher;
    }
    public void setThirdteacherusername(String thirdteacherusername) 
    {
        this.thirdteacherusername = thirdteacherusername;
    }

    public String getThirdteacherusername() 
    {
        return thirdteacherusername;
    }
    public void setInstanceId(String instanceId) 
    {
        this.instanceId = instanceId;
    }

    public String getInstanceId() 
    {
        return instanceId;
    }
    public void setApplyUser(String applyUser) 
    {
        this.applyUser = applyUser;
    }

    public String getApplyUser() 
    {
        return applyUser;
    }
    public void setApplyTime(Date applyTime) 
    {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() 
    {
        return applyTime;
    }
    public void setRealityStartTime(Date realityStartTime) 
    {
        this.realityStartTime = realityStartTime;
    }

    public Date getRealityStartTime() 
    {
        return realityStartTime;
    }
    public void setRealityEndTime(Date realityEndTime) 
    {
        this.realityEndTime = realityEndTime;
    }

    public Date getRealityEndTime() 
    {
        return realityEndTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("firstsubject", getFirstsubject())
            .append("firstteacher", getFirstteacher())
            .append("firstteacherusername", getFirstteacherusername())
            .append("secondsubject", getSecondsubject())
            .append("secondteacher", getSecondteacher())
            .append("secondteacherusername", getSecondteacherusername())
            .append("thirdsubject", getThirdsubject())
            .append("thirdteacher", getThirdteacher())
            .append("thirdteacherusername", getThirdteacherusername())
            .append("instanceId", getInstanceId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("applyUser", getApplyUser())
            .append("applyTime", getApplyTime())
            .append("realityStartTime", getRealityStartTime())
            .append("realityEndTime", getRealityEndTime())
            .toString();
    }
}
