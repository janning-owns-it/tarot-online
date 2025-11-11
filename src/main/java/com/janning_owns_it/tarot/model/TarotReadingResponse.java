package com.janning_owns_it.tarot.model;

import java.util.Set;

public class TarotReadingResponse {

    String arcaneResponse;
    Set<String> sortedCardsInOrder;

    public String getArcaneResponse() {
        return arcaneResponse;
    }

    public void setArcaneResponse(String arcaneResponse) {
        this.arcaneResponse = arcaneResponse;
    }

    public Set<String> getSortedCardsInOrder() {
        return sortedCardsInOrder;
    }

    public void setSortedCardsInOrder(Set<String> sortedCardsInOrder) {
        this.sortedCardsInOrder = sortedCardsInOrder;
    }
}
