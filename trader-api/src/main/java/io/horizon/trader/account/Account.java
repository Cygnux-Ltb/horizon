package io.horizon.trader.account;

import static io.horizon.trader.account.Account.AccountConfig.AccountId;
import static io.horizon.trader.account.Account.AccountConfig.Balance;
import static io.horizon.trader.account.Account.AccountConfig.BrokerId;
import static io.horizon.trader.account.Account.AccountConfig.BrokerName;
import static io.horizon.trader.account.Account.AccountConfig.Credit;
import static io.horizon.trader.account.Account.AccountConfig.InvestorId;
import static io.horizon.trader.account.Account.AccountConfig.Remark;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.set.MutableSet;

import com.typesafe.config.Config;

import io.mercury.common.collections.MutableSets;
import io.mercury.common.config.ConfigOption;
import io.mercury.common.config.ConfigWrapper;
import io.mercury.common.fsm.EnableableComponent;
import io.mercury.common.lang.Asserter;
import io.mercury.common.util.StringSupport;

/**
 * 实际账户, 对应一个实际的经纪商账户
 * 
 * @author yellow013
 *
 */

public final class Account extends EnableableComponent implements Comparable<Account> {

	// 账户ID
	private final int accountId;

	// 经纪商ID
	private final String brokerId;

	// 经纪商名称
	private final String brokerName;

	// 经纪商提供的投资者ID
	private final String investorId;

	// 账户余额
	private long balance;

	// 信用额度
	private long credit;

	// 备注
	private String remark = "";

	// 备用, 数组下标, 用于快速访问本账户对应的仓位信息集合
	// private int positionManagerIndex;

	// 全部子账户
	private final MutableSet<SubAccount> subAccounts = MutableSets.newUnifiedSet();

	/**
	 * 
	 * @param config
	 */
	public Account(@Nonnull Config config) {
		this(new ConfigWrapper<>(config));
	}

	/**
	 * 
	 * @param wrapper
	 */
	private Account(@Nonnull ConfigWrapper<AccountConfig> wrapper) {
		this(wrapper.getIntOrThrows(AccountId), wrapper.getStringOrThrows(BrokerId),
				wrapper.getStringOrThrows(BrokerName), wrapper.getStringOrThrows(InvestorId),
				wrapper.getLong(Balance, 0L), wrapper.getLong(Credit, 0L));
		this.remark = wrapper.getString(Remark, "");
	}

	/**
	 * 
	 * @param accountId
	 * @param brokerName
	 * @param investorId
	 */
	public Account(int accountId, @Nonnull String brokerId, @Nonnull String brokerName, @Nonnull String investorId) {
		this(accountId, brokerId, brokerName, investorId, 0L, 0L);
	}

	/**
	 * 
	 * @param accountId
	 * @param brokerId
	 * @param brokerName
	 * @param investorId
	 * @param balance
	 * @param credit
	 */
	public Account(int accountId, @Nonnull String brokerId, @Nonnull String brokerName, @Nonnull String investorId,
			long balance, long credit) {
		Asserter.greaterThan(accountId, 0, "accountId");
		Asserter.nonEmpty(brokerId, "brokerId");
		Asserter.nonEmpty(brokerName, "brokerName");
		Asserter.nonEmpty(investorId, "investorId");
		this.accountId = accountId;
		this.brokerId = brokerId;
		this.brokerName = brokerName;
		this.investorId = investorId;
		this.balance = balance;
		this.credit = credit;
		enable();
	}

	/**
	 * 仅提供给同一包內的SubAccount调用
	 * 
	 * @param subAccount
	 */
	void addSubAccount(SubAccount subAccount) {
		subAccounts.add(subAccount);
	}

	public int getAccountId() {
		return accountId;
	}

	public String getBrokerId() {
		return brokerId;
	}

	public String getBrokerName() {
		return brokerName;
	}

	public String getInvestorId() {
		return investorId;
	}

	public MutableSet<SubAccount> getSubAccounts() {
		return subAccounts;
	}

	public long getBalance() {
		return balance;
	}

	public long getCredit() {
		return credit;
	}

	public String getRemark() {
		return remark;
	}

	public Account setBalance(long balance) {
		this.balance = balance;
		return this;
	}

	public Account setCredit(long credit) {
		this.credit = credit;
		return this;
	}

	public Account setRemark(String remark) {
		this.remark = remark;
		return this;
	}

	public final static class AccountException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6421678546942382394L;

		public AccountException(String message) {
			super(message);
		}

	}

	private static final String AccountIdField = "{\"accountId\" : ";
	private static final String BrokerNameField = ", \"brokerName\" : ";
	private static final String InvestorIdField = ", \"investorId\" : ";
	private static final String BalanceField = ", \"balance\" : ";
	private static final String CreditField = ", \"credit\" : ";
	private static final String RemarkField = ", \"remark\" : ";
	private static final String SubAccountTotalField = ", \"subAccountTotal\" : ";
	private static final String IsEnabledField = ", \"isEnabled\" : ";
	private static final String End = "}";

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(200);
		builder.append(AccountIdField);
		builder.append(accountId);
		builder.append(BrokerNameField);
		builder.append(brokerName);
		builder.append(InvestorIdField);
		builder.append(investorId);
		builder.append(BalanceField);
		builder.append(balance);
		builder.append(CreditField);
		builder.append(credit);
		builder.append(RemarkField);
		builder.append(remark);
		builder.append(SubAccountTotalField);
		builder.append(subAccounts.size());
		builder.append(IsEnabledField);
		builder.append(isEnabled());
		builder.append(End);
		return builder.toString();
	}

	@Override
	public int compareTo(Account o) {
		return this.accountId < o.accountId ? -1 : this.accountId > o.accountId ? 1 : 0;
	}

	public static enum AccountConfig implements ConfigOption {

		AccountId("sys.accountId"),

		BrokerId("sys.brokerId"),

		BrokerName("sys.brokerName"),

		InvestorId("sys.investorId"),

		Balance("sys.balance"),

		Credit("sys.credit"),

		Remark("sys.remark");

		private final String configName;

		private AccountConfig(String configName) {
			this.configName = configName;
		}

		@Override
		public String getConfigName() {
			return configName;
		}
	}

	public static void main(String[] args) {
		System.out.println(StringSupport.toText(null));
		Account account = new Account(1, "ZSQH", "ZSQH", "200500");
		System.out.println(account.toString());
		System.out.println(account.toString().length());

	}

}
