package tp1_grails

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import utils.AppConfig
import utils.ResponseObject


class A_POIController {

    def springSecurityService

    def index() {}

    @Secured(['isFullyAuthenticated()'])
    def createImagePOI() {
        def photos = params.getList("photos");
        def image;
        photos.each {
            image = Image.saveImageFromUpload(it);
        }
        render new ResponseObject(image) as JSON
    }


    @Secured(['isFullyAuthenticated()'])
    def getOne() {
        if (params.id) {
            if (params.id.toString().isLong()) {
                POI p = POI.findById(params.id)

                if (p) {

                    User u = springSecurityService.currentUser
                    Role role = u.getAuthorities().first()
                    render new ResponseObject(p, (Object) (p.getProprietaire().equals(u) || !role.equals(AppConfig.roleUser)), "", "") as JSON
                    return
                }
            }
        }

        response.setStatus(400)
        render new ResponseObject("Aucun ID valide n'a été fourni", "warning") as JSON
    }

    @Secured(['isFullyAuthenticated()'])
    def getOnePhotos() {
        if (params.id) {
            if (params.id.toString().isLong()) {
                POI p = POI.findById(params.id)

                if (p) {
                    render new ResponseObject(p.getImages()) as JSON
                    return
                }
            }
        }

        response.setStatus(400)
        render new ResponseObject("Aucun ID valide n'a été fourni", "warning") as JSON
    }


    @Secured(['isFullyAuthenticated()'])
    def getAll() {
        render new ResponseObject(POI.findAll()) as JSON
    }


    @Secured(['isFullyAuthenticated()'])
    def delPOI() {
        if (params.id && params.id.toString().isLong()) {

            POI p = POI.findById(params.id)

            if (p) {
                User u = springSecurityService.currentUser
                Role role = u.getAuthorities().first()
                if (!(p.getProprietaire().equals(u) || !role.equals(AppConfig.roleUser))) {
                    response.setStatus(403)
                    render new ResponseObject("Opération non autorisée", "warning") as JSON
                    return;
                }

                while (p.groupes.size() > 0) {
                    p.removeFromGroupes(p.groupes[0])
                }
                p.delete(flush: true);

                render new ResponseObject("Le POI a été supprimé", "success") as JSON
            }
        }

        render new ResponseObject("Aucun ID valide n'a été fourni ", "warning") as JSON
    }


    @Secured(['isFullyAuthenticated()'])
    def majPOI() {
        if (params.id && params.id.toString().isLong()) {

            POI p = POI.findById(params.id)

            if (p) {


                User u = springSecurityService.currentUser
                Role role = u.getAuthorities().first()
                if (!(p.getProprietaire().equals(u) || !role.equals(AppConfig.roleUser))) {
                    response.setStatus(403)
                    render new ResponseObject("Opération non autorisée", "warning") as JSON
                    return;
                }

                List<?> photos = params.getList("imgid");
                List<ResponseObject> reponses = new ArrayList<>()

                if (params.get("nom")) {
                    p.setNom(params.get("nom").toString());
                } else {
                    reponses.add(new ResponseObject("Le nom du POI ne peut pas être vide", "warning"))
                }

                List<Image> images = new ArrayList<>()
                if (!photos.empty) {

                    photos.each {
                        if (!it.toString().isLong()) {
                            response.setStatus(400)
                            render new ResponseObject("L'ID de photo " + it.toString() + " est invalide", "danger") as JSON
                            return
                        }
                    }
                    images = Image.findAllByIdInList(photos)
                }

                if (!images.empty) {
                    p.setImages(images as Set<Image>)
                }
                def groupes = params.getList("selectGroupe")
                List<Groupe> listgroupes = new ArrayList<>()
                if (!groupes.empty) {
                    groupes.each {
                        if (!it.toString().isLong()) {
                            response.setStatus(400)
                            render new ResponseObject("L'ID de groupe " + it.toString() + " est invalide", "danger") as JSON
                            return

                        }
                    };
                    listgroupes = Groupe.findAllByIdInList(groupes);

                }


                if (!listgroupes.empty) {

                    while (p.groupes.size() > 0) {
                        p.removeFromGroupes(p.groupes[0])
                    }

                    listgroupes.each {
                        it.addToPois(p)
                    }
                    println(listgroupes)

                }

                if (params.get("description")) {

                    p.setDescription(params.get("description").toString());

                } else {
                    reponses.add(new ResponseObject("La description du POI ne peut pas être vide", "warning"))
                }

                if (p.save(flush: true)) {
                    render new ResponseObject(reponses, "Le POI a été mis à jour", "success") as JSON
                } else {
                    p.errors.allErrors.each {
                        println(it)
                    }
                    render new ResponseObject(reponses, "Erreur à la sauvegarde du POI", "warning") as JSON
                }

                return
            } else {
                response.setStatus(404)
                render new ResponseObject("Le POI n'existe pas", "warning") as JSON
                return
            }

        }

        response.setStatus(400)
        render new ResponseObject("L'identifiant n'est pas valide", "danger") as JSON
        return


    }

