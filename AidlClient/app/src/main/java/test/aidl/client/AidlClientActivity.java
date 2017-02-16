package test.aidl.client;

import test.aidl.server.IAidlServerService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

public class AidlClientActivity extends Activity {
    // Log tag.
    public static final String TAG = "AidlClientActivity";

    private Button mTrigger = null;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.aidl_client_layout);

        mTrigger = (Button) findViewById(R.id.trigger);
        mTrigger.setOnClickListener(new TriggerOnClickListener());
        mTrigger.setEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Bind.
        Intent service = new Intent("test.aidl.server.ACTION_BIND");
        service.setPackage("test.aidl.server");
        bindService(service, mAidlConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void onPause() {

        // Unbind.
        if (mService != null) {
            unbindService(mAidlConnection);
        }

        super.onPause();
    }


    private IAidlServerService mService = null;
    private AidlConnection mAidlConnection = new AidlConnection();
    private class AidlConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IAidlServerService.Stub.asInterface(service);

            mTrigger.setEnabled(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;

            mTrigger.setEnabled(false);
        }
    }

    private class TriggerOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            android.util.Log.e("TraceLog", TAG + " : onClick() : E");

            String ret = "NOT RECEIVED";

            try {
                ret = mService.getResult("HELLO WORLD !");
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            android.util.Log.e("TraceLog", "    #### Received : " + ret);

            android.util.Log.e("TraceLog", TAG + " : onClick() : X");
        }
    }
}
