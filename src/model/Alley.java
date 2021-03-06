package model;

import db.MainProgramOperations;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Peter on 11/03/2015.
 */
public class Alley {


    private MainProgramOperations progOps;
    private ResultSet rSet;
    private ArrayList<Member> memList = new ArrayList<Member>();
    private ArrayList<Staff> staffList = new ArrayList<Staff>();
    private ArrayList<Lane> laneList = new ArrayList<Lane>();
    private ArrayList<Stock> stockList = new ArrayList<Stock>();
    private ArrayList<Booking> bookingList = new ArrayList<Booking>();

    public Alley (MainProgramOperations po) {
        System.out.println("Inside : AlleyModel");

        this.progOps = po;
        rSet = progOps.getMembers();
        try {
            while (rSet.next()) {
                memList.add(new Member(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getString(4),
                        rSet.getString(5), rSet.getString(6), rSet.getString(7), rSet.getString(8), rSet.getString(9),
                        rSet.getInt(10)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        rSet = progOps.getStaff();
        try {
            while (rSet.next()) {
                staffList.add(new Staff(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getString(4), rSet.getString(5), rSet.getString(6), rSet.getString(7), rSet.getString(8)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        rSet = progOps.getLanes();
        try {
            while (rSet.next()) {
                laneList.add(new Lane(rSet.getInt(1), rSet.getString(2), rSet.getInt(3)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        rSet = progOps.getStock();
        try {
            while (rSet.next()) {
                stockList.add(new Stock(rSet.getInt(1), rSet.getString(2), rSet.getInt(3), rSet.getString(4)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        rSet = progOps.getBookings();
        try {
            while (rSet.next()) {
                bookingList.add(new Booking(rSet.getInt(1), rSet.getInt(2), rSet.getInt(3), rSet.getString(4), rSet.getString(5)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Member> getMemberList()
    {
        System.out.println("Inside : getMemberList() in AlleyModel");
        return memList;
    }

    public ArrayList<Staff> getStaffList()
    {
        System.out.println("Inside : getStaffList() in AlleyModel");
        return staffList;
    }

    public ArrayList<Lane> getLaneList()
    {
        System.out.println("Inside : getLaneList() in AlleyModel");
        return laneList;
    }

    public ArrayList<Stock> getStockList()
    {
        System.out.println("Inside : getStockList() in AlleyModel");
        return stockList;
    }

    public ArrayList<Booking> getBookingList()
    {
        System.out.println("Inside : getBookingList() in AlleyModel");
        return bookingList;
    }

    public void addMember()
    {
        System.out.println("Inside : addMember() in AlleyModel");
        rSet = progOps.getMemLastRow();
        try {
            memList.add(new Member(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getString(4),
                    rSet.getString(5), rSet.getString(6), rSet.getString(7), rSet.getString(8), rSet.getString(9),
                    rSet.getInt(10)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateMember(Member m) {
        for (int i = 0; i < memList.size(); i++) {
            if (memList.get(i).getId() == (m.getId()))
                memList.set(i, m);
        }
    }

    public void addStaff()
    {
        System.out.println("Inside : addStaff() in AlleyModel");
        rSet = progOps.getStaffLastRow();
        try {
            while (rSet.next()) {
                staffList.add(new Staff(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getString(4), rSet.getString(5), rSet.getString(6), rSet.getString(7), rSet.getString(8)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateStaff(Staff s) {
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getId() == (s.getId()))
                staffList.set(i, s);
        }
    }

    public void addStock()
    {
        System.out.println("Inside : addStock() in AlleyModel");
        rSet = progOps.getStockLastRow();
        try {
            memList.add(new Member(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getString(4),
                    rSet.getString(5), rSet.getString(6), rSet.getString(7), rSet.getString(8), rSet.getString(9),
                    rSet.getInt(10)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addBooking()
    {
        System.out.println("Inside : addBooking() in AlleyModel");
        rSet = progOps.getBookingLastRow();
        try {
            bookingList.add(new Booking(rSet.getInt(1), rSet.getInt(2), rSet.getInt(3), rSet.getString(4),
                    rSet.getString(5)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
