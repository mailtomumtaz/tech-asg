/**
 * 
 */
package com.tech.devapp.model.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech.devapp.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Mumtaz
 *
 */

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class BookList {

	@JsonProperty("bookList")
	private List<Book> bookList;
	
	@JsonProperty("voucher")
	private String voucher;
	
	public BookList() {
		bookList = new ArrayList<>();
	}
}
