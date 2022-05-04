package com.codegym.controller;

import com.codegym.model.Book;
import com.codegym.model.Category;
import com.codegym.service.book.IBookService;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class AjaxBookController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

    @PostMapping("")
    public ResponseEntity<Book> create(@RequestBody Book book){
        bookService.save(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ModelAndView showList(){
        ModelAndView modelAndView = new ModelAndView("book/listBook");
        modelAndView.addObject("book", bookService.findAll());
        return modelAndView;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Book>> getAll(){
        Iterable<Book> books = bookService.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.remove(id);
        return new ResponseEntity<>(bookOptional.get(), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id){
        return new ResponseEntity<>(bookService.findById(id).get(),HttpStatus.OK);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Book> editBook(@PathVariable Long id,@RequestBody Book book){
        Optional<Book> bookOptional = bookService.findById(id);
        book.setId(bookOptional.get().getId());
        if (!bookOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.save(book);
        return new ResponseEntity<>(bookOptional.get(),HttpStatus.OK);
    }

}
