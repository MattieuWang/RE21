package com.re21.bouquiniste.dao;

import com.re21.bouquiniste.modules.Book_User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Book_UserMapper {
    int addRecord(Book_User book_user);
    List<Book_User> getRecordByBookId(Integer book_id);
    List<Book_User> getRecordByUserId(Integer user_id);
}
