package id.jasoet.extractor.app.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@ConfigurationProperties(prefix = "spring.data.mongodb.morphia")
data class MorphiaProperties(
    val basePackage: String = "",
    val dataStoreId: String = "morphiaDataStoreId"
)