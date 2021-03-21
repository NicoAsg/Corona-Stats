package efrei.project.corona_stats;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AsyncCountriesListFilling extends AsyncTask<List<Country>, Void, Void> {
    private AppCompatActivity context;
    public AsyncCountriesListFilling(AppCompatActivity context) {
        this.context = context;
    }
    @Override
    protected Void doInBackground(List<Country>... lists) {
        List<Country> countries = sortCountriesList(lists[0]);
        ListView countriesList = (ListView) context.findViewById(R.id.countries_list);
        ArrayAdapter countriesForView = new ArrayAdapter<String>(countriesList.getContext(), R.layout.element_countries_list);

        for(int i = 0; i < countries.size(); i++) {
            countriesForView.add(countries.get(i).getName());
        }

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                countriesList.setAdapter(countriesForView);
                countriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CountryActivity.setCountry(countries.get(position));
                        context.startActivity(new Intent(context, CountryActivity.class));
                    }
                });
            }
        });

        return null;
    }

    private List<Country> sortCountriesList(List<Country> countries) {
        Country temp;
        for(int i = 0; i < countries.size(); i++)
            for(int j = i + 1; j < countries.size(); j++)
                if(countries.get(j).getName().charAt(0) < countries.get(i).getName().charAt(0)) {
                    temp = countries.get(j);
                    countries.set(j, countries.get(i));
                    countries.set(i, temp);
                }
        return countries;
    }
}
