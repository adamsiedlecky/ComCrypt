package pl.adamsiedlecki.spring.tool;

import com.vaadin.flow.component.html.Image;
import com.google.common.io.Resources;
import org.apache.catalina.webresources.FileResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.net.URL;

public class ResourceGetter {

    private static final String comCryptLogoPath = "comcryptLogo.png";

    private static final Logger log = LoggerFactory.getLogger(ResourceGetter.class);

    public static Image getComCryptLogo(){
        File image = getFileByName(comCryptLogoPath);
        if(!image.exists()){
            log.error("LOGO DOESN'T EXIST");
        }
        log.info("LOGO EXISTS");
        return new Image(image.getAbsolutePath(),"ComCrypt");
    }

    private static File getFileByName(String fileNameWithExtension) {
        File logo = null;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            logo = new File("src\\main\\resources\\images\\" + fileNameWithExtension);
            if (!logo.exists()) {
                logo = new File("src/main/resources/images/" + fileNameWithExtension);
            }
            if (!logo.exists()) {
                URL url2 = Resources.getResource("images/" + fileNameWithExtension);
                logo = new File(url2.getFile());
            }
            if (!logo.exists()) {
                logo = new File("src/main/resources/images/" + fileNameWithExtension);
            }
            if (!logo.exists()) {
                logo = new File(fileNameWithExtension);
            }
            if (!logo.exists()) {
                URL url = Resources.getResource("/images/" + fileNameWithExtension);
                logo = new File(url.getFile());
            }
            if (!logo.exists()) {
                log.error("LOGO NOT FOUND");
            }
        } else if (osName.contains("nux")) {
            logo = new File(fileNameWithExtension);
            if (!logo.exists()) {
                logo = new File("src/main/resources/images/" + fileNameWithExtension);
            }
            if (!logo.exists()) {
                URL url2 = Resources.getResource("images/" + fileNameWithExtension);
                logo = new File(url2.getFile());
            }
            if (!logo.exists()) {
                logo = new File("src/main/resources/images/" + fileNameWithExtension);
            }
            if (!logo.exists()) {
                logo = new File(fileNameWithExtension);
            }
            if (!logo.exists()) {
                URL url = Resources.getResource("/images/" + fileNameWithExtension);
                logo = new File(url.getFile());
            }
            if (!logo.exists()) {
                log.error("LOGO NOT FOUND");
            }
        }
        return logo;
    }

}
