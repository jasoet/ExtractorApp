package id.jasoet.extractor.app

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.io.File

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
            initialize()
            SpringApplication.run(ExtractorBootApplication::class.java, *args)
        }

        private fun initialize() {
            val configFile = File("${homeDirectory()}/.extractor.properties")

            if (configFile.exists()) {
                val properties = configFile.readProperties()

                properties.getProperty("mongo.host").let {
                    if (!it.isNullOrBlank())
                        System.setProperty("spring.data.mongodb.host", it)
                }

                properties.getProperty("mongo.database").let {
                    if (!it.isNullOrBlank())
                        System.setProperty("spring.data.mongodb.database", it)
                }

                properties.getProperty("mongo.username").let {
                    if (!it.isNullOrBlank())
                        System.setProperty("spring.data.mongodb.username", it)
                }

                properties.getProperty("mongo.password").let {
                    if (!it.isNullOrBlank())
                        System.setProperty("spring.data.mongodb.password", it)
                }
            }

        }
    }
}
