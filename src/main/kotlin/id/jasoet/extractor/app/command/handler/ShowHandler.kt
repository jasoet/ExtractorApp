package id.jasoet.extractor.app.command.handler

import id.jasoet.extractor.app.command.ShowCommand
import id.jasoet.extractor.app.loadDSL
import id.jasoet.extractor.app.printc
import id.jasoet.extractor.app.service.DocumentService
import kotlinslang.control.tryOf
import kotlinslang.orElse
import nl.komponents.kovenant.functional.map
import org.fusesource.jansi.Ansi
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
            .success {
                val ruleList = loadDSL()[1].second.extractRule()

                it.map { (mapFileName[it.id] ?: "") to it.contentLinesAnalyzed }
                    .sortedBy { it.first }
                    .forEach {
                        val analyzedLines = it.second
                        val fileName = it.first
                        println("$fileName")

                        ruleList.forEach {
                            val (name, rules) = it
                            val operation = rules.first()
                            val result = tryOf { operation.invoke(analyzedLines) }
                                .onFailure { ex ->
                                    printc(Ansi.Color.RED) { ex.message ?: "" }
                                }
                                .orElse("")
                            println("$name => $result")
                        }

                        println("==================")
                    }

            }


    }
}