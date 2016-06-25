package id.jasoet.extractor.app.service

import com.mongodb.WriteResult
import id.jasoet.extractor.app.loader.loadDocumentModel
import id.jasoet.extractor.app.model.DocumentModel
import id.jasoet.extractor.app.model.ProcessedDocument
import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.all
import nl.komponents.kovenant.task
import nl.komponents.kovenant.then
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Key
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStream

/**
 * Service for Document using MongoDB
 *
 * @author Deny Prasetyo
 */

@Service
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

    fun removeDocument(id: String): Promise<List<WriteResult>, Exception> {

        val removeAllDocument = task {
            dataStore.delete(DocumentModel::class.java, id)
        } fail {
            log.error("${it.message} when remove document [$id]", it)
        }

        val removeAllProcessedDocument = task {
            dataStore.delete(ProcessedDocument::class.java, id)
        } fail {
            log.error("${it.message} when remove processed document [$id]", it)
        }

        return all(removeAllDocument, removeAllProcessedDocument)
    }

    fun removeAllDocument(): Promise<List<WriteResult>, Exception> {

        val removeAllDocument = task {
            val allDocument = dataStore.createQuery(DocumentModel::class.java)
            dataStore.delete(allDocument)
        } fail {
            log.error("${it.message} when remove  all document", it)
        }

        val removeAllProcessedDocument = task {
            val allProcessed = dataStore.createQuery(ProcessedDocument::class.java)
            dataStore.delete(allProcessed)
        } fail {
            log.error("${it.message} when remove all processed document ", it)
        }

        return all(removeAllDocument, removeAllProcessedDocument)
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

    fun loadAllDocument(): Promise<List<DocumentModel>, Exception> {
        return task {
            dataStore.createQuery(DocumentModel::class.java).asList()
        } fail {
            log.error("${it.message} when load all document", it)
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

    fun loadAllProcessedDocument(): Promise<List<ProcessedDocument>, Exception> {
        return task {
            dataStore.createQuery(ProcessedDocument::class.java).asList()
        } fail {
            log.error("${it.message} when load all processed document", it)
        }
    }

    fun convertAndProcessDocument(fileName: String, inputStream: InputStream): Promise<Pair<DocumentModel, ProcessedDocument>, Exception> {
        return convertDocument(fileName, inputStream) then {
            it  to it.processDocument()
        } fail {
            log.error("${it.message} when Convert and Process Document ", it)
        }
    }
}