package com.debuggeando_ideas.best_travel.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity(name="tour")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "tour"
    )
    private Set<ReservationEntity> reservationEntities;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "tour"
    )
    private Set<TicketEntity> tickets;
    @ManyToOne
    @JoinColumn(name="id_customer")
    private CustomerEntity customer;

    public void addTicket(TicketEntity ticket){
        if (Objects.isNull(this.tickets)) this.tickets = new HashSet<>();
        this.tickets.add(ticket);
    }
    public void removeTicket(UUID id){
        if (Objects.isNull(this.tickets)) this.tickets = new HashSet<>();
        this.tickets.removeIf(ticket -> ticket.getId().equals(id));
    }

    public void updateTickets(){
        this.tickets.forEach(ticket -> ticket.setTour(this));
    }

    public void addReservation(ReservationEntity reservation){
        if (Objects.isNull(this.reservationEntities)) this.reservationEntities = new HashSet<>();
        this.reservationEntities.add(reservation);
    }

    public void removeReservatoin(UUID idReservation){
        if (Objects.isNull(this.reservationEntities)) this.reservationEntities = new HashSet<>();
        this.reservationEntities.removeIf(r->r.getId().equals(idReservation));
    }

    public void updateReservations(){
        this.reservationEntities.forEach(r->r.setTour(this));
    }
}
