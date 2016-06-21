package id.jasoet.extractor.app.service

import id.jasoet.extractor.app.loader.loadDocumentModel
import id.jasoet.extractor.app.model.DocumentModel
import id.jasoet.extractor.app.model.ProcessedDocument
import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.task
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Key
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.io.InputStream

/**
 * Service for Document using MongoDB
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

    fun loadDocument(id: String): Promise<DocumentModel, Exception> {
        return task {
            dataStore.get(DocumentModel::class.java, id)
        } fail {
            log.error("${it.message} when load document [$id]", it)
        }
    }

    fun storeProcessedDocument(processedDocument: ProcessedDocument): Promise<Key<ProcessedDocument>, Exception> {
        return task {
            dataStore.save(processedDocument)
        } fail {
            log.error("${it.message} when store processed document [${processedDocument.id}]", it)
        }
    }

    fun loadProcessedDocument(id: String): Promise<ProcessedDocument, Exception> {
        return task {
            dataStore.get(ProcessedDocument::class.java, id)
        } fail {
            log.error("${it.message} when load processed document [$id]", it)
        }
    }

    fun convertAndProcessDocument(fileName: String,inputStream: InputStream){

    }
}