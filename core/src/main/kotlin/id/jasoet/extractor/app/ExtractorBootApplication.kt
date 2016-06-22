package id.jasoet.extractor.app

import id.jasoet.extractor.app.loader.loadDocumentModel
import id.jasoet.extractor.app.model.LineModel
import id.jasoet.extractor.app.model.ProcessedDocument
import id.jasoet.extractor.app.service.DocumentService
import id.jasoet.extractor.core.dictionary.DictionaryType
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner
import nl.komponents.kovenant.task
import nl.komponents.kovenant.then
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
            task {
                SpringApplication.run(ExtractorBootApplication::class.java, *args)
            } then { ctx ->
                ctx.getBean(DocumentService::class.java)
            } success  { service ->
                service.removeAllDocument()
            } fail {
                log.error("${it.message} when Delete ", it)
            } then { service ->
                val currentDirectory = currentDirectory()
                println("Current Dir $currentDirectory")
                val fileStream = FileInputStream("$currentDirectory/docs/Real2.docx")
                val documentModel = fileStream.use {
                    it.loadDocumentModel("Real0.docx")
                }
                val savedDoc = service.storeDocument(documentModel).get()
                log.info("Saved doc with id ${savedDoc.id}")
                val listDocs = service.loadAllDocument().get()
                listDocs.forEach {
                    log.info("${it.id} -> ${it.fileName}")
                    val document = it.toDocument()

                    document.contentLinesOriginal().forEach {
                        println("${it.toString()}")
                    }

                    println("")
                    println("")
                    println("")

                    document.contentLinesTyped().forEach {
                        log.info("${it.toString()}")
                    }

                    log.info("==================== ")

                    document.contentLinesCleaned().forEach {
                        log.info("${it.toString()}")
                    }

                    val cleaned = document.contentLinesCleaned().map {
                        val annotation = mapOf(
                            DictionaryType.AGE to "12",
                            DictionaryType.CLAUSE to "345 KUHP"
                        )
                        LineModel(it.type, it.content, annotation)
                    }

                    val processedDocument =
                        ProcessedDocument(
                            it.id,
                            document.contentLinesOriginal(),
                            document.contentLinesCleaned().toLineModel(),
                            document.contentLinesTyped().toLineModel(),
                            cleaned)


                    service.storeProcessedDocument(processedDocument).get()
                    log.info("Saved Processed Document")
                }
            } fail {
                log.error("${it.message} when Delete ", it)
            } always {
                val classWithAnnotation = FastClasspathScanner("id.jasoet.extractor.app.config")
                    .scan()
                    .getNamesOfClassesWithAnnotation(Configuration::class.java)

                log.info("Class with Annotation ${classWithAnnotation.reduce { s, i -> "$s | $i" }}")
                log.info("Application Running in ${currentDirectory()}")
            }

        }
    }
}