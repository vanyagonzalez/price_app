package piv.repository;

import piv.model.PriceValue;

import java.util.*;

/**
 * work with repository implementation
 */
public class PriceValueRepositoryImpl implements PriceValueRepository {
    private static final Map<Long, Collection<PriceValue>> PRICE_HISTORY = new HashMap<Long, Collection<PriceValue>>();

    public void addPriceValue(Long priceId, PriceValue priceValue) {
        Collection<PriceValue> history = PRICE_HISTORY.get(priceId);
        if (history == null) {
            history = new HashSet<PriceValue>();
            history.add(priceValue);
            PRICE_HISTORY.put(priceId, history);
        }

        history.add(priceValue);
    }

    public void rewriteHistoryForPrice(Long priceId, Collection<PriceValue> priceValues) {
        PRICE_HISTORY.put(priceId, priceValues);
    }

    public Collection<PriceValue> getHistoriesByPriceId(Long priceId) {
        return PRICE_HISTORY.get(priceId);
    }

    public void clearData() {
        PRICE_HISTORY.clear();
    }
}
