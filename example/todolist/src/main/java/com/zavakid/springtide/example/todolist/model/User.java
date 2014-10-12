package com.zavakid.springtide.example.todolist.model;

import com.zavakid.springtide.jpa.GenericModel;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author zebin.xuzb 2014-09-27
 */
@Entity
@Table(name="User")
public class User extends GenericModel {

    private String name;
    private String email;
    private String avatarUrl;
    private String encodePasswd;
    private Date gmtRegister;

    @OneToMany(mappedBy = "owner"
            , fetch = FetchType.LAZY
    )
    private List<Task> taskEntities;

    public List<Task> getTaskEntities() {
        return taskEntities;
    }

    public void setTaskEntities(List<Task> taskEntities) {
        this.taskEntities = taskEntities;
    }

    public Date getGmtRegister() {
        return gmtRegister;
    }

    public void setGmtRegister(Date gmtRegister) {
        this.gmtRegister = gmtRegister;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEncodePasswd() {
        return encodePasswd;
    }

    public void setEncodePasswd(String encodePasswd) {
        this.encodePasswd = encodePasswd;
    }
}
