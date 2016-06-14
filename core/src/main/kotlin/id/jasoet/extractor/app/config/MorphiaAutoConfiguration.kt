package id.jasoet.extractor.app.config

import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Morphia
import org.mongodb.morphia.annotations.Entity
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.type.filter.AnnotationTypeFilter

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Configuration
@EnableConfigurationProperties(MongoProperties::class)
open class MorphiaAutoConfiguration {

    @Bean
    open fun mongoClient(mongoProperties: MongoProperties, environment: Environment): MongoClient {
        val mongoClientOption = MongoClientOptions.builder().build();
        return mongoProperties.createMongoClient(mongoClientOption, environment)
    }

    @Bean
    open fun dataStore(mongoClient: MongoClient, morphiaProperties: MorphiaProperties): Datastore {
        val morphia = Morphia()

        val scanner = ClassPathScanningCandidateComponentProvider(true)
        scanner.addIncludeFilter(AnnotationTypeFilter(Entity::class.java))

        val components = scanner.findCandidateComponents(morphiaProperties.basePackage)
        val componentClassNames = components.map {
            Class.forName(it.beanClassName)
        }

        morphia.map(*componentClassNames.toTypedArray())

        return morphia.createDatastore(mongoClient, morphiaProperties.dataStoreId).apply {
            ensureIndexes()
        }
    }
}