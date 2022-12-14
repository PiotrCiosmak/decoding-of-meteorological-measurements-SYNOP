package pl.ciosmak.sections.sectionZero;

import pl.ciosmak.sections.Section;

public class StationID extends Section
{
    public StationID(final String parameters)
    {
        stationID = parameters;
    }

    @Override
    public void show()
    {
        System.out.println("Identyfikator stacji: " + stationID);
    }

    private final String stationID;
}