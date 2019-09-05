package se.lexicon.market.component.dao;

import com.j_spaces.core.client.SQLQuery;
import org.openspaces.extensions.QueryExtension;
import se.lexicon.market.component.entity.MarketOrderEntity;
import se.lexicon.market.componment.dao.MarketOrderDao;
import com.so4it.component.dao.gs.AbstractSpaceDao;
import org.openspaces.core.GigaSpace;

import java.math.BigDecimal;




/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public class MarketOrderDaoImpl extends AbstractSpaceDao<MarketOrderEntity, String> implements MarketOrderDao {

    public MarketOrderDaoImpl(GigaSpace gigaSpace) {
        super(gigaSpace);
    }

    @Override
    public BigDecimal sum() {
        return QueryExtension.sum(getGigaSpace(),new SQLQuery<>(MarketOrderEntity.class,""),"amount");
    }

}



