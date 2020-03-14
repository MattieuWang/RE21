package com.re21.bouquiniste.service;

import com.re21.bouquiniste.dao.BookMapper;
import com.re21.bouquiniste.dao.Book_UserMapper;
import com.re21.bouquiniste.modules.Book;
import com.re21.bouquiniste.modules.Book_User;
import com.re21.bouquiniste.modules.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public int addBook(Book book, User user)
    {
        for(Book_User book_user:book_userMapper.getRecordByUserId(user.getUser_id()))
        {
            if (bookMapper.getBookByName(book.getBook_name()).size()!=0)
            {
                return -1;  // deja ajouter
            }
        }

        book.setBook_id(bookMapper.getSize()+1);

        if (bookMapper.addBook(book)>0 && book_userMapper.addRecord(new Book_User(book.getBook_id(),user.getUser_id()))>0)
        {
            return 1;
        }
        else
        {
            return 0; // echec inconnu
        }
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
}
