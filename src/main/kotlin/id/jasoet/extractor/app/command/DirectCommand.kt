package id.jasoet.extractor.app.command

import com.beust.jcommander.Parameter
import com.beust.jcommander.Parameters

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Parameters(commandDescription = "Direct process file, support wildcard")
data class DirectCommand(
    @Parameter(description = "File/Directories from working directories")
    val files: MutableList<String> = arrayListOf(),
    @Parameter(names = arrayOf("-d", "--dsl"), description = "DSL to be Used for Processing the Files")
    val dsl: String = "PoliceReport")