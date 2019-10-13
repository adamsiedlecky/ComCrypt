package pl.adamsiedlecki.spring.web.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.spring.config.securityStuff.CommCryptUserDetailsService;

@Component
@Scope("prototype")
public class ChangeMyPasswordTab extends VerticalLayout {

    @Autowired
    public ChangeMyPasswordTab(Environment env, CommCryptUserDetailsService userDetailsService){

        VerticalLayout verticalLayout = new VerticalLayout();
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Label label = new Label(env.getProperty("actual.logged.username") + username);
        PasswordField passwordField = new PasswordField(env.getProperty("password.field"));
        Button saveButton = new Button(env.getProperty("save.button"));
        verticalLayout.add(label, passwordField, saveButton);
        this.add(verticalLayout);
        this.setClassName("form-background");
        saveButton.addClickListener(e->{
            userDetailsService.changePassword(username, passwordField.getValue());
            passwordField.clear();
        });
    }
}
