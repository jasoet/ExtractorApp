package id.jasoet.extractor.app.component

import id.jasoet.extractor.core.dictionary.DictionaryContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */
@Component
open class DictionaryInitializer() : InitializingBean {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun afterPropertiesSet() {
        log.info("Initializing Dictionary Context")
        DictionaryContext.initialize()
    }
}