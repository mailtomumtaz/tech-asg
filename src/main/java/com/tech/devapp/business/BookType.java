/**
 * 
 */
package com.tech.devapp.business;

/**
 * @author Mumtaz
 *
 */
public interface BookType {

	final String COMIC_BOOK= "comic";
	final String FICTION_BOOK = "fiction";
	
	default double getDiscount() {
		return 0;
	}
}
