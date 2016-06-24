package id.jasoet.extractor.app.command.handler

import id.jasoet.extractor.app.command.ShowCommand
import org.springframework.stereotype.Component

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Component
class ShowHandler {

    fun handle(command: ShowCommand) {
        println("Process Show Command $command")
    }
}