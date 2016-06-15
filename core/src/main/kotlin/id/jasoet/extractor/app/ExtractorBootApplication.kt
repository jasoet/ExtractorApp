package id.jasoet.extractor.app

import id.jasoet.extractor.app.loader.loadDocumentModel
import id.jasoet.extractor.app.model.DocumentModel
import id.jasoet.extractor.app.model.ProcessedDocument
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner
import org.mongodb.morphia.Datastore
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */


@SpringBootApplication
open class ExtractorBootApplication {
    companion object {
        private val log = LoggerFactory.getLogger(ExtractorBootApplication::class.java)
        @JvmStatic
        fun main(args: Array<String>) {
            val ctx = SpringApplication.run(ExtractorBootApplication::class.java, *args)
            val dataStore = ctx.getBean(Datastore::class.java)

            val fileStream = FileInputStream("/home/jasoet/LaporanKepolisian0.docx")
            val documentModel = fileStream.use {
                it.loadDocumentModel("LaporanKepolisian0.docx")
            }
            val savedDoc = dataStore.save(documentModel)
            log.info("Saved doc with id ${savedDoc.id}")
            val listDocs = dataStore.createQuery(DocumentModel::class.java).asList()
            listDocs.forEach {
                log.info("${it.id} -> ${it.fileName}")
                val document = it.toDocument()
                document.contentLinesCleaned().forEach {
                    log.info("${it.toString()}")
                }

                val processedDocument =
                    ProcessedDocument(
                        it.id,
                        document.contentLinesOriginal(),
                        document.contentLinesTyped().toLineModel(),
                        document.contentLinesCleaned().toLineModel())


                dataStore.save(processedDocument)
                log.info("Saved Processed Document")
            }

            val classWithAnnotation = FastClasspathScanner("id.jasoet.extractor.app.config")
                .scan()
                .getNamesOfClassesWithAnnotation(Configuration::class.java)

            log.info("Class with Annotation ${classWithAnnotation.reduce { s, i -> "$s | $i" }}")
            log.info("Application Running in ${currentDirectory()}")
        }
    }
}