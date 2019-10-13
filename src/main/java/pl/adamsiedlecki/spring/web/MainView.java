package pl.adamsiedlecki.spring.web;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoThemeDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.spring.tool.ResourceGetter;

import static java.lang.System.setProperty;

@Route("")
@PWA(name = "Project for secure communication", shortName = "ComCrypt")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/style.css")
@PageTitle("ComCrypt")
public class MainView extends VerticalLayout {

    @Autowired
    public MainView(Environment env) {
        Notification.show(env.getProperty("main.welcome")).setPosition(Notification.Position.BOTTOM_CENTER);
        add(ResourceGetter.getComCryptLogo());


        Button checkMessageButton = new Button(env.getProperty("check.message.button"));
        checkMessageButton.addClickListener(e->{
            this.getUI().ifPresent(u->u.navigate("show-message"));
        });
        checkMessageButton.setIcon(new Icon(VaadinIcon.ROCKET));
        add(checkMessageButton);

        Button loginButton = new Button(env.getProperty("login.button"));
        add(loginButton);
        loginButton.setIcon(new Icon(VaadinIcon.CROSSHAIRS));
        loginButton.setId("login-button");

        loginButton.addClickListener(e->{
            this.getUI().ifPresent(u->u.getPage().setLocation("/user-panel"));
        });

        setAlignItems(Alignment.CENTER);
    }

}
