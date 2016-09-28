package tp1_grails

import grails.plugin.springsecurity.annotation.Secured

class MainController {

    @Secured('permitAll')
    def index() {

        render(view: "index");

        //render 'ok'
    }
}
