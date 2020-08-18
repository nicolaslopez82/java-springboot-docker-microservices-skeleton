package com.nicolaslopez82.sms.service;

import com.nicolaslopez82.sms.domain.*;
import com.nicolaslopez82.sms.web.RatingDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TourRatingServiceIntegrationTest {

    private static final int CUSTOMER_ID = 456;
    private static final int TOUR_ID = 1;
    private static final int NOT_A_TOUR_ID = 123;

    @Autowired
    private TourRatingService service;

    //Happy Path delete existing TourRating.
    @Test
    public void delete() {
        List<TourRating> tourRatings = service.lookupAll();
        service.delete(tourRatings.get(0), tourRatings.get(0).getCustomerId());
        assertEquals(service.lookupAll().size(), is(tourRatings.size() - 1));
    }

    //UnHappy Path, Tour NOT_A_TOUR_ID does not exist
    @Test(expected = NoSuchElementException.class)
    public void deleteException() {
        TourPackage tourPackage = new TourPackage ("xx", "mock_not_valid");
        Tour tour = new Tour("not_valid_tour", "description", "blurb", 3000, "duration", "bullets",
                "keywords", tourPackage, Difficulty.Difficult, Region.Northern_California);
        TourRating tourRating = new TourRating(tour, 1234, 5);
        service.delete(tourRating, 1234);
    }

    //Happy Path to Create a new Tour Rating
    @Test
    public void createTourRating() {

        TourPackage tourPackage = new TourPackage ("xx", "mock_not_valid");
        Tour tour = new Tour("not_valid_tour", "description", "blurb", 3000, "duration", "bullets",
                "keywords", tourPackage, Difficulty.Difficult, Region.Northern_California);
        TourRating tourRating = new TourRating(tour, 1234, 5);
        RatingDto ratingDto = new RatingDto(tourRating);

        //would throw NoSuchElementException if TourRating for TOUR_ID by CUSTOMER_ID already exists
        service.createTourRating(tour, ratingDto);

        //Verify New Tour Rating created.
        TourRating newTourRating = service.verifyTourRating(tour, CUSTOMER_ID);
        assertEquals(newTourRating.getTour().getId(), is(TOUR_ID));
        assertEquals(newTourRating.getCustomerId(), is(CUSTOMER_ID));
        assertEquals(newTourRating.getScore(), is(2));
        assertEquals(newTourRating.getComment(), is ("it was fair"));
    }

    //UnHappy Path, Tour NOT_A_TOUR_ID does not exist
    @Test(expected = NoSuchElementException.class)
    public void createNewException() {
        TourPackage tourPackage = new TourPackage ("xx", "mock_not_valid");
        Tour tour = new Tour("not_valid_tour", "description", "blurb", 3000, "duration", "bullets",
                "keywords", tourPackage, Difficulty.Difficult, Region.Northern_California);
        TourRating tourRating = new TourRating(tour, 1234, 5);
        RatingDto ratingDto = new RatingDto(tourRating);
        service.createTourRating(tour, ratingDto);
    }

    //Happy Path many customers Rate one tour
    @Test
    public void rateMany() {
        int ratings = service.lookupAll().size();
        service.rateMany(TOUR_ID, 5, new Integer[]{100, 101, 102});
        assertEquals(service.lookupAll().size(), is(ratings + 3));
    }

    //Unhappy Path, 2nd Invocation would create duplicates in the database, DataIntegrityViolationException thrown
    @Test(expected = DataIntegrityViolationException.class)
    public void rateManyProveRollback() {
        int ratings = service.lookupAll().size();
        Integer customers[] = {100, 101, 102};
        service.rateMany(TOUR_ID, 3, customers);
        service.rateMany(TOUR_ID, 3, customers);
    }

    //Happy Path, Update a Tour Rating already in the database
    @Test
    public void update() {

        TourPackage tourPackage = new TourPackage ("xx", "mock_valid");
        Tour tour = new Tour("valid_tour", "description", "blurb", 3000, "duration", "bullets",
                "keywords", tourPackage, Difficulty.Easy, Region.Southern_California);
        TourRating tourRating = new TourRating(tour, CUSTOMER_ID, 3);
        RatingDto ratingDto = new RatingDto(tourRating);

        createNewException();
        ratingDto = service.update(tourRating, ratingDto);
        assertEquals(tourRating.getTour().getId(), is(TOUR_ID));
        assertEquals(tourRating.getCustomerId(), is(CUSTOMER_ID));
        assertEquals(tourRating.getScore(), is(3));
    }
}
