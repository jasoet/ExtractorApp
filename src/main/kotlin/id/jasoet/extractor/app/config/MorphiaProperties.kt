package id.jasoet.extractor.app.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Component
@ConfigurationProperties(prefix = "spring.data.mongodb.morphia")
data class MorphiaProperties(
    var basePackage: String = "",
    var dataStoreId: String = "morphiaDataStoreId"
)