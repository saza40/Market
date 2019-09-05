package se.lexicon.order.component.test.integration.client;

import se.lexicon.market.component.client.MarketOrderComponentClient;
import se.lexicon.market.component.entity.MarketOrderEntity;
import se.lexicon.market.component.test.common.domain.MarketOrderTestBuilder;
import se.lexicon.market.component.test.integration.service.MarketOrderComponentServiceIntegrationTestSuite;
import com.so4it.test.category.IntegrationTest;
import com.so4it.test.gs.rule.ClearGigaSpaceTestRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.RuleChain;
import org.openspaces.core.GigaSpace;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
@Category(IntegrationTest.class)
public class MarketOrderComponentClientIntegrationTest {

    @ClassRule
    public static final RuleChain SUITE_RULE_CHAIN = MarketOrderComponentServiceIntegrationTestSuite.SUITE_RULE_CHAIN;

    @Rule
    public ClearGigaSpaceTestRule clearGigaSpaceTestRule = new ClearGigaSpaceTestRule(MarketOrderComponentServiceIntegrationTestSuite.getExportContext().getBean(GigaSpace.class));

    @Test
    public void testCreatingMarketOrder(){
        MarketOrderComponentClient MarketorderComponentClient = MarketOrderComponentServiceIntegrationTestSuite.getImportContext().getBean(MarketOrderComponentClient.class);
        MarketorderComponentClient.placeMarketOrder(MarketOrderTestBuilder.builder().build());


        Assert.assertEquals(1, MarketOrderComponentServiceIntegrationTestSuite.getExportContext().getBean(GigaSpace.class).count(MarketOrderEntity.templateBuilder().build()));

    }

}
