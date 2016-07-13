package id.jasoet.extractor.app

import id.jasoet.extractor.core.dsl.Dsl
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner
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

fun scanDsl(): List<String> {
    val scanner = FastClasspathScanner("id.jasoet.extractor.app.rule")
        .scan()

    return scanner.getNamesOfSubclassesOf(Dsl::class.java)
}

fun dslObjects(): List<Pair<String, Dsl>> {
    return scanDsl().map {
        it to Class.forName(it).newInstance() as Dsl
    }
}