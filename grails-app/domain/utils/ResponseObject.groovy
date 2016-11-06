package utils

import grails.converters.JSON
import org.grails.web.json.JSONObject
import tp1_grails.Groupe
import tp1_grails.Image
import tp1_grails.POI

/**
 * Created by Thoma on 25/10/2016.
 */
class ResponseObject {

    public String message
    def data
    def data2
    public String status;
    static constraints = {
       //Register Review domain for JSON rendering
        JSON.registerObjectMarshaller(ResponseObject) {
            def output = [:]
            output['message'] = it.message
            output['data'] =  it.data
            output['data2'] =  it.data2
            output['status'] = it.status
            return output;
        }
    }
    ResponseObject(final Object data, final String message,final String status) {
        this.data=data
        this.message=message
        this.status=status
    }
    ResponseObject(final Object data) {
        this.data=data
    }

    ResponseObject(final String message,final String status) {

        this.message=message
        this.status=status
    }


    ResponseObject(final Object data,final Object data2, final String message,final String status) {
        this.data=data
        this.data2=data2
        this.message=message
        this.status=status
    }

}
