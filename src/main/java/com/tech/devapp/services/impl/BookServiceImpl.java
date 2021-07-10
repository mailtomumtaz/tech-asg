/**
 * 
 */
package com.tech.devapp.services.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.devapp.entities.Book;
import com.tech.devapp.repositories.BookRepository;
import com.tech.devapp.services.BookService;

/**
 * @author Mumtaz
 *
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

	BookRepository repository;
	
	@Autowired
	BookServiceImpl(BookRepository repository){
		this.repository = repository;
	}
	
	@Override
	public Optional<Book> save(Book book) {
		
		return Optional.of(repository.save(book));
	}

	@Override
	public void deleteById(long id) {
		repository.deleteById(id);
	}

	@Override
	public Optional<Book> getBookById(long id) {
		
		return repository.findById(id);
	}

	@Override
	public List<Book> getBooks() {
		
		return repository.findAll();
	}

	@Override
	public List<Book> getBooksByListId(List<Long> ids) {
		
		return repository.findByIdList(ids);
	}
}
