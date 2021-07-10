/**
 * 
 */
package com.tech.devapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tech.devapp.entities.Book;

/**
 * @author Mumtaz
 *
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	@Query(value = "Select b from Book b where b.id IN :ids")
	public List<Book> findByIdList(@Param("ids") List<Long> ids);
}
