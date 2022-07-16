package io.horizon.trader.account;

import javax.annotation.Nonnull;

import com.typesafe.config.Config;

import io.mercury.common.config.ConfigOption;
import io.mercury.common.config.ConfigWrapper;
import io.mercury.common.fsm.EnableableComponent;
import io.mercury.common.lang.Asserter;
import io.mercury.common.util.StringSupport;

import java.io.Serial;

/**
 * 系統內使用的虚拟账户
 *
 * @author yellow013
 */
public final class SubAccount extends EnableableComponent implements Comparable<SubAccount> {

    public static final int MaxSubAccountId = Integer.MAX_VALUE >> 1;

    /**
     * 处理外部订单使用的子账户
     */
    public static final SubAccount ExternalOrderSubAccount = new SubAccount();

    /*
     * 子账户ID
     */
    private final int subAccountId;

    /*
     * 子账户名称
     */
    private final String subAccountName;

    /*
     * 所属账户
     */
    private final Account account;

    /**
     * 账户余额
     */

    private long balance;

    /**
     * 信用额度
     */
    private long credit;

    // inner use
    private SubAccount() {
        this.subAccountId = 0;
        this.subAccountName = "External_Order_SubAccount";
        this.account = null;
    }

    public SubAccount(@Nonnull Config config, @Nonnull Account account) {
        this(new ConfigWrapper<>(config), account);
    }

    private SubAccount(@Nonnull ConfigWrapper<SubAccountConfig> wrapper, @Nonnull Account account) {
        this(wrapper.getIntOrThrows(SubAccountConfig.SubAccountId),
                wrapper.getString(SubAccountConfig.SubAccountName, ""),
                wrapper.getLong(SubAccountConfig.SubBalance, 0L), wrapper.getLong(SubAccountConfig.SubCredit, 0L),
                account);
    }

    public SubAccount(int subAccountId, @Nonnull Account account) {
        this(subAccountId, "", account);
    }

    public SubAccount(int subAccountId, String subAccountName, @Nonnull Account account) {
        this(subAccountId, subAccountName, account.getBalance(), account.getCredit(), account);
    }

    public SubAccount(int subAccountId, long balance, long credit, @Nonnull Account account) {
        this(subAccountId, "", account.getBalance(), account.getCredit(), account);
    }

    public SubAccount(int subAccountId, String subAccountName, long balance, long credit, @Nonnull Account account) {
        Asserter.atWithinRange(subAccountId, 1, MaxSubAccountId, "subAccountId");
        Asserter.nonNull(account, "account");
        this.subAccountId = subAccountId;
        this.account = account;
        this.balance = balance;
        this.credit = credit;
        if (StringSupport.nonEmpty(subAccountName))
            this.subAccountName = subAccountName;
        else
            this.subAccountName = "SubAccount[" + subAccountId + "]=>Account[" + account.getBrokerName() + ":"
                    + account.getRemark() + "]";
        account.addSubAccount(this);
        AccountFinder.putSubAccount(this);
        enable();
    }

    public int getSubAccountId() {
        return subAccountId;
    }

    public String getSubAccountName() {
        return subAccountName;
    }

    public Account getAccount() {
        return account;
    }

    public long getBalance() {
        return balance;
    }

    public long getCredit() {
        return credit;
    }

    public static class SubAccountException extends RuntimeException {

        /**
         *
         */
        @Serial
        private static final long serialVersionUID = -8903289183998546839L;

        public SubAccountException(String message) {
            super(message);
        }

    }

    private static final String SubAccountIdField = "{\"subAccountId\" : ";
    private static final String SubAccountNameField = ", \"subAccountName\" : ";
    private static final String AccountField = ", \"account\" : ";
    private static final String BalanceField = ", \"balance\" : ";
    private static final String CreditField = ", \"credit\" : ";
    private static final String IsEnabledField = ", \"isEnabled\" : ";
    private static final String End = "}";

    @Override
    public String toString() {
        return SubAccountIdField +
                subAccountId +
                SubAccountNameField +
                subAccountName +
                AccountField +
                account +
                BalanceField +
                balance +
                CreditField +
                credit +
                IsEnabledField +
                isEnabled() +
                End;
    }

    @Override
    public int compareTo(SubAccount o) {
        return Integer.compare(this.subAccountId, o.subAccountId);
    }

    public enum SubAccountConfig implements ConfigOption {

        SubAccountId("sys.subAccountId"),

        SubAccountName("sys.subAccountName"),

        SubBalance("sys.subBalance"),

        SubCredit("sys.subCredit");

        private final String configName;

        SubAccountConfig(String configName) {
            this.configName = configName;
        }

        @Override
        public String getConfigName() {
            return configName;
        }
    }

    public static void main(String[] args) {
        SubAccount subAccount = new SubAccount(10, new Account(1, "HYQH", "HYQH", "200500", 100000, 0));
        System.out.println(subAccount);
        System.out.println(subAccount.toString().length());
    }

}
