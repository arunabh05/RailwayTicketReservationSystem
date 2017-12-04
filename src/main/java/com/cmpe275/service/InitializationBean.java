package com.cmpe275.service;

import com.cmpe275.domain.Station;
import com.cmpe275.domain.Train;
import com.cmpe275.repository.StationRepository;
import com.cmpe275.repository.TrainRepository;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static com.cmpe275.constant.Constants.*;
/**
 * @author arunabh.shrivastava
 */

@Service
public class InitializationBean {

    private final
    TrainRepository trainRepository;
    private final
    StationRepository stationRepository;

    @Autowired
    public InitializationBean(TrainRepository trainRepository, StationRepository stationRepository){
        this.trainRepository = trainRepository;
        this.stationRepository = stationRepository;

        createStations();
        createTrains();
    }

    public void createTrains(){
        LocalTime startTime = LocalTime.of(06,00);

        while(startTime.isBefore(LocalTime.of(21,15))){

            String TRAIN_NUMBER = startTime.toString().replace(":","");

            String northBoundName = NORTHBOUND_NAME_PREFIX + TRAIN_NUMBER;
            String southBoundName = SOUTHBOUND_NAME_PREFIX + TRAIN_NUMBER;

            boolean isExpress = false;
            long priceRate = 1;
            LocalTime departureTime = startTime;
            if(startTime.getMinute() == 00){
                isExpress = true;
                priceRate = 2;
            }

            Train northboundTrain = new Train(northBoundName, DEFAULT_TRAIN_CAPACITY ,isExpress, departureTime , priceRate);
            Train southboundTrain = new Train(southBoundName, DEFAULT_TRAIN_CAPACITY ,isExpress, departureTime,  priceRate);

            System.out.println(northBoundName + " -- " + isExpress + " -- " + startTime + " -- ");
            System.out.println(southBoundName + " -- " + isExpress + " -- " + startTime + " -- ");

            trainRepository.save(northboundTrain);
            trainRepository.save(southboundTrain);

            startTime = startTime.plusMinutes(15);

        }
    }

    Set<Station> stationSet = new HashSet<Station>();

    public void createStations(){
        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            Station station = new Station(String.valueOf(alphabet));
            stationRepository.save(station);
            stationSet.add(station);
        }
    }


/*
    public Long calculatePrice(Station fromStation, Station toStation){

        Long noOfStations = Math.abs(fromStation.getId() - toStation.getId());
        Long price = (long) Math.ceil(noOfStations/5.0);

        System.out.println("NORMAL PRICE");
        System.out.println("----------------------------------------------------------------");
        System.out.println("From:"+fromStation.getName()+ " To:"+toStation.getName());
        System.out.println("Number of stations travelled:" + noOfStations + " for " + price);
        System.out.println("----------------------------------------------------------------");

        return price;
    }
*/

/*
    public Long calculateExpressPrice(Station fromStation, Station toStation){


        if(isExpressTrainAvailable(fromStation,toStation)){
            Long noOfStations = Math.abs(fromStation.getId() - toStation.getId());
            Long price = (long) Math.round(noOfStations/5.0);

            System.out.println("EXPRESS PRICE");
            System.out.println("----------------------------------------------------------------");
            System.out.println("From:"+fromStation.getName()+ " To:"+toStation.getName());
            System.out.println("Number of stations travelled:" + noOfStations + " for " + price);
            System.out.println("----------------------------------------------------------------");

            return price*2;
        }
        return new Long(0);
    }
*/

/*
    public String getDuration(Station fromStation, Station toStation){
        Long noOfStations = Math.abs(fromStation.getId() - toStation.getId());
        Long duration = (noOfStations * TRAVEL_TIME) - (noOfStations * STOP_DURATION) - STOP_DURATION;
        String formattedDuration = DurationFormatUtils.formatDurationHMS(duration);
        return formattedDuration;
    }
*/

/*
    public String getExpressDuration(Station fromStation, Station toStation){

        if(isExpressTrainAvailable(fromStation,toStation)) {
            Long noOfStations = Math.abs(fromStation.getId() - toStation.getId());
            Long duration = noOfStations * TRAVEL_TIME;
            duration = duration * (noOfStations / 5);
            String formattedDuration = DurationFormatUtils.formatDurationHMS(duration);

            return formattedDuration;
        }
        return "00:00";
    }
*/


/*
    public boolean isExpressTrainAvailable(Station fromStation, Station toStation){
        return (fromStation.getId() == 1 || fromStation.getId() == 6 ||
                fromStation.getId() == 11 || fromStation.getId() == 16 ||
                fromStation.getId() == 21 || fromStation.getId() == 26) &&
                (toStation.getId() == 1 || toStation.getId() == 6 ||
                toStation.getId() == 11 || toStation.getId() == 16 ||
                toStation.getId() == 21 || toStation.getId() == 26);
    }
*/
}
