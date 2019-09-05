package se.lexicon.market.component.service;

import com.so4it.gs.rpc.RemoteResultReducer;
import com.so4it.gs.rpc.exception.ResultReducerException;
import com.so4it.gs.rpc.remoting.RemoteResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public class BigDecimalRemoteResultReducer implements RemoteResultReducer<BigDecimal> {

    @Override
    public BigDecimal reduce(List<RemoteResult<BigDecimal>> list) throws ResultReducerException {
        return BigDecimal.valueOf( list.stream().map( rr -> rr.getResult().doubleValue()).reduce(0.0,Double::sum));
    }
}

