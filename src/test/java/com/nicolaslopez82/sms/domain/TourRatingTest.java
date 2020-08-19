package com.nicolaslopez82.sms.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TourRatingTest {

    private Tour tour = new Tour("title","description","blurb", 50, "1 day", "bullet",
            "keywords",new TourPackage("CC","name"), Difficulty.Difficult, Region.Central_Coast);

    @Test
    public void testConstructor1() throws Exception {
        TourRating rating = new TourRating(tour, 1, 1, "comment");
        testIt(rating);
        assertEquals(rating.getComment(), "comment");
    }

    @Test
    public void testConstructor2() throws Exception {
        TourRating rating = new TourRating(tour, 1, 1);
        testIt(rating);
        assertEquals(rating.getComment(), "Terrible");
    }

    private void testIt(TourRating rating){
        assertEquals(rating.getId(), null);
        assertEquals(rating.getTour(), tour);
        assertEquals(rating.getScore(), 1);
        assertEquals(rating.getCustomerId(), 1);
    }

    @Test
    public void equalsHashcodeVerify() {
        TourRating rating1 = new TourRating(tour, 1, 1, "comment");
        TourRating rating2 = new TourRating(tour, 1, 1, "comment");

        assertEquals(rating1,rating2);
        assertEquals(rating1.hashCode(), rating2.hashCode());
    }
}
