# MVP

Shopping/Order Discount Service

## Prerequisites

You will need :
- JDK 21 
- Docker (otherwise app can be launched using mvn/IDE)

## Setup Instructions

1. Open the `run.sh` file in your text editor.
2. Replace the `PATH\TO\JDK21` placeholder in the `JAVA_HOME` variable with the path to your JDK 21 installation :
   ```bash
   export JAVA_HOME="/path/to/jdk21"
3. Run the following command to make the run.sh script executable :
   ```bash 
   chmod +x run.sh

4. Launch app by running run.sh script file in a shell (ex. gitbash):
   ```bash
   ./run.sh 


## Details about this work
   - Work started to be simple focusing on DDD, TDD and Hexagonal achitecutre with no reactive elements
   - Migrating to reactive workflow and due to personal time limitation tests were no longer valid and shall be passed to build project
   - Went with the assumption Hibernate reactive was integrated with Spring-data-r2dbc so considering time I had to use Hibernate Reactive only etc..
   - Many options were available for architecture (strictly abiding by the rules or breaking some for practical use ...)
   - Was an interesting exercice and shall continue working on it for knowledge purpose