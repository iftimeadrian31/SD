package com.sd.laborator.components

import com.sd.laborator.interfaces.LibraryDAO
import com.sd.laborator.interfaces.LibraryPrinter
import com.sd.laborator.model.Book
import com.sd.laborator.model.Content
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class LibraryAppComponent {
    @Autowired
    private lateinit var libraryDAO: LibraryDAO

    @Autowired
    private lateinit var libraryPrinter: LibraryPrinter

    @Autowired
    private lateinit var connectionFactory: RabbitMqConnectionFactoryComponent
    private lateinit var amqpTemplate: AmqpTemplate

    @Autowired
    fun initTemplate() {
        this.amqpTemplate = connectionFactory.rabbitTemplate()
    }

    fun sendMessage(msg: String) {
        this.amqpTemplate.convertAndSend(connectionFactory.getExchange(),
                                         connectionFactory.getRoutingKey(),
                                         msg)
    }

    @RabbitListener(queues = ["\${libraryapp.rabbitmq.queue}"])
    fun recieveMessage(msg: String) {
        // the result needs processing
        val processedMsg = (msg.split(",").map { it.toInt().toChar() }).joinToString(separator="")
        try {
            val (function, parameter) = processedMsg.split(":")
            val result: String? = when(function) {
                "print" -> customPrint(parameter)
                "find" -> { val (value,type)=parameter.split(" ")
                    customFind(value,type)}
                "publish" -> { val(title,author,publisher,text)=parameter.split("*")
                                val(_,title_val)=title.split("=")
                                val(_,author_val)=author.split("=")
                                val(_,publisher_val)=publisher.split("=")
                                val(_,text_val)=text.split("=")
                                val continut= Content(author_val,text_val,title_val,publisher_val)
                                val carte=Book(continut)
                                addBook(carte)
                                "Book added"}
                else -> null
            }
            if (result != null) sendMessage(result)
        } catch (e: Exception) {
            println(e)
        }
    }

    fun customPrint(format: String): String {
        return when(format) {
            "html" -> libraryPrinter.printHTML(libraryDAO.getBooks())
            "json" -> libraryPrinter.printJSON(libraryDAO.getBooks())
            "xml" -> libraryPrinter.printXML(libraryDAO.getBooks())
            "raw" -> libraryPrinter.printRaw(libraryDAO.getBooks())
            else -> "Not implemented"
        }
    }

    fun customFind(searchParameter: String,type:String): String {
        val (field, value) = searchParameter.split("=")
        return when(field) {
            "author" -> when(type){
                "html" -> this.libraryPrinter.printHTML(this.libraryDAO.findAllByAuthor(value))
                "json" -> this.libraryPrinter.printJSON(this.libraryDAO.findAllByAuthor(value))
                "xml" -> this.libraryPrinter.printXML(this.libraryDAO.findAllByAuthor(value))
                "raw" -> this.libraryPrinter.printRaw(this.libraryDAO.findAllByAuthor(value))
                else -> "buton failed"

            }
            "title" -> when(type){
                "html" -> this.libraryPrinter.printHTML(this.libraryDAO.findAllByTitle(value))
                "json" -> this.libraryPrinter.printJSON(this.libraryDAO.findAllByTitle(value))
                "xml" -> this.libraryPrinter.printXML(this.libraryDAO.findAllByTitle(value))
                "raw" -> this.libraryPrinter.printRaw(this.libraryDAO.findAllByTitle(value))
                else -> "buton failed"

            }
            "publisher" -> when(type){
                "html" -> this.libraryPrinter.printHTML(this.libraryDAO.findAllByPublisher(value))
                "json" -> this.libraryPrinter.printJSON(this.libraryDAO.findAllByPublisher(value))
                "xml" -> this.libraryPrinter.printXML(this.libraryDAO.findAllByPublisher(value))
                "raw" -> this.libraryPrinter.printRaw(this.libraryDAO.findAllByPublisher(value))
                else -> "buton failed"

            }
            else -> "Not a valid field"
        }
    }

    fun addBook(book: Book): Boolean {
        return try {
            this.libraryDAO.addBook(book)
            true
        } catch (e: Exception) {
            false
        }
    }

}