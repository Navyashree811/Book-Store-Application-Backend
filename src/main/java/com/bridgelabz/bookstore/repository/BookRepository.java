package com.bridgelabz.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.model.BookModel;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Long> {

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE bookstore.book SET quantity =:quantity WHERE book_id =:bookId", nativeQuery = true)
	Integer changeBookQuantity(Long bookId, int quantity);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE bookstore.book SET price =:price WHERE book_id =:bookId", nativeQuery = true)
	Integer changeBookPrice(Long bookId, Double price);
}
