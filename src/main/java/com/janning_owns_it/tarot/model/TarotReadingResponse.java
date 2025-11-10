package com.janning_owns_it.tarot.model;

import java.util.List;

public class TarotReadingResponse {

    String arcaneResponse;
    List<String> sortedCardsInOrder;

    public String getArcaneResponse() {
        return arcaneResponse;
    }

    public void setArcaneResponse(String arcaneResponse) {
        this.arcaneResponse = arcaneResponse;
    }

    public List<String> getSortedCardsInOrder() {
        return sortedCardsInOrder;
    }

    public void setSortedCardsInOrder(List<String> sortedCardsInOrder) {
        this.sortedCardsInOrder = sortedCardsInOrder;
    }
}
