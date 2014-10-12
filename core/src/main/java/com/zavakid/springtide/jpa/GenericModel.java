package com.zavakid.springtide.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 此类可作为 基于JPA的model 基类
 * <p/>
 * 改类定义了 id, gmtModify, gmtCreate
 * <p/>
 * 其中定义了几个回调方法:
 * <ul>
 * <li>在保存时, gmtCreate 和 gmtModify 会自动产生</li>
 * <li>在更新时, gmtModify 会自动更新</li>
 * </ul>
 *
 * @author zebin.xuzb 2014-09-27
 */
@MappedSuperclass
public class GenericModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date gmtModify;

    @Temporal(TemporalType.DATE)
    @Column(updatable = false)
    private Date gmtCreate;

    @PreUpdate
    public void beforeUpdate() {
        this.gmtModify = new Date();
    }

    @PrePersist
    public void beforePersist() {
        Date now = new Date();
        this.gmtCreate = now;
        this.gmtModify = now;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
