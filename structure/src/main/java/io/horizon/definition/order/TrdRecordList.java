package io.horizon.definition.order;

import java.util.Optional;

import org.eclipse.collections.api.list.MutableList;

import io.mercury.common.collections.MutableLists;

public final class TrdRecordList {

	private long ordId;

	private MutableList<TrdRecord> allRecord = MutableLists.newFastList(8);

	public TrdRecordList(long ordId) {
		this.ordId = ordId;
	}

	public long ordId() {
		return ordId;
	}

	public MutableList<TrdRecord> allRecord() {
		return allRecord;
	}

	public boolean isEmpty() {
		return allRecord.isEmpty();
	}

	public Optional<TrdRecord> first() {
		return allRecord.getFirstOptional();
	}

	public Optional<TrdRecord> last() {
		return allRecord.getLastOptional();
	}

	private int serial = -1;

	public void add(long epochTime, long trdPrice, int trdQty) {
		allRecord.add(new TrdRecord(++serial, ordId, epochTime, trdPrice, trdQty));
	}

}
