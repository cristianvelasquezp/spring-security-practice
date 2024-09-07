package org.cristianvelasquezp.springsecuritypractice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name="users")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @NotEmpty(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    @Column(unique = true)
    private String email;
    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String role;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<AuthorityEntity> authorities;

    @PrePersist
    public void prePersist() {
        if (this.role == null) {
            this.role = "USER";
        }
    }
}
