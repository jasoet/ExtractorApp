package id.jasoet.extractor.app.config

import com.beust.jcommander.JCommander
import id.jasoet.extractor.app.command.AddCommand
import id.jasoet.extractor.app.command.DirectCommand
import id.jasoet.extractor.app.command.DslCommand
import id.jasoet.extractor.app.command.ShowCommand
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Configuration
open class CommandConfig {

    @Bean(name = arrayOf("commander"))
    open fun commander(): JCommander {
        val commander = JCommander()
        commander.addCommand("add", addCommand())
        commander.addCommand("dsl", dslCommand())
        commander.addCommand("show", showCommand())
        commander.addCommand("direct", directCommand())

        return commander
    }

    @Bean
    open fun addCommand(): AddCommand {
        return AddCommand()
    }

    @Bean
    open fun dslCommand(): DslCommand {
        return DslCommand()
    }

    @Bean
    open fun showCommand(): ShowCommand {
        return ShowCommand()
    }

    @Bean
    open fun directCommand(): DirectCommand {
        return DirectCommand()
    }
}