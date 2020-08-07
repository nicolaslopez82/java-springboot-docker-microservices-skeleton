package com.nicolaslopez82.sms.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.nicolaslopez82.sms.domain.RatingDtoModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RatingDtoModelAssembler extends RepresentationModelAssemblerSupport<RatingDto, RatingDtoModel> {

    public RatingDtoModelAssembler() {
        super(TourRatingController.class, RatingDtoModel.class);
    }

    @Override
    public RatingDtoModel toModel(RatingDto entity) {
        RatingDtoModel ratingDtoModel = instantiateModel(entity);

        ratingDtoModel.add(linkTo(
                methodOn(TourRatingController.class)
                .getAllRatingsForTour(entity.getCustomerId()))
                .withSelfRel());

        return ratingDtoModel;
    }

//    @Override
//    public CollectionModel<TourRatingModel> toCollectionModel(Iterable<? extends TourRating> entities)
//    {
//        CollectionModel<TourRatingModel> tourRatingModel = super.toCollectionModel(entities);
//        return tourRatingModel;
//    }

    @Override
    public CollectionModel<RatingDtoModel> toCollectionModel(Iterable<? extends RatingDto> entities)
    {
        CollectionModel<RatingDtoModel> ratingDtoModel = super.toCollectionModel(entities);
        return ratingDtoModel;
    }
}