    @Secured(['isFullyAuthenticated()'])
    def createPOI() {
        List<?> photos = params.getList("imgid");

        if (!params.get("nom")) {
            response.setStatus(400)
            render new ResponseObject("Il est obligatoire de fournir un nom de POI", "warning") as JSON
            return
        }

        if (!params.get("longitude") || !params.get("longitude").toString().isFloat()) {
            response.setStatus(400)
            render new ResponseObject("Il est obligatoire de fournir une longitude valide", "warning") as JSON
            return
        }

        if (!params.get("latitude") || !params.get("latitude").toString().isFloat()) {
            response.setStatus(400)
            render new ResponseObject("Il est obligatoire de fournir une latitude valide", "warning") as JSON
            return
        }

        if (photos.empty) {
            response.setStatus(400)
            render new ResponseObject("Il est obligatoire de fournir des images pour un POI", "warning") as JSON
            return
        }


        def groupes = params.getList("selectGroupe")
        if (groupes.empty) {
            response.setStatus(400)
            render new ResponseObject("Il est obligatoire de sélectionner au moins un groupe", "warning") as JSON
            return
        };
        if (!params.get("description")) {
            response.setStatus(400)
            render new ResponseObject("Il est obligatoire de fournir une description de POI", "warning") as JSON
            return
        }

        photos.each {
            if (!it.toString().isLong()) {
                response.setStatus(400)
                render new ResponseObject("L'ID de photo " + it.toString() + " est invalide", "danger") as JSON
                return
            }
        }

        groupes.each {
            if (!it.toString().isLong()) {
                response.setStatus(400)
                render new ResponseObject("L'ID de groupe " + it.toString() + " est invalide", "danger") as JSON
                return
            }
        }
        List<Image> images = Image.findAllByIdInList(photos);


        if (images.empty) {
            response.setStatus(400)
            render new ResponseObject("Aucune image valide n'a été fournie", "danger") as JSON
            return
        }
        List<Groupe> listgroupes = Groupe.findAllByIdInList(groupes);

        if (listgroupes.empty) {
            response.setStatus(400)
            render new ResponseObject("Aucun groupe valide n'a été fourni", "danger") as JSON
            return
        }


        POI poi = new POI(
                proprietaire: springSecurityService.currentUser
                , nom: params.get("nom")
                , description: params.get("description")
                , images: images
                , latitude: params.get("latitude").toString().toFloat(), longitude: params.get("longitude").toString().toFloat()
        )
        listgroupes.each {
            it.addToPois(poi)
            it.pois.add(poi)
        }


        if (poi.save(flush: true)) {
            render new ResponseObject(poi, "Le POI a été créé avec succès ! ", "success") as JSON

        } else {
            poi.errors.allErrors.each {
                println(it)
            }
            render new ResponseObject(poi, "Erreur à la création du POI ", "danger  ") as JSON

        }

    }

}
