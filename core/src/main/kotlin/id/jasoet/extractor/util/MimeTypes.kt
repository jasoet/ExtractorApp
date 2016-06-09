package id.jasoet.extractor.util

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

object MimeTypes {

    val microsoftOffice: List<Pair<String, String>> =
            "/mimetypes/msoffice.csv".loadLocalResource()
                    .map {
                        val fields = it.split(",")
                        fields[0] to fields[1]
                    }

    val pdf: List<Pair<String, String>> =
            "/mimetypes/pdf.csv".loadLocalResource()
                    .map {
                        val fields = it.split(",")
                        fields[0] to fields[1]
                    }

}