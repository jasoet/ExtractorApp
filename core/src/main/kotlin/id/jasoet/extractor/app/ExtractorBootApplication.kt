package id.jasoet.extractor.app

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */


@SpringBootApplication
open class ExtractorBootApplication {
    companion object {
        private val log = LoggerFactory.getLogger(ExtractorBootApplication::class.java)
        @JvmStatic
        fun main(args: Array<String>) {
            val ctx = SpringApplication.run(ExtractorBootApplication::class.java, *args)
            log.info("Application Running ${ctx.isActive}")
        }
    }
}