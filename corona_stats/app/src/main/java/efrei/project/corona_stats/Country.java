package efrei.project.corona_stats;

import android.graphics.Bitmap;

public class Country {
    // Country name and abbreviation
    private String name, abbreviation;
    // Country flag image
    private Bitmap flag = null;
    // Covid-19 numbers
    private String confirmed, recovered, deaths;

    public Country(String name, String abbreviation, String confirmed, String recovered, String deaths) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.confirmed = confirmed;
        this.recovered = recovered;
        this.deaths = deaths;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public Bitmap getFlag() {
        return flag;
    }
    public void setFlag(Bitmap flag) {
        this.flag = flag;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getDeaths() {
        return deaths;
    }
}
