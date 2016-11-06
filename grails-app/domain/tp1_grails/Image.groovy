package tp1_grails

import grails.converters.JSON
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest
import utils.AppConfig

import javax.servlet.http.Part
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


class Image {

    String nomFichier
    static constraints = {
        //Register Review domain for JSON rendering
        JSON.registerObjectMarshaller(Image) {
            def output = [:]
            output['id'] = it.id
            output['url'] = "data:image/png;base64," + new File(AppConfig.saveImageFolderString+ File.separator + it.nomFichier).getBytes().encodeBase64().toString()
            return output;
        }

    }

    protected static saveImageFromUpload(def f) {
        if (f && !f.empty) {
            println(f.contentType)
            println(f.size)
            String extension = "";
            int i = f.getOriginalFilename().lastIndexOf('.');
            if (i > 0) {
                extension = f.getOriginalFilename().substring(i);
            }

            String nameNewFile = UUID.randomUUID().toString() + extension;
            String urlNewFile = AppConfig.saveImageFolderString+ File.separator + nameNewFile
            File fileDest = new File(urlNewFile)
            f.transferTo(fileDest)

            Image image = new Image(nomFichier: nameNewFile)
            if (!image.save(flush: true)) {
                image.errors.allErrors.each {
                    println it
                    return null;
                }
            }
            return image
        }
        return null
    }

    protected static Image saveImageFromUpload(Part f) {
        if (!f || f.size == 0) {
            return null
        } else {
            println(f.contentType)
            println(f.size)
            String extension = "";
            int i = f.getSubmittedFileName().lastIndexOf('.');
            if (i > 0) {
                extension = f.getSubmittedFileName().substring(i);
            }
            String nameNewFile = UUID.randomUUID().toString() + extension;
            String urlNewFile = AppConfig.saveImageFolderString + File.separator+ nameNewFile


            Files.copy(f.getInputStream(), Paths.get(urlNewFile), StandardCopyOption.REPLACE_EXISTING)

            //   f.transferTo(file);
            Image image = new Image(nomFichier: nameNewFile)
            if (!image.save(flush: true)) {
                image.errors.allErrors.each {
                    println it
                    return null;
                }
            }
            return image


        }
    }
}
