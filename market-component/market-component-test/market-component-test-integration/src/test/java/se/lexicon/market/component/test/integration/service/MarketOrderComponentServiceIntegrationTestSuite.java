package se.lexicon.market.component.test.integration.service;

import com.so4it.common.bean.MapBeanContext;
import com.so4it.common.jmx.MBeanRegistry;
import com.so4it.common.jmx.MBeanRegistryFactory;
import com.so4it.common.util.PortUtil;
import com.so4it.configuration.core.DynamicConfiguration;
import com.so4it.configuration.core.Setting;
import com.so4it.configuration.test.common.TestConfigurationSource;
import com.so4it.configuration.test.common.TestConfigurationSourceTestRule;
import com.so4it.gs.rpc.test.common.ServiceBeanStateRegistry;
import com.so4it.gs.rpc.test.common.ServiceBindingRule;
import com.so4it.gs.rpc.test.common.ServiceFrameworkCommonTest;
import com.so4it.registry.core.service.ServiceRegistryClient;
import com.so4it.registry.test.common.FakeServiceRegistry;
import com.so4it.test.gs.rule.GigaSpaceEmbeddedLusTestRule;
import com.so4it.test.springframework.SpringContextRule;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import se.lexicon.market.component.service.MarketOrderComponentServiceProvider;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        MarketOrderComponentServiceIntegrationTest.class
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MarketOrderComponentServiceIntegrationTestSuite {

    private static final int LUS_PORT = PortUtil.nextFreePort();

    private static final ServiceRegistryClient SERVICE_REGISTRY = new FakeServiceRegistry();

    private static final TestConfigurationSource CONFIGURATION_SOURCE = new TestConfigurationSource();

    private static final DynamicConfiguration DYNAMIC_CONFIGURATION = ServiceFrameworkCommonTest.createDynamicConfiguration(CONFIGURATION_SOURCE);

    private static final ServiceBeanStateRegistry SERVICE_BEAN_STATE_REGISTRY = new ServiceBeanStateRegistry();

    private static GigaSpaceEmbeddedLusTestRule GIGA_SPACE_TEST_RULE;

    private static SpringContextRule IMPORT_TEST_RULE;

    private static SpringContextRule EXPORT_TEST_RULE;

    private static TestConfigurationSourceTestRule CONFIGURATION_SOURCE_TEST_RULE;

    private static ServiceBindingRule SERVICE_BINDING_RULE;

    @ClassRule
    public static final RuleChain SUITE_RULE_CHAIN = RuleChain
            .outerRule(getGigaSpacesRule())
            .around(getImportContext())
            .around(getExportContext())
            .around(getConfigurationSourceRule())
            .around(getServiceBindingRule());

    public static SpringContextRule getExportContext() {
        if (EXPORT_TEST_RULE == null) {
            EXPORT_TEST_RULE = new SpringContextRule()
                    .addXmlConfiguration("market-component-dao.xml")
                    .addXmlConfiguration("market-component-service.xml")
                    .addXmlConfiguration("market-component-test-export.xml")
                    .addXmlConfiguration("market-component-test-space.xml")
                    .addBean(MBeanRegistry.DEFAULT_BEAN_NAME, MBeanRegistryFactory.createRegistry())
                    .addBean(ServiceRegistryClient.DEFAULT_API_BEAN_NAME, SERVICE_REGISTRY)
                    .addBean(DynamicConfiguration.DEFAULT_BEAN_NAME, DYNAMIC_CONFIGURATION)
                    .addBean(MapBeanContext.DEFAULT_BEAN_NAME, new MapBeanContext())
                    .addProvider(ServiceFrameworkCommonTest.getPropertyProvider());

        }
        return EXPORT_TEST_RULE;
    }

    public static synchronized SpringContextRule getImportContext() {
        if (IMPORT_TEST_RULE == null) {
            IMPORT_TEST_RULE = new SpringContextRule();
            IMPORT_TEST_RULE.addXmlConfiguration("market-component-client.xml");
            IMPORT_TEST_RULE.addXmlConfiguration("market-component-test-import.xml");
            IMPORT_TEST_RULE.addBean(MapBeanContext.DEFAULT_BEAN_NAME, new MapBeanContext());
            IMPORT_TEST_RULE.addBean(ServiceRegistryClient.DEFAULT_API_BEAN_NAME, SERVICE_REGISTRY);
            IMPORT_TEST_RULE.addBean(DynamicConfiguration.DEFAULT_BEAN_NAME, DYNAMIC_CONFIGURATION);
            IMPORT_TEST_RULE.addBean(ServiceBeanStateRegistry.DEFAULT_BEAN_NAME, SERVICE_BEAN_STATE_REGISTRY);
            IMPORT_TEST_RULE.addProvider(ServiceFrameworkCommonTest.getPropertyProvider());
        }
        return IMPORT_TEST_RULE;
    }


    public static GigaSpaceEmbeddedLusTestRule getGigaSpacesRule() {
        if (GIGA_SPACE_TEST_RULE == null) {
            GIGA_SPACE_TEST_RULE = new GigaSpaceEmbeddedLusTestRule(LUS_PORT);
        }

        return GIGA_SPACE_TEST_RULE;
    }


    public static TestConfigurationSourceTestRule getConfigurationSourceRule() {
        if (CONFIGURATION_SOURCE_TEST_RULE == null) {
            CONFIGURATION_SOURCE_TEST_RULE = new TestConfigurationSourceTestRule(CONFIGURATION_SOURCE);
        }
        return CONFIGURATION_SOURCE_TEST_RULE;
    }

    public static TestConfigurationSource getTestConfigurationSource() {
        return CONFIGURATION_SOURCE;
    }

    public static <T> void withSetting(Setting<T> name, T value) {
        CONFIGURATION_SOURCE.set(name, value);
    }

    public static ServiceBindingRule getServiceBindingRule() {
        if (SERVICE_BINDING_RULE == null) {
            SERVICE_BINDING_RULE = new ServiceBindingRule(SERVICE_BEAN_STATE_REGISTRY);
            SERVICE_BINDING_RULE.addServiceProvider(MarketOrderComponentServiceProvider.class);
        }
        return SERVICE_BINDING_RULE;
    }


}


