# pet-shop

An online shop for easy access to our furry (or scaly) companions!

pet-shop is a platform that connects sellers and buyers of domestic animals.
It is still under development.

# Setup Development Environment

## Prequisites

* MongoDB
* JAVA 8
* Maven
* EJB

## Procedure

Fork the repo to your own profile

* clone

` git clone https://github.com/{your user name}/pet-shop`

* add a remote repo to

` git remote add upstream https://github.com/glennfaison/pet-shop`

* enter development branch
` git checkout develop`

* fetch and pull latest changes

`git fetch --all`
`git pull --rebase upstream develop`

 * make changes and submit pull request to the develop branch. The master branch will
 only hold stable released versions
 
 ## Build and execute
 
` mvn package && java -jar target/pet-shop-0.1.0-SNAPSHOT.jar`
