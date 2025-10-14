package com.caffeine.gwanghwamun.domain.review.dto;

import java.util.List;

public record ReviewListResDTO(
        List<ReviewResDTO> reviewList
) {
   public static ReviewListResDTO of(List<ReviewResDTO> reviewList){
       return new ReviewListResDTO(reviewList);
   }
}
