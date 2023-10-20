package com.example.demo.domain.user.repository;

import com.example.demo.domain.user.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {





    @Query(value = "SELECT * FROM carrotdb.product ORDER BY Pno DESC LIMIT :amount OFFSET :offset", nativeQuery = true)
    List<Product> findProductAmountStart(@Param("amount") int amount, @Param("offset") int offset);


    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product p SET p.title = :title, p.details = :details, p.reg_date = :reg_date, p.count = :count,p.place=:place ,p.filename = :filename, p.filesize = :filesize WHERE p.Pno = :Pno")
    Integer updateProduct(
            @Param("title") String title,
            @Param("details") String details,
            @Param("place")String place,
            @Param("reg_date") LocalDateTime reg_date,
            @Param("count") Long count,
            @Param("filename") String filename,
            @Param("filesize") String filesize,
            @Param("Pno") Long Pno
    );

    // Type , Keyword 로 필터링된 count 계산
    @Query("SELECT COUNT(p) FROM Product p WHERE p.title LIKE %:keyWord%")
    Integer countWhereTitleKeyword(@Param("keyWord") String keyWord);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.details LIKE %:details%")
    Integer countWhereDetailsKeyword(@Param("details") String details);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.place LIKE %:place%")
    Integer countWherePlaceKeyword(@Param("place") String place);

    @Query(value = "SELECT * FROM product WHERE title LIKE %:keyWord%  ORDER BY Pno DESC LIMIT :amount OFFSET :offset", nativeQuery = true)
    List<Product> findProductTitleAmountStart(@Param("keyWord")String keyword, @Param("amount") int amount,@Param("offset") int offset);

    @Query(value = "SELECT * FROM product WHERE details LIKE %:keyWord%  ORDER BY Pno DESC LIMIT :amount OFFSET :offset", nativeQuery = true)
    List<Product> findProductDetailsAmountStart(@Param("keyWord")String keyword, @Param("amount") int amount,@Param("offset") int offset);

    @Query(value = "SELECT * FROM product WHERE place LIKE %:keyWord%  ORDER BY Pno DESC LIMIT :amount OFFSET :offset", nativeQuery = true)
    List<Product> findProductPlaceAmountStart(@Param("keyWord")String keyword, @Param("amount") int amount,@Param("offset") int offset);


}
