package tp1_grails

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.security.core.context.SecurityContextHolder
import utils.AppConfig
import utils.ResponseObject

class A_UserController {


    @Secured("hasRole('ROLE_ADMIN')")
    def createUser() {

        def username = params.get("username")
        if (!username) {
            response.setStatus(400)

            render new ResponseObject("Aucun nom d'utilisateur n'a été fourni ", "danger") as JSON
            return
        }
        if (params.get("username").toString().trim().isEmpty()) {

            response.setStatus(400)
            render new ResponseObject("Le nom d'utilisateur ne peut pas être vide", "warning") as JSON
            return
        }
        def password = params.get("password")
        if (!password) {

            response.setStatus(400)
            render new ResponseObject("Aucun mot de passe n'a été fourni ", "danger") as JSON
            return
        }
        def userExist = User.findByUsername(username)
        if (userExist) {

            response.setStatus(400)
            render new ResponseObject("Ce nom d'utilisateur n'est pas disponible", "warning") as JSON
            return
        }
        if (params.get("password").toString().trim().isEmpty()) {

            response.setStatus(400)
            render new ResponseObject("Le mot de passe ne peut pas être vide", "warning") as JSON
            return
        }


        User u = new User(username: username.toString(), password: password.toString()).save(flush: true)
        if (params.get("selectRole") && params.get("selectRole").toString().isLong()) {
            Role r = Role.findById(params.get("selectRole").toString().toLong())
            if (r) {
                UserRole.create(u, r)
                render new ResponseObject("L'utilisateur a été créé avec succès", "success") as JSON
                return
            } else {
                u.delete()
                response.setStatus(400)
                render new ResponseObject("Ce role n'existe pas", "danger")
                return
            }

        }

        response.setStatus(400)
        render new ResponseObject("Aucun role n'a été fourni ", "danger")
        return
    }

    @Secured("hasRole('ROLE_ADMIN')")
    def delUser() {

        if (params.getList("deleteUser")) {
            List<ResponseObject> reponses = new ArrayList<>()

            List<String> strIdList = params.getList("deleteUser")
            List<Long> idList = new ArrayList<>()
            for (String s : strIdList) {
                try {
                    idList.add(Long.valueOf(s))
                }
                catch (Exception e) {
                    reponses.add(new ResponseObject("L'id " + s + " n'est pas valide", "warning"))
                }
            }


            List<User> users = new ArrayList<>()
            if (!idList.empty) {
                users = User.findAllByIdInList(idList)
            }

            if (users.empty) {

                reponses.add(new ResponseObject("Aucun id valide n'a été fourni", "warning"))
                response.setStatus(400)
            } else {
                users.each {
                    if (it.pois.isEmpty() || params.get("force")) {
                        StringBuilder sb = new StringBuilder()
                        sb.append("Le compte " + it.getUsername() + " a été supprimé")
                        it.pois.each {
                            sb.append("POI supprimé :" + it.toVeryShortString())
                        }
                        POI.deleteAll(it.getPois())
                        UserRole.removeAll(it);
                        User.deleteAll(it)
                        reponses.add(new ResponseObject(it.id, sb.toString(), "success"))

                    } else {
                        StringBuilder sb = new StringBuilder()
                        sb.append("Impossible de supprimer " + it.getUsername() + " car il possède les POI suivants :")
                        it.pois.each {
                            sb.append(it.toVeryShortString())
                        }
                        response.setStatus(400)
                        reponses.add(new ResponseObject(sb.toString(), "warning"))
                    }
                }
            }
            render new ResponseObject(reponses) as JSON
        }

        render new ResponseObject("Aucun utilisateur n'a été sélectionné", "warning") as JSON;

    }

