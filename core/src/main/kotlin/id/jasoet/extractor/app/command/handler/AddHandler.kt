package id.jasoet.extractor.app.command.handler

import id.jasoet.extractor.app.command.AddCommand
import id.jasoet.extractor.app.printc
import id.jasoet.extractor.app.workingDirectory
import org.fusesource.jansi.Ansi
import org.springframework.stereotype.Component

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Component
class AddHandler {

    fun handle(command: AddCommand) {
        printc(Ansi.Color.GREEN) {
            "Working Dir: ${workingDirectory()}"
        }
        printc(Ansi.Color.RED) {
            "Process Add Command $command"
        }
    }

}
