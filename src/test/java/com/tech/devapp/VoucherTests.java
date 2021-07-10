package com.tech.devapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.tech.devapp.controller.VoucherController;

@SpringBootTest
class VoucherTests {

	@Autowired
	VoucherController voucherController;
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(voucherController).isNotNull();
	}

	@Test
	void testAddVoucherForZero() {
	 ResponseEntity<?> response = voucherController.addVoucher(0);
	 assertEquals("Invalid amount", response.getBody());
	}

	@Test
	void testAddVoucher() {
	 ResponseEntity<?> response = voucherController.addVoucher(0);
	 assertNotEquals("Invalid amount", response.getBody());
	}

	
	@Test
	void expireVoucher() {
	 String code = voucherController.addVoucher(50).getBody().toString();
	 String response = voucherController.expireVoucher(code).getBody().toString();
	 assertEquals("false", response);
	}

}
