/**
 * 
 */
package com.tech.devapp.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tech.devapp.business.CheckoutCalculator;
import com.tech.devapp.business.ComicBook;
import com.tech.devapp.business.FictionBook;
import com.tech.devapp.entities.Voucher;
import com.tech.devapp.model.Book;
import com.tech.devapp.model.wrapper.BookList;
import com.tech.devapp.services.BookService;
import com.tech.devapp.services.VoucherService;

/**
 * @author Mumtaz
 *
 */
@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	BookService bookService;
	
	@Autowired
	VoucherService voucherService;
	
	@Autowired
	ComicBook comicBook;
	
	@Autowired
	FictionBook fictionBook;
	@GetMapping("/{id}")
	public ResponseEntity<?> getBook(@PathVariable(name="id") String id) {
		
		if(id == null || id.isEmpty()) {
			return new ResponseEntity<>("ID required.", HttpStatus.BAD_REQUEST);
		}
		else
		{
			Optional<com.tech.devapp.entities.Book> optionalBook = bookService.getBookById(Long.parseLong(id));	
			if(optionalBook.isPresent()) {
				return new ResponseEntity<>(getBookUI(optionalBook.get()), HttpStatus.OK);
			}
			return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/checkout")
	public ResponseEntity<?> checkout(@RequestBody List<Book> bookList,
									   @RequestBody String voucher) {
		
		if(bookList == null) {
			return new ResponseEntity<>("Books required.", HttpStatus.BAD_REQUEST);
		}
		else
		{
			List<Long> ids = bookList.stream().map(b -> b.getId()).collect(Collectors.toList());
			List<com.tech.devapp.entities.Book> books = bookService.getBooksByListId(ids);
			
			CheckoutCalculator calculator = new CheckoutCalculator(comicBook, fictionBook);
			double totamAmount = calculator.calculateTotal(books, getVoucherAmount(voucher));
			voucherService.expireVoucher(voucher);
			return new ResponseEntity<>(""+totamAmount, HttpStatus.OK);
		}
	}
	
	
	private double getVoucherAmount(String code) {
		
		if(code == null || code.isEmpty()) return 0;
		Optional<Voucher> optionalVoucher = voucherService.getVoucher(code);
		if(optionalVoucher.isPresent()) {
			Voucher voucher = optionalVoucher.get();
			if(voucher.isValid()) {
				return voucher.getAmount();
			}
		}
		return 0;
	}

	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody Book saveBook) {
		
		if(saveBook != null) {
			
			if(saveBook.getAuthor() != null && saveBook.getAuthor().isEmpty()) {

				return new ResponseEntity<>("Book author required.", HttpStatus.BAD_REQUEST);
			}
			if(saveBook.getDescription() != null && saveBook.getDescription().isEmpty()) {

				return new ResponseEntity<>("Book description required.", HttpStatus.BAD_REQUEST);
			}
			if(saveBook.getName() != null && saveBook.getName().isEmpty()) {

				return new ResponseEntity<>("Book name required.", HttpStatus.BAD_REQUEST);
			}
			if(saveBook.getPrice() <= 0) {

				return new ResponseEntity<>("Book price required.", HttpStatus.BAD_REQUEST);
			}
			if(saveBook.getSbn() != null && saveBook.getSbn().isEmpty()) {

				return new ResponseEntity<>("Book SBN required.", HttpStatus.BAD_REQUEST);
			}
			if(saveBook.getType() != null && saveBook.getType().isEmpty()) {

				return new ResponseEntity<>("Book Type is required.", HttpStatus.BAD_REQUEST);
			}
			if(saveBook.getAuthor() != null && saveBook.getAuthor().isEmpty()) {

				return new ResponseEntity<>("Book authoer required.", HttpStatus.BAD_REQUEST);
			}
			
			Optional<com.tech.devapp.entities.Book> savedBook = bookService.save(getBookEntity(saveBook));
			
			if(savedBook.isPresent()) {
				return new ResponseEntity<>(getBookUI(savedBook.get()),
					HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Book data missing.", HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>("Book data missing.", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/edit")
	public ResponseEntity<?> edit(@RequestBody Book saveBook) {
		
		if(saveBook != null) {
			if(saveBook.getId() <= 0) {

				return new ResponseEntity<>("Book ID required.", HttpStatus.BAD_REQUEST);
			}			
			if(saveBook.getAuthor() != null && saveBook.getAuthor().isEmpty()) {

				return new ResponseEntity<>("Book author required.", HttpStatus.BAD_REQUEST);
			}
			if(saveBook.getDescription() != null && saveBook.getDescription().isEmpty()) {

				return new ResponseEntity<>("Book description required.", HttpStatus.BAD_REQUEST);
			}
			if(saveBook.getName() != null && saveBook.getName().isEmpty()) {

				return new ResponseEntity<>("Book name required.", HttpStatus.BAD_REQUEST);
			}
			if(saveBook.getPrice() <= 0) {

				return new ResponseEntity<>("Book price required.", HttpStatus.BAD_REQUEST);
			}
			if(saveBook.getSbn() != null && saveBook.getSbn().isEmpty()) {

				return new ResponseEntity<>("Book SBN required.", HttpStatus.BAD_REQUEST);
			}
			if(saveBook.getType() != null && saveBook.getType().isEmpty()) {

				return new ResponseEntity<>("Book Type is required.", HttpStatus.BAD_REQUEST);
			}
			if(saveBook.getAuthor() != null && saveBook.getAuthor().isEmpty()) {

				return new ResponseEntity<>("Book authoer required.", HttpStatus.BAD_REQUEST);
			}

			Optional<com.tech.devapp.entities.Book> findBook = bookService.getBookById(saveBook.getId());
			
			if(findBook.isEmpty()) {
				return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
			}
			
			Optional<com.tech.devapp.entities.Book> savedBook = bookService.save(getBookEntity(saveBook));
			
			if(savedBook.isPresent()) {
				return new ResponseEntity<>(getBookUI(savedBook.get()),
					HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("Book data missing.", HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>("Book data missing.", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
	
		if(id == null && id.isEmpty()) {
			return new ResponseEntity<>("Book id required", HttpStatus.BAD_REQUEST);
		}else {
			Optional<com.tech.devapp.entities.Book> findBook = bookService.getBookById(Long.parseLong(id));
			
			if(findBook.isEmpty()) {
				return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
			}
			
			bookService.deleteById(Long.parseLong(id));
			return new ResponseEntity<>("Book deleted.", HttpStatus.OK);
		}
	}
	
	private Book getBookUI(com.tech.devapp.entities.Book entityBook) {
		Book bookEntity = 
				Book.builder()
				.id(entityBook.getId())
				.name(entityBook.getName())
				.author(entityBook.getAuthor())
				.description(entityBook.getDescription())
				.price(entityBook.getPrice())
				.sbn(entityBook.getSbn())
				.Type(entityBook.getType())
				.build();
		return bookEntity;
	}
	
	private com.tech.devapp.entities.Book getBookEntity(Book saveBook) {
		com.tech.devapp.entities.Book bookEntity = 
				com.tech.devapp.entities.Book.builder()
				.id(saveBook.getId())
				.name(saveBook.getName())
				.author(saveBook.getAuthor())
				.description(saveBook.getDescription())
				.price(saveBook.getPrice())
				.sbn(saveBook.getSbn())
				.Type(saveBook.getType())
				.build();
		return bookEntity;
	}
}
