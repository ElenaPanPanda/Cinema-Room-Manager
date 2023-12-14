package com.example.cinemaroommanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.cinemaroommanager.databinding.ActivityMainBinding
import com.example.cinemaroommanager.databinding.PlaceButtonBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*//for tests in Hyperskill
        val duration = intent.getStringExtra("DURATION")
        val rating = intent.getStringExtra("RATING")
        //If the rating isn't in the intent, consider it 4.5f
        //If the movie duration isn't in the intent, consider it 108*/

        val film = Film(randomRating(), randomDuration())

        val inflater = LayoutInflater.from(this)

        val gridLayout = binding.cinemaRoomPlaces

        for (row in 1..Cinema.rowsNumber) {
            for (seat in 1..Cinema.seatsNumber) {
                val view = PlaceButtonBinding.inflate(inflater, gridLayout, false)
                val ticket = Ticket(film, row, seat)

                val button = view.placeButton
                val text = "$row.$seat"
                button.text = text

                button.setOnClickListener {
                    showAlertDialog(ticket, film, button)
                }

                gridLayout.addView(view.root)
            }
        }

        binding.cinemaRoomTicketPrice.text =
            getString(R.string.estimated_ticket_price, film.estimatedTicketPrice.getFormatted())

        binding.cinemaRoomTotalIncome.text =
            getString(R.string.Total_cinema_income, film.totalIncome.getFormatted())

        binding.cinemaRoomCurrentIncome.text =
            getString(R.string.Current_cinema_income, film.currentIncome.getFormatted())

        binding.cinemaRoomAvailableSeats.text =
            getString(R.string.Available_seats, Cinema.availableSeats.toString())

        binding.cinemaRoomOccupiedSeats.text =
            getString(R.string.Occupied_seats, Cinema.occupiedSeats.toString())
    }

    private fun showAlertDialog(ticket: Ticket, film: Film, button: Button) {
        val title = getString(R.string.a_d_title, ticket.row.toString(), ticket.seat.toString())
        val message = getString(R.string.a_d_message, ticket.ticketPrice.getFormatted())

        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            /*// for tests in Hyperskill
            // place -> seat*/

            setTitle(title)
            setMessage(message)
            setPositiveButton(R.string.a_d_positive_button) { _, _ ->
                button.isEnabled = false

                film.currentIncome += ticket.ticketPrice
                binding.cinemaRoomCurrentIncome.text =
                    getString(R.string.Current_cinema_income, film.currentIncome.getFormatted())

                Cinema.availableSeats -= 1
                binding.cinemaRoomAvailableSeats.text =
                    getString(R.string.Available_seats, Cinema.availableSeats.toString())

                Cinema.occupiedSeats += 1
                binding.cinemaRoomOccupiedSeats.text =
                    getString(R.string.Occupied_seats, Cinema.occupiedSeats.toString())
            }
            setNegativeButton(R.string.a_d_negative_button, null)
            show()
        }
    }

    private fun Double.getFormatted(): String {
        val string = String.format("%.2f", this)
        return string.replace(',', '.', false)
    }

    private fun randomDuration(): String = Random.nextDouble(90.0, 108.1).toString()

    private fun randomRating(): String = Random.nextInt(1, 6).toString()
}

