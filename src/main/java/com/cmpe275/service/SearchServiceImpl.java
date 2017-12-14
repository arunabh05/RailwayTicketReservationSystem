package com.cmpe275.service;

import com.cmpe275.domain.*;
import com.cmpe275.repository.SearchRepository;
import com.cmpe275.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

/**
 * @author arunabh.shrivastava
 */
@Service
public class SearchServiceImpl implements SearchService {

    private final
    StationRepository stationRepository;
    private final
    SearchRepository searchRepository;
    @Autowired
    public SearchServiceImpl(StationRepository stationRepository, SearchRepository searchRepository) {
        this.stationRepository = stationRepository;
        this.searchRepository = searchRepository;
    }


    public Set<Transaction> getAvailableTrains(int numberOfPassengers, String departureTime, Long fromStationId,
                                               Long toStationId, String ticketType, String connections,
                                               boolean roundTrip) {
        Station fromStation = stationRepository.findOne(fromStationId);
        Station toStation = stationRepository.findOne(toStationId);
        Set<Transaction> transactionSet = filterTrainByConnections(ticketType,connections, fromStation, toStation,
                departureTime, numberOfPassengers);
        return transactionSet;
    }

    private Set<Transaction> filterTrainByConnections(String ticketType, String connections, Station fromStation,
            Station toStation, String departureTime, int numberOfPassengers){
        if(connections.equalsIgnoreCase("none")){
            return getTrainsWithNoStop(fromStation, toStation, ticketType, departureTime, numberOfPassengers);
        }
        else if(connections.equalsIgnoreCase("one")){
            return getTrainsWithOneStop(fromStation, toStation,ticketType, departureTime, numberOfPassengers);
        }else{
            return getAllAvailableTrains(fromStation, toStation, ticketType, departureTime, numberOfPassengers);
        }
    }

    private Set<Transaction> getAllAvailableTrains(Station fromStation, Station toStation, String ticketType,
            String departureTime, int numberOfPassengers){

        Set<Search> allTrainsFromFirstStation = getAllTrainsFromStations(fromStation, departureTime);
        allTrainsFromFirstStation = filterTrainsByTicketType(allTrainsFromFirstStation, ticketType);
        for(Search search: allTrainsFromFirstStation){
            Set<Search> allTrainsFromSecondStation = getAllTrainsFromStations(search.getToStation(), search.getArrivalTime());
            allTrainsFromSecondStation = filterTrainsByTicketType(allTrainsFromSecondStation, ticketType);
            String departureTimeBefore = LocalTime.parse(departureTime).plusMinutes(120).toString();
            for(Search search1: allTrainsFromSecondStation){
                Set<Search> allTrainsToDestination = searchRepository
                        .findAllByFromStationAndToStationAndDepartureTimeAfterAndDepartureTimeBeforeOrderByArrivalTime(search1.getToStation(), toStation, search1.getArrivalTime(), departureTimeBefore);
                allTrainsFromSecondStation = filterTrainsByTicketType(allTrainsFromSecondStation, ticketType);

                System.out.println(fromStation.getName() +" to "+ search.getToStation().getName()
                        +" to "+ search1.getToStation()+" to "+ toStation);
            }

        }
        return null;
    }


    private Set<Transaction> getTrainsWithOneStop(Station fromStation, Station toStation,
            String ticketType, String departureTime, int numberOfPassengers){
        Set<Search> allTrainFromSource = getAllTrainsFromStations(fromStation, departureTime);
        allTrainFromSource = filterTrainsByTicketType(allTrainFromSource, ticketType);

        Map<Search, Set<Search>> connectingTrainsMap = new HashMap<>();
        Set<Search> connectingTrains;
        for(Search search : allTrainFromSource){
            connectingTrains = new HashSet<>();
            String departureTimeBefore = LocalTime.parse(search.getArrivalTime()).plusMinutes(120).toString();
            String secondTrainDepartureTime = LocalTime.parse(search.getArrivalTime()).plusMinutes(5).toString();
            connectingTrains.addAll(searchRepository.findAllByFromStationAndToStationAndDepartureTimeAfterAndDepartureTimeBeforeOrderByArrivalTime
                    (search.getToStation(),toStation, secondTrainDepartureTime , departureTimeBefore));
            connectingTrainsMap.put(search, connectingTrains);
        }
        Map<Integer, List<Search>> connectingTrainTimeMap =  getFastestConnectingTrains(connectingTrainsMap);
        return createTransactionForTop5Trains(connectingTrainTimeMap, numberOfPassengers);
    }


    private Set<Search> getAllTrainsFromStations(Station fromStation, String departureTime){
        String twoHoursFromDepartureTime = LocalTime.parse(departureTime).plusMinutes(120).toString();
        return searchRepository.findAllByFromStationAndDepartureTimeAfterAndDepartureTimeBeforeOrderByArrivalTime
                (fromStation, departureTime, twoHoursFromDepartureTime);
    }

