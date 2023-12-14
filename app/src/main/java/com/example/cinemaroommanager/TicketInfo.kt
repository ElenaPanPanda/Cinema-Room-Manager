package com.example.cinemaroommanager

class Ticket(
    film: Film,
    val row: Int,
    val seat: Int
) {
    private val maxWeight = 1.5
    private val minWeight = 0.5
    val ticketPrice = film.estimatedTicketPrice * (maxWeight - (weightStep() * (row - 1)))

    private fun weightStep(): Double = (maxWeight - minWeight) / Cinema.rowsNumber
}