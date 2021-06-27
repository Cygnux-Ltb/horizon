package io.horizon.transaction.account;

import static io.mercury.common.util.StringUtil.toText;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.set.MutableSet;

import io.mercury.common.collections.MutableSets;
import io.mercury.common.fsm.EnableableComponent;
import io.mercury.common.util.Assertor;
import io.mercury.common.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 实际账户, 对应一个实际的经纪商账户
 * 
 * @author yellow013
 *
 */
@Getter
public final class Account extends EnableableComponent implements Comparable<Account> {

	// 账户ID
	private final int accountId;

	// 经纪商名称
	private final String brokerName;

	// 经纪商提供的投资者ID
	private final String investorId;

	// 账户余额
	@Setter
	@Accessors(chain = true)
	private long balance;

	// 信用额度

	@Setter
	@Accessors(chain = true)
	private long credit;

	// 备注
	@Setter
	@Accessors(chain = true)
	private String remark = "NONE";

	// 备用, 数组下标, 用于快速访问本账户对应的仓位信息集合
	// private int positionManagerIndex;

	// 子账户集合
	private final MutableSet<SubAccount> subAccounts = MutableSets.newUnifiedSet();

	/**
	 * 
	 * @param accountId
	 * @param brokerName
	 * @param investorId
	 */
	public Account(int accountId, @Nonnull String brokerName, @Nonnull String investorId) {
		this(accountId, brokerName, investorId, 0, 0);
	}

	/**
	 * 
	 * @param accountId
	 * @param brokerName
	 * @param investorId
	 * @param balance
	 * @param credit
	 */
	public Account(int accountId, @Nonnull String brokerName, @Nonnull String investorId, long balance, long credit) {
		Assertor.nonEmpty(brokerName, "brokerName");
		Assertor.nonEmpty(investorId, "investorId");
		this.accountId = accountId;
		this.brokerName = brokerName;
		this.investorId = investorId;
		this.balance = balance;
		this.credit = credit;
	}

	/**
	 * 仅提供给同一包內的SubAccount调用
	 * 
	 * @param subAccount
	 */
	void addSubAccount(SubAccount subAccount) {
		subAccounts.add(subAccount);
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
		builder.append(toText(brokerName));
		builder.append(InvestorIdField);
		builder.append(investorId);
		builder.append(BalanceField);
		builder.append(balance);
		builder.append(CreditField);
		builder.append(credit);
		builder.append(RemarkField);
		builder.append(toText(remark));
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

	public static void main(String[] args) {
		System.out.println(StringUtil.toText(null));
		Account account = new Account(1, "ZSQH", "200500");
		System.out.println(account.toString());
		System.out.println(account.toString().length());

	}

}
