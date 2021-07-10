/**
 * 
 */
package com.tech.devapp.services;

import java.util.List;
import java.util.Optional;

import com.tech.devapp.entities.Book;

/**
 * @author Mumtaz
 *
 */
public interface BookService {

	Optional<Book> save(Book book);
	
	void deleteById(long id);
	
	Optional<Book> getBookById(long id);
	
	List<Book> getBooks();

	List<Book> getBooksByListId(List<Long> ids);
}
