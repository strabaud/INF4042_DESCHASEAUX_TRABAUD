package org.esiea.deschaseaux_trabaud.happyapp;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetBeersService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_GET_ALL_BEERS = "org.esiea.deschaseaux_trabaud.happyapp.action.FOO";

    // TODO: Rename parameters


    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionGetAllBeers(Context context) {
        Intent intent = new Intent(context, GetBeersService.class);
        intent.setAction(ACTION_GET_ALL_BEERS);
        context.startService(intent);
    }


    public GetBeersService() {
        super("GetBeersService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ALL_BEERS.equals(action)) {
                handleActionGetAllBeers();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetAllBeers() {
        // TODO: This function gets all beers
        throw new UnsupportedOperationException("Houston we've got a problem...");

        Log.d(TAG, "Thread service name:" + Thread.currentThread().getName());
        URL url = null;
        try {
            url = new URL("http://binouze.fabrigli.fr/bieres.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                copyInputStreamToFile(conn.getInputStream(),
                        new File(getCacheDir(), "bieres.json"));
                Log.d(TAG, "Bieres json Downloaded !");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void copyInputStreamToFile(InputStream in, File file){
        try{
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
