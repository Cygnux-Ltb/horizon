package io.horizon.trader.order.attr;

import io.mercury.common.datetime.EpochTime;

/**
 * 时间单位为Epoch微秒
 *
 * @author yellow013
 */
public final class OrdTimestamp {

    private final long generateTime;

    private long sendTime;

    private long firstReportTime;

    private long finishTime;

    private OrdTimestamp() {
        this.generateTime = EpochTime.getEpochMicros();
    }

    /**
     * 初始化订单生成时间
     */
    public static OrdTimestamp now() {
        return new OrdTimestamp();
    }

    public long getGenerateTime() {
        return generateTime;
    }

    public long getSendTime() {
        return sendTime;
    }

    public long getFirstReportTime() {
        return firstReportTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    /**
     * 添加发送时间
     *
     * @return OrdTimestamp
     */
    public OrdTimestamp updateSendTime() {
        this.sendTime = EpochTime.getEpochMicros();
        return this;
    }

    /**
     * 添加首次收到订单回报的时间
     *
     * @return OrdTimestamp
     */
    public OrdTimestamp updateFirstReportTime() {
        this.firstReportTime = EpochTime.getEpochMicros();
        return this;
    }

    /**
     * 添加最终完成时间
     *
     * @return OrdTimestamp
     */
    public OrdTimestamp updateFinishTime() {
        this.finishTime = EpochTime.getEpochMicros();
        return this;
    }

}
