package piv.service;

import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.*;
import piv.dto.Price;
import piv.model.PriceKey;
import piv.repository.PriceKeyRepositoryImpl;
import piv.repository.PriceValueRepositoryImpl;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import static org.testng.Assert.assertEquals;

public class PriceServiceTest {
    private PriceService priceService;

    @BeforeMethod
    public void beforeMethod() {
        priceService = new PriceServiceImpl(new PriceKeyRepositoryImpl(), new PriceValueRepositoryImpl());
    }

    @AfterMethod
    public void afterMethod() {
        priceService.clearData();
    }

    @Test
    public void shouldAddNewPrice() {
        Price price = Price.random();
        Collection<Price> importPrices = new HashSet<Price>();
        importPrices.add(price);
        priceService.importPrices(importPrices);

        Collection<Price> exportPrices = priceService.exportPrices();
        assertEquals(exportPrices.size(), importPrices.size());
    }

    @Test
    public void shouldImportPriceCollection() {
        Collection<Price> importPrices = preparePrices();
        priceService.importPrices(importPrices);

        Collection<Price> exportPrices = priceService.exportPrices();
        assertEquals(exportPrices.size(), importPrices.size());
    }

    private Collection<Price> preparePrices() {
        Collection<Price> result = new HashSet<Price>();
        PriceKey priceKey = PriceKey.random();

        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < RandomUtils.nextInt(1, 10); i++) {
            calendar.add(Calendar.DAY_OF_YEAR, -1 * RandomUtils.nextInt(1, 100));
            Date endDate = calendar.getTime();
            calendar.add(Calendar.DAY_OF_YEAR, -1 * RandomUtils.nextInt(1, 100));
            Date beginDate = calendar.getTime();

            result.add(new Price(priceKey.getProductCode(), priceKey.getNumber(), priceKey.getDepart(), beginDate, endDate, RandomUtils.nextLong(0, Long.MAX_VALUE)));
        }
        return result;
    }
}
