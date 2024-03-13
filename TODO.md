# Baynav

## Todo

- Don't return all stops
  - List is huge
    - Also involves sorting by distance = heavy
  - Only return a few
  - Let the user "load more"

## APIs

### `GET` /nearby_stops

- Track number of Transit API calls made
  - If under 5,
    - Cache `(req, res)` in a map
    - Return response
  - If 5, return cached response

#### Request

- lat
- long

#### Response

Sorted in ascending order of distance.

```JSON
[
  {
    "stop_name": "Santa Clara / 5th",
    "global_stop_id": "VTA:45880",
    "distance": "500",
  }
]
```

### `GET` /stop_departures

#### Request

- global_stop_id
- global_route_id _(optional)_

#### Response

- If `global_route_id` is present, this endpoint tracks a particular departure
- Will at least be a single element array
- Can be repeatedly polled - cache response

```JSON
[
  {
    "global_route_id": "VTA:5837",
    "route_short_name": "22",
    "direction_headsign": "Palo Alto",
    "departure_interval": 145, // secs
  }
]
```
