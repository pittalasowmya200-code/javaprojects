import java.io.*;
import java.util.*;

// ---------------- ROOM CLASS ----------------
class Room {
    private int roomNumber;
    private String category;
    private double price;
    private boolean available;

    public Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void displayRoom() {
        System.out.println(
            "Room: " + roomNumber +
            " | Category: " + category +
            " | Price: ₹" + price +
            " | Status: " + (available ? "Available" : "Booked")
        );
    }
}


// ---------------- CUSTOMER CLASS ----------------
class Customer {
    private int customerId;
    private String name;
    private String phone;

    public Customer(int customerId, String name, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}


// ---------------- RESERVATION CLASS ----------------
class Reservation {
    private int reservationId;
    private Customer customer;
    private Room room;
    private String checkIn;
    private String checkOut;
    private double amount;
    private String paymentStatus;
    private String status;

    public Reservation(int reservationId, Customer customer,
                       Room room, String checkIn,
                       String checkOut, double amount) {

        this.reservationId = reservationId;
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.amount = amount;

        this.paymentStatus = "Pending";
        this.status = "Confirmed";
    }

    public int getReservationId() {
        return reservationId;
    }

    public Room getRoom() {
        return room;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void displayReservation() {
        System.out.println("\n========== BOOKING DETAILS ==========");
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Customer ID    : " + customer.getCustomerId());
        System.out.println("Customer Name  : " + customer.getName());
        System.out.println("Phone          : " + customer.getPhone());
        System.out.println("Room Number    : " + room.getRoomNumber());
        System.out.println("Category       : " + room.getCategory());
        System.out.println("Check-in       : " + checkIn);
        System.out.println("Check-out      : " + checkOut);
        System.out.println("Amount         : ₹" + amount);
        System.out.println("Payment        : " + paymentStatus);
        System.out.println("Status         : " + status);
        System.out.println("=====================================");
    }

    public String saveData() {
        return reservationId + "," +
               customer.getCustomerId() + "," +
               customer.getName() + "," +
               customer.getPhone() + "," +
               room.getRoomNumber() + "," +
               room.getCategory() + "," +
               checkIn + "," +
               checkOut + "," +
               amount + "," +
               paymentStatus + "," +
               status;
    }
}


// ---------------- HOTEL SYSTEM CLASS ----------------
public class HotelReservationSystem {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();

    static int reservationCounter = 1001;
    static int customerCounter = 1;


    // Add default rooms
    public static void addDefaultRooms() {

        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(103, "Standard", 1500));

        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2500));
        rooms.add(new Room(203, "Deluxe", 2500));

