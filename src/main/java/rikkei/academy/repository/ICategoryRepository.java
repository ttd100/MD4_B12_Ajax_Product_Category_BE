package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Category;

public interface ICategoryRepository extends JpaRepository<Category,Long> {
}
