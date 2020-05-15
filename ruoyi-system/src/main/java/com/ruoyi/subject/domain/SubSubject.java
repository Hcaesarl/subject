package com.ruoyi.subject.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 课题列表对象 sub_subject
 * 
 * @author ruoyi
 * @date 2020-05-14
 */
public class SubSubject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 创建者姓名 */
    @Excel(name = "创建者姓名")
    private String createByName;

    /** 更新者姓名 */
    @Excel(name = "更新者姓名")
    private String updateByName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setCreateByName(String createByName) 
    {
        this.createByName = createByName;
    }

    public String getCreateByName() 
    {
        return createByName;
    }
    public void setUpdateByName(String updateByName) 
    {
        this.updateByName = updateByName;
    }

    public String getUpdateByName() 
    {
        return updateByName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("content", getContent())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("createByName", getCreateByName())
            .append("updateByName", getUpdateByName())
            .toString();
    }
}
