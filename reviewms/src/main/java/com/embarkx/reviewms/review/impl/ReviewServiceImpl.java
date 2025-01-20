package com.embarkx.reviewms.review.impl;


import com.embarkx.reviewms.review.Review;
import com.embarkx.reviewms.review.ReviewRepository;
import com.embarkx.reviewms.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    ReviewRepository reviewRepository;
//    CompanyService companyService;
    public ReviewServiceImpl(ReviewRepository reviewRepository/*, CompanyService companyService*/) {
        this.reviewRepository = reviewRepository;
//        this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId); //jpa automatically generated this method findby+ companyId (comapny feild be present in review)
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
//        Company company  = companyService.getCompanyById(companyId);
        if(companyId !=null && review !=null){
          review.setCompanyId(companyId);
            reviewRepository.save(review);
          return true;
        }
        return false;
    }

    @Override
    public Review getReview( Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean updateReview(Long reviewId, Review updatedReview)
    {   Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review !=null){
//            updatedReview.setCompany(companyService.getCompanyById(companyId));
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            review.setCompanyId(updatedReview.getCompanyId());
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview( Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review!=null){
            reviewRepository.delete(review);
            return true;
        }
        return false;
    }

}
