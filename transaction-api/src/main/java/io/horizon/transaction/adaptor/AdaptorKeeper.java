package io.horizon.transaction.adaptor;

import static io.mercury.common.collections.MutableMaps.newIntObjectHashMap;
import static io.mercury.common.log.CommonLoggerFactory.getLogger;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.slf4j.Logger;

import io.horizon.transaction.account.Account;
import io.horizon.transaction.account.SubAccount;

/**
 * @topic 存储Adaptor和Mapping关系<br>
 * 
 *        1.以[accountId]查找Adaptor<br>
 *        2.以[subAccountId]查找Adaptor<br>
 * 
 * @author yellow013
 *
 * @TODO 修改为线程安全的管理器<br>
 * 
 *       如果程序运行中不修改Adaptor的引用则可以在多个线程中调用Get函数<br>
 *       如果运行中Adaptor崩溃, 重新创建Adaptor则需要重新Put<br>
 *       目前无法保证这一过程的访问安全
 */
@NotThreadSafe
public final class AdaptorKeeper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1199809125474119945L;

	/**
	 * Logger
	 */
	private static final Logger log = getLogger(AdaptorKeeper.class);

	// 存储Adaptor, 使用accountId索引
	private static final MutableIntObjectMap<Adaptor> AccountAdaptorMap = newIntObjectHashMap();

	// 存储Adaptor, 使用subAccountId索引
	private static final MutableIntObjectMap<Adaptor> SubAccountAdaptorMap = newIntObjectHashMap();

	private AdaptorKeeper() {
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
		adaptor.getAccounts().each(account -> {
			AccountAdaptorMap.put(account.getAccountId(), adaptor);
			log.info("Put adaptor to AccountAdaptorMap, accountId==[{}], remark==[{}], adaptorId==[{}]",
					account.getAccountId(), account.getRemark(), adaptor.getAdaptorId());
			account.getSubAccounts().each(subAccount -> {
				SubAccountAdaptorMap.put(subAccount.getSubAccountId(), adaptor);
				log.info(
						"Put adaptor to SubAccountAdaptorMap, subAccountId==[{}], subAccountName==[{}], adaptorId==[{}]",
						subAccount.getSubAccountId(), subAccount.getSubAccountName(), adaptor.getAdaptorId());
			});
		});
	}

	@Override
	public String toString() {
		return "";
	}

}
