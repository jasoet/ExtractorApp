package id.jasoet.extractor.util

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

object MimeTypeResource {

    val microsoftOffice: List<MimeType> =
            "/mimetypes/msoffice.csv".loadLocalResource()
                    .map {
                        val fields = it.split(",")
                        MimeType(fields[0], fields[1])
                    }

    val pdf: List<MimeType> =
            "/mimetypes/pdf.csv".loadLocalResource()
                    .map {
                        val fields = it.split(",")
                        MimeType(fields[0], fields[1])
                    }
}