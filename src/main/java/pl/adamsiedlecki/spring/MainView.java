package pl.adamsiedlecki.spring;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinServletService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@Route("")
@PWA(name = "Project for secure communication", shortName = "ComCrypt")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/style.css")
public class MainView extends VerticalLayout {

    @Autowired
    public MainView(Environment env) {
        Notification.show("Welcome to the S. Technologies Communication System").setPosition(Notification.Position.BOTTOM_CENTER);
        Image img2 = new Image("images/comcryptLogo.png","Vaadin Logo");
        add(img2);
        setAlignItems(Alignment.CENTER);
    }

}
