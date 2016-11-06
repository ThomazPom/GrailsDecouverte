import org.grails.web.servlet.DefaultGrailsApplicationAttributes
import tp1_grails.Groupe
import tp1_grails.Image
import tp1_grails.POI
import tp1_grails.Role
import tp1_grails.User
import tp1_grails.UserRole
import utils.AppConfig

class BootStrap {

    def init = { servletContext ->
        Role ar = AppConfig.roleAdmin = new Role(authority: "ROLE_ADMIN", type: "Administrateur").save(flush: true)
        Role mr = AppConfig.roleModo = new Role(authority: "ROLE_MODO", type: "Moderateur").save(flush: true)
        Role ur = AppConfig.roleUser = new Role(authority: "ROLE_USER", type: "Utilisateur").save(flush: true)

        User ua1 = new User(username: "admin", password: "admin").save(flush: true)

        User um1 = new User(username: "John", password: "john").save(flush: true)
        User um2 = new User(username: "Patrick", password: "patrick").save(flush: true)
        User um3 = new User(username: "Brian", password: "brian").save(flush: true)

        User uu1 = new User(username: "Emily", password: "emily").save(flush: true)
        User uu2 = new User(username: "Vanessa", password: "vanessa").save(flush: true)
        User uu3 = new User(username: "Jessica", password: "jessica").save(flush: true)
        User uu4 = new User(username: "Alexandra", password: "alexandra").save(flush: true)
        User uu5 = new User(username: "Patricia", password: "patricia").save(flush: true)
        User uu6 = new User(username: "Cecilia", password: "cecilia").save(flush: true)



        UserRole.create(ua1, ar)
        UserRole.create(um1, mr)
        UserRole.create(um2, mr)
        UserRole.create(um3, mr)
        UserRole.create(uu1, ur)
        UserRole.create(uu2, ur)
        UserRole.create(uu3, ur)
        UserRole.create(uu4, ur)
        UserRole.create(uu5, ur)
        UserRole.create(uu6, ur)

        List allusers = User.findAll()

        for (int i = 0; i < 20; i += 2) {
            for (int j = 0; j < 20; j += 2) {
                println(i + "/" + j)

                POI poi = new POI(description: "This is a demo POI", nom: "testPOI", latitude: 48.63290858589534 - i * 0.438770957067405, longitude: -1.03271484375 + j * 0.74267578125, proprietaire: allusers.get(i % allusers.size()))


                if (poi.save(flush: true)) {

                } else {
                    poi.errors.allErrors.each {
                        println(it)
                    }

                }

            }

        }


        def grailsAttributes = new DefaultGrailsApplicationAttributes(servletContext)

        try {
            File grailsAppFolder
            if (AppConfig.saveImageFolderString) {
                grailsAppFolder = new File(AppConfig.saveImageFolderString)
            } else {
                grailsAppFolder = grailsAttributes.getApplicationContext().getResource("/upload/").getFile();
            }
            println("s1" + grailsAppFolder.toString())
            if (grailsAppFolder.exists()) {
                println("s2" + grailsAppFolder.toString())
                if (grailsAppFolder.isFile()) {
                    println("s3" + grailsAppFolder.toString())
                    grailsAppFolder.delete();
                }
            }
            if (!grailsAppFolder.exists()) {
                println("s4" + grailsAppFolder.toString())

                grailsAppFolder.mkdirs();
            }
            if (grailsAppFolder.exists()) {
                println("s5" + grailsAppFolder.toString())
                if (grailsAppFolder.isDirectory()) {
                    println("s6" + grailsAppFolder.toString())
                    AppConfig.saveImageFolderString = grailsAppFolder.getAbsolutePath();
                    AppConfig.saveImageFolder = grailsAppFolder;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    def destroy = {
    }
}
