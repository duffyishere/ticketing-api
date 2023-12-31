package org.duffy.ticketing.domain.concert.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.duffy.ticketing.domain.account.SellerAccount;
import org.duffy.ticketing.domain.concert.Concert;
import org.duffy.ticketing.domain.concert.Seat;
import org.duffy.ticketing.domain.concert.dto.CreateConcertRequest;
import org.duffy.ticketing.domain.concert.dto.GetConcertDetailResponse;
import org.duffy.ticketing.domain.concert.dto.SeatResponse;
import org.duffy.ticketing.domain.concert.repository.ConcertRepository;
import org.duffy.ticketing.domain.concert.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class ConcertService {
    private final ConcertRepository concertRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public void createConcert(SellerAccount seller, CreateConcertRequest body) {
        Concert concert = new Concert(seller, body);
        concertRepository.save(concert);

        List<Seat> seats = createSeats(concert, body.seatingCapacity());
        seatRepository.saveAll(seats);
    }

    private List<Seat> createSeats(Concert concert, int capacity) {
        return IntStream.rangeClosed(1, capacity)
                .mapToObj(seatNumber -> new Seat(seatNumber, concert))
                .collect(Collectors.toList());
    }

    public GetConcertDetailResponse getConcertDetail(Long concertId) {
        Concert concert = getConcertById(concertId);
        return new GetConcertDetailResponse(concert);
    }

    public List<SeatResponse> getSeatsFor(Long concertId) {
        Concert concert = getConcertById(concertId);
        return seatRepository.findByConcert(concert).stream()
                .map(SeatResponse::new)
                .toList();
    }

    public Concert getConcertById(Long concertId) {
        return concertRepository.findById(concertId).orElseThrow(() -> new IllegalArgumentException("No such concert exists."));
    }
}
