package id.jasoet.extractor.app

import id.jasoet.extractor.app.model.LineModel
import id.jasoet.extractor.core.document.line.Line

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

fun currentDirectory(): String {
    return System.getProperty("user.dir")
}

fun List<Line>.toLineModel(): List<LineModel> {
    return this.map {
        LineModel(it.type, it.content, it.annotations)
    }
}
