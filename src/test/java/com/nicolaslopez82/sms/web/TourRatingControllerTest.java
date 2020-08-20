package com.nicolaslopez82.sms.web;

import com.nicolaslopez82.sms.domain.*;
import com.nicolaslopez82.sms.service.TourRatingService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 *
 * Invoke the Controller methods via HTTP.
 * Do not invoke the tourRatingService methods, use Mock instead
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TourRatingControllerTest {

    //These Tour and Rating id's do not already exist in the Data Bases.
    private static final int TOUR_ID = 999;
    private static final int CUSTOMER_ID = 1000;
    private static final int SCORE = 3;
    private static final String COMMENT = "comments";
    private static final String TOUR_RATING_URL = "/tours/" + TOUR_ID + "/ratings";

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private TourRatingService serviceMock;

    @Mock
    private TourRating tourRatingMock;

    @Mock
    private Tour tourMock;

    private RatingDto ratingDto = new RatingDto(SCORE, COMMENT, CUSTOMER_ID );

    @Before
    public void setupReturnValuesOfMockMethods(){
        when(tourRatingMock.getCustomerId()).thenReturn(CUSTOMER_ID);
        when(tourRatingMock.getComment()).thenReturn(COMMENT);
        when(tourRatingMock.getScore()).thenReturn(SCORE);
        when(tourMock.getId()).thenReturn(TOUR_ID);
    }

    /**
     * HTTP POST /tours/{tourId}/ratings
     */
    @Test
    @Ignore
    public void createTourRating() throws Exception {
        restTemplate.postForEntity(TOUR_RATING_URL, ratingDto, Void.class);

        TourPackage tourPackage = new TourPackage("BC", "British Columbia");
        Tour tour = new Tour("Big Sur Retreat",
                "description",
                "blurb",
                750,
                "3 days",
                "5",
                "keyboards",
                tourPackage,
                Difficulty.Medium,
                Region.Central_Coast);

        ratingDto.setCustomerId(CUSTOMER_ID);
        ratingDto.setScore(SCORE);
        ratingDto.setComment(COMMENT);

        verify(this.serviceMock).createTourRating(tour, ratingDto);
    }
}
