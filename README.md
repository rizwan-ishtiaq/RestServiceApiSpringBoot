# RestServiceApiSpringBoot
Rest Service Api with Spring Boot (demo)

In memory Service that handles a (very simple) recruiting process. The process requires two types of objects: job offers and applications from candidates.

<strong>Offer</strong> 
<ul>
  <li>jobTitle (unique)</li>
  <li>startDate</li>
  <li>numberOfApplications</li>
</ul>

<strong>Application</strong>
<ul>
  <li>related offer</li>
  <li>candidate email (unique per Offer)</li>
  <li>resume text</li>
  <li>applicationStatus (APPLIED, INVITED, REJECTED, HIRED)</li>
</ul>

<strong>OPERATIONS</strong>
<ul>
  <li>Create a job offer and read a single and list all offers</li>
  <li>Apply for an job offer</li>
  <li>Read one and list all applications per offer</li>
  <li>Change progress / status of an application</li>
  <li>Track the number of applications</li>
  <li>Status change triggers a notification </li>
</ul>

# Run Project
<ol type="1">
  <li>This is simple maven project</li>
  <li>Download or Clone project</li>
  <li>Goto project root directory</li>
  <li>mvn spring-boot:run</li>
  <li>http://localhost:8080/</li>
</ol>

# End points
<table>
  <tr>
    <th>Method</th>
    <th>Endpoint URL</th>
    <th>Description</th>
  </tr>
  <tr>
    <td>GET</td>
    <td>/</td>
    <td>show All the Endpoints</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/jobs</td>
    <td>List all offers</td>
  </tr>
  <tr>
    <td>POST</td>
    <td>/jobs</td>
    <td>Add new offer</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/jobs/{jobTitle}</td>
    <td>Get Single job</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/jobs/{jobTitle}/applications</td>
    <td>List all application on offer</td>
  </tr>
  <tr>
    <td>POST</td>
    <td>/jobs/{jobTitle}/applications</td>
    <td>Apply candidate application</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/jobs/{jobTitle}/applications/{candidateEmail}</td>
    <td>Return single application on offer for candidate</td>
  </tr>
  <tr>
    <td>PUT</td>
    <td>/jobs/{jobTitle}/applications/{candidateEmail}/change/{status}</td>
    <td>Change the status of particular job</td>
  </tr>
</table>

# Technology Stack
<ul>
  <li>REST API</li>
  <li>java 1.8</li>
  <li>Spring Boot 1.5.9</li>
  <li>Spring 4.3.13</li>
  <li>maven</li>
  <li>Junit</li>
  <li>mockito</li>
  <li>Spring Test</li>
  <li>JSON (jackson)</li>
  <li>JAVAX Validation</li>
</ul>

# Usage
For consuming service one can use firefox or chorme extension search for REST service testing<br/>

set request header<br/>
Content-Type=application/json;charset=UTF-8<br/>

GET 	http://localhost:8080/<br/>
Will Return all the endpoints in JSON<br/>

POST	http://localhost:8080/jobs<br/>
Add new offer (use following body)<br/>
{"jobTitle":"job1"}<br/>

GET	http://localhost:8080/jobs	<br/>
List all offers<br/>

GET	http://localhost:8080/jobs/{jobTitle}<br/>
GET	http://localhost:8080/jobs/job1<br/>
Get Single job<br/>

POST	http://localhost:8080/jobs/{jobTitle}/applications<br/>
POST	http://localhost:8080/jobs/job1/applications<br/>
Apply candidate application (use following body)<br/>
{<br/>
"candidateEmail":"rizwan@mail.com",<br/>
"resumeText":"This is my resume"<br/>
}<br/>

GET	http://localhost:8080/jobs/{jobTitle}/applications<br/>
GET	http://localhost:8080/jobs/job1/applications<br/>
List all application on offer<br/>

GET	http://localhost:8080/jobs/{jobTitle}/applications/{candidateEmail}<br/>
GET	http://localhost:8080/jobs/job1/applications/rizwan@mail.com<br/>
Return single application on offer for candidate<br/>

PUT	http://localhost:8080/jobs/{jobTitle}/applications/{candidateEmail}/change/{status}<br/>
PUT	http://localhost:8080/jobs/job1/applications/rizwan@mail.com/change/INVITED<br/>
Change the status of particular job<br/>
