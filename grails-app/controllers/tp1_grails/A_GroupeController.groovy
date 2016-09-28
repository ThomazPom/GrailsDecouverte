package tp1_grails

import grails.plugin.springsecurity.annotation.Secured

import java.security.acl.Group

class A_GroupeController {

    //Groupe :
    ////String nom
    ////Image logo
    ////static hasMany = [pois:POI]

    @Secured(['isFullyAuthenticated()'])
    def createGroup()
    {


        def f = request.getFile('logo')
        if (f.empty) {
            flash.message = "Il est obligatoire de fournir un logo pour les groupes"
            flash.put("typeAlert", "danger")
            render(view: '/A_Message')
            return
        }


        def webrootDir = servletContext.getRealPath("/") //app directory
        File fileDest = new File(webrootDir,"images/uploads/ghgh.jpg")
        f.transferTo(fileDest)

        //f.transferTo(new File('/wamp64/www/'+UUID.randomUUID()))

        def nom  =params.get("nom")

        nom ="test"
        def logo = new Image(localURL:"http://localhost/phpmyadmin/themes/pmahomme/img/logo_left.png")

        def pois=[]
        Groupe group = new Groupe(logo: logo, nom:nom,pois: pois)

        if(!group.save(flush:true)) {
            group.errors.allErrors.each {
                println it
            }
        }


        flash.message = "Le groupe a été créé avec succes"
        flash.put("typeAlert", "success")
        render(view:'/A_Message')

    }
}
