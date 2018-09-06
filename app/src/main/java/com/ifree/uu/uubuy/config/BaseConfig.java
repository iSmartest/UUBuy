package com.ifree.uu.uubuy.config;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Created by 小火
 * Create time on  2017/3/22
 * My mailbox is 1403241630@qq.com
 * <p>
 * Entity mapped to table "BASE_CONFIG".
 */
public class BaseConfig {

    private Long id;
    private String name;
    private String content;
    private Long updateTime;
    private Long createTime;
    private Long status;

    public BaseConfig() {
    }

    public BaseConfig(Long id) {
        this.id = id;
    }

    public BaseConfig(Long id, String name, String content, Long updateTime, Long createTime, Long status) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

}
