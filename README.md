<h1>Railway Logistics Simulator</h1>
<h2>Overview</h2>
<p>Railway Logistics Simulator (RLS) is a multi-threaded Java application that simulates railway logistics, including train movement, station management, and collision prevention. The system models real-time train operations with various types of wagons, dynamic pathfinding, and automated logging.</p>
<h2>Features</h2>
<ul>
  <li><b>Train Simulation: </b>Real-time train movement with dynamically determined routes.</li>
  <li><b>Multi-Threading & Synchronization:</b> Prevents collisions and ensures efficient train scheduling.</li>
  <li><b>Graph-Based Pathfinding: </b>Algorithmic route selection between 100 stations.</li>
  <li><b>Collision Prevention: </b>Ensures only one train can be on a track segment at a time, with queueing for busy routes.</li>
  <li><b>Custom Exception Handling:</b> Includes RailroadHazard for over-speeding trains.</li>
  <li><b>Diverse Wagon Types:</b> Passenger, cargo, and specialized wagons with unique properties.</li>
  <li><b>Automated Logging:</b> Saves train state and cargo reports every 5 seconds.</li>
</ul>
<h2>Installation</h2>
<ol>
  <li>Clone the repository: <b>git clone https://github.com/wwydra/RLS</b></li>
  <li>Navigate to a specific project directory: <b>cd RLS</b></li>
  <li>Open the project in your preferred IDE.</li>
</ol>
<h2>Requirements</h2>
<ul>
  <li>Java 8 or higher</li>
  <li>An IDE: Such as IntelliJ IDEA, Eclipse, or VS Code (recommended for development).</li>
</ul>
