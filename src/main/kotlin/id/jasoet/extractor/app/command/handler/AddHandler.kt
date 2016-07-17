package id.jasoet.extractor.app.command.handler

import id.jasoet.extractor.app.command.AddCommand
import id.jasoet.extractor.app.printc
import id.jasoet.extractor.app.scan
import id.jasoet.extractor.app.service.DocumentService
import id.jasoet.extractor.app.workingDirectory
import id.jasoet.extractor.core.util.measureExecutionNano
import nl.komponents.kovenant.all
import nl.komponents.kovenant.functional.bind
import nl.komponents.kovenant.then
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

        val directoryScanner: DirectoryScanner =
            DirectoryScanner()
                .scan(
                    baseDir = workingDirectory(),
                    includes = command.files
                )

        val foundFiles: List<File> = directoryScanner.includedFiles.map { file ->
            File(directoryScanner.basedir, file)
        }

        printc(Ansi.Color.MAGENTA) {
            "Found ${foundFiles.size} files"
        }

        val listStoredId = all(foundFiles.map {
            log.info("Processing ${it.absolutePath}")

            val result = measureExecutionNano {
                documentService.convertDocument(it.absolutePath, FileInputStream(it))
            }

            printc(Ansi.Color.GREEN) {
                "Processing ${it.absolutePath} for ${result.first} nsec"
            }
            result.second
        }) fail {
            log.error("${it.message} when convert Documents", it)
            printc(Ansi.Color.RED) {
                "Error When Convert Document: ${it.message}"
            }
        } bind { listPair ->
            all(listPair.map { doc ->
                log.info("Store Document ${doc.fileName} with id ${doc.id}")

                val result = measureExecutionNano {
                    documentService.storeDocument(doc)
                }

                printc(Ansi.Color.GREEN) {
                    "Store Document ${doc.fileName} with id ${doc.id} for ${result.first} nsec"
                }

                result.second
            })
        } fail {
            log.error("${it.message} when Store Documents", it)
            printc(Ansi.Color.RED) {
                "Error When Store Document: ${it.message}"
            }
        } success { listKey ->
            listKey.forEach { key ->
                log.info("Finish Processing Document with id ${key.id}")
                printc(Ansi.Color.GREEN) {
                    "Finish Processing Document with id ${key.id}"
                }
            }
        } then {
            it.map { it.id.toString() }
        }

        listStoredId.success {
            documentService
                .loadDocument(it)
                .fail {
                    log.error("${it.message} when Load Documents", it)
                    printc(Ansi.Color.RED) {
                        "Error When Load Document: ${it.message}"
                    }
                }
                .bind {
                    all(it.map { doc ->
                        log.info("Analyze Document with id ${doc.id}")

                        val result = measureExecutionNano {
                            documentService.processDocument(doc)
                        }

                        printc(Ansi.Color.GREEN) {
                            "Analyze Document with id ${doc.id} for ${result.first} nsec"
                        }
                        result.second

                    })
                }
                .fail {
                    log.error("${it.message} when Process Documents", it)
                    printc(Ansi.Color.RED) {
                        "Error When Process Document: ${it.message}"
                    }
                }
                .bind {
                    all(it.map { doc ->
                        log.info("Store Processed Document id ${doc.id} with ${doc.contentLinesAnalyzed.size} analyzed Lines")

                        val result = measureExecutionNano {
                            documentService.storeProcessedDocument(doc)
                        }

                        printc(Ansi.Color.GREEN) {
                            "Store Processed Document id ${doc.id} with ${doc.contentLinesAnalyzed.size} analyzed Lines for ${result.first} nsec"
                        }
                        result.second
                    })
                }
                .fail {
                    log.error("${it.message} when Store Processed Documents", it)
                    printc(Ansi.Color.RED) {
                        "Error When Store Processed Documents: ${it.message}"
                    }
                }
        }

    }

}
