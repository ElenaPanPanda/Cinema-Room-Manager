package com.example.cinemaroommanager

class Film(
    private val rating: String,
    private val duration: String,
) {

    val estimatedTicketPrice = estimatedTicketPrice()
    val totalIncome = totalIncome()
    var currentIncome = 0.0

    private fun estimatedTicketPrice(): Double  = (rating.toDouble() * movieDurationProfit()) / Cinema.availableSeats

    private fun movieDurationProfit(): Double {
        val duration = this.duration.toDouble()
        return -(1f / 90) * (duration * duration) + (2 * duration) + 90
    }

    private fun totalIncome(): Double {
        var totalIncome = 0.0

        for (seat in 1..Cinema.seatsNumber) {
            for (row in 1..Cinema.rowsNumber) {
                totalIncome += Ticket(this, row, seat).ticketPrice
            }
        }

        return totalIncome
    }
}