package id.jasoet.extractor.app

import id.jasoet.extractor.app.model.ExtractedDocument
import id.jasoet.extractor.core.dsl.Dsl
import id.jasoet.extractor.core.dsl.model.FieldResult
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner
import org.apache.commons.lang3.StringUtils
import org.apache.maven.shared.utils.io.DirectoryScanner
import org.fusesource.jansi.Ansi
import org.fusesource.jansi.AnsiConsole
import java.io.File
import java.io.FileInputStream
import java.util.Properties

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

fun workingDirectory(): String {
    return System.getProperty("user.dir")
}

fun homeDirectory(): String {
    return System.getProperty("user.home")
}

fun File.readProperties(): Properties {
    return FileInputStream(this).use {
        val properties = Properties()
        properties.load(it)
        properties
    }
}

fun printc(fgColor: Ansi.Color,
           bgColor: Ansi.Color = Ansi.Color.DEFAULT,
           text: () -> String) {
    val output = Ansi.ansi().fg(fgColor).bg(bgColor).a(text.invoke()).reset()
    AnsiConsole.out.println(output)
}

fun printc(text: Ansi.() -> Ansi) {
    val output = text.invoke(Ansi.ansi()).reset()
    AnsiConsole.out.println(output)
}

fun loadDSL(packageName: String = "id.jasoet.extractor.app.rule"): Map<String, Dsl> {
    val scanner = FastClasspathScanner(packageName)
        .scan()

    return scanner.getNamesOfSubclassesOf(Dsl::class.java).map {
        val dsl = Class.forName(it).newInstance() as Dsl
        dsl.name to dsl
    }.toMap()
}

val dslMap: Map<String, Dsl> by lazy {
    loadDSL()
}

fun DirectoryScanner.scan(baseDir: String,
                          includes: List<String> = emptyList(),
                          excludes: List<String> = emptyList(),
                          followSymLinks: Boolean = true,
                          caseSensitive: Boolean = true): DirectoryScanner {
    return this.apply {
        this.basedir = File(baseDir)
        if (includes.isNotEmpty()) {
            this.setIncludes(*includes.toTypedArray())
        }

        if (excludes.isNotEmpty()) {
            this.setExcludes(*excludes.toTypedArray())
        }

        this.setFollowSymlinks(followSymLinks)
        this.setCaseSensitive(caseSensitive)

        this.scan()
    }
}

fun String.leftPad(len: Int, ch: String = " "): String {
    return StringUtils.leftPad(this, len, ch)
}

fun String.rightPad(len: Int, ch: String = " "): String {
    return StringUtils.rightPad(this, len, ch)
}

fun String.center(len: Int, ch: String = " "): String {
    return StringUtils.center(this, len, ch)
}

fun List<FieldResult>.toExtractedDocument(id: String, dslName: String): ExtractedDocument {
    val results = this.map { it.name to it.result }.toMap()

    val errors = this
        .filter { it.exception != null }
        .map {
            val (name, result, ex) = it
            if (ex == null) {
                name to ""
            } else {
                val message = "${ex.javaClass.canonicalName}[${ex.message}]"
                name to message
            }
        }
        .toMap()

    return ExtractedDocument(id, dslName, results, errors)
}

