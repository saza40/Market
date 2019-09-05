package se.lexicon.market.api.mapper;

import com.so4it.api.mapper.ValueMapper;
import com.so4it.common.util.Mapper;


/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public final class OrderApiMapper {

    public static Order map(se.lexicon.order.api.Order order){

        return Mapper.of(accountBalance,AccountBalance:: builder)
                .map(com.seb.account.api.AccountBalance::getId,AccountBalance.Builder::withId)
                .map(com.seb.account.api.AccountBalance::getArrangementId,AccountBalance.Builder::withArrangementId)
                .map(com.seb.account.api.AccountBalance::getBatchId,AccountBalance.Builder::withBatchId)
                .map(com.seb.account.api.AccountBalance::getInsertionTimestamp, ValueMapper::map, AccountBalance.Builder::withInsertionTimestamp)
                .map(com.seb.account.api.AccountBalance::getBalanceList,BalanceApiMapper::map,AccountBalance.Builder::withBalances)
                .build(AccountBalance.Builder::build);

    }

    public static  com.seb.account.api.AccountBalance map(AccountBalance accountBalance){
        return Mapper.of(accountBalance,com.seb.account.api.AccountBalance::newBuilder)
                .map(AccountBalance::getId,com.seb.account.api.AccountBalance.Builder::setId)
                .map(AccountBalance::getArrangementId,com.seb.account.api.AccountBalance.Builder::setArrangementId)
                .map(AccountBalance::getBatchId,com.seb.account.api.AccountBalance.Builder::setBatchId)
                .map(AccountBalance::getInsertionTimestamp, ValueMapper::map, com.seb.account.api.AccountBalance.Builder::setInsertionTimestamp)
                .map(AccountBalance::getBalances,BalanceApiMapper::map,com.seb.account.api.AccountBalance.Builder::addAllBalance)
                .build(com.seb.account.api.AccountBalance.Builder::build);

    }




    public static com.seb.account.component.domain.CreateAccountBalanceRequest map(com.seb.account.api.CreateAccountBalanceRequest request){

        return Mapper.of(request,com.seb.account.component.domain.CreateAccountBalanceRequest::builder)
                .map(com.seb.account.api.CreateAccountBalanceRequest::getArrangementId,com.seb.account.component.domain.CreateAccountBalanceRequest.Builder::withArrangementId)
                .map(com.seb.account.api.CreateAccountBalanceRequest::getBatchId,com.seb.account.component.domain.CreateAccountBalanceRequest.Builder::withBatchId)
                .map(com.seb.account.api.CreateAccountBalanceRequest::getInsertionTimestamp, ValueMapper::map, com.seb.account.component.domain.CreateAccountBalanceRequest.Builder::withInsertionTimestamp)
                .map(com.seb.account.api.CreateAccountBalanceRequest::getBalanceList,BalanceApiMapper::map,com.seb.account.component.domain.CreateAccountBalanceRequest.Builder::withBalances)
                .build(com.seb.account.component.domain.CreateAccountBalanceRequest.Builder::build);

    }

    public static com.seb.account.api.CreateAccountBalanceRequest map(com.seb.account.component.domain.CreateAccountBalanceRequest request){

        return Mapper.of(request,com.seb.account.api.CreateAccountBalanceRequest::newBuilder)
                .map(com.seb.account.component.domain.CreateAccountBalanceRequest::getArrangementId,com.seb.account.api.CreateAccountBalanceRequest.Builder::setArrangementId)
                .map(com.seb.account.component.domain.CreateAccountBalanceRequest::getBatchId,com.seb.account.api.CreateAccountBalanceRequest.Builder::setBatchId)
                .map(com.seb.account.component.domain.CreateAccountBalanceRequest::getInsertionTimestamp, ValueMapper::map, com.seb.account.api.CreateAccountBalanceRequest.Builder::setInsertionTimestamp)
                .map(com.seb.account.component.domain.CreateAccountBalanceRequest::getBalances,BalanceApiMapper::map,com.seb.account.api.CreateAccountBalanceRequest.Builder::addAllBalance)
                .build(com.seb.account.api.CreateAccountBalanceRequest.Builder::build);

    }

}
