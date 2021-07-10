/**
 * 
 */
package com.tech.devapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.devapp.entities.Voucher;

/**
 * @author Mumtaz
 *
 */
@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

	public Optional<Voucher> findByCode(String code);
}
