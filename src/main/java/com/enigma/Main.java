package com.enigma;

import com.enigma.config.Db_Connector;
import com.enigma.entitiy.*;
import com.enigma.repository.impl.*;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        Db_Connector dbConnector = new Db_Connector();
        Connection con = dbConnector.startConnect();

        CustomerRepoImpl customerRepo = new CustomerRepoImpl(con);
//        System.out.println(customerRepo.selectAll());
//        customerRepo.create(new Customer("Dimas H", "2012-10-12"));
//        customerRepo.update(new Customer(2, "Dimas Hadianto Batch 14", "2004-12-23"));
//        customerRepo.delete(4);
//        System.out.println(customerRepo.getById(1));

        FilmRepoImpl filmRepo = new FilmRepoImpl(con);
        System.out.println(filmRepo.selectAll());
//        filmRepo.create(new Film("Titanic", 120, "2023-12-4", 35000, 2));
//        filmRepo.update(new Film(2, "Heros", 100, "2023-12-02", 35000, 1));
//        filmRepo.delete(5);
//        System.out.println(filmRepo.getById(1));

        RatingRepoImpl ratingRepo = new RatingRepoImpl(con);
//        System.out.println(ratingRepo.selectAll());
//        System.out.println(ratingRepo.getById(1));

        SeatRepoImpl seatRepo = new SeatRepoImpl(con);
//        System.out.println(seatRepo.selectAll());
//        seatRepo.create(new Seat("E1", 5));
//        seatRepo.update(new Seat(1, "A1", 1));
//        seatRepo.delete(1);
//        System.out.println(seatRepo.getById(1));

        TheaterRepoImpl theaterRepo = new TheaterRepoImpl(con);
//        System.out.println(theaterRepo.selectAll());
//        theaterRepo.create(new Theater("Studio 5", 50, 1));
//        theaterRepo.update(new Theater(5, "Sudio 5", 50, 2));
//        theaterRepo.delete(5);
//        System.out.println(theaterRepo.getById(1));

        TicketRepoImpl ticketRepo = new TicketRepoImpl(con);
//        System.out.println(ticketRepo.selectAll());
//        ticketRepo.create(new Ticket(1,5));
//        ticketRepo.update(new Ticket(1, 1, 3));
//        ticketRepo.delete(1);
//        System.out.println(ticketRepo.getById(2));

    }
}