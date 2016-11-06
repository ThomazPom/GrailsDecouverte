package tp1_grails

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.security.core.context.SecurityContextHolder
import utils.ResponseObject

class A_UserController {

    def createUser() {

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
            }
            else
            {
                users.each {
                    if(it.pois.isEmpty() || params.get("force"))
                    {
                        StringBuilder sb = new StringBuilder()
                        sb.append("Le compte "+it.getUsername()+" a été supprimé")
                        it.pois.each {
                            sb.append("POI supprimé :"+ it.toVeryShortString())
                        }
                        POI.deleteAll(it.getPois())
                        UserRole.removeAll(it);
                        User.deleteAll(it)
                        reponses.add(new ResponseObject(it.id,sb.toString(),"success"))

                    }
                    else{
                        StringBuilder sb = new StringBuilder()
                        sb.append("Impossible de supprimer "+it.getUsername()+" car il possède les POI suivants :")
                        it.pois.each {
                            sb.append(it.toVeryShortString())
                        }
                        response.setStatus(400)
                        reponses.add(new ResponseObject(sb.toString(),"warning"))
                    }
                }
            }
            render new ResponseObject(reponses) as JSON
        }

        render new ResponseObject("Aucun utilisateur n'a été sélectionné", "warning") as JSON;

    }

    @Secured("hasRole('ROLE_ADMIN')")
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

        List<ResponseObject> reponses = new ArrayList<>()
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
        if (params.get("selectRole") && params.get("selectRole").toString().isLong()) {
            Role r = Role.findById(Integer.parseInt(params.get("selectRole").toString()))
            if (r) {
                UserRole.removeAll(u)
                UserRole.create(u, r)
                reponses.add(new ResponseObject("Le role de l'utilisateur a été modifié avec succès", "success"))
            } else {

                reponses.add(new ResponseObject("Ce role n'existe pas", "danger"))
            }

        }

        u.save(flush: true)
        render new ResponseObject(reponses, u, "L'utilisateur a été modifié avec succes", "success") as JSON

    }


    @Secured(['isFullyAuthenticated()'])
    def logout() {
        SecurityContextHolder.clearContext()
        redirect(controller: "main", action: "index")
    }

    @Secured("hasRole('ROLE_ADMIN')")
    def getUsers() {


        render new ResponseObject(User.findAll()) as JSON
    }
    @Secured(['permitAll'])
    def getPublicUsers() {
        Set<User> user = User.findAll();
        List<ResponseObject> reponses = new ArrayList<>()
        user.each {
            reponses.add(new ResponseObject( [it.getUsername(),it.getAuthorities().first().getAuthority() ] as String[]))
        }

        render new ResponseObject(reponses) as JSON
    }

    @Secured("hasRole('ROLE_ADMIN')")
    def getUser() {
        if (!params.id || !params.id.toString().isLong()) {
            response.setStatus(400)
            render new ResponseObject("Aucun ID valide n'a été fourrni", "danger") as JSON
            return
        }


        User u = User.findById(params.id)
        if (!u) {
            response.setStatus(400)
            render new ResponseObject("Cet utilisateur n'existe pas", "danger") as JSON
            return
        }
        render new ResponseObject(u) as JSON
    }

    @Secured("hasRole('ROLE_ADMIN')")
    def getRoles() {
        render new ResponseObject(Role.findAll()) as JSON
    }


}
