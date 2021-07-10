/**
 * 
 */
package com.tech.devapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Mumtaz
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Book {

	private long id;
	
	private String name;

	private String description;
	
	private String author;
	
	private String Type;
	
	private double price;
	
	private String sbn;

}
