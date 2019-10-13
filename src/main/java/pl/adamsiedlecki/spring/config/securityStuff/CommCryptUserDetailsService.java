package pl.adamsiedlecki.spring.config.securityStuff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommCryptUserDetailsService implements UserDetailsService {

    private UserDAO userDAO;
    private static final Logger log = LoggerFactory.getLogger(CommCryptUser.class);

    public void changePassword(String username, String newPassword){
        Optional<CommCryptUser> user = userDAO.getByUsername(username);
        if(user.isPresent()){
            user.get().setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userDAO.saveAndFlush(user.get());
        }
    }

    @Autowired
    public CommCryptUserDetailsService(UserDAO userDAO){
        this.userDAO = userDAO;

        try{
            UserDetails details = loadUserByUsername("adam-s");
            log.info("Account for OWNER is already created.");
        }catch (UsernameNotFoundException e){
            addUser(new CommCryptUser("adam-s","pass", List.of(new UserRole("OWNER"),
                    new UserRole("ADMIN"),new UserRole("USER"))));
            log.info("Creating new account for OWNER");
        }
        try{
            UserDetails details = loadUserByUsername("test-user");
        }catch (UsernameNotFoundException e){
            addUser(new CommCryptUser("test-user","pass", List.of(new UserRole("USER"))));
        }
    }

    public void addUser(CommCryptUser user){
        userDAO.save(user);
    }

    public List<CommCryptUser> findAll(){
        return userDAO.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CommCryptUser> user = userDAO.getByUsername(username);
        if(user.isPresent()){
            return new MyUserPrincipal(userDAO.getByUsername(username).get());
        }else{
            throw new UsernameNotFoundException(username);
        }


    }
}
