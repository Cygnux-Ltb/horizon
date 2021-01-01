package io.horizon.structure.account;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.impl.collector.Collectors2;
import org.slf4j.Logger;

import io.horizon.structure.account.Account.AccountException;
import io.horizon.structure.account.SubAccount.SubAccountException;
import io.mercury.common.collections.MutableMaps;
import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.util.Assertor;

/**
 * 
 * 用于全局管理Account
 * 
 * @author yellow013
 *
 */
@ThreadSafe
public final class AccountManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6883109944757142986L;

	// Logger
	private static final Logger log = CommonLoggerFactory.getLogger(AccountManager.class);

	// 存储Account信息, 一对一关系,以accountId索引
	private static final MutableIntObjectMap<Account> AccountMap = MutableMaps.newIntObjectHashMap();

	// 存储Account信息, 一对一关系, 以investorId索引
	private static final MutableMap<String, Account> AccountMapByInvestorId = MutableMaps.newUnifiedMap();

	// 存储Account信息, 多对一关系, 以subAccountId索引
	private static final MutableIntObjectMap<Account> AccountMapBySubAccountId = MutableMaps.newIntObjectHashMap();

	// 存储SubAccount信息, 一对一关系, 以subAccountId索引
	private static final MutableIntObjectMap<SubAccount> SubAccountMap = MutableMaps.newIntObjectHashMap();

	// 初始化标识
	private static final AtomicBoolean isInitialized = new AtomicBoolean(false);

	private AccountManager() {
	}

	public static void initialize(@Nonnull SubAccount... subAccounts) throws IllegalStateException {
		if (isInitialized.compareAndSet(false, true)) {
			try {
				Assertor.requiredLength(subAccounts, 1, "subAccounts");
				// 建立subAccount相关索引
				Stream.of(subAccounts).collect(Collectors2.toSet()).each(AccountManager::putSubAccount);
				// 建立account相关索引
				Stream.of(subAccounts).map(SubAccount::getAccount).collect(Collectors2.toSet())
						.each(AccountManager::putAccount);
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
		AccountMap.put(account.getAccountId(), account);
		AccountMapByInvestorId.put(account.getInvestorId(), account);
		log.info("Put account, accountId==[{}], investorId==[{}]", account.getAccountId(), account.getInvestorId());
	}

	private static void putSubAccount(SubAccount subAccount) {
		SubAccountMap.put(subAccount.getSubAccountId(), subAccount);
		AccountMapBySubAccountId.put(subAccount.getSubAccountId(), subAccount.getAccount());
		log.info("Put subAccount, subAccountId==[{}], accountId==[{}]", subAccount.getSubAccountId(),
				subAccount.getAccount().getAccountId());
	}

	public static boolean isInitialized() {
		return isInitialized.get();
	}

	@Nonnull
	public static Account getAccount(int accountId) throws AccountException {
		Account account = AccountMap.get(accountId);
		if (account == null)
			throw new AccountException("Account error in mapping : accountId[" + accountId + "] no mapped instance");
		return account;
	}

	@Nonnull
	public static Account getAccountBySubAccountId(int subAccountId) throws AccountException {
		Account account = AccountMapBySubAccountId.get(subAccountId);
		if (account == null)
			throw new AccountException(
					"Account error in mapping : subAccountId[" + subAccountId + "] no mapped instance");
		return account;
	}

	@Nonnull
	public static Account getAccountByInvestorId(String investorId) throws AccountException {
		Account account = AccountMapByInvestorId.get(investorId);
		if (account == null)
			throw new AccountException("Account error in mapping : investorId[" + investorId + "] no mapped instance");
		return account;
	}

	@Nonnull
	public static SubAccount getSubAccount(int subAccountId) throws SubAccountException {
		SubAccount subAccount = SubAccountMap.get(subAccountId);
		if (subAccount == null)
			throw new SubAccountException(
					"SubAccount error in mapping : subAccountId[" + subAccountId + "] no mapped instance");
		return subAccount;
	}

	public static Account setAccountNotTradable(int accountId) {
		return getAccount(accountId).disable();
	}

	public static Account setAccountTradable(int accountId) {
		return getAccount(accountId).enable();
	}

	public static boolean isAccountTradable(int accountId) {
		return getAccount(accountId).isEnabled();
	}

	public static SubAccount setSubAccountNotTradable(int subAccountId) {
		return getSubAccount(subAccountId).disable();
	}

	public static SubAccount setSubAccountTradable(int subAccountId) {
		return getSubAccount(subAccountId).enable();
	}

	public static boolean isSubAccountTradable(int subAccountId) {
		return getSubAccount(subAccountId).isEnabled();
	}

	@Override
	public String toString() {
		return "";
	}

}
