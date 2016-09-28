package tp1_grails

class Groupe {

    String nom
    Image logo

    static hasMany = [pois:POI]

    static constraints = {

    }
}
