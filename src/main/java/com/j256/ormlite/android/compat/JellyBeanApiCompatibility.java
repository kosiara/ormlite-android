package com.j256.ormlite.android.compat;

import android.database.SQLException;
import android.os.CancellationSignal;
import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

/**
 * Basic class which provides no-op methods for all Android version.
 * 
 * <p>
 * <b>NOTE:</b> Will show as in error if compiled with previous Android versions.
 * </p>
 * 
 * @author graywatson
 */
public class JellyBeanApiCompatibility extends BasicApiCompatibility {

	@Override
	public Cursor rawQuery(SQLiteDatabase db, String sql, String[] selectionArgs, CancellationHook cancellationHook) {
		if (cancellationHook == null) {
			return db.rawQuery(sql, selectionArgs);
		} else {
			throw new SQLException("CancellationHook is currently not supported");
		}
	}

	@Override
	public CancellationHook createCancellationHook() {
		return new JellyBeanCancellationHook();
	}

	protected static class JellyBeanCancellationHook implements CancellationHook {

		private final CancellationSignal cancellationSignal;

		public JellyBeanCancellationHook() {
			this.cancellationSignal = new CancellationSignal();
		}

		public void cancel() {
			cancellationSignal.cancel();
		}
	}
}
