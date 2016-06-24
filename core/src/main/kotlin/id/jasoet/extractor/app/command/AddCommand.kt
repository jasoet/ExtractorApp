package id.jasoet.extractor.app.command

import com.beust.jcommander.Parameter
import com.beust.jcommander.Parameters

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Parameters(commandDescription = "Add Files to be processed, support wildcard")
data class AddCommand(
    @Parameter(description = "File/Directories")
    val files: MutableList<String> = arrayListOf(),
    @Parameter(names = arrayOf("-d", "--dsl"), description = "DSL to be Used for Processing the Files, if empty files will not be processed")
    val dsl: String = "")