package piv.repository;

import piv.exception.EntityIsAbsent;
import piv.model.PriceKey;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository for keys
 */
public class PriceKeyRepositoryImpl implements PriceKeyRepository {
    private static final Map<Long, PriceKey> PRICE_KEYS = new HashMap<Long, PriceKey>();
    private static final AtomicLong currentMaxId = new AtomicLong(0);

    public PriceKey findKeyOrAddNew(PriceKey priceKey) {
        Long id;
        try {
            id = isExist(priceKey);
        } catch (EntityIsAbsent entityIsAbsent) {
            id = currentMaxId.incrementAndGet();
        }
        priceKey.setId(id);
        PRICE_KEYS.put(id, priceKey);
        return priceKey;
    }

    private Long isExist(PriceKey priceKey) throws EntityIsAbsent {
        for(PriceKey key : PRICE_KEYS.values()) {
            if (key.equals(priceKey)) {
                return key.getId();
            }
        }

        throw new EntityIsAbsent(String.format("Key entity [%s] is absent", priceKey.toString()));
    }

    public Collection<PriceKey> getPriceKeys() {
        return new HashSet<PriceKey>(PRICE_KEYS.values());
    }

    public void clearData() {
        PRICE_KEYS.clear();
    }
}
