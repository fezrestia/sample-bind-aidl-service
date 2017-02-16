package test.aidl.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;

public class AidlServerService extends Service {
    // Log tag.
    public static final String TAG = "AidlServerService";

    private IAidlServerService.Stub mStub = new IAidlServerService.Stub() {
        @Override
        public String getResult(String arg) throws RemoteException {
            android.util.Log.e("TraceLog", TAG + " : Stub.getResult()");
            return "Received: " + arg + " : SysClock=" + SystemClock.uptimeMillis();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        android.util.Log.e("TraceLog", TAG + " : onBind()");
        return mStub;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        android.util.Log.e("TraceLog", TAG + " : onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        android.util.Log.e("TraceLog", TAG + " : onCreate()");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        android.util.Log.e("TraceLog", TAG + " : onDestroy()");
        super.onDestroy();
    }
}
