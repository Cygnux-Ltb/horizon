package io.horizon.market.indicator;

import io.mercury.common.collections.Capacity;
import io.mercury.common.collections.MutableLists;
import io.mercury.common.collections.MutableMaps;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;

import javax.annotation.CheckForNull;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.Optional;

@NotThreadSafe
public final class PointSet<P extends Point> {

    private final MutableList<P> list;

    private final MutableLongObjectMap<P> map;

    private PointSet(Capacity capacity) {
        this.list = MutableLists.newFastList(capacity.value());
        this.map = MutableMaps.newLongObjectHashMap(capacity.value());
    }

    /**
     * @return PointSet<P>
     */
    public static <P extends Point> PointSet<P> newEmpty() {
        return new PointSet<>(Capacity.L07_SIZE);
    }

    /**
     * @param capacity Capacity
     * @return PointSet<P>
     */
    public static <P extends Point> PointSet<P> newEmpty(Capacity capacity) {
        return new PointSet<>(capacity);
    }

    /**
     * @param point P
     * @return boolean
     */
    public boolean add(P point) {
        long serialId = point.getSerialId();
        if (map.containsKey(serialId))
            return false;
        map.put(serialId, point);
        return list.add(point);
    }

    /**
     * @return int
     */
    public int size() {
        return list.size();
    }

    /**
     * @return P
     */
    public P getLast() {
        return list.getLast();
    }

    /**
     * @return P
     */
    public P getFirst() {
        return list.getFirst();
    }

    /**
     * @param index int
     * @return Optional<P>
     */
    public Optional<P> get(int index) {
        return index < list.size() ? Optional.ofNullable(list.get(index)) : Optional.empty();
    }

    /**
     * TODO 需要修改
     *
     * @param point P
     * @return Optional<P>
     */
    public Optional<P> nextOf(P point) {
        int index = point.getIndex();
        return get(++index);
    }

    /**
     * @param serialId long
     * @return P
     */
    @CheckForNull
    public P getPoint(long serialId) {
        return map.get(serialId);
    }

    /**
     * @return MutableList<P>
     */
    public MutableList<P> getPointList() {
        return list;
    }

    /**
     * @param fromIndex int
     * @param toIndex   int
     * @return MutableList<P>
     */
    public MutableList<P> getSubPointList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

}
