package se.lexicon.account.api.server;

import se.lexicon.account.api.*;
import se.lexicon.account.component.client.AccountComponentClient;
import com.so4it.account.api.mapper.AccountBalanceApiMapper;
import com.so4it.account.api.mapper.AccountTransactionApiMapper;
import com.so4it.api.Account;
import com.so4it.api.ApiServiceProvider;
import com.so4it.api.util.StreamObserverErrorHandler;
import com.so4it.common.util.object.Required;
import io.grpc.stub.StreamObserver;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */

@ApiServiceProvider(
        value = Account.NAME,
        specification = Account.PATH,
        version = Account.VERSION,
        properties = Account.PROPERTIES)
public class AccountApiServerImpl extends AccountApiServiceGrpc.AccountApiServiceImplBase{

    private AccountComponentClient accountComponentClient;

    public AccountApiServerImpl(AccountComponentClient accountComponentClient) {
        this.accountComponentClient = Required.notNull(accountComponentClient,"accountComponentClient");
    }

    @Override
    public void createAccountBalance(CreateAccountBalanceRequest apiRequest, StreamObserver<AccountBalance> responseObserver) {
        StreamObserverErrorHandler.of(responseObserver).execute(() -> {
            com.seb.account.component.domain.CreateAccountBalanceRequest componentRequest = AccountBalanceApiMapper.map(apiRequest);
            com.seb.account.component.domain.AccountBalance accountBalance = accountComponentClient.createAccountBalance(componentRequest);
            com.seb.account.api.AccountBalance apiAccountBalance = AccountBalanceApiMapper.map(accountBalance);
            responseObserver.onNext(apiAccountBalance);
            responseObserver.onCompleted();
        }, "Failed creating API balance");
    }

    @Override
    public void createAccountTransaction(CreateAccountTransactionRequest apiRequest, StreamObserver<AccountTransaction> responseObserver) {
        StreamObserverErrorHandler.of(responseObserver).execute(() -> {
            com.seb.account.component.domain.CreateAccountTransactionRequest componentRequest = AccountTransactionApiMapper.map(apiRequest);
            com.seb.account.component.domain.AccountTransaction accountTransaction = accountComponentClient.createAccountTransaction(componentRequest);
            com.seb.account.api.AccountTransaction apiAccountTransaction = AccountTransactionApiMapper.map(accountTransaction);
            responseObserver.onNext(apiAccountTransaction);
            responseObserver.onCompleted();
        }, "Failed creating API Transaction");
    }

}
