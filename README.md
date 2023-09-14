## SETUP
The project requires the `endpoint.properties` file in the project root. This file contains API details. The following is required in order to build the project.

Create `endpoint.properties` file in the project root.
Add the following content to the file, replacing the placeholder values with your own Digitransit API key and the corresponding Digitransit API URL.
```
DIGITRANSIT_KEY = "Your digitransit API key here"
DIGITRANSIT_URL = "Digitransit URL"
```

The project has been verified to work with `Android Studio Giraffe | 2022.3.1 Patch 1`. Using it with other versions can cause issues.

## Usage
The application requires location permissions in order to be able to fetch nearest public transit stops. The logic currently uses the last known location, if available. 

*Note for emulators*: it can be tricky to populate the emulator with a location. Follow these steps in case of any issues:
1. Launch emulator that has Google Play Services
2. Verify that network is available
3. Verify that location services are turned on
4. On the Emulator's extended controls (`...` symbol on the right hand side of the emulator) enter the `Location` section
5. On the `Single points` tab search for a location in `Helsinki` or another location in the Helsinki capital region
6. Once a desired location has been selected on the map, click on `Set Location` button
7. Enter `Google Maps` app on the emulator
8. Wait for Google Maps to fetch the new location (this might take a while)
9. Once location is fetched open the `PublicTransitStops` app

## Application requirements
- Minimum SDK version 26
- Google Play Services (location)

## General notes on code quality
- Code structure: code is structured loosely, not following any specific pattern to the point. Clean architecture, or similar patterns, would be recommended if this were to be developed further. Adding further complexity to the existing structure can make it difficult to locate files.
- Code is testable, however, tests have only been written for `NearestStopsScreenViewModel` 
- Data types from network layer are leaked all the way to presentation layer. To fix this, it would be recommended to create separate presentation entities, so that the presentation layer is not coupled with the network layer
- Location data is only fetched once (latest know location), making it so that if it's not available the app will remain in Error state. Fetching new location updates would improve this solution.
