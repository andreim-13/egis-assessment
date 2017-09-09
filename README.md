# Egis Architect Assessment
### Brief
As a senior member of the development team you will be expected to be a thought leader - this includes mentoring and coaching of junior developers via amongst other mechanisms code review.

[...]

Your task (should you choose to accept it). Is to review the attached code and provide comments to help guide the developer to a better solution / PR.

### First Observations
1. The delivery of the project was rather poor, Maven or other automation tool would've been preferred to deliver necessary libraries. Worst case scenario, external libraries could've been packaged together with the project.

   _In order to help the developer, I would suggest that s/he looks into Maven and describing dependencies using it. Once the concept is understood more advanced topics can be discussed._

2. More important than point above, I believe the candidate misunderstood the task given or perhaps has little knowledge of JSON. 

   _This is a very important point, in order to gather whether the developer misunderstood the task or has little knowledge of subject matter, I prefer to have a 10-15 minute chat face-to-face rather than using electronic media. The main issue that I have with the achieved result is the fact that the output is useless - it's a meaningless collection of JSON objects._
   
   _If the issue was with understanding of the requirement, perhaps more granular tasks would be required with this developer until such time that s/he is more comfortable with the requirements at a more condensed level._
   
3. The tests are rather useless, they are not testing the code written, but whether the HTML page exists, contains required data, and can be parsed. This is not what unit tests should be doing - we're not testing an external resource, but whether our code works and works well.

   _Combined with the above two observation, the question that needs answering is whether the developer has the relevant experience and understanding, and if further training / mentoring is required. I prefer to do this in a face-to-face session, and our of that session determine whether additional mentoring, formal courses, or other ways ways of improvement are required._
   
### Second Look
Going through the project in more detail, the following important elements of required coding style are missing:
1. Design. Feels as the code was written with little to no thought of how this requirement should be tackled. Solution is quick and dirty, barely spike grade. 
2. Readability. The code is terse, monolithic and was most likely done without thinking through the requirements posed.
3. Comments. Lots of them. Most of them are unnecessary and could be omitted altogether if the code was more readable.
4. Unit Tests. None of the tests actually test the code written. The point of unit tests are missed or misunderstood.

_Definitely a 1-on-1 is necessary to figure out whether the issue is with understanding, knowledge, experience, etc. Perhaps more granular tasks with 5-10 minutes "design session" before coding commences and 5-10 minutes debriefing after the coding completes._