package id.jasoet.extractor.app.command

import com.beust.jcommander.Parameter
import com.beust.jcommander.Parameters

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */
@Parameters(commandDescription = "Show Stored Document. Only Show Processed Document by Default")
data class ShowCommand(
    @Parameter(description = "Document Ids")
    val ids: MutableList<String> = arrayListOf(),
    @Parameter(names = kotlin.arrayOf("-a", "--all"), description = "Show Documents and Processed Documents")
    val all: Boolean = false,
    @Parameter(names = kotlin.arrayOf("-d", "--document"), description = "Only Show Documents")
    val onlyDocument: Boolean = false
)