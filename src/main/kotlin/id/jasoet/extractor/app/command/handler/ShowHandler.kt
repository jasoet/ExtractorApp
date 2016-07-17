package id.jasoet.extractor.app.command.handler

import id.jasoet.extractor.app.command.ShowCommand
import id.jasoet.extractor.app.dslMap
import id.jasoet.extractor.app.service.DocumentService
import nl.komponents.kovenant.functional.map
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Component
class ShowHandler {

    @Autowired
    lateinit var documentService: DocumentService

    fun handle(command: ShowCommand) {
        println("Process Show Command $command")

        val mapFileName = documentService
            .loadAllDocument()
            .map {
                it.map {
                    it.id to it.fileName
                }.toMap()
            }
            .get()


        documentService
            .loadAllProcessedDocument()
            .success { processedDocuments ->
                val dslName = "PoliceReport"

                val dsl = dslMap[dslName] ?: throw IllegalArgumentException("DSL $dslName not Found")
                println("Process With ${dsl.name}[${dsl.className}] ")
                processedDocuments.forEach { doc ->
                    val fileName = mapFileName[doc.id]
                    println(fileName)
                    val results = dsl.extract(doc.contentLinesAnalyzed)
                    results.forEach {
                        val (name, result, ex) = it
                        val errorMessage = if (ex != null) "[${ex.message}]" else ""
                        println("$name => $result $errorMessage")
                    }
                }
            }
    }
}