package com.springboot.elearningmanagementstructurebyfeature.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Modifying
    @Query ("UPDATE Category as cate SET cate.isDeleted = true WHERE cate.id=?1")
    void updateIsDeletedById (Integer id);

    //using JPQL
    //@Query (value = "SELECT * FROM categories", nativeQuery = true"

    @Query("SELECT cate FROM Category AS cate")
    List<Category> selectAllCategories();

    @Query("SELECT cate.name FROM Category AS cate ")
    List<String> selectCategoryNames();

    @Query("SELECT cate FROM  Category AS cate where cate.name=?1 AND cate.isDeleted=?2")
    List<Category> selectCategoryByNameAndIsDeleted(String name, boolean isDeleted);

    @Query("SELECT cate FROM Category  AS cate WHERE cate.name = :name")
    List<Category> selectCategoryByName(String name);

    @Transactional //should use in Service Layer
    @Modifying
    @Query("""
            UPDATE Category AS cate
            SET cate.name = :name
            WHERE cate.id = :id
            """)
    void updateCategoriesName(Integer id, String name);
    @Modifying
    @Query(""" 
            DELETE Category AS cate 
            WHERE cate.id = :id
           """)
    void removeById(Integer id);
}
