package com.re21.bouquiniste.modules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// 记录 user 在平台上出的书
public class Book_User {
    private Integer record_id;
    private Integer book_id;
    private Integer user_id;
    private String date;

    public Book_User(Integer book_id, Integer user_id) {
        this.book_id = book_id;
        this.user_id = user_id;
    }
}
