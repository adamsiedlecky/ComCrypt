package pl.adamsiedlecki.spring.web;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.spring.db.entity.Message;
import pl.adamsiedlecki.spring.db.service.MessageService;
import pl.adamsiedlecki.spring.tool.cryptography.SymmetricCryptography;

import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Objects;

@Route("show-message")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/style.css")
@PageTitle("ComCrypt")
public class PublicShowMessageUI extends VerticalLayout {

    @Autowired
    public PublicShowMessageUI(Environment env, MessageService messageService){
        TextField messageIdField = new TextField();
        messageIdField.setLabel(env.getProperty("id.message.field"));
        PasswordField keyField = new PasswordField(env.getProperty("key.field"));
        Button showMessageButton = new Button(env.getProperty("decrypt.button"));
        Label messageLabel = new Label();
        showMessageButton.addClickListener(e->{
            Message m = messageService.getByUserProvidedId(messageIdField.getValue());
            if(m!=null){
                String decrypted = SymmetricCryptography.decrypt(Base64.getDecoder().decode(m.getContent()),keyField.getValue());
                messageLabel.setText(decrypted+" | "+m.getAuthor()+" | "+m.getCreationTimePlainString());
            }else{
                messageLabel.setText(Objects.requireNonNull(env.getProperty("message.not.found")));
            }
        });
        add(messageIdField, keyField, showMessageButton, messageLabel);
        this.setAlignItems(Alignment.CENTER);
    }

}
