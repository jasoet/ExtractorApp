package id.jasoet.extractor.app.command

import com.beust.jcommander.Parameter
import com.beust.jcommander.Parameters

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */
@Parameters(commandDescription = "Show DSLs")
data class DslCommand(
    @Parameter(description = "DSL Ids")
    val ids: MutableList<String> = arrayListOf()
)