package id.jasoet.extractor.app.component

import com.beust.jcommander.JCommander
import id.jasoet.extractor.app.command.AddCommand
import id.jasoet.extractor.app.command.DslCommand
import id.jasoet.extractor.app.command.ShowCommand
import id.jasoet.extractor.app.command.handler.AddHandler
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
open class CommandHandler : CommandLineRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var commander: JCommander

    @Autowired
    lateinit var addHandler: AddHandler
    @Autowired
    lateinit var dslHandler: DslHandler
    @Autowired
    lateinit var showHandler: ShowHandler

    @Autowired
    lateinit var addCommand: AddCommand
    @Autowired
    lateinit var dslCommand: DslCommand
    @Autowired
    lateinit var showCommand: ShowCommand

    override fun run(vararg args: String?) {
        commander.parse(*args)
        val command = commander.parsedCommand?.toLowerCase()

        when (command) {
            "add" -> addHandler.handle(addCommand)
            "dsl" -> dslHandler.handle(dslCommand)
            "show" -> showHandler.handle(showCommand)
            else -> {
                commander.usage()
            }
        }
    }
}