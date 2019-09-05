package se.lexicon.market.component.test.integration.service;

import com.so4it.test.category.IntegrationTest;
import com.so4it.test.common.Assert;
import com.so4it.test.common.probe.Poller;
import com.so4it.test.common.probe.SatisfiedWhenTrueReturned;
import com.so4it.test.gs.rule.ClearGigaSpaceTestRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.RuleChain;
import org.openspaces.core.GigaSpace;
import se.lexicon.market.component.domain.*;
import se.lexicon.market.component.service.MarketOrderComponentService;
import se.lexicon.market.component.test.common.domain.MarketOrderTestBuilder;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
@Category(IntegrationTest.class)
public class MarketOrderComponentServiceIntegrationTest {

    @ClassRule
    public static final RuleChain SUITE_RULE_CHAIN = MarketOrderComponentServiceIntegrationTestSuite.SUITE_RULE_CHAIN;

    @Rule
    public ClearGigaSpaceTestRule clearGigaSpaceTestRule = new ClearGigaSpaceTestRule(MarketOrderComponentServiceIntegrationTestSuite.getExportContext().getBean(GigaSpace.class));

    @Test
    public void testMarketComponentServiceExists() {
        MarketOrderComponentService marketOrderComponentService = MarketOrderComponentServiceIntegrationTestSuite.getImportContext().getBean(MarketOrderComponentService.class);
        Assert.assertNotNull(marketOrderComponentService);
    }

    @Test
    public void testPlaceMarket() throws InterruptedException {
        //Set<Currency> currencies = Currency.getAvailableCurrencies();
        //System.out.println(currencies);

        MarketOrderComponentService marketOrderComponentService = MarketOrderComponentServiceIntegrationTestSuite.getImportContext().getBean(MarketOrderComponentService.class);

        MarketOrder marketOrder = MarketOrderTestBuilder.builder().withSsn("111111").withInstrument("ABB").withAmount(BigDecimal.ONE).build();
        marketOrderComponentService.placeMarketOrder(marketOrder);

        Poller.pollAndCheck(SatisfiedWhenTrueReturned.create(() ->  marketOrderComponentService.getMarketOrders("ABB","111111").size() == 1));

    }

    @Test
    public void testPlaceTwoMarkets() throws InterruptedException {
        MarketOrderComponentService marketOrderComponentService = MarketOrderComponentServiceIntegrationTestSuite.getImportContext().getBean(MarketOrderComponentService.class);

        MarketOrder market1 = MarketOrderTestBuilder.builder().withSsn("111222").withOrderId("111222-1").withInstrument("ABB").withAmount(BigDecimal.ONE).build();
        MarketOrder market2 = MarketOrderTestBuilder.builder().withSsn("111222").withOrderId("111222-2").withInstrument("ABB").withAmount(BigDecimal.TEN).build();

        marketOrderComponentService.placeMarketOrder(market1);
        marketOrderComponentService.placeMarketOrder(market2);

        //MarketOrders markets = marketOrderComponentService.getMarketOrders("ABB","111222");

        Poller.pollAndCheck(SatisfiedWhenTrueReturned.create(() ->  marketOrderComponentService.getMarketOrders("ABB","111222").size() == 2));

    }

    @Test
    public void testMatchMarket() {
        MarketOrderComponentService marketOrderComponentService = MarketOrderComponentServiceIntegrationTestSuite.getImportContext().getBean(MarketOrderComponentService.class);

        MarketOrder market1 = MarketOrderTestBuilder.builder()
                //.withId("111111")
                .withSsn("111111")
                .withInstrument("ABB")
                .withNoOfItems(100)
                .withSide(Side.BUY)
                .withMinMaxValue(Money.builder()
                    .withAmount((BigDecimal.valueOf(550d)))
                    .withCurrency(Currency.getInstance("SEK"))
                    .build())
                .build();

        MarketOrder market2 = MarketOrderTestBuilder.builder()
                //.withId("222222")
                .withSsn("222222")
                .withInstrument("ABB")
                .withNoOfItems(100)
                .withSide(Side.SELL)
                .withMinMaxValue(Money.builder()
                    .withAmount((BigDecimal.valueOf(500d)))
                    .withCurrency(Currency.getInstance("SEK"))
                    .build())
                .build();

        MarketOrder market3 = MarketOrderTestBuilder.builder()
                //.withId("333333")
                .withSsn("333333")
                .withInstrument("ABB")
                .withNoOfItems(50)
                .withSide(Side.SELL)
                .withMinMaxValue(Money.builder()
                    .withAmount((BigDecimal.valueOf(480d)))
                    .withCurrency(Currency.getInstance("SEK"))
                    .build())
                .build();

        MarketOrder market4 = MarketOrderTestBuilder.builder()
                //.withId("444444")
                .withSsn("444444")
                .withInstrument("ABB")
                .withNoOfItems(50)
                .withSide(Side.SELL)
                .withMinMaxValue(Money.builder()
                    .withAmount((BigDecimal.valueOf(490d)))
                    .withCurrency(Currency.getInstance("SEK"))
                    .build())
                .build();

        MarketOrder market5 = MarketOrderTestBuilder.builder()
                //.withId("555555")
                .withSsn("555555")
                .withInstrument("ABB")
                .withNoOfItems(100)
                .withSide(Side.BUY)
                .withMinMaxValue(Money.builder()
                    .withAmount((BigDecimal.valueOf(500d)))
                    .withCurrency(Currency.getInstance("SEK"))
                    .build())
                .build();

        marketOrderComponentService.placeMarketOrder(market1);
        marketOrderComponentService.placeMarketOrder(market2);
        marketOrderComponentService.placeMarketOrder(market3);
        marketOrderComponentService.placeMarketOrder(market4);
        marketOrderComponentService.placeMarketOrder(market5);

        MarketOrders marketOrders1 = marketOrderComponentService.getMarketOrders("ABB","111111");
        MarketOrders marketOrders2 = marketOrderComponentService.getMarketOrders("ABB","222222");
        MarketOrders marketOrders3 = marketOrderComponentService.getMarketOrders("ABB","333333");
        MarketOrders marketOrders4 = marketOrderComponentService.getMarketOrders("ABB","444444");
        MarketOrders marketOrders5 = marketOrderComponentService.getMarketOrders("ABB","555555");

//        Assert.assertEquals(1, markets1.getFirst().getMarketDeals().size());
//        Assert.assertEquals(1, markets2.getFirst().getMarketDeals().size());
//        Assert.assertEquals(1, markets3.getFirst().getMarketDeals().size());
//        Assert.assertEquals(1, markets4.getFirst().getMarketDeals().size());
//        Assert.assertEquals(2, markets5.getFirst().getMarketDeals().size());

    }

