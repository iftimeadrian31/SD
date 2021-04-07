package com.sd.laborator.microservices

import com.sd.laborator.interfaces.LibraryDAO
import com.sd.laborator.interfaces.LibraryPrinter
import com.sd.laborator.model.Book
import com.sd.laborator.model.Content
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class LibraryPrinterMicroservice {
    @Autowired
    private lateinit var libraryDAO: LibraryDAO

    @Autowired
    private lateinit var libraryPrinter: LibraryPrinter


    @RequestMapping("/print", method = [RequestMethod.GET])
    @ResponseBody
    fun customPrint(@RequestParam(required = true, name = "format", defaultValue = "") format: String): String {
        //libraryDAO.deleteBookTable()
        //libraryDAO.createBookTable()
        //libraryDAO.addBook(Book(Content(1,"Roberto Ierusalimschy","Preface. When Waldemar, Luiz, and I started the development of Lua, back in 1993, we could hardly imagine that it would spread as it did. ...","Programming in LUA","Teora")))
        //libraryDAO.addBook(Book(Content(2,"Jules Verne","Nemaipomeniti sunt francezii astia! - Vorbiti, domnule, va ascult! ....","Steaua Sudului","Corint")))
        //libraryDAO.addBook(Book(Content(3,"Jules Verne","Cuvant Inainte. Imaginatia copiilor - zicea un mare poet romantic spaniol - este asemenea unui cal nazdravan, iar curiozitatea lor e pintenul ce-l fugareste prin lumea celor mai indraznete proiecte.","O calatorie spre centrul pamantului","Polirom")))
        //libraryDAO.addBook(Book(Content(4,"Jules Verne","Partea intai. Naufragiatii vazduhului. Capitolul 1. Uraganul din 1865. ...","Insula Misterioasa","Teora")))
        //libraryDAO.addBook(Book(Content(5,"Jules Verne","Capitolul I. S-a pus un premiu pe capul unui om. Se ofera premiu de 2000 de lire ...","Casa cu aburi","Albatros")))

        return when(format) {
            "html" -> libraryPrinter.printHTML(libraryDAO.getBooks() as Set<Book>)
            "json" -> libraryPrinter.printJSON(libraryDAO.getBooks() as Set<Book>)
            "raw" -> libraryPrinter.printRaw(libraryDAO.getBooks() as Set<Book>)
            else -> "Not implemented"
        }
    }

    @RequestMapping("/find", method = [RequestMethod.GET])
    @ResponseBody
    fun customFind(@RequestParam(required = false, name = "author", defaultValue = "") author: String,
                   @RequestParam(required = false, name = "title", defaultValue = "") title: String,
                   @RequestParam(required = false, name = "publisher", defaultValue = "") publisher: String): String {
        if (author != "")
            return this.libraryPrinter.printJSON(this.libraryDAO.findAllByAuthor(author) as Set<Book>)
        if (title != "")
            return this.libraryPrinter.printJSON(this.libraryDAO.findAllByTitle(title) as Set<Book>)
        if (publisher != "")
            return this.libraryPrinter.printJSON(this.libraryDAO.findAllByPublisher(publisher) as Set<Book>)
        return "Not a valid field"
    }

}