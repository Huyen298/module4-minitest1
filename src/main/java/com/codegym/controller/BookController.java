package com.codegym.controller;

import com.codegym.model.Book;
import com.codegym.model.Category;
import com.codegym.service.book.BookService;
import com.codegym.service.book.IBookService;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
//@RequestMapping("book")
public class BookController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }


    @GetMapping("/book")
    public ModelAndView list(){
        Iterable<Book> books = bookService.findAll();
        ModelAndView modelAndView = new ModelAndView("list","book",books);
        return modelAndView;
    }
    @GetMapping("book/create")
    public ModelAndView showForm(){
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("book", new Book());
        return modelAndView;
    }
    @PostMapping("/create")
    public ModelAndView addNew(@ModelAttribute("book")Book book){
        bookService.save(book);
        return new ModelAndView("create");
    }
    @GetMapping("book/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("delete");
            modelAndView.addObject("book", book.get());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
        @PostMapping("/delete")
        public String deleteBook(@ModelAttribute("book")Book book){
        bookService.remove(book.getId());
        return "redirect:book";
    }
    @GetMapping("book/edit/{id}")
        public ModelAndView showEditForm(@PathVariable Long id){
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("edit");
            modelAndView.addObject("book", book.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/edit")
    public ModelAndView updateBook(@ModelAttribute("book") Book book) {
        bookService.save(book);
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("book", book);
//        modelAndView.addObject("message", "Customer updated successfully");
        return modelAndView;
    }
}

