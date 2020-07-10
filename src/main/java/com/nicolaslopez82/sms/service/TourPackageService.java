package com.nicolaslopez82.sms.service;

import com.nicolaslopez82.sms.domain.TourPackage;
import com.nicolaslopez82.sms.repository.TourPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class TourPackageService {

    public TourPackageRepository tourPackageRepository;

    @Autowired
    public TourPackageService(TourPackageRepository tourPackageRepository){
        this.tourPackageRepository = tourPackageRepository;
    }

    //TODO Create a Tour Package Injector.

    /**
     * Create a Tour Package
     *
     * @param code of the package.
     * @param name of the package
     *
     * @return new or existing tour package.
     */
    public TourPackage createTourPackage(String code, String name){
        return tourPackageRepository.findById(code).orElse(
                tourPackageRepository.save(new TourPackage(code, name)));
    }

    /**
     * Lookup All TourPackages.
     *
     * @return All Tour Packages.
     */
    public Iterator<TourPackage> lookup(){
        return tourPackageRepository.findAll().iterator();
    }

    /**
     * Total of Packages.
     *
     * @return total of packages.
     */
    public long total(){
        return tourPackageRepository.count();
    }

}
