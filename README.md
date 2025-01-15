# Overview 
This Project is a registration page, which is used for my local swim-club, to registrate new Users.
This is hosted under (https://registration.erster-bsc-pforzheim.de/). This is not production ready and is just hostet for production purposes.
Currently the IBAN Check is always true for easy testing and will be activated once in production.

# Techstack
This project uses Spring boot (Java) as an API-Service which serves the singe-page-application, which is build with vue.js.
For vue.js we also use the folliwing plugins:
- tailwind for styling
- pinia for stage management across the platform
- vue router for single page application routting

# Basic function in a nutshell
After the form has been filled, data is send to the backend, which sends the info via spring mail service to the configurated mails (Registration of the club and the repsonsible of the chosen course.)




