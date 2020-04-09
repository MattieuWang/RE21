package com.re21.bouquiniste.dao;

import com.re21.bouquiniste.modules.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Repository
public interface BookMapper {
    List<Book> getAllBooks();
    List<Book> getBookByName(String name);
    List<Book> getBookByNameAndUserId(@Param("book_name") String book_name,@Param("user_id")Integer user_id);
    List<Book> getBookByTag(String tag);
    List<Book> getBookByUserId(Integer user_id);
    ArrayList<String> getImageLocation(Integer book_id);
    Book getBookById(Integer id);
    void setBookStateById(@Param("id") Integer id,@Param("state") boolean state);
    int addBook(Book book);
    void addBookImage(@Param("book_id")Integer book_id, @Param("location") String image_location);
    int getSize();
}
