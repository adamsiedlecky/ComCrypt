package pl.adamsiedlecki.spring.tool;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.adamsiedlecki.spring.config.securityStuff.CommCryptUser;
import pl.adamsiedlecki.spring.config.securityStuff.CommCryptUserDetailsService;
import pl.adamsiedlecki.spring.config.securityStuff.UserRole;

@Route("user-panel")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/style.css")
@PageTitle("ComCrypt user panel")
public class UserPanelUI extends VerticalLayout {

    private UserDetailsService userDetailsService;

    public UserPanelUI(UserDetailsService userDetailsService, Environment env){
        userDetailsService = userDetailsService;
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal.getAuthorities().contains(new UserRole("OWNER"))){
            Notification.show(env.getProperty("welcome.notification")+principal.getUsername(),1000, Notification.Position.TOP_CENTER);

        }
        Notification.show(env.getProperty("welcome.notification")+principal.getUsername(),1000, Notification.Position.MIDDLE);
    }

}
