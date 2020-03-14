package com.re21.bouquiniste.dao;

import com.re21.bouquiniste.modules.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookMapper {
    List<Book> getAllBooks();
    List<Book> getBookByName(String name);
    List<Book> getBookByTag(String tag);
    Book getBookById(Integer id);
    void setBookStateById(@Param("id") Integer id,@Param("state") boolean state);
    int addBook(Book book);
    int getSize();
}
