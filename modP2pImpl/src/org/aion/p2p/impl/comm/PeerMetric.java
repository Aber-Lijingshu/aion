/*
 * Copyright (c) 2017-2018 Aion foundation.
 *
 * This file is part of the aion network project.
 *
 * The aion network project is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * The aion network project is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the aion network project source files.
 * If not, see <https://www.gnu.org/licenses/>.
 *
 * Contributors to the aion source files in decreasing order of code volume:
 *
 * Aion foundation.
 *
 */

package org.aion.p2p.impl.comm;

public final class PeerMetric {

	private static final int STOP_CONN_AFTER_FAILED_CONN = 2;
	private static final long FAILED_CONN_RETRY_INTERVAL = 3000;
	private static final long BAN_CONN_RETRY_INTERVAL = 300_000;

	int metricFailedConn;
	private long metricFailedConnTs;
	private long metricBanConnTs;

	private long metricHS;
	private long metricHSTs;

	public boolean shouldNotConn() {

		boolean failConnBan = (metricFailedConn > STOP_CONN_AFTER_FAILED_CONN
				&& ((System.currentTimeMillis() - metricFailedConnTs) > FAILED_CONN_RETRY_INTERVAL));

		// wait test confirm!
		// boolean hsBan = (metricHSTs > STOP_CONN_AFTER_FAILED_CONN
		// && ((System.currentTimeMillis() - metricHSTs) > FAILED_CONN_RETRY_INTERVAL));

		boolean hsBan = false;

		return (failConnBan || hsBan || ((System.currentTimeMillis() - metricBanConnTs) < BAN_CONN_RETRY_INTERVAL));
	}

	public void incFailedCount() {
		metricFailedConn++;
		metricFailedConnTs = System.currentTimeMillis();
	}

	public void decFailedCount() {
		if (metricFailedConn > 0)
			metricFailedConn--;
	}

	public void incHScnt() {
		metricHS++;
		metricHSTs = System.currentTimeMillis();
	}

	public void decHScnt() {
		if (metricHS > 0)
			metricHS--;
	}

	public void ban() {
		metricBanConnTs = System.currentTimeMillis();
	}

	public boolean notBan() {
		return ((System.currentTimeMillis() - metricBanConnTs) > BAN_CONN_RETRY_INTERVAL);
	}
}
