package id.jasoet.extractor.kommander

import com.beust.jcommander.Parameter

/**
 * TODO: Documentation
 *
 * @author Deny Prasetyo.
 */


data class KommanderExample(
    @Parameter(description = "services")
    val parameters: MutableList<String> = arrayListOf(),
    @Parameter(names = arrayOf("-log", "-verbose"), description = "Level of verbosity")
    val verbose: Int = 1,
    @Parameter(names = arrayOf("-groups"), description = "Comma-separated list of group names to be run")
    val groups: String = "",
    @Parameter(names = arrayOf("-debug"), description = "Debug mode")
    val debug: Boolean = false)