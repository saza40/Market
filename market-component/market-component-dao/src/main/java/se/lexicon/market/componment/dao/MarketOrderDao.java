package se.lexicon.market.componment.dao;

import se.lexicon.market.component.entity.MarketOrderEntity;
import com.so4it.component.GenericDao;

import java.math.BigDecimal;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public interface MarketOrderDao extends GenericDao<MarketOrderEntity, String> {

    BigDecimal sum();

}

