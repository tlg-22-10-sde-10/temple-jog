package com.game.templejog;

import java.util.List;

/**
 * Created by dev0 on 2/2/23.
 */
public class Encounter {
    String name,type,description,success;
    List<String> weakness;
//    Integer failure,outcome;
    Integer failure;

    public Encounter(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<String> getWeakness() {
        return weakness;
    }

    public void setWeakness(List<String> weakness) {
        this.weakness = weakness;
    }

    public Integer getFailure() {
        return failure;
    }

    public void setFailure(Integer failure) {
        this.failure = failure;
    }

//    public Integer getOutcome() {
//        return outcome;
//    }

//    public void setOutcome(Integer outcome) {
//        this.outcome = outcome;
//    }
}