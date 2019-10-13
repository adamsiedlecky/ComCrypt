package pl.adamsiedlecki.spring.web;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.adamsiedlecki.spring.db.entity.Message;
import pl.adamsiedlecki.spring.db.service.MessageService;
import pl.adamsiedlecki.spring.tool.ResourceGetter;
import pl.adamsiedlecki.spring.tool.cryptography.SymmetricCryptography;

import java.util.Base64;
import java.util.Collection;

@Route("user-panel")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/style.css")
@PageTitle("ComCrypt user panel")
public class UserPanelUI extends VerticalLayout {

    private UserDetailsService userDetailsService;
    private Environment env;
    private MessageService messageService;

    @Autowired
    public UserPanelUI(UserDetailsService userDetailsService, Environment env, MessageService messageService){
        userDetailsService = userDetailsService;
        this.env = env;
        this.messageService = messageService;
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends  GrantedAuthority> authorities = principal.getAuthorities();
        boolean isOwner = false;
        boolean isAdmin = false;
        for(GrantedAuthority a : authorities){
            if(a.getAuthority().contains("OWNER")){
                isOwner = true;
            }
            if(a.getAuthority().contains("ADMIN")){
                isAdmin = true;
            }
        }
        loadPage(isOwner, isAdmin, principal.getUsername());
        this.setAlignItems(Alignment.CENTER);
    }

    private void loadPage(boolean isOwner, boolean isAdmin, String username){
        if(isOwner){
            Notification.show(env.getProperty("welcome.owner.notification"),5000, Notification.Position.TOP_CENTER);
            Notification.show(env.getProperty("welcome.notification")+username,1500, Notification.Position.BOTTOM_START);
            addLogoutButton();
            Image img = ResourceGetter.getUserPanelImage(isOwner);
            img.setClassName("user-panel-image");
            add(img);
            addMessagePanel(isAdmin, isOwner, username);

        }else{
            Notification.show(env.getProperty("welcome.notification")+username,2000, Notification.Position.MIDDLE);
            addLogoutButton();
            add(ResourceGetter.getUserPanelImage(isOwner));
            addMessagePanel(isAdmin, isOwner, username);

        }
    }

    private void addLogoutButton(){
        Button logoutButton = new Button(env.getProperty("logout.button"));
        logoutButton.addClickListener(e-> this.getUI().ifPresent(u->u.getPage().setLocation("/logout")));
        VerticalLayout layout = new VerticalLayout(logoutButton);
        layout.setWidthFull();
        layout.setAlignItems(Alignment.END);
        this.add(layout);

    }

    private void addMessagePanel(boolean isAdmin, boolean isOwner, String username){
        HorizontalLayout rootHorizontal = new HorizontalLayout();

        TextArea messageArea = new TextArea(env.getProperty("message.area"));
        TextField messageIdField = new TextField(env.getProperty("message.id"));
        TextField keyField = new TextField(env.getProperty("key.field"));
        Button sendMessageButton = new Button(env.getProperty("send.message.button"));
        Checkbox includeAuthorCheckbox = new Checkbox(env.getProperty("include.author.checkbox"));
        sendMessageButton.addClickListener(e->{
            if(!messageArea.isEmpty()&&!messageIdField.isEmpty()){
                if(keyField.isEmpty()){
                    Notification.show(env.getProperty("key.not.provided"),3000, Notification.Position.TOP_CENTER);
                }
                String encrypted = Base64.getEncoder().encodeToString(SymmetricCryptography.encrypt(messageArea.getValue(),keyField.getValue()));
                Message m;
                if(includeAuthorCheckbox.getValue()){
                    m = new Message(encrypted,username, messageIdField.getValue());
                }else{
                    m = new Message(encrypted, env.getProperty("anonymous.user"), messageIdField.getValue());
                }
                messageService.save(m);

            }
        });
        VerticalLayout formAddMessageLayout = new VerticalLayout(messageArea, messageIdField, keyField, sendMessageButton );
        if(isAdmin){
            formAddMessageLayout.add(includeAuthorCheckbox);
        }
        formAddMessageLayout.setWidth(messageArea.getWidth());
        formAddMessageLayout.setAlignItems(Alignment.CENTER);
        formAddMessageLayout.addClassName("form-background");

        VerticalLayout showMessageLayout = new VerticalLayout();
        TextField messageIdFieldShow = new TextField(env.getProperty("message.id"));
        PasswordField messageKeyField = new PasswordField(env.getProperty("key.field"));
        Button decryptButton = new Button(env.getProperty("decrypt.button"));
        TextArea decryptedMessageArea = new TextArea(env.getProperty("message.area"));
        showMessageLayout.add(messageIdFieldShow, messageKeyField, decryptedMessageArea);
        showMessageLayout.addClassName("form-background");
        showMessageLayout.setHeight(formAddMessageLayout.getMaxHeight());

        rootHorizontal.add(formAddMessageLayout,showMessageLayout);
        if(isOwner){
            Button controlPanelButton = new Button(env.getProperty("control.panel.button"));
            controlPanelButton.addClickListener(e->this.getUI().ifPresent(u->u.navigate("control-panel")));
            VerticalLayout controlPanelLayout = new VerticalLayout(controlPanelButton);
            controlPanelLayout.addClassName("form-background");
            rootHorizontal.add(controlPanelLayout);
        }
        rootHorizontal.setAlignItems(Alignment.CENTER);
        this.add(rootHorizontal);

    }

}
