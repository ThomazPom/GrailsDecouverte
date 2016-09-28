package tp1_grails

class POI {
    User proprietaire;
    String nom

    String description
    float latitude
    float longitude

    static hasMany = [images:Image,groupes:Groupe]
    static belongsTo = [Groupe]

    static constraints = {



    }
}
