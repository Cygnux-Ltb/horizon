package io.horizon.structure.account;

import static io.mercury.common.util.StringUtil.toText;

import javax.annotation.Nonnull;

import io.mercury.common.fsm.EnableComponent;
import io.mercury.common.util.Assertor;
import lombok.Getter;

/**
 * 虚拟账户
 * 
 * @author yellow013
 *
 */
public final class SubAccount extends EnableComponent<SubAccount> implements Comparable<SubAccount> {

	public static final int MaxSubAccountId = Integer.MAX_VALUE >> 1;

	/**
	 * 处理
	 */
	public static final SubAccount ProcessExternalOrderSubAccount = new SubAccount();

	// 子账户ID
	@Getter
	private final int subAccountId;

	// 子账户名称
	@Getter
	private final String subAccountName;

	// 所属账户
	@Getter
	private final Account account;

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

	private SubAccount() {
		this.subAccountId = 910;
		this.subAccountName = "PROCESS_EXTERNAL_ORDER_SUBACCOUNT";
		this.account = null;
	}

	public SubAccount(int subAccountId, @Nonnull Account account) {
		this(subAccountId, account, account.getBalance(), account.getCredit());
	}

	public SubAccount(int subAccountId, @Nonnull Account account, int balance, int credit) {
		Assertor.lessThan(subAccountId, MaxSubAccountId, "subAccountId");
		Assertor.nonNull(account, "account");
		this.subAccountId = subAccountId;
		this.account = account;
		this.balance = balance;
		this.credit = credit;
		this.subAccountName = "SubAccount[" + subAccountId + "]-Account[" + account.getBrokerName() + ":"
				+ account.getRemark() + "]";
		account.addSubAccount(this);
	}

	@Override
	protected SubAccount returnThis() {
		return this;
	}

	public static class SubAccountException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8903289183998546839L;

		public SubAccountException(String message) {
			super(message);
		}

	}

	private static final String str0 = "{\"subAccountId\" : ";
	private static final String str1 = ", \"subAccountName\" : ";
	private static final String str2 = ", \"account\" : ";
	private static final String str3 = ", \"balance\" : ";
	private static final String str4 = ", \"credit\" : ";
	private static final String str5 = ", \"isEnabled\" : ";
	private static final String str6 = "}";

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(350);
		builder.append(str0);
		builder.append(subAccountId);
		builder.append(str1);
		builder.append(toText(subAccountName));
		builder.append(str2);
		builder.append(account);
		builder.append(str3);
		builder.append(balance);
		builder.append(str4);
		builder.append(credit);
		builder.append(str5);
		builder.append(isEnabled());
		builder.append(str6);
		return builder.toString();
	}

	@Override
	public int compareTo(SubAccount o) {
		return this.subAccountId < o.subAccountId ? -1 : this.subAccountId > o.subAccountId ? 1 : 0;
	}

	public static void main(String[] args) {
		SubAccount subAccount = new SubAccount(10, new Account(1, "HYQH", "200500", 100000, 0));
		System.out.println(subAccount);
		System.out.println(subAccount.toString().length());
	}

}
