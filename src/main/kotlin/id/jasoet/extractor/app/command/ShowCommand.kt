package id.jasoet.extractor.app.command

import com.beust.jcommander.Parameter
import com.beust.jcommander.Parameters

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Parameters(commandDescription = "Show Stored Document. Only Show Documents details by Default")
data class ShowCommand(
    @Parameter(description = "Document Ids")
    val ids: MutableList<String> = arrayListOf(),
    @Parameter(names = kotlin.arrayOf("-c", "--content"), description = "Show Document Content")
    val showContent: Boolean = false,
    @Parameter(names = kotlin.arrayOf("-p", "--processed"), description = "Show Processed Documents")
    val showProcessed: Boolean = false,
    @Parameter(names = kotlin.arrayOf("-e", "--extracted"), description = "Show Extracted Documents")
    val showExtracted: Boolean = false,
    @Parameter(names = kotlin.arrayOf("-a", "--all"), description = "Show Processed and Extracted Documents")
    val showAll: Boolean = false
)