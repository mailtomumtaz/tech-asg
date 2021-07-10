/**
 * 
 */
package com.tech.devapp.controller;

import java.time.Instant;
import java.util.Optional;

import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.devapp.entities.Voucher;
import com.tech.devapp.services.VoucherService;

/**
 * @author Mumtaz
 *
 */
@RestController
@RequestMapping("/voucher")
public class VoucherController {

	@Autowired
	VoucherService voucherService;
	
	@PostMapping("/add/{amount}")
	public ResponseEntity<?> addVoucher(@PathVariable("amount") double amount){
		
		if(amount > 0) {
			Optional<Voucher> optionalVoucher = voucherService.addVoucher(generateVoucherCode(), amount);
			if(optionalVoucher.isPresent()) {
				return new ResponseEntity<>(optionalVoucher.get().getCode(), HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<>("Invalid amount", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/expire/{code}")
	public ResponseEntity<?> expireVoucher(@PathVariable("code") String code){
		
		if(code != null && code.length() > 0) {
			Optional<Voucher> optionalVoucher = voucherService.expireVoucher(code);
			if(optionalVoucher.isPresent()) {
				return new ResponseEntity<>(optionalVoucher.get().isValid(), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Invalid amount", HttpStatus.BAD_REQUEST);
	}

	
	private String generateVoucherCode() {
		
		return ""+ Instant.now().toString();
	} 
}
