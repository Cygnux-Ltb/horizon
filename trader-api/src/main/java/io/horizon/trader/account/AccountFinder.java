package io.horizon.trader.account;

import io.horizon.trader.account.Account.AccountException;
import io.horizon.trader.account.SubAccount.SubAccountException;
import io.mercury.common.collections.MutableMaps;
import io.mercury.common.lang.Asserter;
import io.mercury.common.log4j2.Log4j2LoggerFactory;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.impl.collector.Collectors2;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

/**
 * 用于全局管理Account
 *
 * @author yellow013
 */
@NotThreadSafe
public final class AccountFinder implements Serializable {

    @Serial
    private static final long serialVersionUID = -6883109944757142986L;

    // logger
    private static final Logger log = Log4j2LoggerFactory.getLogger(AccountFinder.class);

    // 存储Account信息, 一对一关系,以accountId索引
    private static final MutableIntObjectMap<Account> Accounts = MutableMaps.newIntObjectHashMap();

    // 存储Account信息, 一对一关系, 以investorId索引
    private static final MutableMap<String, Account> AccountsByInvestorId = MutableMaps.newUnifiedMap();

    // 存储Account信息, 多对一关系, 以subAccountId索引
    private static final MutableIntObjectMap<Account> AccountsBySubAccountId = MutableMaps.newIntObjectHashMap();

    // 存储SubAccount信息, 一对一关系, 以subAccountId索引
    private static final MutableIntObjectMap<SubAccount> SubAccounts = MutableMaps.newIntObjectHashMap();

    // 初始化标识
    @Deprecated
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);

    private AccountFinder() {
    }

    @Deprecated
    public static void initialize(@Nonnull SubAccount... subAccounts) throws IllegalStateException {
        if (isInitialized.compareAndSet(false, true)) {
            try {
                Asserter.requiredLength(subAccounts, 1, "subAccounts");
                // 建立subAccount相关索引
                Stream.of(subAccounts).collect(Collectors2.toSet()).each(AccountFinder::putSubAccount);
                // 建立account相关索引
                Stream.of(subAccounts).map(SubAccount::getAccount).collect(Collectors2.toSet())
                        .each(AccountFinder::putAccount);
            } catch (Exception e) {
                isInitialized.set(false);
                IllegalStateException se = new IllegalStateException("AccountKeeper initialization failed", e);
                log.error("AccountKeeper initialization failed", se);
                throw se;
            }
        } else {
            IllegalStateException e = new IllegalStateException(
                    "AccountKeeper Has been initialized, cannot be initialize again");
            log.error("AccountKeeper already initialized", e);
            throw e;
        }
    }

    private static void putAccount(Account account) {
        Accounts.put(account.getAccountId(), account);
        AccountsByInvestorId.put(account.getInvestorId(), account);
        log.info("Put account, accountId==[{}], investorId==[{}]", account.getAccountId(), account.getInvestorId());
    }

    static void putSubAccount(SubAccount subAccount) {
        SubAccounts.put(subAccount.getSubAccountId(), subAccount);
        AccountsBySubAccountId.put(subAccount.getSubAccountId(), subAccount.getAccount());
        log.info("Put subAccount, subAccountId==[{}], accountId==[{}]", subAccount.getSubAccountId(),
                subAccount.getAccount().getAccountId());
        putAccount(subAccount.getAccount());
    }

    @Deprecated
    public static boolean isInitialized() {
        return isInitialized.get();
    }

    @Nonnull
    public static Account getAccount(int accountId) throws AccountException {
        Account account = Accounts.get(accountId);
        if (account == null)
            throw new AccountException("Account error in mapping : accountId[" + accountId + "] no mapped instance");
        return account;
    }

    @Nonnull
    public static Account getAccountBySubAccountId(int subAccountId) throws AccountException {
        Account account = AccountsBySubAccountId.get(subAccountId);
        if (account == null)
            throw new AccountException(
                    "Account error in mapping : subAccountId[" + subAccountId + "] no mapped instance");
        return account;
    }

    @Nonnull
    public static Account getAccountByInvestorId(String investorId) throws AccountException {
        Account account = AccountsByInvestorId.get(investorId);
        if (account == null)
            throw new AccountException("Account error in mapping : investorId[" + investorId + "] no mapped instance");
        return account;
    }

    @Nonnull
    public static SubAccount getSubAccount(int subAccountId) throws SubAccountException {
        SubAccount subAccount = SubAccounts.get(subAccountId);
        if (subAccount == null)
            throw new SubAccountException(
                    "SubAccount error in mapping : subAccountId[" + subAccountId + "] no mapped instance");
        return subAccount;
    }

    public static void setAccountNotTradable(int accountId) {
        Account account = getAccount(accountId);
        account.disable();
    }

    public static void setAccountTradable(int accountId) {
        Account account = getAccount(accountId);
        account.enable();
    }

    public static boolean isAccountTradable(int accountId) {
        return getAccount(accountId).isEnabled();
    }

    public static SubAccount setSubAccountNotTradable(int subAccountId) {
        SubAccount subAccount = getSubAccount(subAccountId);
        subAccount.disable();
        return subAccount;
    }

    public static SubAccount setSubAccountTradable(int subAccountId) {
        SubAccount subAccount = getSubAccount(subAccountId);
        subAccount.enable();
        return subAccount;
    }

    public static boolean isSubAccountTradable(int subAccountId) {
        return getSubAccount(subAccountId).isEnabled();
    }

    @Override
    public String toString() {
        return "";
    }

}
