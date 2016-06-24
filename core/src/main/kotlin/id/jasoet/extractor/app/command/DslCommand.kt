package id.jasoet.extractor.app.command

import com.beust.jcommander.Parameter
import com.beust.jcommander.Parameters

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */
@Parameters(commandDescription = "Show and Assign DSL to Files")
data class DslCommand(
    @Parameter(description = "DSL Ids")
    val ids: MutableList<String> = arrayListOf()
)