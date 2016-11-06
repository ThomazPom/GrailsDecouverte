package utils

import grails.boot.config.GrailsAutoConfiguration
import org.grails.web.servlet.DefaultGrailsApplicationAttributes
import tp1_grails.Role
import tp1_grails.UserRole

/**
 * Created by Thoma on 27/10/2016.
 */
class AppConfig {



    private static GrailsAutoConfiguration grailsAttributes;
    public static String saveImageFolderString = "C:\\wamp64\\www\\GailsDecouverte\\images\\upload\\" ;
    public static File saveImageFolder;
    public static Role roleAdmin=null;
    public static Role roleModo=null;
    public static Role roleUser=null;

}
