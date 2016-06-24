package id.jasoet.extractor.app.config

import com.beust.jcommander.JCommander
import id.jasoet.extractor.app.command.AddCommand
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
        commander.addCommand("add",AddCommand())
        commander.addCommand("dsl",DslCommand())
        commander.addCommand("show",ShowCommand())

        return commander
    }
}