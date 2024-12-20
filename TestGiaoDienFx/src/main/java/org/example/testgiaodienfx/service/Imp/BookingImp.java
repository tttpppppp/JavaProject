package org.example.testgiaodienfx.service.Imp;

import org.example.testgiaodienfx.model.Bookings;
import org.example.testgiaodienfx.model.DanhSachBooking;

import java.util.List;

public interface BookingImp {
    boolean addBooking(Bookings booking);
    List<Bookings> getAllBookings();
    Bookings getBookingById(int bookingId);
    boolean updateBooking(Bookings booking);
    boolean deleteBooking(int bookingId);
    List<DanhSachBooking> getAllBookingsWithDetails();
}
