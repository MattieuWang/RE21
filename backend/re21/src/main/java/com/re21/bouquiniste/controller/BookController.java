package com.re21.bouquiniste.controller;


import com.re21.bouquiniste.modules.Book;
import com.re21.bouquiniste.modules.User;
import com.re21.bouquiniste.service.BookService;
import com.re21.bouquiniste.service.LoginService;
import com.re21.bouquiniste.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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


    @GetMapping("books")
    public List<Book> getAllBooks()
    {
        return bookService.getAllBooks();
    }

    @GetMapping("/users")
    public ModelAndView getAllUser()
    {
        List<User> users = userService.getAllUsers();
        ModelAndView modelAndView = new ModelAndView("allUsers");
        modelAndView.addObject("users",users);
        return modelAndView;
    }

    @GetMapping("user/{id}")
    public ModelAndView userPage(@PathVariable Integer id, HttpServletRequest request)
    {
        ModelAndView modelAndView = new ModelAndView("userPage");
        User user = userService.getUserById(id);
        modelAndView.addObject("user",user);

        List<Book> books = bookService.getBooksByUserId(id, request);
        modelAndView.addObject("books",books);

        return modelAndView;
    }

    @PutMapping("user/{id}")
    public ModelAndView modifierUser(@PathVariable Integer id, User user)
    {
        System.out.println(user);
        userService.updateUser(user);
        return new ModelAndView("redirect:/users");
    }

    @DeleteMapping("user/{id}")
    public ModelAndView deleteUser(@PathVariable Integer id)
    {
        userService.deleteUser(id);
        return new ModelAndView("allUsers");
    }

    @GetMapping("delete/{id}")
    public ModelAndView delete(@PathVariable Integer id)
    {
        userService.deleteUser(id);
        return new ModelAndView("redirect:/users");
    }

    @GetMapping("insertUser")
    public ModelAndView insertPage()
    {
        return new ModelAndView("insertPage");
    }
    @PostMapping("user")
    public ModelAndView insertUser(User user)
    {
        userService.addUser(user);
        return new ModelAndView("redirect:/users");
    }

    @GetMapping("book/{id}")
    public String getBookById(@PathVariable Integer id)
    {
        return bookService.getBookById(id).toString();
    }

    @GetMapping("/addbooks")
    public ModelAndView insertBooks()
    {
        return new ModelAndView("insertBooks");
    }

    @PostMapping("book")
    public ModelAndView insertBook(Book book, HttpServletRequest request)
    {
        int user_id = (int) request.getSession().getAttribute("userId");
        bookService.addBook(book,user_id);
        int book_id = bookService.getBookCount();
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("uploadImage");
        for (MultipartFile file:files)
        {
            String url = "";

            String fileName = file.getOriginalFilename();
            System.out.println("------------");
            System.out.println(fileName);
            if (fileName == "" || fileName == null)
                continue;
            System.out.println("subString: " + fileName.substring(fileName.lastIndexOf("."), fileName.length()));
            if (fileName != null && fileName != "") {
                String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                        request.getContextPath() + "/book_images/";
                String path = request.getSession().getServletContext().getRealPath("") + "WEB-INF" + File.separator + "classes" + File.separator + "static"
                        + File.separator + "book_images" + File.separator + "user" + user_id; ///WEB-INF/classes/static/

                String fileSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                fileName = UUID.randomUUID().toString() + fileSuffix;
                log.debug("path:{}", path);
                File file1 = new File(path);
                if (!file1.exists() && !file1.isDirectory()) {
                    file1.mkdirs();
                }
                File targetFile = new File(file1, fileName);
                try {
                    file.transferTo(targetFile);
                    url = returnUrl + fileName;
                    System.out.println(path+"/"+fileName);
                    bookService.addBookImage(fileName,book_id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ModelAndView("redirect:/user/"+user_id);

    }


}














