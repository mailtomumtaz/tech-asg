/**
 * 
 */
package com.tech.devapp.business;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mumtaz
 *
 */
@Configuration
public class ComicBook implements BookType {

	@Value("${comic.discount.allow}")
	private static boolean discountAllowed;
	 
	@Value("${comic.discount.percent}")
	private static double discount;
	
	@Override
	public double getDiscount() {
		
		return discountAllowed ? discount : 0;
	}

}
