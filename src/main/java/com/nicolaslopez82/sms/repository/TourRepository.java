package com.nicolaslopez82.sms.repository;

import com.nicolaslopez82.sms.domain.Difficulty;
import com.nicolaslopez82.sms.domain.Region;
import com.nicolaslopez82.sms.domain.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

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

/**
 * Controlling API Exposure
 * @RepositoryRestResource(exported = false) Class annotation.
 * @RestResource(exported = false) Method annotation.
 */
public interface TourRepository extends PagingAndSortingRepository<Tour, Integer> {

    Page<Tour> findByTourPackageCode(String code, Pageable pageable);

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

    @Override
    @RestResource(exported = false)
    Iterable<Tour> findAll(Sort sort);

    @Override
    @RestResource(exported = false)
    <S extends Tour> S save(S s);

    @Override
    @RestResource(exported = false)
    <S extends Tour> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    void deleteById(Integer integer);

    @Override
    @RestResource(exported = false)
    void delete(Tour tour);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Tour> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}




