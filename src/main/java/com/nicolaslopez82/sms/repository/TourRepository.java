package com.nicolaslopez82.sms.repository;

import com.nicolaslopez82.sms.domain.Difficulty;
import com.nicolaslopez82.sms.domain.Region;
import com.nicolaslopez82.sms.domain.Tour;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Simple Query Method Signature:
 *
 * 1. Return Type.
 * 2. findBy.
 * 3. Entity Attribute name (user camelCase).
 * 4. Parameter with data type of the entity attribute.
 */

public interface TourRepository extends CrudRepository<Tour, Integer> {

    List<Tour> findByTourPackageCode(String code);

    /********************************************
     * BASIC EXAMPLES Spring Data Query Methods *
     *******************************************/
    Optional<Tour> findByTitle(String title);
    List<Tour> findByPrice(Integer price);
    Collection<Tour> findByDifficulty(Difficulty difficulty);

    //Valid
    List<Tour> findByRegion(Region region);

    //Invalid
    //Optional<Tour> findByRegion(Region region); //throws IncorrectResultSizeDataAccessException!!!

    /***************************************************
     * INTERMEDIATE EXAMPLES Spring Data Query Methods *
     **************************************************/
    List<Tour> findByTourPackageAndRegion(String code, Region region);
    List<Tour> findByRegionIn(List<Region> regions);
    List<Tour> findByPriceLessThanEqual(Integer maxPrice);
    List<Tour> findByPriceContaining(Integer maxPrice);
    List<Tour> findByKeywordsContains(String keywords);
    List<Tour> findByTourPackageCodeAndBulletsLike(String code, String searchString);
    //List<Tour> findByTourPackageAnAndDifficultyAndRegionAndPriceLessThan(String code, Difficulty difficulty, Region region, Integer maxPrice);

    /***********************************************
     * ADVANCED EXAMPLES Spring Data Query Methods *
     **********************************************/
    @Query("SELECT t FROM Tour t WHERE t.tourPackage.code = ?1 and t.difficulty = ?2 and t.region = ?3 and t.price <= ?4")
    List<Tour> lookupTour(String code, Difficulty difficulty, Region region, Integer maxPrice);

    //Same as
    List<Tour> findByTourPackageCodeAndDifficultyAndRegionAndPriceLessThan(String code, Difficulty difficulty, Region region, Integer maxPrice);

}




