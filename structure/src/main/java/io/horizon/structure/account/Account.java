package io.horizon.structure.account;

import static io.mercury.common.util.StringUtil.toText;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.set.MutableSet;

import io.mercury.common.collections.MutableSets;
import io.mercury.common.fsm.EnableComponent;
import io.mercury.common.util.Assertor;
import io.mercury.common.util.StringUtil;
import lombok.Getter;

public final class Account extends EnableComponent<Account> implements Comparable<Account> {

	/**
	 * 账户ID
	 */
	@Getter
	private final int accountId;

	/**
	 * 经纪商名称
	 */
	@Getter
	private final String brokerName;

	/**
	 * 经纪商提供的投资者ID
	 */
	@Getter
	private final String investorId;

	/**
	 * 账户余额
	 */
	@Getter
	private int balance;

	/**
	 * 信用额度
	 */
	@Getter
	private int credit;

	/**
	 * 备注
	 */
	@Getter
	private String remark = "NONE";

	// 备用, 数组下标, 用于快速访问本账户对应的仓位信息集合
	// private int positionManagerIndex;

	/**
	 * 子账户集合
	 */
	@Getter
	private final MutableSet<SubAccount> subAccounts = MutableSets.newUnifiedSet();

	public Account(int accountId, @Nonnull String brokerName, @Nonnull String investorId) {
		this(accountId, brokerName, investorId, 0, 0);
	}

	public Account(int accountId, @Nonnull String brokerName, @Nonnull String investorId, int balance, int credit) {
		Assertor.nonEmpty(brokerName, "brokerName");
		Assertor.nonEmpty(investorId, "investorId");
		this.accountId = accountId;
		this.brokerName = brokerName;
		this.investorId = investorId;
		this.balance = balance;
		this.credit = credit;
	}

	@Override
	protected Account returnThis() {
		return this;
	}

	/**
	 * 仅提供给SubAccount调用
	 * 
	 * @param subAccount
	 */
	Account addSubAccount(SubAccount subAccount) {
		subAccounts.add(subAccount);
		return this;
	}

	/**
	 * 设置余额
	 * 
	 * @param balance
	 * @return
	 */
	public Account setBalance(int balance) {
		this.balance = balance;
		return this;
	}

	/**
	 * 设置信用
	 * 
	 * @param credit
	 * @return
	 */
	public Account setCredit(int credit) {
		this.credit = credit;
		return this;
	}

	/**
	 * 备注
	 * 
	 * @param remark
	 * @return
	 */
	public Account setRemark(String remark) {
		this.remark = remark;
		return this;
	}

	/**
	 * 获取子账户数量
	 * 
	 * @return
	 */
	public int subAccountTotal() {
		return subAccounts.size();
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

	private static final String str0 = "{\"accountId\" : ";
	private static final String str1 = ", \"brokerName\" : ";
	private static final String str2 = ", \"investorId\" : ";
	private static final String str3 = ", \"balance\" : ";
	private static final String str4 = ", \"credit\" : ";
	private static final String str5 = ", \"remark\" : ";
	private static final String str6 = ", \"subAccountTotal\" : ";
	private static final String str7 = ", \"isEnabled\" : ";
	private static final String str8 = "}";

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(200);
		builder.append(str0);
		builder.append(accountId);
		builder.append(str1);
		builder.append(toText(brokerName));
		builder.append(str2);
		builder.append(investorId);
		builder.append(str3);
		builder.append(balance);
		builder.append(str4);
		builder.append(credit);
		builder.append(str5);
		builder.append(toText(remark));
		builder.append(str6);
		builder.append(subAccountTotal());
		builder.append(str7);
		builder.append(isEnabled());
		builder.append(str8);
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
