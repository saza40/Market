package se.lexicon.market.component.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.so4it.common.domain.AbstractIterable;
import com.so4it.common.domain.AbstractIterableDeserializer;
import com.so4it.common.domain.AbstractIterableSerializer;
import com.so4it.common.domain.DomainClass;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
@DomainClass
@JsonDeserialize(
        using = MarketOrders.Deserializer.class
)
@JsonSerialize(
        using = MarketOrders.Serializer.class
)
public class MarketOrders extends AbstractIterable<MarketOrder> {

    private static final long serialVersionUID = 1L;

    public MarketOrders() {
        super(MarketOrder.class, MarketOrders.class);
    }

    public static MarketOrders valueOf(MarketOrder... endpointRegistrations) {
        return (MarketOrders) AbstractIterable.valueOf(endpointRegistrations, MarketOrder.class, MarketOrders.class);
    }

    public static MarketOrders valueOf(Set<MarketOrder> endpointRegistrations) {
        return (MarketOrders) AbstractIterable.valueOf(endpointRegistrations, MarketOrder.class, MarketOrders.class);
    }

    public static MarketOrders valueOf(Iterable<MarketOrder> endpointRegistrations) {
        return (MarketOrders) AbstractIterable.valueOf(endpointRegistrations, MarketOrder.class, MarketOrders.class);
    }

    public Map<String, MarketOrders> map(Function<MarketOrder, String> mapper) {
        return null;
    }

    public static class Serializer extends AbstractIterableSerializer<MarketOrder> {
        protected Serializer() {
            super(MarketOrder.class, MarketOrders.class);
        }
    }

    public static class Deserializer extends AbstractIterableDeserializer<MarketOrder> {
        protected Deserializer() {
            super(MarketOrder.class, MarketOrders.class);
        }
    }
}
