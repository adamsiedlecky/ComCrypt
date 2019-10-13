package pl.adamsiedlecki.spring.web;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.spring.web.tabs.AllUsersTab;

@Route("control-panel")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/style.css")
@PageTitle("ComCrypt control panel")
public class ControlPanelUI extends VerticalLayout {

    @Autowired
    public ControlPanelUI(Environment env, AllUsersTab allUsersTab){
        Notification.show(env.getProperty("welcome.control.panel"),2000, Notification.Position.BOTTOM_CENTER );
        Tabs tabs = new Tabs();
        tabs.setWidthFull();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.add(allUsersTab);
        this.add(tabs);
    }

}
