package com.jason.springbootjpa.entity;

import com.jason.springbootjpa.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@FilterDef(
        name = "statusFilter",
        parameters = {@ParamDef(
                name = "status",
                type = "string"
        )}
)
@Filter(
        name = "statusFilter",
        condition = "status = :status"
)
@Getter
@Setter
@AllArgsConstructor
public class StatusEntity extends BaseEntity {
        private static final long serialVersionUID = 3854755242453578186L;

        @Column(name = "status")
        @Enumerated(EnumType.STRING)
        private Status status;

        public StatusEntity() { this.status = Status.A; }
}

