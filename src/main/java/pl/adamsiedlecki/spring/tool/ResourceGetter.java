package pl.adamsiedlecki.spring.tool;

import com.vaadin.flow.component.html.Image;

public class ResourceGetter {

    public static Image getComCryptLogo(){
        Image img = new Image("images/comcryptLogo.png","Vaadin Logo");
        img.setWidth("300px");
        img.setHeight("150px");
        return img;
    }

    public static Image getUserPanelImage(boolean isOwner){
        if(isOwner){
            Image img = new Image("images/userPanelOwner.png","user panel image - owner");
            img.setWidth("300px");
            img.setHeight("150px");
            return img;
        }else{
            Image img = new Image("images/userPanel.png","user panel image");
            img.setWidth("300px");
            img.setHeight("150px");
            return img;
        }
    }

}
