package tp1_grails

import grails.converters.JSON
import utils.AppConfig

class POI {
    User proprietaire;
    String nom


    String description
    float latitude
    float longitude

    static hasMany = [images:Image,groupes:Groupe]
    static belongsTo = [Groupe]
    static hasOne = [proprietaire:User]
    static constraints = {

    }

    public String toShortString()
    {

        return (  "\n"+nom
                + "\n\tLat: " + latitude
                + "\n\tLng: " + longitude
                + "\n\tPropriétaire: " + proprietaire.getUsername())
    }
    public String toVeryShortString()
    {

        return (  "\n\t"+nom
                + "\n\tLat: " + latitude
                + "\n\tLng: " + longitude);
    }
}
