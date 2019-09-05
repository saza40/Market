package se.lexicon.market.component.service;

import com.so4it.gs.rpc.RemoteResultReducer;
import com.so4it.gs.rpc.exception.ResultReducerException;
import com.so4it.gs.rpc.remoting.RemoteResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

public class InstrumentRemoteResultReducer implements RemoteResultReducer<Set<String>> {


    @Override
    public Set<String> reduce(List<RemoteResult<Set<String>>> list) throws ResultReducerException {
        List<String> instruments = new ArrayList<>();
        list.stream().forEach( rr -> instruments.addAll(rr.getResult()));
        return instruments.stream().collect(Collectors.toSet());
    }

}
