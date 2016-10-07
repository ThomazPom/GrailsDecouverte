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

        def image= Image.saveImageFromUpload(request.getFile('logo'))
        if (!image) {
            flash.message = "Il est obligatoire de fournir un logo pour les groupes"
            flash.put("typeAlert", "danger")
            render(view: '/A_Message')
            return
        }
        def nom  =params.get("nom")
        def pois=[]
        Groupe group = new Groupe(logo: (Image)image, nom:nom,pois: pois)

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
