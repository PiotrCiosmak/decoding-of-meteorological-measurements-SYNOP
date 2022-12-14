package pl.ciosmak;

import pl.ciosmak.data.RemoteDataProvider;
import pl.ciosmak.extractor.Extractor;
import pl.ciosmak.sections.LoadDataFromFile;
import pl.ciosmak.sections.Section;
import pl.ciosmak.sections.sectionOne.*;
import pl.ciosmak.sections.sectionZero.Date;
import pl.ciosmak.sections.sectionZero.StationID;
import pl.ciosmak.sections.sectionZero.StationTypeID;
import pl.ciosmak.sections.sectionZero.WindIndex;

import java.util.ArrayList;

import static pl.ciosmak.data.DataProvider.splitData;

public class Main
{
    public static void main(String[] args)
    {
        RemoteDataProvider remoteDataProvider = new RemoteDataProvider();
        remoteDataProvider.initialize();
        ArrayList<String> linesOfData = splitData(remoteDataProvider.getData());
        LoadDataFromFile.load();
        ArrayList<Section> listOfSection = new ArrayList<>();
        for (var line : linesOfData)
        {
            Extractor extractor = new Extractor(line);
            if (!extractor.getStationTypeID().equals("AAXX"))
                continue;
            listOfSection.add(new StationID(extractor.getStationID()));
            listOfSection.add(new Date(extractor.getYear(), extractor.getMonth(), extractor.getDay(), extractor.getHour(), extractor.getMinute()));
            listOfSection.add(new StationTypeID(extractor.getStationTypeID()));
            listOfSection.add(new WindIndex(extractor.getTimeOfObservationAndWindIndex()));
            listOfSection.add(new PrecipitationGroupIndexAndCloudBaseHeightAndHorizontalVisibility(extractor.getPrecipitationGroupIndexAndCloudBaseHeightAndHorizontalVisibility()));
            String stationType = ((PrecipitationGroupIndexAndCloudBaseHeightAndHorizontalVisibility) listOfSection.get(listOfSection.size() - 1)).getStationType();
            listOfSection.add(new AmountOfGeneralCloudCoverAndWindData(extractor.getAmountOfGeneralCloudCoverAndWindData()));
            listOfSection.add(new AirTemperature(extractor.getAirTemperature()));
            listOfSection.add(new DewPointTemperature(extractor.getDewPointTemperature()));
            listOfSection.add(new AtmosphericPressureAtTheStationLevel(extractor.getAtmosphericPressureAtTheStationLevel()));
            listOfSection.add(new AtmosphericPressureAtSeaLevel(extractor.getAtmosphericPressureAtSeaLevel()));
            listOfSection.add(new TendencyOfAtmosphericPressure(extractor.getTendencyOfAtmosphericPressure()));
            listOfSection.add(new Rainfall(extractor.getRainfall()));
            listOfSection.add(new PresentAndPastWeatherCondition(extractor.getPresentAndPastWeatherCondition(), stationType));
            listOfSection.add(new Clouds(extractor.getClouds()));
            listOfSection.add(new CurrentObservationTimeWhenActualObservationTimeDiffersMoreThanTenMinutesFromStandardGGTimeInSectionZero(extractor.getCurrentObservationTimeWhenActualObservationTimeDiffersMoreThanTenMinutesFromStandardGGTimeInSectionZero()));
        }

        for (var element : listOfSection)
        {
            if (element.getClass().getSimpleName().equals("StationID"))
            {
                System.out.println("\n---POMIAR---");
            }
            element.show();
        }
    }
}