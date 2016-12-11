package piv.service;

import org.apache.commons.collections4.CollectionUtils;
import piv.dto.Price;
import piv.model.PriceKey;
import piv.model.PriceValue;
import piv.repository.PriceKeyRepository;
import piv.repository.PriceKeyRepositoryImpl;
import piv.repository.PriceValueRepository;
import piv.repository.PriceValueRepositoryImpl;

import java.util.*;

/**
 * PriceService implementation
 */
public class PriceServiceImpl implements PriceService {
    private PriceKeyRepository priceKeyRepository = new PriceKeyRepositoryImpl();
    private PriceValueRepository priceValueRepository = new PriceValueRepositoryImpl();

    public PriceServiceImpl(PriceKeyRepository priceKeyRepository, PriceValueRepository priceValueRepository) {
        this.priceKeyRepository = priceKeyRepository;
        this.priceValueRepository = priceValueRepository;
    }

    /**
     * import all prices into data repository
     * @param prices collection for import
     */
    public void importPrices(Collection<Price> prices) {
        for(Price price : prices) {
            PriceKey key = priceKeyRepository.findKeyOrAddNew(
                    new PriceKey(price.getProductCode(), price.getNumber(), price.getDepart())
            );

            PriceValue value = new PriceValue(price.getBegin(), price.getEnd(), price.getValue());
            addPriceValueToHistory(key.getId(), value);
        }
    }

    /**
     * Export all prices from data bank
     * @return collection of prices
     */
    public Collection<Price> exportPrices() {
        Collection<Price> result = new ArrayList<Price>();
        for(PriceKey priceKey : priceKeyRepository.getPriceKeys()) {
            for(PriceValue priceValue : priceValueRepository.getHistoriesByPriceId(priceKey.getId())) {
                result.add(
                        new Price(priceKey.getProductCode(), priceKey.getNumber(), priceKey.getDepart()
                        , priceValue.getBegin(), priceValue.getEnd(), priceValue.getValue())
                );
            }
        }

        return result;
    }

    private void addPriceValueToHistory(Long priceId, PriceValue priceValue) {
        Collection<PriceValue> history = priceValueRepository.getHistoriesByPriceId(priceId);

        if (CollectionUtils.isEmpty(history)) {
            priceValueRepository.addPriceValue(priceId, priceValue);
        } else {
            history = new TreeSet<PriceValue>(
                    priceValueRepository.getHistoriesByPriceId(priceId)
            );

            Set<PriceValue> newHistory = new HashSet<PriceValue>();
            for(PriceValue priceInHistory : history) {
                int compareResult = priceValue.compareTo(priceInHistory);
                if (compareResult > 0) {
                    newHistory.add(priceValue);
                    newHistory.addAll(history);
                    priceValueRepository.rewriteHistoryForPrice(priceId, newHistory);
                    return;
                } else if (compareResult == 0) {
                    int beginCompareResult = priceInHistory.getBegin().compareTo(priceValue.getBegin());
                    int endCompareResult = priceInHistory.getEnd().compareTo(priceValue.getEnd());

                    if (beginCompareResult < 0 && endCompareResult > 0) {
                        if (!priceInHistory.getValue().equals(priceValue.getValue())) {
                            newHistory.add(new PriceValue(priceInHistory.getBegin(), priceValue.getEnd(), priceValue.getValue()));
                            newHistory.add(new PriceValue(priceValue.getBegin(), priceInHistory.getEnd(), priceValue.getValue()));
                        } else {
                            priceValue.setBegin(priceInHistory.getBegin());
                            priceValue.setEnd(priceInHistory.getEnd());
                        }

                    } else if (beginCompareResult < 0) {
                        if (!priceInHistory.getValue().equals(priceValue.getValue())) {
                            priceInHistory.setEnd(priceValue.getBegin());
                            newHistory.add(priceInHistory);
                        } else {
                            priceValue.setBegin(priceInHistory.getBegin());
                        }
                    } else if (endCompareResult > 0) {
                        if (!priceInHistory.getValue().equals(priceValue.getValue())) {
                            priceInHistory.setBegin(priceValue.getEnd());
                            newHistory.add(priceInHistory);
                        } else {
                            priceValue.setEnd(priceInHistory.getEnd());
                        }
                    } else {
                        priceInHistory.setValue(priceValue.getValue());
                    }
                } else {
                    newHistory.add(priceInHistory);
                }
            }

            newHistory.add(priceValue);
            priceValueRepository.rewriteHistoryForPrice(priceId, newHistory);
        }
    }

    public void clearData() {
        priceKeyRepository.clearData();
        priceValueRepository.clearData();
    }
}
