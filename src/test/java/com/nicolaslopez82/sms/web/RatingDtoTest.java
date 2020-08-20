package com.nicolaslopez82.sms.web;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RatingDtoTest {

    private static final Integer SCORE = 1;
    private static final Integer CUSTOMER_ID = 2;

    @Test
    public void testConstructor() throws Exception {
        RatingDto dto = new RatingDto(1,"comment", 2);
        assertEquals(dto.getScore(), SCORE );
        assertEquals(dto.getComment(), "comment");
        assertEquals(dto.getCustomerId(), CUSTOMER_ID);
    }

    @Test
    public void testSetters() {
        RatingDto dto = new RatingDto();
        dto.setComment("comment");
        dto.setCustomerId(2);
        dto.setScore(1);
        assertEquals(dto.getScore(), SCORE);
        assertEquals(dto.getComment(),"comment");
        assertEquals(dto.getCustomerId(), CUSTOMER_ID);
    }
}
