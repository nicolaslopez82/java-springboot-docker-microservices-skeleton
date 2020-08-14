package com.nicolaslopez82.sms.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegionConverterTest {

    private RegionConverter regionConverter = new RegionConverter();

    @Test
    public void convertToDatabaseColumn() throws Exception{
        assertEquals(regionConverter.convertToDatabaseColumn(Region.Northern_California), Region.Northern_California.getLabel());
    }

    @Test
    public void convertToEntityAttribute() throws Exception{
        assertEquals(regionConverter.convertToEntityAttribute(Region.Northern_California.getLabel()), Region.Northern_California);
    }

}
