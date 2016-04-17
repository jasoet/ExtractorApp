package id.jasoet.extractor.engine

import org.slf4j.LoggerFactory

/**
 * Documentation Here
 *
 * @author Deny Prasetyo.
 */

object EngineApplication {
    private val log = LoggerFactory.getLogger(EngineApplication::class.java)

    @JvmStatic
    fun main(args: Array<String>) {
        log.info("Do Nothing Only To Satisfy Spring Boot Plugin")
    }
}