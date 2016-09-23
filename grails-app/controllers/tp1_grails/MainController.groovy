package tp1_grails

class MainController {


    def index() {

        def htmlContent = "<h1>HelloWorld</h1>"
        render text: htmlContent, contentType:"text/html", encoding:"UTF-8"
    }
}
