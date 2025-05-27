**Assignment: Deploy WEB App on HTTP Port: 8080 with Autoscaling and Load balancer**

**This web application can be deployed in a single VM. However considering High availability I have deployed it in Managed Instance group and configured load balancer.**

**Step 1: Prepare Webpage content**

Logged in to DevOps Server Lab Desktop

Logged in to my GitHub Repository

Cloned "https://github.com/ramanujds/devops-practice " by import repository feature in GitHub

Opened Visual Studio Code and cloned devops-prctice git repo from my GitHub repo

edited webpage output from "Hello World" to my own word by editing "/devops-practicesrc/main/java/com/rds/mywebapp/api/MyWebApi.java" with vi editor.

Cummited changes and pushed the updates to my git master branch with following commands.

git add MyWebApi.java
git commit -m "updated webpage content"
git push -u origin master


**Step 2: Create Health Check**

Name: mywebapp-healthcheck
Scope: Regional
Region: Asia-South1
Protocol:TCP, Port 8080
Left other settings as default and created health check.


**Step 3: Create Instance Template**

Name: my-webapp-instance-template
Region: Acia-South1
Type:E2 Micro
Provisioning Model: Spot
On VM Termination: Stop
Boot Disk: Ubuntu
Service Account: Compute Engine Default Service Account
Access Scope: Allow Default Access
Firewall: Allow HTTP Traffic
Advanced Option:
Network Interface: Default Network
Management: Automation Startup Script

sudo apt update -y
sudo apt install git -y
sudo apt install default-jdk -y
sudo apt install maven -y
git clone https://github.com/muruganashokkumar/devops-practice
cd devops-practice
mvn clean package
sudo nohup java -jar target/my-web-app-0.0.1-SNAPSHOT.jar &


**Step 4: Create Instance Group**

Name: my-webapp-instancegroup
selected "my-webapp-instance-template"
Location: Single zone: Asia-South1
Auto Scaling Mode: Add and remove instance to the group
Instance: Minimum 2, Maximum 6
Autoscaling Signal: CPU Utilization (60%)
Auto healing Healthcheck: mywebapp-healthcheck
Updates during VM instance repair: Keep the same instance configuration
Port Mapping: http 8080

**Step 4: Create Load Balancer:**

Name:my-web-lb
Type: Application Load Balancer HTTP/HTTPS
Public Facing (External)
Global or single region deployment
Load balancer generation: Global external Application Load Balancer
Frontend Config:
Name:myweb-frontend
Protocol: HTTP
IP Version:IPV4
Port:8080

**Backend Config:**
Name:my-web-bakend
Backend Type: Instance Group
Protocol:HTTP
Named Port: http
Timeout:30
IP Address Policy: Only IPV4
Selected Instance group: my-webapp-instancegroup
Port Numbers: 8080
Balancing mode: Utilization
Selected health check as "mywebapp-healthcheck "
Create
