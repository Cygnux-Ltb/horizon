package io.horizon.market.instrument.attr;

import io.mercury.common.datetime.TimeZone;
import io.mercury.common.sequence.Serial;
import io.mercury.common.sequence.TimeWindow;
import io.mercury.serialization.json.JsonWrapper;
import org.eclipse.collections.api.list.ImmutableList;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

import static io.mercury.common.lang.Asserter.nonNull;

/**
 * 指示某交易标的一段交易时间
 *
 * @author yellow013
 */
public final class TradablePeriod implements Serial<TradablePeriod> {

    private final int serialId;

    private final LocalTime start;

    private final LocalTime end;

    private final Duration duration;

    public TradablePeriod(int serialId, LocalTime start, LocalTime end) {
        nonNull(start, "start");
        nonNull(end, "end");
        this.serialId = serialId;
        this.start = start;
        this.end = end;
        Duration between = Duration.between(start, end);
        if (between.getSeconds() > 0)
            this.duration = between;
        else
            this.duration = between.plusDays(1);
    }

    @Override
    public long serialId() {
        return serialId;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public Duration getDuration() {
        return duration;
    }

    /**
     * 分割交易时段
     *
     * @param date     LocalDate
     * @param offset   ZoneOffset
     * @param duration Duration
     * @return ImmutableList<TimeWindow>
     */
    public ImmutableList<TimeWindow> segmentation(@Nonnull LocalDate date, @Nonnull ZoneOffset offset,
                                                  @Nonnull Duration duration) {
        return TimeWindow.segmentationWindow(date, start, end, offset, duration);
    }


    @Override
    public String toString() {
        return JsonWrapper.toJson(this);
    }

    public static void main(String[] args) {

        TradablePeriod tradingPeriod = new TradablePeriod(0, LocalTime.of(21, 0, 0), LocalTime.of(2, 30, 0));

        tradingPeriod.segmentation(LocalDate.now(), TimeZone.CST, Duration.ofMinutes(45))
                .each(timePeriod -> System.out.println(timePeriod.getStart() + " - " + timePeriod.getEnd()));

        LocalDateTime of = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 55, 30));

        System.out.println(of);
        System.out.println(of.plusMinutes(30));

        System.out.println(Duration.between(LocalTime.of(23, 0), LocalTime.of(23, 0)).toHours());

        System.out.println(LocalTime.of(23, 0).plusHours(3));
    }

}
