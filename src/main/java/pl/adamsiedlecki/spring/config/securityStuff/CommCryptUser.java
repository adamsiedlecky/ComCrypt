package pl.adamsiedlecki.spring.config.securityStuff;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class CommCryptUser{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserRole> roles;

    public CommCryptUser(String username, String password, List<UserRole> roles) {
        this.username = username;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.roles = roles;
    }

    public CommCryptUser() {
    }

    public Long getId(){
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public List<String> getRolesPlainString(){
        List<String> list = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities = getRoles();
        for (GrantedAuthority auth: authorities){
            list.add(auth.getAuthority());
        }
        return list;
    }
}
