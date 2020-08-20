package com.nicolaslopez82.sms.service;

import com.nicolaslopez82.sms.domain.Tour;
import com.nicolaslopez82.sms.domain.TourRating;
import com.nicolaslopez82.sms.repository.TourRatingRepository;
import com.nicolaslopez82.sms.repository.TourRepository;
import com.nicolaslopez82.sms.web.RatingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TourRatingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TourRatingService.class);
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
    public void createTourRating(Tour tour, RatingDto ratingDto) throws NoSuchElementException {
        LOGGER.info("Create Rating for tour {} of customers {}", tour.getId(), ratingDto.getCustomerId());
        tourRatingRepository.save(
                new TourRating(tour,
                                ratingDto.getCustomerId(),
                                ratingDto.getScore(),
                                ratingDto.getComment()));
    }

    /**
     * Create a new Tour Rating in the database
     *
     * @param tourId tour identifier
     * @param customerId customer identifier
     * @param score score of the tour rating
     * @param comment additional comment
     * @throws NoSuchElementException if no Tour found.
     */
    public void createTourRating(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
        LOGGER.info("Create Rating for tour {} of customers {}", tourId, customerId);
        tourRatingRepository.save(
                new TourRating(verifyTour(tourId), customerId,
                        score, comment));
    }

    /**
     * Get a ratings by id.
     *
     * @param id rating identifier
     * @return TourRatings
     */
    public Optional<TourRating> lookupRatingById(int id)  {
        return tourRatingRepository.findById(id);
    }

    /**
     * Get All Ratings.
     *
     * @return List of TourRatings
     */
    public List<TourRating> lookupAll()  {
        LOGGER.info("Lookup all Ratings");
        return tourRatingRepository.findAll();
    }

    /**
     * Lookup a the Ratings for a tour.
     *
     * @param tour Tour.
     * @return All Tour Ratings as RatingDto's
     */
    public List<RatingDto> getAllRatingsForTour(Tour tour){
        LOGGER.info("Lookup Rating for tour {}", tour.getId());
        return tourRatingRepository.findByTourId(tour.getId()).stream()
                .map(RatingDto::new).collect(Collectors.toList());
    }

    /**
     * Get a page of tour ratings for a tour.
     *
     * @param tourId tour identifier
     * @param pageable page parameters to determine which elements to fetch
     * @return Page of TourRatings
     * @throws NoSuchElementException if no Tour found.
     */
    public Page<TourRating> lookupRatings(int tourId, Pageable pageable) throws NoSuchElementException  {
        LOGGER.info("Lookup Rating for tour {}", tourId);
        return tourRatingRepository.findByTourId(verifyTour(tourId).getId(), pageable);
    }

    /**
     * Lookup a page of Ratings for a tour.
     *
     * @param tour Tour.
     * @param pageable paging details
     * @return Requested page of Tour Ratings as RatingDto's
     */
    public Page<RatingDto> getRatings(Tour tour, Pageable pageable){
        LOGGER.info("Getting the ratings for tour {}", tour.getId());
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
        LOGGER.info("Getting the average rating for tour {}", tour.getId());
        Map<String, Double> mapResult = new HashMap<>();
        mapResult.put("average",tourRatingRepository.findByTourId(tour.getId()).stream()
                .mapToInt(TourRating::getScore).average()
                .orElseThrow(() ->
                        new NoSuchElementException("Tour has no Ratings")));
        return mapResult;
    }

    /**
     * Service for many customers to give the same score for a service
     *
     * @param tourId
     * @param score
     * @param customers
     */
    public void rateMany(int tourId,  int score, Integer [] customers) {
        LOGGER.info("Rate tour {} by customers {}", tourId, Arrays.asList(customers).toString());
        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new NoSuchElementException());
        for (Integer c : customers) {
            LOGGER.debug("Attempt to create Tour Rating for customer {}", c);
            tourRatingRepository.save(new TourRating(tour, c, score));
        }
    }

    /**
     * Verify and return the TourRating for a particular Tour and Customer
     * @param tour Tour.
     * @param customerId customer identifier
     * @return the found TourRating
     * @throws NoSuchElementException if no TourRating found
     */
    public TourRating verifyTourRating(Tour tour, int customerId) throws NoSuchElementException{
        LOGGER.info("Verifing Tour Rating for tour {} of customer {}", tour.getId(), customerId);
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
    public RatingDto update(TourRating tourRating, RatingDto ratingDto) throws NoSuchElementException {
        LOGGER.info("Update Rating for tour {} of customers {}", tourRating.getTour().getId(), ratingDto.getCustomerId());
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
    public RatingDto updateWithPatch(TourRating tourRating, RatingDto ratingDto) throws NoSuchElementException {
        LOGGER.info("Update-Patch Rating for tour {} of customers {}", tourRating.getTour().getId(), ratingDto.getCustomerId());
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
    public void delete(TourRating tourRating, int customerId) throws NoSuchElementException {
        LOGGER.info("Delete Rating for tour: {} of customer: {}", tourRating.getTour().getId(), customerId);
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
        LOGGER.info("Looking up for Rating for tour: {}", tourId);
        return tourRepository.findById(tourId).orElseThrow(() ->
                new NoSuchElementException("Tour does not exist " + tourId));
    }
}
