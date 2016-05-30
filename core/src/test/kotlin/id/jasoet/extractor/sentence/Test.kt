package id.jasoet.extractor.sentence

import id.jasoet.extractor.document.extractDocument
import opennlp.tools.sentdetect.SentenceDetectorME
import opennlp.tools.sentdetect.SentenceModel
import org.junit.Test

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class Test {

    @Test
    fun main() {

        val exampleDoc = javaClass.getResourceAsStream("/LaporanKepolisian1.docx")

        val modelInputStream = javaClass.getResourceAsStream("/en-sent.bin")

        val sentenceModel = SentenceModel(modelInputStream)
        val sentenceDetector = SentenceDetectorME(sentenceModel)

        val document = exampleDoc.use {
            it.extractDocument()
        }
g
        val sentences = document.map {
            sentenceDetector.sentDetect(it.contentHandler.toString())
        }.get()

        sentences.forEachIndexed { i, s ->
            println("$i >> $s")
        }
    }

}