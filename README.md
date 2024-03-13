# BayNav :bus:

This project attempts to bring the VTA's Transit app experience on Wear OS smartwatches. The goal is to bridge the gap between the Bay Area's public transport frequenters and Android smartwatch enthusiasts.

## Ain't on Play Store? :no_mouth:

That's 'cuz we're FOSS! :shield:

For sure, but there's more to it. Outright, the app is pretty straightforward but here's why it isn't on Play Store yet:

1. You need an API key to poll Transit, which comes with rate limits -- one key can't rule it all
2. Imagine typing out a 64-character API key on an ant-sized watch screen
3. A phone app pair sounds good but out of scope at the moment
4. Other access code flows can be ingenious but then again #3

## Gimme the app :money_with_wings:

The only way to get an installable APK is by cloning and building this project on Android Studio, locally.

### Most important steps! :point_up:

1. Email the Transit team for an API key
2. Edit `baynav/util/Constants.java` to include the API key in the `TRANSIT_API_KEY` variable

_**PS:** I'll try to find some time to set up Gradle on my headless server to automate builds based on your own API key._

---

Hopefully the VTA will recognize this tiny project and be generous enough to provide an API key with enough quota. :grin: