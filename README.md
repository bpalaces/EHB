# EHB


## TODO

### Logic:
* Come to conclusion about issues in the SRS diagram by either not implementing them or comming up with a better way. Issues include the loop back 10% force increase, the continual sounds, the whole idea of having engaging and disengaging button states rather than engaged and disengaged. I believe we are allows to deviate from the SRS diagram as necessary, etc.

### Control Logic Layer
* Testing

### Virtual Layer
* Resolve Button polling issue.
* Add logic to prevent sound from play on top of its self, given sound file length play if deltaT between function calls is less than said length.
* Testing

### Unit Testing
* Create a Unit test package and unit tests for each of the virtual and control logic components using JUnit. 
* Make sure all the components being tested execute every possible branch (i.e 100% code coverage), IntelliJ has built in features for this.
