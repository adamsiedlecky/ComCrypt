package pl.adamsiedlecki.spring.web.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.spring.config.securityStuff.CommCryptUser;
import pl.adamsiedlecki.spring.config.securityStuff.CommCryptUserDetailsService;
import pl.adamsiedlecki.spring.config.securityStuff.UserRole;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class AddUserTab extends VerticalLayout {

    private CommCryptUserDetailsService userDetailsService;

    @Autowired
    public AddUserTab(CommCryptUserDetailsService userDetailsService, Environment env){
        this.userDetailsService = userDetailsService;
        VerticalLayout root = new VerticalLayout();
        TextField usernameTextField = new TextField(env.getProperty("username.field"));
        PasswordField passwordField = new PasswordField(env.getProperty("password.field"));
        Checkbox isAdmin = new Checkbox("ADMIN");
        Checkbox isOwner = new Checkbox("OWNER");
        Button saveButton = new Button(env.getProperty("save.button"));
        root.add(new Label(env.getProperty("add.user.tab")),usernameTextField, passwordField, isAdmin, isOwner, saveButton);

        saveButton.addClickListener(e->{
            if(!usernameTextField.isEmpty()&&!passwordField.isEmpty()){
                List<UserRole> roleList = new ArrayList<>();
                roleList.add(new UserRole("USER"));
                if(isAdmin.getValue()){
                    roleList.add(new UserRole("ADMIN"));
                }
                if(isOwner.getValue()){
                    roleList.add(new UserRole("OWNER"));
                }
                userDetailsService.addUser(new CommCryptUser(usernameTextField.getValue(),
                        passwordField.getValue(),roleList));
                usernameTextField.clear();
                passwordField.clear();
                isAdmin.clear();
                isOwner.clear();
            }
            else{
                Notification.show(env.getProperty("empty.fields.notification"));
            }
        });

        add(root);
        root.setAlignItems(FlexComponent.Alignment.CENTER);
        setClassName("form-background");
    }

}
