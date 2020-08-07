package com.nicolaslopez82.sms.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "TourRating")
@Relation(collectionRelation = "TourRating")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TourRatingModel extends RepresentationModel<TourRatingModel> {

    private Integer id;
    private Tour tour;
    private Integer customerId;
    private Integer score;
    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
