package store.model;

import java.lang.String;
import java.util.Date;

// name,buy,get,start_date,end_date

public class Promotion {
    private String name;
    private int buy;
    private int get;
    private Date startDate;
    private Date endDate;

    public Promotion(String name, int buy, int get, Date startDate, Date endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
