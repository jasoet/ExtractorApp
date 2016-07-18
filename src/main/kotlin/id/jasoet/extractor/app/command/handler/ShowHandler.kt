package id.jasoet.extractor.app.command.handler

import id.jasoet.extractor.app.center
import id.jasoet.extractor.app.command.ShowCommand
import id.jasoet.extractor.app.leftPad
import id.jasoet.extractor.app.printc
import id.jasoet.extractor.app.rightPad
import id.jasoet.extractor.app.service.DocumentService
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
        val documentsPromise =
            if (command.ids.isEmpty()) {
                printc(Ansi.Color.GREEN) {
                    "Show All Documents\n"
                }
                documentService.loadAllDocument()
            } else {
                printc(Ansi.Color.GREEN) {
                    "Show Documents with id [${command.ids.joinToString(", ")}]\n"
                }
                documentService.loadDocument(command.ids)
            }

        val documents = documentsPromise.get()
        documents.forEach { doc ->
            printc(Ansi.Color.YELLOW) {
                "\n\n".rightPad(100, "*")
            }
            printc(Ansi.Color.BLUE) {
                """
                |Id:            ${doc.id}
                |FileName:      ${doc.fileName}
                |ContentType:   ${doc.contentType}
                |""".trimMargin()
            }

            if (command.showContent) {
                printc(Ansi.Color.BLUE) {
                    """
                    |
                    |${"Content".center(30, "=")}
                    |${doc.content}
                    |
                    |""".trimMargin()
                }
            }

            if (command.showProcessed || command.showAll) {
                val processedDocuments = documentService
                    .loadProcessedDocument(doc.id).get()

                printc(Ansi.Color.YELLOW) {
                    "Document Parts".center(30, "=")
                }

                processedDocuments.contentLinesAnalyzed.forEach { line ->
                    printc {
                        val type = line.type.toString().trim().leftPad(10)
                        fgRed().a(type)
                            .fgBrightYellow().a("|\t")
                            .fgBrightBlue().a(line.content.trim())
                    }
                }
            }

            if (command.showExtracted || command.showAll) {
                val extractedDocument = documentService
                    .loadExtractedDocument(doc.id).get()

                printc(Ansi.Color.YELLOW) {
                        "".rightPad(30, "=")
                }

                printc(Ansi.Color.RED) {
                    "DSL to Extract : ${extractedDocument.dslName}"
                }

                printc(Ansi.Color.YELLOW) {
                    "Results".center(30, "=")
                }
                extractedDocument.results.forEach { result ->
                    printc {
                        val type = result.key.toString().trim().leftPad(20)
                        fgRed().a(type)
                            .fgBrightYellow().a("|")
                            .fgBrightBlue().a(result.value.trim())
                    }
                }
                if (extractedDocument.errors.isNotEmpty()) {
                    printc(Ansi.Color.YELLOW) {
                        "Errors".center(30, "=")
                    }
                }
                extractedDocument.errors.forEach { result ->
                    printc {
                        val type = result.key.toString().trim().leftPad(20)
                        fgRed().a(type)
                            .fgBrightYellow().a("|")
                            .fgBrightBlue().a(result.value.trim())
                    }
                }
            }


        }
    }
}