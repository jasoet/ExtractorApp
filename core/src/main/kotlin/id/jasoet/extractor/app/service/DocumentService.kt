package id.jasoet.extractor.app.service

import id.jasoet.extractor.app.loader.loadDocumentModel
import id.jasoet.extractor.app.model.DocumentModel
import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.task
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Key
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.io.InputStream

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Repository
class DocumentService {
    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var dataStore: Datastore

    fun convertDocument(fileName: String, inputStream: InputStream): Promise<DocumentModel, Exception> {
        return task {
            inputStream.use {
                it.loadDocumentModel(fileName)
            }
        } fail {
            log.error("${it.message} when load document $fileName", it)
        }
    }

    fun storeDocument(documentModel: DocumentModel): Promise<Key<DocumentModel>, Exception> {
        return task {
            dataStore.save(documentModel)
        } fail {
            log.error("${it.message} when save document [${documentModel.id}]", it)
        }
    }
}