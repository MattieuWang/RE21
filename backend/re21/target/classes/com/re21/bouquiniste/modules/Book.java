package com.re21.bouquiniste.modules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Book {

    @Value("un livre")
    private String book_name;
    @Value("cest un livre vraiment interessant")
    private String book_desc;
    private Integer book_id;
    @Value("1")
    private Integer book_num;
    @Value("/")
    private String image_location;  // 以 , 分割每个图片位置
    @Value("#pas precise")
    private String tags;    // 书籍所属分类   以 , 分割
    @Value("false")
    private boolean hasDone;  // 是否已被交易 default is false

    public Book(String book_name,String book_desc)
    {
        this.book_name = book_name;
        this.book_desc = book_desc;
    }
    public Book(String book_name,String book_desc,String image_location,String tags)
    {
        this.book_name = book_name;
        this.book_desc = book_desc;
        this.image_location = image_location;
        this.tags = tags;
        book_num = 1;
    }
    public Book(String book_name,String book_desc,int book_num,String image_location,String tags)
    {
        this.book_name = book_name;
        this.book_desc = book_desc;
        this.image_location = image_location;
        this.tags = tags;
        this.book_num = book_num;
    }


    public Book(String book_name) {
        this.book_name = book_name;
        book_desc="cest un livre vraiment interessant";
        book_num=1;
        image_location="/";
        tags = "#";
        hasDone =false;
    }
}
