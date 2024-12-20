package org.example.testgiaodienfx.service;

import org.example.testgiaodienfx.model.Bookings;
import org.example.testgiaodienfx.model.DanhSachBooking;
import org.example.testgiaodienfx.repository.BookingRepository;
import org.example.testgiaodienfx.service.Imp.BookingImp;

import java.util.List;

public class BookingSerivce implements BookingImp {
    BookingRepository bookingRepository = new BookingRepository();

    @Override
    public boolean addBooking(Bookings booking) {
        return bookingRepository.addBooking(booking);
    }

    @Override
    public List<Bookings> getAllBookings() {
        return bookingRepository.getAllBookings();
    }

    @Override
    public Bookings getBookingById(int bookingId) {
        return bookingRepository.getBookingById(bookingId);
    }

    @Override
    public boolean updateBooking(Bookings booking) {
        return bookingRepository.updateBooking(booking);
    }

    @Override
    public boolean deleteBooking(int bookingId) {
        return bookingRepository.deleteBooking(bookingId);
    }

    @Override
    public List<DanhSachBooking> getAllBookingsWithDetails() {
        return bookingRepository.getAllBookingsWithDetails();
    }
}
