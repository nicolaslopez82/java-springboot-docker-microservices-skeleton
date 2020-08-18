package com.nicolaslopez82.sms.service;

import com.nicolaslopez82.sms.domain.Tour;
import com.nicolaslopez82.sms.domain.TourRating;
import com.nicolaslopez82.sms.repository.TourRatingRepository;
import com.nicolaslopez82.sms.repository.TourRepository;
import com.nicolaslopez82.sms.web.RatingDto;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TourRatingServiceTest {

    private static final int CUSTOMER_ID = 123;
    private static final int TOUR_ID = 1;
    private static final int TOUR_RATING_ID = 100;

    @Mock
    private TourRepository tourRepositoryMock;
    @Mock
    private TourRatingRepository tourRatingRepositoryMock;

    @InjectMocks //Autowire TourRatingService(tourRatingRepositoryMock, tourRepositoryMock)
    private TourRatingService service;

    @Mock
    private Tour tourMock;
    @Mock
    private TourRating tourRatingMock;
    @Mock
    private RatingDto ratingDtoMock;

    /**
     * Mock responses to commonly invoked methods.
     */
    @Before
    public void setupReturnValuesOfMockMethods(){
        when(tourRepositoryMock.findById(TOUR_ID)).thenReturn(Optional.of(tourMock));
        when(tourMock.getId()).thenReturn(TOUR_ID);
        when(tourRatingRepositoryMock.findByTourIdAndCustomerId(TOUR_ID, CUSTOMER_ID)).thenReturn(Optional.of(tourRatingMock));
        when(tourRatingRepositoryMock.findByTourId(TOUR_ID)).thenReturn(Arrays.asList(tourRatingMock));
    }

    /**************************************************************************************
     *
     * Verify the service return value
     *
     **************************************************************************************/
    @Test
    public void lookupRatingById() {
        when(tourRatingRepositoryMock.findById(TOUR_RATING_ID)).thenReturn(Optional.of(tourRatingMock));

        //invoke and verify lookupRatingById
        assertEquals(service.lookupRatingById(TOUR_RATING_ID).get(), tourRatingMock);
    }

    @Test
    public void lookupAll() {
        when(tourRatingRepositoryMock.findAll()).thenReturn(Arrays.asList(tourRatingMock));

        //invoke and verify lookupAll
        assertEquals(service.lookupAll().get(0), tourRatingMock);
    }

    @Test
    public void getAverageScore() {
        when(tourRatingMock.getScore()).thenReturn(10);

        //invoke and verify getAverage rating.
        assertEquals(service.getAverage(tourMock), 10.0);
    }

    @Test
    public void lookupRating(){
        //create mocks of Pageable and Page (only needed in this test).
        Pageable pageable = mock(Pageable.class);
        Page page = mock(Page.class);
        when(tourRatingRepositoryMock.findByTourId(1, pageable)).thenReturn(page);

        //invoke and verify lookupRatings.
        assertEquals(service.lookupRatings(TOUR_ID, pageable), page);
    }

    /**************************************************************************************
     *
     * Verify the invocation of dependencies.
     *
     **************************************************************************************/
    @Test
    public void delete(){
        //invoke delete
        service.delete(tourRatingMock, CUSTOMER_ID);

        verify(tourRatingRepositoryMock).delete(any(TourRating.class));
    }

    @Test
    public void rateMany(){
        //invoke rateMany
        service.rateMany(TOUR_ID, 10, new Integer[]{CUSTOMER_ID, CUSTOMER_ID + 1});

        //verify tourRatingRepository.save invoked twice.
        verify(tourRatingRepositoryMock, times(2)).save(any(TourRating.class));
    }

    @Test
    public void update(){
        //invoke update
        service.update(tourRatingMock, ratingDtoMock);

        //verify tourRatingRepository.save invoked once
        verify(tourRatingRepositoryMock).save(any(TourRating.class));

        //verify and tourRating setter methods invoked.
        verify(tourRatingMock).setComment("Great");
        verify(tourRatingMock).setScore(5);
    }

    @Test
    public void updateWithPatch() {
        //invoke updateSome
        service.updateWithPatch(tourRatingMock, ratingDtoMock);

        //verify tourRatingRepository.save invoked once
        verify(tourRatingRepositoryMock).save(any(TourRating.class));

        //verify and tourRating setter methods invoked
        verify(tourRatingMock).setComment("awful");
        verify(tourRatingMock).setScore(1);
    }

    /**************************************************************************************
     *
     * Verify the invocation of dependencies
     * Capture parameter values.
     * Verify the parameters.
     *
     *************************************************************************************/

    @Test
    public void createTourRating(){
        //prepare to capture a TourRating Object.
        ArgumentCaptor<TourRating> tourRatingArgumentCaptor = ArgumentCaptor.forClass(TourRating.class);

        //invoke createTourRating.
        service.createTourRating(tourMock, ratingDtoMock);

        //verify tourRatingRepository.save invoked one and capture the TourRating Object.
        verify(tourRatingRepositoryMock).save(tourRatingArgumentCaptor.capture());

        //verify the attributes of the Tour Rating Object.
        assertEquals(tourRatingArgumentCaptor.getValue().getTour(), tourMock);
        assertEquals(tourRatingArgumentCaptor.getValue().getCustomerId(), CUSTOMER_ID);
        assertEquals(tourRatingArgumentCaptor.getValue().getScore(), 2);
        assertEquals(tourRatingArgumentCaptor.getValue().getComment(), "ok");
    }


}
