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
public class FictionBook implements BookType {

	@Value("${fiction.discount.allow}")
	private boolean discountAllowed;
	 
	@Value("${fiction.discount.percent}")
	private double discount;
	
	@Override
	public double getDiscount() {
		
		return discountAllowed ? discount : 0;
	}

}
