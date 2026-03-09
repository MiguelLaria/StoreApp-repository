package com.academy.reviewservice.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
@Document(collection = "reviews")
public class Review {
    @Id
    private String id; // MongoDB uses String IDs (ObjectId)
    private String productId; // Reference to Product service
    private String author;
    private int rating; // 1-5 stars
    private String comment;
    private LocalDateTime createdAt;
    // Default constructor
    public Review() {
        this.createdAt = LocalDateTime.now();
    }
    public Review(String productId, String author,
                  int rating, String comment) {
        this.productId = productId;
        this.author = author;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }
    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) {

        this.productId = productId;
    }
    public String getAuthor() { return author; }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getRating() { return rating; }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getComment() { return comment; }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
