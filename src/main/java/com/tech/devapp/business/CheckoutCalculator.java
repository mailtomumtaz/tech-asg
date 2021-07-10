/**
 * 
 */
package com.tech.devapp.business;

import java.util.List;

import com.tech.devapp.entities.Book;

/**
 * @author Mumtaz
 *
 */
public class CheckoutCalculator {

	private double amount = 0;
	private BookType comicBook;
	private BookType fictionBook;
	
	public CheckoutCalculator(BookType comicBook, BookType fictionBook){
		this.comicBook = comicBook;
		this.fictionBook = fictionBook;
	}
	
	public double calculateTotal(List<Book> books, double voucherAmount) {
		
		for(Book book : books) {
			if(book.getType().equals(BookType.COMIC_BOOK)) {
				amount +=  getBookCost(book, comicBook);
			} else if(book.getType().equals(BookType.FICTION_BOOK)) {
				amount += getBookCost(book, fictionBook);
			}
		}
		
		return applyVoucher(amount, voucherAmount);
	}
	
	private double applyVoucher(double amount, double voucherAmount) {
		double total = 0;
		if(amount >= 0) { 
			total = amount - voucherAmount;
		}
		return total > 0 ? total : 0;
	}

	private double getBookCost(Book book, BookType bookType) {
		return (book.getPrice() - 
				(book.getPrice() * bookType.getDiscount()));
	}
}
