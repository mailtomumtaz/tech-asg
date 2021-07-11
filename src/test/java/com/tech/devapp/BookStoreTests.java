/**
 * 
 */
package com.tech.devapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tech.devapp.controller.BookController;
import com.tech.devapp.controller.VoucherController;
import com.tech.devapp.entities.Voucher;
import com.tech.devapp.model.Book;
import com.tech.devapp.model.wrapper.BookList;
import com.tech.devapp.services.VoucherService;

/**
 * @author Mumtaz
 *
 */
@SpringBootTest
class BookStoreTests {

	@Autowired
	BookController controller;
	
	@Autowired
	VoucherController voucherController;
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
		assertThat(voucherController).isNotNull();
	}

	@Test
	void testAddBook() {
		ResponseEntity<?> response = controller.add(getBook());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	void testEditBook() {
		ResponseEntity<?> response = controller.add(getBook());
		Book book = (Book) response.getBody();
		book.setPrice(99);
		Book responseBook  = (Book) controller.edit(book).getBody();
		assertEquals(99.0, responseBook.getPrice());
	}
	
	@Test
	void testDeleteBook() {
		ResponseEntity<?> response = controller.add(getBook());
		Book book = (Book) response.getBody();
		response  = controller.delete(""+ book.getId());
		assertEquals("Book deleted.", response.getBody());
	}
	
	
	@Test
	void testAddBookMissingData() {

		ResponseEntity<?> response = controller.add(new Book());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void testSingleBookCheckoutWithDiscount() {
		List<Book> bookWithIds = getBookList();
		bookWithIds.add((Book) controller.add(getBookWithDiscount()).getBody());
		
		BookList bookList = BookList.builder()
				.bookList(bookWithIds)
				.voucher("")
				.build();
		
		ResponseEntity<?> response = controller.checkout(bookList);
		assertEquals("450.0", response.getBody());
		
	}
	
	@Test
	void testSingleBookCheckoutNoDiscount() {
		List<Book> bookWithIds = getBookList();
		bookWithIds.add((Book) controller.add(getBookWithoutDiscount()).getBody());
		
		BookList bookList = BookList.builder()
				.bookList(bookWithIds)
				.voucher("")
				.build();
		
		ResponseEntity<?> response = controller.checkout(bookList);
		assertEquals("50.0", response.getBody());
		
	}
	
	@Test
	void testCheckoutWithoutVoucher() {
		
		
		List<Book> bookWithIds = new ArrayList<>();
		List<Book> books = getBookList();
		for(Book book : books) {
			bookWithIds.add((Book) controller.add(book).getBody());
		}
		BookList bookList = BookList.builder()
				.bookList(bookWithIds)
				.voucher("")
				.build();
		
		ResponseEntity<?> response = controller.checkout(bookList);
		assertEquals("280.0", response.getBody());
	}

	@Test
	void testCheckoutWithVoucher() {
		
		List<Book> bookWithIds = new ArrayList<>();
		List<Book> books = getBookList();
		for(Book book : books) {
			bookWithIds.add((Book) controller.add(book).getBody());
		}

		String code = voucherController.addVoucher(30).getBody().toString();
		BookList bookList = BookList.builder()
				.bookList(bookWithIds)
				.voucher(code)
				.build();
		
		ResponseEntity<?> response = controller.checkout(bookList);
		assertEquals("250.0", response.getBody());
	}
	
	private Book getBook() {
		Book book = Book.builder()
				.name("Java8")
				.author("Mumtaz")
				.description("Book description")
				.price(100)
				.sbn("123-321")
				.Type("comic")
				.build();
		
		return book;
	}
	
	private Book getBookWithoutDiscount() {
		Book book = Book.builder()
				.name("Java8 No Discount")
				.author("Mumtaz")
				.description("Book description")
				.price(50)
				.sbn("123-321")
				.Type("comic")
				.build();
		
		return book;
	}
	
	private Book getBookWithDiscount() {
		Book book = Book.builder()
				.name("Java8 Discounted")
				.author("Mumtaz")
				.description("Book description")
				.price(500)
				.sbn("523-521")
				.Type("fiction")
				.build();
		
		return book;
	}
	
	
	private List<Book> getBookList() {
		List<Book> books = new ArrayList<>();
		Book bookComic = Book.builder()
				.name("New Comic")
				.author("Mumtaz")
				.description("Book description")
				.price(100)
				.sbn("123-321")
				.Type("comic")
				.build();
		
		Book bookFiction = Book.builder()
				.name("New Fiction")
				.author("Mumtaz")
				.description("Book description")
				.price(200)
				.sbn("123-321")
				.Type("fiction")
				.build();
		books.add(bookComic);
		books.add(bookFiction);
		return books;
	}
}
