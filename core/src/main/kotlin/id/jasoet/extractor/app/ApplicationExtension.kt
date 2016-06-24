package id.jasoet.extractor.app

import id.jasoet.extractor.app.model.LineModel
import id.jasoet.extractor.core.document.line.Line
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

fun List<Line>.toLineModel(): List<LineModel> {
    return this.map {
        LineModel(it.type, it.content, it.annotations)
    }
}

fun File.readProperties(): Properties {
    return FileInputStream(this).use {
        val properties = Properties()
        properties.load(it)
        properties
    }
}
