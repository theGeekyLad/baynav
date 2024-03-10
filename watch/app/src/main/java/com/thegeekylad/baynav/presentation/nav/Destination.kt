package com.thegeekylad.baynav.presentation.nav

sealed class Destination(path: String);

data class StopsDestination(val path: String = "nearby_stops"): Destination(path)
data class DeparturesDestination(val path: String = "stop_departures"): Destination(path)