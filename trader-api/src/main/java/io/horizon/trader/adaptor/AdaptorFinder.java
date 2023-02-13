package io.horizon.trader.adaptor;

import io.horizon.trader.account.Account;
import io.horizon.trader.account.SubAccount;
import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import java.io.Serial;
import java.io.Serializable;

import static io.mercury.common.collections.MutableMaps.newIntObjectHashMap;
import static io.mercury.common.log.Log4j2LoggerFactory.getLogger;

/**
 * @author yellow013
 * @topic 存储Adaptor和Mapping关系<br>
 * <p>
 * 1.以[accountId]查找Adaptor<br>
 * 2.以[subAccountId]查找Adaptor<br>
 * TODO 修改为线程安全的管理器<br>
 * <p>
 * 如果程序运行中不修改Adaptor的引用则可以在多个线程中调用Get函数<br>
 * 如果运行中Adaptor崩溃, 重新创建Adaptor则需要重新Put<br>
 * 目前不保证这一过程的线程安全
 */
@NotThreadSafe
public final class AdaptorFinder implements Serializable {

    @Serial
    private static final long serialVersionUID = -1199809125474119945L;

    /**
     * Logger
     */
    private static final Logger log = getLogger(AdaptorFinder.class);

    /**
     * 存储Adaptor, 使用accountId索引
     */
    private static final MutableIntObjectMap<Adaptor> AccountAdaptorMap = newIntObjectHashMap();

    /**
     * 存储Adaptor, 使用subAccountId索引
     */
    private static final MutableIntObjectMap<Adaptor> SubAccountAdaptorMap = newIntObjectHashMap();

    private AdaptorFinder() {
    }

    public static Adaptor getAdaptorByAccount(@Nonnull Account account) {
        return AccountAdaptorMap.get(account.getAccountId());
    }

    public static Adaptor getAdaptorByAccountId(int accountId) {
        return AccountAdaptorMap.get(accountId);
    }

    public static Adaptor getAdaptorBySubAccount(@Nonnull SubAccount subAccount) {
        return SubAccountAdaptorMap.get(subAccount.getSubAccountId());
    }

    public static Adaptor getAdaptorBySubAccountId(int subAccountId) {
        return SubAccountAdaptorMap.get(subAccountId);
    }

    public static void putAdaptor(@Nonnull Adaptor adaptor) {
        var account = adaptor.getAccount();
        AccountAdaptorMap.put(account.getAccountId(), adaptor);
        log.info("Put adaptor to AccountAdaptorMap, accountId==[{}], remark==[{}], adaptorId==[{}]",
                account.getAccountId(), account.getRemark(), adaptor.getAdaptorId());
        account.getSubAccounts().each(subAccount -> {
            SubAccountAdaptorMap.put(subAccount.getSubAccountId(), adaptor);
            log.info("Put adaptor to SubAccountAdaptorMap, subAccountId==[{}], subAccountName==[{}], adaptorId==[{}]",
                    subAccount.getSubAccountId(), subAccount.getSubAccountName(), adaptor.getAdaptorId());
        });
    }

    @Override
    public String toString() {
        return "";
    }

}
