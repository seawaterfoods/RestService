package com.joe.restservice.dto;

import com.joe.restservice.domain.Book;
import com.joe.restservice.util.CusotmBeanUtils;
import org.springframework.beans.BeanUtils;

public class BookDTO {

    private Long id;
    private String author;
    private String description;
    private String name;
    private Integer status;

    public BookDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



    public void convertToBook(Book book){
        new BookCovert().convert(this,book);
    }

    private class BookCovert implements Convert<BookDTO, Book>{

        //        convert將currentBook轉換成bookDTO並忽略值為null的屬性
        @Override
        public Book convert(BookDTO bookDTO, Book book) {
//      過濾屬性為null的值，取得值為null的屬性名 String[]
            String[] nullPropertyNames= CusotmBeanUtils.getNullPropertyNames(bookDTO);
//      於BeanUtils.copyProperties(Object source, Object target,String 指定不copy的屬性值)，第三位參數可選忽略值
            BeanUtils.copyProperties(bookDTO,book,nullPropertyNames);

            return book;
        }

        @Override
        public Book convert(BookDTO bookDTO) {
            return null;
        }
    }
}
