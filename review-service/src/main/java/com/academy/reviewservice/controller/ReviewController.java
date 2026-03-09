package com.academy.reviewservice.controller;
import com.academy.reviewservice.model.Review;
import com.academy.reviewservice.repository.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewRepository repository;
    public ReviewController(ReviewRepository repository) {
        this.repository = repository;
    }
    @GetMapping
    public List<Review> getAll() {
        return repository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Review> getById(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/product/{productId}")
    public List<Review> getByProduct(
            @PathVariable String productId) {
        return repository.findByProductId(productId);
    }
    @PostMapping
    public Review create(@RequestBody Review review) {
        return repository.save(review);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
