package org.utech.userservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel {
    private String name;
    private String email;
    private String hashedPassword;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    private boolean isEmailVerified;

    @Builder
    public User(Long Id,String name, String email, String hashedPassword, List<Role> roles, boolean isEmailVerified) {
        super(Id);
        this.name = name;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.roles = roles;
        this.isEmailVerified = isEmailVerified;
    }

    public User() {
        super();
    }
}
