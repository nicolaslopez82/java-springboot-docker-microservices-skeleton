package com.nicolaslopez82.sms.web;

import com.nicolaslopez82.sms.domain.Tour;
import com.nicolaslopez82.sms.domain.TourRating;
import com.nicolaslopez82.sms.service.TourRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Tour Rating Controller
 */
@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {

    private TourRatingService tourRatingService;

    @Autowired
    public TourRatingController(TourRatingService tourRatingService) {
        this.tourRatingService = tourRatingService;
    }

    protected TourRatingController() {}

    /**
     * Create a Tour Rating.
     *
     * @param tourId tour identifier
     * @param ratingDto rating data transfer object
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId,
                                 @RequestBody @Validated RatingDto ratingDto){
        Tour tour = verifyTour(tourId);
        tourRatingService.createTourRating(tour, ratingDto);
    }

    /**
     * Lookup a the Ratings for a tour.
     *
     * @param tourId Tour Identifier
     * @return All Tour Ratings as RatingDto's
     */
    @GetMapping(path = "/ratings/tour")
    public List<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId){
        Tour tour = verifyTour(tourId);
        return tourRatingService.getAllRatingsForTour(tour);
    }

    /**
     * Lookup a page of Ratings for a tour.
     *
     * @param tourId Tour Identifier
     * @param pageable paging details
     * @return Requested page of Tour Ratings as RatingDto's
     */
    @GetMapping
    public Page<RatingDto> getRatings(@PathVariable(value = "tourId") int tourId, Pageable pageable){
        Tour tour = verifyTour(tourId);
        return tourRatingService.getRatings(tour, pageable);
    }

    /**
     * Calculate the average Score of a Tour.
     *
     * @param tourId tour identifier
     * @return Tuple of "average" and the average value.
     */
    @GetMapping(path = "/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") int tourId){
        Tour tour = verifyTour(tourId);
        return tourRatingService.getAverage(tour);
    }

    /**
     * Verify and return the TourRating for a particular tourId and Customer
     * @param tourId tour identifier
     * @param customerId customer identifier
     * @return the found TourRating
     * @throws NoSuchElementException if no TourRating found
     */
    private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException {
        Tour tour =  verifyTour(tourId);
        return tourRatingService.verifyTourRating(tour, customerId);
    }

    /**
     * Update score and comment of a Tour Rating
     *
     * @param tourId tour identifier
     * @param ratingDto rating Data Transfer Object
     * @return The modified Rating DTO.
     */
    @PutMapping
    public RatingDto updateWithPut(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
        return tourRatingService.updateWithPut(rating, ratingDto);
    }

    /**
     * Update score or comment of a Tour Rating
     *
     * @param tourId tour identifier
     * @param ratingDto rating Data Transfer Object
     * @return The modified Rating DTO.
     */
    @PatchMapping
    public RatingDto updateWithPatch(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
        return tourRatingService.updateWithPatch(rating, ratingDto);
    }

    /**
     * Delete a Rating of a tour made by a customer
     *
     * @param tourId tour identifier
     * @param customerId customer identifier
     */
    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "customerId") int customerId) {
        TourRating rating = verifyTourRating(tourId, customerId);
        tourRatingService.delete(rating, customerId);
    }

    /**
     * Verify and return the Tour given a tourId.
     *
     * @param tourId tour identifier
     * @return the found Tour
     * @throws NoSuchElementException if no Tour found.
     */
    private Tour verifyTour(int tourId) throws NoSuchElementException {
        return tourRatingService.verifyTour(tourId);
    }

    /**
     * Exception handler if NoSuchElementException is thrown in this Controller
     *
     * @param ex exception
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
