package id.jasoet.extractor.app.component

import com.beust.jcommander.JCommander
import id.jasoet.extractor.app.command.AddCommand
import id.jasoet.extractor.app.command.DirectCommand
import id.jasoet.extractor.app.command.DslCommand
import id.jasoet.extractor.app.command.ShowCommand
import id.jasoet.extractor.app.command.handler.AddHandler
import id.jasoet.extractor.app.command.handler.DirectHandler
import id.jasoet.extractor.app.command.handler.DslHandler
import id.jasoet.extractor.app.command.handler.ShowHandler
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */
@Component
open class CommandHandler
@Autowired
constructor(val commander: JCommander,
            val addHandler: AddHandler,
            val dslHandler: DslHandler,
            val showHandler: ShowHandler,
            val directHandler: DirectHandler,
            val addCommand: AddCommand,
            val dslCommand: DslCommand,
            val showCommand: ShowCommand,
            val directCommand: DirectCommand) : CommandLineRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        commander.parse(*args)
        val command = commander.parsedCommand?.toLowerCase()

        when (command) {
            "add" -> addHandler.handle(addCommand)
            "dsl" -> dslHandler.handle(dslCommand)
            "show" -> showHandler.handle(showCommand)
            "direct" -> directHandler.handle(directCommand)
            else -> {
                commander.usage()
            }
        }
    }
}