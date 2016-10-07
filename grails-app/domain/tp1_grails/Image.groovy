package tp1_grails

class Image {

    static constraints = {
        String localURL
    }

    protected static saveImageFromUpload(f) {
        if (f.empty) {
            return false
        } else {

            String urlNewFile = ApplicationConfig.saveImageFolderString + File.pathSeparator + UUID.randomUUID();
            new File(urlNewFile);
            Image  image = new Image(localURL: urlNewFile)
            if(!image.save(flush:true)) {
                image.errors.allErrors.each {
                    println it
                    return false;
                }
            }
            return image


        }
    }
}
