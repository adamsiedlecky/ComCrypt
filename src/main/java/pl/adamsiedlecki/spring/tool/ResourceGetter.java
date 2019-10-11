package pl.adamsiedlecki.spring.tool;

import com.vaadin.flow.component.html.Image;

public class ResourceGetter {

    public static Image getComCryptLogo(){
        Image img = new Image("images/comcryptLogo.png","Vaadin Logo");
        img.setWidth("300px");
        img.setHeight("150px");
        return img;
    }

}
