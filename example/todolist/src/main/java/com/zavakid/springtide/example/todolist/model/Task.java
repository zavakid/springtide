package com.zavakid.springtide.example.todolist.model;

import com.zavakid.springtide.jpa.GenericModel;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zebin.xuzb 2014-09-27
 */
@Entity
@Table(name = "Task")
public class Task extends GenericModel {

    private String title;
    private String description;

    private Boolean finished;
    private Date gmtFinished;

    @Column(insertable = false, updatable = false, name = "userId")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User owner;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Date getGmtFinished() {
        return gmtFinished;
    }

    public void setGmtFinished(Date gmtFinished) {
        this.gmtFinished = gmtFinished;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
