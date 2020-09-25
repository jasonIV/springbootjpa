package com.jason.springbootjpa.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -380564813881107013L;

    @LastModifiedDate
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update_date", nullable = false)
    private Date lastUpdateDate;

    @LastModifiedBy
    @Column(name = "last_update_by")
    private String lastUpdateBy;

    @CreatedDate
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

}