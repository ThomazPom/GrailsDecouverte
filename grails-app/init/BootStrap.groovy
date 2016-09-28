import tp1_grails.Role
import tp1_grails.User

class BootStrap {

    def init = { servletContext ->
        Role a1 = new Role(authority : "ROLE_ADMIN").save(flush:true)
        Role a2 = new Role(type : "Moderateur").save(flush:true)
        User a4 = new User(username: "admin" , password: "admin")
        a4.save(flush:true,failOnError: true)
    }
    def destroy = {
    }
}
