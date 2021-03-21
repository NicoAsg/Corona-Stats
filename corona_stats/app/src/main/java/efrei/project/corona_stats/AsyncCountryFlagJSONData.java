package efrei.project.corona_stats;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncCountryFlagJSONData extends AsyncTask<Country, Void, Void> {
    private Context context;
    public AsyncCountryFlagJSONData(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Country... countries) {
        URL url;
        try {
            url = new URL("https://flagcdn.com/w80/" + countries[0].getAbbreviation().toLowerCase() + ".webp");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream is = new BufferedInputStream(connection.getInputStream());
            countries[0].setFlag(BitmapFactory.decodeStream(is));

            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
