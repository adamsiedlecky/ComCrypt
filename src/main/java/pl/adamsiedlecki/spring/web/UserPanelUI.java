package pl.adamsiedlecki.spring.web;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.adamsiedlecki.spring.config.securityStuff.CommCryptUser;
import pl.adamsiedlecki.spring.config.securityStuff.CommCryptUserDetailsService;
import pl.adamsiedlecki.spring.config.securityStuff.UserRole;
import pl.adamsiedlecki.spring.tool.ResourceGetter;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Route("user-panel")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/style.css")
@PageTitle("ComCrypt user panel")
public class UserPanelUI extends VerticalLayout {

    private UserDetailsService userDetailsService;
    private Environment env;

    public UserPanelUI(UserDetailsService userDetailsService, Environment env){
        userDetailsService = userDetailsService;
        this.env = env;
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends  GrantedAuthority> authorities = principal.getAuthorities();
        boolean isOwner = false;
        for(GrantedAuthority a : authorities){
            if(a.getAuthority().contains("OWNER")){
                isOwner = true;
            }
        }
        loadPage(isOwner, principal.getUsername());
        this.setAlignItems(Alignment.CENTER);
    }

    private void loadPage(boolean isOwner, String username){
        if(isOwner){
            Notification.show(env.getProperty("welcome.owner.notification"),5000, Notification.Position.TOP_CENTER);
            Notification.show(env.getProperty("welcome.notification")+username,1500, Notification.Position.MIDDLE);
            Image img = ResourceGetter.getUserPanelImage(isOwner);
            img.setClassName("user-panel-image");
            add(img);
        }else{
            Notification.show(env.getProperty("welcome.notification")+username,2000, Notification.Position.MIDDLE);
            add(ResourceGetter.getUserPanelImage(isOwner));
        }
    }

}
