package id.jasoet.extractor.app.command.handler

import id.jasoet.extractor.app.command.AddCommand
import id.jasoet.extractor.app.printc
import id.jasoet.extractor.app.workingDirectory
import org.springframework.stereotype.Component

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Component
class AddHandler {

    fun handle(command: AddCommand) {
        printc {
            fgBrightGreen().a("Working Dir: ${workingDirectory()}")
        }
        printc {
            fgBrightRed().a("Process Add Command $command")
        }
    }

}
