/**
 * 
 */
package com.tech.devapp.services.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.devapp.entities.Voucher;
import com.tech.devapp.repositories.VoucherRepository;
import com.tech.devapp.services.VoucherService;

/**
 * @author Mumtaz
 *
 */
@Service
@Transactional
public class VoucherServiceImpl implements VoucherService {

	@Autowired
	VoucherRepository repository;

	@Override
	public Optional<Voucher> getVoucher(String voucherCode) {
		
		return repository.findByCode(voucherCode);
	}

	@Override
	public Optional<Voucher> addVoucher(String voucherCode, double amount) {
		
		Voucher voucher = Voucher.builder()
				.amount(amount)
				.code(voucherCode)
				.isValid(true)
				.build();
		return Optional.of( repository.save(voucher));
	}

	@Override
	public Optional<Voucher> expireVoucher(String voucherCode) {
		if(voucherCode == null || voucherCode.isEmpty()) return Optional.empty();
		
		Optional<Voucher> optionalVoucher = repository.findByCode(voucherCode);
		if(optionalVoucher.isPresent()) {
			Voucher voucher =	optionalVoucher.get();
			voucher.setValid(false);
		
		return Optional.of( repository.save(voucher));
		}
		
		return optionalVoucher;
		
	}
}
