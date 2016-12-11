package piv.repository;

import piv.model.PriceValue;

import java.util.Collection;
import java.util.Set;

public interface PriceValueRepository {
    void addPriceValue(Long priceId, PriceValue priceValue);
    void rewriteHistoryForPrice(Long priceId, Collection<PriceValue> priceValues);
    Collection<PriceValue> getHistoriesByPriceId(Long priceId);
    void clearData();
}
