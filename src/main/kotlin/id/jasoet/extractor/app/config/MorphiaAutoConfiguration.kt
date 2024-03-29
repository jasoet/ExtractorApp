package id.jasoet.extractor.app.config

import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Morphia
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Configuration
@EnableConfigurationProperties(MongoProperties::class)
@EnableAutoConfiguration(exclude = arrayOf(DataSourceAutoConfiguration::class))
open class MorphiaAutoConfiguration {

    @Bean
    open fun mongoClient(mongoProperties: MongoProperties, environment: Environment): MongoClient {
        val mongoClientOption = MongoClientOptions.builder().build();
        return mongoProperties.createMongoClient(mongoClientOption, environment)
    }

    @Bean
    open fun dataStore(mongoClient: MongoClient, morphiaProperties: MorphiaProperties): Datastore {
        val morphia = Morphia()

        morphia.mapPackage(morphiaProperties.basePackage, true)

        return morphia.createDatastore(mongoClient, morphiaProperties.dataStoreId).apply {
            ensureIndexes()
        }
    }
}