package com.afagoal.hamster.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * Created by BaoCai on 17/10/25. Description:
 */
@Getter
@Setter
@MappedSuperclass
public class UuidEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    private String id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdBy;

    private String updatedBy;

    private byte state;

    @Version
    private Byte version;

    @PrePersist
    public void prePersist() {
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate() {
        this.setUpdatedAt(LocalDateTime.now());
    }

}
