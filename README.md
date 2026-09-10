# Bus Trips

A Java console application that displays upcoming bus arrivals at a selected bus stop using GTFS public transport schedule data.

## 1. Running and Using the Application

### Prerequisites

Before running the application, make sure you have:

* Java Development Kit (JDK) 26 or newer
* Git
* Maven

You can verify that Java and Maven are installed by running:

```bash
java -version
mvn -version
```

### Clone the Repository

Clone the GitHub repository using:

```bash
git clone https://github.com/FallenBanana200/papercast.git
```

Then navigate into the project directory:

```bash
cd papercast
```

### Run the Tests

Before running the application, the tests can be executed with:

```bash
mvn test
```

The project contains both unit tests and an integration test for the GTFS data processing flow.

### Run the Application

The application can be started from the terminal using Maven:

```bash
mvn exec:java -Dexec.mainClass="org.example.Main"
```

When the application starts, it displays instructions in the console.

### Using the Application

The application first asks for a bus stop ID:

```text
Enter station ID:
```

Enter the ID of the desired stop. The ID must be an integer.

For example:

```text
Enter station ID: 2
```

If the stop does not exist, the application informs the user and asks for another ID:

```text
Station with ID 1 was not found.
```

After entering a valid stop ID, the application asks how many upcoming buses should be displayed for each route:

```text
Enter number of upcoming buses per line:
```

For example:

```text
Enter number of upcoming buses per line: 5
```

The application then asks which time format should be used:

```text
Choose time format [A] Absolute / [R] Relative:
```

Two formats are available:

* **Absolute (`A`)** – displays the actual arrival time, for example `10:30:00`
* **Relative (`R`)** – displays how many minutes remain until the bus arrives, for example `in 15 min`

For example:

```text
Choose time format [A] Absolute / [R] Relative: r
```

The application then displays the selected stop and upcoming arrivals grouped by bus route:

```text
Stop: AL Masjid Al-nabawi (Clock Roundabout)

Route 101:
  in 2 min
  in 5 min
  in 15 min
  in 17 min
  in 20 min

Route 106:
  in 2 min
  in 3 min
  in 17 min
  in 18 min
  in 27 min
```

Only arrivals within the next **two hours** are considered, and at most the requested number of arrivals is displayed for each route.

After displaying the results, the application asks whether the user wants to perform another search:

```text
[N] New search
[Q] Quit
>
```

Enter:

* `N` to search for another bus stop
* `Q` to exit the application

The application continues accepting new searches until the user chooses `Q`.

## 2. How to Make the Task More Challenging

The task could be made more challenging by extending the application beyond the static GTFS schedule data.

### Live Updates Using a Realtime API

Instead of relying only on the static GTFS files, the application could also use a public realtime transport API.

The application could periodically request updated data from an API endpoint and combine it with the scheduled GTFS data. For example, the realtime data could contain:

* current delays,
* updated arrival times,
* canceled trips,
* service disruptions,
* and, if available, the current position of buses.

GTFS Realtime specifically supports trip updates, vehicle positions, and service alerts, which can be used to provide more accurate information about the current state of public transport.

For example, instead of displaying only:

```text
Route 101:
  10:30
  10:45
  11:00
```

the application could combine the scheduled times with realtime information and display:

```text
Route 101:
  10:34  (+4 min delay)
  10:45
  CANCELED
```

An even more advanced version could periodically refresh the data while the application is running, allowing the displayed arrivals to change as new information becomes available.

### Advanced Route and Stop Filtering

The application could also provide more advanced filtering of bus journeys.

Instead of selecting only a bus stop, the user could specify a desired route through several stops or a final destination. For example:

```text
Start stop: 2
Intermediate stops: 5, 8
Destination: 12
```

The application would then display only buses that serve the requested journey.

Possible extensions could include:

* filtering by final destination,
* filtering by one or more intermediate stops,
* selecting a specific route,
* selecting a direction,
* finding routes that pass through a sequence of selected stops,
* and showing only buses that can complete the requested journey.

This would require the application to use more of the relationships between `stops.txt`, `stop_times.txt`, `trips.txt`, and `routes.txt`, rather than simply grouping arrivals by route.

## 3. AI Usage

AI tools were used throughout the development of this project as a development and review aid.

The overall architecture and the main design decisions were my own. I decided how the application should be structured, which responsibilities should belong to the different classes, and how the GTFS data should flow through the application.

The application was developed iteratively by me with substantial AI assistance in writing and refactoring the code. AI was mainly used for:

* suggesting implementations for parts of the code,
* refactoring existing code,
* improving code readability,
* simplifying and shortening some functions,
* pointing out edge cases that I had initially overlooked,
* and helping speed up the development process.

I reviewed the AI-generated and AI-suggested code before using it. I also modified or removed parts of the suggestions when they were unnecessary or did not fit the scope or structure of the project. The final implementation was therefore not used blindly, but reviewed and adapted by me.

### Testing

Testing was the part of the project where I relied most heavily on AI assistance. I have had limited previous experience with writing automated tests, so the unit and integration test code was largely generated with AI assistance.

I reviewed the generated tests and made sure I understood what each test was checking, why it was needed, and how it relates to the functionality of the application.

### README

The README content was based on my own instructions and ideas about what should be documented. AI was mainly used to improve the wording, readability, structure, and articulation of those ideas.
