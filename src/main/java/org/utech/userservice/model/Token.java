package org.utech.userservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class Token extends BaseModel {
    private String value;
    private boolean deleted;
    @ManyToOne
    private User user;
    private Date expiryAt;
}