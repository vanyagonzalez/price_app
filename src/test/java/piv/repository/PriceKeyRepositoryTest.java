package piv.repository;

import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import piv.model.PriceKey;

import java.util.ArrayList;
import java.util.Collection;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Tests for PriceKeyRepository
 */
public class PriceKeyRepositoryTest {
    private PriceKeyRepository priceKeyRepository;

    @BeforeMethod
    public void beforeMethod() {
        priceKeyRepository = new PriceKeyRepositoryImpl();
        for (int i =0; i < RandomUtils.nextInt(1, 10); i++) {
            priceKeyRepository.findKeyOrAddNew(PriceKey.random());
        }
    }

    @AfterMethod
    public void afterMethod() {
        priceKeyRepository.clearData();
    }

    @Test
    public void shouldAddNewKey() {
        Collection<PriceKey> oldKeys = priceKeyRepository.getPriceKeys();

        PriceKey newKey = PriceKey.random();
        PriceKey addedKey = priceKeyRepository.findKeyOrAddNew(newKey);
        assertNotNull(addedKey.getId());

        Collection<PriceKey> newKeys = priceKeyRepository.getPriceKeys();
        assertEquals(oldKeys.size() + 1, newKeys.size());
    }

    @Test
    public void shouldFindPresentedKey() {
        ArrayList<PriceKey> oldKeys = new ArrayList<PriceKey>(priceKeyRepository.getPriceKeys());

        PriceKey key = oldKeys.get(RandomUtils.nextInt(0, oldKeys.size()));
        PriceKey foundKey = priceKeyRepository.findKeyOrAddNew(key);
        assertEquals(key, foundKey);

        Collection<PriceKey> newKeys = priceKeyRepository.getPriceKeys();
        assertEquals(oldKeys.size(), newKeys.size());
    }
}
