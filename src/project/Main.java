package project;

import project.carriages.*;
import project.exceptions.TooHeavyLoadException;
import project.exceptions.TooManyCarriagesException;
import project.exceptions.TooManyElectricCarriagesException;
import project.exceptions.TooManyPassengersException;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Station> stations = new ArrayList<>();
        TreeMap<Station, HashMap<Station, Integer>> stationsMap = new TreeMap<>();

        for (int i = 0; i < 100; i++){
            String name = "Station " + i;
            Station station = new Station(name);
            stations.add(station);
            stationsMap.put(station, new HashMap<>());
        }

        for (int i = 0; i < 100; i++){
            HashMap<Station, Integer> directConnection = stationsMap.get(stations.get(i));
            int connectionsCount = (int)(Math.random()*2 + 1);

            for (int j = 0; j < connectionsCount; j++){
                int index = (int)(Math.random()*100);
                while (index == i || stations.get(index) == stations.get(i)) {
                    index = (int)(Math.random()*100);
                }
                int distance = (int)(Math.random() * 60 + 1);
                if (!directConnection.containsKey(stations.get(index))) {
                    directConnection.put(stations.get(index), distance);
                }
            }
            for (Station station : directConnection.keySet()){
                HashMap<Station, Integer> spr = stationsMap.get(station);
                if (station != stations.get(i)) {
                    if (!spr.containsKey(stations.get(i))) {
                        int distance = directConnection.get(station);
                        spr.put(stations.get(i), distance);
                    }
                }
            }
        }

        ArrayList<Train> trains = new ArrayList<>();
        ArrayList<TrainThread> trainThreads = new ArrayList<>();
        
        for (int i = 0; i < 25; i++) {
            String name = "Locomotive " + i;
            int maxCarriageCount = (int) (Math.random() * 15 + 10);
            double maxTowing = Math.random() * 1000000;
            int maxPDSE = (int) (Math.random() * 10 + 6);
            double speed = Math.random() * 120;

            Locomotive locomotive = new Locomotive(name, maxCarriageCount, maxTowing, maxPDSE, speed);
            locomotive.setHomeStation(stations.get(i));
            Train train = new Train(locomotive, stationsMap);
            locomotive.setTrain(train);

            int carriageCount = (int) (Math.random() * 6 + 5);
            for (int j = 0; j < carriageCount; j++) {
                int typ = (int) (Math.random() * 12);
                switch (typ) {
                    case 0 -> {
                        PassengerCarriage passengerCarriage = new PassengerCarriage((int) (Math.random() * 100), "fire protection", Math.random() * 1000);
                        passengerCarriage.connect();
                        try {
                            passengerCarriage.load((int) (Math.random() * 50), Math.random() * 100);
                        } catch (TooManyPassengersException e) {
                            e.printStackTrace();
                        }
                        try {
                            train.addCarriage(passengerCarriage);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 1 -> {
                        MailCarriage mailCarriage = new MailCarriage("Mail", "fire protection", Math.random() * 100);
                        mailCarriage.connect();
                        mailCarriage.load("letters", Math.random() * 1000);
                        try {
                            train.addCarriage(mailCarriage);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 2 -> {
                        RestaurantCarriage restaurantCarriage = new RestaurantCarriage("PGHM", "internal", Math.random() * 100);
                        restaurantCarriage.connect();
                        restaurantCarriage.load("groceries", Math.random() * 1000);
                        try {
                            train.addCarriage(restaurantCarriage);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 3 -> {
                        HeavyGoodsWagon heavyGoodsWagon = new HeavyGoodsWagon("PSH", "tapes", Math.random() * 100);
                        heavyGoodsWagon.load("coal", Math.random() * 1000);
                        try {
                            train.addCarriage(heavyGoodsWagon);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 4 -> {
                        BasicGoodsWagon basicGoodsWagon = new BasicGoodsWagon("TMI", "partition walls", Math.random() * 100);
                        basicGoodsWagon.load("gravel", Math.random() * 1000);
                        try {
                            train.addCarriage(basicGoodsWagon);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 5 -> {
                        ExplosivesWagon explosivesWagon = new ExplosivesWagon("TNT", "fire protection", Math.random() * 100);
                        explosivesWagon.load("dynamite", Math.random() * 1000);
                        try {
                            train.addCarriage(explosivesWagon);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 6 -> {
                        HazardMaterialsWagon hazardMaterialsWagon = new HazardMaterialsWagon("TOX", "ADR", Math.random() * 100);
                        hazardMaterialsWagon.load("ammonia", Math.random() * 1000);
                        try {
                            train.addCarriage(hazardMaterialsWagon);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 7 -> {
                        GasesTankWagon gasesTankWagon = new GasesTankWagon("GAZ", "fire protection", Math.random() * 100);
                        gasesTankWagon.load("azote", Math.random() * 1000);
                        try {
                            train.addCarriage(gasesTankWagon);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 8 -> {
                        RefrigeratedWagon refrigeratedWagon = new RefrigeratedWagon("COLD", "tapes", Math.random() * 100);
                        refrigeratedWagon.connect();
                        refrigeratedWagon.load("frozen food", Math.random() * 1000);
                        try {
                            train.addCarriage(refrigeratedWagon);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 9 -> {
                        BaggagePostalCarriage baggagePostalCarriage = new BaggagePostalCarriage("BAG", "DRT", Math.random() * 100);
                        baggagePostalCarriage.connect();
                        baggagePostalCarriage.load("packages", Math.random() * 1000);
                        try {
                            train.addCarriage(baggagePostalCarriage);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 10 -> {
                        ToxicLiquidsTankWagon toxicLiquidsTankWagon = new ToxicLiquidsTankWagon("CMT", "fire protection", Math.random() * 100);
                        toxicLiquidsTankWagon.load("gas", Math.random() * 1000);
                        try {
                            train.addCarriage(toxicLiquidsTankWagon);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 11 -> {
                        LiquidsTankWagon liquidsTankWagon = new LiquidsTankWagon("CI", "sealed", Math.random() * 100);
                        liquidsTankWagon.load("water", Math.random() * 1000);
                        try {
                            train.addCarriage(liquidsTankWagon);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            int startingStation = (int)(Math.random()*100);
            int destination = (int)(Math.random()*100);
            while (startingStation == destination){
                startingStation = (int)(Math.random()*100);
            }
            Route route = new Route(stations.get(startingStation), stations.get(destination));
            train.establishRoute(route.markTheRoute(stationsMap));

            train.locomotive.setSourceStation(stations.get(startingStation));
            train.locomotive.setDestination(stations.get(destination));

            trains.add(train);
            TrainThread trainThread = new TrainThread(train, stationsMap);
            trainThreads.add(trainThread);
        }

        TrafficControl trafficControl = new TrafficControl(trainThreads);
        trafficControl.start();

        AppStateThread appStateThread = new AppStateThread(trains, "src/project/text/AppState.txt");
        appStateThread.start();

        System.out.println("Available by default: \n" +
                " - stations with numbers 1 - 100 \n" +
                " - trains with numbers 1 - 25");
        System.out.println("===================================");
        System.out.println("Select option:" + "\n" +
                "- to determine the train route: T" + "\n" +
                "- to display the train report: R" + "\n" +
                "- to create a train: P" + "\n" +
                "- to create a carriage: W" + "\n" +
                "- to create a station: S" + "\n" +
                "- to remove an object: U" + "\n" + "\n" +
                "- to terminate the program: Q");
        System.out.println("===================================");

        Scanner scanner = new Scanner(System.in);
        char option = scanner.next().charAt(0);

        while (option != 'Q'){
            switch (option){
                case 'R' -> {
                    System.out.println("Select train number.");
                    int trainNumber = scanner.nextInt();
                    System.out.println(trains.get(trainNumber - 1).print());
                    System.out.println();
                    System.out.println("Train route:");
                    ArrayList<Station> currRoute = trains.get(trainNumber - 1).getRoute();
                    if (currRoute != null && !currRoute.isEmpty()) {
                        for (Station station : currRoute) {
                            System.out.println(station);
                        }
                        System.out.println("Percentage of route completed: " + trainThreads.get(trainNumber - 1).getRoutePercent() + "%");
                        System.out.println("Current station: " +
                                trainThreads.get(trainNumber - 1).getCurrentStation().getName());
                        System.out.println("Next station: " +
                                trainThreads.get(trainNumber - 1).getNextStation().getName());
                        System.out.println("Percentage of route completed between nearest stations: " + trainThreads.get(trainNumber - 1).getCurrRoutePercent() + "%");
                    }
                }
                case 'T' -> {
                    System.out.println("Determining the route for train number: ");
                    int trainNumber = scanner.nextInt();
                    System.out.println("Determine the starting station number.");
                    int startingStationNum = scanner.nextInt();
                    System.out.println("Determine the destination station number.");
                    int destinationStationNum = scanner.nextInt();

                    trains.get(trainNumber - 1).locomotive.setSourceStation(stations.get(startingStationNum));
                    trains.get(trainNumber - 1).locomotive.setDestination(stations.get(destinationStationNum));

                    Route route = new Route(trains.get(trainNumber - 1).locomotive.sourceStation, trains.get(trainNumber - 1).locomotive.destinationStation);
                    trains.get(trainNumber - 1).establishRoute(route.markTheRoute(stationsMap));

                    System.out.println("The route has been marked.");
                }
                case 'P' -> {
                    System.out.println("Provide the name of the locomotive.");
                    String name = scanner.next();
                    System.out.println("Set the maximum number of carriages.");
                    int maxCarriages = scanner.nextInt();
                    System.out.println("Determine the maximum towing capacity.");
                    double maxTowing = scanner.nextDouble();
                    System.out.println("Set the maximum number of wagons with PDSE.");
                    int maxPDSE = scanner.nextInt();
                    System.out.println("Set the speed.");
                    double speed = scanner.nextDouble();

                    Locomotive l = new Locomotive(name, maxCarriages, maxTowing, maxPDSE, speed);
                    Train train = new Train(l, stationsMap);
                    l.setTrain(train);

                    System.out.println("Determine the starting station number.");
                    int startingStationNum = scanner.nextInt();
                    System.out.println("Determine the destination station number.");
                    int destinationStationNum = scanner.nextInt();
                    l.setSourceStation(stations.get(startingStationNum));
                    l.setDestination(stations.get(destinationStationNum));
                    Route route = new Route(train.locomotive.sourceStation, train.locomotive.destinationStation);
                    train.establishRoute(route.markTheRoute(stationsMap));

                    TrainThread trainThread = new TrainThread(train, stationsMap);
                    trainThread.start();
                    trafficControl.addThread(trainThread);
                    trains.add(train);
                    appStateThread.addTrain(train);

                    System.out.println("Train no. " + train.number + " has been created.");
                }
                case 'W' -> {
                    System.out.println("What kind of carriage do you want to create?" + "\n" +
                            " - mail carriage: PO" + "\n" +
                            " - baggage and postal carriage: BP" + "\n" +
                            " - refrigerated wagon: CH" + "\n" +
                            " - liquid toxic materials wagon: CMT" + "\n" +
                            " - liquid materials wagon: MC" + "\n" +
                            " - gas materials wagon: MG" + "\n" +
                            " - toxic materials wagon: MT" + "\n" +
                            " - explosives wagon: MW" + "\n" +
                            " - passenger carriage: PA" + "\n" +
                            " - restaurant carriage: R" + "\n" +
                            " - basic goods wagon: TP" + "\n" +
                            " - heavy goods wagon : TC" + "\n");
                    String carriageType = scanner.next();
                    boolean done = false;

                    while (!done) {
                        switch (carriageType) {
                            case "BP" -> {
                                System.out.println("Enter the shipper.");
                                String shipper = scanner.next();
                                System.out.println("What are the safeguards?");
                                String security = scanner.next();
                                System.out.println("Provide weight without load.");
                                double weight = scanner.nextDouble();

                                BaggagePostalCarriage baggagePostalCarriage = new BaggagePostalCarriage(shipper,security, weight);

                                System.out.println("What to load into the wagon?");
                                String cargo = scanner.next();
                                System.out.println("What is the weight of the cargo?");
                                double cargoWeight = scanner.nextDouble();
                                baggagePostalCarriage.load(cargo, cargoWeight);

                                System.out.println("What train number do you want to assign this wagon to?");
                                int trainNumber = scanner.nextInt();
                                try {
                                    trains.get(trainNumber - 1).addCarriage(baggagePostalCarriage);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "CH" -> {
                                System.out.println("Enter the shipper.");
                                String shipper = scanner.next();
                                System.out.println("What are the safeguards?");
                                String security = scanner.next();
                                System.out.println("Provide weight without load.");
                                double weight = scanner.nextDouble();

                                RefrigeratedWagon refrigeratedWagon = new RefrigeratedWagon(shipper,security, weight);

                                System.out.println("What to load into the wagon?");
                                String cargo = scanner.next();
                                System.out.println("What is the weight of the cargo?");
                                double cargoWeight = scanner.nextDouble();
                                refrigeratedWagon.load(cargo, cargoWeight);

                                System.out.println("What train number do you want to assign this wagon to?");
                                int trainNumber = scanner.nextInt();
                                try {
                                    trains.get(trainNumber - 1).addCarriage(refrigeratedWagon);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "CMT" -> {
                                System.out.println("Enter the shipper.");
                                String shipper = scanner.next();
                                System.out.println("What are the safeguards?");
                                String security = scanner.next();
                                System.out.println("Provide weight without load.");
                                double weight = scanner.nextDouble();

                                ToxicLiquidsTankWagon toxicLiquidsTankWagon = new ToxicLiquidsTankWagon(shipper,security, weight);

                                System.out.println("What to load into the wagon?");
                                String cargo = scanner.next();
                                System.out.println("What is the weight of the cargo?");
                                double cargoWeight = scanner.nextDouble();
                                toxicLiquidsTankWagon.load(cargo, cargoWeight);

                                System.out.println("What train number do you want to assign this wagon to?");
                                int trainNumber = scanner.nextInt();
                                try {
                                    trains.get(trainNumber - 1).addCarriage(toxicLiquidsTankWagon);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "MC" -> {
                                System.out.println("Enter the shipper.");
                                String shipper = scanner.next();
                                System.out.println("What are the safeguards?");
                                String security = scanner.next();
                                System.out.println("Provide weight without load.");
                                double weight = scanner.nextDouble();

                                LiquidsTankWagon liquidsTankWagon = new LiquidsTankWagon(shipper,security, weight);

                                System.out.println("What to load into the wagon?");
                                String cargo = scanner.next();
                                System.out.println("What is the weight of the cargo?");
                                double cargoWeight = scanner.nextDouble();
                                liquidsTankWagon.load(cargo, cargoWeight);

                                System.out.println("What train number do you want to assign this wagon to?");
                                int trainNumber = scanner.nextInt();
                                try {
                                    trains.get(trainNumber - 1).addCarriage(liquidsTankWagon);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "MG" -> {
                                System.out.println("Enter the shipper.");
                                String shipper = scanner.next();
                                System.out.println("What are the safeguards?");
                                String security = scanner.next();
                                System.out.println("Provide weight without load.");
                                double weight = scanner.nextDouble();

                                GasesTankWagon gasesTankWagon = new GasesTankWagon(shipper,security, weight);

                                System.out.println("What to load into the wagon?");
                                String cargo = scanner.next();
                                System.out.println("What is the weight of the cargo?");
                                double cargoWeight = scanner.nextDouble();
                                gasesTankWagon.load(cargo, cargoWeight);

                                System.out.println("What train number do you want to assign this wagon to?");
                                int trainNumber = scanner.nextInt();
                                try {
                                    trains.get(trainNumber - 1).addCarriage(gasesTankWagon);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "MT" -> {
                                System.out.println("Enter the shipper.");
                                String shipper = scanner.next();
                                System.out.println("What are the safeguards?");
                                String security = scanner.next();
                                System.out.println("Provide weight without load.");
                                double weight = scanner.nextDouble();

                                HazardMaterialsWagon hazardMaterialsWagon = new HazardMaterialsWagon(shipper,security, weight);

                                System.out.println("What to load into the wagon?");
                                String cargo = scanner.next();
                                System.out.println("What is the weight of the cargo?");
                                double cargoWeight = scanner.nextDouble();
                                hazardMaterialsWagon.load(cargo, cargoWeight);

                                System.out.println("What train number do you want to assign this wagon to?");
                                int trainNumber = scanner.nextInt();
                                try {
                                    trains.get(trainNumber - 1).addCarriage(hazardMaterialsWagon);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "MW" -> {
                                System.out.println("Enter the shipper.");
                                String shipper = scanner.next();
                                System.out.println("What are the safeguards?");
                                String security = scanner.next();
                                System.out.println("Provide weight without load.");
                                double weight = scanner.nextDouble();

                                ExplosivesWagon explosivesWagon = new ExplosivesWagon(shipper,security, weight);

                                System.out.println("What to load into the wagon?");
                                String cargo = scanner.next();
                                System.out.println("What is the weight of the cargo?");
                                double cargoWeight = scanner.nextDouble();
                                explosivesWagon.load(cargo, cargoWeight);

                                System.out.println("What train number do you want to assign this wagon to?");
                                int trainNumber = scanner.nextInt();
                                try {
                                    trains.get(trainNumber - 1).addCarriage(explosivesWagon);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "PA" -> {
                                System.out.println("Enter the number of seats.");
                                int seatsNumber = scanner.nextInt();
                                System.out.println("What are the safeguards?");
                                String security = scanner.next();
                                System.out.println("Provide weight without load.");
                                double weight = scanner.nextDouble();

                                PassengerCarriage passengerCarriage = new PassengerCarriage(seatsNumber,security, weight);

                                System.out.println("How many passengers?");
                                int passengersCount = scanner.nextInt();
                                System.out.println("What is the weight?");
                                double passengersWeight = scanner.nextDouble();
                                try {
                                    passengerCarriage.load(passengersCount, passengersWeight);
                                }catch (TooManyPassengersException e){
                                    e.printStackTrace();
                                }

                                System.out.println("What train number do you want to assign this wagon to?");
                                int trainNumber = scanner.nextInt();
                                try {
                                    trains.get(trainNumber - 1).addCarriage(passengerCarriage);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "R" -> {
                                System.out.println("Enter the shipper.");
                                String shipper = scanner.next();
                                System.out.println("What are the safeguards?");
                                String security = scanner.next();
                                System.out.println("Provide weight without load.");
                                double weight = scanner.nextDouble();

                                RestaurantCarriage restaurantCarriage = new RestaurantCarriage(shipper,security, weight);

                                System.out.println("What to load into the wagon?");
                                String cargo = scanner.next();
                                System.out.println("What is the weight of the cargo?");
                                double cargoWeight = scanner.nextDouble();
                                restaurantCarriage.load(cargo, cargoWeight);

                                System.out.println("What train number do you want to assign this wagon to?");
                                int trainNumber = scanner.nextInt();
                                try {
                                    trains.get(trainNumber - 1).addCarriage(restaurantCarriage);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "TP" -> {
                                System.out.println("Enter the shipper.");
                                String shipper = scanner.next();
                                System.out.println("What are the safeguards?");
                                String security = scanner.next();
                                System.out.println("Provide weight without load.");
                                double weight = scanner.nextDouble();

                                BasicGoodsWagon basicGoodsWagon = new BasicGoodsWagon(shipper,security, weight);

                                System.out.println("What to load into the wagon?");
                                String cargo = scanner.next();
                                System.out.println("What is the weight of the cargo?");
                                double cargoWeight = scanner.nextDouble();
                                basicGoodsWagon.load(cargo, cargoWeight);

                                System.out.println("What train number do you want to assign this wagon to?");
                                int trainNumber = scanner.nextInt();
                                try {
                                    trains.get(trainNumber - 1).addCarriage(basicGoodsWagon);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "TC" -> {
                                System.out.println("Enter the shipper.");
                                String shipper = scanner.next();
                                System.out.println("What are the safeguards?");
                                String security = scanner.next();
                                System.out.println("Provide weight without load.");
                                double weight = scanner.nextDouble();

                                HeavyGoodsWagon heavyGoodsWagon = new HeavyGoodsWagon(shipper,security, weight);

                                System.out.println("What to load into the wagon?");
                                String cargo = scanner.next();
                                System.out.println("What is the weight of the cargo?");
                                double cargoWeight = scanner.nextDouble();
                                heavyGoodsWagon.load(cargo, cargoWeight);

                                System.out.println("What train number do you want to assign this wagon to?");
                                int trainNumber = scanner.nextInt();
                                try {
                                    trains.get(trainNumber - 1).addCarriage(heavyGoodsWagon);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "PO" -> {
                                System.out.println("Enter the shipper.");
                                String shipper = scanner.next();
                                System.out.println("What are the safeguards?");
                                String security = scanner.next();
                                System.out.println("Provide weight without load.");
                                double weight = scanner.nextDouble();

                                MailCarriage mailCarriage = new MailCarriage(shipper,security, weight);

                                System.out.println("What to load into the wagon?");
                                String cargo = scanner.next();
                                System.out.println("What is the weight of the cargo?");
                                double cargoWeight = scanner.nextDouble();
                                mailCarriage.load(cargo, cargoWeight);

                                System.out.println("What train number do you want to assign this wagon to?");
                                int trainNumber = scanner.nextInt();
                                try {
                                    trains.get(trainNumber - 1).addCarriage(mailCarriage);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            default -> {
                                System.out.println("Select a wagon from the list.");
                                carriageType = scanner.next();
                            }
                        }
                    }
                }
                case 'S' -> {
                    System.out.println("Enter the name of the station.");
                    String name = scanner.next();

                    Station station = new Station(name);

                    System.out.println("How many stations should it be connected to?");
                    int num = scanner.nextInt();
                    int stationCount = 0;
                    HashMap<Station, Integer> directConnection = new HashMap<>();

                    while (stationCount < num){
                        System.out.println("Enter the station number to connect to.");
                        int number = scanner.nextInt();
                        Station s = stations.get(number);
                        System.out.println("Enter the distance.");
                        int distance = scanner.nextInt();
                        directConnection.put(s, distance);
                        HashMap<Station, Integer> otherSide = stationsMap.get(s);
                        otherSide.put(station, distance);

                        stationCount++;
                    }

                    stationsMap.put(station, directConnection);
                    stations.add(station);

                    System.out.println("Station " + name + " with number " + station.getIndex() + " has been created.");
                }
                case 'U' -> {
                    System.out.println("What object do you want to remove?" + "\n" +
                            " - stations: S" + "\n" +
                            " - train: P" + "\n" +
                            " - carriage: W");
                    String delete = scanner.next();
                    boolean done = false;

                    while (!done){
                        switch (delete) {
                            case "S" -> {
                                System.out.println("Enter the number of the station you want to remove.");
                                int stationNumber = scanner.nextInt();
                                stationsMap.remove(stations.get(stationNumber));
                                stations.remove(stationNumber);

                                System.out.println("Station no. " + stationNumber + " has been removed.");
                                System.out.println("Thus, stations with higher numbers will be indexed with numbers 1 lower than previously.");
                                System.out.println("When selecting a station number higher than the station you just deleted, enter the number reduced by 1 from the previously entered number.");
                                done = true;
                            }
                            case "P" -> {
                                System.out.println("Enter the train number to be removed.");
                                int trainNumber = scanner.nextInt();
                                appStateThread.deleteTrain(trains.get(trainNumber - 1));
                                trains.remove(trainNumber - 1);
                                trainThreads.get(trainNumber - 1).interrupt();

                                System.out.println("The train has been removed and therefore trains with higher numbers will be indexed with numbers 1 lower than previously.");
                                System.out.println("When selecting a train number higher than the train you just deleted, enter the number reduced by 1 from the previously entered number.");
                                done = true;
                            }
                            case "W" -> {
                                System.out.println("Select train number.");
                                int trainNumber = scanner.nextInt();
                                System.out.println("Carriage with what cargo would you like to remove?");
                                String cargo = scanner.next();
                                trains.get(trainNumber - 1).removeCarriage(cargo);

                                System.out.println("Carriage with load " + cargo + " has been removed.");
                                done = true;
                            }
                            default -> {
                                System.out.println("Select one of the options.");
                                delete = scanner.next();
                            }
                        }
                    }

                }
                default -> System.out.println("Select one of the options given in the instructions.");
            }
            System.out.println("===================================");
            System.out.println("Choose option:" + "\n" +
                    "- to determine the train route: T" + "\n" +
                    "- to display the train report: R" + "\n" +
                    "- to create a train: P" + "\n" +
                    "- to create a carriage: W" + "\n" +
                    "- to create a station: S" + "\n" +
                    "- to remove an object: U" + "\n" + "\n" +
                    "- to terminate the program: Q");
            System.out.println("===================================");
            option = scanner.next().charAt(0);
        }

        trafficControl.finish();
        appStateThread.interrupt();
    }
}