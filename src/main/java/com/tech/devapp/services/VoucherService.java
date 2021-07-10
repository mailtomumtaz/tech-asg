/**
 * 
 */
package com.tech.devapp.services;

import java.util.Optional;

import com.tech.devapp.entities.Voucher;

/**
 * @author Mumtaz
 *
 */
public interface VoucherService {

	Optional<Voucher> getVoucher(String voucherCode);
	
	Optional<Voucher> addVoucher(String voucherCode, double amount);
	
	Optional<Voucher> expireVoucher(String voucherCode);
	
}