    private Map<Integer, List<Search>> getFastestConnectingTrains(Map<Search, Set<Search>> connectingTrainsMap) {

        Map<Integer, List<Search>> connectingTrainTimeMap = new HashMap<>();
        LocalTime maxArrivalTime = LocalTime.of(23, 0);
        for(Search firstConnectingTrain: connectingTrainsMap.keySet()){
            for(Search secondConnectingTrain : connectingTrainsMap.get(firstConnectingTrain)){
                if (firstConnectingTrain.getToStation().equals(secondConnectingTrain.getFromStation())) {
                    LocalTime arrivalTime = LocalTime.parse(secondConnectingTrain.getArrivalTime());
                    int arrivalTimeInSeconds = arrivalTime.toSecondOfDay();
                    if (arrivalTime.isBefore(maxArrivalTime)) {
                        ArrayList<Search> connectingTrainList = new ArrayList<>();
                        connectingTrainList.add(firstConnectingTrain);
                        connectingTrainList.add(secondConnectingTrain);
                        if(connectingTrainTimeMap.containsKey(arrivalTimeInSeconds)){
                            if(connectingTrainTimeMap.get(arrivalTimeInSeconds).get(1).getFromStation().getId() >
                                    secondConnectingTrain.getFromStation().getId()){
                                connectingTrainTimeMap.put(arrivalTimeInSeconds, connectingTrainList);
                            }
                        }else {
                            connectingTrainTimeMap.put(arrivalTimeInSeconds, connectingTrainList);
                        }
                    }
                }
            }
        }
        return connectingTrainTimeMap;
    }

    private Set<Transaction> getTrainsWithNoStop(Station fromStation, Station toStation, String ticketType,
                                                 String departureTime, int numberOfPassengers){
        Set<Search> searchSet = searchRepository.findTop5ByFromStationAndToStationAndDepartureTimeAfterOrderByArrivalTime
                (fromStation, toStation, departureTime);
        searchSet = filterTrainsByTicketType(searchSet, ticketType);

        Ticket ticket;
        Set<Ticket> ticketSet;
        Set<Transaction> transactionSet = new HashSet<>();
        Transaction transaction;

        for(Search search: searchSet){
            System.out.println(search.getFromStation().getName() +"::"+search.getDepartureTime()+"::"+
                    search.getToStation().getName() + "::" + search.getArrivalTime());
            System.out.println(":::::::::::::::::::");

            ticketSet = new HashSet<>();
            ticket = new Ticket(search);
            ticketSet.add(ticket);
            transaction = new Transaction(ticketSet, search.getPrice(), search.getDuration());
            transactionSet.add(transaction);
        }
        return transactionSet;
    }

    private Set<Transaction> createTransactionForTop5Trains(Map<Integer, List<Search>> connectingTrainTimeMap,
                                                            int numberOfPassengers){
        Set<Integer> arrivalTimeTrainSet = connectingTrainTimeMap.keySet();
        ArrayList<Integer> arrivalTimeTrainList = new ArrayList<>(arrivalTimeTrainSet);
        Collections.sort(arrivalTimeTrainList);

        Ticket firstTrainTicket;
        Ticket secondTrainTicket;
        Transaction transaction;
        Set<Ticket> ticketSet;
        Set<Transaction> transactionSet = new HashSet<>();

        for(int i=0;i<5;i++){
            ticketSet = new HashSet<>();
            Search firstTrain = connectingTrainTimeMap.get(arrivalTimeTrainList.get(i)).get(0);
            Search secondTrain = connectingTrainTimeMap.get(arrivalTimeTrainList.get(i)).get(1);

            System.out.println(firstTrain.getFromStation().getName() +"::"+firstTrain.getDepartureTime()+"::"+
                    firstTrain.getToStation().getName() + "::" + firstTrain.getArrivalTime());
            System.out.println(secondTrain.getFromStation().getName()+"::"+secondTrain.getDepartureTime()+"::"+
                    secondTrain.getToStation().getName() + "::" + secondTrain.getArrivalTime());
            System.out.println(":::::::::::::::::::");

            firstTrainTicket = new Ticket(firstTrain);
            secondTrainTicket = new Ticket(secondTrain);

            ticketSet.add(firstTrainTicket);
            ticketSet.add(secondTrainTicket);

            String totalDuration = LocalTime.parse(secondTrain.getArrivalTime())
                    .minusNanos(LocalTime.parse(firstTrain.getDepartureTime()).toNanoOfDay()).toString();
            long totalPrice = firstTrain.getPrice() + secondTrain.getPrice();
            transaction = new Transaction(ticketSet, totalPrice, totalDuration);
            transactionSet.add(transaction);
        }
        return transactionSet;
    }

    private Set<Search> filterTrainsByTicketType(Set<Search> trains, String ticketType){
        Set<Search> filteredTrains = new HashSet<>();

        if(ticketType.equalsIgnoreCase("any")){
            return trains;
        }
        else if(ticketType.equalsIgnoreCase("express")){
            for(Search search: trains){
                if(!search.getTrain().isExpress()){
                    filteredTrains.add(search);
                }
            }
        }else{
            for(Search search: trains){
                if(!search.getTrain().isExpress()){
                    filteredTrains.add(search);
                }
            }
        }
        trains.removeAll(filteredTrains);
        return trains;
    }
}