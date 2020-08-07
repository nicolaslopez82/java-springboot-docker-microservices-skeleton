package com.nicolaslopez82.sms.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@JsonRootName(value = "RatingDto")
@Relation(collectionRelation = "RatingDto")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RatingDtoModel extends RepresentationModel<RatingDtoModel> {

    private Integer score;
    private String comment;
    private Integer customerId;

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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
