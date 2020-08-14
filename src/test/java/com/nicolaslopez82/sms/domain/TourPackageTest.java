package com.nicolaslopez82.sms.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TourPackageTest {

    @Test
    public void testConstructorAndGetters() throws Exception {
        TourPackage tourPackage = new TourPackage("CC", "name");
        assertEquals(tourPackage.getCode(), "CC");
        assertEquals(tourPackage.getName(), "name");
    }

    @Test
    public void equalsHashCodeVerify(){
        TourPackage tourPackage_1 = new TourPackage("CC", "name");
        TourPackage tourPackage_2 = new TourPackage("CC", "name");

        assertEquals(tourPackage_1, tourPackage_2);
        assertEquals(tourPackage_1.hashCode(), tourPackage_2.hashCode());
    }
}