    @Test
    public void testGetAllMarketComponent() throws InterruptedException {
        MarketOrderComponentService marketOrderComponentService = MarketOrderComponentServiceIntegrationTestSuite.getImportContext().getBean(MarketOrderComponentService.class);

        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("111111").withInstrument("ABB").withAmount(BigDecimal.ONE).build());
        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("111111").withInstrument("ABB").withAmount(BigDecimal.TEN).build());
        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("111111").withInstrument("SAAB").withAmount(BigDecimal.TEN).build());

        Poller.pollAndCheck(SatisfiedWhenTrueReturned.create(() ->  marketOrderComponentService.getMarketOrders("ABB","111111").size() == 2));
        Poller.pollAndCheck(SatisfiedWhenTrueReturned.create(() ->  marketOrderComponentService.getMarketOrders("SAAB","111111").size() == 1));

    }

    @Test
    public void testGetInstruments() throws InterruptedException {
        MarketOrderComponentService marketOrderComponentService = MarketOrderComponentServiceIntegrationTestSuite.getImportContext().getBean(MarketOrderComponentService.class);

        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("111111").withInstrument("ABB").withAmount(BigDecimal.ONE).build());
        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("111111").withInstrument("ABB").withAmount(BigDecimal.TEN).build());
        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("111111").withInstrument("SAAB").withAmount(BigDecimal.TEN).build());

        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("222222").withInstrument("ABB").withAmount(BigDecimal.ONE).build());
        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("222222").withInstrument("SAAB").withAmount(BigDecimal.TEN).build());
        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("222222").withInstrument("ERICSSON").withAmount(BigDecimal.TEN).build());
        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("222222").withInstrument("ABB").withAmount(BigDecimal.TEN).build());

        Poller.pollAndCheck(SatisfiedWhenTrueReturned.create(() ->  marketOrderComponentService.getInstruments("222222").size() == 3));
        Poller.pollAndCheck(SatisfiedWhenTrueReturned.create(() ->  marketOrderComponentService.getInstruments("111111").size() == 2));

        //Assert.assertEquals(3, marketOrderComponentService.getInstruments("222222").size());
        //Assert.assertEquals(2, marketOrderComponentService.getInstruments("111111").size());

        Assert.assertEquals("[ABB, SAAB, ERICSSON]", marketOrderComponentService.getInstruments("222222").toString());
        Assert.assertEquals("[ABB, SAAB]", marketOrderComponentService.getInstruments("111111").toString());

    }

    @Test
    public void testGetTotalMarketValue() throws InterruptedException {
        MarketOrderComponentService marketOrderComponentService = MarketOrderComponentServiceIntegrationTestSuite.getImportContext().getBean(MarketOrderComponentService.class);

        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("111111").withInstrument("ABB").withAmount(BigDecimal.ONE).build());
        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("111111").withInstrument("ABB").withAmount(BigDecimal.TEN).build());
        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("111111").withInstrument("SAAB").withAmount(BigDecimal.TEN).build());

        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("222222").withInstrument("ABB").withAmount(BigDecimal.ONE).build());
        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("222222").withInstrument("SAAB").withAmount(BigDecimal.TEN).build());
        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("222222").withInstrument("ERICSSON").withAmount(BigDecimal.TEN).build());
        marketOrderComponentService.placeMarketOrder(MarketOrderTestBuilder.builder().withSsn("222222").withInstrument("ABB").withAmount(BigDecimal.TEN).build());

        Poller.pollAndCheck(SatisfiedWhenTrueReturned.create(() ->  marketOrderComponentService.getInstruments("222222").size() == 3));

        Assert.assertEquals(BigDecimal.valueOf(52.0), marketOrderComponentService.getTotalMarketValueOfAllMarkets());

    }

}
