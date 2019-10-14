package pl.adamsiedlecki.spring.web.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.spring.config.securityStuff.CommCryptUserDetailsService;

@Component
@Scope("prototype")
public class DeleteUserTab extends VerticalLayout {

    private CommCryptUserDetailsService userDetailsService;

    @Autowired
    public DeleteUserTab(Environment env, CommCryptUserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
        Label titleLabel = new Label(env.getProperty("delete.user"));
        TextField userIdField = new TextField(env.getProperty("id.field"));
        Button deleteButton = new Button(env.getProperty("delete.user.button"));
        deleteButton.setIcon(new Icon(VaadinIcon.CLOSE));
        this.add(titleLabel, userIdField, deleteButton);
        deleteButton.addClickListener(e->{
            if(NumberUtils.isDigits(userIdField.getValue())){
                userDetailsService.deleteUser(Long.parseLong(userIdField.getValue()));
                userIdField.clear();
            }else{
                Notification.show(env.getProperty("value.is.not.numeric"));
            }

        });
        this.setAlignItems(FlexComponent.Alignment.CENTER);
        this.setClassName("form-background");
    }

}
