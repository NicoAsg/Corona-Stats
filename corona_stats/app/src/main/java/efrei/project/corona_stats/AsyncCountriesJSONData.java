package efrei.project.corona_stats;

import android.os.AsyncTask;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class AsyncCountriesJSONData extends AsyncTask<String, Void, JSONObject> {
    private AppCompatActivity context;
    public AsyncCountriesJSONData(AppCompatActivity mainAct) {
        this.context = mainAct;
    }

    @Override
    protected JSONObject doInBackground(String... _url) {
        URL url;
        try {
            url = new URL(_url[0]);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            try {
                InputStream is = new BufferedInputStream(connection.getInputStream());
                String response = readStream(is);
                return new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        Iterator keys = result.keys();
        String countryName, countryAbbreviation;
        String countryCasesConfirmed, countryPeopleRecovered, countryPeopleDead;
        List<Country> countries = new ArrayList<Country>();

        while(keys.hasNext()) {
            try {
                countryName = keys.next().toString();
                countryCasesConfirmed = result.getJSONObject(countryName).getJSONObject("All").getString("confirmed");
                countryPeopleRecovered = result.getJSONObject(countryName).getJSONObject("All").getString("recovered");
                countryPeopleDead = result.getJSONObject(countryName).getJSONObject("All").getString("deaths");

                try {
                    countryAbbreviation = result.getJSONObject(countryName).getJSONObject("All").getString("abbreviation");
                    countries.add(new Country(countryName, countryAbbreviation, countryCasesConfirmed, countryPeopleRecovered, countryPeopleDead));
                    new AsyncCountryFlagJSONData(context).execute(countries.get(countries.size() - 1));
                }
                catch (JSONException e) {
                    countries.add(new Country(countryName, "no abbreviation", countryCasesConfirmed, countryPeopleRecovered, countryPeopleDead));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        setGeneralStats(countries.get(countries.size() - 1));
        countries.remove(countries.size() - 1);
        new AsyncCountriesListFilling(context).execute(countries);
    }

    private void setGeneralStats(Country general) {
        TextView confirmed = context.findViewById(R.id.total_cases_text);
        TextView recovered = context.findViewById(R.id.total_recovered_text);
        TextView deaths = context.findViewById(R.id.total_deaths_text);

        confirmed.setText(general.getConfirmed());
        recovered.setText(general.getRecovered());
        deaths.setText(general.getDeaths());
    }

    private static String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = buffer.readLine(); line != null; line = buffer.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}
