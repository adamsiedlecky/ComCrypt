package pl.adamsiedlecki.spring.web;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
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
import pl.adamsiedlecki.spring.tool.ResourceGetter;
import java.util.Collection;

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
            Button controlPanelButton = new Button(env.getProperty("control.panel.button"));
            controlPanelButton.addClickListener(e->this.getUI().ifPresent(u->u.navigate("control-panel")));
            add(controlPanelButton);
            addMessagePanel();
            addLogoutButton();
        }else{
            Notification.show(env.getProperty("welcome.notification")+username,2000, Notification.Position.MIDDLE);
            add(ResourceGetter.getUserPanelImage(isOwner));
            addMessagePanel();
            addLogoutButton();
        }
    }

    private void addLogoutButton(){
        Button logoutButton = new Button(env.getProperty("logout.button"));
        logoutButton.addClickListener(e-> this.getUI().ifPresent(u->u.getPage().setLocation("/user-panel")));
        this.add(logoutButton);
    }

    private void addMessagePanel(){
        TextArea messageArea = new TextArea(env.getProperty("message.area"));
        TextField messageIdField = new TextField(env.getProperty("message.id"));
        TextField keyField = new TextField(env.getProperty("key.field"));
        //HorizontalLayout horizontalLayout = new HorizontalLayout(messageArea, messageIdField, keyField);
        Button sendMessageButton = new Button(env.getProperty("send.message.button"));
        VerticalLayout formLayout = new VerticalLayout(messageArea, messageIdField, keyField, sendMessageButton );
        formLayout.setWidth(messageArea.getWidth());
        formLayout.setAlignItems(Alignment.CENTER);
        formLayout.addClassName("form-background");
        this.add(formLayout);
        this.setAlignItems(Alignment.CENTER);
    }

}
