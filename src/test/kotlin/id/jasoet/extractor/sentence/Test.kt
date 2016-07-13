package id.jasoet.extractor.sentence

import id.jasoet.extractor.core.document.extractDocument
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

        val sentences = document.map {
            sentenceDetector.sentDetect(it.content.toString())
        }.get()

        sentences.forEachIndexed { i, s ->
            println("$i >> $s")
        }
    }

    @Test
    fun tokenManual() {
        val exampleDoc = javaClass.getResourceAsStream("/LaporanKepolisian1.docx")

        val document = exampleDoc.use {
            it.extractDocument()
        }

        println(document.get().content.toString())
    }
}