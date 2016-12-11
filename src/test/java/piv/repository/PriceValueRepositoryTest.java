package piv.repository;

import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import piv.model.PriceKey;
import piv.model.PriceValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.testng.Assert.assertEquals;

/**
 * Tests for PriceValueRepository
 */
public class PriceValueRepositoryTest {
    private PriceKeyRepository priceKeyRepository;
    private PriceValueRepository priceValueRepository;
    private PriceKey key;

    @BeforeTest
    private void initTest() {

    }

    @BeforeMethod
    public void beforeMethod() {
        priceKeyRepository = new PriceKeyRepositoryImpl();
        priceValueRepository = new PriceValueRepositoryImpl();
        for (int i = 0; i < RandomUtils.nextInt(1, 10); i++) {
            priceKeyRepository.findKeyOrAddNew(PriceKey.random());
        }

        ArrayList<PriceKey> oldKeys = new ArrayList<PriceKey>(priceKeyRepository.getPriceKeys());
        key = oldKeys.get(RandomUtils.nextInt(0, oldKeys.size()));
    }

    @AfterMethod
    public void afterMethod() {
        priceKeyRepository.clearData();
    }

    @Test
    public void shouldAddNewPriceValueToHistory() {
        priceValueRepository.addPriceValue(key.getId(), PriceValue.random());
        Collection<PriceValue> priceValues = priceValueRepository.getHistoriesByPriceId(key.getId());
        assertEquals(priceValues.size(), 1);
    }

    @Test
    public void shouldRewriteHistory() {
        priceValueRepository.addPriceValue(key.getId(), PriceValue.random());
        Collection<PriceValue> priceValues = preparePriceValues();
        priceValueRepository.rewriteHistoryForPrice(key.getId(), priceValues);
        Collection<PriceValue> rewrote = priceValueRepository.getHistoriesByPriceId(key.getId());
        assertEquals(rewrote.size(), priceValues.size());
    }


    private Collection<PriceValue> preparePriceValues() {
        Collection<PriceValue> priceValues = new HashSet<PriceValue>();
        for (int i =0; i < RandomUtils.nextInt(1, 10); i++) {
            priceValues.add(PriceValue.random());
        }
        return priceValues;
    }
}
