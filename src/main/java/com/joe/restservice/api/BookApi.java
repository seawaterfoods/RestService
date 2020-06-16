package com.joe.restservice.api;

import com.joe.restservice.domain.Book;
import com.joe.restservice.dto.BookDTO;
import com.joe.restservice.exception.InvalidRequestException;
import com.joe.restservice.exception.NotFoundException;
import com.joe.restservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class BookApi {

    @Autowired
    private BookService bookService;

//    獲取書單列表
    @GetMapping("/books")
    public ResponseEntity<?> listAllBooks(){
        List<Book> books =bookService.findAllBooks();
        if (books.isEmpty()){
            throw new NotFoundException("Books Not Found");
        }
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

//    獲取一個書單
    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(@PathVariable Long id){
        Book book = bookService.getBookById(id);

        if (book==null){
            throw new NotFoundException(String.format("boo by id %s",id));
        }
        return new ResponseEntity<Object>(book,HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<?> saveBook(@Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new InvalidRequestException("Ivliad parameter",bindingResult);
        }

        Book book1 = bookService.saveBook(bookDTO.convertToBook());

        return new ResponseEntity<Object>(book1,HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id,@Valid @RequestBody BookDTO bookDTO,BindingResult bindingResult){

        Book currentBook = bookService.getBookById(id);
        if (currentBook==null){
            throw new NotFoundException(String.format("boo by id %s",id));
        }
        if (bindingResult.hasErrors()){
            throw new InvalidRequestException("Ivliad parameter",bindingResult);
        }
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
