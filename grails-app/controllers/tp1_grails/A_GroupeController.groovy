package tp1_grails

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import utils.ResponseObject

class A_GroupeController {

    //Groupe :
    ////String nom
    ////Image logo
    ////static hasMany = [pois:POI]


    @Secured(['isFullyAuthenticated()'])
    def delGroup() {
        if (params.getList("deleteGroup")) {
            List<ResponseObject> reponses = new ArrayList<>()

            List<String> strIdList = params.getList("deleteGroup")
            List<Long> idList = new ArrayList<>()
            for (String s : strIdList) {
                try {
                    idList.add(Long.valueOf(s))
                }
                catch (Exception e) {
                    reponses.add(new ResponseObject("L'id " + s + " n'est pas valide", "warning"))
                }
            }


            List<Groupe> groupes = new ArrayList<>()
            if (!idList.empty) {
                groupes = Groupe.findAllByIdInList(idList)
            }

            if (groupes.empty) {

                reponses.add(new ResponseObject("Aucun id de groupe valide n'a été fourni", "warning"))
                response.setStatus(400)
            }

//            println(groupes)
            //          println(idList)

            groupes.each {
                List<POI> orphanPOI = new ArrayList<>();
                it.pois.each {
                    if (it.groupes.size() == 1) {
                        orphanPOI.add(it)
                    }
                }
                if (!orphanPOI.isEmpty() && !params.get("force")) {


                    StringBuilder sb = new StringBuilder();
                    sb.append("Impossible de supprimer le groupe " + it.nom + " car il contient les POI suivants qui n'ont pas d'autres groupes :")
                    orphanPOI.each {
                        sb.append(it.toShortString())
                    }
                    response.setStatus(400)
                    reponses.add(new ResponseObject(orphanPOI, sb.toString(), "warning"))

                } else {
                    POI.deleteAll(orphanPOI)
                    Groupe.deleteAll(groupes)
                    reponses.add(new ResponseObject(it, "Le groupe " + it.nom + " a été supprimé avec succès", "success"))
                }
            }
            render new ResponseObject(reponses, "", "") as JSON
        }
        render new ResponseObject("Aucun groupe n'a été sélectionné", "warning") as JSON;

    }

    @Secured(['isFullyAuthenticated()'])
    def majGroup() {

        if (params.id) {


            Groupe g = Groupe.findById(params.id)
            if (g) {

                def nom = params.get("nom")
                if (!nom) {

                    render new ResponseObject("Il est  obligatoire de fournir un nom valide pour ce groupe", "danger") as JSON
                    response.setStatus(400)
                    return
                }

                def image = Image.saveImageFromUpload(request.getPart('logo'))
                if (image) {
                    g.setLogo(image)
                }





                g.setNom(nom)

                render new ResponseObject(g, "Le groupe a été modifié avec succès", "success") as JSON

            }


        }

        render new ResponseObject(null, "Ce groupe n'existe pas", "danger") as JSON
        response.setStatus(400)

    }

    @Secured(['isFullyAuthenticated()'])
    def createGroup() {

        def image = Image.saveImageFromUpload(request.getPart('logo'))
        if (!image) {

            response.setStatus(400)
            render new ResponseObject("Il est  obligatoire de fournir un logo de groupe", "danger") as JSON

            return
        }
        def nom = params.get("nom")
        if (!nom) {

            render new ResponseObject("Il est  obligatoire de fournir un nom de groupe", "danger") as JSON
            response.setStatus(400)
            return
        }
        def pois = []
        Groupe group = new Groupe(logo: (Image) image, nom: nom, pois: pois)
        if (!group.save(flush: true)) {
            group.errors.allErrors.each {
            }
        }

        render new ResponseObject(group, "Le groupe a été créé avec succès", "success") as JSON


    }

    @Secured(['isFullyAuthenticated()'])
    def getGroupes() {
        render new ResponseObject(Groupe.findAll()) as JSON
    }

    @Secured(['isFullyAuthenticated()'])
    def getGroupsPOI() {
        if (params.id && params.id.toString().isLong()) {
            POI poi = POI.findById(params.id);
            if (poi) {
                render new ResponseObject(poi.groupes) as JSON
            }
        }

        render new ResponseObject("Aucun ID de POI valide n'a été renseigné","danger") as JSON;


    }
    @Secured(['isFullyAuthenticated()'])
    def getLogoGroup() {
        if (params.id && params.id.toString().isLong()) {
            Groupe groupe = Groupe.findById(params.id);
            if (groupe) {
                render new ResponseObject(groupe.logo) as JSON
            }

        }

        render new ResponseObject("Aucun ID de groupe valide n'a été renseigné","danger") as JSON;

    }
}
