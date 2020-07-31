package com.nicolaslopez82.sms.service;

import com.nicolaslopez82.sms.domain.Tour;
import com.nicolaslopez82.sms.domain.TourRating;
import com.nicolaslopez82.sms.repository.TourRatingRepository;
import com.nicolaslopez82.sms.repository.TourRepository;
import com.nicolaslopez82.sms.web.RatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TourRatingService {

    private TourRatingRepository tourRatingRepository;
    private TourRepository tourRepository;

    @Autowired
    public TourRatingService(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    /**
     * Create a Tour Rating.
     *
     * @param tour tour to save
     * @param ratingDto rating data transfer object
     */
    public void createTourRating(Tour tour, RatingDto ratingDto) {
        tourRatingRepository.save(
                new TourRating(tour,
                                ratingDto.getCustomerId(),
                                ratingDto.getScore(),
                                ratingDto.getComment()));
    }

    /**
     * Lookup a the Ratings for a tour.
     *
     * @param tour Tour.
     * @return All Tour Ratings as RatingDto's
     */
    public List<RatingDto> getAllRatingsForTour(Tour tour){
        return tourRatingRepository.findByTourId(tour.getId()).stream()
                .map(RatingDto::new).collect(Collectors.toList());
    }

    /**
     * Lookup a page of Ratings for a tour.
     *
     * @param tour Tour.
     * @param pageable paging details
     * @return Requested page of Tour Ratings as RatingDto's
     */
    public Page<RatingDto> getRatings(Tour tour, Pageable pageable){
        Page<TourRating> ratings = tourRatingRepository.findByTourId(tour.getId(), pageable);
        return new PageImpl<>(
                ratings.get().map(RatingDto::new).collect(Collectors.toList()),
                pageable,
                ratings.getTotalElements()
        );
    }

    /**
     * Calculate the average Score of a Tour.
     *
     * @param tour Tour.
     * @return Tuple of "average" and the average value.
     */
    public Map<String, Double> getAverage(Tour tour){
        Map<String, Double> mapResult = new HashMap<>();
        mapResult.put("average",tourRatingRepository.findByTourId(tour.getId()).stream()
                .mapToInt(TourRating::getScore).average()
                .orElseThrow(() ->
                        new NoSuchElementException("Tour has no Ratings")));
        return mapResult;
    }

    /**
     * Verify and return the TourRating for a particular Tour and Customer
     * @param tour Tour.
     * @param customerId customer identifier
     * @return the found TourRating
     * @throws NoSuchElementException if no TourRating found
     */
    public TourRating verifyTourRating(Tour tour, int customerId) throws NoSuchElementException{
        return tourRatingRepository.findByTourIdAndCustomerId(tour.getId(), customerId).orElseThrow(() ->
            new NoSuchElementException("\"Tour-Rating pair for request(\"\n" +
                    "                        + tourId + \" for customer\" + customerId))"));
    }

    /**
     * Update score and comment of a Tour Rating
     *
     * @param tourRating TourRating to update.
     * @param ratingDto rating Data Transfer Object.
     * @return The modified Rating DTO.
     */
    public RatingDto updateWithPut(TourRating tourRating, RatingDto ratingDto){
        tourRating.setScore(ratingDto.getScore());
        tourRating.setComment(ratingDto.getComment());
        return new RatingDto(tourRatingRepository.save(tourRating));
    }

    /**
     * Update score or comment of a Tour Rating
     *
     * @param tourRating TourRating to update.
     * @param ratingDto rating Data Transfer Object.
     * @return The modified Rating DTO.
     */
    public RatingDto updateWithPatch(TourRating tourRating, RatingDto ratingDto){
        if (ratingDto.getScore() != null) {
            tourRating.setScore(ratingDto.getScore());
        }
        if (ratingDto.getComment() != null) {
            tourRating.setComment(ratingDto.getComment());
        }
        return new RatingDto(tourRatingRepository.save(tourRating));
    }

    /**
     * Delete a Rating of a tour made by a customer
     *
     * @param tourRating TourRating to delete
     * @param customerId customer identifier
     */
    public void delete(TourRating tourRating, int customerId){
        tourRatingRepository.delete(tourRating);
    }

    /**
     * Verify and return the Tour given a tourId.
     *
     * @param tourId tour identifier
     * @return the found Tour
     * @throws NoSuchElementException if no Tour found.
     */
    public Tour verifyTour(int tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId).orElseThrow(() ->
                new NoSuchElementException("Tour does not exist " + tourId));
    }
}
