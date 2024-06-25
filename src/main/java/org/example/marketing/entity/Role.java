package org.example.marketing.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.marketing.entity.enums.RoleName;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Enumerated(EnumType.STRING)
    private RoleName role;

    public Role(RoleName role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role.toString();
    }
}
