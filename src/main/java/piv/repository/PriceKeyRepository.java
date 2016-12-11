package piv.repository;

import piv.model.PriceKey;

import java.util.Collection;

public interface PriceKeyRepository {
    PriceKey findKeyOrAddNew(PriceKey priceKey);
    Collection<PriceKey> getPriceKeys();
    void clearData();
}