        rooms.add(new Room(301, "Suite", 4000));
        rooms.add(new Room(302, "Suite", 4000));
    }


    // Search rooms
    public static void searchRooms() {

        System.out.println("\n===== SEARCH ROOMS =====");

        System.out.println("1. Standard");
        System.out.println("2. Deluxe");
        System.out.println("3. Suite");
        System.out.println("4. All");

        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        String category = "";

        if (choice == 1)
            category = "Standard";
        else if (choice == 2)
            category = "Deluxe";
        else if (choice == 3)
            category = "Suite";
        else if (choice != 4) {
            System.out.println("Invalid choice.");
            return;
        }

        boolean found = false;

        for (Room room : rooms) {

            if (room.isAvailable() &&
                (choice == 4 || room.getCategory().equals(category))) {

                room.displayRoom();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No available rooms found.");
        }
    }


    // Display all rooms
    public static void displayAllRooms() {

        System.out.println("\n========== ALL ROOMS ==========");

        for (Room room : rooms) {
            room.displayRoom();
        }
    }


    // Find room
    public static Room findRoom(int roomNumber) {

        for (Room room : rooms) {

            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }

        return null;
    }


    // Make reservation
    public static void makeReservation() {

        System.out.println("\n========== MAKE RESERVATION ==========");

        sc.nextLine();

        System.out.print("Enter customer name: ");
        String name = sc.nextLine();

        System.out.print("Enter phone number: ");
        String phone = sc.nextLine();

        System.out.print("Enter room number: ");
        int roomNumber = sc.nextInt();

        Room room = findRoom(roomNumber);

        if (room == null) {
            System.out.println("Room does not exist.");
            return;
        }

        if (!room.isAvailable()) {
            System.out.println("Room is already booked.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter check-in date (DD-MM-YYYY): ");
        String checkIn = sc.nextLine();

        System.out.print("Enter check-out date (DD-MM-YYYY): ");
        String checkOut = sc.nextLine();

        Customer customer =
            new Customer(customerCounter++, name, phone);

        double amount = room.getPrice();

        Reservation reservation =
            new Reservation(
                reservationCounter++,
                customer,
                room,
                checkIn,
                checkOut,
                amount
            );

        // Block the room
        room.setAvailable(false);

        // Payment
        System.out.println("\n========== PAYMENT ==========");
        System.out.println("Amount to pay: ₹" + amount);

        System.out.println("1. UPI");
        System.out.println("2. Card");
        System.out.println("3. Cash");

        System.out.print("Select payment method: ");
        int paymentChoice = sc.nextInt();

        if (paymentChoice >= 1 && paymentChoice <= 3) {

            System.out.println("Processing payment...");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Payment interrupted.");
            }

            reservation.setPaymentStatus("Successful");

            System.out.println("Payment successful!");

        } else {

            System.out.println("Invalid payment option.");
            reservation.setPaymentStatus("Pending");
        }

        reservations.add(reservation);

        saveReservation(reservation);

        System.out.println("\nReservation successful!");
        System.out.println("Reservation ID: " +
                           reservation.getReservationId());
    }


    // Cancel reservation
    public static void cancelReservation() {

        System.out.println("\n========== CANCEL RESERVATION ==========");

        System.out.print("Enter reservation ID: ");
        int id = sc.nextInt();

        Reservation foundReservation = null;

        for (Reservation reservation : reservations) {

            if (reservation.getReservationId() == id) {
                foundReservation = reservation;
                break;
            }
        }

        if (foundReservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        foundReservation.setStatus("Cancelled");

        // Make room available again
        foundReservation.getRoom().setAvailable(true);

        System.out.println("Reservation cancelled successfully.");
        System.out.println(
            "Room " +
            foundReservation.getRoom().getRoomNumber() +
            " is now available."
        );

        saveAllReservations();
    }


    // View booking details
    public static void viewBooking() {

        System.out.println("\n========== VIEW BOOKING ==========");

        System.out.print("Enter reservation ID: ");
        int id = sc.nextInt();

        for (Reservation reservation : reservations) {

            if (reservation.getReservationId() == id) {

                reservation.displayReservation();
                return;
            }
        }

        System.out.println("Reservation not found.");
    }


    // View all bookings
    public static void viewAllBookings() {

        System.out.println("\n========== ALL BOOKINGS ==========");

        if (reservations.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Reservation reservation : reservations) {
            reservation.displayReservation();
        }
    }


    // Save one reservation to file
    public static void saveReservation(Reservation reservation) {

        try {

            FileWriter writer =
                new FileWriter("bookings.txt", true);

            writer.write(reservation.saveData() + "\n");

            writer.close();

        } catch (IOException e) {

            System.out.println(
                "Error saving booking: " + e.getMessage()
            );
        }
    }


    // Rewrite all reservations
    public static void saveAllReservations() {

        try {

            FileWriter writer =
                new FileWriter("bookings.txt");

            for (Reservation reservation : reservations) {

                writer.write(reservation.saveData() + "\n");
            }

            writer.close();

        } catch (IOException e) {

            System.out.println(
                "Error saving bookings: " + e.getMessage()
            );
        }
    }


    // Main menu
    public static void main(String[] args) {

        addDefaultRooms();

        int choice;

        do {

            System.out.println("\n\n==================================");
            System.out.println("     HOTEL RESERVATION SYSTEM");
            System.out.println("==================================");

            System.out.println("1. Search Available Rooms");
            System.out.println("2. Display All Rooms");
            System.out.println("3. Make Reservation");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. View Booking Details");
            System.out.println("6. View All Bookings");
            System.out.println("7. Exit");

            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    searchRooms();
                    break;

                case 2:
                    displayAllRooms();
                    break;

                case 3:
                    makeReservation();
                    break;

                case 4:
                    cancelReservation();
                    break;

                case 5:
                    viewBooking();
                    break;

                case 6:
                    viewAllBookings();
                    break;

                case 7:
                    System.out.println(
                        "Thank you for using Hotel Reservation System!"
                    );
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 7);

        sc.close();
    }
}