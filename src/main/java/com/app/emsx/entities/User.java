package com.app.emsx.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * User Entity
 * -----------------------------------------------------
 * ✔ Representa un usuario del sistema EMS
 * ✔ Implementa UserDetails para integración con Spring Security
 * ✔ Incluye rol (ROLE_ADMIN o ROLE_USER)
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    /**
     * Rol del usuario (ejemplo: ROLE_ADMIN o ROLE_USER)
     */
    @Column(nullable = false)
    private String role = "ROLE_USER";

    /**
     * ✅ Devuelve la lista de roles del usuario
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Devuelve siempre una autoridad, para evitar problemas si el campo está vacío
        return List.of(new SimpleGrantedAuthority(role != null ? role : "ROLE_USER"));
    }

    /**
     * ✅ El username para Spring Security será el email
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * ✅ Indica si la cuenta está activa
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Normaliza el email a minúsculas antes de guardar/actualizar.
     */
    @PrePersist
    @PreUpdate
    private void normalizeEmail() {
        if (email != null) {
            email = email.toLowerCase();
        }
    }
}
