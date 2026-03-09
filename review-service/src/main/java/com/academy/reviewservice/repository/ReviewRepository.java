package com.academy.reviewservice.repository;
import com.academy.reviewservice.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ReviewRepository
        extends MongoRepository<Review, String> {
    // Same pattern as JPA! Auto-generated from name.
    List<Review> findByProductId(String productId);
    List<Review> findByAuthor(String author);
    List<Review> findByRatingGreaterThanEqual(int rating);
}
