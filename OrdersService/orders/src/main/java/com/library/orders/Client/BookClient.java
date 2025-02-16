package com.library.orders.Client;

import com.library.orders.DTO.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="BOOK-SERVICE")
public interface BookClient {
    /*@GetMapping("/getbookbyid/{book_id}")
    public ResponseEntity<byte[]> getBookById(@PathVariable("book_id") int bid);

    @GetMapping("/getbookContent/{book_id}")
    public ResponseEntity<byte[]> getContentById(@PathVariable("book_id") int bid);*/

    @GetMapping("/book/getBookDetailsById/{book_id}")
    public ResponseEntity<BookDTO> getBookDetails(@PathVariable("book_id") int bid);

    @GetMapping("/book/price/{book_id}")
    public double bookPrice(@PathVariable("book_id") int bid);
}
