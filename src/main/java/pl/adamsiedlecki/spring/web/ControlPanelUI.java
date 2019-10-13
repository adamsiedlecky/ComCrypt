package pl.adamsiedlecki.spring.web;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@Route("control-panel")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/style.css")
@PageTitle("ComCrypt control panel")
public class ControlPanelUI {

    @Autowired
    public ControlPanelUI(Environment env){
        Notification.show(env.getProperty("welcome.control.panel"),2000, Notification.Position.BOTTOM_CENTER );
    }

}
