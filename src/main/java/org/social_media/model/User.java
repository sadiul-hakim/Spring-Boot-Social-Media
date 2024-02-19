package org.social_media.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 45)
    private String firstName = "";

    @Column(length = 45)
    private String lastName = "";

    @Column(length = 60)
    private String email = "";

    @Column(length = 120)
    private String password = "";

    private String gender = "";

    @Column(columnDefinition = "JSON")
    @Convert(converter = StringListConverter.class)
    private List<String> authorities = new ArrayList<>();

    @Column(columnDefinition = "JSON")
    @Convert(converter = NumberListConverter.class)
    private List<Long> followers = new ArrayList<>();

    @Column(columnDefinition = "JSON")
    @Convert(converter = NumberListConverter.class)
    private List<Long> following = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp startDate = new Timestamp(System.currentTimeMillis());

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

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
}
