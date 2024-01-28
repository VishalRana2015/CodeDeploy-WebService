<h1>Code Pipeline Demonstration Project</h1>

<h3>Frameworks and Languages used</h3>
- Spring Boot - Java
- Linux Scripts

<h3> /scripts </h3>
This folder contains scripts that run as part of Code Deployment.

- applicationStop.sh : This scripts run before CodeDeploy Agent starts deploying the new version of the application. We have used this script to stop the already running project gracefully.
- applicationStart.sh : This scripts run after CodeDeploy Agent has downloaded and set the project. The script is used to start the application on the server.

<h3> appspec.yml</h3>

- The name of the file should be appspec.yml (case sensitive).
- Code Deploy Agent installed on EC2 instances uses this file to run deployment instructions.

<h3> buildspec.yml </h3>

- Name of the file is case sensitive (Optionally you can supply different name in Code Build Project).
- This file is used by CodeBuild to build the project.

<h3>CI/CD</h3>
Whenever a new commit happens on the master branch, AWS triggers Code Pipeline. That pipeline builds the project and then deploys on an EC2 Instance.

