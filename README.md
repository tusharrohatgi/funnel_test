# funnel test

Purpose - Performing test on Sales funnel Site.

Pre-Requisites : https://hello.friday.de must be Up and running.

Tools/Technologies  Used 
   
  
    JAVA 
    Selenium 
    Maven 
    Jacoco 
    TestNG 
  
Test Case Count : 4

          
    Testing with Single DataProvider provided 3 diff set of data in single test cases.(Re-using code.) 
    Test Negative Scenario 
  

Features Present
  
  
     a. Picking Browser from the property file.
     b. Executing Test Cases using @Test with @DataProvider for different set of data for same test case.
     c. Using @AfterMethod and @BeforeMethod for setting up the @Test scenarios.
     d. Generating jacoco after executing 
   
  Commands to Execute
  
    mvn clean install - Install and Download Jars and executes test(s).
    mvn clean instal -DskipTest = clean and download/install jars but no test(s) are executed.
    mvn clean test = Executes Test cases only.
   
       
