---
title: Making an HTTP call from Android
type: lab
duration: "1:30"
creator:
    name: Yuliya Kaleda
    city: NYC
---

# ![](https://ga-dash.s3.amazonaws.com/production/assets/logo-9f88ae6c9c3871690e33280fcf557f33.png) Making an HTTP call from Android

## Introduction

> ***Note:*** _This can be a pair programming activity or done independently._

In this lab, you will be building an app that sends a GET request to pull information from the Walmart API. You will be working with the search endpoint of the API and using it to find and display information about tea, cereal, and chocolate products.

You'll need to generate your own API key to build your app with three buttons (tea, chocolate, cereal) and a list view. When the button is clicked the list view gets populated with the information about the appropriate products using data from the API.  

Your app doesn't need to look like the deliverable below, but it needs to have the same functionality.

## Exercise

#### Requirements

- Register your _development_ app and get an API key from the [Walmart Labs Developer Page](https://developer.walmartlabs.com/member)
- [Read up](https://developer.walmartlabs.com/io-docs) on the `search` endpoint for how to structure your API call
- Your app should contain three buttons: tea, chocolate, cereal
- Your app should contain one ListView that will get populated with product names pulled from the Walmart API endpoint
- Make sure the app is thread safe:

  * Check if the connection is available
  * Use `AsyncTask` to pull the data from the API
  * The ListView should get updated on the UI thread
 

**Bonus:**

- In addition to the name of products, show the prices of the corresponding products
- When one AsyncTask gets fired, make sure you will cancel other AsyncTasks that may be making HTTP requests

#### Deliverable

Here is a gif that demonstrates what the app's functionality should look like:

<p align="center">
  <img src="./screenshots/networking-lab.gif" width="320">
</p>

## Resources

[Walmart API Docs](https://developer.walmartlabs.com/io-docs)
