package com.re21.bouquiniste.controller;


import com.re21.bouquiniste.modules.Book;
import com.re21.bouquiniste.modules.User;
import com.re21.bouquiniste.service.BookService;
import com.re21.bouquiniste.service.LoginService;
import com.re21.bouquiniste.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
@Slf4j
public class BookController {

    @Resource
    private LoginService loginService;
    @Resource
    private BookService bookService;
    @Resource
    private UserService userService;

    @PostMapping("book")
    public String addBook(HttpServletRequest request, Book book)
    {
        User user = (User)request.getSession().getAttribute("userLg");
        bookService.addBook(book,user);
        log.debug("Controller: ajouter un livre, gestion reussie");
        return user.toString();
    }

    @GetMapping("login")
    public String login(String username,String password)
    {
        String userinfo = loginService.userLogin(username,password);
        if (userinfo == "login fail")
            return "fail";
        else
            return userinfo;
    }

    @PostMapping("user")
    public String addUser(User user)
    {
        userService.addUser(user);
        return userService.getAllUserByString();
    }

    @GetMapping("user/{id}")
    public String getUserById(@PathVariable Integer id)
    {
        return userService.getUserById(id).toString();
    }

    @GetMapping("book/{id}")
    public String getBookById(@PathVariable Integer id)
    {
        return bookService.getBookById(id).toString();
    }




}
