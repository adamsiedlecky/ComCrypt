package pl.adamsiedlecki.spring.web.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.spring.config.securityStuff.CommCryptUser;
import pl.adamsiedlecki.spring.config.securityStuff.CommCryptUserDetailsService;

@Component
@Scope("prototype")
public class AllUsersTab extends VerticalLayout {

    private CommCryptUserDetailsService userDetailsService;
    private Grid<CommCryptUser> usersGrid;

    public AllUsersTab(CommCryptUserDetailsService userDetailsService, Environment env){
        VerticalLayout verticalLayout = new VerticalLayout();
        this.userDetailsService = userDetailsService;
        Button refreshButton = new Button(env.getProperty("refresh.button"));
        refreshButton.addClickListener(e->refresh());
        usersGrid = new Grid<>();
        usersGrid.setWidthFull();
        usersGrid.addColumn(CommCryptUser::getId).setHeader(env.getProperty("id.column"));
        usersGrid.addColumn(CommCryptUser::getUsername).setHeader(env.getProperty("username.field"));
        usersGrid.addColumn(CommCryptUser::getRolesPlainString).setHeader(env.getProperty("roles"));
        verticalLayout.add(refreshButton, usersGrid);
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        verticalLayout.setWidthFull();
        verticalLayout.setClassName("little-top-margin");
        this.add(verticalLayout);
        this.setClassName("little-top-margin");
        refresh();
        this.setHeight("270px");
        usersGrid.setHeight("150px");
    }

    private void refresh(){
        usersGrid.setItems(userDetailsService.findAll());
    }

}
