package com.nicolaslopez82.sms.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TourTest {

    @Test
    public void testConstructorAndGetters() throws Exception{
        TourPackage tourPackage = new TourPackage("CC", "name");
        Tour tour = new Tour("title","description","blurb", 50, "1 day", "bullet",
                "keywords", tourPackage, Difficulty.Difficult, Region.Central_Coast);
        assertNull(tour.getId());
        assertEquals(tour.getTitle(), "title");
        assertEquals(tour.getDescription(), "description");
        assertEquals(tour.getBlurb(), "blurb");
        assertEquals(tour.getPrice(), 50);
        assertEquals(tour.getDuration(), "1 day");
        assertEquals(tour.getBullets(), "bullet");
        assertEquals(tour.getKeywords(), "keywords");
        assertEquals(tour.getTourPackage().getCode(), "CC");
        assertEquals(tour.getDifficulty(), Difficulty.Difficult);
        assertEquals(tour.getRegion(), Region.Central_Coast);
    }

    @Test
    public void equalsHashcodeVerify() {
        TourPackage tourPackage = new TourPackage("CC","name");
        Tour tour1 = new Tour("title","description","blurb", 50, "1 day", "bullet",
                "keywords", tourPackage, Difficulty.Difficult, Region.Central_Coast);
        Tour tour2 = new Tour("title","description","blurb", 50, "1 day", "bullet",
                "keywords", tourPackage, Difficulty.Difficult, Region.Central_Coast);

        assertEquals(tour1, tour2);
        assertEquals(tour1.hashCode(), tour2.hashCode());
    }
}
