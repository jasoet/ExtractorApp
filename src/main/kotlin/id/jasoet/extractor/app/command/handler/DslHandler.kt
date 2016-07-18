package id.jasoet.extractor.app.command.handler

import id.jasoet.extractor.app.command.DslCommand
import id.jasoet.extractor.app.dslMap
import id.jasoet.extractor.app.printc
import id.jasoet.extractor.core.dsl.Dsl
import org.fusesource.jansi.Ansi
import org.springframework.stereotype.Component

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Component
class DslHandler {

    fun printDsl(data: Pair<String, Dsl>) {
        val (name, dsl) = data
        val ruleNames = dsl.getFieldRules()
            .map {
                it.name
            }
            .joinToString("\n\t")

        printc(Ansi.Color.BLUE) {
            """
            |Name = $name
            |Class = ${dsl.className}
            |Rule Names =
            |    $ruleNames

            """.trimMargin()
        }
    }

    fun handle(command: DslCommand) {

        if (command.ids.isNotEmpty()) {
            printc(Ansi.Color.GREEN) {
                "Show DSLs with id [${command.ids.joinToString(", ")}]"
            }
            printc(Ansi.Color.RED) {
                "==============================="
            }

            dslMap.filter { command.ids.contains(it.key) }.forEach {
                printDsl(it.toPair())
            }
        } else {
            printc(Ansi.Color.GREEN) {
                "Show All DSLs"
            }
            printc(Ansi.Color.RED) {
                "==============================="
            }

            dslMap.forEach {
                printDsl(it.toPair())
            }
        }
    }
}