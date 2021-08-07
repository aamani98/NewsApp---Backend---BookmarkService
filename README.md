# News APP Project

Fetch the news from third party API, search for an article, open the article and add the article to bookmarks list.
User can sign-in and sign-up to their accounts to access the news articles and bookmark the articles.

## Tech Stack Used

- SpringBoot
- MySql
- MongoDB
- Angular
- Docker

## Frontend Flow Module

Frontend is build using Angular and Bootstrap and Angular Material is used for styling.
The Components included are:
**Register Component** - user can fill the details to sign up
**Login Component** - user can fill the details to sign in
**Dashboard Component** - shows list of news articles populating from external API. User can search for an article using keywords and filter the article based on country and source.
**Full News Component** - show news details populating from external API.
**Bookmark Component** - shows all the bookmarked articles.
**Old News Component** - search for an article based on date range source and key word.

## Backend Flow Module

Backend is build using SpringBoot, JPA and for data persistence MySql and MongoDB are used.
Backend consist of two micro-services:
**User Service**

- Created a server in Spring Boot to facilitate user registration and login with MySQL as backend. Upon login, JWT token will be generated. It is used in the Filters set in bookmark service.
- Dockerize the UserService (create dockerfile).

**Bookmark Service** - for bookmarking news articles of user, which uses MongoDB for storing the bookmark information.

- Developed a server in Spring Boot to facilitate CRUD operation over bookmarked news articles stored in MongoDB. JWT Filter is applied for all the API calls of this service. JWT token is used to authorize the user access to all the resources.
- Dockerize the Bookmarked Service(create dockerfile).
