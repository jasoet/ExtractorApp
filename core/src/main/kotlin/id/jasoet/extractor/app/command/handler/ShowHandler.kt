package id.jasoet.extractor.app.command.handler

import id.jasoet.extractor.app.command.ShowCommand
import id.jasoet.extractor.app.service.DocumentService
import id.jasoet.extractor.core.document.findAnchor
import id.jasoet.extractor.core.document.findAnchorIndex
import id.jasoet.extractor.core.document.subList
import id.jasoet.extractor.core.dsl.Anchor.Key
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

        documentService
            .loadAllProcessedDocument()
            .success {
                val analyzedLines = it.first().contentLinesAnalyzed
                val anchor = analyzedLines.findAnchor(Key("WAKTU KEJADIAN"))
                val anchorIndex = analyzedLines.findAnchorIndex(Key("WAKTU KEJADIAN"))

                println("Index $anchorIndex")
                println("Anchor $anchor")
                val subList = analyzedLines.subList(from = Key("WAKTU KEJADIAN"))
                println(subList)
            }


    }
}