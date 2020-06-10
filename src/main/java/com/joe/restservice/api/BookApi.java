package com.joe.restservice.api;

import com.joe.restservice.domain.Book;
import com.joe.restservice.dto.BookDTO;
import com.joe.restservice.service.BookService;
import com.joe.restservice.util.CusotmBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class BookApi {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<?> listAllBooks(){
        List<Book> books =bookService.findAllBooks();
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(@PathVariable Long id){
        Book book = bookService.getBookById(id);
        return new ResponseEntity<Object>(book,HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<?> saveBook(@RequestBody Book book){
        Book book1 = bookService.saveBook(book);
        return new ResponseEntity<Object>(book1,HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id,@RequestBody BookDTO bookDTO){
        Book currentBook = bookService.getBookById(id);
        bookDTO.convertToBook(currentBook);
        Book book1 = bookService.updateBook(currentBook);
        return new ResponseEntity<Object>(book1,HttpStatus.OK);
    }


    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        bookService.deleteBookById(id);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/books")
    public ResponseEntity<?> deleteAllBooks(){
        bookService.findAllBooks();
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }


}
