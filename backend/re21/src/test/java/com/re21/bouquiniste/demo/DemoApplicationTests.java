package com.re21.bouquiniste.demo;

import com.re21.bouquiniste.MyApplication;
import com.re21.bouquiniste.modules.Book;
import com.re21.bouquiniste.modules.User;
import com.re21.bouquiniste.service.BookService;
import com.re21.bouquiniste.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {MyApplication.class})
@Slf4j
class DemoApplicationTests {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        System.out.println(bookService.getAllBooks());
    }

    @Test
    void testInsertUser()
    {
        User user1 = new User("tang","aaa");
        if (userService.addUser(user1)==1)
        {
            log.debug("gestion reussie");
        }
        else
        {
            log.debug("gestion echoue");
        }
    }

    @Test
    public void testInsertBook()
    {
        Book book = new Book("book2","le deuxieme livre","/","pedagogie");
        if(bookService.addBook(book, userService.getUserById(1))>0)
        {
            log.debug("ajouter un livre, gestion reussie");
        }
        else
        {
            log.debug("echouer a ajouter un livre");
        }
    }

}












