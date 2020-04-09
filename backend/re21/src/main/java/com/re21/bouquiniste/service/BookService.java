package com.re21.bouquiniste.service;

import com.re21.bouquiniste.dao.BookMapper;
import com.re21.bouquiniste.dao.Book_UserMapper;
import com.re21.bouquiniste.modules.Book;
import com.re21.bouquiniste.modules.Book_User;
import com.re21.bouquiniste.modules.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Resource
    private BookMapper bookMapper;
    @Resource
    private Book_UserMapper book_userMapper;

    public List<Book> getAllBooks()
    {
        return bookMapper.getAllBooks();
    }

    public void addBook(Book book, Integer user_id)
    {
        book.setBook_id(bookMapper.getSize()+1);
        bookMapper.addBook(book);
        book_userMapper.addRecord(new Book_User(book.getBook_id(),user_id));
    }

    public void addBookImage(String imageLocation, Integer book_id)
    {
        bookMapper.addBookImage(book_id,imageLocation);

    }

    public int getBookCount()
    {
        return bookMapper.getSize();
    }

    public Book getBookById(Integer id)
    {
        return bookMapper.getBookById(id);
    }
    public List<Book> getBooksByName(String bookname)
    {
        List<Book> books = bookMapper.getBookByName(bookname);
        return books;
    }

    public List<Book> getBooksByNameAndUserId(String book_name,Integer user_id)
    {
        return bookMapper.getBookByNameAndUserId(book_name,user_id);
    }

    public List<Book> getBooksByUserId(Integer user_id, HttpServletRequest request)
    {
        String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                request.getContextPath() + "/book_images/user"+user_id+"/";
        System.out.println("path: "+path);
        List<Book> books = bookMapper.getBookByUserId(user_id);
        for (Book book : books)
        {
            ArrayList<String> locations = bookMapper.getImageLocation(book.getBook_id());
            ArrayList<String> results = new ArrayList<>();
            for (String location:locations)
            {
                results.add(path+location);
            }
            book.setImage_location(results);
            System.out.println("locations: "+results);
        }
        return books;
    }
}






