    @Secured("hasRole('ROLE_ADMIN')||hasRole('ROLE_MODO')")
    def majUser() {

        if ((!params.id || !params.id.toString().isLong()) && (!params.get('selectUser') || !params.get('selectUser').toString().isLong())) {
            response.setStatus(400)
            render new ResponseObject("Aucun ID valide n'a été fourrni", "danger") as JSON
            return
        }

        def id = params.id;
        if (!params.id) id = params.get('selectUser').toString().toLong()
        User u = User.findById(id)

        if (!u) {
            response.setStatus(400)
            render new ResponseObject("Cet utilisateur n'existe pas", "danger") as JSON
            return
        }


        User crrru = springSecurityService.currentUser
        Role currrole = crrru.getAuthorities().first()




        List<ResponseObject> reponses = new ArrayList<>()
        if (!currrole.equals(AppConfig.roleModo) && params.get("selectRole") && params.get("selectRole").toString().isLong()) {
            Role r = Role.findById(Integer.parseInt(params.get("selectRole").toString()))
            if (r) {
                if (!r.equals(AppConfig.roleUser) && currrole.equals(AppConfig.roleModo)) {
                    reponses.add(new ResponseObject("Opération non autorisée", "danger"))
                } else {
                    UserRole.removeAll(u)
                    UserRole.create(u, r)
                    reponses.add(new ResponseObject("Le role de l'utilisateur a été modifié avec succès", "success"))
                }
            } else {

                reponses.add(new ResponseObject("Ce role n'existe pas", "danger"))
            }

        }

        if (currrole.equals(AppConfig.roleModo)) {
            if (!u.getAuthorities().first().equals(AppConfig.roleUser)) {
                response.setStatus(403)
                render new ResponseObject("Edition de cet utilisateur non autorisée", "danger") as JSON
                return
            }
        }
        if (params.get("username")) {
            if (params.get("username").toString().isEmpty()) {
                reponses.add(new ResponseObject("Le nom d'utilisateur ne peut pas etre vide", "warning"))
            } else {
                u.setUsername(params.get("username").toString())
                reponses.add(new ResponseObject("Le nom d'utilisateur a été modifié avec succès", "success"))
            }
        } else {
            reponses.add(new ResponseObject("Le nom d'utilisateur ne peut pas etre vide", "warning"))
        }

        u.save(flush: true)
        render new ResponseObject(reponses, u, "L'utilisateur a été modifié avec succes", "success") as JSON

    }


    @Secured(['isFullyAuthenticated()'])
    def logout() {
        SecurityContextHolder.clearContext()
        redirect(controller: "main", action: "index")
    }

    def springSecurityService

    @Secured("hasRole('ROLE_ADMIN')||hasRole('ROLE_MODO')")
    def getUsers() {


        User u = springSecurityService.currentUser

        Role role = u.getAuthorities().first()
        if (role.equals(AppConfig.roleModo)) {
            render new ResponseObject(UserRole.findAllByRole(AppConfig.roleUser).user) as JSON
            return
        }

        render new ResponseObject(User.findAll()) as JSON
    }

    @Secured(['permitAll'])
    def getPublicUsers() {
        Set<User> user = User.findAll();
        List<ResponseObject> reponses = new ArrayList<>()
        user.each {
            reponses.add(new ResponseObject([it.getUsername(), it.getAuthorities().first().getAuthority()] as String[]))
        }

        render new ResponseObject(reponses) as JSON
    }

    @Secured("hasRole('ROLE_ADMIN')||hasRole('ROLE_MODO')")
    def getUser() {
        if (!params.id || !params.id.toString().isLong()) {
            response.setStatus(400)
            render new ResponseObject("Aucun ID valide n'a été fourrni", "danger") as JSON
            return
        }

        User curru = springSecurityService.currentUser

        User u = User.findById(params.id)
        Role role = curru.getAuthorities().first()



        if (!u) {
            response.setStatus(400)
            render new ResponseObject("Cet utilisateur n'existe pas", "danger") as JSON
            return
        }
        if (role.equals(AppConfig.roleModo) && !u.getAuthorities().first().equals(AppConfig.roleUser)) {

            response.setStatus(403)
            render new ResponseObject("Opération non autorisée", "warning") as JSON
            return
        }

        render new ResponseObject(u) as JSON
    }

    @Secured("hasRole('ROLE_ADMIN')||hasRole('ROLE_MODO')")
    def getRoles() {


        User u = springSecurityService.currentUser
        Role role = u.getAuthorities().first()
        if (role.equals(AppConfig.roleModo)) {

            render new ResponseObject(Arrays.asList(AppConfig.roleUser)) as JSON
            return
        }


        render new ResponseObject(Role.findAll()) as JSON
    }


}
