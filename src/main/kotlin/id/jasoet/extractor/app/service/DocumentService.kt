package id.jasoet.extractor.app.service

import com.mongodb.WriteResult
import id.jasoet.extractor.app.dslMap
import id.jasoet.extractor.app.loader.loadDocumentModel
import id.jasoet.extractor.app.model.DocumentModel
import id.jasoet.extractor.app.model.ExtractedDocument
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

    fun processDocument(document: DocumentModel): Promise<ProcessedDocument, Exception> {
        return task {
            document.processDocument()
        } fail {
            log.error("${it.message} when process document ${document.id}", it)
        }
    }


    fun convertAndProcessDocument(fileName: String, inputStream: InputStream): Promise<Pair<DocumentModel, ProcessedDocument>, Exception> {
        return convertDocument(fileName, inputStream) then {
            it to it.processDocument()
        } fail {
            log.error("${it.message} when Convert and Process Document ", it)
        }
    }

    fun extractDocument(processedDocument: ProcessedDocument, dslName: String): Promise<ExtractedDocument, Exception> {
        return task {
            val dsl = dslMap[dslName] ?: throw IllegalArgumentException("DSL $dslName not Found")
            val fieldResults = dsl.extract(processedDocument.contentLinesAnalyzed)

            val results = fieldResults.map { it.name to it.result }.toMap()

            val errors = fieldResults
                .filter { it.exception != null }
                .map {
                    val (name, result, ex) = it
                    if (ex == null) {
                        name to ""
                    } else {
                        val message = "${ex.javaClass.canonicalName}[${ex.message}]"
                        name to message
                    }
                }
                .toMap()

            ExtractedDocument(processedDocument.id, dslName, results, errors)
        } fail {
            log.error("${it.message} when Extract Processed Document  [${processedDocument.id}]", it)
        }
    }

    fun storeExtractedDocument(extractedDocument: ExtractedDocument): Promise<Key<ExtractedDocument>, Exception> {
        return task {
            dataStore.save(extractedDocument)
        } fail {
            log.error("${it.message} when save extracted document [${extractedDocument.id}]", it)
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

    fun storeDocument(documentModels: List<DocumentModel>): Promise<List<Key<DocumentModel>>, Exception> {
        return task {
            dataStore.save(documentModels).toList()
        } fail {
            log.error("${it.message} when save document [${documentModels.map { it.id }}]", it)
        }
    }

    fun loadDocument(id: String): Promise<DocumentModel, Exception> {
        return task {
            dataStore.get(DocumentModel::class.java, id)
        } fail {
            log.error("${it.message} when load document [$id]", it)
        }
    }

    fun loadDocument(ids: List<String>): Promise<List<DocumentModel>, Exception> {
        return task {
            dataStore.get(DocumentModel::class.java, ids).toList()
        } fail {
            log.error("${it.message} when load document [$ids]", it)
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

    fun storeProcessedDocument(processedDocuments: List<ProcessedDocument>): Promise<List<Key<ProcessedDocument>>, Exception> {
        return task {
            dataStore.save(processedDocuments).toList()
        } fail {
            log.error("${it.message} when store processed document [${processedDocuments.map { it.id }}]", it)
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

}