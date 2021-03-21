package efrei.project.corona_stats;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class CountryActivity extends AppCompatActivity {
    private static Country country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        TextView countryName = findViewById(R.id.country_name);
        TextView confirmedCases = findViewById(R.id.confirmed_cases);
        TextView recoveredPeople = findViewById(R.id.recovered_people);
        TextView deadPeople = findViewById(R.id.dead_people);

        countryName.setText(country.getName());
        confirmedCases.setText("Covid-19 confirmed cases: " + country.getConfirmed());
        recoveredPeople.setText("People recovered from Covid-19: " + country.getRecovered());
        deadPeople.setText("Covid-19 deaths: " + country.getDeaths());

        if (country.getFlag() != null) {
            ImageView countryFlag = findViewById(R.id.country_flag);
            countryFlag.setImageBitmap(country.getFlag());
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }

    public static void setCountry(Country country) {
        CountryActivity.country = country;
    }
}
