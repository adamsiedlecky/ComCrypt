package pl.adamsiedlecki.spring;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoThemeDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.spring.tool.ResourceGetter;

@Route("")
@PWA(name = "Project for secure communication", shortName = "ComCrypt")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/style.css")
public class MainView extends VerticalLayout {

    @Autowired
    public MainView(Environment env) {
        Notification.show("Welcome to the S. Technologies Communication System").setPosition(Notification.Position.BOTTOM_CENTER);
        add(ResourceGetter.getComCryptLogo());

        TextField messageIdField = new TextField();
        messageIdField.setLabel(env.getProperty("id.message.field"));
        Button checkMessageButton = new Button(env.getProperty("check.message.button"));
        checkMessageButton.setIcon(new Icon(VaadinIcon.ROCKET));
        checkMessageButton.setId("horizontalButton");
        add(new HorizontalLayout(messageIdField, checkMessageButton));

        Button loginButton = new Button(env.getProperty("login.button"));
        add(loginButton);
        loginButton.setIcon(new Icon(VaadinIcon.CROSSHAIRS));
        loginButton.setId("login-button");
        setAlignItems(Alignment.CENTER);
    }

}
