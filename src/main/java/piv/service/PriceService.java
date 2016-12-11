package piv.service;

import piv.dto.Price;
import piv.model.PriceValue;

import java.util.Collection;

public interface PriceService {
    void importPrices(Collection<Price> prices);
    Collection<Price> exportPrices();
    void clearData();
}
