package id.jasoet.extractor.app.command.handler

import id.jasoet.extractor.app.command.AddCommand
import id.jasoet.extractor.app.printc
import id.jasoet.extractor.app.service.DocumentService
import id.jasoet.extractor.app.workingDirectory
import nl.komponents.kovenant.all
import nl.komponents.kovenant.functional.bind
import org.apache.maven.shared.utils.io.DirectoryScanner
import org.fusesource.jansi.Ansi
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileInputStream

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Component
class AddHandler {
    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var documentService: DocumentService

    fun handle(command: AddCommand) {
        printc(Ansi.Color.GREEN) {
            "Working Dir: ${workingDirectory()}"
        }
        printc(Ansi.Color.YELLOW) {
            "Scanning: ${command.files.reduce { s, x -> "$s, $x" }}"
        }

        val directoryScanner =
            DirectoryScanner().apply {
                this.basedir = File(workingDirectory())
                this.setIncludes(*command.files.toTypedArray())
                this.scan()
            }

        val foundFiles = directoryScanner.includedFiles.map { file ->
            File(directoryScanner.basedir, file)
        }

        printc(Ansi.Color.MAGENTA) {
            "Found ${foundFiles.size} files"
        }


        all(foundFiles.map {
            log.info("Processing ${it.absolutePath}")
            printc(Ansi.Color.GREEN) {
                "Processing ${it.absolutePath}"
            }
            documentService.convertAndProcessDocument(it.absolutePath, FileInputStream(it))
        }) fail {
            log.error("${it.message} when process Documents", it)
            printc(Ansi.Color.RED) {
                "Error When Processing Document: ${it.message}"
            }
        } bind { listPair ->
            all(listPair.map { pair ->
                all(
                    documentService.storeDocument(pair.first),
                    documentService.storeProcessedDocument(pair.second)
                )
            })
        } fail {
            log.error("${it.message} when Store Documents", it)
            printc(Ansi.Color.RED) {
                "Error When Store Document: ${it.message}"
            }
        } success {
            it.flatten().forEach {
                printc(Ansi.Color.GREEN) {
                    "Stored Document ${it.id}"
                }
            }
        }

    }

}
