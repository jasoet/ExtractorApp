package id.jasoet.extractor.app.command.handler

import id.jasoet.extractor.app.center
import id.jasoet.extractor.app.command.DirectCommand
import id.jasoet.extractor.app.dslMap
import id.jasoet.extractor.app.leftPad
import id.jasoet.extractor.app.loader.loadDocumentModel
import id.jasoet.extractor.app.printc
import id.jasoet.extractor.app.rightPad
import id.jasoet.extractor.app.scan
import id.jasoet.extractor.app.toExtractedDocument
import id.jasoet.extractor.app.workingDirectory
import org.apache.maven.shared.utils.io.DirectoryScanner
import org.fusesource.jansi.Ansi
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileInputStream
import kotlin.system.measureTimeMillis

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Component
class DirectHandler {
    private val log = LoggerFactory.getLogger(javaClass)

    fun handle(command: DirectCommand) {
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

        val directResults = foundFiles.map { file ->
            val milliSeconds = measureTimeMillis {
                val doc = FileInputStream(file).use {
                    it.loadDocumentModel(file.absolutePath)
                }

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

                printc(Ansi.Color.BLUE) {
                    """
                    |
                    |${"Content".center(30, "=")}
                    |${doc.content}
                    |
                    |""".trimMargin()
                }

                val processedDocument = doc.processDocument()

                printc(Ansi.Color.YELLOW) {
                    "Document Parts".center(30, "=")
                }

                processedDocument.contentLinesAnalyzed.forEach { line ->
                    printc {
                        val type = line.type.toString().trim().leftPad(10)
                        fgRed().a(type)
                            .fgBrightYellow().a("|\t")
                            .fgBrightBlue().a(line.content.trim())
                    }
                }

                val dsl = dslMap[command.dsl] ?: throw IllegalArgumentException("DSL ${command.dsl} Not Found!")

                val extractedDocument = dsl
                    .extract(processedDocument.contentLinesAnalyzed)
                    .toExtractedDocument(doc.id, command.dsl)

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

            file.absolutePath to milliSeconds
        }

        printc(Ansi.Color.YELLOW) {
            "SUMARRY".center(100, "=")
        }

        directResults.forEach {
            val (fileName, ms) = it
            printc {
                fgRed().a(fileName)
                    .fgBrightYellow().a(" => ")
                    .fgBrightBlue().a("$ms ms")
            }
        }

    }

}
