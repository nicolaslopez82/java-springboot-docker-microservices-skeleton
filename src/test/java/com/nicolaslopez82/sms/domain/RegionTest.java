package com.nicolaslopez82.sms.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegionTest {

    @Test
    public void findBylabel() throws Exception{
        assertEquals(Region.Central_Coast, Region.findByLabel("Central Coast"));
        assertEquals(Region.Northern_California, Region.findByLabel("Northern California"));
        assertEquals(Region.Southern_California, Region.findByLabel("Southern California"));
        assertEquals(Region.Varies, Region.findByLabel("Varies"));
    }

    @Test
    public void getLabel() throws Exception{
        assertEquals(Region.Central_Coast.getLabel(), "Central Coast");
        assertEquals(Region.Northern_California.getLabel(), "Northern California");
        assertEquals(Region.Southern_California.getLabel(), "Southern California");
        assertEquals(Region.Varies.getLabel(), "Varies");
    }
}
