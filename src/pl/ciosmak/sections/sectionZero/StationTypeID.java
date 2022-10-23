package pl.ciosmak.sections.sectionZero;

import pl.ciosmak.sections.Section;

public class StationTypeID extends Section
{
    public StationTypeID(String parameters)
    {
        stationTypeID = parameters;
    }

    @Override
    public void show()
    {
        System.out.println("Typ stacji: " + stationTypeID);
    }

    private String stationTypeID;
}
